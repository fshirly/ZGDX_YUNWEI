package com.fable.insightview.platform.attendanceconf.mapper;

import java.util.List;

import com.fable.insightview.platform.attendanceconf.entity.AttendancePeriodConf;
import com.fable.insightview.platform.attendanceconf.entity.AttendancePlanConf;
import com.fable.insightview.platform.contractmanager.entity.ComboxBean;
import com.fable.insightview.platform.page.Page;

public interface AttendancePeriodConfMapper {
	/*
	 * 添加签到计划信息
	 */
	void  insertAttendancePeriodConf(AttendancePeriodConf attendancePeriodConf);
	/*
	 * 查询签到计划信息
	 */
	List<AttendancePeriodConf> queryAttendancePeriodConflist(Integer attendanceId);
	/*
	 * 修改签到计划信息
	 */
	void updateAttendancePeriodConf(AttendancePeriodConf attendancePeriodConf);
	/*
	 * 删除签到信息
	 */
	void deleteAttendancePeriodConf(Integer attendanceId);
	
	/*
	 * 删除签到时间段
	 */
	void delete(Integer attPeriodId);
}
