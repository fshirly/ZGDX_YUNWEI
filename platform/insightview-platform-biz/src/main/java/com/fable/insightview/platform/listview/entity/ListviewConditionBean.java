/*
 * ListviewConditionBean.java
 * Copyright(C) 2015-2020 飞搏软件公司
 * All rights reserved.
 *-------------------------------------------------
 * 2015-10-19 Created
 */ 
package com.fable.insightview.platform.listview.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ListView条件表
 * 
 * @author fables
 * @version 1.0 2015-10-19
 */
//@ApiModel(value="ListView条件表")
public class ListviewConditionBean {
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
     * 控件类型
     */
    //@ApiModelProperty(value="控件类型")
    private String controlType;

    /**
     * 左括号
     */
    //@ApiModelProperty(value="左括号")
    private String leftBracket;

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
     * 比较符
     */
    //@ApiModelProperty(value="比较符")
    private String operator;

    /**
     * 右括号
     */
    //@ApiModelProperty(value="右括号")
    private String rightBracket;

    /**
     * 逻辑运算符
     */
    //@ApiModelProperty(value="逻辑运算符")
    private String logicSymbol;

    /**
     * 数据源类型
     */
    //@ApiModelProperty(value="数据源类型")
    private BigDecimal sourceType;

    /**
     * 数据源
     */
    //@ApiModelProperty(value="数据源")
    private String listSource;

    /**
     * 默认值
     */
    //@ApiModelProperty(value="默认值")
    private String defaultValue;

    /**
     * 序号
     */
    //@ApiModelProperty(value="序号")
    private BigDecimal sortOrder;

    /**
     * 是否高级
     */
    //@ApiModelProperty(value="是否高级")
    private String advanced;

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

    public String getControlType() {
        return controlType;
    }

    public void setControlType(String controlType) {
        this.controlType = controlType;
    }

    public String getLeftBracket() {
        return leftBracket;
    }

    public void setLeftBracket(String leftBracket) {
        this.leftBracket = leftBracket;
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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getRightBracket() {
        return rightBracket;
    }

    public void setRightBracket(String rightBracket) {
        this.rightBracket = rightBracket;
    }

    public String getLogicSymbol() {
        return logicSymbol;
    }

    public void setLogicSymbol(String logicSymbol) {
        this.logicSymbol = logicSymbol;
    }

    public BigDecimal getSourceType() {
        return sourceType;
    }

    public void setSourceType(BigDecimal sourceType) {
        this.sourceType = sourceType;
    }

    public String getListSource() {
        return listSource;
    }

    public void setListSource(String listSource) {
        this.listSource = listSource;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public BigDecimal getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(BigDecimal sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getAdvanced() {
        return advanced;
    }

    public void setAdvanced(String advanced) {
        this.advanced = advanced;
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