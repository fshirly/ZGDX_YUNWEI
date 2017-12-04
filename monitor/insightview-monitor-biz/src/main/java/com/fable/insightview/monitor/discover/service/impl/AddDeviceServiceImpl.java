package com.fable.insightview.monitor.discover.service.impl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.fable.insightview.monitor.discover.service.IAddDeviceService;
import com.fable.insightview.monitor.dispatcher.Dispatcher;
import com.fable.insightview.monitor.dispatcher.constants.TaskType;
import com.fable.insightview.monitor.dispatcher.facade.ManageFacade;
import com.fable.insightview.monitor.dispatcher.notify.TaskDispatchNotification;
import com.fable.insightview.monitor.dispatcher.server.TaskDispatcherServer;
import com.fable.insightview.monitor.molist.entity.SysMoInfoBean;
import com.fable.insightview.monitor.molist.entity.SysMonitorsTemplateUsedBean;
import com.fable.insightview.monitor.perf.entity.MoInfoBean;
import com.fable.insightview.monitor.perf.entity.PerfTaskInfoBean;
import com.fable.insightview.monitor.perf.mapper.PerfPollTaskMapper;
import com.fable.insightview.monitor.perf.mapper.PerfTaskInfoMapper;
import com.fable.insightview.monitor.perf.mapper.SysMonitorsOfMOClassMapper;
import com.fable.insightview.monitor.perf.service.IObjectPerfConfigService;
import com.fable.insightview.monitor.util.SiteUtill;
import com.fable.insightview.monitor.utils.DbConnection;
import com.fable.insightview.monitor.website.entity.SiteDns;
import com.fable.insightview.monitor.website.entity.SiteFtp;
import com.fable.insightview.monitor.website.entity.SiteHttp;
import com.fable.insightview.monitor.website.entity.SitePort;
import com.fable.insightview.monitor.website.entity.SysSiteCommunityBean;
import com.fable.insightview.monitor.website.entity.WebSite;
import com.fable.insightview.monitor.website.mapper.SysSiteCommunityMapper;
import com.fable.insightview.monitor.website.mapper.WebSiteMapper;
import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;

/**
 * 新增监测对象
 *
 */
@Service
public class AddDeviceServiceImpl implements IAddDeviceService {
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final Logger logger = LoggerFactory.getLogger(AddDeviceServiceImpl.class);
	@Autowired WebSiteMapper webSiteMapper;
	@Autowired SysSiteCommunityMapper siteCommunityMapper;
	@Autowired PerfPollTaskMapper perfPollTaskMapper;
	@Autowired SysMonitorsOfMOClassMapper monitorsOfMOClassMapper;
	@Autowired PerfTaskInfoMapper perfTaskInfoMapper;
	@Autowired IObjectPerfConfigService objectPerfConfigService;

	@Override
	public Map<String, Object> addSite(WebSite webSite) {
		//入站点对象总表结果
		boolean addWebSite = false;
		//入单个站点表结果
		boolean addSingleSite = false;
		//站点总表id
		int siteId = -1;
		int siteType = webSite.getSiteType();
		//单表id
		int moId = -1;
		//入站点对象总表
		try {
			int webSiteMoId = DbConnection.getID("MO", 1);
			webSite.setMoID(webSiteMoId);
			webSiteMapper.insertWebSite(webSite);
			siteId = webSite.getMoID();
			addWebSite = true;
		} catch (Exception e) {
			addWebSite = false;
			logger.error("入站点总表异常：" + e);
		}
		//入ftp
		if(siteType == 1){
			SiteFtp ftp = webSite.getSiteFtp();
			int ftpMoId = DbConnection.getID("MO", 1);;
			ftp.setMoID(ftpMoId);
			ftp.setParentMOID(siteId);
			ftp.setCreateTime(new Timestamp(System.currentTimeMillis()));
			ftp.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
			try {
				webSiteMapper.insertWebSiteFtp(ftp);
				moId = ftp.getMoID();
				addSingleSite = true;
			} catch (Exception e) {
				addSingleSite = false;
				logger.error("入ftp站点表异常：" + e);
			}
		}
		//入 DNS
		else if(siteType == 2){
			SiteDns dns = webSite.getSiteDns();
			int dnsMoId = DbConnection.getID("MO", 1);;
			dns.setMoID(dnsMoId);
			dns.setParentMOID(siteId);
			dns.setCreateTime(new Timestamp(System.currentTimeMillis()));
			dns.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
			try {
				webSiteMapper.insertWebSiteDns(dns);
				moId = dns.getMoID();
				addSingleSite = true;
			} catch (Exception e) {
				addSingleSite = false;
				logger.error("入dns站点表异常：" + e);
			}
		}
		//入 HTTP
		else if(siteType == 3){
			SiteHttp http = webSite.getSiteHttp();
			int httpMoId = DbConnection.getID("MO", 1);;
			http.setMoID(httpMoId);
			http.setParentMOID(siteId);
			http.setCreateTime(new Timestamp(System.currentTimeMillis()));
			http.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
			try {
				webSiteMapper.insertWebSiteHttp(http);
				moId = http.getMoID();
				addSingleSite = true;
			} catch (Exception e) {
				addSingleSite = false;
				logger.error("入http站点表异常：" + e);
			}
		}
		//入 TCP
		else if(siteType == 4){
			SitePort sitePort = webSite.getSitePort();
			int portMoId = DbConnection.getID("MO", 1);
			sitePort.setMoID(portMoId);
			sitePort.setParentMOID(siteId);
			sitePort.setPortType(1);
			sitePort.setCreateTime(new Timestamp(System.currentTimeMillis()));
			sitePort.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
			try {
				webSiteMapper.insertWebSitePort(sitePort);
				moId = sitePort.getMoID();
				addSingleSite = true;
			} catch (Exception e) {
				addSingleSite = false;
				logger.error("入tcp站点表异常：" + e);
			}
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		if(addWebSite == true && addSingleSite == true){
			result.put("addSiteResult", "true");
		}else{
			result.put("addSiteResult", "false");
		}
		result.put("moId", moId);
		return result;
	}

	@Override
	public boolean addSiteCommunity(WebSite webSite) {
		int siteType = webSite.getSiteType();
		SysSiteCommunityBean siteCommunityBean = new SysSiteCommunityBean();
		try {
			// 入ftp
			if (siteType == 1) {
				if (webSite.getSiteFtp().getIsAuth() == 2) {
					siteCommunityBean.setSiteType(1);
					siteCommunityBean.setIpAddress(webSite.getSiteFtp()
							.getIpAddr());
					siteCommunityBean.setPort(webSite.getSiteFtp().getPort());
					siteCommunityBean.setUserName(webSite.getSiteFtp()
							.getUserName());
					siteCommunityBean.setPassword(webSite.getSiteFtp()
							.getPassword());
					siteCommunityMapper.insertSiteCommunity(siteCommunityBean);
				}
			}
			// 入 HTTP
			else if (siteType == 3) {
				siteCommunityBean.setSiteType(2);
				siteCommunityBean.setPort(null);
				siteCommunityBean.setPassword(null);
				siteCommunityBean.setPassword(null);
				siteCommunityBean.setIpAddress(webSite.getSiteHttp()
						.getHttpUrl());
				siteCommunityBean.setRequestMethod(webSite.getSiteHttp()
						.getRequestMethod());
				siteCommunityMapper.insertSiteCommunity(siteCommunityBean);
			}

			return true;
		} catch (Exception e) {
			logger.error("新增站点凭证表异常：" + e);
		}
		return false;
	}

	@Override
	public boolean updateSiteCommunity(WebSite webSite,int siteCommunityId) {
		int siteType = webSite.getSiteType();
		SysSiteCommunityBean siteCommunityBean = new SysSiteCommunityBean();
		siteCommunityBean.setId(siteCommunityId);
		// 入ftp
		if (siteType == 1) {
			if (webSite.getSiteFtp().getIsAuth() == 2) {
				siteCommunityBean.setSiteType(1);
				siteCommunityBean.setIpAddress(webSite.getSiteFtp()
						.getIpAddr());
				siteCommunityBean.setPort(webSite.getSiteFtp().getPort());
				siteCommunityBean.setUserName(webSite.getSiteFtp()
						.getUserName());
				siteCommunityBean.setPassword(webSite.getSiteFtp()
						.getPassword());
			}
		}
		// 入 HTTP
		else if (siteType == 3) {
			siteCommunityBean.setSiteType(2);
			siteCommunityBean.setIpAddress(webSite.getSiteHttp()
					.getHttpUrl());
			siteCommunityBean.setRequestMethod(webSite.getSiteHttp()
					.getRequestMethod());
		}
		try {
			siteCommunityMapper.updateSiteCommunity(siteCommunityBean);
			return true;
		} catch (Exception e) {
			logger.error("更新站点凭证表异常：" + e);
		}
		return false;
	}

	@Override
	public boolean addSitePerfTask(WebSite webSite,int templateID,String moTypeLstJson) {
		// 新增采集任务结果
		boolean addTaskFlag = false;
		//新增监测器结果
		boolean insertMoFlag = false;
		//删除监测器结果
		boolean deleteMoFlag = true;
		// 采集任务id
		int taskId = -1;
		int moId = webSite.getMoID();
		int moClassID = webSite.getMoClassID();
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
				.getSession().getAttribute("sysUserInfoBeanOfSession");
		PerfTaskInfoBean perfTask = new PerfTaskInfoBean();
		perfTask = perfTaskInfoMapper.getTaskByMOIDAndClassAndDBName(moId,moClassID,"");
		
		//没有采集任务
		if(perfTask == null){
			PerfTaskInfoBean perfTask2 = new PerfTaskInfoBean();
			perfTask2.setMoId(moId);
			perfTask2.setStatus(1);
			perfTask2.setOperateStatus(1);// 操作状态
			perfTask2.setProgressStatus(1);// 进度状态
			perfTask2.setLastOPResult(0);
			perfTask2.setCollectorId(-1);
			perfTask2.setOldCollectorId(-1);
			perfTask2.setCreator(sysUserInfoBeanTemp.getId());
			perfTask2.setCreateTime(dateFormat.format(new Date()));
			perfTask2.setMoClassId(moClassID);
			try {
				perfPollTaskMapper.insertTaskInfo(perfTask2);
				taskId = perfTask2.getTaskId();
				addTaskFlag = true;
			} catch (Exception e) {
				addTaskFlag = false;
				logger.error("新增站点采集任务异常：" + e);
			}
		}
		//已经存在采集任务的 更新采集任务
		else{
			taskId = perfTask.getTaskId();
			//删除原来采集任务的监测器
			perfTaskInfoMapper.deleteMoList(taskId);
			deleteMoFlag = true;
			
			// 更新采集任务
			perfTask.setMoId(moId);
			perfTask.setStatus(perfTask.getStatus());
			perfTask.setCreator(sysUserInfoBeanTemp.getId());
			perfTask.setCreateTime(dateFormat.format(new Date()));
			perfTask.setTaskId(perfTask.getTaskId());
			perfTask.setOperateStatus(2); // 操作状态
			perfTask.setMoClassId(perfTask.getMoClassId());
			perfTask.setProgressStatus(1);
			perfTask.setLastStatusTime(dateFormat.format(new Date()));// 最近状态时间
			try {
				perfTaskInfoMapper.updateTask(perfTask);
				addTaskFlag = true;
			} catch (Exception e) {
				addTaskFlag = false;
				logger.error("新增站点采集任务异常：" + e);
			}
		}
		
		//添加监测器
		insertMoFlag = this.insertMonitors(webSite,taskId,templateID,moTypeLstJson);
		
		if(addTaskFlag == true && insertMoFlag == true && deleteMoFlag == true){
			Dispatcher dispatcher = Dispatcher.getInstance();
			ManageFacade facade = dispatcher.getManageFacade();

			List<TaskDispatcherServer> servers = facade
					.listActiveServersOf(TaskDispatcherServer.class);
			if (servers.size() > 0) {
				String topic = "TaskDispatch";
				TaskDispatchNotification message = new TaskDispatchNotification();
				message.setDispatchTaskType(TaskType.TASK_COLLECT);
				facade.sendNotification(servers.get(0).getIp(), topic, message);
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean testSite(WebSite webSite) {
		SiteUtill siteUtill = new SiteUtill();
		int moClassId = webSite.getMoClassID();
		boolean testResult = false;
		Map<String, String> testMap = new HashMap<String, String>();
		if(moClassId == 91){
			testMap = siteUtill.dnsConnect(webSite.getSiteDns().getDomainName());
			String dnsState = testMap.get("DnsState");
			String ipAddr = testMap.get("IPAddr");
			String inputIPAddr = webSite.getSiteDns().getIpAddr();
			if(inputIPAddr == null || "".equals(inputIPAddr)){
				if("2".equals(dnsState)){
					testResult = false;
				}else{
					testResult = true;
				}
			}else{
				if("1".equals(dnsState) && inputIPAddr.equals(ipAddr)){
					testResult = true;
				}else{
					testResult = false;
				}
			}
		}else if(moClassId == 92){
			try {
				testMap = siteUtill.ftpConnect(webSite.getSiteFtp().getIpAddr(), webSite.getSiteFtp().getPort(), webSite.getSiteFtp().getUserName(), webSite.getSiteFtp().getPassword());
				String connsState = testMap.get("ConnsState");
				String loginState = testMap.get("LoginState");
				if("2".equals(connsState) || "2".equals(loginState)){
					testResult = false;
				}else{
					testResult = true;
				}
			} catch (Exception e) {
				logger.error("ftp测试异常："+e);
				testResult = false;
			}
		}else if(moClassId == 93){
			int requestMethod= webSite.getSiteHttp().getRequestMethod();
			String rMthd = "GET";
			if(requestMethod == 1){
				rMthd = "GET";
			}else if(requestMethod == 2){
				rMthd = "POST";
			}else if(requestMethod == 3){
				rMthd = "HEAD";
			}
			testMap = siteUtill.httpConnect(webSite.getSiteHttp().getHttpUrl(), rMthd);
			String connsState = testMap.get("ConnsState");
			if("2".equals(connsState)){
				testResult = false;
			}else{
				testResult = true;
			}
		}else if(moClassId == 94){
			try {
				testMap = siteUtill.tcpConnect(webSite.getSitePort().getIpAddr(), webSite.getSitePort().getPort());
				String PortState = testMap.get("PortState");
				if("2".equals(PortState)){
					testResult = false;
				}else{
					testResult = true;
				}
			} catch (Exception e) {
				logger.error("tcp测试异常："+e);
				testResult = false;
			}
		}
		return testResult;
	}

	@Override
	public Map<String, Object> isExistSite(WebSite webSite) {
		int moClassId = webSite.getMoClassID();
		String isExistSite = "false";
		String updateSite = "false";
		int moID = -1;
		//dns
		if(moClassId == 91){
			SiteDns dns = webSite.getSiteDns();
			SiteDns siteDns = webSiteMapper.getDnsByNameAndDomainName(dns);
			if(siteDns != null){
				isExistSite = "true";
				moID = siteDns.getMoID();
				dns.setMoID(moID);
				dns.setParentMOID(siteDns.getParentMOID());
				dns.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
				try {
					webSiteMapper.updateWebSiteDns(dns);
					updateSite = "true";
				} catch (Exception e) {
					updateSite = "false";
					logger.error("更新dns对象表异常："+e);
				}
			}else{
				isExistSite = "false";
			}
		}
		//ftp
		else if(moClassId == 92){
			SiteFtp ftp = webSite.getSiteFtp();
			SiteFtp siteFtp = webSiteMapper.getFtpByNameAndIpAndPort(ftp);
			if(siteFtp != null){
				isExistSite = "true";
				moID = siteFtp.getMoID();
				ftp.setMoID(moID);
				ftp.setParentMOID(siteFtp.getParentMOID());
				ftp.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
				try {
					webSiteMapper.updateWebSiteFtp(ftp);
					updateSite = "true";
				} catch (Exception e) {
					updateSite = "false";
					logger.error("更新dns对象表异常："+e);
				}
			}else{
				isExistSite = "false";
			}
		}
		//http
		else if(moClassId == 93){
			SiteHttp http = webSite.getSiteHttp();
			SiteHttp siteHttp = webSiteMapper.getHttpByNameUrl(http);
			if(siteHttp != null){
				isExistSite = "true";
				moID = siteHttp.getMoID();
				http.setMoID(moID);
				http.setParentMOID(siteHttp.getParentMOID());
				http.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
				try {
					webSiteMapper.updateWebSiteHttp(http);
					updateSite = "true";
				} catch (Exception e) {
					updateSite = "false";
					logger.error("更新dns对象表异常："+e);
				}
			}else{
				isExistSite = "false";
			}
		}
		//tcp
		else if(moClassId == 94){
			SitePort port = webSite.getSitePort();
			SitePort sitePort = webSiteMapper.getTcpByNameAndIpAndPort(port);
			if(sitePort != null){
				isExistSite = "true";
				moID = sitePort.getMoID();
				port.setMoID(moID);
				port.setParentMOID(sitePort.getParentMOID());
				port.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
				try {
					webSiteMapper.updateWebSitePort(port);
					updateSite = "true";
				} catch (Exception e) {
					updateSite = "false";
					logger.error("更新ftp对象表异常："+e);
				}
			}
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isExist", isExistSite);
		result.put("moID", moID);
		result.put("updateSite", updateSite);
		return result;
	}

	public boolean insertMonitors(WebSite site,int taskId,int templateID,String moTypeLstJson){
		boolean insertMoFlag = false;
		//绑定模板结果
		boolean setTemplateFlag = false;
		
		//绑定模板
		SysMonitorsTemplateUsedBean templateUsedBean = new SysMonitorsTemplateUsedBean();
		templateUsedBean.setMoID(site.getMoID());
		templateUsedBean.setMoClassID(site.getMoClassID());
		templateUsedBean.setTemplateID(templateID);
		setTemplateFlag = objectPerfConfigService.setTmemplate(templateUsedBean);
		
		if(setTemplateFlag == true){
			//没有使用模板
			if(templateID == -1){
				String[] moList = site.getMoList().split(",");
				String[] moIntervalList = site.getMoIntervalList().split(",");
				String[] moTimeUnitList = site.getMoTimeUnitList().split(",");
				int moInterval = -1;
				for (int i = 0; i < moList.length; i++) {
					logger.info("新增任务：监测器ID=" + moList[i] + "的监测器入库");
					MoInfoBean moBean = new MoInfoBean();
					moBean.setMid(Integer.parseInt(moList[i]));
					moBean.setTaskId(taskId);
					if(Integer.parseInt(moTimeUnitList[i]) == 1){
						moInterval = Integer.parseInt(moIntervalList[i]) * 60;
					}else if(Integer.parseInt(moTimeUnitList[i]) == 2){
						moInterval = Integer.parseInt(moIntervalList[i]) * 3600;
					}else if(Integer.parseInt(moTimeUnitList[i]) == 3){
						moInterval = Integer.parseInt(moIntervalList[i]) * 3600 * 24;
					}
					moBean.setDoIntervals(moInterval);
					
					try {
						perfPollTaskMapper.insertTaskMo(moBean);
						insertMoFlag = true;
					} catch (Exception e) {
						logger.error("新增监测器异常：" + e);
						insertMoFlag = false;
						break;
					}
				}
			}
			//使用模板
			else{
				List<String> moTypeList = JSON.parseArray(moTypeLstJson,String.class);
				for (int i = 0; i <moTypeList.size(); i++) {
					String[] moTypeInfoLst = moTypeList.get(i).split(",");
					int monitorTypeID = Integer.parseInt(moTypeInfoLst[0]);
					int timeInterval = Integer.parseInt(moTypeInfoLst[2]);
					int timeUnit = Integer.parseInt(moTypeInfoLst[3]);
					
					List<SysMoInfoBean> monitorList = objectPerfConfigService.getMoList(site.getMoID(), site.getMoClassID());
					for (int j = 0; j < monitorList.size(); j++) {
						if(monitorList.get(j).getMonitorTypeID() == monitorTypeID){
							MoInfoBean moBean = new MoInfoBean();
							moBean.setMid(monitorList.get(j).getMid());
							moBean.setTaskId(taskId);
							int interval = timeInterval;
							if(timeUnit == 1){
								interval = timeInterval * 60;
							}else if(timeUnit == 2){
								interval = timeInterval * 3600;
							}else if(timeUnit == 3){
								interval = timeInterval * 3600 * 24;
							}
							moBean.setDoIntervals(interval);
							try {
								perfPollTaskMapper.insertTaskMo(moBean);
								insertMoFlag = true;
							} catch (Exception e) {
								logger.error("新增监测器异常：" + e);
								insertMoFlag = false;
								break;
							}
						}
					}
				}
			}
		}
		if(insertMoFlag == true && setTemplateFlag == true){
			return true;
		}
		return false;
	}
}
