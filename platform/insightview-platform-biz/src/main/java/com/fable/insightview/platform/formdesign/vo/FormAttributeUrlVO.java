package com.fable.insightview.platform.formdesign.vo;

/**
 * 表单属性定制的VO类
 * 
 * @author Maowei
 * 
 */
public class FormAttributeUrlVO {
	private Integer id;
	private String editUrl;
	private String viewUrl;
	private String widgetType;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getWidgetType() {
		return widgetType;
	}
	public void setWidgetType(String widgetType) {
		this.widgetType = widgetType;
	}
	
}
