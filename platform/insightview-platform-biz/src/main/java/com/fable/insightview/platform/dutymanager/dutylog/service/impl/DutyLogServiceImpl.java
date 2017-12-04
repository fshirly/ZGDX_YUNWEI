package com.fable.insightview.platform.dutymanager.dutylog.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.dutymanager.duty.mapper.DutyMapper;
import com.fable.insightview.platform.dutymanager.duty.mapper.OrdersDutyMapper;
import com.fable.insightview.platform.dutymanager.duty.mapper.UsersOrderMapper;
import com.fable.insightview.platform.dutymanager.dutylog.entity.PfDutyLog;
import com.fable.insightview.platform.dutymanager.dutylog.mapper.DutyLogMapper;
import com.fable.insightview.platform.dutymanager.dutylog.service.IDutyLogService;
import com.fable.insightview.platform.sysinit.SystemParamInit;

@Service
public class DutyLogServiceImpl implements IDutyLogService {

	@Autowired
	private DutyMapper dutyMapper;
	@Autowired
	private DutyLogMapper dutyLogMapper;
	@Autowired
	private OrdersDutyMapper ordersDutyMpper;
	@Autowired
	private UsersOrderMapper userOrderMapper;

	@Override
	public List<Map<String, Object>> queryDutyLogs(Map<String, Object> params) {
		List<Map<String, Object>> dutys = dutyMapper.queryMulti(params);
		if (dutys.isEmpty()) {
			return new ArrayList<Map<String, Object>>();
		}
		String currentUserId = params.get("currentUser").toString();
		List<Integer> dutyIds = new ArrayList<Integer>();
		String file = SystemParamInit.getValueByKey("fileServerURL");
		for (Map<String, Object> duty : dutys) {
			dutyIds.add(Integer.parseInt(duty.get("ID") + ""));
			if (null != duty.get("LeaderId") && currentUserId.equals(duty.get("LeaderId").toString())) {
				duty.put("checkIn", true);
			}
		}
		List<Map<String, Object>> orders = ordersDutyMpper.query(dutyIds);// 值班中的所有值班班次
		List<Map<String, Object>> users = userOrderMapper.query(dutyIds);// 值班中的所有值班人员
		List<Map<String, Object>> logs = dutyLogMapper.queryLogs(dutyIds);//查询所有日志信息
		List<Map<String, Object>> userNames = null;// 一个班次中的值班人员
		Map<String, Object> orderInfo = null;// 一个值班中的班次信息
		String orderId = null, dutyId;
		for (Map<String, Object> duty : dutys) {
			duty.put("fileUrl", file);
			dutyId = duty.get("ID").toString();
			for (Map<String, Object> order : orders) {
				if (order.get("DutyId").toString().equals(dutyId)) {
					orderInfo = (Map<String, Object>) duty.get("order");
					if (null == orderInfo) {
						orderInfo = new HashMap<String, Object>();
					} else {
						break;
					}
					userNames = new ArrayList<Map<String, Object>>();
					orderId = order.get("ID").toString();
					for (Map<String, Object> user : users) {
						if (orderId.equals(user.get("OrderIdOfDuty").toString())) {
							userNames.add(user);
						}
						if (currentUserId.equals(user.get("UserId").toString()) && "1".equals(user.get("UserType").toString())) {
							user.put("checkIn", true);
						}
					}
					order.put("userNames", userNames);
					orderInfo.putAll(order);
					duty.put("order", orderInfo);
				}
			}
			for (Map<String, Object> log : logs) {
				if (dutyId.equals(log.get("DutyId").toString())) {
					duty.putAll(log);
					break;
				}
			}
		}
		return dutys;
	}

	@Override
	public Map<String, Object> queryLog(Date duty) {
		return dutyLogMapper.queryLog(duty);
	}

	@Override
	public Map<String, Object> queryLeaderInfo(Map<String, Object> params) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> duty = queryLog(sdf.parse(params.get("duty").toString()));
		Map<String, Object> before = queryLog(sdf.parse(params.get("before").toString()));
		if (null != before && !before.isEmpty()) {
			duty.put("before", before.get("Advices"));
		}
		if (null == duty.get("LeaderRegisterDate")) {
			sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
			duty.put("LeaderRegisterDate",sdf.format(new Date()));
		}
		if (null == duty.get("Title")) {
			String title = params.get("duty").toString();
			title = title.replaceFirst("-", "年").replaceFirst("-", "月");
			duty.put("Title", title + "日 值班日志");
		}
		return duty;
	}

	@Override
	public void insert(PfDutyLog log) {
		dutyLogMapper.insert(log);
	}

	@Override
	public void updateLeaderLog(PfDutyLog log) {
		dutyLogMapper.updateLeaderLog(log);
	}

	@Override
	public void updateDutyerLog(PfDutyLog log) {
		dutyLogMapper.updateDuyterLog(log);
	}

	@Override
	public void handle(PfDutyLog log) {
		if (log.getId() == 0) {/*新增值班日志*/
			insert(log);
		} else if (null == log.getDutyerRegisterDate()){/*更新带班领导日志*/
			updateLeaderLog(log);
		} else {/*更新值班负责人日志*/
			updateDutyerLog(log);
		}
	}

	@Override
	public Map<String, Object> queryDutyerInfo(Map<String, Object> params) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> duty = queryLog(sdf.parse(params.get("duty").toString()));
		Map<String, Object> before = queryLog(sdf.parse(params.get("before").toString()));
		if (null != before && !before.isEmpty()) {
			duty.put("before", before.get("Advices"));
		}
		if (null == duty.get("DutyerRegisterDate")) {
			sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
			duty.put("DutyerRegisterDate",sdf.format(new Date()));
		}
		if (null == duty.get("Title")) {
			String title = params.get("duty").toString();
			title = title.replaceFirst("-", "年").replaceFirst("-", "月");
			duty.put("Title", title + "日 值班日志");
		}
		return duty;
	}
	
	@Override
	public void updateLeadNotice(String dutyId, String notices) {
		List<Map<String, Object>> dutyLogs = dutyLogMapper.queryDutyLogs(dutyId);
		if (null == dutyLogs || dutyLogs.isEmpty()) {
			Map<String, Object> duty = dutyMapper.query(Integer.parseInt(dutyId));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			PfDutyLog pLog = new PfDutyLog();
			pLog.setTitle(sdf.format(duty.get("DutyDate")) + " 值班日志");
			pLog.setNoticePoints(notices);
			pLog.setDutyId(Integer.parseInt(dutyId));
			pLog.setLeaderRegisterDate(new Date());
			dutyLogMapper.insert(pLog);
		} else {
			dutyLogMapper.updateLeadNotice(dutyId, notices);
		}
	}

	@Override
	public void updateDutyLogs(Map<String, Object> logInfo) {
		String dutyId = logInfo.get("dutyId") + "";
		String orderId = logInfo.get("orderId") + "";
		String userId = logInfo.get("userId") + "";
		int isPri = userOrderMapper.queryPrincle(orderId, userId);
		if (isPri == 0) {/*不是值班负责人*/
			this.updateOrderStatus(logInfo);
			return;
		}
		List<Map<String, Object>> dutyLogs = dutyLogMapper.queryDutyLogs(dutyId);
		if (null == dutyLogs || dutyLogs.isEmpty()) {
			Map<String, Object> duty = dutyMapper.query(Integer.parseInt(dutyId));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			PfDutyLog pLog = new PfDutyLog();
			pLog.setTitle(sdf.format(duty.get("DutyDate")) + " 值班日志");
			pLog.setDutyId(Integer.parseInt(dutyId));
			pLog.setDutyerRegisterDate(new Date());
			pLog.setDutyLog(logInfo.get("dutyLog")==null?"":logInfo.get("dutyLog")+"");
			pLog.setAdvices(logInfo.get("advices")==null?"":logInfo.get("advices")+"");
			dutyLogMapper.insert(pLog);
		} else {
			logInfo.put("dutyerRegisterDate", new Date());
			dutyLogMapper.updateDutyLogs(logInfo);
		}
		this.updateOrderStatus(logInfo);
	}
	
	private void updateOrderStatus (Map<String, Object> logInfo) {
		String orderId = logInfo.get("orderId") + "";
		double exists = Double.parseDouble(logInfo.get("exists") + "");
		int users = userOrderMapper.queryUsers(orderId);
		if (users <= exists) {
			ordersDutyMpper.updateStatus(orderId, logInfo.get("status") + "");
		}
	}
}
