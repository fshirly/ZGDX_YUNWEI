package com.fable.insightview.platform.tasklog.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.tasklog.entity.PfTaskLog;
import com.fable.insightview.platform.tasklog.mapper.PfTaskLogUsersMapper;
import com.fable.insightview.platform.tasklog.mapper.TaskLogMapper;
import com.fable.insightview.platform.tasklog.service.TaskLogService;

@Service
public class TaskLogServiceImpl implements TaskLogService {

	@Autowired
	private TaskLogMapper taskLogMapper;

	@Autowired
	private PfTaskLogUsersMapper userMapper;

	@Override
	public List<PfTaskLog> findTaskLogs(Page<PfTaskLog> page) {
		return taskLogMapper.selectTaskLogs(page);
	}

	@Override
	public boolean editTaskLog(PfTaskLog taskLog) {
		int count = taskLogMapper.updateTaskLog(taskLog);
		if (count > 0) {
			return true;
		}
		return false;
	}

	@Override
	public PfTaskLog findTaskLogById(Integer id) {
		return taskLogMapper.selectTaskLogById(id);
	}

	@Override
	public void insert(PfTaskLog tasklog) {
		taskLogMapper.insert(tasklog);
	}

	@Override
	public void generaterTaskLogs(Date taskTime) {
		List<Integer> userIds = userMapper.queryUserIds();/* 查询日志配置人员 */
		if (null == userIds || userIds.isEmpty()) {
			return;
		}
		if (null == taskTime) {
			taskTime = new Date();
		}
		List<PfTaskLog> taskLogs = this.queryTaskLogs(taskTime);/* 查询任务日志信息 */
		if (null != taskLogs && taskLogs.size() > 0) {
			return;
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		for (Integer userId : userIds) {/* 生成任务日志信息 */
			this.insert(new PfTaskLog(df.format(taskTime) + "个人工作日志", taskTime, 1, 1, userId));
		}
	}

	@Override
	public List<PfTaskLog> queryTaskLogs(Date taskTime) {
		return taskLogMapper.queryTaskLogs(taskTime);
	}

	@Override
	public void manual(Date taskTime) {
		this.generaterTaskLogs(taskTime);
	}

}
