package com.fable.insightview.platform.core.gridstertabs.widget;

import java.util.ArrayList;
import java.util.List;

import com.fable.insightview.platform.core.gridstertabs.Widget;
import com.fable.insightview.platform.core.gridstertabs.widget.chart.ChartSeries;

public abstract class Chart extends Widget{

	private String dataSourceUrl;//数据源 json
	
	private String dataSourceUrlNext;//数据源 json
	
	private String fieldName; //与ChartItem中的fieldName同值，这里为了在配置文件加载GaugeChart中使用
	
	protected List<ChartSeries> chartSeries = new ArrayList<ChartSeries>();
	
	public List<ChartSeries> getBarChartItems() {
		return chartSeries;
	}
	
	public void addChartItem(ChartSeries item){
		chartSeries.add(item);
		item.setType(this.getChartType());
	}

	public String getDataSourceUrl() {
		return dataSourceUrl;
	}

	public void setDataSourceUrl(String dataSourceUrl) {
		this.dataSourceUrl = dataSourceUrl;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getDataSourceUrlNext() {
		return dataSourceUrlNext;
	}

	public void setDataSourceUrlNext(String dataSourceUrlNext) {
		this.dataSourceUrlNext = dataSourceUrlNext;
	}

	public abstract String getChartType();
}
