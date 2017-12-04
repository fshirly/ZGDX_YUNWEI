package com.fable.insightview.platform.managedDomain.entity;

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
 * @TABLE_NAME: SysManagedDomain
 * @Description:
 * @author: caoj
 * @Create at:2014-06-10 
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "SysManagedDomain")
public class ManagedDomain extends com.fable.insightview.platform.itsm.core.entity.Entity{
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sysmanageddomain_gen")
	@TableGenerator(initialValue=10001, name = "sysmanageddomain_gen", table = "SysKeyGenerator", pkColumnName = "GenName", valueColumnName = "GenValue", pkColumnValue = "SysManagedDomainPK", allocationSize = 1)
	@Column(name="DomainID")
	private Integer domainId;
	
	@Column(name="DomainName")
	private String domainName;
	
	@Column(name="DomainAlias")
	private String domainAlias;
	
	@Column(name="ShowOrder")
	private Integer showOrder;
	
	@Column(name="OrganizationID")
	private Integer organizationId;
	
	@Column(name="DomainDescr")
	private String domainDescr;
	
	@Column(name="ParentID")
	private String parentId;
	
	@Transient
	private String organizationName;
	
	@Transient
	private String parentDomainName;
	
	public String getParentDomainName() {
		return parentDomainName;
	}


	public void setParentDomainName(String parentDomainName) {
		this.parentDomainName = parentDomainName;
	}

	@Transient
	private Integer level;
	
	public Integer getLevel() {
		return level;
	}


	public void setLevel(Integer level) {
		this.level = level;
	}


	public ManagedDomain() {
		super();
	}


	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getDomainAlias() {
		return domainAlias;
	}

	public void setDomainAlias(String domainAlias) {
		this.domainAlias = domainAlias;
	}

	public Integer getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(Integer showOrder) {
		this.showOrder = showOrder;
	}


	public String getDomainDescr() {
		return domainDescr;
	}

	public void setDomainDescr(String domainDescr) {
		this.domainDescr = domainDescr;
	}

	
	public Integer getDomainId() {
		return domainId;
	}


	public void setDomainId(Integer domainId) {
		this.domainId = domainId;
	}


	public Integer getOrganizationId() {
		return organizationId;
	}


	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}


	public String getParentId() {
		return parentId;
	}


	public void setParentId(String parentId) {
		this.parentId = parentId;
	}


	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	
}
