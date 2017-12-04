package com.fable.insightview.platform.flex.model;

import java.io.Serializable;
/**
 * 对应BirdEye图表对象
 * @author 郑自辉
 *
 */
public class Graph implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 节点集合
	 */
	public Node[] Node;
	
	/**
	 * 连线集合
	 */
	public Edge[] Edge;

	public Node[] getNode() {
		return Node;
	}

	public void setNode(Node[] node) {
		Node = node;
	}

	public Edge[] getEdge() {
		return Edge;
	}

	public void setEdge(Edge[] edge) {
		Edge = edge;
	}
}
