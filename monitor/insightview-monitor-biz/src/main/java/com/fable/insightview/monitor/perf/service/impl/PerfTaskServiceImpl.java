package com.fable.insightview.monitor.perf.service.impl;

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
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.fable.insightview.monitor.database.entity.Db2InfoBean;
import com.fable.insightview.monitor.database.mapper.Db2Mapper;
import com.fable.insightview.monitor.discover.entity.MODeviceObj;
import com.fable.insightview.monitor.discover.mapper.MODeviceMapper;
import com.fable.insightview.monitor.dispatcher.Dispatcher;
import com.fable.insightview.monitor.dispatcher.constants.TaskType;
import com.fable.insightview.monitor.dispatcher.facade.ManageFacade;
import com.fable.insightview.monitor.dispatcher.notify.TaskDispatchNotification;
import com.fable.insightview.monitor.dispatcher.server.Server;
import com.fable.insightview.monitor.dispatcher.server.TaskDispatcherServer;
import com.fable.insightview.monitor.harvester.entity.SysServerHostInfo;
import com.fable.insightview.monitor.harvester.mapper.HarvesterMapper;
import com.fable.insightview.monitor.manageddomainipscope.entity.ManagedDomainIPScopeBean;
import com.fable.insightview.monitor.manageddomainipscope.mapper.ManagedDomainIPScopeMapper;
import com.fable.insightview.monitor.mobject.entity.MObjectDefBean;
import com.fable.insightview.monitor.mobject.mapper.MobjectInfoMapper;
import com.fable.insightview.monitor.molist.entity.SysMoInfoBean;
import com.fable.insightview.monitor.molist.entity.SysMonitorsTemplateBean;
import com.fable.insightview.monitor.molist.entity.SysMonitorsTemplateUsedBean;
import com.fable.insightview.monitor.molist.mapper.SysMoInfoMapper;
import com.fable.insightview.monitor.perf.entity.MoInfoBean;
import com.fable.insightview.monitor.perf.entity.PerfPollTaskBean;
import com.fable.insightview.monitor.perf.entity.PerfTaskInfoBean;
import com.fable.insightview.monitor.perf.mapper.PerfPollTaskMapper;
import com.fable.insightview.monitor.perf.mapper.PerfTaskInfoMapper;
import com.fable.insightview.monitor.perf.mapper.SysMonitorsOfMOClassMapper;
import com.fable.insightview.monitor.perf.service.IObjectPerfConfigService;
import com.fable.insightview.monitor.perf.service.IPerfGeneralConfigService;
import com.fable.insightview.monitor.perf.service.IPerfTaskService;
import com.fable.insightview.monitor.website.entity.SiteDns;
import com.fable.insightview.monitor.website.entity.SiteFtp;
import com.fable.insightview.monitor.website.entity.SiteHttp;
import com.fable.insightview.monitor.website.entity.SitePort;
import com.fable.insightview.monitor.website.entity.SysSiteCommunityBean;
import com.fable.insightview.monitor.website.entity.WebSite;
import com.fable.insightview.monitor.website.mapper.SysSiteCommunityMapper;
import com.fable.insightview.monitor.website.mapper.WebSiteMapper;
import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.ipmanager.util.IPPoolUtil;
import com.fable.insightview.platform.snmpcommunity.entity.SNMPCommunityBean;
import com.fable.insightview.platform.snmpcommunity.entity.SysVMIfCommunityBean;
import com.fable.insightview.platform.snmpcommunity.service.ISnmpCommunityService;
import com.fable.insightview.platform.snmpcommunity.service.ISysVMIfCommunityService;

@Service
public class PerfTaskServiceImpl implements IPerfTaskService {

	@Autowired
	PerfPollTaskMapper perfPollTaskMapper;
	@Autowired
	PerfTaskInfoMapper perfTaskInfoMapper;
	@Autowired
	MODeviceMapper moDeviceMapper;
	@Autowired
	SysMonitorsOfMOClassMapper monitorsOfMOClassMapper;
	@Autowired
	ISnmpCommunityService snmpCommunityService;
	@Autowired
	ISysVMIfCommunityService vmIfCommunityService;
	@Autowired
	MobjectInfoMapper mobjectInfoMapper;
	@Autowired
	IPerfGeneralConfigService configService;
	@Autowired
	HarvesterMapper harvesterMapper;
	@Autowired
	SysMoInfoMapper sysMoInfoMapper;
	@Autowired
	IObjectPerfConfigService objectPerfConfigService;
	@Autowired
	Db2Mapper db2Mapper;
	@Autowired
	ManagedDomainIPScopeMapper domainIPScopeMapper;
	@Autowired WebSiteMapper webSiteMapper;
	@Autowired SysSiteCommunityMapper siteCommunityMapper;
	
	
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final Logger logger = LoggerFactory
			.getLogger(PerfTaskServiceImpl.class);
	private static final int FINISH_PROCESS = 5;
	private static final int STOP_OPERATESTATUS = 4;
	private static final int START_OPERATESTATUS = 5;
	private static final int FIRST_PROCESS = 1;
	private static final int STOP_STATUS = 1;
	private static final int START_STATUS = 0;

	/**
	 *给设备添加默认任务
	 */
	@Override
	public boolean addPerfTasks(List<Integer> moidList) {
		PerfTaskInfoBean perfTask = new PerfTaskInfoBean();
		int exsitTaskId = 0;
		int addTaskFlag = 0;
		int updateTaskFlag = 0;
		int deleteMoFlag = 0;
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
				.getSession().getAttribute("sysUserInfoBeanOfSession");
		List<Integer> vmMoIds = new ArrayList<Integer>();
		List<Integer> moidListNew = new ArrayList<Integer>();
		try {
			logger.info("设备发现moList大小 = " + moidList.size());
			for (int i = 0; i < moidList.size(); i++) {
				int moId = moidList.get(i);
				if (moId != 0) {
					MODeviceObj moDevice = moDeviceMapper
							.selectByPrimaryKey(moId);
					if (moDevice != null) {
						logger.info("moDevice.getMoClassId() == "
								+ moDevice.getMoClassId());
						if (moDevice.getMoClassId() == 8) {
							List<Integer> lst = moDeviceMapper.getVmMoIds(moId);
							if (lst != null) {
								for (int j = 0; j < lst.size(); j++) {
									vmMoIds.add(lst.get(j));
								}
							}

						}

						if (moDevice.getMoClassId() == 9) {
							vmMoIds.add(moId);
						} else {
							moidListNew.add(moId);
						}
					}
				}
			}
			if (vmMoIds.size() > 0) {
				for (int i = 0; i < vmMoIds.size(); i++) {
					int moId = vmMoIds.get(i);
					int isExsit = perfTaskInfoMapper.isExsitTask(moId);
					if (isExsit > 0) {
						exsitTaskId = perfTaskInfoMapper.exsitTaskId(moId);
						PerfPollTaskBean bean = new PerfPollTaskBean();
						bean.setTaskId(exsitTaskId);
						bean.setOperateStatus(3);
						bean.setProgressStatus(1);
						int deleteTask = perfTaskInfoMapper
								.updateTaskStatus(bean);
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
					facade.sendNotification(servers.get(0).getIp(), topic,
							message);
				}
			}
			logger.info("将虚拟机过滤后的moList大小 = " + moidListNew.size());
			for (int i = 0; i < moidListNew.size(); i++) {
				int moId = moidListNew.get(i);
				logger.info(">>>>>moId == " + moId);
				MODeviceObj moDevice = moDeviceMapper.selectByPrimaryKey(moId);

				int moClassId = moDevice.getMoClassId();
				String deviceip = moDevice.getDeviceip();

				// 判断是否有凭证
				boolean isExistCommunity = false;
				if (moClassId == 8 || moClassId == 9) {
					SysVMIfCommunityBean vmIfBean = new SysVMIfCommunityBean();
					vmIfBean.setDeviceIP(deviceip);
					SysVMIfCommunityBean vmIfCommunity = vmIfCommunityService
							.getObjFromDeviceIP(vmIfBean);
					if (vmIfCommunity != null) {
						isExistCommunity = true;
					} else {
						String[] split = deviceip.split("\\.");
						String ip = split[0]+"."+split[1]+"."+split[2]+".*";
						vmIfBean.setDeviceIP(ip);
						SysVMIfCommunityBean vmIfCommunity2 = new SysVMIfCommunityBean();
						vmIfCommunity2 = vmIfCommunityService.getObjFromDeviceIP(vmIfBean);
						if (vmIfCommunity2 == null) {
							vmIfBean.setDeviceIP("*");
							vmIfCommunity2 = vmIfCommunityService.getObjFromDeviceIP(vmIfBean);
						}
						if(vmIfCommunity2 != null){
							isExistCommunity = true;
						} else {
							isExistCommunity = false;
						}
					}
				} else {
					isExistCommunity = true;
				}

				if (isExistCommunity == true) {
					List<String> moList = perfPollTaskMapper.getMoList(moId);

					// 如果根据厂商和设备类型没有获得监测器，就直接根据厂商获取
					if (moList == null || moList.size() == 0) {
						moList = perfPollTaskMapper.getMoByManufacturer(moId);
					}
					logger.info(">>>>>>>>>监测器个数 == " + moList.size());
					if (moList.size() > 0) {
						int isExsit = perfTaskInfoMapper.isExsitTask(moId);
						logger.info(">>>>>>>>>任务是否已经存在 == " + isExsit);
						if (isExsit > 0) {
							logger.info(">>>>>>>>>>进入存在操作>>>>>>>>");
							PerfTaskInfoBean perfTaskTemp = perfTaskInfoMapper
									.getTaskInfoByMoId2(moId);
							logger.info("判断是否需要更新");
							logger
									.info(">>>>>>>>perfTaskTemp.getMoClassId() == "
											+ perfTaskTemp.getMoClassId());
							logger.info(">>>>>>>>moDevice.getMoClassId() == "
									+ moDevice.getMoClassId());

							// 如果是虚拟机的话不建采集任务，虚拟宿主机才创建采集任务
							if (perfTaskTemp.getMoClassId() < moDevice
									.getMoClassId()) {
								logger.info(">>>>>>>>进入更新任务>>>>>>>>>>>>");
								exsitTaskId = perfTaskInfoMapper
										.exsitTaskId(moId);
								perfTask.setTaskId(exsitTaskId);
								perfTask.setMoId(moId);
								perfTask.setStatus(1);
								perfTask.setOperateStatus(2);// 操作状态
								perfTask.setProgressStatus(1);// 进度状态
								perfTask.setLastOPResult(0);
								perfTask
										.setCreator(sysUserInfoBeanTemp.getId());
								perfTask.setCreateTime(dateFormat
										.format(new Date()));
								perfTask.setMoClassId(moDevice.getMoClassId());
								updateTaskFlag = perfTaskInfoMapper
										.updateTask(perfTask);
								deleteMoFlag = perfTaskInfoMapper
										.deleteMoList(exsitTaskId);
								MoInfoBean moBean = new MoInfoBean();
								for (int j = 0; j < moList.size(); j++) {
									moBean.setTaskId(perfTask.getTaskId());
									moBean.setMid(Integer.parseInt(moList
											.get(j).split(",")[0]));

									int moInterval = 5;
									String className = mobjectInfoMapper
											.getMobjectById(
													moDevice.getMoClassId())
											.getClassName();
									int initPeriodVal = configService
											.initCollectPeriodVal(className);
									if (initPeriodVal != -1) {
										moInterval = initPeriodVal;
									}
									moBean.setDoIntervals(moInterval * 60);
									int addMoFlag = perfPollTaskMapper
											.insertTaskMo(moBean);
								}

							} else {
								logger.info("ID = " + moId + " 的设备已分发任务！");
								continue;
							}
						} else {
							logger.info(">>>>>>>>>>进入任务不存在操作>>>>>>>>");
							perfTask.setMoId(moId);
							perfTask.setStatus(1);
							perfTask.setOperateStatus(1);// 操作状态
							perfTask.setProgressStatus(1);// 进度状态
							perfTask.setLastOPResult(0);
							perfTask.setCollectorId(-1);
							perfTask.setOldCollectorId(-1);
							perfTask.setCreator(sysUserInfoBeanTemp.getId());
							perfTask.setCreateTime(dateFormat
									.format(new Date()));
							perfTask.setMoClassId(moDevice.getMoClassId());
							addTaskFlag = perfPollTaskMapper
									.insertTaskInfo(perfTask);
							logger.info(">>>>>>>>新增是否成功 == " + addTaskFlag);
							MoInfoBean moBean = new MoInfoBean();
							for (int j = 0; j < moList.size(); j++) {
								moBean.setTaskId(perfTask.getTaskId());
								moBean.setMid(Integer.parseInt(moList.get(j)
										.split(",")[0]));
								int moInterval = -1;
								SysMoInfoBean sysMo = sysMoInfoMapper.getMoInfoByMid(Integer.parseInt(moList
										.get(j).split(",")[0]));
								if(sysMo != null){
									if(sysMo.getDoIntervals() != null && sysMo.getTimeUnit() != null){
										if(sysMo.getTimeUnit() == 1){
											moInterval = sysMo.getDoIntervals() * 60;
										}else if(sysMo.getTimeUnit() == 2){
											moInterval = sysMo.getDoIntervals() * 60 * 60;
										}else if(sysMo.getTimeUnit() == 3){
											moInterval = sysMo.getDoIntervals() * 60 * 60 * 24;
										}
									}
								}
								if(moInterval == -1){
									String className = mobjectInfoMapper
									.getMobjectById(moDevice.getMoClassId())
									.getClassName();
									int initPeriodVal = configService
									.initCollectPeriodVal(className);
									if (initPeriodVal != -1) {
										moInterval = initPeriodVal * 60;
									}
								}
								if(moInterval == -1){
									moInterval = 1200;
								}
								moBean.setDoIntervals(moInterval);
								int addMoFlag = perfPollTaskMapper
										.insertTaskMo(moBean);
							}
						}
					}
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
				facade.sendNotification(servers.get(0).getIp(), topic, message);
			}
			/*
			 * for (int i = 0; i < moidList.size(); i++) { int
			 * moId=moidList.get(i); MODevice moDevice =
			 * moDeviceMapper.selectByPrimaryKey(moId); // String moClassName =
			 * moDeviceMapper.getMoClassByMoId(moDevice); //
			 * if(!"VM".equals(moClassName)){ List<String>
			 * moList=perfPollTaskMapper.getMoList(moId); if(moList.size()>0){
			 * int isExsit=perfTaskInfoMapper.isExsitTask(moId); if(isExsit>0){
			 * PerfTaskInfoBean perfTaskTemp =
			 * perfTaskInfoMapper.getTaskInfoByMoId(moId);
			 * if(perfTaskTemp.getMoClassId() < moDevice.getMoClassId()){
			 * exsitTaskId=perfTaskInfoMapper.exsitTaskId(moId);
			 * if(moDevice.getMoClassId()==9){ PerfPollTaskBean bean = new
			 * PerfPollTaskBean(); bean.setTaskId(exsitTaskId);
			 * bean.setOperateStatus(3); bean.setProgressStatus(1); int
			 * deleteTask = perfTaskInfoMapper.updateTaskStatus(bean);
			 * deleteMoFlag=perfTaskInfoMapper.deleteMoList(exsitTaskId); }else{
			 * perfTask.setTaskId(exsitTaskId); perfTask.setMoId(moId);
			 * perfTask.setStatus(1); perfTask.setOperateStatus(2);//操作状态
			 * perfTask.setProgressStatus(1);//进度状态 perfTask.setLastOPResult(0);
			 * perfTask.setCreator(sysUserInfoBeanTemp.getId());
			 * perfTask.setCreateTime(dateFormat.format(new Date()));
			 * perfTask.setMoClassId(moDevice.getMoClassId()); updateTaskFlag=
			 * perfTaskInfoMapper.updateTask(perfTask);
			 * deleteMoFlag=perfTaskInfoMapper.deleteMoList(exsitTaskId);
			 * MoInfoBean moBean=new MoInfoBean(); for (int j = 0; j <
			 * moList.size(); j++) { moBean.setTaskId(perfTask.getTaskId());
			 * moBean.setMid(Integer.parseInt(moList.get(j).split(",")[0]));
			 * moBean.setDoIntervals(10*60); int
			 * addMoFlag=perfPollTaskMapper.insertTaskMo(moBean); } }
			 * 
			 * }else{ logger.info("ID = "+moId+" 的设备已分发任务！"); continue; } }else{
			 * if(moDevice.getMoClassId()!=9){ perfTask.setMoId(moId);
			 * perfTask.setStatus(1); perfTask.setOperateStatus(1);//操作状态
			 * perfTask.setProgressStatus(1);//进度状态 perfTask.setLastOPResult(0);
			 * perfTask.setCreator(sysUserInfoBeanTemp.getId());
			 * perfTask.setCreateTime(dateFormat.format(new Date()));
			 * perfTask.setMoClassId(moDevice.getMoClassId());
			 * addTaskFlag=perfPollTaskMapper.insertTaskInfo(perfTask);
			 * MoInfoBean moBean=new MoInfoBean(); for (int j = 0; j <
			 * moList.size(); j++) { moBean.setTaskId(perfTask.getTaskId());
			 * moBean.setMid(Integer.parseInt(moList.get(j).split(",")[0]));
			 * moBean.setDoIntervals(10*60); int
			 * addMoFlag=perfPollTaskMapper.insertTaskMo(moBean); } } } } // }
			 * 
			 * }
			 */

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加默认任务异常！", e.getMessage());
			return false;
		}

	}

	/**
	 * 获得监测器
	 */
	@Override
	public List<String> getByMOClassID(int moClassId) {
		List<SysMoInfoBean> monitorLst = sysMoInfoMapper.getMoByMoClassId(moClassId);
		return this.getConcatMonitor(monitorLst);
	}

	@Override
	public List<String> getMOList(List<String> moList, int taskId) {
		Map<String, Object> midAndNameMap = new HashMap<String, Object>();
		for (int i = 0; i < moList.size(); i++) {
			String midAndNameStr = moList.get(i);
			midAndNameMap.put(midAndNameStr.split(",")[0], midAndNameStr
					.split(",")[1]);
		}

		logger.info("获取任务ID = " + taskId + " 的监测器信息");
		List<String> midAndDoIntervalsList = new ArrayList<String>();
		midAndDoIntervalsList = perfPollTaskMapper.getMoListByTaskId(taskId);

		List<String> result = new ArrayList<String>();
		for (int i = 0; i < midAndDoIntervalsList.size(); i++) {
			String midAndDoIntervalsStr = midAndDoIntervalsList.get(i);
			String midStr = midAndDoIntervalsStr.split(",")[0];
			String name = (String) midAndNameMap.get(midStr);
			int doIntervals = Integer.parseInt(midAndDoIntervalsStr.split(",")[1]);
			int timeUnit =1;
			if(midStr.equals("53")){
				timeUnit = 0;
			}else{
				if(doIntervals >=86400){
					doIntervals = doIntervals /60/60/24;
					timeUnit = 3;
				}else if(doIntervals >= 3600){
					doIntervals = doIntervals /60/60;
					timeUnit = 2;
				}else if(doIntervals >=60){
					doIntervals = doIntervals /60;
					timeUnit = 1;
				}
			}
			String resultStr = midStr + "," + name + "," + doIntervals + "," + timeUnit;
			result.add(resultStr);
		}
		return result;
	}

	@Override
	public Map<String, Object> findHostTree() {
		List<SysServerHostInfo> hostList = harvesterMapper.getServerHostLst();
		List<SysServerHostInfo> resultLst = new ArrayList<SysServerHostInfo>();
		int status = 2;
		for (int i = 0; i < hostList.size(); i++) {
			Dispatcher dispatcher = Dispatcher.getInstance();
			ManageFacade facade = dispatcher.getManageFacade();
			
			//获得所有的服务
			List<Server> allServers = facade.listAllActiveServers();
			
			for (Server server : allServers) {
				if(hostList.get(i).getIpaddress().equals(server.getIp()) && "PerfPoll".equals(server.getProcessName())){
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

	@Override
	public int getTemplateID(int moId, int moClassId) {
		SysMonitorsTemplateUsedBean templateUsedBean = sysMoInfoMapper.getTemplateByMoIDAndMOClassID(moId, moClassId);
		if(templateUsedBean != null){
			return templateUsedBean.getTemplateID();
		}
		return -1;
	}

	@Override
	public List<SysMonitorsTemplateBean> getAllTemplate(int moClassId) {
		List<SysMonitorsTemplateBean> templateLst = new ArrayList<SysMonitorsTemplateBean>();
		SysMonitorsTemplateBean bean  = new SysMonitorsTemplateBean();
		bean.setTemplateID(-1);
		bean.setTemplateName("未使用模板");
		templateLst.add(bean);
		List<SysMonitorsTemplateBean> addList = sysMoInfoMapper.queryMoTemplatesByClassID(moClassId);
		for (int i = 0; i < addList.size(); i++) {
			templateLst.add(addList.get(i));
		}
		return templateLst;
	}

	@Override
	public boolean doSetPerfTask(PerfTaskInfoBean bean, String moIDs,int templateID,String moTypeLstJson) {
		String[] split = moIDs.split(",");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		try {
			for (int i = 0; i < split.length; i++) {
				int moId = Integer.parseInt(split[i]);
				//使用模板，判断该设备是否有监测器
				if(templateID != -1){
					List<SysMoInfoBean> monitorList = objectPerfConfigService.getMoList(moId, bean.getMoClassId());
					if(monitorList.size() > 0){
						boolean isAdd = false;
						boolean deleteMoFlag = true;
						boolean insertMoFlag = true;
						boolean insertTask = true;
						int taskId = -1;
						//获得采集任务
						PerfTaskInfoBean perftask = perfTaskInfoMapper.getTaskInfoByMoId2(moId);
						//没有采集任务
						if(perftask == null){
							List<PerfPollTaskBean> vCenterTaskLst = new ArrayList<PerfPollTaskBean>();
							if(bean.getMoClassId() == 8){
								vCenterTaskLst = perfPollTaskMapper.getVCenterTask(bean.getMoId());
							}
							if(bean.getMoClassId() != 8 || vCenterTaskLst.size() <= 0){
								isAdd = true;
								
								SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
								.getSession().getAttribute("sysUserInfoBeanOfSession");
								bean.setMoId(moId);
								bean.setCreator(sysUserInfoBeanTemp.getId());
								bean.setCreateTime(dateFormat.format(new Date()));
								bean.setOperateStatus(1);// 操作状态
								bean.setProgressStatus(1);// 进度状态
								bean.setLastOPResult(0);
								bean.setCollectorId(-1);
								bean.setOldCollectorId(-1);
								bean.setLastStatusTime(dateFormat.format(new Date()));
								String dbName = "";
//							if(bean.getMoClassId() == 54){
//								
//							}
								bean.setDbName(dbName);
								try {
									perfPollTaskMapper.insertTaskInfo(bean);
									insertTask = true;
								} catch (Exception e) {
									logger.error("新增采集任务异常：" + e);
									insertTask = false;
								}
								taskId = bean.getTaskId();
							}
						}
						//已经有采集任务的
						else{
							taskId = perftask.getTaskId();
							//删除原来采集任务的监测器
							perfTaskInfoMapper.deleteMoList(perftask.getTaskId());
							deleteMoFlag = true;
							
							//更新采集任务
							bean.setStatus(perftask.getStatus());
							// 更新采集任务
							SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
							.getSession().getAttribute("sysUserInfoBeanOfSession");
							perftask.setMoId(moId);
							perftask.setStatus(perftask.getStatus());
							perftask.setCreator(sysUserInfoBeanTemp.getId());
							perftask.setCreateTime(dateFormat.format(new Date()));
							perftask.setTaskId(perftask.getTaskId());
							perftask.setOperateStatus(2); // 操作状态
							perftask.setMoClassId(bean.getMoClassId());
							if (perftask.getStatus() != 1) {
								perftask.setProgressStatus(1);
							}
							perftask.setLastStatusTime(dateFormat.format(new Date()));// 最近状态时间
							perfTaskInfoMapper.updateTask(perftask);
						}
						
						//新增监测器
						List<String> moTypeList = JSON.parseArray(moTypeLstJson,String.class);
						for (int n = 0; n <moTypeList.size(); n++) {
							String[] moTypeInfoLst = moTypeList.get(n).split(",");
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
						
						if (insertMoFlag == true && deleteMoFlag == true && insertTask == true) {
							if (isAdd == true) {
								// 删除其虚拟机的采集任务
								if (bean.getMoClassId() == 8) {
									List<Integer> vmMoIds = new ArrayList<Integer>();
									// 获得其虚拟机信息
									List<MODeviceObj> vmLst = moDeviceMapper.getVms(bean
											.getMoId());
									if (vmLst.size() > 0) {
										for (int m = 0; m < vmLst.size(); m++) {
											if (vmLst.get(m).getMoClassId() == 9) {
												vmMoIds.add(vmLst.get(m).getMoid());
											}
										}
									}
									if (vmMoIds.size() > 0) {
										for (int m = 0; m < vmMoIds.size(); m++) {
											int vmMoId = vmMoIds.get(m);
											int isExsitTask = perfTaskInfoMapper
											.isExsitTask(vmMoId);
											if (isExsitTask > 0) {
												int exsitTaskId = perfTaskInfoMapper
												.exsitTaskId(vmMoId);
												PerfPollTaskBean perfTask = new PerfPollTaskBean();
												perfTask.setTaskId(exsitTaskId);
												perfTask.setOperateStatus(3);
												perfTask.setProgressStatus(1);
												int deleteTask = perfTaskInfoMapper
												.updateTaskStatus(perfTask);
											} else {
												continue;
											}
										}
									}
									this.notifyDisPatch();
								}
								//对象类型为vCenter
								else if(bean.getMoClassId() == 75){
									String vCenterIP = bean.getDeviceIp();
									List<MODeviceObj> phLst = moDeviceMapper.getPhs(vCenterIP);
									List<Integer> phIds = new ArrayList<Integer>();
									if(phLst.size() > 0){
										for (int m = 0; i < phLst.size(); m++) {
											phIds.add(phLst.get(m).getMoid());
										}
									}
									
									//删除对应的宿主机的任务
									if(phIds.size() > 0){
										for (int j = 0; j < phIds.size(); j++) {
											int phMoId = phIds.get(j);
											int isExsitTask = perfTaskInfoMapper.isExsitTask(phMoId);
											if (isExsitTask > 0) {
												int exsitTaskId = perfTaskInfoMapper.exsitTaskId(phMoId);
												PerfPollTaskBean perfTask = new PerfPollTaskBean();
												perfTask.setTaskId(exsitTaskId);
												perfTask.setOperateStatus(3);
												perfTask.setProgressStatus(1);
												perfTaskInfoMapper.updateTaskStatus(perfTask);
											} else {
												continue;
											}
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
								//非虚拟宿主机
								else{
									this.notifyDisPatch();
								}
							}else{
								//更新的情况
								if (bean.getStatus() != 1) {
									this.notifyDisPatch();
								}
							}
						} 
						
					}
				}
			}
			return true;
		} catch (Exception e) {
			logger.error("使用模板下发采集任务异常："+e);
			return false;
		}
	}

	/**
	 * 通知
	 */
	public void notifyDisPatch() {
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
	}

	@Override
	public boolean doSetDb2PerfTask(PerfTaskInfoBean bean, String moIDs,
			int templateID, String moTypeLstJson) {
		String[] split = moIDs.split(",");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		try {
			for (int i = 0; i < split.length; i++) {
				int moId = Integer.parseInt(split[i]);
				List<Db2InfoBean> db2Lst = db2Mapper.getDataBaseByserver(moId);
				//使用模板，判断该设备是否有监测器
				if(templateID != -1){
					for (int m = 0; m < db2Lst.size(); m++) {
						String dbName = db2Lst.get(m).getDatabaseName();
						
						List<SysMoInfoBean> monitorList = objectPerfConfigService.getMoList(moId, bean.getMoClassId());
						if(monitorList.size() > 0){
							boolean isAdd = false;
							boolean deleteMoFlag = true;
							boolean insertMoFlag = true;
							boolean insertTask = true;
							int taskId = -1;
							//获得采集任务
							PerfTaskInfoBean perftask = perfTaskInfoMapper.getTaskByMOIDAndClassAndDBName(moId,54,dbName);
							//没有采集任务
							if(perftask == null){
								isAdd = true;
								
								SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
								.getSession().getAttribute("sysUserInfoBeanOfSession");
								bean.setMoId(moId);
								bean.setCreator(sysUserInfoBeanTemp.getId());
								bean.setCreateTime(dateFormat.format(new Date()));
								bean.setOperateStatus(1);// 操作状态
								bean.setProgressStatus(1);// 进度状态
								bean.setLastOPResult(0);
								bean.setCollectorId(-1);
								bean.setOldCollectorId(-1);
								bean.setLastStatusTime(dateFormat.format(new Date()));
								bean.setDbName(dbName);
								try {
									perfPollTaskMapper.insertTaskInfo(bean);
									insertTask = true;
								} catch (Exception e) {
									logger.error("新增采集任务异常：" + e);
									insertTask = false;
								}
								taskId = bean.getTaskId();
							}
							//已经有采集任务的
							else{
								taskId = perftask.getTaskId();
								//删除原来采集任务的监测器
								perfTaskInfoMapper.deleteMoList(perftask.getTaskId());
								deleteMoFlag = true;
								
								//更新采集任务
								bean.setStatus(perftask.getStatus());
								// 更新采集任务
								SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
								.getSession().getAttribute("sysUserInfoBeanOfSession");
								perftask.setMoId(moId);
								perftask.setStatus(perftask.getStatus());
								perftask.setCreator(sysUserInfoBeanTemp.getId());
								perftask.setCreateTime(dateFormat.format(new Date()));
								perftask.setTaskId(perftask.getTaskId());
								perftask.setOperateStatus(2); // 操作状态
								perftask.setMoClassId(bean.getMoClassId());
								if (perftask.getStatus() != 1) {
									perftask.setProgressStatus(1);
								}
								perftask.setLastStatusTime(dateFormat.format(new Date()));// 最近状态时间
								perfTaskInfoMapper.updateTask(perftask);
							}
							
							//新增监测器
							List<String> moTypeList = JSON.parseArray(moTypeLstJson,String.class);
							for (int n = 0; n <moTypeList.size(); n++) {
								String[] moTypeInfoLst = moTypeList.get(n).split(",");
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
							
							if (insertMoFlag == true && deleteMoFlag == true && insertTask == true) {
								if (isAdd == true) {
									this.notifyDisPatch();
								}else{
									//更新的情况
									if (bean.getStatus() != 1) {
										this.notifyDisPatch();
									}
								}
							} 
							
						}
					}
				}
			}
			return true;
		} catch (Exception e) {
			logger.error("使用模板下发采集任务异常："+e);
			return false;
		}
	}

	@Override
	public List<String> listMoList(int moId) {
		List<SysMoInfoBean> monitorLst = new ArrayList<SysMoInfoBean>();
		monitorLst = sysMoInfoMapper.getMoByManuAndCategory(moId);
		if(monitorLst == null || monitorLst.size() == 0){
			monitorLst = sysMoInfoMapper.getMoByManufacturer(moId);
		}
		
		return this.getConcatMonitor(monitorLst);
	}
	
	public List<String> getConcatMonitor(List<SysMoInfoBean> monitorLst){
		List<String> moList = new ArrayList<String>();
		if(monitorLst.size() > 0){
			for (int i = 0; i < monitorLst.size(); i++) {
				int mid = monitorLst.get(i).getMid();
				String moName = monitorLst.get(i).getMoName();
				int doIntervals = -1;
				if(monitorLst.get(i).getDoIntervals() != null){
					doIntervals = monitorLst.get(i).getDoIntervals();
				}
				int timeUnit = 1;
				if(monitorLst.get(i).getTimeUnit() != null){
					timeUnit = monitorLst.get(i).getTimeUnit();
				}
				String str = mid + "," + moName + "," + doIntervals+ "," + timeUnit;
				moList.add(str);
			}
		}
		return moList;
	}

	@Override
	public boolean isInScope(String deviceIp, int collectorId) {
		int deviceIpToInt = IPPoolUtil.ipToInt(deviceIp);
		List<Integer> domainIdLst = harvesterMapper.getDomainByServer(collectorId);
		List<ManagedDomainIPScopeBean> scopeLst = domainIPScopeMapper.getScopesByDomainIds(domainIdLst);
		if(domainIdLst.contains(1) && scopeLst.size() == 0){
			return true;
		}
		for (int i = 0; i < scopeLst.size(); i++) {
			String ip1 = scopeLst.get(i).getIp1();
			String ip2 = scopeLst.get(i).getIp2();
			//ip范围
			if(scopeLst.get(i).getScopeType() == 2){
				int ip1ToInt = IPPoolUtil.ipToInt(ip1);
				int ip2ToInt = IPPoolUtil.ipToInt(ip2);
				if(deviceIpToInt >= ip1ToInt && deviceIpToInt <= ip2ToInt){
					return true;
				}
			}
			//子网
			else{
				int[] ipscope = IPPoolUtil.getIPIntScope(ip1, ip2);
				if(deviceIpToInt >= ipscope[0] && deviceIpToInt <= ipscope[1]){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean delVMsByVHost(PerfTaskInfoBean bean) {
		int vhostMoId = bean.getMoId();
		int exsitTaskId = 0;
		int isExsitTask = 0;
		List<Integer> vmMoIds = new ArrayList<Integer>();
		// 获得其虚拟机信息
		List<MODeviceObj> vmLst = moDeviceMapper.getVms(vhostMoId);
		if (vmLst.size() > 0) {
			for (int i = 0; i < vmLst.size(); i++) {
				if (vmLst.get(i).getMoClassId() == 9) {
					vmMoIds.add(vmLst.get(i).getMoid());
				}
			}
		}
		try {
			if (vmMoIds.size() > 0) {
				for (int i = 0; i < vmMoIds.size(); i++) {
					int moId = vmMoIds.get(i);
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
						
					} else {
						continue;
					}
				}

				//通知
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
			return true;
		} catch (Exception e) {
			logger.error("删除虚拟宿主机"+vhostMoId+"的虚拟机的采集任务异常："+e);
		}
		return false;
	}

	@Override
	public String getSiteName(int moId, int moClassId) {
		String siteName = "";
		if(moClassId == 91){
			SiteDns dns = webSiteMapper.getSiteDnsByMoId(moId);
			if(dns != null){
				siteName = dns.getSiteName();
			}
		}
		else if(moClassId == 92){
			SiteFtp ftp = webSiteMapper.getSiteFtpByMoId(moId);
			if(ftp != null){
				siteName = ftp.getSiteName();
			}
		}
		else if(moClassId == 93){
			SiteHttp http = webSiteMapper.getSiteHttpByMoId(moId);
			if(http != null){
				siteName = http.getSiteName();
			}
		}
		else if(moClassId == 94){
			SitePort sitePort = webSiteMapper.getSitePortByMoId(moId);
			if(sitePort != null){
				siteName = sitePort.getSiteName();
			}
		}
		return siteName;
	}
	
	@Override
	public SiteDns findSiteDnsInfo(int moID) {
		SiteDns dns = webSiteMapper.getSiteDnsByMoId(moID);
		return dns;
	}

	@Override
	public SiteFtp findSiteFtpnfo(int moID) {
		SiteFtp ftp = webSiteMapper.getFtpAndCommInfo(moID);
		if(ftp == null){
			ftp = webSiteMapper.getSiteFtpByMoId(moID);
		}
		return ftp;
	}

	@Override
	public SiteHttp findSiteHttpnfo(int moID) {
		SiteHttp http = webSiteMapper.getHttpAndCommInfo(moID);
		if(http == null){
			http = webSiteMapper.getSiteHttpByMoId(moID);
		}
		return http;
	}

	@Override
	public SitePort findSitePortInfo(int moID) {
		SitePort sitePort = webSiteMapper.getSitePortByMoId(moID);
		return sitePort;
	}
	
	@Override
	public int operateSiteCommunity(WebSite webSite) {
		int result = 1;
		int moClassID = webSite.getMoClassID();
		if(moClassID == 92){
			int isAuth = webSite.getSiteFtp().getIsAuth();
			if(isAuth ==2){
				List<SysSiteCommunityBean> sitecomLst = siteCommunityMapper.getFtpCommunityByMOID(webSite.getMoID());
				SysSiteCommunityBean communityBean = new SysSiteCommunityBean();
				if(sitecomLst.size() > 0){
					communityBean.setUserName(webSite.getSiteFtp().getUserName());
					communityBean.setPassword(webSite.getSiteFtp().getPassword());
					communityBean.setId(sitecomLst.get(0).getId());
					try {
						siteCommunityMapper.updateSiteCommunity(communityBean);
						result = 1;
					} catch (Exception e) {
						result = 0;
					}
				}else{
					communityBean.setSiteType(1);
					communityBean.setIpAddress(webSite.getSiteFtp().getIpAddr());
					communityBean.setUserName(webSite.getSiteFtp().getUserName());
					communityBean.setPassword(webSite.getSiteFtp().getPassword());
					communityBean.setPort(webSite.getSiteFtp().getPort());
					try {
						siteCommunityMapper.insertSiteCommunity(communityBean);
						result = 1;
					} catch (Exception e) {
						result = 0;
					}
				}
			}
		}else if(moClassID == 93){
			List<SysSiteCommunityBean> sitecomLst = siteCommunityMapper.getHttpCommunityByMOID(webSite.getMoID());
			SysSiteCommunityBean communityBean = new SysSiteCommunityBean();
			if(sitecomLst.size() > 0){
				communityBean.setRequestMethod(webSite.getSiteHttp().getRequestMethod());
				communityBean.setId(sitecomLst.get(0).getId());
				try {
					siteCommunityMapper.updateSiteCommunity(communityBean);
					result = 1;
				} catch (Exception e) {
					result = 0;
				}
			}else{
				communityBean.setSiteType(2);
				communityBean.setIpAddress(webSite.getSiteHttp().getHttpUrl());
				communityBean.setRequestMethod(webSite.getSiteHttp().getRequestMethod());
				try {
					siteCommunityMapper.insertSiteCommunity(communityBean);
					result = 1;
				} catch (Exception e) {
					result = 0;
				}
			}
		}
		return result;
	}

	@Override
	public PerfTaskInfoBean getDNSTask(int taskId) {
		return perfTaskInfoMapper.getDNSTask(taskId);
	}

	@Override
	public PerfTaskInfoBean getFtpTask(int taskId) {
		PerfTaskInfoBean taskInfoBean = perfTaskInfoMapper.getFtpTask(taskId);
		List<SysSiteCommunityBean> siteComLst = siteCommunityMapper.getFtpCommunityByMOID(taskInfoBean.getMoId());
		if(siteComLst.size() > 0){
			SysSiteCommunityBean communityBean = siteComLst.get(0);
			taskInfoBean.setSiteCommunityBean(communityBean);
		}
		return taskInfoBean;
	}

	@Override
	public PerfTaskInfoBean getHttpTask(int taskId) {
		PerfTaskInfoBean taskInfoBean = perfTaskInfoMapper.getHttpTask(taskId);
		List<SysSiteCommunityBean> siteComLst = siteCommunityMapper.getHttpCommunityByMOID(taskInfoBean.getMoId());
		if(siteComLst.size() > 0){
			SysSiteCommunityBean communityBean = siteComLst.get(0);
			taskInfoBean.setSiteCommunityBean(communityBean);
		}
		return taskInfoBean;
	}

	@Override
	public PerfTaskInfoBean getTcpTask(int taskId) {
		return perfTaskInfoMapper.getTcpTask(taskId);
	}

	@Override
	public Map<String, Object> doBatchStop(String taskIds) {
		Map<String, Object> result = new HashMap<String, Object>();
		String[] splitIds = taskIds.split(",");
		//进度为完成任务ID
		String notFinishIds = "";
		//已停止的任务ID
		String stopStatusIds = ""; 
		for (String strId : splitIds) {
			PerfPollTaskBean taskBean = perfPollTaskMapper
					.getTaskByTaskId(Integer.parseInt(strId));
			int processStatus = taskBean.getProgressStatus();
			int status = taskBean.getStatus();
			if (processStatus == FINISH_PROCESS) {
				if (status != STOP_STATUS) {
					taskBean.setProgressStatus(FIRST_PROCESS);
					taskBean.setOperateStatus(STOP_OPERATESTATUS);
					perfTaskInfoMapper.updateTaskStatus(taskBean);
					this.notifyDisPatch();
				}else{
					stopStatusIds += strId + ",";
				}
			} else {
				notFinishIds += strId + ",";
			}
		}
		result.put("notFinishIds", notFinishIds);
		result.put("stopStatusIds", stopStatusIds);
		return result;
	}

	@Override
	public Map<String, Object> initTree() {
		List<MObjectDefBean> mobjectList = new ArrayList<MObjectDefBean>();
		try {
			mobjectList = mobjectInfoMapper.getAllMObject();
		} catch (Exception e) {
			logger.error("获得对象异常：" + e);
		}
		List<MObjectDefBean> menuLst = new ArrayList<MObjectDefBean>();
		for (int i = 0; i < mobjectList.size(); i++) {
			MObjectDefBean bean = mobjectList.get(i);
			if (bean.getParentClassId() != null) {
				if (bean.getClassId() != 9) {
					if (bean.getClassId() == -1) {
						bean.setClassId(1);
					}
					menuLst.add(bean);
				}
			}
		}

		for (int i = 0; i < menuLst.size(); i++) {
			MObjectDefBean bean = menuLst.get(i);
			if (bean.getParentClassId() == null || bean.getParentClassId() == 1) {
				bean.setParentClassId(0);
			}
		}

		String menuLstJson = JsonUtil.toString(menuLst);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("menuLstJson", menuLstJson);
		return result;
	}

	@Override
	public List<String> listMoListForRoom(int ResManufacturerID) {
		List<SysMoInfoBean> monitorLst = new ArrayList<SysMoInfoBean>();
		monitorLst = sysMoInfoMapper.getMoByManuAndCategoryForRoom(ResManufacturerID);
		
		return this.getConcatMonitor(monitorLst);
	}

	@Override
	public Map<String, Object> doBatchStart(String taskIds) {
		Map<String, Object> result = new HashMap<String, Object>();
		String[] splitIds = taskIds.split(",");
		//进度为完成任务ID
		String notFinishIds = "";
		//已停止的任务ID
		String startStatusIds = ""; 
		for (String strId : splitIds) {
			PerfPollTaskBean taskBean = perfPollTaskMapper
					.getTaskByTaskId(Integer.parseInt(strId));
			int processStatus = taskBean.getProgressStatus();
			int status = taskBean.getStatus();
			if (processStatus == FINISH_PROCESS) {
				if (status != START_STATUS) {
					taskBean.setProgressStatus(FIRST_PROCESS);
					taskBean.setOperateStatus(START_OPERATESTATUS);
					perfTaskInfoMapper.updateTaskStatus(taskBean);
					this.notifyDisPatch();
				}else{
					startStatusIds += strId + ",";
				}
			} else {
				notFinishIds += strId + ",";
			}
		}
		result.put("notFinishIds", notFinishIds);
		result.put("startStatusIds", startStatusIds);
		return result;
	}

	
}
