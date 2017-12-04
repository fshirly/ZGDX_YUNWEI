package com.fable.insightview.platform.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * 
 * @TABLE_NAME: SysUserGroupBean
 * @Description:
 * @author: 武林
 * @Create at: Wed Dec 04 10:39:09 CST 2013
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "OrganizationalEntity")
public class OrganizationalEntityBean extends com.fable.insightview.platform.itsm.core.entity.Entity {
	@Column(name = "DTYPE")
	private String dtype;

	@Id
	@Column(name = "id")
	private String id;

	public String getDtype() {
		return dtype;
	}

	public void setDtype(String dtype) {
		this.dtype = dtype;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
