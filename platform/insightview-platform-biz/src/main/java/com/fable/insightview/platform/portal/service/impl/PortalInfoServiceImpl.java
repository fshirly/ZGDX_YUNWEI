package com.fable.insightview.platform.portal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.portal.entity.PortalInfoBean;
import com.fable.insightview.platform.portal.mapper.PortalInfoMapper;
import com.fable.insightview.platform.portal.service.IPortalInfoService;




@Service
public class PortalInfoServiceImpl implements IPortalInfoService {
	
	@Autowired
	PortalInfoMapper portalInfoMapper;
	
	@Override
	public PortalInfoBean getPortalByNameAndUserId(PortalInfoBean portalBean) {
		return portalInfoMapper.getPortalByNameAndUserId(portalBean);
	}

	@Override
	public int updateByPortalNameAndUserId(PortalInfoBean portalBean) {
		return portalInfoMapper.updateByPortalNameAndUserId(portalBean);
	}

	@Override
	public List<PortalInfoBean> queryPortalInfos() {
		return portalInfoMapper.queryPortalInfos();
	}

	@Override
	public PortalInfoBean getPortalByName(String portalName) {
		return portalInfoMapper.getPortalByName(portalName);
	}

	@Override
	public int insertPortal(PortalInfoBean bean) {
		return portalInfoMapper.insertPortal(bean);
	}

	@Override
	public int updatePortal(PortalInfoBean bean) {
		return portalInfoMapper.updatePortal(bean);
	}

	@Override
	public int updatePortalTree(PortalInfoBean bean) {
		return portalInfoMapper.updatePortalTree(bean);
	}

	@Override
	public int deletePortal(PortalInfoBean bean) {
		return portalInfoMapper.deletePortal(bean);
	}

	@Override
	public List<PortalInfoBean> queryPortalInfosByDesc(PortalInfoBean bean) {
		return portalInfoMapper.queryPortalInfosByDesc(bean);
	}

	@Override
	public PortalInfoBean getPortalByOwnerUserId(PortalInfoBean bean) {
		return portalInfoMapper.getPortalByOwnerUserId(bean);
	}

	@Override
	public PortalInfoBean getPortalByUserId(PortalInfoBean bean) {
		return portalInfoMapper.getPortalByUserId(bean);
	}

	@Override
	public List<PortalInfoBean> queryPortalInfosByType(PortalInfoBean portalBean) {
		return portalInfoMapper.queryPortalInfosByType(portalBean);
	}

	@Override
	public int deletePortalByName(PortalInfoBean bean) {
		return portalInfoMapper.deletePortalByName(bean);
	}

	@Override
	public PortalInfoBean getPortalById(String portalId) {
		return portalInfoMapper.getPortalById(portalId);
	}

}
