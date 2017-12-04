package com.fable.insightview.platform.core.portal.widget.chart;

public class GaugeChartSeries extends ChartSeries{

	public GaugeChartSeries() {
		getParam().put("radius", "'75%'");
		getParam().put("center", "['12%', '50%']");
		getParam().put("detail", "{formatter:'{value}%'}");
	}
}
