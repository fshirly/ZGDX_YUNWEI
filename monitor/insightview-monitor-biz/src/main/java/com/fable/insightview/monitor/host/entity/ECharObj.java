package com.fable.insightview.monitor.host.entity;
 
public class ECharObj {
	private String text; // 标题
	private String legend;
	private String xAxisData; // x轴
	private String seriesData;

	public String getSeriesData() {
		return seriesData;
	}

	public void setSeriesData(String seriesData) {
		this.seriesData = seriesData;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getLegend() {
		return legend;
	}

	public void setLegend(String legend) {
		this.legend = legend;
	}

	public String getxAxisData() {
		return xAxisData;
	}

	public void setxAxisData(String xAxisData) {
		this.xAxisData = xAxisData;
	}

}
