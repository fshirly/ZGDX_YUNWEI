package com.fable.insightview.monitor.room3d.entity;

import org.codehaus.jackson.map.annotate.JsonSerialize;


/**
 * @author Administrator
 * 描点
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
public class Point {
	
	private Integer x;
	
	private Integer y;

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	
	

}
