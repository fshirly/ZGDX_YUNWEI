package com.fable.insightview.monitor.alarmmgr.alarmdispatch.service;

import java.util.List;

import com.fable.insightview.monitor.alarmmgr.entity.AlarmDispatchDetail; 

public interface IAlarmDispatchService {
	
	/**
	 * 查询发送失败的工单
	 */
	List<AlarmDispatchDetail> batchSendFailed();
	
	/**
	 * 查询是否发送过
	 * @param alarm
	 * @return
	 */
	Integer isExistDispatchRecord(AlarmDispatchDetail alarm);
	
	/**
	 * 插入发送记录
	 * @param alarm
	 */
	void insertAlarmDispatchRecord(AlarmDispatchDetail alarm);
	
	/**
	 * 更新发送记录
	 * @param alarm
	 */
	void updateAlarmDispatchRecord(AlarmDispatchDetail alarm);
	
	/**
	 * CMDB回写处理结果
	 * @param alarm
	 */
	void updateAlarmDispatchRecordByCMDB(AlarmDispatchDetail alarm);
}
