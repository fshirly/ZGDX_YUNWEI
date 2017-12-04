package com.fable.insightview.monitor.alarmmgr.alarmlevel.controller;

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

import com.fable.insightview.monitor.alarmmgr.alarmlevel.entity.AlarmLevelBean;
import com.fable.insightview.monitor.alarmmgr.alarmlevel.mapper.AlarmLevelMapper;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;

@Controller
@RequestMapping("/monitor/alarmMgr/alarmlevel")
public class AlarmLevelController {
	@Autowired
	AlarmLevelMapper alarmLevelMapper;
	
	private static final Logger log = LoggerFactory.getLogger(AlarmLevelController.class);
	
	
	/**
	 *@Description:跳转到页面，显示列表
	 *@return 
	 *@returnLevel ModelAndView
	 */
	@RequestMapping("/toAlarmLevelList")
	public  ModelAndView toAlarmLevelList(HttpServletRequest request,HttpServletResponse response,String navigationBar){
		String flag=request.getParameter("flag");
		if(flag !=null && !"".equals(flag)){
			request.setAttribute("flag", flag);
		}else{
			request.setAttribute("flag", "doInit");
		}		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("monitor/alarmMgr/alarmlevel/alarmLevelList");
		mv.addObject("navigationBar", navigationBar);
		return mv;
	}
	
	/**
	 *@Description:列表数据显示
	 *@param vo
	 *@returnType Map<String,Object>
	 */
	@RequestMapping("/listAlarmLevel")
	@ResponseBody
	public Map<String,Object> listAlarmLevel(AlarmLevelBean vo) {
		log.info("显示页面列表信息。。。。");
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
		.getFlexiGridPageInfo(request);
		
		Page<AlarmLevelBean> page = new Page<AlarmLevelBean>(); 
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		
		//设置查询参数
		Map<String,Object>  paramMap = new HashMap<String,Object>();
		paramMap.put("alarmLevelName", vo.getAlarmLevelName());
		paramMap.put("sort", request.getParameter("sort"));
		paramMap.put("order", request.getParameter("order"));
		page.setParams(paramMap);
		//查询分页数据
		List<AlarmLevelBean> levelList =  alarmLevelMapper.queryInfoList(page);
		
		int total = page.getTotalRecord();
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("total", total);
		result.put("rows", levelList);
		log.info("显示页面信息结束。。。。");
		return result;
	}
	
	@RequestMapping("/toAlarmLevelAdd")
	public  ModelAndView toAlarmLevelAdd(HttpServletRequest request,ModelMap map){
		log.info("跳转到新增页面alarmLevelAdd.jsp");
		//获取告警等级小图标数据
		List<AlarmLevelBean> iconList = alarmLevelMapper.queryLevelInfo();
		log.info("获取下拉列表数据...结束");
		map.put("iconList",iconList);
		return new ModelAndView("monitor/alarmMgr/alarmlevel/alarmLevelAdd");
	}
	
	@RequestMapping("/addAlarmLevel")
	@ResponseBody
	public int addAlarmLevel(AlarmLevelBean vo,HttpServletRequest request){
		try {
			vo.setIsSystem(0);//用户自定义
			System.out.println("********LevelName:"+vo.getAlarmLevelName());
			System.out.println("********LevelColor:"+vo.getLevelColor());
			System.out.println("********LevelIcon:"+vo.getLevelIcon());
			String levelColor = vo.getLevelColor();
			
			if("#ff0000".equals(levelColor)){
				vo.setLevelColorName("红色");
				vo.setLevelIcon("1.png");
			}else if("#ff9900".equals(levelColor)){
				vo.setLevelColorName("橙色");
				vo.setLevelIcon("2.png");
			}else if("#ffff00".equals(levelColor)){
				vo.setLevelColorName("黄色");
				vo.setLevelIcon("3.png");
			}else if("#0000ff".equals(levelColor)){
				vo.setLevelColorName("蓝色");
				vo.setLevelIcon("4.png");
			}else if("#009900".equals(levelColor)){
				vo.setLevelColorName("绿色");
				vo.setLevelIcon("1.png");
			}else if("#c0c0c0".equals(levelColor)){
				vo.setLevelColorName("灰色");
				vo.setLevelIcon("5.png");
			}
			
			alarmLevelMapper.insertSelective(vo);
			log.info("新增告警等级...结束");
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 *@Description:跳转到告警等级修改页面
	 *@param request
	 *@returnLevel ModelAndView
	 */
	@RequestMapping("/toAlarmLevelModify")
	public  ModelAndView toAlarmLevelModify(ModelMap map,AlarmLevelBean vo){
		log.info("跳转到修改页面alarmLevelModify.jsp");
		vo = alarmLevelMapper.queryByID(vo.getAlarmLevelID());
		map.put("alarmVo",vo);
		
		List<AlarmLevelBean> iconList = alarmLevelMapper.queryLevelInfo();
		log.info("获取下拉列表数据...结束");
		map.put("iconList",iconList);
		return new ModelAndView("monitor/alarmMgr/alarmlevel/alarmLevelModify");
	}
	
	/**
	 *@Description:修改告警等级
	 *@param vo
	 */
	@RequestMapping("/updateAlarmLevel")
	@ResponseBody
	public boolean updateAlarmLevel(AlarmLevelBean vo,HttpServletRequest request){
		log.info("**************ID:"+vo.getAlarmLevelID());
		int isSystem = alarmLevelMapper.queryByID(vo.getAlarmLevelID()).getIsSystem();
		System.out.println(">>>>>>IsSystem<<<<<<:"+isSystem);
		//try {
		String levelColor = vo.getLevelColor();
		System.out.println("********LevelColor:"+vo.getLevelColor());
		
		if("#ff0000".equals(levelColor)){
			vo.setLevelColorName("红色");
			vo.setLevelIcon("1.png");
		}else if("#ff9900".equals(levelColor)){
			vo.setLevelColorName("橙色");
			vo.setLevelIcon("2.png");
		}else if("#ffff00".equals(levelColor)){
			vo.setLevelColorName("黄色");
			vo.setLevelIcon("3.png");
		}else if("#0000ff".equals(levelColor)){
			vo.setLevelColorName("蓝色");
			vo.setLevelIcon("4.png");
		}else if("#009900".equals(levelColor)){
			vo.setLevelColorName("绿色");
			vo.setLevelIcon("1.png");
		}else if("#c0c0c0".equals(levelColor)){
			vo.setLevelColorName("灰色");
			vo.setLevelIcon("5.png");
		}
		
		System.out.println(vo.getLevelColorName());
		
		if(isSystem != 1){
			alarmLevelMapper.updateByPrimaryKey(vo);
		 return true;
		}else{
			return false;
//		}}catch (Exception e) {
//			log.error("修改错误", e.getMessage());
//			return false;
		}
	}
	
	@RequestMapping("/checkIsUsed")
	@ResponseBody
	public boolean checkIsUsed(AlarmLevelBean vo){
		log.info("删除前。。。检查告警等级是否被使用");
		int usedNum = alarmLevelMapper.getIsUsed(vo.getAlarmLevelID());
		log.info("告警等级是否被使用次数："+usedNum);
		if(usedNum == 0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 *@Description:删除告警类型
	 *@param vo
	 *@return
	 *@returnLevel boolean
	 */
	@RequestMapping("/delAlarmLevel")
	@ResponseBody
	public boolean delAlarmLevel(AlarmLevelBean vo)throws Exception{
		System.out.println("********"+vo.getAlarmLevelID());
		int isSystem = alarmLevelMapper.queryByID(vo.getAlarmLevelID()).getIsSystem();
		System.out.println(">>>>>>IsSystem<<<<<<:"+isSystem);
		if(isSystem != 1){
			alarmLevelMapper.deleteByPrimaryKey(vo.getAlarmLevelID());
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 *@Description:批量删除
	 *@param request
	 */
	@RequestMapping("/doBatchDel")
	@ResponseBody
	public Map<String,Object> doBatchDel(HttpServletRequest request){
		String alarmLevelIDs =request.getParameter("alarmLevelIDs");
		log.info("选中要批量删除的ID:"+alarmLevelIDs);
		//获取单个ID
		String[] splitIds = alarmLevelIDs.split(",");
		String defineName = "";
		String defineSysName = "";
		int flag = 0;
		/* 能够被删的ID数组 */
		List<Integer> delDefine = new ArrayList<Integer>();
		/* 不能被删保留的ID数组 */
		List<Integer> reserveDefine = new ArrayList<Integer>();
		AlarmLevelBean alarmLevel =null;
		//1.//判断是否使用
		for(String strID : splitIds){
			//遍历获取选项的ID
			int alarmLevelID = Integer.parseInt(strID);
			alarmLevel = alarmLevelMapper.queryByID(alarmLevelID);
			
			int usedNum = alarmLevelMapper.getIsUsed(alarmLevelID);
			if(usedNum == 0){
				delDefine.add(alarmLevelID);
			}else{
				flag = 2;
				reserveDefine.add(alarmLevelID);
				defineSysName += alarmLevel.getAlarmLevelName()+",";
			}
		}
			
		//判断isSystem是否为系统定义,
		for(int i=0; i < delDefine.size();i++){
			int id = delDefine.get(i);
			alarmLevel = alarmLevelMapper.queryByID(id);
			try {
				if(alarmLevel.getIsSystem() == 0){
					alarmLevelMapper.deleteByPrimaryKey(id);
				}else{
					flag = 1;
					reserveDefine.add(id);
					defineName += alarmLevel.getAlarmLevelName()+",";
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
	 *@Description:检查告警等级是否存在
	 *@param vo
	 *@return 
	 */
	@RequestMapping("/checkNameUnique")
	@ResponseBody
	public boolean checkNameUnique(AlarmLevelBean vo){
		log.info("检查等级名是否存在！");
		log.info("要检查的名称alarmLevelName:"+vo.getAlarmLevelName());
		int i= alarmLevelMapper.checkName(vo.getAlarmLevelName());
		log.info("*****:"+i);
		return ( i==0  )?true:false;
	}
	
	/**
	 *@Description:修改前检查告警等级是否存在
	 *@param vo
	 *@return 
	 */
	@RequestMapping("/checkNameUniqueBeforeUpdate")
	@ResponseBody
	public boolean checkNameUniqueBeforeUpdate(AlarmLevelBean vo){
		log.info("检查等级名是否存在！");
		log.info("要检查的名称alarmLevelName:"+vo.getAlarmLevelName());
		int i= alarmLevelMapper.checkNameBeforeUpdate(vo);
		log.info("*****:"+i);
		return ( i==0  )?true:false;
	}
	
	
	/**
	 * 告警类型详情页面跳转
	 */
	@RequestMapping("/toAlarmLevelDetail")
	public ModelAndView toAlarmLevelDetail(ModelMap map,AlarmLevelBean vo)throws Exception {		
		log.info("告警等级id:"+vo.getAlarmLevelID());
		vo = alarmLevelMapper.queryByID(vo.getAlarmLevelID());
		map.put("alarmVo", vo);
		return new ModelAndView("monitor/alarmMgr/alarmlevel/alarmLevelDetail");
	}

	@RequestMapping("/getAllAlarmLevel")
	@ResponseBody
	public List<AlarmLevelBean> getAllAlarmLevel(){
		return alarmLevelMapper.getAllAlarmLevel();
	}
}
