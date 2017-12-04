package com.fable.insightview.monitor.alarmmgr.alarmdispatch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.alarmmgr.entity.AlarmDispatchDetail; 
import com.fable.insightview.monitor.alarmmgr.alarmdispatch.mapper.AlarmDispatchMapper;
import com.fable.insightview.monitor.alarmmgr.alarmdispatch.service.IAlarmDispatchService; 

@Service
public class AlarmDispatchServicImpl implements IAlarmDispatchService {

	@Autowired
	private AlarmDispatchMapper alarmDispatchMapper;
	
	@Override
	public List<AlarmDispatchDetail> batchSendFailed() {
		return alarmDispatchMapper.batchSendFailed();
	}

	@Override
	public void insertAlarmDispatchRecord(AlarmDispatchDetail alarm) {
		alarmDispatchMapper.insertAlarmDispatchRecord(alarm);
	}

	@Override
	public Integer isExistDispatchRecord(AlarmDispatchDetail alarm) {
		return alarmDispatchMapper.isExistDispatchRecord(alarm);
	}

	@Override
	public void updateAlarmDispatchRecord(AlarmDispatchDetail alarm) {
		alarmDispatchMapper.updateAlarmDispatchRecord(alarm);
	}

	@Override
	public void updateAlarmDispatchRecordByCMDB(AlarmDispatchDetail alarm) {
		alarmDispatchMapper.updateAlarmDispatchRecordByCMDB(alarm);
	}
}