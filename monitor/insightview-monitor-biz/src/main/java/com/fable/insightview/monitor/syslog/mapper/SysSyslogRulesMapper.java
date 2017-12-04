package com.fable.insightview.monitor.syslog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.monitor.syslog.entity.SysSyslogRulesBean;
import com.fable.insightview.platform.page.Page;

/**
 * syslog规则mapper接口
 * 
 */
public interface SysSyslogRulesMapper {
	/**
	 * 分页查询syslog规则
	 */
	List<SysSyslogRulesBean> getSyslogRules(Page<SysSyslogRulesBean> page);
	
	/**
	 * 根据ID获得syslog规则信息
	 */
	SysSyslogRulesBean getSyslogRuleByID(int ruleID);
	
	/**
	 * 根据条件查询syslog规则
	 */
	List<SysSyslogRulesBean> getRuleByCondition(SysSyslogRulesBean bean);
	
	/**
	 * 新增syslog规则
	 */
	int insertSyslogRule(SysSyslogRulesBean bean);
	
	/**
	 * 根据规则ID跟新
	 */
	int updateSyslogRuleByID(SysSyslogRulesBean bean);
	
	/**
	 * 根据规则ID删除
	 */
	boolean delSyslogRuleByID(int ruleID);
	
	/**
	 * 根据规则IDs批量删除
	 */
	boolean delSyslogRuleByIDs(@Param("ruleIDs") String ruleIDs);
}
