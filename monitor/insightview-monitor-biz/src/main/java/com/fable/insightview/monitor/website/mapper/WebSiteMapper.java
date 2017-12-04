package com.fable.insightview.monitor.website.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fable.insightview.monitor.host.entity.AlarmActiveDetail;
import com.fable.insightview.monitor.website.entity.SiteDns;
import com.fable.insightview.monitor.website.entity.SiteFtp;
import com.fable.insightview.monitor.website.entity.SiteHttp;
import com.fable.insightview.monitor.website.entity.SitePort;
import com.fable.insightview.monitor.website.entity.WebSite;
import com.fable.insightview.platform.page.Page;

public interface WebSiteMapper {

	void insertWebSite(WebSite webSite);

	void insertWebSiteHttp(SiteHttp http);

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
	
	int getDNSCountByName(SiteDns dns);
	
	int getFTPCountByName(SiteFtp ftp);
	
	int getHttpCountByName(SiteHttp http);
	
	int getDNSCountByUrl(SiteDns dns);
	
	int getFTPCountByUrl(SiteFtp ftp);
	
	int getHttpCountByUrl2(SiteHttp http);
	
	int updateWebSiteHttp(SiteHttp http);

	int updateWebSiteFtp(SiteFtp ftp);

	int updateWebSiteDns(SiteDns dns);
	
	SiteHttp getHttpByNameUrl(SiteHttp http);
	
	SiteFtp getFtpByNameAndIpAndPort(SiteFtp ftp);
	
	SiteDns getDnsByNameAndDomainName(SiteDns dns);
	
	int getDNSCountByName2(SiteDns dns);
	
	int getFTPCountByName2(SiteFtp ftp);
	
	int getHttpCountByName2(SiteHttp http);
	
	int getDNSCountByDomainName(SiteDns dns);
	
	int getFTPCountByIPAddr(SiteFtp ftp);
	
	int getHttpCountByUrl(SiteHttp http);
	
	SiteFtp getFtpAndCommInfo(int moID);
	
	SiteHttp getHttpAndCommInfo(int moID);
	
	List<SitePort> getAllWebSitePort(Page<SitePort> page);
	
	SitePort getSitePortByMoId(int moID);
	
	SitePort getFirstSitePort();
	
	List<SitePort> getPerfSitePortByMoId(Map<String, Object> paramMap);
	
	SitePort getPortChartAvailability(Map<String, Object> paramMap);
	//暂不实现
	List<SitePort> queryPortPerf(Map<String, Object> paramMap);
	
	int getTCPCountByName(SitePort sitePort);
	
	int getTCPCountByIPAddr(SitePort sitePort);
	
	void insertWebSitePort(SitePort sitePort);
	
	int updateWebSitePort(SitePort sitePort);
	
	int getTCPCountByCondition(SitePort sitePort);
	
	int getTCPCountByCondition2(SitePort sitePort);
	
	SitePort getTcpByNameAndIpAndPort(SitePort sitePort);
	
	WebSite getSiteNameAnMOID(int moID);
	
	Date getDateNow();
	int getConfParamValue();
	
	List<WebSite> getAllSites(Map map);
	//增加同步站点数据会写
	int  updateWebSiteHttpId(Map map);
	int updateWebSiteTcpId(Map map);
}
