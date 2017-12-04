package com.fable.insightview.monitor.TerminalIllegalEvent.controller;

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

import com.fable.insightview.monitor.TerminalIllegalEvent.entity.SyslogClientIllegalEventBean;
import com.fable.insightview.monitor.TerminalIllegalEvent.entity.SyslogPorxyAccessLogBean;
import com.fable.insightview.monitor.TerminalIllegalEvent.service.ISyslogClientIllegalEventService;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.page.Page;

/**
 * 终端违规事件控制层
 * @author zhaods
 *
 */
@Controller
@RequestMapping("/monitor/illegalEvnet")
public class SyslogClientIllegalEventController {

	@Autowired
	private ISyslogClientIllegalEventService syslogClientIllegalEventService;
	
	private static final Logger logger = LoggerFactory.getLogger(SyslogClientIllegalEventController.class);
	
	/**
	 * 跳转到终端违规事件查询界面
	 * @param request
	 * @param navigationBar
	 * @return
	 */
	@RequestMapping("/toIllegalEventView")
	public ModelAndView toIllegalEventView(HttpServletRequest request, String navigationBar){
		logger.debug("进入终端违规事件查询界面");
		ModelAndView mv = new ModelAndView();
		mv.addObject("navigationBar", navigationBar);
		mv.setViewName("monitor/TerminalIllegalEvent/SyslogClientIllegalEvent_list");
		return mv;
	}
	/**
	 * 调整到用户访问事件查询界面
	 */
	@RequestMapping("/toProxyAccessView")
	public ModelAndView toqueryProxyAccessListView(HttpServletRequest request, String navigationBar){
		logger.debug("进入用户访问路径查询界面");
		ModelAndView mv = new ModelAndView();
		mv.addObject("navigationBar", navigationBar);
		mv.setViewName("monitor/TerminalIllegalEvent/ProxyAccessList");
		return mv;
	}
	/**
	 * 分页查询用户访问事件列表
	 * @param SyslogClientIllegalEventBean
	 * @return
	 */
	@RequestMapping("/queryProxyAccessList")
	@ResponseBody
	public Map<String,Object> queryProxyAccessList(SyslogPorxyAccessLogBean syslog){
		logger.debug("开始分页查询用户访问事件列表");
		Map<String,Object> result = new HashMap<String,Object>();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		//分页
		Page<SyslogPorxyAccessLogBean> page = new Page<SyslogPorxyAccessLogBean>();
		Map<String,Object> param = new HashMap<String,Object>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		param.put("userName", syslog.getUserName());
		param.put("identity", syslog.getIdentity());
		param.put("orgname", syslog.getOrgname());
		param.put("firstTime", syslog.getFirstTime());
		param.put("lastTime", syslog.getLastTime());
		//设置分页查询参数
		page.setParams(param);
		List<SyslogPorxyAccessLogBean> illegalEventList = this.syslogClientIllegalEventService.queryProxyAccessList(page);
		int total = page.getTotalRecord();
		result.put("total", total);
		result.put("rows", illegalEventList);
		return result;
	}
	/**
	 * 分页查询终端违规事件列表
	 * @param SyslogClientIllegalEventBean
	 * @return
	 */
	@RequestMapping("/queryIllegalEventList")
	@ResponseBody
	public Map<String,Object> queryIllegalEventList(SyslogClientIllegalEventBean syslog){
		logger.debug("开始分页查询终端违规事件列表");
		Map<String,Object> result = new HashMap<String,Object>();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		//分页
		Page<SyslogClientIllegalEventBean> page = new Page<SyslogClientIllegalEventBean>();
		Map<String,Object> param = new HashMap<String,Object>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		param.put("userName", syslog.getUserName());
		param.put("clientNo", syslog.getClientNo());
		param.put("typeId", syslog.getTypeId());
		param.put("firstTime", syslog.getFirstTime());
		param.put("lastTime", syslog.getLastTime());
		//设置分页查询参数
		page.setParams(param);
		List<SyslogClientIllegalEventBean> illegalEventList = this.syslogClientIllegalEventService.queryIllegalEventList(page);
		int total = page.getTotalRecord();
		result.put("total", total);
		result.put("rows", illegalEventList);
		return result;
	}
	
}
