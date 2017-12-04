package com.fable.insightview.platform.common.entity;

import java.util.HashMap;
import java.util.List;

//@ApiModel(value = "Excel导出条件数据对象")
public class ExcelExportConditionBean {

	//@ApiModelProperty(value = "Map条件")
	private HashMap<String, String> condition;

	//@ApiModelProperty(value = "列")
	private List<ColumnBean> cols;

	//@ApiModelProperty(value = "listview名称")
	private String listviewName;

	public HashMap<String, String> getCondition() {
		return condition;
	}

	public void setCondition(HashMap<String, String> condition) {
		this.condition = condition;
	}

	public List<ColumnBean> getCols() {
		return cols;
	}

	public void setCols(List<ColumnBean> cols) {
		this.cols = cols;
	}

	public String getListviewName() {
		return listviewName;
	}

	public void setListviewName(String listviewName) {
		this.listviewName = listviewName;
	}

}
