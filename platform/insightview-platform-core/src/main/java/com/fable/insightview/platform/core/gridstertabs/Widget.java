package com.fable.insightview.platform.core.gridstertabs;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;

public abstract class Widget extends Component{

	private String widgetName;
	
	private String widgetTitle;
	
	private String chartType;
	
	private String isEdit;
	
	private String widgetId;
	
	private String editUrl;
	
	private String widgetIndex;
	
	private String widgetWidth;

	public StringBuilder draw() {
		StringBuilder sb = new StringBuilder();
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("widgetName", widgetName);
		valueMap.put("widgetTitle", widgetTitle);
		valueMap.put("chartType", chartType);
		valueMap.put("isEdit", isEdit);
		valueMap.put("widgetIndex", widgetIndex);
		if (valueMap != null && valueMap.size() > 0) {
			StrSubstitutor sub = new StrSubstitutor(valueMap,"$[","]");
			sb.append(sub.replace(TemplateFactory.getTemplate("Widget")));
		}
		return sb;
	}

	public String getWidgetName() {
		return widgetName;
	}

	public void setWidgetName(String widgetName) {
		this.widgetName = widgetName;
	} 

	public String getWidgetTitle() {
		return widgetTitle;
	}

	public void setWidgetTitle(String widgetTitle) {
		this.widgetTitle = widgetTitle;
	}

	public String getChartType() {
		return chartType;
	}

	public void setChartType(String chartType) {
		this.chartType = chartType;
	}

	public String getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(String isEdit) {
		this.isEdit = isEdit;
	}

	public String getWidgetId() {
		return widgetId;
	}

	public void setWidgetId(String widgetId) {
		this.widgetId = widgetId;
	}

	public String getEditUrl() {
		return editUrl;
	}

	public void setEditUrl(String editUrl) {
		this.editUrl = editUrl;
	}

	public String getWidgetIndex() {
		return widgetIndex;
	}

	public void setWidgetIndex(String widgetIndex) {
		this.widgetIndex = widgetIndex;
	}

	public String getWidgetWidth() {
		return widgetWidth;
	}

	public void setWidgetWidth(String widgetWidth) {
		this.widgetWidth = widgetWidth;
	}


}
