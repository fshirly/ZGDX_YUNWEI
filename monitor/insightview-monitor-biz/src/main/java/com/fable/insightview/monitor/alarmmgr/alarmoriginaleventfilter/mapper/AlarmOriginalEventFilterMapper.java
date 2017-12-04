package com.fable.insightview.monitor.alarmmgr.alarmoriginaleventfilter.mapper;

import java.util.List;

import com.fable.insightview.monitor.alarmmgr.entity.AlarmOriginalEventFilterBean;
import com.fable.insightview.monitor.alarmmgr.entity.SysTrapTaskBean;
import com.fable.insightview.platform.page.Page;

public interface AlarmOriginalEventFilterMapper {
	/* 删除 */
	boolean delAlarmFilterByAlarmDefineID(List<Integer> alarmDefineIds);

	/* 获得过滤策略 */
	List<AlarmOriginalEventFilterBean> selectAlarmFilter(
			Page<AlarmOriginalEventFilterBean> page);

	List<AlarmOriginalEventFilterBean> getAlarmFilterByAlarmDefineId(
			int alarmDefineID);

	int insertAlarmFilter(AlarmOriginalEventFilterBean bean);

	int updateAlarmFilter(AlarmOriginalEventFilterBean bean);

	int updateFilterByMatch(AlarmOriginalEventFilterBean bean);

	boolean delAlarmFilterByAlarmFilterID(int filterID);

	int selectAlarmFilter();

	List<Integer> getAlarmDefineIDs();

	List<AlarmOriginalEventFilterBean> getAlarmFilters(int alarmDefineID);

	int getByKeyWord(AlarmOriginalEventFilterBean bean);

	int getByKeyWordAndId(AlarmOriginalEventFilterBean bean);

	int insertTrapTask(SysTrapTaskBean bean);

	SysTrapTaskBean getTrapTaskByMOID(String alarmOID);

	SysTrapTaskBean getTaskByMOIDAndStatus(String alarmOID);

	int updateSysTrapTask(SysTrapTaskBean bean);

	boolean delSysTrapTask(int taskID);
	
	List<SysTrapTaskBean> getTrapTaskByMOID2(String alarmOID);
}
