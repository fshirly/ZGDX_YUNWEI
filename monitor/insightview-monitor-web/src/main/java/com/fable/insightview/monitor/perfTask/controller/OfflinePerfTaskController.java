package com.fable.insightview.monitor.perfTask.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.monitor.perf.entity.PerfPollTaskBean;
import com.fable.insightview.monitor.perf.service.IOfflinePerftaskService;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.page.Page;

@Controller
@RequestMapping("/monitor/offlinePerfTask")
public class OfflinePerfTaskController {
	private final Logger logger = LoggerFactory
			.getLogger(PerfTaskController.class);
	@Autowired
	IOfflinePerftaskService offlinePerftaskService;

	/**
	 * 跳转至离线采集任务列表页面
	 */
	@RequestMapping("/toPerfTaskList")
	public ModelAndView toPerfTaskList(String navigationBar,
			HttpServletRequest request) {
		request.setAttribute("navigationBar", navigationBar);
		return new ModelAndView("monitor/perf/offlinePerfTask_list");
	}

	/**
	 * 加载离线采集任务列表数据
	 */
	@RequestMapping("/listOfflinePerfTasks")
	@ResponseBody
	public Map<String, Object> listPerfTasks(PerfPollTaskBean taskBean)
			throws Exception {
		logger.info("加载离线采集任务列表页面数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<PerfPollTaskBean> page = new Page<PerfPollTaskBean>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("deviceIp", taskBean.getDeviceIp());
		paramMap.put("taskId", taskBean.getTaskId());
		paramMap.put("status", taskBean.getStatus());
		paramMap.put("serverName", taskBean.getServerName());
		String fieldName = request.getParameter("sort");
		String sort = "";
		if ("deviceIp".equals(fieldName)) {
			sort = "device.DeviceIP";
		} else if ("status".equals(fieldName)) {
			sort = "task.Status";
		} else if ("lastStatusTime".equals(fieldName)) {
			sort = "task.LastStatusTime";
		} else if ("className".equals(fieldName)) {
			sort = "def.ClassLable";
		} else if ("deviceManufacture".equals(fieldName)) {
			sort = "manu.ResManufacturerName";
		} else if ("deviceType".equals(fieldName)) {
			sort = "rescate.ResCategoryName";
		} else if ("serverName".equals(fieldName)) {
			sort = "host.IPAddress";
		}
		paramMap.put("sort", sort);
		paramMap.put("order", request.getParameter("order"));
		page.setParams(paramMap);
		List<PerfPollTaskBean> taskList = offlinePerftaskService.getOfflinePerfTask(page);
		int total = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", taskList);
		logger.info("加载离线采集任务列表页面数据over");
		return result;
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
		request.setAttribute("taskId", taskId);
		request.setAttribute("serverId", serverId);
		request.setAttribute("serverName", serverName);
		return new ModelAndView("monitor/perf/appointOfflineCollector");
	}

	/**
	 * 打开查看页面
	 */
	@RequestMapping("/toShowPerfTaskDetail")
	public ModelAndView toShowPerfTaskDetail(HttpServletRequest request) {
		String taskId = request.getParameter("taskId");
		logger.info("打开查看id为：{}的离线采集任务任务页面",taskId);
		request.setAttribute("taskId", taskId);
		return new ModelAndView("monitor/perf/offlinePerfTask_detail");
	}
	
	/**
	 * 跳转至采集任务编辑页面
	 */
	@RequestMapping("/toEditOfflinePerfTask")
	public ModelAndView toEditOfflinePerfTask(HttpServletRequest request) {
		String flag = request.getParameter("flag");
		logger.info("跳转至离线采集任务{}页面",flag);
		String taskId = request.getParameter("taskId");
		String serverId = request.getParameter("serverId");
		String serverName = request.getParameter("serverName");
		request.setAttribute("flag", flag);
		request.setAttribute("taskId", taskId);
		request.setAttribute("serverId", serverId);
		request.setAttribute("serverName", serverName);
		return new ModelAndView("monitor/perf/offlinePerfTask_edit");
	}
	
	/**
	 * 获得发现的离线采集机
	 */
	@RequestMapping("/findHostLst")
	@ResponseBody
	public Map<String, Object> findHostLst(){
		logger.info("获得所有发现的离线采集机");
		return offlinePerftaskService.findHostLst();
	}
}
