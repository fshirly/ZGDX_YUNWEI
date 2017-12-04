/*
 * DataSecurityItemBeanMapper.java
 * Copyright(C) 2015-2020 飞搏软件公司
 * All rights reserved.
 *-------------------------------------------------
 * 2015-10-27 Created
 */
package com.fable.insightview.platform.dataobject.mapper;

import java.util.List;
import java.util.Map;

import com.fable.insightview.platform.dataobject.entity.DataSecurityItemBean;

public interface DataSecurityItemBeanMapper {

    List<DataSecurityItemBean> selectItemsByDataObjectId(Map<String, String> params);

    /**
     * 查询指定数据权限ID下的权限条件
     */
    List<DataSecurityItemBean> queryDataSecurityItems(List<String> dataSecurityIds);
}