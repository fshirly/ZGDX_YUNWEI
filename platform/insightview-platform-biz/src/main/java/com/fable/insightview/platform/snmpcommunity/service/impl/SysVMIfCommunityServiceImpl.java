package com.fable.insightview.platform.snmpcommunity.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.snmpcommunity.dao.ISysVMIfCommunityDao;
import com.fable.insightview.platform.snmpcommunity.entity.MODeviceBean;
import com.fable.insightview.platform.snmpcommunity.entity.SysVMIfCommunityBean;
import com.fable.insightview.platform.snmpcommunity.service.ISysVMIfCommunityService;

@Service("sysVMIfCommunityService")
public class SysVMIfCommunityServiceImpl implements ISysVMIfCommunityService {
	@Autowired
	protected ISysVMIfCommunityDao sysVMIfCommunityDao;

	@Override
	public boolean addSysAuthCommunity(SysVMIfCommunityBean authBean) {
		return sysVMIfCommunityDao.addSysAuthCommunity(authBean);
	}

	@Override
	public boolean delSysAuthCommunityById(SysVMIfCommunityBean authBean) {
		return sysVMIfCommunityDao.delSysAuthCommunityById(authBean);
	}

	@Override
	public SysVMIfCommunityBean getMoIDBymoName(String moName) {
		return null;
	}

	@Override
	public List<SysVMIfCommunityBean> getSysAuthCommunityByConditions(
			SysVMIfCommunityBean authBean, FlexiGridPageInfo flexiGridPageInfo,
			String type) {
		return sysVMIfCommunityDao.getSysAuthCommunityByConditions(authBean,
				flexiGridPageInfo, type);
	}

	@Override
	public List<SysVMIfCommunityBean> getSysAuthCommunityByConditions(
			SysVMIfCommunityBean authBean) {
		return sysVMIfCommunityDao.getSysAuthCommunityByConditions(authBean);
	}

	@Override
	public int getTotalCount(SysVMIfCommunityBean authBean, String type) {
		return sysVMIfCommunityDao.getTotalCount(authBean, type);
	}

	@Override
	public boolean updateSysAuthCommunity(SysVMIfCommunityBean authBean) {
		return sysVMIfCommunityDao.updateSysAuthCommunity(authBean);
	}

	@Override
	public List<MODeviceBean> getDeviceById(int moId) {
		return sysVMIfCommunityDao.getDeviceById(moId);
	}

	@Override
	public List<SysVMIfCommunityBean> getSysAuthCommunityById(int id) {
		return sysVMIfCommunityDao.getSysAuthCommunityById(id);
	}

	@Override
	public boolean checkDeviceIP(SysVMIfCommunityBean authBean) {
		return sysVMIfCommunityDao.checkDeviceIP(authBean);
	}

	@Override
	public SysVMIfCommunityBean getObjFromDeviceIP(SysVMIfCommunityBean vo) {
		return sysVMIfCommunityDao.getObjFromDeviceIP(vo);
	}

	@Override
	public List<SysVMIfCommunityBean> getByIp(String ip) {
		return sysVMIfCommunityDao.getByIp(ip);
	}

}
