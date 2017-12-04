package com.fable.insightview.monitor.database.entity;

import java.util.Date;

public class PerfMSSQLDatabaseBean {
	 private Integer id;
	 private Integer moId;
	 private Date collectTime;
	 private long dataFileSize;//数据文件总大小
	 private long logFileSize;//日志文件总大小
	 private long logFileUsedSize;//日志文件已用大小
	 private double logUsage;//日志空间利用率
	 private long transactions;//每秒事务数
	 private long activeTransactions;//活跃事务数
	 private long logFlushes;//每秒日志刷新数
	 private long logFlushWaits;//每秒等待日志刷新的提交数
	 private long logFlushWaitTime;//刷新日志的总等待时间
	 private long logGrowths;//日志增长次数
	 private long logShrinks;//日志收缩次数
	 private long logTruncations;//日志截断次数
	 private String formatTime;
	 private long perfValue;
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
	public long getDataFileSize() {
		return dataFileSize;
	}
	public void setDataFileSize(long dataFileSize) {
		this.dataFileSize = dataFileSize;
	}
	public long getLogFileSize() {
		return logFileSize;
	}
	public void setLogFileSize(long logFileSize) {
		this.logFileSize = logFileSize;
	}
	public long getLogFileUsedSize() {
		return logFileUsedSize;
	}
	public void setLogFileUsedSize(long logFileUsedSize) {
		this.logFileUsedSize = logFileUsedSize;
	}
	public double getLogUsage() {
		return logUsage;
	}
	public void setLogUsage(double logUsage) {
		this.logUsage = logUsage;
	}
	public long getTransactions() {
		return transactions;
	}
	public void setTransactions(long transactions) {
		this.transactions = transactions;
	}
	public long getActiveTransactions() {
		return activeTransactions;
	}
	public void setActiveTransactions(long activeTransactions) {
		this.activeTransactions = activeTransactions;
	}
	public long getLogFlushes() {
		return logFlushes;
	}
	public void setLogFlushes(long logFlushes) {
		this.logFlushes = logFlushes;
	}
	public long getLogFlushWaits() {
		return logFlushWaits;
	}
	public void setLogFlushWaits(long logFlushWaits) {
		this.logFlushWaits = logFlushWaits;
	}
	public long getLogFlushWaitTime() {
		return logFlushWaitTime;
	}
	public void setLogFlushWaitTime(long logFlushWaitTime) {
		this.logFlushWaitTime = logFlushWaitTime;
	}
	public long getLogGrowths() {
		return logGrowths;
	}
	public void setLogGrowths(long logGrowths) {
		this.logGrowths = logGrowths;
	}
	public long getLogShrinks() {
		return logShrinks;
	}
	public void setLogShrinks(long logShrinks) {
		this.logShrinks = logShrinks;
	}
	public long getLogTruncations() {
		return logTruncations;
	}
	public void setLogTruncations(long logTruncations) {
		this.logTruncations = logTruncations;
	}
	public String getFormatTime() {
		return formatTime;
	}
	public void setFormatTime(String formatTime) {
		this.formatTime = formatTime;
	}
	public long getPerfValue() {
		return perfValue;
	}
	public void setPerfValue(long perfValue) {
		this.perfValue = perfValue;
	}
	 
}
