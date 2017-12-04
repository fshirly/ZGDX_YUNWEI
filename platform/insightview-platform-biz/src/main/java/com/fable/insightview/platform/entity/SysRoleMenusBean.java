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
@Table(name = "SysRoleMenus")
public class SysRoleMenusBean extends
		com.fable.insightview.platform.itsm.core.entity.Entity implements
		Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sysrolemenu_gen")
	@TableGenerator(initialValue = 100001, name = "sysrolemenu_gen", table = "SysKeyGenerator", pkColumnName = "GenName", valueColumnName = "GenValue", pkColumnValue = "SysRoleMenuPK", allocationSize = 1)
	@Column(name = "ID")
	private int id;

	@Column(name = "RoleID")
	private int roleID;

	@Column(name = "MenuID")
	private int menuID;

	@Transient
	private String menuIdAttr;

	public String getMenuIdAttr() {
		return menuIdAttr;
	}

	public SysRoleMenusBean() {
	}

	public SysRoleMenusBean(int roleID, int menuID) {
		super();
		this.roleID = roleID;
		this.menuID = menuID;
	}

	public void setMenuIdAttr(String menuIdAttr) {
		this.menuIdAttr = menuIdAttr;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRoleID() {
		return roleID;
	}

	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}

	public int getMenuID() {
		return menuID;
	}

	public void setMenuID(int menuID) {
		this.menuID = menuID;
	}

}