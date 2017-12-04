package com.fable.insightview.platform.snmppen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.snmppen.dao.ISnmpPenDao;
import com.fable.insightview.platform.snmppen.entity.SnmpPenInfo;
import com.fable.insightview.platform.snmppen.service.ISnmpPenService;

@Service("snmpPenService")
public class SnmpPenServiceImpl implements ISnmpPenService {

	@Autowired
	protected ISnmpPenDao snmpPenDao;
	
	@Override
	public void addInfo(SnmpPenInfo vo) {
		// TODO Auto-generated method stub
		 snmpPenDao.addInfo(vo);
	}

	@Override
	public void deleteInfo(SnmpPenInfo vo) {
		// TODO Auto-generated method stub
		 snmpPenDao.deleteInfo(vo);
	}

	@Override
	public SnmpPenInfo getInfoById(String paramName,String paramVal){
		// TODO Auto-generated method stub
		return snmpPenDao.getInfoById(paramName,paramVal);
	}

	@Override
	public void updateInfo(SnmpPenInfo vo) {
		// TODO Auto-generated method stub
		 snmpPenDao.updateInfo(vo);
	}

	@Override
	public void deleteBathInfo(String id) {
		// TODO Auto-generated method stub
		snmpPenDao.deleteBathInfo(id);
	}

	@Override
	public FlexiGridPageInfo queryPage(SnmpPenInfo vo,
			FlexiGridPageInfo flexiGridPageInfo) {
		// TODO Auto-generated method stub
		return snmpPenDao.queryPage(vo, flexiGridPageInfo);
	}

}
