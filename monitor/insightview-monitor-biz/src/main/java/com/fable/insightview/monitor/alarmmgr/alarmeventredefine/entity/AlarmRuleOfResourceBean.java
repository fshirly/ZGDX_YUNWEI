package com.fable.insightview.monitor.alarmmgr.alarmeventredefine.entity;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * 重定义规则与资源关系
 * 
 */
public class AlarmRuleOfResourceBean {
	@NumberGenerator(name = "monitorAlarmRuleOfResPK")
	private Integer id;
	private Integer moClassId;
	private Integer ruleOfMOClassId;
	private Integer moId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRuleOfMOClassId() {
		return ruleOfMOClassId;
	}

	public void setRuleOfMOClassId(Integer ruleOfMOClassId) {
		this.ruleOfMOClassId = ruleOfMOClassId;
	}

	public Integer getMoId() {
		return moId;
	}

	public void setMoId(Integer moId) {
		this.moId = moId;
	}

	public Integer getMoClassId() {
		return moClassId;
	}

	public void setMoClassId(Integer moClassId) {
		this.moClassId = moClassId;
	}
	
}
