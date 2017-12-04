package com.fable.insightview.monitor.middleware.websphere.entity;

import java.util.Date;

public class PerfWASThreadPoolBean {
	private Integer id;
	private Integer moId;
	private Date collectTime;
	private long createThreads;//创建线程数
	private long activeThreads;//活动线程数
	private long destroyThreads;//销毁线程数
	private double poolSize;//线程池大小
	private double percentMaxed;//所有线程的平均使用时间百分比
	private String threadName;// 池名称
	private long maxConns;// 最大线程数
	private long minConns;// 最小线程数
	private long inactivityTimeOut;// 线程不活跃超时
	private String growable;// 是否允许超出最大尺寸
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
	public long getCreateThreads() {
		return createThreads;
	}
	public void setCreateThreads(long createThreads) {
		this.createThreads = createThreads;
	}
	public long getActiveThreads() {
		return activeThreads;
	}
	public void setActiveThreads(long activeThreads) {
		this.activeThreads = activeThreads;
	}
	public long getDestroyThreads() {
		return destroyThreads;
	}
	public void setDestroyThreads(long destroyThreads) {
		this.destroyThreads = destroyThreads;
	}
	public double getPoolSize() {
		return poolSize;
	}
	public void setPoolSize(double poolSize) {
		this.poolSize = poolSize;
	}
	public double getPercentMaxed() {
		return percentMaxed;
	}
	public void setPercentMaxed(double percentMaxed) {
		this.percentMaxed = percentMaxed;
	}
	public String getFormatTime() {
		return formatTime;
	}
	public void setFormatTime(String formatTime) {
		this.formatTime = formatTime;
	}
	public String getThreadName() {
		return threadName;
	}
	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}
	public long getMaxConns() {
		return maxConns;
	}
	public void setMaxConns(long maxConns) {
		this.maxConns = maxConns;
	}
	public long getMinConns() {
		return minConns;
	}
	public void setMinConns(long minConns) {
		this.minConns = minConns;
	}
	public long getInactivityTimeOut() {
		return inactivityTimeOut;
	}
	public void setInactivityTimeOut(long inactivityTimeOut) {
		this.inactivityTimeOut = inactivityTimeOut;
	}
	public String getGrowable() {
		return growable;
	}
	public void setGrowable(String growable) {
		this.growable = growable;
	}
	
}
