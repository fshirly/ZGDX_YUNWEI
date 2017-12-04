package com.fable.insightview.platform.snmpdevice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.snmpdevice.dao.ISnmpDeviceDao;
import com.fable.insightview.platform.snmpdevice.entity.SnmpDeviceSysOIDInfo;
import com.fable.insightview.platform.snmpdevice.service.ISnmpDeviceService;
@Service("snmpDeviceService")
public class SnmpDeviceServiceImpl implements ISnmpDeviceService {
	@Autowired
	private ISnmpDeviceDao snmpDeviceDao;

	@Override
	public void addInfo(SnmpDeviceSysOIDInfo vo) {
		 snmpDeviceDao.addInfo(vo);
	}

	@Override
	public void deleteInfo(SnmpDeviceSysOIDInfo vo) {
		 snmpDeviceDao.deleteInfo(vo);
	}

	@Override
	public SnmpDeviceSysOIDInfo getInfoById(String paramName, String paramVal) {
		return snmpDeviceDao.getInfoById(paramName, paramVal);
	}

	@Override
	public void updateInfo(SnmpDeviceSysOIDInfo vo) {
		 snmpDeviceDao.updateInfo(vo);
	}

	@Override
	public void deleteBathInfo(String id) {
		snmpDeviceDao.deleteBathInfo(id);
	}

	@Override
	public FlexiGridPageInfo queryPage(SnmpDeviceSysOIDInfo vo,
			FlexiGridPageInfo flexiGridPageInfo) {
		return snmpDeviceDao.queryPage(vo, flexiGridPageInfo);
	}
	
}
