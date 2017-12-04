package com.fable.insightview.monitor.discover.entity;

import java.util.Date;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

public class SysNetworkDiscoverTask {
	@NumberGenerator(name = "MonitorCollectTaskPK")
	private Integer taskid;

	private Integer tasktype;

	private String webipaddress;

	private String ipaddress1;

	private String ipaddress2;

	private String netmask;

	private Integer hops;

	private Integer creator;

	private Date createtime;

	private String moClassNames;

	private int port;

	private Integer collectorid;
	private Integer operatestatus;
	private Integer updateinterval;
	private Integer status;
	private Date laststatustime;

	private Integer progressstatus;
	private Integer lastopresult;
	private String errorinfo;
	private String webIPAddress;

	// 供查询
	private String collectorName;
	private String laststatustimeStr;
	private String createtimeStr;
	private String creatorName;
	private String tasktypeInfo;
	private String statusInfo;
	private String progressStatusInfo;
	private String discoverRange;
	private String moClassLable;
	private Integer classID;
	// 设备任务中是否需要配置采集机任务 0：否，1：是
	private int isConfig;
	
	//数据库名
	private String dbName;
	
	private String errorID;

	// 任务类型是否在线
	private String isOffline;
	
	public Date getLaststatustime() {
		return laststatustime;
	}

	public void setLaststatustime(Date laststatustime) {
		this.laststatustime = laststatustime;
	}

	public Integer getUpdateinterval() {
		return updateinterval;
	}

	public void setUpdateinterval(Integer updateinterval) {
		this.updateinterval = updateinterval;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getProgressstatus() {
		return progressstatus;
	}

	public void setProgressstatus(Integer progressstatus) {
		this.progressstatus = progressstatus;
	}

	public Integer getLastopresult() {
		return lastopresult;
	}

	public void setLastopresult(Integer lastopresult) {
		this.lastopresult = lastopresult;
	}

	public String getErrorinfo() {
		return errorinfo;
	}

	public void setErrorinfo(String errorinfo) {
		this.errorinfo = errorinfo;
	}

	public String getWebipaddress() {
		return webipaddress;
	}

	public void setWebipaddress(String webipaddress) {
		this.webipaddress = webipaddress;
	}

	public Integer getOperatestatus() {
		return operatestatus;
	}

	public void setOperatestatus(Integer operatestatus) {
		this.operatestatus = operatestatus;
	}

	public Integer getTaskid() {
		return taskid;
	}

	public void setTaskid(Integer taskid) {
		this.taskid = taskid;
	}

	public Integer getTasktype() {
		return tasktype;
	}

	public void setTasktype(Integer tasktype) {
		this.tasktype = tasktype;
	}

	public String getIpaddress1() {
		return ipaddress1;
	}

	public void setIpaddress1(String ipaddress1) {
		this.ipaddress1 = ipaddress1 == null ? null : ipaddress1.trim();
	}

	public String getIpaddress2() {
		return ipaddress2;
	}

	public void setIpaddress2(String ipaddress2) {
		this.ipaddress2 = ipaddress2 == null ? null : ipaddress2.trim();
	}

	public String getNetmask() {
		return netmask;
	}

	public void setNetmask(String netmask) {
		this.netmask = netmask == null ? null : netmask.trim();
	}

	public Integer getHops() {
		return hops;
	}

	public void setHops(Integer hops) {
		this.hops = hops;
	}

	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Integer getCollectorid() {
		return collectorid;
	}

	public void setCollectorid(Integer collectorid) {
		this.collectorid = collectorid;
	}

	public String getMoClassNames() {
		return moClassNames;
	}

	public void setMoClassNames(String moClassNames) {
		this.moClassNames = moClassNames;
	}

	public String getCollectorName() {
		return collectorName;
	}

	public void setCollectorName(String collectorName) {
		this.collectorName = collectorName;
	}

	public String getWebIPAddress() {
		return webIPAddress;
	}

	public void setWebIPAddress(String webIPAddress) {
		this.webIPAddress = webIPAddress;
	}

	public String getLaststatustimeStr() {
		return laststatustimeStr;
	}

	public void setLaststatustimeStr(String laststatustimeStr) {
		this.laststatustimeStr = laststatustimeStr;
	}

	public String getCreatetimeStr() {
		return createtimeStr;
	}

	public void setCreatetimeStr(String createtimeStr) {
		this.createtimeStr = createtimeStr;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getTasktypeInfo() {
		return tasktypeInfo;
	}

	public void setTasktypeInfo(String tasktypeInfo) {
		this.tasktypeInfo = tasktypeInfo;
	}

	public String getStatusInfo() {
		return statusInfo;
	}

	public void setStatusInfo(String statusInfo) {
		this.statusInfo = statusInfo;
	}

	public String getProgressStatusInfo() {
		return progressStatusInfo;
	}

	public void setProgressStatusInfo(String progressStatusInfo) {
		this.progressStatusInfo = progressStatusInfo;
	}

	public String getDiscoverRange() {
		return discoverRange;
	}

	public void setDiscoverRange(String discoverRange) {
		this.discoverRange = discoverRange;
	}

	public int getIsConfig() {
		return isConfig;
	}

	public void setIsConfig(int isConfig) {
		this.isConfig = isConfig;
	}

	public String getMoClassLable() {
		return moClassLable;
	}

	public void setMoClassLable(String moClassLable) {
		this.moClassLable = moClassLable;
	}

	public Integer getClassID() {
		return classID;
	}

	public void setClassID(Integer classID) {
		this.classID = classID;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getErrorID() {
		return errorID;
	}

	public void setErrorID(String errorID) {
		this.errorID = errorID;
	}

	public String getIsOffline() {
		return isOffline;
	}

	public void setIsOffline(String isOffline) {
		this.isOffline = isOffline;
	}

}