package com.fable.insightview.notices.controller;

import java.util.ArrayList;
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

import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.notices.entity.NoticePersonsBean;
import com.fable.insightview.platform.notices.service.INoticePersonsService;
import com.fable.insightview.platform.page.Page;

@Controller
@RequestMapping("/platform/noticePerson")
public class NoticePersonsController {

	@Autowired
	private INoticePersonsService personService;

	private final Logger LOGGER = LoggerFactory.getLogger(NoticePersonsController.class);

	/**
	 * 跳转列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/toList")
	public ModelAndView toList() {
		return new ModelAndView("/notices/noticePersons_list");
	}

	/**
	 * 确认信息提醒页面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/toConfirm")
	public ModelAndView toConfirm(int id) {
		ModelAndView mv = new ModelAndView("/notices/noticePersons_confirm");
		try {
			mv.addObject("info", personService.querySingle(id));
		} catch (Exception e) {
			LOGGER.error("消息提醒：跳转到提醒确认页面异常,{}", e);
		}
		return mv;
	}

	/**
	 * 确认消息提醒
	 * 
	 * @param noticePersons
	 * @return
	 */
	@RequestMapping("/confirm")
	@ResponseBody
	public boolean confirm(NoticePersonsBean noticePersons) {
		try {
			noticePersons.setStatus(2);/* 确认提醒信息的状态 */
			personService.update(noticePersons);
		} catch (Exception e) {
			LOGGER.error("消息提醒：提醒确认信息保存异常,{}", e);
			return false;
		}
		return true;
	}

	/**
	 * 查看信息提醒页面
	 * 
	 * @param id
	 */
	@RequestMapping("/toView")
	public ModelAndView toView(int id) {
		ModelAndView mv = new ModelAndView("/notices/noticePersons_detial");
		try {
			mv.addObject("info", personService.querySingle(id));
		} catch (Exception e) {
			LOGGER.error("消息提醒：跳转到提醒详细页面异常,{}", e);
		}
		return mv;
	}

	/**
	 * 列表搜索
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request) {
		Page<NoticePersonsBean> page = new Page<NoticePersonsBean>();
		Map<String, Object> params = new HashMap<String, Object>();
		SecurityUserInfoBean sysUser = (SecurityUserInfoBean) request.getSession().getAttribute("sysUserInfoBeanOfSession");/* 当前登陆用户 */
		page.setPageNo(Integer.parseInt(request.getParameter("page")));
		page.setPageSize(Integer.parseInt(request.getParameter("rows")));
		params.put("title", request.getParameter("title"));
		params.put("status", request.getParameter("status"));
		params.put("creator", request.getParameter("creator"));
		params.put("currentUserId", sysUser.getId());
		params.put("createBegin", request.getParameter("createBegin"));
		params.put("createEnd", request.getParameter("createEnd"));
		params.put("confirmBegin", request.getParameter("confirmBegin"));
		params.put("confirmEnd", request.getParameter("confirmEnd"));
		page.setParams(params);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<NoticePersonsBean> persons = personService.list(page);
			result.put("total", page.getTotalRecord());
			result.put("rows", persons);
		} catch (Exception e) {
			result.put("total", 0);
			result.put("rows", new ArrayList<Map<String, Object>>());
			LOGGER.error("消息提醒：查询接收人员信息列表异常：{}", e);
		}
		return result;
	}

	/**
	 * 列表搜索
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryPersons")
	@ResponseBody
	public Map<String, Object> queryPersonsInfo(HttpServletRequest request) {
		Page<NoticePersonsBean> page = new Page<NoticePersonsBean>();
		Map<String, Object> params = new HashMap<String, Object>();
		page.setPageNo(Integer.parseInt(request.getParameter("page")));
		page.setPageSize(Integer.parseInt(request.getParameter("rows")));
		params.put("noticeId", request.getParameter("noticeId"));
		page.setParams(params);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<NoticePersonsBean> persons = personService.queryMultis(page);
			result.put("total", page.getTotalRecord());
			result.put("rows", persons);
		} catch (Exception e) {
			result.put("total", 0);
			result.put("rows", new ArrayList<Map<String, Object>>());
			LOGGER.error("消息提醒：查询接收人员信息列表异常：{}", e);
		}
		return result;
	}

}
