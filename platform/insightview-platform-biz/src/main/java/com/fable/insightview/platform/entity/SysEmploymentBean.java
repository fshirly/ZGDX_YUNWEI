package com.fable.insightview.platform.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

/**
 * 单位员工
 * 
 * @author maow
 */
@Entity
@Table(name = "SysEmployment")
public class SysEmploymentBean extends com.fable.insightview.platform.itsm.core.entity.Entity
		implements
			Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "employment_gen")
	@TableGenerator(initialValue=100001, name = "employment_gen", table = "SysKeyGenerator", pkColumnName = "GenName", valueColumnName = "GenValue", pkColumnValue = "EmploymentPK", allocationSize = 1)
	@Column(name = "EmpId")
	private Integer empId;

	@Column(name = "UserId")
	private Integer userId;

	@Column(name = "EmployeeCode")
	private String employeeCode;

	@Column(name = "OrganizationID")
	private Integer organizationID;

	@Column(name = "DeptId")
	private Integer deptID;

	@Column(name = "GradeID")
	private Integer gradeID;
	
	@Column(name = "StartDate")
	private Date startDate;
	
	@Column(name = "EndDate")
	private Date endDate;

	@Transient
	private String userName;
	
	public SysEmploymentBean() {
		super();
	}

	public SysEmploymentBean(String employeeCode) {
		super();
		this.employeeCode = employeeCode;
	}

	public SysEmploymentBean(Integer organizationID) {
		super();
		this.organizationID = organizationID;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public Integer getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(Integer organizationID) {
		this.organizationID = organizationID;
	}

	public Integer getDeptID() {
		return deptID;
	}

	public void setDeptID(Integer deptID) {
		this.deptID = deptID;
	}

	public Integer getGradeID() {
		return gradeID;
	}

	public void setGradeID(Integer gradeID) {
		this.gradeID = gradeID;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
