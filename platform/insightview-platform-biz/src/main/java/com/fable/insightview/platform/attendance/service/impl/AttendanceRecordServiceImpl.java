package com.fable.insightview.platform.attendance.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.attendance.dao.AttendanceRecordDAO;
import com.fable.insightview.platform.attendance.entity.AttRecInfoStatisVO;
import com.fable.insightview.platform.attendance.entity.AttendancePlanConfVO;
import com.fable.insightview.platform.attendance.entity.AttendanceRecordInfo;
import com.fable.insightview.platform.attendance.entity.HisAttRecordInfoVo;
import com.fable.insightview.platform.attendance.mapper.AttendanceRecordMapper;
import com.fable.insightview.platform.attendance.service.AttendanceRecordService;
import com.fable.insightview.platform.attendanceconf.entity.AttendancePeopleConf;
import com.fable.insightview.platform.attendanceconf.entity.AttendancePeriodConf;
import com.fable.insightview.platform.attendanceconf.entity.AttendancePlanConf;
import com.fable.insightview.platform.dao.ISysUserDao;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.page.Page;

/**
 * 人员签到
 * @author xue.antai
 *
 */
@Service
public class AttendanceRecordServiceImpl implements AttendanceRecordService {

	@Autowired
	private AttendanceRecordMapper attendanceRecordMapper;
	
	@Autowired
	private ISysUserDao sysUserDao;
	
	@Autowired
	private AttendanceRecordDAO attendanceRecordDAO;
	
	public List<AttendanceRecordInfo> qryCurrAttRecordInfoList(
			Page<AttendanceRecordInfo> page) {
//		List<AttendanceRecordInfo> list = this.attendanceRecordDAO.qryCurrAttRecordInfoList(page);
//		return list;
		List<AttendanceRecordInfo> list = attendanceRecordMapper.qryCurrAttRecordInfoList(page);
		return list;
	}

	public List<AttendancePlanConfVO> qryCurrAttPlanConfList(Long currUserId) {
		return this.attendanceRecordMapper.qryCurrAttPlanConfList(currUserId);
	}

	public Integer qryCurrUncheckedAttRecordCount(Integer attPlanId, Long curUserId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("attPlanId", attPlanId);
		params.put("curUserId", curUserId);
		return this.attendanceRecordMapper.qryCurrUncheckedAttRecordCount(params);
	}

	public boolean checkInAttendanceRecord(Integer attPeopleId,Integer attPeriodId, Long currUserId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("attPeopleId", attPeopleId);
		params.put("attPeriodId", attPeriodId);
		params.put("currUserId", currUserId);
		String attSignDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		params.put("attSignDate", attSignDate);
		Integer attRecordId = this.attendanceRecordMapper.getAttRecordInfo(params);
		int ret = this.attendanceRecordMapper.updateAttTime(attRecordId);
		if(ret > 0) {
			return true;
		}
		return false;
	}

	public int qryHisAttRecordInfoCount(Map<String, Object> qryParams) {
		int cnt = this.attendanceRecordMapper.qryHisAttRecordInfoCount(qryParams);
		return cnt;
	}

	public List<HisAttRecordInfoVo> qryHisAttRecordInfoList(
			Page<AttendanceRecordInfo> page) {
		Date attStartTime = null;
		Date attEndTime = null;
		Integer attendanceId = (Integer) page.getParams().get("attPlanId"); 
		AttendanceRecordInfo ari = this.attendanceRecordMapper.getAttRecInfoByAttendanceId(attendanceId);
		if(null != attendanceId) {
			if(null == page.getParams().get("planStartTime")) {
				attStartTime = ari.getAttStartTime();
			}
			else {
				attStartTime = (Date) page.getParams().get("planStartTime");
			}
			if(null == page.getParams().get("planEndTime")) {
				attEndTime = ari.getAttEndTime();
			}
			else {
				attEndTime = (Date) page.getParams().get("planEndTime");
			}
			attEndTime = attEndTime.getTime() <= new Date().getTime() ? attEndTime : new Date();
		}
		
		page.getParams().put("planStartTime", attStartTime);
		page.getParams().put("planEndTime", attEndTime);
		
		List<AttendanceRecordInfo> attRecInfoList = this.attendanceRecordMapper.qryHisAttRecordInfoList(page);
		List<HisAttRecordInfoVo> list = new ArrayList<HisAttRecordInfoVo>();
		AttendanceRecordInfo attRecInfo = null;
		AttendanceRecordInfo nextAttRecInfo = null;
		HisAttRecordInfoVo haiv = null;
		List<String[]> signTimeSlot = null;
		String[] arr = null;
		int sum = 0;
		int unchkSum = 0;
		
		for(int i = 0;i < attRecInfoList.size();i++) {
			sum++;
			attRecInfo = (AttendanceRecordInfo) attRecInfoList.get(i);
			nextAttRecInfo = i<attRecInfoList.size()-1 ? (AttendanceRecordInfo) attRecInfoList.get(i+1) : null;
			if(null == attRecInfo.getAttTime()) {
				unchkSum++;
			}
			if(null == haiv) {
				haiv = new HisAttRecordInfoVo();
				signTimeSlot = new ArrayList<String[]>();
				
				haiv.setAttDate(attRecInfo.getAttDate());
				arr = new String[2];
				arr[0] = attRecInfo.getStartTime()+ "~"+ attRecInfo.getEndTime();
				if(null != attRecInfo.getAttTime()) {
					arr[1] = new SimpleDateFormat("HH:mm").format(attRecInfo.getAttTime());
				}
				else {
					arr[1] = "未签到";
				}
				signTimeSlot.add(arr);
			}
			else {
				if(haiv.getAttDate().equals(attRecInfo.getAttDate())) {
					arr = new String[2];
					arr[0] = attRecInfo.getStartTime()+ "~"+ attRecInfo.getEndTime();
					if(null != attRecInfo.getAttTime()) {
						arr[1] = new SimpleDateFormat("HH:mm").format(attRecInfo.getAttTime());
					}
					else {
						arr[1] = "未签到";
					}
					
					signTimeSlot.add(arr);
				}
			}
			
			if(attRecInfoList.size()==1) {
				haiv.setSignTimeSlot(signTimeSlot);
				haiv.setStatistics(unchkSum+ "/" + sum);
				list.add(haiv);
				sum = 0;
				unchkSum = 0;
				break;
			}
			if(null != nextAttRecInfo && attRecInfo.getAttDate().equals(nextAttRecInfo.getAttDate())) {
				continue;
			}
			else {
				haiv.setSignTimeSlot(signTimeSlot);
				haiv.setStatistics(unchkSum+ "/" + sum);
				list.add(haiv);
				haiv = null;
				sum = 0;
				unchkSum = 0;
			}
			
		}
		
		return list;
	}

	
	public List<AttRecInfoStatisVO> qryAttRecInfoStatisVOs(
			Page<AttRecInfoStatisVO> page, boolean isPage) {
		Date attStartTime = null;
		Date attEndTime = null;
		Integer attendanceId = (Integer) page.getParams().get("attPlanId");
		if(null == attendanceId) {
			return new ArrayList<AttRecInfoStatisVO>();
		}
		
		AttendanceRecordInfo ari = this.attendanceRecordMapper.getAttRecInfoByAttendanceId(attendanceId);
		if(null == page.getParams().get("checkInStartTime")) {
			attStartTime = ari.getAttStartTime();
		}
		else {
			attStartTime = (Date) page.getParams().get("checkInStartTime");
		}
		if(null == page.getParams().get("checkInEndTime")) {
			attEndTime = ari.getAttEndTime();
		}
		else {
			attEndTime = (Date) page.getParams().get("checkInEndTime");
		}
		attEndTime = attEndTime.getTime() <= new Date().getTime() ? attEndTime : new Date();
		
		page.getParams().put("checkInStartTime", attStartTime);
		page.getParams().put("checkInEndTime", attEndTime);
//		return this.attendanceRecordMapper.qryAttRecInfoStatisVOs(page);
		List<AttRecInfoStatisVO> list = this.attendanceRecordDAO.qryAttRecInfoStatisVOs(page,isPage); 
		return list;	
			
	}
	
	public List<HisAttRecordInfoVo> qryAttRecInfoStatis(Page<AttRecInfoStatisVO> page,int peroidCount, boolean isPage) {
		List<AttRecInfoStatisVO> attRecInfoList = this.qryAttRecInfoStatisVOs(page, isPage);
		List<HisAttRecordInfoVo> list = new ArrayList<HisAttRecordInfoVo>();
		AttRecInfoStatisVO obj = null;
		HisAttRecordInfoVo haiv = null;
		List<String[]> signTimeSlot = null;
		String[] attTimesArr = null;
		String[] signTimesArr = null;
		String userName = null;
		
		for(int i = 0,unchkSum = 0;i < attRecInfoList.size();i++) {
			obj = (AttRecInfoStatisVO) attRecInfoList.get(i);
			haiv = new HisAttRecordInfoVo();
			haiv.setAttDate(obj.getAttDate());
			haiv.setUserId(obj.getUserId());
			userName = this.sysUserDao.getUserNameByUserId(obj.getUserId());
			haiv.setUserName(userName);
			attTimesArr = obj.getAttTimes().split(";");
			signTimesArr = obj.getSignTimes().split(";");
			signTimeSlot = new ArrayList<String[]>();
			for(int j=0;j<attTimesArr.length;j++) {
				String[] arr = new String[2];
				arr[0] = signTimesArr[j];
				arr[1] = attTimesArr[j];
				signTimeSlot.add(arr);
				if("未签到".equals(attTimesArr[j])) {
					unchkSum++;
				}
				else {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm");
					try {
						arr[1] = new SimpleDateFormat("HH:mm").format(sdf.parse(attTimesArr[j]));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}
			haiv.setSignTimeSlot(signTimeSlot);
			haiv.setStatistics((peroidCount-unchkSum) + "/" + peroidCount);
			list.add(haiv);
			unchkSum = 0;
		}
		
		return list;
	}

	public int qryAttRecStatisTotalCount(Map<String, Object> qryParams) {
		Date attStartTime = null;
		Date attEndTime = null;
		Integer attendanceId = (Integer) qryParams.get("attPlanId"); 
		if(null == attendanceId) {
			return 0;
		}
		AttendanceRecordInfo ari = this.attendanceRecordMapper.getAttRecInfoByAttendanceId(attendanceId);
		
		if(null == qryParams.get("checkInStartTime")) {
			attStartTime = ari.getAttStartTime();
		}
		else {
			attStartTime = (Date) qryParams.get("checkInStartTime");
		}
		if(null == qryParams.get("checkInEndTime")) {
			attEndTime = ari.getAttEndTime();
		}
		else {
			attEndTime = (Date) qryParams.get("checkInEndTime");
		}
		attEndTime = attEndTime.getTime() <= new Date().getTime() ? attEndTime : new Date();
		
		qryParams.put("checkInStartTime", attStartTime);
		qryParams.put("checkInEndTime", attEndTime);
		qryParams.put("hasUncheckedIn", 0);
		Integer ret = this.attendanceRecordMapper.qryAttRecInfoStatisCount(qryParams);
		return ret;
	}

	public int qryAttRecStatisUncheckedCount(Map<String, Object> qryParams) {
		Date attStartTime = null;
		Date attEndTime = null;
		Integer attendanceId = (Integer) qryParams.get("attPlanId"); 
		if(null == attendanceId) {
			return 0;
		}
		AttendanceRecordInfo ari = this.attendanceRecordMapper.getAttRecInfoByAttendanceId(attendanceId);
		if(null == qryParams.get("checkInStartTime")) {
			attStartTime = ari.getAttStartTime();
		}
		else {
			attStartTime = (Date) qryParams.get("checkInStartTime");
		}
		if(null == qryParams.get("checkInEndTime")) {
			attEndTime = ari.getAttEndTime();
		}
		else {
			attEndTime = (Date) qryParams.get("checkInEndTime");
		}
		attEndTime = attEndTime.getTime() <= new Date().getTime() ? attEndTime : new Date();
		
		qryParams.put("checkInStartTime", attStartTime);
		qryParams.put("checkInEndTime", attEndTime);
		
		qryParams.put("hasUncheckedIn", 1);
		Integer ret = this.attendanceRecordMapper.qryAttRecInfoStatisCount(qryParams);
		return ret;
	}

	public boolean addAttendanceRecordInfo(AttendancePlanConf attendancePlanConf,
			List<AttendancePeriodConf> attendancePeriodConfs,List<AttendancePeopleConf> attendancePeopleConfs) {
		List<String> attDates = this.getAttDateList(attendancePlanConf.getAttStartTime(), attendancePlanConf.getAttEndTime());
		int ret = 0;
		List<AttendanceRecordInfo> list = new ArrayList<AttendanceRecordInfo>();
		AttendanceRecordInfo ari = null;
		AttendancePeriodConf apc = null;
		AttendancePeopleConf pepConf = null;
		for(int i = 0;i < attDates.size();i++) {
			for(int j = 0;j < attendancePeriodConfs.size();j++) {
				apc = attendancePeriodConfs.get(j);
				for(int k = 0;k < attendancePeopleConfs.size();k++) {
					pepConf = attendancePeopleConfs.get(k);
					ari = new AttendanceRecordInfo();
					ari.setAttPeopleId(pepConf.getId());
					ari.setAttPeriodId(apc.getAttPeriodId());
					ari.setAttSignDate(attDates.get(i));
					list.add(ari);
					ret = this.attendanceRecordMapper.insertAttendanceRecordInfo(ari);
				}
			}
		}
//		ret = this.attendanceRecordMapper.batchInsertAttRecInfo(list);
		
		if(ret > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 根据起止时间获取日期列表
	 * @param attStartTime
	 * @param attEndTime
	 * @return
	 */
	private List<String> getAttDateList(Date attStartTime, Date attEndTime) {
		List<String> attDates = new ArrayList<String>();
		if(null == attStartTime || null == attEndTime) {
			throw new IllegalArgumentException("attStartTime and attEndTime cannot be null");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			attStartTime = sdf.parse(sdf.format(attStartTime));
			attEndTime = sdf.parse(sdf.format(attEndTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String attDate = null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(attStartTime);
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(attEndTime);
		while(cal.getTimeInMillis()<=endCal.getTimeInMillis()) {
			attDate = sdf.format(cal.getTime());
			cal.add(Calendar.DAY_OF_MONTH, 1);
			attDates.add(attDate);
		}
		
		return attDates;
	}
	
	public List<AttendancePeriodConf> qryAttPeriodConfListByPlanConfId(
			Integer attendanceId) {
		List<AttendancePeriodConf> list = this.attendanceRecordMapper.qryAttPeriodConfListByPlanConfId(attendanceId);
		return list;
	}

	public List<AttendancePeopleConf> qryAttPeopleConfListByPlanConfId(
			Integer attendanceId) {
		List<AttendancePeopleConf> list = this.attendanceRecordMapper.qryAttPeopleConfListByPlanConfId(attendanceId);
		return list;
	}

	public boolean detachDeperatedAttRecInfo(AttendancePlanConf planConf,
			List<AttendancePeriodConf> periodConfs,
			List<AttendancePeopleConf> pepConfs) {
		int ret = 0;
		if(null == planConf.getStopTime() || null == planConf.getAttEndTime()) {
			throw new IllegalArgumentException("stop time and attEndTime cannot be null");
		}
		// 停用当天的签到计划不保留
		List<String> attDates = this.getAttDateList(planConf.getStopTime(), planConf.getAttEndTime());
		List<Integer> attPeopleIds = new ArrayList<Integer>();
		List<Integer> attPerIds = new ArrayList<Integer>();
		Iterator it = null;
		for(it = pepConfs.iterator();it.hasNext();) {
			attPeopleIds.add(((AttendancePeopleConf)it.next()).getId());
		}
		for(it = periodConfs.iterator();it.hasNext();) {
			attPerIds.add(((AttendancePeriodConf)it.next()).getAttPeriodId());
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("attPepConfIds", attPeopleIds);
		params.put("attPerConfIds", attPerIds);
		params.put("attDates", attDates);
		ret = this.attendanceRecordMapper.batchDelAttRecInfo(params);
		if(ret > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<SysUserInfoBean> qryPlanPeopleListByPlanId(Integer attPlanId) {
		List<SysUserInfoBean> users = this.attendanceRecordMapper.qryPlanPeopleListByPlanId(attPlanId);
		return users;
	}

	public List<AttendancePlanConfVO> qryAllAttPlanConfList() {
		List<AttendancePlanConfVO> list= this.attendanceRecordMapper.qryAllAttPlanConfList();
		return list;
	}

	
}
