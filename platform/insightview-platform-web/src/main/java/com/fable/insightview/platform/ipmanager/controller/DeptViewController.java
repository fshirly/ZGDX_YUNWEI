package com.fable.insightview.platform.ipmanager.controller;

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

import com.fable.insightview.platform.entity.OrgDeptProviderTreeBean;
import com.fable.insightview.platform.entity.SysEmploymentBean;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.ipmanager.entity.IPManAddressListBean;
import com.fable.insightview.platform.ipmanager.entity.IPManSubNetRDeptBean;
import com.fable.insightview.platform.ipmanager.service.IDeptViewService;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.page.Page;

/**
 * ip地址管理(部门视图)
 *
 */
@Controller
@RequestMapping("/platform/deptViewManager")
public class DeptViewController {
	private final Logger logger = LoggerFactory.getLogger(DeptViewController.class);
	
	@Autowired
	IDeptViewService deptViewService;
	
	/**
	 * 跳转到部门视图页面
	 */
	@RequestMapping("/toDeptViewList")
	public ModelAndView toDeviceManagerList(String navigationBar) {
		return new ModelAndView("platform/ipmanager/deptView").addObject("navigationBar", navigationBar);
	}
	
	/**
	 * 点击部门视图中单位跳转的页面
	 */
	@RequestMapping("/toOrgUseSubNetInfo")
	public ModelAndView toOrgUseSubNetInfo(HttpServletRequest request) {
		String treeId = request.getParameter("treeId");
		request.setAttribute("organizationId", treeId);
		return new ModelAndView("platform/ipmanager/orgUseSubNetInfo");
	}
	
	/**
	 * 点击单位，列表展示
	 */
	@RequestMapping("/listOrgUseSubNet")
	@ResponseBody
	public Map<String, Object> listOrgUseSubNet(HttpServletRequest request,IPManSubNetRDeptBean bean){
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<IPManSubNetRDeptBean> page = new Page<IPManSubNetRDeptBean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String treeId = request.getParameter("organizationId");
		if("-1".equals(treeId)){
			paramMap.put("organizationId", -1);
		}else{
			int organizationId = Integer.parseInt(treeId.substring(1));
			paramMap.put("organizationId", organizationId);
		}
		paramMap.put("deptName", bean.getDeptName());
		page.setParams(paramMap);
		
		List<IPManSubNetRDeptBean> subnetRDeptLst = deptViewService.listOrgUseSubNet(page);
		int total = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", subnetRDeptLst);
		return result;
	}
	
	/**
	 * 点击部门视图中部门跳转的页面
	 */
	@RequestMapping("/toDeptUseSubNetInfo")
	public ModelAndView toDeptUseSubNetInfo(HttpServletRequest request) {
		String treeId = request.getParameter("treeId");
		String deptId = treeId.substring(1);
		request.setAttribute("deptId", deptId);
		return new ModelAndView("platform/ipmanager/deptUseSubNetInfo");
	}
	
	/**
	 * 获得部门下的所有子网
	 */
	@RequestMapping("/getAllSubNet")
	@ResponseBody
	public List<IPManAddressListBean> getAllSubNet(HttpServletRequest request){
		int deptId = Integer.parseInt(request.getParameter("deptId"));
		return deptViewService.getAllSubNet(deptId);
	}
	
	/**
	 * 点击部门，列表展示
	 */
	@RequestMapping("/listDeptUseSubNet")
	@ResponseBody
	public Map<String, Object> listDeptUseSubNet(HttpServletRequest request,IPManAddressListBean bean){
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<IPManAddressListBean> page = new Page<IPManAddressListBean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		int deptId = Integer.parseInt(request.getParameter("deptId"));
		paramMap.put("deptId", deptId);
		paramMap.put("startIp", bean.getStartIp());
		paramMap.put("endIp", bean.getEndIp());
		paramMap.put("subNetId", bean.getSubNetId());
		paramMap.put("status", bean.getStatus());
		page.setParams(paramMap);
		
		List<IPManAddressListBean> addressLst = deptViewService.listDeptUseSubNet(page);
		int total = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", addressLst);
		return result;
	}
	
	/**
	 * 分配到设备
	 */
	@RequestMapping("/doGiveToDevice")
	@ResponseBody
	public boolean doGiveToDevice(IPManAddressListBean addressListBean){
		return deptViewService.doGiveToDevice(addressListBean);
	}
	
	/**
	 * 收回到部门
	 */
	@RequestMapping("/doWithdrawToDept")
	@ResponseBody
	public boolean doWithdrawToDept(IPManAddressListBean addressListBean){
		return deptViewService.doWithdrawToDept(addressListBean);
	}
	
	/**
	 * 批量分配到设备
	 */
	@RequestMapping("/doBatchGiveToDevice")
	@ResponseBody
	public Map<String, Object> doBatchGiveToDevice(HttpServletRequest request){
		String ids = request.getParameter("ids");
		logger.info("批量分配到设备的id为："+ids);
		return deptViewService.doBatchGiveToDevice(ids);
	}
	
	/**
	 * 批量收回到部门
	 */
	@RequestMapping("/doBatchWithdrawToDept")
	@ResponseBody
	public Map<String, Object> doBatchWithdrawToDept(HttpServletRequest request){
		String ids = request.getParameter("ids");
		logger.info("批量收回到部门的id为："+ids);
		return deptViewService.doBatchWithdrawToDept(ids);
	}
	
	/**
	 * 查询树
	 * @return
	 */
	@RequestMapping("/searchTreeNodes")
	@ResponseBody
	public OrgDeptProviderTreeBean searchTreeNodes(HttpServletRequest request){
		String treeName = request.getParameter("treeName");
		String firstOrgId = request.getParameter("firstOrgId");
		return deptViewService.searchTreeNodes(treeName,Integer.parseInt(firstOrgId));
	}
	
	/**
	 * ip地址页面
	 */
	@RequestMapping("/toShowIPaddrModify")
	public ModelAndView toShowIPaddrModify(HttpServletRequest request) {
		String id = request.getParameter("id");
		String deptId = request.getParameter("deptId");
		String userId = request.getParameter("userId");
		request.setAttribute("id", id);
		request.setAttribute("deptId", deptId);
		request.setAttribute("userId", userId);
		return new ModelAndView("platform/ipmanager/ipAddress_modify");
	}

	/**
	 * 初始化IP地址信息
	 */
	@RequestMapping("/initIPAddressDetail")
	@ResponseBody
	public IPManAddressListBean initIPAddressDetail(int id){
		return deptViewService.getAddressInfoById(id); 
	}
	
	/**
	 * 更新ip地址
	 */
	@RequestMapping("/updateIPAddress")
	@ResponseBody
	public boolean updateIPAddress(IPManAddressListBean bean){
		return deptViewService.updateNoteByID(bean);
	}
	
	/**
	 * 跳转至分配ip地址页面
	 */
	@RequestMapping("/toGiveIP")
	public ModelAndView toGiveIP(HttpServletRequest request) {
		String id = request.getParameter("id");
		String deptId = request.getParameter("deptId");
		request.setAttribute("id", id);
		request.setAttribute("deptId", deptId);
		return new ModelAndView("platform/ipmanager/ipAddress_assign");
	}

	/**
	 * 获得部门人员
	 */
	@RequestMapping("/getDeptUsers")
	@ResponseBody
	public List<SysEmploymentBean> getDeptUsers(HttpServletRequest request){
		if(!"".equals(request.getParameter("deptId")) && request.getParameter("deptId") !=  null){
			int deptId = Integer.parseInt(request.getParameter("deptId"));
			return deptViewService.getDeptUsers(deptId);
		}
		return null;
	}
}
