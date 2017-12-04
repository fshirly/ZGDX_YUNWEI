package com.fable.insightview.monitor.middleware.weblogic.entity;

import java.util.Date;

public class PerfWebLogicThreadPoolBean {
	private Integer id;
	private Integer moId;
	private Date collectTime;
	private long executeThreadTotal;//线程总数
	private long executeThreadIdle;//空闲线程数
	private long pendingUserRequest;//暂挂用户请求数
	private double threadPoolUsage;//线程池使用率
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
	public long getExecuteThreadTotal() {
		return executeThreadTotal;
	}
	public void setExecuteThreadTotal(long executeThreadTotal) {
		this.executeThreadTotal = executeThreadTotal;
	}
	public long getExecuteThreadIdle() {
		return executeThreadIdle;
	}
	public void setExecuteThreadIdle(long executeThreadIdle) {
		this.executeThreadIdle = executeThreadIdle;
	}
	public long getPendingUserRequest() {
		return pendingUserRequest;
	}
	public void setPendingUserRequest(long pendingUserRequest) {
		this.pendingUserRequest = pendingUserRequest;
	}
	public double getThreadPoolUsage() {
		return threadPoolUsage;
	}
	public void setThreadPoolUsage(double threadPoolUsage) {
		this.threadPoolUsage = threadPoolUsage;
	}
	public String getFormatTime() {
		return formatTime;
	}
	public void setFormatTime(String formatTime) {
		this.formatTime = formatTime;
	}
	
	
}
