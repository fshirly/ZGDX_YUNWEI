package com.fable.insightview.monitor.alarmmgr.devicewblistsetting.entity;


public enum InterfaceOrPort {
	INTERFACE("1"),
	PORT("2"),
	INTERNET_ACCESS("3");
	private String value;
	private InterfaceOrPort(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	public boolean is(String value){
		return this.value.equals(value);
	}
}
