package com.fable.insightview.monitor.alarmmgr.alarmtype.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.monitor.alarmmgr.alarmtype.mapper.AlarmTypeMapper;
import com.fable.insightview.monitor.alarmmgr.alarmtype.entity.AlarmTypeBean;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;

@Controller
@RequestMapping("/monitor/alarmMgr/alarmtype")
public class AlarmTypeController {
	@Autowired
	AlarmTypeMapper alarmTypeMapper;
	
	private static final Logger log = LoggerFactory.getLogger(AlarmTypeController.class);
	
	
	/**
	 *@Description:跳转到页面，显示列表
	 *@return 
	 *@returnType ModelAndView
	 *@author zhurt
	 */
	@RequestMapping("/toAlarmTypeList")
	public  ModelAndView toAlarmTypeList(HttpServletRequest request,HttpServletResponse response,String navigationBar){
		String flag=request.getParameter("flag");
		if(flag !=null && !"".equals(flag)){
			request.setAttribute("flag", flag);
		}else{
			request.setAttribute("flag", "doInit");
		}		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("monitor/alarmMgr/alarmtype/alarmTypeList");
		mv.addObject("navigationBar", navigationBar);
		return mv;
	}
	
	@RequestMapping("/listAlarmType")
	@ResponseBody
	public Map<String,Object> listAlarmType(AlarmTypeBean vo) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
		.getFlexiGridPageInfo(request);
		
		Page<AlarmTypeBean> page = new Page<AlarmTypeBean>(); 
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		
		paramMap.put("alarmTypeName", vo.getAlarmTypeName());
		paramMap.put("sort", request.getParameter("sort"));
		paramMap.put("order", request.getParameter("order"));
		page.setParams(paramMap);
		
		List<AlarmTypeBean> typeList =  alarmTypeMapper.queryInfoList(page);
		
		int total = page.getTotalRecord();
		log.info("*******total:"+total);
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("total", total);
		result.put("rows", typeList);
		return result;
	}
	
	
	@RequestMapping("/toAlarmTypeAdd")
	public  ModelAndView toAlarmTypeAdd(HttpServletRequest request){
		log.info("跳转到新增页面alarmTypeAdd.jsp");
		return new ModelAndView("monitor/alarmMgr/alarmtype/alarmTypeAdd");
	}
	
	@RequestMapping("/addAlarmType")
	@ResponseBody
	public int addAlarmType(AlarmTypeBean vo,HttpServletRequest request){
		try {
			System.out.println("********typeName:"+vo.getAlarmTypeName());
			vo.setIsSystem(0);
			alarmTypeMapper.insertSelective(vo);
			log.info("新增告警类型...结束");
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 *@Description:跳转到修改页面
	 *@param request
	 *@returnType ModelAndView
	 */
	@RequestMapping("/toAlarmTypeModify")
	public  ModelAndView toAlarmTypeModify(ModelMap map,AlarmTypeBean vo){
		log.info("跳转到修改页面alarmTypeModify.jsp");
		vo = alarmTypeMapper.queryByID(vo.getAlarmTypeID());
		map.put("alarmVo", vo);
		return new ModelAndView("monitor/alarmMgr/alarmtype/alarmTypeModify");
	}
	
	@RequestMapping("/updateAlarmType")
	@ResponseBody
	public boolean updateAlarmType(AlarmTypeBean vo)throws Exception{
		log.info("**************ID:"+vo.getAlarmTypeID());
		int isSystem = alarmTypeMapper.queryByID(vo.getAlarmTypeID()).getIsSystem();
		System.out.println(">>>>>>IsSystem<<<<<<:"+isSystem);
		if(isSystem != 1){
			alarmTypeMapper.updateByPrimaryKey(vo);
		 return true;
		}else{
			return false;
		}
	}
	
	@RequestMapping("/checkIsUsed")
	@ResponseBody
	public boolean checkIsUsed(AlarmTypeBean vo){
		log.info("删除前。。。检查告警类型是否被使用");
		int usedNum = alarmTypeMapper.getIsUsed(vo.getAlarmTypeID());
		log.info("告警类型是否被使用次数："+usedNum);
		if(usedNum == 0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 *@Description:删除告警类型
	 *@param vo
	 *@returnType boolean
	 */
	@RequestMapping("/delAlarmType")
	@ResponseBody
	public boolean delAlarmType(AlarmTypeBean vo)throws Exception{
		log.info("开始。。。删除告警类型");
		int isSystem = alarmTypeMapper.queryByID(vo.getAlarmTypeID()).getIsSystem();
//		int usedNum = alarmTypeMapper.getIsUsed(vo.getAlarmTypeID());
//		log.info("判断是否系统自定义的IsSystem的值："+isSystem+", \t告警类型被使用次数useNUM: "+usedNum);
		if(isSystem != 1 ){
			alarmTypeMapper.deleteByPrimaryKey(vo.getAlarmTypeID());
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 *@Description:检查告警类型名称是否存在
	 *@param vo
	 *@return 
	 */
	@RequestMapping("/checkNameUnique")
	@ResponseBody
	public boolean checkNameUnique(AlarmTypeBean vo){
		log.info("检查类型名是否存在！");
		log.info("要检查的名称alarmTypeName:"+vo.getAlarmTypeName());
		int i=alarmTypeMapper.checkName(vo);
		log.info("*****:"+i);
		return ( i==0 )?true:false;
	}
	
	/**
	 *@Description:修改前检查告警类型名称是否存在
	 *@param vo
	 *@return 
	 */
	@RequestMapping("/checkNameBeforeUpdate")
	@ResponseBody
	public boolean checkNameBeforeUpdate(AlarmTypeBean vo){
		log.info("检查类型名是否存在！");
		log.info("要检查的名称alarmTypeName:"+vo.getAlarmTypeName());
		int i=alarmTypeMapper.checkNameBeforeUpdate(vo);
		log.info("*****:"+i);
		return ( i==0 )?true:false;
	}
	
	/**
	 *@Description:批量删除
	 *@param request
	 */
	@RequestMapping("/doBatchDel")
	@ResponseBody
	public Map<String,Object> doBatchDel(HttpServletRequest request){
		String alarmTypeIDs =request.getParameter("alarmTypeIDs");
		log.info("选中要批量删除的ID:"+alarmTypeIDs);
		//获取单个ID
		String[] splitIds = alarmTypeIDs.split(",");
		String defineName = "";
		String defineSysName = "";
		int flag = 0;
		/* 能够被删的ID数组 */
		List<Integer> delDefine = new ArrayList<Integer>();
		/* 不能被删保留的ID数组 */
		List<Integer> reserveDefine = new ArrayList<Integer>();
		AlarmTypeBean alarmType =null;
		//1.判断是否被引用
		for(String strID : splitIds){
			//遍历获取选项的ID
			int alarmTypeID = Integer.parseInt(strID);
			alarmType = alarmTypeMapper.queryByID(alarmTypeID);
			
			int usedNum = alarmTypeMapper.getIsUsed(alarmTypeID);
			if(usedNum == 0){
				delDefine.add(alarmTypeID);
			}else{
				flag = 2;
				reserveDefine.add(alarmTypeID);
				defineSysName += alarmType.getAlarmTypeName()+",";
			}
		}
		//2.判断是否是否为系统定义
		for(int i=0; i < delDefine.size();i++){
			int id = delDefine.get(i);
			alarmType = alarmTypeMapper.queryByID(id);
			try {
				if(alarmType.getIsSystem() == 0){
					alarmTypeMapper.deleteByPrimaryKey(id);
				}else{
					flag = 1;
					reserveDefine.add(id);
					defineName += alarmType.getAlarmTypeName()+",";
				}
			} catch (Exception e) {
				log.error("删除异常："+e.getMessage());
				
			}
		}
		Map<String,Object> result  = new HashMap<String,Object>();
		
		result.put("flag", flag);
		return result;
	}
	
	/**
	 * 告警类型详情页面跳转
	 */
	@RequestMapping("/toAlarmTypeDetail")
	public ModelAndView toAlarmTypeDetail(ModelMap map,AlarmTypeBean vo)throws Exception {		
		log.info("告警类型id:"+vo.getAlarmTypeID());
		vo = alarmTypeMapper.queryByID(vo.getAlarmTypeID());
		map.put("alarmVo", vo);
		return new ModelAndView("monitor/alarmMgr/alarmtype/alarmTypeDetail");
	}
	
	@RequestMapping("/getAllAlarmType")
	@ResponseBody
	public List<AlarmTypeBean> findAlarmType(){
		return alarmTypeMapper.getAllAlarmType();
		 
	}
}
