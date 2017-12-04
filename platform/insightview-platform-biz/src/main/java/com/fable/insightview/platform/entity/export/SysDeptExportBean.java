package com.fable.insightview.platform.entity.export;

import java.util.List;

import com.fable.insightview.platform.entity.DepartmentBean;
import com.fable.insightview.platform.entity.SysUserInfoBean;



public class SysDeptExportBean<T> {
	
	private String colNameArrStr;
	private String colTitleArrStr;

	private String[] titleArr;
	private String[] colnameArr;

	// 导出的excel文件名
	private String exlName;

	private List<T> expResource;

	private DepartmentBean sysDeptBean;

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

	public DepartmentBean getSysDeptBean() {
		return sysDeptBean;
	}

	public void setSysDeptBean(DepartmentBean sysDeptBean) {
		this.sysDeptBean = sysDeptBean;
	}

}
