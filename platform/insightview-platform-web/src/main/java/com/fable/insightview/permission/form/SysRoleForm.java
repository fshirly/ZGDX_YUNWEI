package com.fable.insightview.permission.form;

/**
 * 单位组织FORMBEAN
 * 
 * @author 武林
 * 
 */

public class SysRoleForm implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int userId;
	private int SysRoleId;
	private String sysRoleName;
	private String parentSysRoleName;

	public SysRoleForm() {
		super();
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getSysRoleId() {
		return SysRoleId;
	}

	public void setSysRoleId(int SysRoleId) {
		this.SysRoleId = SysRoleId;
	}

	public String getSysRoleName() {
		return sysRoleName;
	}

	public void setSysRoleName(String sysRoleName) {
		this.sysRoleName = sysRoleName;
	}

	public String getParentSysRoleName() {
		return parentSysRoleName;
	}

	public void setParentSysRoleName(String parentSysRoleName) {
		this.parentSysRoleName = parentSysRoleName;
	}

}
