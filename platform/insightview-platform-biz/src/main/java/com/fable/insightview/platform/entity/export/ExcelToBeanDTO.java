package com.fable.insightview.platform.entity.export;

import java.io.Serializable;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;

import com.fable.insightview.platform.itsm.core.entity.Entity;

/**
 * Excel导出DTO
 * @author xue.antai
 *
 */
public class ExcelToBeanDTO<T> extends Entity implements Serializable {
	private String colNameArrStr;
	private String colTitleArrStr;

	private String[] titleArr;
	private String[] colnameArr;
	
	private String attPlanName;
	
	private Integer periodCount;

	// 导出的excel文件名
	private String exlName;

	private List<T> expResource;

	public String getAttPlanName() {
		return attPlanName;
	}

	public void setAttPlanName(String attPlanName) {
		this.attPlanName = attPlanName;
	}

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

	public Integer getPeriodCount() {
		return periodCount;
	}

	public void setPeriodCount(Integer periodCount) {
		this.periodCount = periodCount;
	}
	
}
