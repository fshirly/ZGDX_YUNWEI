package com.fable.insightview.platform.core.portal;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;

public class Portal extends Container {

	private String portalName;
	
	private String portalTitle;

	private Grid grid;
	
	@Override
	public void addComponent(Component component){
		if(component instanceof Grid){
			setGrid((Grid)component);
		}
	}
	
	public String getPortalTitle() {
		return portalTitle;
	}

	public void setPortalTitle(String portalTitle) {
		this.portalTitle = portalTitle;
	}

	@Override
	public StringBuilder draw() {
		
		StringBuilder sb = new StringBuilder();
		
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("portalName", this.getPortalName());
		
		StrSubstitutor sub = new StrSubstitutor(valueMap, "$[","]");
		
		sb.append("$(function(){");
		sb.append(sub.replace(TemplateFactory.getTemplate("Portal-pre")));
		sb.append(grid.draw());
		sb.append(sub.replace(TemplateFactory.getTemplate("Portal-post")));
		sb.append(grid.render());
		sb.append("});");
		System.out.println("sb=============="+sb);
		return sb;
	}
	
	public String getPortalName() {
		return portalName;
	}

	public void setPortalName(String portalName) {
		this.portalName = portalName;
	}

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
//		this.components.set(0, this.grid);
		this.components.add(this.grid);
		grid.setParentContainer(this);
	}

}
