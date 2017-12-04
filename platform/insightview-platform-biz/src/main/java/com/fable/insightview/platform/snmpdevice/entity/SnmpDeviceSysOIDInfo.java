package com.fable.insightview.platform.snmpdevice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

/**
 * @ClassName:     SnmpDeviceSysOIDMapper.java
 * @Description:   设备OID维护
 * @author         郑小辉
 * @Date           2014-6-16 下午01:46:35 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "SNMPDeviceSysObjectID")
public class SnmpDeviceSysOIDInfo extends com.fable.insightview.platform.itsm.core.entity.Entity{

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "snmpDeviceSysOID_gen")
	@TableGenerator(initialValue=10001, name = "snmpDeviceSysOID_gen", table = "SysKeyGenerator", pkColumnName = "GenName", valueColumnName = "GenValue", pkColumnValue = "PlatformSnmpDevicePK", allocationSize = 1)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "PEN")
	private Integer pen;
	
	@Column(name = "DeviceOID")
	private String deviceOID;
	
	@Column(name = "DeviceModelName")
	private String deviceModelName;
	
	@Column(name = "DeviceType")
	private String deviceType;
	
	
	@Column(name = "DeviceNameAbbr")
	private String deviceNameAbbr;
	
	@Column(name = "OS")
	private String os;
	
	@Column(name = "DeviceIcon")
	private String deviceIcon;	
	
	@Column(name = "ResCategoryID")
	private Integer resCategoryID;
	
	@Transient
	private String resCategoryName;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPen() {
		return pen;
	}
	public void setPen(Integer pen) {
		this.pen = pen;
	}
	public String getDeviceOID() {
		return deviceOID;
	}
	public void setDeviceOID(String deviceOID) {
		this.deviceOID = deviceOID;
	}
	public String getDeviceModelName() {
		return deviceModelName;
	}
	public void setDeviceModelName(String deviceModelName) {
		this.deviceModelName = deviceModelName;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getDeviceNameAbbr() {
		return deviceNameAbbr;
	}
	public void setDeviceNameAbbr(String deviceNameAbbr) {
		this.deviceNameAbbr = deviceNameAbbr;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getDeviceIcon() {
		return deviceIcon;
	}
	public void setDeviceIcon(String deviceIcon) {
		this.deviceIcon = deviceIcon;
	}
	public Integer getResCategoryID() {
		return resCategoryID;
	}
	public void setResCategoryID(Integer resCategoryID) {
		this.resCategoryID = resCategoryID;
	}
	public String getResCategoryName() {
		return resCategoryName;
	}
	public void setResCategoryName(String resCategoryName) {
		this.resCategoryName = resCategoryName;
	}
	
}
