package com.fable.insightview.monitor.alarmmgr.entity;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * 告警重复策略
 * 
 * @author hanl
 * 
 */

@Alias("alarmRepeatPolicy")
public class AlarmRepeatPolicyBean {
	/* 策略ID */
	@NumberGenerator(name="monitorRepeatPolicyPK")
	private Integer ploicyID;

	/* 事件名 */
	private Integer alarmDefineID;

	/* 时间窗口 */
	private Integer timeWindow;

	/* 重复告警条件 */
	private Integer alarmOnCount;

	/* 告警升级条件 */
	private Integer upgradeOnCount;

	/* 是否启用 */
	private Integer isUsed;

	/* 操作标志 */
	private Integer mFlag;
	
	private String alarmName;

	public Integer getPloicyID() {
		return ploicyID;
	}

	public void setPloicyID(Integer ploicyID) {
		this.ploicyID = ploicyID;
	}

	public Integer getAlarmDefineID() {
		return alarmDefineID;
	}

	public void setAlarmDefineID(Integer alarmDefineID) {
		this.alarmDefineID = alarmDefineID;
	}

	public Integer getTimeWindow() {
		return timeWindow;
	}

	public void setTimeWindow(Integer timeWindow) {
		this.timeWindow = timeWindow;
	}

	public Integer getAlarmOnCount() {
		return alarmOnCount;
	}

	public void setAlarmOnCount(Integer alarmOnCount) {
		this.alarmOnCount = alarmOnCount;
	}

	public Integer getUpgradeOnCount() {
		return upgradeOnCount;
	}

	public void setUpgradeOnCount(Integer upgradeOnCount) {
		this.upgradeOnCount = upgradeOnCount;
	}

	public Integer getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Integer isUsed) {
		this.isUsed = isUsed;
	}

	public Integer getmFlag() {
		return mFlag;
	}

	public void setmFlag(Integer mFlag) {
		this.mFlag = mFlag;
	}

	public String getAlarmName() {
		return alarmName;
	}

	public void setAlarmName(String alarmName) {
		this.alarmName = alarmName;
	}



}
