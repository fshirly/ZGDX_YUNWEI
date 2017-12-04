package com.fable.insightview.monitor.mo.controller;

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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.monitor.mobject.entity.MObjectDefBean;
import com.fable.insightview.monitor.molist.entity.ManufacturerInfoBean;
import com.fable.insightview.monitor.molist.entity.ResCategoryDefineBean;
import com.fable.insightview.monitor.molist.entity.SysMOManufacturerResDefBean;
import com.fable.insightview.monitor.molist.entity.SysMoInfoBean;
import com.fable.insightview.monitor.molist.entity.SysMonitorsTypeBean;
import com.fable.insightview.monitor.molist.service.ISysMoService;
import com.fable.insightview.monitor.perf.entity.SysMonitorsOfMOClassBean;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.page.Page;
@Controller
@RequestMapping("/monitor/sysMo")
public class SysMoListController {
	@Autowired ISysMoService sysMoService;
	
	private final Logger logger = LoggerFactory.getLogger(SysMoListController.class);
	
	/**
	 * 加载列表页面
	 */
	@RequestMapping("/toSysMoTypeList")
	public ModelAndView toPerfTaskList(String navigationBar) {
		ModelAndView mv= new ModelAndView("monitor/molist/sysMoType_list");
		mv.addObject("navigationBar", navigationBar);
		return mv;
	}
	
	/**
	 * 加载列表页面数据
	 */
	@RequestMapping("/listSysMoType")
	@ResponseBody
	public Map<String, Object> listSysMoType(SysMonitorsTypeBean monitorTypeBean)
			throws Exception {
		logger.info("加载列表页面数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<SysMonitorsTypeBean> page = new Page<SysMonitorsTypeBean>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("monitorTypeName", request.getParameter("monitorTypeName"));
		paramMap.put("moClassID", request.getParameter("moClassID"));
		page.setParams(paramMap);
		List<SysMonitorsTypeBean> taskList = sysMoService.queryMonitorTypes(page);
		int total = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", taskList);
		logger.info("加载列表页面数据over");
		return result;
	}
	
	/**
	 * 新增监测器类型
	 */
	@RequestMapping("/addMonitorsType")
	@ResponseBody
	public boolean addMonitorsType(SysMonitorsTypeBean bean) throws Exception {
		int insertFlag = sysMoService.insertMonitorType(bean);
		if (insertFlag > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 修改监测器类型
	 */
	@RequestMapping("/updateMonitorsType")
	@ResponseBody
	public boolean updateMonitorsType(SysMonitorsTypeBean bean) throws Exception {
		int updateFlag = sysMoService.updateMonitorType(bean);
		if (updateFlag > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断类型是否已经存在
	 */
	@RequestMapping("/isExistMonitorsType")
	@ResponseBody
	public boolean isExistMonitorsType(String monitorTypeName) throws Exception {
		SysMonitorsTypeBean bean = sysMoService.getMonitorTypeByName(monitorTypeName);
		if (bean != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * 打开编辑页面
	 * @throws Exception
	 */
	@RequestMapping("/toShowAddView")
	@ResponseBody
	public ModelAndView toShowAddView(HttpServletRequest request) {
		return new ModelAndView("monitor/molist/sysMoType_add");
	}
	
	/**
	 * 打开编辑页面
	 * @throws Exception
	 */
	@RequestMapping("/toShowModifyView")
	@ResponseBody
	public ModelAndView toShowModifyView(HttpServletRequest request) {
		String monitorTypeID = request.getParameter("monitorTypeID");
		request.setAttribute("monitorTypeID", monitorTypeID);
		return new ModelAndView("monitor/molist/sysMoType_modify");
	}
	
	/**
	 * 根据Id获取类型信息
	 */
	@RequestMapping("/toGetMonitorTypeById")
	@ResponseBody
	public SysMonitorsTypeBean toGetMonitorTypeById(Integer monitorTypeID) throws Exception {
		return sysMoService.getMonitorTypeById(monitorTypeID);
	}
	
	/**
	 * 根据monitorTypeId获取监测对象对应关系信息
	 */
	@RequestMapping("/toDelMoClassByMonitorTypeId")
	@ResponseBody
	public Integer toDelMoClassByMonitorTypeId(String monitorTypeIDs) throws Exception {
		int result = 0;
		if (monitorTypeIDs.contains(",") == true) {
			String[]  monitorTypeID =  monitorTypeIDs.split(",");
			for (int i = 0; i < monitorTypeID.length; i++) {
				result = sysMoService.delMoClassByMonitorTypeId(Integer.parseInt(monitorTypeID[i]));
			}
		} else {
			result = sysMoService.delMoClassByMonitorTypeId(Integer.parseInt(monitorTypeIDs));
		}
		return result;
	}
	
	/**
	 * 根据monitorTypeId获取监测器对应关系信息
	 */
	@RequestMapping("/toGetMonitorsByMonitorTypeId")
	@ResponseBody
	public Integer toGetMonitorsByMonitorTypeId(String monitorTypeIDs) throws Exception {
		int result = 0;
		if (monitorTypeIDs.contains(",") == true) {
			String[]  monitorTypeID =  monitorTypeIDs.split(",");
			for (int i = 0; i < monitorTypeID.length; i++) {
				result += sysMoService.getMonitorsByMonitorTypeId(Integer.parseInt(monitorTypeID[i]));
			}
		} else {
			result = sysMoService.getMonitorsByMonitorTypeId(Integer.parseInt(monitorTypeIDs));
		}
		return result;
	}
	
	/**
	 * 根据monitorTypeId获取监测类型与模板对应关系信息
	 */
	@RequestMapping("/toGetUsedTemplateByTypeId")
	@ResponseBody
	public Integer toGetUsedTemplateByTypeId(String monitorTypeIDs) throws Exception {
		int result = 0;
		if (monitorTypeIDs.contains(",") == true) {
			String[]  monitorTypeID =  monitorTypeIDs.split(",");
			for (int i = 0; i < monitorTypeID.length; i++) {
				result += sysMoService.getUsedTemplateByTypeId(Integer.parseInt(monitorTypeID[i]));
			}
		} else {
			result = sysMoService.getUsedTemplateByTypeId(Integer.parseInt(monitorTypeIDs));
		}
		return result;
	}
	
	/**
	 * 根据monitorTypeId删除类型
	 */
	@RequestMapping("/toDelByMonitorTypeId")
	@ResponseBody
	public boolean toDelByMonitorTypeId(String monitorTypeIDs) throws Exception {
		int flag = 0;
		if (monitorTypeIDs.contains(",") == true) {
			String[]  monitorTypeID =  monitorTypeIDs.split(",");
			for (int i = 0; i < monitorTypeID.length; i++) {
				flag = sysMoService.delByMonitorTypeId(Integer.parseInt(monitorTypeID[i]));
			}
		} else {
			flag = sysMoService.delByMonitorTypeId(Integer.parseInt(monitorTypeIDs));
		}
		
		if (flag >0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping("/toDelMonitorTypes")
	@ResponseBody
	public boolean toDelMonitorTypes() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Integer flag = 0;
		String monitorTypeIds = request.getParameter("monitorTypeIds");
		if (monitorTypeIds.contains(",") == true) {
			String[] ids = monitorTypeIds.split(",");
			for (int i = 0; i < ids.length; i++) {
				flag = sysMoService.delByMonitorTypeId(Integer.parseInt(ids[i]));
			}
		} else {
			flag = sysMoService.delByMonitorTypeId(Integer.parseInt(monitorTypeIds));
		}
		if (flag >0) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * 加载监测器列表页面
	 */
	@RequestMapping("/toSysMoInfoList")
	public ModelAndView toSysMoInfoList(HttpServletRequest request,String navigationBar) {
		Map<Integer,String> typeMap = new HashMap<Integer,String>();
		List<SysMonitorsTypeBean> moTypeList = sysMoService.getAllMonitorTypes();
		for (int i = 0; i < moTypeList.size(); i++) {
			typeMap.put(moTypeList.get(i).getMonitorTypeID(), moTypeList.get(i).getMonitorTypeName());
		}
//		Map<Integer,String> resManuMap = new HashMap<Integer,String>();
//		List<ManufacturerInfoBean> manuList = sysMoService.queryAllManufacturer();
//		for (int i = 0; i < manuList.size(); i++) {
//			resManuMap.put(manuList.get(i).getResManufacturerID(), manuList.get(i).getResManufacturerName());
//		}
		request.setAttribute("moTypeList", typeMap);
		request.setAttribute("navigationBar", navigationBar);
		return new ModelAndView("monitor/molist/sysMoInfo_list");
	}
	
	/**
	 * 加载列表页面数据
	 */
	@RequestMapping("/listSysMoInfo")
	@ResponseBody
	public Map<String, Object> listSysMoInfo(SysMoInfoBean moInfoBean)
			throws Exception {
		logger.info("加载列表页面数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<SysMoInfoBean> page = new Page<SysMoInfoBean>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("monitorTypeName", request.getParameter("monitorTypeName"));
		paramMap.put("moName", request.getParameter("moName"));
		paramMap.put("monitorProperty", request.getParameter("monitorProperty"));
		paramMap.put("moClassLable", request.getParameter("moClassLable"));
		paramMap.put("resManufacturerName", request.getParameter("resManufacturerName"));
		paramMap.put("resCategoryName", request.getParameter("resCategoryName"));
		page.setParams(paramMap);
		List<SysMoInfoBean> taskList = sysMoService.queryMoInfos(page);
		int total = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", taskList);
		logger.info("加载列表页面数据over");
		return result;
	}
	
	
	/**
	 * 打开编辑页面
	 * @throws Exception
	 */
	@RequestMapping("/toShowAddMoInfoView")
	@ResponseBody
	public ModelAndView toShowAddMoInfoView(HttpServletRequest request) {
		Map<Integer,String> typeMap = new HashMap<Integer,String>();
		List<SysMonitorsTypeBean> moTypeList = sysMoService.getAllMonitorTypes();
		for (int i = 0; i < moTypeList.size(); i++) {
			typeMap.put(moTypeList.get(i).getMonitorTypeID(), moTypeList.get(i).getMonitorTypeName());
		}
		request.setAttribute("typeMap", typeMap);
		return new ModelAndView("monitor/molist/sysMoInfo_add");
	}
	
	/**
	 * 打开编辑页面
	 * @throws Exception
	 */
	@RequestMapping("/toShowModifyMoInfoView")
	@ResponseBody
	public ModelAndView toShowModifyMoInfoView(HttpServletRequest request) {
		String mid = request.getParameter("mid");
		request.setAttribute("mid", mid);
		return new ModelAndView("monitor/molist/sysMoInfo_modify");
	}
	
	/**
	 * 打开基础信息编辑页面
	 * @throws Exception
	 */
	@RequestMapping("/toShowModifyMoBaseView")
	@ResponseBody
	public ModelAndView toShowModifyMoBaseView(HttpServletRequest request) {
		String mid = request.getParameter("mid");
		Map<Integer,String> typeMap = new HashMap<Integer,String>();
		List<SysMonitorsTypeBean> moTypeList = sysMoService.getAllMonitorTypes();
		for (int i = 0; i < moTypeList.size(); i++) {
			typeMap.put(moTypeList.get(i).getMonitorTypeID(), moTypeList.get(i).getMonitorTypeName());
		}
		request.setAttribute("typeMap", typeMap);
		request.setAttribute("mid", mid);
		return new ModelAndView("monitor/molist/sysMoInfoBase_modify");
	}
	
	/**
	 * 打开适用范围编辑页面
	 * @throws Exception
	 */
	@RequestMapping("/toShowModifyMoRangeView")
	@ResponseBody
	public ModelAndView toShowModifyMoRangeView(HttpServletRequest request) {
		String mid = request.getParameter("mid");
		request.setAttribute("mid", mid);
		return new ModelAndView("monitor/molist/sysMoInfoRange_modify");
	}
	
	/**
	 * 新增监测器
	 */
	@RequestMapping("/addMoInfo")
	@ResponseBody
	public int addMoInfo(SysMoInfoBean bean) throws Exception {
		int insertFlag = sysMoService.insertMoInfo(bean);
		if (insertFlag > 0) {
			return bean.getMid();
		}
		return -1;
	}
	
	
	/**
	 * 加载厂商型号列表页面数据
	 */
	@RequestMapping("/listSysManufactureByMid")
	@ResponseBody
	public Map<String, Object> listSysManufactureByMid(Integer mid)
			throws Exception {
		logger.info("加载列表页面数据");
		List<SysMOManufacturerResDefBean> taskList = sysMoService.getMoManuCateByMid(mid);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", taskList.size());
		result.put("rows", taskList);
		logger.info("加载列表页面数据over");
		return result;
	}
	
	/**
	 * 加载厂商数据
	 */
	@RequestMapping("/listSysManufactureInfos")
	@ResponseBody
	public List<String> listSysManufactureInfos()throws Exception {
		List<ManufacturerInfoBean> manuList = sysMoService.queryAllManufacturer();
		List<String> result = new ArrayList<String>();
		for (int i = 0; i < manuList.size(); i++) {
			result.add(manuList.get(i).getResManufacturerID()+";"+manuList.get(i).getResManufacturerName());
		}
		return result;
	}
	
	/**
	 * 加载厂商数据
	 */
	@RequestMapping("/listResCateInfos")
	@ResponseBody
	public List<String> listResCateInfos(HttpServletRequest request)throws Exception {
		Integer manuId = Integer.parseInt(request.getParameter("resManufacturerID"));
		List<ResCategoryDefineBean> cateList = null;
		if (manuId == 0) {
			cateList = sysMoService.queryAllResCategory();
		} else {
			cateList = sysMoService.queryResCategoryByManuId(manuId);
		}
		List<String> result = new ArrayList<String>();
		for (int i = 0; i < cateList.size(); i++) {
			result.add(cateList.get(i).getResCategoryID()+";"+cateList.get(i).getResCategoryName());
		}
		return result;
	}
	
	/**
	 * 新增监测器厂商范围
	 */
	@RequestMapping("/addMoManuCateRelation")
	@ResponseBody
	public boolean addMoManuCateRelation(SysMOManufacturerResDefBean relationBean) throws Exception {
		int insertFlag = sysMoService.insertMoManuCateRelation(relationBean);
		if (insertFlag > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 新增监测器监测对象范围
	 */
	@RequestMapping("/addMoClassRelation")
	@ResponseBody
	public boolean addMoClassRelation(SysMonitorsOfMOClassBean moClassRelationBean) throws Exception {
		int insertFlag = 0;
		int delResult = sysMoService.delMoClassRelation(moClassRelationBean);
		if (delResult >= 0) {
			insertFlag = sysMoService.insertMoClassRelation(moClassRelationBean);
		}
		if (insertFlag > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 根据id删除监测器与厂商型号关系
	 */
	@RequestMapping("/toDelManuCateById")
	@ResponseBody
	public boolean toDelManuCateById(Integer id) throws Exception {
		Integer flag = sysMoService.delManuCateById(id);
		if (flag >0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 批量删除监测器与厂商型号关系
	 */
	@RequestMapping("/toDelManuCateByIds")
	@ResponseBody
	public boolean toDelManuCateByIds(String ids) throws Exception {
		Integer flag = 0;
		if (ids.contains(",") == true) {
			String[] idLst = ids.split(",");
			for (int i = 0; i < idLst.length; i++) {
				flag = sysMoService.delManuCateById(Integer.parseInt(idLst[i]));
			}
		} else {
			flag = sysMoService.delManuCateById(Integer.parseInt(ids));
		}
		
		if (flag >0) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * 根据MID加载监测器基础信息
	 */
	@RequestMapping("/getMoBaseInfoByMid")
	@ResponseBody
	public SysMoInfoBean getMoBaseInfoByMid(Integer mid)throws Exception {
		return sysMoService.getMoInfoByMid(mid);
	}
	
	/**
	 * 根据MID加载监测器与对象信息
	 */
	@RequestMapping("/getMoClassRelationInfoByMid")
	@ResponseBody
	public SysMoInfoBean getMoClassRelationInfoByMid(Integer mid)throws Exception {
		return sysMoService.getMoClassRelationInfoByMid(mid);
	}
	
	/**
	 * 修改监测器
	 */
	@RequestMapping("/updateMoInfo")
	@ResponseBody
	public boolean updateMoInfo(SysMoInfoBean bean) throws Exception {
		int updateFlag = sysMoService.updateMoInfo(bean);
		if (updateFlag > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 打开详情页面
	 * @throws Exception
	 */
	@RequestMapping("/toShowMoInfoDetail")
	@ResponseBody
	public ModelAndView toShowMoInfoDetail(HttpServletRequest request) {
		String mid = request.getParameter("mid");
		request.setAttribute("mid", mid);
		return new ModelAndView("monitor/molist/sysMoInfo_detail");
	}
	
	/**
	 * 打开基础信息详情页面
	 * @throws Exception
	 */
	@RequestMapping("/toShowMoBaseDetail")
	@ResponseBody
	public ModelAndView toShowMoBaseDetail(HttpServletRequest request) {
		String mid = request.getParameter("mid");
		request.setAttribute("mid", mid);
		return new ModelAndView("monitor/molist/sysMoInfoBase_detail");
	}
	
	/**
	 * 打开适用范围详情页面
	 * @throws Exception
	 */
	@RequestMapping("/toShowMoRangeDetail")
	@ResponseBody
	public ModelAndView toShowMoRangeDetail(HttpServletRequest request) {
		String mid = request.getParameter("mid");
		request.setAttribute("mid", mid);
		return new ModelAndView("monitor/molist/sysMoInfoRange_detail");
	}
	
	/**
	 * 修改监测器监测对象范围
	 */
	@RequestMapping("/updateMoClassRelation")
	@ResponseBody
	public boolean updateMoClassRelation(SysMonitorsOfMOClassBean moClassRelationBean) throws Exception {
		int insertFlag = sysMoService.updateMoClassRelation(moClassRelationBean);
		if (insertFlag > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 监测器是否关联采集任务
	 */
	@RequestMapping("/existMoOfPerfTask")
	@ResponseBody
	public boolean existMoOfPerfTask(String mids) throws Exception {
		int countVal = 0;
		if (mids.contains(",") == true) {
			String[] mid = mids.split(",");
			for (int i = 0; i < mid.length; i++) {
				countVal = sysMoService.queryMoOfPerfTask(Integer.parseInt(mid[i]));
				if (countVal > 0) {
					return true;
				}
			}
		} else {
			countVal = sysMoService.queryMoOfPerfTask(Integer.parseInt(mids));
		}
		
		if (countVal > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 根据mid删除监测器
	 */
	@RequestMapping("/toDelMoInfoById")
	@ResponseBody
	public boolean toDelMoInfoById(String mids) throws Exception {
		Integer flag = 0;
		if (mids.contains(",") == true) {
			String[] mid = mids.split(",");
			for (int i = 0; i < mid.length; i++) {
				int delManuCateFlag = sysMoService.delMoOfManuCateByMid(Integer.parseInt(mid[i]));
				int delMoClassFlag = sysMoService.delMoOfClassByMid(Integer.parseInt(mid[i]));
				if (delManuCateFlag >= 0 && delMoClassFlag >= 0) {
					flag = sysMoService.delMoInfoByMid(Integer.parseInt(mid[i]));
				}
			}
		} else {
			int delManuCateFlag = sysMoService.delMoOfManuCateByMid(Integer.parseInt(mids));
			int delMoClassFlag = sysMoService.delMoOfClassByMid(Integer.parseInt(mids));
			if (delManuCateFlag >= 0 && delMoClassFlag >= 0) {
				flag = sysMoService.delMoInfoByMid(Integer.parseInt(mids));
			}
		}
		
		if (flag >=0) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * 判断监测器调度类名是否已经存在
	 */
	@RequestMapping("/checkMoClass")
	@ResponseBody
	public int checkMoClass(String moClass) throws Exception {
		return sysMoService.getCountByMoClass(moClass);
		
	}
	
	/**
	 * 初始化对象类型树
	 */
	@RequestMapping("/initTree")
	@ResponseBody
	public Map<String, Object> initTree() throws Exception {
		return sysMoService.initPortalTree();
	}
}
