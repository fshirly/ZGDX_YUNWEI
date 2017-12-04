package com.fable.insightview.monitor.middelware.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.monitor.discover.entity.MODeviceObj;
import com.fable.insightview.monitor.discover.mapper.MODeviceMapper;
import com.fable.insightview.monitor.middleware.middlewarecommunity.entity.SysMiddleWareCommunityBean;
import com.fable.insightview.monitor.middleware.middlewarecommunity.service.ISysMiddleWareCommunityService;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;

@Controller
@RequestMapping("/monitor/MiddleWareCommunity")
public class MiddleWareCommunityController {
	@Autowired
	ISysMiddleWareCommunityService middleWareCommunityService;
	@Autowired
	MODeviceMapper moDeviceMapper;
	private final Logger logger = LoggerFactory
			.getLogger(MiddleWareCommunityController.class);
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 跳转至列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/toCommunityList")
	@ResponseBody
	public ModelAndView toCommunityList(String navigationBar) {
		ModelAndView mv =new ModelAndView("monitor/middleware/middlewarecommunity/middleWareCommunity_list");
		mv.addObject("navigationBar", navigationBar);
		return mv;
	}

	/**
	 * 加载中间件数据表格
	 */
	@RequestMapping("/listMiddleWareCommunity")
	@ResponseBody
	public Map<String, Object> listMiddleWareCommunity(
			SysMiddleWareCommunityBean communityBean) {
		logger.info("开始加载中间件数据。。。。。。。。");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<SysMiddleWareCommunityBean> page = new Page<SysMiddleWareCommunityBean>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ipAddress", communityBean.getIpAddress());
		paramMap.put("middleWareType", communityBean.getMiddleWareType());
		paramMap.put("userName", communityBean.getUserName());
		page.setParams(paramMap);
		List<SysMiddleWareCommunityBean> communityList = middleWareCommunityService
				.getMiddleWareCommunityList(page);
		int total = page.getTotalRecord();
		logger.info("total=====" + total);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", communityList);
		logger.info("加载数据结束。。。");
		return result;
	}

	/**
	 * 删除中间件凭证
	 * 
	 * @return
	 */
	@RequestMapping("/delMiddleCommunity")
	@ResponseBody
	public boolean delMiddleCommunity(SysMiddleWareCommunityBean communityBean) {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(communityBean.getId());
		try {
			middleWareCommunityService.delMiddleWareCommunity(ids);
			return true;
		} catch (Exception e) {
			logger.error("删除中间件凭证异常：" + e);
			return false;
		}
	}

	/**
	 * 批量删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delMiddleCommunitys")
	@ResponseBody
	public boolean delMiddleCommunitys(HttpServletRequest request) {
		logger.info("批量删除........start");
		String delIDs = request.getParameter("delIDs");
		String[] splitIds = delIDs.split(",");
		List<Integer> ids = new ArrayList<Integer>();
		for (String strId : splitIds) {
			int id = Integer.parseInt(strId);
			ids.add(id);
		}
		try {
			middleWareCommunityService.delMiddleWareCommunity(ids);
			return true;
		} catch (Exception e) {
			logger.error("删除中间件凭证异常：" + e);
			return false;
		}
	}

	/**
	 * 打开新增页面
	 */
	@RequestMapping("/toMiddleCommunityAdd")
	@ResponseBody
	public ModelAndView toMiddleCommunityAdd(HttpServletRequest request) {
		logger.info("加载新增页面");
		return new ModelAndView(
				"monitor/middleware/middlewarecommunity/middleWareCommunity_add");
	}

	/**
	 * 中间件凭证唯一性的验证
	 */
	@RequestMapping("/checkCommunity")
	@ResponseBody
	public boolean checkCommunity(SysMiddleWareCommunityBean communityBean) {
		logger.info("验证中间件凭证的唯一性......");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		String flag = request.getParameter("flag");
		return middleWareCommunityService.checkCommunity(flag, communityBean);
	}

	/**
	 * 新增中间件凭证
	 * 
	 * @return
	 */
	@RequestMapping("addCommunity")
	@ResponseBody
	public boolean addCommunity(SysMiddleWareCommunityBean communityBean) {
		return middleWareCommunityService
				.insertMiddleWareCommunity(communityBean);
	}

	/**
	 * 打开更新页面
	 */
	@RequestMapping("/toMiddleCommunityModify")
	@ResponseBody
	public ModelAndView toMiddleCommunityModify(HttpServletRequest request) {
		logger.info("加载更新页面");
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		return new ModelAndView(
				"monitor/middleware/middlewarecommunity/middleWareCommunity_modify");
	}

	/**
	 * 初始化中间件凭证信息
	 * 
	 * @param perfKPIDefBean
	 * @return
	 */
	@RequestMapping("initCommunityDetail")
	@ResponseBody
	public SysMiddleWareCommunityBean initCommunityDetail(int id) {
		return middleWareCommunityService.getCommunityByID(id);
	}

	/**
	 * 更新指标
	 * 
	 * @return
	 */
	@RequestMapping("updateCommunity")
	@ResponseBody
	public boolean updateCommunity(SysMiddleWareCommunityBean bean) {
		return middleWareCommunityService.updateCommunityByID(bean);
	}

	/**
	 * 打开查看页面
	 */
	@RequestMapping("/toShowCommunity")
	@ResponseBody
	public ModelAndView toShowCommunity(HttpServletRequest request) {
		logger.info("加载查看页面");
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		return new ModelAndView(
				"monitor/middleware/middlewarecommunity/middleWareCommunity_detail");
	}

	@RequestMapping("/findDeviceInfo")
	@ResponseBody
	public MODeviceObj findDeviceInfo(int moID) {
		MODeviceObj moDevice = moDeviceMapper.selectByPrimaryKey(moID);
		return moDevice;
	}
}
