package com.fable.insightview.monitor.alarmmgr.entity;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * trap 任务
 * 
 * @param <FilterExpression>
 * 
 */
public class SysTrapTaskBean {
	@NumberGenerator(name = "MonitorCollectTaskPK")
	private Integer taskID;
	private String serverIP;
	private String filterExpression;
	private Integer progressStatus;
	private Integer collectorID;
	private Long creator;
	private String createTime;
	private Integer lastOPResult;
	private String errorInfo;
	private Integer operateStatus;
	private String alarmOID;

	private String creatorName;
	private String collectorName;
	private String isOffline;
	private String collectorIds;

	public Integer getTaskID() {
		return taskID;
	}

	public void setTaskID(Integer taskID) {
		this.taskID = taskID;
	}

	public String getServerIP() {
		return serverIP;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	public String getFilterExpression() {
		return filterExpression;
	}

	public void setFilterExpression(String filterExpression) {
		this.filterExpression = filterExpression;
	}

	public Integer getProgressStatus() {
		return progressStatus;
	}

	public void setProgressStatus(Integer progressStatus) {
		this.progressStatus = progressStatus;
	}

	public Integer getCollectorID() {
		return collectorID;
	}

	public void setCollectorID(Integer collectorID) {
		this.collectorID = collectorID;
	}

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getLastOPResult() {
		return lastOPResult;
	}

	public void setLastOPResult(Integer lastOPResult) {
		this.lastOPResult = lastOPResult;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	public Integer getOperateStatus() {
		return operateStatus;
	}

	public void setOperateStatus(Integer operateStatus) {
		this.operateStatus = operateStatus;
	}

	public String getAlarmOID() {
		return alarmOID;
	}

	public void setAlarmOID(String alarmOID) {
		this.alarmOID = alarmOID;
	}

	public String getCollectorName() {
		return collectorName;
	}

	public void setCollectorName(String collectorName) {
		this.collectorName = collectorName;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getIsOffline() {
		return isOffline;
	}

	public void setIsOffline(String isOffline) {
		this.isOffline = isOffline;
	}

	public String getCollectorIds() {
		return collectorIds;
	}

	public void setCollectorIds(String collectorIds) {
		this.collectorIds = collectorIds;
	}
}
