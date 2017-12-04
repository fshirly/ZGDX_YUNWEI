package com.fable.insightview.platform.core.gridstertabs.widget;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;

import com.fable.insightview.platform.core.gridstertabs.TemplateFactory;
import com.fable.insightview.platform.core.gridstertabs.Widget;

public class EmbedWidget extends Widget{
	
	private String url;
	
	private String urlNext;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUrlNext() {
		return urlNext;
	}

	public void setUrlNext(String urlNext) {
		this.urlNext = urlNext;
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
		valueMap.put("isEdit", getIsEdit());
		valueMap.put("widgetId", getWidgetId());
		valueMap.put("editUrl", getEditUrl());
		valueMap.put("widgetIndex", getWidgetIndex());
		valueMap.put("widgetWidth", getWidgetWidth());
		valueMap.put("urlNext", this.getUrlNext());
		return valueMap;
	}
}
