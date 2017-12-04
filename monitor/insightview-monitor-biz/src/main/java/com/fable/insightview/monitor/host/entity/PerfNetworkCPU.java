package com.fable.insightview.monitor.host.entity;

import java.util.Date;

public class PerfNetworkCPU {
	private Integer id;
	private Integer moId;
	private Date collectTime;
	private double cpUsage;//平均使用率
	private double perUsage;//单条使用率
	private String formatTime;
	private String moName;
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
	public double getCpUsage() {
		return cpUsage;
	}
	public void setCpUsage(double cpUsage) {
		this.cpUsage = cpUsage;
	}
	public String getFormatTime() {
		return formatTime;
	}
	public void setFormatTime(String formatTime) {
		this.formatTime = formatTime;
	}
	public double getPerUsage() {
		return perUsage;
	}
	public void setPerUsage(double perUsage) {
		this.perUsage = perUsage;
	}
	public String getMoName() {
		return moName;
	}
	public void setMoName(String moName) {
		this.moName = moName;
	}
	
	
}
