package com.fable.insightview.monitor.middleware.websphere.entity;

import java.util.Date;

public class PerfWASJDBCPoolBean {
	private Integer id;
	private Integer moId;
	private Date collectTime;
	private double poolUsage;//连接池使用率
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
	public double getPoolUsage() {
		return poolUsage;
	}
	public void setPoolUsage(double poolUsage) {
		this.poolUsage = poolUsage;
	}
	public String getFormatTime() {
		return formatTime;
	}
	public void setFormatTime(String formatTime) {
		this.formatTime = formatTime;
	}
	
}
