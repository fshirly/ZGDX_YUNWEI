package com.fable.insightview.platform.common.chart;
/**
 * 图表组件的点
 * 图表组件最基本的单元，每一个数据项均为一个Point
 * @author 郑自辉
 *
 */
public class Point {

	/**
	 * 当没有x,y,z轴时使用的值，如饼图、仪表盘等
	 */
	private String value;
	
	/**
	 * x轴值
	 */
	private String xValue;
	
	/**
	 * y轴值
	 */
	private String yValue;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getxValue() {
		return xValue;
	}

	public void setxValue(String xValue) {
		this.xValue = xValue;
	}

	public String getyValue() {
		return yValue;
	}

	public void setyValue(String yValue) {
		this.yValue = yValue;
	}
}
