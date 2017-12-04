package com.fable.insightview.platform.tasklog.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.tasklog.entity.PfTaskLog;

public interface TaskLogMapper {

	List<PfTaskLog> selectTaskLogs(Page<PfTaskLog> page);

	int updateTaskLog(PfTaskLog taskLog);

	PfTaskLog selectTaskLogById(@Param("id") Integer id);

	/**
	 * 新增任务日志信息
	 * 
	 * @param tasklog
	 */
	void insert(PfTaskLog tasklog);

	/**
	 * 查询日期下的任务日志信息
	 * 
	 * @param taskTime
	 * @return
	 */
	List<PfTaskLog> queryTaskLogs(@Param("taskTime") Date taskTime);
}
