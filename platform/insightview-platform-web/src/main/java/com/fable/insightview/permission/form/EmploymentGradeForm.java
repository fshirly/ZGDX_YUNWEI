package com.fable.insightview.permission.form;

/**
 * 单位组织FORMBEAN
 * 
 * @author 武林
 * 
 */

public class EmploymentGradeForm implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int userId;
	private int organizationId;
	private String organizationName;
	private String parentOrganizationName;

	public EmploymentGradeForm() {
		super();
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getParentOrganizationName() {
		return parentOrganizationName;
	}

	public void setParentOrganizationName(String parentOrganizationName) {
		this.parentOrganizationName = parentOrganizationName;
	}

}
