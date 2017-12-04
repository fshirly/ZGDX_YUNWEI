package com.fable.insightview.platform.formdesign.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * 业务字段关联表
 * 
 * @author Maowei
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "FdBusinessAttributes")
public class FdBusinessAttributes extends
		com.fable.insightview.platform.itsm.core.entity.Entity {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "fdBusinessAttributes_gen")
	@TableGenerator(initialValue = INIT_VALUE, name = "fdBusinessAttributes_gen", table = "SysKeyGenerator", pkColumnName = "GenName", valueColumnName = "GenValue", pkColumnValue = "FdBusinessAttributesPK", allocationSize = 1)
	@Column(name = "Id")
	private Integer id;

	@Column(name = "BusinessId")
	private String businessId;

	@Column(name = "BusinessType")
	private String businessType;

	@Column(name = "AttributeId")
	private Integer attributeId;

	@Column(name = "AttributeTable")
	private String attributeTable;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public Integer getAttributeId() {
		return attributeId;
	}

	public void setAttributeId(Integer attributeId) {
		this.attributeId = attributeId;
	}

	public String getAttributeTable() {
		return attributeTable;
	}

	public void setAttributeTable(String attributeTable) {
		this.attributeTable = attributeTable;
	}

}
