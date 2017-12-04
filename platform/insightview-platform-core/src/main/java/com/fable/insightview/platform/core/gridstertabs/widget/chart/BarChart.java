package com.fable.insightview.platform.core.gridstertabs.widget.chart;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;

import com.fable.insightview.platform.core.gridstertabs.TemplateFactory;
import com.fable.insightview.platform.core.gridstertabs.widget.Chart;


public class BarChart extends Chart {
	
	private String unit; 
	
	@Override
	public StringBuilder draw() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.draw());//绘制widget
		
		StrSubstitutor sub = new StrSubstitutor(getValueMap(), "$[","]");
		sb.append(sub.replace(TemplateFactory.getTemplate("BarChart")));
		return sb;
	}
	
	public Map<String, Object> getValueMap() {
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("widgetTitle", getWidgetTitle());
		valueMap.put("widgetName", getWidgetName());
		valueMap.put("isEdit", getIsEdit());
		valueMap.put("widgetId", getWidgetId());
		valueMap.put("editUrl", getEditUrl());
		valueMap.put("fieldName", getFieldName());
		valueMap.put("dataSourceUrl", getDataSourceUrl());
		valueMap.put("legends", getLegendsDesc());
		valueMap.put("unit", unit);
		valueMap.put("series", getSeriesDesc());
		valueMap.put("widgetIndex", getWidgetIndex());
		valueMap.put("widgetWidth", getWidgetWidth());
		valueMap.put("dataSourceUrlNext", getDataSourceUrlNext());
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
		if (sb.indexOf("时间") >= 0) {
			unit = "ms";
		} else if (sb.indexOf("数") >= 0 && sb.indexOf("率") < 0) {
			unit = "";
		} else {
			unit = "%";
		}
		return sb.toString();
	}
	
	private String getSeriesDesc(){
		StringBuilder sb = new StringBuilder("[");
		if(chartSeries != null && chartSeries.size() > 0){
			for(ChartSeries item : chartSeries){
				sb.append(item.draw());
				sb.deleteCharAt(sb.lastIndexOf(","));
				sb.deleteCharAt(sb.lastIndexOf("}"));
				if(getLegendsDesc().contains(",") == false){
//					sb.append(",itemStyle: { normal: {color:function(seriesIndex, series, dataIndex, data){return option.color[seriesIndex.dataIndex];}}}");
					StrSubstitutor sub = new StrSubstitutor(null, "$[", "]");
					sb.append(sub.replace(TemplateFactory.getTemplate("BarChartSeries")));
				}
			}
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		
		sb.append("]");
		return sb.toString();
	}

	@Override
	public String getChartType() {
		return "bar";
	}

}
