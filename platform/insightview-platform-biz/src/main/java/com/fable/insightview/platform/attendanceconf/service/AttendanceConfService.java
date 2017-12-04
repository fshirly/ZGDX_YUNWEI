package com.fable.insightview.platform.attendanceconf.service;

import java.util.List;

import com.fable.insightview.platform.attendanceconf.entity.AttendancePeopleConf;
import com.fable.insightview.platform.attendanceconf.entity.AttendancePeriodConf;
import com.fable.insightview.platform.attendanceconf.entity.AttendancePlanConf;
import com.fable.insightview.platform.attendanceconf.entity.SysUserInfo;
import com.fable.insightview.platform.contractmanager.entity.ComboxBean;
import com.fable.insightview.platform.page.Page;

public interface AttendanceConfService {
	List<AttendancePlanConf> queryAttendancePlanConflist(Page page);
	List<ComboxBean> queryAttendanceConfZtTypeCombox();
	Integer insertAttendanceConf(AttendancePlanConf attendancePlanConf,List<AttendancePeriodConf> list);
	boolean validatorvalidatorAttStartTime(AttendancePlanConf attendancePlanConf);
	void deleteAttendancePlanConf(Integer attendanceId);
	void  deleteattendanceConfBathch(String ids);
	void updateattendanceConfzt(AttendancePlanConf attendancePlanConf);
	AttendancePlanConf queryAttendancePlanConf(Integer attendanceId);
	List<AttendancePeriodConf> queryAttendancePeriodConf_list_Info(Integer attendanceId);
	void updateAttendanceConf(AttendancePlanConf attendancePlanConf,List<AttendancePeriodConf> list);
	List<SysUserInfo> queryAttendancePeoplePlanConflist(Page page);
	List<SysUserInfo> queryAttendancePeoplePlanConflist_select(Page page);
	List<ComboxBean> queryAttendanceProviderConfZtTypeCombox();
	void insertAttendanceConf(AttendancePeopleConf attendancePeopleConf);
	void deleteAttendancePeopleConf(Integer id);
	boolean  validatorAttendancetitle(String title);
	/**
	 * 校验是否可以启动配置计划
	 * @param attendanceId
	 * @return
	 */
	boolean validateStartAttPlanConf(Integer attendanceId);
}
