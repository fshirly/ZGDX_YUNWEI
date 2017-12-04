package com.fable.insightview.platform.itsm.core.util;

import java.util.List;

import javax.sql.DataSource;

/**
 * Flex Title: FlexiGridPageInfo Description:
 * 
 * @author shugl
 * @date 2011-3-12
 * @version 1.0
 */
public class FlexiGridPageInfo {
	/*
	 * 获得当前页数
	 */
	private int page = 1;
	/*
	 * 获得每页数据最大量
	 */
	private int rp = 10;
	/*
	 * 当前总记录数
	 */
	private long total;
	/*
	 * 页面显示的数据
	 */
	private List rows = null;
	
	private String sort = null;
	
	private String order = null;

	public int getPage() {
		return page;
	}

	public FlexiGridPageInfo() {
		super();
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRp() {
		return rp;
	}

	public void setRp(int rp) {
		this.rp = rp;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

}
