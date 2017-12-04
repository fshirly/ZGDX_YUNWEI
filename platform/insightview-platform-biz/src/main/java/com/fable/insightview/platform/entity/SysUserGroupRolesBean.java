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

/**
 * 角色信息
 * 
 * @author 武林
 */
@Entity
@Table(name = "SysUserGroupRoles")
public class SysUserGroupRolesBean extends com.fable.insightview.platform.itsm.core.entity.Entity
		implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sysgrouprole_gen")
	@TableGenerator(initialValue=10001, name = "sysgrouprole_gen", table = "SysKeyGenerator", pkColumnName = "GenName", valueColumnName = "GenValue", pkColumnValue = "SysGroupRolePK", allocationSize = 1)
	@Column(name = "ID")
	private int id;

	@Column(name = "GroupID")
	private int groupID;

	@Column(name = "RoleID")
	private int roleID;

	@Transient
	private String roleIdArr;

	
	public SysUserGroupRolesBean(int groupID, int roleID) {
		super();
		this.groupID = groupID;
		this.roleID = roleID;
	}

	public String getRoleIdArr() {
		return roleIdArr;
	}

	public void setRoleIdArr(String roleIdArr) {
		this.roleIdArr = roleIdArr;
	}

	public SysUserGroupRolesBean() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGroupID() {
		return groupID;
	}

	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}

	public int getRoleID() {
		return roleID;
	}

	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}

}