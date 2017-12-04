package com.fable.insightview.platform.dutymanager.dutyorder.service;

import java.util.List;

import com.fable.insightview.platform.dutymanager.dutyorder.entity.DutyOrder;

public interface DutyOrderService {

	List<DutyOrder> findAllDutyOrders();
	
	boolean deleteDutyOrderById(Integer id);
	
	boolean editDutyOrder(DutyOrder dutyOrder);
	
	void addDutyOrder(DutyOrder dutyOrder);
	
	DutyOrder findDutyOrderById(Integer id);
}
