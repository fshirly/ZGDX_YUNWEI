package com.fable.insightview.monitor.database.entity;

import java.util.Date;

public class PerfMySQLTableCacheBean {
    private Integer id;

    private Integer moId;

    private Date collectTime;

    private long openedTables;

    private long openTables;

    private long tableCache;

    private double tableCacheHits;

    private double tableCacheUsage;
    
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

	public long getOpenedTables() {
		return openedTables;
	}

	public void setOpenedTables(long openedTables) {
		this.openedTables = openedTables;
	}

	public long getOpenTables() {
		return openTables;
	}

	public void setOpenTables(long openTables) {
		this.openTables = openTables;
	}

	public long getTableCache() {
		return tableCache;
	}

	public void setTableCache(long tableCache) {
		this.tableCache = tableCache;
	}

	public double getTableCacheHits() {
		return tableCacheHits;
	}

	public void setTableCacheHits(double tableCacheHits) {
		this.tableCacheHits = tableCacheHits;
	}

	public double getTableCacheUsage() {
		return tableCacheUsage;
	}

	public void setTableCacheUsage(double tableCacheUsage) {
		this.tableCacheUsage = tableCacheUsage;
	}

}