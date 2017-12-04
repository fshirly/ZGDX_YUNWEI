package com.fable.insightview.notices.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.notices.entity.NotificationsBean;
import com.fable.insightview.platform.notices.service.INotificationsService;
import com.fable.insightview.platform.page.Page;

@Controller
@RequestMapping("/platform/notification")
public class NotificationsController {

	@Autowired
	private INotificationsService notifiService;

	private final Logger LOGGER = LoggerFactory.getLogger(NotificationsController.class);

	/**
	 * 跳转到日常事务管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/todailyRoutine")
	public ModelAndView todailyRoutine() {
		return new ModelAndView("/notices/daily_routine");
	}

	/**
	 * 跳转到消息提醒管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/toNotifications")
	public ModelAndView toNotifications() {
		return new ModelAndView("/notices/notifications");
	}

	/**
	 * 跳转到消息提醒列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/toList")
	public ModelAndView toList() {
		return new ModelAndView("/notices/notifications_list");
	}

	/**
	 * 跳转到新增页面
	 * 
	 * @return
	 */
	@RequestMapping("/toAdd")
	public ModelAndView toAdd() {
		ModelAndView mv = new ModelAndView("/notices/notifications_add");
		mv.addObject("CreateTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		return mv;
	}

	/**
	 * 跳转到新增页面
	 * 
	 * @return
	 */
	@RequestMapping("/toEdit")
	public ModelAndView toEdit(int notifiId) {
		ModelAndView mv = new ModelAndView("/notices/notifications_add");
		Map<String, String> info = notifiService.querySingle(notifiId);
		mv.addObject("info", info);
		mv.addObject("CreateTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(info.get("CreateTime")));
		return mv;
	}

	/**
	 * 跳转到查看页面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/toView")
	public ModelAndView toView(int notifiId) {
		ModelAndView mv = new ModelAndView("/notices/notifications_detial");
		Map<String, String> info = notifiService.querySingle(notifiId);
		mv.addObject("info", info);
		mv.addObject("CreateTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(info.get("CreateTime")));
		return mv;
	}

	/**
	 * 修改消息提醒信息
	 * 
	 * @param notification
	 * @return
	 */
	@RequestMapping("/insert")
	@ResponseBody
	public boolean insert(NotificationsBean notification, HttpServletRequest request) {
		try {
			SecurityUserInfoBean sysUser = (SecurityUserInfoBean) request.getSession().getAttribute("sysUserInfoBeanOfSession");/* 当前登陆用户 */
			notification.setCreator(sysUser.getId().intValue());
			notifiService.insert(notification);
		} catch (Exception e) {
			LOGGER.error("消息提醒：提醒创建异常,{}", e);
			return false;
		}
		return true;
	}

	/**
	 * 修改消息提醒信息
	 * 
	 * @param notification
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public boolean update(NotificationsBean notification) {
		try {
			notifiService.update(notification);
		} catch (Exception e) {
			LOGGER.error("消息提醒：编辑提醒异常,{}", e);
			return false;
		}
		return true;
	}

	/**
	 * 删除消息提醒
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public boolean delete(String ids) {
		try {
			notifiService.deleteMulti(ids);
		} catch (Exception e) {
			LOGGER.error("消息提醒：删除消息提醒异常, {}", e);
			return false;
		}
		return true;
	}

	/**
	 * 跳转到配置接收人员页面
	 * 
	 * @param userIds
	 * @param userNams
	 * @return
	 */
	@RequestMapping("/toUser")
	public ModelAndView toUser(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/notices/configerUser");
		String userInfo = request.getParameter("userInfo");
		SecurityUserInfoBean sysUser = (SecurityUserInfoBean) request.getSession().getAttribute("sysUserInfoBeanOfSession");/* 当前登陆用户 */
		mv.addObject("orgId", notifiService.getOrgId(sysUser.getId().intValue()));
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", sysUser.getId() + "");
		if (StringUtils.isNotEmpty(userInfo)) {
			Map<String, String> userSl = new HashMap<String, String>();
			String[] users = userInfo.split(",");
			String[] userA = null;
			String userIds = null;
			for (String user : users) {
				userA = user.split(":");
				userSl.put(userA[0], userA[1]);
				if (StringUtils.isEmpty(userIds)) {
					userIds = userA[0];
				} else {
					userIds += "," + userA[0];
				}
			}
			params.put("userIds", userIds);
			mv.addObject("userSl", userSl);/* 已选择的人员 */
		}
		mv.addObject("users", notifiService.queryColleagues(params));/* 未选中的人员 */
		return mv;
	}

	/**
	 * 查询消息提醒列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request) {
		Page<NotificationsBean> page = new Page<NotificationsBean>();
		Map<String, Object> params = new HashMap<String, Object>();
		SecurityUserInfoBean sysUser = (SecurityUserInfoBean) request.getSession().getAttribute("sysUserInfoBeanOfSession");/* 当前登陆用户 */
		page.setPageNo(Integer.parseInt(request.getParameter("page")));
		page.setPageSize(Integer.parseInt(request.getParameter("rows")));
		params.put("title", request.getParameter("title"));
		params.put("currentUserId", sysUser.getId());
		String recipient = request.getParameter("recipient");
		params.put("recipient", recipient == null ? -1 : recipient);
		params.put("createBegin", request.getParameter("createBegin"));
		params.put("createEnd", request.getParameter("createEnd"));
		page.setParams(params);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<NotificationsBean> persons = notifiService.list(page);
			result.put("total", page.getTotalRecord());
			result.put("rows", persons);
		} catch (Exception e) {
			result.put("total", 0);
			result.put("rows", new ArrayList<Map<String, Object>>());
			LOGGER.error("消息提醒：查询创建消息提醒信息列表异常：{}", e);
		}
		return result;
	}

	/**
	 * 查询当前用户单位人员信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/users")
	@ResponseBody
	public List<Map<String, String>> queryColleague(HttpServletRequest request) {
		try {
			Map<String, String> params = new HashMap<String, String>();
			SecurityUserInfoBean sysUser = (SecurityUserInfoBean) request.getSession().getAttribute("sysUserInfoBeanOfSession");/* 当前登陆用户 */
			params.put("userId", sysUser.getId() + "");
			params.put("deptId", request.getParameter("deptId"));
			params.put("name", request.getParameter("userName"));
			params.put("userIds", request.getParameter("userIds"));
			return notifiService.queryColleagues(params);
		} catch (Exception e) {
			LOGGER.error("消息提醒：查询单位用户异常,{}", e);
			return new ArrayList<Map<String, String>>();
		}
	}
}
