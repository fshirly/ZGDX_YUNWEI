package com.fable.insightview.monitor.harvester.entity;

import java.util.Date;

import org.apache.ibatis.type.Alias;

@Alias("sysServerInstallService")
public class SysServerInstallService {
	private Integer id;

	private Integer serverid;

	private String ipaddress;// IP地址

	private String constantitemname;// 服务类型

	private String servicename;// 服务名称

	private Integer servicestatus;// 服务状态

	private String installdir;

	private String registertime;

	private String currentversion;

	private String newversion;

	private String lastchangetime;

	private String processName;

	private Date lastChangeDate;
	private Date lastUpdateTime;
	private Integer installServiceId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getServerid() {
		return serverid;
	}

	public void setServerid(Integer serverid) {
		this.serverid = serverid;
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public String getConstantitemname() {
		return constantitemname;
	}

	public void setConstantitemname(String constantitemname) {
		this.constantitemname = constantitemname;
	}

	public String getServicename() {
		return servicename;
	}

	public void setServicename(String servicename) {
		this.servicename = servicename;
	}

	public Integer getServicestatus() {
		return servicestatus;
	}

	public void setServicestatus(Integer servicestatus) {
		this.servicestatus = servicestatus;
	}

	public String getInstalldir() {
		return installdir;
	}

	public void setInstalldir(String installdir) {
		this.installdir = installdir;
	}

	public String getRegistertime() {
		return registertime;
	}

	public void setRegistertime(String registertime) {
		this.registertime = registertime
				.substring(0, registertime.length() - 2);
	}

	public String getCurrentversion() {
		return currentversion;
	}

	public void setCurrentversion(String currentversion) {
		this.currentversion = currentversion;
	}

	public String getNewversion() {
		return newversion;
	}

	public void setNewversion(String newversion) {
		this.newversion = newversion;
	}

	public String getLastchangetime() {
		return lastchangetime;
	}

	public void setLastchangetime(String lastchangetime) {
		this.lastchangetime = lastchangetime.substring(0, lastchangetime
				.length() - 2);
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public Date getLastChangeDate() {
		return lastChangeDate;
	}

	public void setLastChangeDate(Date lastChangeDate) {
		this.lastChangeDate = lastChangeDate;
	}

	public Integer getInstallServiceId() {
		return installServiceId;
	}

	public void setInstallServiceId(Integer installServiceId) {
		this.installServiceId = installServiceId;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	

}