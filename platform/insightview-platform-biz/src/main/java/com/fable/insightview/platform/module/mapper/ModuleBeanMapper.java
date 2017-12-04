/*
 * ModuleBeanMapper.java
 * Copyright(C) 2015-2020 飞搏软件公司
 * All rights reserved.
 *-------------------------------------------------
 * 2015-10-19 Created
 */ 
package com.fable.insightview.platform.module.mapper;

import java.util.List;

import com.fable.insightview.platform.module.entity.ModuleBean;


public interface ModuleBeanMapper {
    int deleteByPrimaryKey(String id);

    int insert(ModuleBean record);

    int insertSelective(ModuleBean record);

    ModuleBean selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ModuleBean record);

    int updateByPrimaryKey(ModuleBean record);
    
    List<ModuleBean> query();
    
    /**
     * 查找所有子节点
     * @param id
     * @return
     */
    String[] queryChildren(String id);
    
    int batchDelete(String[] ids);
}