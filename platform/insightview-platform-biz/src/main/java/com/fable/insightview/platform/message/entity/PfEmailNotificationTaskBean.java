package com.fable.insightview.platform.message.entity;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * 邮件通知任务
 * 
 */
public class PfEmailNotificationTaskBean {
	@NumberGenerator(name = "monitorEmailNotififyTaskPK")
	private Integer id;
	private String emailAddress;
	private String message;
	private Integer sendedTimes;
	private Integer maxTimes;
	private Integer status;
	private String lastUpdateTime;
	private String expectSendTime;
	private Integer source;
	private Integer notifyType;
	private String title;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getSendedTimes() {
		return sendedTimes;
	}

	public void setSendedTimes(Integer sendedTimes) {
		this.sendedTimes = sendedTimes;
	}

	public Integer getMaxTimes() {
		return maxTimes;
	}

	public void setMaxTimes(Integer maxTimes) {
		this.maxTimes = maxTimes;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getExpectSendTime() {
		return expectSendTime;
	}

	public void setExpectSendTime(String expectSendTime) {
		this.expectSendTime = expectSendTime;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Integer getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(Integer notifyType) {
		this.notifyType = notifyType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
