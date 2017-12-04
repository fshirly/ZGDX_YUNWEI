package com.fable.insightview.monitor.host.entity;

import java.util.Date;

public class PerfNetworkMemory {
	private Integer id;
	private Integer moId;
	private Date collectTime;
	private double memoryUsage;//平均内存使用率
	private double perMemoryUsage;
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
	public double getMemoryUsage() {
		return memoryUsage;
	}
	public void setMemoryUsage(double memoryUsage) {
		this.memoryUsage = memoryUsage;
	}
	public String getFormatTime() {
		return formatTime;
	}
	public void setFormatTime(String formatTime) {
		this.formatTime = formatTime;
	}
	public double getPerMemoryUsage() {
		return perMemoryUsage;
	}
	public void setPerMemoryUsage(double perMemoryUsage) {
		this.perMemoryUsage = perMemoryUsage;
	}
	public String getMoName() {
		return moName;
	}
	public void setMoName(String moName) {
		this.moName = moName;
	}
	
	
}
