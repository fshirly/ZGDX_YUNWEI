package com.fable.insightview.platform.dutymanager.duty.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.platform.dutymanager.duty.entity.DutyChangeRecord;
import com.fable.insightview.platform.dutymanager.duty.entity.PfDuty;

public interface DutyChangeMapper {

	/**
	 * 根据值班id查询值班信息
	 */
	PfDuty selectDutyById(@Param("dutyId")Integer dutyId);
	
	/**
	 * 根据值班日期查询值班信息
	 */
	PfDuty selectDutyByDate(@Param("dutyDate")Date dutyDate);
	
	/**
	 * 根据UserId查询Username
	 */
	String selectUsernameByUserId(@Param("userId")Integer userId);
	
	/**
	 * 根据值班id查询所有班次的值班人员Id
	 */
	List<Integer> selectUserIdsByDutyId(@Param("dutyId")Integer dutyId);
	
	/**
	 * 查询全部调班记录
	 */
	List<DutyChangeRecord> selectDutyChangeRecordsByDutyDate(@Param("dutyDate")Date dutyDate);
	
	/**
	 * 查询值班组下的全部成员
	 */
	List<Map<String, Object>> selectAllDutyers(@Param("userId")Integer userId);
	
	/**
	 * 新增调班记录
	 */
	void insertDutyChangeRecord(DutyChangeRecord dutyChangeRecord);
	
	/**
	 * 给值班人调班操作
	 */
	void updateDutyUser(@Param("dutyId")Integer dutyId, @Param("fromUserId")Integer fromUserId, @Param("toUserId")Integer toUserId);
	
	/**
	 * 给带班领导调班操作
	 */
	void updateDutyLeader(@Param("dutyId")Integer dutyId, @Param("toUserId")Integer toUserId);
}
