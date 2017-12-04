package com.fable.insightview.monitor.syslog.entity;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * syslog任务
 * 
 */
@Alias("syslogTask")
public class SysSyslogTaskBean {
	@NumberGenerator(name = "MonitorCollectTaskPK")
	private Integer taskID;
	private Integer operateStatus;
	private Integer progressStatus;
	private String lastStatusTime;
	private Integer collectorID;
	private Integer ruleID;
	private Long creator;
	private String createTime;
	private Integer lastOPResult;
	private String errorInfo;

	private String ruleName;
	private String creatorName;
	private String collectorName;
	private String taskIDs;
	
	private String isOffline;
	
	public Integer getTaskID() {
		return taskID;
	}

	public void setTaskID(Integer taskID) {
		this.taskID = taskID;
	}

	public Integer getProgressStatus() {
		return progressStatus;
	}

	public void setProgressStatus(Integer progressStatus) {
		this.progressStatus = progressStatus;
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

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Integer getOperateStatus() {
		return operateStatus;
	}

	public void setOperateStatus(Integer operateStatus) {
		this.operateStatus = operateStatus;
	}

	public String getLastStatusTime() {
		return lastStatusTime;
	}

	public void setLastStatusTime(String lastStatusTime) {
		this.lastStatusTime = lastStatusTime;
	}

	public Integer getCollectorID() {
		return collectorID;
	}

	public void setCollectorID(Integer collectorID) {
		this.collectorID = collectorID;
	}

	public Integer getRuleID() {
		return ruleID;
	}

	public void setRuleID(Integer ruleID) {
		this.ruleID = ruleID;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getCollectorName() {
		return collectorName;
	}

	public void setCollectorName(String collectorName) {
		this.collectorName = collectorName;
	}

	public String getTaskIDs() {
		return taskIDs;
	}

	public void setTaskIDs(String taskIDs) {
		this.taskIDs = taskIDs;
	}

	public String getIsOffline() {
		return isOffline;
	}

	public void setIsOffline(String isOffline) {
		this.isOffline = isOffline;
	}
}
