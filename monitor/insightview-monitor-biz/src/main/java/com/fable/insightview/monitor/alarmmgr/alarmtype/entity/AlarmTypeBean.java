package com.fable.insightview.monitor.alarmmgr.alarmtype.entity;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;
/**
 * 告警类型
 * @Description:AlarmTypeBean
 * @author zhurt
 * @date 2014-7-16
 */

@Alias("typeInfo")
public class AlarmTypeBean {
	@NumberGenerator(name = "AlarmTypeID")
	
	private int alarmTypeID;	//告警类型编号
	private String alarmTypeName;	//告警类型名称
	private int isSystem;	//是否系统定义(0,1)

	public AlarmTypeBean() {
	}

	public int getAlarmTypeID() {
		return alarmTypeID;
	}

	public void setAlarmTypeID(int alarmTypeID) {
		this.alarmTypeID = alarmTypeID;
	}

	public String getAlarmTypeName() {
		return alarmTypeName;
	}

	public void setAlarmTypeName(String alarmTypeName) {
		this.alarmTypeName = alarmTypeName;
	}

	public int getIsSystem() {
		return isSystem;
	}

	public void setIsSystem(int isSystem) {
		this.isSystem = isSystem;
	}

}
