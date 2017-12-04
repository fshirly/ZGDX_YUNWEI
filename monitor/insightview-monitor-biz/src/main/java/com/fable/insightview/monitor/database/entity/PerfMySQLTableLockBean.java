package com.fable.insightview.monitor.database.entity;

import java.util.Date;

public class PerfMySQLTableLockBean {
    private Integer id;

    private Integer moId;

    private Date collectTime;

    private long tableLocksImmediate;

    private long tableLocksWaited;

    private double tableLockHits;
    
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

	public long getTableLocksImmediate() {
		return tableLocksImmediate;
	}

	public void setTableLocksImmediate(long tableLocksImmediate) {
		this.tableLocksImmediate = tableLocksImmediate;
	}

	public long getTableLocksWaited() {
		return tableLocksWaited;
	}

	public void setTableLocksWaited(long tableLocksWaited) {
		this.tableLocksWaited = tableLocksWaited;
	}

	public double getTableLockHits() {
		return tableLockHits;
	}

	public void setTableLockHits(double tableLockHits) {
		this.tableLockHits = tableLockHits;
	}

	public String getFormatTime() {
		return formatTime;
	}

	public void setFormatTime(String formatTime) {
		this.formatTime = formatTime;
	}

}