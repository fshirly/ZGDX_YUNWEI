package com.fable.insightview.monitor.room3d.entity;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * @author Administrator
 * 3D ROOM 位置模型
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
public class Position {
	
	
	/**
	 * 指吸附到某个图元上
	 */
	private Host host;
	
	/**
	 * 图元提示信息
	 */
	private String toolTip;
	
	/**
	 * 图元位置,相当于3D XZ轴
	 */
	@JsonProperty("position")
	private Pos pos;
	
	/**
	 * 图元的宽度，相当于3D X轴长度
	 */
	private Integer width;
	
	/**
	 * 图元的高度,相当于3D Z轴长度
	 */
	private Integer height;
	
	/**
	 * 相当于3D Y轴的长度
	 */
	private Integer tall;
	
	/**
	 * 相当于3D Y轴位置
	 */
	private Double elevation;
	
	/**
	 * 用于描述点连接样式
	 */
	private Segments segments;
	
	/**
	 * 图元所有的描点
	 */
	private Points points;
	
	/**
	 * 决定3d图形线的粗细
	 */
	private Integer thickness;
	
	private Boolean closePath;
	
	private String tag;
	

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Boolean getClosePath() {
		return closePath;
	}

	public void setClosePath(Boolean closePath) {
		this.closePath = closePath;
	}

	public Host getHost() {
		return host;
	}

	public void setHost(Host host) {
		this.host = host;
	}

	public String getToolTip() {
		return toolTip;
	}

	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
	}


	public Pos getPos() {
		return pos;
	}

	public void setPos(Pos pos) {
		this.pos = pos;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getTall() {
		return tall;
	}

	public void setTall(Integer tall) {
		this.tall = tall;
	}

	public Double getElevation() {
		return elevation;
	}

	public void setElevation(Double elevation) {
		this.elevation = elevation;
	}

	public Segments getSegments() {
		return segments;
	}

	public void setSegments(Segments segments) {
		this.segments = segments;
	}

	public Points getPoints() {
		return points;
	}

	public void setPoints(Points points) {
		this.points = points;
	}

	public Integer getThickness() {
		return thickness;
	}

	public void setThickness(Integer thickness) {
		this.thickness = thickness;
	}

	

}
