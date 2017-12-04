package com.fable.insightview.platform.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

/**
 * 邮件配置信息
 * 
 * @author 王淑平
 */
@Entity
@Table(name = "SysMailServerConfig")
public class SysMailServerConfigBean extends
		com.fable.insightview.platform.itsm.core.entity.Entity implements
		Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "mailserverconfig_gen")
	@TableGenerator(initialValue=10001, name = "mailserverconfig_gen", table = "SysKeyGenerator", pkColumnName = "GenName", valueColumnName = "GenValue", pkColumnValue = "MailserverConfigPK", allocationSize = 1)
	@Column(name = "ID")
	private int id;

	@Column(name = "MailServer")
	private String mailServer;

	@Column(name = "Port")
	private Integer port;

	@Column(name = "Timeout")
	private int timeout;

	@Column(name = "SenderAccount")
	private String senderAccount;

	@Column(name = "IsAuth")
	private double isAuth;

	@Column(name = "UserName")
	private String userName;

	@Column(name = "Password")
	private String password;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMailServer() {
		return mailServer;
	}

	public void setMailServer(String mailServer) {
		this.mailServer = mailServer;
	}


	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getSenderAccount() {
		return senderAccount;
	}

	public void setSenderAccount(String senderAccount) {
		this.senderAccount = senderAccount;
	}

	public double getIsAuth() {
		return isAuth;
	}

	public void setIsAuth(double isAuth) {
		this.isAuth = isAuth;
	}

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

	public SysMailServerConfigBean() {
	}

}