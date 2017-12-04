package com.fable.insightview.platform.core.push;

import java.util.List;

/**
 * web推送消息模型定义
 * 
 * @author 郑自辉
 *
 */
public class PushMessage {

	/**
	 * 通知的用户ID
	 */
	private List<Integer> notifyUserIds;
	
	/**
	 * 通知的用户账号
	 */
	private List<String> notifyUserAccounts;
	
	/**
	 * 通知的消息
	 */
	private Object message;

	public List<Integer> getNotifyUserIds() {
		return notifyUserIds;
	}

	public void setNotifyUserIds(List<Integer> notifyUserIds) {
		this.notifyUserIds = notifyUserIds;
	}

	public List<String> getNotifyUserAccounts() {
		return notifyUserAccounts;
	}

	public void setNotifyUserAccounts(List<String> notifyUserAccounts) {
		this.notifyUserAccounts = notifyUserAccounts;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}
}
