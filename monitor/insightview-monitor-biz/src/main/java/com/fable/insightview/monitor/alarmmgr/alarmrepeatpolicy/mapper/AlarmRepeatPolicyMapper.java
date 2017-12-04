package com.fable.insightview.monitor.alarmmgr.alarmrepeatpolicy.mapper;

import java.util.List;

import com.fable.insightview.monitor.alarmmgr.entity.AlarmRepeatPolicyBean;

public interface AlarmRepeatPolicyMapper {
	/* 删除 */
	boolean delRepeatPolicyByAlarmDefineID(List<Integer> alarmDefineIds);

	AlarmRepeatPolicyBean getAlarmRepeatByAlarmDefineID(int alarmDefineID);

	int insertAlarmRepeatPolicy(AlarmRepeatPolicyBean bean);

	int updateAlarmRepeatPolicy(AlarmRepeatPolicyBean bean);
}
