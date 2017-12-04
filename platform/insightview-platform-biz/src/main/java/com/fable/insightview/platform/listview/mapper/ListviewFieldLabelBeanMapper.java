/*
 * ListviewFieldLabelBeanMapper.java
 * Copyright(C) 2015-2020 飞搏软件公司
 * All rights reserved.
 *-------------------------------------------------
 * 2015-10-19 Created
 */ 
package com.fable.insightview.platform.listview.mapper;

import java.util.List;

import com.fable.insightview.platform.listview.entity.ListviewFieldLabelBean;

public interface ListviewFieldLabelBeanMapper {
    int deleteByPrimaryKey(String id);

    int insert(ListviewFieldLabelBean record);

    int insertSelective(ListviewFieldLabelBean record);

    ListviewFieldLabelBean selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ListviewFieldLabelBean record);

    int updateByPrimaryKey(ListviewFieldLabelBean record);
    
    int deleteAllByListviewId(String listviewId);
    
    List<ListviewFieldLabelBean> selectFieldLabelByListviewId(String listviewId);
    
    int insertList(List<ListviewFieldLabelBean> list);
}