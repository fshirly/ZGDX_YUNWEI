package com.fable.insightview.monitor.alarmdispatcher.distribute;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.fable.insightview.monitor.alarmdispatcher.eneity.CustomAlarmNode;
import com.fable.insightview.monitor.alarmdispatcher.eneity.DistributionAlarmObj;
import com.fable.insightview.monitor.alarmdispatcher.utils.AlarmObjToJSON;
import com.fable.insightview.monitor.alarmdispatcher.utils.InvokeRestInterface;
import com.fable.insightview.monitor.alarmmgr.alarmactive.mapper.AlarmActiveMapper;
import com.fable.insightview.monitor.alarmmgr.alarmdispatch.mapper.AlarmDispatchMapper;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmDispatchDetail;
import com.fable.insightview.monitor.entity.AlarmNode;
import com.fable.insightview.platform.core.util.BeanLoader;

/**
 * 通过Rest接口将告警信息组织JSON发送
 * 如果发送错误将记录,定时组织重发
 */
public class AlarmDispatchThread implements Runnable {

	public static Logger logger = LoggerFactory
			.getLogger(AlarmDispatchThread.class);

	private int defaultCount = 1;
	
	AlarmNode alarm;

	public AlarmDispatchThread(AlarmNode alarm) {
		this.alarm = alarm;
	}
	
	AlarmDispatchMapper alarmDispatchServic = (AlarmDispatchMapper) BeanLoader
			.getBean("alarmDispatchMapper");
	
	AlarmActiveMapper alarmActiveServic = (AlarmActiveMapper) BeanLoader
	.getBean("alarmActiveMapper");

	@Override
	public void run() {
		
		int alarmID = alarm.getAlarmID();
		
		logger.info("告警信息派发至CMDB! alarmID=" + alarmID
					+ " alarmOperate=" + alarm.getAlarmOperateStatus());
		AlarmNode node = alarmActiveServic.isExistDispatchRecord(alarmID);
		if (node != null && node.getDispatchID() != null && !node.getDispatchID().equals("")) {
			logger.info("此告警已经派发过,不再进行派发,AlarmID="+alarmID);
			return;
		}
		Date date = new Date();
		Timestamp dispatchTime = new Timestamp(date.getTime());
		
		DistributionAlarmObj jsonObj = new DistributionAlarmObj();
		jsonObj.setTransferor("inner"); 
		jsonObj.setTransferTime(dispatchTime);
//		jsonObj.setOpType(alarm.getAlarmOperate());
		jsonObj.setFirstTransferTime(dispatchTime);
		jsonObj.setCurrentCount(defaultCount);
		
		Random random = new Random();  
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");    
		Date curDate = new Date(System.currentTimeMillis());//获取当前时间      
		String dispatchID =  formatter.format(curDate) +String.valueOf(random.nextInt(10000));  
				
		CustomAlarmNode content = new CustomAlarmNode();
		content.setAlarmDescription(alarm.getAlarmContent());
		content.setAlarmID(alarmID);
		content.setAlarmLevel(alarm.getAlarmLevel());
		content.setAlarmLevelName(alarm.getAlarmLevelName());
		content.setAlarmStatus(alarm.getAlarmStatus());
		content.setAlarmOperateStatus(alarm.getAlarmOperateStatus());
		content.setStatusName(alarm.getStatusName());
		content.setOperateStatusName(alarm.getOperateStatusName());
		//新传入3个参数，告警匹配使用
		content.setLastTime(alarm.getLastTime());
		content.setAlarmDefineID(alarm.getAlarmDefineID());
		content.setmOName(alarm.getMoName());
		
		content.setAlarmTitle(alarm.getAlarmTitle());
		
		content.setMoClassID(alarm.getMoclassID());
		content.setMoClassName(alarm.getMoClassName());
		if(alarm.getMoClassName()==null){
			content.setMoClassName("");
		}
		content.setSourceMoClassID(alarm.getSourceMOClassID());
		content.setSourceMOIpAdress(alarm.getSourceMOIPAddress());
		content.setSourceMOName(alarm.getSourceMOName());
		content.setStartTime(alarm.getStartTime());
		content.setRepeatCount(alarm.getRepeatCount());
//		content.setWorkOrderNum("");		
		jsonObj.setContent(content); 
		jsonObj.setDispatchID(dispatchID);
		
		JSONObject objJson = JSONObject.parseObject(AlarmObjToJSON.ObjectToJsonString(jsonObj)); 
		AlarmDispatchDetail detail =  InvokeRestInterface.invokeParty(objJson);
		if(detail != null){ 
			detail.setAlarmID(alarmID);
			detail.setDispatchID(dispatchID);
			alarm.setDispatchID(dispatchID);
			detail.setDispatchType(1);
			// query isExist
//			Integer sendCount = 0;
//			try{
//				sendCount = alarmDispatchServic.isExistDispatchRecord(detail);
//			}catch(Exception e){
//				logger.error("---",e);
//			}
//			
//			if (sendCount!=null && sendCount > 0) {
//				
//				logger.info("查询告警派发情况,之前已经派发过,告警ID="+alarmID+" sendCount="+sendCount);
//				detail.setResendTime(dispatchTime);
//				detail.setSendCount(sendCount + 1);
//				alarmDispatchServic.updateAlarmDispatchRecord(detail);
//			} else {
//				logger.info("查询告警派发情况,之前未派发过,告警ID="+alarmID+" sendCount="+sendCount);
				
				detail.setSendCount(defaultCount);
				detail.setDispatchTime(dispatchTime);
				detail.setDispatchJson(objJson.toJSONString());
				alarmDispatchServic.insertAlarmDispatchRecord(detail);
//			}
			
			Map param = new HashMap();
			param.put("alarmOperateStatus", 23);
			param.put("dispatchID", dispatchID);
			param.put("ids", alarmID);
			param.put("dispatchUser", "inner");
			param.put("dispatchTime", dispatchTime);
			alarmActiveServic.updateAlarmActiveDetail(param); 
		}
	}
}