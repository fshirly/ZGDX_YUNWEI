package com.fable.insightview.platform.dutymanager.duty.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.dutymanager.duty.entity.DutyChangeRecord;
import com.fable.insightview.platform.dutymanager.duty.entity.PfDuty;
import com.fable.insightview.platform.dutymanager.duty.mapper.DutyChangeMapper;
import com.fable.insightview.platform.dutymanager.duty.service.DutyChangeService;

@Service
public class DutyChangeServiceImpl implements DutyChangeService {
	
	@Autowired
	private DutyChangeMapper dutyChangeMapper;

	@Override
	public String findLeaderNameByDutyDate(String dutyDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(dutyDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		PfDuty duty = dutyChangeMapper.selectDutyByDate(date);
		if(duty == null || duty.getLeaderId() == null) {
			return "";
		}
		return dutyChangeMapper.selectUsernameByUserId(duty.getLeaderId());
	}

	@Override
	public List<Integer> findDutyersByDutyDate(String dutyDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(dutyDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		PfDuty duty = dutyChangeMapper.selectDutyByDate(date);
		if(duty == null) {
			return new ArrayList<Integer>();
		}
		return dutyChangeMapper.selectUserIdsByDutyId(duty.getId());
	}

	@Override
	public String findUsernameByUserId(Integer userId) {
		return dutyChangeMapper.selectUsernameByUserId(userId);
	}

	@Override
	public List<Map<String, Object>> findAllDutyers(Integer userId) {
		return dutyChangeMapper.selectAllDutyers(userId);
	}

	@Override
	public List<DutyChangeRecord> findDutyChangeRecordsByDutyDate(String dutyDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(dutyDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dutyChangeMapper.selectDutyChangeRecordsByDutyDate(date);
	}

	@Override
	public PfDuty findDutyByDate(String dutyDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(dutyDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		PfDuty duty = dutyChangeMapper.selectDutyByDate(date);
		return duty;
	}

	@Override
	public void addDutyChangeRecord(DutyChangeRecord dutyChangeRecord) {
		dutyChangeMapper.insertDutyChangeRecord(dutyChangeRecord);
	}

	@Override
	public void changeDuty(String dutyDate, String fromUser, String toUser) {
		Integer fromUserId = Integer.parseInt(fromUser);
		Integer toUserId = Integer.parseInt(toUser);
		PfDuty duty = this.findDutyByDate(dutyDate);
		Integer dutyId = duty.getId();
		Integer leaderId = dutyChangeMapper.selectDutyById(dutyId).getLeaderId();
		//根据值班日期获取所有值班人员的id
		List<Integer> ids = this.findDutyersByDutyDate(dutyDate);
		//判断调班的人是否既是带班领导又是值班人
		if(fromUserId.equals(leaderId) && ids.contains(fromUserId)) {
			dutyChangeMapper.updateDutyLeader(dutyId, toUserId);
			dutyChangeMapper.updateDutyUser(dutyId, fromUserId, toUserId);
			return;
		}
		//判断调班的人是带班领导调班还是值班人调班
		if(fromUserId.equals(leaderId)) {
			dutyChangeMapper.updateDutyLeader(dutyId, toUserId);
		} else {
			dutyChangeMapper.updateDutyUser(dutyId, fromUserId, toUserId);
		}
	}

}
