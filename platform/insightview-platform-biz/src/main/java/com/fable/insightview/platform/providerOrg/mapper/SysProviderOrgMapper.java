package com.fable.insightview.platform.providerOrg.mapper;

import java.util.List;

import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.provider.entity.ProviderInfoBean;
import com.fable.insightview.platform.providerOrg.entity.SysProviderOrganization;

public interface SysProviderOrgMapper {

	/**
	 * 很据供应商id获得供应商组织关系列表
	 * @param providerId
	 * @return
	 */
	List<SysProviderOrganization> getOrgByProviderId(Integer providerId);

	/**
	 * 很据供应商Id删除关系
	 * @param providerId
	 * @return
	 */
	int delByProviderId(Integer providerId);

	/**
	 * 新智能供应商组织关系
	 * @param bean
	 */
	void insertProviderOrg(SysProviderOrganization bean);

	/**
	 * 根据组织id过滤供应商
	 * @param orgId
	 * @return
	 */
	List<ProviderInfoBean> queryProviderByOrgId(int orgId);

	/**
	 * 获取供应商用户列表
	 * @param page
	 * @return
	 */
	List<SysUserInfoBean> getProviderUser(Page<SysUserInfoBean> page);

	int getTotalCount(SysUserInfoBean sysUserBean);


}
