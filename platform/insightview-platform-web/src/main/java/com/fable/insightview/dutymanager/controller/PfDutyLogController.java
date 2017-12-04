package com.fable.insightview.dutymanager.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.dutymanager.dutylog.entity.PfDutyLog;
import com.fable.insightview.platform.dutymanager.dutylog.service.IDutyLogService;

@Controller
@RequestMapping("/platform/dutylog")
public class PfDutyLogController {

	@Autowired
	private IDutyLogService dutyLogService;

	private final static Logger LOGGER = LoggerFactory.getLogger(PfDutyLogController.class);

	/**
	 * 跳转到值班记录日志页面
	 * 
	 * @param navigationBar
	 * @return
	 */
	@RequestMapping("/toLog")
	public ModelAndView toLog(String navigationBar) {
		return new ModelAndView("/dutymanager/duty_log").addObject("navigationBar", navigationBar);
	}

	/**
	 * 查询值班日历日志信息
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	@RequestMapping("/queryEvents")
	@ResponseBody
	public List<Map<String, Object>> queryEvents(String start, String end) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		SecurityUserInfoBean sysUser = (SecurityUserInfoBean) request.getSession().getAttribute("sysUserInfoBeanOfSession");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("begin", start);
		params.put("end", end);
		params.put("currentUser", sysUser.getId());
		try {
			return dutyLogService.queryDutyLogs(params);
		} catch (Exception e) {
			LOGGER.error("查询值班记录日志异常,{}", e);
			return new ArrayList<Map<String,Object>>();
		}
	}
	
	/**
	 * 跳转到带班领导登记日志页面
	 * @param logId
	 * @param duty
	 * @return
	 */
	@RequestMapping("/toLeaderLog")
	public ModelAndView toLeaderLog(String duty) {
		ModelAndView mv = new ModelAndView("/dutymanager/duty_leader_log");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("duty", duty);
		params.put("before", getBeforeDay(duty));
		mv.addObject("dutyDate", duty);
		try {
			mv.addObject("log", dutyLogService.queryLeaderInfo(params));
		} catch (Exception e) {
			LOGGER.error("查询带班领导日志异常：{}", e);
		}
		return mv;
	}
	
	/**
	 * 跳转到值班负责人登记日志页面
	 * @param logId
	 * @param duty
	 * @return
	 */
	@RequestMapping("/toDutyerLog")
	public ModelAndView toDutyerLog (String duty, String dutyer) {
		ModelAndView mv = new ModelAndView("/dutymanager/duty_dutyer_log");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("duty", duty);
		params.put("before", getBeforeDay(duty));
		mv.addObject("dutyer", dutyer);
		mv.addObject("dutyDate", duty);
		try {
			mv.addObject("log", dutyLogService.queryDutyerInfo(params));
		} catch (Exception e) {
			LOGGER.error("查询值班负责人日志异常：{}", e);
		}
		return mv;
	}
	
	/**
	 * 新增值班日志信息
	 * @param log
	 * @return
	 */
	@RequestMapping("/handle")
	@ResponseBody
	public boolean handle (PfDutyLog log) {
		try {
			dutyLogService.handle(log);
			return true;
		} catch (Exception e) {
			LOGGER.error("新增(编辑)值班记录日志异常：{}", e);
			return false;
		}
	}
	
	/**
	 * 获取日期的前一天
	 * @param date
	 * @return
	 */
	private String getBeforeDay (String date) {
		try {
			SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
			Date beginDate = dft.parse(date);
			Calendar cal = Calendar.getInstance();
			cal.setTime(beginDate);
			cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
			return dft.format(cal.getTime());
		} catch (ParseException e) {
			LOGGER.error("获取日期的前一天异常：{}", e);
			return null;
		}
	}
}
