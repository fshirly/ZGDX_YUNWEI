package com.fable.insightview.attendanceconf.controller;

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

import com.fable.insightview.platform.attendance.service.AttendanceRecordService;
import com.fable.insightview.platform.attendanceconf.entity.AttendancePeopleConf;
import com.fable.insightview.platform.attendanceconf.entity.AttendancePeriodConf;
import com.fable.insightview.platform.attendanceconf.entity.AttendancePlanConf;
import com.fable.insightview.platform.attendanceconf.entity.SysUserInfo;
import com.fable.insightview.platform.attendanceconf.service.AttendanceConfService;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.contractmanager.entity.ComboxBean;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.page.Page;

@Controller
@RequestMapping("/attendanceConf")
public class AttendancePlanConfContractor {
	@Autowired
	private AttendanceConfService attendanceConfService;
	@Autowired
	private AttendanceRecordService attendanceRecordService;
	
	private final static Logger logger = LoggerFactory.getLogger(AttendancePlanConfContractor.class);
	/*
	 * 菜单跳转页面
	 */
	@RequestMapping("/attendanceConf_list_page")
    public ModelAndView toattendanceConfList(String navigationBar) {
		return new ModelAndView("platform/attendanceConf/attendanceConf_list").addObject("navigationBar", navigationBar);
	}
	/*
	 *添加页跳转
	 */
	@RequestMapping("/attendanceConf_add")
    public ModelAndView toattendanceConfAdd() {
		return new ModelAndView("platform/attendanceConf/attendanceConf_add");
	}
	/*
	 * 编辑页跳转
	 */
	@RequestMapping("/attendanceConf_edit")
	public ModelAndView toattendanceConfedit(Integer attendanceId,String index){
		ModelAndView mv=new ModelAndView();
		mv.addObject("attendanceId", attendanceId);
		mv.addObject("index", index);
		mv.setViewName("platform/attendanceConf/attendanceConf_edit");
		return mv;
	}
	/*
	 *添加计划页跳转
	 */
	@RequestMapping("/attendancePlanConf_add")
    public ModelAndView toattendancePlanConfAdd(Integer attendanceId) {
		ModelAndView mv=new ModelAndView();
		mv.addObject("attendanceId", attendanceId);
		return new ModelAndView("platform/attendanceConf/attendancePlanConfAdd");
	}
	/*
	 * 编辑计划页跳转
	 */
	@RequestMapping("/attendancePlanConf_edit")
	public ModelAndView toattendancePlanConfEdit(Integer attendanceId){
		ModelAndView mv=new ModelAndView();
		AttendancePlanConf attendancePlanConf=attendanceConfService.queryAttendancePlanConf(attendanceId);
		mv.addObject("attendancePlanConf", attendancePlanConf);
		mv.setViewName("platform/attendanceConf/attendancePlanConfEdit");
		return mv;
	}
	/*
	 * 跳转到人员配置列表
	 */
	@RequestMapping("/attendancePeopleConf_list")
	public ModelAndView toattendancePeopleConfList(Integer attendanceId){
		ModelAndView mv=new ModelAndView();
		mv.addObject("attendanceId", attendanceId);
		mv.setViewName("platform/setSignIn/setPersonnel");
		return mv;
	}
	/*
	 * 跳转大鹏人员配置添加页
	 */
	@RequestMapping("/attendancepeopleConf_add")
	public ModelAndView toattendancePeopleConfAdd(Integer attendanceId){
		ModelAndView mv=new ModelAndView();
		mv.addObject("attendanceId", attendanceId);
		mv.setViewName("platform/setSignIn/personnelSelection");
		return mv;
	}
	/*
	 * 跳转人员配置详情页
	 */
	@RequestMapping("/attendancePeopleConf_list_detail")
	public ModelAndView toattendancePeopleConfDetail(Integer attendanceId){
		ModelAndView mv=new ModelAndView();
		mv.addObject("attendanceId", attendanceId);
		mv.setViewName("platform/setSignIn/setPersonnelDetail");
		return mv;
	}
	/*
	 * 编辑计划详情页
	 */
	@RequestMapping("/attendancePlanConf_detail")
	public ModelAndView toattendancePlanConf_Detail(Integer attendanceId){
		ModelAndView mv=new ModelAndView();
		AttendancePlanConf attendancePlanConf=attendanceConfService.queryAttendancePlanConf(attendanceId);
		mv.addObject("attendancePlanConf", attendancePlanConf);
		mv.setViewName("platform/attendanceConf/attendancePlanConfDetail");
		return mv;
	}
	/*
	 * 详情页跳转 
	 */
	@RequestMapping("/attendanceConf_detail")
	public ModelAndView toattendancePlanConfDetail(Integer attendanceId){
		ModelAndView mv=new ModelAndView();
		AttendancePlanConf attendancePlanConf=attendanceConfService.queryAttendancePlanConf(attendanceId);
		mv.addObject("attendanceId", attendanceId);
		mv.setViewName("platform/attendanceConf/attendanceConf_Detail");
		return mv;
		
	}
	/*
	 * 签到配置列表页
	 */
	@RequestMapping("/attendanceConf_list")
	@ResponseBody
	public  Map<String,Object> getAttendanceConf_list_Info(AttendancePlanConf attendancePlanConf, HttpServletRequest request){
		//获取当前每页行数和当前页数
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil.getFlexiGridPageInfo(request);
		Page<AttendancePlanConf> page = new Page<AttendancePlanConf>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("attStartTime_start",attendancePlanConf.getAttStartTime_start());
		paramMap.put("attStartTime_end", attendancePlanConf.getAttStartTime_end());
		paramMap.put("attEndTime_start", attendancePlanConf.getAttEndTime_start());
		paramMap.put("attEndTime_end", attendancePlanConf.getAttEndTime_end());
		paramMap.put("title", attendancePlanConf.getTitle());
		page.setParams(paramMap);
		// 查询分页数据
		List<AttendancePlanConf> list = attendanceConfService.queryAttendancePlanConflist(page);
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", list);
		logger.info("结束...获取页面显示数据");
		return result;
	}
	/*
	 * 签到人员配置list页
	 */
	@RequestMapping("/attendancepeopleConf_list")
	@ResponseBody
	public  Map<String,Object> getAttendanceAddPeopleConf_list_Info(SysUserInfo sysUserInfo,HttpServletRequest request){
		//获取当前每页行数和当前页数
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil.getFlexiGridPageInfo(request);
		Page<SysUserInfo> page = new Page<SysUserInfo>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userName",sysUserInfo.getUserName());
		paramMap.put("provide_Org", sysUserInfo.getProvide_Org());
		paramMap.put("userType",sysUserInfo.getUserType());
		paramMap.put("attendanceId",sysUserInfo.getAttendanceId());
		page.setParams(paramMap);
		// 查询分页数据
		List<SysUserInfo> list = attendanceConfService.queryAttendancePeoplePlanConflist(page);
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", list);
		logger.info("结束...获取页面显示数据");
		return result;
	}
	/*
	 * 签到人员配置list页(查询)
	 */
	@RequestMapping("/attendancepeopleConf_list_select")
	@ResponseBody
	public  Map<String,Object> getAttendancePeopleConf_list_Info(Integer attendanceId,HttpServletRequest request){
		//获取当前每页行数和当前页数
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil.getFlexiGridPageInfo(request);
		Page<SysUserInfo> page = new Page<SysUserInfo>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("attendanceId",attendanceId);
		page.setParams(paramMap);
		// 查询分页数据
		List<SysUserInfo> list = attendanceConfService.queryAttendancePeoplePlanConflist_select(page);
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", list);
		logger.info("结束...获取页面显示数据");
		return result;
	}
	/*
	 * 添加页
	 * 
	 */
	@RequestMapping("/attendancePlanConfInfo_add")
	@ResponseBody
	public Integer addAttendancePlanConf(AttendancePlanConf attendancePlanConf, HttpServletRequest request){
		 Integer result=null;;
		 try {
			 //ObjectMapper mapper = CommonUtil.getMapperInstance(false);
			 //DeserializationConfig cfg = mapper.getDeserializationConfig();
			 //cfg.setDateFormat(new SimpleDateFormat("HH:mm"));
			 List<AttendancePeriodConf> list=JsonUtil.toList(attendancePlanConf.getPlantimes(),AttendancePeriodConf.class);
			 result= attendanceConfService.insertAttendanceConf(attendancePlanConf, list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return result;
	}
	/*
	 * 添加人员计划关系
	 */
	@RequestMapping("/attendancepeopleConfInfoadd")
	@ResponseBody
	public boolean addattendancepeopleConfInfo(AttendancePeopleConf attendancePeopleConf,HttpServletRequest request){
		 boolean flag=true;
		 try {
			attendanceConfService.insertAttendanceConf(attendancePeopleConf);
		} catch (Exception e) {
			flag=false;
			e.printStackTrace();
		}
		return flag;
	}
	
	/*
	 * 签到计划下拉框
	 */
	@RequestMapping("/attendanceConfztcombox")
	@ResponseBody
	public List<ComboxBean> getAttendanceConfZtType(){
		List<ComboxBean> list = new ArrayList<ComboxBean>();
		list=attendanceConfService.queryAttendanceConfZtTypeCombox();
		//list.add(0,bean);
		return list;
	}
	/*
	 * 供应商的下拉框
	 */
	@RequestMapping("/attendanceConfzt_providerinfo_combox")
	@ResponseBody
	public List<ComboxBean> getAttendanceProviderConfZtType(){
		ComboxBean<String,String> bean=new ComboxBean<String,String>();
		bean.setId(null);
		bean.setNeir(null);
		List<ComboxBean> list = new ArrayList<ComboxBean>();
		list=attendanceConfService.queryAttendanceProviderConfZtTypeCombox();
		list.add(0,bean);
		return list;
	}

	/*
	 * 校验签到时间
	 */
	@Deprecated
	@RequestMapping("/validatorattStartTime")
	@ResponseBody
	public boolean validatorAttStartTime(AttendancePlanConf attendancePlanConf, HttpServletRequest request){
		return attendanceConfService.validatorvalidatorAttStartTime(attendancePlanConf);
	}
	/*
	 * 删除信息
	 */
	@RequestMapping("/attendanceConfdelete")
	@ResponseBody
	public boolean deleteAttendancePlanConf(Integer attendanceId){
		boolean flag=true;
		try {
			attendanceConfService.deleteAttendancePlanConf(attendanceId);
		} catch (Exception e) {
			flag=false;
			e.printStackTrace();
		}
		return flag;
	}
	/*
	 * 批量删除签到信息
	 */
	@RequestMapping("/attendanceConfBathchdelete")
	@ResponseBody
	public boolean deleteattendanceConfBathch(String ids){
		boolean flag=true;
		try {
			attendanceConfService.deleteattendanceConfBathch(ids);
		} catch (Exception e) {
			flag=false;
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 校验是否启动
	 * @param planConf
	 * @return
	 */
	@RequestMapping("/validateStartAttPlanConf")
	@ResponseBody
	public boolean validateStartAttPlanConf(AttendancePlanConf planConf) {
		boolean flag = false;
		flag = this.attendanceConfService.validateStartAttPlanConf(planConf.getAttendanceId());
		return flag;
	}
	
	/*
	 * 修改签到配置
	 */
	@RequestMapping("/attendanceplanconfzt")
	@ResponseBody
	public boolean updateattendanceConfzt(AttendancePlanConf attendancePlanConf){
		boolean flag=true;
		try {
			attendanceConfService.updateattendanceConfzt(attendancePlanConf);
		} catch (Exception e) {
			flag=false;
			e.printStackTrace();
		}
		return flag;
	}
	/*
	 * 查询表格页
	 */
	@RequestMapping("/attendancePeriodConflist")
	@ResponseBody
	public  Map<String,Object> getAttendancePeriodConf_list_Info(Integer attendanceId){
		List<AttendancePeriodConf> list=attendanceConfService.queryAttendancePeriodConf_list_Info(attendanceId);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", list);
		return result;
	}
	/*
	 * 修改签到配置
	 */
	@RequestMapping("/attendancePlanConfInfo_update")
	@ResponseBody
	public boolean updateAttendanceConfinfo(AttendancePlanConf attendancePlanConf,HttpServletRequest request){
		 boolean flag=true;
		 try {
			 //ObjectMapper mapper = CommonUtil.getMapperInstance(false);
			 //DeserializationConfig cfg = mapper.getDeserializationConfig();
			 //cfg.setDateFormat(new SimpleDateFormat("HH:mm"));
			 List<AttendancePeriodConf> list=JsonUtil.toList(attendancePlanConf.getPlantimes(),AttendancePeriodConf.class);
			 attendanceConfService.updateAttendanceConf(attendancePlanConf, list);
		} catch (Exception e) {
			flag=false;
			e.printStackTrace();
		}
		 return flag;
	}
	/*
	 * 删除签到配置
	 */
	@RequestMapping("/deleteAttendancePeopleConf")
	@ResponseBody
	public boolean deleteAttendancePeopleConf(Integer id){
		boolean flag=true;
		try {
			attendanceConfService.deleteAttendancePeopleConf(id);
		} catch (Exception e) {
			flag=false;
			e.printStackTrace();
		}
		return flag;
	}
	/*
	 * 判断标题是否重复
	 */
	@RequestMapping("/attendanceConfvalidatortitle")
	@ResponseBody
	public boolean validatorAttendancetitle(String title){
		return attendanceConfService.validatorAttendancetitle(title);
	}
}    
