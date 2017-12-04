package com.fable.insightview.monitor.syslog.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.monitor.harvester.entity.SysServerHostInfo;
import com.fable.insightview.monitor.syslog.entity.SysSyslogRulesBean;
import com.fable.insightview.monitor.syslog.entity.SysSyslogTaskBean;
import com.fable.insightview.monitor.syslog.service.ISysSyslogRulesService;
import com.fable.insightview.monitor.syslog.service.ISysSyslogTaskService;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.page.Page;

/**
 * syslog规则
 * 
 * 
 */
@Controller
@RequestMapping("/monitor/syslogRule")
public class SysSyslogRulesController {
	private final Logger logger = LoggerFactory
			.getLogger(SysSyslogRulesController.class);
	@Autowired
	ISysSyslogRulesService syslogRulesService;
	@Autowired
	ISysSyslogTaskService syslogTaskService;

	/**
	 * 跳转到syslog规则列表页面
	 */
	@RequestMapping("/toSyslogRuleList")
	public ModelAndView toSyslogRuleList(String navigationBar) {
		return new ModelAndView("monitor/syslog/syslogRule_list").addObject(
				"navigationBar", navigationBar);
	}

	/**
	 * 加载syslog规则列表
	 */
	@RequestMapping("/listSyslogRules")
	@ResponseBody
	public Map<String, Object> listSyslogRules(SysSyslogRulesBean bean,
			HttpServletRequest request) {
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<SysSyslogRulesBean> page = new Page<SysSyslogRulesBean>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ruleName", bean.getRuleName());
		paramMap.put("serverIP", bean.getServerIP());
		paramMap.put("isAll", bean.getIsAll());
		page.setParams(paramMap);
		Map<String, Object> result = syslogRulesService.getSyslogTasks(page);
		return result;
	}

	/**
	 * 跳转至新增页面
	 */
	@RequestMapping("/toAddSyslogRule")
	public ModelAndView toAddSyslogRule() {
		return new ModelAndView("monitor/syslog/syslogRule_add");
	}

	/**
	 * 采集机列表
	 * 
	 * @return
	 */
	@RequestMapping("/listSyslogCollector")
	@ResponseBody
	public Map<String, Object> listSyslogCollector(HttpServletRequest request) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		return syslogRulesService.listSyslogCollector(paramMap);
	}
	
	/**
	 * 离线采集机列表
	 * @return
	 */
	@RequestMapping("/listOfflineSyslogCollector")
	@ResponseBody
	public Map<String, Object> listOfflineSyslogCollector(){
		return syslogRulesService.listOfflineSyslogCollector();
	}
	
	/**
	 * 检验规则名称是否重复
	 */
	@RequestMapping("/checkRuleName")
	@ResponseBody
	public boolean checkRuleName(SysSyslogRulesBean bean){
		return syslogRulesService.checkRuleName(bean);
	}
	
	/**
	 * 新增
	 */
	@RequestMapping("/doAddSyslogRule")
	@ResponseBody
	public Map<String, Object> doAddSyslogRule(SysSyslogRulesBean bean,HttpServletRequest request){
		//新增规则结果
		boolean addRuleFlag = false;
		//是否选择采集机下发任务
		boolean isHaveTask = true;
		//新增任务结果
		boolean addTaskFlag = false;
		if("0".equals(bean.getIsOffline())){
			bean.setIsOffline(null);
		}
		Map<String, Object> addRuleResultMap = syslogRulesService
				.doAddSyslogRule(bean);
		if (addRuleResultMap != null) {
			//新增syslog规则
			int insertCount = Integer.parseInt(addRuleResultMap.get("insertCount").toString());
			
			//新增规则成功后，如果选择采集机则创建syslog任务
			if (insertCount > 0) {
				addRuleFlag = true;
				int ruleID = Integer.parseInt(addRuleResultMap.get("ruleID")
						.toString());
				String collectorIds = bean.getCollectorIds();
				if (!"".equals(collectorIds) && collectorIds != null) {
					String[] splits = collectorIds.split(",");
					addTaskFlag = syslogRulesService.addSyslogTask(splits, ruleID);
				} else {
					isHaveTask = false;
					logger.info("未选择采集机，不创建syslog任务！");
					
				}
			}
		}
		//返回结果
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("addRuleFlag", addRuleFlag);
		result.put("isHaveTask", isHaveTask);
		result.put("addTaskFlag", addTaskFlag);
		return result;
	}
	
	/**
	 * 跳转至规则查看详情页面
	 */
	@RequestMapping("/toSyslogRuleDetail")
	public ModelAndView toSyslogRuleDetail(HttpServletRequest request) {
		String ruleID = request.getParameter("ruleID");
		request.setAttribute("ruleID", ruleID);
		return new ModelAndView("monitor/syslog/syslogRule_detail");
	}
	
	/**
	 * 查看规则详情
	 * 
	 */
	@RequestMapping("/initRuleDetail")
	@ResponseBody
	public SysSyslogRulesBean initRuleDetail(SysSyslogRulesBean bean) {
		logger.info("初始化syslog规则详情信息，id为："+bean.getRuleID());
		return syslogRulesService.initRuleDetail(+bean.getRuleID());
	}
	
	/**
	 * 获得已经下发的采集机
	 */
	@RequestMapping("/getIssuedCollectors")
	@ResponseBody
	public Map<String, Object> getIssuedCollectors(
			HttpServletRequest request) {
		int ruleID = -1;
		if (!"".equals(request.getParameter("ruleID"))
				&& request.getParameter("ruleID") != null) {
			ruleID = Integer.parseInt(request.getParameter("ruleID"));
			SysSyslogTaskBean bean = new SysSyslogTaskBean();
			bean.setRuleID(ruleID);
			return syslogTaskService.getIssuedCollectors(bean);
		}
		return null;
	}
	
	/**
	 * 校验规则是否可以编辑
	 * return 没有完成的syslog任务的ids
	 */
	@RequestMapping("/checkIsEdit")
	@ResponseBody
	public String checkIsEdit(int ruleID){
		return syslogRulesService.checkIsEdit(ruleID);
	}
	
	/**
	 * 跳转至规则编辑页面
	 */
	@RequestMapping("/toModifySyslogRule")
	public ModelAndView toModifySyslogRule(HttpServletRequest request) {
		String ruleID = request.getParameter("ruleID");
		request.setAttribute("ruleID", ruleID);
		return new ModelAndView("monitor/syslog/syslogRule_modify");
	}
	
	/**
	 * 规则编辑页面初始化
	 */
	@RequestMapping("/initRuleInfo")
	@ResponseBody
	public SysSyslogRulesBean initRuleInfo(int ruleID){
		return syslogRulesService.initRuleInfo(ruleID);
	}
	
	/**
	 * 更新
	 */
	@RequestMapping("/doUpdateSyslogRule")
	@ResponseBody
	public Map<String, Object> doUpdateSyslogRule(SysSyslogRulesBean bean,HttpServletRequest request){
		//编辑规则结果
		boolean updateRuleFlag = false;
		//是否有采集机下发任务
		boolean isHaveTask = false;
		//编辑任务结果
		boolean updateTaskFlag = false;
		//编辑syslog规则
		updateRuleFlag = syslogRulesService.updateSyslogRule(bean);
		
		//编辑规则成功后，如果有syslog任务则更新syslog任务
		if (updateRuleFlag) {
			int ruleID = bean.getRuleID();
			String taskIDs = syslogRulesService.getSyslogTasks(ruleID);
			if(!"".equals(taskIDs) && taskIDs != null){
				isHaveTask = true;
				updateTaskFlag = syslogRulesService.updateSyslogTask(taskIDs);
			}
		}
		//返回结果
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("updateRuleFlag", updateRuleFlag);
		result.put("isHaveTask", isHaveTask);
		result.put("updateTaskFlag", updateTaskFlag);
		return result;
	}
	
	/**
	 * 校验规则是否可以删除
	 * 
	 */
	@RequestMapping("/checkIsDel")
	@ResponseBody
	public String checkIsDel(int ruleID){
		return syslogRulesService.checkIsDel(ruleID);
	}
	
	/**
	 * 删除规则
	 */
	@RequestMapping("/delRule")
	@ResponseBody
	public boolean delRule(Integer ruleID) {
		logger.info("删除的规则为：" + ruleID);
		return syslogRulesService.delRule(ruleID);
	}
	
	/**
	 * 批量删除规则
	 */
	@RequestMapping("/delRules")
	@ResponseBody
	public Map<String, Object> delRules(HttpServletRequest request){
		boolean delResult = true;
		String ruleIDs = request.getParameter("ruleIDs");
		Map<String, Object> map = syslogRulesService.getTaskIDsByRuleIDs(ruleIDs);
		String delRuleIds = map.get("delRuleIds").toString();
		String undelRuleNames = map.get("undelRuleNames").toString();
		if(!"".equals(delRuleIds) && delRuleIds != null){
			delResult = syslogRulesService.delRules(ruleIDs);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("delResult", delResult);
		result.put("undelRuleNames", undelRuleNames);
		return result;
	}
	
	/**
	 * 跳转至规则编辑页面
	 */
	@RequestMapping("/toAddCollector")
	public ModelAndView toAddCollector(HttpServletRequest request) {
		String ruleID = request.getParameter("ruleID");
		String isOffline = request.getParameter("isOffline");
		request.setAttribute("ruleID", ruleID);
		request.setAttribute("isOffline", isOffline);
		return new ModelAndView("monitor/syslog/syslogRuleAddCollector");
	}
	
	/**
	 * 未下发的采集机列表
	 */
	@RequestMapping("/getUnIssuedCollectors")
	@ResponseBody
	public Map<String, Object> getUnIssuedCollectors(HttpServletRequest request,SysServerHostInfo bean) {
		String ruleID = request.getParameter("ruleID");
		String isOffline = request.getParameter("isOffline");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ipaddress", bean.getIpaddress());
		paramMap.put("isOffline", isOffline);
		if(!"".equals(ruleID) && ruleID != null){
			paramMap.put("filterRuleID", Integer.parseInt(ruleID));
		}
		return syslogRulesService.listSyslogCollector(paramMap);
	}
	
	/**
	 * 添加新的采集机
	 */
	@RequestMapping("/doAddCollectors")
	@ResponseBody
	public boolean doAddCollectors(SysSyslogRulesBean bean){
		String collextorIds = bean.getCollectorIds();
		String[] splits = collextorIds.split(",");
		int ruleID = bean.getRuleID();
		return syslogRulesService.addSyslogTask(splits, ruleID);
	}
}
