package com.fable.insightview.platform.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * 单位职位表
 * 
 * @author 武林
 */
@Entity
@Table(name = "SysEmploymentGrade")
public class EmploymentGradeBean extends com.fable.insightview.platform.itsm.core.entity.Entity
		implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "employmentgrade_gen")
	@TableGenerator(initialValue=10001, name = "employmentgrade_gen", table = "SysKeyGenerator", pkColumnName = "GenName", valueColumnName = "GenValue", pkColumnValue = "EmploymentGradePK", allocationSize = 1)
	@Column(name = "GRADEID")
	private int GradeID;

	@Column(name = "OrganizationID")
	private int organizationID;

	@Column(name = "GradeName")
	private String gradeName;

	@Column(name = "Descr")
	private String descr;

	public int getGradeID() {
		return GradeID;
	}

	public void setGradeID(int gradeID) {
		GradeID = gradeID;
	}

	public int getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(int organizationID) {
		this.organizationID = organizationID;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

}