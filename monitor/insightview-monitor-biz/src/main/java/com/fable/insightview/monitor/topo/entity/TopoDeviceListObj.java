package com.fable.insightview.monitor.topo.entity;

import java.util.List;
import java.util.Map;

public class TopoDeviceListObj {
	
	private int total;
	
	private List<Map<String,Object>> row ;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<Map<String, Object>> getRow() {
		return row;
	}

	public void setRow(List<Map<String, Object>> row) {
		this.row = row;
	}

}
