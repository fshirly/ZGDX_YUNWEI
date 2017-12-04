package com.fable.insightview.monitor.syslog.service.impl;

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

import com.fable.insightview.monitor.alarmmgr.alarmeventdefine.mapper.AlarmEventDefineMapper;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmEventDefineBean;
import com.fable.insightview.monitor.dispatcher.Dispatcher;
import com.fable.insightview.monitor.dispatcher.constants.TaskType;
import com.fable.insightview.monitor.dispatcher.facade.ManageFacade;
import com.fable.insightview.monitor.dispatcher.notify.TaskDispatchNotification;
import com.fable.insightview.monitor.dispatcher.server.Server;
import com.fable.insightview.monitor.dispatcher.server.TaskDispatcherServer;
import com.fable.insightview.monitor.harvester.entity.SysServerHostInfo;
import com.fable.insightview.monitor.harvester.mapper.HarvesterMapper;
import com.fable.insightview.monitor.syslog.entity.SysSyslogRulesBean;
import com.fable.insightview.monitor.syslog.entity.SysSyslogTaskBean;
import com.fable.insightview.monitor.syslog.mapper.SysSyslogRulesMapper;
import com.fable.insightview.monitor.syslog.mapper.SysSyslogTaskMapper;
import com.fable.insightview.monitor.syslog.service.ISysSyslogRulesService;
import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.page.Page;

@Service
public class SysSyslogRulesServiceImpl implements ISysSyslogRulesService {
	private final Logger logger = LoggerFactory
			.getLogger(SysSyslogRulesServiceImpl.class);
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// 采集机正常状态
	private static final int NORMAL_STATUS = 1;
	// 采集机挂起状态
	private static final int ABNORMAL_STATUS = 2;
	// syslog类型id
	private static final int SYSLOG_ALARMTYPEID = 8;
	// 进度： 等待分发
	private static final int FIRST_PROGRESS = 1;
	// 进度：完成
	private static final int FINISH_PROGRESS = 5;
	// 新增操作状态
	private static final int ADD_OPERATESTATUS = 1;
	// 更新操作状态
	private static final int UPDATE_OPERATESTATUS = 2;
	// 最后操作状态：成功
	private static final int SUCESSRESULT = 0;
	//离线
	private static final String OFFLINE = "1";
	
	private static final String FACILITY= "facility";
	private static final String SEVERITY= "severity";
	private static final String MESSAGE= "msg";
	
	@Autowired SysSyslogRulesMapper syslogRulesMapper;
	@Autowired SysSyslogTaskMapper syslogTaskMapper;
	@Autowired HarvesterMapper harvesterMapper;
	@Autowired AlarmEventDefineMapper alarmEventDefineMapper;
	
	@Override
	public Map<String, Object> getSyslogTasks(Page<SysSyslogRulesBean> page) {
		List<SysSyslogRulesBean> syslogRuleLst = syslogRulesMapper.getSyslogRules(page);
		int total = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", syslogRuleLst);
		result.put("total", total);
		return result;
	}

	@Override
	public Map<String, Object> listSyslogCollector(Map map) {
		List<SysServerHostInfo> resultLst = new ArrayList<SysServerHostInfo>();
		//获得在线采集机
		List<SysServerHostInfo> hostList = harvesterMapper.getCollectorLst(map);
		//采集机状态   1：正常，2：挂起
		int status = ABNORMAL_STATUS;
		for (int i = 0; i < hostList.size(); i++) {
			Dispatcher dispatcher = Dispatcher.getInstance();
			ManageFacade facade = dispatcher.getManageFacade();
			
			//获得所有的服务
			List<Server> allServers = facade.listAllActiveServers();
			
			for (Server server : allServers) {
				if(hostList.get(i).getIpaddress().equals(server.getIp()) && "SyslogServer".equals(server.getProcessName())){
					status = NORMAL_STATUS;
					break;
				}else{
					continue;
				}
			}
			if(status == NORMAL_STATUS){
				resultLst.add(hostList.get(i));
			}
			status = ABNORMAL_STATUS;
		}
		
		//获得离线
		List<SysServerHostInfo> offlineHostList = harvesterMapper.getOfflineCollectorLst(map);
		resultLst.addAll(offlineHostList);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", resultLst);
		return result;
	}

	@Override
	public boolean checkRuleName(SysSyslogRulesBean bean) {
		List<SysSyslogRulesBean> syslogRuleLst = syslogRulesMapper.getRuleByCondition(bean);
		if(syslogRuleLst.size() > 0){
			return false;
		}
		return true;
	}

	@Override
	public Map<String, Object> doAddSyslogRule(SysSyslogRulesBean bean) {
		Map<String, Object> result = new HashMap<String, Object>();
		int insertCount = 0;
		int ruleID = -1;
		// 获得syslog事件
		List<AlarmEventDefineBean> alarmeventLst = alarmEventDefineMapper
				.getAlarmeventByAlarmType(SYSLOG_ALARMTYPEID);
		String alarmOID = "";
		if (alarmeventLst.size() > 0) {
			alarmOID = alarmeventLst.get(0).getAlarmOID();
		}
		bean.setAlarmOID(alarmOID);
		String filterExpression = this.getFilterExpression(bean);
		logger.info("表达式长度：" + filterExpression.length());
		bean.setFilterExpression(filterExpression);
		try {
			insertCount = syslogRulesMapper.insertSyslogRule(bean);
			if (insertCount > 0) {
				ruleID = bean.getRuleID();
				logger.info("新增的syslog规则的id为：" + ruleID);
			}
			result.put("insertCount", insertCount);
			result.put("ruleID", ruleID);
		} catch (Exception e) {
			logger.error("新增syslog规则异常：", e);
		}
		return result;
	}

	/**
	 * 设置syslog任务的过滤表达式
	 */
	String getFilterExpression(SysSyslogRulesBean bean){
		String facilityFilter = "";
		String severityFilter = "";
		String messageFilter = "";
		String facilitylist = bean.getFacilitylist();
		logger.info("facilitylist:" + facilitylist);
		if(!"".equals(facilitylist) && facilitylist != null){
			String[] facilityArray = facilitylist.split(",");
			facilityFilter += "(";
			for (int i = 0; i < facilityArray.length - 1; i++) {
				facilityFilter += "#syslog.facility eq "+facilityArray[i] + " or ";
			}
			facilityFilter += "#syslog.facility eq "+facilityArray[facilityArray.length - 1] + ")";
		}
		
		String severitylist = bean.getSeveritylist();
		logger.info("severitylist:" + severitylist);
		if(!"".equals(severitylist) && severitylist != null){
			String[] severityArray = severitylist.split(",");
			severityFilter += "(";
			for (int i = 0; i < severityArray.length - 1; i++) {
				severityFilter += "#syslog.severity eq "+severityArray[i] + " or ";
			}
			severityFilter += "#syslog.severity eq "+severityArray[severityArray.length - 1] + ")";
		}
		
		String keyword = bean.getKeyword();
		logger.info("keyword:" + keyword);
		if(!"".equals(keyword) && keyword != null){
			String[] messageArray = keyword.split(",");
			messageFilter += "(";
			for (int i = 0; i < messageArray.length - 1; i++) {
				messageFilter += "#syslog.msg.contains('"+messageArray[i] + "') or ";
			}
			messageFilter += "#syslog.msg.contains('"+messageArray[messageArray.length - 1] + "'))";
		}
		String filterStr = "";
		if(!"".equals(facilityFilter) && !"".equals(severityFilter) && !"".equals(messageFilter)){
			filterStr += facilityFilter + " and "+ severityFilter + " and "+ messageFilter;
			filterStr = "(" + filterStr + ")";
		}else if(!"".equals(facilityFilter) && !"".equals(severityFilter) && "".equals(messageFilter)){
			filterStr += facilityFilter + " and "+ severityFilter;
			filterStr = "(" + filterStr + ")";
		}else if(!"".equals(facilityFilter) && "".equals(severityFilter) && !"".equals(messageFilter)){
			filterStr += facilityFilter + " and "+ messageFilter;
			filterStr = "(" + filterStr + ")";
		}else if("".equals(facilityFilter) && !"".equals(severityFilter) && !"".equals(messageFilter)){
			filterStr += severityFilter + " and "+ messageFilter;
		}else if(!"".equals(facilityFilter) && "".equals(severityFilter) && "".equals(messageFilter)){
			filterStr += facilityFilter;
		}else if("".equals(facilityFilter) && !"".equals(severityFilter) && "".equals(messageFilter)){
			filterStr += severityFilter;
		}else if("".equals(facilityFilter) && "".equals(severityFilter) && !"".equals(messageFilter)){
			filterStr += messageFilter;
		}
		logger.info("syslog过滤表达式为:" + filterStr);
		return filterStr;
	}
	
	/**
	 * 新增syslog任务
	 */
	@Override
	public boolean addSyslogTask(String[] splits, int ruleID) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
		.getSession().getAttribute("sysUserInfoBeanOfSession");
		
		try {
			for (int i = 0; i < splits.length; i++) {
				int collectorID = Integer.parseInt(splits[i].split(":")[0]);
				SysSyslogTaskBean bean = new SysSyslogTaskBean();
				bean.setCreator(sysUserInfoBeanTemp.getId());
				bean.setCreateTime(dateFormat.format(new Date()));
				bean.setProgressStatus(FIRST_PROGRESS);
				bean.setOperateStatus(ADD_OPERATESTATUS);
				bean.setLastOPResult(SUCESSRESULT);
				bean.setLastStatusTime(dateFormat.format(new Date()));// 最近状态时间
				bean.setRuleID(ruleID);
				bean.setCollectorID(collectorID);
				if(OFFLINE.equals(splits[i].split(":")[1])){
					bean.setIsOffline(OFFLINE);
				}
				syslogTaskMapper.insertSyslogTask(bean);
				this.notifyDisPatch();
			}
			return true;
		} catch (NumberFormatException e) {
			logger.error("新增Syslog任务失败：", e);
		}
		return false;
	}
	
	/**
	 * 任务下发
	 */
	public void notifyDisPatch(){
		Dispatcher dispatch = Dispatcher.getInstance();
		ManageFacade facade = dispatch.getManageFacade();
		List<TaskDispatcherServer> servers = facade
				.listActiveServersOf(TaskDispatcherServer.class);
		if (servers.size() > 0) {
			String topic = "TaskDispatch";
			TaskDispatchNotification message = new TaskDispatchNotification();
			message.setDispatchTaskType(TaskType.TASK_SYSLOG);
			facade.sendNotification(servers.get(0).getIp(), topic, message);
		}
	}

	@Override
	public SysSyslogRulesBean initRuleDetail(int ruleID) {
		return syslogRulesMapper.getSyslogRuleByID(ruleID);
	}

	@Override
	public String checkIsEdit(int ruleID) {
		String taskIDs = "";
		List<SysSyslogTaskBean> syslogTaskLst = syslogTaskMapper.getTaskInfoByRuleID(ruleID);
		for (int i = 0; i < syslogTaskLst.size(); i++) {
			int progressStatus = syslogTaskLst.get(i).getProgressStatus();
			int taskID = syslogTaskLst.get(i).getTaskID();
			if(progressStatus != FINISH_PROGRESS){
				taskIDs += taskID + ",";
			}
		}
		if(!"".equals(taskIDs)){
			taskIDs = taskIDs.substring(0 , taskIDs.lastIndexOf(","));
		}
		return taskIDs;
	}

	@Override
	public SysSyslogRulesBean initRuleInfo(int ruleID) {
		SysSyslogRulesBean bean = syslogRulesMapper.getSyslogRuleByID(ruleID);
		bean = this.analyzeFilterExpression(bean);
		return bean;
	}
	
	public SysSyslogRulesBean analyzeFilterExpression(SysSyslogRulesBean bean) {
		String filterExpression = bean.getFilterExpression();
//		try {
//			Ognl.parseExpression(filterExpression);
//		} catch (OgnlException e) {
//			logger.error("ognl解析异常：" , e);
//		}

//		String  filterExpression = "!((#syslog.facility.value eq 0 or #syslog.facility.value eq 1) and (#syslog.severity.value eq 0 or #syslog.severity.value eq 1) and (#syslog.msg.value eq dd or #syslog.msg.value eq 测试))";
		
		String facilitylist = "";
		String severitylist = "";
		String keyword = "";
		String[] keyArray = filterExpression.split("and");
		if(keyArray.length >0){
			for (int i = 0; i < keyArray.length; i++) {
				String[] singleFilterArray = keyArray[i].split(" or ");
				String type = keyArray[i].split("#")[1].split(" ")[0].split("\\.")[1];
				if(FACILITY.equals(type)){
					for (int j = 0; j < singleFilterArray.length; j++) {
						facilitylist += singleFilterArray[j].split("eq ")[1] + ",";
					}
				}else if(SEVERITY.equals(type)){
					for (int j = 0; j < singleFilterArray.length; j++) {
						severitylist += singleFilterArray[j].split("eq ")[1] + ",";
					}
				}else if(MESSAGE.equals(type)){
					for (int j = 0; j < singleFilterArray.length; j++) {
						keyword += singleFilterArray[j].split("'")[1] + ",";
					}
				}
			}
		}
		
		if(!"".equals(facilitylist)){
			facilitylist = facilitylist.substring(0, facilitylist.indexOf(")"));
		}
		if(!"".equals(severitylist)){
			severitylist = severitylist.substring(0, severitylist.indexOf(")"));
		}
		if(!"".equals(keyword)){
			keyword = keyword.substring(0, keyword.lastIndexOf(","));
		}
		
//		if(FACILITY.equals(lastKeyName)){
//			facilitylist = facilitylist.substring(0, facilitylist.lastIndexOf(")"));
//		}else if(SEVERITY.equals(lastKeyName)){
//			severitylist = severitylist.substring(0, severitylist.lastIndexOf(")"));
//		}else if(MESSAGE.equals(lastKeyName)){
//			keyword = keyword.substring(0, keyword.lastIndexOf(")"));
//		}
		bean.setFacilitylist(facilitylist);
		bean.setSeveritylist(severitylist);
		bean.setKeyword(keyword);
		return bean;
	}

	@Override
	public boolean updateSyslogRule(SysSyslogRulesBean bean) {
		String filterExpression = this.getFilterExpression(bean);
		logger.info("表达式长度："+filterExpression.length());
		bean.setFilterExpression(filterExpression);
		try {
			syslogRulesMapper.updateSyslogRuleByID(bean);
			return true;
		} catch (Exception e) {
			logger.error("更新syslog规则异常："+ e,e);
		}
		return false;
	}

	@Override
	public String getSyslogTasks(int ruleID) {
		String taskIDs = "";
		List<SysSyslogTaskBean> syslogTaskLst = syslogTaskMapper.getTaskInfoByRuleID(ruleID);
		for (int i = 0; i < syslogTaskLst.size(); i++) {
			int taskID = syslogTaskLst.get(i).getTaskID();
			taskIDs += taskID + ",";
		}
		if(!"".equals(taskIDs) && taskIDs != null){
			taskIDs = taskIDs.substring(0, taskIDs.lastIndexOf(","));
		}
		return taskIDs;
	}

	@Override
	public boolean updateSyslogTask(String taskIDs) {
		SysSyslogTaskBean syslogTask = new SysSyslogTaskBean();
		logger.info("更新的syslog任务id为：" + taskIDs);
		syslogTask.setTaskIDs(taskIDs);
		syslogTask.setProgressStatus(FIRST_PROGRESS);
		syslogTask.setOperateStatus(UPDATE_OPERATESTATUS);
		syslogTask.setLastStatusTime(dateFormat.format(new Date()));
		try {
			syslogTaskMapper.updateSyslogTaskByIDs(syslogTask);
			this.notifyDisPatch();
			return true;
		} catch (Exception e) {
			logger.error("更新syslog任务异常：",e);
		}
		return false;
	}

	@Override
	public boolean delRule(Integer ruleID) {
		//删除已经删除完成的syslog任务结果
		boolean delDeledTaskFlag = false;
		//删除规则
		boolean delRuleFlag = false;
		
		//删除已经删除完成的syslog任务
		try {
			syslogTaskMapper.delDeledSyslogTask();
			delDeledTaskFlag = true;
		} catch (Exception e) {
			logger.error("删除已经删除完成的syslog任务异常：",e);
		}
		
		//删除syslog规则
		try {
			syslogRulesMapper.delSyslogRuleByID(ruleID);
			delRuleFlag = true;
		} catch (Exception e) {
			logger.error("删除syslog规则："+ ruleID +"异常：",e);
		}
		
		if(delDeledTaskFlag && delRuleFlag){
			return true;
		}
		return false;
	}

	@Override
	public Map<String, Object> getTaskIDsByRuleIDs(String ruleIDs) {
		//能够删除的规则id
		String delRuleIds = "";
		//不能删除的规则名称
		String undelRuleNames = "";
		
		String[] splits = ruleIDs.split(",");
		for (int i = 0; i < splits.length; i++) {
			int ruleID = Integer.parseInt(splits[i]);
			//获得该规则下的任务
			List<SysSyslogTaskBean> syslogTaskLst = syslogTaskMapper.getTaskInfoByRuleID(ruleID);
			//没有完成的任务id
			String unfinishTaskId = "";
			for (int j = 0; j < syslogTaskLst.size(); j++) {
				int taskID = syslogTaskLst.get(j).getTaskID();
				unfinishTaskId += taskID + ",";
			}
			if("".equals(unfinishTaskId) || unfinishTaskId == null){
				delRuleIds += ruleID + ",";
			}else{
				String ruleName = syslogTaskLst.get(0).getRuleName();
				undelRuleNames += ruleName + ",";
			}
		}
		if(!"".equals(undelRuleNames) && undelRuleNames != null){
			undelRuleNames = undelRuleNames.substring(0, undelRuleNames.lastIndexOf(","));
		}
		
		//返回结果
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("delRuleIds", delRuleIds);
		result.put("undelRuleNames", undelRuleNames);
		return result;
	}

	@Override
	public boolean delRules(String ruleIDs) {
		String[] splits = ruleIDs.split(",");
		if(splits.length > 0){
			for (int i = 0; i < splits.length; i++) {
				int ruleID = Integer.parseInt(splits[i]);
				boolean delFlag = this.delRule(ruleID);
				if(!delFlag){
					logger.error("删除规则：" + ruleID + "失败！");
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public String checkIsDel(int ruleID) {
		String taskIDs = "";
		List<SysSyslogTaskBean> syslogTaskLst = syslogTaskMapper.getTaskInfoByRuleID(ruleID);
		for (int i = 0; i < syslogTaskLst.size(); i++) {
			int taskID = syslogTaskLst.get(i).getTaskID();
			taskIDs += taskID + ",";
		}
		if(!"".equals(taskIDs)){
			taskIDs = taskIDs.substring(0 , taskIDs.lastIndexOf(","));
		}
		return taskIDs;
	}

	@Override
	public Map<String, Object> listOfflineSyslogCollector() {
		String processName = "SyslogServer";
		List<SysServerHostInfo> hostList = harvesterMapper.getAllServerHost(processName);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", hostList);
		return result;
	}
}
