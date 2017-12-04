package com.fable.insightview.monitor.alarmmgr.alarmOriginalEvent.service;

import java.util.List;
import java.util.Map;

import com.fable.insightview.platform.page.Page;

public interface IAlarmOriginalEventService {
	public List<Map<String ,Object>> getOriginalEvent(Page<Map<String ,Object>> page);
	
	public boolean modifyOriginalEvent(String eventOids ,String timeBegin ,String timeEnd ,String moids ,String sourceMoids);
}
