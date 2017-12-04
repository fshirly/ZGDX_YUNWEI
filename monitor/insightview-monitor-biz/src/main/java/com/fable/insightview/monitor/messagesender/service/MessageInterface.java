package com.fable.insightview.monitor.messagesender.service;

public interface MessageInterface {

//	public String sendMessage(String systemId, String extensionNo, String toUserMobile, String content, String fromUserName);
//	
//	public String report(String systemId, String extensionNo, long smId);
//	
//	public String reply(String systemId, String extensionNo, String startTime, String endTime);
	
	public String sendMessage(String In0);
	
	public String report(String In0);
	
	public String reply(String In0);
}
