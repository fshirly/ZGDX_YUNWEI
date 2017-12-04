package com.fable.insightview.platform.core.gridstertabs.widget.chart;

public class GaugeChartSeries extends ChartSeries{

	public GaugeChartSeries() {
//		getParam().put("radius", "'75%'");
//		getParam().put("center", "['12%', '50%']");
		getParam().put("detail", "{formatter:'{value}%'}");
//		仪表盘外围
//		(value <= 75) ? 'green' : (value >= 90 ? 'red' : 'yellow');
		getParam().put("axisLine", "{show: true, lineStyle: {color: [[0.75, '#3CB371'],[0.9,'#E4B116'],[1, '#BC1717' ]],width: 10}}");
		getParam().put("axisTick", "{splitNumber: 10,length :15,lineStyle: {color: 'auto'}}");
		getParam().put("axisLabel", "{textStyle: {color: 'auto'}}");
		getParam().put("splitLine", "{length :22,lineStyle: {color: 'auto'}}");
		getParam().put("pointer", "{width : 5,color : 'auto'}");
		
	}
	
	
	/**
	 * 	axisLine: {show: true, lineStyle: {color: [[0.2, '#2ec7c9'],[0.8, '#5ab1ef'],[1, '#d87a80']],width: 10}},
		axisTick: {splitNumber: 10,length :15,lineStyle: {color: 'auto'}},
		axisLabel: {textStyle: {color: 'auto'}},
		splitLine: {length :22,lineStyle: {color: 'auto'}},
		pointer : {width : 5,color : 'auto'},
	 */
}
