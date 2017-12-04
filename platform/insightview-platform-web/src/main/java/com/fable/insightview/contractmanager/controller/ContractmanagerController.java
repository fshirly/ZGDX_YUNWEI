package com.fable.insightview.contractmanager.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
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

import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.contractmanager.entity.ComboxBean;
import com.fable.insightview.platform.contractmanager.entity.ContractAccessoryInfo;
import com.fable.insightview.platform.contractmanager.entity.ContractPayment;
import com.fable.insightview.platform.contractmanager.entity.ProjectContract;
import com.fable.insightview.platform.contractmanager.entity.ProjectContractChange;
import com.fable.insightview.platform.contractmanager.service.ContractManagerService;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.page.Page;


@Controller
@RequestMapping("/contractmanager")
public class ContractmanagerController {
	@Autowired
	private ContractManagerService  contractManagerService;
	private final static Logger logger = LoggerFactory.getLogger(ContractmanagerController.class);
	/*
	 * 调整页面
	 */
	@RequestMapping("/contractManager_list")
    public ModelAndView tocontractManagerList(String navigationBar) {
		return new ModelAndView("platform/projectManager/contractManager/contractManager_list").addObject("navigationBar", navigationBar);
	}
	/*
	 * 跳转到编辑页
	 */
	@RequestMapping("/contractManager_edit")
    public ModelAndView tocontractManager_edit(HttpServletRequest request) {
		Integer contractid=Integer.parseInt(request.getParameter("contractid"));
		Integer choosetab=Integer.parseInt(request.getParameter("choosetab"));
		ModelAndView mv = new ModelAndView();
		ProjectContract projectContract=contractManagerService.contractManageEdit(contractid);
		//保证金(保留两位小数)
		Double cashDeposit = projectContract.getCashDeposit();
		DecimalFormat df = new DecimalFormat("#.00");
		if(cashDeposit != null) {
			mv.addObject("cashDeposit", df.format(cashDeposit));
		}
		//合同金额(保留两位小数)
		BigDecimal moneyAmount = projectContract.getMoneyAmount();
		if(moneyAmount != null) {
			mv.addObject("moneyAmount", df.format(moneyAmount));
		}
		//合同余额(保留两位小数)
		Integer remainder = projectContract.getRemainder();
		if(remainder != null && remainder.intValue() > 0) {
			mv.addObject("remainder", df.format(remainder));
		} else if (remainder != null && remainder.intValue() == 0){
			mv.addObject("remainder", "0.00");
		} else {
			mv.addObject("remainder", df.format(moneyAmount));
		}
		mv.addObject("projectContract",projectContract);
		mv.addObject("choosetab",choosetab);
		mv.setViewName("platform/projectManager/contractManager/contractManager_edit");
		return mv;
	}
	/*
	 * 跳转到添加页
	 */
	@RequestMapping("/contractManager_add")
    public ModelAndView tocontractManager_add() {
		return new ModelAndView("platform/projectManager/contractManager/contractManager_add");
	}
	/*
	 * 跳转到添加计划页
	 */
	@RequestMapping("/contractPaymentPlanadd")
	public ModelAndView toContractPaymentPlanaddView(HttpServletRequest request){
		Integer contractid=Integer.parseInt(request.getParameter("contractid"));
		ModelAndView mv = new ModelAndView();
		mv.addObject("contractid",contractid);
		mv.setViewName("platform/projectManager/contractManager/contractPaymentPlan_add");
		return mv;
	}
	/*
	 * 调转到计划外付款页面
	 */
	@RequestMapping("/contractPaymentNoPlanadd")
	public ModelAndView toContractPaymentNoPlanaddView(HttpServletRequest request){
		Integer contractid=Integer.parseInt(request.getParameter("contractid"));
		ModelAndView mv = new ModelAndView();
		mv.addObject("contractid",contractid);
		mv.setViewName("platform/projectManager/contractManager/contractPaymentNoPlan_add");
		return mv;
	}
	/*
	 * 调转到付款信息修改页
	 */
	@RequestMapping("/contractPaymentInfo_update")
	@ResponseBody
	public ModelAndView toUpdateContractPaymentInfo(HttpServletRequest request){
		Integer paymentID=Integer.parseInt(request.getParameter("paymentID"));
		ContractPayment contractPayment=contractManagerService.queryContractPaymentInfo(paymentID);
		ModelAndView mv = new ModelAndView();
		mv.addObject("ContractPayment",contractPayment);
		mv.setViewName("platform/projectManager/contractManager/contractPayment_Update");
		return mv;
	}
	/*
	 * 跳转到详情页
	 */
	@RequestMapping("/contractManager_detail")
    public ModelAndView tocontractManager_detail(HttpServletRequest request) {
		Integer contractid=Integer.parseInt(request.getParameter("contractid"));
		ModelAndView mv = new ModelAndView();
		ProjectContract projectContract=contractManagerService.contractManageDetail(contractid);
		//保证金(保留两位小数)
		Double cashDeposit = projectContract.getCashDeposit();
		DecimalFormat df = new DecimalFormat("#.00");
		if(cashDeposit != null) {
			mv.addObject("cashDeposit", df.format(cashDeposit));
		}
		//合同金额(保留两位小数)
		BigDecimal moneyAmount = projectContract.getMoneyAmount();
		if(moneyAmount != null) {
			mv.addObject("moneyAmount", df.format(moneyAmount));
		}
		//合同余额(保留两位小数)
		Integer remainder = projectContract.getRemainder();
		if(remainder != null && remainder.intValue() > 0) {
			mv.addObject("remainder", df.format(remainder));
		} else if (remainder != null && remainder.intValue() == 0){
			mv.addObject("remainder", "0.00");
		} else {
			mv.addObject("remainder", df.format(moneyAmount));
		}

		mv.addObject("projectContractInfo",projectContract);
		mv.setViewName("platform/projectManager/contractManager/contractManager_detail");
		return mv;
	}
	/*
	 * 跳转到付款信息详情页
	 */
	@RequestMapping("/contractPaymentInfoDetail")
	public ModelAndView toContractPaymentInfo(HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		Integer paymentID=Integer.parseInt(request.getParameter("paymentID"));
		ContractPayment contractPayment=contractManagerService.queryContractPaymentInfo(paymentID);
		mv.addObject("ContractPayment",contractPayment);
		mv.setViewName("platform/projectManager/contractManager/contractPayment_Detail");
		return mv;
	}
	/*
	 * 跳转到变更新增页面
	 */
	@RequestMapping("/contractChaneInfo_add")
	public ModelAndView toContractChaneAdd(HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		Integer contractid=Integer.parseInt(request.getParameter("contractid"));
		String singtime = request.getParameter("singtime");
		mv.addObject("contractid",contractid);
		mv.addObject("singtime",singtime);
		mv.setViewName("platform/projectManager/contractManager/contractChaneAdd");
		return mv;
	}
	/*
	 * 跳转到变更修改页
	 */
	@RequestMapping("/contractChaneInfoupdateview")
	public ModelAndView toContractChangeUpdate(HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		Integer id=Integer.parseInt(request.getParameter("id"));
		String singtime = request.getParameter("singtime");
		ProjectContractChange projectContractChange=contractManagerService.queryProjectContractChange(id);
		mv.addObject("ProjectContractChange",projectContractChange);
		mv.addObject("singtime",singtime);
		mv.setViewName("platform/projectManager/contractManager/contractChangeUpdate");
		return mv;
	}
	/*
	 * 跳转到变更详情页
	 */
	@RequestMapping("/contractchangeInfoDetail")
	public ModelAndView toContractChangeDetail(HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		Integer id=Integer.parseInt(request.getParameter("id"));
		ProjectContractChange projectContractChange=contractManagerService.queryProjectContractChange(id);
		mv.addObject("ProjectContractChange",projectContractChange);
		mv.setViewName("platform/projectManager/contractManager/contractChangeDetail");
		return mv;
	}
	/*
	 *下拉框
	 */
	@RequestMapping("/projectcombox")
	@ResponseBody
	public List<ComboxBean>  getProjectCombox(){
		List<ComboxBean> pairs = new ArrayList<ComboxBean>();
		ComboxBean<String,String> bean=new ComboxBean<String,String>();
		bean.setId("");
		bean.setNeir("请选择..");
		pairs=contractManagerService.queryProjectCombox();
		pairs.add(0,bean);
		return pairs;
	}
	/*
	 * 下拉框(合同类型)
	 */
	@RequestMapping("/contracttypecombox")
	@ResponseBody
	public List<ComboxBean> getContractType(){
		ComboxBean<String,String> bean=new ComboxBean<String,String>();
		bean.setId("");
		bean.setNeir("请选择..");
		List<ComboxBean> list = new ArrayList<ComboxBean>();
		list=contractManagerService.queryContractTypeCombox();
		list.add(0,bean);
		return list;
	}
	/*
	 * 合同信息添加
	 */
	@RequestMapping("/contractmanagerinfoadd")
	@ResponseBody
	public boolean addContractManagerInfo(ProjectContract projectContract){
		boolean flag=true;
		try {
			List<ContractAccessoryInfo> list=JsonUtil.toList(projectContract.getFileCachearrat(),ContractAccessoryInfo.class);
			contractManagerService.insertContractInfo(projectContract,list);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}
   /*
    * 合同信息查看
    */
	@RequestMapping("/contractManagerInfo_list")
	@ResponseBody
	public Map<String,Object> getContractManagerInfo_list(ProjectContract projectContract,HttpServletRequest request){
		//获取当前每页行数和当前页数
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil.getFlexiGridPageInfo(request);
		Page<ProjectContract> page = new Page<ProjectContract>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("title", projectContract.getTitle());
		paramMap.put("validTimeBegin", projectContract.getValidTimeBegin());
		paramMap.put("validTimeEnd", projectContract.getValidTimeEnd());
		paramMap.put("owner", projectContract.getOwner());
		paramMap.put("partyB", projectContract.getPartyB());
		page.setParams(paramMap);
		// 查询分页数据
		List<ProjectContract> list = contractManagerService.queryContractManagerList(page);
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", list);
		logger.info("结束...获取页面显示数据");
		return result;
	}
	/*
	 * 合同附件信息
	 */
	@RequestMapping("/fileupdate_list")
	@ResponseBody
	public Map<String,Object> getfileInfo_list(HttpServletRequest request){
		//获取当前每页行数和当前页数
		//FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil.getFlexiGridPageInfo(request);
		//Page<ContractAccessoryInfo> page = new Page<ContractAccessoryInfo>();
		//page.setPageNo(flexiGridPageInfo.getPage());
		//page.setPageSize(flexiGridPageInfo.getRp());
		// 设置分页参数
		//page.setPageNo(flexiGridPageInfo.getPage());
		//page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Integer contractID=Integer.parseInt(request.getParameter("contractid"));
		//Map<String, Object> paramMap = new HashMap<String, Object>();
		//paramMap.put("contractID",contractID);
		//page.setParams(paramMap);
		// 查询分页数据
		List<ContractAccessoryInfo> list = contractManagerService.queryContractFileInfoList(contractID);
		//int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		//result.put("total", totalCount);
		result.put("rows", list);
		logger.info("结束...获取页面显示数据");
		return result;
	}
	/*
	 * 修改
	 */
	@RequestMapping("/contractManagerInfo_update")
	@ResponseBody
	public boolean updatecontractManagerInfo(ProjectContract projectContract,HttpServletRequest request){
		logger.info("执行updateProvider()方法，修改供应商信息");
		boolean flag=true;
		try {
			List<ContractAccessoryInfo> list=JsonUtil.toList(projectContract.getFileCachearrat(),ContractAccessoryInfo.class);
			contractManagerService.updatecontractManagerInfo(projectContract,list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}
	/*
	 * 计划付款查询页
	 */
	@RequestMapping("/contractpayrecord")
	@ResponseBody
	public Map<String,Object> getContractPaymentInfo(HttpServletRequest request){
		Integer contractId=Integer.parseInt(request.getParameter("contractid"));
		//获取当前每页行数和当前页数
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil.getFlexiGridPageInfo(request);
		Page<ContractPayment> page = new Page<ContractPayment>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("contractID", contractId);
		page.setParams(paramMap);
		// 查询分页数据
		List<ContractPayment> list = contractManagerService.queryContractPaymentList(page);
		//Double  paymentcount=contractManagerService.getContractPaymentCount(contractId);
		//FooterTotal footer=new FooterTotal();
		//footer.setPlanPayTime("总共");
		//footer.setAmount(paymentcount);
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", list);
		//result.put("footer",footer );
		logger.info("结束...获取页面显示数据");
		return result;
	}
	/*
	 * 增加计划
	 */
	@RequestMapping("/contractPaymentPlanInfo_add")
	@ResponseBody
	public boolean addContractPaymentPlan(ContractPayment contractPayment){
		boolean flag=true;
		try {
			contractManagerService.addContractPaymentPlan(contractPayment);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}
	/*
	 * 计划外付款
	 */
	@RequestMapping("/contractPaymentNoPlanInfo_add")
	@ResponseBody
	public boolean addContractPaymentNoPlan(ContractPayment contractPayment){
		boolean flag=true;
		try {
			contractManagerService.addContractPaymentNoPlan(contractPayment);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}
	/*
	 * 校验计划付款时间不能大于签订时间
	 */
	@RequestMapping("/contractPlanPaymentvalidatorTime")
	@ResponseBody
	public boolean validatorPaymenyPlanTime(ContractPayment contractPayment){
		return contractManagerService.validatorPlanPayTime(contractPayment);
	}
	/*
	 * 效验实际付款时间不能大于签订时间
	 */
	@RequestMapping("/contractPaymentvalidatorTime")
	@ResponseBody
	public boolean validatorPaymenyTime(ContractPayment contractPayment){
		return contractManagerService.validatorNoPlanPayTime(contractPayment);
	}
	/*
	 * 计划付款不能超过合同余额
	 */
	@RequestMapping("/contractPlanPaymentvalidatorCount")
	@ResponseBody
	public Double validatorPlanPaymenyTime(ContractPayment contractPayment){
		return contractManagerService.validatorPlanPaymeny(contractPayment);
	}
	/*
	 * 付款不能超过合同余额
	 */
	@RequestMapping("/contractNoPlanPaymentvalidatorCount")
	@ResponseBody
	public Double validatorNoPlanPaymenyTime(ContractPayment contractPayment){
		return contractManagerService.validatorNoPlanPaymeny(contractPayment);
	}
	/*
	 * 修改付款信息
	 */
	@RequestMapping("/contractPaymentinfoupdate")
	@ResponseBody
	public boolean  updateContractPayment(ContractPayment contractPayment){
		boolean flag=true;
		try {
			contractManagerService.updateContractPaymentInfo(contractPayment);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag=false;
			e.printStackTrace();
		}
		return flag;
	}
	/*
	 * 查看变更信息(list)
	 */
	@RequestMapping("/projectcontractchangelist")
	@ResponseBody
	public Map<String,Object> getContractChangeInfo(HttpServletRequest request){
		Integer contractId=Integer.parseInt(request.getParameter("contractid"));
		//获取当前每页行数和当前页数
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil.getFlexiGridPageInfo(request);
		Page<ProjectContractChange> page = new Page<ProjectContractChange>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("contractID", contractId);
		page.setParams(paramMap);
		// 查询分页数据
		List<ProjectContractChange> list = contractManagerService.queryProjectContractChangeInfo(page);
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", list);
		logger.info("结束...获取页面显示数据");
		return result;
	}
	/*
	 * 添加变更信息
	 */
	@RequestMapping("/contractChangeInfo_add")
	@ResponseBody
	public boolean addContractChangeInfo(ProjectContractChange projectContractChange){
		boolean flag=true;
		try {
			contractManagerService.addContractChangeInfo(projectContractChange);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag=false;
			e.printStackTrace();
		}
		return flag;
	}
	/*
	 * 修改变更信息
	 */
	@RequestMapping("/contractChaneInfo_update")
	@ResponseBody
	public boolean updateContractChangeInfo(ProjectContractChange projectContractChange){
		boolean flag=true;
		try {
			contractManagerService.updateProjectContractChangeInfo(projectContractChange);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag=false;
			e.printStackTrace();
		}
		return flag;
	}
	/*
	 * 删除变更信息
	 */
	@RequestMapping("/contractChaneInfodelete")
	@ResponseBody
	public boolean deleteProjectContractChange(Integer id){
		boolean flag=true;
		try {
			contractManagerService.deleteProjectContractChangeInfo(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag=false;
			e.printStackTrace();
		}
		return flag;
	}
	/*
	 * 批量删除变更信息
	 */
	@RequestMapping("/contractChaneInfoBathchdelete")
	@ResponseBody
	public boolean deleteBatchProjectContractChange(String ids){
		boolean flag=true;
		try {
			contractManagerService.deleteBatchProjectContractChangeInfo(ids);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag=false;
			e.printStackTrace();
		}
		return flag;
	}
	/*
	 * 删除付款信息contracepaymentInfodelete
	 */
	@RequestMapping("/contracepaymentInfodelete")
	@ResponseBody
	public boolean deleteContractPaymentInfo(Integer paymentID){
		boolean flag=true;
		try {
		    contractManagerService.deleteContractPaymentInfo(paymentID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag=false;
			e.printStackTrace();
		}
		return flag;
	}
	/*
	 * 删除合同信息
	 */
	@RequestMapping("/projectcontraceInfodelete")
	@ResponseBody
	public boolean deleteProjectContractInfo(Integer id){
		boolean flag=true;
		try {
		    contractManagerService.deleteProjectContractInfo(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag=false;
			e.printStackTrace();
		}
		return flag;
	}
	/*
	 * 批量删除合同信息
	 */
	@RequestMapping("/projectcontractInfoBathchdelete")
	@ResponseBody
	public boolean deleteBatchProjectContractInfo(String ids){
		boolean flag=true;
		try {
		    contractManagerService.deleteBatchProjectContractInfo(ids);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag=false;
			e.printStackTrace();
		}
		return flag;
	}
	/*
	 * 校验计划付款时间重复
	 */
	@RequestMapping("/contractPlanmentvalidatorTimeDouble")
	@ResponseBody
	public boolean validatorTimePlanTimeDouble(ContractPayment contractPayment){
		boolean flag=true;
			flag=contractManagerService.validatorTimePlanTimeDouble(contractPayment);
		return flag;
	}
	/*
	 * 获取已付款总数
	 */
	@RequestMapping("/projectcontractpaymentcount")
	@ResponseBody
	public Double getContractPaymentCount(HttpServletRequest request){
		Integer contractid=Integer.parseInt(request.getParameter("contractid"));
	    return contractManagerService.getContractPaymentCount(contractid);
		
	}
	/**
	 * 付款修改页计划付款信息校验
	 */
	@RequestMapping("/contractPlanPaymentvalidatorCountUpdate")
	@ResponseBody
	public Double getPlanPyment_update(ContractPayment contractPayment){
		return contractManagerService.getPlanPyment_update(contractPayment);
	}
	/*
	 * 付款信息修改页疾患计划付款信息校验
	 */
	@RequestMapping("/contractNoPlanPaymentvalidatorCountUpdate")
	@ResponseBody
	public Double getNoPlanPayment_update(ContractPayment contractPayment){
		return contractManagerService.getNoPlanPayment_update(contractPayment);
	}
}
