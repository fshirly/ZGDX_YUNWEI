package com.fable.insightview.monitor.perfTask.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.monitor.mobject.entity.MObjectDefBean;
import com.fable.insightview.monitor.mobject.mapper.MobjectInfoMapper;
import com.fable.insightview.monitor.perf.entity.PerfKPIDefBean;
import com.fable.insightview.monitor.perf.entity.SysKPIOfMOClassBean;
import com.fable.insightview.monitor.perf.service.IPerfKPIDefService;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.page.Page;

@Controller
@RequestMapping("/monitor/perfKPIDef")
public class PerfKPIDefController {
	@Autowired
	IPerfKPIDefService perfKPIDefService;
	@Autowired
	MobjectInfoMapper mobjectInfoMapper;
	private final Logger logger = LoggerFactory
			.getLogger(PerfKPIDefController.class);
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 加载列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/toPerfKPIDefList")
	public ModelAndView toPerfTaskList(ModelMap map,String navigationBar) {
		List<PerfKPIDefBean> categoryLst = perfKPIDefService.getAllCategory();
		map.put("categoryLst", categoryLst);
		ModelAndView mv=new ModelAndView("monitor/perf/perfKPIDefs_list");
		mv.addObject("navigationBar", navigationBar);
		return mv;
	}

	/**
	 * 加载指标数据表格
	 */
	@RequestMapping("/toListPerfKPIDef")
	@ResponseBody
	public Map<String, Object> listPerfKPIDef(PerfKPIDefBean perfKPIDefBean) {
		logger.info("开始加载采集指标数据。。。。。。。。");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<PerfKPIDefBean> page = new Page<PerfKPIDefBean>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", perfKPIDefBean.getName());
		paramMap.put("enName", perfKPIDefBean.getEnName());
		paramMap.put("kpiCategory", perfKPIDefBean.getKpiCategory());
		paramMap.put("className", perfKPIDefBean.getClassName());
		paramMap.put("isSupport", perfKPIDefBean.getIsSupport());
		page.setParams(paramMap);
		List<PerfKPIDefBean> perfKPIDefList = perfKPIDefService
				.getPerfKPIDefList(page);
		int total = page.getTotalRecord();
		logger.info("total=====" + total);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", perfKPIDefList);
		logger.info("加载数据结束。。。");
		return result;
	}

	/**
	 * 删除指标
	 * 
	 * @param mokpiThresholdBean
	 * @return
	 */
	@RequestMapping("/delPerfKPIDef")
	@ResponseBody
	public Map<String, Object> delPerfKPIDef(PerfKPIDefBean perfKPIDefBean) {
		List<Integer> kpiIDs = new ArrayList<Integer>();
		Map<String, Object> result = new HashMap<String, Object>();
		boolean flag = false;
		boolean checkRS = false;
		logger.info("删除前验证。。。。。start");
		boolean checkResult = perfKPIDefService.checkBeforeDel(perfKPIDefBean
				.getKpiID());
		if (checkResult == true) {
			kpiIDs.add(perfKPIDefBean.getKpiID());
			try {
				perfKPIDefService.delPerKPIDef(kpiIDs);
				flag = true;
			} catch (Exception e) {
				logger.error("删除指标异常：" + e);
				flag = false;
				checkRS = false;
			}
		} else {
			flag = false;
			checkRS = true;
		}
		result.put("flag", flag);
		result.put("checkRS", checkRS);
		return result;
	}

	/**
	 * 批量删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delPerfKPIDefs")
	@ResponseBody
	public Map<String, Object> delPerfKPIDefs(HttpServletRequest request) {
		logger.info("批量删除........start");
		boolean flag = false;
		boolean rs = false;
		boolean checkRs = false;
		String kpiIDs = request.getParameter("kpiIDs");
		String[] splitIds = kpiIDs.split(",");
		String kpiName = "";
		/* 能够被删的ID数组 */
		List<Integer> delId = new ArrayList<Integer>();
		/* 不能被删保留的ID数组 */
		List<Integer> reserveId = new ArrayList<Integer>();
		for (String strId : splitIds) {
			Integer kpiID = Integer.parseInt(strId);
			logger.info("删除前的验证");
			checkRs = perfKPIDefService.checkBeforeDel(kpiID);
			if (checkRs) {
				delId.add(kpiID);
				logger.info("能够被删的Id：" + kpiID);
			} else {
				reserveId.add(kpiID);
				logger.info("不能够被删而保留的Id：" + kpiID);
			}
		}
		logger.info("删除能够删除的指标.....start");
		try {
			perfKPIDefService.delPerKPIDef(delId);
			flag = true;
			rs = false;
		} catch (Exception e) {
			logger.error("删除异常：" + e.getMessage());
			flag = false;
			rs = false;
		}

		if (reserveId.size() > 0) {
			flag = false;
			String kName = "";
			for (int i = 0; i < reserveId.size(); i++) {
				kName = perfKPIDefService.getPerfKPIDefById(reserveId.get(i))
						.getName();
				kpiName = kpiName + kName + ",";
			}
			kpiName = kpiName.substring(0, kpiName.lastIndexOf(","));
			logger.info("不能删除的指标名称：" + kpiName);
			flag = false;
			rs = true;
		}
		Map<String, Object> result = new HashMap<String, Object>();
		kpiName = " 【 " + kpiName + " 】 ";
		result.put("flag", flag);
		result.put("rs", rs);
		result.put("kpiName", kpiName);
		return result;
	}

	/**
	 * 打开新增页面
	 */
	@RequestMapping("/toPerKPIDefAdd")
	@ResponseBody
	public ModelAndView toPerKPIDefAdd(HttpServletRequest request) {
		logger.info("加载新增页面");
		return new ModelAndView("monitor/perf/perfKPIDef_add");
	}

	/**
	 * 初始化对象类型树
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/initTree")
	@ResponseBody
	public Map<String, Object> initPortalTree() throws Exception {
		List<MObjectDefBean> mobjectList = mobjectInfoMapper.getMObject2();
		// List<MObjectDefBean> menuLst = new ArrayList<MObjectDefBean>();
		// for (int i = 0; i < mobjectList.size(); i++) {
		// MObjectDefBean bean = mobjectList.get(i);
		// if (bean.getParentClassId() != null || bean.getClassId() == 1) {
		// menuLst.add(bean);
		// }
		// }
		// for (int i = 0; i < mobjectList.size(); i++) {
		// MObjectDefBean bean = mobjectList.get(i);
		// if (bean.getParentClassId() == null) {
		// bean.setParentClassId(0);
		// }
		// }
		List<MObjectDefBean> treeData = new ArrayList<MObjectDefBean>();
		for (int i = 0; i < mobjectList.size(); i++) {
			if(mobjectList.get(i).getClassId() != -1){
				treeData.add(mobjectList.get(i));
			}
		}
		String menuLstJson = JsonUtil.toString(treeData);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("menuLstJson", menuLstJson);
		return result;
	}

	/**
	 * 指标名称唯一性的验证
	 */
	@RequestMapping("/checkPerKPIName")
	@ResponseBody
	public Map<String, Object> checkPerKPIName(PerfKPIDefBean perfKPIDefBean) {
		logger.info("验证指标名称的唯一性......");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		String flag = request.getParameter("flag");
		return perfKPIDefService.checkPerKPIName(flag, perfKPIDefBean);
	}

	/**
	 * 新增指标
	 * 
	 * @return
	 */
	@RequestMapping("addPerfKPIDef")
	@ResponseBody
	public boolean addPerfKPIDef(PerfKPIDefBean perfKPIDefBean) {
		return perfKPIDefService.addPerfKPIDef(perfKPIDefBean);
	}

	/**
	 * 打开更新页面
	 */
	@RequestMapping("/toPerKPIDefModify")
	@ResponseBody
	public ModelAndView toPerKPIDefModify(HttpServletRequest request) {
		logger.info("加载更新页面");
		String kpiID = request.getParameter("kpiID");
		request.setAttribute("kpiID", kpiID);
		return new ModelAndView("monitor/perf/perfKPIDef_modify");
	}

	/**
	 * 初始化指标详情信息
	 * 
	 * @param perfKPIDefBean
	 * @return
	 */
	@RequestMapping("initPerfKPIDefDetail")
	@ResponseBody
	public PerfKPIDefBean initPerfKPIDefDetail(PerfKPIDefBean perfKPIDefBean) {
		return perfKPIDefService.getPerfKPIDefById(perfKPIDefBean.getKpiID());
	}

	/**
	 * 更新指标
	 * 
	 * @return
	 */
	@RequestMapping("updatePerfKPIDef")
	@ResponseBody
	public boolean updatePerfKPIDef(PerfKPIDefBean perfKPIDefBean) {
		return perfKPIDefService.updatePerfKPIDef(perfKPIDefBean);
	}

	/**
	 * 打开查看页面
	 */
	@RequestMapping("/toPerKPIDefDetail")
	@ResponseBody
	public ModelAndView toPerKPIDefDetail(HttpServletRequest request) {
		logger.info("加载更新页面");
		String kpiID = request.getParameter("kpiID");
		request.setAttribute("kpiID", kpiID);
		return new ModelAndView("monitor/perf/perfKPIDef_detail");
	}
	
	/**
	 * 打开配置页面
	 */
	@RequestMapping("/toOpenSet")
	@ResponseBody
	public ModelAndView toOpenSet(HttpServletRequest request) {
		String kpiID = request.getParameter("kpiID");
		logger.info("加载配置页面，配置的指标id为："+kpiID);
		String classID = request.getParameter("classID");
		logger.info("配置的指标类型id为："+classID);
		request.setAttribute("kpiID", kpiID);
		request.setAttribute("classID", classID);
		return new ModelAndView("monitor/perf/perfKPIDef_config");
	}
	
	/**
	 * 加载指标与对象关系表格
	 * 
	 */
	@RequestMapping("/initTblKPIOfMOClass")
	@ResponseBody
	public Map<String, Object> initTblKPIOfMOClass(SysKPIOfMOClassBean bean){
		logger.info("开始加载指标与对象类型关系数据。。。。。。。。");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<SysKPIOfMOClassBean> page = new Page<SysKPIOfMOClassBean>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("kpiID", bean.getKpiID());
		page.setParams(paramMap);
		List<SysKPIOfMOClassBean> kpiOfMOClassList = perfKPIDefService
				.getKPIOfMOClassList(page);
		int total = page.getTotalRecord();
		logger.info("total=====" + total);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", kpiOfMOClassList);
		return result;
	}
	
	/**
	 * 判断指标与对象关系是否已存在
	 */
	@RequestMapping("/isExistKPIOfMOClass")
	@ResponseBody
	public boolean isExistKPIOfMOClass(SysKPIOfMOClassBean bean){
		return perfKPIDefService.isExistKPIOfMOClass(bean);
	}
	
	/**
	 * 新增指标与对象关系
	 */
	@RequestMapping("/addKPIOfMOClass")
	@ResponseBody
	public boolean addKPIOfMOClass(SysKPIOfMOClassBean bean){
		return perfKPIDefService.addKPIOfMOClass(bean);
	}

	/**
	 * 删除指标与对象关系
	 */
	@RequestMapping("/delKPIOfMOClass")
	@ResponseBody
	public boolean delKPIOfMOClass(int id){
		return perfKPIDefService.delKPIOfMOClass(id);
	}
	
	/**
	 * 获得所有的指标类别
	 * @return
	 */
	@RequestMapping("/getAllCategory")
	@ResponseBody 
	public List<PerfKPIDefBean> getAllCategory(){
		return perfKPIDefService.getAllCategory();
	}
	
	/**
	 * 初始化父对象类型树
	 */
	@RequestMapping("/initParentTree")
	@ResponseBody
	public Map<String, Object> initParentTree(HttpServletRequest request) throws Exception {
		String childClassID = request.getParameter("childClassID");
		List<Integer> ids = perfKPIDefService.getAllParentClassID(childClassID);
		return perfKPIDefService.initParentTree(ids);
	}
}
