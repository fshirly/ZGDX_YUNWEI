package com.fable.insightview.platform.core.portal.widget;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;

import com.fable.insightview.platform.core.portal.TemplateFactory;
import com.fable.insightview.platform.core.portal.Widget;

public class EmbedWidget extends Widget{
	
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public StringBuilder draw() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.draw());//绘制widget
		
		StrSubstitutor sub = new StrSubstitutor(getValueMap(), "$[","]");
		sb.append(sub.replace(TemplateFactory.getTemplate("EmbedWidget")));
		return sb;
	}
	
	public Map<String, Object> getValueMap() {
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("widgetTitle", getWidgetTitle());
		valueMap.put("widgetName", getWidgetName());
		valueMap.put("url", this.getUrl());
		return valueMap;
	}
}
