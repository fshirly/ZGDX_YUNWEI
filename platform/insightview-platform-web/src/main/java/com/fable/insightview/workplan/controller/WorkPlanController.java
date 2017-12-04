package com.fable.insightview.workplan.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.fable.insightview.platform.workplan.entity.WorkPlan;
import com.fable.insightview.platform.workplan.entity.WorkPlanParams;
import com.fable.insightview.platform.workplan.service.WorkPlanService;

/**
 * 工作计划管理
 * @author chenly
 *
 */
@Controller
@RequestMapping("/workPlan")
public class WorkPlanController {

	@Autowired
	private WorkPlanService workPlanService;
	
	/**
	 * 跳转到工作计划列表页面
	 * @return
	 */
	@RequestMapping("/toWorkPlanList")
	public ModelAndView toWorkPlanList() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("workplan/workPlan_list");
		return mv;
	}
	
	/**
	 * 加载工作计划列表信息
	 * @return
	 */
	@RequestMapping("/loadWorkPlanList")
	@ResponseBody
	public Map<String, Object> loadWorkPlanList(WorkPlanParams workPlanParams, Integer page, Integer rows) {
		//获取当前登录用户信息
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		SecurityUserInfoBean sysUser = (SecurityUserInfoBean) request.getSession().getAttribute("sysUserInfoBeanOfSession");
		Integer userId = sysUser.getId().intValue();
		Page<WorkPlan> pages = new Page<WorkPlan>();
		pages.setPageNo(page);
		pages.setPageSize(rows);
		Map<String, Object> params = pages.getParams();
		params.put("creater", userId);
		params.put("title", workPlanParams.getTitle());
		params.put("planStart1", workPlanParams.getPlanStart1());
		params.put("planStart2", workPlanParams.getPlanStart2());
		params.put("planType", workPlanParams.getPlanType());
		params.put("planEnd1", workPlanParams.getPlanEnd1());
		params.put("planEnd2", workPlanParams.getPlanEnd2());
		
		List<WorkPlan> list = workPlanService.findWorkPlans(pages);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", pages.getTotalRecord());
		result.put("rows", list);
		return result;
	}
	
	/**
	 * 跳转到显示工作计划详情页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/toShowWorkPlan")
	public ModelAndView toShowWorkPlan(Integer id) {
		ModelAndView mv = new ModelAndView();
		//准备回显的数据workPlan
		WorkPlan workPlan = workPlanService.findWorkPlanById(id);
		mv.addObject("workPlan", workPlan);
		mv.setViewName("workplan/workPlan_detail");
		return mv;
	}
	
	/**
	 * 跳转到添加工作计划页面
	 * @return
	 */
	@RequestMapping("/toAddWorkPlan")
	public ModelAndView toAddWorkPlan() {
		//获取当前登录用户信息
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		SecurityUserInfoBean sysUser = (SecurityUserInfoBean) request.getSession().getAttribute("sysUserInfoBeanOfSession");
		String username = sysUser.getUserName();
		WorkPlan workPlan = new WorkPlan();
		workPlan.setCreaterName(username);
		ModelAndView mv = new ModelAndView();
		mv.addObject("workPlan", workPlan);
		mv.setViewName("workplan/workPlan_add");
		return mv;
	}
	
	/**
	 * 添加工作计划
	 * @return
	 */
	@RequestMapping("/addWorkPlan")
	@ResponseBody
	public String addWorkPlan(WorkPlan workPlan) {
		//获取当前登录用户信息
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		SecurityUserInfoBean sysUser = (SecurityUserInfoBean) request.getSession().getAttribute("sysUserInfoBeanOfSession");
		Integer creater = sysUser.getId().intValue();
		workPlan.setCreater(creater);
		workPlanService.addWorkPlan(workPlan);
		return "success";
	}
	
	/**
	 * 跳转到修改工作计划页面
	 * @return
	 */
	@RequestMapping("/toEditWorkPlan")
	public ModelAndView toEditWorkPlan(Integer id) {
		ModelAndView mv = new ModelAndView();
		//准备回显的数据workPlan
		WorkPlan workPlan = workPlanService.findWorkPlanById(id);
		mv.addObject("workPlan", workPlan);
		mv.setViewName("workplan/workPlan_add");
		return mv;
	}
	
	/**
	 * 修改工作计划
	 * @return
	 */
	@RequestMapping("/editWorkPlan")
	@ResponseBody
	public String editWorkPlan(WorkPlan workPlan) {
		boolean flag = workPlanService.editWorkPlan(workPlan);
		if(flag) {
			return "success";
		}
		return "failure";
	}
	
	/**
	 * 删除工作计划
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteWorkPlan")
	@ResponseBody
	public String deleteWorkPlan(Integer id) {
		boolean flag = workPlanService.deleteWorkPlanById(id);
		if(flag) {
			return "success";
		}
		return "failure";
	}
	
	/**
	 * 批量删除工作计划
	 * @param ids
	 * @return
	 */
	@RequestMapping("/deleteWorkPlans")
	@ResponseBody
	public String deleteWorkPlans(String ids) {
		String[] idsArr = ids.split(",");
		for(String id : idsArr) {
			this.deleteWorkPlan(Integer.valueOf(id));
		}
		return "success";
	}
	
	/**
	 * 跳转到日历视图页面
	 * @return
	 */
	@RequestMapping("/toCalendar")
	public ModelAndView toCalendar() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("workplan/workPlan_calendar");
		return mv;
	}
	
	/**
	 * 加载所有的工作计划的事件
	 * @return
	 */
	@RequestMapping("/loadEvents")
	@ResponseBody
	public List<Map<String, Object>> loadEvents() {
		//获取当前登录用户信息
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		SecurityUserInfoBean sysUser = (SecurityUserInfoBean) request.getSession().getAttribute("sysUserInfoBeanOfSession");
		Integer userId = sysUser.getId().intValue();
		
		List<WorkPlan> list = workPlanService.findWorkPlansByUserId(userId);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		Map<String, Object> map = null;
		for(WorkPlan workPlan : list) {
			map = new HashMap<String, Object>();
			map.put("id", workPlan.getId());
			String title = workPlan.getTitle();
			if(title.length() < 11) {
				map.put("title", workPlan.getTitle());
			} else {
				map.put("title", "<span title=\""+title+"\">"+title.substring(0, 10)+"...</span>");
			}
			map.put("start", sdf.format(workPlan.getPlanStart()));
			calendar.setTime(workPlan.getPlanEnd());
			calendar.add(Calendar.DATE, 1);
			map.put("end", sdf.format(calendar.getTime()));
			map.put("className", "event_style event_"+workPlan.getId());
			result.add(map);
		}
		return result;
	}
	
	/**
	 * 跳转到日历视图中工作计划详情页面
	 * @return
	 */
	@RequestMapping("/toShowCalWorkPlan")
	public ModelAndView toShowCalWorkPlan(Integer id) {
		ModelAndView mv = new ModelAndView();
		//准备回显的数据workPlan
		WorkPlan workPlan = workPlanService.findWorkPlanById(id);
		mv.addObject("workPlan", workPlan);
		mv.setViewName("workplan/workPlan_calDetail");
		return mv;
	}
	
	/**
	 * 跳转到日历视图中工作计划编辑页面
	 * @return
	 */
	@RequestMapping("/toEditCalWorkPlan")
	public ModelAndView toEditCalWorkPlan(Integer id) {
		ModelAndView mv = new ModelAndView();
		//准备回显的数据workPlan
		WorkPlan workPlan = workPlanService.findWorkPlanById(id);
		mv.addObject("workPlan", workPlan);
		mv.setViewName("workplan/workPlan_calAdd");
		return mv;
	}
	
	/**
	 * 跳转到日历视图中工作计划添加页面
	 * @return
	 */
	@RequestMapping("/toAddCalWorkPlan")
	public ModelAndView toAddCalWorkPlan(Integer id) {
		//获取当前登录用户信息
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		SecurityUserInfoBean sysUser = (SecurityUserInfoBean) request.getSession().getAttribute("sysUserInfoBeanOfSession");
		String username = sysUser.getUserName();
		WorkPlan workPlan = new WorkPlan();
		workPlan.setCreaterName(username);
		ModelAndView mv = new ModelAndView();
		mv.addObject("workPlan", workPlan);
		mv.setViewName("workplan/workPlan_calAdd");
		return mv;
	}
}



