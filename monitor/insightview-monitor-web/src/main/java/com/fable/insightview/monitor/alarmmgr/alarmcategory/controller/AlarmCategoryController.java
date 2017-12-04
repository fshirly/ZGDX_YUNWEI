package com.fable.insightview.monitor.alarmmgr.alarmcategory.controller;

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

import com.fable.insightview.monitor.alarmmgr.alarmcategory.entity.AlarmCategoryBean;
import com.fable.insightview.monitor.alarmmgr.alarmcategory.mapper.AlarmCategoryMapper;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;

@Controller
@RequestMapping("/monitor/alarmMgr/alarmcategory")
public class AlarmCategoryController {
	@Autowired
	AlarmCategoryMapper alarmCategoryMapper;
	
	private static final Logger log = LoggerFactory.getLogger(AlarmCategoryController.class);
	
	
	@RequestMapping("/toCategoryList")
	public  ModelAndView toCategoryList(HttpServletRequest request,HttpServletResponse response,String navigationBar){
		log.info("进入页面alarmCategory_list.jsp");	
		ModelAndView mv = new ModelAndView("monitor/alarmMgr/alarmcategory/alarmCategory_list");
		mv.addObject("navigationBar", navigationBar);
		return mv;
		
	}
	
	@RequestMapping("/listAlarmCategory")
	@ResponseBody
	public Map<String,Object> listAlarmCategory(AlarmCategoryBean vo) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
		.getFlexiGridPageInfo(request);
		
		Page<AlarmCategoryBean> page = new Page<AlarmCategoryBean>(); 
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		
		paramMap.put("categoryName", vo.getCategoryName());
		paramMap.put("alarmOID", vo.getAlarmOID());
		paramMap.put("sort", request.getParameter("sort"));
		paramMap.put("order", request.getParameter("order"));
		page.setParams(paramMap);
		
		List<AlarmCategoryBean> categoryList =  alarmCategoryMapper.queryInfoList(page);
		
		int total = page.getTotalRecord();
		log.info("*******total:"+total);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("total", total);
		result.put("rows", categoryList);
		return result;
	}
	
	/**
	 *@Description:告警分类增加页面跳转
	 *@param request
	 *@returnType ModelAndView
	 */
	@RequestMapping("/toAlarmCategoryAdd")
	public  ModelAndView toAlarmCategoryAdd(HttpServletRequest request){
		log.info("跳转到新增页面alarmCategoryAdd.jsp");
		return new ModelAndView("monitor/alarmMgr/alarmcategory/alarmCategoryAdd");
	}
	
	/**
	 *@Description:新增告警分类
	 *@param vo
	 *@returnType int
	 */
	@RequestMapping("/addAlarmCategory")
	@ResponseBody
	public int addAlarmCategory(AlarmCategoryBean vo,HttpServletRequest request){
		try {
				log.info("开始。。。执行新增方法");
				vo.setIsSystem(0);
				alarmCategoryMapper.insertSelective(vo);
				return 1;
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
	}
	
	/**
	 *@Description:告警分类修改页面跳转
	 *@param request
	 *@returnType ModelAndView
	 */
	@RequestMapping("/toAlarmCategoryModify")
	public  ModelAndView toAlarmCategoryModify(ModelMap map,AlarmCategoryBean vo){
		log.info("跳转到修改页面alarmCategoryModify.jsp");
		vo = alarmCategoryMapper.queryByID(vo.getCategoryID());
		map.put("alarmVo", vo);
		return new ModelAndView("monitor/alarmMgr/alarmcategory/alarmCategoryModify");
	}
	
	/**
	 *@Description:告警分类修改
	 *@param vo
	 *@returnType boolean
	 */
	@RequestMapping("/updateAlarmCategory")
	@ResponseBody
	public boolean updateAlarmCategory(AlarmCategoryBean vo)throws Exception{
		log.info("开始。。。执行告警分类修改方法");
		log.info("**************ID:"+vo.getCategoryID());
		int isSystem = alarmCategoryMapper.queryByID(vo.getCategoryID()).getIsSystem();
		//判断IsSystem的值，当IsSystem为1是，该告警分类为系统定义，不能删除
		if(isSystem != 1){
			alarmCategoryMapper.updateByPrimaryKey(vo);
		 return true;
		}else{
			return false;
		}
	}
	
	
	@RequestMapping("/checkIsUsed")
	@ResponseBody
	public boolean checkIsUsed(AlarmCategoryBean vo){
		log.info("删除前。。。检查告警分类是否被使用");
		int usedNum = alarmCategoryMapper.getIsUsed(vo.getCategoryID());
		log.info("告警分类是否被使用次数："+usedNum);
		if(usedNum == 0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 删除告警分类
	 */
	@RequestMapping("/delAlarmCategory")
	@ResponseBody
	public boolean delAlarmCategory(AlarmCategoryBean vo) throws Exception{
		log.info("开始。。。删除告警分类");
		log.info("isSystem:"+vo.getIsSystem());
		int isSystem = alarmCategoryMapper.queryByID(vo.getCategoryID()).getIsSystem();
		log.info("isSystem:"+vo.getIsSystem());
		//int usedNum = alarmCategoryMapper.getIsUsed(vo.getCategoryID());
		//log.info("判断是否系统自定义的IsSystem的值："+isSystem);
		if(isSystem != 1){
			alarmCategoryMapper.deleteByPrimaryKey(vo.getCategoryID());
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 *@Description:批量删除
	 *@param request
	 *@returnType Map<String,Object>
	 */
	@RequestMapping("/doBatchDel")
	@ResponseBody
	public Map<String,Object> doBatchDel(HttpServletRequest request){
		String categoryIDs =request.getParameter("categoryIDs");
		log.info("选中要批量删除的ID:"+categoryIDs);
		//获取单个ID
		String[] splitIds = categoryIDs.split(",");
		String defineName = "";
		String defineSysName = "";
		int flag = 0;
		//能够被删的ID数组
		List<Integer> delDefine = new ArrayList<Integer>();
		//不能被删保留的ID数组 
		List<Integer> reserveDefine = new ArrayList<Integer>();
		AlarmCategoryBean  alarmCategory =null;
		//1.判断是否为系统定义
		for(String strID : splitIds){
			//遍历获取选项的ID
			int categoryID = Integer.parseInt(strID);
			alarmCategory = alarmCategoryMapper.queryByID(categoryID);
			//判断是否使用
			int usedNum = alarmCategoryMapper.getIsUsed(categoryID);
			if(usedNum == 0){
				delDefine.add(categoryID);//用作系统定义判断的ID;
			}else{
				reserveDefine.add(categoryID);
				defineSysName += alarmCategory.getCategoryName()+",";
				flag = 2;
			}
		}
		
		//判断isSystem是否为系统定义,
		for(int i=0; i < delDefine.size();i++){
			int id = delDefine.get(i);
			alarmCategory = alarmCategoryMapper.queryByID(id);
			try {
				if(alarmCategory.getIsSystem() == 0){
					alarmCategoryMapper.deleteByPrimaryKey(id);//删除
				}else{
					flag = 1;
					reserveDefine.add(id);
					defineName += alarmCategory.getCategoryName()+",";
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
	 *@Description:检查告警类型名称是否存在
	 *@param vo
	 *@return 
	 */
	@RequestMapping("/checkNameUnique")
	@ResponseBody
	public boolean checkNameUnique(AlarmCategoryBean vo){
		log.info("检查分类名是否存在！");
		log.info("要检查的名称CategoryName:"+vo.getCategoryName());
		int i=alarmCategoryMapper.checkName(vo.getCategoryName());
		log.info("判断是否存在的值NUM(0：不存在；>0:存在):"+i);
		return ( i==0 )?true:false;
	}
	
	/**
	 *@Description:修改前检查告警类型名称是否存在
	 *@param vo
	 *@return 
	 */
	@RequestMapping("/checkNameUniqueBeforeUpdate")
	@ResponseBody
	public boolean checkNameUniqueBeforeUpdate(AlarmCategoryBean vo){
		log.info("检查分类名是否存在！");
		log.info("要检查的名称CategoryName:"+vo.getCategoryName());
		int i=alarmCategoryMapper.checkNameBeforeUpdate(vo);
		log.info("判断是否存在的值NUM(0：不存在；>0:存在):"+i);
		return ( i==0 )?true:false;
	}
	
	/**
	 * 告警分类详情页面跳转
	 */
	@RequestMapping("/toAlarmCategoryDetail")
	public ModelAndView toAlarmCategoryDetail(ModelMap map,AlarmCategoryBean vo)throws Exception {		
		log.info("告警类型id:"+vo.getCategoryID());
		vo = alarmCategoryMapper.queryByID(vo.getCategoryID());
		map.put("alarmVo", vo);
		return new ModelAndView("monitor/alarmMgr/alarmcategory/alarmCategoryDetail");
	}
	
	@RequestMapping("/getAllAlarmGategory")
	@ResponseBody
	public List<AlarmCategoryBean> getAllAlarmGategory(){
		return alarmCategoryMapper.getAllAlarmGategory();
	}
	
	@RequestMapping("/getAlarmCategoryInfo")
	@ResponseBody
	public AlarmCategoryBean getAlarmCategoryInfo(int categoryID){
		log.info("获得分类信息。。。。。。。。。");
		log.info("分类Id========="+categoryID);
		AlarmCategoryBean bean = alarmCategoryMapper.getAlarmGategoryById(categoryID);
		log.info("告警分类信息：标记==="+bean.getAlarmOID()+",名字===="+bean.getCategoryName());
		return bean;
	}
}
