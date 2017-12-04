package com.fable.insightview.platform.supplier.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jxl.write.DateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.platform.common.util.DateUtil;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.entity.OrganizationBean;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.provider.entity.ProviderInfoBean;
import com.fable.insightview.platform.providerOrg.entity.SysProviderOrganization;
import com.fable.insightview.platform.providerOrg.service.ISysProviderOrgService;
import com.fable.insightview.platform.service.IOrganizationService;
import com.fable.insightview.platform.supplier.enitity.ProviderAccessoryInfo;
import com.fable.insightview.platform.supplier.enitity.ProviderServiceCertificate;
import com.fable.insightview.platform.supplier.enitity.ProviderSoftHardwareProxy;
import com.fable.insightview.platform.supplier.enitity.Supplier;
import com.fable.insightview.platform.supplier.service.ISupplierService;

/**
 * @Description:   供应商管理
 * @author         刘召庆
 *            
 */
@Controller
@RequestMapping("/platform/supplier")
public class SupplierController {
	@Autowired
	private ISupplierService supplierService;
	
	@Autowired
	private IOrganizationService organizationService;
	
	@Autowired
	private ISysProviderOrgService sysProviderOrgService;
	
	//private final Logger logger = LoggerFactory.getLogger(SupplierController.class);
	
	/**
	 * 菜单页面跳转供应商管理页面
	 * @return
	 */
	@RequestMapping("/toSupplierManage")
	public ModelAndView toSupplierManage(String navigationBar) {
		ModelAndView mv = new ModelAndView("platform/supplier/suppliermanage_list");
		mv.addObject("navigationBar", navigationBar);
		return mv;
	}
	/**
	 * 菜单页面跳转供应商管理新增页面
	 * @return
	 */
	@RequestMapping("/toSupplierAdd")
	public ModelAndView supplierAdd() {
		return new ModelAndView("platform/supplier/supplier");
	}
	/**
	 * 跳到供应商详情页面
	 * @param vo
	 * @param request
	 * @return
	 */
	@RequestMapping("/supplierLLook")
	@ResponseBody
	public ModelAndView baseSdetail(HttpServletRequest request,Map<String,Object> map){
		String lProviderId = request.getParameter("providerId");
		map.put("lProviderId", lProviderId);
		return new ModelAndView("platform/supplier/supplierLLook");
	}
	
	/**
	 * 跳到供应商编辑页面
	 * @param vo
	 * @param request
	 * @return
	 */
	@RequestMapping("/supplierLEdit")
	@ResponseBody
	public ModelAndView supplierLEdit(HttpServletRequest request,Map<String,Object> map){
		String lProviderId = request.getParameter("providerId") == "" ? "0" : request.getParameter("providerId");
		Map<String,Object> params = new HashMap<String,Object>();
		Page<ProviderAccessoryInfo> page1=new Page<ProviderAccessoryInfo>(); 
		Page<ProviderSoftHardwareProxy> page2=new Page<ProviderSoftHardwareProxy>(); 
		Page<ProviderServiceCertificate> page3=new Page<ProviderServiceCertificate>(); 
		page1.setPageSize(10);
		page2.setPageSize(10);
		page3.setPageSize(10);
		params.put("providerId", Integer.parseInt(lProviderId));
		page1.setParams(params);
		page2.setParams(params);
		page3.setParams(params);
		//设置查询参数
		List<ProviderAccessoryInfo> list1 = supplierService.getAttachment(page1);
		List<ProviderSoftHardwareProxy> list2 = supplierService.getSoftHardware(page2);
		List<ProviderServiceCertificate> list3 = supplierService.getServiceInfo(page3);
		//查询总记录数
		int totalCount1 = page1.getTotalRecord() == 0 ? 10 : page1.getTotalRecord();
		int totalCount2 = page2.getTotalRecord() == 0 ? 10 : page2.getTotalRecord();
		int totalCount3 = page3.getTotalRecord() == 0 ? 10 : page3.getTotalRecord();
		page1.setPageSize(totalCount1);
		page2.setPageSize(totalCount2);
		page3.setPageSize(totalCount3);
		list1 = supplierService.getAttachment(page1);
		list2 = supplierService.getSoftHardware(page2);
		list3 = supplierService.getServiceInfo(page3);
		Supplier s = supplierService.queryBaseS(Integer.parseInt(lProviderId));
		
		map.put("lProviderId", lProviderId);
		map.put("supplier", s);
		map.put("establishedTime", DateUtil.date2String2(s.getEstablishedTime()));
		map.put("attachmentArray", JsonUtil.toString(list1));
		map.put("g_attachmentArrays", JsonUtil.toString(list2));
		map.put("gs_attachmentArrays", JsonUtil.toString(list3));
		//System.out.println("****************************----"+map);
		return new ModelAndView("platform/supplier/supplierLEdit");
	}
	
	/**
	 * 连接跳转供应商详情页面
	 * @return
	 *//*
	@RequestMapping("/toSLLook")
	public ModelAndView toSLLook() {
		return new ModelAndView("platform/supplier/supplierView");
	}*/
	/**
	 * 跳转供应商软硬件代理详情页面
	 * @return
	 */
	@RequestMapping("/softHardLook")
	@ResponseBody
	public ModelAndView toSoftHardLook() {
		return new ModelAndView("platform/supplier/softHardLook");
	}
	/**
	 * 跳转供应商详情页面中软硬件代理详情页面
	 * @return
	 */
	@RequestMapping("/toSoftHardCertificateLook")
	@ResponseBody
	public ModelAndView softHardCertificateLook(HttpServletRequest request,Map<String,Object> map) {
		String proxyId = request.getParameter("proxyId");
		map.put("proxyId", proxyId == null ? "0" : proxyId);
		return new ModelAndView("platform/supplier/softHardCertificateLook");
	}
	
	/**
	 * 跳转供应商软硬件代理新增页面
	 * @return
	 */
	@RequestMapping("/toSoftHardAdd")
	public ModelAndView toSoftHardAdd() {
		return new ModelAndView("platform/supplier/softhard_add");
	}
	/**
	 * 跳转供应商详情页面中的服务代理详情页面
	 * @return
	 */
	@RequestMapping("/toServiceCertificateLook")
	public ModelAndView serviceCertificateLook(HttpServletRequest request,Map<String,Object> map) {
		String serviceId = request.getParameter("serviceId");
		map.put("serviceId", serviceId == null ? "0" : serviceId);
		return new ModelAndView("platform/supplier/serviceCertificateLook");
	}
	/**
	 * 跳转供应商服务代理详情页面
	 * @return
	 */
	@RequestMapping("/toServiceLook")
	public ModelAndView serviceLook() {
		return new ModelAndView("platform/supplier/serviceLook");
	}
	/**
	 * 跳转供应商软硬件代理修改页面
	 * @return
	 */
	@RequestMapping("/toSHUpdate")
	@ResponseBody
	public ModelAndView toSHUpdate(HttpServletRequest request,Map<String,Object> map) {
		String sHData = request.getParameter("data");
		map.put("shdata",JsonUtil.toList(sHData,Supplier.class));
		return new ModelAndView("platform/supplier/softhard_edit");
	}
	/**
	 * 跳转供应商服务代理修改页面
	 * @return
	 */
	@RequestMapping("/toSUpdate")
	@ResponseBody
	public ModelAndView toSUpdate(HttpServletRequest request,Map<String,Object> map) {
		return new ModelAndView("platform/supplier/service_edit");
	}
	/**
	 * 跳转供应商服务新增页面
	 * @return
	 */
	@RequestMapping("/toServiceAdd")
	@ResponseBody
	public ModelAndView toServiceAdd(HttpServletRequest request,Map<String,Object> map) {
		String sHData = request.getParameter("data");
		map.put("shdata",JsonUtil.toList(sHData,Supplier.class));
		return new ModelAndView("platform/supplier/service_add");
	}
	
	/**
	 * 获取供应商管理页面数据
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping("/baseInfo")
	@ResponseBody
	public Map<String, Object> baseInfo(Supplier vo, HttpServletRequest request){
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		Page<Supplier> page=new Page<Supplier>(); 
		param.put("providerName",vo.getProviderName());
		param.put("proxyFirmName",vo.getProxyFirmName());
		param.put("serviceFirmName",vo.getServiceFirmName());
		//设置分页参数
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		page.setParams(param);
		//设置查询参数
		
		List<Supplier> list = supplierService.getSupplier(page);
		//查询总记录数
		int totalCount = page.getTotalRecord();
		result.put("total", totalCount);
		result.put("rows", list);
		return result;
	}
	
	/**
	 * 获取供应商增加页面中基本信息附件的数据
	 * @param vo
	 * @param request
	 * @return
	 */
	@RequestMapping("/attachmentInfo")
	@ResponseBody
	public Map<String, Object> attachmentInfo(ProviderAccessoryInfo vo, HttpServletRequest request){
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		Page<ProviderAccessoryInfo> page=new Page<ProviderAccessoryInfo>(); 
		//设置分页参数
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		params.put("providerId", vo.getProviderId());
		page.setParams(params);
		//设置查询参数
		List<ProviderAccessoryInfo> list = supplierService.getAttachment(page);
		//查询总记录数
		int totalCount = page.getTotalRecord();
		result.put("total", totalCount);
		result.put("rows", list);
		return result;
	}
	
/**
	 * 获取供应商增加页面中软硬件代理的数据
	 * @param vo
	 * @param request
	 * @return
	 *//*
	@RequestMapping("/addSupplier")
	@ResponseBody
	public Map<String, Object> addSupplier(Supplier vo, HttpServletRequest request){
		Map<String, Object> result = new HashMap<String, Object>();
		Page<Supplier> page=new Page<Supplier>(); 
		//设置分页参数
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		//设置查询参数
		List<Supplier> list = supplierService.getSoftHardware(page);
		//查询总记录数
		int totalCount = page.getTotalRecord();
		result.put("total", totalCount);
		result.put("rows", list);
		return result;
	}*/
	
	/**
	 * 获取供应商增加页面中软硬件代理的数据
	 * @param vo
	 * @param request
	 * @return
	 */
	@RequestMapping("/addSupplierInfo")
	@ResponseBody
	public boolean addSupplierInfo(Supplier vo, HttpServletRequest request){
		try {
			supplierService.insertSupplierInfo(vo);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 修改供应商的数据
	 * @param vo
	 * @param request
	 * @return
	 */
	@RequestMapping("/editSupplierInfo")
	@ResponseBody
	public boolean editSupplierInfo(Supplier vo, HttpServletRequest request){
		try {
			supplierService.editSupplierInfo(vo);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 获取供应商增加页面中软硬件代理的数据
	 * @param vo
	 * @param request
	 * @return
	 */
	@RequestMapping("/softHardwareInfo")
	@ResponseBody
	public Map<String, Object> softHardwareInfo(ProviderSoftHardwareProxy vo, HttpServletRequest request){
		System.out.println("softHardwareInfo......................."+vo.getProviderId());
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		Page<ProviderSoftHardwareProxy> page=new Page<ProviderSoftHardwareProxy>(); 
		//设置分页参数
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		//设置查询参数
		params.put("providerId", vo.getProviderId());
		page.setParams(params);
		List<ProviderSoftHardwareProxy> list = supplierService.getSoftHardware(page);
		//查询总记录数
		int totalCount = page.getTotalRecord();
		result.put("total", totalCount);
		result.put("rows", list);
		return result;
	}
	
	/**
	 * 获取供应商增加页面中软硬件代理的数据
	 * @param vo
	 * @param request
	 * @return
	 */
	@RequestMapping("/getServiceInfo")
	@ResponseBody
	public Map<String, Object> getServiceInfo(ProviderServiceCertificate vo, HttpServletRequest request){
		System.out.println("getServiceInfo......................."+vo.getProviderId());
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		Page<ProviderServiceCertificate> page=new Page<ProviderServiceCertificate>(); 
		//设置分页参数
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		//设置查询参数
		params.put("providerId", vo.getProviderId());
		page.setParams(params);
		List<ProviderServiceCertificate> list = supplierService.getServiceInfo(page);
		//查询总记录数
		int totalCount = page.getTotalRecord();
		result.put("total", totalCount);
		result.put("rows", list);
		return result;
	}
	
	/**
	 * 获取供应商详情页面中数据
	 * @param vo
	 * @param request
	 * @return
	 */
	@RequestMapping("/baseSdetail")
	@ResponseBody
	public Supplier baseSdetail(Supplier vo){
		//System.out.println(vo.getProviderId());
		return supplierService.queryBaseS(vo.getProviderId());
	}
	
	/**
	 * 删除供应商信息附件
	 * @param vo
	 * @param request
	 * @return
	 */
	@RequestMapping("/deletePai")
	@ResponseBody
	public String deletePai(ProviderAccessoryInfo vo){
		try{
			supplierService.deletePai(vo.getProviderAccessoryId());
			return "true";
		} catch(Exception e) {
			return "false";
		}
	}
	
	/**
	 * 删除软硬件代理
	 * @param vo
	 * @param request
	 * @return
	 */
	@RequestMapping("/deletePshp")
	@ResponseBody
	public String deletePshp(ProviderSoftHardwareProxy vo){
		try{
			supplierService.deletePshp(vo.getProxyId());
			return "true";
		} catch(Exception e) {
			return "false";
		}
	}
	
	/**
	 * 删除软硬件代理
	 * @param vo
	 * @param request
	 * @return
	 */
	@RequestMapping("/deletePsc")
	@ResponseBody
	public String deletePsc(ProviderServiceCertificate vo){
		try{
			supplierService.deletePsc(vo.getServiceId());
			return "true";
		} catch(Exception e) {
			return "false";
		}
	}
	
	/**
	 * 删除供应商
	 * @param vo
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteSupplier")
	@ResponseBody
	public boolean deleteSupplier(Supplier vo){
		return supplierService.deleteSupplier(vo.getProviderId());
	}
	
	/**
	 * 删除供应商
	 * @param vo
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteSuppliers")
	@ResponseBody
	public Map<String, Object> deleteSuppliers(HttpServletRequest request){
		String providerIds = request.getParameter("providerIds");
		String res = supplierService.deleteSuppliers(providerIds);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("res", res);
		return m;
	}
	
	
	
	/**
	 * 查询组织树
	 * @param sysProviderOrganization
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getOrganizationTreeVal")
	@ResponseBody
	public Map<String, Object> getOrganizationTreeVal(SysProviderOrganization sysProviderOrganization) throws Exception {
		List<OrganizationBean> orgLst = organizationService.getOrganizationTreeVal();
		
		String OrgId = sysProviderOrgService.queryOrgByProvider(sysProviderOrganization.getProviderId());
		if(OrgId!=""){
			if(OrgId.substring(OrgId.length()-1, OrgId.length()).equals(",")){
				OrgId = OrgId.substring(0,OrgId.length()-1);
			}
		}
		String orgLstJson = JsonUtil.toString(orgLst);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("orgLstJson", orgLstJson);
		result.put("OrgId", OrgId);
		return result;
	}
	
	/**
	 * 添加组织供应商关系
	 * @param sysProviderOrganization
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addProviderOrganization")
	@ResponseBody
	public boolean addProviderOrganization(SysProviderOrganization sysProviderOrganization)
			throws Exception {
		return sysProviderOrgService.addSysProviderOrg(sysProviderOrganization);
	}
	
	/**
	 * 查询服务于改组织的所有供应商
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryProviderInOrg")
	@ResponseBody
	public List<ProviderInfoBean> queryProviderInOrg(HttpServletRequest request)
			throws Exception {
		String orgId = request.getParameter("organizationID");
		return sysProviderOrgService.queryProviderByOrgId(Integer.parseInt(orgId));
	}
	
	/**
	 * 查询摸组织下的供应商用户
	 * @param sysUserBean
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listProviderUser")
	@ResponseBody
	public Map<String, Object> listProviderUser(SysUserInfoBean sysUserBean, HttpServletRequest request)
			throws Exception {
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<SysUserInfoBean> page=new Page<SysUserInfoBean>(); 
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("userAccount", sysUserBean.getUserAccount());
		paramMap.put("userName", sysUserBean.getUserName());
		paramMap.put("groupIdFilter", sysUserBean.getGroupIdFilter());
		paramMap.put("providerId", request.getParameter("providerId"));
		paramMap.put("organizationID", request.getParameter("organizationID"));
		page.setParams(paramMap);
		List<SysUserInfoBean> providerUsers=sysProviderOrgService.getProviderUser(page);
		sysUserBean.setProviderId(Integer.parseInt(request.getParameter("providerId")));
		sysUserBean.setOrganizationId(Integer.parseInt(request.getParameter("organizationID")));
		int total=sysProviderOrgService.getTotalCount(sysUserBean);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", providerUsers);
		return result;
	}

	/**
	 * 查询硬件代理单条记录
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryProviderServiceCertificate")
	@ResponseBody
	public  ProviderServiceCertificate queryProviderServiceCertificate(HttpServletRequest request)
			throws Exception {
		String serviceId = request.getParameter("serviceId");
		return supplierService.queryProviderServiceCertificate(Integer.parseInt(serviceId));
	}
	
	/**
	 * 查询服务代理单条记录
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryProviderSoftHardwareProxy")
	@ResponseBody
	public  ProviderSoftHardwareProxy queryProviderSoftHardwareProxy(HttpServletRequest request)
			throws Exception {
		String proxyId = request.getParameter("proxyId");
		return supplierService.queryProviderSoftHardwareProxy(Integer.parseInt(proxyId));
	}
}
