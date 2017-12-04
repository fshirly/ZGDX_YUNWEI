package com.fable.insightview.monitor.syslog.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.alarmmgr.alarmeventdefine.mapper.AlarmEventDefineMapper;
import com.fable.insightview.monitor.dispatcher.Dispatcher;
import com.fable.insightview.monitor.dispatcher.constants.TaskType;
import com.fable.insightview.monitor.dispatcher.facade.ManageFacade;
import com.fable.insightview.monitor.dispatcher.notify.TaskDispatchNotification;
import com.fable.insightview.monitor.dispatcher.server.TaskDispatcherServer;
import com.fable.insightview.monitor.syslog.entity.SysSyslogTaskBean;
import com.fable.insightview.monitor.syslog.mapper.SysSyslogTaskMapper;
import com.fable.insightview.monitor.syslog.service.ISysSyslogTaskService;
import com.fable.insightview.platform.page.Page;
@Service
public class SysSyslogTaskServiceImpl implements ISysSyslogTaskService {
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final Logger logger = LoggerFactory
	.getLogger(SysSyslogTaskServiceImpl.class);
	//任务进度为等待分发
	private static final int FIRST_PROGRESSSTATUS = 1;
	//任务进度已完成
	private static final int FINISH_PROGRESSSTATUS = 5;
	// 删除操作状态
	private static final int DEL_OPERATESTATUS = 3;
	@Autowired SysSyslogTaskMapper syslogTaskMapper;
	@Autowired AlarmEventDefineMapper alarmEventDefineMapper;

	@Override
	public Map<String, Object> getSyslogTasks(Page<SysSyslogTaskBean> page) {
		List<SysSyslogTaskBean> syslogTaskLst = syslogTaskMapper.selectSyslogTasks(page);
		int total = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", syslogTaskLst);
		result.put("total", total);
		return result;
	}

	@Override
	public Map<String, Object> getProcessByTaskId(String taskIds) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<String> processLst = new ArrayList<String>();
		List<String> errorInfoLst = new ArrayList<String>();
		try {
			if (taskIds != "" && taskIds != null) {
				if (taskIds.contains(",") == true) {
					String[] taskIdLst = taskIds.split(",");
					for (int i = 0; i < taskIdLst.length; i++) {
						SysSyslogTaskBean task = syslogTaskMapper
								.getTaskInfoByTaskID(Integer.parseInt(taskIdLst[i]
										.split(":")[0]));
						processLst.add(task.getProgressStatus() + ":"
								+ taskIdLst[i].split(":")[1]);
						errorInfoLst.add(task.getErrorInfo() + ":"
								+ taskIdLst[i].split(":")[1]);
					}
				} else {
					SysSyslogTaskBean task = syslogTaskMapper
							.getTaskInfoByTaskID(Integer.parseInt(taskIds
									.split(":")[0]));
					processLst.add(task.getProgressStatus() + ":"
							+ taskIds.split(":")[1]);
					errorInfoLst.add(task.getErrorInfo() + ":"
							+ taskIds.split(":")[1]);
				}
			}
			result.put("processLst", processLst);
			result.put("errorInfoLst", errorInfoLst);
		} catch (NumberFormatException e) {
			logger.error("根据id获得syslog任务异常："+e);
		}
		return result;
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
	public boolean resendTask(SysSyslogTaskBean bean) {
		try {
			bean.setProgressStatus(FIRST_PROGRESSSTATUS);
			syslogTaskMapper.updateSyslogTaskByID(bean);
			this.notifyDisPatch();
			return true;
		} catch (Exception e) {
			logger.error("syslog任务重发失败：" + e);
		}
		return false;
	}

	@Override
	public boolean delTask(Integer taskID) {
		try {
			SysSyslogTaskBean bean = new SysSyslogTaskBean();
			bean.setProgressStatus(FIRST_PROGRESSSTATUS);
			bean.setOperateStatus(DEL_OPERATESTATUS);
			bean.setTaskID(taskID);
			bean.setLastStatusTime(dateFormat.format(new Date()));
			syslogTaskMapper.updateSyslogTaskByID(bean);
			this.notifyDisPatch();
			return true;
		} catch (Exception e) {
			logger.error("syslog任务删除失败：" + e);
		}
		return false;
	}

	@Override
	public Map<String, Object> delTasks(String taskIDs) {
		boolean delResult = false;
		String unDelTaskIDs = "";
		String delTaskIds = "";
		String[] splitIds = taskIDs.split(",");
		for (String strId : splitIds) {
			SysSyslogTaskBean taskBean = syslogTaskMapper
					.getTaskInfoByTaskID(Integer.parseInt(strId));
			int processStatus = taskBean.getProgressStatus();
			//已经完成的任务才可删除
			if (processStatus == FINISH_PROGRESSSTATUS) {
				delTaskIds += Integer.parseInt(strId) + ",";
			} else {
				unDelTaskIDs += Integer.parseInt(strId) + ",";
			}
		}
		
		//删除能够删除的任务
		try {
			if(!"".endsWith(delTaskIds)){
				delTaskIds = delTaskIds.substring(0, delTaskIds.lastIndexOf(","));
				logger.info("删除的syslog任务的id为："+delTaskIds);
				
				SysSyslogTaskBean bean = new SysSyslogTaskBean();
				bean.setProgressStatus(FIRST_PROGRESSSTATUS);
				bean.setOperateStatus(DEL_OPERATESTATUS);
				bean.setLastStatusTime(dateFormat.format(new Date()));
				bean.setTaskIDs(delTaskIds);
				syslogTaskMapper.updateSyslogTaskByIDs(bean);
				this.notifyDisPatch();
			}
			delResult = true;
		} catch (Exception e) {
			logger.error("批量删除syslog任务异常："+ e);
		}
		unDelTaskIDs = this.doUnDelTaskNames(unDelTaskIDs);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("delResult", delResult);
		result.put("unDelTaskIDs", unDelTaskIDs);
		return result;
	}
	
	/**
	 * 对不能删除的syslog任务名称进行处理
	 */
	public String doUnDelTaskNames(String unDelTaskNames){
		String taskNames = "";
		String name = "";
		if(!"".endsWith(unDelTaskNames)){
			String[] splitNameLst = unDelTaskNames.split(",");
			for (int i = 0; i < splitNameLst.length; i++) {
				name = splitNameLst[i];
				taskNames += name + ",";
			}
			taskNames = taskNames.substring(0, taskNames.lastIndexOf(","));
			taskNames = "<br/><div style='word-wrap: break-word; word-break: break-all; float: left; width: 200px;'>【 " + taskNames + " 】</div>";
		}
		return taskNames;
	}

	@Override
	public SysSyslogTaskBean initTaskDetail(Integer taskID) {
		return syslogTaskMapper.getTaskInfoByTaskID(taskID);
	}


	@Override
	public Map<String, Object> getIssuedCollectors(SysSyslogTaskBean bean) {
		List<SysSyslogTaskBean> taskLst = syslogTaskMapper.getCollectorByRuleID(bean);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", taskLst);
//		result.put("total", taskLst.size());
		return result;
	}

	@Override
	public String doBatchResend(String taskIDs) {
		String[] splitIds = taskIDs.split(",");
		String unResendTaskIds = "";
		boolean updateFlag = false;
		try {
			for (String strId : splitIds) {
				SysSyslogTaskBean task = syslogTaskMapper.getTaskInfoByTaskID(Integer.parseInt(strId));
				int processStatus = task.getProgressStatus();
				if(processStatus != FINISH_PROGRESSSTATUS){
					SysSyslogTaskBean taskBean = new SysSyslogTaskBean();
					taskBean.setProgressStatus(FIRST_PROGRESSSTATUS);
					taskBean.setTaskID(Integer.parseInt(strId));
					syslogTaskMapper.updateSyslogTaskByID(taskBean);
				}else{
					unResendTaskIds += strId + ",";
				}
			}
			updateFlag = true;
		} catch (NumberFormatException e) {
			logger.error("批量重发syslog任务异常：",e);
		}
		if(updateFlag){
			this.notifyDisPatch();
		}
		return unResendTaskIds;
	}

	@Override
	public Map<String, Object> getOperateStatus(String taskIds) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<String> operateLst = new ArrayList<String>();
		try {
			if (taskIds != "" && taskIds != null) {
				if (taskIds.contains(",") == true) {
					String[] taskIdLst = taskIds.split(",");
					for (int i = 0; i < taskIdLst.length; i++) {
						SysSyslogTaskBean task = syslogTaskMapper
								.getTaskInfoByTaskID(Integer.parseInt(taskIdLst[i]
										.split(":")[0]));
						operateLst.add(task.getOperateStatus() + ":"
								+ taskIdLst[i].split(":")[1]);
					}
				} else {
					SysSyslogTaskBean task = syslogTaskMapper
							.getTaskInfoByTaskID(Integer.parseInt(taskIds
									.split(":")[0]));
					operateLst.add(task.getOperateStatus() + ":"
							+ taskIds.split(":")[1]);
				}
			}
			result.put("operateLst", operateLst);
		} catch (NumberFormatException e) {
			logger.error("根据id获得syslog任务异常："+e);
		}
		return result;
	}

	@Override
	public Map<String, Object> getSyslogOfflineTasks(Page<SysSyslogTaskBean> page) {
		List<SysSyslogTaskBean> syslogTaskLst = syslogTaskMapper.selectOfflineSyslogTasks(page);
		int total = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", syslogTaskLst);
		result.put("total", total);
		return result;
	}
}
