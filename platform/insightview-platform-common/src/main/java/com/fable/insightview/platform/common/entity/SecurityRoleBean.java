package com.fable.insightview.platform.common.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;

import com.fable.insightview.platform.itsm.core.entity.IdEntity;

/**
 * 角色信息
 * 
 * @author 武林
 */
@Entity
@Table(name = "SysRole")
public class SecurityRoleBean extends IdEntity implements Serializable,
		GrantedAuthority {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ROLEID")
	private Long id;

	@Transient
	private int organizationId;

	@Column(name = "RoleName")
	private String roleName;

	@Column(name = "Descr")
	private String descr;

	public SecurityRoleBean(String roleName) {
		this.roleName = roleName;
	}

	public SecurityRoleBean() {
	}

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return this.roleName;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;

	}
}