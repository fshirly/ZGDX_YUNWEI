package com.fable.insightview.monitor.discover.service;

import java.util.Map;

import com.fable.insightview.monitor.website.entity.WebSite;

public interface IAddDeviceService {
	/**
	 * 新增站点
	 */
	Map<String, Object> addSite(WebSite webSite);
	
	/**
	 * 新增站点凭证
	 */
	boolean addSiteCommunity(WebSite webSite);
	
	/**
	 * 更新站点凭证
	 */
	boolean updateSiteCommunity(WebSite webSite,int siteCommunityId);
	
	/**
	 * 新增站点的采集任务
	 */
	boolean addSitePerfTask(WebSite webSite,int templateID,String moTypeLstJson);
	
	/**
	 * 测试站点
	 */
	boolean testSite(WebSite webSite);
	
	Map<String, Object> isExistSite(WebSite webSite);
}
