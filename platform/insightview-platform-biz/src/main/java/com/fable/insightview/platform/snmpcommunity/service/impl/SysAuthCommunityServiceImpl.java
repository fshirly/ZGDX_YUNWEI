package com.fable.insightview.platform.snmpcommunity.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.snmpcommunity.dao.ISysAuthCommunityDao;
import com.fable.insightview.platform.snmpcommunity.entity.MODeviceBean;
import com.fable.insightview.platform.snmpcommunity.entity.SysAuthCommunityBean;
import com.fable.insightview.platform.snmpcommunity.service.ISysAuthCommunityService;

@Service("sysAuthCommunityService")
public class SysAuthCommunityServiceImpl implements ISysAuthCommunityService {
	@Autowired
	protected ISysAuthCommunityDao sysAuthCommunityDao;
	@Override
	public boolean addSysAuthCommunity(SysAuthCommunityBean authBean) {
		return sysAuthCommunityDao.addSysAuthCommunity(authBean);
	}

	@Override
	public boolean delSysAuthCommunityById(SysAuthCommunityBean authBean) {
		return sysAuthCommunityDao.delSysAuthCommunityById(authBean);
	}

	@Override
	public SysAuthCommunityBean getMoIDBymoName(String moName) {
		return null;
	}

	@Override
	public List<SysAuthCommunityBean> getSysAuthCommunityByConditions(
			SysAuthCommunityBean authBean, FlexiGridPageInfo flexiGridPageInfo,String type) {
		return sysAuthCommunityDao.getSysAuthCommunityByConditions(authBean, flexiGridPageInfo,type);
	}

	@Override
	public List<SysAuthCommunityBean> getSysAuthCommunityByConditions(
			SysAuthCommunityBean authBean) {
		return sysAuthCommunityDao.getSysAuthCommunityByConditions(authBean);
	}

	@Override
	public int getTotalCount(SysAuthCommunityBean authBean,String type) {
		return sysAuthCommunityDao.getTotalCount(authBean,type);
	}

	@Override
	public boolean updateSysAuthCommunity(SysAuthCommunityBean authBean) {
		return sysAuthCommunityDao.updateSysAuthCommunity(authBean);
	}

	@Override
	public List<MODeviceBean> getDeviceById(int moId) {
		return sysAuthCommunityDao.getDeviceById(moId);
	}

	@Override
	public List<SysAuthCommunityBean> getSysAuthCommunityById(int id) {
		return sysAuthCommunityDao.getSysAuthCommunityById(id);
	}

	@Override
	public boolean checkDeviceIP(SysAuthCommunityBean authBean) {
		return sysAuthCommunityDao.checkDeviceIP(authBean);
	}

	@Override
	public SysAuthCommunityBean getObjFromDeviceIP(SysAuthCommunityBean authBean) {
		return sysAuthCommunityDao.getObjFromDeviceIP(authBean);
	}


	

	
	
}
