package com.fable.insightview.monitor.notificationtasktracker.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fable.insightview.monitor.messagesender.service.MessageSender;
import com.fable.insightview.monitor.messagesender.service.impl.PhoneVoiceSend;
import com.fable.insightview.monitor.notificationtasktracker.entity.EmailNotificationTaskBean;
import com.fable.insightview.monitor.notificationtasktracker.entity.SmsNotificationTaskBean;
import com.fable.insightview.monitor.notificationtasktracker.mapper.EmailNotificationTaskMapper;
import com.fable.insightview.monitor.notificationtasktracker.mapper.SmsNotificationTaskMapper;
import com.fable.insightview.platform.message.entity.PhoneNotificationTaskBean;
import com.fable.insightview.platform.message.mapper.PhoneNotificationTaskMapper;
import com.fable.insightview.platform.smstools.entity.SysSmsConfigBean;
import com.fable.insightview.platform.smstools.mapper.SysSmsConfigMapper;

/**
 * 轮询通知任务
 * 
 */
public class NotificationTaskTracker {
	private static final Logger logger = LoggerFactory
			.getLogger(NotificationTaskTracker.class);
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	SysSmsConfigMapper smsConfigMapper;
	@Autowired
	private SmsNotificationTaskMapper smsNotificationTaskMapper;
	@Autowired
	private EmailNotificationTaskMapper emailNotificationTaskMapper;
	@Resource(name = "emailSender")
	private MessageSender emailSender;
	@Resource(name = "smsSenderBySmsCat")
	private MessageSender smsSenderBySmsCat;
	@Resource(name = "smsSenderByGateway")
	private MessageSender smsSenderByGateway;
	@Resource(name = "smsSenderByUrl")
	private MessageSender smsSenderByUrl;
	@Resource(name = "smsSenderByHttpClient")
	private MessageSender smsSenderByHttpClient;
	@Resource(name = "smsSenderByAxis")
	private MessageSender smsSenderByAxis;
	@Resource(name = "phoneVoiceSend")
	private PhoneVoiceSend phoneVoiceSend;
	@Resource(name = "smsSenderHttpUrl")
	private MessageSender smsSenderHttpUrl;
	@Autowired
	PhoneNotificationTaskMapper phoneNotificationTaskMapper;
	
	private Map<String, String> configMap;
	
	public void notificationTrack() {
		
		ScheduledThreadPoolExecutor stpe = new ScheduledThreadPoolExecutor(1);
		stpe.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				try {
					logger.debug("轮询短信通知任务。。。。。start");
					List<SmsNotificationTaskBean> smsTaskList = smsNotificationTaskMapper
							.getNotificationTask();
					logger.debug("轮询短信通知任务11111111111111111111111111111111");
					//是否有短信配置
					boolean isHaveConfig = true;
					for (int i = 0; i < smsTaskList.size(); i++) {
						logger.debug("轮询短信通知任务222");
						// 获得短信通知配置
						getSmsConfigMap();
						logger.debug("轮询短信通知任务333");
						if (configMap.size() > 0) {
							SmsNotificationTaskBean smsNotificationTask = smsTaskList
									.get(i);
							int sendedTimes = smsNotificationTask
									.getSendedTimes();
							String phoneNumber = smsNotificationTask
									.getPhoneNumber();
							String message = smsNotificationTask.getMessage();

							// 发送结果
							boolean smsSenderFlag = false;
							logger.debug("轮询短信通知任务444");
							smsSenderFlag = sendSms(phoneNumber, message);

							logger.info("短信发送的结果：" + smsSenderFlag);
							if (smsSenderFlag) {
								smsNotificationTask.setStatus(2);
								smsNotificationTask
										.setLastUpdateTime(dateFormat
												.format(new Date()));
								smsNotificationTask
										.setSendedTimes(sendedTimes + 1);
								smsNotificationTaskMapper
										.updateNotificationTask(smsNotificationTask);
							} else {
								smsNotificationTask.setStatus(3);
								smsNotificationTask
										.setLastUpdateTime(dateFormat
												.format(new Date()));
								smsNotificationTask
										.setSendedTimes(sendedTimes + 1);
								smsNotificationTaskMapper
										.updateNotificationTask(smsNotificationTask);
							}
						} else {
							isHaveConfig = false;
						}
					}
					
					if(!isHaveConfig){
						logger.error("没有短信配置信息，请检查数据库表SysSmsConfig信息！");
					}
					
					logger.debug("轮询邮件通知任务。。。。。start");
					List<EmailNotificationTaskBean> emailTaskList = emailNotificationTaskMapper
							.getNotificationTask();
					for (int i = 0; i < emailTaskList.size(); i++) {
						EmailNotificationTaskBean emailNotificationTask = emailTaskList
								.get(i);
						int sendedTimes = emailNotificationTask
								.getSendedTimes();
//						int maxTimes = emailNotificationTask.getMaxTimes();
//						int status = emailNotificationTask.getStatus();
						String emailAddress = emailNotificationTask
								.getEmailAddress();
						String message = emailNotificationTask.getMessage();
						String title = emailNotificationTask.getTitle();
						boolean emailSenderFlag = false;
						
						logger.debug("发送邮件。。。。。start");
						try {
							emailSenderFlag = emailSender.send(emailAddress,
									message,title);
						} catch (Exception e) {
							logger.error("邮件发送失败：" + e);
						}
						logger.debug("邮件发送结果：" + emailSenderFlag);
						
						logger.debug("邮件发送后，修改邮件任务表。。。。start");
						if (emailSenderFlag) {
							emailNotificationTask.setStatus(2);
							emailNotificationTask.setLastUpdateTime(dateFormat
									.format(new Date()));
							emailNotificationTask
									.setSendedTimes(sendedTimes + 1);
							emailNotificationTaskMapper
									.updateNotificationTask(emailNotificationTask);
						} else {
							emailNotificationTask.setStatus(3);
							emailNotificationTask.setLastUpdateTime(dateFormat
									.format(new Date()));
							emailNotificationTask
									.setSendedTimes(sendedTimes + 1);
							emailNotificationTaskMapper
									.updateNotificationTask(emailNotificationTask);
						}
						logger.debug("邮件发送后，修改邮件任务表。。。。end");
					}
					
					logger.info("轮询语音通知任务。。。");
					List<PhoneNotificationTaskBean> phoneNotifyTaskList = phoneNotificationTaskMapper.getNotificationTask();
					for (int i = 0; i < phoneNotifyTaskList.size(); i++) {
						PhoneNotificationTaskBean phoneNotificationTaskBean = phoneNotifyTaskList.get(i);
						int sendedTimes = phoneNotificationTaskBean.getSendedTimes();
						boolean phoneSenderFlag = phoneVoiceSend.send(phoneNotificationTaskBean);
						logger.info("电话语音通知结果："+phoneSenderFlag);
						if(phoneSenderFlag){
							phoneNotificationTaskBean.setStatus(2);
							phoneNotificationTaskBean.setSendedTimes(sendedTimes + 1);
							phoneNotificationTaskBean.setLastUpdateTime(dateFormat.format(new Date()));
							phoneNotificationTaskMapper.updateNotificationTask(phoneNotificationTaskBean);
						}
					}
				} catch (Exception e) {
					logger.error("轮询通知任务失败!", e);
				}
			}
		}, 300, 60, TimeUnit.SECONDS);
	}
	
	/**
	 * 将短信配置信息置于map中
	 */
	public Map<String, String> getSmsConfigMap(){
		configMap = new HashMap<String, String>();
		//获得短信通知配置
		List<SysSmsConfigBean> smsConfigLst = smsConfigMapper.getConfigInfo();
		for (int i = 0; i < smsConfigLst.size(); i++) {
			String key = smsConfigLst.get(i).getParamKey();
			String value = smsConfigLst.get(i).getParamValue();
			configMap.put(key, value);
		}
		return configMap;
	}

	/**
	 * 发送短信
	 * @param phoneNumber
	 * @param message
	 * @param configMap
	 * @return
	 */
	public boolean sendSms(String phoneNumber,String message){
		//发送结果
		boolean smsSenderFlag = false;
		
		try {
			int sendType = Integer.parseInt(configMap.get("SendType"));
			//调用第三方接口发送短信
			if(sendType == 1){
				smsSenderFlag =	smsSenderByUrl.send(phoneNumber,message,configMap);
			}
			//短信网关方式发送短信
			else if(sendType == 2){
				smsSenderFlag =	smsSenderByGateway.send(phoneNumber, message,configMap);
			}
			//短信猫方式发送短信
			else if(sendType == 3){
				smsSenderFlag =	smsSenderBySmsCat.send(phoneNumber, message);
			}
			//泰州调用webservice接口发送短信
			else if(sendType == 4){
				smsSenderFlag =	smsSenderByAxis.send(phoneNumber, message,configMap);
			}
			//扬州调用webservice接口发送短信
			else if(sendType == 5){
				smsSenderFlag =	smsSenderByHttpClient.send(phoneNumber, message,configMap);
			}
			//四川调用webservice接口发送短信
			else if(sendType == 6){
				logger.debug("..开始发送短信..");
				smsSenderFlag = smsSenderHttpUrl.send(phoneNumber, message, configMap);
			}
			
			else{
				logger.info("配置的sendType为："+sendType);
				logger.error("短息配置信息有误！");
				return false;
			}
		} catch (NumberFormatException e) {
			logger.error("短息配置SendType信息有误！");
			return false;
		}
		return smsSenderFlag;
	}
	
	
	public MessageSender getEmailSender() {
		return emailSender;
	}

	public void setEmailSender(MessageSender emailSender) {
		this.emailSender = emailSender;
	}


	public MessageSender getSmsSenderBySmsCat() {
		return smsSenderBySmsCat;
	}

	public void setSmsSenderBySmsCat(MessageSender smsSenderBySmsCat) {
		this.smsSenderBySmsCat = smsSenderBySmsCat;
	}


	public MessageSender getSmsSenderByGateway() {
		return smsSenderByGateway;
	}

	public void setSmsSenderByGateway(MessageSender smsSenderByGateway) {
		this.smsSenderByGateway = smsSenderByGateway;
	}

	public MessageSender getSmsSenderByUrl() {
		return smsSenderByUrl;
	}

	public void setSmsSenderByUrl(MessageSender smsSenderByUrl) {
		this.smsSenderByUrl = smsSenderByUrl;
	}

	public Map<String, String> getConfigMap() {
		return configMap;
	}

	public void setConfigMap(Map<String, String> configMap) {
		this.configMap = configMap;
	}

	public MessageSender getSmsSenderByHttpClient() {
		return smsSenderByHttpClient;
	}

	public void setSmsSenderByHttpClient(MessageSender smsSenderByHttpClient) {
		this.smsSenderByHttpClient = smsSenderByHttpClient;
	}

	public MessageSender getSmsSenderByAxis() {
		return smsSenderByAxis;
	}

	public void setSmsSenderByAxis(MessageSender smsSenderByAxis) {
		this.smsSenderByAxis = smsSenderByAxis;
	}

	public PhoneVoiceSend getPhoneVoiceSend() {
		return phoneVoiceSend;
	}

	public void setPhoneVoiceSend(PhoneVoiceSend phoneVoiceSend) {
		this.phoneVoiceSend = phoneVoiceSend;
	}

	public MessageSender getSmsSenderHttpUrl() {
		return smsSenderHttpUrl;
	}

	public void setSmsSenderHttpUrl(MessageSender smsSenderHttpUrl) {
		this.smsSenderHttpUrl = smsSenderHttpUrl;
	}
	
}
