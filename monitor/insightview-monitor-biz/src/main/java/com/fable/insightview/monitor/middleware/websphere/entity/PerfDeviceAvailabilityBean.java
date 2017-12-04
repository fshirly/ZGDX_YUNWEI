package com.fable.insightview.monitor.middleware.websphere.entity;

import java.util.Date;

public class PerfDeviceAvailabilityBean {
	private Integer id;
	private Integer moId;
	private Date collectTime;
	private long packetLoss;//丢包
	private long responseTime;//响应时间
	private long deviceStatus;//状态
	private String formatTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMoId() {
		return moId;
	}
	public void setMoId(Integer moId) {
		this.moId = moId;
	}
	public Date getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}
	public long getPacketLoss() {
		return packetLoss;
	}
	public void setPacketLoss(long packetLoss) {
		this.packetLoss = packetLoss;
	}
	public long getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(long responseTime) {
		this.responseTime = responseTime;
	}
	public long getDeviceStatus() {
		return deviceStatus;
	}
	public void setDeviceStatus(long deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
	public String getFormatTime() {
		return formatTime;
	}
	public void setFormatTime(String formatTime) {
		this.formatTime = formatTime;
	}
	
}
