package com.fable.insightview.platform.flex.model;

import java.io.Serializable;
/**
 * 对应BirdEye节点间的连线
 * @author 郑自辉
 *
 */
public class Edge implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 开始节点ID
	 */
	public String fromID;
	
	/**
	 * 结束节点ID
	 */
	public String toID;
	
	/**
	 * 连线显示名称
	 */
	public String edgeLabel;
	
	/**
	 * 连线长度
	 */
	public int flow;
	
	/**
	 * 连线颜色
	 */
	public String color;
	
	/**
	 * 连线样式
	 */
	public String edgeClass;
	
	/**
	 * 连线图标名称
	 */
	public String edgeIcon;

	public String getFromID() {
		return fromID;
	}

	public void setFromID(String fromID) {
		this.fromID = fromID;
	}

	public String getToID() {
		return toID;
	}

	public void setToID(String toID) {
		this.toID = toID;
	}

	public String getEdgeLabel() {
		return edgeLabel;
	}

	public void setEdgeLabel(String edgeLabel) {
		this.edgeLabel = edgeLabel;
	}

	public int getFlow() {
		return flow;
	}

	public void setFlow(int flow) {
		this.flow = flow;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getEdgeClass() {
		return edgeClass;
	}

	public void setEdgeClass(String edgeClass) {
		this.edgeClass = edgeClass;
	}

	public String getEdgeIcon() {
		return edgeIcon;
	}

	public void setEdgeIcon(String edgeIcon) {
		this.edgeIcon = edgeIcon;
	}
}
