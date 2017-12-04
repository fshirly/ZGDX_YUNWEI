package com.fable.insightview.monitor.manageddomainipscope.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.monitor.manageddomainipscope.entity.ManagedDomainIPScopeBean;
import com.fable.insightview.platform.page.Page;

/**
 * 管理域IP范围
 *
 */
public interface ManagedDomainIPScopeMapper {
	
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
	int insertDomainIpScope(ManagedDomainIPScopeBean bean);
	
	/**
	 * 更新
	 */
	int updateDomainIpScope(ManagedDomainIPScopeBean bean);
	
	/**
	 * 删除
	 */
	boolean delDomainIpScope(@Param("scopeIDs")String scopeIDs);
	
	int getByInfo(ManagedDomainIPScopeBean bean);
	
	int getByInfo2(ManagedDomainIPScopeBean bean);
	
	List<ManagedDomainIPScopeBean> getScopesByDomainIds(List<Integer> domainIds);
}
