package com.fable.insightview.platform.attendanceconf.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.platform.attendanceconf.entity.AttendancePeopleConf;
import com.fable.insightview.platform.attendanceconf.entity.AttendancePlanConf;
import com.fable.insightview.platform.contractmanager.entity.ComboxBean;
import com.fable.insightview.platform.page.Page;

public interface AttendancePlanConfMapper {
	/*
	 * 添加签到计划信息
	 */
	void  insertAttendancePlanConf(AttendancePlanConf attendancePlanConf);
	/*
	 * 查询签到计划信息
	 */
	List<AttendancePlanConf> queryAttendancePlanConflist(Page page);
	/*
	 * 修改签到计划信息
	 */
	void updateAttendancePlanConf(AttendancePlanConf attendancePlanConf);
	/*
	 * 详情页
	 */
	AttendancePlanConf queryAttendancePlanConfDetail(Integer attendanceId);
	/*
	 * 删除签到信息
	 */
	void deleteAttendancePlanConf(Integer attendanceId);
	/*
	 * 计划状态
	 */
	List<ComboxBean> queryZhuangtCombox();
	/*
	 * 判断签到时间是否重复
	 */
	Integer validatorAttendancePlanConfTime(@Param(value="planConf")AttendancePlanConf attendancePlanConf);
	/*
	 * 修改签到计划状态
	 */
	void updateAttendancePlanConfzt(AttendancePlanConf attendancePlanConf);
    /*
     * 判断标题是否重复
     */
	Integer validatorAttendanceTitleDouble(String title);
	/*
	 * 删除签到记录
	 */
	void deleteAttendanceRecord(Integer attendanceId);
}
