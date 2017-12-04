package com.fable.insightview.monitor.alarmmgr.alarmview.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.monitor.alarmmgr.alarmactive.mapper.AlarmActiveMapper;
import com.fable.insightview.monitor.alarmmgr.alarmview.entity.ParamterVo;
import com.fable.insightview.monitor.alarmmgr.alarmview.mapper.AlarmViewMapper;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmEventDefineBean;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmLevelInfo;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmTypeInfo;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmViewColCfgInfo;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmViewColDefInfo;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmViewFilterInfo;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmViewInfo;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmViewSoundCfgInfo;
import com.fable.insightview.monitor.alarmmgr.entity.MOSourceBean;
import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.page.Page;

/**
 * @Description:   告警视图自定义控制器
 * @author         zhengxh
 * @Date           2014-7-21
 */
@Controller
@RequestMapping("/monitor/alarmView")
public class AlarmViewController {
	
	@Autowired
	private AlarmViewMapper alarmViewMapper;
	
	@Autowired
	private AlarmActiveMapper alarmActiveMapper;
	
	private final static Logger logger = LoggerFactory.getLogger(AlarmViewController.class);
	
	/**
	 * 告警详情页面跳转
	 */
	@RequestMapping("/toAlarmView")
	public ModelAndView toAlarmView(String navigationBar)throws Exception {			
		ModelAndView mv =  new ModelAndView("monitor/alarmMgr/alarmview/alarmView_list");
		mv.addObject("navigationBar", navigationBar);
		return mv;
	}
	
	/**
	 * 获取页面显示数据
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listAlarmView")
	@ResponseBody
	public Map<String, Object> listAlarmView(AlarmViewInfo vo, HttpServletRequest request)throws Exception{
		logger.info("开始...获取页面显示数据");
		//获取当前用户对象
		SecurityUserInfoBean userVo = (SecurityUserInfoBean)request.getSession().getAttribute("sysUserInfoBeanOfSession");
		vo.setUserID(Integer.parseInt(userVo.getId()+""));
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil.getFlexiGridPageInfo(request);
		Page<AlarmViewInfo> page=new Page<AlarmViewInfo>(); 
		//设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		//设置查询参数
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("cfgName", vo.getCfgName());		
		paramMap.put("userID", vo.getUserID());
		page.setParams(paramMap);
		//查询分页数据
		List<AlarmViewInfo> list = alarmViewMapper.queryList(page);
		//查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String,Object> result = new HashMap<String,Object>();		
		//设置至前台显示
		result.put("total",totalCount );
		result.put("rows",list);
		logger.info("结束...获取页面显示数据");
		return result;
	}
	
	/**
	 * 新增页面跳转	
	 */
	@RequestMapping("/toAddAlarmView")
	public ModelAndView toAddAlarmView()throws Exception {		
		return new ModelAndView("monitor/alarmMgr/alarmview/alarmView_add");
	}
	
	/**
	 * 获取显示列子页面显示数据
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listColCfg")
	@ResponseBody
	public Map<String, Object> listColCfg(AlarmViewColCfgInfo vo, HttpServletRequest request)throws Exception{
		logger.info("开始...获取页面显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil.getFlexiGridPageInfo(request);
		Page<AlarmViewColCfgInfo> page=new Page<AlarmViewColCfgInfo>(); 
		//设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		//设置查询参数
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("viewCfgID", vo.getViewCfgID());
		page.setParams(paramMap);
		//查询分页数据
		List<AlarmViewColCfgInfo> list = alarmViewMapper.queryColCfgList(page);
		//查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String,Object> result = new HashMap<String,Object>();		
		//设置至前台显示
		result.put("total",totalCount );
		result.put("rows",list);
		logger.info("结束...获取页面显示数据");
		return result;
	}
	
	/**
	 * 获取告警声音子页面显示数据
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listSound")
	@ResponseBody
	public Map<String, Object> listSound(AlarmViewSoundCfgInfo vo, HttpServletRequest request)throws Exception{
		logger.info("开始...获取页面显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil.getFlexiGridPageInfo(request);
		Page<AlarmViewSoundCfgInfo> page=new Page<AlarmViewSoundCfgInfo>(); 
		//设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		//设置查询参数
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("viewCfgID", vo.getViewCfgID());
		page.setParams(paramMap);
		//查询分页数据
		List<AlarmViewSoundCfgInfo> list = alarmViewMapper.querySoundList(page);
		//查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String,Object> result = new HashMap<String,Object>();		
		//设置至前台显示
		result.put("total",totalCount );
		result.put("rows",list);
		logger.info("结束...获取页面显示数据");
		return result;
	}
	
	/**
	 * 获取过滤条件子页面显示数据
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listFilter")
	@ResponseBody
	public Map<String, Object> listFilter(AlarmViewFilterInfo vo, HttpServletRequest request)throws Exception{
		logger.info("开始...获取页面显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil.getFlexiGridPageInfo(request);
		Page<AlarmViewFilterInfo> page=new Page<AlarmViewFilterInfo>(); 
		//设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		//设置查询参数
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("viewCfgID", vo.getViewCfgID());
		page.setParams(paramMap);
		//查询分页数据
		List<AlarmViewFilterInfo> list = alarmViewMapper.queryFilterList(page);
		//查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String,Object> result = new HashMap<String,Object>();		
		//设置至前台显示
		result.put("total",totalCount );
		result.put("rows",list);
		logger.info("结束...获取页面显示数据");
		return result;
	}	
	
	/**
	 * 初始化告警级别下拉框数据
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/initAlarmLevel")
	@ResponseBody
	public Map<String, Object> initAlarmLevel() throws Exception {
		logger.info("开始...初始化告警级别数据");
		List<AlarmLevelInfo> alarmLevelLst = alarmActiveMapper.queryLevelInfo();
		String alarmLstJson = JsonUtil.toString(alarmLevelLst);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("alarmLstJson", alarmLstJson);
		logger.info("结束...初始化告警级别数据");
		return result;
	}
	
	/**
	 * 过滤条件：告警级别
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/filterAlarmLevel")
	@ResponseBody
	public Map<String, Object> filterAlarmLevel(HttpServletRequest request) throws Exception {
		logger.info("开始...获取页面告警级别显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil.getFlexiGridPageInfo(request);
		Page<AlarmLevelInfo> page=new Page<AlarmLevelInfo>(); 
		//设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		//设置查询参数
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("viewCfgID", request.getParameter("viewCfgID"));
		paramMap.put("alarmLevelName", request.getParameter("alarmLevelName"));
		page.setParams(paramMap);
		//查询分页数据
		List<AlarmLevelInfo> list = alarmActiveMapper.queryAlarmLevelList(page);
		//查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String,Object> result = new HashMap<String,Object>();		
		//设置至前台显示
		result.put("total",totalCount );
		result.put("rows",list);
		logger.info("结束...获取页面告警级别显示数据");
		return result;
	}
	
	/**
	 * 过滤条件：告警类型
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/filterAlarmType")
	@ResponseBody
	public Map<String, Object> filterAlarmType(HttpServletRequest request) throws Exception {
		logger.info("开始...获取页面告警类型显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil.getFlexiGridPageInfo(request);
		Page<AlarmTypeInfo> page=new Page<AlarmTypeInfo>(); 
		//设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		//设置查询参数
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("viewCfgID", request.getParameter("viewCfgID"));
		paramMap.put("alarmTypeName", request.getParameter("alarmTypeName"));
		page.setParams(paramMap);
		//查询分页数据
		List<AlarmTypeInfo> list = alarmActiveMapper.queryAlarmTypeList(page);
		//查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String,Object> result = new HashMap<String,Object>();		
		//设置至前台显示
		result.put("total",totalCount );
		result.put("rows",list);
		logger.info("结束...获取页面告警类型显示数据");
		return result;
	}
	
	/**
	 * 过滤条件：告警源对象
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/filterMOSource")
	@ResponseBody
	public Map<String, Object> filterMOSource(HttpServletRequest request) throws Exception {
		logger.info("开始...获取页面告警源对象显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil.getFlexiGridPageInfo(request);
		Page<MOSourceBean> page=new Page<MOSourceBean>(); 
		//设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		//设置查询参数
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("viewCfgID", request.getParameter("viewCfgID"));
		paramMap.put("moName", request.getParameter("moName"));
		paramMap.put("deviceIP", request.getParameter("deviceIP"));
		page.setParams(paramMap);
		//查询分页数据
		List<MOSourceBean> list = alarmViewMapper.queryMOSourceList(page);
		//查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String,Object> result = new HashMap<String,Object>();		
		//设置至前台显示
		result.put("total",totalCount );
		result.put("rows",list);
		logger.info("结束...获取页面告警源对象显示数据");
		return result;
	}
	
	/**
	 * 过滤条件：告警事件
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/filterAlarmEvent")
	@ResponseBody
	public Map<String, Object> filterAlarmEvent(HttpServletRequest request) throws Exception {
		logger.info("开始...获取页面告警事件显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil.getFlexiGridPageInfo(request);
		Page<AlarmEventDefineBean> page=new Page<AlarmEventDefineBean>(); 
		//设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		//设置查询参数
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("viewCfgID", request.getParameter("viewCfgID"));
		paramMap.put("alarmName", request.getParameter("alarmName"));
		page.setParams(paramMap);
		//查询分页数据
		List<AlarmEventDefineBean> list = alarmViewMapper.queryEventList(page);
		//查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String,Object> result = new HashMap<String,Object>();		
		//设置至前台显示
		result.put("total",totalCount );
		result.put("rows",list);
		logger.info("结束...获取页面告警事件显示数据");
		return result;
	}
	
	/**
	 * 增加信息
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addAlarmView")
	@ResponseBody
	public Map<String,Object> addAlarmView(AlarmViewInfo vo,HttpServletRequest request){
		logger.info("开始...增加AlarmViewInfo");
		//获取当前用户对象
		Map<String,Object> result = new HashMap<String,Object>();
		SecurityUserInfoBean userVo = (SecurityUserInfoBean)request.getSession().getAttribute("sysUserInfoBeanOfSession");
		vo.setUserID(Integer.parseInt(userVo.getId()+""));
		vo.setIsSystem(0);//表示用户自定义
		/*判断视图是否选择默认,如是需将当前用户的其它默认视图设为否 */
		if(vo.getUserDefault().equals(1)){
			alarmViewMapper.updateUserDefault(vo);
		}
		
		if(vo.getDefaultRows() == null){
			vo.setDefaultRows(-1);
		}
		
		if(vo.getDefaultInterval() == null){
			vo.setDefaultInterval(-1);
		}
		
		try {
			alarmViewMapper.insertInfo(vo);
		} catch (Exception e) {
			logger.error("插入异常："+e.getMessage(),vo);
			vo.setViewCfgID(-1);//表示异常
		}
		
		result.put("flag", vo.getViewCfgID());
		logger.info("结束...增加AlarmViewInfo");
		return result ;
	}
	
	/**
	 * 更新视图信息
	 * @param vo
	 * @return
	 */
	@RequestMapping("/updateAlarmView")
	@ResponseBody
	public boolean updateAlarmView(AlarmViewInfo vo,HttpServletRequest request){
		logger.info("开始...修改AlarmViewInfo");
		SecurityUserInfoBean userVo = (SecurityUserInfoBean)request.getSession().getAttribute("sysUserInfoBeanOfSession");
		vo.setUserID(Integer.parseInt(userVo.getId()+""));
		vo.setIsSystem(0);//表示用户自定义
		/*判断视图是否选择默认,如是需将当前用户的其它默认视图设为否 */
		if(vo.getUserDefault().equals(1)){
			alarmViewMapper.updateUserDefault(vo);
		}
		
		if(vo.getDefaultRows() == null){
			vo.setDefaultRows(-1);
		}
		
		if(vo.getDefaultInterval() == null){
			vo.setDefaultInterval(-1);
		}
		
		try {
			alarmViewMapper.updateInfo(vo);
		} catch (Exception e) {
			logger.error("修改异常："+e.getMessage(),vo);
			return false;
		}
		
		logger.info("结束...修改AlarmViewInfo");
		return true ;
	}
	
	
	/**
	 * 删除信息
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delAlarmView")
	@ResponseBody
	public boolean delAlarmView(AlarmViewInfo vo){
		logger.info("开始...删除AlarmViewInfo");
		logger.debug("delete by param id={}",vo.getViewCfgID());
		
		try {
			/*因为有外键关系的子表,因此先删子表*/
			ParamterVo paramVo = new ParamterVo();
			paramVo.setKey("viewCfgID");
			paramVo.setVal(vo.getViewCfgID()+"");
			alarmViewMapper.deleteColCfgInfo(paramVo);
			alarmViewMapper.deleteFilterInfo(paramVo);
			alarmViewMapper.deleteSoundInfo(paramVo);
			//删主表
			alarmViewMapper.deleteInfo(vo.getViewCfgID());
		} catch (Exception e) {
			logger.error("删除异常："+e.getMessage(),vo);
			return false;
		}
		
		logger.info("结束...删除AlarmViewInfo");
		return true ;
	}
	
	/**
	 * 批量删除信息
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delBathAlarmView")
	@ResponseBody
	public boolean delBathAlarmView(@RequestParam("id") String id){
		logger.info("开始...批量删除AlarmViewInfo");
		logger.debug("delete by param id={}",id);
		
		try {
			/*因为有外键关系的子表,因此先删子表*/
			ParamterVo paramVo = new ParamterVo();
			paramVo.setKey("viewCfgID");			
			String arr[] = id.split(",");
			for(int i=0; i<arr.length; i++){
				paramVo.setVal(arr[i]);
				alarmViewMapper.deleteColCfgInfo(paramVo);
				alarmViewMapper.deleteFilterInfo(paramVo);
				alarmViewMapper.deleteSoundInfo(paramVo);
				//删主表
				alarmViewMapper.deleteInfo(Integer.parseInt(arr[i]));
			}
		} catch (Exception e) {
			logger.error("删除异常："+e.getMessage());
			return false;
		}
		
		logger.info("结束...批量删除AlarmViewInfo");
		return true ;
	}
	
	
	
	/**
	 * 根据cfgID查找信息
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findCfgDlg")
	@ResponseBody
	public AlarmViewColCfgInfo findCfgDlg(AlarmViewColCfgInfo vo)throws Exception {
		logger.info("传递参数cfgID={}",vo.getCfgID());
		vo = alarmViewMapper.queryColCfgInfoById(vo);
		return vo;
	}
	
	/**
	 * 修改列信息
	 * @param vo
	 * @return
	 */
	@RequestMapping("/updateCfgDlg")
	@ResponseBody
	public boolean updateCfgDlg(AlarmViewColCfgInfo vo){
		logger.info("开始...修改AlarmViewColCfgInfo");
		
		try {
			alarmViewMapper.updateCfgDlg(vo);
		} catch (Exception e) {
			logger.error("修改异常："+e.getMessage(),vo);
			return false;
		}
		
		logger.info("结束...修改AlarmViewColCfgInfo");
		return true ;
	}
	
	/**
	 * 增加列
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addColCfgDlg")
	@ResponseBody
	public boolean addColCfgDlg(@RequestParam("id") String id,@RequestParam("viewCfgID") Integer viewCfgID){
		logger.info("开始...增加AlarmViewColCfgInfo");
		//查询所有列定义
		List<AlarmViewColDefInfo> colDefList = alarmViewMapper.queryColDefList();
		
		int size = colDefList.size();
		AlarmViewColCfgInfo cfgVo = new AlarmViewColCfgInfo();
		cfgVo.setViewCfgID(viewCfgID);
		String arr[] = id.split(",");
		
		try {
			for(int i=0;i<size;i++){
				AlarmViewColDefInfo defVo = colDefList.get(i);
				cfgVo.setColID(defVo.getColID());
				if(isExistsArr(arr,defVo.getColID()+"") && 
						alarmViewMapper.queryColCfgInfoByColID(cfgVo) == null){//判断是否需要增加该记录										
					cfgVo.setColWidth(defVo.getColWidth());
					cfgVo.setIsVisible(defVo.getIsVisible());
					cfgVo.setColValueOrder(defVo.getColValueOrder());
					cfgVo.setColOrder(defVo.getColValueOrder());
					alarmViewMapper.insertColCfg(cfgVo);
				}
			}
		} catch (Exception e) {
			logger.error("插入异常："+e.getMessage());
			return false;
		}
		
		logger.info("结束...增加AlarmViewColCfgInfo");
		return true ;
	}
	
	/**
	 * 判断一个字符串是否存在一个数组中
	 * @param arr
	 * @param id
	 * @return
	 */
	public boolean isExistsArr(String[] arr,String id){
		for(String temp:arr){
			if(temp.equals(id)){
				return true;
			}
		}
		return false;
	}	
	
	/**
	 * 删除列
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delColCfgDlg")
	@ResponseBody
	public boolean delColCfgDlg(@RequestParam("cfgID") String cfgID){
		logger.info("开始...删除AlarmViewColCfgInfo");
		
		try {
			ParamterVo paramVo = new ParamterVo();
			paramVo.setKey("cfgID");
			paramVo.setVal(cfgID);
			alarmViewMapper.deleteColCfgInfo(paramVo);
		} catch (Exception e) {
			logger.error("删除异常："+e.getMessage());
			return false;
		}
		
		logger.info("结束...删除AlarmViewColCfgInfo");
		return true ;
	}
	
	/**
	 * 增加过滤条件
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addFilterDlg")
	@ResponseBody
	public boolean addFilterDlg(AlarmViewFilterInfo vo){
		logger.info("开始...增加AlarmViewFilterInfo");
		String arr[] = vo.getFilterValeName().split(",");
		
		try {
			for(int i=0;i<arr.length;i++){
				vo.setFilterKeyValue(Integer.parseInt(arr[i]));
				alarmViewMapper.insertFilterInfo(vo);
			}
		} catch (Exception e) {
			logger.error("插入异常："+e.getMessage(),vo);
			return false;
		}
		
		logger.info("结束...增加AlarmViewFilterInfo");
		return true ;
	}
	
	/**
	 * 删除过滤条件
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delFilterDlg")
	@ResponseBody
	public boolean delFilterDlg(AlarmViewFilterInfo vo){
		logger.info("开始...删除AlarmViewFilterInfo");
		
		try {
			ParamterVo paramVo = new ParamterVo();
			paramVo.setKey("filterID");
			paramVo.setVal(vo.getFilterID()+"");
			alarmViewMapper.deleteFilterInfo(paramVo);
		} catch (Exception e) {
			logger.error("删除异常："+e.getMessage(),vo);
			return false;
		}
		
		logger.info("结束...删除AlarmViewFilterInfo");
		return true ;
	}
	
	/**
	 * 声音页面新增跳转
	 * @param vo
	 * @param map
	 * @return
	 * @throws Exception
	 */
	 
	@RequestMapping("/toAddSoundDlg")
	public ModelAndView toAddSoundDlg(AlarmViewSoundCfgInfo vo,ModelMap map)throws Exception {
		logger.info("传递参数视图viewCfgID={}",vo.getViewCfgID());
		List<AlarmLevelInfo> alarmLevelLst = alarmActiveMapper.queryLevelInfo();
		
		map.put("viewCfgID", vo.getViewCfgID());
		map.put("alarmLevelLst", alarmLevelLst);
		return new ModelAndView("monitor/alarmMgr/alarmview/alarmView_soundAdd");
	}
	
	/**
	 * 增加声音
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addSoundDlg")
	@ResponseBody
	public Map<String,Object> addSoundDlg(AlarmViewSoundCfgInfo vo){
		logger.info("开始...增加AlarmViewSoundCfgInfo");
		String flag = "1";
		
		try {
			AlarmViewSoundCfgInfo soundVo = alarmViewMapper.checkSoundLevelInfo(vo);
			if(soundVo == null){
				alarmViewMapper.insertSoundInfo(vo);
			}else{
				flag = "2";
			}			
		} catch (Exception e) {
			logger.error("插入异常："+e.getMessage(),vo);
			flag = "3";
		}
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("flag", flag);
		
		logger.info("结束...增加AlarmViewSoundCfgInfo");
		return result ;
	}
	
	/**
	 * 删除声音
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delSoundDlg")
	@ResponseBody
	public boolean delSoundDlg(AlarmViewSoundCfgInfo vo){
		logger.info("开始...删除AlarmViewColCfgInfo");
		
		try {
			ParamterVo paramVo = new ParamterVo();
			paramVo.setKey("cfgID");
			paramVo.setVal(vo.getCfgID()+"");
			alarmViewMapper.deleteSoundInfo(paramVo);
		} catch (Exception e) {
			logger.error("删除异常："+e.getMessage(),vo);
			return false;
		}
		
		logger.info("结束...删除AlarmViewColCfgInfo");
		return true ;
	}
	
	/**
	 * 告警级别页面弹出
	 */
	@RequestMapping("/toSelectAlarmLevel")
	public ModelAndView toSelectAlarmLevel(HttpServletRequest request, ModelMap map)throws Exception {
		String proUrl = "/monitor/alarmView/filterAlarmLevel?viewCfgID=" + request.getParameter("viewCfgID");
		map.put("proUrl", proUrl);
		return new ModelAndView("monitor/component/mcAlarmLevelList");
	}
	
	/**
	 * 告警类型页面弹出
	 */
	@RequestMapping("/toSelectAlarmType")
	public ModelAndView toSelectAlarmType(HttpServletRequest request, ModelMap map)throws Exception {
		String proUrl = "/monitor/alarmView/filterAlarmType?viewCfgID=" + request.getParameter("viewCfgID");
		map.put("proUrl", proUrl);
		return new ModelAndView("monitor/component/mcAlarmTypeList");
	}
	
	/**
	 * 告警源对象页面弹出
	 */
	@RequestMapping("/toSelectMOSource")
	public ModelAndView toSelectMOSource(HttpServletRequest request, ModelMap map)throws Exception {
		String proUrl = "/monitor/alarmView/filterMOSource?viewCfgID=" + request.getParameter("viewCfgID");
		map.put("proUrl", proUrl);
		return new ModelAndView("monitor/component/mcMOSourceList");
	}
	
	/**
	 * 告警事件页面弹出
	 */
	@RequestMapping("/toSelectAlarmEvent")
	public ModelAndView toSelectAlarmEvent(HttpServletRequest request, ModelMap map)throws Exception {
		String proUrl = "/monitor/alarmView/filterAlarmEvent?viewCfgID=" + request.getParameter("viewCfgID");
		map.put("proUrl", proUrl);
		return new ModelAndView("monitor/component/mcAlarmEventList");
	}
	
	
	/**
	 * 修改页面跳转
	 */
	@RequestMapping("/toUpadteAlarmView")
	public ModelAndView toUpadteAlarmView(AlarmViewInfo vo,ModelMap map)throws Exception {
		vo = alarmViewMapper.queryViewInfoById(vo);
		map.put("alarmVo", vo);
		return new ModelAndView("monitor/alarmMgr/alarmview/alarmView_modify");
	}
	
	/**
	 * 查看页面跳转
	 */
	@RequestMapping("/toViewAlarmView")
	public ModelAndView toViewAlarmView(AlarmViewInfo vo,ModelMap map)throws Exception {
		vo = alarmViewMapper.queryViewInfoById(vo);
		map.put("alarmVo", vo);
		return new ModelAndView("monitor/alarmMgr/alarmview/alarmView_detail");
	}
	
	
}
