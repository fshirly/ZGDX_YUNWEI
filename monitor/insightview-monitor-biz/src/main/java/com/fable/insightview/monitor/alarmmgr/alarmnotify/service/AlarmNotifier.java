package com.fable.insightview.monitor.alarmmgr.alarmnotify.service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.fable.insightview.monitor.alarmmgr.alarmnotify.mapper.AlarmNotifyCfgMapper;
import com.fable.insightview.monitor.alarmmgr.alarmnotifyfilter.mapper.AlarmNotifyFilterMapper;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmNotifyCfgBean;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmNotifyFilterBean;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmNotifyToUsersBean;
import com.fable.insightview.monitor.discover.mapper.MODeviceMapper;
import com.fable.insightview.monitor.entity.AlarmNode;
import com.fable.insightview.monitor.event.EventType;
import com.fable.insightview.monitor.mobject.entity.MObjectDefBean;
import com.fable.insightview.monitor.mobject.mapper.MobjectInfoMapper;
import com.fable.insightview.monitor.notificationtasktracker.entity.EmailNotificationTaskBean;
import com.fable.insightview.monitor.notificationtasktracker.entity.SmsNotificationTaskBean;
import com.fable.insightview.monitor.notificationtasktracker.mapper.EmailNotificationTaskMapper;
import com.fable.insightview.monitor.notificationtasktracker.mapper.SmsNotificationTaskMapper;
import com.fable.insightview.monitor.utils.OgnlUtil;
import com.fable.insightview.platform.event.BaseEvent;
import com.fable.insightview.platform.event.DefaultEventDispatcher;
import com.fable.insightview.platform.event.Listener;
import com.fable.insightview.platform.message.entity.PhoneNotificationTaskBean;
import com.fable.insightview.platform.message.mapper.PhoneNotificationTaskMapper;

//@Component("alarmNotifier")
public class AlarmNotifier {
	@Autowired
	private AlarmNotifyCfgMapper alarmNotifyCfgMapper;
	@Autowired
	private AlarmNotifyFilterMapper NotifyFilterMapper;
	@Autowired
	private EmailNotificationTaskMapper emailNotificationTaskMapper;
	@Autowired
	private SmsNotificationTaskMapper smsNotificationTaskMapper;
	@Autowired
	private MobjectInfoMapper mobjectInfoMapper;
	@Autowired
	MODeviceMapper moDeviceMapper;
	@Autowired
	IAlarmNotifyCfgService notifyCfgService;
	@Autowired
	PhoneNotificationTaskMapper phoneNotificationTaskMapper;
	// @Autowired
	private Cache alarmNotifyConfigCache;

	private DefaultEventDispatcher alarmEventDispatcher;

	private static final Logger logger = org.slf4j.LoggerFactory
			.getLogger(AlarmNotifier.class);
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public void init() {
		logger.info("初始化，向缓存中添加告警通知数据");
		List<AlarmNotifyCfgBean> notifycfgList = alarmNotifyCfgMapper
				.getAllAlarmNotifyCfg();
		for (int i = 0; i < notifycfgList.size(); i++) {
			Element element = new Element(notifycfgList.get(i).getPolicyID(),
					notifycfgList.get(i));
			alarmNotifyConfigCache.put(element);
		}

		alarmEventDispatcher.addEventListener(EventType.ALARM_NEW.name(),
				new Listener() {

					@Override
					public synchronized boolean onEvent(BaseEvent event) {
						doAfterReceive(event, "ALARM_NEW");
						return false;
					}
				}, 1);

		// 重复告警
		alarmEventDispatcher.addEventListener(EventType.ALARM_UPDATE.name(),
				new Listener() {
					@Override
					public synchronized boolean onEvent(BaseEvent event) {
						doAfterReceive(event, "ALARM_UPDATE");
						return false;
					}
				}, 1);
		
		// 告警升级
		alarmEventDispatcher.addEventListener(EventType.ALARM_UPGRADE.name(),
				new Listener() {
					@Override
					public synchronized boolean onEvent(BaseEvent event) {
						doAfterReceive(event, "ALARM_UPGRADE");
						return false;
					}
				}, 1);

		// 告警清除
		alarmEventDispatcher.addEventListener(EventType.ALARM_CLEAR.name(),
				new Listener() {
					@Override
					public synchronized boolean onEvent(BaseEvent event) {
						doAfterReceive(event, "ALARM_CLEAR");
						return false;
					}
				}, 1);

		// 告警删除
		alarmEventDispatcher.addEventListener(EventType.ALARM_DELETE.name(),
				new Listener() {
					@Override
					public synchronized boolean onEvent(BaseEvent event) {
						doAfterReceive(event, "ALARM_DELETE");
						return false;
					}
				}, 1);
	}

	public boolean doAfterReceive(BaseEvent event, String eventType) {
		try {
			AlarmNode alarm = (AlarmNode) event.getTarget();
			// 获得sourceMOClassName、moClassName
			MObjectDefBean sourceMobject = mobjectInfoMapper
					.getMobjectById(alarm.getSourceMOClassID());
			MObjectDefBean mobject = mobjectInfoMapper.getMobjectById(alarm
					.getMoclassID());
			if (sourceMobject != null) {
				alarm.setSourceMOClassName(sourceMobject.getClassLable());
			}
			if (mobject != null) {
				alarm.setMoClassName(mobject.getClassLable());
			}
			logger.info("eventType：" + eventType);
			logger.info("告警标题======" + alarm.getAlarmTitle());
			logger.info("告警ID======" + alarm.getAlarmID());
			logger.info("告警事件alarmDefineID======" + alarm.getAlarmDefineID());
			logger.info("告警事件recoverAlarmDefineID======" + alarm.getRecoverAlarmDefineID());
			logger.info("告警发生时间=====" + alarm.getStartTime());
			logger.info("告警重复次数=====" + alarm.getRepeatCount());
			logger.info("告警等级=====" + alarm.getAlarmLevel());
			logger.info("告警类型=====" + alarm.getAlarmType());
			logger.info("告警源=====" + alarm.getSourceMOID());
			List<AlarmNotifyCfgBean> matchPolicyList = getMatchPolicy(alarm, eventType);
			createNotificationTask(matchPolicyList, alarm, eventType);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	/**
	 * 匹配通知配置
	 * 
	 * @param alarm
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AlarmNotifyCfgBean> getMatchPolicy(AlarmNode alarm, String eventType) {
		logger.info("匹配通知配置。。。。start");
		// 缓存中所有通知配置id
		List<Integer> policyIdList = alarmNotifyConfigCache.getKeys();
		// 满足条件的通知配置
		List<AlarmNotifyCfgBean> matchPolicyList = new ArrayList<AlarmNotifyCfgBean>();
		for (int i = 0; i < policyIdList.size(); i++) {
			int policyID = policyIdList.get(i);
			Element element = alarmNotifyConfigCache.get(policyID);
			AlarmNotifyCfgBean alarmNotifyCfg = (AlarmNotifyCfgBean) element
					.getObjectValue();
			int alarmOnCount = alarmNotifyCfg.getAlarmOnCount();
			logger.info("接收到的告警的重复次数为：" + alarm.getRepeatCount());
			int alarmRepeatCount = alarm.getRepeatCount() + 1;
			logger.info("告警实际产生的重复次数为：" + alarmRepeatCount);
			logger.info("通知策略" + policyID + "的告警重复次数为：" + alarmOnCount);
			boolean isNeedMatchConfig = this.isNeedMatchConfig(eventType, alarmOnCount, alarmRepeatCount);
			logger.info("是否需要进行匹配通知：" + isNeedMatchConfig);
			//如果通知策略的重复次数为默认的0，或者接收的告警重复次数大于等于设置的值 ，才进行通知策略匹配
			if(isNeedMatchConfig){
				logger.info("告警重复次数满足通知策略中配置的通知次数，开始通知过滤条件匹配......");
				// 获得通知配置下的所有通知策略
				List<AlarmNotifyFilterBean> filterList = NotifyFilterMapper
				.getAlarmNotifyFilterByID(policyID);
				boolean isMatch = true;
				boolean levelMatch = false;
				boolean typeMatch = false;
				boolean sourceMatch = false;
				boolean alarmMatch = false;
				// 如果通知配置下有通知策略，则判断是否匹配
				if (filterList.size() > 0) {
					// 分别获得四中类别的通知策略
					List<AlarmNotifyFilterBean> levelFilterList = new ArrayList<AlarmNotifyFilterBean>();
					List<AlarmNotifyFilterBean> typeFilterList = new ArrayList<AlarmNotifyFilterBean>();
					List<AlarmNotifyFilterBean> sourceFilterList = new ArrayList<AlarmNotifyFilterBean>();
					List<AlarmNotifyFilterBean> alarmFilterList = new ArrayList<AlarmNotifyFilterBean>();
					for (int j = 0; j < filterList.size(); j++) {
						String filterKey = filterList.get(j).getFilterKey();
						if ("AlarmLevel".equals(filterKey)) {
							levelFilterList.add(filterList.get(j));
						} else if ("AlarmType".equals(filterKey)) {
							typeFilterList.add(filterList.get(j));
						} else if ("AlarmSourceMOID".equals(filterKey)) {
							sourceFilterList.add(filterList.get(j));
						} else {
							alarmFilterList.add(filterList.get(j));
						}
					}
					
					// 过滤关键字为告警等级的通知策略是否匹配
					if (levelFilterList.size() > 0) {
						for (int j = 0; j < levelFilterList.size(); j++) {
							int filterKeyValue = levelFilterList.get(j)
							.getFilterKeyValue();
							if (filterKeyValue == alarm.getAlarmLevel()) {
								levelMatch = true;
								break;
							} else {
								levelMatch = false;
								continue;
							}
						}
					} else {
						levelMatch = true;
					}
					
					// 过滤关键字为告警类型的通知策略是否匹配
					if (typeFilterList.size() > 0) {
						for (int j = 0; j < typeFilterList.size(); j++) {
							int filterKeyValue = typeFilterList.get(j)
							.getFilterKeyValue();
							if (filterKeyValue == alarm.getAlarmType()) {
								typeMatch = true;
								break;
							} else {
								typeMatch = false;
								continue;
							}
						}
					} else {
						typeMatch = true;
					}
					
					// 过滤关键字为告警源的通知策略是否匹配
					if (sourceFilterList.size() > 0) {
						for (int j = 0; j < sourceFilterList.size(); j++) {
							int filterKeyValue = sourceFilterList.get(j)
							.getFilterKeyValue();
							if (filterKeyValue == alarm.getSourceMOID()) {
								sourceMatch = true;
								break;
							} else {
								sourceMatch = false;
								continue;
							}
						}
					} else {
						sourceMatch = true;
					}
					
					// 过滤关键字为告警事件的通知策略是否匹配
					if (alarmFilterList.size() > 0) {
						for (int j = 0; j < alarmFilterList.size(); j++) {
							int filterKeyValue = alarmFilterList.get(j)
							.getFilterKeyValue();
							int fitAlarmDefineId = alarm.getAlarmDefineID();
							if("ALARM_CLEAR".equals(eventType) && alarm.getRecoverAlarmDefineID() != 0 ){
								fitAlarmDefineId = alarm.getRecoverAlarmDefineID();
							}
							logger.info("与通知策略告警事件匹配的defineId为：" + fitAlarmDefineId);
							if (filterKeyValue == fitAlarmDefineId) {
								alarmMatch = true;
								break;
							} else {
								alarmMatch = false;
								continue;
							}
						}
					} else {
						alarmMatch = true;
					}
					isMatch = levelMatch && typeMatch && sourceMatch && alarmMatch;
				} else {
					// 如果通知配置下没有通知策略，则不匹配
					isMatch = false;
				}
				logger.info("levelMatch：" + levelMatch);
				logger.info("typeMatch：" + typeMatch);
				logger.info("sourceMatch：" + sourceMatch);
				logger.info("alarmMatch：" + alarmMatch);
				logger.info("匹配通知配置的结果：" + isMatch);
				// 满足条件的通知配置
				if (isMatch == true) {
					matchPolicyList.add(alarmNotifyCfg);
				}
			}
		}
		logger.info("匹配通知配置。。。。end");
		return matchPolicyList;
	}

	/**
	 * 创建通知任务
	 */
	public boolean createNotificationTask(
			List<AlarmNotifyCfgBean> matchPolicyList, AlarmNode alarmEvent,
			String eventType) {
		logger.info("创建通知任务。。。。start");
		boolean flag = false;
		if (matchPolicyList.size() > 0) {
			for (int i = 0; i < matchPolicyList.size(); i++) {
				logger.info("获得满足条件的通知配置信息");
				AlarmNotifyCfgBean alarmNotifyCfg = matchPolicyList.get(i);
				int isSms = alarmNotifyCfg.getIsSms();
				int isEmail = alarmNotifyCfg.getIsEmail();
				int isPhone = alarmNotifyCfg.getIsPhone();
				int voiceMessageType = alarmNotifyCfg.getVoiceMessageType();
				
				if (isSms == 1 || isEmail == 1 || isPhone == 1) {
					int maxTimes = alarmNotifyCfg.getMaxTimes();
					logger.info("获得满足条件的通知配置下的通知用户");
					List<AlarmNotifyToUsersBean> notifyUserList = notifyCfgService
							.getAllNotifyToUsersByID(alarmNotifyCfg.getPolicyID());
					String smsAlarmContent = "";
					if(isSms == 1){
						if ("ALARM_CLEAR".equals(eventType)) {
							smsAlarmContent = alarmNotifyCfg.getSmsClearAlarmContent();
						}else if ("ALARM_DELETE".equals(eventType)) {
							smsAlarmContent = alarmNotifyCfg.getSmsDeleteAlarmContent();
						}else{
							smsAlarmContent = alarmNotifyCfg.getSmsAlarmContent();
						}
					}
					
					String emailAlarmContent = "";
					if(isEmail == 1){
						if ("ALARM_CLEAR".equals(eventType)) {
							emailAlarmContent = alarmNotifyCfg.getEmailClearAlarmContent();
						}else if ("ALARM_DELETE".equals(eventType)) {
							emailAlarmContent = alarmNotifyCfg.getEmailDeleteAlarmContent();
						}else{
							emailAlarmContent = alarmNotifyCfg.getEmailAlarmContent();
						}
						if(!"".equals(emailAlarmContent) && emailAlarmContent != null){
							emailAlarmContent = emailAlarmContent.replaceAll(
									"<br/>", "\r\n");
							emailAlarmContent = emailAlarmContent.replaceAll(
									"<br>", "\r\n");
							emailAlarmContent = emailAlarmContent.replaceAll(
									"&nbsp;", " ");
						}
					}
					
					//语音通知类型为文字模板
					String phoneAlarmContent = "";
					if(isPhone == 1){
						if(voiceMessageType == 1){
							if ("ALARM_CLEAR".equals(eventType)) {
								phoneAlarmContent = alarmNotifyCfg.getPhoneClearAlarmContent();
							}else if ("ALARM_DELETE".equals(eventType)) {
								phoneAlarmContent = alarmNotifyCfg.getPhoneDeleteAlarmContent();
							}else{
								phoneAlarmContent = alarmNotifyCfg.getPhoneAlarmContent();
							}
						}
						//语音通知类型为已有的录音
						else{
							if ("ALARM_CLEAR".equals(eventType)) {
								phoneAlarmContent = alarmNotifyCfg.getClearAlarmVoicePath();
							}else if ("ALARM_DELETE".equals(eventType)) {
								phoneAlarmContent = alarmNotifyCfg.getDeleteAlarmVoicePath();
							}else{
								phoneAlarmContent = alarmNotifyCfg.getAlarmVoicePath();
							}
						}
					}
				
					for (int j = 0; j < notifyUserList.size(); j++) {
						Map<String, Object> root = new HashMap<String, Object>();
						Map<String, Object> user = new HashMap<String, Object>();
						AlarmNotifyToUsersBean notifyToUser = notifyUserList.get(j);
						if ("".equals(notifyToUser.getUserName())
								|| notifyToUser.getUserName() == null) {
							user.put("userName", "用户");
						} else {
							user.put("userName", notifyToUser.getUserName());
						}

						Timestamp startTime = alarmEvent.getStartTime();
						String startTimeStr = dateFormat.format(startTime);
						Map<String, Object> alarm = new HashMap<String, Object>();
						alarm.put("sourceMOClassName", alarmEvent
								.getSourceMOClassName());
						alarm.put("sourceMOIPAddress", alarmEvent
								.getSourceMOIPAddress());
						alarm.put("moClassName", alarmEvent.getMoClassName());
						alarm.put("moName", alarmEvent.getMoName());
						alarm.put("startTime", startTimeStr);
						alarm.put("alarmTitle", alarmEvent.getAlarmTitle());
						alarm.put("alarmContent", alarmEvent.getAlarmContent());
						alarm.put("alarmTypeName", alarmEvent
								.getAlarmTypeName());
						alarm.put("alarmLevelName", alarmEvent
								.getAlarmLevelName());
						alarm.put("sourceMOName", alarmEvent.getSourceMOName());
						
						root.put("user", user);
						root.put("alarm", alarm);
						
						String message = "";
						String recipient = "";
						// 是否需要发送短信
						try {
							if (isSms == 1) {
								logger.info("创建短信通知任务");
								message = OgnlUtil.parseText(smsAlarmContent, null, root);
								recipient = notifyToUser.getMobileCode();
								if (recipient != null && !"".equals(recipient) && !"".equals(message) && message != null) {
									SmsNotificationTaskBean smsTask = new SmsNotificationTaskBean();
									smsTask.setPhoneNumber(recipient);
									smsTask.setMessage(message);
									smsTask.setSendedTimes(0);
									smsTask.setMaxTimes(maxTimes);
									smsTask.setStatus(1);
									smsTask.setLastUpdateTime(dateFormat
											.format(new Date()));
									smsTask.setExpectSendTime(dateFormat
											.format(new Date()));
									smsTask.setSource(3);
									smsTask.setNotifyType(100);
									smsNotificationTaskMapper
											.insertNotificationTask(smsTask);
								}
							}
							// 是否需要发送邮件
							if (isEmail == 1) {
								logger.info("创建邮件通知任务");
								message = OgnlUtil.parseText(emailAlarmContent, null, root);
								recipient = notifyToUser.getEmail();
								if (recipient != null && !"".equals(recipient) && !"".equals(message) && message != null) {
									EmailNotificationTaskBean emailTask = new EmailNotificationTaskBean();
									emailTask.setEmailAddress(recipient);
									emailTask.setMessage(message);
									emailTask.setSendedTimes(0);
									emailTask.setMaxTimes(maxTimes);
									emailTask.setStatus(1);
									emailTask.setLastUpdateTime(dateFormat
											.format(new Date()));
									emailTask.setExpectSendTime(dateFormat
											.format(new Date()));
									emailTask.setSource(3);
									emailTask.setNotifyType(1);
									emailTask.setTitle("告警通知");
									emailNotificationTaskMapper.insertNotificationTask(emailTask);
								}
							}
							//是否电话语音通知
							if(isPhone == 1){
								logger.info("创建电话语音通知任务");
								if(voiceMessageType == 1){
									message = OgnlUtil.parseText(phoneAlarmContent, null, root);
								}else{
									message = phoneAlarmContent;
								}
								recipient = notifyToUser.getMobileCode();
								if (recipient != null && !"".equals(recipient) && !"".equals(message) && message != null) {
									PhoneNotificationTaskBean phoneNotificationTaskBean = new PhoneNotificationTaskBean();
									phoneNotificationTaskBean.setPhoneNumber(recipient);
									phoneNotificationTaskBean.setMessage(message);
									phoneNotificationTaskBean.setSendedTimes(0);
									phoneNotificationTaskBean.setMaxTimes(maxTimes);
									phoneNotificationTaskBean.setStatus(1);
									phoneNotificationTaskBean.setLastUpdateTime(dateFormat.format(new Date()));
									phoneNotificationTaskBean.setExpectSendTime(dateFormat.format(new Date()));
									phoneNotificationTaskBean.setSendType(0);
									phoneNotificationTaskBean.setVoiceMessageType(alarmNotifyCfg.getVoiceMessageType());
									phoneNotificationTaskBean.setName(null);
									//发送的优先级
									phoneNotificationTaskBean.setNotifyPriority(100);
									phoneNotificationTaskMapper.insertNotificationTask(phoneNotificationTaskBean);
								}
							}
							flag = true;
						} catch (Exception e) {
							logger.error("创建通知异常：" ,e);
							flag = false;
							return false;
						}
					}
				}
			}
		}
		logger.info("创建通知任务。。。。end");
		return flag;
	}

	public Cache getAlarmNotifyConfigCache() {
		return alarmNotifyConfigCache;
	}

	public void setAlarmNotifyConfigCache(Cache alarmNotifyConfigCache) {
		this.alarmNotifyConfigCache = alarmNotifyConfigCache;
	}

	public DefaultEventDispatcher getAlarmEventDispatcher() {
		return alarmEventDispatcher;
	}

	public void setAlarmEventDispatcher(
			DefaultEventDispatcher alarmEventDispatcher) {
		this.alarmEventDispatcher = alarmEventDispatcher;
	}

	/**
	 * 是否满足匹配通知条件
	 * @param eventType 告警类型
	 * @param alarmOnCount 通知策略配置告警重复次数
	 * @param alarmRepeatCount 告警实际产生的次数
	 */
	boolean isNeedMatchConfig(String eventType, int alarmOnCount, int alarmRepeatCount){
		if(alarmOnCount == 0){
			if(!"ALARM_UPDATE".equals(eventType)){
				return true;
			}
			return false;
		}else{
			if("ALARM_CLEAR".equals(eventType) || "ALARM_DELETE".equals(eventType)){
				if(alarmRepeatCount >= alarmOnCount){
					return true;
				}else{
					return false;
				}
			}else{
				if(alarmRepeatCount == alarmOnCount){
					return true;
				}
				return false;
			}
		}
	}
}
