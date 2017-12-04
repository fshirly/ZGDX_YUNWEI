package com.fable.insightview.monitor.perf.entity;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.monitor.middleware.middlewarecommunity.entity.SysMiddleWareCommunityBean;
import com.fable.insightview.monitor.sysdbmscommunity.entity.SysDBMSCommunityBean;
import com.fable.insightview.monitor.sysroomcommunity.entity.SysRoomCommunityBean;
import com.fable.insightview.monitor.website.entity.SysSiteCommunityBean;
import com.fable.insightview.monitor.website.entity.WebSite;
import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;
import com.fable.insightview.platform.snmpcommunity.entity.SysVMIfCommunityBean;


@Alias("taskInfo")
public class PerfTaskInfoBean {
	@NumberGenerator(name = "MonitorCollectTaskPK")
	private int taskId;
	private int moId;
	private int snmpId;
	private String deviceIp;
	private int domainId;
	private String domainName;
	private String deviceType;
	private String deviceManufacture;
	private int snmpVersion;
	private String moName;
	private int snmpPort;
	private String readCommunity;
	private String encryptionKey;
	private String usmUser;
	private String securityLevel;
	private String authAlogrithm;
	private String authKey;
	private String encryptionAlogrithm;
	private String contexName;
	private String moList;
	private String moIntervalList;
	private String moTimeUnitList;
	private Integer status;
	private long creator;
	private String createTime;
	private String lastStatusTime;// 最近状态时间
	private Integer progressStatus;// 操作进度
	private Integer operateStatus;// 最近一次操作状态
	private Integer lastOPResult;// 最近操作结果
	private String lastOPTime;// 最近操作时间
	private String errorInfo;
	private Integer moClassId;
	private Integer collectorId;
	private Integer oldCollectorId;
	private String isOffline;

	private SysVMIfCommunityBean vmIfCommunityBean;
	private SysDBMSCommunityBean dbmsCommunityBean;
	private SysMiddleWareCommunityBean middleWareCommunityBean;
	private SysRoomCommunityBean roomCommunityBean;
	private SysSiteCommunityBean siteCommunityBean;
	private WebSite webSite;
	private String authType;
	private String moClassName;
	private String className;
	private String userName;
	private String password;
	
	private String writeCommunity;
	
	private Integer vmId;
	private Integer port;
	
	private String dbName;
	private String collectorName;
	
	private Integer templateID;
	private String ipAddr;
	private Integer isAuth;
	private Integer manufacturerID;

	public PerfTaskInfoBean() {
	}

	public PerfTaskInfoBean(int taskId, int moId, int snmpId, String deviceIp,
			int domainId, String domainName, String deviceType,
			String deviceManufacture, int snmpVersion, String moName,
			int snmpPort, String readCommunity, String encryptionKey,
			String usmUser, String securityLevel, String authAlogrithm,
			String authKey, String encryptionAlogrithm, String contexName,
			String moList, String moIntervalList, Integer status, long creator,
			String createTime, String lastStatusTime, Integer progressStatus,
			Integer operateStatus, Integer lastOPResult, String lastOPTime,
			String errorInfo, Integer moClassId) {
		super();
		this.taskId = taskId;
		this.moId = moId;
		this.snmpId = snmpId;
		this.deviceIp = deviceIp;
		this.domainId = domainId;
		this.domainName = domainName;
		this.deviceType = deviceType;
		this.deviceManufacture = deviceManufacture;
		this.snmpVersion = snmpVersion;
		this.moName = moName;
		this.snmpPort = snmpPort;
		this.readCommunity = readCommunity;
		this.encryptionKey = encryptionKey;
		this.usmUser = usmUser;
		this.securityLevel = securityLevel;
		this.authAlogrithm = authAlogrithm;
		this.authKey = authKey;
		this.encryptionAlogrithm = encryptionAlogrithm;
		this.contexName = contexName;
		this.moList = moList;
		this.moIntervalList = moIntervalList;
		this.status = status;
		this.creator = creator;
		this.createTime = createTime;
		this.lastStatusTime = lastStatusTime;
		this.progressStatus = progressStatus;
		this.operateStatus = operateStatus;
		this.lastOPResult = lastOPResult;
		this.lastOPTime = lastOPTime;
		this.errorInfo = errorInfo;
		this.moClassId = moClassId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Integer getMoClassId() {
		return moClassId;
	}

	public void setMoClassId(Integer moClassId) {
		this.moClassId = moClassId;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public int getMoId() {
		return moId;
	}

	public void setMoId(int moId) {
		this.moId = moId;
	}

	public int getSnmpId() {
		return snmpId;
	}

	public void setSnmpId(int snmpId) {
		this.snmpId = snmpId;
	}

	public String getDeviceIp() {
		return deviceIp;
	}

	public void setDeviceIp(String deviceIp) {
		this.deviceIp = deviceIp;
	}

	public int getDomainId() {
		return domainId;
	}

	public void setDomainId(int domainId) {
		this.domainId = domainId;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceManufacture() {
		return deviceManufacture;
	}

	public void setDeviceManufacture(String deviceManufacture) {
		this.deviceManufacture = deviceManufacture;
	}

	public int getSnmpVersion() {
		return snmpVersion;
	}

	public void setSnmpVersion(int snmpVersion) {
		this.snmpVersion = snmpVersion;
	}

	public String getMoName() {
		return moName;
	}

	public void setMoName(String moName) {
		this.moName = moName;
	}

	public int getSnmpPort() {
		return snmpPort;
	}

	public void setSnmpPort(int snmpPort) {
		this.snmpPort = snmpPort;
	}

	public String getReadCommunity() {
		return readCommunity;
	}

	public void setReadCommunity(String readCommunity) {
		this.readCommunity = readCommunity;
	}

	public String getEncryptionKey() {
		return encryptionKey;
	}

	public void setEncryptionKey(String encryptionKey) {
		this.encryptionKey = encryptionKey;
	}

	public String getUsmUser() {
		return usmUser;
	}

	public void setUsmUser(String usmUser) {
		this.usmUser = usmUser;
	}

	public String getSecurityLevel() {
		return securityLevel;
	}

	public void setSecurityLevel(String securityLevel) {
		this.securityLevel = securityLevel;
	}

	public String getAuthAlogrithm() {
		return authAlogrithm;
	}

	public void setAuthAlogrithm(String authAlogrithm) {
		this.authAlogrithm = authAlogrithm;
	}

	public String getAuthKey() {
		return authKey;
	}

	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}

	public String getEncryptionAlogrithm() {
		return encryptionAlogrithm;
	}

	public void setEncryptionAlogrithm(String encryptionAlogrithm) {
		this.encryptionAlogrithm = encryptionAlogrithm;
	}

	public String getContexName() {
		return contexName;
	}

	public void setContexName(String contexName) {
		this.contexName = contexName;
	}

	public String getMoList() {
		return moList;
	}

	public void setMoList(String moList) {
		this.moList = moList;
	}

	public String getMoIntervalList() {
		return moIntervalList;
	}

	public void setMoIntervalList(String moIntervalList) {
		this.moIntervalList = moIntervalList;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public long getCreator() {
		return creator;
	}

	public void setCreator(long creator) {
		this.creator = creator;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getLastStatusTime() {
		return lastStatusTime;
	}

	public void setLastStatusTime(String lastStatusTime) {
		this.lastStatusTime = lastStatusTime;
	}

	public Integer getProgressStatus() {
		return progressStatus;
	}

	public void setProgressStatus(Integer progressStatus) {
		this.progressStatus = progressStatus;
	}

	public Integer getOperateStatus() {
		return operateStatus;
	}

	public void setOperateStatus(Integer operateStatus) {
		this.operateStatus = operateStatus;
	}

	public Integer getLastOPResult() {
		return lastOPResult;
	}

	public void setLastOPResult(int lastOPResult) {
		this.lastOPResult = lastOPResult;
	}

	public String getLastOPTime() {
		return lastOPTime;
	}

	public void setLastOPTime(String lastOPTime) {
		this.lastOPTime = lastOPTime;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	public SysVMIfCommunityBean getVmIfCommunityBean() {
		return vmIfCommunityBean;
	}

	public void setVmIfCommunityBean(SysVMIfCommunityBean vmIfCommunityBean) {
		this.vmIfCommunityBean = vmIfCommunityBean;
	}

	public SysDBMSCommunityBean getDbmsCommunityBean() {
		return dbmsCommunityBean;
	}

	public void setDbmsCommunityBean(SysDBMSCommunityBean dbmsCommunityBean) {
		this.dbmsCommunityBean = dbmsCommunityBean;
	}

	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public String getMoClassName() {
		return moClassName;
	}

	public void setMoClassName(String moClassName) {
		this.moClassName = moClassName;
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

	public void setLastOPResult(Integer lastOPResult) {
		this.lastOPResult = lastOPResult;
	}

	public SysMiddleWareCommunityBean getMiddleWareCommunityBean() {
		return middleWareCommunityBean;
	}

	public void setMiddleWareCommunityBean(
			SysMiddleWareCommunityBean middleWareCommunityBean) {
		this.middleWareCommunityBean = middleWareCommunityBean;
	}

	public SysRoomCommunityBean getRoomCommunityBean() {
		return roomCommunityBean;
	}

	public void setRoomCommunityBean(SysRoomCommunityBean roomCommunityBean) {
		this.roomCommunityBean = roomCommunityBean;
	}

	public String getWriteCommunity() {
		return writeCommunity;
	}

	public void setWriteCommunity(String writeCommunity) {
		this.writeCommunity = writeCommunity;
	}

	public Integer getCollectorId() {
		return collectorId;
	}

	public void setCollectorId(Integer collectorId) {
		this.collectorId = collectorId;
	}

	public Integer getVmId() {
		return vmId;
	}

	public void setVmId(Integer vmId) {
		this.vmId = vmId;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public Integer getOldCollectorId() {
		return oldCollectorId;
	}

	public void setOldCollectorId(Integer oldCollectorId) {
		this.oldCollectorId = oldCollectorId;
	}

	public String getCollectorName() {
		return collectorName;
	}

	public void setCollectorName(String collectorName) {
		this.collectorName = collectorName;
	}

	public Integer getTemplateID() {
		return templateID;
	}

	public void setTemplateID(Integer templateID) {
		this.templateID = templateID;
	}

	public String getMoTimeUnitList() {
		return moTimeUnitList;
	}

	public void setMoTimeUnitList(String moTimeUnitList) {
		this.moTimeUnitList = moTimeUnitList;
	}

	public WebSite getWebSite() {
		return webSite;
	}

	public void setWebSite(WebSite webSite) {
		this.webSite = webSite;
	}

	public SysSiteCommunityBean getSiteCommunityBean() {
		return siteCommunityBean;
	}

	public void setSiteCommunityBean(SysSiteCommunityBean siteCommunityBean) {
		this.siteCommunityBean = siteCommunityBean;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public Integer getIsAuth() {
		return isAuth;
	}

	public void setIsAuth(Integer isAuth) {
		this.isAuth = isAuth;
	}

	public Integer getManufacturerID() {
		return manufacturerID;
	}

	public void setManufacturerID(Integer manufacturerID) {
		this.manufacturerID = manufacturerID;
	}

	public String getIsOffline() {
		return isOffline;
	}

	public void setIsOffline(String isOffline) {
		this.isOffline = isOffline;
	}
}
