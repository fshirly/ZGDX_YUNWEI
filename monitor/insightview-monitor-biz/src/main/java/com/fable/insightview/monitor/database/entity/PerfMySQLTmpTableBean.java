package com.fable.insightview.monitor.database.entity;

import java.math.BigDecimal;
import java.util.Date;

public class PerfMySQLTmpTableBean {
    private Integer id;

    private Integer moId;

    private Date collectTime;

    private long tmpTables;

    private long tmpDiskTables;

    private double tmpUsage;
    
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

	public long getTmpTables() {
		return tmpTables;
	}

	public void setTmpTables(long tmpTables) {
		this.tmpTables = tmpTables;
	}

	public long getTmpDiskTables() {
		return tmpDiskTables;
	}

	public void setTmpDiskTables(long tmpDiskTables) {
		this.tmpDiskTables = tmpDiskTables;
	}

	public double getTmpUsage() {
		return tmpUsage;
	}

	public void setTmpUsage(double tmpUsage) {
		this.tmpUsage = tmpUsage;
	}

    
}