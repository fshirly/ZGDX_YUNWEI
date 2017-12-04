package com.fable.insightview.platform.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

@SuppressWarnings("serial")
@Entity
@Table(name = "SysProviderUser")
public class SysProviderUserBean extends com.fable.insightview.platform.itsm.core.entity.Entity implements Serializable{
	
	
	@Id
	@NumberGenerator(name = "SysProviderUserPK")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sysprovideruserbean_gen")
	@TableGenerator(initialValue=10001, name = "sysprovideruserbean_gen", table = "SysKeyGenerator", pkColumnName = "GenName", valueColumnName = "GenValue", pkColumnValue = "SysProviderUserPK", allocationSize = 1)
	@Column(name="ID")
	private int id;
	
	@Column(name="ProviderID")
	private int providerId;
	
	@Column(name="UserID")
	private int userId;

	public SysProviderUserBean() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProviderId() {
		return providerId;
	}

	public void setProviderId(int providerId) {
		this.providerId = providerId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
}
