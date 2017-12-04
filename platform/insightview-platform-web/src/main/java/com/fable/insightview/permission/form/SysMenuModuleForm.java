package com.fable.insightview.permission.form;

/**
 * 单位组织FORMBEAN
 * 
 * @author 武林
 * 
 */

public class SysMenuModuleForm implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String menuNameFilter;
	private String menuLevelFilter;
	private String parentMenuIDFilter;
	private String parID;

	public SysMenuModuleForm() {
		super();
	}

	public String getMenuNameFilter() {
		return menuNameFilter;
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

	public String getParID() {
		return parID;
	}

	public void setParID(String parID) {
		this.parID = parID;
	}

}
