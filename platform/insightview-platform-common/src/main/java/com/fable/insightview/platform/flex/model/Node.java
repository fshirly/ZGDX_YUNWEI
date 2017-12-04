package com.fable.insightview.platform.flex.model;

import java.io.Serializable;

/**
 * 对应BirdEye的节点
 * @author 郑自辉
 *
 */
public class Node implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 节点ID
	 */
	public String id;
	
	/**
	 * 节点名称
	 */
	public String name;
	
	/**
	 * 节点描述
	 */
	public String desc;
	
	/**
	 * 节点颜色
	 */
	public String nodeColor;
	
	/**
	 * 节点大小
	 */
	public int nodeSize;
	
	/**
	 * 节点样式
	 */
	public String nodeClass;
	
	/**
	 * 节点对应的图标名称
	 */
	public String nodeIcon;
	
	/**
	 * 节点x坐标
	 */
	public int x;
	
	/**
	 * 节点y坐标
	 */
	public int y;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getNodeColor() {
		return nodeColor;
	}

	public void setNodeColor(String nodeColor) {
		this.nodeColor = nodeColor;
	}

	public int getNodeSize() {
		return nodeSize;
	}

	public void setNodeSize(int nodeSize) {
		this.nodeSize = nodeSize;
	}

	public String getNodeClass() {
		return nodeClass;
	}

	public void setNodeClass(String nodeClass) {
		this.nodeClass = nodeClass;
	}

	public String getNodeIcon() {
		return nodeIcon;
	}

	public void setNodeIcon(String nodeIcon) {
		this.nodeIcon = nodeIcon;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
