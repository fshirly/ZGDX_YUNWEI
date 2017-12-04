package com.fable.insightview.platform.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;

/**
 * 
 * @TABLE_NAME: ViewWidgets
 * @Description:
 * @author: wul
 * @Create at: Fri Jan 03 10:15:31 CST 2014
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "ViewWidgets")
public class ViewWidgetsBean extends com.fable.insightview.platform.itsm.core.entity.Entity {

	@Id
	@GeneratedValue
	private int widgetsInfoID;

	@Column(name = "BizViewType")
	private String bizViewType;

	public ViewWidgetsBean() {
		super();
	}

	public int getWidgetsInfoID() {
		return widgetsInfoID;
	}

	public void setWidgetsInfoID(int widgetsInfoID) {
		this.widgetsInfoID = widgetsInfoID;
	}

	public String getBizViewType() {
		return bizViewType;
	}

	public void setBizViewType(String bizViewType) {
		this.bizViewType = bizViewType;
	}

}
