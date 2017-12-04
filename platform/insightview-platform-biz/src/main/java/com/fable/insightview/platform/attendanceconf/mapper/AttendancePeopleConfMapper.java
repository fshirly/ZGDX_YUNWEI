package com.fable.insightview.platform.attendanceconf.mapper;
import java.util.List;

import com.fable.insightview.platform.attendanceconf.entity.AttendancePeopleConf;
import com.fable.insightview.platform.attendanceconf.entity.AttendancePeriodConf;
import com.fable.insightview.platform.attendanceconf.entity.SysUserInfo;
import com.fable.insightview.platform.contractmanager.entity.ComboxBean;
import com.fable.insightview.platform.page.Page;
public interface AttendancePeopleConfMapper {
	/*
	 * 获取用户信息添加时
	 */
   List<SysUserInfo> querySysUserInfoList(Page page);
   /*
    * 已添加获取用户信息
    */
   List<SysUserInfo> querySysUserSucessInfoList(Page page);
   /*
    * 供应商的下拉框
    */
   List<ComboxBean> queryAttendanceProviderConfZtTypeCombox();
   /*
    * 插入人员与签到计划关系表
    */
   void  insertAttendancePeopleConf(AttendancePeopleConf attendancePeopleConf);
   /*
    * 删除签到配置信息
    */
   void deleteAttendancePeopleConf(Integer id);
   /*
    * 删掉签到人员信息（根据签到计划）
    */
   void deleteAttendancePlanPeopleConf(Integer attendanceId);
   /**
    * 根据签到计划获取相应的人员设置列表
    * @param attendanceId
    * @return
    */
   List<AttendancePeopleConf> queryAttendancePeopleConfList(Integer attendanceId);
}
