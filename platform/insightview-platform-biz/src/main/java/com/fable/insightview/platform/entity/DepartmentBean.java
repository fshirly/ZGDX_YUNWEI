package com.fable.insightview.platform.entity;

import java.io.Serializable;

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

import org.codehaus.jackson.annotate.JsonIgnore;


/**
 * 职位级别
 * 
 * @author 武林
 */
@Entity
@Table(name = "SysDepartment")
public class DepartmentBean extends
		com.fable.insightview.platform.itsm.core.entity.Entity implements
		Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "department_gen")
	@TableGenerator(initialValue = INIT_VALUE, name = "department_gen", table = "SysKeyGenerator", pkColumnName = "GenName", valueColumnName = "GenValue", pkColumnValue = "DepartmentPK", allocationSize = 1)
	@Column(name = "deptId")
	private Integer deptId;

	@Column(name = "DeptName")
	private String deptName;

	@ManyToOne
	@JoinColumn(name = "OrganizationID")
	private OrganizationBean organizationBean;

	@Column(name = "ParentDeptID")
	private Integer parentDeptID;

	@Column(name = "HeadOfDept")
	private Integer headOfDept;

	@Column(name = "Descr")
	private String descr;

	@Column(name = "DeptCode")
	private String deptCode;
	
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Transient
	private Integer organizationID;
	@Transient
	private String organizationName;

	@Transient
	@JsonIgnore
	private DepartmentBean parentDept;

	@Transient
	private String treeType;

	@Transient
	private String doName;

	@Transient
	private String headName;
	
	@Transient
	private String parentDeptName;

	@Transient
	private String nodeID;
	
	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public OrganizationBean getOrganizationBean() {
		return organizationBean;
	}

	public void setOrganizationBean(OrganizationBean organizationBean) {
		this.organizationBean = organizationBean;
	}

	public Integer getParentDeptID() {
		return parentDeptID;
	}

	public void setParentDeptID(Integer parentDeptID) {
		this.parentDeptID = parentDeptID;
	}

	public Integer getHeadOfDept() {
		return headOfDept;
	}

	public void setHeadOfDept(Integer headOfDept) {
		this.headOfDept = headOfDept;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public Integer getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(Integer organizationID) {
		this.organizationID = organizationID;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public DepartmentBean getParentDept() {
		return parentDept;
	}

	public void setParentDept(DepartmentBean parentDept) {
		this.parentDept = parentDept;
	}

	public String getTreeType() {
		return treeType;
	}

	public void setTreeType(String treeType) {
		this.treeType = treeType;
	}

	public String getDoName() {
		return doName;
	}

	public void setDoName(String doName) {
		this.doName = doName;
	}

	public String getHeadName() {
		return headName;
	}

	public void setHeadName(String headName) {
		this.headName = headName;
	}

	public String getNodeID() {
		return nodeID;
	}

	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getParentDeptName() {
		return parentDeptName;
	}

	public void setParentDeptName(String parentDeptName) {
		this.parentDeptName = parentDeptName;
	}

}