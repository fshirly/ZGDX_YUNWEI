package com.fable.insightview.platform.smstools.service;

import com.fable.insightview.platform.smstools.entity.HostInfo;


/**
 * 短信发送
 * 
 */
public interface SmsSender {
	boolean send(String recipient, String message);
	
	boolean send(String message,HostInfo hostInfo);
	
	boolean send(String urlStr, int returnType);
}
