package com.fable.insightview.platform.dutymanager.duty.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.dutymanager.duty.service.IDutyForPublish;
import com.fable.insightview.platform.dutymanager.duty.service.IDutyService;
import com.fable.insightview.platform.sysinit.SystemParamInit;

@Service
public class DutyForPublishImpl implements IDutyForPublish {

	@Autowired
	private IDutyService dutyService;

	@Override
	public Map<String, Object> getDutying(Date dutyDate) {
		return dutyService.queryPointInfo(dutyDate);
	}

	@Override
	public Map<String, Object> getTomorrowDutying(Date dutyDate) {
		Map<String, Object> currentDuty = this.getDutying(dutyDate);
		if (null == currentDuty || currentDuty.isEmpty()) {
			dutyDate = getBeforeOrAfter(dutyDate, 1);
			List<Map<String, Object>> dutyers = dutyService.betweenDuty(dutyDate, dutyDate, 1);
			if (null != dutyers && dutyers.isEmpty()) {
				currentDuty = dutyers.get(0);
			}
		}
		return currentDuty;
	}

	@Override
	public List<Map<String, Object>> getDuties(Map<String, Object> params) throws Exception{
		List<Map<String, Object>> duties = dutyService.queryMulti(params);
		Object current = params.get("current");
		if (null != duties && !duties.isEmpty() && null != current) {/*获取当前时间的值班人*/
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Map<String, Object> currentDuty;
			currentDuty = this.getDutying(df.parse(String.valueOf(current)));
			if (null != currentDuty && !currentDuty.isEmpty()) {
				for (Map<String, Object> duty : duties) {
					if (String.valueOf(duty.get("ID")).equals(String.valueOf(currentDuty.get("ID")))) {
						duty.put("dutyIcon", currentDuty.get("dutyIcon"));
						duty.put("dutyName", currentDuty.get("dutyName"));
					}
				}
			}
		}
		return duties;
	}

	@Override
	public Map<String, Object> getPublishDuty(Date dutyDate) {
		return packInfo(dutyDate);
	}

	/* 获取当前值班信息及明天值班信息 */
	private Map<String, Object> packInfo(Date dutyDate) {
		Map<String, Object> dutyInfo = new HashMap<String, Object>();
		Map<String, Object> currentDuty = this.getDutying(dutyDate);
		List<Map<String, Object>> nextDuties = dutyService.findAllDutiesAfterDate(dutyDate);
		Map<String, Object> nextDuty = new HashMap<String, Object>();
		if(!nextDuties.isEmpty()) {
			for(Map<String, Object> duty : nextDuties) {
				String dutyName = String.valueOf(duty.get("dutyName"));
				if(currentDuty == null || currentDuty.isEmpty()) {
					nextDuty = duty;
					break;
				} else {
					String currentDutyName = String.valueOf(currentDuty.get("dutyName"));
					if(!dutyName.equals(currentDutyName)) {
						nextDuty = duty;
						break;
					}
				}
			}
		}
		List<Map<String, Object>> dutyers = null;
		String file = SystemParamInit.getValueByKey("fileServerURL");
		if (null == currentDuty || currentDuty.isEmpty()) {
			Date currentDate = getBeforeOrAfter(dutyDate, 0);
			dutyDate = getBeforeOrAfter(dutyDate, 1);
			List<Map<String, Object>> currentDutyers = dutyService.betweenDuty(currentDate, currentDate, 1);
			currentDuty = new HashMap<String, Object>();
			if (null != currentDutyers && !currentDutyers.isEmpty()) {
				Map<String, Object> dutying = currentDutyers.get(0);
				if (dutying.containsKey("leaderName")) {
					currentDuty.put("leaderName", dutying.get("leaderName"));
				}
				if (dutying.containsKey("leaderIcon")) {
					currentDuty.put("leaderIcon", dutying.get("leaderIcon"));
				}
			}
		} else {
			dutyDate = getBeforeOrAfter((Date) currentDuty.get("DutyDate"), 1);
		}
		dutyers = dutyService.betweenDuty(dutyDate, dutyDate, 1);/* 明天值班信息 */
		if (null != dutyers && !dutyers.isEmpty()) {
			dutyInfo.put("Tomorrow", setFilePath(dutyers.get(0), file));
		}
		dutyInfo.put("Current", setFilePath(currentDuty, file));
		dutyInfo.put("Next", setFilePath(nextDuty, file));
		return dutyInfo;
	}

	/* 设置文件路径 */
	private Map<String, Object> setFilePath(Map<String, Object> duty, String server) {
		if (duty.containsKey("leaderIcon")) {
			duty.put("leaderIcon", server + duty.get("leaderIcon"));
		}
		if (duty.containsKey("dutyIcon")) {
			duty.put("dutyIcon", server + duty.get("dutyIcon"));
		}
		return duty;
	}

	/* 获取特定时间相隔几天日期 */
	private Date getBeforeOrAfter(Date pointTime, int intervalDays) {
		if (null == pointTime) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(pointTime);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) + intervalDays);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

}
