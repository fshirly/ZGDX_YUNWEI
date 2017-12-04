package com.fable.insightview.monitor.alarmmgr.devicewblistsetting.entity;

public enum SysBlackListOpStatus {
	INSERT("1"),
	MODIFY("2"),
	DELETE("3"),
	PUASE("4"),
	RECOVERY("5");
	private String value;
	SysBlackListOpStatus(String value){
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	public Integer getIntValue(){
		return Integer.valueOf(value);
	}
}
