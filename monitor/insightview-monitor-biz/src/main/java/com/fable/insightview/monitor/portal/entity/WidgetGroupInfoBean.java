package com.fable.insightview.monitor.portal.entity;

import org.apache.ibatis.type.Alias;


@Alias("widgetGroupInfo")
public class WidgetGroupInfoBean {
	private String widgetGroupId;
	private String widgetGroupName;
	private String widgetGroupDesc;
	public WidgetGroupInfoBean() {
	}
	public String getWidgetGroupId() {
		return widgetGroupId;
	}
	public void setWidgetGroupId(String widgetGroupId) {
		this.widgetGroupId = widgetGroupId;
	}
	public String getWidgetGroupName() {
		return widgetGroupName;
	}
	public void setWidgetGroupName(String widgetGroupName) {
		this.widgetGroupName = widgetGroupName;
	}
	public String getWidgetGroupDesc() {
		return widgetGroupDesc;
	}
	public void setWidgetGroupDesc(String widgetGroupDesc) {
		this.widgetGroupDesc = widgetGroupDesc;
	}
}
