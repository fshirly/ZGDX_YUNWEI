package com.fable.insightview.platform.dutymanager.duty.service;

import java.util.List;
import java.util.Map;

import com.fable.insightview.platform.dutymanager.duty.entity.DutyChangeRecord;
import com.fable.insightview.platform.dutymanager.duty.entity.PfDuty;

public interface DutyChangeService {

	/**
	 * 根据值班日期查询值班领导名
	 */
	String findLeaderNameByDutyDate(String dutyDate);
	
	/**
	 * 根据值班日期查询所有班次的值班人员id
	 */
	List<Integer> findDutyersByDutyDate(String dutyDate);
	
	/**
	 * 根据用户id查找用户姓名
	 */
	String findUsernameByUserId(Integer userId);
	
	/**
	 * 查找值班组下的所有成员
	 */
	List<Map<String, Object>> findAllDutyers(Integer userId);
	
	/**
	 * 根据值班日期查询相应的调班记录
	 */
	List<DutyChangeRecord> findDutyChangeRecordsByDutyDate(String dutyDate);
	
	/**
	 * 根据值班日期查询值班信息
	 */
	PfDuty findDutyByDate(String dutyDate);
	
	/**
	 * 新增调班记录
	 */
	void addDutyChangeRecord(DutyChangeRecord dutyChangeRecord);
	
	/**
	 * 调班操作
	 */
	void changeDuty(String dutyDate, String fromUserId, String toUserId);
}
