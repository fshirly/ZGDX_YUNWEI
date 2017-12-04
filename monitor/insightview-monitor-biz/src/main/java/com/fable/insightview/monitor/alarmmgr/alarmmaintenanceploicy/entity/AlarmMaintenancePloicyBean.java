package com.fable.insightview.monitor.alarmmgr.alarmmaintenanceploicy.entity;

import java.sql.Timestamp;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;
/**
 * 告警维护期事件策略
 * @Description:AlarmMaintenancePolicyBean
 * @author zhurt
 * @String 2014-7-16
 */
public class AlarmMaintenancePloicyBean {
	@NumberGenerator(name = "PloicyID")
	private int ploicyID;	//策略ID
	private String maintainTitle; 	//维护期标题
	private int maintainType; 		//维护期类型 ( 1-新建 2-割接 3-故障 默认为1)
	private String maintainDesc;	//维护期描述
	private int sourceMOID;		//事件源对象ID
	private Timestamp startTime;		//维护开始时间
	private Timestamp endTime;		//维护结束时间
	private int isUsed;		//是否启用
	private int mFlag;		//操作标志
	private String createUser;	//新增人
	private Timestamp createTime;	//新增时间
	private String modifyUser;	//最近修改人
	private Timestamp modifyTime;	//最近修改时间
	
	//查询，显示参数
	private String alarmName;
	private String sourceMOName;
	private String moname;
	private String deviceip;
	private Integer moid;
	
	
	public Integer getMoid() {
		return moid;
	}

	public void setMoid(Integer moid) {
		this.moid = moid;
	}

	public AlarmMaintenancePloicyBean() {
	}

	public int getPloicyID() {
		return ploicyID;
	}

	public void setPloicyID(int ploicyID) {
		this.ploicyID = ploicyID;
	}

	public String getMaintainTitle() {
		return maintainTitle;
	}

	public void setMaintainTitle(String maintainTitle) {
		this.maintainTitle = maintainTitle;
	}

	public int getMaintainType() {
		return maintainType;
	}

	public void setMaintainType(int maintainType) {
		this.maintainType = maintainType;
	}

	public String getMaintainDesc() {
		return maintainDesc;
	}

	public void setMaintainDesc(String maintainDesc) {
		this.maintainDesc = maintainDesc;
	}

	public int getSourceMOID() {
		return sourceMOID;
	}

	public void setSourceMOID(int sourceMOID) {
		this.sourceMOID = sourceMOID;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public int getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(int isUsed) {
		this.isUsed = isUsed;
	}

	public int getmFlag() {
		return mFlag;
	}

	public void setmFlag(int mFlag) {
		this.mFlag = mFlag;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getAlarmName() {
		return alarmName;
	}

	public void setAlarmName(String alarmName) {
		this.alarmName = alarmName;
	}

	public String getSourceMOName() {
		return sourceMOName;
	}

	public void setSourceMOName(String sourceMOName) {
		this.sourceMOName = sourceMOName;
	}

	public String getMoname() {
		return moname;
	}

	public void setMoname(String moname) {
		this.moname = moname;
	}

	public String getDeviceip() {
		return deviceip;
	}

	public void setDeviceip(String deviceip) {
		this.deviceip = deviceip;
	}
}
