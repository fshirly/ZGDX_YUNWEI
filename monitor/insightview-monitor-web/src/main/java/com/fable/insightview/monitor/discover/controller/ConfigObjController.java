package com.fable.insightview.monitor.discover.controller;

import java.text.DateFormat;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.fable.insightview.monitor.alarmmgr.entity.MOKPIThresholdBean;
import com.fable.insightview.monitor.database.entity.MODBMSServerBean;
import com.fable.insightview.monitor.database.service.IDb2Service;
import com.fable.insightview.monitor.database.service.IMsServerService;
import com.fable.insightview.monitor.database.service.IMyService;
import com.fable.insightview.monitor.database.service.IOracleService;
import com.fable.insightview.monitor.database.service.ISyBaseService;
import com.fable.insightview.monitor.discover.entity.MODeviceObj;
import com.fable.insightview.monitor.discover.mapper.MODeviceMapper;
import com.fable.insightview.monitor.discover.service.IMObjectResSynService;
import com.fable.insightview.monitor.discover.service.impl.IConfigObjService;
import com.fable.insightview.monitor.dispatcher.Dispatcher;
import com.fable.insightview.monitor.dispatcher.constants.TaskType;
import com.fable.insightview.monitor.dispatcher.facade.ManageFacade;
import com.fable.insightview.monitor.dispatcher.notify.TaskDispatchNotification;
import com.fable.insightview.monitor.dispatcher.server.TaskDispatcherServer;
import com.fable.insightview.monitor.environmentmonitor.entity.MOZoneManagerBean;
import com.fable.insightview.monitor.environmentmonitor.service.IEnvMonitorService;
import com.fable.insightview.monitor.environmentmonitor.service.IZoneManagerService;
import com.fable.insightview.monitor.middleware.middlewarecommunity.entity.SysMiddleWareCommunityBean;
import com.fable.insightview.monitor.middleware.middlewarecommunity.service.ISysMiddleWareCommunityService;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJMXBean;
import com.fable.insightview.monitor.middleware.tomcat.service.IMiddlewareService;
import com.fable.insightview.monitor.mobject.mapper.MobjectInfoMapper;
import com.fable.insightview.monitor.molist.entity.SysMoInfoBean;
import com.fable.insightview.monitor.molist.entity.SysMonitorsTemplateBean;
import com.fable.insightview.monitor.molist.entity.SysMonitorsTemplateUsedBean;
import com.fable.insightview.monitor.molist.service.ISysMoService;
import com.fable.insightview.monitor.perf.entity.MoInfoBean;
import com.fable.insightview.monitor.perf.entity.PerfPollTaskBean;
import com.fable.insightview.monitor.perf.entity.PerfTaskInfoBean;
import com.fable.insightview.monitor.perf.mapper.PerfKPIDefMapper;
import com.fable.insightview.monitor.perf.mapper.PerfPollTaskMapper;
import com.fable.insightview.monitor.perf.mapper.PerfTaskInfoMapper;
import com.fable.insightview.monitor.perf.service.IObjectPerfConfigService;
import com.fable.insightview.monitor.perf.service.IPerfGeneralConfigService;
import com.fable.insightview.monitor.perfTask.PerfTaskNotify;
import com.fable.insightview.monitor.snmpcommunitytemp.entity.SNMPCommunityTempBean;
import com.fable.insightview.monitor.snmpcommunitytemp.service.ISNMPCommunityTempService;
import com.fable.insightview.monitor.sysdbmscommunity.entity.SysDBMSCommunityBean;
import com.fable.insightview.monitor.sysdbmscommunity.service.ISysDBMSCommunityService;
import com.fable.insightview.monitor.sysroomcommunity.entity.SysRoomCommunityBean;
import com.fable.insightview.monitor.sysroomcommunity.service.ISysRoomCommunityService;
import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.manufacturer.service.IManufacturerService;
import com.fable.insightview.platform.snmpcommunity.entity.SysVMIfCommunityBean;
import com.fable.insightview.platform.snmpcommunity.service.ISnmpCommunityService;
import com.fable.insightview.platform.snmpcommunity.service.ISysAuthCommunityService;
import com.fable.insightview.platform.snmpcommunity.service.ISysVMIfCommunityService;

/**
 * @Description: 监测对象配置项
 * @Date 2014-11-5
 */
@Controller
@RequestMapping("/monitor/configObjMgr")
public class ConfigObjController {
	@Autowired 
	IConfigObjService configObjService;
	@Autowired
	MobjectInfoMapper mobjectInfoMapper;
	@Autowired
	MODeviceMapper moDeviceMapper;
	@Autowired
	IOracleService orclService;
	@Autowired
	IMiddlewareService imiddlewareService;
	@Autowired
	IManufacturerService manufacturerService;
	@Autowired
	ISysAuthCommunityService sysAuthCommunityService;
	@Autowired
	ISysVMIfCommunityService sysVMIfCommunityService;
	@Autowired
	ISnmpCommunityService snmpCommunityService;
	@Autowired
	PerfKPIDefMapper perfKPIDefMapper;
	@Autowired
	PerfPollTaskMapper perfPollTaskMapper;
	@Autowired
	PerfTaskInfoMapper perfTaskInfoMapper;
	@Autowired
	ISysDBMSCommunityService sysDBMSCommunityService;
	@Autowired
	ISysMiddleWareCommunityService middleWareCommunityService;
	@Autowired
	IMyService myService;
	@Autowired
	IMObjectResSynService mobjectResSyn;
	@Autowired
	ISysRoomCommunityService roomCommunityService;
	@Autowired
	IPerfGeneralConfigService configService;
	@Autowired
	IZoneManagerService zoneManagerService;
	@Autowired
	IEnvMonitorService envMonitorService;
	@Autowired
	IDb2Service db2Service;
	@Autowired
	ISNMPCommunityTempService snmpCommunityTempService;
	@Autowired
	ISyBaseService sybaseService;
	@Autowired
	IMsServerService msService;
	@Autowired
	IObjectPerfConfigService objectPerfConfigService;
	@Autowired
	ISysMoService sysMoService;

	PerfTaskNotify perfTaskNotify = new PerfTaskNotify();
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final static Logger logger = LoggerFactory
			.getLogger(ConfigObjController.class);

	/**
	 * 打开配置页面
	 */
	@RequestMapping("/toSetMonitor")
	public ModelAndView toSetMonitor(HttpServletRequest request, ModelMap map,
			MODeviceObj vo) {
		logger.info("加载配置页面");
		System.out.println("加载配置页面");
		String deviceip = request.getParameter("deviceip");
		String moAlias = request.getParameter("moAlias");
		if("null".equals(moAlias)|| moAlias==null){
			moAlias="";
		}
		String moid = request.getParameter("moid");
		String moClassId = request.getParameter("moClassId");
		String nemanufacturername = request.getParameter("nemanufacturername");
		String dbName = request.getParameter("dbName");
		String port = "-1";
		if (!"".equals(request.getParameter("port"))
				&& request.getParameter("port") != null) {
			port = request.getParameter("port");
		}
		String className = mobjectInfoMapper.getMobjectById(
				Integer.parseInt(moClassId)).getClassLable();
		int classID = Integer.parseInt(moClassId);
		String taskId = "";
		PerfTaskInfoBean perftask = perfTaskInfoMapper.getTaskByMOIDAndDBName(Integer.parseInt(moid),dbName);
		if (perftask != null) {
			taskId = perftask.getTaskId() + "";
		}
		
		//获得模板的id
		int templateID = objectPerfConfigService.getTemplateID(Integer.parseInt(moid), Integer.parseInt(moClassId));
		List<SysMonitorsTemplateBean> templateLst = sysMoService.queryMoTemplatesByClassID(Integer.parseInt(moClassId));
		Map<Integer,String> templateMap = new HashMap<Integer,String>();
		for (int i = 0; i < templateLst.size(); i++) {
			templateMap.put(templateLst.get(i).getTemplateID(), templateLst.get(i).getTemplateName());
		}
		
		//二层、三层交换机需加上交换机的模板
		if(classID == 59 || classID == 60){
			List<SysMonitorsTemplateBean> templateParentLst = sysMoService.queryMoTemplatesByClassID(Integer.parseInt("6"));
			for (int i = 0; i < templateParentLst.size(); i++) {
				templateMap.put(templateParentLst.get(i).getTemplateID(), templateParentLst.get(i).getTemplateName());
			}
		}
		
		map.put("deviceip", deviceip);
		map.put("moid", moid);
		map.put("moClassId", moClassId);
		map.put("nemanufacturername", nemanufacturername);
		map.put("className", className);
		map.put("taskId", taskId);
		map.put("port", port);
		map.put("dbName", dbName);
		map.put("templateID", templateID);
		map.put("templateMap", templateMap);
		map.put("flag", request.getParameter("flag"));
		map.put("moAlias", moAlias);
		return new ModelAndView("monitor/device/setMonitor");
	}

	/**
	 * 初始化SNMP凭证
	 */
	@RequestMapping("/getSNMPByIP")
	@ResponseBody
	public SNMPCommunityTempBean getSNMPByIP(HttpServletRequest request) {
		SNMPCommunityTempBean snmpcommunity = new SNMPCommunityTempBean();
		String deviceIp = request.getParameter("deviceIp");
		List<MODeviceObj> modeviceLst = moDeviceMapper.getDeviceByIpInfo(deviceIp);
		if(modeviceLst.size() > 0){
			snmpcommunity = snmpCommunityTempService.getSnmpTempByIP(modeviceLst.get(0).getDeviceip());
		}
		return snmpcommunity;
	}

	/**
	 * 初始化VMWare凭证
	 */
	@RequestMapping("/initVmwareVal")
	@ResponseBody
	public SysVMIfCommunityBean initVmwareVal(String deviceip) {
		SysVMIfCommunityBean sysVMIfCommunity = new SysVMIfCommunityBean();
		sysVMIfCommunity.setDeviceIP(deviceip);
		sysVMIfCommunity = sysVMIfCommunityService
				.getObjFromDeviceIP(sysVMIfCommunity);
		if (sysVMIfCommunity == null) {
			SysVMIfCommunityBean sysVMIfCommunity2 = new SysVMIfCommunityBean();
			
			String[] split = deviceip.split("\\.");
			String ip = split[0]+"."+split[1]+"."+split[2]+".*";
			sysVMIfCommunity2.setDeviceIP(ip);
			sysVMIfCommunity = sysVMIfCommunityService.getObjFromDeviceIP(sysVMIfCommunity2);
			
			if(sysVMIfCommunity == null){
				sysVMIfCommunity2.setDeviceIP("*");
				sysVMIfCommunity = sysVMIfCommunityService.getObjFromDeviceIP(sysVMIfCommunity2);
			}
		}
		return sysVMIfCommunity;
	}

	/**
	 * 初始化数据库凭证
	 */
	@RequestMapping("/initDBVal")
	@ResponseBody
	public SysDBMSCommunityBean initDBVal(SysDBMSCommunityBean bean) {
		SysDBMSCommunityBean dbmsCommunityBean = new SysDBMSCommunityBean();
		List<SysDBMSCommunityBean> dbmsList = sysDBMSCommunityService
				.getByIPAndPort(bean);
		if (dbmsList.size() > 0) {
			dbmsCommunityBean = dbmsList.get(0);
		} else {
			List<SysDBMSCommunityBean> dbmsList2 = new ArrayList<SysDBMSCommunityBean>();
			String[] split = bean.getIp().split("\\.");
			String ip = split[0]+"."+split[1]+"."+split[2]+".*";
			bean.setIp(ip);
			dbmsList2 = sysDBMSCommunityService.getByIPAndPort(bean);
			if(dbmsList2.size() <= 0 || dbmsList2 == null){
				bean.setIp("*");
				dbmsList2 = sysDBMSCommunityService.getByIPAndPort(bean);
			}
			
			if (dbmsList2.size() > 0) {
				dbmsCommunityBean = dbmsList2.get(0);
			}
		}
		return dbmsCommunityBean;
	}
	
	/**
	 * 初始化DB2数据库凭证
	 */
	@RequestMapping("/initDB2Val")
	@ResponseBody
	public SysDBMSCommunityBean initDB2Val(SysDBMSCommunityBean bean,HttpServletRequest request) {
		SysDBMSCommunityBean dbmsCommunityBean = new SysDBMSCommunityBean();
		String dbName = request.getParameter("dbName");
		dbmsCommunityBean.setDbName(dbName);
		List<SysDBMSCommunityBean> dbmsList = sysDBMSCommunityService.getByIPAndTypeAndPortAndName(bean);
		if (dbmsList.size() > 0) {
			dbmsCommunityBean = dbmsList.get(0);
		} else {
			List<SysDBMSCommunityBean> dbmsList2 = new ArrayList<SysDBMSCommunityBean>();
			String[] split = bean.getIp().split("\\.");
			String ip = split[0]+"."+split[1]+"."+split[2]+".*";
			bean.setIp(ip);
			dbmsList2 = sysDBMSCommunityService.getByIPAndTypeAndPortAndName(bean);
			if(dbmsList2.size() <= 0 || dbmsList2 == null){
				bean.setIp("*");
				dbmsList2 = sysDBMSCommunityService.getByIPAndTypeAndPortAndName(bean);
			}
			
			if (dbmsList2.size() > 0) {
				dbmsCommunityBean = dbmsList2.get(0);
			}
		}
		return dbmsCommunityBean;
	}

	/**
	 * 初始化中间件凭证
	 */
	@RequestMapping("/initMiddleWareVal")
	@ResponseBody
	public SysMiddleWareCommunityBean initMiddleWareVal(
			SysMiddleWareCommunityBean bean) {
		SysMiddleWareCommunityBean middleWareCommunityBean = new SysMiddleWareCommunityBean();
		middleWareCommunityBean = middleWareCommunityService
				.getCommunityByIPAndPort(bean);
		if (middleWareCommunityBean == null) {
			String[] split = bean.getIpAddress().split("\\.");
			String ip = split[0]+"."+split[1]+"."+split[2]+".*";
			bean.setIpAddress(ip);
			
			middleWareCommunityBean = middleWareCommunityService.getCommunityByIPAndPort(bean);
			if(middleWareCommunityBean == null){
				bean.setIpAddress("*");
				middleWareCommunityBean = middleWareCommunityService.getCommunityByIPAndPort(bean);
			}
		}
		return middleWareCommunityBean;
	}

	/**
	 * 初始化机房监控环境凭证
	 */
	@RequestMapping("/initRoomDetail")
	@ResponseBody
	public SysRoomCommunityBean initRoomDetail(SysRoomCommunityBean bean) {
		SysRoomCommunityBean roomCommunityBean = new SysRoomCommunityBean();
		roomCommunityBean = roomCommunityService
				.getRoomCommunityByIPAndPort(bean);
		if (roomCommunityBean == null) {
			String[] split = bean.getIpAddress().split("\\.");
			String ip = split[0]+"."+split[1]+"."+split[2]+".*";
			bean.setIpAddress(ip);
			roomCommunityBean = roomCommunityService.getRoomCommunityByIPAndPort(bean);
			if(roomCommunityBean == null){
				bean.setIpAddress("*");
				roomCommunityBean = roomCommunityService.getRoomCommunityByIPAndPort(bean);
			}
		}
		return roomCommunityBean;
	}

	/**
	 * 更新对象类型
	 */
	@RequestMapping("/updateClass")
	@ResponseBody
	public boolean updateClass(HttpServletRequest request, MODeviceObj device) {
		int moClassId = device.getMoClassId();
		int moid = device.getMoid();
		String taskIdStr = request.getParameter("taskId");
		String moAlias = request.getParameter("moAlias");
		int port = Integer.parseInt(request.getParameter("port"));
		try {
			if (moClassId == 15 || moClassId == 16 || moClassId == 54 || moClassId==81|| moClassId==86) {
				MODBMSServerBean dbServerBean = new MODBMSServerBean();
				dbServerBean.setMoClassId(moClassId);
				dbServerBean.setMoid(moid);
				if (moClassId == 15) {
					dbServerBean.setDbmstype("oracle");
				} else if (moClassId == 16) {
					dbServerBean.setDbmstype("mysql");
				} else if (moClassId == 54) {
					dbServerBean.setDbmstype("db2");
				}else if (moClassId == 81) {
					dbServerBean.setDbmstype("sybase");
				}else if (moClassId == 86) {
					dbServerBean.setDbmstype("mssql");
				}
				dbServerBean.setPort(port);
				dbServerBean.setMoalias(moAlias);
				orclService.updateDBMSServerMOClassID(dbServerBean);
			} else if (moClassId == 19 || moClassId == 20 || moClassId == 53) {
				MOMiddleWareJMXBean jmxBean = new MOMiddleWareJMXBean();
				jmxBean.setMoId(moid);
				jmxBean.setMoClassId(moClassId);
				if (moClassId == 19) {
					jmxBean.setJmxType("websphere");
					jmxBean.setPortType("websphere");
				} else if (moClassId == 20) {
					jmxBean.setJmxType("tomcat");
					jmxBean.setPortType("tomcat");
				} else if (moClassId == 53) {
					jmxBean.setJmxType("weblogic");
					jmxBean.setPortType("weblogic");
				}
				jmxBean.setPort(port);
				jmxBean.setMoalias(moAlias);
				imiddlewareService.updateMiddleWareJMX(jmxBean);
			} else if (moClassId == 44) {
				MOZoneManagerBean zoneManagerBean = new MOZoneManagerBean();
				zoneManagerBean.setMoID(moid);
				zoneManagerBean.setMoClassId(moClassId);

				zoneManagerService.updateZoneManager(zoneManagerBean);
			} else {
				device.setMoalias(moAlias);
				moDeviceMapper.updateModeviceMOClassID(device);
			}

			// 修改对应的性能采集的对象类型
			if (!"".endsWith(taskIdStr) && taskIdStr != null) {
				PerfTaskInfoBean perfTask = new PerfTaskInfoBean();
				perfTask.setMoClassId(moClassId);
				perfTask.setTaskId(Integer.parseInt(taskIdStr));
				perfPollTaskMapper.updateTaskClassID(perfTask);
			}
			return true;
		} catch (Exception e) {
			logger.error("更新对象类型异常：" + e);
			return false;
		}
	}

	/**
	 * 配置SNMP
	 */
//	@RequestMapping("/setSnmpCommunity")
//	@ResponseBody
//	public boolean setSnmpCommunity(SNMPCommunityBean bean,
//			HttpServletRequest request) {
//		boolean result = false;
//		String flag = request.getParameter("flag");
//		PerfTaskInfoBean perfTaskInfoBean = new PerfTaskInfoBean();
//		perfTaskInfoBean.setDeviceIp(bean.getDeviceIP());
//		int count = perfTaskInfoMapper.isExsitSnmp(perfTaskInfoBean);
//		if ("update".equals(flag) && count > 0) {
//			try {
//				perfTaskInfoMapper.updateSnmpCommunity(bean);
//				result = true;
//			} catch (Exception e) {
//				logger.error("更新SNMP异常：" + e);
//				result = false;
//			}
//
//		} else {
//			try {
//				// perfPollTaskMapper.addSnmpCommunity(bean);
//				snmpCommunityService.addSnmpCommunity(bean);
//				result = true;
//			} catch (Exception e) {
//				System.out.println("新增SNMP异常：" + e);
//				result = false;
//			}
//		}
//		return result;
//	}

	/**
	 * 配置vmware
	 */
	@RequestMapping("/setVMwareCommunity")
	@ResponseBody
	public boolean setVMwareCommunity(SysVMIfCommunityBean bean,
			HttpServletRequest request) {
		boolean result = false;
		String flag = request.getParameter("flag");
		List<SysVMIfCommunityBean> vmwareLst = sysVMIfCommunityService
				.getSysAuthCommunityByConditions(bean);
		if ("update".equals(flag) && vmwareLst.size() > 0) {
			try {
				perfTaskInfoMapper.updateVMIfCommunity(bean);
				result = true;
			} catch (Exception e) {
				logger.error("更新vmware异常：" + e);
				result = false;
			}
		} else {
			try {
				sysVMIfCommunityService.addSysAuthCommunity(bean);
				result = true;
			} catch (Exception e) {
				logger.error("新增vmware异常：" + e);
				result = false;
			}

		}
		return result;
	}

	/**
	 * 配置数据库凭证
	 */
	@RequestMapping("/setDBMSCommunity")
	@ResponseBody
	public boolean setDBMSCommunity(SysDBMSCommunityBean bean,
			HttpServletRequest request) {
		boolean result = false;
		String flag = request.getParameter("flag");
		boolean isExsitDBMS = sysDBMSCommunityService.checkbeforeAdd(bean);
		if ("update".equals(flag) && isExsitDBMS == false) {
			try {
				if(!"DB2".equals(bean.getDbmsType())){
					sysDBMSCommunityService.updateDBMSCommunity(bean);
				}else{
					sysDBMSCommunityService.updateDBMSCommunity2(bean);
				}
				result = true;
			} catch (Exception e) {
				logger.error("更新数据库凭证异常：" + e);
				result = false;
			}

		} else {
			try {
				sysDBMSCommunityService.addDBMSCommunity(bean);
				result = true;
			} catch (Exception e) {
				logger.error("新增数据库凭证异常：" + e);
				result = false;
			}
		}
		return result;
	}

	/**
	 * 配置中间件凭证
	 */
	@RequestMapping("/setMiddleCommunity")
	@ResponseBody
	public boolean setMiddleCommunity(SysMiddleWareCommunityBean bean,
			HttpServletRequest request) {
		boolean result = false;
		String flag = request.getParameter("flag");
		boolean isExisMiddleWare = middleWareCommunityService
				.isExistMiddleWare(bean);
		if ("update".equals(flag) && isExisMiddleWare == true) {
			try {
				middleWareCommunityService.updateMiddleWareCommunity(bean);
				result = true;
			} catch (Exception e) {
				logger.error("更新中间件凭证异常：" + e);
				result = false;
			}
		} else {
			try {
				middleWareCommunityService.insertMiddleWareCommunity(bean);
				result = true;
			} catch (Exception e) {
				logger.error("新增中间件凭证异常：" + e);
				result = false;
			}
		}
		return result;
	}

	/**
	 * 配置机房环境监控凭证
	 */
	@RequestMapping("/setRoomCommunity")
	@ResponseBody
	public boolean setRoomCommunity(SysRoomCommunityBean bean,
			HttpServletRequest request) {
		boolean result = false;
		String flag = request.getParameter("flag");
		boolean isExistRoom = roomCommunityService.isExistRoom(bean);
		if ("update".equals(flag) && isExistRoom == true) {
			try {
				roomCommunityService.updateCommunityByIP(bean);
				result = true;
			} catch (Exception e) {
				logger.error("更新机房环境监控凭证异常：" + e);
				result = false;
			}
		} else {
			try {
				roomCommunityService.insertRoomCommunity(bean);
				result = true;
			} catch (Exception e) {
				logger.error("新增机房环境监控凭证异常：" + e);
				result = false;
			}
		}
		return result;
	}

	/**
	 * 新增监测器
	 */
	@RequestMapping("/doSetMonitors")
	@ResponseBody
	public Map<String, Object> doSetMonitors(PerfTaskInfoBean bean,
			HttpServletRequest request) throws Exception {
		logger.info("新增任务");
		Map<String, Object> result = new HashMap<String, Object>();
		boolean setTemplateFlag = true;
		boolean isInMoType = true;
		boolean isNoMonitor = true;
		boolean deleteMoFlag = true;
		boolean insertMoFlag = true;
		boolean insertTask = true;
		
		//绑定模板
		SysMonitorsTemplateUsedBean templateUsedBean = new SysMonitorsTemplateUsedBean();
		templateUsedBean.setMoID(bean.getMoId());
		templateUsedBean.setMoClassID(bean.getMoClassId());
		templateUsedBean.setTemplateID(bean.getTemplateID());
		setTemplateFlag = objectPerfConfigService.setTmemplate(templateUsedBean);
		
		if(setTemplateFlag == false){
			result.put("setTemplateFlag", setTemplateFlag);
			return result;
		}else{
			//该设备是否有监测器
			boolean setMonitorsRS = false;
			//监测器中是否有该模板的监测器
			int taskId = bean.getTaskId();
			boolean isAdd = false;
			int templateID = Integer.parseInt(request.getParameter("templateID"));
			List<SysMoInfoBean> monitorList = new ArrayList<SysMoInfoBean>();
			
			
			//使用模板，判断该设备是否有监测器
			if(templateID != -1){
				int moClassId = Integer.parseInt(request.getParameter("moClassId"));
				monitorList = objectPerfConfigService.getMoList(bean.getMoId(), moClassId);
				if(monitorList.size() == 0 || monitorList == null){
					isNoMonitor = false;
					result.put("isNoMonitor", isNoMonitor);
					return result;
				}else{
					isNoMonitor = true;
					String moTypeLstJson = request.getParameter("moTypeLstJson");
					List<String> moTypeList = JSON.parseArray(moTypeLstJson,String.class);
					List<Integer> monitorIds = new ArrayList<Integer>();
					for (int i = 0; i < moTypeList.size(); i++) {
						String[] moTypeInfoLst = moTypeList.get(i).split(",");
						int monitorTypeID = Integer.parseInt(moTypeInfoLst[0]);
						for (int j = 0; j < monitorList.size(); j++) {
							if(monitorList.get(j).getMonitorTypeID() != null){
								if(monitorTypeID == monitorList.get(j).getMonitorTypeID()){
									monitorIds.add(monitorList.get(j).getMid());
								}
							}
						}
					}
					if(monitorIds.size() <= 0){
						isInMoType = false;
						result.put("isNoMonitor", isNoMonitor);
						result.put("isInMoType", isInMoType);
						return result;
					}
				}
			}
			
			String flag = request.getParameter("flag");
			if ("edit".equals(flag) || taskId != -1) {
				try {
					perfTaskInfoMapper.deleteMoList(taskId);
					deleteMoFlag = true;
				} catch (Exception e) {
					logger.error("删除原有的监测器异常：" + e);
					deleteMoFlag = false;
				}
			} else {
				deleteMoFlag = true;
			}
			
			if (!"".equals(bean.getMoList())) {
				PerfTaskInfoBean task = perfTaskInfoMapper.getTaskByTaskId(bean
						.getTaskId());
				if (taskId == -1 || task == null) {
					List<PerfPollTaskBean> vCenterTaskLst = new ArrayList<PerfPollTaskBean>();
					if(bean.getMoClassId() == 8){
						vCenterTaskLst = perfPollTaskMapper.getVCenterTask(bean.getMoId());
					}
					if(bean.getMoClassId() != 8 || vCenterTaskLst.size() <= 0){
						isAdd = true;
						SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
						.getSession().getAttribute("sysUserInfoBeanOfSession");
						bean.setCreator(sysUserInfoBeanTemp.getId());
						bean.setCreateTime(dateFormat.format(new Date()));
						bean.setOperateStatus(1);// 操作状态
						bean.setProgressStatus(1);// 进度状态
						bean.setLastOPResult(0);
						bean.setCollectorId(-1);
						bean.setOldCollectorId(-1);
						bean.setLastStatusTime(dateFormat.format(new Date()));
						String dbName = request.getParameter("dbName");
						bean.setDbName(dbName);
						try {
							perfPollTaskMapper.insertTaskInfo(bean);
							insertTask = true;
						} catch (Exception e) {
							logger.error("新增采集任务异常：" + e);
							insertTask = false;
						}
						taskId = bean.getTaskId();
					}else{
						//vCenter任务已经存在
						result.put("isVCenterTask", true);
						return result;
					}
				} else {
					bean.setStatus(task.getStatus());
					// 更新采集任务
					SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
					.getSession().getAttribute("sysUserInfoBeanOfSession");
					task.setMoId(bean.getMoId());
					task.setStatus(task.getStatus());
					task.setCreator(sysUserInfoBeanTemp.getId());
					task.setCreateTime(dateFormat.format(new Date()));
					task.setTaskId(taskId);
					task.setOperateStatus(2); // 操作状态
					task.setMoClassId(bean.getMoClassId());
				if (task.getStatus() != 1) {
					task.setProgressStatus(1);
				}
					task.setLastStatusTime(dateFormat.format(new Date()));// 最近状态时间
					perfTaskInfoMapper.updateTask(task);
				}
				//没有使用模板
				if(templateID == -1){
					logger.info("将任务ID=" + taskId + "的监测器是：" + bean.getMoList());
					String[] moList = bean.getMoList().split(",");
					String[] moIntervalList = bean.getMoIntervalList().split(",");
					String[] moTimeUnitList = bean.getMoTimeUnitList().split(",");
					int moInterval = -1;
					for (int i = 0; i < moList.length; i++) {
						logger.info("监测器ID=" + moList[i] + "的监测器入库");
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
						}
					}
				}
				//使用模板
				else{
					String moTypeLstJson = request.getParameter("moTypeLstJson");
					List<String> moTypeList = JSON.parseArray(moTypeLstJson,String.class);
					for (int i = 0; i <moTypeList.size(); i++) {
						String[] moTypeInfoLst = moTypeList.get(i).split(",");
						int monitorTypeID = Integer.parseInt(moTypeInfoLst[0]);
						int timeInterval = Integer.parseInt(moTypeInfoLst[2]);
						int timeUnit = Integer.parseInt(moTypeInfoLst[3]);
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
								}
							}
						}
					}
				}
			}
			
			if (insertMoFlag == true && deleteMoFlag == true && insertTask == true) {
				perfTaskNotify.notifyDisPatch();
				if (isAdd == true) {
					// 删除其虚拟机的采集任务
					if (bean.getMoClassId() == 8) {
						List<Integer> vmMoIds = new ArrayList<Integer>();
						// 获得其虚拟机信息
						List<MODeviceObj> vmLst = moDeviceMapper.getVms(bean
								.getMoId());
						if (vmLst.size() > 0) {
							for (int i = 0; i < vmLst.size(); i++) {
								if (vmLst.get(i).getMoClassId() == 9) {
									vmMoIds.add(vmLst.get(i).getMoid());
								}
							}
						}
						if (vmMoIds.size() > 0) {
							for (int i = 0; i < vmMoIds.size(); i++) {
								int moId = vmMoIds.get(i);
								int isExsitTask = perfTaskInfoMapper
								.isExsitTask(moId);
								if (isExsitTask > 0) {
									int exsitTaskId = perfTaskInfoMapper
									.exsitTaskId(moId);
									PerfPollTaskBean perfTask = new PerfPollTaskBean();
									perfTask.setTaskId(exsitTaskId);
									perfTask.setOperateStatus(3);
									perfTask.setProgressStatus(1);
									int deleteTask = perfTaskInfoMapper
									.updateTaskStatus(perfTask);
									perfTaskInfoMapper.deleteMoList(exsitTaskId);
								} else {
									continue;
								}
							}
						Dispatcher dispatcher = Dispatcher.getInstance();
						ManageFacade facade = dispatcher.getManageFacade();
						List<TaskDispatcherServer> servers = facade
								.listActiveServersOf(TaskDispatcherServer.class);
						if (servers.size() > 0) {
							String topic = "TaskDispatch";
							TaskDispatchNotification message = new TaskDispatchNotification();
							message.setDispatchTaskType(TaskType.TASK_COLLECT);
							facade.sendNotification(servers.get(0).getIp(),
									topic, message);
						}
						}
					}
					//对象类型为vCenter
					else if(bean.getMoClassId() == 75){
						String vCenterIP = bean.getDeviceIp();
						List<MODeviceObj> phLst = moDeviceMapper.getPhs(vCenterIP);
						List<Integer> phIds = new ArrayList<Integer>();
						if(phLst.size() > 0){
							for (int i = 0; i < phLst.size(); i++) {
								phIds.add(phLst.get(i).getMoid());
							}
						}
						
						//删除对应的宿主机的任务
						if(phIds.size() > 0){
							for (int j = 0; j < phIds.size(); j++) {
								int moId = phIds.get(j);
								int isExsitTask = perfTaskInfoMapper.isExsitTask(moId);
								if (isExsitTask > 0) {
									int exsitTaskId = perfTaskInfoMapper.exsitTaskId(moId);
									PerfPollTaskBean perfTask = new PerfPollTaskBean();
									perfTask.setTaskId(exsitTaskId);
									perfTask.setOperateStatus(3);
									perfTask.setProgressStatus(1);
									perfTaskInfoMapper.updateTaskStatus(perfTask);
									perfTaskInfoMapper.deleteMoList(exsitTaskId);
								} else {
									continue;
								}
							}
						Dispatcher dispatcher = Dispatcher.getInstance();
						ManageFacade facade = dispatcher.getManageFacade();
						List<TaskDispatcherServer> servers = facade
						.listActiveServersOf(TaskDispatcherServer.class);
						if (servers.size() > 0) {
							String topic = "TaskDispatch";
							TaskDispatchNotification message = new TaskDispatchNotification();
							message
							.setDispatchTaskType(TaskType.TASK_COLLECT);
							facade.sendNotification(servers.get(0).getIp(),
									topic, message);
						}
						}
					}
					//非虚拟宿主机
					else{
					perfTaskNotify.notifyDisPatch();
					}
				}else{
					//更新的情况
					if (bean.getStatus() != 1) {
					perfTaskNotify.notifyDisPatch();
					}
				}
				setMonitorsRS = true;
			} else {
				setMonitorsRS = false;
			}
			result.put("setTemplateFlag", setTemplateFlag);
			result.put("isNoMonitor", isNoMonitor);
			result.put("isInMoType", isInMoType);
			result.put("isVCenterTask", false);
			result.put("setMonitorsRS", setMonitorsRS);
			result.put("taskId", taskId);
			return result;
		}
		
	}

	/**
	 * 加载阈值表格数据
	 */
	@RequestMapping("/listMOKPIThreshold")
	@ResponseBody
	public Map<String, Object> listThreshold(
			MOKPIThresholdBean mokpiThresholdBean) {
		return configObjService.listThreshold(mokpiThresholdBean);
	}

	

	@RequestMapping("/initCollectPeriodVal")
	@ResponseBody
	public int initCollectPeriodVal(HttpServletRequest request) {

		logger.info("获得获得对象默认的采集周期。。。start  ");
		String moClassId = request.getParameter("moClassId");
		String className = mobjectInfoMapper.getMobjectById(
				Integer.parseInt(moClassId)).getClassName();
		logger.info("对象类型为。。。  " + className);
		return configService.initCollectPeriodVal(className);
	}

	@RequestMapping("/isDevice")
	@ResponseBody
	public boolean isDevice(HttpServletRequest request) {
		String moClassId = request.getParameter("moClassId");
		String deviceip = request.getParameter("deviceip");
		String moId = request.getParameter("moid");
		MODeviceObj device = new MODeviceObj();
		device.setMoClassId(Integer.parseInt(moClassId));
		device.setMoid(Integer.parseInt(moId));
		device.setDeviceip(deviceip);
		MODeviceObj bean = moDeviceMapper.getInfoByIPAndClass(device);
		if (bean == null) {
			return false;
		} else {
			return true;
		}
	}

	@RequestMapping("/isDatabase")
	@ResponseBody
	public boolean isDatabase(HttpServletRequest request) {
		int moClassId = Integer.parseInt(request.getParameter("moClassId"));
		String ip = request.getParameter("ip");
		int port = Integer.parseInt(request.getParameter("port"));
		String moId = request.getParameter("moid");
		MODBMSServerBean dbms = new MODBMSServerBean();
		dbms.setMoClassId(moClassId);
		dbms.setIp(ip);
		dbms.setPort(port);
		dbms.setMoid(Integer.parseInt(moId));
		MODBMSServerBean bean = orclService.getDBMSServerByIpAndTypeAlias(dbms);
		if (bean == null) {
			return false;
		} else {
			return true;
		}
	}

	@RequestMapping("/isMiddle")
	@ResponseBody
	public boolean isMiddle(HttpServletRequest request) {
		int moClassId = Integer.parseInt(request.getParameter("moClassId"));
		String ip = request.getParameter("ip");
		int port = Integer.parseInt(request.getParameter("port"));
		String moId = request.getParameter("moid");
		String jmxType = "websphere";
		if (moClassId == 19) {
			jmxType = "websphere";
		} else if (moClassId == 20) {
			jmxType = "tomcat";
		} else if (moClassId == 53) {
			jmxType = "weblogic";
		}
		MOMiddleWareJMXBean jmx = new MOMiddleWareJMXBean();
		jmx.setIp(ip);
		jmx.setJmxType(jmxType);
		jmx.setPort(port);
		jmx.setMoId(Integer.parseInt(moId));
		MOMiddleWareJMXBean bean = imiddlewareService
				.getJMXInfoByIPAndTypeAlias(jmx);
		if (bean == null) {
			return false;
		} else {
			return true;
		}
	}

	@RequestMapping("/isRoom")
	@ResponseBody
	public boolean isRoom(HttpServletRequest request) {
		int moClassId = Integer.parseInt(request.getParameter("moClassId"));
		String ip = request.getParameter("ip");
		int port = Integer.parseInt(request.getParameter("port"));
		MOZoneManagerBean zoneManagerBean = new MOZoneManagerBean();
		zoneManagerBean.setMoClassId(moClassId);
		zoneManagerBean.setIpAddress(ip);
		zoneManagerBean.setPort(port);
		MOZoneManagerBean bean = zoneManagerService
				.getZoneManagerByIP(zoneManagerBean);
		if (bean == null) {
			return false;
		} else {
			return true;
		}
	}

	@RequestMapping("/getMiddleWareCommunity")
	@ResponseBody
	public SysMiddleWareCommunityBean getMiddleWareCommunity(
			SysMiddleWareCommunityBean bean) {
		SysMiddleWareCommunityBean middleWareCommunityBean = middleWareCommunityService
				.getCommunityByIPAndPort(bean);
		return middleWareCommunityBean;
	}
	
	/**
	 * 获得设备配置的模板中的监测器类型
	 */
	@RequestMapping("/listMoListByTemplete")
	@ResponseBody
	public List<String> listMoListByTemplete(HttpServletRequest request){
		int templateID = Integer.parseInt(request.getParameter("templateID"));
		return objectPerfConfigService.listMoListByTemplete(templateID);
	}
}
