package com.fable.insightview.monitor.alarmmgr.alarmeventredefine.service;

import java.util.List;
import java.util.Map;

import com.fable.insightview.monitor.alarmmgr.alarmcategory.entity.AlarmCategoryBean;
import com.fable.insightview.monitor.alarmmgr.alarmeventredefine.entity.AlarmEventRedefineBean;
import com.fable.insightview.monitor.alarmmgr.alarmeventredefine.entity.AlarmRuleOfMOClassBean;
import com.fable.insightview.monitor.alarmmgr.alarmlevel.entity.AlarmLevelBean;
import com.fable.insightview.monitor.alarmmgr.alarmtype.entity.AlarmTypeBean;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmEventDefineBean;
import com.fable.insightview.monitor.deviceresview.entity.DeviceResViewBean;
import com.fable.insightview.platform.page.Page;


/**
 * 告警重定义
 *
 */
public interface IAlarmEventRedefineService {
	/**
	 * 告警重定义列表数据
	 */
	Map<String, Object> listAlarmEventRedefine(Page<AlarmEventRedefineBean> page);
	
	/**
	 * 删除重定义规则
	 */
	boolean delAlarmEventRedefine(String ruleIds);
	
	/**
	 * 获得所有的告警等级
	 */
	List<AlarmLevelBean> getAllAlarmLevel();
	
	/**
	 * 获得所有的告警类型
	 */
	List<AlarmTypeBean> getAllAlarmType();
	
	/**
	 * 获得所有的告警分类
	 */
	List<AlarmCategoryBean> getAllAlarmCategory();
	
	/**
	 * 获得告警事件列表数据
	 */
	Map<String, Object> listForSelectEvent(Page<AlarmEventDefineBean> page);
	
	/**
	 * 获得资源类型树数据
	 */
	Map<String, Object> findResClassLst();
	
	/**
	 * 获得资源列表数据
	 */
	Map<String, Object> listDeviceRes(Page<DeviceResViewBean> page);
	
	Map<String, Object> getEffectiveRes(AlarmEventRedefineBean bean);
	
	/**
	 * 新增或编辑重定义规则
	 * @param bean
	 * @return
	 */
	 Map<String, Object> editEventRedefine(AlarmEventRedefineBean bean);
	 
	 /**
	  * 新增各种关系
	  */
	 boolean addRelation(AlarmEventRedefineBean bean,List<Integer> unDefinedMoIdLst);
	
	 /**
	  * 校验规则名称
	  */
	 boolean checkRuleName(AlarmEventRedefineBean bean);
	 
	 /**
	 * 根据id获得重定义规则
	 */
	 AlarmEventRedefineBean getRuleByID(int ruleId);
	 
	 /**
	 * 根据规则id获得重定义与对象类型关系
	 */
	 AlarmRuleOfMOClassBean getRuleOfClassByRuleID(int ruleId);
	 
	 /**
	 * 初始化获得已经添加的资源
	 */
	 List<DeviceResViewBean> initSelectedRes(int ruleId);
	 
	 /**
	  * 初始化获得已经添加的事件
	  */
	 Map<String, Object> initSelectedEvent(int ruleId);
}
