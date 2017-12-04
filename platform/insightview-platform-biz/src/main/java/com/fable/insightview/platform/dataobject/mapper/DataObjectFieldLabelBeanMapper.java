/*
 * DataObjectFieldLabelBeanMapper.java
 * Copyright(C) 2015-2020 飞搏软件公司
 * All rights reserved.
 *-------------------------------------------------
 * 2015-10-27 Created
 */
package com.fable.insightview.platform.dataobject.mapper;

import java.util.List;

import com.fable.insightview.platform.dataobject.entity.DataObjectFieldLabelBean;

public interface DataObjectFieldLabelBeanMapper {
    List<DataObjectFieldLabelBean> selectListByDataObjectId(String dataObjectId);

    /**
     * 查询出指定数据对象下的字段列表
     */
    // List<DataObjectFieldLabelBean> queryFieldLabelsByDataObjectId(String dataObjectId);
}