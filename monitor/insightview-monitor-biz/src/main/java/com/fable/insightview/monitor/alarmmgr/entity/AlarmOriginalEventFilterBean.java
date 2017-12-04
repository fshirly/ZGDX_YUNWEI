package com.fable.insightview.monitor.alarmmgr.entity;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * 告警原始事件过滤器
 * 
 * @author hanl
 * 
 */

@Alias("alarmOriginalEventFilter")
public class AlarmOriginalEventFilterBean {
	/* 事件过滤编号 */
	@NumberGenerator(name="monitorOriginalFilterPK")
	private Integer filterID;

	/* 告警定义编号 */
	private Integer alarmDefineID;

	/* 过滤关键字 */
	private String keyWord;

	/* 关键字运算符 */
	private String keyOperator;

	/* 过滤值 */
	private String keyValue;

	/* 操作标志 */
	private Integer mFlag;
	
	private String alarmName;
	private String match;
	private String action;

	public Integer getFilterID() {
		return filterID;
	}

	public void setFilterID(Integer filterID) {
		this.filterID = filterID;
	}

	public Integer getAlarmDefineID() {
		return alarmDefineID;
	}

	public void setAlarmDefineID(Integer alarmDefineID) {
		this.alarmDefineID = alarmDefineID;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getKeyOperator() {
		return keyOperator;
	}

	public void setKeyOperator(String keyOperator) {
		this.keyOperator = keyOperator;
	}

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
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

	public String getMatch() {
		return match;
	}

	public void setMatch(String match) {
		this.match = match;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}


}
