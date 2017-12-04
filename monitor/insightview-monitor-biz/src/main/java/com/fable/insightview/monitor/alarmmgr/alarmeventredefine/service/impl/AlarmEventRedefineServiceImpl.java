package com.fable.insightview.monitor.alarmmgr.alarmeventredefine.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.fable.insightview.monitor.alarmmgr.alarmcategory.entity.AlarmCategoryBean;
import com.fable.insightview.monitor.alarmmgr.alarmcategory.mapper.AlarmCategoryMapper;
import com.fable.insightview.monitor.alarmmgr.alarmeventdefine.mapper.AlarmEventDefineMapper;
import com.fable.insightview.monitor.alarmmgr.alarmeventredefine.entity.AlarmEventRedefineBean;
import com.fable.insightview.monitor.alarmmgr.alarmeventredefine.entity.AlarmRuleOfEventBean;
import com.fable.insightview.monitor.alarmmgr.alarmeventredefine.entity.AlarmRuleOfMOClassBean;
import com.fable.insightview.monitor.alarmmgr.alarmeventredefine.entity.AlarmRuleOfResourceBean;
import com.fable.insightview.monitor.alarmmgr.alarmeventredefine.mapper.AlarmEventRedefineMapper;
import com.fable.insightview.monitor.alarmmgr.alarmeventredefine.service.IAlarmEventRedefineService;
import com.fable.insightview.monitor.alarmmgr.alarmlevel.entity.AlarmLevelBean;
import com.fable.insightview.monitor.alarmmgr.alarmlevel.mapper.AlarmLevelMapper;
import com.fable.insightview.monitor.alarmmgr.alarmtype.entity.AlarmTypeBean;
import com.fable.insightview.monitor.alarmmgr.alarmtype.mapper.AlarmTypeMapper;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmEventDefineBean;
import com.fable.insightview.monitor.database.mapper.Db2Mapper;
import com.fable.insightview.monitor.database.mapper.MySQLMapper;
import com.fable.insightview.monitor.database.mapper.OracleMapper;
import com.fable.insightview.monitor.deviceresview.entity.DeviceResViewBean;
import com.fable.insightview.monitor.deviceresview.mapper.DeviceResViewMapper;
import com.fable.insightview.monitor.mobject.entity.MObjectDefBean;
import com.fable.insightview.monitor.mobject.mapper.MobjectInfoMapper;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.dict.util.DictionaryLoader;
import com.fable.insightview.platform.page.Page;

/**
 * 告警重定义
 *
 */
@Service
public class AlarmEventRedefineServiceImpl implements IAlarmEventRedefineService {
	private static final Logger logger = LoggerFactory.getLogger(AlarmEventRedefineServiceImpl.class);
	private static final String RESLEVEL_TYPE = "resLevel";
	//所有告警事件相同告警等级
	private static final int EVENT_SAME = 1;
	
	private static final int ORACLE = 15;
	private static final int MYSQL = 16;
	private static final int DB2 = 54;
	private static final int SYBASE = 81;
	private static final int MSSQL = 86;
	// 获得数据字典中视图级别名称
	Map<Integer, String> resLevelMap = DictionaryLoader.getConstantItems(RESLEVEL_TYPE);
	@Autowired
	AlarmEventRedefineMapper alarmEventRedefineMapper;
	@Autowired
	AlarmLevelMapper alarmLevelMapper;
	@Autowired
	AlarmTypeMapper alarmTypeMapper;
	@Autowired
	AlarmCategoryMapper alarmCategoryMapper;
	@Autowired
	AlarmEventDefineMapper defineMapper;
	@Autowired
	MobjectInfoMapper mobjectInfoMapper;
	@Autowired
	DeviceResViewMapper deviceResViewMapper;
	@Autowired
	MySQLMapper mySQLMapper;
	@Autowired
	OracleMapper oracleMapper;
	@Autowired
	Db2Mapper db2Mapper;
	
	@Override
	public Map<String, Object> listAlarmEventRedefine(
			Page<AlarmEventRedefineBean> page) {
		List<AlarmEventRedefineBean> list = alarmEventRedefineMapper.listAlarmEventRedefine(page);
		int total = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", list);
		result.put("total", total);
		return result;
	}

	@Override
	public boolean delAlarmEventRedefine(String ruleIds) {
		boolean delRuleFlag = true;
		boolean delRuleOfEventFlag = true;
		boolean delRuleOfMOClassFlag = true;
		boolean delRuleOfResourceFlag = true;
		//删除重定义规则与事件关系
		try {
			alarmEventRedefineMapper.delRuleOfEventByRuleIds(ruleIds);
		} catch (Exception e) {
			delRuleOfEventFlag = false;
			logger.error("删除重定义规则与事件关系异常：",e);
		}
		//删除重定义规则与资源关系
		try {
			alarmEventRedefineMapper.delRuleOfResourceByRuleIds(ruleIds);
		} catch (Exception e) {
			delRuleOfResourceFlag = false;
			logger.error("删除重定义规则与资源关系：",e);
		}
		//删除重定义与对象类型关系
		try {
			alarmEventRedefineMapper.delRuleOfMOClassByRuleIds(ruleIds);
		} catch (Exception e) {
			delRuleOfMOClassFlag = false;
			logger.error("删除重定义与对象类型关系：",e);
		}
		//删除重定义规则
		try {
			alarmEventRedefineMapper.delRuleByIDs(ruleIds);
		} catch (Exception e) {
			delRuleFlag = false;
			logger.error("删除重定义规则：",e);
		}
		if(delRuleOfEventFlag && delRuleOfMOClassFlag && delRuleOfResourceFlag && delRuleFlag){
			return true;
		}
		return false;
	}

	@Override
	public List<AlarmLevelBean> getAllAlarmLevel() {
		return alarmLevelMapper.getAllAlarmLevel();
	}

	@Override
	public List<AlarmCategoryBean> getAllAlarmCategory() {
		return alarmCategoryMapper.getAllAlarmGategory();
	}

	@Override
	public List<AlarmTypeBean> getAllAlarmType() {
		return alarmTypeMapper.getAllAlarmType();
	}

	@Override
	public Map<String, Object> listForSelectEvent(Page<AlarmEventDefineBean> page) {
		List<AlarmEventDefineBean> list = defineMapper.getDefinedEvent(page);
		int total = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", list);
		result.put("total", total);
		return result;
	}

	@Override
	public Map<String, Object> findResClassLst() {
		List<MObjectDefBean> mobjectList = mobjectInfoMapper.getAllResMObject();
		List<MObjectDefBean> menuLst = new ArrayList<MObjectDefBean>();
		for (int i = 0; i < mobjectList.size(); i++) {
			MObjectDefBean bean = mobjectList.get(i);
			if (bean.getParentClassId() != null || bean.getClassId() == 1) {
				if (bean.getClassId() < 95 && bean.getClassId() != 75 && bean.getClassId() != 59 && bean.getClassId() != 60 && bean.getClassId() != 44 && bean.getClassId() != -1) {
					menuLst.add(bean);
				}
			}
		}

		for (int i = 0; i < menuLst.size(); i++) {
			MObjectDefBean bean = menuLst.get(i);
			if (bean.getParentClassId() == null) {
				bean.setParentClassId(0);
			}
			if(bean.getClassId() == 6){
				bean.setChildCount(0);
			}
		}
		String menuLstJson = JsonUtil.toString(menuLst);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("menuLstJson", menuLstJson);
		return result;
	}

	@Override
	public Map<String, Object> listDeviceRes(Page<DeviceResViewBean> page) {
		List<DeviceResViewBean> list = deviceResViewMapper.getDeviceInfo(page);
		for (DeviceResViewBean deviceRes : list) {
			if(deviceRes.getResLevel() != null){
				String resLevelName = resLevelMap.get(deviceRes.getResLevel());
				deviceRes.setResLevelName(resLevelName);
			}
		}
		int total = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", list);
		result.put("total", total);
		return result;
	}

	@Override
	public Map<String, Object> editEventRedefine(AlarmEventRedefineBean bean) {
		boolean editRuleFlag = true;
		int ruleId = bean.getRuleId();
		try {
			//新增规则
			if(ruleId == -1){
				alarmEventRedefineMapper.insertAlarmEventRedefine(bean);
				ruleId = bean.getRuleId();
			}
			else{
				//编辑规则
				alarmEventRedefineMapper.updateEventRedefineByID(bean);
				//删除之前的重定义规则与事件的关系
				alarmEventRedefineMapper.delRuleOfEventByRuleIds(bean.getRuleId().toString());
				//删除之前的重定义规则与对象类型关系
				alarmEventRedefineMapper.delRuleOfMOClassByRuleIds(bean.getRuleId().toString());
				//删除之前的重定义规则与资源的关系
				alarmEventRedefineMapper.delRuleOfResourceByRuleIds(bean.getRuleId().toString());
			}
		} catch (Exception e) {
			editRuleFlag = false;
			logger.error("编辑重定义规则异常！",e);
		}
		logger.info("重定义规则的id为："+ruleId);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("ruleId", ruleId);
		result.put("editRuleFlag", editRuleFlag);
		return result;
	}
	
	@Override
	public Map<String, Object> getEffectiveRes(AlarmEventRedefineBean bean) {
		Map<String, Object> result = new HashMap<String, Object>();
		boolean isEffectiveRes = true; 
		//重定义规则id
		int ruleId = bean.getRuleId();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ruleId", ruleId);
		//已经选择的监测资源id
		String choosedMoIds = bean.getChoosedMoIds();
		logger.info("已经选择的监测资源id为:{}",choosedMoIds);
		paramMap.put("choosedMoIds", choosedMoIds);
		
		//获得选择的监测资源之中已经定义的重定义规则
		List<AlarmEventRedefineBean> definedEventRuleLst = alarmEventRedefineMapper.getRuleAndRelation(paramMap);
		result.put("definedEventLst", definedEventRuleLst);
		//获得选择的资源id中已经定义过重定义规则的moid
		List<Integer> definedMoIdLst = alarmEventRedefineMapper.getDefinedMoId(paramMap);
		//获得还没有定义过重定义规则的资源
		List<Integer> unDefinedMoIdLst = new ArrayList<Integer>();
		String[] splits = choosedMoIds.split(",");
		for (int i = 0; i < splits.length; i++) {
			int moId = Integer.parseInt(splits[i]);
			if(!definedMoIdLst.contains(moId)){
				unDefinedMoIdLst.add(moId);
			}
		}
		//如果没有有效的资源则不添加
		if(unDefinedMoIdLst.size() == 0){
			isEffectiveRes = false;
			result.put("isEffectiveRes", isEffectiveRes);
			return result;
		}
		result.put("isEffectiveRes", isEffectiveRes);
		result.put("unDefinedMoIdLst", unDefinedMoIdLst);
		return result;
	}

	@Override
	public boolean addRelation(AlarmEventRedefineBean bean,List<Integer> unDefinedMoIdLst) {
		boolean addRuleOfEvent = true;
		boolean addRuleOfClass = true;
		boolean addRuleOfRes = true;
		
		//重定义规则id
		int ruleId = bean.getRuleId();
		
		//新增重定义规则与事件关系
		int isSame = bean.getIsSame();
		try {
			//所有告警事件相同告警等级
			if(isSame == EVENT_SAME){
				AlarmRuleOfEventBean ruleOfEvent = new AlarmRuleOfEventBean();
				ruleOfEvent.setRuleId(ruleId);
				ruleOfEvent.setAlarmDefineId(-1);
				ruleOfEvent.setAlarmLevelId(bean.getAlarmLevelId());
				alarmEventRedefineMapper.insertRuleOfEvent(ruleOfEvent);
			}
			//不同告警事件定义不同告警等级
			else{
				//添加的告警定义事件
				String eventData = bean.getEventData();
				List<AlarmEventDefineBean> eventeLst = JSON.parseArray(eventData,AlarmEventDefineBean.class);
				for (int i = 0; i < eventeLst.size(); i++) {
					AlarmRuleOfEventBean ruleOfEvent = new AlarmRuleOfEventBean();
					ruleOfEvent.setRuleId(ruleId);
					ruleOfEvent.setAlarmDefineId(eventeLst.get(i).getAlarmDefineID());
					ruleOfEvent.setAlarmLevelId(eventeLst.get(i).getAlarmLevelID());
					alarmEventRedefineMapper.insertRuleOfEvent(ruleOfEvent);
				}
			}
		} catch (Exception e) {
			addRuleOfEvent = false;
			logger.error("新增重定义规则与事件关系异常：{}",e);
		}
		
		//新增重定义与对象类型关系
		int moClassId = bean.getMoClassId();
		int ruleOfClassId = -1;
		try {
			AlarmRuleOfMOClassBean ruleOfClass = new AlarmRuleOfMOClassBean();
			ruleOfClass.setMoClassId(moClassId);
			ruleOfClass.setRuleId(ruleId);
			alarmEventRedefineMapper.insertRuleOfMOClass(ruleOfClass);
			ruleOfClassId = ruleOfClass.getId();
		} catch (Exception e) {
			addRuleOfClass = false;
			logger.error("新增重定义规则与对象类型关系异常：{}",e);
		}
		
		//新增重定义规则与资源关系
		try {
			for (int i = 0; i < unDefinedMoIdLst.size(); i++) {
				int moId = unDefinedMoIdLst.get(i);
				AlarmRuleOfResourceBean ruleOfRes = new AlarmRuleOfResourceBean();
				ruleOfRes.setRuleOfMOClassId(ruleOfClassId);
				ruleOfRes.setMoId(moId);
				alarmEventRedefineMapper.insertRuleOfResource(ruleOfRes);
				//如果是数据库类型，新增数据库子对象与重定义规则关系
				if(moClassId == MYSQL || moClassId == ORACLE || moClassId == DB2 || moClassId == SYBASE || moClassId == MSSQL){
					addDBChildOfRule(moClassId, moId, ruleOfClassId);
				}
			}
		} catch (Exception e) {
			addRuleOfEvent = false;
			logger.error("新增重定义规则与事件关系异常：{}",e);
		}
		
		boolean addRelation = addRuleOfEvent && addRuleOfClass && addRuleOfRes;
		return addRelation;
	}
	
	private void addDBChildOfRule(int moClassId,int moId,int ruleOfClassId){
		List<Integer> childIdList = null;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("dbmsMoId", moId);
		switch (moClassId) {
		case MYSQL:
			//获得MysqlServer
			param.put("tableName", "MOMySQLDBServer");
			childIdList = mySQLMapper.getDBChildId(param);
			break;
		case ORACLE:
			//获得该oracle服务下的所有数据库实例、数据库
			childIdList = oracleMapper.getOrclAlarmSourceId(moId);
			break;
		case DB2:
			//获得该DB2服务下的所有数据库实例、数据库
			childIdList = db2Mapper.getDB2AlarmSourceId(moId);
			break;
		case SYBASE:
			//获得SybaseServer
			param.put("tableName", "MOSybaseServer");
			childIdList = mySQLMapper.getDBChildId(param);
			break;
		case MSSQL:
			//获得MssqlServer
			param.put("tableName", "MOMsSQLServer");
			childIdList = mySQLMapper.getDBChildId(param);
			break;
		}
		for (Integer id : childIdList) {
			AlarmRuleOfResourceBean ruleOfRes = new AlarmRuleOfResourceBean();
			ruleOfRes.setRuleOfMOClassId(ruleOfClassId);
			ruleOfRes.setMoId(id);
			alarmEventRedefineMapper.insertRuleOfResource(ruleOfRes);
		}
	}
	
	@Override
	public boolean checkRuleName(AlarmEventRedefineBean bean) {
		List<AlarmEventRedefineBean> ruleLst = alarmEventRedefineMapper.getEventRule(bean);
		if(ruleLst == null || ruleLst.size() <= 0){
			return true;
		}
		return false;
	}

	@Override
	public AlarmEventRedefineBean getRuleByID(int ruleId) {
		return alarmEventRedefineMapper.getRuleByID(ruleId);
	}

	@Override
	public AlarmRuleOfMOClassBean getRuleOfClassByRuleID(int ruleId) {
		return alarmEventRedefineMapper.getRuleOfClassByRuleID(ruleId);
	}

	@Override
	public List<DeviceResViewBean> initSelectedRes(int ruleId) {
		List<DeviceResViewBean> list = deviceResViewMapper.getDeviceByRuleID(ruleId);
		for (DeviceResViewBean deviceRes : list) {
			if(deviceRes.getResLevel() != null){
				String resLevelName = resLevelMap.get(deviceRes.getResLevel());
				deviceRes.setResLevelName(resLevelName);
			}
		}
		return list;
	}

	@Override
	public Map<String, Object> initSelectedEvent(int ruleId) {
		List<AlarmEventDefineBean> eventList = alarmEventRedefineMapper.getEventListByRuleId(ruleId);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("eventList", eventList);
		return result;
	}

}
