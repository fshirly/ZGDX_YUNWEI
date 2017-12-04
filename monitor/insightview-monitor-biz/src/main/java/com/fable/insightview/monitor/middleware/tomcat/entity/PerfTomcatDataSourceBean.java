package com.fable.insightview.monitor.middleware.tomcat.entity;

import java.util.Date;

public class PerfTomcatDataSourceBean {
	private Integer id;
	private Integer moId;
	private Date collectTime;//采集时间
	private long initSize;//初始大小
	private long maxActive;//最大连接数
	private long minIdle;//最小空闲连接数
	private long maxIdle;//最大空闲连接数
	private long maxWait;//最长等待连接时间
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
	public long getInitSize() {
		return initSize;
	}
	public void setInitSize(long initSize) {
		this.initSize = initSize;
	}
	public long getMaxActive() {
		return maxActive;
	}
	public void setMaxActive(long maxActive) {
		this.maxActive = maxActive;
	}
	public long getMinIdle() {
		return minIdle;
	}
	public void setMinIdle(long minIdle) {
		this.minIdle = minIdle;
	}
	public long getMaxIdle() {
		return maxIdle;
	}
	public void setMaxIdle(long maxIdle) {
		this.maxIdle = maxIdle;
	}
	public long getMaxWait() {
		return maxWait;
	}
	public void setMaxWait(long maxWait) {
		this.maxWait = maxWait;
	}
	public String getFormatTime() {
		return formatTime;
	}
	public void setFormatTime(String formatTime) {
		this.formatTime = formatTime;
	}
	
}
