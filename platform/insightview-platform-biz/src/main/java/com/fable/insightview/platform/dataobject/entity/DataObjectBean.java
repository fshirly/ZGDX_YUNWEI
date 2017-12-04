/*
 * DataObjectBean.java
 * Copyright(C) 2015-2020 飞搏软件公司
 * All rights reserved.
 *-------------------------------------------------
 * 2015-10-27 Created
 */
package com.fable.insightview.platform.dataobject.entity;

import java.util.Date;
import java.util.List;

/**
 * 数据对象表
 * 
 * @author fables
 * @version 1.0 2015-10-27
 */
//@ApiModel(value = "数据对象表")
public class DataObjectBean {
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
	 * 对象名称
	 */
	//@ApiModelProperty(value = "对象名称")
	private String objectName;

	/**
	 * 类型，1表；2视图；3自定义SQL
	 */
	//@ApiModelProperty(value = "类型，1表；2视图；3自定义SQL")
	private String type;

	/**
	 * SQL
	 */
	//@ApiModelProperty(value = "SQL")
	private String fbsSql;

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
	 * 数据对象字段标签列表
	 */
	//@ApiModelProperty(value = "数据对象字段标签列表")
	private List<DataObjectFieldLabelBean> fieldLabels;

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

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFbsSql() {
		return fbsSql;
	}

	public void setFbsSql(String fbsSql) {
		this.fbsSql = fbsSql;
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

	public List<DataObjectFieldLabelBean> getFieldLabels() {
		return fieldLabels;
	}

	public void setFieldLabels(List<DataObjectFieldLabelBean> fieldLabels) {
		this.fieldLabels = fieldLabels;
	}
}