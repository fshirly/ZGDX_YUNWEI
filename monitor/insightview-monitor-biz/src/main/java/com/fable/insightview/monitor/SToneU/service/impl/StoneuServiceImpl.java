package com.fable.insightview.monitor.SToneU.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.fable.insightview.monitor.AcUPS.service.IAcConditionService;
import com.fable.insightview.monitor.SToneU.entity.MODeviceSt;
import com.fable.insightview.monitor.SToneU.entity.PerfStoneuBean;
import com.fable.insightview.monitor.SToneU.mapper.StoneuMapper;
import com.fable.insightview.monitor.SToneU.service.StoneuService;
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
import com.fable.insightview.monitor.perf.service.IObjectPerfConfigService;
import com.fable.insightview.monitor.util.ConfigParameterCommon;
import com.fable.insightview.monitor.util.SToneuComm;
import com.fable.insightview.monitor.utils.DbConnection;
import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.page.Page;

@Service
public class StoneuServiceImpl implements StoneuService {
	private static final Logger logger = LoggerFactory.getLogger(StoneuServiceImpl.class);
	@Autowired
	StoneuMapper stoneMapper;
	@Autowired PerfPollTaskMapper perfPollTaskMapper;
	@Autowired PerfTaskInfoMapper perfTaskInfoMapper;
	@Autowired IObjectPerfConfigService objectPerfConfigService;
	
	@Autowired 
	private IAcConditionService condService;
	
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public List<MODeviceSt> getStoneuList(Page<MODeviceSt> page) {
		return stoneMapper.getStoneuList(page);
	}
	@Override
	public boolean getTestResult(ConfigParameterCommon snmp) {
		SToneuComm stoneu = new SToneuComm();
		return stoneu.getStoneuCondition(snmp);
	}
	 
	@Override
	public int getResManufacturerID(String OID) {
		return stoneMapper.getResManufacturerID(OID);
	}
	 
	@Override
	public boolean addSitePerfTask(MODeviceSt stoneu, int templateID, String moTypeLstJson) {

		// 新增采集任务结果
		boolean addTaskFlag = false;
		//新增监测器结果
		boolean insertMoFlag = false;
		//删除监测器结果
		boolean deleteMoFlag = true;
		// 采集任务id
		int taskId = -1;
		int moId = stoneu.getMOid();
		int moClassID = stoneu.getMOClassID();
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
				.getSession().getAttribute("sysUserInfoBeanOfSession");
		PerfTaskInfoBean perfTask = new PerfTaskInfoBean();
		// 检查采集任务是否存在
		int i  = perfTaskInfoMapper.isExsitTask(moId);
		//没有采集任务
		if(i == 0){
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
				logger.error("新增采集任务异常：" + e);
			}
		}
		//已经存在采集任务的 更新采集任务
		else{
			perfTask = perfTaskInfoMapper.getTaskInfoByMoId(moId);
			if(perfTask !=null){
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
				logger.error("新增采集任务异常：" + e);
			}
		  }else{
			  logger.debug("没有获取采集任务信息======");
		  }
		}
		
		//添加监测器
		insertMoFlag = this.insertMonitors(stoneu,taskId,templateID,moTypeLstJson);
		
		if(addTaskFlag == true && insertMoFlag == true && deleteMoFlag == true){
			Dispatcher dispatcher = Dispatcher.getInstance();
			ManageFacade facade = dispatcher.getManageFacade();

			List<TaskDispatcherServer> servers = facade.listActiveServersOf(TaskDispatcherServer.class);
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
	
	

	public boolean insertMonitors(MODeviceSt stone,int taskId,int templateID,String moTypeLstJson){
		boolean insertMoFlag = false;
		//绑定模板结果
		boolean setTemplateFlag = false;
		
		//绑定模板
		 SysMonitorsTemplateUsedBean templateUsedBean = new SysMonitorsTemplateUsedBean();
		templateUsedBean.setMoID(stone.getMOid());
		templateUsedBean.setMoClassID(stone.getMOClassID());
		templateUsedBean.setTemplateID(templateID);
		setTemplateFlag = objectPerfConfigService.setTmemplate(templateUsedBean);
		
		if(setTemplateFlag == true){
			//没有使用模板
			if(templateID == -1){
				String[] moList = stone.getMoList().split(",");
				String[] moIntervalList = stone.getMoIntervalList().split(",");
				String[] moTimeUnitList = stone.getMoTimeUnitList().split(",");
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
					
					List<SysMoInfoBean> monitorList = objectPerfConfigService.getMoList(stone.getMOid(), stone.getMOClassID());
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
	@Override
	public boolean addDoBatch(MODeviceSt stoneu,int templateID, String moTypeLstJson) {
		
		boolean flag =false;
		 String ip = stoneu.getDeviceIp();
		 		stoneu.setMOid(DbConnection.getMOID(1));
		 		stoneu.setMoName("斯特纽机房");
		 		stoneu.setDomainid(condService.getDomainID(ip));
		 		stoneu.setCreateTime(dateFormat.format(new Date()));
		 		stoneu.setLastUpdateTime(dateFormat.format(new Date()));
		 		stoneu.setUpdateAlarmTime(dateFormat.format(new Date()));
				stoneMapper.addModevice(stoneu);
				
			 	List<MODeviceSt> childDevice =  stoneMapper.queryChildInfo();
				List<MODeviceSt> tempList = new ArrayList<MODeviceSt>();
				Set<String> moSet = new HashSet<String>();
				for (MODeviceSt moDeviceSt : childDevice) {
					moSet.add(moDeviceSt.getMoName()+"_"+moDeviceSt.getMOClassID());
				}
				
				for (String string : moSet) {
					MODeviceSt mo = new MODeviceSt();
					mo.setMOid(DbConnection.getMOID(1));
					mo.setCreateTime(dateFormat.format(new Date()));
					mo.setLastUpdateTime(dateFormat.format(new Date()));
					mo.setUpdateAlarmTime(dateFormat.format(new Date()));
					mo.setNeManufacturerID(stoneu.getNeManufacturerID());
					mo.setMOClassID(Integer.parseInt(string.split("_")[1]));
					mo.setDeviceIp(ip);
					mo.setMoName(string.split("_")[0]);
					mo.setSourceMOID(stoneu.getMOid());
					tempList.add(mo);
				}
				// 批量插入子对象
				stoneMapper.addBatch(tempList); 
				
				stoneu.getSNMPbean().setMoID(stoneu.getMOid());
				stoneu.getSNMPbean().setID(DbConnection.getID("SNMPCommunityTempPK", 1));
			// 新增snmp信息
			int j= stoneMapper.insertSNMP(stoneu.getSNMPbean());
		if(j>0){
			// 新增采集任务
			flag = addSitePerfTask(stoneu,templateID,moTypeLstJson);
		}
		return flag;
	}
	@Override
	public boolean updateInfo(MODeviceSt stoneu,int templateID, String moTypeLstJson) {
		stoneu.setUpdateAlarmTime(dateFormat.format(new Date()));
		stoneu.setLastUpdateTime(dateFormat.format(new Date()));
		 stoneMapper.updateInfo(stoneu);
		 stoneMapper.updateSnmp(stoneu.getSNMPbean());
			//采集任务
			return this.addSitePerfTask(stoneu, templateID, moTypeLstJson);
	}
	@Override
	public MODeviceSt queryByMOID(Map<String, Object> paramMap) {
		return stoneMapper.queryByMOID(paramMap);
	}
	@Override
	public List<PerfStoneuBean> perfList(String deviceIP) {
		return stoneMapper.perfList(deviceIP);
	}
	@Override
	public int checkMOdevice(Map<String, Object> paramMap) {
		 
		return stoneMapper.checkMOdevice(paramMap);
	}
	@Override
	public Date getDateNow() {
		return stoneMapper.getDateNow();
	}

	
	 
}
