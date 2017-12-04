package com.fable.insightview.monitor.alarmmgr.alarmoriginaleventfilter.service;

import java.util.Map;

import com.fable.insightview.monitor.alarmmgr.entity.AlarmOriginalEventFilterBean;

public interface IAlarmOriginalEventFilterService {
	public Map<String, Object> listAlarmFilter(
			AlarmOriginalEventFilterBean alarmOriginalEventFilterBean);

	public Map<String, Object> getAlarmFilterByAlarmDefineId(int alarmDefineId);
}
