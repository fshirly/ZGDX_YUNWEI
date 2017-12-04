package com.fable.insightview.platform.attendanceconf.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.attendance.service.AttendanceRecordService;
import com.fable.insightview.platform.attendanceconf.entity.AttendancePeopleConf;
import com.fable.insightview.platform.attendanceconf.entity.AttendancePeriodConf;
import com.fable.insightview.platform.attendanceconf.entity.AttendancePlanConf;
import com.fable.insightview.platform.attendanceconf.entity.AttendanceThread;
import com.fable.insightview.platform.attendanceconf.entity.SysUserInfo;
import com.fable.insightview.platform.attendanceconf.mapper.AttendancePeopleConfMapper;
import com.fable.insightview.platform.attendanceconf.mapper.AttendancePeriodConfMapper;
import com.fable.insightview.platform.attendanceconf.mapper.AttendancePlanConfMapper;
import com.fable.insightview.platform.attendanceconf.service.AttendanceConfService;
import com.fable.insightview.platform.contractmanager.entity.ComboxBean;
import com.fable.insightview.platform.page.Page;

@Service
public class AttendanceConfServiceImpl implements AttendanceConfService {
   @Autowired
   private AttendancePlanConfMapper attendancePlanConfMapper;
   @Autowired
   private AttendancePeriodConfMapper attendancePeriodConfMapper;
   @Autowired
   private AttendancePeopleConfMapper attendancePeopleConfMapper;
   @Autowired
   private AttendanceRecordService attendanceRecordService;
   /*
    *查询签订计划 
    */
   @Override
   public List<AttendancePlanConf> queryAttendancePlanConflist(Page page) {
	  return attendancePlanConfMapper.queryAttendancePlanConflist(page);
   }
/*
 * 查询下拉框
 * 
 */
@Override
public List<ComboxBean> queryAttendanceConfZtTypeCombox() {
	return attendancePlanConfMapper.queryZhuangtCombox();
}
/*
 * 添加配置
 */
public Integer insertAttendanceConf(AttendancePlanConf attendancePlanConf,List<AttendancePeriodConf> list){
	Integer result=0;
	try {
		if(attendancePlanConf.getStatus()==2){
			attendancePlanConf.setLaunchTime(new Date());
		}
		attendancePlanConfMapper.insertAttendancePlanConf(attendancePlanConf);
		Integer attendanceId=attendancePlanConf.getAttendanceId();
		for(int i=0;i<list.size();i++){
			AttendancePeriodConf attendancePeriodConf=list.get(i);
			attendancePeriodConf.setAttendanceId(attendanceId);
			attendancePeriodConfMapper.insertAttendancePeriodConf(attendancePeriodConf);
		}
		result=attendanceId;
	} catch (Exception e) {
		e.printStackTrace();
	}
	return result;
	
}
/*
 * 效验签到时间
 */
@Override
public boolean validatorvalidatorAttStartTime(AttendancePlanConf attendancePlanConf) {
	boolean flag=true;
	Integer count=attendancePlanConfMapper.validatorAttendancePlanConfTime(attendancePlanConf);
	if(count==null){
		count=0;
	}
	if(count>0){
		flag=false;
	}
	return flag;
}
/*
 * 删除签到信息
 */
@Override
public void deleteAttendancePlanConf(Integer attendanceId) {
	attendancePlanConfMapper.deleteAttendanceRecord(attendanceId);//删除签到记录
	attendancePeriodConfMapper.deleteAttendancePeriodConf(attendanceId);//删除签到时间段
	attendancePeopleConfMapper.deleteAttendancePlanPeopleConf(attendanceId);//删除签到人员
	attendancePlanConfMapper.deleteAttendancePlanConf(attendanceId);
	
}
/*
 * 批量删除签到信息
 */
@Override
public void deleteattendanceConfBathch(String ids) {
	 if(!ids.isEmpty()){
   	  String[] arrayid=ids.split(",");
   	  for(int i=0;i<arrayid.length;i++){
   		  Integer attendanceId=Integer.parseInt(arrayid[i]);
   		attendancePlanConfMapper.deleteAttendanceRecord(attendanceId);//删除签到记录
   		attendancePeriodConfMapper.deleteAttendancePeriodConf(attendanceId);//删除签到时间段
   		attendancePeopleConfMapper.deleteAttendancePlanPeopleConf(attendanceId);//删除签到人员
   		attendancePlanConfMapper.deleteAttendancePlanConf(attendanceId);
   	  }
   	  
     }   
	
}
/*
 * 修改签到状态
 */
@Override
public void updateattendanceConfzt(AttendancePlanConf attendancePlanConf) {
	if(attendancePlanConf.getStatus()==2){
		attendancePlanConf.setLaunchTime(new Date());
	}else if(attendancePlanConf.getStatus()==3){
		attendancePlanConf.setStopTime(new Date());
	}
	attendancePlanConfMapper.updateAttendancePlanConfzt(attendancePlanConf);
	Thread t = new Thread(new AttendanceThread(attendancePlanConf.getStatus(),this.attendanceRecordService,
			this.attendancePlanConfMapper, attendancePlanConf.getAttendanceId()));
	t.start();
	
}
/*
 *获取签到信息
 */
@Override
public AttendancePlanConf queryAttendancePlanConf(Integer attendanceId) {
	return attendancePlanConfMapper.queryAttendancePlanConfDetail(attendanceId);
}
/*
 * 查出签到时间段
 * 
 */
@Override
public List<AttendancePeriodConf> queryAttendancePeriodConf_list_Info(Integer attendanceId) {
	return attendancePeriodConfMapper.queryAttendancePeriodConflist(attendanceId);
}
/*
 * 修改配置页
 */
@Override
public void updateAttendanceConf(AttendancePlanConf attendancePlanConf,List<AttendancePeriodConf> list) {
	attendancePlanConf.setConfigTime(new Date());
	attendancePlanConfMapper.updateAttendancePlanConf(attendancePlanConf);
	//attendancePlanConfMapper.insertAttendancePlanConf(attendancePlanConf);
	Integer attendanceId=attendancePlanConf.getAttendanceId();
	for(int i=0;i<list.size();i++){
		AttendancePeriodConf attendancePeriodConf=list.get(i);
		if (attendancePeriodConf.getAttendanceId() == null) {
			if (attendancePeriodConf.getStartTime() == null) {
				attendancePeriodConfMapper.delete(attendancePeriodConf.getAttPeriodId());
			} else {
				attendancePeriodConf.setAttendanceId(attendanceId);
				attendancePeriodConfMapper.insertAttendancePeriodConf(attendancePeriodConf);
			}
		} else {
			attendancePeriodConfMapper.updateAttendancePeriodConf(attendancePeriodConf);
		}
	}
}
/*
 * 查出用户
 */
@Override
public List<SysUserInfo> queryAttendancePeoplePlanConflist(Page page) {
	return attendancePeopleConfMapper.querySysUserInfoList(page);
}
/*
 * 查出已添加用户
 */
@Override
public List<SysUserInfo> queryAttendancePeoplePlanConflist_select(Page page) {
	
	return attendancePeopleConfMapper.querySysUserSucessInfoList(page);
}
/*
 * 供应商的下拉框
 */
@Override
public List<ComboxBean> queryAttendanceProviderConfZtTypeCombox() {
	return attendancePeopleConfMapper.queryAttendanceProviderConfZtTypeCombox();
}
/*
 * 添加人员计划关系
 */
@Override
public void insertAttendanceConf(AttendancePeopleConf attendancePeopleConf) {
	String userIdStr[]=attendancePeopleConf.getUserIdStr().split(",");
	for(int i=0;i<userIdStr.length;i++){
		AttendancePeopleConf tempattendancePeopleConf=new AttendancePeopleConf();
		tempattendancePeopleConf.setAttendanceId(attendancePeopleConf.getAttendanceId());
		tempattendancePeopleConf.setUserId(new Integer(userIdStr[i]));
		attendancePeopleConfMapper.insertAttendancePeopleConf(tempattendancePeopleConf);
		
	}
	
}
/*
 * 删除人员与签到配置关系
 */
@Override
public void deleteAttendancePeopleConf(Integer id) {
	attendancePeopleConfMapper.deleteAttendancePeopleConf(id); 
	
}
/*
 * 判断标题是否重复(non-Javadoc)
 * @see com.fable.insightview.platform.attendanceconf.service.AttendanceConfService#validatorAttendancetitle(java.lang.String)
 */
@Override
public boolean validatorAttendancetitle(String title) {
	boolean flag=false;
	Integer count=attendancePlanConfMapper.validatorAttendanceTitleDouble(title);
	if(count==null){
		count=1;
	}
	if(count>0){
		flag=true;
	}
	return flag;
}

	@Override
	public boolean validateStartAttPlanConf(Integer attendanceId) {
		List<AttendancePeriodConf> periodConfs = this.attendancePeriodConfMapper.queryAttendancePeriodConflist(attendanceId);
		List<AttendancePeopleConf> peopleConfs = this.attendancePeopleConfMapper.queryAttendancePeopleConfList(attendanceId);
		if(null != periodConfs && !periodConfs.isEmpty() 
				&& null != peopleConfs && !peopleConfs.isEmpty()) {
			return true;
		}
		return false;
	}



   
}
