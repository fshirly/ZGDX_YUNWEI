package com.fable.insightview.platform.attendance.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.platform.attendance.entity.AttRecInfoStatisVO;
import com.fable.insightview.platform.attendance.entity.AttendancePlanConfVO;
import com.fable.insightview.platform.attendance.entity.AttendanceRecordInfo;
import com.fable.insightview.platform.attendance.entity.HisAttRecordInfoVo;
import com.fable.insightview.platform.attendanceconf.entity.AttendancePeopleConf;
import com.fable.insightview.platform.attendanceconf.entity.AttendancePeriodConf;
import com.fable.insightview.platform.attendanceconf.entity.AttendancePlanConf;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.page.Page;

/**
 * 人员签到
 * @author xue.antai
 *
 */
public interface AttendanceRecordService {

	/**
	 * 获取当前签到计划的人员签到列表
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
	List<AttendancePlanConfVO> qryCurrAttPlanConfList(Long currUserId);
	
	/**
	 * 所有已生效的计划列表
	 * @return
	 */
	List<AttendancePlanConfVO> qryAllAttPlanConfList();

	/**
	 * 当前用户未签到的签到计划次数
	 * @param attPlanId
	 * @param curUserId
	 * @return
	 */
	Integer qryCurrUncheckedAttRecordCount(Integer attPlanId, Long curUserId);

	/**
	 * 人员签到
	 * @param attPeopleId
	 * @param attPeriodId
	 * @param currUserId
	 * @return
	 */
	boolean checkInAttendanceRecord(Integer attPlanPeriodId, Integer attPeriodId, Long currUserId);

	/**
	 * 属于当前用户的签到计划的签到记录列表
	 * @param page
	 * @return
	 */
	List<HisAttRecordInfoVo> qryHisAttRecordInfoList(
			Page<AttendanceRecordInfo> page);

	/**
	 * 属于当前用户的签到计划的签到时间段数目
	 * @param qryParams
	 * @return
	 */
	int qryHisAttRecordInfoCount(Map<String, Object> qryParams);

	/**
	 * 签到记录统计列表
	 * @param page
	 * @param isPage 是否分页
	 * @return
	 */
	List<AttRecInfoStatisVO> qryAttRecInfoStatisVOs(
			Page<AttRecInfoStatisVO> page, boolean isPage);
	/**
	 * 签到统计列表
	 * @param page
	 * @param periodCount
	 * @param isPage 是否分页
	 * @return
	 */
	public List<HisAttRecordInfoVo> qryAttRecInfoStatis(Page<AttRecInfoStatisVO> page, int periodCount, boolean isPage);

	/**
	 * 签到统计总数
	 * @param qryParams
	 * @return
	 */
	int qryAttRecStatisTotalCount(Map<String, Object> qryParams);

	/**
	 * 未签到统计总数
	 * @param qryParams
	 * @return
	 */
	int qryAttRecStatisUncheckedCount(@Param(value="params")Map<String, Object> qryParams);

	/**
	 * 生成签到计划实例
	 * @param attendancePlanConf
	 * @param attendancePeriodConfs
	 * @param attendancePeopleConfs
	 * @return
	 */
	boolean addAttendanceRecordInfo(AttendancePlanConf attendancePlanConf,
			List<AttendancePeriodConf> attendancePeriodConfs,List<AttendancePeopleConf> attendancePeopleConfs);

	/**
	 * 获取该计划的时间段配置列表
	 * @param attendanceId
	 * @return
	 */
	List<AttendancePeriodConf> qryAttPeriodConfListByPlanConfId(
			Integer attendanceId);

	/**
	 * 获取该计划对应的人员配置列表
	 * @param attendanceId
	 * @return
	 */
	List<AttendancePeopleConf> qryAttPeopleConfListByPlanConfId(
			Integer attendanceId);

	/**
	 * 废弃已失效未签到的签到计划对应的签到记录
	 * @param planConf
	 * @param periodConfs
	 * @param pepConfs
	 * @return
	 */
	boolean detachDeperatedAttRecInfo(AttendancePlanConf planConf,
			List<AttendancePeriodConf> periodConfs,
			List<AttendancePeopleConf> pepConfs);

	/**
	 * 根据签到计划获取对应人员配置的用户信息
	 * @param attPlanId
	 * @return
	 */
	List<SysUserInfoBean> qryPlanPeopleListByPlanId(Integer attPlanId);

}
