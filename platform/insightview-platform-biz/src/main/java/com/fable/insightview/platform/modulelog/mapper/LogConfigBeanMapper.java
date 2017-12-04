package com.fable.insightview.platform.modulelog.mapper;

import java.util.List;

import com.fable.insightview.platform.modulelog.entity.LogConfigBean;


public interface LogConfigBeanMapper {

    /**
     * 查询系统日志配置
     */
    List<LogConfigBean> queryLogConfigs();

    /**
     * 查询启动状态的系统日志配置
     */
    // List<LogConfigBean> queryStartedLogConfigs();

    /**
     * 插入系统日志配置
     */
    void insertLogConfigs(List<LogConfigBean> logConfigs);

    /**
     * 更新系统日志配置
     */
    void updateLogConfigs(List<LogConfigBean> logConfigs);
    
    /**
     * 更新某个系统日志配置
     */
    void updateLogConfig(LogConfigBean logConfig);

}
