package com.fable.insightview.monitor.monetworkIif.entity;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * 管理对象网络接口
 * 
 */

@Alias("moNetworkIf")
public class MONetworkIfBean {
	private Integer moID;
	private String moName;
	private String moAlias;
	private Integer deviceMOID;
	private Integer operStatus;
	private Integer adminStatus;
	private Integer alarmLevel;
	private Timestamp createTime;
	private Timestamp lastUpdateTime;
	private Integer createBy;
	private Integer updateBy;
	private String instance;
	private String ifName;
	private String ifAlias;
	private String ifDescr;
	private String ifType;
	private Integer ifMtu;
	private String ifSpeed;
	private String ifSpeedDescr;
	private Integer ifAdminStatus;
	private Integer ifOperStatus;
	private String ifLastChange;
	private String mIBModule;
	private Integer fullDuplex;

	private String deviceMOName;
	private String deviceIP;
	private String adminStatusName;
	private String operStatusName;
	private Integer resid;
	private Integer parentMoClassId;
	private String ifTypeName;
	private int isCollect;
	// TODO 
	private int DoIntervals;
	private int defDoIntervals;
	private Date collectTime;
	private String operaTip;
	private String operstatusdetail;
	//TODO
	private int sourcePort;
	public String getIfSpeedDescr() {
		return ifSpeedDescr;
	}

	public void setIfSpeedDescr(String ifSpeedDescr) {
		this.ifSpeedDescr = ifSpeedDescr;
	}
	
	public Integer getResid() {
		return resid;
	}

	public void setResid(Integer resid) {
		this.resid = resid;
	}

	public Integer getMoID() {
		return moID;
	}

	public void setMoID(Integer moID) {
		this.moID = moID;
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

	public Integer getDeviceMOID() {
		return deviceMOID;
	}

	public void setDeviceMOID(Integer deviceMOID) {
		this.deviceMOID = deviceMOID;
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

	public Integer getAlarmLevel() {
		return alarmLevel;
	}

	public void setAlarmLevel(Integer alarmLevel) {
		this.alarmLevel = alarmLevel;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
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

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	public String getIfName() {
		return ifName;
	}

	public void setIfName(String ifName) {
		this.ifName = ifName;
	}

	public String getIfAlias() {
		return ifAlias;
	}

	public void setIfAlias(String ifAlias) {
		this.ifAlias = ifAlias;
	}

	public String getIfDescr() {
		return ifDescr;
	}

	public void setIfDescr(String ifDescr) {
		this.ifDescr = ifDescr;
	}

	public String getIfType() {
		return ifType;
	}

	public void setIfType(String ifType) {
		this.ifType = ifType;
	}

	public Integer getIfMtu() {
		return ifMtu;
	}

	public void setIfMtu(Integer ifMtu) {
		this.ifMtu = ifMtu;
	}

	public Integer getIfAdminStatus() {
		return ifAdminStatus;
	}

	public void setIfAdminStatus(Integer ifAdminStatus) {
		this.ifAdminStatus = ifAdminStatus;
	}

	public Integer getIfOperStatus() {
		return ifOperStatus;
	}

	public void setIfOperStatus(Integer ifOperStatus) {
		this.ifOperStatus = ifOperStatus;
	}

	public String getIfLastChange() {
		return ifLastChange;
	}

	public void setIfLastChange(String ifLastChange) {
		this.ifLastChange = ifLastChange;
	}

	public String getmIBModule() {
		return mIBModule;
	}

	public void setmIBModule(String mIBModule) {
		this.mIBModule = mIBModule;
	}

	public Integer getFullDuplex() {
		return fullDuplex;
	}

	public void setFullDuplex(Integer fullDuplex) {
		this.fullDuplex = fullDuplex;
	}

	public String getDeviceMOName() {
		return deviceMOName;
	}

	public void setDeviceMOName(String deviceMOName) {
		this.deviceMOName = deviceMOName;
	}

	public String getDeviceIP() {
		return deviceIP;
	}

	public void setDeviceIP(String deviceIP) {
		this.deviceIP = deviceIP;
	}

	public String getAdminStatusName() {
		return adminStatusName;
	}

	public void setAdminStatusName(String adminStatusName) {
		this.adminStatusName = adminStatusName;
	}

	public String getOperStatusName() {
		return operStatusName;
	}

	public void setOperStatusName(String operStatusName) {
		this.operStatusName = operStatusName;
	}

	public Integer getParentMoClassId() {
		return parentMoClassId;
	}

	public void setParentMoClassId(Integer parentMoClassId) {
		this.parentMoClassId = parentMoClassId;
	}

	public String getIfSpeed() {
		return ifSpeed;
	}

	public void setIfSpeed(String ifSpeed) {
		this.ifSpeed = ifSpeed;
	}

	public String getIfTypeName() {
		return ifTypeName;
	}

	public void setIfTypeName(String ifTypeName) {
		this.ifTypeName = ifTypeName;
	}

	public int getIsCollect() {
		return isCollect;
	}

	public void setIsCollect(int isCollect) {
		this.isCollect = isCollect;
	}

	public int getSourcePort() {
		return sourcePort;
	}

	public void setSourcePort(int sourcePort) {
		this.sourcePort = sourcePort;
	}

	public int getDoIntervals() {
		return DoIntervals;
	}

	public void setDoIntervals(int doIntervals) {
		DoIntervals = doIntervals;
	}

	public int getDefDoIntervals() {
		return defDoIntervals;
	}

	public void setDefDoIntervals(int defDoIntervals) {
		this.defDoIntervals = defDoIntervals;
	}

	public Date getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}

	public String getOperaTip() {
		return operaTip;
	}

	public void setOperaTip(String operaTip) {
		this.operaTip = operaTip;
	}

	public String getOperstatusdetail() {
		return operstatusdetail;
	}

	public void setOperstatusdetail(String operstatusdetail) {
		this.operstatusdetail = operstatusdetail;
	}

}
