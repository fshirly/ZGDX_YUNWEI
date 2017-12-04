package com.fable.insightview.platform.rescatedef.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;


/**
 * @ClassName:     ResCateDefineInfo.java
 * @Description:   产品目录 
 * @author         郑小辉
 * @Date           2014-6-24 下午04:25:49 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "ResCategoryDefine")
public class ResCateDefInfo extends com.fable.insightview.platform.itsm.core.entity.Entity  {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "resCateDef_gen")
	@TableGenerator(initialValue=10001, name = "resCateDef_gen", table = "SysKeyGenerator", pkColumnName = "GenName", valueColumnName = "GenValue", pkColumnValue = "PlatformResCateDefPK", allocationSize = 1)
	@Column(name = "ResCategoryID")
	private int resCategoryID;
	
	@Column(name = "ResCategoryName")
	private String  resCategoryName;
	
	@Column(name = "ResCategoryAlias")
	private String  resCategoryAlias;
	
	@Column(name = "ResCategoryDescr")
	private String  resCategoryDescr;
	
	@Column(name = "ResManufacturerID")
	private int resManufacturerID;
	
	@Column(name = "ResModel")
	private String  resModel;
	
	@Column(name = "CreateTime",updatable = false)
	private Date	createTime;
	
	@Column(name = "LastModifyTime")
	private Date lastModifyTime;
	

	@Column(name = "ResTypeID")
	private int resTypeID;
	
	@Transient
	private String resTypeName;
	
	@Transient
	private String  resManufacturerName;

	public int getResCategoryID() {
		return resCategoryID;
	}

	public void setResCategoryID(int resCategoryID) {
		this.resCategoryID = resCategoryID;
	}

	public String getResCategoryName() {
		return resCategoryName;
	}

	public void setResCategoryName(String resCategoryName) {
		this.resCategoryName = resCategoryName;
	}

	public String getResCategoryAlias() {
		return resCategoryAlias;
	}

	public void setResCategoryAlias(String resCategoryAlias) {
		this.resCategoryAlias = resCategoryAlias;
	}

	public String getResCategoryDescr() {
		return resCategoryDescr;
	}

	public void setResCategoryDescr(String resCategoryDescr) {
		this.resCategoryDescr = resCategoryDescr;
	}

	public int getResManufacturerID() {
		return resManufacturerID;
	}

	public void setResManufacturerID(int resManufacturerID) {
		this.resManufacturerID = resManufacturerID;
	}

	public String getResModel() {
		return resModel;
	}

	public void setResModel(String resModel) {
		this.resModel = resModel;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public String getResManufacturerName() {
		return resManufacturerName;
	}

	public void setResManufacturerName(String resManufacturerName) {
		this.resManufacturerName = resManufacturerName;
	}

	public int getResTypeID() {
		return resTypeID;
	}

	public void setResTypeID(int resTypeID) {
		this.resTypeID = resTypeID;
	}

	public String getResTypeName() {
		return resTypeName;
	}

	public void setResTypeName(String resTypeName) {
		this.resTypeName = resTypeName;
	}
	
}
