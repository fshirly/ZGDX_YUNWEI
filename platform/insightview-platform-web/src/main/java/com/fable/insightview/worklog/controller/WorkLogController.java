package com.fable.insightview.worklog.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.fable.insightview.platform.worklog.entity.WorkLog;
import com.fable.insightview.platform.worklog.entity.WorkLogParams;
import com.fable.insightview.platform.worklog.service.WorkLogService;
import com.fable.insightview.platform.workplan.entity.WorkPlan;

/**
 * 工作日志管理
 * @author chenly
 *
 */
@Controller
@RequestMapping("/workLog")
public class WorkLogController {
	
	@Autowired
	private WorkLogService workLogService;

	/**
	 * 跳转到工作日志列表页面
	 * @return
	 */
	@RequestMapping("/toWorkLogList")
	public ModelAndView toWorkLogList() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("worklog/workLog_list");
		return mv;
	}
	
	/**
	 * 加载工作日志列表信息
	 * @return
	 */
	@RequestMapping("/loadWorkLogList")
	@ResponseBody
	public Map<String, Object> loadWorkLogList(WorkLogParams workLogParams, Integer page, Integer rows) {
		//获取当前登录用户信息
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		SecurityUserInfoBean sysUser = (SecurityUserInfoBean) request.getSession().getAttribute("sysUserInfoBeanOfSession");
		Integer userId = sysUser.getId().intValue();
		Page<WorkLog> pages = new Page<WorkLog>();
		pages.setPageNo(page);
		pages.setPageSize(rows);
		Map<String, Object> params = pages.getParams();
		params.put("creater", userId);
		params.put("title", workLogParams.getTitle());
		params.put("logStart1", workLogParams.getLogStart1());
		params.put("logStart2", workLogParams.getLogStart2());
		params.put("starLevel", workLogParams.getStarLevel());
		params.put("logEnd1", workLogParams.getLogEnd1());
		params.put("logEnd2", workLogParams.getLogEnd2());
		
		List<WorkLog> list = workLogService.findWorkLogs(pages);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", pages.getTotalRecord());
		result.put("rows", list);
		return result;
	}
	
	/**
	 * 跳转到显示工作日志详情页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/toShowWorkLog")
	public ModelAndView toShowWorkLog(Integer id) {
		ModelAndView mv = new ModelAndView();
		//准备回显的数据workLog
		WorkLog workLog = workLogService.findWorkLogById(id);
		mv.addObject("workLog", workLog);
		mv.setViewName("worklog/workLog_detail");
		return mv;
	}
	
	/**
	 * 跳转到添加工作日志页面
	 * @return
	 */
	@RequestMapping("/toAddWorkLog")
	public ModelAndView toAddWorkLog() {
		//获取当前登录用户信息
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		SecurityUserInfoBean sysUser = (SecurityUserInfoBean) request.getSession().getAttribute("sysUserInfoBeanOfSession");
		String username = sysUser.getUserName();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String createTime = sdf.format(new Date());
		WorkLog workLog = new WorkLog();
		//设置默认的主题名
		workLog.setTitle(createTime.substring(0, 10)+"个人工作日志");
		//设置默认的创建人（当前登录用户）
		workLog.setCreaterName(username);
		//设置默认的创建时间
		workLog.setCreateTime(createTime);
		ModelAndView mv = new ModelAndView();
		mv.addObject("workLog", workLog);
		mv.setViewName("worklog/workLog_add");
		return mv;
	}
	
	/**
	 * 添加工作日志
	 * @return
	 */
	@RequestMapping("/addWorkLog")
	@ResponseBody
	public String addWorkLog(WorkLog workLog) {
		//获取当前登录用户信息
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		SecurityUserInfoBean sysUser = (SecurityUserInfoBean) request.getSession().getAttribute("sysUserInfoBeanOfSession");
		Integer creater = sysUser.getId().intValue();
		workLog.setCreater(creater);
		workLogService.addWorkLog(workLog);
		return "success";
	}
	
	/**
	 * 跳转到修改工作日志页面
	 * @return
	 */
	@RequestMapping("/toEditWorkLog")
	public ModelAndView toEditWorkLog(Integer id) {
		ModelAndView mv = new ModelAndView();
		//准备回显的数据workLog
		WorkLog workLog = workLogService.findWorkLogById(id);
		mv.addObject("workLog", workLog);
		mv.setViewName("worklog/workLog_add");
		return mv;
	}
	
	/**
	 * 修改工作日志
	 * @return
	 */
	@RequestMapping("/editWorkLog")
	@ResponseBody
	public String editWorkLog(WorkLog workLog) {
		boolean flag = workLogService.editWorkLog(workLog);
		if(flag) {
			return "success";
		}
		return "failure";
	}
	
	/**
	 * 删除工作日志
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteWorkLog")
	@ResponseBody
	public String deleteWorkLog(Integer id) {
		boolean flag = workLogService.deleteWorkLogById(id);
		if(flag) {
			return "success";
		}
		return "failure";
	}
	
	/**
	 * 批量删除工作日志
	 * @param ids
	 * @return
	 */
	@RequestMapping("/deleteWorkLogs")
	@ResponseBody
	public String deleteWorkLogs(String ids) {
		String[] idsArr = ids.split(",");
		for(String id : idsArr) {
			this.deleteWorkLog(Integer.valueOf(id));
		}
		return "success";
	}
	
	/**
	 * 跳转到工作日志日历视图页面
	 * @return
	 */
	@RequestMapping("/toCalendar")
	public ModelAndView toCalendar() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("worklog/workLog_calendar");
		return mv;
	}
	
	/**
	 * 跳转到日历视图中的工作日志详情页面
	 * @return
	 */
	@RequestMapping("/toShowCalWorkLog")
	public ModelAndView toShowCalWorkLog(Integer id) {
		ModelAndView mv = new ModelAndView();
		//准备回显的数据workLog
		WorkLog workLog = workLogService.findWorkLogById(id);
		mv.addObject("workLog", workLog);
		mv.setViewName("worklog/workLog_calDetail");
		return mv;
	}
	
	/**
	 * 跳转到日历视图中的工作日志添加页面
	 * @return
	 */
	@RequestMapping("/toAddCalWorkLog")
	public ModelAndView toAddCalWorkLog() {
		//获取当前登录用户信息
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		SecurityUserInfoBean sysUser = (SecurityUserInfoBean) request.getSession().getAttribute("sysUserInfoBeanOfSession");
		String username = sysUser.getUserName();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String createTime = sdf.format(new Date());
		WorkLog workLog = new WorkLog();
		//设置默认的主题名
		workLog.setTitle(createTime.substring(0, 10)+"个人工作日志");
		//设置默认的创建人（当前登录用户）
		workLog.setCreaterName(username);
		//设置默认的创建时间
		workLog.setCreateTime(createTime);
		ModelAndView mv = new ModelAndView();
		mv.addObject("workLog", workLog);
		mv.setViewName("worklog/workLog_calAdd");
		return mv;
	}
	
	/**
	 * 跳转到日历视图中的工作日志编辑页面
	 * @return
	 */
	@RequestMapping("/toEditCalWorkLog")
	public ModelAndView toEditCalWorkLog(Integer id) {
		ModelAndView mv = new ModelAndView();
		//准备回显的数据workLog
		WorkLog workLog = workLogService.findWorkLogById(id);
		mv.addObject("workLog", workLog);
		mv.setViewName("worklog/workLog_calAdd");
		return mv;
	}
	
	/**
	 * 加载工作日志中的所有事件
	 * @return
	 */
	@RequestMapping("/loadEvents")
	@ResponseBody
	public List<Map<String, Object>> loadEvents() {
		//获取当前登录用户信息
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		SecurityUserInfoBean sysUser = (SecurityUserInfoBean) request.getSession().getAttribute("sysUserInfoBeanOfSession");
		Integer userId = sysUser.getId().intValue();
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<WorkLog> list = workLogService.findWorkLogsByUserId(userId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		Map<String, Object> map = null;
		for(WorkLog workLog : list) {
			map = new HashMap<String, Object>();
			map.put("id", workLog.getId());
			map.put("title", workLog.getTitle());
			map.put("start", sdf.format(workLog.getStartTime()));
			calendar.setTime(workLog.getEndTime());
			calendar.add(Calendar.DATE, 1);
			map.put("end", sdf.format(calendar.getTime()));
			map.put("className", "event_style event_"+workLog.getId());
			result.add(map);
		}
		return result;
	}
}
