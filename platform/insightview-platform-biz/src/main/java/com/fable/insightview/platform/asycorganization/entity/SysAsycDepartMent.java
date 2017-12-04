package com.fable.insightview.platform.asycorganization.entity;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * 异步部门信息
 * @author Administrator
 *
 */
public class SysAsycDepartMent {
  @NumberGenerator(name="deptID")
  public String deptID;
  public String deptName;
  public String deptCode;
  public String organizationID;
  public String parentDeptID;
  public String headOfDept;
  public String descr;
  public String organizationCode;
  public String organizationName;
public String getDeptID() {
	return deptID;
}
public void setDeptID(String deptID) {
	this.deptID = deptID;
}
public String getDeptName() {
	return deptName;
}
public void setDeptName(String deptName) {
	this.deptName = deptName;
}
public String getDeptCode() {
	return deptCode;
}
public void setDeptCode(String deptCode) {
	this.deptCode = deptCode;
}
public String getOrganizationID() {
	return organizationID;
}
public void setOrganizationID(String organizationID) {
	this.organizationID = organizationID;
}
public String getParentDeptID() {
	return parentDeptID;
}
public void setParentDeptID(String parentDeptID) {
	this.parentDeptID = parentDeptID;
}
public String getHeadOfDept() {
	return headOfDept;
}
public void setHeadOfDept(String headOfDept) {
	this.headOfDept = headOfDept;
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
public String getOrganizationName() {
	return organizationName;
}
public void setOrganizationName(String organizationName) {
	this.organizationName = organizationName;
}
}
