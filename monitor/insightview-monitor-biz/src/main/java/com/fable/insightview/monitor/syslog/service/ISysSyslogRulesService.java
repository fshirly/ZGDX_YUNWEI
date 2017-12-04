package com.fable.insightview.monitor.syslog.service;

import java.util.Map;

import com.fable.insightview.monitor.harvester.entity.SysServerHostInfo;
import com.fable.insightview.monitor.syslog.entity.SysSyslogRulesBean;
import com.fable.insightview.platform.page.Page;

/**
 * syslog规则服务层
 *
 */
public interface ISysSyslogRulesService {
	/**
	 * syslog规则分页查询信息
	 */
	public Map<String, Object> getSyslogTasks(Page<SysSyslogRulesBean> page);
	
	/**
	 * syslog采集机列表
	 */
	Map<String, Object> listSyslogCollector(Map map);
	
	/**
	 * syslog离线采集机任务列表
	 */
	Map<String, Object> listOfflineSyslogCollector();
	
	/**
	 * 检验规则名称是否重复
	 */
	boolean checkRuleName(SysSyslogRulesBean bean);
	
	/**
	 * 新增规则
	 */
	public Map<String, Object> doAddSyslogRule(SysSyslogRulesBean bean);
	
	/**
	 * 新增任务
	 */
	boolean addSyslogTask(String[] splits, int ruleID);
	
	/**
	 * 查看规则详情
	 * 
	 */
	SysSyslogRulesBean initRuleDetail(int ruleID);
	
	/**
	 * 校验规则是否可以编辑、删除
	 */
	String checkIsEdit(int ruleID);
	
	/**
	 * 规则编辑页面初始化
	 */
	SysSyslogRulesBean initRuleInfo(int ruleID);
	
	/**
	 * 更新规则
	 */
	boolean updateSyslogRule(SysSyslogRulesBean bean);
	
	/**
	 * 获得规则对应的syslog任务
	 */
	String getSyslogTasks(int ruleID);
	
	/**
	 * 更新任务
	 */
	boolean updateSyslogTask(String taskIDs);
	
	/**
	 * 删除规则
	 */
	boolean delRule(Integer ruleID);
	
	/**
	 * 根据规则获得下面能删除、不能删除任务ids
	 */
	Map<String, Object> getTaskIDsByRuleIDs(String ruleIDs);
	
	/**
	 * 批量删除规则
	 */
	boolean delRules(String ruleIDs);
	
	/**
	 * 校验规则是否可以删除
	 */
	String checkIsDel(int ruleID);
	
}
