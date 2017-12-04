package com.fable.insightview.platform.common.bpm.activiti.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TaskNodeList {
	private Collection<TaskNodeRS> data = new ArrayList<TaskNodeRS>();

	private Integer total;
	private Integer start;
	private String sort;
	private String order;
	private Integer size;
	
	public Collection<TaskNodeRS> getTaskNodeRS() {
		return data;
	}

	public void setTaskNodeRS(List<TaskNodeRS> tasks) {
		this.data = tasks;
	}		
	
	public Collection<TaskNodeRS> getData() {
		return data;
	}

	public void setData(Collection<TaskNodeRS> data) {
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
