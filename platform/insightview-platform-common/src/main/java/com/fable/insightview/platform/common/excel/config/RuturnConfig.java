package com.fable.insightview.platform.common.excel.config;

import java.util.HashMap;
import java.util.Map;

public class RuturnConfig {
	private String className = null;

	private Map propertyMap = new HashMap();
	
	private Map columnMap = new HashMap();
	
	private Map flagMap = new HashMap();
	
	private Map messageMap = new HashMap();
	
	public Map getFlagMap() {
		return flagMap;
	}

	public void setFlagMap(Map flagMap) {
		this.flagMap = flagMap;
	}

	public Map getMessageMap() {
		return messageMap;
	}

	public void setMessageMap(Map messageMap) {
		this.messageMap = messageMap;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Map getPropertyMap() {
		return propertyMap;
	}

	public void setPropertyMap(Map propertyMap) {
		this.propertyMap = propertyMap;
	}

	public Map getColumnMap() {
		return columnMap;
	}

	public void setColumnMap(Map columnMap) {
		this.columnMap = columnMap;
	}
	
	
}
