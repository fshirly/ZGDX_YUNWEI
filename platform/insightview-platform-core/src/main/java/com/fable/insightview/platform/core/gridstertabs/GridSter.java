package com.fable.insightview.platform.core.gridstertabs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;

import com.fable.insightview.platform.core.gridstertabs.Grid.GridSterTabsDiv;

public class GridSter extends Container {
	
	String gridSterName;
	
	String marginsHorizontal;
	
	String marginsVertival;
	
	String baseDimensionsWidth;
	
	String baseDimensionsHeight;
	
	String moType; 
	
	boolean resize;
	
	private Grid grid;
	
	Integer gridsterCount;
	
	String outerGridSterName;
	
	Integer drawCount;
	
	String divId;
	
	String portalType;
	
	private List<GridSterTabs> tabs = new ArrayList<GridSterTabs>();
	
	public void addGridSterTabs(GridSterTabs gridSterTabs){
		tabs.add((GridSterTabs)gridSterTabs);
	}
	
	public String getGridSterName() {
		return gridSterName;
	}


	public void setGridSterName(String gridSterName) {
		this.gridSterName = gridSterName;
	}


	public String getMarginsHorizontal() {
		return marginsHorizontal;
	}


	public void setMarginsHorizontal(String marginsHorizontal) {
		this.marginsHorizontal = marginsHorizontal;
	}


	public String getMarginsVertival() {
		return marginsVertival;
	}


	public void setMarginsVertival(String marginsVertival) {
		this.marginsVertival = marginsVertival;
	}


	public String getBaseDimensionsWidth() {
		return baseDimensionsWidth;
	}


	public void setBaseDimensionsWidth(String baseDimensionsWidth) {
		this.baseDimensionsWidth = baseDimensionsWidth;
	}


	public String getBaseDimensionsHeight() {
		return baseDimensionsHeight;
	}


	public void setBaseDimensionsHeight(String baseDimensionsHeight) {
		this.baseDimensionsHeight = baseDimensionsHeight;
	}


	public boolean isResize() {
		return resize;
	}


	public void setResize(boolean resize) {
		this.resize = resize;
	}



	public String getMoType() {
		return moType;
	}


	public void setMoType(String moType) {
		this.moType = moType;
	}

	public String getPortalType() {
		return portalType;
	}

	public void setPortalType(String portalType) {
		this.portalType = portalType;
	}

	public Integer getDrawCount() {
		return drawCount;
	}
	
	public String getDivId() {
		return divId;
	}

	public void setDivId(String divId) {
		this.divId = divId;
	}

	public void setDrawCount(Integer drawCount) {
		this.drawCount = drawCount;
	}

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
		this.components.add(this.grid);
		grid.setParentContainer(this);
	}
	
	public Integer getGridsterCount() {
		return gridsterCount;
	}

	public void setGridsterCount(Integer gridsterCount) {
		this.gridsterCount = gridsterCount;
	}
	
	public String getOuterGridSterName() {
		return outerGridSterName;
	}

	public void setOuterGridSterName(String outerGridSterName) {
		this.outerGridSterName = outerGridSterName;
	}

	public StringBuilder drawGridsterPre(){
		StringBuilder sb = new StringBuilder();
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("gridSterName", this.getGridSterName());
		valueMap.put("divId", this.getDivId());
		valueMap.put("moType", this.getMoType());
		valueMap.put("portalType", this.getPortalType());
		valueMap.put("gridsterCount", this.getGridsterCount());
		StrSubstitutor sub = new StrSubstitutor(valueMap, "$[","]");
		sb.append("var refreshUrl; var moType; var portalType; var arrWidgetIndex = []; var gridster = []; $(function(){");
		sb.append(sub.replace(TemplateFactory.getTemplate("GridSter-pre")));
		return sb;
	}
	
	@Override
	public StringBuilder draw() {
		StringBuilder sb = new StringBuilder();
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("gridSterName", this.getGridSterName());
		valueMap.put("divId", this.getDivId());
		valueMap.put("marginsHorizontal", this.getMarginsHorizontal());
		valueMap.put("marginsVertival", this.getMarginsVertival());
		valueMap.put("baseDimensionsWidth", this.getBaseDimensionsWidth());
		valueMap.put("baseDimensionsHeight", this.getBaseDimensionsHeight());
		valueMap.put("resize", true);
		valueMap.put("moType", this.getMoType());
		valueMap.put("portalType", this.getPortalType());
		valueMap.put("gridsterCount", this.getGridsterCount());
		StrSubstitutor sub = new StrSubstitutor(valueMap, "$[","]");
		if(tabs.size() > 0){
			for (int i = 0; i < tabs.size(); i++) {
				//tabs_window
				valueMap.put("outerGridSterName", this.getOuterGridSterName());
				valueMap.put("tabsId", "tabs_window"+i);
				if(i == 0 && drawCount < 1){
					StrSubstitutor sub2 = new StrSubstitutor(valueMap, "$[","]");
					sb.append(sub2.replace(TemplateFactory.getTemplate("GridSter-tabsPre")))
					.append("outerDiv.append(\"<div id='").append("tabs_window"+i).append("' class='easyui-tabs viewtabs' fit='true' >")
					.append(grid.drawTabsDivs());
				}
				sb.append(sub.replace(TemplateFactory.getTemplate("GridSter-mid")));
				sb.append(grid.draw());
				sb.append(sub.replace(TemplateFactory.getTemplate("GridSter-end")));
				sb.append(grid.render());
				tabs.remove(i);
			}
		} else {
//			sb.append("var gridster = []; $(function(){");
//			sb.append(sub.replace(TemplateFactory.getTemplate("GridSter-pre")));
			sb.append(sub.replace(TemplateFactory.getTemplate("GridSter-mid")));
			sb.append(grid.draw());
			sb.append(sub.replace(TemplateFactory.getTemplate("GridSter-end")));
			sb.append(grid.render());
		}
//		sb.append("});");
		return sb;
	}
	
	public StringBuilder drawEnd(){
		StringBuilder sb = new StringBuilder();
		sb.append(TemplateFactory.getTemplate("portalViewJs"));
//		sb.append("tabsIndex = $('#tabsIndex').val();");
//		sb.append("var flag = $('#flag').val();");
//		sb.append("if(flag == 'device'){");
//		sb.append("$('#btnSave').hide();");
//		sb.append("}");
//		sb.append("var p = getRootPatch();");
//		sb.append("ss = ifrSrcMaps.keyset();");
//		sb.append("console.log('ss == '+ss);");	   	
//		sb.append("others = otherSrcMaps.keyset();");
//		sb.append("window.setTimeout(function(){");
//		sb.append("console.log('others == '+others);");
//		sb.append("for (var i=0; i<others.length-1; i++) {");
//		sb.append("$('#'+others[i]).ready(function(){ ");
//		sb.append("$('#'+others[i+1]).attr('src', getRootPatch()+otherSrcMaps[others[i+1]+'_']);");
//		sb.append("console.log(otherSrcMaps[others[i+1]+'_']);");
//		sb.append("});");
//		sb.append("if (i == others.length-2) {");
//		sb.append("console.log(ifrSrcMaps[ss[0]+'_']);");
//		sb.append("$('#'+ss[0]).attr('src', getRootPatch()+ifrSrcMaps[ss[0]+'_']);");
//		sb.append("}");
//		sb.append("}");
//		sb.append("$('#'+others[0]).attr('src', getRootPatch()+otherSrcMaps[others[0]+'_']);");
//		sb.append("}, 500);");
		sb.append("});");
		return sb;
	}
	
}
