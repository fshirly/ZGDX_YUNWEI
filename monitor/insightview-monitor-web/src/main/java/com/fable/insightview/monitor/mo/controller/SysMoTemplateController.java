package com.fable.insightview.monitor.mo.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.monitor.alarmmgr.entity.MOKPIThresholdBean;
import com.fable.insightview.monitor.discover.entity.MODeviceObj;
import com.fable.insightview.monitor.molist.entity.SysMoTemplateIntervalBean;
import com.fable.insightview.monitor.molist.entity.SysMoTypeOfMoClassBean;
import com.fable.insightview.monitor.molist.entity.SysMonitorsTemplateBean;
import com.fable.insightview.monitor.molist.entity.SysMonitorsTemplateUsedBean;
import com.fable.insightview.monitor.molist.service.ISysMoService;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.page.Page;

@Controller
@RequestMapping("/monitor/sysMoTemplate")
public class SysMoTemplateController {
@Autowired ISysMoService sysMoService;
	
	private final Logger logger = LoggerFactory.getLogger(SysMoTemplateController.class);
	
	/**
	 * 加载列表页面
	 */
	@RequestMapping("/toSysMoTemplateList")
	public ModelAndView toSysMoTemplateList(String navigationBar) {
		ModelAndView mv= new ModelAndView("monitor/molist/sysMoTemplate_list");
		mv.addObject("navigationBar", navigationBar);
		return mv;
	}
	
	/**
	 * 加载列表页面数据
	 */
	@RequestMapping("/listSysMoTemplate")
	@ResponseBody
	public Map<String, Object> listSysMoTemplate(SysMonitorsTemplateBean templateBean)
			throws Exception {
		logger.info("加载列表页面数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<SysMonitorsTemplateBean> page = new Page<SysMonitorsTemplateBean>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("templateName", request.getParameter("templateName"));
		page.setParams(paramMap);
		List<SysMonitorsTemplateBean> taskList = sysMoService.queryMoTemplates(page);
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
	@RequestMapping("/toShowAddView")
	@ResponseBody
	public ModelAndView toShowAddView(HttpServletRequest request) {
		return new ModelAndView("monitor/molist/sysMoTemplate_add");
	}
	
	/**
	 * 打开编辑页面
	 * @throws Exception
	 */
	@RequestMapping("/toShowModifyView")
	@ResponseBody
	public ModelAndView toShowModifyView(HttpServletRequest request) {
		String templateID = request.getParameter("templateID");
		request.setAttribute("templateID", templateID);
		return new ModelAndView("monitor/molist/sysMoTemplate_modify");
	}
	
	/**
	 * 打开查看模板页面
	 * @throws Exception
	 */
	@RequestMapping("/toShowDetailView")
	@ResponseBody
	public ModelAndView toShowDetailView(HttpServletRequest request) {
		String templateID = request.getParameter("templateID");
		request.setAttribute("templateID", templateID);
		return new ModelAndView("monitor/molist/sysMoTemplate_detail");
	}
	
	/**
	 * 根据moClassID获取监测器信息
	 */
	@RequestMapping("/listMoList")
	@ResponseBody
	public List<String> listMoList(Integer moClassID) throws Exception {
		List<String> moList = new ArrayList<String>();
		moList = sysMoService.getMoList(moClassID);
		return moList;
	}
	
	
	/**
	 * 根据templateID获取监测器信息
	 */
	@RequestMapping("/listMoByTemplateID")
	@ResponseBody
	public List<String> listMoByTemplateID(Integer templateID) throws Exception {
		List<String> moList = new ArrayList<String>();
		moList = sysMoService.getMoListByTemplateId(templateID);
		return moList;
	}
	
	/**
	 * 根据templateID获取模板信息
	 */
	@RequestMapping("/getMoTemplateInfoByID")
	@ResponseBody
	public SysMonitorsTemplateBean getMoTemplateInfoByID(Integer templateID) throws Exception {
		SysMonitorsTemplateBean templateBean = sysMoService.getTemplateByID(templateID);
		return templateBean;
	}
	
	/**
	 * 新增模板
	 */
	@RequestMapping("/addMoTemplate")
	@ResponseBody
	public boolean addMoTemplate(SysMonitorsTemplateBean templateBean) throws Exception {
		int result = 0;
		int insertFlag = sysMoService.insertMoTemplateInfo(templateBean);
		if (insertFlag > 0) {
			String[] molst = templateBean.getMoList().split(",");
			String[] moIntervalList = templateBean.getMoIntervalList().split(",");
			String[] moTimeUnitList = templateBean.getMoTimeUnitList().split(",");
			for (int i = 0; i < molst.length; i++) {
				SysMoTemplateIntervalBean intervalBean = new SysMoTemplateIntervalBean();
				intervalBean.setTemplateID(templateBean.getTemplateID());
				intervalBean.setMonitorTypeID(Integer.parseInt(molst[i]));
				intervalBean.setTimeInterval(Integer.parseInt(moIntervalList[i]));
				intervalBean.setTimeUnit(Integer.parseInt(moTimeUnitList[i]));
				result = sysMoService.insertMoTempInterval(intervalBean);
			}
		}
		if (result > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 修改模板
	 */
	@RequestMapping("/updateMoTemplate")
	@ResponseBody
	public boolean updateMoTemplate(SysMonitorsTemplateBean templateBean) throws Exception {
		int updateFlag = sysMoService.updateMoTemplateInfo(templateBean);
		int result = 0;
		if (updateFlag > 0) {
			String[] molst = templateBean.getMoList().split(",");
			String[] moIntervalList = templateBean.getMoIntervalList().split(",");
			String[] moTimeUnitList = templateBean.getMoTimeUnitList().split(",");
			int delResult = sysMoService.delIntervalByTemplateID(templateBean.getTemplateID());
			if (delResult > 0) {
				for (int i = 0; i < molst.length; i++) {
					SysMoTemplateIntervalBean intervalBean = new SysMoTemplateIntervalBean();
					intervalBean.setTemplateID(templateBean.getTemplateID());
					intervalBean.setMonitorTypeID(Integer.parseInt(molst[i]));
					intervalBean.setTimeInterval(Integer.parseInt(moIntervalList[i]));
					intervalBean.setTimeUnit(Integer.parseInt(moTimeUnitList[i]));
					result = sysMoService.insertMoTempInterval(intervalBean);
				}
			}
		}
		if (result > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 根据templateID删除模板信息
	 */
	@RequestMapping("/delTemplateByID")
	@ResponseBody
	public boolean delTemplateByID(Integer templateID) throws Exception {
		int delResult = 0;
		int flag = sysMoService.delTempIntervalByID(templateID);
		if(flag > 0){
			delResult = sysMoService.delTemplateByID(templateID);
		}
		if (delResult > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping("/delTemplatesByIDs")
	@ResponseBody
	public boolean delTemplatesByIDs(String templateIDs) throws Exception {
		int delResult = 0;
		if (templateIDs.contains(",") == true) {
			String[] ids = templateIDs.split(",");
			for (int i = 0; i < ids.length; i++) {
				int flag = sysMoService.delTempIntervalByID(Integer.parseInt(ids[i]));
				if (flag > 0) {
					delResult = sysMoService.delTemplateByID(Integer.parseInt(ids[i]));
				}
			}
		} else {
			int flag = sysMoService.delTempIntervalByID(Integer.parseInt(templateIDs));
			if (flag > 0) {
				delResult = sysMoService.delTemplateByID(Integer.parseInt(templateIDs));
			}
		}
		
		if (delResult > 0) {
			return true;
		}
		return false;
	}
	
	//打开配置页面
	@RequestMapping("/toShowConfig")
	public ModelAndView toShowConfig(HttpServletRequest result,
			HttpServletResponse response,String navigationBar) {
		ModelAndView mv= new ModelAndView("monitor/molist/sysMoTypeRange");
		mv.addObject("navigationBar", navigationBar);
		return mv;
	}
	
	//打开配置页面
	@RequestMapping("/toShowConfigRange")
	public ModelAndView toShowConfigRange(HttpServletRequest request,
			HttpServletResponse response) {
		String moClassID = request.getParameter("moClassID");
		request.setAttribute("moClassID", moClassID);
		return new ModelAndView("monitor/molist/sysMoTypeRange_list");
	}
	
	/**
	 * 加载列表页面数据
	 */
	@RequestMapping("/listSysMoTypeRange")
	@ResponseBody
	public Map<String, Object> listSysMoTypeRange(Integer moClassID)
			throws Exception {
		logger.info("加载列表页面数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<SysMoTypeOfMoClassBean> page = new Page<SysMoTypeOfMoClassBean>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("moClassID", moClassID);
		page.setParams(paramMap);
		List<SysMoTypeOfMoClassBean> taskList = sysMoService.queryTypeOfClassByMoClassID(page);
		int total = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", taskList);
		logger.info("加载列表页面数据over");
		return result;
	}
	
	/**
	 * 模板类型是否配置范围
	 */
	@RequestMapping("/existMoTemplate")
	@ResponseBody
	public boolean existMoTemplate(int moClassID) throws Exception {
		int countVal = sysMoService.queryTypesByMoClassID(moClassID);
		if (countVal > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 打开类型配置页面
	 * @throws Exception
	 */
	@RequestMapping("/toShowAddTypeView")
	@ResponseBody
	public ModelAndView toShowAddTypeView(HttpServletRequest request) {
		String moClassID = request.getParameter("moClassID");
		request.setAttribute("moClassID", moClassID);
		return new ModelAndView("monitor/molist/sysMoTypeConfig_list");
	}
	
	/**
	 * 新增类型配置范围
	 */
	@RequestMapping("/addMoTypeOfMoClass")
	@ResponseBody
	public boolean addMoTypeOfMoClass(SysMoTypeOfMoClassBean bean) throws Exception {
		int result = sysMoService.insertMoTypeOfMoClass(bean);
		if (result > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 删除类型配置范围
	 */
	@RequestMapping("/delMoTypeOfMoClass")
	@ResponseBody
	public boolean delMoTypeOfMoClass(int id) throws Exception {
		int result = sysMoService.delMoTypeOfMoClass(id);
		if (result > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 批量删除类型配置范围
	 */
	@RequestMapping("/delMultiMoTypesOfMoClass")
	@ResponseBody
	public boolean delMultiMoTypesOfMoClass(String ids) throws Exception {
		int result = 0;
		if (ids.contains(",") == true) {
			String[] idLst = ids.split(",");
			for (int i = 0; i < idLst.length; i++) {
				result = sysMoService.delMoTypeOfMoClass(Integer.parseInt(idLst[i]));
			}
		} else {
			result = sysMoService.delMoTypeOfMoClass(Integer.parseInt(ids));
		}
		
		if (result > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 新增类型配置范围
	 */
	@RequestMapping("/addMultiMoTypesOfMoClass")
	@ResponseBody
	public boolean addMultiMoTypesOfMoClass(SysMoTypeOfMoClassBean bean) throws Exception {
		int result = 0;
		if (bean.getMonitorTypeIDs().contains(",") == true) {
			String[] typeIds = bean.getMonitorTypeIDs().split(",");
			for (int i = 0; i < typeIds.length; i++) {
				bean.setMonitorTypeID(Integer.parseInt(typeIds[i]));
				result = sysMoService.insertMoTypeOfMoClass(bean);
			}
		} else {
			bean.setMonitorTypeID(Integer.parseInt(bean.getMonitorTypeIDs()));
			result = sysMoService.insertMoTypeOfMoClass(bean);
		}
		if (result > 0) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * 打开套用模板页面
	 * @throws Exception
	 */
	@RequestMapping("/toShowUseTemplateView")
	@ResponseBody
	public ModelAndView toShowUseTemplateView(HttpServletRequest request) {
		String moClassID = request.getParameter("moClassID");
		String moIDs = request.getParameter("moIDs");
		List<SysMonitorsTemplateBean> templateLst = sysMoService.queryMoTemplatesByClassID(Integer.parseInt(moClassID));
		Map<Integer,String> templateMap = new HashMap<Integer,String>();
		for (int i = 0; i < templateLst.size(); i++) {
			templateMap.put(templateLst.get(i).getTemplateID(), templateLst.get(i).getTemplateName());
		}
		if ("59".equals(moClassID) || "60".equals(moClassID)) {
			List<SysMonitorsTemplateBean> templateParentLst = sysMoService.queryMoTemplatesByClassID(Integer.parseInt("6"));
			for (int i = 0; i < templateParentLst.size(); i++) {
				templateMap.put(templateParentLst.get(i).getTemplateID(), templateParentLst.get(i).getTemplateName());
			}
		}
		request.setAttribute("moClassID", moClassID);
		request.setAttribute("moIDs", moIDs);
		request.setAttribute("templateMap", templateMap);
		return new ModelAndView("monitor/molist/sysMoTemplateUsed_edit");
	}
	
	/**
	 * 根据templateID获取监测器类型信息
	 */
	@RequestMapping("/listMoTypeByTemplateID")
	@ResponseBody
	public List<String> listMoTypeByTemplateID(Integer templateID) throws Exception {
		List<String> moList = new ArrayList<String>();
		moList = sysMoService.getMoTypeListByTemplateId(templateID);
		return moList;
	}
	
	/**
	 * 新增设备与模板关系
	 */
	@RequestMapping("/addMoTemplateOfMoDevice")
	@ResponseBody
	public boolean addMoTemplateOfMoDevice(SysMonitorsTemplateUsedBean bean) throws Exception {
		logger.info("新增设备{}与模板关系",bean.getMoIDs());
		return sysMoService.addMoTemplateOfMoDevice(bean);
	}
	
	
	/**
	 * 根据moID获取设备已使用模板
	 */
	@RequestMapping("/getDeviceUsedTemplate")
	@ResponseBody
	public SysMonitorsTemplateUsedBean getDeviceUsedTemplate(Integer moID) throws Exception {
		SysMonitorsTemplateUsedBean usedBean = sysMoService.getUsedTemplateByMoID(moID);
		return usedBean;
	}
	
	/**
	 * 监测模板与设备
	 */
	@RequestMapping("/existUsedTemplate")
	@ResponseBody
	public boolean existUsedTemplate(String templateIDs) throws Exception {
		int countVal = 0;
		if (templateIDs.contains(",") == true) {
			String[] templateID = templateIDs.split(",");
			for (int i = 0; i < templateID.length; i++) {
				countVal = sysMoService.queryUsedTemplateByTempId(Integer.parseInt(templateID[i]));
				if (countVal > 0) {
					return true;
				}
			}
		} else {
			countVal = sysMoService.queryUsedTemplateByTempId(Integer.parseInt(templateIDs));
		}
		
		if (countVal > 0) {
			return true;
		}
		return false;
	}
	

	/**
	 * 判断模板名称是否已经存在
	 */
	@RequestMapping("/checkMoTemplateName")
	@ResponseBody
	public int checkMoTemplateName(String templateName) throws Exception {
		return sysMoService.getCountByTemplateName(templateName);
		
	}
	
	
	/**
	 * 新增设备与模板关系
	 */
	@RequestMapping("/delMoTemplateOfMoDevice")
	@ResponseBody
	public boolean delMoTemplateOfMoDevice(String moIDs) throws Exception {
		int delResult = 0;
		if (moIDs.contains(",") == true) {
			String[] moID = moIDs.split(",");
			for (int i = 0; i < moID.length; i++) {
				delResult = sysMoService.delMoTemplateOfMoDevice(Integer.parseInt(moID[i]));
			}
		} else {
			delResult = sysMoService.delMoTemplateOfMoDevice(Integer.parseInt(moIDs));
		}
		if (delResult >= 0) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * 根据监测器类型ID判断模板是否被套用
	 */
	@RequestMapping("/isUsedTempByTypeID")
	@ResponseBody
	public boolean isUsedTempByTypeID(int monitorTypeID) throws Exception {
		int result = 0;
		List<SysMoTemplateIntervalBean> intervalLst = sysMoService.queryIntervalByTypeId(monitorTypeID);
		if (intervalLst != null) {
			for (int i = 0; i < intervalLst.size(); i++) {
				result = sysMoService.queryUsedTemplateByTempId(intervalLst.get(i).getTemplateID());
				//模板被使用
				if (result > 0) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	
	/**
	 * 监测类型配置是否被模板使用
	 */
	@RequestMapping("/isUsedByTemplate")
	@ResponseBody
	public boolean isUsedByTemplate(HttpServletRequest request) throws Exception {
		int countVal = 0;
		String moClassID = request.getParameter("moClassID");
		String monitorTypeIDs = request.getParameter("monitorTypeIDs");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("moClassID", moClassID);
		paramMap.put("monitorTypeIDs", monitorTypeIDs);
		countVal = sysMoService.isUsedByTemplate(paramMap);
		if (countVal > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 打开套用该模板设备的页面
	 */
	@RequestMapping("/doOpenDevice")
	public ModelAndView doOpenDevice(HttpServletRequest request) {
		String templateID = request.getParameter("templateID");
		request.setAttribute("templateID", templateID);
		return new ModelAndView("monitor/molist/sysMoTemplate_used");
	}
	/**
	 * 绑定的设备列表
	 */
	@RequestMapping("/listUsedDevice")
	@ResponseBody
	public Map<String, Object> listUsedDevice(SysMonitorsTemplateBean bean)
			throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MOKPIThresholdBean> page = new Page<MOKPIThresholdBean>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// paramMap.put("alarmName", alarmEventDefineBean.getAlarmName());
		paramMap.put("templateID", bean.getTemplateID());
		page.setParams(paramMap);
		List<MODeviceObj> moDeviceLst = sysMoService.listUsedDevice(page);
		// 获取总记录数
		int total = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", moDeviceLst);
		return result;
	}
	
}
