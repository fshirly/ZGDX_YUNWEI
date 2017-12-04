package com.fable.insightview.platform.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * 
 * @TABLE_NAME: WidgetsInfo
 * @Description:
 * @author: wul
 * @Create at: Fri Jan 03 10:18:52 CST 2014
 */
@Entity
@Table(name = "WidgetsInfo")
public class WidgetsInfoBean extends com.fable.insightview.platform.itsm.core.entity.Entity
		implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "widgetsinfo_gen")
	@TableGenerator(initialValue=INIT_VALUE, name = "widgetsinfo_gen", table = "SysKeyGenerator", pkColumnName = "GenName", valueColumnName = "GenValue", pkColumnValue = "WidgetsInfoPK", allocationSize = 1)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "WidgetName")
	private String widgetName;

	@Column(name = "WidgetTitle")
	private String widgetTitle;

	@Column(name = "WidgetType")
	private Integer widgetType;

	@Column(name = "WidgetImageURL")
	private String widgetImageUrl;
	
	@Column(name="WidgetUrl")
	private String widgetUrl;
	
	@Column(name="WidgetWidth")
	private Integer widgetWidth;
	
	@Column(name="WidgetHeight")
	private Integer widgetHeight;

	public WidgetsInfoBean() {
		super();
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public String getWidgetName() {
		return widgetName;
	}

	public void setWidgetName(String widgetName) {
		this.widgetName = widgetName;
	}

	public String getWidgetUrl() {
		return widgetUrl;
	}

	public void setWidgetUrl(String widgetUrl) {
		this.widgetUrl = widgetUrl;
	}

	public String getWidgetTitle() {
		return widgetTitle;
	}

	public void setWidgetTitle(String widgetTitle) {
		this.widgetTitle = widgetTitle;
	}

	public Integer getWidgetType() {
		return widgetType;
	}

	public void setWidgetType(Integer widgetType) {
		this.widgetType = widgetType;
	}

	public String getWidgetImageUrl() {
		return widgetImageUrl;
	}

	public void setWidgetImageUrl(String widgetImageUrl) {
		this.widgetImageUrl = widgetImageUrl;
	}

	public Integer getWidgetWidth() {
		return widgetWidth;
	}

	public void setWidgetWidth(Integer widgetWidth) {
		this.widgetWidth = widgetWidth;
	}

	public Integer getWidgetHeight() {
		return widgetHeight;
	}

	public void setWidgetHeight(Integer widgetHeight) {
		this.widgetHeight = widgetHeight;
	}
}
