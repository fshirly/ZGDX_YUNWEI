/**
 * 
 */
package com.fable.insightview.platform.listview.entity;

import java.util.HashMap;

/**
 * @author Administrator
 *
 */
//@ApiModel(value = "预览查询条件数据对象")
public class ListviewPreviewQueryConditionBean {
	
	//@ApiModelProperty(value = "Map条件")
	private HashMap<String, String> condition;

	//@ApiModelProperty(value = "每页数")
	private int pageSize;

	//@ApiModelProperty(value = "当前页")
	private int pageNum;
	
	//@ApiModelProperty(value = "listview预览配置信息")
	private ListviewDataBean previewData;

	public HashMap<String, String> getCondition() {
		return condition;
	}

	public void setCondition(HashMap<String, String> condition) {
		this.condition = condition;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public ListviewDataBean getPreviewData() {
		return previewData;
	}

	public void setPreviewData(ListviewDataBean previewData) {
		this.previewData = previewData;
	}

	
}
