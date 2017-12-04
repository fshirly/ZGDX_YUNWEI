package com.fable.insightview.platform.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name = "SysConstantTypeDef")
public class SysConstantTypeBean extends com.fable.insightview.platform.itsm.core.entity.Entity {

	@Id
	@Column(name="ConstantTypeId")
	private Long constantTypeId;
	
	@Column(name="ConstantTypeName")
	private String constantTypeName;
	
	@Column(name="ConstantTypeCName")
	private String constantTypeCName;

	@Column(name="ParentTypeID")
	private Long parentTypeID;
	
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

	public SysConstantTypeBean() {
	}


	public Long getConstantTypeId() {
		return constantTypeId;
	}

	public void setConstantTypeId(Long constantTypeId) {
		this.constantTypeId = constantTypeId;
	}

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

	public Long getParentTypeID() {
		return parentTypeID;
	}

	public void setParentTypeID(Long parentTypeID) {
		this.parentTypeID = parentTypeID;
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

	public SysConstantTypeBean( Long constantTypeId,
			String constantTypeName, String constantTypeCName,
			Long parentTypeID, String remark, Date effTime, Date expTime,
			Date updateTime) {
		super();
		this.constantTypeId = constantTypeId;
		this.constantTypeName = constantTypeName;
		this.constantTypeCName = constantTypeCName;
		this.parentTypeID = parentTypeID;
		this.remark = remark;
		this.effTime = effTime;
		this.expTime = expTime;
		this.updateTime = updateTime;
	}

	
}
