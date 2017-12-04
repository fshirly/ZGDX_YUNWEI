package com.fable.insightview.monitor.middleware.tomcat.entity;

import java.util.Date;

public class PerfTomcatThreadPoolBean {
	private Integer id;
	private Integer moId;
	private Date collectTime;//采集时间
	private long maxThreads;//最大线程数
	private long currThreads;//当前线程数
	private long busyThreads;//繁忙线程数
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
	public long getMaxThreads() {
		return maxThreads;
	}
	public void setMaxThreads(long maxThreads) {
		this.maxThreads = maxThreads;
	}
	public long getCurrThreads() {
		return currThreads;
	}
	public void setCurrThreads(long currThreads) {
		this.currThreads = currThreads;
	}
	public long getBusyThreads() {
		return busyThreads;
	}
	public void setBusyThreads(long busyThreads) {
		this.busyThreads = busyThreads;
	}
	public String getFormatTime() {
		return formatTime;
	}
	public void setFormatTime(String formatTime) {
		this.formatTime = formatTime;
	}
	
}
