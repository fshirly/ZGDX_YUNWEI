package com.fable.insightview.platform.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

/**
 * 
 * @TABLE_NAME: SysUserGroupBean
 * @Description:
 * @author: 武林
 * @Create at: Wed Dec 04 10:39:09 CST 2013
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "SysUserGroup")
public class SysUserGroupBean extends com.fable.insightview.platform.itsm.core.entity.Entity {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sysuserinfo_gen")
	@TableGenerator(initialValue=10001, name = "sysuserinfo_gen", table = "SysKeyGenerator", pkColumnName = "GenName", valueColumnName = "GenValue", pkColumnValue = "SysUserInfoPK", allocationSize = 1)
	@Column(name = "GroupID")
	private int groupID;

	@ManyToOne
	@JoinColumn(name = "OrganizationID")
	private OrganizationBean organizationBean;

	@Column(name = "GroupName")
	private String groupName;

	@Column(name = "Descr")
	private String descr;
	
	@Transient
	private String doName;
	
	@Transient
	private String organizationName;

	public SysUserGroupBean() {
	}

	public int getGroupID() {
		return groupID;
	}

	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}

	public OrganizationBean getOrganizationBean() {
		return organizationBean;
	}

	public void setOrganizationBean(OrganizationBean organizationBean) {
		this.organizationBean = organizationBean;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getDoName() {
		return doName;
	}

	public void setDoName(String doName) {
		this.doName = doName;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	
}
