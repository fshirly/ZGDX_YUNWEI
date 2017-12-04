/*
 * TreeDictionaryBean.java
 * Copyright(C) 2015-2020 飞搏软件公司
 * All rights reserved.
 *-------------------------------------------------
 * 2015-10-19 Created
 */ 
package com.fable.insightview.platform.common.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 树形字典表
 * 
 * @author fables
 * @version 1.0 2015-10-19
 */
//@ApiModel(value="树形字典表")
public class TreeDictionaryBean {
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
     * 父节点ID
     */
    //@ApiModelProperty(value="父节点ID")
    private String pid;

    /**
     * 类型
     */
    //@ApiModelProperty(value="类型")
    private String type;

    /**
     * 名称
     */
    //@ApiModelProperty(value="名称")
    private String name;

    /**
     * 代码
     */
    //@ApiModelProperty(value="代码")
    private String code;

    /**
     * 排序
     */
    //@ApiModelProperty(value="排序")
    private BigDecimal sortOrder;

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

    /**
     * 修改者
     */
    //@ApiModelProperty(value="修改者")
    private String updateId;

    /**
     * 修改者姓名
     */
    //@ApiModelProperty(value="修改者姓名")
    private String updateName;

    /**
     * 修改时间
     */
    //@ApiModelProperty(value="修改时间")
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

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
    
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
    	if(obj == null) return false;
    	if(this == obj) return true;
    	
    	if(obj instanceof TreeDictionaryBean) {
    		TreeDictionaryBean tmp = (TreeDictionaryBean)obj;
    		if(tmp.type.equals(this.type) && tmp.id.equals(this.id) && tmp.pid.equals(this.pid) && 
    				tmp.name.equals(this.name) && tmp.code.equals(this.code) && tmp.sortOrder.equals(this.sortOrder)) {
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
    	int _hashCode = 1;
    	
    	if(this.type != null)
    		_hashCode *= this.type.hashCode();
    	
    	if(this.id != null)
    		_hashCode *= this.id.hashCode();
    	
    	if(this.pid != null)
    		_hashCode *= this.pid.hashCode();
    	
    	if(this.name != null)
    		_hashCode *= this.name.hashCode();
    	
    	if(this.code != null)
    		_hashCode *= this.code.hashCode();
    	
    	if(this.sortOrder != null)
    		_hashCode *= this.sortOrder.hashCode();
    	
    	return _hashCode;
    }
}