package com.fable.insightview.monitor.database.entity;

import java.util.Date;

import org.apache.ibatis.type.Alias;

@Alias("MODBMSServer")
public class MODBMSServerBean {
	private Integer moid;

	private String moname;

	private String moalias;

	private String dbmstype;

	private String serverversion;

	private String ip;

	private Integer port;

	private String username;

	private String password;

	private String operstatus;

	private String adminstatus;

	private Integer alarmlevel;

	private Integer domainid;

	private Date createtime;

	private Date lastupdatetime;

	private Integer createby;

	private Integer updateby;

	private Integer moClassId;
	// 实例 instance
	private String instancename;// 实例名称

	private String deviceip;// 所属主机IP

	private String dbname;// 所属资源名称

	private String totalsize;// 实例总空间

	private String freesize;// 实例空闲空间

	private String installpath;// 安装路径

	private Date starttime;// 启动时间

	private String levelicon;// 告警图标

	private double deviceavailability;
	// 表空间
	private String tbsname;// 表空间名称

	private String kpiname;

	private String perfvalue;

	private double tbsusage;// 表空间使用率

	private Integer dbmsMoId; // 数据库服务对象ID

	private Integer dbMoId; // 数据库资源对象ID

	private int perfValueAvai;
	
	private int nodeNum;// 节点数
	
	private Integer id;
	
	private String serverName;
	
	private double spilledUsage; //排序溢出率
	private double logUsage; //日志使用率
	private double shareMemUsage; //共享排序内存利用率
	private double waitingLockUsage; //等待锁定的应用程序比率
	private double pkgCacheHits; //程序包高速缓存命中率
	private double catCacheHits; //目录高速缓存命中率
	// 供查询使用
	private String domainName;
	private String operStatusName;
	private String alarmLevelName;
	private String operaTip;
	private Integer moDeviceId;
	private String formatTime;
	private Integer templateID;
	
	private Date updateAlarmTime;
	private String durationTime;//持续时间
	private Integer doIntervals; 
	private Integer defDoIntervals;
	private Date collectTime;
	
	public String getOperaTip() {
		return operaTip;
	}

	public void setOperaTip(String operaTip) {
		this.operaTip = operaTip;
	}

	public double getTbsusage() {
		return tbsusage;
	}

	public void setTbsusage(double tbsusage) {
		this.tbsusage = tbsusage;
	}

	public String getTbsname() {
		return tbsname;
	}

	public void setTbsname(String tbsname) {
		this.tbsname = tbsname;
	}

	public String getInstancename() {
		return instancename;
	}

	public void setInstancename(String instancename) {
		this.instancename = instancename;
	}

	public double getDeviceavailability() {
		return deviceavailability;
	}

	public void setDeviceavailability(double deviceavailability) {
		this.deviceavailability = deviceavailability;
	}

	public String getDeviceip() {
		return deviceip;
	}

	public void setDeviceip(String deviceip) {
		this.deviceip = deviceip;
	}

	public String getDbname() {
		return dbname;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	public String getTotalsize() {
		return totalsize;
	}

	public void setTotalsize(String totalsize) {
		this.totalsize = totalsize;
	}

	public String getFreesize() {
		return freesize;
	}

	public void setFreesize(String freesize) {
		this.freesize = freesize;
	}

	public String getKpiname() {
		return kpiname;
	}

	public void setKpiname(String kpiname) {
		this.kpiname = kpiname;
	}

	public String getPerfvalue() {
		return perfvalue;
	}

	public void setPerfvalue(String perfvalue) {
		this.perfvalue = perfvalue;
	}

	public String getInstallpath() {
		return installpath;
	}

	public void setInstallpath(String installpath) {
		this.installpath = installpath;
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public String getLevelicon() {
		return levelicon;
	}

	public void setLevelicon(String levelicon) {
		this.levelicon = levelicon;
	}

	public Integer getMoid() {
		return moid;
	}

	public void setMoid(Integer moid) {
		this.moid = moid;
	}

	public String getMoname() {
		return moname;
	}

	public void setMoname(String moname) {
		this.moname = moname == null ? null : moname.trim();
	}

	public String getMoalias() {
		return moalias;
	}

	public void setMoalias(String moalias) {
		this.moalias = moalias == null ? null : moalias.trim();
	}

	public String getDbmstype() {
		return dbmstype;
	}

	public void setDbmstype(String dbmstype) {
		this.dbmstype = dbmstype == null ? null : dbmstype.trim();
	}

	public String getServerversion() {
		return serverversion;
	}

	public void setServerversion(String serverversion) {
		this.serverversion = serverversion == null ? null : serverversion
				.trim();
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip == null ? null : ip.trim();
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public String getOperstatus() {
		return operstatus;
	}

	public void setOperstatus(String operstatus) {
		this.operstatus = operstatus;
	}

	public String getAdminstatus() {
		return adminstatus;
	}

	public void setAdminstatus(String adminstatus) {
		this.adminstatus = adminstatus;
	}

	public Integer getAlarmlevel() {
		return alarmlevel;
	}

	public void setAlarmlevel(Integer alarmlevel) {
		this.alarmlevel = alarmlevel;
	}

	public Integer getDomainid() {
		return domainid;
	}

	public void setDomainid(Integer domainid) {
		this.domainid = domainid;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getLastupdatetime() {
		return lastupdatetime;
	}

	public void setLastupdatetime(Date lastupdatetime) {
		this.lastupdatetime = lastupdatetime;
	}

	public Integer getCreateby() {
		return createby;
	}

	public void setCreateby(Integer createby) {
		this.createby = createby;
	}

	public Integer getUpdateby() {
		return updateby;
	}

	public void setUpdateby(Integer updateby) {
		this.updateby = updateby;
	}

	public Integer getMoClassId() {
		return moClassId;
	}

	public void setMoClassId(Integer moClassId) {
		this.moClassId = moClassId;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getOperStatusName() {
		return operStatusName;
	}

	public void setOperStatusName(String operStatusName) {
		this.operStatusName = operStatusName;
	}

	public String getAlarmLevelName() {
		return alarmLevelName;
	}

	public void setAlarmLevelName(String alarmLevelName) {
		this.alarmLevelName = alarmLevelName;
	}

	public Integer getMoDeviceId() {
		return moDeviceId;
	}

	public void setMoDeviceId(Integer moDeviceId) {
		this.moDeviceId = moDeviceId;
	}

	public String getFormatTime() {
		return formatTime;
	}

	public void setFormatTime(String formatTime) {
		this.formatTime = formatTime;
	}

	public Integer getDbmsMoId() {
		return dbmsMoId;
	}

	public void setDbmsMoId(Integer dbmsMoId) {
		this.dbmsMoId = dbmsMoId;
	}

	public Integer getDbMoId() {
		return dbMoId;
	}

	public void setDbMoId(Integer dbMoId) {
		this.dbMoId = dbMoId;
	}

	public int getPerfValueAvai() {
		return perfValueAvai;
	}

	public void setPerfValueAvai(int perfValueAvai) {
		this.perfValueAvai = perfValueAvai;
	}

	public int getNodeNum() {
		return nodeNum;
	}

	public void setNodeNum(int nodeNum) {
		this.nodeNum = nodeNum;
	}

	public double getSpilledUsage() {
		return spilledUsage;
	}

	public void setSpilledUsage(double spilledUsage) {
		this.spilledUsage = spilledUsage;
	}

	public double getLogUsage() {
		return logUsage;
	}

	public void setLogUsage(double logUsage) {
		this.logUsage = logUsage;
	}

	public double getShareMemUsage() {
		return shareMemUsage;
	}

	public void setShareMemUsage(double shareMemUsage) {
		this.shareMemUsage = shareMemUsage;
	}

	public double getWaitingLockUsage() {
		return waitingLockUsage;
	}

	public void setWaitingLockUsage(double waitingLockUsage) {
		this.waitingLockUsage = waitingLockUsage;
	}

	public double getPkgCacheHits() {
		return pkgCacheHits;
	}

	public void setPkgCacheHits(double pkgCacheHits) {
		this.pkgCacheHits = pkgCacheHits;
	}

	public double getCatCacheHits() {
		return catCacheHits;
	}

	public void setCatCacheHits(double catCacheHits) {
		this.catCacheHits = catCacheHits;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public Integer getTemplateID() {
		return templateID;
	}

	public void setTemplateID(Integer templateID) {
		this.templateID = templateID;
	}

	public Date getUpdateAlarmTime() {
		return updateAlarmTime;
	}

	public void setUpdateAlarmTime(Date updateAlarmTime) {
		this.updateAlarmTime = updateAlarmTime;
	}

	public String getDurationTime() {
		return durationTime;
	}

	public void setDurationTime(String durationTime) {
		this.durationTime = durationTime;
	}

	public Integer getDoIntervals() {
		return doIntervals;
	}

	public void setDoIntervals(Integer doIntervals) {
		this.doIntervals = doIntervals;
	}

	public Integer getDefDoIntervals() {
		return defDoIntervals;
	}

	public void setDefDoIntervals(Integer defDoIntervals) {
		this.defDoIntervals = defDoIntervals;
	}

	public Date getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}

}