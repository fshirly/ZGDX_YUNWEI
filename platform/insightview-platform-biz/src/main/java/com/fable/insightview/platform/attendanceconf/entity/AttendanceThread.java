package com.fable.insightview.platform.attendanceconf.entity;

import java.util.List;

import com.fable.insightview.platform.attendance.service.AttendanceRecordService;
import com.fable.insightview.platform.attendanceconf.mapper.AttendancePlanConfMapper;

public class AttendanceThread implements Runnable {
	private Integer status = null;
	private AttendanceRecordService attendanceRecordService = null;
	private AttendancePlanConfMapper attendancePlanConfMapper = null;
	private Integer attendanceId;
	
	public AttendanceThread(Integer status, AttendanceRecordService attendanceService,
			AttendancePlanConfMapper attendancePlanConfMapper, Integer attendanceId) {
		super();
		this.status = status;
		this.attendanceRecordService = attendanceService;
		this.attendanceId = attendanceId;
		this.attendancePlanConfMapper = attendancePlanConfMapper;
	}



	@Override
	public void run() {
		AttendancePlanConf planConf = this.attendancePlanConfMapper.queryAttendancePlanConfDetail(this.attendanceId);
		List<AttendancePeriodConf> periodConfs = this.attendanceRecordService.qryAttPeriodConfListByPlanConfId(this.attendanceId);
		List<AttendancePeopleConf> pepConfs = this.attendanceRecordService.qryAttPeopleConfListByPlanConfId(this.attendanceId); 
		
		if(2 == this.status) {
			this.attendanceRecordService.addAttendanceRecordInfo(planConf, periodConfs, pepConfs);
		}
		else if(3 == this.status) {
			this.attendanceRecordService.detachDeperatedAttRecInfo(planConf, periodConfs, pepConfs);
		}

	}

}
