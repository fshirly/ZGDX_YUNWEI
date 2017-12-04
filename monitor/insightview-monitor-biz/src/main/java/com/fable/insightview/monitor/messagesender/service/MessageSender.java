package com.fable.insightview.monitor.messagesender.service;

import java.util.Map;

public interface MessageSender {
	boolean send(String recipient, String message);
	boolean send(String recipient, String message, String title);
	boolean send(String recipient, String message,Map<String, String> configMap);
}
