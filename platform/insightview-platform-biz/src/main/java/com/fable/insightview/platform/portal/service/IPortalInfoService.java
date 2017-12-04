package com.fable.insightview.platform.portal.service;

import java.util.List;

import com.fable.insightview.platform.portal.entity.PortalInfoBean;



public interface IPortalInfoService {
	
	int insertPortal(PortalInfoBean bean);

	int updatePortal(PortalInfoBean bean);

	PortalInfoBean getPortalByName(String portalName);
	
	public PortalInfoBean getPortalByNameAndUserId(PortalInfoBean portalBean);

	public int updateByPortalNameAndUserId(PortalInfoBean portalBean);

	//查询所有portal信息
	public List<PortalInfoBean> queryPortalInfos();
	//修改portal树节点
	int updatePortalTree(PortalInfoBean bean);
	
	int deletePortal(PortalInfoBean bean);
	
	public List<PortalInfoBean> queryPortalInfosByDesc(PortalInfoBean bean);
	
	PortalInfoBean getPortalByUserId(PortalInfoBean bean);
	
	PortalInfoBean getPortalByOwnerUserId(PortalInfoBean bean);
	
	List<PortalInfoBean> queryPortalInfosByType(PortalInfoBean portalBean);
	
	int deletePortalByName(PortalInfoBean bean);
	//根据ID获取视图信息
	PortalInfoBean getPortalById(String portalId);
	
}
