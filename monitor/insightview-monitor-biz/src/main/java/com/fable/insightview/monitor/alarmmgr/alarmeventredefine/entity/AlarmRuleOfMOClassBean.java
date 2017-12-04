package com.fable.insightview.monitor.alarmmgr.alarmeventredefine.entity;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * 重定义与对象类型关系
 * 
 */
public class AlarmRuleOfMOClassBean {
	@NumberGenerator(name = "monitorAlarmRuleOfClassPK")
	private Integer id;
	private Integer ruleId;
	private Integer moClassId;
	
	private String classLable;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRuleId() {
		return ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	public Integer getMoClassId() {
		return moClassId;
	}

	public void setMoClassId(Integer moClassId) {
		this.moClassId = moClassId;
	}

	public String getClassLable() {
		return classLable;
	}

	public void setClassLable(String classLable) {
		this.classLable = classLable;
	}

}
