package com.fable.insightview.platform.core.gridstertabs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;

public class Grid extends Container{
	
	private List<GridSterWidget> widgets = new ArrayList<GridSterWidget>();
	
	private List<GridSterTabsDiv> tabsDivs = new ArrayList<GridSterTabsDiv>();
	
	public void addGridSterWidget(GridSterWidget gridSterWidget){
		widgets.add((GridSterWidget)gridSterWidget);
	}
	
	public void addGridSterTabsDiv(GridSterTabsDiv gridSterTabsDiv){
		tabsDivs.add((GridSterTabsDiv)gridSterTabsDiv);
	}
	
	public void delGridSterTabs(){
		tabsDivs = null;
	}
	
	public void delGridSterWidget(){
		widgets.removeAll(widgets);
	}
	
	@Override
	public void addComponent(Component component){
		if(component instanceof GridSterWidget){
			addGridSterWidget((GridSterWidget)component);
		}
	}
	
	@Override
	public StringBuilder draw() {
		StringBuilder sb = new StringBuilder();
		sb.append("var widgets = [");
		for(GridSterWidget gridSterWidget : widgets){
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("liName", gridSterWidget.getLiName());
			valueMap.put("width", gridSterWidget.getWidth());
			valueMap.put("height", gridSterWidget.getHeight());
			valueMap.put("col", gridSterWidget.getCol());
			valueMap.put("row", gridSterWidget.getRow());
			StrSubstitutor sub = new StrSubstitutor(valueMap, "$[","]");
			sb.append(sub.replace(TemplateFactory.getTemplate("GridSterWidget")));
		}
		if (sb.toString().contains(",") == true) {
			sb.deleteCharAt(sb.lastIndexOf(","));
		}
		sb.append("];");
		return sb;
	}
	
	public StringBuilder render(){
		StringBuilder sb = new StringBuilder();
//		for(int i=0;i<widgets.size();i++){
		if (widgets.size() > 0) {
			sb.append(widgets.get(0).draw());
		}
//		}
		return sb;
	}
	
	public StringBuilder drawTabsDivs() {
		StringBuilder sb = new StringBuilder();
		if(tabsDivs != null){
			for(GridSterTabsDiv gridSterTabsDiv : tabsDivs){
//				Map<String, Object> valueMap = new HashMap<String, Object>();
//				valueMap.put("divId", gridSterTabsDiv.getDivId());
//				valueMap.put("tabsDivTitle", gridSterTabsDiv.getTabsDivTitle());
//				StrSubstitutor sub = new StrSubstitutor(valueMap, "$[","]");
//				sb.append(sub.replace(TemplateFactory.getTemplate("GridSter-tabsDiv")));
				sb.append("<div title='").append(gridSterTabsDiv.getTabsDivTitle())
				.append("' id='").append(gridSterTabsDiv.getDivId()).append("' class='gridster'><ul></ul></div>");
			}
			sb.append("</div>\");");
		}
		
		return sb;
	}
	
	public class GridSterWidget extends Container{
		
		private String liName;
		
		private String col;
		
		private String row;
		
		public String getCol() {
			return col;
		}

		public void setCol(String col) {
			this.col = col;
		}

		public String getRow() {
			return row;
		}

		public void setRow(String row) {
			this.row = row;
		}

		public String getLiName() {
			return liName;
		}

		public void setLiName(String liName) {
			this.liName = liName;
		}

		@Override
		public StringBuilder draw() {
			StringBuilder sb = new StringBuilder();
			sb.append(super.draw());
			return sb;
		}
		
	}
	
	public class GridSterTabsDiv extends Container{
		
		private String divId;
		
		private String tabsDivTitle;

		public String getDivId() {
			return divId;
		}

		public void setDivId(String divId) {
			this.divId = divId;
		}

		public String getTabsDivTitle() {
			return tabsDivTitle;
		}

		public void setTabsDivTitle(String tabsDivTitle) {
			this.tabsDivTitle = tabsDivTitle;
		}

		@Override
		public StringBuilder draw() {
			StringBuilder sb = new StringBuilder();
			sb.append(super.draw());
			return sb;
		}
	}
}
