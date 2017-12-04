package com.fable.insightview.platform.formdesign.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * 特殊属性初始化值预置表
 * 
 * @author zhengz
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "FdFormDefault")
public class FdFormDefault extends com.fable.insightview.platform.itsm.core.entity.Entity  {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "fdFormDefault_gen")
	@TableGenerator(initialValue=10001, name = "fdFormDefault_gen", table = "SysKeyGenerator", pkColumnName = "GenName", valueColumnName = "GenValue", pkColumnValue = "FdFormDefaultPK", allocationSize = 1)
	@Column(name = "Id")
	private Integer id;

	@Column(name = "AttributeLabel")
	private String attributeLabel;

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

	public String getAttributeLabel() {
		return attributeLabel;
	}

	public void setAttributeLabel(String attributeLabel) {
		this.attributeLabel = attributeLabel;
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
