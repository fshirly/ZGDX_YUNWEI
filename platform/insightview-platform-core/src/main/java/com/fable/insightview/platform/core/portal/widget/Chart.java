package com.fable.insightview.platform.core.portal.widget;

import java.util.ArrayList;
import java.util.List;

import com.fable.insightview.platform.core.portal.Widget;
import com.fable.insightview.platform.core.portal.widget.chart.ChartSeries;

public abstract class Chart extends Widget{

	private String dataSourceUrl;//数据源 json
	
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
	
	public abstract String getChartType();
}
