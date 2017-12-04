package com.fable.insightview.monitor.middleware.tomcat.entity;

import java.util.Date;

public class MOMiddleWareJMXBean {
	private Integer moId;
	private String jmxType;
	private String portType;
	private String moalias;
	private String ip;
	private Integer port;
	private String userName;
	private String passWord;
	private String serverName;
	private String jmxVersion;
	private String jmxStatus;
	private Date startupTime;
	private String oSName;
	private String oSVersion;
	private Integer operStatus;
	private Integer adminStatus;
	private Integer deviceMOID;
	private Integer alarmLevel;
	private Date createTime;
	private Date lastUpdateTime;
	private String jvmName;// 虚拟机名称
	private String jvmVendor;// 虚拟机厂商
	private String jvmVersion;// 虚拟机版本
	private String javaVendor;// JAVA厂商
	private String javaVersion;// JAVA版本
	private String levelicon;// 告警图标
	private String operStatusName;
	private String alarmLevelName;
	private String levelColor;
	private String deviceIP;
	private Integer moClassId;
	private Date updateAlarmTime;
	private String durationTime;//持续时间
	private Integer doIntervals; 
	private Integer defDoIntervals;
	private Date collectTime;

	private double deviceAvailability;// 可用率

	private String moName;
	private String jtaName;
	private String jtaStatus;
	private String jmsName;
	private String jmsStatus;

	private double memoryMax;
	private double memorySize;
	private double memoryFree;
	private double memoryUsage;
	private String domainName;
	private Integer domainId;
	
	private String upTime;//已运行时间
	private String heapSizeMax;//堆内存最大值
	
	private String url;
	private Integer id;//中间件凭证id

	private int perfValue;

	private String operaTip;
	private Integer templateID;
	
	public String getDeviceIP() {
		return deviceIP;
	}

	public void setDeviceIP(String deviceIP) {
		this.deviceIP = deviceIP;
	}

	public double getMemoryMax() {
		return memoryMax;
	}

	public void setMemoryMax(double memoryMax) {
		this.memoryMax = memoryMax;
	}

	public double getMemorySize() {
		return memorySize;
	}

	public void setMemorySize(double memorySize) {
		this.memorySize = memorySize;
	}

	public double getMemoryFree() {
		return memoryFree;
	}

	public void setMemoryFree(double memoryFree) {
		this.memoryFree = memoryFree;
	}

	public double getMemoryUsage() {
		return memoryUsage;
	}

	public void setMemoryUsage(double memoryUsage) {
		this.memoryUsage = memoryUsage;
	}

	public double getDeviceAvailability() {
		return deviceAvailability;
	}

	public void setDeviceAvailability(double deviceAvailability) {
		this.deviceAvailability = deviceAvailability;
	}

	public String getJvmVendor() {
		return jvmVendor;
	}

	public void setJvmVendor(String jvmVendor) {
		this.jvmVendor = jvmVendor;
	}

	public String getJvmVersion() {
		return jvmVersion;
	}

	public void setJvmVersion(String jvmVersion) {
		this.jvmVersion = jvmVersion;
	}

	public String getJavaVendor() {
		return javaVendor;
	}

	public void setJavaVendor(String javaVendor) {
		this.javaVendor = javaVendor;
	}

	public String getJavaVersion() {
		return javaVersion;
	}

	public void setJavaVersion(String javaVersion) {
		this.javaVersion = javaVersion;
	}

	public Integer getMoId() {
		return moId;
	}

	public void setMoId(Integer moId) {
		this.moId = moId;
	}

	public String getJmxType() {
		return jmxType;
	}

	public void setJmxType(String jmxType) {
		this.jmxType = jmxType;
	}

	public String getPortType() {
		return portType;
	}

	public void setPortType(String portType) {
		this.portType = portType;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getJmxVersion() {
		return jmxVersion;
	}

	public void setJmxVersion(String jmxVersion) {
		this.jmxVersion = jmxVersion;
	}

	public String getJmxStatus() {
		return jmxStatus;
	}

	public void setJmxStatus(String jmxStatus) {
		this.jmxStatus = jmxStatus;
	}

	public Date getStartupTime() {
		return startupTime;
	}

	public void setStartupTime(Date startupTime) {
		this.startupTime = startupTime;
	}

	public String getoSName() {
		return oSName;
	}

	public void setoSName(String oSName) {
		this.oSName = oSName;
	}

	public String getoSVersion() {
		return oSVersion;
	}

	public void setoSVersion(String oSVersion) {
		this.oSVersion = oSVersion;
	}

	public Integer getOperStatus() {
		return operStatus;
	}

	public void setOperStatus(Integer operStatus) {
		this.operStatus = operStatus;
	}

	public Integer getAdminStatus() {
		return adminStatus;
	}

	public void setAdminStatus(Integer adminStatus) {
		this.adminStatus = adminStatus;
	}

	public Integer getDeviceMOID() {
		return deviceMOID;
	}

	public void setDeviceMOID(Integer deviceMOID) {
		this.deviceMOID = deviceMOID;
	}

	public Integer getAlarmLevel() {
		return alarmLevel;
	}

	public void setAlarmLevel(Integer alarmLevel) {
		this.alarmLevel = alarmLevel;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getJvmName() {
		return jvmName;
	}

	public void setJvmName(String jvmName) {
		this.jvmName = jvmName;
	}

	public String getLevelicon() {
		return levelicon;
	}

	public void setLevelicon(String levelicon) {
		this.levelicon = levelicon;
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

	public String getLevelColor() {
		return levelColor;
	}

	public void setLevelColor(String levelColor) {
		this.levelColor = levelColor;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public Integer getDomainId() {
		return domainId;
	}

	public void setDomainId(Integer domainId) {
		this.domainId = domainId;
	}

	public String getMoName() {
		return moName;
	}

	public void setMoName(String moName) {
		this.moName = moName;
	}

	public String getJtaName() {
		return jtaName;
	}

	public void setJtaName(String jtaName) {
		this.jtaName = jtaName;
	}

	public String getJtaStatus() {
		return jtaStatus;
	}

	public void setJtaStatus(String jtaStatus) {
		this.jtaStatus = jtaStatus;
	}

	public String getJmsName() {
		return jmsName;
	}

	public void setJmsName(String jmsName) {
		this.jmsName = jmsName;
	}

	public String getJmsStatus() {
		return jmsStatus;
	}

	public void setJmsStatus(String jmsStatus) {
		this.jmsStatus = jmsStatus;
	}

	public Integer getMoClassId() {
		return moClassId;
	}

	public void setMoClassId(Integer moClassId) {
		this.moClassId = moClassId;
	}

	public int getPerfValue() {
		return perfValue;
	}

	public void setPerfValue(int perfValue) {
		this.perfValue = perfValue;
	}

	public String getOperaTip() {
		return operaTip;
	}

	public void setOperaTip(String operaTip) {
		this.operaTip = operaTip;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUpTime() {
		return upTime;
	}

	public void setUpTime(String upTime) {
		this.upTime = upTime;
	}

	public String getHeapSizeMax() {
		return heapSizeMax;
	}

	public void setHeapSizeMax(String heapSizeMax) {
		this.heapSizeMax = heapSizeMax;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getMoalias() {
		return moalias;
	}

	public void setMoalias(String moalias) {
		this.moalias = moalias;
	}

}
