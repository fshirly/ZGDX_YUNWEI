package com.fable.insightview.platform.common.bpm.activiti.model;

import java.io.Serializable;

public class ActiveNodeInfoRS implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1929213378380137142L;

	private String width;
	private String height;
	private ActiveNodeRS activeNode = new ActiveNodeRS();
	private String nodeName;
	
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public ActiveNodeRS getActiveNode() {
		return activeNode;
	}
	public void setActiveNode(ActiveNodeRS activeNode) {
		this.activeNode = activeNode;
	}

	
}