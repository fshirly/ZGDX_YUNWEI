package com.fable.insightview.platform.worklog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.worklog.entity.WorkLog;

public interface WorkLogMapper {
	
	List<WorkLog> selectWorkLogs(Page<WorkLog> page);

	void insertWorkLog(WorkLog workLog);
	
	int deleteWorkLogById(@Param("id")Integer id);
	
	int updateWorkLog(WorkLog workLog);
	
	WorkLog selectWorkLogById(@Param("id")Integer id);
	
	List<WorkLog> selectWorkLogsByUserId(@Param("userId")Integer userId);
}
