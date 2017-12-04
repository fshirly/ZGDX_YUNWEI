package com.fable.insightview.monitor.alarmmgr.alarmeventredefine.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.monitor.alarmmgr.alarmeventredefine.entity.AlarmEventRedefineBean;
import com.fable.insightview.monitor.alarmmgr.alarmeventredefine.entity.AlarmRuleOfEventBean;
import com.fable.insightview.monitor.alarmmgr.alarmeventredefine.entity.AlarmRuleOfMOClassBean;
import com.fable.insightview.monitor.alarmmgr.alarmeventredefine.entity.AlarmRuleOfResourceBean;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmEventDefineBean;
import com.fable.insightview.platform.page.Page;

/**
 * 告警重定义
 *
 */
public interface AlarmEventRedefineMapper {
	/**
	 * 告警重定义列表数据
	 */
	List<AlarmEventRedefineBean> listAlarmEventRedefine(Page<AlarmEventRedefineBean> page);

	/**
	 * 根据id批量删除重定义规则
	 */
	boolean delRuleByIDs(@Param("ruleIds")String ruleIds);
	
	/**
	 * 根据重定义规则id删除重定义规则与事件关系
	 */
	boolean delRuleOfEventByRuleIds(@Param("ruleIds")String ruleIds);
	
	/**
	 * 根据重定义规则id删除重定义与对象类型关系
	 */
	boolean delRuleOfMOClassByRuleIds(@Param("ruleIds")String ruleIds);
	
	/**
	 * 根据重定义规则id删除重定义规则与资源关系
	 */
	boolean delRuleOfResourceByRuleIds(@Param("ruleIds")String ruleIds);
	
	/**
	 * 新增告警重定义规则
	 */
	int insertAlarmEventRedefine(AlarmEventRedefineBean bean);
	
	/**
	 * 新增重定义规则与事件关系
	 */
	int insertRuleOfEvent(AlarmRuleOfEventBean bean);
	
	/**
	 * 新增重定义与对象类型关系
	 */
	int insertRuleOfMOClass(AlarmRuleOfMOClassBean bean);
	
	/**
	 * 新增重定义规则与资源关系
	 */
	int insertRuleOfResource(AlarmRuleOfResourceBean bean);
	
	/**
	 * 更新告警重定义规则
	 */
	int updateEventRedefineByID(AlarmEventRedefineBean bean);
	
	/**
	 * 获得重定义规则及关系
	 * @param bean
	 * @return
	 */
	List<AlarmEventRedefineBean> getRuleAndRelation(Map<String, Object> map);
	
	/**
	 * 获得已经定义过规则的资源id
	 * @param map
	 * @return
	 */
	List<Integer> getDefinedMoId(Map<String, Object> map);
	
	/**
	 * 根据条件获得重定义规则
	 */
	List<AlarmEventRedefineBean> getEventRule(AlarmEventRedefineBean bean);
	
	/**
	 * 根据id获得重定义规则
	 */
	AlarmEventRedefineBean getRuleByID(int ruleId);
	
	/**
	 * 根据规则id获得重定义与对象类型关系
	 */
	AlarmRuleOfMOClassBean getRuleOfClassByRuleID(int ruleId);
	
	/**
	 * 根据重定义规则获得添加的事件
	 */
	List<AlarmEventDefineBean> getEventListByRuleId(int ruleId);
	
	/**
	 * 根据moids批量删除
	 */
	boolean delRuleOfResourceByMoIds(@Param("moIds")String moIds);
	
	/**
	 * 跟moId获得告警重定义规则
	 */
	List<AlarmEventRedefineBean> getRuleByMOID(@Param("moId")int moId);
}
