package com.fable.insightview.monitor.database.entity;

import java.util.Date;

public class PerfMySQLQueryCacheBean {
    private Integer id;

    private Integer moId;

    private Date collectTime;

    private long cacheSize;

    private long freeMemory;

    private long freeBlocks;

    private long totalBlocks;

    private double fragMentationRate;

    private double qcacheHits;

    private double qcacheUsage;
    
    private String formatTime;

	public String getFormatTime() {
		return formatTime;
	}

	public void setFormatTime(String formatTime) {
		this.formatTime = formatTime;
	}

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

	public long getCacheSize() {
		return cacheSize;
	}

	public void setCacheSize(long cacheSize) {
		this.cacheSize = cacheSize;
	}

	public long getFreeMemory() {
		return freeMemory;
	}

	public void setFreeMemory(long freeMemory) {
		this.freeMemory = freeMemory;
	}

	public long getFreeBlocks() {
		return freeBlocks;
	}

	public void setFreeBlocks(long freeBlocks) {
		this.freeBlocks = freeBlocks;
	}

	public long getTotalBlocks() {
		return totalBlocks;
	}

	public void setTotalBlocks(long totalBlocks) {
		this.totalBlocks = totalBlocks;
	}

	public double getFragMentationRate() {
		return fragMentationRate;
	}

	public void setFragMentationRate(double fragMentationRate) {
		this.fragMentationRate = fragMentationRate;
	}

	public double getQcacheHits() {
		return qcacheHits;
	}

	public void setQcacheHits(double qcacheHits) {
		this.qcacheHits = qcacheHits;
	}

	public double getQcacheUsage() {
		return qcacheUsage;
	}

	public void setQcacheUsage(double qcacheUsage) {
		this.qcacheUsage = qcacheUsage;
	}

	

    
}