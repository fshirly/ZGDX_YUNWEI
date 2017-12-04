package com.fable.insightview.monitor.discover.entity;

import java.util.List;
import java.util.Map;

public class SynchronConstant { 
	private int resTypeId;
	
	private int assetTypeId;

	private int moId;

	private int moTypeId;

	private int resId;

	private Object content;
	//判断是组件还是设备， 1：组件
	private int isComponent;
	//所有组件类型以及其对应的资源ID
	private List<Map<String, Object>> allComponent;

	public int getAssetTypeId() {
		return assetTypeId;
	}

	public void setAssetTypeId(int assetTypeId) {
		this.assetTypeId = assetTypeId;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public int getResTypeId() {
		return resTypeId;
	}

	public void setResTypeId(int resTypeId) {
		this.resTypeId = resTypeId;
	}

	public int getMoId() {
		return moId;
	}

	public void setMoId(int moId) {
		this.moId = moId;
	}

	public int getMoTypeId() {
		return moTypeId;
	}

	public void setMoTypeId(int moTypeId) {
		this.moTypeId = moTypeId;
	}

	public int getResId() {
		return resId;
	}

	public void setResId(int resId) {
		this.resId = resId;
	}

	public int getIsComponent() {
		return isComponent;
	}

	public void setIsComponent(int isComponent) {
		this.isComponent = isComponent;
	}

	public List<Map<String, Object>> getAllComponent() {
		return allComponent;
	}

	public void setAllComponent(List<Map<String, Object>> allComponent) {
		this.allComponent = allComponent;
	}
 
}
