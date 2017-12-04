package com.fable.insightview.platform.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * 单位组织表
 * 
 * @author 武林
 */
@Entity
@Table(name = "SysOrganization")
public class OrganizationBean extends
		com.fable.insightview.platform.itsm.core.entity.Entity{

	private static final long serialVersionUID = 1L;
	@Id
	@NumberGenerator(name = "OrganizationPK")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "organization_gen")
	@TableGenerator(initialValue = INIT_VALUE, name = "organization_gen", table = "SysKeyGenerator", pkColumnName = "GenName", valueColumnName = "GenValue", pkColumnValue = "OrganizationPK", allocationSize = 1)
	@Column(name = "OrganizationID")
	private Integer organizationID;
	@Column(name = "ParentOrgID")
	private Integer parentOrgID;
	@Column(name = "OrganizationName")
	private String organizationName;
	@Column(name = "OrganizationCode")
	private String organizationCode;
	@Column(name = "Descr")
	private String descr;

	@Transient
	private String parentOrganizationName;

	@Transient
	private String doName;

	@Transient
	private String nodeID;

	public String getNodeID() {
		return nodeID;
	}

	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}

	public String getDoName() {
		return doName;
	}

	public void setDoName(String doName) {
		this.doName = doName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getParentOrganizationName() {
		return parentOrganizationName;
	}

	public void setParentOrganizationName(String parentOrganizationName) {
		this.parentOrganizationName = parentOrganizationName;
	}

	public OrganizationBean() {
	}

	public Integer getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(Integer organizationID) {
		this.organizationID = organizationID;
	}

	public Integer getParentOrgID() {
		return parentOrgID;
	}

	public void setParentOrgID(Integer parentOrgID) {
		this.parentOrgID = parentOrgID;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	 

}