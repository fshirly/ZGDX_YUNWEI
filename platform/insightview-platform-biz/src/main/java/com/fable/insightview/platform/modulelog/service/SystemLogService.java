package com.fable.insightview.platform.modulelog.service;

import java.util.List;

import com.fable.insightview.platform.modulelog.entity.LogConfigBean;
import com.fable.insightview.platform.modulelog.entity.SystemLogBean;

/**
 * 系统日志管理Service接口
 * 
 * @author nimj
 */
public interface SystemLogService {

//    /**
//     * 分页查询访问日志信息
//     */
//    List<SystemLogBean> queryAccessLogs(AccessLogBeanDto accessLog);
//
//    /**
//     * 查询满足条件的记录数
//     */
//    int queryAccessLogCount(SystemLogBean accessLog);
//
//    /**
//     * 根据查询条件将访问日志结果集导出为Excel
//     */
//    void exportAccessLogsExcel(AccessLogBeanDto accessLog, HttpServletResponse response);
//
//    /**
//     * 查询满足条件的操作日志数
//     */
//    int queryOperationLogCount(OperationLogBeanDto operationLogDto);
//
//    /**
//     * 分页查询操作日志信息
//     */
//    List<OperationLogBean> queryOperationLogs(OperationLogBeanDto operationLogDto);
//
//    /**
//     * 根据查询条件将操作日志结果集导出为Excel
//     */
//    void exportOperationLogsExcel(OperationLogBeanDto operationLogDto, HttpServletResponse response);
//
//    /**
//     * 查询满足条件的异常日志数
//     */
//    int queryExceptionLogCount(ExceptionLogBeanDto exceptionLogDto);
//
//    /**
//     * 分页查询异常日志信息
//     */
//    List<ExceptionLogBean> queryExceptionLogs(ExceptionLogBeanDto exceptionLogDto);
//
//    /**
//     * 根据查询条件将异常日志结果集导出为Excel
//     */
//    void exportExceptionLogsExcel(ExceptionLogBeanDto exceptionLogDto, HttpServletResponse response);
//
    /**
     * 查询系统日志配置
     */
    List<LogConfigBean> queryLogConfigs();
//
//    /**
//     * 查询启动状态的系统日志配置
//     */
//    // List<LogConfigBean> queryStartedLogConfigs();
//
    /**
     * 保存系统日志配置
     */
    void saveLogConfigs(List<LogConfigBean> logConfigs);
    
    /**
     * 根据请求方法获取模块名称
     * @param url 请求URL
     * @param methodName 方法名
     * @return
     */
    public String getModuleNameByMethod(String url, String methodName) throws Exception;
    
    /**
     * 根据请求方法获取模块id
     * @param url 请求URL
     * @param methodName 方法名
     * @return
     */
    public String getModuleIdByMethod(String url, String methodName) throws Exception;

    /**
     * 插入系统访问日志
     * @param obj 系统访问日志对象
     * @throws Exception
     */
    public void insertAccessLogService(SystemLogBean obj) throws Exception;
    
//    /**
//     * 插入系统操作日志
//     * @param obj 系统操作日志对象
//     * @throws Exception
//     */
//    public void insertOperationLogService(OperationLogBean obj) throws Exception;
//    
//    /**
//     * 插入系统异常日志
//     * @param obj 系统异常日志对象
//     * @throws Exception
//     */
//    public void insertExceptionLogService(ExceptionLogBean obj) throws Exception;
//    
    /**
     * 是否允许记录日志
     * @param type 日志类型(1:系统URL日志，2:SQL操作日志，3:异常日志)
     * @return
     * @throws Exception
     */
    public boolean isLoggerEnable(String type) throws Exception;
//
//	/**
//	 * 根据ID获取异常堆栈信息
//	 */
//	String getExceptionStackInfo(String id);
}
