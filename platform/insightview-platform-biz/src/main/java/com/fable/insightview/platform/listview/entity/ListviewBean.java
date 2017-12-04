/*
 * ListviewBean.java
 * Copyright(C) 2015-2020 飞搏软件公司
 * All rights reserved.
 *-------------------------------------------------
 * 2015-10-28 Created
 */ 
package com.fable.insightview.platform.listview.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ListView配置表
 * 
 * @author fables
 * @version 1.0 2015-10-28
 */
//@ApiModel(value="ListView配置表")
public class ListviewBean {
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
     * 名称（英文）
     */
    //@ApiModelProperty(value="名称（英文）")
    private String name;

    /**
     * 标题（中文）
     */
    //@ApiModelProperty(value="标题（中文）")
    private String title;

    /**
     * 数据对象ID
     */
    //@ApiModelProperty(value="数据对象ID")
    private String dataobjectId;

    /**
     * 过滤
     */
    //@ApiModelProperty(value="过滤")
    private String filter;

    /**
     * 排序
     */
    //@ApiModelProperty(value="排序")
    private String sortOrder;

    /**
     * 页记录数
     */
    //@ApiModelProperty(value="页记录数")
    private BigDecimal pageSize;

    /**
     * 分页可选范围
     */
    //@ApiModelProperty(value="分页可选范围")
    private String pageSizeList;

    /**
     *  条件列数
     */
    //@ApiModelProperty(value=" 条件列数")
    private BigDecimal colCount;

    /**
     * 是否允许导出
     */
    //@ApiModelProperty(value="是否允许导出")
    private String isExport;

    /**
     * 是否自动加载
     */
    //@ApiModelProperty(value="是否自动加载")
    private String isAutoBind;

    /**
     * 启用数据权限
     */
    //@ApiModelProperty(value="启用数据权限")
    private String isRight;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDataobjectId() {
        return dataobjectId;
    }

    public void setDataobjectId(String dataobjectId) {
        this.dataobjectId = dataobjectId;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public BigDecimal getPageSize() {
        return pageSize;
    }

    public void setPageSize(BigDecimal pageSize) {
        this.pageSize = pageSize;
    }

    public String getPageSizeList() {
        return pageSizeList;
    }

    public void setPageSizeList(String pageSizeList) {
        this.pageSizeList = pageSizeList;
    }

    public BigDecimal getColCount() {
        return colCount;
    }

    public void setColCount(BigDecimal colCount) {
        this.colCount = colCount;
    }

    public String getIsExport() {
        return isExport;
    }

    public void setIsExport(String isExport) {
        this.isExport = isExport;
    }

    public String getIsAutoBind() {
        return isAutoBind;
    }

    public void setIsAutoBind(String isAutoBind) {
        this.isAutoBind = isAutoBind;
    }

    public String getIsRight() {
        return isRight;
    }

    public void setIsRight(String isRight) {
        this.isRight = isRight;
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
}