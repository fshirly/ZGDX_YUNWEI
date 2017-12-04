package com.fable.insightview.platform.dutymanager.duty.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.platform.dutymanager.duty.entity.PfDutyReserve;

public interface DutyReserveMapper {

	/**
	 * 批量添加值班备勤人员信息
	 * @param dutyReserve
	 */
	void insert (PfDutyReserve dutyReserve);
	
	/**
	 * 删除值班信息中的备勤人员信息
	 * @param dutyId
	 */
	void delete (@Param("dutyId")int dutyId);	
	
	/**
	 * 查询值班中的备勤人员
	 * @param dutyId
	 * @return
	 */
	List<Map<String, Object>> query(@Param("dutyId")int dutyId);
	
	/**
	 * 查询多个值班记录的备勤人员
	 * @param dutyIds
	 * @return
	 */
	List<Map<String, Object>> queryMulti (List<Integer> dutyIds);
}
