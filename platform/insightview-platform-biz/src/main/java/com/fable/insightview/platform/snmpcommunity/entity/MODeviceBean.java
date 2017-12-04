package com.fable.insightview.platform.snmpcommunity.entity;

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
 * 设备
 * 
 * @author 王淑平
 */
@Entity
@Table(name = "MODevice")
public class MODeviceBean extends
		com.fable.insightview.platform.itsm.core.entity.Entity implements
		Serializable {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("all")
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "modevice_gen")
	@TableGenerator(initialValue=10001, name = "modevice_gen", table = "SysKeyGenerator", pkColumnName = "GenName", valueColumnName = "GenValue", pkColumnValue = "MODevicePK", allocationSize = 1)
	@Column(name = "MOID")
	private Integer moID;

	@Column(name = "MOName")
	private String moName;

	@Column(name = "MOAlias")
	private String mOAlias;

	@Column(name = "OperStatus")
	private int operStatus;

	@Column(name = "AdminStatus")
	private int adminStatus;

	@Column(name = "AlarmLevel")
	private int alarmLevel;

	@Column(name = "DomainID")
	private int domainID;

	@Column(name = "CreateTime")
	private String dreateTime;

	@Column(name = "LastUpdateTime")
	private String lastUpdateTime;

	@Column(name = "CreateBy")
	private int createBy;

	@Column(name = "UpdateBy")
	private int updateBy;

	@Column(name = "DeviceIP")
	private String deviceIP;

	@Column(name = "NeCollectorID")
	private int neCollectorID;

	@Column(name = "NeManufacturerID")
	private int neManufacturerID;

	@Column(name = "NeCategoryID")
	private int neCategoryID;

	@Column(name = "NeVersion")
	private String neVersion;

	@Column(name = "OS")
	private String oS;

	@Column(name = "OSVersion")
	private String oSVersion;

	@Column(name = "SnmpVersion")
	private int snmpVersion;

	@Column(name = "IsManage")
	private int isManage;

	@Column(name = "TaskId")
	private int taskId;

	public int getMoID() {
		return moID;
	}

	public void setMoID(int moID) {
		this.moID = moID;
	}


	public String getMoName() {
		return moName;
	}

	public void setMoName(String moName) {
		this.moName = moName;
	}

	public String getmOAlias() {
		return mOAlias;
	}

	public void setmOAlias(String mOAlias) {
		this.mOAlias = mOAlias;
	}

	public int getOperStatus() {
		return operStatus;
	}

	public void setOperStatus(int operStatus) {
		this.operStatus = operStatus;
	}

	public int getAdminStatus() {
		return adminStatus;
	}

	public void setAdminStatus(int adminStatus) {
		this.adminStatus = adminStatus;
	}

	public int getAlarmLevel() {
		return alarmLevel;
	}

	public void setAlarmLevel(int alarmLevel) {
		this.alarmLevel = alarmLevel;
	}

	public int getDomainID() {
		return domainID;
	}

	public void setDomainID(int domainID) {
		this.domainID = domainID;
	}

	public String getDreateTime() {
		return dreateTime;
	}

	public void setDreateTime(String dreateTime) {
		this.dreateTime = dreateTime;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public int getCreateBy() {
		return createBy;
	}

	public void setCreateBy(int createBy) {
		this.createBy = createBy;
	}

	public int getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(int updateBy) {
		this.updateBy = updateBy;
	}

	public String getDeviceIP() {
		return deviceIP;
	}

	public void setDeviceIP(String deviceIP) {
		this.deviceIP = deviceIP;
	}

	public int getNeCollectorID() {
		return neCollectorID;
	}

	public void setNeCollectorID(int neCollectorID) {
		this.neCollectorID = neCollectorID;
	}

	public int getNeManufacturerID() {
		return neManufacturerID;
	}

	public void setNeManufacturerID(int neManufacturerID) {
		this.neManufacturerID = neManufacturerID;
	}

	public int getNeCategoryID() {
		return neCategoryID;
	}

	public void setNeCategoryID(int neCategoryID) {
		this.neCategoryID = neCategoryID;
	}

	public String getNeVersion() {
		return neVersion;
	}

	public void setNeVersion(String neVersion) {
		this.neVersion = neVersion;
	}

	public String getoS() {
		return oS;
	}

	public void setoS(String oS) {
		this.oS = oS;
	}

	public String getoSVersion() {
		return oSVersion;
	}

	public void setoSVersion(String oSVersion) {
		this.oSVersion = oSVersion;
	}

	public int getSnmpVersion() {
		return snmpVersion;
	}

	public void setSnmpVersion(int snmpVersion) {
		this.snmpVersion = snmpVersion;
	}

	public int getIsManage() {
		return isManage;
	}

	public void setIsManage(int isManage) {
		this.isManage = isManage;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public void setMoID(Integer moID) {
		this.moID = moID;
	}
	
	
}