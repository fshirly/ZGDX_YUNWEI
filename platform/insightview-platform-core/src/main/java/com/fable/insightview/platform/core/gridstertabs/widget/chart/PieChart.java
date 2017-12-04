package com.fable.insightview.platform.core.gridstertabs.widget.chart;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;

import com.fable.insightview.platform.core.gridstertabs.TemplateFactory;
import com.fable.insightview.platform.core.gridstertabs.widget.Chart;

public class PieChart extends Chart {
	
	@Override
	public StringBuilder draw() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.draw());//绘制widget
		
		StrSubstitutor sub = new StrSubstitutor(getValueMap(), "$[","]");
		sb.append(sub.replace(TemplateFactory.getTemplate("PieChart")));
		return sb;
	}
	
	public Map<String, Object> getValueMap() {
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("widgetTitle", getWidgetTitle());
		valueMap.put("widgetName", getWidgetName());
		valueMap.put("dataSourceUrl", getDataSourceUrl());
//		valueMap.put("legends", getLegendsDesc());
//		valueMap.put("series", getSeriesDesc());
		valueMap.put("widgetIndex", getWidgetIndex());
		valueMap.put("widgetWidth", getWidgetWidth());
		valueMap.put("pieName", "pieName");
		valueMap.put("label", getWidgetTitle());
		valueMap.put("type", "pie");
		valueMap.put("fieldName", getFieldName());
		return valueMap;
	}
	
	private String getLegendsDesc(){
		StringBuilder sb = new StringBuilder("[");
		if(chartSeries != null && chartSeries.size() > 0){
			for(ChartSeries item : chartSeries){
				sb.append("'").append(item.getLabel()).append("',");
			}
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append("]");
		return sb.toString();
	}
	
	private String getSeriesDesc(){
		StringBuilder sb = new StringBuilder("[");
		if(chartSeries != null && chartSeries.size() > 0){
			for(ChartSeries item : chartSeries){
				sb.append(item.draw());
			}
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		sb.append("]");
		return sb.toString();
	}
	
	@Override
	public String getChartType() {
		return "pie";
	}

}
