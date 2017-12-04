package com.fable.insightview.platform.core.gridstertabs;

public class GridSterTabs extends Container{
	
	private String tabsId;
	
	public String getTabsId() {
		return tabsId;
	}

	public void setTabsId(String tabsId) {
		this.tabsId = tabsId;
	}

	@Override
	public StringBuilder draw() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.draw());
		return sb;
	}
}
