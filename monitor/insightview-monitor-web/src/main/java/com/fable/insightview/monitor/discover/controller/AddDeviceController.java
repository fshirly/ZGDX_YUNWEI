package com.fable.insightview.monitor.discover.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.monitor.database.entity.MODBMSServerBean;
import com.fable.insightview.monitor.database.service.IOracleService;
import com.fable.insightview.monitor.discover.entity.MODeviceObj;
import com.fable.insightview.monitor.discover.entity.SysNetworkDiscoverTask;
import com.fable.insightview.monitor.discover.mapper.MODeviceMapper;
import com.fable.insightview.monitor.discover.mapper.SysNetworkDiscoverTaskMapper;
import com.fable.insightview.monitor.discover.service.IAddDeviceService;
import com.fable.insightview.monitor.discover.service.IOfflineDiscoverService;
import com.fable.insightview.monitor.environmentmonitor.entity.MOZoneManagerBean;
import com.fable.insightview.monitor.environmentmonitor.service.IZoneManagerService;
import com.fable.insightview.monitor.middleware.middlewarecommunity.entity.SysMiddleWareCommunityBean;
import com.fable.insightview.monitor.middleware.middlewarecommunity.service.ISysMiddleWareCommunityService;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJMXBean;
import com.fable.insightview.monitor.middleware.tomcat.service.IMiddlewareService;
import com.fable.insightview.monitor.mobject.entity.MObjectDefBean;
import com.fable.insightview.monitor.mobject.mapper.MobjectInfoMapper;
import com.fable.insightview.monitor.perf.mapper.PerfTaskInfoMapper;
import com.fable.insightview.monitor.snmpcommunitytemp.entity.SNMPCommunityTempBean;
import com.fable.insightview.monitor.snmpcommunitytemp.service.ISNMPCommunityTempService;
import com.fable.insightview.monitor.sysdbmscommunity.entity.SysDBMSCommunityBean;
import com.fable.insightview.monitor.sysdbmscommunity.service.ISysDBMSCommunityService;
import com.fable.insightview.monitor.sysroomcommunity.entity.SysRoomCommunityBean;
import com.fable.insightview.monitor.sysroomcommunity.service.ISysRoomCommunityService;
import com.fable.insightview.monitor.website.entity.SysSiteCommunityBean;
import com.fable.insightview.monitor.website.entity.WebSite;
import com.fable.insightview.monitor.website.service.ISysSiteCommunityService;
import com.fable.insightview.monitor.website.service.IWebSiteService;
import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.snmpcommunity.entity.SNMPCommunityBean;
import com.fable.insightview.platform.snmpcommunity.entity.SysVMIfCommunityBean;
import com.fable.insightview.platform.snmpcommunity.service.ISnmpCommunityService;
import com.fable.insightview.platform.snmpcommunity.service.ISysAuthCommunityService;
import com.fable.insightview.platform.snmpcommunity.service.ISysVMIfCommunityService;

/**
 * 
 * 新增设备
 * 
 */
@Controller
@RequestMapping("/monitor/addDevice")
public class AddDeviceController {
	@Autowired
	MobjectInfoMapper mobjectInfoMapper;
	@Autowired
	ISnmpCommunityService snmpCommunityService;
	@Autowired
	SysNetworkDiscoverTaskMapper sysNetworkDiscover;
	@Autowired
	ISysVMIfCommunityService sysVMIfCommunityService;
	@Autowired
	ISysDBMSCommunityService sysDBMSCommunityService;
	@Autowired
	ISysMiddleWareCommunityService middleWareCommunityService;
	@Autowired
	SysNetworkDiscoverTaskMapper discoverTaskMapper;
	@Autowired
	IOracleService oracleService;
	@Autowired
	IMiddlewareService middlewareService;
	@Autowired
	MODeviceMapper deviceMapper;
	@Autowired
	PerfTaskInfoMapper perfTaskInfoMapper;
	@Autowired
	ISysAuthCommunityService sysAuthCommunityService;
	@Autowired
	ISysRoomCommunityService roomCommunityService;
	@Autowired
	IZoneManagerService zoneManagerService;
	@Autowired
	ISNMPCommunityTempService snmpCommunityTempService;
	@Autowired
	ISysSiteCommunityService siteCommunityService;
	@Autowired
	IWebSiteService webSiteService;
	@Autowired
	IAddDeviceService addDeviceService;
	@Autowired
	IOfflineDiscoverService offlineDiscoverService;

	private static final Logger logger = LoggerFactory
			.getLogger(AddDeviceController.class);
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@RequestMapping("/toAddDevice")
	public ModelAndView toAddDevice(HttpServletRequest request,
			HttpServletResponse response,String navigationBar) {

		logger.info("跳转至新增设备界面。。。。。。start");
		request.setAttribute("flag", "true");
		request.setAttribute("navigationBar", navigationBar);
		return new ModelAndView("monitor/discover/addDevice_list");
	}

	/**
	 * 初始化对象类型树
	 * 
	 */
	@RequestMapping("/initTree")
	@ResponseBody
	public Map<String, Object> initPortalTree() {
		List<MObjectDefBean> mobjectList = new ArrayList<MObjectDefBean>();
		try {
			mobjectList = mobjectInfoMapper.getAllMObject();
		} catch (Exception e) {
			logger.error("获得对象异常：" + e);
		}
		List<MObjectDefBean> menuLst = new ArrayList<MObjectDefBean>();
		for (int i = 0; i < mobjectList.size(); i++) {
			MObjectDefBean bean = mobjectList.get(i);
			if (bean.getParentClassId() != null || bean.getClassId() == 1) {
				if (bean.getClassId() != 9 && bean.getClassId() != -1) {
					menuLst.add(bean);
				}
			}
		}

		for (int i = 0; i < menuLst.size(); i++) {
			MObjectDefBean bean = menuLst.get(i);
			if (bean.getParentClassId() == null) {
				bean.setParentClassId(0);
			}
		}

		String menuLstJson = JsonUtil.toString(menuLst);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("menuLstJson", menuLstJson);
		return result;
	}

	/**
	 * 判断是否为叶子节点
	 * 
	 */
	@RequestMapping("/isLeaf")
	@ResponseBody
	public boolean idLeaf(int classID) {
		int count = mobjectInfoMapper.isLeaf(classID);
		if (count > 0) {
			return false;
		}
		return true;
	}

	/**
	 * 新增(认证为：Snmp)
	 * 
	 */
	@RequestMapping("/addSnmpCommunity")
	@ResponseBody
	public boolean addSnmpCommunity(HttpServletRequest request) {
		boolean flag1 = true;
		boolean flag2 = true;
		String deviceIP = request.getParameter("deviceIP");

		logger.info("新增发现任务。。。。start");
		String moClassNames = request.getParameter("moClassNames");
		String isOffline = request.getParameter("isOffline");
		SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
				.getSession().getAttribute("sysUserInfoBeanOfSession");
		int creator = sysUserInfoBeanTemp.getId().intValue();
		int port = 161;
		String dbName = "";
		//离线发现
		if(!"".equals(isOffline) && isOffline != null){
			int collectorid = Integer.parseInt(request.getParameter("collectorid"));
			flag2 = offlineDiscoverService.addSingleOfflineTask(deviceIP, creator, moClassNames, port,dbName, isOffline, collectorid);
		}
		//在线发现
		else{
			flag2 = sysDBMSCommunityService.addDiscoverTask(deviceIP, creator, moClassNames, port,dbName);
		}

		if (flag1 == true && flag2 == true) {
			return true;
		}
		return false;
	}

	/**
	 * 新增(认证为：Vmware)
	 * 
	 */
	@RequestMapping("/addVmware")
	@ResponseBody
	public boolean addVmware(SysVMIfCommunityBean authBean,
			HttpServletRequest request) {

		logger.info("开始...新增VMware");
		boolean flag1 = false;
		boolean flag2 = false;
		String isExsitVmware = request.getParameter("isExsitVmware");
		String isOffline = request.getParameter("isOffline");
		try {
			if ("true".equals(isExsitVmware)) {
				perfTaskInfoMapper.updateVMIfCommunity(authBean);
			} else {
				sysVMIfCommunityService.addSysAuthCommunity(authBean);
			}
			flag1 = true;
		} catch (Exception e) {
			logger.error("插入异常：" + e.getMessage(), authBean);
			return false;
		}
		logger.info("结束...新增VMware");

		logger.info("新增发现任务。。。。start");
		String moClassNames = request.getParameter("moClassNames");
		SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
				.getSession().getAttribute("sysUserInfoBeanOfSession");
		int creator = sysUserInfoBeanTemp.getId().intValue();
		int port = 0;
		if (authBean.getPort() != null) {
			port = authBean.getPort();
		}
		String dbName = "";
		//离线发现
		if(!"".equals(isOffline) && isOffline != null){
			int collectorid = Integer.parseInt(request.getParameter("collectorid"));
			flag2 = offlineDiscoverService.addSingleOfflineTask(authBean.getDeviceIP(), creator, moClassNames, port,dbName, isOffline, collectorid);
		}
		//在线发现
		else{
			flag2 = sysDBMSCommunityService.addDiscoverTask(authBean.getDeviceIP(), creator, moClassNames, port,dbName);
		}

		if (flag1 == true && flag2 == true) {
			return true;
		}
		return false;
	}

	/**
	 * 新增(认证为：JMX)
	 * 
	 */
	@RequestMapping("/addMiddleCommunity")
	@ResponseBody
	public boolean addMiddleCommunity(SysMiddleWareCommunityBean communityBean,
			HttpServletRequest request) {

		logger.info("开始...新增JMX");
		boolean result = false;
		boolean flag1 = false;
		boolean flag2 = false;
		String isExistMiddle = request.getParameter("isExistMiddle");
		String isOffline = request.getParameter("isOffline");
		try {
			if ("true".equals(isExistMiddle)) {
				middleWareCommunityService
						.updateMiddleWareCommunity(communityBean);
			} else {
				middleWareCommunityService
						.insertMiddleWareCommunity(communityBean);
			}
			flag1 = true;
		} catch (Exception e) {
			logger.error("插入异常：" + e.getMessage(), communityBean);
			return false;
		}
		logger.info("新增发现任务。。。。start");
		SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
				.getSession().getAttribute("sysUserInfoBeanOfSession");
		int creator = sysUserInfoBeanTemp.getId().intValue();
		String moClassNames = request.getParameter("moClassNames");
		int port = 0;
		if (!"".equals(communityBean.getPort())
				&& communityBean.getPort() != null) {
			port = Integer.parseInt(communityBean.getPort());
		}
		String dbName = "";
		//离线发现
		if(!"".equals(isOffline) && isOffline != null){
			int collectorid = Integer.parseInt(request.getParameter("collectorid"));
			flag2 = offlineDiscoverService.addSingleOfflineTask(communityBean.getIpAddress(), creator, moClassNames, port,dbName, isOffline, collectorid);
		}
		//在线发现
		else{
			flag2 = sysDBMSCommunityService.addDiscoverTask(communityBean.getIpAddress(), creator, moClassNames, port,dbName);
		}
		if (flag1 == true && flag2 == true) {
			result = true;
		} else {
			result = false;
		}

		return result;
	}

	/**
	 * 验证数据库认证中改设备IP是否存在
	 * 
	 */
	@RequestMapping("/checkAddDBMSCommunity")
	@ResponseBody
	public boolean checkAddDBMSCommunity(SysDBMSCommunityBean dbmsCommunityBean) {
		return sysDBMSCommunityService.checkbeforeAdd(dbmsCommunityBean);
	}

	/**
	 * 新增(认证为：数据库)
	 * 
	 */
	@RequestMapping("/addDBMSCommunity")
	@ResponseBody
	public boolean addDBMSCommunity(SysDBMSCommunityBean dbmsCommunityBean,
			HttpServletRequest request) {

		logger.info("开始...新增数据库验证");
		boolean flag1 = false;
		boolean flag2 = false;
		String isExistDB = request.getParameter("isExistDB");
		String isOffline = request.getParameter("isOffline");
		if ("true".equals(isExistDB)) {
			if(!"DB2".equals(dbmsCommunityBean.getDbmsType())){
				flag1 = sysDBMSCommunityService.updateDBMSCommunity(dbmsCommunityBean);
			}else{
				flag1 = sysDBMSCommunityService.updateDBMSCommunity2(dbmsCommunityBean);
			}
		} else {
			flag1 = sysDBMSCommunityService.addDBMSCommunity(dbmsCommunityBean);
		}
		logger.info("结束...新增数据库验证");

		logger.info("新增发现任务。。。。start");
		String moClassNames = request.getParameter("moClassNames");
		SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
				.getSession().getAttribute("sysUserInfoBeanOfSession");
		int creator = sysUserInfoBeanTemp.getId().intValue();
		int port = 0;
		if (dbmsCommunityBean.getPort() != null) {
			port = dbmsCommunityBean.getPort();
		}
		String dbName = "";
		if ("DB2".equals(dbmsCommunityBean.getDbmsType())){
			dbName = dbmsCommunityBean.getDbName();
		}
		//离线发现
		if(!"".equals(isOffline) && isOffline != null){
			int collectorid = Integer.parseInt(request.getParameter("collectorid"));
			flag2 = offlineDiscoverService.addSingleOfflineTask(dbmsCommunityBean.getIp(), creator, moClassNames, port,dbName, isOffline, collectorid);
		}
		//在线发现
		else{
			flag2 = sysDBMSCommunityService.addDiscoverTask(dbmsCommunityBean.getIp(), creator, moClassNames, port,dbName);
		}
			

		if (flag1 == true && flag2 == true) {
			return true;
		}
		return false;
	}

	/**
	 * 对象类型为数据库时，验证对象IP是已经发现的
	 * 
	 */
	@RequestMapping("/isDiscovered")
	@ResponseBody
	public boolean isDiscovered(String deviceip) {
		return sysDBMSCommunityService.isDiscovered(deviceip);
	}

	@RequestMapping("/getMOClassNames")
	@ResponseBody
	public List<MObjectDefBean> getMOClassNames() {
		List<MObjectDefBean> allMObjectLst = mobjectInfoMapper.getAllMObject();
		List<MObjectDefBean> mobjectLst = new ArrayList<MObjectDefBean>();
		for (int i = 0; i < allMObjectLst.size(); i++) {
			int classId = allMObjectLst.get(i).getClassId();
			if (classId == 15 || classId == 16  || classId == 19
					|| classId == 20) {
				mobjectLst.add(allMObjectLst.get(i));
			}
		}
		return mobjectLst;

	}

	/**
	 * 加载列表页面数据
	 * 
	 */
	@RequestMapping("/listTasks")
	@ResponseBody
	public Map<String, Object> listTasks(SysNetworkDiscoverTask discoverTaskBean) {

		logger.info("加载列表页面数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<SysNetworkDiscoverTask> page = new Page<SysNetworkDiscoverTask>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ipaddress1", discoverTaskBean.getIpaddress1());
		paramMap.put("moClassNames", discoverTaskBean.getMoClassNames());
		paramMap.put("collectorName", discoverTaskBean.getCollectorName());
		paramMap.put("taskid", discoverTaskBean.getTaskid());
		page.setParams(paramMap);
		List<SysNetworkDiscoverTask> taskList = new ArrayList<SysNetworkDiscoverTask>();
		try {
			taskList = sysNetworkDiscover.selectDeviceTasks(page);
		} catch (Exception e) {
			logger.error("获得发现任务异常：" + e);
		}

		boolean flag = false;
		int isConfig = 0;
		for (int i = 0; i < taskList.size(); i++) {
			SysNetworkDiscoverTask task = taskList.get(i);
			if (task.getLaststatustime() != null) {
				String laststatustimeStr = dateFormat.format(task
						.getLaststatustime());
				task.setLaststatustimeStr(laststatustimeStr);
			}
			if (task.getCreatetime() != null) {
				String createtimeStr = dateFormat.format(task.getCreatetime());
				task.setCreatetimeStr(createtimeStr);
			}

			// 操作进度已完成，判断手否已发现
			if(!"".equals(task.getErrorinfo()) && task.getErrorinfo() != null && !"discovery-004".equals(task.getErrorID())){
				isConfig = 0;
				task.setIsConfig(isConfig);
			}else{
				if (task.getProgressstatus() == 5) {
					int moClassId = task.getClassID();
					String ip = task.getIpaddress1();
					int port = task.getPort();
					// 数据库是否已发现
					if (moClassId == 15 || moClassId == 16 || moClassId == 81 || moClassId == 86) {
						MODBMSServerBean dbmsServerBean = new MODBMSServerBean();
						dbmsServerBean.setIp(ip);
						dbmsServerBean.setMoClassId(moClassId);
						dbmsServerBean.setPort(port);
						int count = oracleService.getDBMSServerByIp(dbmsServerBean);
						if (count > 0) {
							flag = true;
						}
					}
					else if(moClassId == 54){
						String errorInfo = task.getErrorinfo();
						if(!"".equals(errorInfo) && errorInfo != null){
							flag = false;
						}else{
							flag = true;
						}
					}
					
					// 中间件是否已发现
					else if (moClassId == 19 || moClassId == 20 || moClassId == 53) {
						String jmxType = "websphere";
						if (moClassId == 19) {
							jmxType = "websphere";
						} else if (moClassId == 20) {
							jmxType = "tomcat";
						} else if (moClassId == 53) {
							jmxType = "weblogic";
						}
						MOMiddleWareJMXBean middleJmxBean = new MOMiddleWareJMXBean();
						middleJmxBean.setJmxType(jmxType);
						middleJmxBean.setIp(ip);
						middleJmxBean.setPort(port);
						int count = middlewareService
						.getJMXByIPAndType(middleJmxBean);
						if (count > 0) {
							flag = true;
						}
					}
					
					// 机房环境监控是否存在
					else if (moClassId == 44) {
						MOZoneManagerBean zoneManagerBean = new MOZoneManagerBean();
						zoneManagerBean.setMoClassId(moClassId);
						zoneManagerBean.setIpAddress(ip);
						zoneManagerBean.setPort(port);
						MOZoneManagerBean bean = zoneManagerService
						.getZoneManagerByIP(zoneManagerBean);
						if (bean != null) {
							flag = true;
						}
						
					}
					
					// 设备是否已发现
					else {
						List<MODeviceObj> devicelIst = deviceMapper
						.getDeviceListByIP(ip);
						if (devicelIst.size() > 0) {
							flag = true;
						}else{
							int count = deviceMapper.getMoinfoCount(ip);
							if(count > 0){
								flag = true;
							}else{
								flag = false;
							}
						}
					}
					
					// 如果已经发现
					if (flag == true) {
						isConfig = 1;
					} else {
						isConfig = 0;
					}
					task.setIsConfig(isConfig);
				}
			}

		}
		int total = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", taskList);
		logger.info("加载列表页面数据over");
		return result;
	}

	/**
	 * 打开查看页面
	 */
	@RequestMapping("/toShowTaskDetail")
	@ResponseBody
	public ModelAndView toShowTaskDetail(HttpServletRequest request) {
		logger.info("打开查看页面");
		String taskId = request.getParameter("taskId");
		String flag = request.getParameter("flag");
		request.setAttribute("taskId", taskId);
		request.setAttribute("flag", flag);
		return new ModelAndView("monitor/discover/addDevice_detail");
	}

	/**
	 * 打开新增页面
	 */
	@RequestMapping("/toShowTaskAdd")
	@ResponseBody
	public ModelAndView toShowTaskAdd(HttpServletRequest request) {
		logger.info("打开新增页面");
		String flag = request.getParameter("flag");
		request.setAttribute("flag", flag);
		return new ModelAndView("monitor/discover/addDevice_add");
	}

	/**
	 * 根据任务ID获取操作进度
	 * 
	 */
	@RequestMapping("/getIsConfig")
	@ResponseBody
	public Map<String, Object> getIsConfig(String taskIds) {
		List<String> indexLst = new ArrayList<String>();
		boolean flag = false;
		int isConfig = 0;
		if (taskIds != "") {
			String[] taskIdLst = taskIds.split(",");
			for (int i = 0; i < taskIdLst.length; i++) {
				SysNetworkDiscoverTask task = discoverTaskMapper
						.getTaskInfoByTaskId(Integer.parseInt(taskIdLst[i]
								.split(":")[0]));
				if(!"".equals(task.getErrorinfo()) && task.getErrorinfo() != null && !"discovery-004".equals(task.getErrorID())){
					isConfig = 0;
					indexLst.add(taskIdLst[i].split(":")[1] + ":" + isConfig);
				}else{
					String ip = task.getIpaddress1();
					String moClassNames = task.getMoClassNames();
					int port = task.getPort();
					if ("Oracle".equals(moClassNames)
							|| "Mysql".equals(moClassNames)
							|| "DB2".equals(moClassNames)
							|| "Sybase".equals(moClassNames)
							|| "MsSql".equals(moClassNames)) {
						int moClassId = 15;
						if ("Oracle".equals(moClassNames)) {
							moClassId = 15;
						} else if ("Mysql".equals(moClassNames)) {
							moClassId = 16;
						} else if ("DB2".equals(moClassNames))  {
							moClassId = 54;
						} else if ("Sybase".equals(moClassNames)) {
							moClassId = 81;
						} else if ("MsSql".equals(moClassNames))  {
							moClassId = 86;
						}
						MODBMSServerBean dbmsServerBean = new MODBMSServerBean();
						
						dbmsServerBean.setIp(ip);
						dbmsServerBean.setMoClassId(moClassId);
						dbmsServerBean.setPort(port);
						if(moClassId != 54){
							int count = oracleService.getDBMSServerByIp(dbmsServerBean);
							if (count > 0) {
								flag = true;
							}
						}else{
							String errorInfo = task.getErrorinfo();
							if(!"".equals(errorInfo) && errorInfo != null){
								flag = false;
							}else{
								flag = true;
							}
						}
					} else if ("Websphere".equals(moClassNames)
							|| "Tomcat".equals(moClassNames)
							|| "Weblogic".equals(moClassNames)) {
						String jmxType = "websphere";
						if ("Websphere".equals(moClassNames)) {
							jmxType = "websphere";
						} else if ("Tomcat".equals(moClassNames)) {
							jmxType = "tomcat";
						} else {
							jmxType = "weblogic";
						}
						MOMiddleWareJMXBean middleJmxBean = new MOMiddleWareJMXBean();
						middleJmxBean.setJmxType(jmxType);
						middleJmxBean.setIp(ip);
						middleJmxBean.setPort(port);
						int count = middlewareService
						.getJMXByIPAndType(middleJmxBean);
						if (count > 0) {
							flag = true;
						}
					} else if ("RoomEnvironmentManager".equals(moClassNames)) {
						MOZoneManagerBean zoneManagerBean = new MOZoneManagerBean();
						zoneManagerBean.setMoClassId(44);
						zoneManagerBean.setIpAddress(ip);
						zoneManagerBean.setPort(port);
						MOZoneManagerBean bean = zoneManagerService
						.getZoneManagerByIP(zoneManagerBean);
						
						if (bean != null) {
							flag = true;
						}
					} else {
						List<MODeviceObj> devicelIst = deviceMapper
						.getDeviceListByIP(ip);
						if (devicelIst.size() > 0) {
							flag = true;
						}else{
							int count = deviceMapper.getMoinfoCount(ip);
							if(count > 0){
								flag = true;
							}else{
								flag = false;
							}
						}
					}
					// 如果已经发现
					if (flag == true) {
						isConfig = 1;
					} else {
						isConfig = 0;
					}
					indexLst.add(taskIdLst[i].split(":")[1] + ":" + isConfig);
				}
			}
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("indexLst", indexLst);
		return result;
	}

	@RequestMapping("/doBeforeSet")
	@ResponseBody
	public Map<String, Object> doBeforeSet(HttpServletRequest request) {
		String deviceip = request.getParameter("deviceip");
		String moClassIdStr = request.getParameter("moClassId");
		String portStr = request.getParameter("port");
		int moClassId = Integer.parseInt(moClassIdStr);
		int port = Integer.parseInt(portStr);
		int moid = 0;
		int classId = moClassId;
		String nemanufacturername = "";
		if (moClassId == 15 || moClassId == 16 || moClassId == 54 || moClassId == 81 || moClassId == 86) {
			MODBMSServerBean dbmsServerBean = new MODBMSServerBean();
			dbmsServerBean.setIp(deviceip);
			dbmsServerBean.setMoClassId(moClassId);
			dbmsServerBean.setPort(port);
			MODBMSServerBean serverBean = oracleService
					.getDBMSServerByIpAndType(dbmsServerBean);
			if (serverBean != null) {
				moid = serverBean.getMoid();
			}
		} else if (moClassId == 19 || moClassId == 20 || moClassId == 53) {
			String jmxType = "websphere";
			if (moClassId == 19) {
				jmxType = "websphere";
			} else if (moClassId == 20) {
				jmxType = "tomcat";
			} else if (moClassId == 53) {
				jmxType = "weblogic";
			}
			MOMiddleWareJMXBean middleJmxBean = new MOMiddleWareJMXBean();
			middleJmxBean.setJmxType(jmxType);
			middleJmxBean.setIp(deviceip);
			middleJmxBean.setPort(port);
			MOMiddleWareJMXBean jmxBean = middlewareService
					.getJMXInfoByIPAndType(middleJmxBean);
			if (jmxBean != null) {
				moid = jmxBean.getMoId();
			}
		} else if (moClassId == 44) {
			MOZoneManagerBean zoneManagerBean = new MOZoneManagerBean();
			zoneManagerBean.setMoClassId(moClassId);
			zoneManagerBean.setIpAddress(deviceip);
			zoneManagerBean.setPort(port);
			MOZoneManagerBean managerBean = zoneManagerService
					.getZoneManagerByIP(zoneManagerBean);
			if (managerBean != null) {
				moid = managerBean.getMoID();
			}
		} else {
			List<MODeviceObj> devicelIst = deviceMapper
					.getDeviceByIpInfo(deviceip);
			if (devicelIst.size() > 0) {
				moid = devicelIst.get(0).getMoid();
				nemanufacturername = devicelIst.get(0).getNemanufacturername();
				classId = devicelIst.get(0).getMoClassId();
			}
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("moid", moid);
		result.put("nemanufacturername", nemanufacturername);
		result.put("moClassId", classId);
		return result;
	}

	/**
	 * 初始化Vmware凭证
	 */
	@RequestMapping("/initVmwareCommunity")
	@ResponseBody
	public Map<String, Object> initVmwareCommunity(SysVMIfCommunityBean authBean) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<SysVMIfCommunityBean> snmpLst = sysVMIfCommunityService
				.getSysAuthCommunityByConditions(authBean);
		boolean isExsitVmware = false;
		boolean isExistCommunity = false;
		SysVMIfCommunityBean bean = new SysVMIfCommunityBean();
		if (null == snmpLst || snmpLst.size() <= 0) {
			isExsitVmware = false;
			
			String[] split = authBean.getDeviceIP().split("\\.");
			String ip = split[0]+"."+split[1]+"."+split[2]+".*";
			SysVMIfCommunityBean vmCommunity = new SysVMIfCommunityBean();
			vmCommunity.setDeviceIP(ip);
			SysVMIfCommunityBean vmIf = new SysVMIfCommunityBean();
			vmIf = sysVMIfCommunityService.getObjFromDeviceIP(vmCommunity);
			
			if(vmIf == null){
				vmCommunity.setDeviceIP("*");
				vmIf = sysVMIfCommunityService.getObjFromDeviceIP(vmCommunity);
			}
			
			if(vmIf != null){
				isExistCommunity = true;
			}else{
				isExistCommunity = false;
			}
			bean = vmIf;
		} else {
			isExistCommunity = true;
			isExsitVmware = true;
			bean = snmpLst.get(0);
		}
		result.put("isExistCommunity", isExistCommunity);
		result.put("isExsitVmware", isExsitVmware);
		result.put("vmwareCommunity", bean);
		return result;
	}

	/**
	 * 初始化数据库凭证
	 */
	@RequestMapping("/initDBCommunity")
	@ResponseBody
	public Map<String, Object> initDBCommunity(
			SysDBMSCommunityBean dbmsCommunityBean) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<SysDBMSCommunityBean> dbList = sysDBMSCommunityService
				.getByIPAndPort(dbmsCommunityBean);
		boolean isExistDB = false;
		boolean isExistCommunity = false;
		SysDBMSCommunityBean bean = new SysDBMSCommunityBean();
		if (null == dbList || dbList.size() <= 0) {
			isExistDB = false;
			String[] split = dbmsCommunityBean.getIp().split("\\.");
			String ip = split[0]+"."+split[1]+"."+split[2]+".*";
			dbmsCommunityBean.setIp(ip);
			List<SysDBMSCommunityBean> dbCommunityLst = new ArrayList<SysDBMSCommunityBean>();
			dbCommunityLst = sysDBMSCommunityService.getByIPAndPort(dbmsCommunityBean);
			if(dbCommunityLst.size() <= 0 || dbCommunityLst == null){
				dbmsCommunityBean.setIp("*");
				dbCommunityLst = sysDBMSCommunityService.getByIPAndPort(dbmsCommunityBean);
			}
			if(dbCommunityLst != null && dbCommunityLst.size() > 0){
				isExistCommunity = true;
				bean = dbCommunityLst.get(0);
			}else{
				isExistCommunity = false;
			}
		} else {
			isExistCommunity = true;
			isExistDB = true;
			bean = dbList.get(0);
		}
		result.put("isExistCommunity", isExistCommunity);
		result.put("isExistDB", isExistDB);
		result.put("dbCommunity", bean);
		return result;
	}
	
	/**
	 * 初始化数据库凭证
	 */
	@RequestMapping("/initDB2Community")
	@ResponseBody
	public Map<String, Object> initDB2Community(
			SysDBMSCommunityBean dbmsCommunityBean,HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		dbmsCommunityBean.setDbName(request.getParameter("dbName"));
		List<SysDBMSCommunityBean> dbList = sysDBMSCommunityService
				.getByIPAndTypeAndPortAndName(dbmsCommunityBean);
		boolean isExistDB = false;
		boolean isExistCommunity = false;
		SysDBMSCommunityBean bean = new SysDBMSCommunityBean();
		if (null == dbList || dbList.size() <= 0) {
			isExistDB = false;
			
			String[] split = dbmsCommunityBean.getIp().split("\\.");
			String ip = split[0]+"."+split[1]+"."+split[2]+".*";
			dbmsCommunityBean.setIp(ip);
			List<SysDBMSCommunityBean> dbCommunityLst = new ArrayList<SysDBMSCommunityBean>();
			dbCommunityLst = sysDBMSCommunityService.getByIPAndTypeAndPortAndName(dbmsCommunityBean);
			if(dbCommunityLst.size() <= 0 || dbCommunityLst == null){
				dbmsCommunityBean.setIp("*");
				dbCommunityLst = sysDBMSCommunityService.getByIPAndTypeAndPortAndName(dbmsCommunityBean);
			}
			if(dbCommunityLst != null && dbCommunityLst.size() > 0){
				isExistCommunity = true;
				bean = dbCommunityLst.get(0);
			}else{
				isExistCommunity = false;
			}
		} else {
			isExistCommunity = true;
			isExistDB = true;
			bean = dbList.get(0);
		}
		result.put("isExistCommunity", isExistCommunity);
		result.put("isExistDB", isExistDB);
		result.put("dbCommunity", bean);
		return result;
	}

	/**
	 * 初始化中间件凭证
	 * 
	 */
	@RequestMapping("/initMiddleCommunity")
	@ResponseBody
	public Map<String, Object> initMiddleCommunity(
			SysMiddleWareCommunityBean middleWareCommunityBean) {
		Map<String, Object> result = new HashMap<String, Object>();
		SysMiddleWareCommunityBean bean = middleWareCommunityService
				.getCommunityByIPAndPort(middleWareCommunityBean);
		boolean isExistMiddle = false;
		boolean isExistCommunity = false;
		if (bean == null) {
			isExistMiddle = false;
			
			SysMiddleWareCommunityBean jmxCommunity = new SysMiddleWareCommunityBean();
			String[] split = middleWareCommunityBean.getIpAddress().split("\\.");
			String ip = split[0]+"."+split[1]+"."+split[2]+".*";
			middleWareCommunityBean.setIpAddress(ip);
			
			jmxCommunity = middleWareCommunityService.getCommunityByIPAndPort(middleWareCommunityBean);
			if(jmxCommunity == null){
				middleWareCommunityBean.setIpAddress("*");
				jmxCommunity = middleWareCommunityService.getCommunityByIPAndPort(middleWareCommunityBean);
			}
			
			if(jmxCommunity != null){
				isExistCommunity = true;
			}else{
				isExistCommunity = false;
			}
			bean = jmxCommunity;
		} else {
			isExistCommunity = true;
			isExistMiddle = true;
		}
		result.put("isExistCommunity", isExistCommunity);
		result.put("isExistMiddle", isExistMiddle);
		result.put("middleCommunity", bean);
		return result;
	}

	/**
	 * 初始化机房环境监控凭证
	 * 
	 */
	@RequestMapping("/initRoomCommunity")
	@ResponseBody
	public Map<String, Object> initRoomCommunity(
			SysRoomCommunityBean roomCommunityBean) {
		Map<String, Object> result = new HashMap<String, Object>();
		SysRoomCommunityBean bean = roomCommunityService
				.getRoomCommunityByIPAndPort(roomCommunityBean);
		boolean isExistRoom = false;
		boolean isExistCommunity = false;
		if (bean == null) {
			isExistRoom = false;
			
			SysRoomCommunityBean roomCommunity = new SysRoomCommunityBean();
			String[] split = roomCommunityBean.getIpAddress().split("\\.");
			String ip = split[0]+"."+split[1]+"."+split[2]+".*";
			roomCommunityBean.setIpAddress(ip);
			roomCommunity = roomCommunityService.getRoomCommunityByIPAndPort(roomCommunityBean);
			if(roomCommunity == null){
				roomCommunityBean.setIpAddress("*");
				roomCommunity = roomCommunityService.getRoomCommunityByIPAndPort(roomCommunityBean);
			}
			
			if(roomCommunity != null){
				isExistCommunity = true;
				bean = roomCommunity;
			}else{
				isExistCommunity = false;
			}
		} else {
			isExistCommunity = true;
			isExistRoom = true;
		}
		result.put("isExistCommunity", isExistCommunity);
		result.put("isExistRoom", isExistRoom);
		result.put("roomCommunity", bean);
		return result;
	}

	/**
	 * 新增(认证为：机房环境监控)
	 * 
	 */
	@RequestMapping("/addRoomCommunity")
	@ResponseBody
	public boolean addRoomCommunity(SysRoomCommunityBean communityBean,
			HttpServletRequest request) {

		logger.info("开始...新增机房环境监控凭证");
		boolean result = false;
		boolean flag1 = false;
		boolean flag2 = false;
		String isExistRoom = request.getParameter("isExistRoom");
		String isOffline = request.getParameter("isOffline");
		try {
			if ("true".equals(isExistRoom)) {
				roomCommunityService.updateCommunityByIP(communityBean);
			} else {
				roomCommunityService.insertRoomCommunity(communityBean);
			}
			flag1 = true;
		} catch (Exception e) {
			logger.error("插入异常：" + e.getMessage(), communityBean);
			return false;
		}
		logger.info("新增发现任务。。。。start");
		SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
				.getSession().getAttribute("sysUserInfoBeanOfSession");
		int creator = sysUserInfoBeanTemp.getId().intValue();
		String moClassNames = request.getParameter("moClassNames");
		String dbName = "";
		//离线发现
		if(!"".equals(isOffline) && isOffline != null){
			int collectorid = Integer.parseInt(request.getParameter("collectorid"));
			flag2 = offlineDiscoverService.addSingleOfflineTask(communityBean.getIpAddress(), creator,
					moClassNames, communityBean.getPort(),dbName, isOffline, collectorid);
		}
		//在线发现
		else{
			flag2 = sysDBMSCommunityService.addDiscoverTask(communityBean.getIpAddress(), creator,
					moClassNames, communityBean.getPort(),dbName);
		}
		if (flag1 == true && flag2 == true) {
			result = true;
		} else {
			result = false;
		}

		return result;
	}
	
	@RequestMapping("/listSnmpCommunity")
	@ResponseBody
	public Map<String, Object> listSnmpCommunity(){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<SNMPCommunityTempBean> page = new Page<SNMPCommunityTempBean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("deviceIp", request.getParameter("deviceIp"));
		page.setParams(paramMap);
		// 查询分页数据
		List<SNMPCommunityBean> snmpLst = snmpCommunityTempService.listSnmpAndSnmpTem(page);
		// 查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", totalCount);
		result.put("rows", snmpLst);
		return result;
	}
	
	/**
	 * 是否已经发现vCenter设备
	 */
	@RequestMapping("/isVCenter")
	@ResponseBody
	public boolean isVCenter(String deviceip){
		List<MODeviceObj> deviceList = deviceMapper.getDeviceListByIP(deviceip);
		if (deviceList.size() <= 0) {
			return true;
		} else {
			int moId = deviceList.get(0).getMoid();
			List<MODeviceObj> vcenterDeviceLst = deviceMapper.getVCenterDevice(moId);
			if(vcenterDeviceLst.size() <= 0){
				return true;
			}
			return false;
		}
	}
	
	/**
	 * 初始化FTP凭证
	 */
	@RequestMapping("/initFTPCommunity")
	@ResponseBody
	public Map<String, Object> initFTPCommunity(SysSiteCommunityBean sysSiteCommunityBean) {
		Map<String, Object> result = new HashMap<String, Object>();
		SysSiteCommunityBean siteCommunityBean = siteCommunityService.getByIPAndPort(sysSiteCommunityBean);
		boolean isExistSite = false;
		if (null == siteCommunityBean) {
			isExistSite = false;
		} else {
			isExistSite = true;
		}
		result.put("isExistSite", isExistSite);
		result.put("siteCommunity", siteCommunityBean);
		return result;
	}
	
	/**
	 * 初始化HTTP凭证
	 */
	@RequestMapping("/initHttpCommunity")
	@ResponseBody
	public Map<String, Object> initHttpCommunity(SysSiteCommunityBean sysSiteCommunityBean) {
		Map<String, Object> result = new HashMap<String, Object>();
		SysSiteCommunityBean siteCommunityBean = siteCommunityService.getByIPAndSiteType(sysSiteCommunityBean);
		boolean isExistSite = false;
		if (null == siteCommunityBean) {
			isExistSite = false;
		} else {
			isExistSite = true;
		}
		result.put("isExistSite", isExistSite);
		result.put("siteCommunity", siteCommunityBean);
		return result;
	}
	
	/**
	 * 站点名称校验重复结果
	 * @return true:没有重复，false:重复
	 */
	@RequestMapping("/checkSiteName")
	@ResponseBody
	public Map<String, Object> checkSiteName(WebSite webSite) {
		return webSiteService.checkSiteName(webSite);
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
		//站点id
		int moId = -1;
		
		int siteType = webSite.getSiteType();
		
		//判断是否已经发现过对象,如果存则更新对象表
		Map<String, Object> isExistMap = addDeviceService.isExistSite(webSite);
		String isExist = (String) isExistMap.get("isExist");
		
		if("true".equals(isExist)){
			moId = Integer.parseInt(String.valueOf(isExistMap.get("moID")));
			addWebSite = (String) isExistMap.get("updateSite");
		}else{
			//入站点对象表
			Map<String, Object> addWebSiteMap = addDeviceService.addSite(webSite);
			addWebSite = (String) addWebSiteMap.get("addSiteResult");
			moId = Integer.parseInt(String.valueOf(addWebSiteMap.get("moId")));
		}
		
		
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
		
		//采集任务
		webSite.setMoID(moId);
		int templateID = Integer.parseInt(request.getParameter("templateID"));
		String moTypeLstJson = request.getParameter("moTypeLstJson");
		addPerfTask = addDeviceService.addSitePerfTask(webSite,templateID,moTypeLstJson);
		
		if("true".equals(addWebSite) && addSiteCommunity == true && addPerfTask == true){
			return true;
		}
		return false;
	}
	
	/**
	 * 站点测试
	 */
	@RequestMapping("/testSite")
	@ResponseBody
	public boolean testSite(WebSite webSite){
		return addDeviceService.testSite(webSite);
	}
}