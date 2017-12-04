/*
 * DataSecurityBeanMapper.java
 * Copyright(C) 2015-2020 飞搏软件公司
 * All rights reserved.
 *-------------------------------------------------
 * 2015-10-27 Created
 */
package com.fable.insightview.platform.dataobject.mapper;

import java.util.List;
import java.util.Map;

import com.fable.insightview.platform.dataobject.entity.DataObjectBean;
import com.fable.insightview.platform.dataobject.entity.RoleWithDataSecurityBean;

public interface DataSecurityBeanMapper {
    /**
     * 查询所有的RoleWithDataSecurityBean对象（每个该对象由一个RoleBean对象、一个DataSecurityBean及多个DataSecurityItemBean拼装而成）
     */
    List<RoleWithDataSecurityBean> queryRoleTree(DataObjectBean dataObject);

    /**
     * 批量更新数据对象的权限
     */
    void updateDataSecuritys(Map<String, Object> dataSecurityMap);
}