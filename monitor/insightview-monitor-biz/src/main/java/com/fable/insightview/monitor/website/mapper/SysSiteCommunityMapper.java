package com.fable.insightview.monitor.website.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.monitor.website.entity.SysSiteCommunityBean;
import com.fable.insightview.platform.page.Page;

public interface SysSiteCommunityMapper {
	/**
	 * 根据IP和port获得凭证
	 */
	public SysSiteCommunityBean getByIPAndPort(SysSiteCommunityBean bean);
	
	/**
	 * 根据IP和siteType获得凭证
	 */
	public SysSiteCommunityBean getByIPAndSiteType(SysSiteCommunityBean bean);
	
	/**
	 * 新增
	 */
	int insertSiteCommunity(SysSiteCommunityBean bean);
	
	/**
	 * 更新
	 */
	int updateSiteCommunity(SysSiteCommunityBean bean);
	
	List<SysSiteCommunityBean> getFtpCommunityByMOID(int moID);
	
	List<SysSiteCommunityBean> getHttpCommunityByMOID(int moID);
	
	/**
	 * 根据条件获得站点凭证
	 */
	List<SysSiteCommunityBean> getCommunityByConditions(Page<SysSiteCommunityBean> page);
	
	List<SysSiteCommunityBean> getByConditions(SysSiteCommunityBean bean);

	/**
	 * 根据ID获得凭证信息
	 */
	SysSiteCommunityBean getByID(int id);
	
	/**
	 * 根据id删除
	 */
	boolean delById(Integer id);
	
	/**
	 * 批量删除
	 */
	boolean delByIds(@Param("ids")String ids);
}
