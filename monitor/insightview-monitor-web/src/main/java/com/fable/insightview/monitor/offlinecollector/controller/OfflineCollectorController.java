package com.fable.insightview.monitor.offlinecollector.controller;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.monitor.harvester.entity.SysServerHostInfo;
import com.fable.insightview.monitor.harvester.entity.SysServerInstallService;
import com.fable.insightview.monitor.harvester.entity.SysServiceInfo;
import com.fable.insightview.monitor.offlinecollector.service.IOfflineCollectorService;
import com.fable.insightview.monitor.utils.DbConnection;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.page.Page;

/**
 * 离线采集机管理
 *
 */
@Controller
@RequestMapping("/monitor/offlineCollector")
public class OfflineCollectorController {
	private Logger logger = LoggerFactory.getLogger(OfflineCollectorController.class);
	private DateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final short COLLECTORSERVER = 2; 
	private static final String CollectKey = "111111"; 
	@Autowired
	IOfflineCollectorService offlineCollectorService;
	
	/**
	 * 跳转至离线采集机管理页面
	 */
	@RequestMapping("/toOfflineCollectorList")
	@ResponseBody
	public ModelAndView toOfflineCollectorList(String navigationBar) {
		logger.info("跳转至离线采集机管理页面。。。。start");
		ModelAndView mv = new ModelAndView("monitor/offlinecollector/offlineCollector_list");
		mv.addObject("navigationBar", navigationBar);
		return mv;
	}
	
	/**
	 * 获得离线采集机任务列表
	 */
	@RequestMapping("/getOfflineCollectorList")
	@ResponseBody
	public Map<String, Object> getOfflineCollectorList(SysServerInstallService installService,HttpServletRequest request){
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
		.getFlexiGridPageInfo(request);
		Page<SysServerInstallService> page = new Page<SysServerInstallService>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ipaddress", installService.getIpaddress());
		paramMap.put("servicename", installService.getServicename());
		page.setParams(paramMap);
		return offlineCollectorService.getOfflineCollectorList(page);
	}
	
	/**
	 * 获得采集机信息
	 * @param ipaddress
	 * @return
	 */
	@RequestMapping("/getHostInfo")
	@ResponseBody
	public SysServerHostInfo getHostInfo(String ipaddress){
		return offlineCollectorService.getOfflineHostByIp(ipaddress);
	}
	
	/**
	 * 跳转至新增离线采集机页面
	 */
	@RequestMapping("/toAddOfflineCollector")
	public ModelAndView toAddOfflineCollector(ModelMap map,HttpServletRequest request) {
		logger.info("跳转至离线采集机管理页面。。。。start");
		List<SysServiceInfo> serviceInfoLst = offlineCollectorService.getAllServiceInfo();
		map.put("serviceInfoLst", serviceInfoLst);
		String id = request.getParameter("id");
		String serverid = request.getParameter("serverid");
		if(!"".equals(serverid) && serverid != null){
			SysServerHostInfo hostInfo = offlineCollectorService.getOfflineHostById(Integer.parseInt(serverid));
			String installServiceId = request.getParameter("installServiceId");
			map.put("hostInfo", hostInfo);
			map.put("id", id);
			map.put("installServiceId", installServiceId);
		}
		ModelAndView mv = new ModelAndView("monitor/offlinecollector/offlineCollector_add");
		return mv;
	}
	
	/**
	 * 校验采集服务是否存在
	 */
	@RequestMapping("/isExist")
	@ResponseBody
	public boolean isExist(SysServerHostInfo bean){
		return offlineCollectorService.isExist(bean);
	}
	
	/**
	 * 新增/修改离线采集服务
	 */
	@RequestMapping("/editOfflineService")
	@ResponseBody
	public boolean addOfflineService(SysServerHostInfo bean,HttpServletRequest request){
		boolean editHost = true;
		boolean editInstallService = false;
		boolean delHost = true;
		int serverid = bean.getServerid();
		int id = bean.getId();
		//未存在 则新增采集机
		if(serverid == -1){
			//获得新增id
			serverid = DbConnection.getID("serverid", 1);
			bean.setServerid(serverid);
			bean.setServertype(COLLECTORSERVER);
			bean.setCollectkey(CollectKey);
			bean.setRegistertime(new Date());
			editHost = offlineCollectorService.addHost(bean);
		}
		//如果已存在，则更新采集机信息
		else{
			editHost = offlineCollectorService.updateHost(bean);
		}
		if(editHost){
			SysServerInstallService serverInstallService = new SysServerInstallService();
			//新增服务
			if(id == -1){
				id = DbConnection.getID("sysserverinstallserviceid", 1);
				serverInstallService.setId(id);
				serverInstallService.setServerid(serverid);
				serverInstallService.setInstallServiceId(bean.getInstallServiceId());
				serverInstallService.setServicestatus(1);
				serverInstallService.setInstalldir("null");
				serverInstallService.setRegistertime(dft.format(new Date()));
				serverInstallService.setLastChangeDate(new Date());
				serverInstallService.setLastUpdateTime(new Date());
				serverInstallService.setCurrentversion("null");
				editInstallService = offlineCollectorService.addInstallService(serverInstallService);
			}
			//修改服务
			else{
				serverInstallService.setId(id);
				serverInstallService.setServerid(serverid);
				serverInstallService.setInstallServiceId(bean.getInstallServiceId());
				serverInstallService.setLastChangeDate(new Date());
				serverInstallService.setLastUpdateTime(new Date());
				editInstallService = offlineCollectorService.updateInstallService(serverInstallService);
			}
		}
		//删除没有服务的采集机(使用原来和后来的采集机ID)
		String oldServerId = request.getParameter("oldServerId");
		if(!"".equals(oldServerId) && oldServerId != null){
			//如果是编辑 修改了采集机IP ，且修改后原来的IP没有采集服务则删除
			if(id != -1 && id != Integer.parseInt(oldServerId)){
				int count = offlineCollectorService.getServiceCountByServerID(Integer.parseInt(oldServerId));
				if(count == 0){
					delHost = offlineCollectorService.delOfflineServerHost(Integer.parseInt(oldServerId));
				}
			}
		}
		
		if(editHost && editInstallService && delHost){
			return true;
		}
		return false;
	}
	
	/**
	 * 删除服务
	 */
	@RequestMapping("/delHostService")
	@ResponseBody
	public Map<String, Object> delHostService(SysServerInstallService bean){
		Map<String, Object> result = new HashMap<String, Object>();
		//是否被使用 false：没有使用，true：正在被使用
		boolean isUsed = offlineCollectorService.isInUse(bean);
		result.put("isUsed", isUsed);
		if(!isUsed){
			boolean delService = offlineCollectorService.delHostService(bean);
			result.put("delService", delService);
		}
		return result;
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping("/batchDelHostService")
	@ResponseBody
	public Map<String, Object> batchDelHostService(HttpServletRequest request){
		String ids = request.getParameter("ids");
		String serverIds = request.getParameter("serverIds");
		return offlineCollectorService.batchDelHostService(ids, serverIds);
	}
}
