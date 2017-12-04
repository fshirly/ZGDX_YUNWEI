package com.fable.insightview.monitor.host.entity;

public class Mine2Obj {
	private String type;
	private String name;
	
	public Mine2Obj(){
		
	}

	public Mine2Obj(String type,String name){
		this.type = type;
		this.name = name;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
