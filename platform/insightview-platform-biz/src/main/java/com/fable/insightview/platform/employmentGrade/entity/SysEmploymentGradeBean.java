package com.fable.insightview.platform.employmentGrade.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

/**
 * 职位级别
 * 
 * @TABLE_NAME: SysEmploymentGrade
 * @author hanl
 * @Create at:2014-6-17
 * 
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "SysEmploymentGrade")
public class SysEmploymentGradeBean extends com.fable.insightview.platform.itsm.core.entity.Entity {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "employmentgrade_gen")
	@TableGenerator(initialValue=10001, name = "employmentgrade_gen", table = "SysKeyGenerator", pkColumnName = "GenName", valueColumnName = "GenValue", pkColumnValue = "SysEmploymentGradePK", allocationSize = 1)
	@Column(name = "GradeID")
	private Integer gradeID;
	
	@Column(name = "OrganizationID")
	private Integer organizationID;
	
	@Column(name = "GradeName")
	private String gradeName;
	
	@Column(name = "Descr")
	private String descr;
	
	@Transient
	private String organizationName;

	@Transient
	private String doName;
	
	@Transient
	private String nodeID;
	
	public String getNodeID() {
		return nodeID;
	}

	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}

	public Integer getGradeID() {
		return gradeID;
	}

	public void setGradeID(Integer gradeID) {
		this.gradeID = gradeID;
	}

	public Integer getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(Integer organizationID) {
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

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getDoName() {
		return doName;
	}

	public void setDoName(String doName) {
		this.doName = doName;
	}

}
