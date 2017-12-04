package com.fable.insightview.tasklog.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.tasklog.entity.PfTaskLog;
import com.fable.insightview.platform.tasklog.service.TaskLogService;

@Controller
@RequestMapping("/platform/taskLog")
public class TaskLogController {
	
	@Autowired
	private TaskLogService taskLogService;

	/**
	 * 跳转到任务日志列表页面
	 * @return
	 */
	@RequestMapping("/toTaskLogList")
	public ModelAndView toTaskLogList() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("tasklog/tasklog_list");
		return mv;
	}
	
	/**
	 * 加载任务日志列表信息
	 * @return
	 */
	@RequestMapping("/loadTaskLogList")
	@ResponseBody
	public Map<String,Object> loadTaskLogList(String title, Integer status, Date taskTime1, Date taskTime2, Integer page, Integer rows) {
		//获取当前登录用户信息
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		SecurityUserInfoBean sysUser = (SecurityUserInfoBean) request.getSession().getAttribute("sysUserInfoBeanOfSession");
		Integer userId = sysUser.getId().intValue();
		
		Page<PfTaskLog> pages = new Page<PfTaskLog>();
		pages.setPageNo(page);
		pages.setPageSize(rows);
		Map<String, Object> params = pages.getParams();
		params.put("title", title);
		params.put("status", status);
		params.put("taskTime1", taskTime1);
		params.put("taskTime2", taskTime2);
		params.put("userId", userId);
		List<PfTaskLog> list = taskLogService.findTaskLogs(pages);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", pages.getTotalRecord());
		result.put("rows", list);
		return result;
	}
	
	/**
	 * 跳转到显示任务日志详情页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/toShowTaskLog")
	public ModelAndView toShowTaskLog(Integer id, String flag) {
		ModelAndView mv = new ModelAndView();
		PfTaskLog taskLog = taskLogService.findTaskLogById(id);
		mv.addObject("taskLog", taskLog);
		if("t".equals(flag)) {
			mv.setViewName("tasklog/tasklogQuery_detail");
		} else {
			mv.setViewName("tasklog/tasklog_detail");
		}
		return mv;
	}
	
	/**
	 * 跳转到填写任务日志页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/toWriteTaskLog")
	public ModelAndView toWriteTaskLog(Integer id) {
		ModelAndView mv = new ModelAndView();
		PfTaskLog taskLog = taskLogService.findTaskLogById(id);
		//设置填写日期
		taskLog.setWriteTime(new Date());
		mv.addObject("taskLog", taskLog);
		mv.setViewName("tasklog/tasklog_add");
		return mv;
	}
	
	/**
	 * 修改任务日志
	 * @param taskLog
	 * @return
	 */
	@RequestMapping("/editTaskLog")
	@ResponseBody
	public String editTaskLog(PfTaskLog taskLog) {
		boolean flag = taskLogService.editTaskLog(taskLog);
		if(flag) {
			return "success";
		}
		return "failure";
	}
	
	/**
	 * 跳转到任务日志查询列表页面
	 * @return
	 */
	@RequestMapping("/toTaskLogQueryList")
	public ModelAndView toTaskLogQueryList() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("tasklog/tasklogQuery_list");
		return mv;
	}
	
	/**
	 * 加载任务日志查询列表信息
	 * @return
	 */
	@RequestMapping("/loadTaskLogQueryList")
	@ResponseBody
	public Map<String, Object> loadTaskLogQueryList(Date taskTime1, Date taskTime2, Integer status, Integer page, Integer rows) {
		Page<PfTaskLog> pages = new Page<PfTaskLog>();
		pages.setPageNo(page);
		pages.setPageSize(rows);
		Map<String, Object> params = pages.getParams();
		params.put("taskTime1", taskTime1);
		params.put("taskTime2", taskTime2);
		params.put("status", status);
		
		List<PfTaskLog> list = taskLogService.findTaskLogs(pages);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", pages.getTotalRecord());
		result.put("rows", list);
		return result;
	}
}




