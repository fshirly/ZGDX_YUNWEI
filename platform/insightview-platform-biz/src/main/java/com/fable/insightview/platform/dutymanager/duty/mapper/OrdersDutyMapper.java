package com.fable.insightview.platform.dutymanager.duty.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.platform.dutymanager.duty.entity.PfOrdersDuty;

public interface OrdersDutyMapper {

	/**
	 * 查询值班下的值班班次信息
	 * @param dutyIds
	 * @return
	 */
	List<Map<String, Object>> query (List<Integer> dutyIds);
	
	/**
	 * 批量新增值班信息中的班次信息
	 * @param orders
	 */
	void insert (PfOrdersDuty orders) ;
	
	/**
	 * 删除值班下班次信息
	 * @param dutyId
	 */
	void delete (@Param("dutyId")int dutyId);
	
	/**
	 * 查询值班信息下的值班班次信息
	 * @param dutyId
	 * @return
	 */
	List<Map<String, Object>> querySingle (@Param("dutyId")int dutyId);
	
	/**
	 * 删除值班记录下的一个班次信息
	 * @param orderIdOfDuty
	 */
	void deleteSingle (@Param("orderIdOfDuty")int orderIdOfDuty);

	/**
	 * 批量删除值班班次信息
	 * @param orderIds
	 */
	void deleteMulti (@Param("orderIds")String orderIds);
	
	/**
	 * 更新值班班次的交班状态
	 * @param orderId
	 * @param status
	 */
	void updateStatus (@Param("orderId")String orderId, @Param("status")String status);
	
	/**
	 * 依据orderId查询值班班次信息
	 * @param orderId
	 * @return
	 */
	Map<String, Object>	queryOrder(@Param("orderId")String orderId);
}
