/*
 * LogConfigBean.java
 * Copyright(C) 2015-2020 飞搏软件公司
 * All rights reserved.
 *-------------------------------------------------
 * 2015-10-19 Created
 */ 
package com.fable.insightview.platform.modulelog.entity;

import java.util.Date;

/**
 * @author nimj
 */
//@ApiModel(value = "日志配置表")
public class LogConfigBean {
    //@ApiModelProperty(value="主键")
    private String id;

    //@ApiModelProperty(value="系统ID")
    private String sysId;

    //@ApiModelProperty(value = "日志类型（1：系统访问日志，2：系统操作日志，3：系统异常日志）")
    private String type;

    //@ApiModelProperty(value = "是否启动（0：未启动，1：已启动）")
    private String isStart = "0";

    //@ApiModelProperty(value = "时间配置（cron表达式）")
    private String timeCfg;

    //@ApiModelProperty(value="备注")
    private String note;

    //@ApiModelProperty(value = "创建者ID")
    private String creatorId;

    //@ApiModelProperty(value="创建者姓名")
    private String creatorName;

    //@ApiModelProperty(value="创建时间")
    private Date createdTime;

    //@ApiModelProperty(value = "修改者ID")
    private String updateId;

    //@ApiModelProperty(value = "修改者姓名")
    private String updateName;

    //@ApiModelProperty(value = "修改时间")
    private Date updatedTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }


    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsStart() {
        return isStart;
    }

    public void setIsStart(String isStart) {
        this.isStart = isStart;
    }

    public String getTimeCfg() {
        return timeCfg;
    }

    public void setTimeCfg(String timeCfg) {
        this.timeCfg = timeCfg;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
}