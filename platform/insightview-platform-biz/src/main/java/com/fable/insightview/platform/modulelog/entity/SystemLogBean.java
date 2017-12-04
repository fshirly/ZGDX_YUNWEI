/*
 * SystemLogBean.java
 * Copyright(C) 2015-2020 飞搏软件公司
 * All rights reserved.
 *-------------------------------------------------
 * 2015-10-19 Created
 */
package com.fable.insightview.platform.modulelog.entity;

import java.util.Date;

/**
 * 系统URL日志表
 * 
 * @author fables
 * @version 1.0 2015-10-19
 */
//@ApiModel(value = "系统URL日志表")
public class SystemLogBean {
    /**
     * 主键
     */
    //@ApiModelProperty(value = "主键")
    private String id;

    /**
     * 系统ID
     */
    //@ApiModelProperty(value = "系统ID")
    private String sysId;

    /**
     * 用户ID
     */
    //@ApiModelProperty(value = "用户ID")
    private String userId;

    /**
     * 用户名
     */
    //@ApiModelProperty(value = "用户名")
    private String username;

    /**
     * 页面名称
     */
    //@ApiModelProperty(value = "页面名称")
    private String pageName;

    /**
     * URL
     */
    //@ApiModelProperty(value = "URL")
    private String url;

    /**
     * 方法名
     */
    //@ApiModelProperty(value = "方法名")
    private String methodName;

    /**
     * 客户端名称
     */
    //@ApiModelProperty(value = "客户端名称")
    private String clientName;

    /**
     * IP地址
     */
    //@ApiModelProperty(value = "IP地址")
    private String ip;

    /**
     * 客户端代理
     */
    //@ApiModelProperty(value = "客户端代理")
    private String clientAgent;

    /**
     * 备注
     */
    //@ApiModelProperty(value = "备注")
    private String note;

    /**
     * 创建者
     */
    //@ApiModelProperty(value = "创建者")
    private String creatorId;

    /**
     * 创建者姓名
     */
    //@ApiModelProperty(value = "创建者姓名")
    private String creatorName;

    /**
     * 创建时间
     */
    //@ApiModelProperty(value = "创建时间")
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

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getClientAgent() {
        return clientAgent;
    }

    public void setClientAgent(String clientAgent) {
        this.clientAgent = clientAgent;
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