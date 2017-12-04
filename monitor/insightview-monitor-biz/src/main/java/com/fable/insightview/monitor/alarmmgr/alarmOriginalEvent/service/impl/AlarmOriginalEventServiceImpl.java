package com.fable.insightview.monitor.alarmmgr.alarmOriginalEvent.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.alarmmgr.alarmOriginalEvent.mapper.AlarmOriginalEventMapper;
import com.fable.insightview.monitor.alarmmgr.alarmOriginalEvent.service.IAlarmOriginalEventService;
import com.fable.insightview.platform.page.Page;

@Service
public class AlarmOriginalEventServiceImpl implements
		IAlarmOriginalEventService {
	@Autowired
	private AlarmOriginalEventMapper orginalMapper;
	
	@Override
	public List<Map<String, Object>> getOriginalEvent(
			Page<Map<String, Object>> page) {
		return orginalMapper.getOriginalEvent(page);
	}

	@Override
	public boolean modifyOriginalEvent(String eventOids ,String timeBegin ,String timeEnd ,String moids ,String sourceMoids) {
		boolean success = false;
		
		Map<String ,String> map = new HashMap<String ,String>();
		map.put("eventOids", eventOids);
		
		if(!StringUtils.isEmpty(timeBegin)){
			map.put("timeBegin", timeBegin);
		}
		if(!StringUtils.isEmpty(timeEnd)){
			map.put("timeEnd", timeEnd);
		}
		if(!StringUtils.isEmpty(moids)){
			map.put("moids", moids);
		}
		if(!StringUtils.isEmpty(sourceMoids)){
			map.put("sourceMoids", sourceMoids);
		}
		
		int inserts = orginalMapper.insert2OriginalHistory(map);
		int dels = orginalMapper.deleteOriginalEvent(map);
		
		if(inserts == dels)
			success = true;
		return success;
	}

}
