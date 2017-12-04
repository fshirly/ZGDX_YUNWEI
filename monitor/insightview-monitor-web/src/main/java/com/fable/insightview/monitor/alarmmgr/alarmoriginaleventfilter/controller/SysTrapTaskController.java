package com.fable.insightview.monitor.alarmmgr.alarmoriginaleventfilter.controller;

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

import com.fable.insightview.monitor.alarmmgr.entity.SysTrapTaskBean;
import com.fable.insightview.monitor.alarmmgr.traptask.mapper.SysTrapTaskMapper;
import com.fable.insightview.monitor.dispatcher.Dispatcher;
import com.fable.insightview.monitor.dispatcher.constants.TaskType;
import com.fable.insightview.monitor.dispatcher.facade.ManageFacade;
import com.fable.insightview.monitor.dispatcher.notify.TaskDispatchNotification;
import com.fable.insightview.monitor.dispatcher.server.TaskDispatcherServer;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;

/**
 * 
 * trap任务
 * 
 */
@Controller
@RequestMapping("/monitor/trapTask")
public class SysTrapTaskController {
	private static final Logger logger = LoggerFactory
			.getLogger(SysTrapTaskController.class);
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	SysTrapTaskMapper trapTaskMapper;

	/**
	 * 跳转至trap任务界面
	 * 
	 */
	@RequestMapping("/toSysTrapTask")
	public ModelAndView toAddDevice(HttpServletRequest result,
			HttpServletResponse response,String navigationBar) {
		
		logger.info("跳转至trap任务列表界面。。。。。。start");
		ModelAndView mv= new ModelAndView("monitor/alarmMgr/traptask/trapTask_list");
		mv.addObject("navigationBar", navigationBar);
		return mv;
	}

	/**
	 * 加载列表页面数据
	 * 
	 */
	@RequestMapping("/listTrapTasks")
	@ResponseBody
	public Map<String, Object> listTasks(SysTrapTaskBean trapTaskBean){
		
		logger.info("加载列表页面数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Map<String, Object> result = new HashMap<String, Object>();
		Page<SysTrapTaskBean> page = new Page<SysTrapTaskBean>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("alarmOID", trapTaskBean.getAlarmOID());
		paramMap.put("serverIP", trapTaskBean.getServerIP());
		paramMap.put("collectorName", trapTaskBean.getCollectorName());
		page.setParams(paramMap);
		List<SysTrapTaskBean> taskList;
		try {
			taskList = trapTaskMapper.selectTrapTasks(page);
			result.put("rows", taskList);
		} catch (Exception e) {
			logger.error("获得trap任务异常："+e);		
		}
		int total = page.getTotalRecord();
		result.put("total", total);
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
		String taskID = request.getParameter("taskID");
		request.setAttribute("taskID", taskID);
		return new ModelAndView("monitor/alarmMgr/traptask/trapTask_detail");
	}

	/**
	 * 查看详情
	 * 
	 */
	@RequestMapping("/initTaskDetail")
	@ResponseBody
	public SysTrapTaskBean initTaskDetail(SysTrapTaskBean bean) {
		SysTrapTaskBean trapTask = trapTaskMapper.getTaskInfoByTaskID(bean
				.getTaskID());
		return trapTask;
	}

	/**
	 * 根据任务ID获取操作进度
	 * 
	 */
	@RequestMapping("/getProcessByTaskId")
	@ResponseBody
	public Map<String, Object> getProcessByTaskId(String taskIds) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<String> processLst = new ArrayList<String>();
		List<String> errorInfoLst = new ArrayList<String>();
		try {
			if (taskIds != "") {
				if (taskIds.contains(",") == true) {
					String[] taskIdLst = taskIds.split(",");
					for (int i = 0; i < taskIdLst.length; i++) {
						SysTrapTaskBean task = trapTaskMapper
								.getTaskInfoByTaskID(Integer.parseInt(taskIdLst[i]
										.split(":")[0]));
						processLst.add(task.getProgressStatus() + ":"
								+ taskIdLst[i].split(":")[1]);
						errorInfoLst.add(task.getErrorInfo() + ":"
								+ taskIdLst[i].split(":")[1]);
					}
				} else {
					SysTrapTaskBean task = trapTaskMapper
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
			logger.error("根据id获得发现任务异常："+e);
		}
		return result;
	}

	/**
	 * 重发任务
	 * 
	 */
	@RequestMapping("/resendTask")
	@ResponseBody
	public boolean resendTask(SysTrapTaskBean taskBean) {
		try {
			trapTaskMapper.updateTaskProgressStatus(taskBean);
			Dispatcher dispatch = Dispatcher.getInstance();
			ManageFacade facade = dispatch.getManageFacade();
			List<TaskDispatcherServer> servers = facade
					.listActiveServersOf(TaskDispatcherServer.class);
			if (servers.size() > 0) {
				String topic = "TaskDispatch";
				TaskDispatchNotification message = new TaskDispatchNotification();
				message.setDispatchTaskType(TaskType.TASK_TRAP);
				facade.sendNotification(servers.get(0).getIp(), topic, message);
			}
			return true;
		} catch (Exception e) {
			logger.error("任务重发失败：" + e);
			return false;
		}

	}

	/**
	 * 根据任务ID获取采集机
	 * 
	 */
	@RequestMapping("/getCollectorNameByTaskId")
	@ResponseBody
	public List<String> getCollectorNameByTaskId(String taskIds){
		List<String> collectorNameLst = new ArrayList<String>();
		if (taskIds != "") {
			if (taskIds.contains(",") == true) {
				String[] taskIdLst = taskIds.split(",");
				for (int i = 0; i < taskIdLst.length; i++) {
					String collectorName = trapTaskMapper.getTaskInfoByTaskID(
							Integer.parseInt(taskIdLst[i].split(":")[0]))
							.getCollectorName();
					collectorNameLst.add(collectorName + ":"
							+ taskIdLst[i].split(":")[1]);
				}
			} 
			
			//只有一个任务
			else {
				String collectorName = trapTaskMapper.getTaskInfoByTaskID(
						Integer.parseInt(taskIds.split(":")[0]))
						.getCollectorName();
				collectorNameLst.add(collectorName + ":"
						+ taskIds.split(":")[1]);
			}
		}
		return collectorNameLst;
	}
	
	/**
	 * 跳转至trap任务界面
	 * 
	 */
	@RequestMapping("/toSysTrapOfflineTask")
	public ModelAndView toSysTrapOfflineTask(HttpServletRequest result,
			HttpServletResponse response,String navigationBar) {
		
		logger.info("跳转至trap离线任务界面。。。。。。start");
		ModelAndView mv= new ModelAndView("monitor/alarmMgr/traptask/offlineTrapTask_list");
		mv.addObject("navigationBar", navigationBar);
		return mv;
	}
	
	/**
	 * 加载列表页面数据
	 * 
	 */
	@RequestMapping("/listOfflineTrapTasks")
	@ResponseBody
	public Map<String, Object> listOfflineTrapTasks(SysTrapTaskBean trapTaskBean){
		
		logger.info("加载列表页面数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Map<String, Object> result = new HashMap<String, Object>();
		Page<SysTrapTaskBean> page = new Page<SysTrapTaskBean>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("alarmOID", trapTaskBean.getAlarmOID());
		paramMap.put("serverIP", trapTaskBean.getServerIP());
		paramMap.put("collectorName", trapTaskBean.getCollectorName());
		page.setParams(paramMap);
		List<SysTrapTaskBean> taskList;
		try {
			taskList = trapTaskMapper.selectOfflineTrapTasks(page);
			result.put("rows", taskList);
		} catch (Exception e) {
			logger.error("获得trap离线任务异常："+e);		
		}
		int total = page.getTotalRecord();
		result.put("total", total);
		logger.info("加载列表页面数据over");
		return result;
	}
}