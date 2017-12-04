package com.fable.insightview.monitor.perfTask.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.alibaba.fastjson.JSON;
import com.fable.insightview.monitor.database.entity.MODBMSServerBean;
import com.fable.insightview.monitor.database.service.IOracleService;
import com.fable.insightview.monitor.discover.entity.MODeviceObj;
import com.fable.insightview.monitor.discover.mapper.MODeviceMapper;
import com.fable.insightview.monitor.dispatcher.Dispatcher;
import com.fable.insightview.monitor.dispatcher.constants.TaskType;
import com.fable.insightview.monitor.dispatcher.facade.ManageFacade;
import com.fable.insightview.monitor.dispatcher.notify.TaskDispatchNotification;
import com.fable.insightview.monitor.dispatcher.server.TaskDispatcherServer;
import com.fable.insightview.monitor.environmentmonitor.entity.MOZoneManagerBean;
import com.fable.insightview.monitor.environmentmonitor.service.IZoneManagerService;
import com.fable.insightview.monitor.middleware.middlewarecommunity.entity.SysMiddleWareCommunityBean;
import com.fable.insightview.monitor.middleware.middlewarecommunity.service.ISysMiddleWareCommunityService;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJMXBean;
import com.fable.insightview.monitor.middleware.tomcat.service.IMiddlewareService;
import com.fable.insightview.monitor.mobject.entity.MObjectDefBean;
import com.fable.insightview.monitor.molist.entity.SysMoInfoBean;
import com.fable.insightview.monitor.molist.entity.SysMonitorsTemplateBean;
import com.fable.insightview.monitor.molist.entity.SysMonitorsTemplateUsedBean;
import com.fable.insightview.monitor.perf.entity.MoInfoBean;
import com.fable.insightview.monitor.perf.entity.PerfPollTaskBean;
import com.fable.insightview.monitor.perf.entity.PerfTaskInfoBean;
import com.fable.insightview.monitor.perf.mapper.PerfPollTaskMapper;
import com.fable.insightview.monitor.perf.mapper.PerfTaskInfoMapper;
import com.fable.insightview.monitor.perf.service.IObjectPerfConfigService;
import com.fable.insightview.monitor.perf.service.IPerfTaskService;
import com.fable.insightview.monitor.perfTask.PerfTaskNotify;
import com.fable.insightview.monitor.sysdbmscommunity.entity.SysDBMSCommunityBean;
import com.fable.insightview.monitor.sysdbmscommunity.mapper.SysDBMSCommunityMapper;
import com.fable.insightview.monitor.sysdbmscommunity.service.ISysDBMSCommunityService;
import com.fable.insightview.monitor.sysroomcommunity.entity.SysRoomCommunityBean;
import com.fable.insightview.monitor.sysroomcommunity.service.ISysRoomCommunityService;
import com.fable.insightview.monitor.website.entity.SiteDns;
import com.fable.insightview.monitor.website.entity.SiteFtp;
import com.fable.insightview.monitor.website.entity.SiteHttp;
import com.fable.insightview.monitor.website.entity.SitePort;
import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.snmpcommunity.dao.ISnmpCommunityDao;
import com.fable.insightview.platform.snmpcommunity.entity.SysVMIfCommunityBean;
import com.fable.insightview.platform.snmpcommunity.service.ISnmpCommunityService;
import com.fable.insightview.platform.snmpcommunity.service.ISysVMIfCommunityService;

@Controller
@RequestMapping("/monitor/perfTask")
public class PerfTaskController {
	@Autowired
	PerfPollTaskMapper perfPollTaskMapper;
	@Autowired
	PerfTaskInfoMapper perfTaskInfoMapper;
	@Autowired
	IPerfTaskService perfTaskService;
	@Autowired
	MODeviceMapper moDeviceMapper;
	@Autowired
	ISysVMIfCommunityService sysVMIfCommunityService;
	@Autowired
	ISysDBMSCommunityService sysDBMSCommunityService;
	@Autowired
	SysDBMSCommunityMapper dbmsCommunityMapper;
	@Autowired
	IOracleService oracleService;
	@Autowired
	IMiddlewareService middlewareService;
	@Autowired
	ISysMiddleWareCommunityService middleWareCommunityService;
	@Autowired
	ISysRoomCommunityService roomCommunityService;
	@Autowired
	IZoneManagerService zoneManagerService;
	@Autowired
	ISnmpCommunityDao snmpCommunityDao;
	@Autowired
	ISnmpCommunityService snmpCommunityService;
	@Autowired
	IObjectPerfConfigService objectPerfConfigService;

	private final Logger logger = LoggerFactory
			.getLogger(PerfTaskController.class);
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	PerfTaskNotify perfTaskNotify = new PerfTaskNotify();
	private static final int FIRST_PROGRESS = 1;
	private static final int FINISH_PROGRESS = 5;
	private static final int UPDATE_OPERATESTATUS = 2;
	private static final int STOP_OPERATESTATUS = 4;

	/**
	 * 加载列表页面
	 */
	@RequestMapping("/toPerfTaskList")
	public ModelAndView toPerfTaskList(String navigationBar) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		String serverId = request.getParameter("serverId");
		if (serverId != null && !"".equals(serverId)) {
			request.setAttribute("serverId", serverId);
		} else {
			request.setAttribute("serverId", "-1");
		}
		request.setAttribute("navigationBar", navigationBar);
		return new ModelAndView("monitor/perf/perfTask_list");
	}

	/**
	 * 加载列表页面数据
	 */
	@RequestMapping("/listPerfTasks")
	@ResponseBody
	public Map<String, Object> listPerfTasks(PerfPollTaskBean taskBean)
			throws Exception {
		logger.info("加载列表页面数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		int serverId = taskBean.getServerId();
		Page<PerfPollTaskBean> page = new Page<PerfPollTaskBean>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("serverId", serverId);
		paramMap.put("deviceIp", taskBean.getDeviceIp());
		paramMap.put("taskId", taskBean.getTaskId());
		paramMap.put("status", taskBean.getStatus());
		paramMap.put("serverName", taskBean.getServerName());
		String fieldName = request.getParameter("sort");
		String sort = "";
		if("deviceIp".equals(fieldName)){
			sort = "device.DeviceIP";
		}else if("status".equals(fieldName)){
			sort = "task.Status";
		}else if("lastStatusTime".equals(fieldName)){
			sort = "task.LastStatusTime";
		}else if("className".equals(fieldName)){
			sort = "def.ClassLable";
		}else if("deviceManufacture".equals(fieldName)){
			sort = "manu.ResManufacturerName";
		}else if("deviceType".equals(fieldName)){
			sort = "rescate.ResCategoryName";
		}else if("serverName".equals(fieldName)){
			sort = "host.IPAddress";
		}
		paramMap.put("sort",sort);
		paramMap.put("order", request.getParameter("order"));
		page.setParams(paramMap);
		List<PerfPollTaskBean> taskList = perfPollTaskMapper
				.selectPerfPollTasks(page);
		int total = page.getTotalRecord();
		// 数据库服务
		for (int i = 0; i < taskList.size(); i++) {
			PerfPollTaskBean task = taskList.get(i);
			int moClassId = task.getMoClassId();
			if (moClassId == 15 || moClassId == 16 || moClassId == 54 || moClassId == 81 || moClassId == 86) {
				
				PerfTaskInfoBean perf = perfTaskInfoMapper.getByTaskIdAndMOID(task.getTaskId());
				if (perf != null) {
					String deviceIp = perf.getDeviceIp();
					task.setDeviceIp(deviceIp);
				}
			}
			if (moClassId == 19 || moClassId == 20 || moClassId == 53) {
				MOMiddleWareJMXBean middle = middlewareService
						.selectMoMidByPrimaryKey(task.getMoId());
				if (middle != null) {
					String deviceIp = middle.getIp();
					task.setDeviceIp(deviceIp);
				}
			}

			if (moClassId == 44) {
				// 获得被采设备的IP
				MOZoneManagerBean zoneManagerBean = zoneManagerService
						.getZoneManagerInfo(task.getMoId());
				if (zoneManagerBean != null) {
					String deviceIp = zoneManagerBean.getIpAddress();
					task.setDeviceIp(deviceIp);
				}
			}
			
			if(moClassId == 91 || moClassId == 92 || moClassId == 93 || moClassId == 94){
				task.setDeviceIp(perfTaskService.getSiteName(task.getMoId(), moClassId));
			}
			// 获得对应的监测器数
			int moCounts = perfPollTaskMapper
					.getMonitorCounts(task.getTaskId());
			task.setMoCounts(moCounts);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", taskList);
		logger.info("加载列表页面数据over");
		return result;
	}

	/**
	 * 根据任务获取监测器信息
	 */
	@RequestMapping("/listMoList")
	@ResponseBody
	public List<String> listMoList(PerfTaskInfoBean bean) throws Exception {
		logger.info("获取设备ID = " + bean.getMoId() + " 的监测器信息");
		return perfTaskService.listMoList(bean.getMoId());
	}

	/***
	 * 根据厂商ID获取监测器信息
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listMoListForManufacturerID")
	@ResponseBody
	public List<String> listMoListForManufacturerID(PerfTaskInfoBean bean) throws Exception {
		logger.info("获取设备ID = " + bean.getMoId() + " 的监测器信息");
		return perfTaskService.listMoListForRoom(bean.getManufacturerID());
	}
	
	/**
	 * 根据监测对象获取监测器信息
	 */
	@RequestMapping("/listDBMoList")
	@ResponseBody
	public List<String> listDBMoList(int moClassId) throws Exception {
		List<String> moList = new ArrayList<String>();
		moList = perfTaskService.getByMOClassID(moClassId);
		return moList;
	}

	/**
	 * 根据任务ID获取对应的监测器
	 */
	@RequestMapping("/listMoListByTaskId")
	@ResponseBody
	public List<String> listMoListByTaskId(PerfPollTaskBean taskBean)
			throws Exception {
		logger.info("获取任务ID = " + taskBean.getTaskId() + " 的监测器信息");
		List<String> moList = new ArrayList<String>();
		moList = perfPollTaskMapper.getMoListByTaskId(taskBean.getTaskId());
		return moList;
	}

	/**
	 * 删除任务
	 */
	@RequestMapping("/delTask")
	@ResponseBody
	public boolean delTask(PerfPollTaskBean taskBean) {
		int operateStatus = taskBean.getOperateStatus();
		if (operateStatus != 4) {
			taskBean.setProgressStatus(1);
		}
		taskBean.setOperateStatus(3);
		try {
			int i = perfTaskInfoMapper.updateTaskStatus(taskBean);
			perfTaskInfoMapper.deleteMoList(taskBean.getTaskId());
			if (i >= 1) {
				if (operateStatus != 4) {
					logger.info("向后台发送删除消息");
					perfTaskNotify.notifyDisPatch();
					logger.info("向后台发送删除消息over");
				}
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error("删除任务异常！", e);
			return false;
		}

	}

	/**
	 * 新增任务
	 */
	@RequestMapping("/addPerfTask")
	@ResponseBody
	public int addPerfTask(PerfTaskInfoBean bean) throws Exception {
		logger.info("新增任务");
		boolean setTemplateFlag = true;
		int insertTaskFlag = 0;
		int updateTaskFlag = 0;
		int deleteMoFlag = 0;
		int insertMoFlag = 0;
		int updateSnmpFlag = 0;
		int moClassId = bean.getMoClassId();
		String authType = bean.getAuthType();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		int templateID = Integer.parseInt(request.getParameter("templateID"));
		List<SysMoInfoBean> monitorList = new ArrayList<SysMoInfoBean>();
		
		//绑定模板
		SysMonitorsTemplateUsedBean templateUsedBean = new SysMonitorsTemplateUsedBean();
		templateUsedBean.setMoID(bean.getMoId());
		templateUsedBean.setMoClassID(bean.getMoClassId());
		templateUsedBean.setTemplateID(bean.getTemplateID());
		setTemplateFlag = objectPerfConfigService.setTmemplate(templateUsedBean);
		if(setTemplateFlag == false){
			return -4;
		}
		
		//使用模板，判断该设备是否有监测器
		if(templateID != -1){
			monitorList = objectPerfConfigService.getMoList(bean.getMoId(), moClassId);
			if(monitorList.size() == 0 || monitorList == null){
				return -3;
			}
		}
		
		List<PerfPollTaskBean> vCenterTaskLst = new ArrayList<PerfPollTaskBean>();
		if("Vmware".equals(authType) && moClassId == 8){
			vCenterTaskLst = perfPollTaskMapper.getVCenterTask2(bean);
		}
		if(!"Vmware".equals(authType) || vCenterTaskLst.size() <= 0){
			
			SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
			.getSession().getAttribute("sysUserInfoBeanOfSession");
			bean.setCreator(sysUserInfoBeanTemp.getId());
			bean.setCreateTime(dateFormat.format(new Date()));
			bean.setOperateStatus(1);// 操作状态
			bean.setProgressStatus(1);// 进度状态
			bean.setLastOPResult(0);
			if( bean.getCollectorId() == null ||bean.getCollectorId() == -1){
				bean.setCollectorId(-1);
			}
			bean.setOldCollectorId(-1);
			bean.setLastStatusTime(dateFormat.format(new Date()));// 最近状态时间
			int isExsit = 0;
			if(moClassId != 54){
				isExsit = perfTaskInfoMapper.isExsitTask3(bean);
			}else{
				PerfTaskInfoBean task = perfTaskInfoMapper.getTaskByMOIDAndDBName2(bean);
				if(task != null){
					isExsit = 1; 
				}
			}
			int exsitTaskId = 0;
			if (isExsit > 0) {
				logger.info("此设备已有相关任务，只更新，不插表");
				// exsitTaskId=perfTaskInfoMapper.exsitTaskId(bean.getMoId());
				// bean.setTaskId(exsitTaskId);
				// updateTaskFlag= perfTaskInfoMapper.updateTask(bean);
				// deleteMoFlag=perfTaskInfoMapper.deleteMoList(exsitTaskId);
				return 0;
			} else {
				insertTaskFlag = perfPollTaskMapper.insertTaskInfo(bean);
			}
			if ("SNMP".equals(authType)) {
				updateSnmpFlag = 1;
			} 
			else if ("Vmware".equals(authType)) {
				List<SysVMIfCommunityBean> snmpLst = sysVMIfCommunityService
				.getSysAuthCommunityByConditions(bean
						.getVmIfCommunityBean());
				if (null == snmpLst || snmpLst.size() <= 0) {
					boolean updateVMware = false;
					try {
						updateVMware = sysVMIfCommunityService
						.addSysAuthCommunity(bean.getVmIfCommunityBean());
					} catch (Exception e) {
						logger.error("插入VMware异常：" + e);
					}
					
					if (updateVMware == true) {
						updateSnmpFlag = 1;
					} else {
						updateSnmpFlag = 0;
					}
				} else {
					updateSnmpFlag = perfTaskInfoMapper.updateVMIfCommunity(bean
							.getVmIfCommunityBean());
				}
				
			} else if ("dbms".equals(authType)) {
				boolean isExsitDBMS = sysDBMSCommunityService.checkbeforeAdd(bean
						.getDbmsCommunityBean());
				if (isExsitDBMS == true) {
					boolean addDBMS = true;
					try {
						addDBMS = sysDBMSCommunityService.addDBMSCommunity(bean
								.getDbmsCommunityBean());
					} catch (Exception e) {
						addDBMS = false;
						logger.error("插入DBMS异常===" + e);
					}
					if (addDBMS == true) {
						updateSnmpFlag = 1;
					} else {
						updateSnmpFlag = 0;
					}
				} else {
					boolean updateDBMS = false ;
					if(moClassId !=54){
						updateDBMS = sysDBMSCommunityService.updateDBMSCommunity(bean.getDbmsCommunityBean());
					}else{
						updateDBMS = sysDBMSCommunityService.updateDBMSCommunity2(bean.getDbmsCommunityBean());
					}
					if (updateDBMS == true) {
						updateSnmpFlag = 1;
					} else {
						updateSnmpFlag = 0;
					}
				}
			} else if ("MiddleWare".equals(authType)) {
				boolean isExisMiddleWare = middleWareCommunityService
				.isExistMiddleWare(bean.getMiddleWareCommunityBean());
				if (isExisMiddleWare == false) {
					boolean addMiddleWare = true;
					addMiddleWare = middleWareCommunityService
					.insertMiddleWareCommunity(bean
							.getMiddleWareCommunityBean());
					if (addMiddleWare == true) {
						updateSnmpFlag = 1;
					} else {
						updateSnmpFlag = 0;
					}
				} else {
					boolean updateMiddleWare = true;
					updateMiddleWare = middleWareCommunityService
					.updateMiddleWareCommunity(bean
							.getMiddleWareCommunityBean());
					if (updateMiddleWare == true) {
						updateSnmpFlag = 1;
					} else {
						updateSnmpFlag = 0;
					}
				}
			} else if ("room".equals(authType)) {
				boolean isExistRoom = roomCommunityService.isExistRoom(bean
						.getRoomCommunityBean());
				if (isExistRoom == false) {
					boolean addRoom = true;
					addRoom = roomCommunityService.insertRoomCommunity(bean
							.getRoomCommunityBean());
					if (addRoom == true) {
						updateSnmpFlag = 1;
					} else {
						updateSnmpFlag = 0;
					}
				} else {
					boolean updateRoom = true;
					updateRoom = roomCommunityService.updateCommunityByIP(bean
							.getRoomCommunityBean());
					if (updateRoom == true) {
						updateSnmpFlag = 1;
					} else {
						updateSnmpFlag = 0;
					}
				}
			}else if ("site".equals(authType)) {
				updateSnmpFlag = perfTaskService.operateSiteCommunity(bean.getWebSite());
			}else if("unKnown".equals(authType)){
				updateSnmpFlag = 1;
			}else if("Conditons".equals(authType)){
				updateSnmpFlag = 1;
			}else if("Ups".equals(authType)){
				updateSnmpFlag = 1;
			}
			
			//没有使用模板
			if(templateID == -1){
				logger.info("将任务ID=" + bean.getTaskId() + "的监测器是：" + bean.getMoList());
				String[] moList = bean.getMoList().split(",");
				String[] moIntervalList = bean.getMoIntervalList().split(",");
				String[] moTimeUnitList = bean.getMoTimeUnitList().split(",");
				int moInterval = -1;
				for (int i = 0; i < moList.length; i++) {
					logger.info("新增任务：监测器ID=" + moList[i] + "的监测器入库");
					MoInfoBean moBean = new MoInfoBean();
					moBean.setMid(Integer.parseInt(moList[i]));
					moBean.setTaskId(bean.getTaskId());
					if(Integer.parseInt(moTimeUnitList[i]) == 0){
						moInterval = Integer.parseInt(moIntervalList[i]);
					}else if(Integer.parseInt(moTimeUnitList[i]) == 1){
						moInterval = Integer.parseInt(moIntervalList[i]) * 60;
					}else if(Integer.parseInt(moTimeUnitList[i]) == 2){
						moInterval = Integer.parseInt(moIntervalList[i]) * 3600;
					}else if(Integer.parseInt(moTimeUnitList[i]) == 3){
						moInterval = Integer.parseInt(moIntervalList[i]) * 3600 * 24;
					}
					moBean.setDoIntervals(moInterval);
					insertMoFlag = perfPollTaskMapper.insertTaskMo(moBean);
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
							moBean.setTaskId(bean.getTaskId());
							int interval = timeInterval;
							if(timeUnit == 1){
								interval = timeInterval * 60;
							}else if(timeUnit == 2){
								interval = timeInterval * 3600;
							}else if(timeUnit == 3){
								interval = timeInterval * 3600 * 24;
							}else if(timeUnit == 0){
								interval = timeInterval;
							}
							moBean.setDoIntervals(interval);
							try {
								perfPollTaskMapper.insertTaskMo(moBean);
								insertMoFlag = 1;
							} catch (Exception e) {
								logger.error("新增监测器异常：" + e);
								insertMoFlag = 0;
							}
						}
					}
				}
			}
			
			if ((insertTaskFlag >= 1 && updateSnmpFlag >= 1 && insertMoFlag >= 1)
					|| (updateTaskFlag >= 1 && deleteMoFlag >= 1
							&& updateSnmpFlag >= 1 && insertMoFlag >= 1)) {
				perfTaskNotify.notifyDisPatch();
				
				if ("Vmware".equals(authType)) {
					//虚拟宿主机
					if(moClassId == 8){
						//删除虚拟宿主机的虚拟机的采集任务
						perfTaskService.delVMsByVHost(bean);
						
					}
					//对象类型为vCenter
					else if(moClassId == 75){
						String vCenterIP = bean.getDeviceIp();
						List<MODeviceObj> phLst = moDeviceMapper.getPhs(vCenterIP);
						List<Integer> phIds = new ArrayList<Integer>();
						if(phLst.size() > 0){
							for (int i = 0; i < phLst.size(); i++) {
								phIds.add(phLst.get(i).getMoid());
							}
						}
						
						//删除对应的宿主机的任务
						int isExsitTask = 0;
						if(phIds.size() > 0){
							for (int j = 0; j < phIds.size(); j++) {
								int moId = phIds.get(j);
								PerfTaskInfoBean t = new PerfTaskInfoBean();
								t.setMoId(moId);
								t.setIsOffline(bean.getIsOffline());
								if(bean.getIsOffline() == null || "".equals(bean.getIsOffline())){
									isExsitTask = perfTaskInfoMapper.isExsitTask(moId);
								}else{
									isExsitTask = perfTaskInfoMapper.isExistOfflineTask(moId);
								}
								if (isExsitTask > 0) {
									if(bean.getIsOffline() == null || "".equals(bean.getIsOffline())){
										exsitTaskId = perfTaskInfoMapper.exsitTaskId(moId);
									}else{
										exsitTaskId = perfTaskInfoMapper.existOfflineTaskId(moId);
									}
									PerfPollTaskBean perfTask = new PerfPollTaskBean();
									perfTask.setTaskId(exsitTaskId);
									perfTask.setOperateStatus(3);
									perfTask.setProgressStatus(1);
									perfTaskInfoMapper.updateTaskStatus(perfTask);
									perfTaskInfoMapper.deleteMoList(exsitTaskId);
								} 
								//删除虚拟宿主机的虚拟机的采集任务
								perfTaskService.delVMsByVHost(t);
							}
							perfTaskNotify.notifyDisPatch();
						}
					}
				}
				return 1;
			} else {
				return -1;
			}
		}else{
			//vCenter任务已经存在
			return -2;
		}
		
	}

	/**
	 * 根据设备ID查找设备信息
	 */
	@RequestMapping("/findDeviceInfo")
	@ResponseBody
	public PerfTaskInfoBean findDeviceInfo(PerfTaskInfoBean bean)
			throws Exception {
		PerfTaskInfoBean taskBean = perfTaskInfoMapper.getDeviceById(bean
				.getMoId());
		int templateID = perfTaskService.getTemplateID(taskBean.getMoId(), taskBean.getMoClassId());
		taskBean.setTemplateID(templateID);
		return taskBean;
	}

	/**
	 * 根据任务ID获取设备信息
	 */
	@RequestMapping("/findDeviceByTaskId")
	@ResponseBody
	public PerfTaskInfoBean findDeviceByTaskId(int taskId) throws Exception {
		PerfTaskInfoBean taskBean = perfTaskInfoMapper
				.getDeviceByTaskId(taskId);

		String s = JsonUtil.toString(taskBean);
		logger.info("根据任务ID为" + taskId + "获取设备信息：" + s);
		return taskBean;
	}

	/**
	 * 初始化信息（VMware）
	 */
	@RequestMapping("/initVmwareVal")
	@ResponseBody
	public PerfTaskInfoBean initVmwareVal(int taskId) {
		PerfTaskInfoBean taskBean = perfTaskInfoMapper
				.getPerTaskInfoByTaskId(taskId);
		taskBean.setTaskId(taskId);
		SysVMIfCommunityBean vmBean = perfTaskInfoMapper
				.getVmIfCommunityInfo(taskId);
		if (vmBean == null) {
			String[] split = taskBean.getDeviceIp().split("\\.");
			String ip = split[0]+"."+split[1]+"."+split[2]+".*";
			vmBean = perfTaskInfoMapper.getVmIfCommunityByIP(ip);
			if(vmBean == null){
				vmBean = perfTaskInfoMapper.getVmIfCommunityByIP("*");
			}
		}
		taskBean.setVmIfCommunityBean(vmBean);
		return taskBean;
	}

	/**
	 * 初始化信息（DBMS）
	 */
	@RequestMapping("/initDBVal")
	@ResponseBody
	public PerfTaskInfoBean initDBVal(int taskId) {
		PerfTaskInfoBean taskBean = perfTaskInfoMapper
				.getByTaskIdAndMOID(taskId);
		taskBean.setTaskId(taskId);
		String deviceIP = taskBean.getDeviceIp();
		SysDBMSCommunityBean dbmsCommunityBean = dbmsCommunityMapper
				.getDBMSByTaskId(taskId);
		if(dbmsCommunityBean == null){
			SysDBMSCommunityBean dbBean = new SysDBMSCommunityBean();
			MODBMSServerBean server = oracleService
					.selectMoDbmsByPrimaryKey(taskBean.getMoId());
			String[] split = taskBean.getDeviceIp().split("\\.");
			String ip = split[0]+"."+split[1]+"."+split[2]+".*";
			
			dbBean.setIp(ip);
			dbBean.setDbmsType(server.getDbmstype());
			dbBean.setPort(server.getPort());
			
			List<SysDBMSCommunityBean> dbCommunityLst = new ArrayList<SysDBMSCommunityBean>();
			dbCommunityLst = sysDBMSCommunityService.getByIPAndPort(dbBean);
			
			if (dbCommunityLst != null && dbCommunityLst.size() > 0) {
				dbmsCommunityBean = dbCommunityLst.get(0);
			}else{
				dbBean.setIp("*");
				dbCommunityLst = sysDBMSCommunityService.getByIPAndPort(dbBean);
				if(dbCommunityLst != null && dbCommunityLst.size() > 0) {
					dbmsCommunityBean = dbCommunityLst.get(0);
				}
			}
		}
		dbmsCommunityBean.setIp(deviceIP);
		taskBean.setDbmsCommunityBean(dbmsCommunityBean);
		return taskBean;
	}

	/**
	 * 初始化信息（中间件）
	 */
	@RequestMapping("/initMiddleWareDetail")
	@ResponseBody
	public PerfTaskInfoBean initMiddleWareDetail(int taskId) {
		PerfTaskInfoBean taskBean = perfTaskInfoMapper
				.getMiddleWareTask(taskId);
		taskBean.setTaskId(taskId);
		SysMiddleWareCommunityBean middleWareCommunityBean = middleWareCommunityService
				.getMiddleWareTask(taskId);
		
		if(middleWareCommunityBean == null){
			MOMiddleWareJMXBean jmx = middlewareService
					.selectMoMidByPrimaryKey(taskBean.getMoId());
			SysMiddleWareCommunityBean middleCommunity = new SysMiddleWareCommunityBean();
			
			String[] split = jmx.getIp().split("\\.");
			String ip = split[0]+"."+split[1]+"."+split[2]+".*";
			middleCommunity.setIpAddress(ip);
			middleCommunity.setPort(jmx.getPort().toString());
			middleCommunity.setMiddleWareType(jmx.getJmxType());
			
			middleWareCommunityBean = middleWareCommunityService.getCommunityByIPAndPort(middleCommunity);
			if(middleWareCommunityBean == null){
				middleCommunity.setIpAddress("*");
				middleWareCommunityBean = middleWareCommunityService.getCommunityByIPAndPort(middleCommunity);
			}
		}
		taskBean.setMiddleWareCommunityBean(middleWareCommunityBean);
		return taskBean;
	}

	/**
	 * 更新任务
	 */
	@RequestMapping("/updateTask")
	@ResponseBody
	public Map<String, Object> updateTask(PerfTaskInfoBean bean) {
		logger.info("更新性能任务taskId=" + bean.getTaskId());
		Map<String, Object> result = new HashMap<String, Object>();
		int updateSnmpFlag = 0;
		boolean setTemplateFlag = true;
		boolean delMoFlag = true;
		boolean isNoMonitor = true;
		boolean updateFlag = false;
		boolean insertMoFlag = false;
		int j = 0;
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest();
			int templateID = Integer.parseInt(request.getParameter("templateID"));
			List<SysMoInfoBean> monitorList = new ArrayList<SysMoInfoBean>();
			
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
				//使用模板，判断该设备是否有监测器
				if(templateID != -1){
					monitorList = objectPerfConfigService.getMoList(bean.getMoId(), bean.getMoClassId());
					if(monitorList.size() == 0 || monitorList == null){
						isNoMonitor = true;
						result.put("isNoMonitor", isNoMonitor);
						return result;
					}
				}
				SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
						.getSession().getAttribute("sysUserInfoBeanOfSession");
				bean.setCreator(sysUserInfoBeanTemp.getId());
				bean.setCreateTime(dateFormat.format(new Date()));
				
				if (bean.getStatus() != 1) {
					bean.setProgressStatus(1);// 进度状态
				}
				bean.setLastOPResult(0);
				bean.setLastStatusTime(dateFormat.format(new Date()));// 最近状态时间
				bean.setOperateStatus(2);// 操作状态
				int i = perfTaskInfoMapper.updateTask2(bean);
				try {
					j = perfTaskInfoMapper.deleteMoList(bean.getTaskId());
					delMoFlag = true;
				} catch (Exception e) {
					delMoFlag = false;
				}
				//没有使用模板
				if(templateID == -1){
					if(!"".equals(bean.getMoList()) && bean.getMoList() != null){
						String[] moList = bean.getMoList().split(",");
						logger.info("被更新任务配置的监测器ID为：" + bean.getMoList());
						String[] moIntervalList = bean.getMoIntervalList().split(",");
						String[] moTimeUnitList = bean.getMoTimeUnitList().split(",");
						for (int k = 0; k < moList.length; k++) {
							int moInterval = -1;
							logger.info("更新任务：moList[" + k + "] = " + moList[k]);
							MoInfoBean moBean = new MoInfoBean();
							moBean.setMid(Integer.parseInt(moList[k]));
							moBean.setTaskId(bean.getTaskId());
							if(Integer.parseInt(moTimeUnitList[k]) == 1){
								moInterval = Integer.parseInt(moIntervalList[k]) * 60;
							}else if(Integer.parseInt(moTimeUnitList[k]) == 2){
								moInterval = Integer.parseInt(moIntervalList[k]) * 3600;
							}else if(Integer.parseInt(moTimeUnitList[k]) == 3){
								moInterval = Integer.parseInt(moIntervalList[k]) * 3600 * 24;
							}else if(Integer.parseInt(moTimeUnitList[k]) == 0){
								moInterval = Integer.parseInt(moIntervalList[k]);
							}
							moBean.setDoIntervals(moInterval);
							perfPollTaskMapper.insertTaskMo(moBean);
							insertMoFlag = true;
						}
					}
				}
				//使用模板
				else{
					String moTypeLstJson = request.getParameter("moTypeLstJson");
					List<String> moTypeList = JSON.parseArray(moTypeLstJson,String.class);
					for (int m = 0; m <moTypeList.size(); m++) {
						String[] moTypeInfoLst = moTypeList.get(m).split(",");
						int monitorTypeID = Integer.parseInt(moTypeInfoLst[0]);
						int timeInterval = Integer.parseInt(moTypeInfoLst[2]);
						int timeUnit = Integer.parseInt(moTypeInfoLst[3]);
						for (int n = 0; n < monitorList.size(); n++) {
							if(monitorList.get(n).getMonitorTypeID() == monitorTypeID){
								MoInfoBean moBean = new MoInfoBean();
								moBean.setMid(monitorList.get(n).getMid());
								moBean.setTaskId(bean.getTaskId());
								int interval = timeInterval;
								if(timeUnit == 1){
									interval = timeInterval * 60;
								}else if(timeUnit == 2){
									interval = timeInterval * 3600;
								}else if(timeUnit == 3){
									interval = timeInterval * 3600 * 24;
								}else if(timeUnit == 0){
									interval = timeInterval;
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
				
				String authType = bean.getAuthType();
				if ("SNMP".equals(authType)) {
					updateSnmpFlag = 1;
				} else if ("Vmware".equals(authType)) {
					updateSnmpFlag = perfTaskInfoMapper.updateVMIfCommunity(bean
							.getVmIfCommunityBean());
				} else if ("MiddleWare".equals(authType)) {
					boolean updateMiddleWare = middleWareCommunityService
							.updateMiddleWareCommunity(bean
									.getMiddleWareCommunityBean());
					if (updateMiddleWare == true) {
						updateSnmpFlag = 1;
					} else {
						updateSnmpFlag = 0;
					}
				} else if ("room".equals(authType)) {
					boolean updateRoom = true;
					updateRoom = roomCommunityService.updateCommunityByIP(bean
							.getRoomCommunityBean());
					if (updateRoom == true) {
						updateSnmpFlag = 1;
					} else {
						updateSnmpFlag = 0;
					}
				} else if ("site".equals(authType)) {
					updateSnmpFlag = perfTaskService.operateSiteCommunity(bean.getWebSite());
				} else if ("unKnown".equals(authType)) {
					updateSnmpFlag = 1;
				} else if ("Conditons".equals(authType)) {
					updateSnmpFlag = 1;
				} else if ("Ups".equals(authType)) {
					updateSnmpFlag = 1;
				} else {
					boolean updateDBMS = false;
					if(bean.getMoClassId() != 54){
						updateDBMS = sysDBMSCommunityService.updateDBMSCommunity(bean.getDbmsCommunityBean());
					}else{
						updateDBMS = sysDBMSCommunityService.updateDBMSCommunity2(bean.getDbmsCommunityBean());
					}
					if (updateDBMS == true) {
						updateSnmpFlag = 1;
					} else {
						updateSnmpFlag = 0;
					}
				}

				if (i >= 1 && delMoFlag == true && updateSnmpFlag >= 1) {
					logger.info("任务当前状态：" + bean.getStatus());
					if (bean.getStatus() != 1) {
						perfTaskNotify.notifyDisPatch();
						logger.info("向后台发送更新任务消息");
					}else{
						if(!"".equals(bean.getIsOffline()) && bean.getIsOffline() != null){
							//停止的任务要再运行
							PerfPollTaskBean startPerfBean = new PerfPollTaskBean();
							startPerfBean.setOperateStatus(2);
							startPerfBean.setProgressStatus(1);
							startPerfBean.setTaskId(bean.getTaskId());
							stopOrRunTask(startPerfBean);
						}
					}
					logger.info("更新成功");
					updateFlag = true;
				} else {
					updateFlag = false;
				}
			}
			
			
		} catch (Exception e) {
			logger.error("更新任务异常！" + e);
			updateFlag = false;
		}
		result.put("setTemplateFlag", setTemplateFlag);
		result.put("updateFlag", updateFlag);
		result.put("isNoMonitor", isNoMonitor);
		return result;
	}

	/**
	 * 暂停任务
	 */
	@RequestMapping("/stopOrRunTask")
	@ResponseBody
	public boolean stopOrRunTask(PerfPollTaskBean taskBean) throws Exception {
		int i = perfTaskInfoMapper.updateTaskStatus(taskBean);
		if (i >= 1) {
			perfTaskNotify.notifyDisPatch();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 批量删除任务
	 */
	@RequestMapping("/delTasks")
	@ResponseBody
	public String delTasks(HttpServletRequest request) throws Exception {
		String unDelTaskIds = "";
		String taskIds = request.getParameter("taskIds");
		String[] splitIds = taskIds.split(",");
		for (String strId : splitIds) {
			PerfPollTaskBean taskBean = perfPollTaskMapper
					.getTaskByTaskId(Integer.parseInt(strId));
			int processStatus = taskBean.getProgressStatus();
			int operateStatus = taskBean.getOperateStatus();
			if (processStatus == 5) {
				if (operateStatus != 4) {
					taskBean.setProgressStatus(1);
				}
				taskBean.setOperateStatus(3);
				int i = perfTaskInfoMapper.updateTaskStatus(taskBean);
				perfTaskInfoMapper.deleteMoList(Integer.parseInt(strId));
				if (i > 0) {
					if (operateStatus != 4) {
						logger.info("向后台发送批量删除消息");
						perfTaskNotify.notifyDisPatch();
						logger.info("向后台发送批量删除消息over");
					}
				}
			} else {
				unDelTaskIds += strId + ",";
			}
		}
		return unDelTaskIds;
	}

	/**
	 * 根据任务ID获取操作进度
	 */
	@RequestMapping("/getProcessByTaskId")
	@ResponseBody
	public Map<String, Object> getProcessByTaskId(String taskIds)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		List<String> processLst = new ArrayList<String>();
		List<String> statusLst = new ArrayList<String>();
		List<String> errorInfoLst = new ArrayList<String>();
		if (taskIds != "") {
			if (taskIds.contains(",") == true) {
				String[] taskIdLst = taskIds.split(",");
				for (int i = 0; i < taskIdLst.length; i++) {
					PerfPollTaskBean task = new PerfPollTaskBean();

					task = perfPollTaskMapper.getTaskByTaskId(Integer
							.parseInt(taskIdLst[i].split(":")[0]));
					processLst.add(task.getProgressStatus() + ":"
							+ taskIdLst[i].split(":")[1]);
					statusLst.add(task.getStatus() + ":"
							+ taskIdLst[i].split(":")[1]);
					errorInfoLst.add(task.getErrorInfo() + ":"
							+ taskIdLst[i].split(":")[1]);
				}
			} else {
				PerfPollTaskBean task = new PerfPollTaskBean();
				task = perfPollTaskMapper.getTaskByTaskId(Integer
						.parseInt(taskIds.split(":")[0]));
				processLst.add(task.getProgressStatus() + ":"
						+ taskIds.split(":")[1]);
				statusLst.add(task.getStatus() + ":" + taskIds.split(":")[1]);
				errorInfoLst.add(task.getErrorInfo() + ":"
						+ taskIds.split(":")[1]);
			}
		}
		result.put("processLst", processLst);
		result.put("statusLst", statusLst);
		result.put("errorInfoLst", errorInfoLst);
		return result;
	}

	@RequestMapping("/getAuthType")
	@ResponseBody
	public PerfTaskInfoBean getAuthType(int taskId) {
		PerfTaskInfoBean taskBean = perfTaskInfoMapper
				.getTaskInfoByTaskId(taskId);
		int templateID = objectPerfConfigService.getTemplateID(taskBean.getMoId(),taskBean.getMoClassId());
		taskBean.setTemplateID(templateID);
		return taskBean;
	}

	/**
	 * 弹出数据库服务列表
	 */
	@RequestMapping("/toDBMSServerList")
	@ResponseBody
	public ModelAndView toVolumeList(HttpServletRequest request,
			HttpServletResponse response) {
		String flag = request.getParameter("flag");
		String moClassId = request.getParameter("moClassId");
		if (flag != null && !"".equals(flag)) {
			request.setAttribute("flag", flag);
		} else {
			request.setAttribute("flag", "doInit");
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("monitor/component/modbmsserverList");
		mv.addObject("moClassId", moClassId);
		return mv;
	}

	/**
	 * 加载数据库服务列表
	 */
	@RequestMapping("/selectDBMSServerList")
	@ResponseBody
	public Map<String, Object> selectDeviceList(MODBMSServerBean dbmsServer)
			throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MODBMSServerBean> page = new Page<MODBMSServerBean>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ip", dbmsServer.getIp());
		paramMap.put("moClassId", dbmsServer.getMoClassId());
		page.setParams(paramMap);

		List<MODBMSServerBean> dbmsServerList = oracleService
				.getDBMSServerList(page);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", page.getTotalRecord());
		result.put("rows", dbmsServerList);
		return result;
	}

	/**
	 * 弹出中间件资源列表
	 */
	@RequestMapping("/toMiddleWareList")
	@ResponseBody
	public ModelAndView toMiddleWareList(HttpServletRequest request,
			HttpServletResponse response) {
		String flag = request.getParameter("flag");
		String jmxType = request.getParameter("jmxType");
		if (flag != null && !"".equals(flag)) {
			request.setAttribute("flag", flag);
		} else {
			request.setAttribute("flag", "doInit");
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("monitor/device/middleware_list");
		mv.addObject("jmxType", jmxType);
		return mv;
	}

	/**
	 * 查询数据库服务
	 */
	@RequestMapping("/findDBMSServerInfo")
	@ResponseBody
	public MODBMSServerBean findDBMSServerInfo(MODBMSServerBean bean)
			throws Exception {
		MODBMSServerBean dbmsServer = new MODBMSServerBean();
	
		if(bean.getMoClassId() != 54){
			dbmsServer = oracleService.getDBMSServerInfo(bean.getMoid());
			if (dbmsServer.getDbmstype() == null) {
				SysDBMSCommunityBean dbBean = new SysDBMSCommunityBean();
				MODBMSServerBean server = oracleService
				.selectMoDbmsByPrimaryKey(bean.getMoid());
				
				String[] split = server.getIp().split("\\.");
				String ip = split[0]+"."+split[1]+"."+split[2]+".*";
				dbBean.setIp(ip);
				dbBean.setDbmsType(server.getDbmstype());
				dbBean.setPort(server.getPort());
				
				List<SysDBMSCommunityBean> dbCommunityLst = new ArrayList<SysDBMSCommunityBean>();
				dbCommunityLst = sysDBMSCommunityService.getByIPAndPort(dbBean);
				
				if (dbCommunityLst != null && dbCommunityLst.size() > 0) {
					dbmsServer.setPort(dbCommunityLst.get(0).getPort());
					dbmsServer.setDbname(dbCommunityLst.get(0).getDbName());
					dbmsServer.setDbmstype(dbCommunityLst.get(0).getDbmsType());
					dbmsServer.setUsername(dbCommunityLst.get(0).getUserName());
					dbmsServer.setPassword(dbCommunityLst.get(0).getPassword());
				}else{
					dbBean.setIp("*");
					dbCommunityLst = sysDBMSCommunityService.getByIPAndPort(dbBean);
					if(dbCommunityLst != null && dbCommunityLst.size() > 0) {
						dbmsServer.setPort(dbCommunityLst.get(0).getPort());
						dbmsServer.setDbname(dbCommunityLst.get(0).getDbName());
						dbmsServer.setDbmstype(dbCommunityLst.get(0).getDbmsType());
						dbmsServer.setUsername(dbCommunityLst.get(0).getUserName());
						dbmsServer.setPassword(dbCommunityLst.get(0).getPassword());
					}
				}
			}
		}
		int templateID = perfTaskService.getTemplateID(bean.getMoid(), bean.getMoClassId());
		dbmsServer.setTemplateID(templateID);
		return dbmsServer;
	}

	/**
	 * 查询虚拟主机服务
	 */
	@RequestMapping("/findVmwareDeviceInfo")
	@ResponseBody
	public PerfTaskInfoBean findVmwareDeviceInfo(PerfTaskInfoBean bean)
			throws Exception {
		PerfTaskInfoBean taskbean = perfTaskInfoMapper.getVmwareDeviceInfo(bean
				.getMoId());
		int templateID = perfTaskService.getTemplateID(taskbean.getMoId(), taskbean.getMoClassId());
		taskbean.setTemplateID(templateID);
		if (taskbean.getVmId() == null || taskbean.getVmId() <= 0) {
			String[] split = taskbean.getDeviceIp().split("\\.");
			String ip = split[0]+"."+split[1]+"."+split[2]+".*";
			SysVMIfCommunityBean vmCommunity = new SysVMIfCommunityBean();
			vmCommunity.setDeviceIP(ip);
			SysVMIfCommunityBean vmIf = new SysVMIfCommunityBean();
			vmIf = sysVMIfCommunityService.getObjFromDeviceIP(vmCommunity);
			if(vmIf != null){
				taskbean.setPort(vmIf.getPort());
				taskbean.setUserName(vmIf.getUserName());
				taskbean.setPassword(vmIf.getPassword());
			}else{
				vmCommunity.setDeviceIP("*");
				vmIf = sysVMIfCommunityService.getObjFromDeviceIP(vmCommunity);
				if(vmIf != null){
					taskbean.setPort(vmIf.getPort());
					taskbean.setUserName(vmIf.getUserName());
					taskbean.setPassword(vmIf.getPassword());
				}
			}
		}
		return taskbean;
	}

	/**
	 * 查询中间件信息
	 */
	@RequestMapping("/findMiddleWareInfo")
	@ResponseBody
	public MOMiddleWareJMXBean findMiddleWareInfo(MOMiddleWareJMXBean bean,HttpServletRequest request)
			throws Exception {
		MOMiddleWareJMXBean middleWareJMXBean = middlewareService
				.getMiddleWareInfo(bean.getMoId());
		if (middleWareJMXBean.getId() == null) {
			MOMiddleWareJMXBean jmx = middlewareService
					.selectMoMidByPrimaryKey(bean.getMoId());
			SysMiddleWareCommunityBean middleCommunity = new SysMiddleWareCommunityBean();
			
			SysMiddleWareCommunityBean jmxCommunity = new SysMiddleWareCommunityBean();
			String[] split = jmx.getIp().split("\\.");
			String ip = split[0]+"."+split[1]+"."+split[2]+".*";
			middleCommunity.setIpAddress(ip);
			middleCommunity.setPort(jmx.getPort().toString());
			middleCommunity.setMiddleWareType(jmx.getJmxType());
			
			jmxCommunity = middleWareCommunityService.getCommunityByIPAndPort(middleCommunity);
			if(jmxCommunity == null){
				middleCommunity.setIpAddress("*");
				jmxCommunity = middleWareCommunityService.getCommunityByIPAndPort(middleCommunity);
			}
			if(jmxCommunity != null){
				middleWareJMXBean.setPort(Integer.parseInt(jmxCommunity.getPort()));
				middleWareJMXBean.setUserName(jmxCommunity.getUserName());
				middleWareJMXBean.setPassWord(jmxCommunity.getPassWord());
				middleWareJMXBean.setDomainId(jmxCommunity.getDomainID());
				middleWareJMXBean.setUrl(jmxCommunity.getUrl());
			}
		}
		if(!"".equals(request.getParameter("moClassId")) && request.getParameter("moClassId") != null){
			int moClassId = Integer.parseInt(request.getParameter("moClassId"));
			int templateID = perfTaskService.getTemplateID(bean.getMoId(), moClassId);
			middleWareJMXBean.setTemplateID(templateID);
		}
		return middleWareJMXBean;
	}

	/**
	 * 重发任务
	 */
	@RequestMapping("/resendTask")
	@ResponseBody
	public boolean resendTask(PerfPollTaskBean taskBean) throws Exception {
		PerfPollTaskBean task = perfPollTaskMapper.getTaskByTaskId(taskBean.getTaskId());
		int processStatus = task.getProgressStatus();
		int operateStatus = task.getOperateStatus();
		if(processStatus >= FIRST_PROGRESS && processStatus < FINISH_PROGRESS){
			//如果操作状态为暂停，重发时操作状态改为修改
			if(operateStatus == STOP_OPERATESTATUS){
				taskBean.setOperateStatus(UPDATE_OPERATESTATUS);
			}
		}
		try {
			perfTaskInfoMapper.updateTaskProgressStatus(taskBean);
			perfTaskNotify.notifyDisPatch();
			return true;
		} catch (Exception e) {
			logger.error("任务重发失败：" + e);
			return false;
		}
	}

	/**
	 * 根据任务ID获取采集机
	 */
	@RequestMapping("/getServerNameByTaskId")
	@ResponseBody
	public List<String> getServerNameByTaskId(String taskIds) throws Exception {
		List<String> serverNameLst = new ArrayList<String>();
		if (taskIds != "") {
			if (taskIds.contains(",") == true) {
				String[] taskIdLst = taskIds.split(",");
				for (int i = 0; i < taskIdLst.length; i++) {
					String serverName = perfPollTaskMapper
							.getServerNameByTaskId(Integer
									.parseInt(taskIdLst[i].split(":")[0]));
					serverNameLst.add(serverName + ":"
							+ taskIdLst[i].split(":")[1]);
				}
			} else {
				String serverName = perfPollTaskMapper
						.getServerNameByTaskId(Integer.parseInt(taskIds
								.split(":")[0]));
				serverNameLst.add(serverName + ":" + taskIds.split(":")[1]);
			}
		}
		return serverNameLst;
	}

	/**
	 * 打开查看页面
	 */
	@RequestMapping("/toShowPerfTaskDetail")
	@ResponseBody
	public ModelAndView toShowPerfTaskDetail(HttpServletRequest request) {
		logger.info("打开查看页面");
		String taskId = request.getParameter("taskId");
		request.setAttribute("taskId", taskId);
		return new ModelAndView("monitor/perf/perfTask_detail");
	}

	/**
	 * 弹出管理对象ZoneManage列表
	 */
	@RequestMapping("/toZoneManageList")
	@ResponseBody
	public ModelAndView toZoneManageList(HttpServletRequest request,
			HttpServletResponse response) {
		String flag = request.getParameter("flag");
		String moClassId = request.getParameter("moClassId");
		if (flag != null && !"".equals(flag)) {
			request.setAttribute("flag", flag);
		} else {
			request.setAttribute("flag", "doInit");
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("monitor/component/moZoneManagerList");
		mv.addObject("moClassId", moClassId);
		return mv;
	}

	/**
	 * 加载管理对象ZoneManage列表
	 */
	@RequestMapping("/selectZoneManagerList")
	@ResponseBody
	public Map<String, Object> selectZoneManagerList(
			MOZoneManagerBean zoneManagerBean) throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MOZoneManagerBean> page = new Page<MOZoneManagerBean>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ip", zoneManagerBean.getIpAddress());
		paramMap.put("moClassId", request.getParameter("moClassId"));
		page.setParams(paramMap);

		List<MOZoneManagerBean> zoneManagerList = zoneManagerService
				.getZoneManagerList(page);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", page.getTotalRecord());
		result.put("rows", zoneManagerList);
		return result;
	}

	/**
	 * 查询ZoneManage服务
	 */
	@RequestMapping("/findZoneManagerInfo")
	@ResponseBody
	public MOZoneManagerBean findZoneManagerInfo(MOZoneManagerBean bean)
			throws Exception {
		MOZoneManagerBean zoneManagerBean = zoneManagerService
				.getZoneManagerInfo(bean.getMoID());
		if (zoneManagerBean.getId() == null) {
			SysRoomCommunityBean room = new SysRoomCommunityBean();
			SysRoomCommunityBean roomCommunity = new SysRoomCommunityBean();
			String[] split = zoneManagerBean.getIpAddress().split("\\.");
			String ip = split[0]+"."+split[1]+"."+split[2]+".*";
			room.setIpAddress(ip);
			room.setPort(zoneManagerBean.getPort());
			roomCommunity = roomCommunityService.getRoomCommunityByIPAndPort(room);
			if(roomCommunity == null){
				room.setIpAddress("*");
				roomCommunity = roomCommunityService.getRoomCommunityByIPAndPort(room);
			}
			if (roomCommunity != null) {
				zoneManagerBean.setUserName(roomCommunity.getUserName());
				zoneManagerBean.setPassWord(roomCommunity.getPassWord());
			}
		}
		int templateID = perfTaskService.getTemplateID(bean.getMoID(), bean.getMoClassId());
		zoneManagerBean.setTemplateID(templateID);
		return zoneManagerBean;
	}

	/**
	 * 初始化信息（机房监控凭证）
	 */
	@RequestMapping("/initRoomDetail")
	@ResponseBody
	public PerfTaskInfoBean initRoomDetail(int taskId) {
		PerfTaskInfoBean taskBean = perfTaskInfoMapper.getRoomTask(taskId);
		SysRoomCommunityBean roomCommunityBean = roomCommunityService
				.getRoomByTask(taskId);
		if(roomCommunityBean == null){
			SysRoomCommunityBean room = new SysRoomCommunityBean();
			String[] split = taskBean.getDeviceIp().split("\\.");
			String ip = split[0]+"."+split[1]+"."+split[2]+".*";
			room.setIpAddress(ip);
			room.setPort(taskBean.getPort());
			roomCommunityBean = roomCommunityService.getRoomCommunityByIPAndPort(room);
			if(roomCommunityBean == null){
				room.setIpAddress("*");
				roomCommunityBean = roomCommunityService.getRoomCommunityByIPAndPort(room);
			}
		}
		taskBean.setRoomCommunityBean(roomCommunityBean);
		return taskBean;
	}

	/**
	 * 查看任务监测器信息
	 */
	@RequestMapping("/listInitMoList")
	@ResponseBody
	public List<String> listInitMoList(PerfTaskInfoBean bean) throws Exception {
		logger.info("获取设备ID = " + bean.getMoId() + " 的监测器信息");
		List<String> moList = new ArrayList<String>();
		moList = perfPollTaskMapper.getMoList(bean.getMoId());

		// 如果根据厂商和型号获取不到监测器，就只根据厂商获取
		if (moList.size() <= 0 && bean.getMoId() != 0) {
			moList = perfPollTaskMapper.getMoByManufacturer(bean.getMoId());
		}

		return perfTaskService.getMOList(moList, bean.getTaskId());
	}

	/**
	 * 根据监测对象获取监测器信息
	 */
	@RequestMapping("/listInitDBMoList")
	@ResponseBody
	public List<String> listInitDBMoList(PerfTaskInfoBean bean)
			throws Exception {
		List<String> moList = new ArrayList<String>();
		moList = perfTaskService.getByMOClassID(bean.getMoClassId());
		return perfTaskService.getMOList(moList, bean.getTaskId());
	}
	
	
	/**
	 * 批量重发
	 */
	@RequestMapping("/doBatchResend")
	@ResponseBody
	public String doBatchResend(HttpServletRequest request) throws Exception {
		String taskIds = request.getParameter("taskIds");
		String[] splitIds = taskIds.split(",");
		String unDelTaskIds = "";
		boolean isNotify = false;
		for (String strId : splitIds) {
			PerfPollTaskBean task = perfPollTaskMapper.getTaskByTaskId(Integer.parseInt(strId));
			int processStatus = task.getProgressStatus();
			int operateStatus = task.getOperateStatus();
			if(processStatus >= FIRST_PROGRESS && processStatus < FINISH_PROGRESS){
				PerfPollTaskBean taskBean = new PerfPollTaskBean();
				taskBean.setProgressStatus(FIRST_PROGRESS);
				taskBean.setTaskId(Integer.parseInt(strId));
				//如果操作状态为暂停，重发时操作状态改为修改
				if(operateStatus == STOP_OPERATESTATUS){
					taskBean.setOperateStatus(UPDATE_OPERATESTATUS);
				}
				int i = perfTaskInfoMapper.updateTaskProgressStatus(taskBean);
				if (i > 0) {
					isNotify = true;
				}
			}else{
				unDelTaskIds += strId + ",";
			}
		}
		if(isNotify == true){
			perfTaskNotify.notifyDisPatch();
		}
		return unDelTaskIds;
	}
	
	/**
	 * 采集机树
	 */
	@RequestMapping("/findHostLst")
	@ResponseBody
	public Map<String, Object> findHostLst() {
		return perfTaskService.findHostTree();
	}
	
	/**
	 * 初始化DB2信息
	 */
	@RequestMapping("/initDB2Val")
	@ResponseBody
	public PerfTaskInfoBean initDB2Val(int taskId) {
		PerfTaskInfoBean taskBean = perfTaskInfoMapper
				.getByTaskIdAndMOID(taskId);
		taskBean.setTaskId(taskId);
		String deviceIP = taskBean.getDeviceIp();
		SysDBMSCommunityBean dbmsCommunityBean = dbmsCommunityMapper
				.getDBMSByTaskIdAndDBName(taskId);
		if(dbmsCommunityBean == null){
			SysDBMSCommunityBean dbBean = new SysDBMSCommunityBean();
			MODBMSServerBean server = oracleService
					.selectMoDbmsByPrimaryKey(taskBean.getMoId());
			String[] split = taskBean.getDeviceIp().split("\\.");
			String ip = split[0]+"."+split[1]+"."+split[2]+".*";
			
			dbBean.setIp(ip);
			dbBean.setDbmsType(server.getDbmstype());
			dbBean.setPort(server.getPort());
			dbBean.setDbName(taskBean.getDbName());
			List<SysDBMSCommunityBean> dbCommunityLst = new ArrayList<SysDBMSCommunityBean>();
			dbCommunityLst = sysDBMSCommunityService.getByIPAndTypeAndPortAndName(dbBean);
			
			if (dbCommunityLst != null && dbCommunityLst.size() > 0) {
				dbmsCommunityBean = dbCommunityLst.get(0);
			}else{
				dbBean.setIp("*");
				dbCommunityLst = sysDBMSCommunityService.getByIPAndPort(dbBean);
				if(dbCommunityLst != null && dbCommunityLst.size() > 0) {
					dbmsCommunityBean = dbCommunityLst.get(0);
				}
			}
		}
		dbmsCommunityBean.setIp(deviceIP);
		taskBean.setDbmsCommunityBean(dbmsCommunityBean);
		return taskBean;
	}
	
	/**
	 * 获得所有的模板
	 */
	@RequestMapping("/getAllTemplate")
	@ResponseBody
	public List<SysMonitorsTemplateBean> getAllTemplate(HttpServletRequest request){
		return perfTaskService.getAllTemplate(Integer.parseInt(request.getParameter("moClassId")));
	}
	
	/**
	 * 批量套用模板创建采集任务
	 */
	@RequestMapping("/doSetPerfTask")
	@ResponseBody
	public boolean doSetPerfTask(PerfTaskInfoBean bean,HttpServletRequest request){
		int templateID = Integer.parseInt(request.getParameter("templateID"));
		String moIDs = request.getParameter("moIDs");
		String moTypeLstJson = request.getParameter("moTypeLstJson");
		return perfTaskService.doSetPerfTask(bean, moIDs, templateID,moTypeLstJson);
		
	}
	
	
	/**
	 * 批量套用模板创建采集任务(DB2)
	 */
	@RequestMapping("/doSetDb2PerfTask")
	@ResponseBody
	public boolean doSetDb2PerfTask(PerfTaskInfoBean bean,HttpServletRequest request){
		int templateID = Integer.parseInt(request.getParameter("templateID"));
		String moIDs = request.getParameter("moIDs");
		String moTypeLstJson = request.getParameter("moTypeLstJson");
		return perfTaskService.doSetDb2PerfTask(bean, moIDs, templateID,moTypeLstJson);
		
	}
	
	/**
	 * 打开指定采集机页面
	 */
	@RequestMapping("/toAppointCollector")
	@ResponseBody
	public ModelAndView toAppointCollector(HttpServletRequest request) {
		logger.info("打开指定采集机页面");
		String taskId = request.getParameter("taskId");
		String serverId = request.getParameter("serverId");
		String serverName = request.getParameter("serverName");
		String deviceIp = request.getParameter("deviceIp");
		String moClassId = request.getParameter("moClassId");
		request.setAttribute("taskId", taskId);
		request.setAttribute("serverId", serverId);
		request.setAttribute("serverName", serverName);
		request.setAttribute("deviceIp", deviceIp);
		request.setAttribute("moClassId", moClassId);
		return new ModelAndView("monitor/perf/appointCollector");
	}
	
	/**
	 * 指定采集机
	 */
	@RequestMapping("/appointCollector")
	@ResponseBody
	public boolean appointCollector(PerfTaskInfoBean taskBean) throws Exception {
		try {
			logger.info("新采集机ID："+taskBean.getCollectorId()+",原来的采集机ID："+taskBean.getOldCollectorId());
			perfTaskInfoMapper.updateTask3(taskBean);

			perfTaskNotify.notifyDisPatch();
			return true;
		} catch (Exception e) {
			logger.error("指定采集机失败：" + e);
		}
		return false;
	}
	
	@RequestMapping("/isInScope")
	@ResponseBody
	public boolean isInScope(PerfTaskInfoBean taskBean){
		String deviceIp = taskBean.getDeviceIp();
		int collectorId = taskBean.getCollectorId();
		return perfTaskService.isInScope(deviceIp,collectorId);
	}
	
	/**
	 * 查询dns
	 */
	@RequestMapping("/findSiteDnsInfo")
	@ResponseBody
	public SiteDns findSiteDnsInfo(int moID) throws Exception {
		SiteDns dns = perfTaskService.findSiteDnsInfo(moID);
		int templateID = perfTaskService.getTemplateID(moID, 91);
		dns.setTemplateID(templateID);
		return dns;
	}
	
	/**
	 * 查询ftp
	 */
	@RequestMapping("/findSiteFtpnfo")
	@ResponseBody
	public SiteFtp findSiteFtpnfo(int moID){
		SiteFtp ftp = perfTaskService.findSiteFtpnfo(moID);
		int templateID = perfTaskService.getTemplateID(moID, 92);
		ftp.setTemplateID(templateID);
		return ftp;
	}
	
	/**
	 * 查询http
	 */
	@RequestMapping("/findSiteHttpnfo")
	@ResponseBody
	public SiteHttp findSiteHttpnfo(int moID){
		SiteHttp http = perfTaskService.findSiteHttpnfo(moID);
		int templateID = perfTaskService.getTemplateID(moID, 93);
		http.setTemplateID(templateID);
		return http;
	}
	
	/**
	 * 查询tcp
	 */
	@RequestMapping("/findSitePortInfo")
	@ResponseBody
	public SitePort findSitePortInfo(int moID) throws Exception {
		SitePort sitePort = perfTaskService.findSitePortInfo(moID);
		int templateID = perfTaskService.getTemplateID(moID, 94);
		sitePort.setTemplateID(templateID);
		return sitePort;
	}
	
	/**
	 * 初始化dns信息
	 */
	@RequestMapping("/initDnsDetail")
	@ResponseBody
	public PerfTaskInfoBean initDnsDetail(int taskId) {
		PerfTaskInfoBean taskBean = perfTaskService.getDNSTask(taskId);
		return taskBean;
	}
	
	/**
	 * 初始化ftp信息
	 */
	@RequestMapping("/initFtpDetail")
	@ResponseBody
	public PerfTaskInfoBean initFtpDetail(int taskId) {
		PerfTaskInfoBean taskBean = perfTaskService.getFtpTask(taskId);
		return taskBean;
	}
	
	/**
	 * 初始化http信息
	 */
	@RequestMapping("/initHttpDetail")
	@ResponseBody
	public PerfTaskInfoBean initHttpDetail(int taskId) {
		PerfTaskInfoBean taskBean = perfTaskService.getHttpTask(taskId);
		return taskBean;
	}
	
	/**
	 * 初始化tcp信息
	 */
	@RequestMapping("/initTcpDetail")
	@ResponseBody
	public PerfTaskInfoBean initTcpDetail(int taskId) {
		PerfTaskInfoBean taskBean = perfTaskService.getTcpTask(taskId);
		return taskBean;
	}
	
	/**
	 * 批量暂停
	 */
	@RequestMapping("/doBatchStop")
	@ResponseBody
	public Map<String, Object> doBatchStop(HttpServletRequest request){
		String taskIds = request.getParameter("taskIds");
		return perfTaskService.doBatchStop(taskIds);
	}
	
	/**
	 * 初始化对象类型树
	 * 
	 */
	@RequestMapping("/initTree")
	@ResponseBody
	public Map<String, Object> initTree() {
		return perfTaskService.initTree();
	}
	
	/**
	 * 根据设备ID查找未知设备信息
	 */
	@RequestMapping("/findUnknownDeviceInfo")
	@ResponseBody
	public PerfTaskInfoBean findUnknownDeviceInfo(PerfTaskInfoBean bean)
			throws Exception {
		PerfTaskInfoBean taskBean = perfTaskInfoMapper.getDeviceById2(bean
				.getMoId());
		int templateID = perfTaskService.getTemplateID(taskBean.getMoId(), taskBean.getMoClassId());
		taskBean.setTemplateID(templateID);
		return taskBean;
	}
	
	/**
	 * 初始化信息（UnknownDevice）
	 */
	@RequestMapping("/initUnknownDeviceVal")
	@ResponseBody
	public PerfTaskInfoBean initUnknownDeviceVal(int taskId) {
		PerfTaskInfoBean taskBean = perfTaskInfoMapper
				.getPerTaskInfoByTaskId(taskId);
		taskBean.setTaskId(taskId);
		return taskBean;
	}
	
	/**
	 * 批量恢复
	 * @param request
	 * @return
	 */
	@RequestMapping("/doBatchStart")
	@ResponseBody
	public Map<String, Object> doBatchStart(HttpServletRequest request){
		String taskIds = request.getParameter("taskIds");
		return perfTaskService.doBatchStart(taskIds);
	}
}
