package com.fable.insightview.platform.dutymanager.dutylog.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.platform.dutymanager.dutylog.entity.PfDutyLog;

public interface DutyLogMapper {

	/**
	 * 新增值班记录日志信息
	 * @param dutyLog
	 */
	void insert (PfDutyLog dutyLog);
	
	/**
	 * 查询值班记录的日志信息
	 * @param dutyIds
	 * @return
	 */
	List<Map<String, Object>> queryLogs (List<Integer> dutyIds);
	
	/**
	 * 查询值班日志信息
	 * @param duty
	 * @return
	 */
	Map<String, Object>	queryLog (@Param("duty")Date duty);
	
	/**
	 * 更新带班领导的日志信息
	 * @param dutyLog
	 */
	void updateLeaderLog (PfDutyLog dutyLog) ;
	
	/**
	 * 更新值班负责人的日志信息
	 * @param dutyLog
	 */
	void updateDuyterLog (PfDutyLog dutyLog) ;
	
	/**
	 * 更新值班带班领导的交办事项
	 * @param dutyId
	 * @param notices
	 */
	void updateLeadNotice(@Param("dutyId") String dutyId, @Param("notices") String notices);
	
	/**
	 * 更新值班人的值班日志
	 * @param logInfo
	 */
	void updateDutyLogs(Map<String, Object> logInfo);
	
	/**
	 * 查询值班日志个数
	 * @param dutyId
	 * @return
	 */
	List<Map<String, Object>> queryDutyLogs(@Param("dutyId")String dutyId);
}
