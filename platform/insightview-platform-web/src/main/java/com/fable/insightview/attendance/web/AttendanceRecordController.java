package com.fable.insightview.attendance.web;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.platform.attendance.entity.AttRecInfoStatisVO;
import com.fable.insightview.platform.attendance.entity.AttendancePlanConfVO;
import com.fable.insightview.platform.attendance.entity.AttendanceRecordInfo;
import com.fable.insightview.platform.attendance.entity.HisAttRecordInfoVo;
import com.fable.insightview.platform.attendance.service.AttendanceRecordService;
import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.entity.export.ExcelToBeanDTO;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.page.Page;

/**
 * 人员签到
 * @author xue.antai
 *
 */
@Controller
@RequestMapping("/attendanceRecord")
public class AttendanceRecordController extends ExcelExportController {
	@Autowired
	private AttendanceRecordService attendanceRecordService;
	@Autowired
	private final static Logger logger = LoggerFactory.getLogger(AttendanceRecordController.class); 
	
	@RequestMapping("/viewAttendanceRecordList")
	public ModelAndView viewAttendanceRecordList(HttpServletRequest request, String navigationBar) {
		return new ModelAndView("/platform/attendance/attendance_record_list").addObject("navigationBar", navigationBar);
	}
	
	/**
	 * 当前用户当前签到计划未签到总数
	 * @param request
	 * @param ari
	 * @return
	 */
	@RequestMapping("/toCountUnchkAttRecInfo")
	@ResponseBody
	public Map<String, Object> toCountUnchkAttRecInfo(HttpServletRequest request, AttendanceRecordInfo ari) {
		Map<String, Object> params = new HashMap<String, Object>();

		SecurityUserInfoBean currUserInfo = this.getUserIdBySession(request);
		Long userId = currUserInfo.getId();
		Integer uncheckedCount = this.attendanceRecordService.qryCurrUncheckedAttRecordCount(ari.getAttPlanId(), userId);
		params.put("uncheckedCount", uncheckedCount);
		return params;
	}
	
	/**
	 * 当前用户的人员签到列表
	 * @param attendanceRecordInfo
	 * @param request
	 * @return
	 */
	@RequestMapping("/toAttendanceRecordList")
	@ResponseBody
	public Map<String, Object> toAttendanceRecordList(AttendanceRecordInfo attendanceRecordInfo, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		FlexiGridPageInfo flexGridPageInfo = new FlexiGridPageInfoUtil().getFlexiGridPageInfo(request);
		Page<AttendanceRecordInfo> page = new Page<AttendanceRecordInfo>();
		page.setPageNo(flexGridPageInfo.getPage());
		page.setPageSize(flexGridPageInfo.getRp());
		
		SecurityUserInfoBean currUserInfo = this.getUserIdBySession(request);
		Long userId = currUserInfo.getId();
		
		Map<String, Object> qryParams = new HashMap<String, Object>();
		qryParams.put("attPlanId", attendanceRecordInfo.getAttPlanId());
		qryParams.put("userId", userId);
		page.setParams(qryParams);
		List<AttendanceRecordInfo> data	= this.attendanceRecordService.qryCurrAttRecordInfoList(page);
		result.put("rows", data);
		result.put("total", page.getTotalRecord());
		return result;
	}
	
	/**
	 * 签到记录
	 * @param attRecordInfo
	 * @param request
	 * @return
	 */
	@RequestMapping("/toHisAttRecordList")
	@ResponseBody
	public Map<String, Object> toHisAttRecordList(AttendanceRecordInfo attRecordInfo, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		FlexiGridPageInfo flexiGridPageInfo = new FlexiGridPageInfoUtil().getFlexiGridPageInfo(request);
		Page<AttendanceRecordInfo> page = new Page<AttendanceRecordInfo>();
		
		SecurityUserInfoBean currUserInfo = this.getUserIdBySession(request);
		Long curUserId = currUserInfo.getId();
		Map<String, Object> qryParams = new HashMap<String, Object>();
		qryParams.put("userId", curUserId);
		qryParams.put("attPlanId", attRecordInfo.getAttPlanId());
		qryParams.put("planStartTime", attRecordInfo.getAttStartTime());
		qryParams.put("planEndTime", attRecordInfo.getAttEndTime());
		
		int periodCount = this.attendanceRecordService.qryHisAttRecordInfoCount(qryParams);
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp()*periodCount);
		page.setParams(qryParams);
		
		List<HisAttRecordInfoVo> data = this.attendanceRecordService.qryHisAttRecordInfoList(page);
		result.put("rows", data);
		result.put("total", page.getTotalRecord()==0?0:page.getTotalRecord()/periodCount);
		result.put("periodCount", periodCount);
		return result;
	}
	
	/**
	 * 当前用户的签到计划列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/toAttendancePlanList") 
	@ResponseBody
	public List<AttendancePlanConfVO> toAttendancePlanList(HttpServletRequest request) {
		Long currUserId = this.getUserIdBySession(request).getId();
		List<AttendancePlanConfVO> list = this.attendanceRecordService.qryCurrAttPlanConfList(currUserId);
		return list;
	}
	
	/**
	 * 已生效的签到计划列表
	 * @return
	 */
	@RequestMapping("/toAllAttPlanList")
	@ResponseBody
	public List<AttendancePlanConfVO> toAllAttPlanList() {
		List<AttendancePlanConfVO> list = this.attendanceRecordService.qryAllAttPlanConfList();
		return list;
	}
	
	@RequestMapping("/toPlanPeopleList")
	@ResponseBody
	public List<SysUserInfoBean> toPlanPeopleList(AttendanceRecordInfo ari) {
		List<SysUserInfoBean> users = this.attendanceRecordService.qryPlanPeopleListByPlanId(ari.getAttPlanId());
		return users;
	}
	
	/**
	 * 签到
	 * @param request
	 * @return
	 */
	@RequestMapping("/toCheckInAttendanceRecord") 
	@ResponseBody
	public boolean toCheckInAttendanceRecord(HttpServletRequest request) {
		boolean flag = false;
		Long currUserId = this.getUserIdBySession(request).getId();
		Integer attPeopleId = Integer.valueOf(request.getParameter("attPeopleId"));
		Integer attPeriodId = Integer.valueOf(request.getParameter("attPeriodId"));
		flag = this.attendanceRecordService.checkInAttendanceRecord(attPeopleId, attPeriodId, currUserId);
		return flag;
	}
	
	/**
	 * 签到统计视图
	 * @param request
	 * @return
	 */
	@RequestMapping("/viewStatisAttendanceRecord")
	public ModelAndView viewStatisAttendanceRecord(HttpServletRequest request, String navigationBar) {
		return new ModelAndView("/platform/attendance/attendance_statistics").addObject("navigationBar", navigationBar);
	}
	
	
	@RequestMapping("/attendancePop")
	public ModelAndView attendancePop(HttpServletRequest request) {
		return new ModelAndView("/platform/attendance/attendance_pop");
	}
	
	/**
	 * 打印新窗口
	 * @param request
	 * @param ars
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/viewAttRecordPrint")
	public ModelAndView viewAttRecordPrint(HttpServletRequest request, AttRecInfoStatisVO ars) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("attPlanId", ars.getAttPlanId());
		params.put("userId", ars.getUserId());
		params.put("hasUncheckedIn", ars.getHasUncheckedIn());
		params.put("checkInStartTime", ars.getCheckInStartTime());
		params.put("checkInEndTime", ars.getCheckInEndTime());
		params.put("title", new String(request.getParameter("title").getBytes(), "UTF-8"));
		params.put("pageSize", request.getParameter("pageSize"));
		params.put("pageNo", request.getParameter("pageNo"));
		
		return new ModelAndView("/platform/attendance/attendance_record_print", params);
	}
	
	/**
	 * 打印列表
	 * @param ars
	 * @param request
	 * @return
	 */
	@RequestMapping("/toAttRecordPrint")
	@ResponseBody
	public Map<String, Object> toAttRecordPrint(AttRecInfoStatisVO ars, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> qryParams = new HashMap<String, Object>();
		qryParams.put("attPlanId", ars.getAttPlanId());
		qryParams.put("userId", ars.getUserId());
		qryParams.put("hasUncheckedIn", ars.getHasUncheckedIn());
		qryParams.put("checkInStartTime", ars.getCheckInStartTime());
		qryParams.put("checkInEndTime", ars.getCheckInEndTime());
		
		Page<AttRecInfoStatisVO> page = new Page<AttRecInfoStatisVO>();
		page.setParams(qryParams);
		int periodCount = this.attendanceRecordService.qryHisAttRecordInfoCount(qryParams);
		List<HisAttRecordInfoVo> data = this.attendanceRecordService.qryAttRecInfoStatis(page,periodCount, false);
		result.put("rows", data);
		result.put("total", data.size());
		return result;
	}
	
	/**
	 * 签到统计总计
	 * @param request
	 * @param ars
	 * @return
	 */
	@RequestMapping("/toStatisAttendanceRecordCount")
	@ResponseBody
	public Map<String, Object> toStatisAttendanceRecordCount(HttpServletRequest request, AttRecInfoStatisVO ars) {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> qryParams = new HashMap<String, Object>();
		qryParams.put("attPlanId", ars.getAttPlanId());
		qryParams.put("userId", ars.getUserId());
		qryParams.put("checkInStartTime", ars.getCheckInStartTime());
		qryParams.put("checkInEndTime", ars.getCheckInEndTime());
		int totalCount = this.attendanceRecordService.qryAttRecStatisTotalCount(qryParams);
		int uncheckedCount = this.attendanceRecordService.qryAttRecStatisUncheckedCount(qryParams);
		
		params.put("totalCount", totalCount);
		params.put("uncheckedCount", uncheckedCount);
		params.put("checkedCount", totalCount - uncheckedCount);
		return params;
	}
	
	/**
	 * 签到统计列表
	 * @param attRecInfo
	 * @param request
	 * @return
	 */
	@RequestMapping("/toStatisAttendanceRecord")
	@ResponseBody
	public Map<String, Object> toStatisAttendanceRecord(AttRecInfoStatisVO ars, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		Page<AttRecInfoStatisVO> page = new Page<AttRecInfoStatisVO>();
		FlexiGridPageInfo flexiGridPageInfo = new FlexiGridPageInfoUtil().getFlexiGridPageInfo(request);
		Map<String, Object> qryParams = new HashMap<String, Object>();
		qryParams.put("attPlanId", ars.getAttPlanId());
		qryParams.put("userId", ars.getUserId());
		qryParams.put("hasUncheckedIn", ars.getHasUncheckedIn());
		qryParams.put("checkInStartTime", ars.getCheckInStartTime());
		qryParams.put("checkInEndTime", ars.getCheckInEndTime());
		
		int periodCount = this.attendanceRecordService.qryHisAttRecordInfoCount(qryParams);
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		page.setParams(qryParams);
		
		List<HisAttRecordInfoVo> data = this.attendanceRecordService.qryAttRecInfoStatis(page,periodCount, true);
		result.put("periodCount", periodCount);
		result.put("rows", data);
		result.put("total", page.getTotalRecord());
		return result;
	}
	
	private SecurityUserInfoBean getUserIdBySession(HttpServletRequest request) {
		SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
				.getSession().getAttribute("sysUserInfoBeanOfSession");

		return sysUserInfoBeanTemp;
	}
	
	/**
	 * 导出签到统计信息
	 * @param ars
	 * @param request
	 * @param response
	 * @param export
	 */
	@RequestMapping("/toExpStatisAttRecord")
	public void toExpStatisAttRecord(AttRecInfoStatisVO ars, HttpServletRequest request,
			HttpServletResponse response,ExcelToBeanDTO<T> export) {
		Page<AttRecInfoStatisVO> page = new Page<AttRecInfoStatisVO>();
		FlexiGridPageInfo flexiGridPageInfo = new FlexiGridPageInfoUtil().getFlexiGridPageInfo(request);
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		
		Map<String, Object> qryParams = new HashMap<String, Object>();
		qryParams.put("attPlanId", ars.getAttPlanId());
		qryParams.put("userId", ars.getUserId());
		qryParams.put("hasUncheckedIn", ars.getHasUncheckedIn());
		qryParams.put("checkInStartTime", ars.getCheckInStartTime());
		qryParams.put("checkInEndTime", ars.getCheckInEndTime());
		page.setParams(qryParams);
		// 签到时间段预设列数
		int periodCount = this.attendanceRecordService.qryHisAttRecordInfoCount(qryParams);
		List staticsInfo = this.attendanceRecordService.qryAttRecInfoStatis(page,periodCount, false);
		export.setPeriodCount(periodCount);
		this.exportVal(response, export, staticsInfo);
	}
	
}
