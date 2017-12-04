package com.fable.insightview.platform.dataobject.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据权限模块数据权限设置页面角色及权限条件封装类
 * 
 * @author nimj
 */
//@ApiModel(value = "角色及权限条件封装类")
public class RoleWithDataSecurityBean {
    /**
     * 角色ID
     */
    //@ApiModelProperty(value = "角色ID")
    private String id;

    /**
     * 角色名
     */
    //@ApiModelProperty(value = "角色名")
    private String roleName;

    /**
     * 父角色ID
     */
    //@ApiModelProperty(value = "父角色ID")
    private String pid;

    /**
     * 数据对象ID
     */
    //@ApiModelProperty(value = "数据对象ID")
    private String dataObjectId;

    /**
     * 数据权限ID
     */
    //@ApiModelProperty(value = "数据权限ID")
    private String dataSecurityId;

    /**
     * 该角色上配置的数据权限条件
     */
    //@ApiModelProperty(value = "数据权限条件")
    private List<DataSecurityItemBean> dsItems;

    /**
     * 子角色
     */
    //@ApiModelProperty(value = "子角色")
    private List<RoleWithDataSecurityBean> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDataObjectId() {
        return dataObjectId;
    }

    public void setDataObjectId(String dataObjectId) {
        this.dataObjectId = dataObjectId;
    }

    public String getDataSecurityId() {
        return dataSecurityId;
    }

    public void setDataSecurityId(String dataSecurityId) {
        this.dataSecurityId = dataSecurityId;
    }

    public List<DataSecurityItemBean> getDsItems() {
        if (dsItems == null) {
            dsItems = new ArrayList<DataSecurityItemBean>();
        }
        return dsItems;
    }

    public void setDsItems(List<DataSecurityItemBean> dsItems) {
        this.dsItems = dsItems;
    }

    public List<RoleWithDataSecurityBean> getChildren() {
        return children;
    }

    public void setChildren(List<RoleWithDataSecurityBean> children) {
        this.children = children;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

}
