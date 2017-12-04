package com.fable.insightview.monitor.perf.entity;

import org.apache.ibatis.type.Alias;


@Alias("perfTask")
public class PerfPollTaskBean {
	// @NumberGenerator(name = "perfPollTaskId")
	private int taskId;
	private int mid;
	private int moId;
	private int doIntervals;
	private int status;
	private String createTime;
	private String deviceIp;
	private String deviceType;
	private String deviceManufacture;
	private int moCounts;
	private int serverId;
	private String serverName;
	private String lastStatusTime;// 最近状态时间
	private int progressStatus;// 操作进度
	private int operateStatus;// 最近一次操作状态
	private int lastOPResult;// 最近操作结果
	private String lastOPTime;// 最近操作时间
	private String errorInfo;
	private int moClassId;
	private String className;
	private String isOffline;
	
	public PerfPollTaskBean() {
	}

	public int getOperateStatus() {
		return operateStatus;
	}

	public void setOperateStatus(int operateStatus) {
		this.operateStatus = operateStatus;
	}

	public int getLastOPResult() {
		return lastOPResult;
	}

	public void setLastOPResult(int lastOPResult) {
		this.lastOPResult = lastOPResult;
	}

	public String getLastOPTime() {
		return lastOPTime;
	}

	public void setLastOPTime(String lastOPTime) {
		this.lastOPTime = lastOPTime;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	public PerfPollTaskBean(int taskId, int mid, int moId, int doIntervals,
			int status, String createTime, String deviceIp, String deviceType,
			String deviceManufacture, int moCounts, int serverId,
			String serverName, String lastStatusTime, int progressStatus,
			int operateStatus, int lastOPResult, String lastOPTime,
			String errorInfo,String isOffline) {
		super();
		this.taskId = taskId;
		this.mid = mid;
		this.moId = moId;
		this.doIntervals = doIntervals;
		this.status = status;
		this.createTime = createTime;
		this.deviceIp = deviceIp;
		this.deviceType = deviceType;
		this.deviceManufacture = deviceManufacture;
		this.moCounts = moCounts;
		this.serverId = serverId;
		this.serverName = serverName;
		this.lastStatusTime = lastStatusTime;
		this.progressStatus = progressStatus;
		this.operateStatus = operateStatus;
		this.lastOPResult = lastOPResult;
		this.lastOPTime = lastOPTime;
		this.errorInfo = errorInfo;
		this.isOffline = isOffline;
	}

	public String getLastStatusTime() {
		return lastStatusTime;
	}

	public void setLastStatusTime(String lastStatusTime) {
		this.lastStatusTime = lastStatusTime;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public int getMoId() {
		return moId;
	}

	public void setMoId(int moId) {
		this.moId = moId;
	}

	public int getDoIntervals() {
		return doIntervals;
	}

	public void setDoIntervals(int doIntervals) {
		this.doIntervals = doIntervals;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getDeviceIp() {
		return deviceIp;
	}

	public void setDeviceIp(String deviceIp) {
		this.deviceIp = deviceIp;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceManufacture() {
		return deviceManufacture;
	}

	public void setDeviceManufacture(String deviceManufacture) {
		this.deviceManufacture = deviceManufacture;
	}

	public int getMoCounts() {
		return moCounts;
	}

	public void setMoCounts(int moCounts) {
		this.moCounts = moCounts;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public int getProgressStatus() {
		return progressStatus;
	}

	public void setProgressStatus(int progressStatus) {
		this.progressStatus = progressStatus;
	}

	public int getMoClassId() {
		return moClassId;
	}

	public void setMoClassId(int moClassId) {
		this.moClassId = moClassId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getIsOffline() {
		return isOffline;
	}

	public void setIsOffline(String isOffline) {
		this.isOffline = isOffline;
	}
}
