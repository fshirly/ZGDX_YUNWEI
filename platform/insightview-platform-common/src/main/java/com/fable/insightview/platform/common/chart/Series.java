package com.fable.insightview.platform.common.chart;

import java.util.ArrayList;
import java.util.List;

/**
 * 图表组件的系列，代表一种统计项
 * @author 郑自辉
 *
 */
public class Series {

	/**
	 * 对应的点数据
	 */
	private List<Point> points = new ArrayList<Point>();

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}
}
