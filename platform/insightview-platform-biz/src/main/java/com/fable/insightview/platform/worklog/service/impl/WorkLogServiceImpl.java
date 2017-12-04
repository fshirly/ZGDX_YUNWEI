package com.fable.insightview.platform.worklog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.worklog.entity.WorkLog;
import com.fable.insightview.platform.worklog.mapper.WorkLogMapper;
import com.fable.insightview.platform.worklog.service.WorkLogService;

@Service
public class WorkLogServiceImpl implements WorkLogService {

	@Autowired
	private WorkLogMapper workLogMapper;
	
	@Override
	public boolean deleteWorkLogById(Integer id) {
		int count = workLogMapper.deleteWorkLogById(id);
		if(count > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean editWorkLog(WorkLog workLog) {
		int count = workLogMapper.updateWorkLog(workLog);
		if(count > 0) {
			return true;
		}
		return false;
	}

	@Override
	public void addWorkLog(WorkLog workLog) {
		workLogMapper.insertWorkLog(workLog);
	}

	@Override
	public WorkLog findWorkLogById(Integer id) {
		return workLogMapper.selectWorkLogById(id);
	}

	@Override
	public List<WorkLog> findWorkLogs(Page page) {
		return workLogMapper.selectWorkLogs(page);
	}

	@Override
	public List<WorkLog> findWorkLogsByUserId(Integer userId) {
		return workLogMapper.selectWorkLogsByUserId(userId);
	}

}
