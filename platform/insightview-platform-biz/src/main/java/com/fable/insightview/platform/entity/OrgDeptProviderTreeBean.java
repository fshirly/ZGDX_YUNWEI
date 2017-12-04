package com.fable.insightview.platform.entity;

import java.io.Serializable;

import org.hibernate.annotations.Entity;


@Entity
public class OrgDeptProviderTreeBean extends  com.fable.insightview.platform.itsm.core.entity.Entity
		implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String parentId;
	private String name;
	private String type;
	public OrgDeptProviderTreeBean() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
