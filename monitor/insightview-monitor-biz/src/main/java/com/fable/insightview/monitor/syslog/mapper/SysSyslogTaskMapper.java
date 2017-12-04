package com.fable.insightview.monitor.syslog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.monitor.syslog.entity.SysSyslogTaskBean;
import com.fable.insightview.platform.page.Page;

public interface SysSyslogTaskMapper {
	/**
	 * 分页查询syslog
	 */
	List<SysSyslogTaskBean> selectSyslogTasks(Page<SysSyslogTaskBean> page);

	/**
	 * 根据id获得syslog任务信息
	 */
	SysSyslogTaskBean getTaskInfoByTaskID(int taskID);
	
	/**
	 * 根据条件查询syslog信息
	 */
	List<SysSyslogTaskBean> getTaskByCondition(SysSyslogTaskBean bean);

	/**
	 * 新增
	 */
	int insertSyslogTask(SysSyslogTaskBean bean);
	
	/**
	 * 根据任务id更新
	 */
	int updateSyslogTaskByID(SysSyslogTaskBean bean);
	
	/**
	 * 根据任务ids批量更新
	 */
	int updateSyslogTaskByIDs(SysSyslogTaskBean bean);
	
	/**
	 * 根据任务id删除
	 */
	boolean delSyslogTaskByID(int taskID);
	
	/**
	 * 根据任务ids批量删除
	 */
	boolean delSyslogTaskByIDs(@Param("taskIDs") String taskIDs);
	
	/**
	 * 根据规则查询
	 */
	List<SysSyslogTaskBean> getCollectorByRuleID(SysSyslogTaskBean bean);
	
	/**
	 * 根据规则获得syslog任务信息
	 */
	List<SysSyslogTaskBean> getTaskInfoByRuleID(int ruleID);
	
	/**
	 * 删除已经删除的syslog任务
	 */
	boolean delDeledSyslogTask();
	
	/**
	 * 分页查询syslog离线任务
	 */
	List<SysSyslogTaskBean> selectOfflineSyslogTasks(Page<SysSyslogTaskBean> page);
	
	List<SysSyslogTaskBean> getOfflineTaskByHost(int collectorID);
}