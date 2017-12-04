package com.fable.insightview.monitor.messagesender.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fable.insightview.monitor.messagesender.service.MessageSender;
import com.fable.insightview.platform.smstools.service.SmsSender;

/**
 * 发送短信
 * 
 */
@Component("smsSenderBySmsCat")
public class SmsSenderBySmsCat implements MessageSender {
	private static final Logger logger = org.slf4j.LoggerFactory
			.getLogger(SmsSenderBySmsCat.class);
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	SmsSender  sendSmsBySmsCat;
	@Override
	public boolean send(String recipient, String message) {
//		SmsSender  sendSmsBySmsCat = new SendSmsBySmsCat();
		return sendSmsBySmsCat.send(recipient, message);
	}
	@Override
	public boolean send(String recipient, String message,
			Map<String, String> configMap) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean send(String recipient, String message, String title) {
		// TODO Auto-generated method stub
		return false;
	}


}
