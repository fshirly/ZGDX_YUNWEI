package com.fable.insightview.monitor.website.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.monitor.website.entity.SysSiteCommunityBean;
import com.fable.insightview.monitor.website.service.ISysSiteCommunityService;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.page.Page;

@Controller
@RequestMapping("/monitor/siteCommunity")
public class SiteCommunityController {
	private Logger logger = LoggerFactory.getLogger(SiteCommunityController.class);
	
	@Autowired
	ISysSiteCommunityService siteCommunityService;
	
	/**
	 * 跳转至站点凭证列表页面
	 */
	@RequestMapping("/toSiteCommunityList")
	@ResponseBody
	public ModelAndView toSelfCheckRegistList(String navigationBar) {
		ModelAndView mv = new ModelAndView("monitor/sitecommunity/siteCommunity_list");
		mv.addObject("navigationBar", navigationBar);
		return mv ;
 
	}
	
	/**
	 * 加载站点凭证列表
	 */
	@RequestMapping("/listSiteCommunity")
	@ResponseBody
	public Map<String, Object> listSelfCheck(SysSiteCommunityBean siteCommunityBean){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<SysSiteCommunityBean> page = new Page<SysSiteCommunityBean>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ipAddress", siteCommunityBean.getIpAddress());
		paramMap.put("siteType", siteCommunityBean.getSiteType());
		page.setParams(paramMap);
		
		List<SysSiteCommunityBean> communityList = siteCommunityService.getCommunityByConditions(page);
		int total = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		logger.info("total=====" + total);
		result.put("total", total);
		result.put("rows", communityList);
		return result;
	}
	
	/**
	 * 跳转至站点凭证新增页面
	 * @return
	 */
	@RequestMapping("/toShowAdd")
	@ResponseBody
	public ModelAndView toShowAdd() {
		return new ModelAndView("monitor/sitecommunity/siteCommunity_add");
	}
	
	/**
	 * 校验站点凭证是否存在
	 * @param siteCommunityBean
	 * @return true:不存在 ;false：已存在
	 */
	@RequestMapping("/checkCommunity")
	@ResponseBody
	public boolean checkCommunity(SysSiteCommunityBean siteCommunityBean){
		return siteCommunityService.checkCommunity(siteCommunityBean);
	}
	
	/**
	 * 新增站点凭证
	 */
	@RequestMapping("/addCommunity")
	@ResponseBody
	public boolean addCommunity(SysSiteCommunityBean siteCommunityBean){
		return siteCommunityService.addCommunity(siteCommunityBean);
	}
	
	/**
	 * 跳转至站点凭证编辑页面
	 * @return
	 */
	@RequestMapping("/toShowModify")
	@ResponseBody
	public ModelAndView toShowModify(ModelMap map,HttpServletRequest request) {
		String id = request.getParameter("id");
		map.put("id", id);
		return new ModelAndView("monitor/sitecommunity/siteCommunity_modify");
	}
	
	/**
	 * 初始化凭证信息
	 */
	@RequestMapping("/initSiteCommunity")
	@ResponseBody
	public SysSiteCommunityBean initSiteCommunity(int id){
		return siteCommunityService.initSiteCommunity(id);
	}
	
	@RequestMapping("/updateCommunity")
	@ResponseBody
	public boolean updateCommunity(SysSiteCommunityBean siteCommunityBean){
		return siteCommunityService.updateCommunity(siteCommunityBean);
	}
	
	/**
	 * 跳转至站点凭证编辑页面
	 * @return
	 */
	@RequestMapping("/toShowDetail")
	@ResponseBody
	public ModelAndView toShowDetail(ModelMap map,HttpServletRequest request) {
		String id = request.getParameter("id");
		map.put("id", id);
		return new ModelAndView("monitor/sitecommunity/siteCommunity_detail");
	}
	
	/**
	 * 删除站点凭证
	 */
	@RequestMapping("/delSiteCommunity")
	@ResponseBody
	public boolean delSiteCommunity(int id){
		logger.info("删除站点凭证，ID为："+id);
		return siteCommunityService.delById(id);
	}
	
	/**
	 * 删除站点凭证
	 */
	@RequestMapping("/delSiteCommunitys")
	@ResponseBody
	public boolean delSiteCommunitys(HttpServletRequest request){
		String ids = request.getParameter("ids");
		logger.info("批量删除站点凭证，ID为："+ids);
		return siteCommunityService.delByIds(ids);
	}
}
