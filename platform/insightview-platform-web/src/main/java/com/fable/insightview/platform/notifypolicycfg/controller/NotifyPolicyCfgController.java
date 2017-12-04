package com.fable.insightview.platform.notifypolicycfg.controller;

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

import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.message.entity.PhoneVoiceBean;
import com.fable.insightview.platform.notifypolicycfg.entity.NotifyPolicyCfgBean;
import com.fable.insightview.platform.notifypolicycfg.entity.PolicyTypeBean;
import com.fable.insightview.platform.notifypolicycfg.service.INotifyPolicyCfgService;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.service.ISysUserService;

/**
 * 通知策略
 *
 */
@Controller
@RequestMapping("/platform/notifyPolicyCfg")
public class NotifyPolicyCfgController {
	private Logger logger = LoggerFactory.getLogger(NotifyPolicyCfgController.class);
	@Autowired
	INotifyPolicyCfgService policyCfgService;
	@Autowired
	ISysUserService sysUserService;
	
	/**
	 * 跳转至通知策略页面
	 */
	@RequestMapping("/toNotifyPolicyCfg")
	public ModelAndView toNotifyPolicyCfg(String navigationBar) {
		logger.info("进入通知策略界面");
		ModelAndView mv = new ModelAndView("platform/notifypolicycfg/notifyPolicyCfg_list");
		mv.addObject("navigationBar", navigationBar);
		return mv;
	}
	
	/**
	 * 加载列表数据
	 */
	@RequestMapping("/listNotifyPolicyCfg")
	@ResponseBody
	public Map<String, Object> listNotifyPolicyCfg(NotifyPolicyCfgBean policyCfgBean){
		logger.info("开始加载通知策略数据。。。。。。。。");
		logger.info("查询规则名称为：：" + policyCfgBean.getPolicyName());
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<NotifyPolicyCfgBean> page = new Page<NotifyPolicyCfgBean>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("policyName", policyCfgBean.getPolicyName());
		page.setParams(paramMap);
		List<NotifyPolicyCfgBean> notifyCfgList = policyCfgService.selectNotifyPolicyCfg(page);
		int total = page.getTotalRecord();
		logger.info("total=====" + total);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", notifyCfgList);
		logger.info("加载数据结束。。。");
		return result;
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delNotifyCfg")
	@ResponseBody
	public boolean delNotifyCfg(int policyId){
		logger.info("删除id为：{}的通知策略" , policyId);
		return policyCfgService.delNotifyCfg(policyId);
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping("/delNotifyCfgs")
	@ResponseBody
	public boolean delNotifyCfg(HttpServletRequest request){
		String policyIds = request.getParameter("policyIds");
		logger.info("批量删除的通知策略的id为：{}" , policyIds);
		return policyCfgService.delNotifyCfgs(policyIds);
	}
	
	/**
	 * 跳转至通知策略新增/编辑通知策略页面页面
	 */
	@RequestMapping("/toShowAddNotifyCfg")
	public ModelAndView toShowAddNotifyCfg(HttpServletRequest request) {
		String editFlag = request.getParameter("editFlag");
		String policyId = request.getParameter("policyId");
		logger.info("进入通知策略{}界面",editFlag);
		ModelAndView mv = new ModelAndView("platform/notifypolicycfg/notifyPolicyCfg_edit").addObject("editFlag", editFlag).addObject("policyId", policyId);
		return mv;
	}
	
	/**
	 * 获得所有的策略类型
	 * @return
	 */
	@RequestMapping("/getAllPolicyType")
	@ResponseBody
	public List<PolicyTypeBean> getAllPolicyType(){
		return policyCfgService.getAllPolicyType();
	}
	
	/**
	 * 获得初始化策略类型对应的模板内容
	 */
	@RequestMapping("/getTypeContent")
	@ResponseBody
	public Map<String, Object> getTypeContent(HttpServletRequest request){
		int typeId = Integer.parseInt(request.getParameter("typeId"));
		logger.info("选择的id为：{}的策略类型。",typeId);
		return policyCfgService.getTypeContent(typeId);
	}
	
	/**
	 * 初始化通知策略的模板内容
	 */
	@RequestMapping("/getPolicyContent")
	@ResponseBody
	public Map<String, Object> getPolicyContent(HttpServletRequest request){
		int policyId = Integer.parseInt(request.getParameter("policyId"));
		logger.info("获得策略的id为：{}的通知模板。",policyId);
		return policyCfgService.getPolicyContent(policyId);
	}
	
	/**
	 * 跳转至新增/编辑模板页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/toShowEditContent")
	public ModelAndView toShowEditContent(HttpServletRequest request) {
		String editContentFlag = request.getParameter("editContentFlag");
		String type = request.getParameter("type");
		String contentId = request.getParameter("contentId");
		logger.info("进入{}模板界面", editContentFlag);
		// 获得所有的录音
		List<PhoneVoiceBean> voiceList = policyCfgService.getAllVoice();
		ModelAndView mv = new ModelAndView(
				"platform/notifypolicycfg/notifyContent_edit").addObject(
				"editContentFlag", editContentFlag).addObject("type", type)
				.addObject("voiceList", voiceList).addObject("contentId", contentId);
		return mv;
	}
	
	/**
	 * 用户列表
	 */
	@RequestMapping("/listSysUser")
	@ResponseBody
	public Map<String, Object> listSysUser(SysUserInfoBean sysUserBean)
			throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);

		List<SysUserInfoBean> userLst = sysUserService
				.getSysUserByNotifyFilter(sysUserBean, flexiGridPageInfo);
		// 获取总记录数
		int total = sysUserService.getTotalCountByNotifyFilter(sysUserBean);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", userLst);
		return result;
	}
	
	/**
	 * 校验通知策略规则名称是否已存在
	 * @return true 已存在
	 * 		   false 不存在
	 */
	@RequestMapping("/checkCfgName")
	@ResponseBody
	public boolean checkCfgName(NotifyPolicyCfgBean bean){
		return policyCfgService.checkCfgName(bean);
	}
	
	/**
	 * 新增或编辑通知策略
	 */
	@RequestMapping("/doEditPolicyCfg")
	@ResponseBody
	public boolean doEditPolicyCfg(NotifyPolicyCfgBean bean,HttpServletRequest request){
		String editFlag = request.getParameter("editFlag");
		return policyCfgService.doEditPolicyCfg(bean, editFlag);
	}
	
	/**
	 * 初始化通知策略
	 */
	@RequestMapping("/doInitPolicyCfg")
	@ResponseBody
	public Map<String, Object> doInitPolicyCfg(int policyId){
		return policyCfgService.doInitPolicyCfg(policyId);
	}
	
	/**
	 * 跳转至查看模板页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/toShowNotifyCfgDetail")
	public ModelAndView toShowNotifyCfgDetail(HttpServletRequest request) {
		String policyId = request.getParameter("policyId");
		logger.info("进入通知策略id为：{}的查看页面界面",policyId);
		ModelAndView mv = new ModelAndView("platform/notifypolicycfg/notifyPolicyCfg_detail").addObject("policyId", policyId);
		return mv;
	}
	
	/**
	 * 获得所有的通知策略
	 */
	@RequestMapping("/getAllNotifyPolicy")
	@ResponseBody
	public List<NotifyPolicyCfgBean> getAllNotifyPolicy(){
		return policyCfgService.getAllNotifyPolicy();
	}
	
}
