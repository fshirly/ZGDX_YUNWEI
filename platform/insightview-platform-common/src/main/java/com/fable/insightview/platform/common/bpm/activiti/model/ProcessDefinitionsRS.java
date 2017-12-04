package com.fable.insightview.platform.common.bpm.activiti.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class ProcessDefinitionsRS implements Serializable{

	private static final long serialVersionUID = -555493363623066337L;

	private Collection<DefinitionsRS> data = new ArrayList<DefinitionsRS>();

	private Integer total;
	private Integer start;
	private String sort;
	private String order;
	private Integer size;
	
	public Collection<DefinitionsRS> getDefinitions() {
		return data;
	}

	public void setDefinitions(List<DefinitionsRS> definitions) {
		this.data = definitions;
	}		
	
	public Collection<DefinitionsRS> getData() {
		return data;
	}

	public void setData(Collection<DefinitionsRS> data) {
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