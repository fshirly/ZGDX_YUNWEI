package com.fable.insightview.platform.backup.db;
/**
 * 数据库连接信息
 * @author duyang 20170812
 */
public class DbInfo {
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 驱动
	 */
	private String driver = "com.mysql.jdbc.Driver";
	/**
	 * 数据库连接地址，包含实例名称
	 */
	private String url;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
