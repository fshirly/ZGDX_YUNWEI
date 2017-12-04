package com.fable.insightview.platform.core.gridstertabs.widget.chart;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;

import com.fable.insightview.platform.core.gridstertabs.Component;
import com.fable.insightview.platform.core.gridstertabs.TemplateFactory;

public class ChartMark extends Component{

	private String type;// min max average
	
	private String name;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public StringBuilder draw() {
		StringBuilder sb = new StringBuilder();
		
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("type", this.getType());
		valueMap.put("name", this.getName());
		
		StrSubstitutor sub = new StrSubstitutor(valueMap, "$[","]");
		sb.append(sub.replace(TemplateFactory.getTemplate("ChartMark")));
		return sb;
	}
	
}
