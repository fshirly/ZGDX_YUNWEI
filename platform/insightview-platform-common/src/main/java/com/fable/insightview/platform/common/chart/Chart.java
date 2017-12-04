package com.fable.insightview.platform.common.chart;

import java.util.ArrayList;
import java.util.List;

/**
 * 图表组件模型
 * @author 郑自辉
 *
 */
public class Chart {

	/**
	 * 图表系列
	 */
	private List<Series> series = new ArrayList<Series>();
	
	public Chart()
	{
		
	}
	
	public Chart(List<Series> series)
	{
		this.series = series;
	}

	public List<Series> getSeries() {
		return series;
	}

	public void setSeries(List<Series> series) {
		this.series = series;
	}
}
