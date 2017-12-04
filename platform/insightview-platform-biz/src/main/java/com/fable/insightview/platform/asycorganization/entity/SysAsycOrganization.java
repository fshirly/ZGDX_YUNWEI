package com.fable.insightview.platform.asycorganization.entity;
/**
 * 异步机构实体类
 * @author liy
 *
 */
public class SysAsycOrganization {
   public String organizationID;
   public String parentOrgID;
   public String organizationName;
   public String descr;
   public String organizationCode;
public String getOrganizationID() {
	return organizationID;
}
public void setOrganizationID(String organizationID) {
	this.organizationID = organizationID;
}
public String getParentOrgID() {
	return parentOrgID;
}
public void setParentOrgID(String parentOrgID) {
	this.parentOrgID = parentOrgID;
}
public String getOrganizationName() {
	return organizationName;
}
public void setOrganizationName(String organizationName) {
	this.organizationName = organizationName;
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
}
