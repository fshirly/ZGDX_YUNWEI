package com.fable.insightview.monitor.database.entity;

import java.util.Date;

import org.apache.ibatis.type.Alias;


@Alias("MOMySQLDBServer")
public class MOMySQLDBServerBean {
    private Integer moId;

    private Integer dbmsMoid;

    private String deviceMoid;

    private String ip;

    private Integer port;

    private String serverName;

    private Date startTime;

    private String installPath;

    private String dataPath;

    private String serverCharset;

    private Integer dbNum;
    //MODBMSServer表信息
    private String moName;//数据库名称
    
    private String moAlias;//数据库别名
    
    private String deviceIP;//资源IP
    
    private Date createTime;//创建时间
    
    private Integer createBy;//创建人
    
    private Integer updateBy;//修改人
    
    private Date lastUpdateTime;//最近更新时间
    
    private String levelIcon;//告警图标
    
    private Integer alarmLevel;//告警级别
    
    private String operStatus;//操作状态
    
    private double deviceAvailability;//数据库可用率

    private String alarmLevelName;
    
    private String operaTip;
    
    private int perfValue;
    
    private String dbmsType;
    
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

	public String getAlarmLevelName() {
		return alarmLevelName;
	}

	public void setAlarmLevelName(String alarmLevelName) {
		this.alarmLevelName = alarmLevelName;
	}

	public double getDeviceAvailability() {
		return deviceAvailability;
	}

	public void setDeviceAvailability(double deviceAvailability) {
		this.deviceAvailability = deviceAvailability;
	}

	public String getOperStatus() {
		return operStatus;
	}

	public void setOperStatus(String operStatus) {
		this.operStatus = operStatus;
	}

	public String getMoName() {
		return moName;
	}

	public void setMoName(String moName) {
		this.moName = moName;
	}

	public String getMoAlias() {
		return moAlias;
	}

	public void setMoAlias(String moAlias) {
		this.moAlias = moAlias;
	}

	public String getDeviceIP() {
		return deviceIP;
	}

	public void setDeviceIP(String deviceIP) {
		this.deviceIP = deviceIP;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Integer createBy) {
		this.createBy = createBy;
	}

	public Integer getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Integer updateBy) {
		this.updateBy = updateBy;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getLevelIcon() {
		return levelIcon;
	}

	public void setLevelIcon(String levelIcon) {
		this.levelIcon = levelIcon;
	}

	public Integer getAlarmLevel() {
		return alarmLevel;
	}

	public void setAlarmLevel(Integer alarmLevel) {
		this.alarmLevel = alarmLevel;
	}

	public Integer getMoId() {
		return moId;
	}

	public void setMoId(Integer moId) {
		this.moId = moId;
	}


	public Integer getDbmsMoid() {
		return dbmsMoid;
	}

	public void setDbmsMoid(Integer dbmsMoid) {
		this.dbmsMoid = dbmsMoid;
	}

	public String getDeviceMoid() {
		return deviceMoid;
	}

	public void setDeviceMoid(String deviceMoid) {
		this.deviceMoid = deviceMoid;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getInstallPath() {
		return installPath;
	}

	public void setInstallPath(String installPath) {
		this.installPath = installPath;
	}

	public String getDataPath() {
		return dataPath;
	}

	public void setDataPath(String dataPath) {
		this.dataPath = dataPath;
	}

	public String getServerCharset() {
		return serverCharset;
	}

	public void setServerCharset(String serverCharset) {
		this.serverCharset = serverCharset;
	}

	public Integer getDbNum() {
		return dbNum;
	}

	public void setDbNum(Integer dbNum) {
		this.dbNum = dbNum;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPerfValue() {
		return perfValue;
	}

	public void setPerfValue(int perfValue) {
		this.perfValue = perfValue;
	}

	public String getDbmsType() {
		return dbmsType;
	}

	public void setDbmsType(String dbmsType) {
		this.dbmsType = dbmsType;
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