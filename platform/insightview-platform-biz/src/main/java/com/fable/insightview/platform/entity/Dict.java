package com.fable.insightview.platform.entity;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name = "SysConstantItemDef")
public class Dict extends com.fable.insightview.platform.itsm.core.entity.Entity  {

	@Id
	@Column(name="ID")
	private Long id;
	
	@Column(name="ConstantTypeId")
	private Long constantTypeId;
	
	@Column(name="ConstantItemId")
	private Long constantItemId;
	
	@Column(name="ConstantItemName")
	private String constantItemName;
	
	@Column(name="ConstantItemAlias")
	private String constantItemAlias;
	
	@Column(name="Remark")
	private String remark;
	
	@Column(name="EffTime")
	@Temporal(TemporalType.DATE)
	private Date effTime;
	
	@Column(name="ExpTime")
	@Temporal(TemporalType.DATE)
	private Date expTime;
	
	@Column(name="UpdateTime")
	@Temporal(TemporalType.DATE)
	private Date updateTime;
	
	@Transient
	private String constantTypeName;

	@Transient
	private String constantTypeCName;
	
	public String getConstantTypeName() {
		return constantTypeName;
	}

	public void setConstantTypeName(String constantTypeName) {
		this.constantTypeName = constantTypeName;
	}

	public String getConstantTypeCName() {
		return constantTypeCName;
	}

	public void setConstantTypeCName(String constantTypeCName) {
		this.constantTypeCName = constantTypeCName;
	}

	public Dict() {
		
	}
	

	public Dict(Long id, Long constantTypeId, Long constantItemId,
			String constantItemName, String remark, Date effTime, Date expTime,
			Date updateTime, String constantTypeName, String constantTypeCName) {
		super();
		this.id = id;
		this.constantTypeId = constantTypeId;
		this.constantItemId = constantItemId;
		this.constantItemName = constantItemName;
		this.remark = remark;
		this.effTime = effTime;
		this.expTime = expTime;
		this.updateTime = updateTime;
		this.constantTypeName = constantTypeName;
		this.constantTypeCName = constantTypeCName;
	}

	public Long getID() {
		return id;
	}

	public void setID(Long id) {
		this.id = id;
	}

	public Long getConstantTypeId() {
		return constantTypeId;
	}

	public void setConstantTypeId(Long constantTypeId) {
		this.constantTypeId = constantTypeId;
	}

	public Long getConstantItemId() {
		return constantItemId;
	}

	public void setConstantItemId(Long constantItemId) {
		this.constantItemId = constantItemId;
	}

	public String getConstantItemName() {
		return constantItemName;
	}

	public void setConstantItemName(String constantItemName) {
		this.constantItemName = constantItemName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getEffTime() {
		return effTime;
	}

	public void setEffTime(Date effTime) {
		this.effTime = effTime;
	}

	public Date getExpTime() {
		return expTime;
	}

	public void setExpTime(Date expTime) {
		this.expTime = expTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getConstantItemAlias() {
		return constantItemAlias;
	}

	public void setConstantItemAlias(String constantItemAlias) {
		this.constantItemAlias = constantItemAlias;
	}
	
}
