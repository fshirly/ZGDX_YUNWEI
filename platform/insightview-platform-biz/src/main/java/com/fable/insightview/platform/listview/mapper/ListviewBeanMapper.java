/*
 * ListviewBeanMapper.java
 * Copyright(C) 2015-2020 飞搏软件公司
 * All rights reserved.
 *-------------------------------------------------
 * 2015-10-28 Created
 */
package com.fable.insightview.platform.listview.mapper;

import java.util.HashMap;
import java.util.List;

import com.fable.insightview.platform.common.entity.TreeDictionaryBean;
import com.fable.insightview.platform.listview.entity.ListviewBean;

public interface ListviewBeanMapper {
	int deleteByPrimaryKey(String id);

	int insert(ListviewBean record);

	int insertSelective(ListviewBean record);

	ListviewBean selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(ListviewBean record);

	int updateByPrimaryKey(ListviewBean record);

	List<ListviewBean> selectListviewBeans(ListviewBean record);

	ListviewBean selectByName(String name);

	List<TreeDictionaryBean> selectTreeDictionaryByDynamicSql(String sql);

	List<HashMap<String, Object>> selectByDynamicSql(String sql);
	
	long selectCountByDynamicSql(String sql);
}