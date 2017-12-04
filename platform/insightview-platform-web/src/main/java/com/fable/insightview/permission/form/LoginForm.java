package com.fable.insightview.permission.form;

/**
 * 单位组织FORMBEAN
 * 
 * @author 武林
 * 
 */

public class LoginForm implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String userAccount;
	private String userPassword;

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

}
