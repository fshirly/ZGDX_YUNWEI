package com.fable.insightview.monitor.database.entity;

import java.util.Date;

public class PerfDB2DatabaseBean {
	private Integer id;
	private Integer moId;
	private Date collectTime;
	private Integer spilledUsage;
	private Integer logUsage;
	private Integer deadLocks;
	private Integer shareMemUsage;
	private Integer lockEscals;
	private Integer waitingLockUsage;
	private Integer pkgCacheHits;
	private Integer catCacheHits;
	private Integer succSqls;
	private Integer failedSqls;
	private Integer workUnits;
	private String formatTime;
	private String databaseName;
	private String perfValue;
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
	public Integer getSpilledUsage() {
		return spilledUsage;
	}
	public void setSpilledUsage(Integer spilledUsage) {
		this.spilledUsage = spilledUsage;
	}
	public Integer getLogUsage() {
		return logUsage;
	}
	public void setLogUsage(Integer logUsage) {
		this.logUsage = logUsage;
	}
	public Integer getDeadLocks() {
		return deadLocks;
	}
	public void setDeadLocks(Integer deadLocks) {
		this.deadLocks = deadLocks;
	}
	public Integer getShareMemUsage() {
		return shareMemUsage;
	}
	public void setShareMemUsage(Integer shareMemUsage) {
		this.shareMemUsage = shareMemUsage;
	}
	public Integer getLockEscals() {
		return lockEscals;
	}
	public void setLockEscals(Integer lockEscals) {
		this.lockEscals = lockEscals;
	}
	public Integer getWaitingLockUsage() {
		return waitingLockUsage;
	}
	public void setWaitingLockUsage(Integer waitingLockUsage) {
		this.waitingLockUsage = waitingLockUsage;
	}
	public Integer getPkgCacheHits() {
		return pkgCacheHits;
	}
	public void setPkgCacheHits(Integer pkgCacheHits) {
		this.pkgCacheHits = pkgCacheHits;
	}
	public Integer getCatCacheHits() {
		return catCacheHits;
	}
	public void setCatCacheHits(Integer catCacheHits) {
		this.catCacheHits = catCacheHits;
	}
	public Integer getSuccSqls() {
		return succSqls;
	}
	public void setSuccSqls(Integer succSqls) {
		this.succSqls = succSqls;
	}
	public Integer getFailedSqls() {
		return failedSqls;
	}
	public void setFailedSqls(Integer failedSqls) {
		this.failedSqls = failedSqls;
	}
	public Integer getWorkUnits() {
		return workUnits;
	}
	public void setWorkUnits(Integer workUnits) {
		this.workUnits = workUnits;
	}
	public String getFormatTime() {
		return formatTime;
	}
	public void setFormatTime(String formatTime) {
		this.formatTime = formatTime;
	}
	public String getDatabaseName() {
		return databaseName;
	}
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
	public String getPerfValue() {
		return perfValue;
	}
	public void setPerfValue(String perfValue) {
		this.perfValue = perfValue;
	}
	
}
