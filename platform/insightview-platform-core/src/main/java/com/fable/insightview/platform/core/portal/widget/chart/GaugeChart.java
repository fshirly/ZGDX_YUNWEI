package com.fable.insightview.platform.core.portal.widget.chart;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;

import com.fable.insightview.platform.core.portal.TemplateFactory;
import com.fable.insightview.platform.core.portal.widget.Chart;

public class GaugeChart  extends Chart{
	@Override
	public StringBuilder draw() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.draw());//绘制widget
		
		StrSubstitutor sub = new StrSubstitutor(getValueMap(), "$[","]");
		sb.append(sub.replace(TemplateFactory.getTemplate("GaugeChart")));
		return sb;
	}
	
	public Map<String, Object> getValueMap() {
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("widgetTitle", getWidgetTitle());
		valueMap.put("widgetName", getWidgetName());
		valueMap.put("dataSourceUrl", getDataSourceUrl());
		valueMap.put("series", getSeriesDesc());
		return valueMap;
	}
	
	
	private String getSeriesDesc(){
		StringBuilder sb = new StringBuilder("[");
		if(chartSeries != null && chartSeries.size() > 0){
			for(ChartSeries item : chartSeries){
				sb.append(item.draw());
			}
		}
		if(sb.toString().contains(",")==true){
			sb.deleteCharAt(sb.lastIndexOf(","));
		}
		sb.append("]");
		return sb.toString();
	}
	@Override
	public String getChartType() {
		return "gauge";
	}

}
