/*
 * SystemLogBeanMapper.java
 * Copyright(C) 2015-2020 飞搏软件公司
 * All rights reserved.
 *-------------------------------------------------
 * 2015-10-19 Created
 */
package com.fable.insightview.platform.modulelog.mapper;

import java.util.List;
import java.util.Map;

import com.fable.insightview.platform.modulelog.entity.SystemLogBean;

public interface SystemLogBeanMapper {
    int deleteByPrimaryKey(String id);

    int insert(SystemLogBean record);

    int insertSelective(SystemLogBean record);

    SystemLogBean selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SystemLogBean record);

    int updateByPrimaryKey(SystemLogBean record);

    /**
     * 分页查询访问日志
     */
    List<SystemLogBean> queryAccessLogs(Map<String, Object> map);

    /**
     * 查询满足条件的记录数
     */
    int queryAccessLogCount(SystemLogBean accessLog);
}