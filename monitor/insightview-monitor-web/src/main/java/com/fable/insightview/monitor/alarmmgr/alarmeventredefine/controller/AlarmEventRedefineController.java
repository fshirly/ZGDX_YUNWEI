package com.fable.insightview.monitor.alarmmgr.alarmeventredefine.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.monitor.alarmmgr.alarmcategory.entity.AlarmCategoryBean;
import com.fable.insightview.monitor.alarmmgr.alarmeventredefine.entity.AlarmEventRedefineBean;
import com.fable.insightview.monitor.alarmmgr.alarmeventredefine.service.IAlarmEventRedefineService;
import com.fable.insightview.monitor.alarmmgr.alarmlevel.entity.AlarmLevelBean;
import com.fable.insightview.monitor.alarmmgr.alarmtype.entity.AlarmTypeBean;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmEventDefineBean;
import com.fable.insightview.monitor.deviceresview.entity.DeviceResViewBean;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.common.util.KeyValPair;
import com.fable.insightview.platform.dict.util.DictionaryLoader;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.page.Page;

/**
 * 告警重定义
 *
 */
@Controller
@RequestMapping("/monitor/alarmEventRedefine")
public class AlarmEventRedefineController {
	private static final Logger logger = LoggerFactory.getLogger(AlarmEventRedefineController.class);
	private static final int RESOURCE_LEVEL_ID = 3087;
	
	@Autowired
	IAlarmEventRedefineService alarmEventRedefineService;
	/**
	 * 告警重定义列表页面
	 */
	@RequestMapping("/toAlarmEventRedefine")
	public ModelAndView toAlarmEventRedefine(String navigationBar) {
		logger.info("进入告警重定义列表页面");
		ModelAndView mv = new ModelAndView("monitor/alarmMgr/alarmeventredefine/alarmEventRedefine_list");
		mv.addObject("navigationBar", navigationBar);
		return mv;
	}
	
	/**
	 * 告警重定义列表数据
	 */
	@RequestMapping("/listAlarmEventRedefine")
	@ResponseBody
	public Map<String, Object> listAlarmEventRedefine(HttpServletRequest request, AlarmEventRedefineBean bean){
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil .getFlexiGridPageInfo(request);
		Page<AlarmEventRedefineBean> page = new Page<AlarmEventRedefineBean>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ruleName", bean.getRuleName());
		paramMap.put("isEnable", bean.getIsEnable());
		paramMap.put("ruleDesc", bean.getRuleDesc());
		page.setParams(paramMap);
		return alarmEventRedefineService.listAlarmEventRedefine(page);
	}
	
	/**
	 * 删除重定义规则
	 */
	@RequestMapping("/delAlarmEventRedefine")
	@ResponseBody
	public boolean delAlarmEventRedefine(HttpServletRequest request){
		String ruleIds = request.getParameter("ruleIds");
		logger.info("删除id为：{}的重定义规则。",ruleIds);
		return alarmEventRedefineService.delAlarmEventRedefine(ruleIds);
	}
	
	/**
	 * 告警重定义新增、编辑页面
	 */
	@RequestMapping("/toEditAlarmEventRedefine")
	public ModelAndView toEditAlarmEventRedefine(HttpServletRequest request) {
		List<AlarmLevelBean> alarmLevelList = alarmEventRedefineService.getAllAlarmLevel();
		ModelAndView mv = new ModelAndView("monitor/alarmMgr/alarmeventredefine/alarmEventRedefine_edit");
		if(request.getParameter("ruleId") != null && !"".equals(request.getParameter("ruleId"))){
			int ruleId = Integer.parseInt(request.getParameter("ruleId"));
			AlarmEventRedefineBean rule = alarmEventRedefineService.getRuleByID(ruleId);
			mv.addObject("rule", rule);
			int moClassId = alarmEventRedefineService.getRuleOfClassByRuleID(ruleId).getMoClassId();
			String moClassName = alarmEventRedefineService.getRuleOfClassByRuleID(ruleId).getClassLable();
			mv.addObject("moClassId", moClassId);
			mv.addObject("moClassName", moClassName);
		}
		mv.addObject("ruleId", request.getParameter("ruleId"));
		mv.addObject("alarmLevelList", alarmLevelList);
		mv.addObject("levelJson", JsonUtil.toString(alarmLevelList));
		return mv;
	}
	
	/**
	 * 跳转至添加告警事件定义告警等级
	 */
	@RequestMapping("/toAddDefinedEvent")
	public ModelAndView toAddDefinedEvent(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("monitor/alarmMgr/alarmeventredefine/addDefinedEvent");
		List<AlarmTypeBean> typeList = alarmEventRedefineService.getAllAlarmType();
		List<AlarmCategoryBean> categoryList = alarmEventRedefineService.getAllAlarmCategory();
		mv.addObject("typeList", typeList);
		mv.addObject("categoryList", categoryList);
		return mv;
	}
	
	/**
	 * 告警事件列表数据
	 */
	@RequestMapping("/listForSelectEvent")
	@ResponseBody
	public Map<String, Object> listForSelectEvent(HttpServletRequest request,AlarmEventDefineBean bean){
		int moClassId = Integer.parseInt(request.getParameter("moClassId"));
		String alarmDefineIds = request.getParameter("alarmDefineIds");
		Page<AlarmEventDefineBean> page = new Page<AlarmEventDefineBean>();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil.getFlexiGridPageInfo(request);
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("moClassId", moClassId);
		params.put("alarmDefineIds", alarmDefineIds);
		params.put("alarmName", bean.getAlarmName());
		params.put("alarmTypeID", bean.getAlarmTypeID());
		params.put("categoryID", bean.getCategoryID());
		page.setParams(params);
		return alarmEventRedefineService.listForSelectEvent(page);
		
	}
	
	/**
	 * 跳转至添加资源的页面
	 */
	@RequestMapping("/toAddResMO")
	public ModelAndView toAddResMO(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("monitor/alarmMgr/alarmeventredefine/addResMo");
		return mv;
	}
	
	/**
	 * 资源类型树
	 */
	@RequestMapping("/findResClassLst")
	@ResponseBody
	public Map<String, Object> findResClassLst(){
		return alarmEventRedefineService.findResClassLst();

	}
	
	/**
	 * 获得资源列表数据
	 */
	@RequestMapping("/listForSelectRes")
	@ResponseBody
	public Map<String, Object> listForSelectRes(HttpServletRequest request,DeviceResViewBean bean){
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil .getFlexiGridPageInfo(request);
		Page<DeviceResViewBean> page = new Page<DeviceResViewBean>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("moClassIds", request.getParameter("moClassIds"));
		paramMap.put("moIds", request.getParameter("moIds"));
		paramMap.put("deviceIp", bean.getDeviceIp());
		paramMap.put("moName", bean.getMoName());
		paramMap.put("resLevel", bean.getResLevel());
		page.setParams(paramMap);
		return alarmEventRedefineService.listDeviceRes(page);
	}
	
	/**
	 * 获得所有的资源等级
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getAllResLevel")
	@ResponseBody
	public List<KeyValPair<String,String>> getAllResLevel() {
		//查询所有的资源等级
		List<KeyValPair<String,String>> pairs = new ArrayList<KeyValPair<String,String>>();
		pairs.add(new KeyValPair("-1","全部"));
		Map<Integer,String> map = DictionaryLoader.getConstantItems(String.valueOf(RESOURCE_LEVEL_ID));
		for(Integer key : map.keySet()) {
			pairs.add(new KeyValPair(key,map.get(key)));
		}
		return pairs;
	}
	
	/**
	 * 新增或编辑告警重定义
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/editEventRedefine")
	@ResponseBody
	public Map<String, Object> editEventRedefine(AlarmEventRedefineBean bean){
		Map<String, Object> result = new HashMap<String, Object>();
		//添加的资源中是否包含有效资源
		boolean isEffectiveRes = true;
		List<AlarmEventRedefineBean> definedEventLst = null;
		//判断是否所有的监测资源都是已经定义过告警事件
		Map<String, Object> effectiveResFlag = alarmEventRedefineService.getEffectiveRes(bean);
		isEffectiveRes = ((Boolean) effectiveResFlag.get("isEffectiveRes")).booleanValue();
		result.put("isEffectiveRes", isEffectiveRes);
		definedEventLst = (List<AlarmEventRedefineBean>) effectiveResFlag.get("definedEventLst");
		Map<String, Object> definedEventMap = new HashMap<String, Object>();
		definedEventMap.put("rows", definedEventLst);
		result.put("definedEventMap", definedEventMap);
		result.put("definedEventLst", definedEventLst);
		
		if(!isEffectiveRes){
			return result;
		}
		List<Integer> unDefinedMoIdLst = (List<Integer>) effectiveResFlag.get("unDefinedMoIdLst");
		Map<String, Object> editRuleMap = alarmEventRedefineService.editEventRedefine(bean);
		//新增/编辑规则、删除之前的关系
		int ruleId = Integer.parseInt(editRuleMap.get("ruleId").toString());
		bean.setRuleId(ruleId);
		//新增/编辑重定义规则信息结果
		boolean editRuleFlag = ((Boolean)editRuleMap.get("editRuleFlag")).booleanValue();
		//新增重定义规则与事件、资源、对象类型关系的结果
		boolean addRelation = false;
		
		logger.info("新增/编辑重定义规则信息结果：{}",editRuleFlag);
		
		if(editRuleFlag){
			//新增关系
			addRelation = alarmEventRedefineService.addRelation(bean,unDefinedMoIdLst);
			logger.info("新增重定义规则与事件、资源、对象类型关系的结果：{}",addRelation);
			
		}
		boolean editFlag = editRuleFlag && addRelation;
		result.put("editFlag", editFlag);
		return result;	
	}
	
	/**
	 * 校验规则名称
	 * @reture true:任务名称不存在
	 * 		   false:任务规则名称已存在
	 */
	@RequestMapping("/checkRuleName")
	@ResponseBody
	public boolean checkRuleName(AlarmEventRedefineBean bean){
		return alarmEventRedefineService.checkRuleName(bean);
	}
	
	/**
	 * 告警重定义查看页面
	 */
	@RequestMapping("/toAlarmEventRedefineDetail")
	public ModelAndView toAlarmEventRedefineDetail(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("monitor/alarmMgr/alarmeventredefine/alarmEventRedefine_detail");
		if(request.getParameter("ruleId") != null && !"".equals(request.getParameter("ruleId"))){
			int ruleId = Integer.parseInt(request.getParameter("ruleId"));
			AlarmEventRedefineBean rule = alarmEventRedefineService.getRuleByID(ruleId);
			mv.addObject("rule", rule);
			int moCLassId = alarmEventRedefineService.getRuleOfClassByRuleID(ruleId).getMoClassId();
			String moCLassName = alarmEventRedefineService.getRuleOfClassByRuleID(ruleId).getClassLable();
			mv.addObject("moClassId", moCLassId);
			mv.addObject("moClassName", moCLassName);
		}
		mv.addObject("ruleId", request.getParameter("ruleId"));
		return mv;
	}
	
	/**
	 * 初始化获得已经添加的资源
	 */
	@RequestMapping("/initSelectedRes")
	@ResponseBody
	public List<DeviceResViewBean> initSelectedRes(HttpServletRequest request){
		int ruleId = Integer.parseInt(request.getParameter("ruleId"));
		return alarmEventRedefineService.initSelectedRes(ruleId);
	}
	
	/**
	 * 初始化获得已经添加的资源
	 */
	@RequestMapping("/initSelectedEvent")
	@ResponseBody
	public Map<String, Object> initSelectedEvent(HttpServletRequest request){
		int ruleId = Integer.parseInt(request.getParameter("ruleId"));
		return alarmEventRedefineService.initSelectedEvent(ruleId);
	}
}
