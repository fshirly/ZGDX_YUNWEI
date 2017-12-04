package com.fable.insightview.platform.core.gridstertabs.widget.chart;

import com.fable.insightview.platform.core.gridstertabs.Component;

public abstract class ChartItem extends Component{

	private String label;
	
	private String fieldName;
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
}
