package com.fable.insightview.platform.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FdSysValidateRule")
public class FdSysValidateRule extends com.fable.insightview.platform.itsm.core.entity.Entity{

	@Id
//	@GeneratedValue(strategy = GenerationType.TABLE, generator = "resvalidate_gen")
//	@TableGenerator(initialValue=INIT_VALUE, name = "resvalidate_gen", table = "SysKeyGenerator", pkColumnName = "GenName", valueColumnName = "GenValue", pkColumnValue = "ResFdSysValidateRulePK", allocationSize = 1)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "ValidatorName")
	private String validatorName;
	
	@Column(name = "ValidatorValue")
	private String validatorValue;
	
	@Column(name = "ValidatorMsg")
	private String validatorMsg;
	
	@Column(name = "ValidType")
	private String validType;

	public String getValidType() {
		return validType;
	}

	public void setValidType(String validType) {
		this.validType = validType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getValidatorName() {
		return validatorName;
	}

	public void setValidatorName(String validatorName) {
		this.validatorName = validatorName;
	}

	public String getValidatorValue() {
		return validatorValue;
	}

	public void setValidatorValue(String validatorValue) {
		this.validatorValue = validatorValue;
	}

	public String getValidatorMsg() {
		return validatorMsg;
	}

	public void setValidatorMsg(String validatorMsg) {
		this.validatorMsg = validatorMsg;
	}
}
