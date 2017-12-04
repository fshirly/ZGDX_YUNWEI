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
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.monitor.dispatcher.configuration.AlarmRuleConfig;
import com.fable.insightview.monitor.perf.entity.PerfCollectPeriodBean;
import com.fable.insightview.monitor.perf.service.IPerfGeneralConfigService;
import com.fable.insightview.monitor.perf.service.impl.PerfGeneralConfigServiceImpl;

/**
 * 采集通用配置
 * 
 */
@Controller
@RequestMapping("/monitor/perfGeneralConfig")
public class PerfGeneralConfigController {
	private final Logger logger = LoggerFactory
			.getLogger(PerfGeneralConfigServiceImpl.class);
	@Autowired
	IPerfGeneralConfigService configService;

	/**
	 * 跳转至采集通用配置界面
	 * 
	 * @return
	 */
	@RequestMapping("/toPerfGeneralConfig")
	public ModelAndView toPerfGeneralConfig(String navigationBar) {
		ModelAndView mv= new ModelAndView("monitor/perf/perfGeneralConfig");
		mv.addObject("navigationBar", navigationBar);
		return mv;
	}

	/**
	 * 获得告警设置的初始值
	 */
	@RequestMapping("/initAlarmRuleVal")
	@ResponseBody
	public AlarmRuleConfig initAlarmRuleVal() {

		logger.info("获得告警设置的默认值。。。start");
		AlarmRuleConfig alarmRuleConfig = configService.getAlarmrRuleConfig();
		return alarmRuleConfig;
	}

	/**
	 * 设置告警条件的默认值
	 */
	@RequestMapping("/doSetAlarmRule")
	@ResponseBody
	public boolean doSetAlarmRule(AlarmRuleConfig alarmRuleConfig) {

		logger.info("获得告警设置的默认值。。。start");
		return configService.updateAlarmRuleConfig(alarmRuleConfig);
	}

	/**
	 * 获得对象默认的采集周期
	 */
	@RequestMapping("/initCollectPeriodVal")
	@ResponseBody
	public int initCollectPeriodVal(HttpServletRequest request) {

		logger.info("获得获得对象默认的采集周期。。。start  ");
		String className = request.getParameter("className");
		logger.info("对象类型为。。。  " + className);
		return configService.initCollectPeriodVal(className);
	}

	/**
	 * 设置采集周期默认值
	 */
	@RequestMapping("/doSetCollectPeriod")
	@ResponseBody
	public boolean doSetCollectPeriod(HttpServletRequest request) {
		String className = request.getParameter("className");
		String collectPeriodVal = request.getParameter("collectPeriod");
		int collectPeriod = Integer.parseInt(collectPeriodVal);
		return configService.updateCollectPeriod(className, collectPeriod);
	}

	/**
	 * 获得所有对象类型的采集周期
	 */
	@RequestMapping("/listCollectPeriod")
	@ResponseBody
	public Map<String, Object> listCollectPeriod() {
		List<PerfCollectPeriodBean> collectPeriodLst = configService
				.getAllCollectPeriod();
		int total = 0;
		if (collectPeriodLst != null && collectPeriodLst.size() > 0) {
			total = collectPeriodLst.size();
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", collectPeriodLst);
		return result;
	}

	/**
	 * 打开新增页面
	 */
	@RequestMapping("/toShowCollectPeriodAdd")
	@ResponseBody
	public ModelAndView toShowCollectPeriodAdd(HttpServletRequest request) {

		logger.info("加载新增页面");
		return new ModelAndView("monitor/perf/perfGeneralConfig_add");
	}

	/**
	 * 打开编辑页面
	 */
	@RequestMapping("/toShowCollectPeriodModify")
	@ResponseBody
	public ModelAndView toShowCollectPeriodModify(HttpServletRequest request) {

		logger.info("加载编辑页面");
		// request.setAttribute("className", request.getParameter("className"));
		// request.setAttribute("classLable",
		// request.getParameter("classLable"));
		// request.setAttribute("collectPeriod",
		// request.getParameter("collectPeriod"));
		return new ModelAndView("monitor/perf/perfGeneralConfig_modify");
	}

	/**
	 * 验证该对象类型是否已经配置过
	 */
	@RequestMapping("/isExitSetting")
	@ResponseBody
	public boolean isExitSetting(HttpServletRequest request) {
		String className = request.getParameter("className");
		return configService.isExitSetting(className);
	}
}
