package com.fable.insightview.platform.worklog.service;

import java.util.List;

import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.worklog.entity.WorkLog;

public interface WorkLogService {
	
	List<WorkLog> findWorkLogs(Page page);
	
	boolean deleteWorkLogById(Integer id);
	
	boolean editWorkLog(WorkLog workLog);
	
	void addWorkLog(WorkLog workLog);
	
	WorkLog findWorkLogById(Integer id);
	
	List<WorkLog> findWorkLogsByUserId(Integer userId);
}
