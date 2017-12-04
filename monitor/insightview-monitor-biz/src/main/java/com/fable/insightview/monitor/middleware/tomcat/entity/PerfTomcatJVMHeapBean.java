package com.fable.insightview.monitor.middleware.tomcat.entity;

import java.util.Date;

public class PerfTomcatJVMHeapBean {
	private Integer id;
	private Integer moId;
	private Date collectTime;
	private long heapSize;//总内存
	private long heapFree;//空闲内存
	private long heapMax;//内存最大值
	private double heapUsage;//内存使用率
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

	public long getHeapSize() {
		return heapSize;
	}

	public void setHeapSize(long heapSize) {
		this.heapSize = heapSize;
	}

	public long getHeapFree() {
		return heapFree;
	}

	public void setHeapFree(long heapFree) {
		this.heapFree = heapFree;
	}

	public long getHeapMax() {
		return heapMax;
	}

	public void setHeapMax(long heapMax) {
		this.heapMax = heapMax;
	}

	public double getHeapUsage() {
		return heapUsage;
	}

	public void setHeapUsage(double heapUsage) {
		this.heapUsage = heapUsage;
	}

	public String getFormatTime() {
		return formatTime;
	}

	public void setFormatTime(String formatTime) {
		this.formatTime = formatTime;
	}

}
