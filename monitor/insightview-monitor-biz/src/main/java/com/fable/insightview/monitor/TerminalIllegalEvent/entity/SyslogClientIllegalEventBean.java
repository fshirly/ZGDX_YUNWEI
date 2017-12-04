package com.fable.insightview.monitor.TerminalIllegalEvent.entity;

import java.util.Date;

/**
 * 终端违规事件实体类
 * @author zhaods
 *
 */
public class SyslogClientIllegalEventBean {

	private Integer id;
	
	private String deviceIp;
	
	//证书序列号
	private String certSerial; 
	
	//事件列表序列号
	private Integer indexId;
	
	//类别
	private Integer typeId;
	
	//第一次发生时间
	private Date firstTime;
	
	//最后一次发生时间
	private Date lastTime;
	
	//违规事件的详细描述
	private String memo;
	
	//违规次数
	private Integer repeatTimes;
	
	private String firstTimeStr;
	
	private String lastTimeStr;
	
	private Integer userId;
	
	private String userName;
	
	private Integer orgId;
	
	private String orgName;
	
	private String clientNo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDeviceIp() {
		return deviceIp;
	}

	public void setDeviceIp(String deviceIp) {
		this.deviceIp = deviceIp;
	}

	public String getCertSerial() {
		return certSerial;
	}

	public void setCertSerial(String certSerial) {
		this.certSerial = certSerial;
	}

	public Integer getIndexId() {
		return indexId;
	}

	public void setIndexId(Integer indexId) {
		this.indexId = indexId;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Date getFirstTime() {
		return firstTime;
	}

	public void setFirstTime(Date firstTime) {
		this.firstTime = firstTime;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getRepeatTimes() {
		return repeatTimes;
	}

	public void setRepeatTimes(Integer repeatTimes) {
		this.repeatTimes = repeatTimes;
	}

	public String getFirstTimeStr() {
		return firstTimeStr;
	}

	public void setFirstTimeStr(String firstTimeStr) {
		this.firstTimeStr = firstTimeStr;
	}

	public String getLastTimeStr() {
		return lastTimeStr;
	}

	public void setLastTimeStr(String lastTimeStr) {
		this.lastTimeStr = lastTimeStr;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getClientNo() {
		return clientNo;
	}

	public void setClientNo(String clientNo) {
		this.clientNo = clientNo;
	}
}
