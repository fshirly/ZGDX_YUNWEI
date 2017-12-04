package com.fable.insightview.monitor.manageddomainipscope.controller;

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

import com.fable.insightview.monitor.manageddomainipscope.entity.ManagedDomainIPScopeBean;
import com.fable.insightview.monitor.manageddomainipscope.service.IManagedDomainIPScopeService;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.managedDomain.entity.ManagedDomain;
import com.fable.insightview.platform.managedDomain.service.IManagedDomainService;


/**
 * 管理域ip范围
 *
 */

@Controller
@RequestMapping("/monitor/domainipscope")
public class ManagedDomainIPScopeController {
	private Logger logger = LoggerFactory
	.getLogger(ManagedDomainIPScopeController.class);
	
	@Autowired
	IManagedDomainIPScopeService domainIPScopeService;
	@Autowired
	IManagedDomainService managedDomainService;
	
	/**
	 * 跳转至管理域ip范围界面
	 */
	@RequestMapping("/toDomainIPScope")
	public ModelAndView toDomainIPScope(String navigationBar) {
		
		logger.info("进入管理域ip范围界面");
		ModelAndView mv = new ModelAndView("monitor/manageddomainipscope/domainIPScope_list");
		mv.addObject("navigationBar", navigationBar);
		return mv ;
	}

	/**
	 * 加载表格数据
	 */
	@RequestMapping("/listDomainIPScope")
	@ResponseBody
	public Map<String, Object> listThreshold(
			ManagedDomainIPScopeBean managedDomainIPScopeBean) {
		
		logger.debug("开始加载数据。。。。。。。。");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<ManagedDomainIPScopeBean> page = new Page<ManagedDomainIPScopeBean>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("ip1", managedDomainIPScopeBean.getIp1());
		paramMap.put("scopeType", managedDomainIPScopeBean.getScopeType());
		paramMap.put("domainID", managedDomainIPScopeBean.getDomainID());
		paramMap.put("node", managedDomainIPScopeBean.getNode());
		page.setParams(paramMap);
		
		List<ManagedDomainIPScopeBean> ipScopeList = domainIPScopeService.getDomainIpScopeList(page);

		int total = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", ipScopeList);
		logger.info("加载数据结束。。。");
		return result;
	}
	
	/**
	 * 跳转至查看界面
	 */
	@RequestMapping("/toShowDomainIPScopeDetail")
	public ModelAndView toShowDomainIPScopeDetail(HttpServletRequest request) {
		
		logger.info("进入管理域ip范围查看界面");
		String scopeID = request.getParameter("scopeID");
		request.setAttribute("scopeID", scopeID);
		return new ModelAndView(
				"monitor/manageddomainipscope/domainIPScope_detail");
	}
	
	/**
	 * 查看详情
	 */
	@RequestMapping("/initDomainIPScopeDetail")
	@ResponseBody
	public ManagedDomainIPScopeBean initDomainIPScopeDetail(int scopeID){
		return domainIPScopeService.getDomainIpScopeInfo(scopeID);
	}
	
	/**
	 * 跳转至新增界面
	 */
	@RequestMapping("/toShowDomainIPScopeAdd")
	public ModelAndView toShowDomainIPScopeAdd(HttpServletRequest request) {
		
		logger.info("进入管理域ip范围新增界面");
//		String domainName = request.getParameter("domainName");
//		String domainID = request.getParameter("domainID");
//		request.setAttribute("domainName", domainName);
//		request.setAttribute("domainID", domainID);
		return new ModelAndView(
				"monitor/manageddomainipscope/domainIPScope_add");
	}
	
	/**
	 * 新增
	 */
	@RequestMapping("/addDomainIPScope")
	@ResponseBody
	public boolean addDomainIPScope(ManagedDomainIPScopeBean bean){
		return domainIPScopeService.insertDomainIpScope(bean);
	}
	
	/**
	 * 跳转至更新界面
	 */
	@RequestMapping("/toShowDomainIPScopeModify")
	public ModelAndView toShowDomainIPScopeModify(HttpServletRequest request) {
		
		logger.info("进入管理域ip范围更新界面");
		String scopeID = request.getParameter("scopeID");
		request.setAttribute("scopeID", scopeID);
		return new ModelAndView(
				"monitor/manageddomainipscope/domainIPScope_modify");
	}
	
	/**
	 * 更新
	 */
	@RequestMapping("/updateDomainIPScope")
	@ResponseBody
	public boolean updateDomainIPScope(ManagedDomainIPScopeBean bean){
		
		logger.info("更新管理域ip范围。。。。start");
		logger.info("更新的id为："+bean.getScopeID());
		return domainIPScopeService.updateDomainIpScope(bean);
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delDomainIPScope")
	@ResponseBody
	public boolean delDomainIPScope(int scopeID){
		
		logger.info("删除的id为 ："+scopeID);
		return domainIPScopeService.delDomainIpScope(scopeID+"");
	}
	
	/**
	 * 批量删除
	 * @return
	 */
	@RequestMapping("/delDomainIPScopes")
	@ResponseBody
	public boolean delDomainIPScopes(HttpServletRequest request){
		String scopeIDs = request.getParameter("scopeIDs");
		logger.info("删除的id为 ："+scopeIDs);
		return domainIPScopeService.delDomainIpScope(scopeIDs);
	}
	
	/**
	 * 验证是否存在
	 */
	@RequestMapping("/checkExist")
	@ResponseBody
	public boolean checkExist(HttpServletRequest request,ManagedDomainIPScopeBean bean){
		String flag = request.getParameter("flag");
		return  domainIPScopeService.checkExist(bean, flag);
	}
	
	/**
	 * 查找节点ID
	 * @param domainName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/searchTreeNodes")
	@ResponseBody
	public Map<String, Object> searchTreeNodes(String domainName)throws Exception{
		ManagedDomain managedDomain = managedDomainService.getIdByDomainName(domainName);
		String nodes = managedDomain.getParentId();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("nodes", nodes);
		return result;
	}
	
	/**
	 * 获得管理域描述
	 * @param request
	 * @return
	 */
	@RequestMapping("/getDomainDesc")
	@ResponseBody
	public String getDomainDesc(HttpServletRequest request){
		if(!"".equals(request.getParameter("domainID")) && request.getParameter("domainID") != null){
			int domainID = Integer.parseInt(request.getParameter("domainID"));
			return domainIPScopeService.getDomainDesc(domainID);
		}else{
			logger.error("没有选择的管理域！");
		}
		return null;
	}
}
