package com.fable.insightview.platform.core.portal.widget;

import com.fable.insightview.platform.core.portal.Widget;

public class DataTables extends Widget{

	private String dataSourceUrl;//数据源 json
	
	private boolean paginate;//是否分分页
	
	private int displayLength;//每页条数
	
	private boolean autoWidth;//自动宽度

	public String getDataSourceUrl() {
		return dataSourceUrl;
	}

	public void setDataSourceUrl(String dataSourceUrl) {
		this.dataSourceUrl = dataSourceUrl;
	}

	public boolean isPaginate() {
		return paginate;
	}

	public void setPaginate(boolean paginate) {
		this.paginate = paginate;
	}

	public int getDisplayLength() {
		return displayLength;
	}

	public void setDisplayLength(int displayLength) {
		this.displayLength = displayLength;
	}

	public boolean isAutoWidth() {
		return autoWidth;
	}

	public void setAutoWidth(boolean autoWidth) {
		this.autoWidth = autoWidth;
	}
	
}
