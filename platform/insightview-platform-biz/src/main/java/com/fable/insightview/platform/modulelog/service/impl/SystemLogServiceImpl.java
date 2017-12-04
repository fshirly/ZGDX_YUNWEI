package com.fable.insightview.platform.modulelog.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fable.insightview.platform.cache.DataCache;
import com.fable.insightview.platform.cache.LogConfigCacheEntry;
import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.common.util.Cast;
import com.fable.insightview.platform.common.util.StringUtil;
import com.fable.insightview.platform.common.util.Tool;
import com.fable.insightview.platform.core.exception.BusinessException;
import com.fable.insightview.platform.modulelog.entity.LogConfigBean;
import com.fable.insightview.platform.modulelog.entity.SystemLogBean;
import com.fable.insightview.platform.modulelog.mapper.LogConfigBeanMapper;
import com.fable.insightview.platform.modulelog.mapper.SystemLogBeanMapper;
import com.fable.insightview.platform.modulelog.service.SystemLogService;
import com.fable.insightview.platform.restSecurity.mapper.MethodBeanMapper;

/**
 * 系统日志管理Service实现类
 * 
 * @author nimj
 */
@Service
public class SystemLogServiceImpl implements SystemLogService {
    private static final Logger logger = Logger.getLogger(SystemLogServiceImpl.class);

    @Autowired
    private SystemLogBeanMapper systemLogBeanMapper;

//    @Autowired
//    private OperationLogBeanMapper operationLogBeanMapper;
//
//    @Autowired
//    private ExceptionLogBeanMapper exceptionLogBeanMapper;

    @Autowired
    private LogConfigBeanMapper logConfigBeanMapper;

    @Autowired
    private MethodBeanMapper methodBeanMapper;

//    @Autowired
//    private RequestUtil requestUtil;
//
//    @Autowired
//    private TaskUtil taskUtil;

//    @Override
//    public List<SystemLogBean> queryAccessLogs(AccessLogBeanDto accessLog) {
//        int pageNum = accessLog.getPageNum();
//        int pageSize = accessLog.getPageSize();
//        pageNum = pageNum <= 0 ? 1 : pageNum;
//        pageSize = pageSize <= 0 ? 10 : pageSize;
//        int startIndex = (pageNum - 1) * pageSize + 1;
//        int endIndex = pageNum * pageSize;
//        Map<String, Object> map = new HashMap<String, Object>(8);
//        putAccessLogMap(accessLog, map);
//        map.put("startIndex", startIndex);
//        map.put("endIndex", endIndex);
//        return systemLogBeanMapper.queryAccessLogs(map);
//    }
//
//    private void putAccessLogMap(AccessLogBeanDto accessLog, Map<String, Object> map) {
//        if (accessLog != null && map != null) {
//            map.put("username", accessLog.getUsername());
//            map.put("ip", accessLog.getIp());
//            map.put("url", accessLog.getUrl());
//            map.put("methodName", accessLog.getMethodName());
//            map.put("startTime", accessLog.getStartTime());
//            map.put("endTime", accessLog.getEndTime());
//        }
//    }
//
//    @Override
//    public int queryAccessLogCount(SystemLogBean accessLog) {
//        return systemLogBeanMapper.queryAccessLogCount(accessLog);
//    }

//    public void exportAccessLogsExcel(AccessLogBeanDto accessLog, HttpServletResponse response) {
//        // 最多允许导出x条数数据
//        int maxRecords = 0;
//        try {
//            maxRecords = Integer.valueOf(SystemPropertyHandler.getProperty("fbs.exportExcel.maxRows"));
//        } catch (Exception e) {
//            logger.error("读取系统配置参数失败，参数名：fbs.exportExcel.maxRows！", e);
//            maxRecords = 100000;// 若取不到值则默认最多允许导出10万条
//        }
//        Map<String, Object> map = new HashMap<String, Object>(2);
//        putAccessLogMap(accessLog, map);
//        map.put("startIndex", 0);
//        map.put("endIndex", maxRecords);
//        List<SystemLogBean> accessLogs = systemLogBeanMapper.queryAccessLogs(map);
//        if (accessLogs == null) {
//            accessLogs = new ArrayList<SystemLogBean>(0);
//        }
//        String[] titles = { "用户ID", "登录用户", "客户端IP", "页面名称", "请求URL", "方法名", "访问时间", "客户端名称", "客户端代理" };
//        List<Object[]> dataAryList = new ArrayList<Object[]>(accessLogs.size());
//        Object[] objAry = null;
//        int i = 0;
//        for (SystemLogBean log : accessLogs) {
//            objAry = new Object[9];
//            i = 0;
//            objAry[i++] = log.getUserId();
//            objAry[i++] = log.getUsername();
//            objAry[i++] = log.getIp();
//            objAry[i++] = log.getPageName();
//            objAry[i++] = log.getUrl();
//            objAry[i++] = log.getMethodName();
//            objAry[i++] = log.getCreatedTime();
//            objAry[i++] = log.getClientName();
//            objAry[i++] = log.getClientAgent();
//            dataAryList.add(objAry);
//        }
//        try {
//            FileUtil.exportExcel(titles, dataAryList, "系统访问日志.xls", response);
//        } catch (Exception e) {
//            throw new BusinessException("00000013");
//        }
//    }

//    @Override
//    public int queryOperationLogCount(OperationLogBeanDto operationLogDto) {
//        addQuotes(operationLogDto);
//        return operationLogBeanMapper.queryOperationLogCount(operationLogDto);
//    }

//    /**
//     * 给orgCode和moduleName加引号（方便数据库操作）
//     */
//    private void addQuotes(OperationLogBeanDto operationLogDto) {
//        if (operationLogDto == null) {
//            return;
//        }
//        String orgCode = operationLogDto.getOrgCode();
//        if (StringUtils.isNotEmpty(orgCode)) {
//            StringBuilder newOrgCode = new StringBuilder();
//            String[] orgCodes = orgCode.split(",");
//            for (int i = 0, len = orgCodes.length; i < len; i++) {
//                if (i > 0) {
//                    newOrgCode.append(",");
//                }
//                newOrgCode.append("'").append(orgCodes[i]).append("'");
//            }
//            operationLogDto.setOrgCode(newOrgCode.toString());
//        }
//        String moduleName = operationLogDto.getModulName();
//        if (StringUtils.isNotEmpty(moduleName)) {
//            StringBuilder newModuleName = new StringBuilder();
//            String[] moduleNames = moduleName.split(",");
//            for (int i = 0, len = moduleNames.length; i < len; i++) {
//                if (i > 0) {
//                    newModuleName.append(",");
//                }
//                newModuleName.append("'").append(moduleNames[i]).append("'");
//            }
//            operationLogDto.setModulName(newModuleName.toString());
//        }
//    }

//    @Override
//    public List<OperationLogBean> queryOperationLogs(OperationLogBeanDto operationLogDto) {
//        int pageNum = operationLogDto.getPageNum();
//        int pageSize = operationLogDto.getPageSize();
//        pageNum = pageNum <= 0 ? 1 : pageNum;
//        pageSize = pageSize <= 0 ? 10 : pageSize;
//        int startIndex = (pageNum - 1) * pageSize + 1;
//        int endIndex = pageNum * pageSize;
//        Map<String, Object> map = new HashMap<String, Object>(8);
//        putOperationLogMap(operationLogDto, map);
//        map.put("startIndex", startIndex);
//        map.put("endIndex", endIndex);
//        return operationLogBeanMapper.queryOperationLogs(map);
//    }

//    private void putOperationLogMap(OperationLogBeanDto operationLog, Map<String, Object> map) {
//        if (operationLog != null && map != null) {
//            map.put("operateType", operationLog.getOperateType());
//            map.put("username", operationLog.getUsername());
//            map.put("orgCode", operationLog.getOrgCode());
//            map.put("modulName", operationLog.getModulName());
//            map.put("startTime", operationLog.getStartTime());
//            map.put("endTime", operationLog.getEndTime());
//        }
//    }

//    @Override
//    public void exportOperationLogsExcel(OperationLogBeanDto operationLog, HttpServletResponse response) {
//        // 最多允许导出x条数数据
//        int maxRecords = 0;
//        try {
//            maxRecords = Integer.valueOf(SystemPropertyHandler.getProperty("fbs.exportExcel.maxRows"));
//        } catch (Exception e) {
//            logger.error("读取系统配置参数失败，参数名：fbs.exportExcel.maxRows！", e);
//            maxRecords = 100000;// 若取不到值则默认最多允许导出10万条
//        }
//        Map<String, Object> map = new HashMap<String, Object>(8);
//        addQuotes(operationLog);
//        putOperationLogMap(operationLog, map);
//        map.put("startIndex", 0);
//        map.put("endIndex", maxRecords);
//        List<OperationLogBean> operationLogs = operationLogBeanMapper.queryOperationLogs(map);
//        if (operationLogs == null) {
//            operationLogs = new ArrayList<OperationLogBean>(0);
//        }
//        String[] titles = { "终端标识", "所属模块", "机构名称", "操作用户", "操作类型", "操作结果", "SQL", "操作时间" };
//        List<Object[]> dataAryList = new ArrayList<Object[]>(operationLogs.size());
//        Object[] objAry = null;
//        int i = 0;
//        for (OperationLogBean log : operationLogs) {
//            objAry = new Object[8];
//            i = 0;
//            objAry[i++] = log.getTerminalId();
//            objAry[i++] = log.getModulName();
//            objAry[i++] = log.getOrgName();
//            objAry[i++] = log.getUsername();
//            objAry[i++] = OperationType.getTextByCode(log.getOperateType());
//            objAry[i++] = OperationResult.getTextByCode(log.getOperateResult());
//            objAry[i++] = log.getOperateCondition();
//            objAry[i++] = log.getOperateTime();
//            dataAryList.add(objAry);
//        }
//        try {
//            FileUtil.exportExcel(titles, dataAryList, "系统操作日志.xls", response);
//        } catch (Exception e) {
//            throw new BusinessException("00000013");
//        }
//    }

//    @Override
//    public int queryExceptionLogCount(ExceptionLogBeanDto exceptionLogDto) {
//        addQuotes(exceptionLogDto);
//        return exceptionLogBeanMapper.queryExceptionLogCount(exceptionLogDto);
//    }

//    private void addQuotes(ExceptionLogBeanDto exceptionLogDto) {
//        if (exceptionLogDto == null) {
//            return;
//        }
//        String moduleName = exceptionLogDto.getModulName();
//        if (StringUtils.isNotEmpty(moduleName)) {
//            StringBuilder newModuleName = new StringBuilder();
//            String[] moduleNames = moduleName.split(",");
//            for (int i = 0, len = moduleNames.length; i < len; i++) {
//                if (i > 0) {
//                    newModuleName.append(",");
//                }
//                newModuleName.append("'").append(moduleNames[i]).append("'");
//            }
//            exceptionLogDto.setModulName(newModuleName.toString());
//        }
//    }

//    @Override
//    public List<ExceptionLogBean> queryExceptionLogs(ExceptionLogBeanDto exceptionLogDto) {
//        int pageNum = exceptionLogDto.getPageNum();
//        int pageSize = exceptionLogDto.getPageSize();
//        pageNum = pageNum <= 0 ? 1 : pageNum;
//        pageSize = pageSize <= 0 ? 10 : pageSize;
//        int startIndex = (pageNum - 1) * pageSize + 1;
//        int endIndex = pageNum * pageSize;
//        Map<String, Object> map = new HashMap<String, Object>(8);
//        putExceptionLogMap(exceptionLogDto, map);
//        map.put("startIndex", startIndex);
//        map.put("endIndex", endIndex);
//        return exceptionLogBeanMapper.queryExceptionLogs(map);
//    }

//    private void putExceptionLogMap(ExceptionLogBeanDto exceptionLogDto, Map<String, Object> map) {
//        if (exceptionLogDto != null && map != null) {
//            map.put("username", exceptionLogDto.getUsername());
//            map.put("modulName", exceptionLogDto.getModulName());
//            map.put("exceptionCode", exceptionLogDto.getExceptionCode());
//            map.put("exceptionMsg", exceptionLogDto.getExceptionMsg());
//            map.put("startTime", exceptionLogDto.getStartTime());
//            map.put("endTime", exceptionLogDto.getEndTime());
//        }
//    }

//    @Override
//    public void exportExceptionLogsExcel(ExceptionLogBeanDto exceptionLogDto, HttpServletResponse response) {
//        // 最多允许导出x条数数据
//        int maxRecords = 0;
//        try {
//            maxRecords = Integer.valueOf(SystemPropertyHandler.getProperty("fbs.exportExcel.maxRows"));
//        } catch (Exception e) {
//            logger.error("读取系统配置参数失败，参数名：fbs.exportExcel.maxRows！", e);
//            maxRecords = 100000;// 若取不到值则默认最多允许导出10万条
//        }
//        Map<String, Object> map = new HashMap<String, Object>(8);
//        addQuotes(exceptionLogDto);
//        putExceptionLogMap(exceptionLogDto, map);
//        map.put("startIndex", 0);
//        map.put("endIndex", maxRecords);
//        List<ExceptionLogBean> exceptionLogs = exceptionLogBeanMapper.queryExceptionLogs(map);
//        if (exceptionLogs == null) {
//            exceptionLogs = new ArrayList<ExceptionLogBean>(0);
//        }
//        String[] titles = { "模块名称", "登录用户", "异常代码", "异常消息", "堆栈信息", "发生时间" };
//        List<Object[]> dataAryList = new ArrayList<Object[]>(exceptionLogs.size());
//        Object[] objAry = null;
//        int i = 0;
//        for (ExceptionLogBean log : exceptionLogs) {
//            objAry = new Object[6];
//            i = 0;
//            objAry[i++] = log.getModulName();
//            objAry[i++] = log.getUsername();
//            objAry[i++] = log.getExceptionCode();
//            objAry[i++] = log.getExceptionMsg();
//            objAry[i++] = log.getExceptionStackInfo();
//            objAry[i++] = log.getCreatedTime();
//            dataAryList.add(objAry);
//        }
//        try {
//            FileUtil.exportExcel(titles, dataAryList, "系统异常日志.xls", response);
//        } catch (Exception e) {
//            throw new BusinessException("00000013");
//        }
//    }

    @Override
    public List<LogConfigBean> queryLogConfigs() {
        return logConfigBeanMapper.queryLogConfigs();
    }

//    public void saveLogConfigs(List<LogConfigBean> logConfigs) {
//        if (CollectionUtils.isEmpty(logConfigs)) {
//            throw new BusinessException("00000014");
//        }
//        
//        List<LogConfigBean> insertCfgs = new ArrayList<LogConfigBean>();
//        List<LogConfigBean> updateCfgs = new ArrayList<LogConfigBean>();
//        for (LogConfigBean cfg : logConfigs) {
//            if (StringUtils.isEmpty(cfg.getId())) {
//                cfg.setId(Cast.guid2Str(Tool.newGuid()));
//                insertCfgs.add(cfg);
//            } else {
//                updateCfgs.add(cfg);
//            }
//        }
//
//        if (!CollectionUtils.isEmpty(insertCfgs)) {
//            logConfigBeanMapper.insertLogConfigs(insertCfgs);
//        }
//        if (!CollectionUtils.isEmpty(updateCfgs)) {
//            logConfigBeanMapper.updateLogConfigs(updateCfgs);
//        }
//
//        // 更新缓存
//        String key = LogConfigCacheEntry.getCacheKey("defaultSysIdKey");//requestUtil.getSysId()
//        DataCache.getInstance().removeCache(key);
//        LogConfigCacheEntry logConfigCacheEntry = new LogConfigCacheEntry(logConfigs);
//        DataCache.getInstance().addCache(key, logConfigCacheEntry);
//    }
    
    public void saveLogConfigs(List<LogConfigBean> logConfigs) {
        if (CollectionUtils.isEmpty(logConfigs)) {
            throw new BusinessException("00000014");
        }
        
        LogConfigBean config = null;
        for (LogConfigBean cfg : logConfigs) {
        	if(StringUtil.equals("1", StringUtil.trimToEmpty(cfg.getType()))){
        		config = cfg;
        		break;
        	}
        }
        
        SecurityUserInfoBean user = ((SecurityUserInfoBean) ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession().getAttribute("sysUserInfoBeanOfSession"));
        
        if(config != null){
        	config.setUpdateId(user.getId().toString());
        	config.setUpdateName(user.getUserName());
        	logConfigBeanMapper.updateLogConfig(config);
        }

        // 更新缓存
        String key = LogConfigCacheEntry.getCacheKey("defaultSysIdKey");//requestUtil.getSysId()
        DataCache.getInstance().removeCache(key);
        LogConfigCacheEntry logConfigCacheEntry = new LogConfigCacheEntry(logConfigs);
        DataCache.getInstance().addCache(key, logConfigCacheEntry);
    }

    // @Override
    // public List<LogConfigBean> queryStartedLogConfigs() {
    // return logConfigBeanMapper.queryStartedLogConfigs();
    // }

    @Override
    public String getModuleNameByMethod(String url, String methodName) throws Exception {
        if (url == null || methodName == null)
            return "";
        return methodBeanMapper.queryModuleNameByMethod(url, methodName);
    }
    
    @Override
    public String getModuleIdByMethod(String url, String methodName) throws Exception {
        if (url == null || methodName == null)
            return "";
        return methodBeanMapper.queryModuleIdByMethod(url, methodName);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void insertAccessLogService(SystemLogBean obj) {
        //requestUtil.boToUserbo(obj);
        obj.setId(Cast.guid2Str(Tool.newGuid()));
        systemLogBeanMapper.insert(obj);
    }

//    @Transactional(propagation = Propagation.NOT_SUPPORTED)
//    public void insertOperationLogService(OperationLogBean obj) {
//        requestUtil.boToUserbo(obj);
//        obj.setId(Cast.guid2Str(Tool.newGuid()));
//        operationLogBeanMapper.insert(obj);
//    }
//
//    @Transactional(propagation = Propagation.NOT_SUPPORTED)
//    public void insertExceptionLogService(ExceptionLogBean obj) {
//        requestUtil.boToUserbo(obj);
//        obj.setId(Cast.guid2Str(Tool.newGuid()));
//        exceptionLogBeanMapper.insert(obj);
//    }

    @Override
    public boolean isLoggerEnable(String type) throws Exception {
        String key = LogConfigCacheEntry.getCacheKey("defaultSysIdKey");
        LogConfigCacheEntry logConfigCacheEntry = (LogConfigCacheEntry) DataCache.getInstance().getCache(key);
        if (logConfigCacheEntry == null) {
            List<LogConfigBean> configs = queryLogConfigs();
            logConfigCacheEntry = new LogConfigCacheEntry(configs);
            DataCache.getInstance().addCache(key, logConfigCacheEntry);
        }

        LogConfigBean config = logConfigCacheEntry.getLogConfig(type);

        // 没有配置或者不启用时返回false
        if (config == null || "0".equals(config.getIsStart())) {
            return false;
        }

        // 时间配置（cron表达式）
        String expression = config.getTimeCfg();
        if (!CronExpression.isValidExpression(expression)) {
            return false;
        }

        // 要求表达式的秒为*
        CronExpression cronExpression = new CronExpression(expression);
        Date now = new Date();
        if (!cronExpression.isSatisfiedBy(now)) {
            return false;
        }

        return true;
    }

//    public String getExceptionStackInfo(String id) {
//        if (StringUtil.isEmpty(id)) {
//            return null;
//        }
//        return exceptionLogBeanMapper.selectExceptionStackInfo(id);
//    }
}
