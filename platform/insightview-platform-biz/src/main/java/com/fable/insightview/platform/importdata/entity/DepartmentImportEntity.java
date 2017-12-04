package com.fable.insightview.platform.importdata.entity;

import org.hibernate.validator.constraints.NotBlank;

import com.fable.insightview.platform.importdata.resolver.Mapping;
import com.fable.insightview.platform.importdata.resolver.Reloadable;

@Reloadable
public class DepartmentImportEntity {
	@Mapping(columnName = "A")
	@NotBlank(message = "部门编码不能为空!")
	private String deptCode;
	@Mapping(columnName = "B")
	@NotBlank(message = "部门名称不能为空!")
	private String deptName;
	@Mapping(columnName = "C")
	@NotBlank(message = "所属单位不能为空!")
	private String organizationName;
	@Mapping(columnName = "D")
	private String parentDeptCode;
	@Mapping(columnName = "E")
	private String descr;

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}


	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getParentDeptCode() {
		return parentDeptCode;
	}

	public void setParentDeptCode(String parentDeptCode) {
		this.parentDeptCode = parentDeptCode;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

}
