package com.fable.insightview.platform.ipmanager.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.platform.dict.util.DictionaryLoader;
import com.fable.insightview.platform.ipmanager.entity.IPManAddressListBean;
import com.fable.insightview.platform.ipmanager.entity.IPManSubNetInfoBean;
import com.fable.insightview.platform.ipmanager.entity.IPManSubNetRDeptBean;
import com.fable.insightview.platform.ipmanager.entity.SubnetDeptTreeBean;
import com.fable.insightview.platform.ipmanager.entity.SubnetExportBean;
import com.fable.insightview.platform.ipmanager.service.ISubnetViewService;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.page.Page;

/**
 * ip地址管理(子网视图)
 *
 */
@Controller
@RequestMapping("/platform/subnetViewManager")
public class SubnetViewController {
	private final Logger logger = LoggerFactory.getLogger(SubnetViewController.class);
	private static final int USED_STATUS = 3;
	
	@Autowired
	ISubnetViewService subnetViewService;
	/**
	 * 跳转到子网视图页面
	 */
	@RequestMapping("/toSubnetViewList")
	public ModelAndView toDeviceManagerList(String navigationBar) {
		return new ModelAndView("platform/ipmanager/subnetView").addObject("navigationBar", navigationBar);
	}
	
	/**
	 * 子网部门树
	 */
	@RequestMapping("/initSubnetTree")
	@ResponseBody
	public Map<String, Object> initSubnetTree(){
		logger.info("获得子网部门树。。。。strat");
		return subnetViewService.getSubnetAndDeptVal();
	}
	
	/**
	 * 跳转到子网的全局页面
	 */
	@RequestMapping("/toSubnetInfoList")
	public ModelAndView toSubnetInfoList(){
		return new ModelAndView("platform/ipmanager/subnetInfo_list");
	}
	
	/**
	 * 全局列表
	 * @param bean
	 * @return
	 */
	@RequestMapping("/listSubnetInfo")
	@ResponseBody
	public Map<String, Object> listSubnetInfo(IPManSubNetInfoBean bean,HttpServletRequest request){
		logger.info("开始...获取子网列表数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<IPManSubNetInfoBean> page = new Page<IPManSubNetInfoBean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("startIp", bean.getStartIp());
		paramMap.put("endIp", bean.getEndIp());
		paramMap.put("deptName", bean.getDeptName());
		paramMap.put("totalNum", bean.getTotalNum());
		page.setParams(paramMap);
		
		List<IPManSubNetInfoBean> subnetInfoLst = subnetViewService.selectSubNetInfo(page);
		int total = subnetViewService.getSubnetCount(bean);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", subnetInfoLst);
		return result;
	}
	
	/**
	 * 跳转至子网详情页面
	 */
	@RequestMapping("/toShowSubnetDetail")
	public ModelAndView toShowSubnetDetail(HttpServletRequest request){
		String subNetId = request.getParameter("subNetId");
		request.setAttribute("subNetId", subNetId);
		return new ModelAndView("platform/ipmanager/subnetInfo_detail");
	}
	
	/**
	 * 子网详情信息
	 */
	@RequestMapping("/initSubnetInfoDetail")
	@ResponseBody
	public IPManSubNetInfoBean initSubnetInfoDetail(IPManSubNetInfoBean bean){
		return subnetViewService.getSubnetInfoByID(bean.getSubNetId());
		
	}
	
	/**
	 * 导出子网全局表
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/exportAllVal")
	@ResponseBody
	public void exportAllVal(HttpServletResponse response,SubnetExportBean<IPManSubNetInfoBean> export) {
		
		List<IPManSubNetInfoBean> subnetInfoLst = subnetViewService.getAllSubNetInfo();
		Map<Integer, String> networkTypeMap = DictionaryLoader.getConstantItems("networkType");
		
		for (int i = 0; i < subnetInfoLst.size(); i++) {
			subnetInfoLst.get(i).setFreeNum(subnetInfoLst.get(i).getFreeNum() + subnetInfoLst.get(i).getPreemptNum());
			String subNetTypeName = networkTypeMap.get(subnetInfoLst.get(i).getSubNetType());
			subnetInfoLst.get(i).setSubNetTypeName(subNetTypeName);
		}
		export.setExpResource(subnetInfoLst);

		SubnetExportBean exportBean = subnetViewService.refExportBean(export);
		subnetViewService.exportExcel(response, exportBean);
	}
	
	/**
	 * 点击空闲跳转的页面
	 */
	@RequestMapping("/toFreeSubnetInfoList")
	public ModelAndView toFreeSubnetInfoList(HttpServletRequest request){
		String treeId = request.getParameter("treeId");
		treeId = treeId.substring(1);
		request.setAttribute("subNetId", treeId);
		return new ModelAndView("platform/ipmanager/freeSubnetInfo_list");
	}
	
	/**
	 * 子网空闲列表
	 * @param bean
	 * @return
	 */
	@RequestMapping("/listFreeSubnetInfo")
	@ResponseBody
	public Map<String, Object> listFreeSubnetInfo(IPManAddressListBean bean,HttpServletRequest request){
		logger.info("开始...获取子网空闲列表数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<IPManAddressListBean> page = new Page<IPManAddressListBean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("startIp", bean.getStartIp());
		paramMap.put("endIp", bean.getEndIp());
		paramMap.put("subNetId", bean.getSubNetId());
		page.setParams(paramMap);
		
		List<IPManAddressListBean> freeSubnetInfoLst = subnetViewService.selectFreeSubNetInfo(page);
		int total = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", freeSubnetInfoLst);
		return result;
	}
	

	/**
	 * 获得子网下的所有空闲导出数据
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/exportFreeVal")
	@ResponseBody
	public void exportFreeVal(HttpServletRequest request ,HttpServletResponse response,SubnetExportBean<IPManAddressListBean> export) {
		List<IPManAddressListBean> subnetInfoLst = subnetViewService.getAllFreeSubNetInfo(export.getBelongSubnetId());
		Map<Integer, String> networkTypeMap = DictionaryLoader.getConstantItems("networkType");
		for (int i = 0; i < subnetInfoLst.size(); i++) {
			String subNetTypeName = networkTypeMap.get(subnetInfoLst.get(i).getSubNetType());
			subnetInfoLst.get(i).setSubNetTypeName(subNetTypeName);
		}
		export.setExpResource(subnetInfoLst);
		SubnetExportBean exportBean = subnetViewService.refExportBean(export);
		subnetViewService.exportExcel(response, exportBean);
	}
	
	/**
	 * 点击子网跳转的页面
	 */
	@RequestMapping("/toSubnetAndAllDept")
	public ModelAndView toSubnetAndAllDept(HttpServletRequest request){
		String treeId = request.getParameter("treeId");
		treeId = treeId.substring(1);
		request.setAttribute("subNetId", treeId);
		return new ModelAndView("platform/ipmanager/subnetAndDept_list");
	}
	
	/**
	 * 点击子网列表
	 * @param bean
	 * @return
	 */
	@RequestMapping("/listSubnetAndDeptInfo")
	@ResponseBody
	public Map<String, Object> listSubnetAndDeptInfo(IPManSubNetRDeptBean bean,HttpServletRequest request){
		logger.info("开始...获取子网列表数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<IPManSubNetRDeptBean> page = new Page<IPManSubNetRDeptBean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("deptName", bean.getDeptName());
		paramMap.put("subNetId", bean.getSubNetId());
		page.setParams(paramMap);
		
		List<IPManSubNetRDeptBean> allIncludeDeptLst = subnetViewService.listSubnetConDept(page);
		int total = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", allIncludeDeptLst);
		return result;
	}
	
	/**
	 * 获得点击子网的导出数据
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/exportSubnetAndDeptVal")
	@ResponseBody
	public void exportSubnetAndDeptVal(HttpServletRequest request ,HttpServletResponse response,SubnetExportBean<IPManSubNetRDeptBean> export) {
		List<IPManSubNetRDeptBean> subnetInfoLst = subnetViewService.getAllSubNetRDeptInfo2(export.getBelongSubnetId());
		export.setExpResource(subnetInfoLst);
		SubnetExportBean exportBean = subnetViewService.refExportBean(export);
		subnetViewService.exportExcel(response, exportBean);
	}

	/**
	 * 点击子网下的部门跳转的页面
	 */
	@RequestMapping("/toDeptInfoList")
	public ModelAndView toDeptInfoList(HttpServletRequest request){
		String treeId = request.getParameter("treeId");
		treeId = treeId.substring(1);
		request.setAttribute("deptId", treeId);
		String parentId = request.getParameter("parentId");
		parentId = parentId.substring(1);
		request.setAttribute("subNetId", parentId);
		return new ModelAndView("platform/ipmanager/deptInfo_list");
	}
	
	/**
	 * 点击子网下部门的列表
	 * @param bean
	 * @return
	 */
	@RequestMapping("/listAllDeptInfo")
	@ResponseBody
	public Map<String, Object> listAllDeptInfo(IPManAddressListBean bean,HttpServletRequest request){
		logger.info("开始...获取子网下部门的列表数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<IPManAddressListBean> page = new Page<IPManAddressListBean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("startIp", bean.getStartIp());
		paramMap.put("endIp", bean.getEndIp());
		paramMap.put("subNetId", bean.getSubNetId());
		paramMap.put("deptId", bean.getDeptId());
		paramMap.put("status", bean.getStatus());
		page.setParams(paramMap);
		
		List<IPManAddressListBean> freeSubnetInfoLst = subnetViewService.selectAllSubnetDeptInfo(page);
		int total = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", freeSubnetInfoLst);
		return result;
	}
	
	/**
	 * 获得子网下部门所有ip的导出数据
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/exportDeptVal")
	@ResponseBody
	public void exportDeptVal(HttpServletRequest request ,HttpServletResponse response,SubnetExportBean<IPManAddressListBean> export) {
		List<IPManAddressListBean> subnetInfoLst = subnetViewService.getAllSubNetDeptInfo(export.getBelongSubnetId(),export.getBelongDeptId());
		Map<Integer, String> networkTypeMap = DictionaryLoader.getConstantItems("networkType");
		for (int i = 0; i < subnetInfoLst.size(); i++) {
			String subNetTypeName = networkTypeMap.get(subnetInfoLst.get(i).getSubNetType());
			subnetInfoLst.get(i).setSubNetTypeName(subNetTypeName);
			int status = subnetInfoLst.get(i).getStatus();
			if(status == USED_STATUS){
				subnetInfoLst.get(i).setStatusName("占用");
			}else{
				subnetInfoLst.get(i).setStatusName("空闲");
			}
		}
		export.setExpResource(subnetInfoLst);
		SubnetExportBean exportBean = subnetViewService.refExportBean(export);
		subnetViewService.exportExcel(response, exportBean);
	}
	
	/**
	 * 跳转至子网新增页面
	 */
	@RequestMapping("/toShowSubnetAdd")
	public ModelAndView toShowSubnetAdd(){
		return new ModelAndView("platform/ipmanager/subnetInfo_add");
	}
	
	/**
	 * 验证新增的子网是否已建立
	 */
	@RequestMapping("/checkAddSubnet")
	@ResponseBody
	public Map<String, Object> checkAddSubnet(IPManSubNetInfoBean bean){
		return subnetViewService.checkAddSubnet(bean);
	}
	
	/**
	 * 新增子网
	 */
	@RequestMapping("/doAddSubnet")
	@ResponseBody
	public boolean doAddSubnet(IPManSubNetInfoBean bean){
		return subnetViewService.doAddSubnet(bean);
	}
	
	/**
	 * 跳转到空闲分配到部门页面
	 */
	@RequestMapping("/toAssignDept")
	public ModelAndView toAssignDept(HttpServletRequest request){
		String ids = request.getParameter("ids");
		String subNetId = request.getParameter("subNetId");
		request.setAttribute("ids", ids);
		request.setAttribute("subNetId", subNetId);
		return new ModelAndView("platform/ipmanager/freeAssignDept");
	}
	
	/**
	 * 空闲IP地址分配到部门
	 */
	@RequestMapping("/doFreeAssignDept")
	@ResponseBody
	public boolean doFreeAssignDept(IPManAddressListBean addressListBean ,HttpServletRequest request){
		return subnetViewService.doFreeAssignDept(addressListBean);
	}
	
	/**
	 * 删除子网
	 */
	@RequestMapping("/delSubNetInfo")
	@ResponseBody
	public Map<String, Object> delPerfKPIDef(IPManSubNetInfoBean subNetInfoBean) {
		Map<String, Object> result = new HashMap<String, Object>();
		boolean flag = false;
		boolean checkRS = false;
		logger.info("删除前验证。。。。。start");
		boolean checkResult = subnetViewService.checkBeforeDel(subNetInfoBean.getSubNetId());
		if (checkResult == true) {
			try {
				//删除子网下的ip地址
				subnetViewService.delBySubNetId(subNetInfoBean.getSubNetId());
				//删除子网关系
				subnetViewService.delSubNetDeptBySubNetID(subNetInfoBean.getSubNetId());
				//删除子网信息
				subnetViewService.delSubNetInfoById(subNetInfoBean.getSubNetId());
				flag = true;
			} catch (Exception e) {
				logger.error("删除子网异常：" , e);
				flag = false;
				checkRS = false;
			}
		} else {
			flag = false;
			checkRS = true;
		}
		result.put("flag", flag);
		result.put("checkRS", checkRS);
		return result;
	}
	
	/**
	 * 批量删除子网
	 */
	@RequestMapping("/delSubNetInfos")
	@ResponseBody
	public Map<String, Object> delSubNetInfos(HttpServletRequest request) {
		logger.info("批量删除子网........start");
		boolean checkResult = false;
		//子网是否删除成功
		boolean flag = false;
		//是否有正在被使用的子网
		boolean rs = false;
		String subNetIds = request.getParameter("subNetIds");
		String[] splitIds = subNetIds.split(",");
		String subNetName = "";
		/* 能够被删的ID数组 */
		List<Integer> delId = new ArrayList<Integer>();
		/* 不能被删保留的ID数组 */
		List<Integer> reserveId = new ArrayList<Integer>();
		
		for (String strId : splitIds) {
			int subNetId = Integer.parseInt(strId);
			checkResult = subnetViewService.checkBeforeDel(subNetId);
			if (checkResult) {
				delId.add(subNetId);
				logger.info("能够被删的Id：" + subNetId);
			} else {
				reserveId.add(subNetId);
				logger.info("不能够被删而保留的Id：" + subNetId);
			}
		}
		
		logger.info("删除能够删除的子网.....start");
		try {
			for (int i = 0; i < delId.size(); i++) {
				//删除子网下的ip地址
				subnetViewService.delBySubNetId(delId.get(i));
				//删除子网部门关系
				subnetViewService.delSubNetDeptBySubNetID(delId.get(i));
			}
			//删除子网信息
			subnetViewService.delSubNetInfos(delId);
			flag = true;
			rs = false;
		} catch (Exception e) {
			logger.error("批量删除子网异常：" + e);
			flag = false;
			rs = false;
		}
		
		if (reserveId.size() > 0) {
			flag = false;
			String sName = "";
			for (int i = 0; i < reserveId.size(); i++) {
				sName = subnetViewService.getSubnetInfoByID(reserveId.get(i)).getIpAddress();
				if((i + 1) % 2 == 0){
					subNetName = subNetName + sName + "," + "<br/>";
				}else{
					subNetName = subNetName + sName + ",";
				}
			}
			subNetName = subNetName.substring(0, subNetName.lastIndexOf(","));
			logger.info("不能删除的子网名称：" + subNetName);
			rs = true;
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		subNetName = "<div style='float:left'>【 " + subNetName + " 】</div>";
		result.put("flag", flag);
		result.put("rs", rs);
		result.put("subNetName", subNetName);
		return result;
	}
	
	/**
	 * 部门的ip地址收回到子网
	 */
	@RequestMapping("/doWithdraw")
	@ResponseBody
	public Map<String, Object> doWithdraw(IPManAddressListBean addressListBean,HttpServletRequest request){
		String ids = request.getParameter("ids");
		return subnetViewService.doWithdraw(addressListBean, ids);
	}
	
	
	/**
	 * 从子网收回到部门
	 */
	@RequestMapping("/doWithdrawDept")
	@ResponseBody
	public Map<String, Object> doWithdrawDept(HttpServletRequest request){
		String ids = request.getParameter("ids");
		int subNetId = Integer.parseInt(request.getParameter("subNetId"));
		return subnetViewService.doWithdrawDept(ids, subNetId);
	}
	
	/**
	 * 跳转到从子网批量分配部门的界面
	 */
	@RequestMapping("/toBatchAssignFromSubNet")
	public ModelAndView toBatchAssignFromSubNet(HttpServletRequest request){
		String subNetId = request.getParameter("subNetId");
		request.setAttribute("subNetId", subNetId);
		return new ModelAndView("platform/ipmanager/assignDeptFromSubNet");
	}
	
	/**
	 * 判断输入的ip是否属于该子网段
	 */
	@RequestMapping("/isInSubNet")
	@ResponseBody
	public boolean isInSubNet(HttpServletRequest request){
		int subNetId = Integer.parseInt(request.getParameter("subNetId"));
		String checkIp = request.getParameter("checkIp");
		return subnetViewService.isInSubNet(subNetId, checkIp);
	}
	
	/**
	 * 判断ip地址段中的ip空闲使用情况
	 */
	@RequestMapping("/checkIsAllFree")
	@ResponseBody
	public Map<String, Object> checkIsAllFree(HttpServletRequest request){
		String startIp = request.getParameter("startIp");
		String endIp = request.getParameter("endIp");
		int subNetId = Integer.parseInt(request.getParameter("subNetId"));
		IPManAddressListBean addressListBean = new IPManAddressListBean();
		addressListBean.setStartIp(startIp);
		addressListBean.setEndIp(endIp);
		addressListBean.setSubNetId(subNetId);
		return subnetViewService.checkIsAllFree(addressListBean);
	}
	
	/**
	 * 空闲ip地址列表页面
	 */
	@RequestMapping("/toViewFreeAddress")
	public ModelAndView toViewFreeAddress(HttpServletRequest request){
		String subNetId = request.getParameter("subNetId");
		String endIp = request.getParameter("endIp");
		String ids = request.getParameter("ids");
		String startIp = request.getParameter("startIp");
		String deptId = request.getParameter("deptId");
		request.setAttribute("subNetId", subNetId);
		request.setAttribute("ids", ids);
		request.setAttribute("startIp", startIp);
		request.setAttribute("endIp", endIp);
		request.setAttribute("deptId", deptId);
		return new ModelAndView("platform/ipmanager/freeAddressInfo");
	}
	
	/**
	 * 跳转到拆分子网的页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/doOpenResolution")
	public ModelAndView doOpenResolution(HttpServletRequest request){
		String subNetId = request.getParameter("subNetId");
		String ipAddress = request.getParameter("ipAddress");
		String subNetMark = request.getParameter("subNetMark");
		request.setAttribute("subNetId", subNetId);
		request.setAttribute("ipAddress", ipAddress);
		request.setAttribute("subNetMark", subNetMark);
		return new ModelAndView("platform/ipmanager/subnetSplit");
	}
	
	/**
	 * 跳转拆分预览页面
	 */
	@RequestMapping("/toSplitAddressInfo")
	public ModelAndView toSplitAddressInfo(HttpServletRequest request){
		String subNetId = request.getParameter("subNetId");
		String ipAddress = request.getParameter("ipAddress");
		String splitNum = request.getParameter("splitNum");
		String subNetMark = request.getParameter("subNetMark");
		request.setAttribute("subNetId", subNetId);
		request.setAttribute("ipAddress", ipAddress);
		request.setAttribute("splitNum", splitNum);
		request.setAttribute("subNetMark", subNetMark);
		return new ModelAndView("platform/ipmanager/splitAddressInfo");
	}
	/**
	 * 拆分预览列表
	 */
	@RequestMapping("/doPreviewSplitSubnet")
	@ResponseBody
	public Map<String, Object> doPreviewSplitSubnet(HttpServletRequest request){
		String ipAddress = request.getParameter("ipAddress");
		String subNetMark = request.getParameter("subNetMark");
		int splitNum = Integer.parseInt(request.getParameter("splitNum"));
		return subnetViewService.doPreviewSplitSubnet(ipAddress, subNetMark, splitNum);
	}
	
	/**
	 * 拆分子网
	 */
	@RequestMapping("/doSplit")
	@ResponseBody
	public boolean doSplit(HttpServletRequest request){
		String ipAddress = request.getParameter("ipAddress");
		String subNetMark = request.getParameter("subNetMark");
		int splitNum = Integer.parseInt(request.getParameter("splitNum"));
		int subNetId = Integer.parseInt(request.getParameter("subNetId"));
		return subnetViewService.doSplit(subNetId, ipAddress, subNetMark, splitNum);
	}
	
	/**
	 * 导出拆分产生的心网络地址、广播地址列表
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/exportSplit")
	@ResponseBody
	public void exportSplit(HttpServletResponse response,SubnetExportBean<IPManSubNetInfoBean> export) {
		String condition = export.getCondition();
		String[] splits = condition.split(";");
		int splitNum = Integer.parseInt(splits[1]);
		String ipAddress = splits[2];
		String subNetMark = splits[3];
		List<IPManSubNetInfoBean> subnetInfoLst = (List<IPManSubNetInfoBean>) subnetViewService.doPreviewSplitSubnet(ipAddress,subNetMark, splitNum).get("rows");
		export.setExpResource(subnetInfoLst);

		SubnetExportBean exportBean = subnetViewService.refExportBean(export);
		subnetViewService.exportExcel(response, exportBean);
	}
	
	/**
	 * 查询树
	 * @return
	 */
	@RequestMapping("/searchTreeNodes")
	@ResponseBody
	public SubnetDeptTreeBean searchTreeNodes(HttpServletRequest request){
		String treeName = request.getParameter("treeName");
		return subnetViewService.searchTreeNodes(treeName);
	}
	
	@RequestMapping("/isSplit")
	@ResponseBody
	public boolean isSplit(HttpServletRequest request){
		int subNetId = Integer.parseInt(request.getParameter("subNetId"));
		String ipAddress = request.getParameter("ipAddress");
		int splitNum = Integer.parseInt(request.getParameter("splitNum"));
		String subNetMark = request.getParameter("subNetMark");
		return subnetViewService.isSplit(subNetId, ipAddress, subNetMark, splitNum);
	}
	
	/**
	 * 获得空闲地址
	 * @param bean
	 * @return
	 */
	@RequestMapping("/getFreeIds")
	@ResponseBody
	public String getFreeIds(IPManAddressListBean bean){
		return subnetViewService.getFreeIds(bean);
	}
	
	/**
	 * 部门分配子网的提示页面
	 */
	@RequestMapping("/toAssignTipInfo")
	public ModelAndView toAssignTipInfo(HttpServletRequest request){
		String freeIds = request.getParameter("freeIds");
		request.setAttribute("freeIds", freeIds);
		String messageTip = request.getParameter("messageTip");
		request.setAttribute("messageTip", messageTip);
		return new ModelAndView("platform/ipmanager/assignTipInfo");
	}
	
	/**
	 * 获得所有的网络类型
	 * @return
	 */
	@RequestMapping("/getAllSubNetType")
	@ResponseBody
	public List<IPManSubNetInfoBean> getAllSubNetType(){
		List<IPManSubNetInfoBean> subnetLst = new ArrayList<IPManSubNetInfoBean>();
		// 获得数据字典中网络类型名称
		Map<Integer, String> networkTypeMap = DictionaryLoader.getConstantItems("networkType");
		for (Map.Entry<Integer, String> entry : networkTypeMap.entrySet()) {
			//System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
			int subNetType = entry.getKey();
			String subNetTypeName = entry.getValue();
			IPManSubNetInfoBean bean = new IPManSubNetInfoBean();
			bean.setSubNetType(subNetType);
			bean.setSubNetTypeName(subNetTypeName);
			subnetLst.add(bean);
		}
		return subnetLst;
	}
	
	/**
	 * 跳转至子网编辑页面
	 */
	@RequestMapping("/toShowSubnetmodify")
	public ModelAndView toShowSubnetmodify(HttpServletRequest request){
		String subNetId = request.getParameter("subNetId");
		request.setAttribute("subNetId", subNetId);
		return new ModelAndView("platform/ipmanager/subnetInfo_modify");
	}
	
	/**
	 * 更新子网信息
	 */
	@RequestMapping("/doUpdateSubnet")
	@ResponseBody
	public boolean doUpdateSubnet(IPManSubNetInfoBean bean){
		return subnetViewService.doUpdateSubnet(bean);	
	}
}
