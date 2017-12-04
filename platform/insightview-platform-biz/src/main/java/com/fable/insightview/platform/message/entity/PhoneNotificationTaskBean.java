package com.fable.insightview.platform.message.entity;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * 电话语音通知任务表
 * 
 */
public class PhoneNotificationTaskBean {
	@NumberGenerator(name = "phoneNotififyTaskPK")
	private Integer id;
	private String phoneNumber;
	private String message;
	private Integer sendedTimes;
	private Integer maxTimes;
	private Integer status;
	private String lastUpdateTime;
	private String expectSendTime;
	private Integer sendType;
	private Integer voiceMessageType;
	private String name;
	private Integer notifyPriority;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	public Integer getSendType() {
		return sendType;
	}

	public void setSendType(Integer sendType) {
		this.sendType = sendType;
	}

	public Integer getVoiceMessageType() {
		return voiceMessageType;
	}

	public void setVoiceMessageType(Integer voiceMessageType) {
		this.voiceMessageType = voiceMessageType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNotifyPriority() {
		return notifyPriority;
	}

	public void setNotifyPriority(Integer notifyPriority) {
		this.notifyPriority = notifyPriority;
	}

}
