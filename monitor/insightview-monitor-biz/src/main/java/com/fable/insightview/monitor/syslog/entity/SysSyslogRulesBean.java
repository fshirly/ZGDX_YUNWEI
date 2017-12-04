package com.fable.insightview.monitor.syslog.entity;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * syslog规则
 * 
 */
@Alias("syslogRule")
public class SysSyslogRulesBean {
	@NumberGenerator(name = "MonitorSyslogRulesPK")
	private Integer ruleID;
	private String ruleName;
	private String filterExpression;
	private String serverIP;
	private String alarmOID;
	private String note;

	// 是否全局
	private Integer isAll;
	private String facilitylist;
	private String severitylist;
	private String keyword;
	private String collectorIds;
	private String isOffline;
	public Integer getRuleID() {
		return ruleID;
	}
	public void setRuleID(Integer ruleID) {
		this.ruleID = ruleID;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getFilterExpression() {
		return filterExpression;
	}
	public void setFilterExpression(String filterExpression) {
		this.filterExpression = filterExpression;
	}
	public String getServerIP() {
		return serverIP;
	}
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}
	public String getAlarmOID() {
		return alarmOID;
	}
	public void setAlarmOID(String alarmOID) {
		this.alarmOID = alarmOID;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Integer getIsAll() {
		return isAll;
	}
	public void setIsAll(Integer isAll) {
		this.isAll = isAll;
	}
	public String getFacilitylist() {
		return facilitylist;
	}
	public void setFacilitylist(String facilitylist) {
		this.facilitylist = facilitylist;
	}
	public String getSeveritylist() {
		return severitylist;
	}
	public void setSeveritylist(String severitylist) {
		this.severitylist = severitylist;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getCollectorIds() {
		return collectorIds;
	}
	public void setCollectorIds(String collectorIds) {
		this.collectorIds = collectorIds;
	}
	public String getIsOffline() {
		return isOffline;
	}
	public void setIsOffline(String isOffline) {
		this.isOffline = isOffline;
	}
	
}
