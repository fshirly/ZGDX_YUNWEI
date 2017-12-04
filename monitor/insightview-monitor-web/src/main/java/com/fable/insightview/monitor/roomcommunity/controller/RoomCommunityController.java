package com.fable.insightview.monitor.roomcommunity.controller;


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

import com.fable.insightview.platform.page.Page;
import com.fable.insightview.monitor.sysroomcommunity.entity.SysRoomCommunityBean;
import com.fable.insightview.monitor.sysroomcommunity.mapper.SysRoomCommunityMapper;
import com.fable.insightview.monitor.sysroomcommunity.service.ISysRoomCommunityService;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;

/**
 * 机房环境监控认证
 * @author zhurt
 */
@Controller
@RequestMapping("/monitor/roomCommunity")
public class RoomCommunityController {
	@Autowired
	ISysRoomCommunityService sysRoomCommunityService;
	
	@Autowired
	SysRoomCommunityMapper  sysRoomCommunityMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(RoomCommunityController.class);
	
	@RequestMapping("/toroomCommunity")
	public ModelAndView toroomCommunity(String navigationBar) {
		logger.info("开始加载认证页面");
		ModelAndView mv =new ModelAndView("monitor/roomCommunity/roomCommunity_list");
		mv.addObject("navigationBar", navigationBar);
		return mv;
	}
	
	/**
	 * 查询列表
	 */
	@RequestMapping("/listRoomCommunity")
	@ResponseBody
	public Map<String,Object> listRoomCommunity(SysRoomCommunityBean sysRoomCommunityBean) throws Exception{
		
		logger.info("查询列表数据。。。");
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
		.getFlexiGridPageInfo(request);
		
		Page<SysRoomCommunityBean> page = new Page<SysRoomCommunityBean>(); 
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		
		paramMap.put("ipAddress", sysRoomCommunityBean.getIpAddress());
		paramMap.put("userName", sysRoomCommunityBean.getUserName());
		paramMap.put("sort", request.getParameter("sort"));
		paramMap.put("order", request.getParameter("order"));
		page.setParams(paramMap);
		
		List<SysRoomCommunityBean> roomCommunityList =  sysRoomCommunityService.getRoomCommunityByConditions(page);
		
		int total = page.getTotalRecord();
		logger.info("*******total:"+total);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("total", total);
		result.put("rows", roomCommunityList);
		return result;
	}
	
	@RequestMapping("/toRoomCommunityAdd")
	public ModelAndView toRoomCommunityAdd(HttpServletRequest request){
		logger.info("跳转到新增页面");
		return new ModelAndView("monitor/roomCommunity/roomCommunity_add");
	}
	
	/**
	 * 唯一性验证
	 */
	@RequestMapping("/checkCommunity")
	@ResponseBody
	public boolean checkCommunity(SysRoomCommunityBean sysRoomCommunityBean) {
		
		logger.info("验证中间件凭证的唯一性......");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		String flag = request.getParameter("flag");
		return sysRoomCommunityService.checkCommunity(flag, sysRoomCommunityBean);
	}
	
	/**
	 * 新增认证
	 */
	@RequestMapping("/addRoomCommunity")
	@ResponseBody
	public boolean addRoomCommunity(SysRoomCommunityBean sysRoomCommunityBean){
		return sysRoomCommunityService.insertRoomCommunity(sysRoomCommunityBean);
	}
	
	/**
	 * 打开更新页面
	 */
	@RequestMapping("/toRoomCommunityModify")
	@ResponseBody
	public ModelAndView toRoomCommunityModify(HttpServletRequest request) {
		logger.info("加载更新页面");
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		return new ModelAndView(
				"monitor/roomCommunity/roomCommunity_modify");
	}
	
	/**
	 * 初始化信息
	 */
	@RequestMapping("initCommunityDetail")
	@ResponseBody
	public SysRoomCommunityBean initCommunityDetail(int id) {
		return sysRoomCommunityService.getCommunityByID(id);
	}
	
	/**
	 * 更新
	 */
	@RequestMapping("updateCommunity")
	@ResponseBody
	public boolean updateCommunity(SysRoomCommunityBean sysRoomCommunityBean) {
		return sysRoomCommunityService.updateCommunityByID(sysRoomCommunityBean);
	}
	
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping("/delRoomCommunity")
	@ResponseBody
	public boolean delRoomCommunity(SysRoomCommunityBean sysRoomCommunityBean) {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(sysRoomCommunityBean.getId());
		try {
			sysRoomCommunityService.delRoomCommunity(ids);
			return true;
		} catch (Exception e) {
			logger.error("删除机房环境监控凭证异常：" + e);
			return false;
		}
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping("/delRoomCommunitys")
	@ResponseBody
	public boolean delRoomCommunitys(HttpServletRequest request) {
		
		logger.info("批量删除........start");
		String delIDs = request.getParameter("delIDs");
		String[] splitIds = delIDs.split(",");
		List<Integer> ids = new ArrayList<Integer>();
		for (String strId : splitIds) {
			int id = Integer.parseInt(strId);
			ids.add(id);
		}
		try {
			sysRoomCommunityService.delRoomCommunity(ids);
			return true;
		} catch (Exception e) {
			logger.error("删除机房环境监控凭证异常：" + e);
			return false;
		}
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
				"monitor/roomCommunity/roomCommunity_detail");
	}
}

