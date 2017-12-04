package com.fable.insightview.platform.ipmanager.entity;

import java.util.List;

public class SubnetExportBean<T> {
	
	private String colNameArrStr;
	private String colTitleArrStr;
	private Integer belongSubnetId;
	private Integer belongDeptId;

	private String[] titleArr;
	private String[] colnameArr;
	private String condition;

	// 导出的excel文件名
	private String exlName;

	private List<T> expResource;


	public String getColNameArrStr() {
		return colNameArrStr;
	}

	public void setColNameArrStr(String colNameArrStr) {
		this.colNameArrStr = colNameArrStr;
	}

	public String getColTitleArrStr() {
		return colTitleArrStr;
	}

	public void setColTitleArrStr(String colTitleArrStr) {
		this.colTitleArrStr = colTitleArrStr;
	}

	public String[] getTitleArr() {
		return titleArr;
	}

	public void setTitleArr(String[] titleArr) {
		this.titleArr = titleArr;
	}

	public String[] getColnameArr() {
		return colnameArr;
	}

	public void setColnameArr(String[] colnameArr) {
		this.colnameArr = colnameArr;
	}

	public String getExlName() {
		return exlName;
	}

	public void setExlName(String exlName) {
		this.exlName = exlName;
	}

	public List<T> getExpResource() {
		return expResource;
	}

	public void setExpResource(List<T> expResource) {
		this.expResource = expResource;
	}

	public Integer getBelongSubnetId() {
		return belongSubnetId;
	}

	public void setBelongSubnetId(Integer belongSubnetId) {
		this.belongSubnetId = belongSubnetId;
	}

	public Integer getBelongDeptId() {
		return belongDeptId;
	}

	public void setBelongDeptId(Integer belongDeptId) {
		this.belongDeptId = belongDeptId;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

}
