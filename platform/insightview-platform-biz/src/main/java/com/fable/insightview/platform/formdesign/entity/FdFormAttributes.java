package com.fable.insightview.platform.formdesign.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * 表单属性表
 * 
 * @author Maowei
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "FdFormAttributes")
public class FdFormAttributes extends com.fable.insightview.platform.itsm.core.entity.Entity {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "fdFormAttributes_gen")
	@TableGenerator(initialValue=10001, name = "fdFormAttributes_gen", table = "SysKeyGenerator", pkColumnName = "GenName", valueColumnName = "GenValue", pkColumnValue = "FdFormAttributesPK", allocationSize = 1)
	@Column(name = "Id")
	private Integer id;
	
	@Column(name = "FormId")
	private Integer formId;
	
	@Column(name = "AttributesLabel")
	private String attributesLabel;
	
	@Column(name = "ColumnName")
	private String columnName;
	
	@Column(name = "WidgetType")
	private String widgetType;
	
	@Column(name = "InitSQL")
	private String initSQL;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFormId() {
		return formId;
	}

	public void setFormId(Integer formId) {
		this.formId = formId;
	}

	public String getAttributesLabel() {
		return attributesLabel;
	}

	public void setAttributesLabel(String attributesLabel) {
		this.attributesLabel = attributesLabel;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getWidgetType() {
		return widgetType;
	}

	public void setWidgetType(String widgetType) {
		this.widgetType = widgetType;
	}

	public String getInitSQL() {
		return initSQL;
	}

	public void setInitSQL(String initSQL) {
		this.initSQL = initSQL;
	}

}

