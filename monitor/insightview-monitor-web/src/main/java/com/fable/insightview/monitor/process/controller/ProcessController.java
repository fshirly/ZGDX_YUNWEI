package com.fable.insightview.monitor.process.controller;

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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.monitor.process.entity.MoProcessBean;
import com.fable.insightview.monitor.process.entity.ProcessKPINameDef;
import com.fable.insightview.monitor.process.service.IProcessInfoService;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.page.Page;

@Controller
@RequestMapping("/monitor/process")
public class ProcessController {
	
	@Autowired IProcessInfoService processInfoService;
	
	private final Logger logger = LoggerFactory.getLogger(ProcessController.class);
	
	@RequestMapping("/toProcessInfo")
	public ModelAndView toProcessInfo(HttpServletRequest request,ModelMap map) {
		String flag = request.getParameter("flag");
		map.put("flag",flag);
		map.put("navigationBar",request.getParameter("navigationBar"));
		return new ModelAndView("monitor/process/processInfo_list");
	}
	
	/**
	 * 加载列表页面数据
	 */
	@RequestMapping("/listProcessInfo")
	@ResponseBody
	public Map<String, Object> listProcessInfo(MoProcessBean moProcessBean)
			throws Exception {
		logger.info("加载列表页面数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MoProcessBean> page = new Page<MoProcessBean>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("deviceIp", request.getParameter("deviceIp"));
		paramMap.put("processName", request.getParameter("processName"));
		paramMap.put("processStateKpi", ProcessKPINameDef.PROCESSSTATE);
		paramMap.put("processCpuKpi", ProcessKPINameDef.PROCESSCPU);
		paramMap.put("processMemKpi", ProcessKPINameDef.PROCESSMEM);
		page.setParams(paramMap);
		List<MoProcessBean> taskList = processInfoService.getMoProcessInfos(page);
		int total = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", taskList);
		logger.info("加载列表页面数据over");
		return result;
	}
}
