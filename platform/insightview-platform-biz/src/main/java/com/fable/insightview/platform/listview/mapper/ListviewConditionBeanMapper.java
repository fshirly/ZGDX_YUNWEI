/*
 * ListviewConditionBeanMapper.java
 * Copyright(C) 2015-2020 飞搏软件公司
 * All rights reserved.
 *-------------------------------------------------
 * 2015-10-19 Created
 */ 
package com.fable.insightview.platform.listview.mapper;

import java.util.List;

import com.fable.insightview.platform.listview.entity.ListviewConditionBean;

public interface ListviewConditionBeanMapper {
    int deleteByPrimaryKey(String id);

    int insert(ListviewConditionBean record);

    int insertSelective(ListviewConditionBean record);

    ListviewConditionBean selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ListviewConditionBean record);

    int updateByPrimaryKey(ListviewConditionBean record);
    
    int deleteAllByListviewId(String listviewId);
    
    List<ListviewConditionBean> selectConditionByListviewId(String listviewId);
    
    int insertList(List<ListviewConditionBean> list);
}