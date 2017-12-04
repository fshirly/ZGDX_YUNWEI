/*
 * ModuleBean.java
 * Copyright(C) 2015-2020 飞搏软件公司
 * All rights reserved.
 *-------------------------------------------------
 * 2015-10-19 Created
 */
package com.fable.insightview.platform.module.entity;

import java.math.BigDecimal;
import java.util.Date;

//import com.wordnik.swagger.annotations.ApiModel;
//import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * 模块表
 * 
 * @author fables
 * @version 1.0 2015-10-19
 */
//@ApiModel(value = "模块表")
public class ModuleBean {
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
     * 父模块ID
     */
    //@ApiModelProperty(value = "父模块ID")
    private String pid;

    //@ApiModelProperty(value = "模块编码（值为name，listview插件展示树用）")
    private String code;

    /**
     * 名称
     */
    //@ApiModelProperty(value = "名称")
    private String name;

    /**
     * 类型（1模块，2功能(按钮)）
     */
    //@ApiModelProperty(value = "类型（1模块，2功能(按钮)）")
    private String type;
    
    /**
     * 排序
     */
    //@ApiModelProperty(value = "排序")
    private BigDecimal sortOrder;

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

    /**
     * 修改者
     */
    //@ApiModelProperty(value = "修改者")
    private String updateId;

    /**
     * 修改者姓名
     */
    //@ApiModelProperty(value = "修改者姓名")
    private String updateName;

    /**
     * 修改时间
     */
    //@ApiModelProperty(value = "修改时间")
    private Date updatedTime;

    /**
     * 
     * URL
     */
    //@ApiModelProperty(value = "URL")
    private String url;
    
    /**
     * 
     * rest调用方法名
     */
  //@ApiModelProperty(value = "方法名")
    private String methodName;
    
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

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(BigDecimal sortOrder) {
        this.sortOrder = sortOrder;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}