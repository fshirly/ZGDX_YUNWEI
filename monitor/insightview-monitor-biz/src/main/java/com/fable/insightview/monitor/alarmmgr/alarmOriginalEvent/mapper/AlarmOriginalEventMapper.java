package com.fable.insightview.monitor.alarmmgr.alarmOriginalEvent.mapper;

import java.util.List;
import java.util.Map;

import com.fable.insightview.platform.page.Page;

public interface AlarmOriginalEventMapper {
	public List<Map<String ,Object>> getOriginalEvent(Page<Map<String ,Object>> page);
	
	public int insert2OriginalHistory(Map<String ,String> map);
	
	public int deleteOriginalEvent(Map<String ,String> map);
}
