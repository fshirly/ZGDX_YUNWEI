package com.fable.insightview.monitor.room3d.entity;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * @author Administrator
 * 
 * 3D ROOM 数据模型
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
public class Data {
	
	/**
	 * 图元对象
	 */
	@JsonProperty("c")
	private String obj;
	
	/**
	 * 图元索引
	 */
	@JsonProperty("i")
	private Integer index;
	
	/**
	 * 图元位置
	 */
	@JsonProperty("p")
	private Position position;
	
	/**
	 * 图元样式
	 */
	@JsonProperty("s")
	private Style style;
	
	/**
	 * 图元属性
	 */
	@JsonProperty("a")
	private Attr attr;

	public String getObj() {
		return obj;
	}

	public void setObj(String obj) {
		this.obj = obj;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Style getStyle() {
		return style;
	}

	public void setStyle(Style style) {
		this.style = style;
	}

	public Attr getAttr() {
		return attr;
	}

	public void setAttr(Attr attr) {
		this.attr = attr;
	}


}
