package com.fable.insightview.platform.message.entity;

import java.util.Date;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * 平台短信实体类
 */
public class PfMessage {

	@NumberGenerator(name = "monitorSmsNotififyTaskPK", allocationSize=1)
	private int id;
	private String phoneNumber;
	private String message;
	private int sendedTimes = 0;
	private int maxTimes = 3;
	private int status = 1;/* 1：未发送 2：发送成功 3：发送失败 */
	private Date lastUpdateTime = new Date();
	private Date expectSendTime = new Date();
	private int source = 4;/* 1: itsm 2: cmdb 3:monitor 4:platform */
	private int notifyType = 300;/* 优先级 - 值越大优先级越高 */

	public PfMessage() {
		super();
	}

	public PfMessage(String phoneNumber, String message) {
		super();
		this.phoneNumber = phoneNumber;
		this.message = message;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public int getSendedTimes() {
		return sendedTimes;
	}

	public void setSendedTimes(int sendedTimes) {
		this.sendedTimes = sendedTimes;
	}

	public int getMaxTimes() {
		return maxTimes;
	}

	public void setMaxTimes(int maxTimes) {
		this.maxTimes = maxTimes;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Date getExpectSendTime() {
		return expectSendTime;
	}

	public void setExpectSendTime(Date expectSendTime) {
		this.expectSendTime = expectSendTime;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public int getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(int notifyType) {
		this.notifyType = notifyType;
	}

}
