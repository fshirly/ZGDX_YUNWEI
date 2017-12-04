package com.fable.insightview.platform.common.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.fable.insightview.platform.common.service.ISecurityUserService;
import com.fable.insightview.platform.itsm.core.entity.IdEntity;
import com.fable.insightview.platform.sysinit.SystemParamInit;

/**
 * 
 * @TABLE_NAME: SysUserInfo
 * @Description:
 * @author: 武林
 * @Create at: Wed Dec 04 10:39:09 CST 2013
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "SysUserInfo")
public class SecurityUserInfoBean  extends IdEntity implements Serializable, UserDetails {
	

	@Id
	@Column(name = "USERID")
	private Long id;

	@Column(name = "UserAccount")
	private String userAccount;

	@Column(name = "UserName")
	private String userName;

	@Column(name = "UserPassword")
	private String userPassword;

	@Column(name = "MobilePhone")
	private String mobilePhone;

	@Column(name = "Email")
	private String email;

	@Column(name = "Telephone")
	private String telephone;

	@Column(name = "IsAutoLock")
	private int isAutoLock;

	@Column(name = "Status")
	private int status;

	@Column(name = "UserType")
	private int userType;

	@Column(name = "CreateTime")
	private Date createTime;

	@Column(name = "LastModifyTime")
	private Date lastModifyTime;

	@Column(name = "lockedTime")
	private Date lockedTime;

	@Column(name = "LockedReason")
	private String lockedReason;

	@Column(name = "State")
	private int state;

	@Transient
	private String groupIdFilter;
	@Transient
	private List<SecurityRoleBean> roles;

	public String getGroupIdFilter() {
		return groupIdFilter;
	}

	public void setGroupIdFilter(String groupIdFilter) {
		this.groupIdFilter = groupIdFilter;
	}

	public SecurityUserInfoBean() {
		super();
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public int getIsAutoLock() {
		return isAutoLock;
	}

	public void setIsAutoLock(int isAutoLock) {
		this.isAutoLock = isAutoLock;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public Date getLockedTime() {
		return lockedTime;
	}

	public void setLockedTime(Date lockedTime) {
		this.lockedTime = lockedTime;
	}

	public String getLockedReason() {
		return lockedReason;
	}

	public void setLockedReason(String lockedReason) {
		this.lockedReason = lockedReason;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		/************************update liujinb start**************************/
		
		boolean ngomsEnabled = false;
		try {
			ngomsEnabled = Boolean.parseBoolean(SystemParamInit.getValueByKey("ngoms.enabled"));
		} catch (Exception e) {
			ngomsEnabled = false;
		}
		if (ngomsEnabled){
			List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
			SimpleGrantedAuthority item = new SimpleGrantedAuthority(
					"ROLE_USER");
			auths.add(item);
			return auths;
		}else{
			WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();  
			ISecurityUserService securityUserService = (ISecurityUserService)wac.getBean("securityUserService");
			roles = securityUserService.updateRoleInfos(this.userAccount);
			return roles;
		}
		/************************update liujinb end**************************/
		
	}
	
	

	public void setRoles(List<SecurityRoleBean> roles) {
		this.roles = roles;
	}
	
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.userPassword;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.userAccount;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub
		this.id = id;
	}

}
