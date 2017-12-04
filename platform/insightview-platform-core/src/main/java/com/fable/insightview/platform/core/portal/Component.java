package com.fable.insightview.platform.core.portal;


public abstract class Component {

	private String width;

	private String height;
	
	private Container parentContainer;//父容器
	
	public abstract StringBuilder draw();
	
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
	
	public void setParentContainer(Container parentContainer) {
		this.parentContainer = parentContainer;
	}

	public Container getParentContainer() {
		return parentContainer;
	}

}
