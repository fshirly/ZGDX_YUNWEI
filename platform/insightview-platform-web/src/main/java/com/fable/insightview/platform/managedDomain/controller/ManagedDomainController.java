package com.fable.insightview.platform.managedDomain.controller;

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

import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.managedDomain.entity.ManagedDomain;
import com.fable.insightview.platform.managedDomain.service.IManagedDomainService;

@Controller
@RequestMapping("/platform/managedDomain")
public class ManagedDomainController {
	@Autowired
	IManagedDomainService managedDomainService;
	
	private final Logger logger = LoggerFactory.getLogger(ManagedDomainController.class);
			
	@RequestMapping(value = "/managedDomainView")
	public ModelAndView managedDomainView(String navigationBar) {
		logger.info("加载管理域列表页面！");
		ModelAndView mv = new ModelAndView();
		mv.addObject("navigationBar", navigationBar);
		mv.setViewName("platform/managedDomain/managedDomainView");
		mv.addObject("navigationBar", navigationBar);
		return mv;
	}
	
	/**
	 * 加载管理域列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/loadManagedDomainList")
	@ResponseBody
	public Map<String, Object> loadManagedDomainList(ManagedDomain managedDomain) {
		logger.info("准备获取管理域列表数据。。。");
		Map<String, Object> result = new HashMap<String, Object>();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil.getFlexiGridPageInfo(request);
		Map<String, Object> domainMap = this.managedDomainService.getManagedDomainList(managedDomain, flexiGridPageInfo);
		List<ManagedDomain> domainList = (List<ManagedDomain>) domainMap.get("managedDomainList");
		int count = (Integer)domainMap.get("total");
		result.put("rows", domainList);
		result.put("total", count);
		logger.info("数据获取结束。。。");
		return result;
	}
	
	/**
	 * 初始化树
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findManagedDomainTreeVal")
	@ResponseBody
	public Map<String, Object> findManagedDomainTreeVal() throws Exception {
		List<ManagedDomain> menuLst = this.managedDomainService.getManagedDomainTreeVal();
		String menuLstJson = JsonUtil.toString(menuLst);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("menuLstJson", menuLstJson);
		return result;
	}
	
	/**
	 * 初始化树
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/initManagedDomainTreeVal")
	@ResponseBody
	public Map<String, Object> initManagedDomainTreeVal() throws Exception {
		List<ManagedDomain> menuLst = this.managedDomainService.getManagedDomainTreeVal();
		for (int i = 0; i < menuLst.size(); i++) {
			if(menuLst.get(i).getDomainId() == 1){
				ManagedDomain allDomain = menuLst.get(i);
				allDomain.setDomainId(0);
				allDomain.setParentId("-1");
				allDomain.setDomainName("所有管理域");
			}
			if("1".equals(menuLst.get(i).getParentId())){
				menuLst.get(i).setParentId("0");
			}
		}
		String menuLstJson = JsonUtil.toString(menuLst);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("menuLstJson", menuLstJson);
		return result;
	}
	
	
	/**
	 * 批量删除
	 * 
	 * @param event
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delManagedDomains")
	@ResponseBody
	public boolean delManagedDomains(HttpServletRequest request) throws Exception {
		boolean flag=false;
		int i=0;
		String domainIds = request.getParameter("domainIds");
		logger.info("被删除管理域ID = "+domainIds);
		String[] splitIds = domainIds.split(",");
		for (String strId : splitIds) {
			Integer domainId =  Integer.parseInt(strId);
			boolean isleaf=managedDomainService.isLeaf(domainId);
			if(isleaf==true){
				i++;
			}
		}
		if(i==splitIds.length){
			for (String strId : splitIds) {
				Integer domainId =  Integer.parseInt(strId);
				flag=managedDomainService.delManagedDomain(domainId);
			}
		}
		return flag;
	}

	
	@RequestMapping("/delManagedDomain")
	@ResponseBody
	public boolean delManagedDomain(int domainId) throws Exception {
		boolean flag=false;
		boolean isleaf=managedDomainService.isLeaf(domainId);
		if(isleaf==true){
			flag=managedDomainService.delManagedDomain(domainId);
		}
		
		return flag;
		
	}
	
	/**
	 * 新增管理域
	 * @param managedDomain
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addManagedDomain")
	@ResponseBody
	public boolean addManagedDomain(ManagedDomain managedDomain)throws Exception{
		return managedDomainService.addManagedDomain(managedDomain);
	}
	
	/**
	 * 初始化编辑数据
	 * @param managedDomain
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/initManagedDomainInfo")
	@ResponseBody
	public ManagedDomain initManagedDomainInfo(ManagedDomain managedDomain)throws Exception{
		logger.info("获取更新数据");
		logger.info("被更新数据Id = "+managedDomain.getDomainId());
		ManagedDomain bean=new ManagedDomain();
		bean=managedDomainService.getManagedDomainInfo(managedDomain.getDomainId()).get(0);
		int parentId=Integer.parseInt(bean.getParentId());
		String parentName="";
		if(parentId !=0 ){
			parentName=managedDomainService.getManagedDomainInfo(parentId).get(0).getDomainName();
		}
		bean.setParentDomainName(parentName);
		return bean;
	}
	
	
	/**
	 * 修改字典项
	 * @param managedDomain
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/editManagedDomainInfo")
	@ResponseBody
	public boolean editManagedDomainInfo(ManagedDomain managedDomain)throws Exception{
		return managedDomainService.updateManagedDomainInfo(managedDomain);
	}
	
	/**
	 * 检查管理域名称
	 * @param domainName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/checkDomainName")
	@ResponseBody
	public boolean checkDomainName(String domainName)throws Exception{
		logger.info("检查用户名是否存在");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		String flag=request.getParameter("flag");
		logger.info(flag+"时检查");
		return managedDomainService.checkDomainName(domainName,flag);
	}
	/**
	 * 查找节点ID
	 * @param domainName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/searchTreeNodes")
	@ResponseBody
	public ManagedDomain searchTreeNodes(String domainName)throws Exception{
		return managedDomainService.getIdByDomainName(domainName);
	}
	
	/**
	 * 打开管理域详情页面
	 * @param domainName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toShowManageDetail")
	@ResponseBody
	public ModelAndView toShowManageDetail(HttpServletRequest request){
		logger.info("加载管理域详情页面");
		String domainId=request.getParameter("domainId");
		request.setAttribute("domainId", domainId);
		return new ModelAndView("platform/managedDomain/managedDomainDetail");
	}
	
	
	/**
	 * 打开编辑管理域页面
	 * @param domainName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toShowManageModify")
	@ResponseBody
	public ModelAndView toShowManageModify(HttpServletRequest request){
		logger.info("加载管理域编辑页面");
		String domainId=request.getParameter("domainId");
		request.setAttribute("domainId", domainId);
		return new ModelAndView("platform/managedDomain/managedDomainModify");
	}
	
	/**
	 * 打开新增管理域页面
	 * @param domainName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toShowManageAdd")
	@ResponseBody
	public ModelAndView toShowManageAdd(HttpServletRequest request){
		logger.info("加载管理域新增页面");
		return new ModelAndView("platform/managedDomain/managedDomainAdd");
	}
}
