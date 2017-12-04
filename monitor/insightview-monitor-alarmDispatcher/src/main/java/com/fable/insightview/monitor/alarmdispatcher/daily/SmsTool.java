package com.fable.insightview.monitor.alarmdispatcher.daily;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fable.insightview.monitor.notificationtasktracker.entity.SmsNotificationTaskBean;
import com.fable.insightview.monitor.notificationtasktracker.mapper.SmsNotificationTaskMapper;
import com.fable.insightview.platform.core.util.BeanLoader;

public class SmsTool {

	SmsNotificationTaskMapper smsNotificationService = (SmsNotificationTaskMapper) BeanLoader
			.getBean("smsNotificationTaskMapper");

	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	SmsNotificationTaskBean smsTask = new SmsNotificationTaskBean();

	public void sendSms(int notifyType, String phoneNumber, String msg) { 
			smsTask.setPhoneNumber(phoneNumber);
			smsTask.setMessage(msg);
			smsTask.setSendedTimes(0);
			smsTask.setSendedTimes(0);
			smsTask.setMaxTimes(3);
			smsTask.setStatus(1);
			smsTask.setLastUpdateTime(dateFormat.format(new Date()));
			smsTask.setExpectSendTime(dateFormat.format(new Date()));
			smsTask.setSource(3);
			smsTask.setNotifyType(200);
			smsNotificationService.insertNotificationTask(smsTask);
	}
}