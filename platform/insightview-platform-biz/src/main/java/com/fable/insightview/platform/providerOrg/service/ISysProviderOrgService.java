package com.fable.insightview.platform.providerOrg.service;

import java.util.List;

import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.provider.entity.ProviderInfoBean;
import com.fable.insightview.platform.providerOrg.entity.SysProviderOrganization;

public interface ISysProviderOrgService {

	/**
	 * 添加供应商和单位的映射
	 * @param sysProviderOrganization
	 * @return
	 */
	boolean addSysProviderOrg(SysProviderOrganization sysProviderOrganization);

	/**
	 * 根据供应商Id查询组织id字符串
	 * @param providerId
	 * @return
	 */
	String queryOrgByProvider(Integer providerId);

	/**
	 * 根据组织id过滤供应商
	 * @param parseInt
	 * @return
	 */
	List<ProviderInfoBean> queryProviderByOrgId(int orgId);

	/**
	 * 获取供应商用户列表
	 * @param page
	 * @return
	 */
	List<SysUserInfoBean> getProviderUser(Page<SysUserInfoBean> page);

	/**
	 * 
	 * @param sysUserBean
	 * @return
	 */
	int getTotalCount(SysUserInfoBean sysUserBean);

}
