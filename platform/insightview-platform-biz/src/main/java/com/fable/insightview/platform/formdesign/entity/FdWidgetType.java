package com.fable.insightview.platform.formdesign.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * 控件类型表
 * 
 * @author Maowei
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "FdWidgetType")
public class FdWidgetType extends com.fable.insightview.platform.itsm.core.entity.Entity  {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "fdWidgetType_gen")
	@TableGenerator(initialValue=10001, name = "fdWidgetType_gen", table = "SysKeyGenerator", pkColumnName = "GenName", valueColumnName = "GenValue", pkColumnValue = "FdWidgetTypePK", allocationSize = 1)
	@Column(name = "Id")
	private Integer id;

	@Column(name = "WidgetType")
	private String widgetType;

	@Column(name = "WidgetName")
	private String widgetName;

	@Column(name = "DataType")
	private String dataType;
	
	@Column(name = "Datalength")
	private Integer datalength;
	
	@Column(name = "EditUrl")
	private String editUrl;

	@Column(name = "ViewUrl")
	private String viewUrl;

	@Column(name = "PropUrl")
	private String propUrl;
	
	@Column(name = "Category")
	private Integer category;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getWidgetType() {
		return widgetType;
	}

	public void setWidgetType(String widgetType) {
		this.widgetType = widgetType;
	}

	public String getWidgetName() {
		return widgetName;
	}

	public void setWidgetName(String widgetName) {
		this.widgetName = widgetName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Integer getDatalength() {
		return datalength;
	}

	public void setDatalength(Integer datalength) {
		this.datalength = datalength;
	}

	public String getEditUrl() {
		return editUrl;
	}

	public void setEditUrl(String editUrl) {
		this.editUrl = editUrl;
	}

	public String getViewUrl() {
		return viewUrl;
	}

	public void setViewUrl(String viewUrl) {
		this.viewUrl = viewUrl;
	}

	public String getPropUrl() {
		return propUrl;
	}

	public void setPropUrl(String propUrl) {
		this.propUrl = propUrl;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}
	
	
}
