package com.fable.insightview.platform.snmpcommunity.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.entity.SysMenuModuleBean;
import com.fable.insightview.platform.entity.SysRoleBean;
import com.fable.insightview.platform.entity.SysRoleMenusBean;
import com.fable.insightview.platform.entity.SysUserGroupRolesBean;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.snmpcommunity.dao.ISnmpCommunityDao;
import com.fable.insightview.platform.snmpcommunity.entity.MODeviceBean;
import com.fable.insightview.platform.snmpcommunity.entity.SNMPCommunityBean;
import com.fable.insightview.platform.snmpcommunity.service.ISnmpCommunityService;

@Service("snmpCommunityService")
public class SnmpCommunityServiceImpl implements ISnmpCommunityService {

	@Autowired
	protected ISnmpCommunityDao snmpCommunityDao;

	@Override
	public List<SNMPCommunityBean> getSnmpCommunityByConditions(
			SNMPCommunityBean snmpBean, FlexiGridPageInfo flexiGridPageInfo) {
		return snmpCommunityDao.getSnmpCommunityByConditions(snmpBean,
				flexiGridPageInfo);
	}

	@Override
	public int getTotalCount(SNMPCommunityBean snmpBean) {
		return snmpCommunityDao.getTotalCount(snmpBean);
	}

	@Override
	public boolean addSnmpCommunity(SNMPCommunityBean snmpBean) {
		return snmpCommunityDao.addSnmpCommunity(snmpBean);
	}

	@Override
	public boolean delSnmpCommunityById(SNMPCommunityBean snmpBean) {
		return snmpCommunityDao.delSnmpCommunityById(snmpBean);
	}

	@Override
	public boolean updateSnmpCommunity(SNMPCommunityBean snmpBean) {
		return snmpCommunityDao.updateSnmpCommunity(snmpBean);
	}


//	@Override
//	public SNMPCommunityBean getMoIDBymoName(String moName) {
//		List<MODeviceBean> moLst=new ArrayList<MODeviceBean>();
//		SNMPCommunityBean snmpbean = new SNMPCommunityBean();
//		moLst = snmpCommunityDao.getMoIDBymoName(moName);
//		try {
//			for (int i = 0; i < moLst.size(); i++) {
//				snmpbean.setMoID(moLst.get(i).getMoID());
//				System.out.println(snmpbean.getMoID());
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return snmpbean;
//	}

	@Override
	public List<SNMPCommunityBean> findSnmpCommunityByID(int id) {
		return snmpCommunityDao.findSnmpCommunityByID(id);
	}

	@Override
	public boolean getSnmpCommunityByConditions(
			SNMPCommunityBean snmpBean) {
		return snmpCommunityDao.getSnmpCommunityByConditions(snmpBean);
	}

//	@Override
//	public List<SNMPCommunityBean> checkDeviceIP(SNMPCommunityBean snmpBean) {
//		return snmpCommunityDao.checkDeviceIP(snmpBean);
//	}
//
//	@Override
//	public SNMPCommunityBean getObjFromDeviceIP(SNMPCommunityBean snmpBean) {
//		return snmpCommunityDao.getObjFromDeviceIP(snmpBean);
//	}

}
