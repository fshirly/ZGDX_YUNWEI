package com.fable.insightview.platform.attendance.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.platform.attendance.entity.AttRecInfoStatisVO;
import com.fable.insightview.platform.attendance.entity.AttendancePlanConfVO;
import com.fable.insightview.platform.attendance.entity.AttendanceRecordInfo;
import com.fable.insightview.platform.attendanceconf.entity.AttendancePeopleConf;
import com.fable.insightview.platform.attendanceconf.entity.AttendancePeriodConf;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.page.Page;

/**
 * 人员签到
 * @author xue.antai
 *
 */
public interface AttendanceRecordMapper {
	/**
	 * 当前用户的人员签到列表
	 * @param page
	 * @return
	 */
	List<AttendanceRecordInfo> qryCurrAttRecordInfoList(
			Page<AttendanceRecordInfo> page);

	/**
	 * 当前用户的签到计划列表
	 * @param currUserId
	 * @return
	 */
	List<AttendancePlanConfVO> qryCurrAttPlanConfList(@Param(value="currUserId")Long currUserId);

	/**
	 * 当前用户未签到的签到计划次数
	 * @param params
	 * @return
	 */
	Integer qryCurrUncheckedAttRecordCount(@Param(value="params")Map<String, Object> params);

	/**
	 * 新增人员签到信息
	 * @param attRecordInfo
	 * @return
	 */
	Integer insertAttendanceRecordInfo(AttendanceRecordInfo attRecordInfo);
	
	/**
	 * 更新人员签到信息
	 * @param attRecordId
	 * @return
	 */
	Integer updateAttTime(Integer attRecordId);

	/**
	 * 属于当前用户的签到计划的签到记录列表
	 * @param page
	 * @return
	 */
	List<AttendanceRecordInfo> qryHisAttRecordInfoList(
			Page<AttendanceRecordInfo> page);

	/**
	 * 属于当前用户的签到计划的签到记录时间段数目
	 * @param qryParams
	 * @return
	 */
	int qryHisAttRecordInfoCount(@Param(value="params")Map<String, Object> qryParams);

	/**
	 * 属于当前用户的签到记录统计列表
	 * @param page
	 * @return
	 */
	List<AttRecInfoStatisVO> qryAttRecInfoStatisVOs(
			Page<AttRecInfoStatisVO> page);

	/**
	 * 签到统计数目
	 * @param params
	 * @return
	 */
	Integer qryAttRecInfoStatisCount(@Param(value="params")Map<String, Object> params);

	/**
	 * 获取计划的签到记录
	 * @param attPeopleId
	 * @param attPeriodId
	 * @param currUserId
	 * @return
	 */
	Integer getAttRecordInfo(@Param(value="params")Map<String, Object> params);

	/**
	 * 根据签到计划Id获取签到时间段设置列表
	 * @param attendanceId
	 * @return
	 */
	List<AttendancePeriodConf> qryAttPeriodConfListByPlanConfId(
			Integer attendanceId);

	/**
	 * 根据签到计划Id获取签到人员设置列表
	 * @param attendanceId
	 * @return
	 */
	List<AttendancePeopleConf> qryAttPeopleConfListByPlanConfId(
			Integer attendanceId);

	/**
	 * 删除已停用的签到计划对应的签到记录
	 * @param params 
	 * @return
	 */
	int batchDelAttRecInfo(@Param(value="params")Map<String, Object> params);

	/**
	 * 批量添加计划的签到记录
	 * @param list
	 * @return
	 */
	int batchInsertAttRecInfo(@Param(value="list")List<AttendanceRecordInfo> list);

	/**
	 * 根据Id获取签到记录
	 * @param attendanceId
	 * @return
	 */
	AttendanceRecordInfo getAttRecInfoByAttendanceId(Integer attendanceId);

	/**
	 * 获取所有的已启动的签到计划
	 * @return
	 */
	List<AttendancePlanConfVO> qryAllAttPlanConfList();

	/**
	 * 根据签到计划Id获取人员对应的用户信息
	 * @param attPlanId
	 * @return
	 */
	List<SysUserInfoBean> qryPlanPeopleListByPlanId(@Param(value="attPlanId")Integer attPlanId);

}
