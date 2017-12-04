package com.fable.insightview.platform.common.entity;

import java.util.List;

/**
 * @author Administrator 表格数据对象，适应于表格控件
 * @param <T>
 *            BO对象
 */
//@ApiModel(value = "表格数据对象")
public class TableDataBean<T> {
	//@ApiModelProperty(value = "列")
	private List<ColumnBean> cols;

	//@ApiModelProperty(value = "数据")
	private List<T> data;

	/** 总记录数 */
	//@ApiModelProperty(value = "记录数")
	private int total;

	/** 页记录数 */
	//@ApiModelProperty(value = "每页数")
	private int pageSize;

	/** 页数 */
	//@ApiModelProperty(value = "当前页")
	private int pageNum;

	/** 分页可选范围 */
	//@ApiModelProperty(value = "分页可选范围")
	private String pageSizeList;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
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

	public String getPageSizeList() {
		return pageSizeList;
	}

	public void setPageSizeList(String pageSizeList) {
		this.pageSizeList = pageSizeList;
	}

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
