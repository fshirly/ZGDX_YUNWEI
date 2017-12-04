/*
 * ListviewFieldLabelBean.java
 * Copyright(C) 2015-2020 飞搏软件公司
 * All rights reserved.
 *-------------------------------------------------
 * 2015-10-19 Created
 */ 
package com.fable.insightview.platform.listview.entity;

import java.util.Date;

/**
 * ListView字段标签表
 * 
 * @author fables
 * @version 1.0 2015-10-19
 */
//@ApiModel(value="ListView字段标签表")
public class ListviewFieldLabelBean {
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
     * LISTVIEW ID
     */
    //@ApiModelProperty(value="LISTVIEW ID")
    private String listviewId;

    /**
     * 属性名称(字段名)
     */
    //@ApiModelProperty(value="属性名称(字段名)")
    private String propertyName;

    /**
     * 显示标题
     */
    //@ApiModelProperty(value="显示标题")
    private String displayTitle;

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

    public String getListviewId() {
        return listviewId;
    }

    public void setListviewId(String listviewId) {
        this.listviewId = listviewId;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getDisplayTitle() {
        return displayTitle;
    }

    public void setDisplayTitle(String displayTitle) {
        this.displayTitle = displayTitle;
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