package com.fable.insightview.monitor.website.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.monitor.discover.service.IAddDeviceService;
import com.fable.insightview.monitor.host.entity.KPINameDef;
import com.fable.insightview.monitor.website.entity.SiteDns;
import com.fable.insightview.monitor.website.entity.SiteFtp;
import com.fable.insightview.monitor.website.entity.SiteHttp;
import com.fable.insightview.monitor.website.entity.SitePort;
import com.fable.insightview.monitor.website.entity.WebSite;
import com.fable.insightview.monitor.website.entity.WebSiteKPINameDef;
import com.fable.insightview.monitor.website.service.IWebSiteService;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.page.Page;

@Controller
@RequestMapping("/monitor/webSite")
public class WebSiteInfoController {
	
	@Autowired IWebSiteService webSiteService;
	@Autowired IAddDeviceService addDeviceService;
	private final Logger logger = LoggerFactory.getLogger(WebSiteInfoController.class);
	
	@RequestMapping("/toWebSiteInfo")
	public ModelAndView toWebSiteInfo(HttpServletRequest request,ModelMap map) {
		String flag = request.getParameter("flag");
		String includeType = request.getParameter("includeType");
		map.put("flag",flag);
		map.put("includeType",includeType);
		map.put("navigationBar",request.getParameter("navigationBar"));
		map.put("relationPath", request.getParameter("relationPath"));
		map.put("mOClassID", request.getParameter("mOClassID"));
		return new ModelAndView("monitor/website/webSite_list");
	}
	
	@RequestMapping("/toAllWebSiteInfo")
	public ModelAndView toAllWebSiteInfo(HttpServletRequest request,ModelMap map,String navigationBar) {
		String flag = request.getParameter("flag");
		map.put("flag",flag);
		ModelAndView mv = new ModelAndView("monitor/website/allWebSite_list");
		mv.addObject("navigationBar", navigationBar);
		return mv;
	}
	
	/**
	 * 加载列表页面数据
	 */
	@RequestMapping("/listWebSiteInfo")
	@ResponseBody
	public Map<String, Object> listWebSiteInfo(WebSite webSite)
			throws Exception {
		logger.info("加载列表页面数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<WebSite> page = new Page<WebSite>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("siteName", request.getParameter("siteName"));
		paramMap.put("siteType", request.getParameter("siteType"));
		paramMap.put("responseTimeKPI", WebSiteKPINameDef.RESPONSETIME);
		paramMap.put("dnsStateKPI", WebSiteKPINameDef.DNSSTATE);
		paramMap.put("connsStateKPI", WebSiteKPINameDef.CONNSSTATE);
		paramMap.put("loginStateKPI", WebSiteKPINameDef.LOGINSTATE);
		paramMap.put("portStateKPI", WebSiteKPINameDef.PORTSTATE);
		paramMap.put("jobSiteFTP", KPINameDef.JOBSITEFTP);
		paramMap.put("jobSiteHTTP", KPINameDef.JOBSITEHTTP);
		paramMap.put("jobSiteDNS", KPINameDef.JOBSITEDNS);
		paramMap.put("jobSiteTCP", KPINameDef.JOBSITETCP);
		String includeType = request.getParameter("includeType");
		paramMap.put("includeType", includeType);
//		String moClassID = request.getParameter("moClassID");
//		if (!(moClassID.equals("-1"))) {
//			paramMap.put("moClassID", "(" + moClassID + ")");
//		}
		page.setParams(paramMap);
		List<WebSite> taskList = webSiteService.getAllWebSites(page);
		int total = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", taskList);
		logger.info("加载列表页面数据over");
		return result;
	}
	
	@RequestMapping("/toWebSiteHttpInfo")
	public ModelAndView toWebSiteHttpInfo(HttpServletRequest request,ModelMap map) {
		String flag = request.getParameter("flag");
		map.put("flag",flag);
		map.put("navigationBar",request.getParameter("navigationBar"));
		map.put("relationPath", request.getParameter("relationPath"));
		map.put("mOClassID", request.getParameter("mOClassID"));
		return new ModelAndView("monitor/website/webSiteHttp_list");
	}
	
	/**
	 * 加载列表页面数据
	 */
	@RequestMapping("/listWebSiteHttpInfo")
	@ResponseBody
	public Map<String, Object> listWebSiteHttpInfo(SiteHttp siteHttp)
			throws Exception {
		logger.info("加载列表页面数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<SiteHttp> page = new Page<SiteHttp>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("siteName", request.getParameter("siteName"));
		paramMap.put("siteType", request.getParameter("siteType"));
		paramMap.put("responseTimeKPI", WebSiteKPINameDef.RESPONSETIME);
		paramMap.put("connsStateKPI", WebSiteKPINameDef.CONNSSTATE);
		paramMap.put("MID", KPINameDef.JOBSITEHTTP);
		page.setParams(paramMap);
		List<SiteHttp> taskList = webSiteService.getAllWebSiteHttp(page);
		int total = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", taskList);
		logger.info("加载列表页面数据over");
		return result;
	}
	
	@RequestMapping("/toWebSiteFtpInfo")
	public ModelAndView toWebSiteFtpInfo(HttpServletRequest request,ModelMap map) {
		String flag = request.getParameter("flag");
		map.put("flag",flag);
		map.put("relationPath", request.getParameter("relationPath"));
		map.put("mOClassID", request.getParameter("mOClassID"));
		map.put("navigationBar",request.getParameter("navigationBar"));
		return new ModelAndView("monitor/website/webSiteFtp_list");
	}
	
	/**
	 * 加载列表页面数据
	 */
	@RequestMapping("/listWebSiteFtpInfo")
	@ResponseBody
	public Map<String, Object> listWebSiteFtpInfo(SiteFtp siteFtp)
			throws Exception {
		logger.info("加载列表页面数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<SiteFtp> page = new Page<SiteFtp>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("siteName", request.getParameter("siteName"));
		paramMap.put("siteType", request.getParameter("siteType"));
		paramMap.put("responseTimeKPI", WebSiteKPINameDef.RESPONSETIME);
		paramMap.put("connsStateKPI", WebSiteKPINameDef.CONNSSTATE);
		paramMap.put("loginStateKPI", WebSiteKPINameDef.LOGINSTATE);
		paramMap.put("MID", KPINameDef.JOBSITEFTP);
		page.setParams(paramMap);
		List<SiteFtp> taskList = webSiteService.getAllWebSiteFtp(page);
		int total = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", taskList);
		logger.info("加载列表页面数据over");
		return result;
	}
	
	@RequestMapping("/toWebSiteDnsInfo")
	public ModelAndView toWebSiteDnsInfo(HttpServletRequest request,ModelMap map) {
		String flag = request.getParameter("flag");
		map.put("flag",flag);
		map.put("relationPath", request.getParameter("relationPath"));
		map.put("mOClassID", request.getParameter("mOClassID"));
		map.put("navigationBar",request.getParameter("navigationBar"));
		return new ModelAndView("monitor/website/webSiteDns_list");
	}
	
	/**
	 * 加载列表页面数据
	 */
	@RequestMapping("/listWebSiteDnsInfo")
	@ResponseBody
	public Map<String, Object> listWebSiteDnsInfo(SiteDns siteDns)
			throws Exception {
		logger.info("加载列表页面数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<SiteDns> page = new Page<SiteDns>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("siteName", request.getParameter("siteName"));
		paramMap.put("siteType", request.getParameter("siteType"));
		paramMap.put("responseTimeKPI", WebSiteKPINameDef.RESPONSETIME);
		paramMap.put("dnsStateKPI", WebSiteKPINameDef.DNSSTATE);
		paramMap.put("MID", KPINameDef.JOBSITEDNS);
		page.setParams(paramMap);
		List<SiteDns> taskList = webSiteService.getAllWebSiteDns(page);
		int total = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", taskList);
		logger.info("加载列表页面数据over");
		return result;
	}
	
	/**
	 * 根据moID获取DNS站点信息
	 */
	@RequestMapping("/findSiteDnsInfo")
	@ResponseBody
	public SiteDns findSiteDnsInfo(Integer moID) throws Exception {
		SiteDns siteDns = webSiteService.getSiteDnsByMoId(moID);
		return siteDns;
	}
	
	/**
	 * 根据moID获取FTP站点信息
	 */
	@RequestMapping("/findSiteFtpInfo")
	@ResponseBody
	public SiteFtp findSiteFtpInfo(Integer moID) throws Exception {
		SiteFtp siteFtp = webSiteService.getSiteFtpByMoId(moID);
		return siteFtp;
	}
	
	/**
	 * 根据moID获取HTTP站点信息
	 */
	@RequestMapping("/findSiteHttpInfo")
	@ResponseBody
	public SiteHttp findSiteHttpInfo(Integer moID) throws Exception {
		SiteHttp siteHttp = webSiteService.getSiteHttpByMoId(moID);
		return siteHttp;
	}
	
	/**
	 * 获取第一条DNS站点信息
	 */
	@RequestMapping("/initDnsSiteName")
	@ResponseBody
	public SiteDns initDnsSiteName(Integer moID) throws Exception {
		SiteDns siteDns = webSiteService.getFirstSiteDns();
		return siteDns;
	}
	
	/**
	 * 获取第一条FTP站点信息
	 */
	@RequestMapping("/initFtpSiteName")
	@ResponseBody
	public SiteFtp initFtpSiteName(Integer moID) throws Exception {
		SiteFtp siteFtp = webSiteService.getFirstSiteFtp();
		return siteFtp;
	}
	
	/**
	 * 获取第一条HTTP站点信息
	 */
	@RequestMapping("/initHttpSiteName")
	@ResponseBody
	public SiteHttp initHttpSiteName(Integer moID) throws Exception {
		SiteHttp siteHttp = webSiteService.getFirstSiteHttp();
		return siteHttp;
	}
	
	/**
	 * 跳转至新增界面
	 */
	@RequestMapping("/toShowSiteAdd")
	public ModelAndView toShowSiteAdd() {
		return new ModelAndView("monitor/website/webSite_add");
	}
	
	
	/**
	 * 校验站点名称和监控地址
	 */
	@RequestMapping("/checkSiteNameAndIPAddr")
	@ResponseBody
	public Map<String, Object> checkSiteNameAndIPAddr(WebSite webSite){
		return webSiteService.checkSiteNameAndIPAddr(webSite);
	}
	
	/**
	 * 新增站点
	 */
	@RequestMapping("/addSite")
	@ResponseBody
	public boolean addSite(WebSite webSite,HttpServletRequest request){
		//入站点对象表结果
		String addWebSite = "false";
		//入凭证表结果
		boolean addSiteCommunity = true;
		//采集任务结果
		boolean addPerfTask = false;
		
		int siteType = webSite.getSiteType();
		
		//入站点对象表
		Map<String, Object> addWebSiteMap = addDeviceService.addSite(webSite);
		addWebSite = (String) addWebSiteMap.get("addSiteResult");
		int moId = Integer.parseInt(String.valueOf(addWebSiteMap.get("moId")));
		
		if("true".equals(addWebSite)){
			String isExistSite = request.getParameter("isExistSite");
			//入凭证表
			if(siteType == 1 || siteType == 3){
				if ("true".equals(isExistSite)) {
					int siteCommunityId = Integer.parseInt(request.getParameter("siteCommunityId"));
					addSiteCommunity = addDeviceService.updateSiteCommunity(webSite, siteCommunityId);
				}else{
					addSiteCommunity = addDeviceService.addSiteCommunity(webSite);
				}
			}
			
			if(addSiteCommunity == true){
				//采集任务
				webSite.setMoID(moId);
				int templateID = Integer.parseInt(request.getParameter("templateID"));
				String moTypeLstJson = request.getParameter("moTypeLstJson");
				addPerfTask = addDeviceService.addSitePerfTask(webSite,templateID,moTypeLstJson);
			}
		}
		
		if("true".equals(addWebSite) && addSiteCommunity == true && addPerfTask == true){
			return true;
		}
		return false;
	}
	
	/**
	 * 跳转至新增界面
	 */
	@RequestMapping("/toShowSiteModify")
	public ModelAndView toShowSiteModify(HttpServletRequest request,ModelMap map) {
		String moID = request.getParameter("moID");
		String siteType = request.getParameter("siteType");
		map.put("moID",moID);
		map.put("siteType",siteType);
		return new ModelAndView("monitor/website/webSite_modify");
	}
	
	/**
	 * 初始化DNS站点信息
	 */
	@RequestMapping("/initDnsInfo")
	@ResponseBody
	public SiteDns initDnsInfo(int moID){
		return webSiteService.initDnsInfo(moID);
	}
	
	/**
	 * 初始化FTP站点信息
	 */
	@RequestMapping("/initFtpInfo")
	@ResponseBody
	public Map<String, Object> initFtpInfo(int moID){
		return webSiteService.initFtpInfo(moID);
	}
	
	/**
	 * 初始化HTTP站点信息
	 */
	@RequestMapping("/initHttpInfo")
	@ResponseBody
	public Map<String, Object> initHttpInfo(int moID){
		return webSiteService.initHttpInfo(moID);
	}
	
	/**
	 * 初始化tcp站点信息
	 */
	@RequestMapping("/initTcpInfo")
	@ResponseBody
	public SitePort initTcpInfo(int moID){
		return webSiteService.initTcpInfo(moID);
	}
	/**
	 * 更新站点
	 */
	@RequestMapping("/updateSite")
	@ResponseBody
	public boolean updateSite(WebSite webSite,HttpServletRequest request){
		//入凭证表结果
		boolean addSiteCommunity = true;
		//采集任务结果
		boolean addPerfTask = false;
		//更新站点信息
		boolean updateResult =  webSiteService.updateSite(webSite);
		
		if(updateResult == true){
			int siteType = webSite.getSiteType();
			String isExistSite = request.getParameter("isExistSite");
			//入凭证表
			if(siteType == 1 || siteType == 3){
				if ("true".equals(isExistSite)) {
					int siteCommunityId = Integer.parseInt(request.getParameter("siteCommunityId"));
					addSiteCommunity = addDeviceService.updateSiteCommunity(webSite, siteCommunityId);
				}else{
					addSiteCommunity = addDeviceService.addSiteCommunity(webSite);
				}
			}
			
			if(addSiteCommunity == true){
				//采集任务
				int templateID = Integer.parseInt(request.getParameter("templateID"));
				String moTypeLstJson = request.getParameter("moTypeLstJson");
				addPerfTask = addDeviceService.addSitePerfTask(webSite, templateID, moTypeLstJson);
			}
		}
		
		if(updateResult == true && addSiteCommunity == true && addPerfTask == true){
			return true;
		}
		return false;
	}
	
	@RequestMapping("/toWebSitePortInfo")
	public ModelAndView toWebSitePortInfo(HttpServletRequest request,ModelMap map) {
		String flag = request.getParameter("flag");
		map.put("flag",flag);
		map.put("relationPath", request.getParameter("relationPath"));
		map.put("mOClassID", request.getParameter("mOClassID"));
		map.put("navigationBar",request.getParameter("navigationBar"));
		return new ModelAndView("monitor/website/webSitePort_list");
	}
	
	/**
	 * 加载列表页面数据
	 */
	@RequestMapping("/listWebSitePortInfo")
	@ResponseBody
	public Map<String, Object> listWebSitePortInfo(SitePort sitePort)
			throws Exception {
		logger.info("加载列表页面数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<SitePort> page = new Page<SitePort>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("siteName", request.getParameter("siteName"));
		paramMap.put("siteType", request.getParameter("siteType"));
		paramMap.put("responseTimeKPI", WebSiteKPINameDef.RESPONSETIME);
		paramMap.put("portStateKPI", WebSiteKPINameDef.PORTSTATE);
		paramMap.put("MID", KPINameDef.JOBSITETCP);
		page.setParams(paramMap);
		List<SitePort> portList = webSiteService.getAllWebSitePort(page);
		int total = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", portList);
		logger.info("加载列表页面数据over");
		return result;
	}
	
	/**
	 * 根据moID获取port站点信息
	 */
	@RequestMapping("/findSitePortInfo")
	@ResponseBody
	public SitePort findSitePortInfo(Integer moID) throws Exception {
		SitePort sitePort = webSiteService.getSitePortByMoId(moID);
		return sitePort;
	}
	
	/**
	 * 获取第一条port站点信息
	 */
	@RequestMapping("/initPortSiteName")
	@ResponseBody
	public SitePort initPortSiteName(Integer moID) throws Exception {
		SitePort sitePort = webSiteService.getFirstSitePort();
		return sitePort;
	}
}
