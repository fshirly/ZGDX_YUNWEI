package com.fable.insightview.platform.formdesign.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * 表单表
 * 
 * @author Maowei
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "FdForm")
public class FdForm extends com.fable.insightview.platform.itsm.core.entity.Entity {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "fdForm_gen")
	@TableGenerator(initialValue=10001, name = "fdForm_gen", table = "SysKeyGenerator", pkColumnName = "GenName", valueColumnName = "GenValue", pkColumnValue = "FdFormPK", allocationSize = 1)
	@Column(name = "Id")
	private Integer id;
	
	@Column(name = "FormId")
	private String formId;
	
	@Column(name = "FormName")
	private String formName;
	
	@Column(name = "FormLayout")
	private Integer formLayout;
	
	@Column(name = "FormAttributes")
	private String formAttributes;
	
	@Column(name = "FormTable")
	private String formTable;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public Integer getFormLayout() {
		return formLayout;
	}

	public void setFormLayout(Integer formLayout) {
		this.formLayout = formLayout;
	}

	public String getFormAttributes() {
		return formAttributes;
	}

	public void setFormAttributes(String formAttributes) {
		this.formAttributes = formAttributes;
	}

	public String getFormTable() {
		return formTable;
	}

	public void setFormTable(String formTable) {
		this.formTable = formTable;
	}
	
	
}
