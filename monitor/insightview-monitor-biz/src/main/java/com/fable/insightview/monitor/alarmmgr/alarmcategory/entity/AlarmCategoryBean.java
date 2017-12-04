package com.fable.insightview.monitor.alarmmgr.alarmcategory.entity;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;
/**
 * 告警分类（按OID分）
 * @Description:AlarmCategoryBean
 * @author zhurt
 * @date 2014-7-17
 */

@Alias("gategoryInfo")
public class AlarmCategoryBean {
	@NumberGenerator(name = "CategoryID")
	private int categoryID;	//告警分类ID
	private String categoryName;//分类名称
	private String alarmOID;	//SNMP企业私有ID
	private int isSystem; // '1：系统定义 0：用户自定义',
	private String descr;	//备注

	public AlarmCategoryBean() {
	}

	public int getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getAlarmOID() {
		return alarmOID;
	}

	public void setAlarmOID(String alarmOID) {
		this.alarmOID = alarmOID;
	}

	public int getIsSystem() {
		return isSystem;
	}

	public void setIsSystem(int isSystem) {
		this.isSystem = isSystem;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

}
