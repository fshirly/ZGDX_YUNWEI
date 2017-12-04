package com.fable.insightview.platform.formdesign.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * 业务节点表单关联表
 * 
 * @author Maowei
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "FdBusinessForms")
public class FdBusinessForms extends com.fable.insightview.platform.itsm.core.entity.Entity {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "fdBusinessForms_gen")
	@TableGenerator(initialValue=10001, name = "fdBusinessForms_gen", table = "SysKeyGenerator", pkColumnName = "GenName", valueColumnName = "GenValue", pkColumnValue = "FdBusinessFormsPK", allocationSize = 1)
	@Column(name = "Id")
	private Integer id;
	
	@Column(name = "BusinessNodeId")
	private String businessNodeId;
	
	@Column(name = "BusinessType")
	private String businessType;
	
	@Column(name = "FormId")
	private Integer formId;
	
	@Column(name = "Seq")
	private Integer seq;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBusinessNodeId() {
		return businessNodeId;
	}

	public void setBusinessNodeId(String businessNodeId) {
		this.businessNodeId = businessNodeId;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public Integer getFormId() {
		return formId;
	}

	public void setFormId(Integer formId) {
		this.formId = formId;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	
}
