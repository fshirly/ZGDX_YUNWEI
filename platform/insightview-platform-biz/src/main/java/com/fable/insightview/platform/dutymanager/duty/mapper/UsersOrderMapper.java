package com.fable.insightview.platform.dutymanager.duty.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.platform.dutymanager.duty.entity.PfUsersOrder;

public interface UsersOrderMapper {

	/**
	 * 查询值班下的值班人员
	 * 
	 * @param dutyIds
	 * @return
	 */
	List<Map<String, Object>> query(List<Integer> dutyIds);

	/**
	 * 新增班次值班人
	 * 
	 * @param usersOrder
	 */
	void insert(PfUsersOrder usersOrder);

	/**
	 * 查询一个值班记录下的所有值班人员
	 * 
	 * @param dutyId
	 * @return
	 */
	List<Map<String, Object>> querySingle(@Param("dutyId") int dutyId);

	/**
	 * 删除班次下的值班人信息
	 * 
	 * @param orderId
	 */
	void deleteByOrderId(@Param("orderId") int orderId);

	/**
	 * 批量删除值班班次值班人信息
	 * 
	 * @param orders
	 */
	void deleteMulti(@Param("orderIds") String orderIds);

	/**
	 * 删除值班记录下的值班人信息
	 * 
	 * @param dutyId
	 */
	void deleteByDutyId(@Param("dutyId") int dutyId);

	/**
	 * 查询值班人信息
	 * 
	 * @param dutyId
	 * @return
	 */
	List<Map<String, Object>> queryNoRepeatDuty(@Param("dutyId") int dutyId);
	
	/**
	 * 查询班次值班人数
	 * @param orderId
	 * @return
	 */
	int queryUsers(@Param("orderId")String orderId);
	
	/**
	 * 查询值班负责人
	 * @param orderId
	 * @param userId
	 * @return
	 */
	int queryPrincle(@Param("orderId")String orderId, @Param("userId")String userId);
}
