package com.fable.insightview.platform.dutymanager.dutylog.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fable.insightview.platform.dutymanager.dutylog.entity.PfDutyLog;

public interface IDutyLogService {

	/**
	 * 查询值班记录及日志信息
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> queryDutyLogs (Map<String, Object> params);
	
	/**
	 * 查询值班记录日志信息
	 * @param duty
	 * @return
	 */
	Map<String, Object>	queryLog(Date duty);
	
	/**
	 * 查询值班带班领导日志信息
	 * @param params
	 * @return
	 */
	Map<String, Object> queryLeaderInfo (Map<String, Object> params) throws Exception;
	
	/**
	 * 查询值班负责人日志信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> queryDutyerInfo (Map<String, Object> params) throws Exception;
	
	/**
	 * 新增值班信息
	 * @param log
	 */
	void insert(PfDutyLog log);
	
	/**
	 * 更新带班领导值班日志
	 * @param log
	 */
	void updateLeaderLog (PfDutyLog log);
	
	/**
	 * 更新值班负责人值班日志
	 * @param log
	 */
	void updateDutyerLog (PfDutyLog log);
	
	/**
	 * 处理值班记录日志信息
	 * @param log
	 */
	void handle (PfDutyLog log);
	
	/**
	 * 更新领导带班交办事项
	 * @param dutyId
	 * @param notices
	 */
	void updateLeadNotice(String dutyId, String notices);
	
	/**
	 * 更新值班人值班日志信息
	 * @param logInfo
	 */
	void updateDutyLogs(Map<String, Object> logInfo);
	
}
