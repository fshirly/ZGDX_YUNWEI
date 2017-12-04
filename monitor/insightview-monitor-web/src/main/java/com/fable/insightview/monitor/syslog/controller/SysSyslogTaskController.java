package com.fable.insightview.monitor.syslog.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.monitor.perf.entity.PerfPollTaskBean;
import com.fable.insightview.monitor.syslog.entity.SysSyslogTaskBean;
import com.fable.insightview.monitor.syslog.service.ISysSyslogTaskService;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.page.Page;

/**
 * syslog任务
 * 
 * @author hanl
 * 
 */
@Controller
@RequestMapping("/monitor/syslogTask")
public class SysSyslogTaskController {
	private final Logger logger = LoggerFactory
			.getLogger(SysSyslogTaskController.class);
	@Autowired
	ISysSyslogTaskService syslogTaskService;

	/**
	 * 跳转到syslog任务页面
	 */
	@RequestMapping("/toSyslogTaskList")
	public ModelAndView toSyslogTaskList(String navigationBar) {
		return new ModelAndView("monitor/syslog/syslogTask_list").addObject(
				"navigationBar", navigationBar);
	}

	/**
	 * 加载syslog任务表格
	 * 
	 * @return
	 */
	@RequestMapping("/listSyslogTasks")
	@ResponseBody
	public Map<String, Object> listSyslogTasks(SysSyslogTaskBean bean,
			HttpServletRequest request) {
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<SysSyslogTaskBean> page = new Page<SysSyslogTaskBean>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("taskID", bean.getTaskID());
		paramMap.put("ruleName", bean.getRuleName());
		paramMap.put("collectorName", bean.getCollectorName());
		page.setParams(paramMap);
		Map<String, Object> result = syslogTaskService.getSyslogTasks(page);
		return result;
	}

	/**
	 * 根据任务ID获取操作进度及错误信息
	 * 
	 */
	@RequestMapping("/getProcessByTaskId")
	@ResponseBody
	public Map<String, Object> getProcessByTaskId(String taskIds) {
		return syslogTaskService.getProcessByTaskId(taskIds);
	}

	/**
	 * 重发任务
	 * 
	 */
	@RequestMapping("/resendTask")
	@ResponseBody
	public boolean resendTask(SysSyslogTaskBean bean) {
		logger.info("重发的任务为：" + bean.getTaskID());
		return syslogTaskService.resendTask(bean);
	}
	
	/**
	 * 删除任务
	 */
	@RequestMapping("/delTask")
	@ResponseBody
	public boolean delTask(Integer taskID) {
		logger.info("删除的任务为：" + taskID);
		return syslogTaskService.delTask(taskID);
	}
	
	/**
	 * 批量删除任务
	 */
	@RequestMapping("/delTasks")
	@ResponseBody
	public Map<String, Object> delTasks(HttpServletRequest request) throws Exception {
		String taskIDs = request.getParameter("taskIDs");
		return syslogTaskService.delTasks(taskIDs);
	}
	
	/**
	 * 跳转至查看详情页面
	 */
	@RequestMapping("/toShowSyslogDetail")
	public ModelAndView toShowSyslogDetail(HttpServletRequest request) {
		String taskID = request.getParameter("taskID");
		request.setAttribute("taskID", taskID);
		return new ModelAndView("monitor/syslog/syslogTask_detail");
	}
	
	/**
	 * 查看详情
	 * 
	 */
	@RequestMapping("/initTaskDetail")
	@ResponseBody
	public SysSyslogTaskBean initTaskDetail(SysSyslogTaskBean bean) {
		logger.info("初始化任务详情信息，任务id为："+bean.getTaskID());
		return syslogTaskService.initTaskDetail(+bean.getTaskID());
	}
	
	/**
	 * 批量重发
	 */
	@RequestMapping("/doBatchResend")
	@ResponseBody
	public String doBatchResend(HttpServletRequest request) throws Exception {
		String taskIDs = request.getParameter("taskIDs");
		return syslogTaskService.doBatchResend(taskIDs);
	}
	
	/**
	 * 根据任务ID获取操作状态
	 * 
	 */
	@RequestMapping("/getOperateStatus")
	@ResponseBody
	public Map<String, Object> getOperateStatus(String taskIds) {
		return syslogTaskService.getOperateStatus(taskIds);
	}
	
	/**
	 * 加载syslog离线任务表格
	 * 
	 * @return
	 */
	@RequestMapping("/listOfflineSyslogTasks")
	@ResponseBody
	public Map<String, Object> listOfflineSyslogTasks(SysSyslogTaskBean bean,
			HttpServletRequest request) {
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<SysSyslogTaskBean> page = new Page<SysSyslogTaskBean>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("taskID", bean.getTaskID());
		paramMap.put("ruleName", bean.getRuleName());
		paramMap.put("collectorName", bean.getCollectorName());
		page.setParams(paramMap);
		Map<String, Object> result = syslogTaskService.getSyslogOfflineTasks(page);
		return result;
	}
	
	/**
	 * 跳转到syslog离线任务页面
	 */
	@RequestMapping("/toSyslogOfflineTaskList")
	public ModelAndView toSyslogOfflineTaskList(String navigationBar) {
		return new ModelAndView("monitor/syslog/offlineSyslogTask_list").addObject(
				"navigationBar", navigationBar);
	}
	
}
