package com.fable.insightview.monitor.host.entity;

import java.util.List;

public class CurveObj {
	private String name;
	private String type;
	private List<Float> data;
	private MineObj markPoint;
	
	public List<Float> getData() {
		return data;
	}

	public void setData(List<Float> data) {
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public MineObj getMarkPoint() {
		return markPoint;
	}

	public void setMarkPoint(MineObj markPoint) {
		this.markPoint = markPoint;
	}
 
}
