package com.fable.insightview.platform.tasklog.service;

import java.util.Date;
import java.util.List;

import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.tasklog.entity.PfTaskLog;

public interface TaskLogService {

	List<PfTaskLog> findTaskLogs(Page<PfTaskLog> page);

	boolean editTaskLog(PfTaskLog taskLog);

	PfTaskLog findTaskLogById(Integer id);

	/**
	 * 新增任务日志信息
	 * 
	 * @param tasklog
	 */
	void insert(PfTaskLog tasklog);

	/**
	 * 生成系统中的所有任务日志信息
	 */
	void generaterTaskLogs(Date taskTime);

	/**
	 * 查询日期下的任务日志信息
	 * 
	 * @param taskTime
	 * @return
	 */
	List<PfTaskLog> queryTaskLogs(Date taskTime);

	/**
	 * 手动生成任务日志信息
	 * 
	 * @param taskTime
	 */
	void manual(Date taskTime);
}
