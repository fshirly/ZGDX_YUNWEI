package com.fable.insightview.platform.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;

/**
 * 
 * @TABLE_NAME: WidgetsInView
 * @Description:
 * @author: wul
 * @Create at: Fri Jan 03 10:20:05 CST 2014
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "WidgetsInView")
public class WidgetsInViewBean extends com.fable.insightview.platform.itsm.core.entity.Entity {

	@Id
	@GeneratedValue
	private int id;

	@Column(name = "BizViewITypeID")
	private String bizViewiTypeId;

	@Column(name = "WidgetID")
	private int widgetId;

	@Column(name = "UserID")
	private int userId;

	@Column(name = "WidgetOrder")
	private int widgetOrder;

	public WidgetsInViewBean() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getWidgetId() {
		return widgetId;
	}

	public void setWidgetId(int widgetId) {
		this.widgetId = widgetId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getWidgetOrder() {
		return widgetOrder;
	}

	public void setWidgetOrder(int widgetOrder) {
		this.widgetOrder = widgetOrder;
	}

}
