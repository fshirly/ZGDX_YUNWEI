package com.fable.insightview.platform.common.bpm.activiti.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TaskUserRS implements Serializable{
	private static final long serialVersionUID = 3063536387762217431L;

	private List<TaskNodeRS> data = new ArrayList<TaskNodeRS>();
	private Integer total;
	private Integer start;
	private String sort;
	private String order;
	private Integer size;

	public List<TaskNodeRS> getData() {
		return data;
	}

	public void setData(List<TaskNodeRS> data) {
		this.data = data;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
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

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}
	
}
