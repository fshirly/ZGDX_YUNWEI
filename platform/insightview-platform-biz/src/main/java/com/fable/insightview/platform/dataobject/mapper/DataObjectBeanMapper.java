/*
 * DataObjectBeanMapper.java
 * Copyright(C) 2015-2020 飞搏软件公司
 * All rights reserved.
 *-------------------------------------------------
 * 2015-10-27 Created
 */
package com.fable.insightview.platform.dataobject.mapper;

import java.util.List;

import com.fable.insightview.platform.dataobject.entity.DataObjectBean;

public interface DataObjectBeanMapper {
    DataObjectBean selectByPrimaryKey(String id);

    DataObjectBean selectByName(String name);

    List<DataObjectBean> selectDataObjects(String sysId);

    /**
     * 批量删除数据对象
     */
    void deleteDataObjects(String[] ids);

    /**
     * 创建数据对象
     */
    void createDataObject(DataObjectBean dataObject);

    /**
     * 更新数据对象
     */
    void updateDataObject(DataObjectBean dataObject);

    /**
     * 使用存储过程更新所有数据对象
     */
    void updateDataObjectsWithProcdure();
}