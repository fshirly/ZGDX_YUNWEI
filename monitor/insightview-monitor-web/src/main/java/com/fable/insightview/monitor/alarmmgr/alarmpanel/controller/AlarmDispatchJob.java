package com.fable.insightview.monitor.alarmmgr.alarmpanel.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.fable.insightview.monitor.alarmdispatcher.utils.InvokeRestInterface;
import com.fable.insightview.monitor.alarmmgr.alarmdispatch.service.IAlarmDispatchService;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmDispatchDetail;

@Controller
public class AlarmDispatchJob {

	private static final long serialVersionUID = 1L;

	@Autowired
	IAlarmDispatchService alarmDispatchServic;

	private static final Logger logger = LoggerFactory
			.getLogger(AlarmDispatchJob.class);

	public void execute() {
		logger.debug("开始定时发送CMDB失败工单!");
		try {
			List<AlarmDispatchDetail> alarmArry = alarmDispatchServic.batchSendFailed();
			for (AlarmDispatchDetail alarm : alarmArry) {
				Date date = new Date();
				Timestamp dispatchTime = new Timestamp(date.getTime());
				Map<String, Object> tmpMap = new HashMap<String, Object>();
				
				// update property
				JSONObject jsonObj = JSONObject.parseObject(alarm.getDispatchJson()); 
				int old_Count = Integer.parseInt(jsonObj.getJSONObject("currentCount").toString());  
				tmpMap.put("currentCount", old_Count + 1);
				tmpMap.put("transferTime", dispatchTime);
				jsonObj.putAll(tmpMap);
				
				AlarmDispatchDetail detail = null;
				// 手工派发
				if(2 == alarm.getDispatchType()){
					detail = InvokeRestInterface.invokeParty2(jsonObj); 
				} else {
					detail = InvokeRestInterface.invokeParty(jsonObj); 
				} 
				detail.setDispatchID(alarm.getDispatchID());
				
				// query isExist
				Integer sendCount = alarmDispatchServic.isExistDispatchRecord(detail);
				if (sendCount > 1) {
					detail.setResendTime(dispatchTime);
					detail.setSendCount(sendCount + 1);
					alarmDispatchServic.updateAlarmDispatchRecord(detail);
				}
			}
		} catch (Exception e) {
			logger.error("定时发送CMDB失败工单错误:", e);
		}
		logger.debug("本次定时发送CMDB失败工单结束!");
	}
}