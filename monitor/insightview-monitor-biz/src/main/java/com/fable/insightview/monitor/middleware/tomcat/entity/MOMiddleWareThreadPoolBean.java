package com.fable.insightview.monitor.middleware.tomcat.entity;

public class MOMiddleWareThreadPoolBean {
	private Integer moId;
	private Integer parentMOID;
	private String threadName;// 池名称
	private long maxConns;// 最大线程数
	private long minConns;// 最小线程数
	private long inactivityTimeOut;// 线程不活跃超时
	private String growable;// 是否允许超出最大尺寸
	private long initCount;// 初始创建线程数
	private long maxSpareSize;// 最大空闲线程数
	private long currThreads;//当前线程数
	private long busyThreads;//繁忙线程数
	//webLogic
	private long executeThreadTotal;//线程总数
	private long executeThreadIdle;//空闲线程数
	private long pendingUserRequest;//暂挂用户请求数
	private double threadPoolUsage;//线程使用率
	private String kpiName;
	private long perfValue;
	private String ip;
	
	public Integer getMoId() {
		return moId;
	}
	public void setMoId(Integer moId) {
		this.moId = moId;
	}
	public Integer getParentMOID() {
		return parentMOID;
	}
	public void setParentMOID(Integer parentMOID) {
		this.parentMOID = parentMOID;
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
	public long getInitCount() {
		return initCount;
	}
	public void setInitCount(long initCount) {
		this.initCount = initCount;
	}
	public long getMaxSpareSize() {
		return maxSpareSize;
	}
	public void setMaxSpareSize(long maxSpareSize) {
		this.maxSpareSize = maxSpareSize;
	}
	public long getCurrThreads() {
		return currThreads;
	}
	public void setCurrThreads(long currThreads) {
		this.currThreads = currThreads;
	}
	public long getBusyThreads() {
		return busyThreads;
	}
	public void setBusyThreads(long busyThreads) {
		this.busyThreads = busyThreads;
	}
	public String getKpiName() {
		return kpiName;
	}
	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}
	public long getPerfValue() {
		return perfValue;
	}
	public void setPerfValue(long perfValue) {
		this.perfValue = perfValue;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public double getThreadPoolUsage() {
		return threadPoolUsage;
	}
	public void setThreadPoolUsage(double threadPoolUsage) {
		this.threadPoolUsage = threadPoolUsage;
	}
	public long getExecuteThreadTotal() {
		return executeThreadTotal;
	}
	public void setExecuteThreadTotal(long executeThreadTotal) {
		this.executeThreadTotal = executeThreadTotal;
	}
	public long getExecuteThreadIdle() {
		return executeThreadIdle;
	}
	public void setExecuteThreadIdle(long executeThreadIdle) {
		this.executeThreadIdle = executeThreadIdle;
	}
	public long getPendingUserRequest() {
		return pendingUserRequest;
	}
	public void setPendingUserRequest(long pendingUserRequest) {
		this.pendingUserRequest = pendingUserRequest;
	}
	

	
}
