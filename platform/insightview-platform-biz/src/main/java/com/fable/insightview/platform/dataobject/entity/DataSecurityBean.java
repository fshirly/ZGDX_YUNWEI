/*
 * DataSecurityBean.java
 * Copyright(C) 2015-2020 飞搏软件公司
 * All rights reserved.
 *-------------------------------------------------
 * 2015-10-27 Created
 */
package com.fable.insightview.platform.dataobject.entity;

import java.util.Date;
import java.util.List;

/**
 * 数据权限表
 * 
 * @author fables
 * @version 1.0 2015-10-27
 */
//@ApiModel(value = "数据权限表")
public class DataSecurityBean {
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
     * 数据对象ID
     */
    //@ApiModelProperty(value = "数据对象ID")
    private String dataobjectId;

    /**
     * 角色ID
     */
    //@ApiModelProperty(value = "角色ID")
    private String roleId;

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
     * 其下的数据权限条件
     */
    //@ApiModelProperty(value = "其下的数据权限条件")
    private List<DataSecurityItemBean> dsItems;

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

    public String getDataobjectId() {
        return dataobjectId;
    }

    public void setDataobjectId(String dataobjectId) {
        this.dataobjectId = dataobjectId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
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

    public List<DataSecurityItemBean> getDsItems() {
        return dsItems;
    }

    public void setDsItems(List<DataSecurityItemBean> dsItems) {
        this.dsItems = dsItems;
    }

}