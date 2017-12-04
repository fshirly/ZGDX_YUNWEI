package com.fable.insightview.platform.restypedef.entity;

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
 * @ClassName:     ResTypeDefineInfo.java
 * @Description:   产品类型 
 * @author         郑小辉
 * @Date           2014-6-20 上午10:13:35 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "ResTypeDefine")
public class ResTypeDefineInfo extends com.fable.insightview.platform.itsm.core.entity.Entity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "resTypeDefine_gen")
	@TableGenerator(initialValue=10001, name = "resTypeDefine_gen", table = "SysKeyGenerator", pkColumnName = "GenName", valueColumnName = "GenValue", pkColumnValue = "PlatformResTypeDefinePK", allocationSize = 1)
	@Column(name = "ResTypeID")
	private int resTypeID;

	@Column(name = "ResTypeName")
	private String resTypeName;

	@Column(name = "ResTypeAlias")
	private String resTypeAlias;
	
	@Column(name = "ResTypeDescr")
	private String resTypeDescr;
	
	@Column(name = "ParentTypeId")
	private int parentTypeId;
	
	@Column(name = "CreateTime",updatable = false)
	private Date createTime;

	@Column(name = "LastModifyTime")
	private Date lastModifyTime;
	
	@Column(name = "Icon")
	private String icon;
	
	@Transient
	private String parentTypeName;
	
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

	public String getResTypeAlias() {
		return resTypeAlias;
	}

	public void setResTypeAlias(String resTypeAlias) {
		this.resTypeAlias = resTypeAlias;
	}

	public String getResTypeDescr() {
		return resTypeDescr;
	}

	public void setResTypeDescr(String resTypeDescr) {
		this.resTypeDescr = resTypeDescr;
	}

	public int getParentTypeId() {
		return parentTypeId;
	}

	public void setParentTypeId(int parentTypeId) {
		this.parentTypeId = parentTypeId;
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getParentTypeName() {
		return parentTypeName;
	}

	public void setParentTypeName(String parentTypeName) {
		this.parentTypeName = parentTypeName;
	}
	
	
}
