package com.fable.insightview.platform.dictionary.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

/**
 * 
 * @TABLE_NAME: SysConstantTypeDef
 * @Description:
 * @author: caoj
 * @Create at:2014-06-10 
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "SysConstantTypeDef")
public class ConstantTypeDef extends com.fable.insightview.platform.itsm.core.entity.Entity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "constanttypedef_gen")
	@TableGenerator(initialValue=100001, name = "constanttypedef_gen", table = "SysKeyGenerator", pkColumnName = "GenName", valueColumnName = "GenValue", pkColumnValue = "SysConstantTypeDefPK", allocationSize = 1)
	@Column(name="ConstantTypeId")
	private Integer constantTypeId;
	
	@Column(name="ConstantTypeName")
	private String constantTypeName;
	
	@Column(name="ConstantTypeCName")
	private String constantTypeCName;
	
	@Column(name="ParentTypeID")
	private Integer parentTypeID;
	
	@Column(name="Remark")
	private String remark;
	
	@Column(name="EffTime")
	private Timestamp effTime;
	
	@Column(name="ExpTime")
	private Timestamp expTime;
	
	@Column(name="UpdateTime")
	private Timestamp updateTime;
	
	@Transient
	private String parentTypeName;
	
	@Transient
	private String constantTypeIds;
	
	public String getConstantTypeIds() {
		return constantTypeIds;
	}

	public void setConstantTypeIds(String constantTypeIds) {
		this.constantTypeIds = constantTypeIds;
	}

	@Transient
	private int level;

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Integer getConstantTypeId() {
		return constantTypeId;
	}

	public void setConstantTypeId(Integer constantTypeId) {
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

	public Integer getParentTypeID() {
		return parentTypeID;
	}

	public void setParentTypeID(Integer parentTypeID) {
		this.parentTypeID = parentTypeID;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	public Timestamp getEffTime() {
		return effTime;
	}

	public void setEffTime(Timestamp effTime) {
		this.effTime = effTime;
	}

	public Timestamp getExpTime() {
		return expTime;
	}

	public void setExpTime(Timestamp expTime) {
		this.expTime = expTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	

	public String getParentTypeName() {
		return parentTypeName;
	}

	public void setParentTypeName(String parentTypeName) {
		this.parentTypeName = parentTypeName;
	}

	public ConstantTypeDef() {
		super();
	}
	
	
}
