/**
 * 分页对象
 */
package com.fable.insightview.platform.common.sqlparser;

import java.util.List;


/**
 * @author zhouwei
 * 
 */
public class Page<T> {

	/**
	 * 页码，从1开始
	 */
	private int pageNum;
	/**
	 * 页面大小
	 */
	private int pageSize;
	/**
	 * 起始行
	 */
	private int startRow;
	/**
	 * 末行
	 */
	private int endRow;
	/**
	 * 总数
	 */
	private long total;
	/**
	 * 总页数
	 */
	private int pages;
	

	private List<T> data;
	
	public Page(int pageNum, int pageSize , long total) {
		setPageNum(pageNum);
		this.pageSize = pageSize;
		setTotal(total);
	}

	public int getPages() {
		return pages;
	}

	public int getEndRow() {
		return endRow;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum <= 0 ? 1 : pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStartRow() {
		return startRow;
	}

	public long getTotal() {
		return total;
	}
	
	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public void setTotal(long total) {
		this.total = total;
		if (pageSize > 0) {
			pages = (int) (total / pageSize + ((total % pageSize == 0) ? 0 : 1));
		} else {
			pages = 1;
		}
		if (pageNum > pages) {
			pageNum = pages;
		}
		calculateStartAndEndRow();
	}

	/**
	 * 计算起止行号
	 */
	private void calculateStartAndEndRow() {
		this.startRow = this.pageNum > 0 ? (this.pageNum - 1) * this.pageSize
				: 0;
		this.endRow = this.startRow + this.pageSize
				* (this.pageNum > 0 ? 1 : 0);
	}

}
