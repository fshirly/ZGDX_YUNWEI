package com.fable.insightview.platform.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

/**
 * 
 * @TABLE_NAME: SysMenuModule
 * @Description:
 * @author: 武林
 * @Create at: Fri Dec 06 14:03:31 CST 2013
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "SysMenuModule")
public class SysMenuModuleBean extends com.fable.insightview.platform.itsm.core.entity.Entity
		implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sysmenu_gen")
	@TableGenerator(initialValue=10001, name = "sysmenu_gen", table = "SysKeyGenerator", pkColumnName = "GenName", valueColumnName = "GenValue", pkColumnValue = "SysMenuPK", allocationSize = 1)
	@Column(name = "MenuID")
	private Integer menuId;

	@Column(name = "MenuName")
	private String menuName;

	@Column(name = "MenuLevel")
	private short menuLevel;

	@Column(name = "ParentMenuID")
	private int parentMenuID;

	@Column(name = "MenuClass")
	private short menuClass;

	@Column(name = "ShowOrder")
	private int showOrder;

	@Column(name = "LinkURL")
	private String linkURL;

	@Column(name = "Icon")
	private String icon;

	@Column(name = "Descr")
	private String descr;
	@Transient
	private String parID;
	@Transient
	private SysMenuModuleBean parentSysMenu;
	@Transient
	private String navigationBar;
	
	@Transient
	private String state;
	
	@Transient
	private String parentMenuIDs;
	@Transient
	private List<SysMenuModuleBean> children = new ArrayList<SysMenuModuleBean>();
	
	public SysMenuModuleBean getParentSysMenu() {
		return parentSysMenu;
	}

	public void setParentSysMenu(SysMenuModuleBean parentSysMenu) {
		this.parentSysMenu = parentSysMenu;
	}

	@Transient
	private int gradeID;

	@Transient
	private int gradeName;

	@Transient
	private String organizationID;

	@Transient
	private String menuNameFilter;
	@Transient
	private String menuLevelFilter;
	@Transient
	private String parentMenuIDFilter;
	@Transient
	private String roleId;
	@Transient
	private String myLinkURL;
	
	public String getRoleId() {
		return roleId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<SysMenuModuleBean> getChildren() {
		return children;
	}

	public void setChildren(List<SysMenuModuleBean> children) {
		this.children = children;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getMenuNameFilter() {
		return menuNameFilter;
	}

	public String getParID() {
		return parID;
	}

	public void setParID(String parID) {
		this.parID = parID;
	}

	public void setMenuNameFilter(String menuNameFilter) {
		this.menuNameFilter = menuNameFilter;
	}

	public String getMenuLevelFilter() {
		return menuLevelFilter;
	}

	public void setMenuLevelFilter(String menuLevelFilter) {
		this.menuLevelFilter = menuLevelFilter;
	}

	public String getParentMenuIDFilter() {
		return parentMenuIDFilter;
	}

	public void setParentMenuIDFilter(String parentMenuIDFilter) {
		this.parentMenuIDFilter = parentMenuIDFilter;
	}

	public SysMenuModuleBean() {
		super();
	}


	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public short getMenuLevel() {
		return menuLevel;
	}

	public void setMenuLevel(short menuLevel) {
		this.menuLevel = menuLevel;
	}

	public int getParentMenuID() {
		return parentMenuID;
	}

	public void setParentMenuID(int parentMenuID) {
		this.parentMenuID = parentMenuID;
	}

	public short getMenuClass() {
		return menuClass;
	}

	public void setMenuClass(short menuClass) {
		this.menuClass = menuClass;
	}

	public int getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}

	public String getLinkURL() {
		return linkURL;
	}

	public void setLinkURL(String linkURL) {
		this.linkURL = linkURL;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public int getGradeID() {
		return gradeID;
	}

	public void setGradeID(int gradeID) {
		this.gradeID = gradeID;
	}

	public int getGradeName() {
		return gradeName;
	}

	public void setGradeName(int gradeName) {
		this.gradeName = gradeName;
	}

	public String getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}

	public String getMyLinkURL() {
		return myLinkURL;
	}

	public void setMyLinkURL(String myLinkURL) {
		this.myLinkURL = myLinkURL;
	}

	public String getParentMenuIDs() {
		return parentMenuIDs;
	}

	public void setParentMenuIDs(String parentMenuIDs) {
		this.parentMenuIDs = parentMenuIDs;
	}

	public String getNavigationBar() {
		return navigationBar;
	}

	public void setNavigationBar(String navigationBar) {
		this.navigationBar = navigationBar;
	}

}
