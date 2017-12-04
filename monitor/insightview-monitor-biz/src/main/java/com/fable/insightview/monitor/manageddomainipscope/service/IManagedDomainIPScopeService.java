package com.fable.insightview.monitor.manageddomainipscope.service;

import java.util.List;


import com.fable.insightview.monitor.manageddomainipscope.entity.ManagedDomainIPScopeBean;
import com.fable.insightview.platform.page.Page;

public interface IManagedDomainIPScopeService {

	/**
	 * 分页查询管理域ip范围数据
	 */
	List<ManagedDomainIPScopeBean> getDomainIpScopeList(Page<ManagedDomainIPScopeBean> page);
	
	/**
	 * 根据id查询
	 */
	ManagedDomainIPScopeBean getDomainIpScopeInfo(int scopeID);
	
	/**
	 * 新增
	 */
	boolean insertDomainIpScope(ManagedDomainIPScopeBean bean);
	
	/**
	 * 更新
	 */
	boolean updateDomainIpScope(ManagedDomainIPScopeBean bean);
	
	/**
	 * 删除
	 */
	boolean delDomainIpScope(String scopeIDs);
	
	/**
	 * 验证是否存在
	 */
	public boolean checkExist(ManagedDomainIPScopeBean bean,String flag);
	
	/**
	 * 获得管理域描述
	 */
	public String getDomainDesc(int domainID);
}
