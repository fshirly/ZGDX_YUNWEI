package com.fable.insightview.platform.portal.mapper;

import java.util.List;

import com.fable.insightview.platform.portal.entity.PortalInfoBean;



public interface PortalInfoMapper {
	
	int insertPortal(PortalInfoBean bean);

	int updatePortal(PortalInfoBean bean);

	PortalInfoBean getPortalByName(String portalName);
	
	PortalInfoBean getPortalByNameAndUserId(PortalInfoBean bean);
	
	int updateByPortalNameAndUserId(PortalInfoBean portalBean);
	
	//查询所有portal信息
	public List<PortalInfoBean> queryPortalInfos();
	
	//修改portal树节点
	int updatePortalTree(PortalInfoBean bean);
	
	int deletePortal(PortalInfoBean bean);
	
	public List<PortalInfoBean> queryPortalInfosByDesc(PortalInfoBean bean);
	
	PortalInfoBean getPortalByUserId(PortalInfoBean bean);
	
	PortalInfoBean getPortalByUserIdAndOwnerUserId(PortalInfoBean bean);
	
	PortalInfoBean getPortalByOwnerUserId(PortalInfoBean bean);
	
	public List<PortalInfoBean> queryPortalInfosByType(PortalInfoBean bean);
	
	int deletePortalByName(PortalInfoBean bean);
	
	PortalInfoBean getPortalById(String portalId);
}
