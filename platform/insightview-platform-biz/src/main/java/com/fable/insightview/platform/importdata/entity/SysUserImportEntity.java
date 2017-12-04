package com.fable.insightview.platform.importdata.entity;

import org.hibernate.validator.constraints.NotBlank;

import com.fable.insightview.platform.importdata.resolver.Mapping;
import com.fable.insightview.platform.importdata.resolver.Reloadable;
import com.fable.insightview.platform.importdata.resolver.metadata.Processor;

@Reloadable
public class SysUserImportEntity {
	@Mapping(columnName = "A")
	@NotBlank(message = "用户名不能为空!")
	private String userAccount;
	@Mapping(columnName = "B")
	@NotBlank(message = "员工编码不能为空!")
	private String employeeCode;
	@Mapping(columnName = "C")
	@NotBlank(message = "部门编码不能为空!")
	private String deptCode;
	@Mapping(columnName = "D")
	@NotBlank(message = "用户姓名不能为空!")
	private String userName;
	@Mapping(columnName = "E")
	@NotBlank(message = "用户类型不能为空!")
	private String userType;
	@Mapping(columnName = "F")
	@NotBlank(message = "手机号码不能为空!")
	private String mobilePhone;
	@Mapping(columnName = "G")
	private String telephone;
	@Mapping(columnName = "H")
	@NotBlank(message = "邮箱不能为空!")
	private String email;
	@Mapping(columnName = "I")
	@NotBlank(message = "职位不能为空!")
	private String gradeName;

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

}
