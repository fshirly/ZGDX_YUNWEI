package com.fable.insightview.monitor.syslog.service;

import java.util.Map;

import com.fable.insightview.monitor.syslog.entity.SysSyslogTaskBean;
import com.fable.insightview.platform.page.Page;

public interface ISysSyslogTaskService {
	/**
	 * 分页查询syslog任务
	 */
	Map<String, Object> getSyslogTasks(Page<SysSyslogTaskBean> page);
	
	/**
	 * 分页查询syslog离线任务
	 */
	Map<String, Object> getSyslogOfflineTasks(Page<SysSyslogTaskBean> page);
	
	/**
	 * 根据任务ID获取操作进度及错误信息
	 * 
	 */
	Map<String, Object> getProcessByTaskId(String taskIds);
	
	/**
	 * 重发
	 */
	boolean resendTask(SysSyslogTaskBean bean);
	
	/**
	 * 删除
	 */
	boolean delTask(Integer taskID);
	
	/**
	 * 批量删除
	 */
	Map<String, Object> delTasks(String taskIDs);
	
	/**
	 * 初始化任务信息
	 */
	SysSyslogTaskBean initTaskDetail(Integer taskID);
	
	
	/**
	 * 获得已经下发的采集机
	 */
	Map<String, Object> getIssuedCollectors(SysSyslogTaskBean bean);
	
	/**
	 * 批量重发
	 */
	String doBatchResend(String taskIDs);
	
	/**
	 * 根据任务ID获取操作状态
	 * 
	 */
	Map<String, Object> getOperateStatus(String taskIds);
}
