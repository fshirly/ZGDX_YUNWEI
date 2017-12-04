/*
 * MethodBeanMapper.java
 * Copyright(C) 2015-2020 飞搏软件公司
 * All rights reserved.
 *-------------------------------------------------
 * 2015-10-19 Created
 */ 
package com.fable.insightview.platform.restSecurity.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.platform.restSecurity.entity.MethodBean;
import com.fable.insightview.platform.restSecurity.entity.ModuleRestDto;

public interface MethodBeanMapper {
    int deleteByPrimaryKey(String id);

    int insert(MethodBean record);

    int insertSelective(MethodBean record);

    MethodBean selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MethodBean record);

    int updateByPrimaryKey(MethodBean record);
    
    String[] queryRestByModuleId(String moduleId);
    
    /**
     * @return
     */
    List<ModuleRestDto> queryModuleRest();
    
    /**
     * 根据模块ID批量删除
     * @param map {moduleId 模块ID, restName Rest列表}
     * @return
     */
    int batchDeleteByModuleId(Map<String, Object> map);
    
    /**
     * 根据请求方法获取模块名称
     * @param url 请求URL
     * @param methodName 方法名
     * @return
     */
    String queryModuleNameByMethod(@Param("url") String url, @Param("methodName") String methodName);
    
    /**
     * 根据请求方法获取模块id
     * @param url 请求URL
     * @param methodName 方法名
     * @return
     */
    String queryModuleIdByMethod(@Param("url") String url, @Param("methodName") String methodName);
}