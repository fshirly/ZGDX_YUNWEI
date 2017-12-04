/*
 * OperationLogBean.java
 * Copyright(C) 2015-2020 飞搏软件公司
 * All rights reserved.
 *-------------------------------------------------
 * 2015-10-19 Created
 */ 
package com.fable.insightview.platform.modulelog.entity;

import java.util.Date;

/**
 * SQL操作日志表
 * 
 * @author fables
 * @version 1.0 2015-10-19
 */
//@ApiModel(value="SQL操作日志表")
public class OperationLogBean {
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
     * 机构名称
     */
    //@ApiModelProperty(value="机构名称")
    private String orgName;

    /**
     * 机构代码
     */
    //@ApiModelProperty(value="机构代码")
    private String orgCode;

    /**
     * 操作类型:0：登录；1：查询；2：新增；3：修改；4：删除
     */
    //@ApiModelProperty(value="操作类型:0：登录；1：查询；2：新增；3：修改；4：删除")
    private String operateType;

    /**
     * 操作时间
     */
    //@ApiModelProperty(value="操作时间")
    private Date operateTime;

    /**
     * 操作条件
     */
    //@ApiModelProperty(value="操作条件")
    private String operateCondition;

    /**
     * 操作结果:1：成功；0：失败
     */
    //@ApiModelProperty(value="操作结果:1：成功；0：失败")
    private String operateResult;

    /**
     * 终端标识
     */
    //@ApiModelProperty(value="终端标识")
    private String terminalId;

    /**
     * 错误代码
     */
    //@ApiModelProperty(value="错误代码")
    private String errorCode;

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

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperateCondition() {
        return operateCondition;
    }

    public void setOperateCondition(String operateCondition) {
        this.operateCondition = operateCondition;
    }

    public String getOperateResult() {
        return operateResult;
    }

    public void setOperateResult(String operateResult) {
        this.operateResult = operateResult;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
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