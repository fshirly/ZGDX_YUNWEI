package com.fable.insightview.monitor.alarmmgr.alarmpanel.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.fable.insightview.monitor.alarmmgr.alarmactive.controller.AlarmActiveController;
import com.fable.insightview.monitor.alarmmgr.alarmactive.mapper.AlarmActiveMapper;
import com.fable.insightview.monitor.alarmmgr.alarmdispatch.service.IAlarmDispatchService;
import com.fable.insightview.monitor.alarmmgr.alarmhistory.mapper.AlarmHistoryMapper;
import com.fable.insightview.monitor.alarmmgr.alarmlevel.entity.AlarmLevelBean;
import com.fable.insightview.monitor.alarmmgr.alarmlevel.mapper.AlarmLevelMapper;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmDispatchDetail;
import com.fable.insightview.monitor.alarmmgr.entity.MOActiveAlarmStInfo;
import com.fable.insightview.monitor.discover.mapper.MODeviceMapper;
import com.fable.insightview.monitor.entity.AlarmNode;
import com.fable.insightview.monitor.environmentmonitor.service.IEnvMonitorService;
import com.fable.insightview.monitor.mobject.entity.MObjectDefBean;
import com.fable.insightview.monitor.mobject.entity.MObjectRelationBean;
import com.fable.insightview.monitor.mobject.mapper.MobjectInfoMapper;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.service.ISysUserService;

@Controller
@RequestMapping("/rest/monitor/alarm")
public class AlarmRestInterfaceController {
	
	@Autowired
	AlarmActiveMapper alarmActiveMapper;
	
	@Autowired
	private AlarmHistoryMapper alarmHistoryMapper;
	
	@Autowired
	MODeviceMapper moDeviceMapper;
	
	@Autowired
	ISysUserService sysUserService;
	
	@Autowired
	MobjectInfoMapper mobjectInfoMapper;
	
	@Autowired
	IEnvMonitorService envMonitorService;
	
	@Autowired
	IAlarmDispatchService alarmDispatchService;
	
	@Autowired
	AlarmLevelMapper alarmLevelMapper;
	
	private final static Logger logger = LoggerFactory.getLogger(AlarmActiveController.class);
	
	/**
	 * 3d机房加载告警查询
	 * @param ciIds 设备的ID
	 * @param request 请求对象
	 * @return 返回告警数据
	 */
	@RequestMapping(value = "/alarmQueryBy3dRoom", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	List<Map<String, Object>> alarmQueryBy3dRoom(@RequestBody String ciIds,
			HttpServletRequest request) {
		try {
			logger.info("3d机房,加载部分活动告警 ciIds:"+ciIds);
			
			if (ciIds.indexOf("[") > -1 && ciIds.indexOf("]") > -1) {
				ciIds = ciIds.replace("[", "");
				ciIds = ciIds.replace("]", "");
			}
			
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Map<String, Object> map = new HashMap<String, Object>();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("resid", ciIds);
			List<AlarmNode> alarmList = alarmActiveMapper.load3dRoomActiveAlarm(params);
			for (AlarmNode alarm : alarmList) {
				map = new HashMap<String, Object>();
				map.put("ciId", alarm.getMoid());  
				map.put("alarmStatus", alarm.getAlarmStatus());
				map.put("alarmOperateStatus", alarm.getAlarmOperateStatus());
				map.put("alarmLevel", alarm.getAlarmLevel());
				map.put("alarmID", alarm.getAlarmID());  
				if (alarm.getLastTime() == null) {
					map.put("lastTime",formatter.format(alarm.getStartTime())); 
				} else {
					map.put("lastTime",formatter.format(alarm.getLastTime())); 
				}
				
				list.add(map);
			}
			 
			List<AlarmNode> alarmTagList = alarmActiveMapper.load3dRoomTagActiveAlarm(params);   
			for (AlarmNode alarm : alarmTagList) {
				map = new HashMap<String, Object>();
				map.put("ciId", alarm.getMoid());  
				map.put("alarmStatus", alarm.getAlarmStatus());
				map.put("alarmOperateStatus", alarm.getAlarmOperateStatus());
				map.put("alarmLevel", alarm.getAlarmLevel());
				map.put("alarmID", alarm.getAlarmID()); 
				if (alarm.getLastTime() == null) {
					map.put("lastTime",formatter.format(alarm.getStartTime())); 
				} else {
					map.put("lastTime",formatter.format(alarm.getLastTime())); 
				} 
				list.add(map);
			}
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Topo加载告警查询
	 * @param moIds 设备的ID
	 * @param request 请求对象
	 * @return 返回告警数据
	 */
	@RequestMapping(value = "/alarmQueryByTopo", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	List<Map<String, Object>> alarmQueryByTopo(@RequestBody String moIds,
			HttpServletRequest request) {
		try {
			logger.info("Topo加载部分活动告警 moIds:"+moIds);
			
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(); 
			Map<String, Object> map = new HashMap<String, Object>();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("moIds", moIds);
			List<AlarmNode> alarmList = alarmActiveMapper.loadTopoActiveAlarm(params);
			
			for (AlarmNode alarm : alarmList) {
				map = new HashMap<String, Object>();
				map.put("moid", alarm.getMoid());
				map.put("sourceMOID", alarm.getSourceMOID());
				map.put("alarmStatus", alarm.getAlarmStatus());
				map.put("alarmLevel", alarm.getAlarmLevel());
				map.put("alarmID", alarm.getAlarmID()); 
				map.put("alarmOID", alarm.getAlarmOID()); 
				map.put("alarmOperateStatus", alarm.getOperateStatusName());
				map.put("sourceMOClassID", alarm.getSourceMOClassID());
				if (alarm.getLastTime() == null) {
					map.put("lastTime",formatter.format(alarm.getStartTime())); 
				} else {
					map.put("lastTime",formatter.format(alarm.getLastTime())); 
				}
				list.add(map);
			}
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Topo加载链路告警查询
	 * @param moIds 设备的ID
	 * @param request 请求对象
	 * @return 返回告警数据
	 */
	@RequestMapping(value = "/linkAlarmQueryByTopo", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	List<Map<String, Object>> linkAlarmQueryByTopo(@RequestBody String moIds,
			HttpServletRequest request) {
		try {
			logger.info("Topo加载Link活动告警 moIds:"+moIds);
			
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(); 
			Map<String, Object> map = new HashMap<String, Object>();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("moIds", moIds);
			List<AlarmNode> alarmList = alarmActiveMapper.loadTopoActiveLinkAlarm(params);
			
			for (AlarmNode alarm : alarmList) {
				map = new HashMap<String, Object>();
				map.put("moid", alarm.getMoid());
				map.put("sourceMOID", alarm.getSourceMOID());
				map.put("alarmStatus", alarm.getAlarmStatus());
				map.put("alarmLevel", alarm.getAlarmLevel());
				map.put("alarmID", alarm.getAlarmID()); 
				map.put("alarmOID", alarm.getAlarmOID()); 
				map.put("alarmOperateStatus", alarm.getOperateStatusName());
				if (alarm.getLastTime() == null) {
					map.put("lastTime",formatter.format(alarm.getStartTime())); 
				} else {
					map.put("lastTime",formatter.format(alarm.getLastTime())); 
				}
				list.add(map);
			}
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 3d机房加载告警详情
	 * @param alarmIDs 告警号
	 * @param request 请求对象
	 * @return 返回告警数据
	 */
	@RequestMapping(value = "/alarmDetailBy3dRoom", method = RequestMethod.POST,
			 headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	public @ResponseBody
	Map<String, Object> alarmDetailBy3dRoom(@RequestBody String alarmIDs,HttpServletRequest request) {
		try {
			if (alarmIDs == null || alarmIDs.equals("")) {
				return null;
			}
			logger.info("alarmIDs:" + alarmIDs);
			AlarmNode alarm = alarmActiveMapper.getInfoById(Integer.parseInt(alarmIDs)); 
			
			Map<String, Object> map = new HashMap<String, Object>(); 
			map.put("alarmID", alarm.getAlarmID());
			map.put("moName", alarm.getMoName());
			map.put("alarmLevel", alarm.getAlarmLevel());			
			map.put("alarmTitle", alarm.getAlarmTitle());
			map.put("sourceMOIPAddress", alarm.getSourceMOIPAddress());
			map.put("sourceMOName", alarm.getSourceMOName());
			map.put("alarmStatus", alarm.getAlarmStatus());
			map.put("alarmType",alarm.getAlarmTypeName());
			
			logger.info("format before:"+alarm.getStartTime());
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (alarm.getStartTime() != null) {
				map.put("startTime", formatter.format(alarm.getStartTime()));
			} else {
				map.put("startTime", "");
			}
			
			if (alarm.getLastTime() != null) {
				map.put("lastTime", formatter.format(alarm.getLastTime()));
			} else {
				map.put("lastTime", "");
			}
			
			map.put("repeatCount",alarm.getRepeatCount());
			map.put("upgradeCount",alarm.getUpgradeCount());
			map.put("alarmDesc",alarm.getAlarmContent());
			map.put("confirmer",alarm.getConfirmer());
			
			if (alarm.getConfirmTime() != null) {
				map.put("confirmTime", formatter.format(alarm.getConfirmTime()));
			} else {
				map.put("confirmTime", "");
			}
			
			map.put("confirmInfo", alarm.getConfirmInfo());
			map.put("dispatchUser", alarm.getDispatchUser());
			
			if (alarm.getDispatchTime() != null) {
				map.put("dispatchTime", formatter.format(alarm
						.getDispatchTime()));
			} else {
				map.put("dispatchTime", "");
			}
			
			map.put("dispatchInfo",alarm.getDispatchInfo());
			
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	} 
	
	/**
	 * 3d机房单个设备告警统计
	 * @author zhengxh
	 * @param ciId 设备的ID
	 * @param request 请求对象
	 * @return 返回告警数据
	 */
	@RequestMapping(value = "/alarmNumberSingleBy3dRoom", method = RequestMethod.POST,
			headers = "Accept=application/json" ,produces="application/json;charset=utf-8" )
	public @ResponseBody
	Map<String, Object> alarmNumberSingleBy3dRoom(@RequestBody String ciId, HttpServletRequest request) {
		try {
			logger.info("单个设备告警统计 ciId:" + ciId);
			
			if (ciId == null || ciId.equals("")) {
				return null;
			}
			
			// 最近一条告警信息
			Map<String, Object> map = new HashMap<String, Object>(); 
			map.put("resId", ciId);
			AlarmNode alarm = alarmActiveMapper.load3dRoomLastActiveAlarm(map); 
			map.clear(); // 清除map
			
			if(alarm != null){
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				map.put("alarmID", alarm.getAlarmID());
				map.put("moName", alarm.getMoName());
				map.put("alarmLevel", alarm.getAlarmLevel());
				map.put("alarmTitle", alarm.getAlarmTitle());
				map.put("sourceMOIPAddress", alarm.getSourceMOIPAddress());
				map.put("sourceMOName", alarm.getSourceMOName());
				map.put("alarmStatus", alarm.getAlarmStatus());
				map.put("alarmType", alarm.getAlarmTypeName());
				if(alarm.getStartTime() != null){
					map.put("startTime", formatter.format(alarm.getStartTime()));
				}else{
					map.put("startTime", "");
				}
				if(alarm.getLastTime() != null){
					map.put("lastTime", formatter.format(alarm.getLastTime()));
				}else{
					map.put("lastTime", "");
				}				
				map.put("repeatCount", alarm.getRepeatCount());
				map.put("upgradeCount", alarm.getUpgradeCount());
				map.put("alarmDesc", alarm.getAlarmContent());				
					map.put("confirmer", alarm.getConfirmer());
				if(alarm.getConfirmTime() != null){
					map.put("confirmTime", formatter.format(alarm.getConfirmTime()));
				}else{
					map.put("confirmTime", "");
				}
				map.put("confirmInfo", alarm.getConfirmInfo());							
				map.put("dispatchUser", alarm.getDispatchUser());	
				if(alarm.getDispatchTime() != null){
					map.put("dispatchTime", formatter.format(alarm.getDispatchTime()));
				}else{
					map.put("dispatchTime", "");
				}				
				map.put("dispatchInfo", alarm.getDispatchInfo());			
			}			
			
			//告警数量
			List<MOActiveAlarmStInfo> alarmStList = alarmActiveMapper.queryMOActiveAlarmStObj(ciId);
			if (alarmStList != null) {
				int size = alarmStList.size();
				int i1 = 0, i2 = 0, i3 = 0, i4 = 0, i5 = 0;
				for (int i = 0; i < size; i++) {
					i1 += alarmStList.get(i).getL1count();
					i2 += alarmStList.get(i).getL2count();
					i3 += alarmStList.get(i).getL3count();
					i4 += alarmStList.get(i).getL4count();
					i5 += alarmStList.get(i).getL5count();
				}
				
				map.put("level1", i1);
				map.put("level2", i2);
				map.put("level3", i3);
				map.put("level4", i4);
				map.put("level5", i5);
			} else {
				map.put("level1", 0);
				map.put("level2", 0);
				map.put("level3", 0);
				map.put("level4", 0);
				map.put("level5", 0);
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	} 
	
	/**
	 * 3d机房告警统计
	 * @author zhengxh
	 * @param ciIds 设备的ID
	 * @param request 请求对象
	 * @return 返回告警数据
	 */
	@RequestMapping(value = "/alarmNumberAllBy3dRoom", method = RequestMethod.POST, 
			headers = "Accept=application/json" ,produces="application/json;charset=utf-8" )
	public @ResponseBody
	Map<String, Object> alarmNumberAllBy3dRoom(@RequestBody String ciId, HttpServletRequest request) {
		try {
			logger.info("3d机房告警统计 ciId:" + ciId);
			if (ciId == null || ciId.equals("")) {
				return null;
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			DecimalFormat df = new DecimalFormat("0.00");//格式化小数  
			//告警数量
			List<MOActiveAlarmStInfo> alarmStList = alarmActiveMapper.queryMOActiveAlarmStObj(ciId);
			int alarmDrivceCount=0;//告警设备数
			if(alarmStList != null){
				int size = alarmStList.size();
				int i1=0,i2=0,i3=0,i4=0,i5=0;
				for(int i = 0; i<size; i++ ){
					i1 += alarmStList.get(i).getL1count();
					i2 += alarmStList.get(i).getL2count();
					i3 += alarmStList.get(i).getL3count();
					i4 += alarmStList.get(i).getL4count();
					i5 += alarmStList.get(i).getL5count();
					
					int tmp = alarmStList.get(i).getL1count()
							+ alarmStList.get(i).getL2count()
							+ alarmStList.get(i).getL3count()
							+ alarmStList.get(i).getL4count()
							+ alarmStList.get(i).getL5count();
					
					if(tmp>0){
						alarmDrivceCount++;
					}					
				}
				map.put("level1", i1);
				map.put("level2", i2);
				map.put("level3", i3);
				map.put("level4", i4);
				map.put("level5", i5);
			    int alarmCount = i1+i2+i3+i4+i5;
				map.put("level1Rate", alarmCount !=0? df.format((double)i1*100/alarmCount):0.00);
				map.put("level2Rate", alarmCount !=0? df.format((double)i2*100/alarmCount):0.00);
				map.put("level3Rate", alarmCount !=0? df.format((double)i3*100/alarmCount):0.00);
				map.put("level4Rate", alarmCount !=0? df.format((double)i4*100/alarmCount):0.00);
				map.put("level5Rate", alarmCount !=0? df.format((double)i5*100/alarmCount):0.00);
				map.put("alarmCount", alarmCount );
			} else {
				map.put("level1", 0);
				map.put("level2", 0);
				map.put("level3", 0);
				map.put("level4", 0);
				map.put("level5", 0);
				map.put("level1Rate", 0.00);
				map.put("level2Rate", 0.00);
				map.put("level3Rate", 0.00);
				map.put("level4Rate", 0.00);
				map.put("level5Rate", 0.00);
				map.put("alarmCount", 0 );
			}
			
			// 告警设备数			
			map.put("alarmDrivceCount", alarmDrivceCount);
			// 机房设备数
			String[] arr = ciId.split(",");
			int len = arr.length;
			map.put("drivceCount", len);
			// 机房的健康
			if(alarmDrivceCount==0 || len==0){
				map.put("roomHealth",100);
			}else{
				map.put("roomHealth", (len != 0)?df.format(100-(double)alarmDrivceCount*100/len): 0.00);
			}			
			// 统计时间
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			map.put("timeCount", formatter.format(new Date()));
			
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	} 
	
	
	/**
	 * 3d机房加载告警（级别-数量）
	 * @author zhengxh
	 * @param jsonString json参数
	 * @param request 请求对象
	 * @return 返回集合数据
	 */
	@RequestMapping(value = "/alarmsOfRoom", method = RequestMethod.POST, headers = "Accept=application/json" ,produces = "application/json; charset=utf-8")
	public @ResponseBody
	List<Map<String, Object>> alarmsOfRoom(@RequestBody String jsonString,	HttpServletRequest request) {
		try {
			logger.info("3d机房加载告警数量 jsonString="+jsonString);
			
			if (jsonString == null || jsonString.equals("")) {
				return null;
			}
			
			//转换成json数组
			JSONArray jsonArray = JSONArray.fromObject(jsonString);			  
	        List<Map<String,Object>> mapListJson = (List)jsonArray;       
	        
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();	
			MOActiveAlarmStInfo mo = null;
			for (int i = 0; i < mapListJson.size(); i++) {  
	            Map<String,Object> obj=mapListJson.get(i);
	            String deviceIds = String.valueOf(obj.get("deviceIds"));
	            if(!deviceIds.endsWith("[]")){
	            	//获取告警数量
		            mo = alarmActiveMapper.queryMOActiveAlarmStObjByResId(deviceIds.substring(1, deviceIds.length()-1));
	            }else{
	            	mo = null;
	            }
	            
	            Map<String, Object> map = new HashMap<String, Object>();
	            map.put("roomId", obj.get("roomId"));
	            if(mo != null){
	            	map.put("level1", mo.getL1count());
	            	map.put("level2", mo.getL2count());
	            	map.put("level3", mo.getL3count());
	            	map.put("level4", mo.getL4count());
	            	map.put("level5", mo.getL5count());
	            }else{
	            	map.put("level1", 0);
	            	map.put("level2", 0);
	            	map.put("level3", 0);
	            	map.put("level4", 0);
	            	map.put("level5", 0);
	            }
	            list.add(map);
			}    
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据用户ID查询用户名
	 * @param userId
	 * @return
	 */
	public String queryUserNameById(String userId){
		SysUserInfoBean bean=sysUserService.findSysUserById(Integer.parseInt(userId));
		if (null == bean) {
			return "";
		} else {
			return bean.getUserName();
		}
	}
	
	/**
	 * 监控类型
	 * @author zhengxh
	 * @param request 请求对象
	 * @return 返回集合数据
	 */
	@RequestMapping(value = "/monitorTypes", method = RequestMethod.POST, headers = "Accept=application/json" ,produces = "application/json; charset=utf-8")
	public @ResponseBody
	List<Map<String, Object>> monitorTypes(HttpServletRequest request) {
		logger.info("监测对象树形数据");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<MObjectDefBean> typeLst = mobjectInfoMapper.queryMObjectRelation2();
		if(typeLst != null){
			for (int i = 0; i < typeLst.size(); i++) {
				MObjectDefBean vo = typeLst.get(i);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", vo.getRelationID());
				map.put("title", vo.getClassLable());
				map.put("parentId", vo.getNewParentID());
				map.put("classId", vo.getClassId());
				map.put("parentClassId", vo.getParentClassId());
				list.add(map);
			}
			return list;
		}
		return null;
	}
	
	/**
	 * 查询菜单子对象
	 */
	@RequestMapping(value = "/getChildIDByParentID", method = RequestMethod.POST, 
			headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	public @ResponseBody
	List<Integer> getChildIdByParentIDs(@RequestBody String parentClassID) {
		List<Integer> classid = new ArrayList<Integer>();
		List<MObjectRelationBean> mobjectRelation = mobjectInfoMapper
				.queryParentClassID(parentClassID);
		for (MObjectRelationBean mObjectRelation : mobjectRelation) {
			classid.add(mObjectRelation.getClassID());
		}
		
		if(classid.size()==0){
			return null;
		}
		
		return classid;
	}
	
	/**
	 * 温度、湿度告警统计
	 * @author wsp
	 * @param ciIds 设备的ID
	 * @param request 请求对象
	 * @return 返回告警数据
	 */
	@RequestMapping(value = "/alarmNumberAllByTH", method = RequestMethod.POST, 
			headers = "Accept=application/json" ,produces="application/json;charset=utf-8")
	public @ResponseBody
	Map<String, Object> alarmNumberAllByTH(@RequestBody String ciId, HttpServletRequest request) {
		try {
			logger.info("温度、湿度告警统计 ciId:" + ciId);
			
			if (ciId == null || ciId.equals("")) {
				return null;
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			DecimalFormat df = new DecimalFormat("0.00");//格式化小数 
			
			//告警数量
			List<MOActiveAlarmStInfo> alarmStList = alarmActiveMapper.queryMOActiveAlarmStObj(ciId);
			int alarmDrivceCount=0;//告警设备数
			if(alarmStList != null){
				int size = alarmStList.size();
				int i1=0,i2=0,i3=0,i4=0,i5=0;
				for(int i = 0; i<size; i++ ){
					i1 += alarmStList.get(i).getL1count();
					i2 += alarmStList.get(i).getL2count();
					i3 += alarmStList.get(i).getL3count();
					i4 += alarmStList.get(i).getL4count();
					i5 += alarmStList.get(i).getL5count();
					
					int tmp = alarmStList.get(i).getL1count()
							+ alarmStList.get(i).getL2count()
							+ alarmStList.get(i).getL3count()
							+ alarmStList.get(i).getL4count()
							+ alarmStList.get(i).getL5count();
					
					if(tmp>0){
						alarmDrivceCount++;
					}					
				}
				map.put("level1", i1);
				map.put("level2", i2);
				map.put("level3", i3);
				map.put("level4", i4);
				map.put("level5", i5);
			    int alarmCount = i1+i2+i3+i4+i5;
				map.put("level1Rate", alarmCount !=0? df.format((double)i1*100/alarmCount):0.00);
				map.put("level2Rate", alarmCount !=0? df.format((double)i2*100/alarmCount):0.00);
				map.put("level3Rate", alarmCount !=0? df.format((double)i3*100/alarmCount):0.00);
				map.put("level4Rate", alarmCount !=0? df.format((double)i4*100/alarmCount):0.00);
				map.put("level5Rate", alarmCount !=0? df.format((double)i5*100/alarmCount):0.00);
				map.put("alarmTotal", alarmCount );
			} else {
				map.put("level1", 0);
				map.put("level2", 0);
				map.put("level3", 0);
				map.put("level4", 0);
				map.put("level5", 0);
				map.put("level1Rate", 0.00);
				map.put("level2Rate", 0.00);
				map.put("level3Rate", 0.00);
				map.put("level4Rate", 0.00);
				map.put("level5Rate", 0.00);
				map.put("alarmTotal", 0 );
			}
			
			String[] arr = ciId.split(",");
			int len = arr.length;
			map.put("labelCount", len);
			//统计时间
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			map.put("timeCount", formatter.format(new Date()));
			
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	} 
	
	/**
	 * 设备告警数量统计
	 * @author zhengxh
	 * @param moIds 设备的ID
	 * @return 返回告警数据
	 */
	@RequestMapping(value = "/alarmNumsOfDevice", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> alarmNumsOfDevice(@RequestBody String moIds) {
		try {
			logger.info("部分活动告警 moIds:"+moIds);
			
			Map<String, Object> map = new HashMap<String, Object>();
			
			List<MOActiveAlarmStInfo> alarmStList = alarmActiveMapper.queryAlarmNumsByMODevice(moIds);
			
			if(alarmStList != null){
				int size = alarmStList.size();
				int i1=0,i2=0,i3=0,i4=0,i5=0;
				for(int i = 0; i<size; i++ ){
					i1 += alarmStList.get(i).getL1count();
					i2 += alarmStList.get(i).getL2count();
					i3 += alarmStList.get(i).getL3count();
					i4 += alarmStList.get(i).getL4count();
					i5 += alarmStList.get(i).getL5count();								
				}
				map.put("level1", i1);
				map.put("level2", i2);
				map.put("level3", i3);
				map.put("level4", i4);
				map.put("level5", i5);
				map.put("alarmCount", i1+i2+i3+i4+i5);
			}	
			
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 流程系统回写派发结果
	 * @param jsonObj
	 * @return 返回告警派单结果
	 */
	@RequestMapping(value = "/alarmDispatchReply", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody String alarmDispatchReply(@RequestBody String jsonString) {
		try {
			logger.info("alarmDispatchReply jsonString "+jsonString);
//			JSONObject responseRecord = JSONObject.parseObject(jsonString); 
			logger.info("json2Map map  开始");
			Map<String,Object> map = JsonUtil.json2Map(jsonString);
			logger.info("json2Map map  map"+map);
			AlarmDispatchDetail detail = new AlarmDispatchDetail();
			
			detail.setDispatchID(map.get("dispatchID").toString());
			detail.setAlarmID(strUtil(map.get("alarmID"))); 
			detail.setWorkOrderId(strUtil(map.get("workOrderId")));
			detail.setDispatchStatus(strUtil(map.get("eventStatus")));
			Object descr = map.get("resultDescr");
			if(descr !=null && descr.toString().compareTo("")>0){
				detail.setResultDescr(map.get("resultDescr").toString());
			}
			String dispatchID = map.get("dispatchID").toString();
//			detail.setDispatchID(responseRecord.get("dispatchID").toString());
//			detail.setAlarmID(strUtil(responseRecord.get("alarmID"))); 
//			detail.setWorkOrderId(strUtil(responseRecord.get("workOrderId"))); 
//			detail.setDispatchStatus(strUtil(responseRecord.get("eventStatus")));
//			Object descr = responseRecord.get("resultDescr");
//			if(descr !=null && descr.toString().compareTo("")>0){
//				detail.setResultDescr(responseRecord.get("resultDescr").toString());
//			}
//			String dispatchID = responseRecord.get("dispatchID").toString();
			Map<String,Object> parMap = new HashMap<String, Object>();
			Map<String,Object> dispatchIDMap = new HashMap<String, Object>();
			if(dispatchID!=null && !dispatchID.equals(""))
			{
				parMap.put("alarmOperateStatus", 24); // 人工清除
				parMap.put("dispatchID", dispatchID);
				alarmHistoryMapper.insertActiveInfo(parMap);
				//删除告警信息
				dispatchIDMap.put("dispatchID",  "DispatchID in ("+dispatchID+")");
				alarmActiveMapper.deleteAlarminfos(dispatchIDMap); // 删除告警信息
			}
			alarmDispatchService.updateAlarmDispatchRecordByCMDB(detail); 
			return "success";
		} catch (Exception e) {
			logger.error("流程系统回写派发结果出错:",e);
			e.printStackTrace();
		}
		return "success";
	}
	
	public static int strUtil(Object obj) {
		if (obj == null || obj.equals("")) {
			return 0;
		}
		return Integer.parseInt(obj.toString().trim());
	}
	
	/**
	 * 告警详情
	 */	
	@RequestMapping(value = "/alarmActiveDetail", method = RequestMethod.POST, headers = "Accept=application/json", 
			produces = "application/json;charset=utf-8")
	public @ResponseBody JSONObject alarmActiveDetail(@RequestBody String alarmID) {		
		logger.info("告警详情id:"+alarmID);
		if (alarmID == null || alarmID.equals("")) {
			return null;
		}
		
		AlarmNode vo = alarmActiveMapper.getInfoById(Integer.parseInt(alarmID));
		if(vo==null ||vo.getAlarmID()==0)
		{
			vo = alarmActiveMapper.getHisInfoById(Integer.parseInt(alarmID));
		}
//		if(vo.getConfirmer() != null && vo.getConfirmer() != ""){
//			vo.setConfirmer(queryUserNameById(vo.getConfirmer()));
//		}
//		
//		if(vo.getDispatchUser() != null && vo.getDispatchUser() != ""){
//			vo.setDispatchUser(queryUserNameById(vo.getDispatchUser()));
//		}
		
		String objJson = ObjectToJsonString(vo);
		logger.info(""+objJson);
		return JSONObject.parseObject(objJson);
	}
	
	public static String ObjectToJsonString(Object o){
		if(o != null){
			ObjectMapper mapper = new ObjectMapper(); 
			try { 
//			    /* We want dates to be treated as ISO8601 not timestamps. */ 
//				mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false); 
  				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 				extracted(mapper, formatter);
				return mapper.writeValueAsString(o);
			} catch (Exception e) {
				logger.error("对象转json失败！", e);
			}
		}
		return null;
	}
	 
 	private static void extracted(ObjectMapper mapper,SimpleDateFormat formatter) {
 		mapper.setDateFormat(formatter);
 	}
	
	/**
	 * Topo选择设备类型过滤
	 * 
	 * @return 返回topo表JSON
	 */
	@RequestMapping(value = "/getAlarmLevel",method = RequestMethod.POST, headers = "Accept=application/json",
			produces = "application/json;charset=utf-8")
	public @ResponseBody
	List<Map<String, Object>> getAlarmLevel() {
		List<AlarmLevelBean> alarmLevelList = alarmLevelMapper.getAllAlarmLevel();
		List<Map<String, Object>> levelList = new ArrayList<Map<String, Object>>(); 
		Map<String, Object> tempMap = null;
		for (AlarmLevelBean level : alarmLevelList) {
			tempMap = new LinkedHashMap<String, Object>();
			tempMap.put("levelID", level.getAlarmLevelValue());
			tempMap.put("levelName", level.getAlarmLevelName());
			levelList.add(tempMap);
		}
		return levelList;
	}
}