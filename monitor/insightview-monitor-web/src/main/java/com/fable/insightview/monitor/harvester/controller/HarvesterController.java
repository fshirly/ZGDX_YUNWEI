package com.fable.insightview.monitor.harvester.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.monitor.dispatcher.Dispatcher;
import com.fable.insightview.monitor.dispatcher.facade.ManageFacade;
import com.fable.insightview.monitor.dispatcher.server.Server;
import com.fable.insightview.monitor.harvester.entity.SysServerHostInfo;
import com.fable.insightview.monitor.harvester.entity.SysServerInstallService;
import com.fable.insightview.monitor.harvester.mapper.HarvesterMapper;
import com.fable.insightview.monitor.harvester.service.IHarvesterService;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.monitor.servermanageddomain.entity.ServerManagedDomainBean;
import com.fable.insightview.monitor.servermanageddomain.service.IServerManagedDomainService;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.managedDomain.entity.ManagedDomain;
import com.fable.insightview.platform.managedDomain.service.IManagedDomainService;

@Controller
@RequestMapping("/monitor/harvesterManage")
public class HarvesterController {
	private Logger logger = LoggerFactory.getLogger(HarvesterController.class);

	@Autowired
	HarvesterMapper harMaper;
	@Autowired
	IManagedDomainService managedDomainService;
	@Autowired
	IServerManagedDomainService serverDomainService;
	@Autowired
	IHarvesterService harvesterService;

	@RequestMapping("/toHarvestInfo")
	public ModelAndView toHarvestLs(String navigationBar) {
		ModelAndView mv= new ModelAndView("monitor/harvester/harvestInfo_list");
		mv.addObject("navigationBar", navigationBar);
		return mv;
	}

	/**
	 * 采集机视角列表
	 * 
	 * @return
	 */
	@RequestMapping("/listHarvestInfo")
	@ResponseBody
	public Map<String, Object> listHarvestInfo() throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		List<SysServerInstallService> harvestLsinfo = harMaper
				.selectHarvestInfo();
	/*	for (int i = 0; i < harvestLsinfo.size(); i++) {
			String ip = harvestLsinfo.get(i).getIpaddress();
			String processName = harvestLsinfo.get(i).getProcessName();
			int status = harvesterService.getStatus(ip, processName);
//			System.out.println("ip="+ip+",processName="+processName+",status="+status);
			harvestLsinfo.get(i).setServicestatus(status);
		}*/
		harvestLsinfo = harvesterService.getharvestLsinfo(harvestLsinfo);
		result.put("rows", harvestLsinfo);
		return result;
	}

	/**
	 * 采集任务视角列表
	 * 
	 * @return
	 */
	@RequestMapping("/listHarvestTaskInfo")
	@ResponseBody
	public Map<String, Object> listHarvestTaskInfo() throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		List<SysServerInstallService> harvestTaskLsinfo = harMaper
				.selectHarvestTaskInfo();
		
		for (int i = 0; i < harvestTaskLsinfo.size(); i++) {
			String ip = harvestTaskLsinfo.get(i).getIpaddress();
			String processName = harvestTaskLsinfo.get(i).getProcessName();
			int status = harvesterService.getStatus(ip, processName);
			harvestTaskLsinfo.get(i).setServicestatus(status);
		}
		result.put("rows", harvestTaskLsinfo);
		return result;
	}

	/**
	 * 采集机树
	 */
	@RequestMapping("/findHostLst")
	@ResponseBody
	public Map<String, Object> findHostLst() {
		List<SysServerHostInfo> hostList = harMaper.getServerHostLst();
		List<SysServerHostInfo> resultLst = new ArrayList<SysServerHostInfo>();
		int status = 2;
		for (int i = 0; i < hostList.size(); i++) {
			Dispatcher dispatcher = Dispatcher.getInstance();
			ManageFacade facade = dispatcher.getManageFacade();
			
			//获得所有的服务
			List<Server> allServers = facade.listAllActiveServers();
			
			for (Server server : allServers) {
				if(hostList.get(i).getIpaddress().equals(server.getIp()) && ("PerfPoll".equals(server.getProcessName()) || "Discovery".equals(server.getProcessName()) || "TrapServer".equals(server.getProcessName())) ){
					status = 1;
					break;
				}else{
					continue;
				}
			}
			if(status == 1){
				resultLst.add(hostList.get(i));
			}
			status = 2;
		}
		for (SysServerHostInfo host : resultLst) {
			host.setParentId(0);
		}
		String hostListJson = JsonUtil.toString(resultLst);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("hostListJson", hostListJson);
		return result;
	}

	/**
	 * 加载表格数据
	 */
	@RequestMapping("/listDomain")
	@ResponseBody
	public Map<String, Object> listDomain(SysServerHostInfo host) {

		logger.debug("开始加载数据。。。。。。。。");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<SysServerHostInfo> page = new Page<SysServerHostInfo>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		//获得所有的服务
		Dispatcher dispatcher = Dispatcher.getInstance();
		ManageFacade facade = dispatcher.getManageFacade();
		List<Server> allServers = facade.listAllActiveServers();
		String ips = "";
		for (int i = 0; i < allServers.size(); i++) {
			if(("PerfPoll".equals(allServers.get(i).getProcessName()) || "Discovery".equals(allServers.get(i).getProcessName()) || "TrapServer".equals(allServers.get(i).getProcessName())) ){
				ips = ips + "'" +allServers.get(i).getIp() + "'" + ",";
			}
		}
		if(!"".equals(ips) && ips.length() > 0){
			ips = ips.substring(0, ips.lastIndexOf(","));
		}
		paramMap.put("ips", ips);
		paramMap.put("serverid", host.getServerid());
		page.setParams(paramMap);
		
		List<SysServerHostInfo> domainList = harMaper.getServerDomain(page);

		int total = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", domainList);
		logger.info("加载数据结束。。。");
		return result;
	}

	@RequestMapping("/toDomainConfig")
	public ModelAndView toDomainConfig(HttpServletRequest request) {

		logger.info("配置采集机所辖管理域。。。start");
		String serverid = request.getParameter("serverid");
		logger.info("采集机id为：" + serverid);
		request.setAttribute("serverid", serverid);
		return new ModelAndView("monitor/harvester/serverManagedDomain");
	}

	@RequestMapping("/getDomainTreeVal")
	@ResponseBody
	public Map<String, Object> getDomainTreeVal(int serverid) {
		// 管理域树
		List<ManagedDomain> menuLst = managedDomainService
				.getManagedDomainTreeVal();
		for (int i = 0; i < menuLst.size(); i++) {
			if(menuLst.get(i).getDomainId() == 1){
				ManagedDomain allDomain = menuLst.get(i);
				allDomain.setDomainId(0);
				allDomain.setParentId("-1");
				allDomain.setDomainName("所有管理域");
			}
			if("1".equals(menuLst.get(i).getParentId())){
				menuLst.get(i).setParentId("0");
			}
		}
		String menuLstJson = JsonUtil.toString(menuLst);
		logger.info("管理域树Json：" + menuLstJson);

		String domainIds = serverDomainService.getDomainIds(serverid);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("menuLstJson", menuLstJson);
		result.put("domainIds", domainIds);
		return result;
	}

	@RequestMapping("/addDomain")
	@ResponseBody
	public boolean addDomain(ServerManagedDomainBean bean,
			HttpServletRequest request) {
		String selDomainId = request.getParameter("selDomainId");
		int serverId = bean.getServerId();
		boolean delFlag = true;
		boolean insertFlag = true;

		logger.info("删除采集机id为:" + serverId + " 原来配置的管理域");
		try {
			serverDomainService.delByServerId(serverId);
			delFlag = true;
		} catch (Exception e) {
			delFlag = false;
			logger.error("删除原有的管理域异常：" + e);
		}

		logger.info("添加采集机id为:" + serverId + " 新配置的管理域" + selDomainId);
		if (!"".equals(selDomainId) && selDomainId != null) {
			String[] domainLst = selDomainId.split(",");
			boolean result = this.isHave(domainLst);
			if (result == false) {
				for (int i = 0; i < domainLst.length; i++) {
					logger.info("管理域的id为：" + domainLst[i]);
					ServerManagedDomainBean serverManagedDomainBean = new ServerManagedDomainBean();
					serverManagedDomainBean.setServerId(serverId);
					serverManagedDomainBean.setDomainId(Integer
							.parseInt(domainLst[i]));
					insertFlag = serverDomainService
							.insertInfo(serverManagedDomainBean);
				}
			} else {
				ServerManagedDomainBean serverManagedDomainBean = new ServerManagedDomainBean();
				serverManagedDomainBean.setServerId(serverId);
				serverManagedDomainBean.setDomainId(1);
				insertFlag = serverDomainService
						.insertInfo(serverManagedDomainBean);
			}
		}

		logger.info("删除的结果：" + delFlag + " , 新增的结果：" + insertFlag);
		if (delFlag == true && insertFlag == true) {
			return true;
		}
		return false;
	}

	/**
	 * 判断选择的管理域中是否包含“所有”
	 */
	public boolean isHave(String[] domainLst) {
		for (int i = 0; i < domainLst.length; i++) {
			if ("0".equals(domainLst[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 强制重启采集程序
	 */
	@RequestMapping("/doRestart")
	@ResponseBody
	public Map<String, Object> doRestart(
			SysServerInstallService sysServerInstallService,
			HttpServletRequest request) {
		String lastchangetime = request.getParameter("lastchangetime");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date lastChangeDate = df.parse(lastchangetime);
			sysServerInstallService.setLastChangeDate(lastChangeDate);
		} catch (ParseException e) {
			logger.error("时间转换异常：" + e);
		}
		return harvesterService.resartProgram(sysServerInstallService);
	}
	
	@RequestMapping("/delHarvestInfo")
	@ResponseBody
	public Map<String, Object> delHarvestInfo(SysServerInstallService bean){
		return harvesterService.delServerInstallService(bean);
	}
}
