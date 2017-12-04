package com.fable.insightview.monitor.website.service;

import java.util.List;

import com.fable.insightview.monitor.website.entity.SysSiteCommunityBean;
import com.fable.insightview.platform.page.Page;

/**
 * 站点凭证
 *
 */
public interface ISysSiteCommunityService {
	/**
	 * 根据IP和port获得凭证
	 */
	public SysSiteCommunityBean getByIPAndPort(SysSiteCommunityBean bean);
	
	/**
	 * 根据IP和siteType获得凭证
	 */
	public SysSiteCommunityBean getByIPAndSiteType(SysSiteCommunityBean bean);
	
	/**
	 * 根据条件获得站点凭证
	 */
	List<SysSiteCommunityBean> getCommunityByConditions(Page<SysSiteCommunityBean> page);
	
	/**
	 * 检验站点凭证是否存在
	 */
	boolean checkCommunity(SysSiteCommunityBean siteCommunityBean);
	/**
	 * 新增站点凭证
	 */
	boolean addCommunity(SysSiteCommunityBean siteCommunityBean);
	
	/**
	 * 初始化站点凭证
	 */
	SysSiteCommunityBean initSiteCommunity(int id);
	
	/**
	 * 更新站点凭证
	 */
	boolean updateCommunity(SysSiteCommunityBean siteCommunityBean);
	
	/**
	 * 根据id删除
	 */
	boolean delById(Integer id);
	
	/**
	 * 批量删除
	 */
	boolean delByIds(String ids);
}
