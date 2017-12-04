package com.fable.insightview.platform.dutymanager.dutyorder.mapper;

import java.util.List;

import com.fable.insightview.platform.dutymanager.dutyorder.entity.DutyOrder;
import com.fable.insightview.platform.page.Page;

/**
 * 值班班次相关
 * @author chenly
 *
 */
public interface DutyOrderMapper {

	/**
	 * 查询值班班次列表
	 */
	List<DutyOrder> selectAllDutyOrder();
	
	/**
	 * 新增值班班次
	 */
	void insertDutyOrder(DutyOrder dutyOrder);
	
	/**
	 * 更新值班班次
	 */
	Integer updateDutyOrder(DutyOrder dutyOrder);
	
	/**
	 * 删除值班班次
	 */
	Integer deleteDutyOrder(Integer id);
	
	/**
	 * 根据id查找DutyOrder
	 */
	DutyOrder selectDutyOrderById(Integer id);
}
