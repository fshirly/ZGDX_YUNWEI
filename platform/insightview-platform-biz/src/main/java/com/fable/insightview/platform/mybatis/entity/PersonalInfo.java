package com.fable.insightview.platform.mybatis.entity;

/**
 * @author Li jiuwei
 * @date   2015年4月9日 下午5:12:22
 */
public class PersonalInfo {
	
	private Integer userId;
	
	private String mobilePhone;
	
	private String telephone;
	
	private String email;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

}
