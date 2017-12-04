package com.fable.insightview.monitor.alarmmgr.traptask.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.monitor.alarmmgr.entity.SysTrapTaskBean;
import com.fable.insightview.platform.page.Page;

public interface SysTrapTaskMapper {
	List<SysTrapTaskBean> selectTrapTasks(Page<SysTrapTaskBean> page);

	SysTrapTaskBean getTaskInfoByTaskID(int taskID);

	int updateTaskProgressStatus(SysTrapTaskBean bean);
	
	List<SysTrapTaskBean> getByAlarmDefineID(@Param("alarmDefineID")int alarmDefineID);

	List<SysTrapTaskBean> selectOfflineTrapTasks(Page<SysTrapTaskBean> page);
	
	List<SysTrapTaskBean> getOfflineTaskByHost(@Param("collectorId")int collectorId);
}