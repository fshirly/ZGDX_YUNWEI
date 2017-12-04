/*
 * FbsSessionLogBeanMapper.java
 * Copyright(C) 2015-2020 飞搏软件公司
 * All rights reserved.
 *-------------------------------------------------
 * 2016-04-12 Created
 */ 
package com.fable.insightview.platform.modulelog.mapper;

import com.fable.insightview.platform.modulelog.entity.FbsSessionLogBean;

public interface FbsSessionLogBeanMapper {
    int deleteByPrimaryKey(String id);

    int insert(FbsSessionLogBean record);

    int insertSelective(FbsSessionLogBean record);

    FbsSessionLogBean selectByPrimaryKey(String id);
    
    int selectBySessionId(String sessionId);
    
    int updateByPrimaryKeySelective(FbsSessionLogBean record);

    int updateByPrimaryKey(FbsSessionLogBean record);
}