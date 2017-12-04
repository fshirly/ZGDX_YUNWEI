package com.fable.insightview.platform.serviceSerialNum.entity;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * 业务编号的生成规则和具体值
 * 
 * @author maow
 */
@Alias("serviceNumRule")
public class ServiceNumRule {
	@NumberGenerator(name = "serviceNumRulePK")
	private Integer id;
	private Integer serialNumId; // 关联ServiceSerialNumber表ID
	private String perfix; // 前缀
	private String timePattern; // 时间格式，如YYYY-MM-DD
	private Date serialTime; // 具体时间
	private String serialNum; // 具体流水号，为四位数字，自增方式见ServiceSerialNumber表
	private String ruleDesc;
	
	private String serialPattern; // 流水号自增的时间单位
	private String serialNumRule;// 流水格式，目前只支持流水号位数和数值范围
	private String serviceNumRulePattern;

	
	public String getServiceNumRulePattern() {
		return serviceNumRulePattern;
	}

	public void setServiceNumRulePattern(String serviceNumRulePattern) {
		this.serviceNumRulePattern = serviceNumRulePattern;
	}

	public String getSerialNumRule() {
		return serialNumRule;
	}

	public void setSerialNumRule(String serialNumRule) {
		this.serialNumRule = serialNumRule;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSerialNumId() {
		return serialNumId;
	}

	public void setSerialNumId(Integer serialNumId) {
		this.serialNumId = serialNumId;
	}

	public String getPerfix() {
		return perfix;
	}

	public void setPerfix(String perfix) {
		this.perfix = perfix;
	}

	public String getTimePattern() {
		return timePattern;
	}

	public void setTimePattern(String timePattern) {
		this.timePattern = timePattern;
	}

	public Date getSerialTime() {
		return serialTime;
	}

	public void setSerialTime(Date serialTime) {
		this.serialTime = serialTime;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public String getRuleDesc() {
		return ruleDesc;
	}

	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}

	public String getSerialPattern() {
		return serialPattern;
	}

	public void setSerialPattern(String serialPattern) {
		this.serialPattern = serialPattern;
	}
	
}
