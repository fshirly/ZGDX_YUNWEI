package com.fable.insightview.platform.core.portal;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;

public abstract class Widget extends Component{

	private String widgetName;
	
	private String widgetTitle;
	
	public StringBuilder draw() {
		StringBuilder sb = new StringBuilder();
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("widgetName", widgetName);
		valueMap.put("widgetTitle", widgetTitle);
		
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
	
}
