package com.fable.insightview.monitor.website.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.host.entity.AlarmActiveDetail;
import com.fable.insightview.monitor.host.entity.HostComm;
import com.fable.insightview.monitor.perf.entity.PerfTaskInfoBean;
import com.fable.insightview.monitor.perf.mapper.PerfTaskInfoMapper;
import com.fable.insightview.monitor.perf.service.IPerfTaskService;
import com.fable.insightview.monitor.website.entity.SiteDns;
import com.fable.insightview.monitor.website.entity.SiteFtp;
import com.fable.insightview.monitor.website.entity.SiteHttp;
import com.fable.insightview.monitor.website.entity.SitePort;
import com.fable.insightview.monitor.website.entity.SysSiteCommunityBean;
import com.fable.insightview.monitor.website.entity.WebSite;
import com.fable.insightview.monitor.website.mapper.WebSiteMapper;
import com.fable.insightview.monitor.website.service.ISysSiteCommunityService;
import com.fable.insightview.monitor.website.service.IWebSiteService;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.sysconf.service.SysConfigService;

@Service
public class WebSiteServiceImpl implements IWebSiteService {
	private static final Logger logger = LoggerFactory.getLogger(WebSiteServiceImpl.class);
	@Autowired WebSiteMapper webSiteMapper;
	@Autowired IPerfTaskService perfTaskService;
	@Autowired PerfTaskInfoMapper perfTaskInfoMapper;
	@Autowired ISysSiteCommunityService siteCommunityService;
	

	@Override
	public void insertWebSite(WebSite webSite) {
		webSiteMapper.insertWebSite(webSite);
	}

	@Override
	public void insertWebSiteDns(SiteDns dns) {
		webSiteMapper.insertWebSiteDns(dns);
	}

	@Override
	public void insertWebSiteFtp(SiteFtp ftp) {
		webSiteMapper.insertWebSiteFtp(ftp);
	}

	@Override
	public void insertWebSiteFttp(SiteHttp http) {
		webSiteMapper.insertWebSiteHttp(http);
	}

	@Override
	public List<WebSite> getAllWebSites(Page<WebSite> page) {
		int period=1;
		long curr=0;
		long currTime=getDateNow().getTime();
		List<WebSite> lst = webSiteMapper.getAllWebSites(page);
		if(lst!=null){
			for (int i = 0; i < lst.size(); i++) {
				WebSite siteBean = lst.get(i);
				Date updateAlarmTime = siteBean.getUpdateAlarmTime();
				Date collectTime=siteBean.getCollectTime();
				if(siteBean.getDoIntervals()==null || "".equals(siteBean.getDoIntervals())){
					period=siteBean.getDefDoIntervals()*getMultiple()*60000;
				}else{
					period=siteBean.getDoIntervals()*getMultiple()*1000;
				}
				if (collectTime != null) {
					curr=currTime-siteBean.getCollectTime().getTime();
					if(curr<=period){
						if ("1".equals(siteBean.getAvailable())||"11".equals(siteBean.getAvailable())) {
							siteBean.setAvailableTip("UP");
							siteBean.setAvailablePng("up.png");
						} else if ("2".equals(siteBean.getAvailable())||"22".equals(siteBean.getAvailable())||"12".equals(siteBean.getAvailable())||"21".equals(siteBean.getAvailable())) {
							siteBean.setAvailableTip("DOWN");
							siteBean.setAvailablePng("down.png");
						} 
						if (updateAlarmTime != null) {
							String durationTime = HostComm.getDurationToTime(currTime-updateAlarmTime.getTime());
							siteBean.setDurationTime(durationTime);
						}else{
							siteBean.setDurationTime("");
						}
					}else{
						siteBean.setAvailableTip("未知");
						siteBean.setAvailablePng("unknown.png");
						String durationTime = HostComm.getDurationToTime(currTime-collectTime.getTime());
						siteBean.setDurationTime(durationTime);
						
					}	
				}else{
						siteBean.setAvailableTip("未知");
						siteBean.setAvailablePng("unknown.png");
						siteBean.setDurationTime("");
					}
				siteBean.setResponseTimeFormat(HostComm.getMsToTime(siteBean.getResponseTime()));
			}
		}
		
		return lst;
	}

	@Override
	public List<SiteDns> getAllWebSiteDns(Page<SiteDns> page) {
		int period=1;
		long curr=0;
		long currTime=getDateNow().getTime();
		List<SiteDns> dnsLst = webSiteMapper.getAllWebSiteDns(page);
		if(dnsLst!=null){
			for (int i = 0; i < dnsLst.size(); i++) {
				SiteDns dns = dnsLst.get(i);
				Date updateAlarmTime = dns.getUpdateAlarmTime();
				Date collectTime=dns.getCollectTime();
				if(dns.getDoIntervals()==null || "".equals(dns.getDoIntervals())){
					period=dns.getDefDoIntervals()*getMultiple()*60000;
				}else{
					period=dns.getDoIntervals()*getMultiple()*1000;
				}
				if (collectTime != null) {
					curr=currTime-dns.getCollectTime().getTime();
					if(curr<=period){
						if ("1".equals(dns.getAvailable())) {
							dns.setAvailableTip("UP");
							dns.setAvailablePng("up.png");
						} else if ("2".equals(dns.getAvailable())) {
							dns.setAvailableTip("DOWN");
							dns.setAvailablePng("down.png");
						} 
						if (updateAlarmTime != null) {
							String durationTime = HostComm.getDurationToTime(currTime-updateAlarmTime.getTime());
							dns.setDurationTime(durationTime);
						}else{
							dns.setDurationTime("");
						}
					}else{
						dns.setAvailableTip("未知");
						dns.setAvailablePng("unknown.png");
						String durationTime = HostComm.getDurationToTime(currTime-collectTime.getTime());
						dns.setDurationTime(durationTime);
						
					}	
				}else{
					dns.setAvailableTip("未知");
					dns.setAvailablePng("unknown.png");
					dns.setDurationTime("");
				}
				
				dns.setFormatTime(HostComm.getMsToTime(dns.getResponseTime()));
			}
		}
		
		return dnsLst;
	}

	@Override
	public List<SiteFtp> getAllWebSiteFtp(Page<SiteFtp> page) {
		int period=1;
		long curr=0;
		long currTime=getDateNow().getTime();
		List<SiteFtp> ftpLst = webSiteMapper.getAllWebSiteFtp(page);
		if(ftpLst!=null){
			for (int i = 0; i < ftpLst.size(); i++) {
				SiteFtp ftp = ftpLst.get(i);
				Date updateAlarmTime = ftp.getUpdateAlarmTime();
				Date collectTime=ftp.getCollectTime();
				if(ftp.getDoIntervals()==null || "".equals(ftp.getDoIntervals())){
					period=ftp.getDefDoIntervals()*getMultiple()*60000;
				}else{
					period=ftp.getDoIntervals()*getMultiple()*1000;
				}
				if (collectTime != null) {
					curr=currTime-ftp.getCollectTime().getTime();
						if(curr<=period){
							if ("11".equals(ftp.getAvailable())) {
								ftp.setAvailableTip("UP");
								ftp.setAvailablePng("up.png");
							} else if ("22".equals(ftp.getAvailable())||"12".equals(ftp.getAvailable())||"21".equals(ftp.getAvailable())) {
								ftp.setAvailableTip("DOWN");
								ftp.setAvailablePng("down.png");
							} 
							if (updateAlarmTime != null) {
								String durationTime = HostComm.getDurationToTime(currTime-updateAlarmTime.getTime());
								ftp.setDurationTime(durationTime);
							}else{
								ftp.setDurationTime("");
							}
						}else{
							ftp.setAvailableTip("未知");
							ftp.setAvailablePng("unknown.png");
							String durationTime = HostComm.getDurationToTime(currTime-collectTime.getTime());
							ftp.setDurationTime(durationTime);
					
						}
				}else{
					ftp.setAvailableTip("未知");
					ftp.setAvailablePng("unknown.png");
					ftp.setDurationTime("");
				}
				ftp.setFormatTime(HostComm.getMsToTime(ftp.getResponseTime()));
			}
		}
		
		return ftpLst;
	}

	@Override
	public List<SiteHttp> getAllWebSiteHttp(Page<SiteHttp> page) {
		int period=1;
		long curr=0;
		long currTime=getDateNow().getTime();
		List<SiteHttp> httpLst = webSiteMapper.getAllWebSiteHttp(page);
		if(httpLst!=null){
			for (int i = 0; i < httpLst.size(); i++) {
				SiteHttp http = httpLst.get(i);
				Date updateAlarmTime = http.getUpdateAlarmTime();
				Date collectTime=http.getCollectTime();
				if(http.getDoIntervals()==null || "".equals(http.getDoIntervals())){
					period=http.getDefDoIntervals()*getMultiple()*60000;
				}else{
					period=http.getDoIntervals()*getMultiple()*1000;
				}
				if (collectTime != null) {
					curr=currTime-http.getCollectTime().getTime();
					if(curr<=period){
						if ("1".equals(http.getAvailable())) {
							http.setAvailableTip("UP");
							http.setAvailablePng("up.png");
						} else if ("2".equals(http.getAvailable())) {
							http.setAvailableTip("DOWN");
							http.setAvailablePng("down.png");
						} 
						if (updateAlarmTime != null) {
							String durationTime = HostComm.getDurationToTime(currTime-updateAlarmTime.getTime());
							http.setDurationTime(durationTime);
						}else{
							http.setDurationTime("");
						}
					}else{
						http.setAvailableTip("未知");
						http.setAvailablePng("unknown.png");
							String durationTime = HostComm.getDurationToTime(currTime-collectTime.getTime());
							http.setDurationTime(durationTime);
					}	
				}else{
					http.setAvailableTip("未知");
					http.setAvailablePng("unknown.png");
					http.setDurationTime("");
				}	
				http.setFormatTime(HostComm.getMsToTime(http.getResponseTime()));
			}
		}
		return httpLst;
	}

	@Override
	public SiteDns getSiteDnsByMoId(int moID) {
		SiteDns dnsBean = webSiteMapper.getSiteDnsByMoId(moID);
		Date updateAlarmTime = dnsBean.getUpdateAlarmTime();
		if (updateAlarmTime != null) {
			String durationTime = HostComm.getMsToTime((getDateNow()).getTime()-updateAlarmTime.getTime());
			dnsBean.setDurationTime(durationTime);
		}
		return dnsBean;
	}

	@Override
	public SiteFtp getSiteFtpByMoId(int moID) {
		SiteFtp ftpBean = webSiteMapper.getSiteFtpByMoId(moID);
		Date updateAlarmTime = ftpBean.getUpdateAlarmTime();
		if (updateAlarmTime != null) {
			String durationTime = HostComm.getMsToTime((getDateNow()).getTime()-updateAlarmTime.getTime());
			ftpBean.setDurationTime(durationTime);
		}
		return ftpBean;
	}

	@Override
	public SiteHttp getSiteHttpByMoId(int moID) {
		SiteHttp httpBean =webSiteMapper.getSiteHttpByMoId(moID);
		Date updateAlarmTime = httpBean.getUpdateAlarmTime();
		if (updateAlarmTime != null) {
			String durationTime = HostComm.getMsToTime((getDateNow()).getTime()-updateAlarmTime.getTime());
			httpBean.setDurationTime(durationTime);
		}
		return httpBean;
	}

	@Override
	public SiteDns getFirstSiteDns() {
		return webSiteMapper.getFirstSiteDns();
	}

	@Override
	public SiteFtp getFirstSiteFtp() {
		return webSiteMapper.getFirstSiteFtp();
	}

	@Override
	public SiteHttp getFirstSiteHttp() {
		return webSiteMapper.getFirstSiteHttp();
	}

	@Override
	public List<SiteDns> getPerfSiteDnsByMoId(Map<String, Object> paramMap) {
		int period=1;
		long curr=0;
		long currTime=getDateNow().getTime();
		List<SiteDns> dnsLst = webSiteMapper.getPerfSiteDnsByMoId(paramMap);
		if(dnsLst!=null){
			for (int i = 0; i < dnsLst.size(); i++) {
				SiteDns dns = dnsLst.get(i);
				Date updateAlarmTime = dns.getUpdateAlarmTime();
				Date collectTime=dns.getCollectTime();
				if(dns.getDoIntervals()==null || "".equals(dns.getDoIntervals())){
					period=dns.getDefDoIntervals()*getMultiple()*60000;
				}else{
					period=dns.getDoIntervals()*getMultiple()*1000;
				}
				if (collectTime != null) {
					curr=currTime-dns.getCollectTime().getTime();
					if(curr<=period){
						if ("1".equals(dns.getAvailable())) {
							dns.setAvailableTip("UP");
							dns.setAvailablePng("up.png");
						} else if ("2".equals(dns.getAvailable())) {
							dns.setAvailableTip("DOWN");
							dns.setAvailablePng("down.png");
						} 
						if (updateAlarmTime != null) {
							String durationTime = HostComm.getDurationToTime(currTime-updateAlarmTime.getTime());
							dns.setDurationTime(durationTime);
						}else{
							dns.setDurationTime("");
						}
					}else{
						dns.setAvailableTip("未知");
						dns.setAvailablePng("unknown.png");
						String durationTime = HostComm.getDurationToTime(currTime-collectTime.getTime());
						dns.setDurationTime(durationTime);
						
					}	
				}else{
					dns.setAvailableTip("未知");
					dns.setAvailablePng("unknown.png");
					dns.setDurationTime("");
				}
				dns.setFormatTime(HostComm.getMsToTime(dns.getResponseTime()));
			}
		}
		return dnsLst;
	}

	@Override
	public List<SiteFtp> getPerfSiteFtpByMoId(Map<String, Object> paramMap) {
		int period=1;
		long curr=0;
		long currTime=getDateNow().getTime();
		List<SiteFtp>  ftpLst = webSiteMapper.getPerfSiteFtpByMoId(paramMap);
		if(ftpLst!=null){
			for (int i = 0; i < ftpLst.size(); i++) {
				SiteFtp ftp = ftpLst.get(i);
				Date updateAlarmTime = ftp.getUpdateAlarmTime();
				Date collectTime=ftp.getCollectTime();
				if(ftp.getDoIntervals()==null || "".equals(ftp.getDoIntervals())){
					period=ftp.getDefDoIntervals()*getMultiple()*60000;
				}else{
					period=ftp.getDoIntervals()*getMultiple()*1000;
				}
				if (collectTime != null) {
						curr=currTime-ftp.getCollectTime().getTime();
						if(curr<=period){
							if ("1".equals(ftp.getAvailable())||"11".equals(ftp.getAvailable())) {
								ftp.setAvailableTip("UP");
								ftp.setAvailablePng("up.png");
							} else if ("2".equals(ftp.getAvailable())||"22".equals(ftp.getAvailable())||"12".equals(ftp.getAvailable())||"21".equals(ftp.getAvailable())) {
								ftp.setAvailableTip("DOWN");
								ftp.setAvailablePng("down.png");
							} 
							if (updateAlarmTime != null) {
								String durationTime = HostComm.getDurationToTime(currTime-updateAlarmTime.getTime());
								ftp.setDurationTime(durationTime);
							}else{
								ftp.setDurationTime("");
							}
						}else{
							ftp.setAvailableTip("未知");
							ftp.setAvailablePng("unknown.png");
							String durationTime = HostComm.getDurationToTime(currTime-collectTime.getTime());
							ftp.setDurationTime(durationTime);
						}	
				}else{
					ftp.setAvailableTip("未知");
					ftp.setAvailablePng("unknown.png");
					ftp.setDurationTime("");
				}
				ftp.setFormatTime(HostComm.getMsToTime(ftp.getResponseTime()));
			}
		}
		return ftpLst;
		
	}

	@Override
	public List<SiteHttp> getPerfSiteHttpByMoId(Map<String, Object> paramMap) {
		int period=1;
		long curr=0;
		long currTime=getDateNow().getTime();
		List<SiteHttp> httpLst = webSiteMapper.getPerfSiteHttpByMoId(paramMap);
		if(httpLst!=null){
			for (int i = 0; i < httpLst.size(); i++) {
				SiteHttp http = httpLst.get(i);
				Date updateAlarmTime = http.getUpdateAlarmTime();
				Date collectTime=http.getCollectTime();
				if(http.getDoIntervals()==null || "".equals(http.getDoIntervals())){
					period=http.getDefDoIntervals()*getMultiple()*60000;
				}else{
					period=http.getDoIntervals()*getMultiple()*1000;
				}
				if (collectTime != null) {
					curr=currTime-http.getCollectTime().getTime();
					if(curr<=period){
						if ("1".equals(http.getAvailable())) {
							http.setAvailableTip("UP");
							http.setAvailablePng("up.png");
						} else if ("2".equals(http.getAvailable())) {
							http.setAvailableTip("DOWN");
							http.setAvailablePng("down.png");
						} 
						if (updateAlarmTime != null) {
							String durationTime = HostComm.getDurationToTime(currTime-updateAlarmTime.getTime());
							http.setDurationTime(durationTime);
						}else{
							http.setDurationTime("");
						}
					}else{
						http.setAvailableTip("未知");
						http.setAvailablePng("unknown.png");
						String durationTime = HostComm.getDurationToTime(currTime-collectTime.getTime());
						http.setDurationTime(durationTime);
					}	
				}else{
					http.setAvailableTip("未知");
					http.setAvailablePng("unknown.png");
					http.setDurationTime("");
				}
				http.setFormatTime(HostComm.getMsToTime(http.getResponseTime()));
			}
		}
		return httpLst;
	}

	@Override
	public SiteDns getDnsChartAvailability(Map<String, Object> paramMap) {
		return webSiteMapper.getDnsChartAvailability(paramMap);
	}

	@Override
	public SiteFtp getFtpChartAvailability(Map<String, Object> paramMap) {
		return webSiteMapper.getFtpChartAvailability(paramMap);
	}

	@Override
	public SiteHttp getHttpChartAvailability(Map<String, Object> paramMap) {
		return webSiteMapper.getHttpChartAvailability(paramMap);
	}

	@Override
	public List<SiteDns> queryDnsPerf(Map<String, Object> paramMap) {
		return webSiteMapper.queryDnsPerf(paramMap);
	}

	@Override
	public List<SiteFtp> queryFtpPerf(Map<String, Object> paramMap) {
		return webSiteMapper.queryFtpPerf(paramMap);
	}

	@Override
	public List<SiteHttp> queryHttpPerf(Map<String, Object> paramMap) {
		return webSiteMapper.queryHttpPerf(paramMap);
	}

	@Override
	public List<AlarmActiveDetail> getWebSiteAlarmInfo(
			Map<String, Object> paramMap) {
		return webSiteMapper.getWebSiteAlarmInfo(paramMap);
	}

	@Override
	public Map<String, Object> checkSiteName(WebSite webSite) {
		int nameCount = 0;
		int ipCount = 0;
		int moClassID = webSite.getMoClassID();
		// DNS
		if (moClassID == 91) {
			SiteDns dns = webSite.getSiteDns();
			dns.setSiteName(webSite.getSiteName());
			nameCount = webSiteMapper.getDNSCountByName(dns);
			if (nameCount <= 0) {
				ipCount = webSiteMapper.getDNSCountByUrl(dns);
			}
		}
		// FTP
		else if (moClassID == 92) {
			SiteFtp ftp = webSite.getSiteFtp();
			ftp.setSiteName(webSite.getSiteName());
			nameCount = webSiteMapper.getFTPCountByName(ftp);
			if (nameCount <= 0) {
				ipCount = webSiteMapper.getFTPCountByUrl(ftp);
			}
		}
		// HTTP
		else if (moClassID == 93) {
			SiteHttp http = webSite.getSiteHttp();
			http.setSiteName(webSite.getSiteName());
			nameCount = webSiteMapper.getHttpCountByName(http);
			if (nameCount <= 0) {
				ipCount = webSiteMapper.getHttpCountByUrl2(http);
			}
		}
		//tcp
		else if(moClassID == 94){
			SitePort sitePort = webSite.getSitePort();
			sitePort.setSiteName(webSite.getSiteName());
			nameCount = webSiteMapper.getTCPCountByCondition(sitePort);
			if (nameCount <= 0) {
				ipCount = webSiteMapper.getTCPCountByCondition2(sitePort);
			}
		}
		Map<String, Object> result = new HashMap<String, Object>();
		if (nameCount <= 0) {
			result.put("checkNameFlag", true);
			if (ipCount <= 0) {
				result.put("checkUrlFlag", true);
			} else {
				result.put("checkUrlFlag", false);
			}
		} else {
			result.put("checkNameFlag", false);
		}
		return result;
	}

	@Override
	public Map<String, Object> checkSiteNameAndIPAddr(WebSite webSite) {
		int nameCount = 0;
		int ipCount = 0;
		int moClassID = webSite.getMoClassID();
		// DNS
		if (moClassID == 91) {
			SiteDns dns = webSite.getSiteDns();
			dns.setSiteName(webSite.getSiteName());
			nameCount = webSiteMapper.getDNSCountByName2(dns);
			if (nameCount <= 0) {
				ipCount = webSiteMapper.getDNSCountByDomainName(dns);
			}
		}
		// FTP
		else if (moClassID == 92) {
			SiteFtp ftp = webSite.getSiteFtp();
			ftp.setSiteName(webSite.getSiteName());
			nameCount = webSiteMapper.getFTPCountByName2(ftp);
			if (nameCount <= 0) {
				ipCount = webSiteMapper.getFTPCountByIPAddr(ftp);
			}
		}
		// HTTP
		else if (moClassID == 93) {
			SiteHttp http = webSite.getSiteHttp();
			http.setSiteName(webSite.getSiteName());
			nameCount = webSiteMapper.getHttpCountByName2(http);
			if (nameCount <= 0) {
				ipCount = webSiteMapper.getHttpCountByUrl(http);
			}
		}
		// TCP
		else if (moClassID == 94) {
			SitePort sitePort = webSite.getSitePort();
			sitePort.setSiteName(webSite.getSiteName());
			nameCount = webSiteMapper.getTCPCountByName(sitePort);
			if (nameCount <= 0) {
				ipCount = webSiteMapper.getTCPCountByIPAddr(sitePort);
			}
		}
		Map<String, Object> result = new HashMap<String, Object>();
		if(nameCount <= 0){
			result.put("checkNameFlag", true);
			if(ipCount <= 0){
				result.put("checkUrlFlag", true);
			}else{
				result.put("checkUrlFlag", false);
			}
		}else{
			result.put("checkNameFlag", false);
		}
		return result;
	}

	@Override
	public SiteDns initDnsInfo(int moID) {
		SiteDns dns = webSiteMapper.getSiteDnsByMoId(moID);
		int templateID = perfTaskService.getTemplateID(moID, 91);
		dns.setTemplateID(templateID);
		int taskId = -1;
		PerfTaskInfoBean perfTask = perfTaskInfoMapper.getTaskByMOIDAndClassAndDBName(moID,91,"");
		if(perfTask != null){
			taskId = perfTask.getTaskId();
		}
		dns.setTaskId(taskId);
		return dns;
	}

	@Override
	public Map<String, Object> initFtpInfo(int moID) {
		SiteFtp ftp = webSiteMapper.getSiteFtpByMoId(moID);
		int templateID = perfTaskService.getTemplateID(moID, 92);
		ftp.setTemplateID(templateID);
		int taskId = -1;
		int siteCommunityId = -1;
		PerfTaskInfoBean perfTask = perfTaskInfoMapper.getTaskByMOIDAndClassAndDBName(moID,92,"");
		if(perfTask != null){
			taskId = perfTask.getTaskId();
		}
		ftp.setTaskId(taskId);
		
		SysSiteCommunityBean bean = new SysSiteCommunityBean();
		bean.setIpAddress(ftp.getIpAddr());
		bean.setPort(ftp.getPort());
		SysSiteCommunityBean siteCommunityBean = siteCommunityService.getByIPAndPort(bean);
		boolean isExistSite = false;
		if (null == siteCommunityBean) {
			isExistSite = false;
		} else {
			ftp.setPassword(siteCommunityBean.getPassword());
			ftp.setUserName(siteCommunityBean.getUserName());
			isExistSite = true;
			siteCommunityId = siteCommunityBean.getId();
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isExistSite", isExistSite);
		result.put("ftp", ftp);
		result.put("siteCommunityId", siteCommunityId);
		return result;
	}

	@Override
	public Map<String, Object> initHttpInfo(int moID) {
		SiteHttp http = webSiteMapper.getSiteHttpByMoId(moID);
		int templateID = perfTaskService.getTemplateID(moID, 93);
		http.setTemplateID(templateID);
		int taskId = -1;
		int siteCommunityId = -1;
		PerfTaskInfoBean perfTask = perfTaskInfoMapper.getTaskByMOIDAndClassAndDBName(moID,93,"");
		if(perfTask != null){
			taskId = perfTask.getTaskId();
		}
		http.setTaskId(taskId);
		
		SysSiteCommunityBean bean = new SysSiteCommunityBean();
		bean.setIpAddress(http.getHttpUrl());
		bean.setSiteType(2);
		SysSiteCommunityBean siteCommunityBean = siteCommunityService.getByIPAndSiteType(bean);
		boolean isExistSite = false;
		if (null == siteCommunityBean) {
			isExistSite = false;
		} else {
			http.setRequestMethod(siteCommunityBean.getRequestMethod());
			isExistSite = true;
			siteCommunityId = siteCommunityBean.getId();
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isExistSite", isExistSite);
		result.put("http", http);
		result.put("siteCommunityId", siteCommunityId);
		return result;
	}
	
	@Override
	public SitePort initTcpInfo(int moID) {
		SitePort sitePort = webSiteMapper.getSitePortByMoId(moID);
		int templateID = perfTaskService.getTemplateID(moID, 94);
		sitePort.setTemplateID(templateID);
		int taskId = -1;
		PerfTaskInfoBean perfTask = perfTaskInfoMapper.getTaskByMOIDAndClassAndDBName(moID,94,"");
		if(perfTask != null){
			taskId = perfTask.getTaskId();
		}
		sitePort.setTaskId(taskId);
		return sitePort;
	}

	@Override
	public boolean updateSite(WebSite webSite) {
		boolean updateSite = false;
		int moClassId = webSite.getMoClassID();
		//dns
		if(moClassId == 91){
			SiteDns dns = webSite.getSiteDns();
				try {
					dns.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
					webSiteMapper.updateWebSiteDns(dns);
					updateSite = true;
				} catch (Exception e) {
					updateSite = false;
					logger.error("更新dns对象表异常："+e);
				}
		}
		//ftp
		else if(moClassId == 92){
			SiteFtp ftp = webSite.getSiteFtp();
				try {
					ftp.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
					webSiteMapper.updateWebSiteFtp(ftp);
					updateSite = true;
				} catch (Exception e) {
					updateSite = false;
					logger.error("更新dns对象表异常："+e);
				}
		}
		//http
		else if(moClassId == 93){
			SiteHttp http = webSite.getSiteHttp();
				try {
					http.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
					webSiteMapper.updateWebSiteHttp(http);
					updateSite = true;
				} catch (Exception e) {
					updateSite = false;
					logger.error("更新dns对象表异常："+e);
				}
		}
		//tcp
		else if(moClassId == 94){
			SitePort sitePort = webSite.getSitePort();
				try {
					sitePort.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
					webSiteMapper.updateWebSitePort(sitePort);
					updateSite = true;
				} catch (Exception e) {
					updateSite = false;
					logger.error("更新tcp对象表异常："+e);
				}
		}
		return updateSite;
	}

	@Override
	public List<SitePort> getAllWebSitePort(Page<SitePort> page) {//wsp
		int period=1;
		long curr=0;
		long currTime=getDateNow().getTime();
		List<SitePort> portLst = webSiteMapper.getAllWebSitePort(page);
		if(portLst!=null){
			for (int i = 0; i < portLst.size(); i++) {
				SitePort port = portLst.get(i);
				Date updateAlarmTime = port.getUpdateAlarmTime();
				Date collectTime=port.getCollectTime();
				if(port.getDoIntervals()==null || "".equals(port.getDoIntervals())){
					period=port.getDefDoIntervals()*getMultiple()*60000;
				}else{
					period=port.getDoIntervals()*getMultiple()*1000;
				}
				if (collectTime != null) {
				curr=currTime-port.getCollectTime().getTime();
						if(curr<=period){
							if ("1".equals(port.getAvailable())) {
								port.setAvailableTip("UP");
								port.setAvailablePng("up.png");
							} else if ("2".equals(port.getAvailable())) {
								port.setAvailableTip("DOWN");
								port.setAvailablePng("down.png");
							} 
							if (updateAlarmTime != null) {
								String durationTime = HostComm.getDurationToTime(currTime-updateAlarmTime.getTime());
								port.setDurationTime(durationTime);
							}else{
								port.setDurationTime("");
							}
						}else{
							port.setAvailableTip("未知");
							port.setAvailablePng("unknown.png");
							String durationTime = HostComm.getDurationToTime(currTime-collectTime.getTime());
							port.setDurationTime(durationTime);
						}	
				}else{
					port.setAvailableTip("未知");
					port.setAvailablePng("unknown.png");
					port.setDurationTime("");
				}
				port.setFormatTime(HostComm.getMsToTime(port.getResponseTime()));
			}
		}
		return portLst;
	}

	@Override
	public SitePort getFirstSitePort() {
		return webSiteMapper.getFirstSitePort();
	}

	@Override
	public List<SitePort> getPerfSitePortByMoId(Map<String, Object> paramMap) {
		int period=1;
		long curr=0;
		long currTime=getDateNow().getTime();
		List<SitePort> portLst= webSiteMapper.getPerfSitePortByMoId(paramMap);
		if(portLst!=null){
			for (int i = 0; i < portLst.size(); i++) {
				SitePort port = portLst.get(i);
				Date updateAlarmTime = port.getUpdateAlarmTime();
				Date collectTime=port.getCollectTime();
				if(port.getDoIntervals()==null || "".equals(port.getDoIntervals())){
					period=port.getDefDoIntervals()*getMultiple()*60000;
				}else{
					period=port.getDoIntervals()*getMultiple()*1000;
				}
				if (collectTime != null) {
						curr=currTime-port.getCollectTime().getTime();
						if(curr<=period){
							if ("1".equals(port.getAvailable())) {
								port.setAvailableTip("UP");
								port.setAvailablePng("up.png");
							} else if ("2".equals(port.getAvailable())) {
								port.setAvailableTip("DOWN");
								port.setAvailablePng("down.png");
							} 
							if (updateAlarmTime != null) {
								String durationTime = HostComm.getDurationToTime(currTime-updateAlarmTime.getTime());
								port.setDurationTime(durationTime);
							}else{
								port.setDurationTime("");
							}
						}else{
							port.setAvailableTip("未知");
							port.setAvailablePng("unknown.png");
							String durationTime = HostComm.getDurationToTime(currTime-collectTime.getTime());
							port.setDurationTime(durationTime);
						}	
				}else{
					port.setAvailableTip("未知");
					port.setAvailablePng("unknown.png");
					port.setDurationTime("");
				}
				port.setFormatTime(HostComm.getMsToTime(port.getResponseTime()));
			}
		}
		return portLst;
	}

	@Override
	public SitePort getPortChartAvailability(Map<String, Object> paramMap) {
		return webSiteMapper.getPortChartAvailability(paramMap);
	}

	@Override
	public SitePort getSitePortByMoId(int moID) {
		SitePort portBean = webSiteMapper.getSitePortByMoId(moID);
		Date updateAlarmTime = portBean.getUpdateAlarmTime();
		if (updateAlarmTime != null) {
			String durationTime = HostComm.getMsToTime((getDateNow()).getTime()-updateAlarmTime.getTime());
			portBean.setDurationTime(durationTime);
		}
		return portBean;
	}

	@Override
	public List<SitePort> queryPortPerf(Map<String, Object> paramMap) {
		return webSiteMapper.queryPortPerf(paramMap);
	}
	
	public Date getDateNow(){
		return webSiteMapper.getDateNow();
	}
	public int getMultiple(){
		return webSiteMapper.getConfParamValue();
	}
	@Override
	public int getConfParamValue() {
		return webSiteMapper.getConfParamValue();
	}

	@Override
	public List<WebSite> getAllSites(Map map) {
		return webSiteMapper.getAllSites(map);
	}
	
}