package com.fable.insightview.platform.core.gridstertabs.widget.chart;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;

import com.fable.insightview.platform.core.gridstertabs.TemplateFactory;

public class ChartSeries extends AxisChartItem{

	
	private Map<String, String> param = new HashMap<String, String>();
	
	private String type;

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public Map<String, String> getParam() {
		return param;
	}

	@Override
	public StringBuilder draw() {
		
		StringBuilder sb = new StringBuilder();
		
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("label", this.getLabel());
		valueMap.put("fieldName", this.getFieldName());
		valueMap.put("type", this.getType());
		valueMap.put("AxisChartItem", super.draw());
		valueMap.put("param", getParamDesc());
		String filedName = this.getFieldName();
		//CacheHits
		if (filedName.contains("Availability") == true || filedName.contains("Hits") == true) {
//			仪表盘外围
			getParam().put("axisLine", "{show: true, lineStyle: {color: [[0.1, '#BC1717'],[0.35,'#E4B116'],[1,'#3CB371']],width: 10}}");
		}
		valueMap.put("param", getParamDesc());
		StrSubstitutor sub = new StrSubstitutor(valueMap, "$[", "]");
		sb.append(sub.replace(TemplateFactory.getTemplate("ChartSeries")));
		return sb;
	}
	
	private String getParamDesc(){
		StringBuilder sb = new StringBuilder(",");
//		for(Iterator<String> iterator = param.keySet().iterator();iterator.hasNext();){
//			String key = iterator.next();
//		}
		for (Map.Entry<String, String> entry : param.entrySet()) {
			sb.append(entry.getKey()).append(":").append(entry.getValue()).append(",");
		}
		if(sb.length()>0){
			sb.deleteCharAt(sb.lastIndexOf(","));
		}
		
		return sb.toString();
	}
}
