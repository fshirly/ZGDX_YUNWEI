package com.fable.insightview.platform.common.entity;

import java.util.List;

//import com.wordnik.swagger.annotations.ApiModel;
//import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author fangang
 * 树形数据对象，适应于树形控件和树形表格控件
 * @param <T> BO对象
 */
//@ApiModel(value = "树形数据对象")
public class TreeDataBean<T> {

	//@ApiModelProperty(value = "列")
	private List<ColumnBean> cols;
	
	//@ApiModelProperty(value = "数据")
	private List<T> data;
	
	public List<ColumnBean> getCols() {
		return cols;
	}
	public void setCols(List<ColumnBean> cols) {
		this.cols = cols;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	
}
