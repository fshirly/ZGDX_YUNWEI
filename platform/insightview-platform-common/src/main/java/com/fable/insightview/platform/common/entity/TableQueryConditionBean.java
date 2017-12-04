package com.fable.insightview.platform.common.entity;

import java.util.HashMap;

//@ApiModel(value = "表格查询条件数据对象")
public class TableQueryConditionBean {

	//@ApiModelProperty(value = "Map条件")
	private HashMap<String, String> condition;

	//@ApiModelProperty(value = "每页数")
	private int pageSize;

	//@ApiModelProperty(value = "当前页")
	private int pageNum;
	
	//@ApiModelProperty(value = "listview名称")
	private String listviewName;

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

	public String getListviewName() {
		return listviewName;
	}

	public void setListviewName(String listviewName) {
		this.listviewName = listviewName;
	}

}
