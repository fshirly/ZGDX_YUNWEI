package com.fable.insightview.monitor.middleware.tomcat.entity;

public class MOMiddleWareMemoryBean {
	private Integer moId;
	private Integer parentMOID;
	private String memType;// 内存池类型
	private String memName;// 内存池名称
	private String mGRName;// 内存管理器名称
	private String initSize;//初始大小
	private String maxSize;//最大大小
	private String memoryFree;//空闲内存
	private double memoryUsage;//内存百分百
	private String kpiName;
	private long perfValue;
	private String ip;
	private Integer port;
	private String serverName;
	
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
	public String getMemType() {
		return memType;
	}
	public void setMemType(String memType) {
		this.memType = memType;
	}
	public String getMemName() {
		return memName;
	}
	public void setMemName(String memName) {
		this.memName = memName;
	}
	public String getmGRName() {
		return mGRName;
	}
	public void setmGRName(String mGRName) {
		this.mGRName = mGRName;
	}

	public double getMemoryUsage() {
		return memoryUsage;
	}
	public void setMemoryUsage(double memoryUsage) {
		this.memoryUsage = memoryUsage;
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
	public String getInitSize() {
		return initSize;
	}
	public void setInitSize(String initSize) {
		this.initSize = initSize;
	}
	public String getMaxSize() {
		return maxSize;
	}
	public void setMaxSize(String maxSize) {
		this.maxSize = maxSize;
	}
	public String getMemoryFree() {
		return memoryFree;
	}
	public void setMemoryFree(String memoryFree) {
		this.memoryFree = memoryFree;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	
}
