/*
 * DataSecurityItemBean.java
 * Copyright(C) 2015-2020 飞搏软件公司
 * All rights reserved.
 *-------------------------------------------------
 * 2015-10-27 Created
 */ 
package com.fable.insightview.platform.dataobject.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 数据权限条件表
 * 
 * @author fables
 * @version 1.0 2015-10-27
 */
//@ApiModel(value="数据权限条件表")
public class DataSecurityItemBean {
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
     * 数据权限ID
     */
    //@ApiModelProperty(value="数据权限ID")
    private String securityId;

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
     * 比较符
     */
    //@ApiModelProperty(value="比较符")
    private String operator;

    /**
     * 比较值
     */
    //@ApiModelProperty(value="比较值")
    private String value;

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
     * 序号
     */
    //@ApiModelProperty(value="序号")
    private BigDecimal sortorder;

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

    public String getSecurityId() {
        return securityId;
    }

    public void setSecurityId(String securityId) {
        this.securityId = securityId;
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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public BigDecimal getSortorder() {
        return sortorder;
    }

    public void setSortorder(BigDecimal sortorder) {
        this.sortorder = sortorder;
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