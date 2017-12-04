package com.fable.insightview.platform.core.portal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;

public class Grid extends Container{

	private List<Column> columns = new ArrayList<Column>();
	
	public void addColumn(Column column){
		columns.add((Column)column);
		column.setParentContainer(this);
		this.components.add(column);
	}
	
	@Override
	public void addComponent(Component component){
		if(component instanceof Column){
			addColumn((Column)component);
		}
	}
	
	@Override
	public StringBuilder draw() {
		StringBuilder sb = new StringBuilder();
		for(Column column : columns){
			sb.append(column.draw());
		}
		return sb;
	}
	
	public StringBuilder render(){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < columns.size(); i++){
			sb.append(render(columns.get(i).components, i));
		}
		return sb;
	}
	
	/**
	 * 将部件渲染到grid中，暂实现按列布局
	 * @param components
	 * @param columnIndex
	 * @return
	 */
	private StringBuilder render(List<Component> components, int columnIndex){
		StringBuilder sb = new StringBuilder();
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("portalName", ((Portal)getParentContainer()).getPortalName());
		valueMap.put("columnIndex", columnIndex);
		for(Component c : components){
			if(c instanceof Widget){
				valueMap.put("widgetName", ((Widget)c).getWidgetName());
				StrSubstitutor sub = new StrSubstitutor(valueMap,"$[","]");  
				sb.append(sub.replace(TemplateFactory.getTemplate("Grid"))); 
			}
			if(c instanceof Container){
				sb.append(render(((Container) c).components, columnIndex));
			}
		}
		return sb;
	}
	
	public class Column extends Container{
		
		@Override
		public StringBuilder draw() {
			StringBuilder sb = new StringBuilder();
			sb.append(super.draw());
			
			StrSubstitutor sub = new StrSubstitutor(getValueMap(), "$[","]");
			sb.append(sub.replace(TemplateFactory.getTemplate("Column")));
			return sb;
		}
		
		public Map<String, Object> getValueMap() {
			Map<String, Object> valueMap = new HashMap<String, Object>();
			Container c = getParentContainer().getParentContainer();
			if(c instanceof Portal){
				valueMap.put("portalName", ((Portal)c).getPortalName());
				valueMap.put("width", this.getWidth());
			}
			return valueMap;
		}
	}
}
