package com.fable.insightview.monitor.website.service;

import java.util.List;
import java.util.Map;

import com.fable.insightview.monitor.host.entity.AlarmActiveDetail;
import com.fable.insightview.monitor.website.entity.SiteDns;
import com.fable.insightview.monitor.website.entity.SiteFtp;
import com.fable.insightview.monitor.website.entity.SiteHttp;
import com.fable.insightview.monitor.website.entity.SitePort;
import com.fable.insightview.monitor.website.entity.WebSite;
import com.fable.insightview.platform.page.Page;

public interface IWebSiteService {
	
	void insertWebSite(WebSite webSite);

	void insertWebSiteFttp(SiteHttp http);

	void insertWebSiteFtp(SiteFtp ftp);

	void insertWebSiteDns(SiteDns dns);
	
	List<WebSite> getAllWebSites(Page<WebSite> page);
	
	List<SiteHttp> getAllWebSiteHttp(Page<SiteHttp> page);
	
	List<SiteFtp> getAllWebSiteFtp(Page<SiteFtp> page);
	
	List<SiteDns> getAllWebSiteDns(Page<SiteDns> page);
	
	SiteDns getSiteDnsByMoId(int moID);
	
	SiteFtp getSiteFtpByMoId(int moID);
	
	SiteHttp getSiteHttpByMoId(int moID);
	
	SiteDns getFirstSiteDns();
	
	SiteFtp getFirstSiteFtp();
	
	SiteHttp getFirstSiteHttp();
	
	List<SiteDns> getPerfSiteDnsByMoId(Map<String, Object> paramMap);
	
	List<SiteFtp> getPerfSiteFtpByMoId(Map<String, Object> paramMap);
	
	List<SiteHttp> getPerfSiteHttpByMoId(Map<String, Object> paramMap);
	
	SiteDns getDnsChartAvailability(Map<String, Object> paramMap);
	
	SiteFtp getFtpChartAvailability(Map<String, Object> paramMap);
	
	SiteHttp getHttpChartAvailability(Map<String, Object> paramMap);
	
	List<SiteDns> queryDnsPerf(Map<String, Object> paramMap);
	
	List<SiteFtp> queryFtpPerf(Map<String, Object> paramMap);
	
	List<SiteHttp> queryHttpPerf(Map<String, Object> paramMap);
	
	List<AlarmActiveDetail> getWebSiteAlarmInfo(Map<String, Object> paramMap);

	/**
	 * 校验站点名称
	 */
	Map<String, Object> checkSiteName(WebSite webSite);
	
	Map<String, Object> checkSiteNameAndIPAddr(WebSite webSite);
	
	/**
	 * 初始化DNS站点信息
	 */
	SiteDns initDnsInfo(int moID);
	
	/**
	 * 初始化FTP站点信息
	 */
	Map<String, Object> initFtpInfo(int moID);
	
	/**
	 * 初始化HTTP站点信息
	 */
	Map<String, Object> initHttpInfo(int moID);
	
	/**
	 * 初始化tcp站点信息
	 */
	SitePort initTcpInfo(int moID);
	
	/**
	 * 更新站点
	 */
	boolean updateSite(WebSite webSite);
	
	List<SitePort> getAllWebSitePort(Page<SitePort> page);
	
	SitePort getSitePortByMoId(int moID);
	
	SitePort getFirstSitePort();
	
	List<SitePort> getPerfSitePortByMoId(Map<String, Object> paramMap);
	
	SitePort getPortChartAvailability(Map<String, Object> paramMap);
	
	List<SitePort> queryPortPerf(Map<String, Object> paramMap);
	
	int getConfParamValue();
	
	List<WebSite> getAllSites(Map map);
}
