package com.fable.insightview.monitor.alarmmgr.alarmeventdefine.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.monitor.alarmmgr.entity.AlarmEventDefineBean;
import com.fable.insightview.platform.page.Page;

public interface AlarmEventDefineMapper {
	/* 查询所有 */
	List<AlarmEventDefineBean> selectAlarmEventDefine(
			Page<AlarmEventDefineBean> page);

	/* 根据ID查询 */
	AlarmEventDefineBean getAlarmEventDefineByID(int alarmDefineID);

	/* 删除 */
	boolean delAlarmEventDefine(List<Integer> alarmDefineIds);

	int insertAlarmEventDefine(AlarmEventDefineBean bean);

	int getAlarmEventDefineByName(@Param(value = "alarmName") String alarmName);

	int getDefineByNameAndID(AlarmEventDefineBean bean);

	int updateAlarmEventDefine(AlarmEventDefineBean bean);

	List<AlarmEventDefineBean> getAllAlarmEvent();

	List<AlarmEventDefineBean> getAlarmByName();

	int updateFilterExpression(int alarmDefineID, String filterExpression);

	int getByAlarmOID(AlarmEventDefineBean bean);

	int getByIDAndAlarmOID(AlarmEventDefineBean bean);
	
	List<AlarmEventDefineBean> getAlarmeventByAlarmType(int alarmTypeID);
	
	List<AlarmEventDefineBean> getDefinedEvent(Page<AlarmEventDefineBean> page);
}
