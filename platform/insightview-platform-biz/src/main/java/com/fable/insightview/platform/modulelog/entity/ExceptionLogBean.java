/*
 * ExceptionLogBean.java
 * Copyright(C) 2015-2020 飞搏软件公司
 * All rights reserved.
 *-------------------------------------------------
 * 2015-10-19 Created
 */ 
package com.fable.insightview.platform.modulelog.entity;

import java.util.Date;

/**
 * 异常日志表
 * 
 * @author fables
 * @version 1.0 2015-10-19
 */
//@ApiModel(value="异常日志表")
public class ExceptionLogBean {
    /**
     * 主键
     */
    //@ApiModelProperty(value="主键")
    private String id;

    /**
     * 系统ID
     */
    //@ApiModelProperty(value="系统ID")
    private String sysId;

    /**
     * 用户ID
     */
    //@ApiModelProperty(value="用户ID")
    private String userId;

    /**
     * 用户名
     */
    //@ApiModelProperty(value="用户名")
    private String username;

    /**
     * 异常代码
     */
    //@ApiModelProperty(value="异常代码")
    private String exceptionCode;

    /**
     * 异常消息
     */
    //@ApiModelProperty(value="异常消息")
    private String exceptionMsg;

    /**
     * 异常堆栈信息
     */
    //@ApiModelProperty(value="异常堆栈信息")
    private String exceptionStackInfo;

    /**
     * 模块名称
     */
    //@ApiModelProperty(value="模块名称")
    private String modulName;

    /**
     * 备注
     */
    //@ApiModelProperty(value="备注")
    private String note;

    /**
     * 创建者
     */
    //@ApiModelProperty(value="创建者")
    private String creatorId;

    /**
     * 创建者姓名
     */
    //@ApiModelProperty(value="创建者姓名")
    private String creatorName;

    /**
     * 创建时间
     */
    //@ApiModelProperty(value="创建时间")
    private Date createdTime;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(String exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public String getExceptionMsg() {
        return exceptionMsg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }

    public String getExceptionStackInfo() {
        return exceptionStackInfo;
    }

    public void setExceptionStackInfo(String exceptionStackInfo) {
        this.exceptionStackInfo = exceptionStackInfo;
    }

    public String getModulName() {
        return modulName;
    }

    public void setModulName(String modulName) {
        this.modulName = modulName;
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
}