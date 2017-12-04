package com.fable.insightview.monitor.portal.entity;

import org.apache.ibatis.type.Alias;


@Alias("widgetInfo")
public class WidgetInfoBean {
	private String widgetId;
	private String widgetName;
	private String widgetContent;
	private String widgetGroupId;
	private String previewImage;
	private String widgetDesc;
	private String widgetGroupName;
	private String widgetFilter;
	private String editUrl;
	public String getWidgetFilter() {
		return widgetFilter;
	}
	public void setWidgetFilter(String widgetFilter) {
		this.widgetFilter = widgetFilter;
	}
	public WidgetInfoBean() {
	}
	public String getWidgetName() {
		return widgetName;
	}
	public void setWidgetName(String widgetName) {
		this.widgetName = widgetName;
	}
	public String getWidgetContent() {
		return widgetContent;
	}
	public void setWidgetContent(String widgetContent) {
		this.widgetContent = widgetContent;
	}
	public String getWidgetId() {
		return widgetId;
	}
	public void setWidgetId(String widgetId) {
		this.widgetId = widgetId;
	}
	public String getWidgetGroupId() {
		return widgetGroupId;
	}
	public void setWidgetGroupId(String widgetGroupId) {
		this.widgetGroupId = widgetGroupId;
	}
	public String getPreviewImage() {
		return previewImage;
	}
	public void setPreviewImage(String previewImage) {
		this.previewImage = previewImage;
	}
	public String getWidgetDesc() {
		return widgetDesc;
	}
	public void setWidgetDesc(String widgetDesc) {
		this.widgetDesc = widgetDesc;
	}
	public String getWidgetGroupName() {
		return widgetGroupName;
	}
	public void setWidgetGroupName(String widgetGroupName) {
		this.widgetGroupName = widgetGroupName;
	}
	public String getEditUrl() {
		return editUrl;
	}
	public void setEditUrl(String editUrl) {
		this.editUrl = editUrl;
	}
	
}
