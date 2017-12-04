package com.fable.insightview.platform.notifypolicycfg.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.fable.insightview.platform.dao.ISysUserDao;
import com.fable.insightview.platform.dutymanager.duty.service.IDutyService;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.message.entity.PfEmailNotificationTaskBean;
import com.fable.insightview.platform.message.entity.PfMessage;
import com.fable.insightview.platform.message.entity.PfOgnlUtil;
import com.fable.insightview.platform.message.entity.PhoneNotificationTaskBean;
import com.fable.insightview.platform.message.entity.PhoneVoiceBean;
import com.fable.insightview.platform.message.mapper.PfEmailNotificationTaskMapper;
import com.fable.insightview.platform.message.mapper.PfMessageMapper;
import com.fable.insightview.platform.message.mapper.PhoneNotificationTaskMapper;
import com.fable.insightview.platform.message.mapper.PhoneVoiceMapper;
import com.fable.insightview.platform.notifypolicycfg.entity.NotifyPolicyCfgBean;
import com.fable.insightview.platform.notifypolicycfg.entity.NotifyPolicyContentBean;
import com.fable.insightview.platform.notifypolicycfg.entity.NotifyToUsersBean;
import com.fable.insightview.platform.notifypolicycfg.entity.PolicyTypeBean;
import com.fable.insightview.platform.notifypolicycfg.mapper.NotifyPolicyCfgMapper;
import com.fable.insightview.platform.notifypolicycfg.mapper.NotifyToUsersMapper;
import com.fable.insightview.platform.notifypolicycfg.mapper.PolicyContentMapper;
import com.fable.insightview.platform.notifypolicycfg.mapper.PolicyTypeMapper;
import com.fable.insightview.platform.notifypolicycfg.service.INotifyPolicyCfgService;
import com.fable.insightview.platform.page.Page;
@Service("notifyPolicyCfgService")
public class NotifyPolicyCfgServiceImpl implements INotifyPolicyCfgService {
	private static final Logger logger = LoggerFactory.getLogger(NotifyPolicyCfgServiceImpl.class);
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final int TYPE_SMS = 1;
	private static final int TYPE_EMAIL = 2;
	private static final int TYPE_PHONE = 3;
	private static final int YES = 1;
	private static final int PHONETYPE_TEXT = 1;
	private static final int PHONETYPE_VOICE = 2;
	private static final int NOTSEND = 1;
	//值班负责人
	private static final int DIRECTOR = -100;
	//值班带班领导
	private static final int LEADER = -101;
	//值班成员
	private static final int MEMBER = -102;
	
	@Autowired
	PolicyTypeMapper policyTypeMapper;
	@Autowired
	PolicyContentMapper policyContentMapper;
	@Autowired
	NotifyPolicyCfgMapper notifyPolicyCfgMapper;
	@Autowired
	NotifyToUsersMapper notifyToUsersMapper;
	@Autowired
	PhoneVoiceMapper phoneVoiceMapper;
	@Autowired
	PfMessageMapper smsNotificationTaskMapper;
	@Autowired
	PfEmailNotificationTaskMapper emailNotificationTaskMapper;
	@Autowired
	PhoneNotificationTaskMapper phoneNotificationTaskMapper;
	@Autowired
	ISysUserDao sysUserDao;
	@Autowired
	IDutyService dutyService;
	
	@Override
	public List<PolicyTypeBean> getAllPolicyType() {
		logger.info("获得所有的策略类型。。。。start");
		List<PolicyTypeBean> typeLst = policyTypeMapper.getAllType();
		PolicyTypeBean typeBean = new PolicyTypeBean();
		//添加默认的通用模板
		typeBean.setTypeId(-1);
		typeBean.setTypeName("通用");
		typeLst.add(0, typeBean);
		return typeLst;
	}
	
	@Override
	public Map<String, Object> getTypeContent(int typeId) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("typeId", typeId);
		List<NotifyPolicyContentBean> policyContentLst = policyContentMapper.getPolicyTypeContent(param);
		//策略类型初始化短信模板
		List<NotifyPolicyContentBean> smsContentLst = new ArrayList<NotifyPolicyContentBean>();
		//策略类型初始化邮件模板
		List<NotifyPolicyContentBean> emailContentLst = new ArrayList<NotifyPolicyContentBean>();
		//策略类型初始化电话语音模板
		List<NotifyPolicyContentBean> phoneContentLst = new ArrayList<NotifyPolicyContentBean>();
		for (NotifyPolicyContentBean bean : policyContentLst) {
			int type = bean.getNotifyType();
			if(type == TYPE_SMS){
				smsContentLst.add(bean);
			}else if(type == TYPE_EMAIL){
				emailContentLst.add(bean);
			}else if(type == TYPE_PHONE){
				phoneContentLst.add(bean);
			}
		}
		result.put("smsContentLst", smsContentLst);
		result.put("emailContentLst", emailContentLst);
		result.put("phoneContentLst", phoneContentLst);
		return result;
	}

	@Override
	public List<NotifyPolicyCfgBean> selectNotifyPolicyCfg(
			Page<NotifyPolicyCfgBean> page) {
		return notifyPolicyCfgMapper.selectNotifyPolicyCfg(page);
	}

	@Override
	public boolean delNotifyCfg(int policyId) {
		try {
			//删除该通知策略的通知模板
//			policyContentMapper.delByPolicyId(policyId);
			
			//删除该通知策略的通知用户
//			notifyToUsersMapper.delByPolicyId(policyId);
			
			//删除通知策略
			notifyPolicyCfgMapper.updateMFalgByID(policyId);
			return true;
		} catch (Exception e) {
			logger.error("删除通知策略异常：",e);
		}
		return false;
	}

	@Transactional
	@Override
	public boolean delNotifyCfgs(String policyIds) {
		try {
			//批量删除该通知策略的通知模板
//			policyContentMapper.delByPolicyIds(policyIds);
			
			//批量删除该通知策略的通知用户
//			notifyToUsersMapper.delByPolicyIds(policyIds);
			
			//批量删除通知策略
			notifyPolicyCfgMapper.updateMFalgByIDs(policyIds);
			return true;
		} catch (Exception e) {
			logger.error("批量删除通知策略异常：",e);
		}
		return false;
	}

	@Override
	public Map<String, Object> getPolicyContent(int policyId) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<NotifyPolicyContentBean> policyContentLst = policyContentMapper.getContentByPolicyId(policyId);
		//策略类型初始化短信模板
		List<NotifyPolicyContentBean> smsContentLst = new ArrayList<NotifyPolicyContentBean>();
		//策略类型初始化邮件模板
		List<NotifyPolicyContentBean> emailContentLst = new ArrayList<NotifyPolicyContentBean>();
		//策略类型初始化电话语音模板
		List<NotifyPolicyContentBean> phoneContentLst = new ArrayList<NotifyPolicyContentBean>();
		for (NotifyPolicyContentBean bean : policyContentLst) {
			int type = bean.getNotifyType();
			if(type == TYPE_SMS){
				smsContentLst.add(bean);
			}else if(type == TYPE_EMAIL){
				emailContentLst.add(bean);
			}else if(type == TYPE_PHONE){
				phoneContentLst.add(bean);
			}
		}
		result.put("smsContentLst", smsContentLst);
		result.put("emailContentLst", emailContentLst);
		result.put("phoneContentLst", phoneContentLst);
		return result;
	}

	@Override
	public List<PhoneVoiceBean> getAllVoice() {
		return phoneVoiceMapper.queryAllPhoneVoice();
	}

	@Override
	public boolean checkCfgName(NotifyPolicyCfgBean bean) {
		int count = notifyPolicyCfgMapper.getByNameAndID(bean);
		if(count > 0){
			return true;
		}
		return false;
	}

	@Override
	public boolean doEditPolicyCfg(NotifyPolicyCfgBean bean, String editFlag) {
		logger.info("{}通知策略",editFlag);
		try {
			int policyId = -1;
			if("add".equals(editFlag)){
				//新增通知策略
				notifyPolicyCfgMapper.insertAlarmNotifyCfg(bean);
			}else{
				//更新通知策略
				notifyPolicyCfgMapper.updateNotifyCfgByID(bean);
				//删除之前的通知用户
				notifyToUsersMapper.delByPolicyId(bean.getPolicyId());
				//删除之前的通知模板
				policyContentMapper.delByPolicyId(bean.getPolicyId());
			}
			policyId = bean.getPolicyId();
			logger.info("通知策略的id为：{}",policyId);
			
			//新增短信模板
			int isSms = bean.getIsSms();
			if(isSms == YES){
				String smsArrayData = bean.getSmsArrayData();
				if(!"".equals(smsArrayData) && smsArrayData != null){
					saveContent(policyId, smsArrayData);
				}
			}
			//新增邮件模板
			int isEmail = bean.getIsEmail();
			if(isEmail == YES){
				String emailArrayData = bean.getEmailArrayData();
				if(!"".equals(emailArrayData) && emailArrayData != null){
					saveContent(policyId, emailArrayData);
				}
			}
			//新增电话语音模板
			int isPhone = bean.getIsPhone();
			if(isPhone == YES){
				String phoneArrayData = bean.getPhoneArrayData();
				if(!"".equals(phoneArrayData) && phoneArrayData != null){
					saveContent(policyId, phoneArrayData);
				}
			}
			//新增通知用户
			String userArrayData = bean.getUserArrayData();
			if(!"".equals(userArrayData) && userArrayData != null){
				List<NotifyToUsersBean> userLst = JSON.parseArray(userArrayData, NotifyToUsersBean.class);
				for (NotifyToUsersBean user : userLst) {
					user.setId(null);
					user.setPolicyId(policyId);
					notifyToUsersMapper.insertNotifyToUsers(user);
				}
			}
			return true;
		} catch (Exception e) {
			logger.error("保存通知策略异常：",e);
		}
		return false;
	}
	
	/**
	 * 新增通知策略模板
	 * @param policyId
	 * @param arrayData
	 */
	void saveContent(int policyId,String arrayData) throws Exception{
		List<NotifyPolicyContentBean> array = JSON.parseArray(arrayData,NotifyPolicyContentBean.class);
		for (int i = 0; i < array.size(); i++) {
			NotifyPolicyContentBean policyContentBean = array.get(i);
			policyContentBean.setId(null);
			policyContentBean.setPolicyId(policyId);
			policyContentMapper.insertNotifyToUsers(policyContentBean);
		}
	}

	@Override
	public Map<String, Object> doInitPolicyCfg(int policyId) {
		logger.info("通知策略的模板为：{}",policyId);
		//获得通知策略的模板
		Map<String, Object> result = this.getPolicyContent(policyId);
		//获得通知策略
		NotifyPolicyCfgBean policyCfg = notifyPolicyCfgMapper.getNotifyCfgByID(policyId);
		result.put("policyCfg", policyCfg);
		//获得通知策略的通知用户
		List<NotifyToUsersBean> userLst = notifyToUsersMapper.getUsersByPolicyID(policyId);
		result.put("userLst", userLst);
		return result;
	}

	@Override
	public List<NotifyPolicyCfgBean> getAllNotifyPolicy() {
		return notifyPolicyCfgMapper.getAllNotifyPolicyCfg();
	}

	/**
	 * 根据通知策略创建通知任务
	 */
	@Override
	public void notify(Map<String, Object> param) {
		try {
			int policyId = Integer.parseInt(param.get("policyId").toString());
			//根据id获得通知策略
			NotifyPolicyCfgBean notifyPolicyCfgBean = notifyPolicyCfgMapper.getNotifyCfgByID(policyId);
			if(notifyPolicyCfgBean != null){
				NotifyPolicyContentBean selectContent = new NotifyPolicyContentBean();
				selectContent.setPolicyId(policyId);
				int isSms = notifyPolicyCfgBean.getIsSms();
				int isEmail = notifyPolicyCfgBean.getIsEmail();
				int isPhone = notifyPolicyCfgBean.getIsPhone();
				//语音通知类型：1 文字模板 2录音
				int voiceMessageType = PHONETYPE_TEXT;
				
				//需要发送短信,获得短信发送的模板
				String smsContent = "";
				String emailContent = "";
				String phoneContent = "";
				if(isSms == YES){
					selectContent.setTypeId(TYPE_SMS);
					String smsContentName = param.get("smsContentName").toString();
					selectContent.setName(smsContentName);
					NotifyPolicyContentBean smsBean = policyContentMapper.getByPolicyAndName(selectContent);
					smsContent = smsBean.getContent();
				}
				// 获得邮件发送的模板
				if (isEmail == YES) {
					selectContent.setTypeId(TYPE_EMAIL);
					String emailContentName = param.get("emailContentName")
							.toString();
					selectContent.setName(emailContentName);
					NotifyPolicyContentBean emailBean = policyContentMapper
							.getByPolicyAndName(selectContent);
					emailContent = emailBean.getContent();
					if (!"".equals(emailContent) && emailContent != null) {
						emailContent = emailContent.replaceAll("<br/>", "\r\n");
						emailContent = emailContent.replaceAll("<br>", "\r\n");
						emailContent = emailContent.replaceAll("&nbsp;", " ");
					}
				}
				//获得电话发送的模板
				if(isPhone == YES){
					selectContent.setTypeId(TYPE_PHONE);
					String phoneContentName = param.get("phoneContentName").toString();
					selectContent.setName(phoneContentName);
					NotifyPolicyContentBean phoneBean = policyContentMapper.getByPolicyAndName(selectContent);
					voiceMessageType = phoneBean.getVoiceMessageType();
					if(voiceMessageType == PHONETYPE_TEXT){
						phoneContent = phoneBean.getContent();
					}else if(voiceMessageType == PHONETYPE_VOICE){
						phoneContent = phoneBean.getVoicePath();
					}
				}
				
				Map<String, Object> root = new HashMap<String, Object>();
				if(isSms == YES || isEmail == YES ||(isPhone == YES && voiceMessageType == PHONETYPE_TEXT)){
					List<String> contentParams = this.getContentParams(param);
					for (int i = 0; i < contentParams.size(); i++) {
						root.put(contentParams.get(i), param.get(contentParams.get(i)));
					}
				}
				
				String message = "";
				String recipient = "";
				//获得通知用户
				List<NotifyToUsersBean> notifyToUserList = this.getAllNotifyToUsers(policyId);
				if(notifyToUserList != null){
					//最大发送次数
					int maxTimes = notifyPolicyCfgBean.getMaxTimes();
					for (int i = 0; i < notifyToUserList.size(); i++) {
						NotifyToUsersBean notifyToUser = notifyToUserList.get(i);
						if(isEmail == YES){
							if ("".equals(notifyToUser.getUserName()) || notifyToUser.getUserName() == null) {
								root.put("userName", "用户");
							} else {
								root.put("userName", notifyToUser.getUserName());
							}
						}
						if (isSms == YES) {
							logger.info("创建短信通知任务");
							message = PfOgnlUtil.parseText(smsContent, null, root);
							recipient = notifyToUser.getMobileCode();
							this.sendSms(recipient, message, maxTimes);
						}
						if(isEmail == YES){
							logger.info("创建邮件通知任务");
							message = PfOgnlUtil.parseText(emailContent, null, root);
							recipient = notifyToUser.getEmail();
							String emailTitle = "";
							if(param.get("emailTitle") != null){
								emailTitle = param.get("emailTitle").toString();
							}
							this.sendEmail(recipient, message, maxTimes, emailTitle);
						}
						
						//是否电话语音通知
						if(isPhone == YES){
							logger.info("创建电话语音通知任务");
							if(voiceMessageType == 1){
								message = PfOgnlUtil.parseText(phoneContent, null, root);
							}else if(voiceMessageType == 2){
								message = phoneContent;
							}
							recipient = notifyToUser.getMobileCode();
							this.sendPhone(recipient, message, maxTimes, voiceMessageType);
						}
					}
				}else{
					logger.error("id为：{}的通知策略没有配置通知用户。",policyId);
				}
			}else{
				logger.error("不存在id为：{}的通知策略。",policyId);
			}
		} catch (Exception e) {
			logger.error("通知策略创建通知任务异常:",e);
		}
	}
	
	/**
	 * 创建短信通知任务
	 * 
	 * @param recipient
	 *            短信接收者
	 * @param message
	 *            短信内容
	 * @param maxTimes
	 *            最大发送次数
	 */
	void sendSms(String recipient, String message, int maxTimes) {
		logger.info("短信通知内容为：{},手机号码为：",message,recipient);
		if (recipient != null && !"".equals(recipient) && !"".equals(message)
				&& message != null && !message.contains("$")) {
			logger.info("创建短信通知任务。。。start");
			PfMessage smsTask = new PfMessage();
			smsTask.setPhoneNumber(recipient);
			smsTask.setMessage(message);
			smsTask.setSendedTimes(0);
			smsTask.setMaxTimes(maxTimes);
			smsTask.setStatus(NOTSEND);
			smsTask.setLastUpdateTime(new Date());
			smsTask.setExpectSendTime(new Date());
			smsTask.setSource(3);
			smsTask.setNotifyType(100);
			smsNotificationTaskMapper.insert(smsTask);
			logger.info("创建短信通知任务。。。end");
		}
	}

	/**
	 * 创建邮件通知任务
	 * 
	 * @param recipient
	 *            邮件接收者
	 * @param message
	 *            邮件内容
	 * @param maxTimes
	 *            最大发送次数
	 * @param emailTitle
	 *            邮件标题
	 */
	void sendEmail(String recipient, String message, int maxTimes,
			String emailTitle) {
		logger.info("邮件通知内容为：{},手机号码为：",message,recipient);
		if (recipient != null && !"".equals(recipient) && !"".equals(message)
				&& message != null && !message.contains("$") ) {
			logger.info("创建邮件通知任务。。。start");
			PfEmailNotificationTaskBean emailTask = new PfEmailNotificationTaskBean();
			emailTask.setEmailAddress(recipient);
			emailTask.setMessage(message);
			emailTask.setSendedTimes(0);
			emailTask.setMaxTimes(maxTimes);
			emailTask.setStatus(NOTSEND);
			emailTask.setLastUpdateTime(dateFormat.format(new Date()));
			emailTask.setExpectSendTime(dateFormat.format(new Date()));
			emailTask.setSource(3);
			emailTask.setNotifyType(1);

			emailTask.setTitle(emailTitle);
			emailNotificationTaskMapper.insertNotificationTask(emailTask);
			logger.info("创建邮件通知任务。。。end");
		}
	}

	/**
	 * 创建电话语音通知任务
	 * 
	 * @param recipient
	 *            短信接收者
	 * @param message
	 *            短信内容
	 * @param maxTimes
	 *            最大发送次数
	 * @param voiceMessageType
	 *            语音通知类型
	 */
	void sendPhone(String recipient, String message, int maxTimes,
			int voiceMessageType) {
		logger.info("电话语音通知内容为：{}，手机号码为：{}",message,recipient);
		if (recipient != null && !"".equals(recipient) && !"".equals(message)
				&& message != null && !message.contains("$")) {
			logger.info("创建电话语音通知任务。。。end");
			PhoneNotificationTaskBean phoneNotificationTaskBean = new PhoneNotificationTaskBean();
			phoneNotificationTaskBean.setPhoneNumber(recipient);
			phoneNotificationTaskBean.setMessage(message);
			phoneNotificationTaskBean.setSendedTimes(0);
			phoneNotificationTaskBean.setMaxTimes(maxTimes);
			phoneNotificationTaskBean.setStatus(NOTSEND);
			phoneNotificationTaskBean.setLastUpdateTime(dateFormat
					.format(new Date()));
			phoneNotificationTaskBean.setExpectSendTime(dateFormat
					.format(new Date()));
			phoneNotificationTaskBean.setSendType(0);
			phoneNotificationTaskBean.setVoiceMessageType(voiceMessageType);
			phoneNotificationTaskBean.setName(null);
			// 发送的优先级
			phoneNotificationTaskBean.setNotifyPriority(100);
			phoneNotificationTaskMapper
					.insertNotificationTask(phoneNotificationTaskBean);
			logger.info("创建电话语音通知任务。。。end");
		}
	}
	
	/**
	 * 获得所有的通知用户
	 * @param policyId
	 * @return
	 */
	public List<NotifyToUsersBean> getAllNotifyToUsers(int policyId) {
		List<NotifyToUsersBean> userLst = notifyToUsersMapper.getUsersByPolicyID(policyId);
		List<Integer> userIds = new ArrayList<Integer>();
		for (int i = 0; i < userLst.size(); i++) {
			if(userLst.get(i).getUserId() != null){
				userIds.add(userLst.get(i).getUserId());
			}
		}
		
		List<NotifyToUsersBean> noticeUsers = new ArrayList<NotifyToUsersBean>();
		
		for (int i = 0; i < userLst.size(); i++) {
			if(userLst.get(i).getUserId() != null){
				int userId = userLst.get(i).getUserId();
				//如果是值班负责人
				if(userId == DIRECTOR){
					Date currentTime = new Date();
					//获得当前时间的值班负责人
					String dutierId = dutyService.getPointDuyter(currentTime);
					this.doAddDutier(dutierId, noticeUsers, userIds);
				}
				//值班带班领导或者值班成员
				else if(userId == LEADER || userId == MEMBER){
					List<String> dutierList = dutyService.findCurrentDutyers();
					if(dutierList.size()>0){
						if(userId == LEADER){
							String leaderId = dutierList.get(0);
							this.doAddDutier(leaderId, noticeUsers, userIds);
						}
						if(userId == MEMBER){
							for (int j = 1; j < dutierList.size(); j++) {
								String memberId = dutierList.get(j);
								this.doAddDutier(memberId, noticeUsers, userIds);
							}
						}	
					}
				} else {
					noticeUsers.add(userLst.get(i));
				}
			} else {
				noticeUsers.add(userLst.get(i));
			}
		}
		return noticeUsers;
	}
	
	/**
	 *  值班人员的处理
	 * @param dutierId 值班人员的id
	 * @param userLst 所有通知用户的信息集合
	 * @param userIds 所有通知用户的id
	 */
	void doAddDutier(String dutierId,List<NotifyToUsersBean> userLst,List<Integer> userIds){
		if(!"".equals(dutierId) && dutierId != null && !"null".equals(dutierId) ){
			if(!userIds.contains(Integer.parseInt(dutierId))){
				
				List<SysUserInfoBean> list = sysUserDao.getSysUserByConditions("userID", dutierId);
				SysUserInfoBean sysUserBeanTemp = list.get(0);
				
				NotifyToUsersBean user = new NotifyToUsersBean();
				user.setUserName(sysUserBeanTemp.getUserName());
				user.setEmail(sysUserBeanTemp.getEmail());
				user.setMobileCode(sysUserBeanTemp.getMobilePhone());
				
				userLst.add(user);
			}
		}
	}
	
	//获得所有的模板参数
	@SuppressWarnings("unchecked")
	List<String> getContentParams(Map<String, Object> param){
		List<String> contentParams = new ArrayList<String>();
		for(Iterator iterator = param.keySet().iterator();iterator.hasNext();){
			String key = (String) iterator.next();
			if (!"policyId".equals(key) && !"smsContentName".equals(key)
					&& !"emailContentName".equals(key)
					&& !"phoneContentName".equals(key)
					&& !"emailTitle".equals(key)) {
				contentParams.add(key);
			}
		}
		return contentParams;
	}

	@Override
	public String findNameByNotifyType(Integer notifyType) {
		return policyContentMapper.selectNameByNotifyType(notifyType);
	}

	@Override
	public Integer findPolicyIdForDuty() {
		return notifyPolicyCfgMapper.selectPolicyIdForDuty();
	}

	@Override
	public void notify(Map<String, Object> param, Integer orderId) {
		try {
			int policyId = Integer.parseInt(param.get("policyId").toString());
			//根据id获得通知策略
			NotifyPolicyCfgBean notifyPolicyCfgBean = notifyPolicyCfgMapper.getNotifyCfgByID(policyId);
			if(notifyPolicyCfgBean != null){
				NotifyPolicyContentBean selectContent = new NotifyPolicyContentBean();
				selectContent.setPolicyId(policyId);
				int isSms = notifyPolicyCfgBean.getIsSms();
				int isEmail = notifyPolicyCfgBean.getIsEmail();
				int isPhone = notifyPolicyCfgBean.getIsPhone();
				//语音通知类型：1 文字模板 2录音
				int voiceMessageType = PHONETYPE_TEXT;
				
				//需要发送短信,获得短信发送的模板
				String smsContent = "";
				String emailContent = "";
				String phoneContent = "";
				if(isSms == YES){
					selectContent.setTypeId(TYPE_SMS);
					String smsContentName = param.get("smsContentName").toString();
					selectContent.setName(smsContentName);
					NotifyPolicyContentBean smsBean = policyContentMapper.getByPolicyAndName(selectContent);
					smsContent = smsBean.getContent();
				}
				// 获得邮件发送的模板
				if (isEmail == YES) {
					selectContent.setTypeId(TYPE_EMAIL);
					String emailContentName = param.get("emailContentName")
							.toString();
					selectContent.setName(emailContentName);
					NotifyPolicyContentBean emailBean = policyContentMapper
							.getByPolicyAndName(selectContent);
					emailContent = emailBean.getContent();
					if (!"".equals(emailContent) && emailContent != null) {
						emailContent = emailContent.replaceAll("<br/>", "\r\n");
						emailContent = emailContent.replaceAll("<br>", "\r\n");
						emailContent = emailContent.replaceAll("&nbsp;", " ");
					}
				}
				//获得电话发送的模板
				if(isPhone == YES){
					selectContent.setTypeId(TYPE_PHONE);
					String phoneContentName = param.get("phoneContentName").toString();
					selectContent.setName(phoneContentName);
					NotifyPolicyContentBean phoneBean = policyContentMapper.getByPolicyAndName(selectContent);
					voiceMessageType = phoneBean.getVoiceMessageType();
					if(voiceMessageType == PHONETYPE_TEXT){
						phoneContent = phoneBean.getContent();
					}else if(voiceMessageType == PHONETYPE_VOICE){
						phoneContent = phoneBean.getVoicePath();
					}
				}
				
				Map<String, Object> root = new HashMap<String, Object>();
				if(isSms == YES || isEmail == YES ||(isPhone == YES && voiceMessageType == PHONETYPE_TEXT)){
					List<String> contentParams = this.getContentParams(param);
					for (int i = 0; i < contentParams.size(); i++) {
						root.put(contentParams.get(i), param.get(contentParams.get(i)));
					}
				}
				
				String message = "";
				String recipient = "";
				//获得通知用户
				List<NotifyToUsersBean> notifyToUserList = this.getAllNotifyToUsers(policyId, orderId);
				if(notifyToUserList != null){
					//最大发送次数
					int maxTimes = notifyPolicyCfgBean.getMaxTimes();
					for (int i = 0; i < notifyToUserList.size(); i++) {
						NotifyToUsersBean notifyToUser = notifyToUserList.get(i);
						if(isEmail == YES){
							if ("".equals(notifyToUser.getUserName()) || notifyToUser.getUserName() == null) {
								root.put("userName", "用户");
							} else {
								root.put("userName", notifyToUser.getUserName());
							}
						}
						if (isSms == YES) {
							logger.info("创建短信通知任务");
							message = PfOgnlUtil.parseTextLose(smsContent, null, root);
							recipient = notifyToUser.getMobileCode();
							this.sendSms(recipient, message, maxTimes);
						}
						if(isEmail == YES){
							logger.info("创建邮件通知任务");
							message = PfOgnlUtil.parseText(emailContent, null, root);
							recipient = notifyToUser.getEmail();
							String emailTitle = "";
							if(null != param.get("emailTitle")) {
								emailTitle = param.get("emailTitle").toString();
							}
							this.sendEmail(recipient, message, maxTimes, emailTitle);
						}
						
						//是否电话语音通知
						if(isPhone == YES){
							logger.info("创建电话语音通知任务");
							if(voiceMessageType == 1){
								message = PfOgnlUtil.parseText(phoneContent, null, root);
							}else if(voiceMessageType == 2){
								message = phoneContent;
							}
							recipient = notifyToUser.getMobileCode();
							this.sendPhone(recipient, message, maxTimes, voiceMessageType);
						}
					}
				}else{
					logger.error("id为：{}的通知策略没有配置通知用户。",policyId);
				}
			}else{
				logger.error("不存在id为：{}的通知策略。",policyId);
			}
		} catch (Exception e) {
			logger.error("通知策略创建通知任务异常:",e);
		}
			
	}
	
	/**
	 * 获得某个值班班次的用户(值班)
	 * @param policyId
	 * @return
	 */
	private List<NotifyToUsersBean> getAllNotifyToUsers(int policyId, Integer orderId) {
		List<NotifyToUsersBean> userLst = notifyToUsersMapper.getUsersByPolicyID(policyId);
		List<Integer> userIds = new ArrayList<Integer>();
		for (int i = 0; i < userLst.size(); i++) {
			if(userLst.get(i).getUserId() != null){
				userIds.add(userLst.get(i).getUserId());
			}
		}
		
		List<NotifyToUsersBean> noticeUsers = new ArrayList<NotifyToUsersBean>();
		
		for (int i = 0; i < userLst.size(); i++) {
			if(userLst.get(i).getUserId() != null){
				int userId = userLst.get(i).getUserId();
				//如果是值班负责人
				if(userId == DIRECTOR){
					String dutierId = String.valueOf(dutyService.findUsersOfOrderByUserType(orderId, 1).get(0).get("UserId"));
					this.doAddDutier(dutierId, noticeUsers, userIds);
				}
				//值班带班领导或者值班成员
				else if(userId == LEADER || userId == MEMBER){
					if(userId == LEADER){
						String leaderId = String.valueOf(dutyService.findUsersOfOrderByUserType(orderId, 1).get(0).get("LeaderId"));
						this.doAddDutier(leaderId, noticeUsers, userIds);
					}
					if(userId == MEMBER){
						List<Map<String, Object>> list = dutyService.findUsersOfOrderByUserType(orderId, 2);
						for(Map<String, Object> map : list) {
							String memberId = map.get("UserId").toString();
							this.doAddDutier(memberId, noticeUsers, userIds);
						}
					}	
				} else {
					noticeUsers.add(userLst.get(i));
				}
			} else {
				noticeUsers.add(userLst.get(i));
			}
		}
		return noticeUsers;
	}
}
