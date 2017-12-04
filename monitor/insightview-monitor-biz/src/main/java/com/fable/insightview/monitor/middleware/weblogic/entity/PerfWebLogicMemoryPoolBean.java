package com.fable.insightview.monitor.middleware.weblogic.entity;

import java.util.Date;

public class PerfWebLogicMemoryPoolBean {
	private Integer id;
	private Integer moId;
	private Date collectTime;
	private long memoryCommitted;// 总内存大小
	private long memoryFree;// 空闲内存大小
	private double memoryUsage;// 内存池使用率
	private long memoryUsed;// 使用内存大小
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
	public long getMemoryCommitted() {
		return memoryCommitted;
	}
	public void setMemoryCommitted(long memoryCommitted) {
		this.memoryCommitted = memoryCommitted;
	}
	public long getMemoryFree() {
		return memoryFree;
	}
	public void setMemoryFree(long memoryFree) {
		this.memoryFree = memoryFree;
	}
	public double getMemoryUsage() {
		return memoryUsage;
	}
	public void setMemoryUsage(double memoryUsage) {
		this.memoryUsage = memoryUsage;
	}
	public long getMemoryUsed() {
		return memoryUsed;
	}
	public void setMemoryUsed(long memoryUsed) {
		this.memoryUsed = memoryUsed;
	}
	public String getFormatTime() {
		return formatTime;
	}
	public void setFormatTime(String formatTime) {
		this.formatTime = formatTime;
	}
	
	

}
