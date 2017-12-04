package com.fable.insightview.platform.dutymanager.dutyorder.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.dutymanager.dutyorder.entity.DutyOrder;
import com.fable.insightview.platform.dutymanager.dutyorder.mapper.DutyOrderMapper;
import com.fable.insightview.platform.dutymanager.dutyorder.service.DutyOrderService;

/**
 * 值班班次
 * @author chenly
 *
 */
@Service
public class DutyOrderServiceImpl implements DutyOrderService {

	@Autowired
	private DutyOrderMapper dutyOrderMapper;
	
	@Override
	public List<DutyOrder> findAllDutyOrders() {
		List<DutyOrder> dutyOrderList = dutyOrderMapper.selectAllDutyOrder();
		return dutyOrderList;
	}

	@Override
	public boolean deleteDutyOrderById(Integer id) {
		int num = dutyOrderMapper.deleteDutyOrder(id);
		if(num > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean editDutyOrder(DutyOrder dutyOrder) {
		int num = dutyOrderMapper.updateDutyOrder(dutyOrder);
		if(num > 0) {
			return true;
		}
		return false;
	}

	@Override
	public void addDutyOrder(DutyOrder dutyOrder) {
		dutyOrderMapper.insertDutyOrder(dutyOrder);
	}

	@Override
	public DutyOrder findDutyOrderById(Integer id) {
		return dutyOrderMapper.selectDutyOrderById(id);
	}

}
