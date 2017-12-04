package com.fable.insightview.platform.attendance.entity;

import java.util.Date;
import java.util.List;

/**
 * 签到记录VO
 * @author xue.antai
 *
 */
public class HisAttRecordInfoVo {
	// 签到日期
	private Date attDate;
	// 签到人ID
	private Integer userId;
	// 签到人姓名
	private String userName;
	// 签到时间段
	private List<String[]> signTimeSlot;
	// 签到总数统计
	private String statistics;
	public Date getAttDate() {
		return attDate;
	}
	public void setAttDate(Date attDate) {
		this.attDate = attDate;
	}
	public List<String[]> getSignTimeSlot() {
		return signTimeSlot;
	}
	public void setSignTimeSlot(List<String[]> signTimeSlot) {
		this.signTimeSlot = signTimeSlot;
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
	public String getStatistics() {
		return statistics;
	}
	public void setStatistics(String statistics) {
		this.statistics = statistics;
	}
	
}
