package com.fable.insightview.monitor.alarmmgr.alarmOriginalEvent.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.monitor.alarmmgr.alarmOriginalEvent.service.IAlarmOriginalEventService;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.page.Page;

@Controller
@RequestMapping("/monitor/alarmOriginalEvent")
public class AlarmOriginalEventController {
	@Autowired
	private IAlarmOriginalEventService orginalService;
	
	@RequestMapping("/toOriginalEventList")
	public ModelAndView toOriginalEventList(String navigationBar){
		return new ModelAndView("monitor/alarmMgr/alarmOriginalEvent/alarmOriginalEvent_list")
		.addObject("navigationBar", navigationBar);
	}
	
	@RequestMapping("/originalEventList")
	@ResponseBody
	public Map<String ,Object> originalEventList(HttpServletRequest request){
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil.getFlexiGridPageInfo(request);
		Page<Map<String ,Object>> page = new Page<Map<String ,Object>>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> params = new HashMap<String, Object>();
		
		request.getSession().removeAttribute("alarmOriginalTimeBegin");
		request.getSession().removeAttribute("alarmOriginalTimeEnd");
		String sourceMoName = request.getParameter("sourceMoName");
		if(!StringUtils.isEmpty(sourceMoName)){
			params.put("sourceMoName", sourceMoName);
		}
		String timeBegin = request.getParameter("timeBegin");
		if(!StringUtils.isEmpty(timeBegin)){
			params.put("timeBegin", timeBegin);
			request.getSession().setAttribute("alarmOriginalTimeBegin", timeBegin);
		}
		String timeEnd = request.getParameter("timeEnd");
		if(!StringUtils.isEmpty(timeEnd)){
			params.put("timeEnd", timeEnd);
			request.getSession().setAttribute("alarmOriginalTimeEnd", timeEnd);
		}
		String moName = request.getParameter("moName");
		if(!StringUtils.isEmpty(moName)){
			params.put("moName", moName);
		}
		String ipAddr = request.getParameter("ipAddr");
		if(!StringUtils.isEmpty(ipAddr)){
			params.put("ipAddr", ipAddr);
		}
		
		page.setParams(params);
		
		Map<String ,Object> result = new HashMap<String ,Object>();
		result.put("rows", orginalService.getOriginalEvent(page));
		result.put("total", page.getTotalRecord());
		
		return result;
	}
	
	@RequestMapping("/deleteOriginalEvent")
	@ResponseBody
	public String deleteOriginalEvent(HttpServletRequest request){
		String result = "";
		
		String eventOids = request.getParameter("eventOids");
		if(StringUtils.isEmpty(eventOids)){
			return "false";
		}
		
		String moids = request.getParameter("moids");
		String sourceMoids = request.getParameter("sourceMoids");
		String timeBegin = (String) request.getSession().getAttribute("alarmOriginalTimeBegin");
		String timeEnd = (String) request.getSession().getAttribute("alarmOriginalTimeEnd");
		
		result = String.valueOf(orginalService.modifyOriginalEvent(eventOids ,timeBegin ,timeEnd ,moids ,sourceMoids));
		return result;
	}
}
