package com.fable.insightview.monitor.alarmmgr.devicewblistsetting.entity;

import java.util.Date;

public class SysBlackListTaskBean {
    private Integer taskID;

    private Integer operateStatus;

    private Integer progressStatus;

    private Date lastStatusTime;

    private Integer collectorID;

    private Integer blackID;

    private Integer creator;

    private Date createTime;

    private Integer lastOPResult;

    private Integer oldCollectorID;

    private String errorInfo;

    private String isOffline;

    public Integer getTaskID() {
        return taskID;
    }

    public void setTaskID(Integer taskID) {
        this.taskID = taskID;
    }

    public Integer getOperateStatus() {
        return operateStatus;
    }

    public void setOperateStatus(Integer operateStatus) {
        this.operateStatus = operateStatus;
    }

    public Integer getProgressStatus() {
        return progressStatus;
    }

    public void setProgressStatus(Integer progressStatus) {
        this.progressStatus = progressStatus;
    }

    public Date getLastStatusTime() {
        return lastStatusTime;
    }

    public void setLastStatusTime(Date lastStatusTime) {
        this.lastStatusTime = lastStatusTime;
    }

    public Integer getCollectorID() {
        return collectorID;
    }

    public void setCollectorID(Integer collectorID) {
        this.collectorID = collectorID;
    }

    public Integer getBlackID() {
        return blackID;
    }

    public void setBlackID(Integer blackID) {
        this.blackID = blackID;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getLastOPResult() {
        return lastOPResult;
    }

    public void setLastOPResult(Integer lastOPResult) {
        this.lastOPResult = lastOPResult;
    }

    public Integer getOldCollectorID() {
        return oldCollectorID;
    }

    public void setOldCollectorID(Integer oldCollectorID) {
        this.oldCollectorID = oldCollectorID;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo == null ? null : errorInfo.trim();
    }

    public String getIsOffline() {
        return isOffline;
    }

    public void setIsOffline(String isOffline) {
        this.isOffline = isOffline == null ? null : isOffline.trim();
    }
}