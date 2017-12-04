package com.fable.insightview.monitor.alarmdispatcher.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fable.insightview.monitor.alarmdispatcher.eneity.ConvertObj; 
import com.fable.insightview.monitor.alarmdispatcher.eneity.Tag;
//import com.fable.insightview.monitor.dispatcher.utils.JsonUtils;
import com.fable.insightview.monitor.entity.AlarmNode; 

/**
 * 告警对象转换
 */
public class AlarmObjToJSON {
	
	public static Logger logger = LoggerFactory
	.getLogger(AlarmObjToJSON.class);
	
	@SuppressWarnings("unchecked")
	public static String writeMapJSON(AlarmNode alarmNode) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			ConvertObj joinsObj = new ConvertObj();
			map.put("v", "2.9");
			joinsObj.setC("ht.Data");
			joinsObj.setI(alarmNode.getAlarmID());
			joinsObj.setA(alarmNode);
			Tag tag = new Tag ();
			tag.setTag(String.valueOf(alarmNode.getAlarmID()));
			joinsObj.setP(tag);
			List total = new ArrayList();
			total.add(joinsObj);
			map.put("d", total);
			//logger.debug(JsonUtils.ObjectToJsonString(map)); 
			return ObjectToJsonString(map);
		} catch (Exception e) {
			logger.error("AlarmObjToJSON",e);
			e.printStackTrace();
		}
		return "";
	}
	
	@SuppressWarnings("unchecked")
	public static String write3dMapJSON(AlarmNode alarmNode,int resId) {
		try {
			Map<String, Object> map = new HashMap<String, Object>(); 
			map = new HashMap<String, Object>();
			map.put("ciId", resId);
			map.put("alarmLevel", alarmNode.getAlarmLevel());
			map.put("alarmID", alarmNode.getAlarmID());
			map.put("alarmStatus", alarmNode.getAlarmStatus());
			map.put("alarmOperateStatus", alarmNode.getAlarmOperateStatus());
			map.put("lastTime", alarmNode.getLastTime());
			logger.debug(ObjectToJsonString(map)); 
			return ObjectToJsonString(map);
		} catch (Exception e) {
			logger.error("AlarmObjToJSON",e);
			e.printStackTrace();
		}
		return "";
	}
	
	@SuppressWarnings("unchecked")
	public static String writeTopoMapJSON(AlarmNode alarmNode) {
		try {
			Map<String, Object> map = new HashMap<String, Object>(); 
			map = new HashMap<String, Object>(); 
			map.put("alarmLevel", alarmNode.getAlarmLevel());
			map.put("alarmID", alarmNode.getAlarmID());
			map.put("alarmStatus", alarmNode.getAlarmStatus());
			map.put("alarmOperateStatus", alarmNode.getAlarmOperateStatus());
			map.put("alarmOID",alarmNode.getAlarmOID());
			map.put("moid", alarmNode.getMoid());
			map.put("sourceMOID", alarmNode.getSourceMOID());
			map.put("lastTime", alarmNode.getLastTime());
			logger.debug(ObjectToJsonString(map)); 
			return ObjectToJsonString(map);
		} catch (Exception e) {
			logger.error("AlarmObjToJSON",e);
			e.printStackTrace();
		}
		return "";
	}
	
	public static String ObjectToJsonString(Object o){
		if(o != null){
			ObjectMapper mapper = new ObjectMapper(); 
			try { 
//			    /* We want dates to be treated as ISO8601 not timestamps. */ 
//				mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false); 
  				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 				extracted(mapper, formatter); 
				//mapper.
				return mapper.writeValueAsString(o);
			} catch (Exception e) {
				logger.error("对象转json失败！", e);
			}
		}
		return null;
	}
	
 	@SuppressWarnings("deprecation")
 	private static void extracted(ObjectMapper mapper,SimpleDateFormat formatter) {
 		mapper.setDateFormat(formatter);
 	}
 }