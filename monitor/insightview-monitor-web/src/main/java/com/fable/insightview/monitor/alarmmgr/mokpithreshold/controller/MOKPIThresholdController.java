package com.fable.insightview.monitor.alarmmgr.mokpithreshold.controller;

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

import com.fable.insightview.monitor.alarmmgr.entity.MOKPIThresholdBean;
import com.fable.insightview.monitor.alarmmgr.mokpithreshold.mapper.MOKPIThresholdMapper;
import com.fable.insightview.monitor.alarmmgr.mokpithreshold.service.IMOKPIThresholdService;
import com.fable.insightview.monitor.database.entity.MODBMSServerBean;
import com.fable.insightview.monitor.database.entity.MOMySQLDBBean;
import com.fable.insightview.monitor.database.entity.MOMySQLDBServerBean;
import com.fable.insightview.monitor.database.entity.MOMySQLRunObjectsBean;
import com.fable.insightview.monitor.database.entity.MOMySQLVarsBean;
import com.fable.insightview.monitor.database.entity.MOOracleDataFileBean;
import com.fable.insightview.monitor.database.entity.MOOracleRollSEGBean;
import com.fable.insightview.monitor.database.entity.MOOracleSGABean;
import com.fable.insightview.monitor.database.entity.MOOracleTBSBean;
import com.fable.insightview.monitor.database.entity.OracleDbInfoBean;
import com.fable.insightview.monitor.database.service.IMyService;
import com.fable.insightview.monitor.database.service.IOracleService;
import com.fable.insightview.monitor.discover.entity.MODeviceObj;
import com.fable.insightview.monitor.discover.mapper.MODeviceMapper;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJMSBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJTABean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJVMBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJdbcDSBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJdbcPoolBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareMemoryBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareServletBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareThreadPoolBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareWebModuleBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.PerfTomcatClassLoadBean;
import com.fable.insightview.monitor.middleware.tomcat.service.IMiddlewareService;
import com.fable.insightview.monitor.middleware.tomcat.service.ITomcatService;
import com.fable.insightview.monitor.middleware.websphere.entity.MoMiddleWareJ2eeAppBean;
import com.fable.insightview.monitor.mobject.entity.MObjectDefBean;
import com.fable.insightview.monitor.mobject.mapper.MobjectInfoMapper;
import com.fable.insightview.monitor.mocpus.entity.MOCPUsBean;
import com.fable.insightview.monitor.mocpus.mapper.MOCPUsMapper;
import com.fable.insightview.monitor.momemories.entity.MOMemoriesBean;
import com.fable.insightview.monitor.momemories.mapper.MOMemoriesMapper;
import com.fable.insightview.monitor.monetworkIif.entity.MONetworkIfBean;
import com.fable.insightview.monitor.monetworkIif.mapper.MONetworkIfMapper;
import com.fable.insightview.monitor.movolumes.entity.MOVolumesBean;
import com.fable.insightview.monitor.movolumes.mapper.MOVolumesMapper;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.monitor.perf.entity.PerfKPIDefBean;
import com.fable.insightview.monitor.perf.mapper.PerfKPIDefMapper;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;

@Controller
@RequestMapping("/monitor/alarmmgr/moKPIThreshold")
public class MOKPIThresholdController {
	private Logger logger = LoggerFactory
			.getLogger(MOKPIThresholdController.class);
	@Autowired
	IMOKPIThresholdService thresholdService;
	@Autowired
	MOKPIThresholdMapper mokpiThresholdMapper;
	@Autowired
	MobjectInfoMapper mobjectInfoMapper;
	@Autowired
	PerfKPIDefMapper perfKPIDefMapper;
	@Autowired
	MODeviceMapper moDeviceMapper;
	@Autowired
	MOVolumesMapper moVolumesMapper;
	@Autowired
	MOMemoriesMapper moMemoriesMapper;
	@Autowired
	MONetworkIfMapper moNetworkIfMapper;
	@Autowired
	MOCPUsMapper mocpUsMapper;
	@Autowired
	IOracleService orclService;
	@Autowired
	IMiddlewareService middlewareService;
	@Autowired
	ITomcatService tomcatService;
	@Autowired
	IMyService myService;

	/**
	 * 阈值规则定义界面弹出
	 */
	@RequestMapping("/toMOKPIThreshold")
	@ResponseBody
	public ModelAndView toAlarmEventDefineList(String navigationBar) {
		logger.info("进入阈值规则定义界面");
		ModelAndView mv = new ModelAndView("monitor/alarmMgr/mokpithreshold/moKPIThreshold_list");
		mv.addObject("navigationBar", navigationBar);
		return mv;
	}

	/**
	 * 加载表格数据
	 */
	@RequestMapping("/listMOKPIThreshold")
	@ResponseBody
	public Map<String, Object> listThreshold(
			MOKPIThresholdBean mokpiThresholdBean) {
		logger.debug("开始加载数据。。。。。。。。");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MOKPIThresholdBean> page = new Page<MOKPIThresholdBean>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// paramMap.put("alarmName", alarmEventDefineBean.getAlarmName());
		paramMap.put("className", mokpiThresholdBean.getClassName());
		page.setParams(paramMap);
		List<MOKPIThresholdBean> thresholdList = thresholdService
				.selectThreshold(page);

		int total = page.getTotalRecord();
		logger.info("total=====" + total);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", thresholdList);
		logger.info("加载数据结束。。。");
		return result;
	}

	/**
	 * 删除阈值规则定义
	 */
	@RequestMapping("/delThreshold")
	@ResponseBody
	public boolean delThreshold(MOKPIThresholdBean mokpiThresholdBean) {
		List<Integer> ruleIDs = new ArrayList<Integer>();
		ruleIDs.add(mokpiThresholdBean.getRuleID());
		return thresholdService.delThreshold(ruleIDs);
	}

	/**
	 * 删除阈值规则定义
	 */
	@RequestMapping("/delThresholds")
	@ResponseBody
	public boolean delThresholds(HttpServletRequest request) {
		String ruleIDs = request.getParameter("ruleIDs");
		List<Integer> ruleIDList = new ArrayList<Integer>();
		String[] splitIds = ruleIDs.split(",");
		for (String s : splitIds) {
			ruleIDList.add(Integer.parseInt(s));
		}
		return thresholdService.delThreshold(ruleIDList);
	}

	/**
	 * 打开新增页面
	 */
	@RequestMapping("/toShowThresholdAdd")
	@ResponseBody
	public ModelAndView toShowThresholdAdd(HttpServletRequest request) {
		logger.info("加载新增页面");
		return new ModelAndView(
				"monitor/alarmMgr/mokpithreshold/moKPIThreshold_add");
	}

	/**
	 * 打开管理对象定义页面
	 */
	@RequestMapping("/toSelectMObjectDef")
	@ResponseBody
	public ModelAndView toSelectMObjectDef(HttpServletRequest request,
			HttpServletResponse response) {
		String flag = request.getParameter("flag");
		if (flag != null && !"".equals(flag)) {
			request.setAttribute("flag", flag);
		} else {
			request.setAttribute("flag", "doInit");
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("monitor/mobject/mobject_list");
		return mv;
	}

	/**
	 * 加载对象类型数据表格
	 */
	@RequestMapping("/selectMObject")
	@ResponseBody
	public Map<String, Object> listMObject(MObjectDefBean mobjectDefBean) {
		logger.info("开始加载管理对象定义数据。。。。。。。。");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MObjectDefBean> page = new Page<MObjectDefBean>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("className", mobjectDefBean.getClassName());
		page.setParams(paramMap);
		List<MObjectDefBean> mobjectList = mobjectInfoMapper
				.selectMobject(page);
		int total = page.getTotalRecord();
		logger.info("total=====" + total);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", mobjectList);
		logger.info("加载数据结束。。。");
		return result;
	}

	/**
	 * 初始化对象类型树
	 */
	@RequestMapping("/findMObject")
	@ResponseBody
	public Map<String, Object> findMObject(MObjectDefBean mobjectDefBean)
			throws Exception {
		return thresholdService.findMObject(mobjectDefBean);

	}

	/**
	 * 打开指标页面
	 */
	@RequestMapping("/toSelectPerfKPIDef")
	@ResponseBody
	public ModelAndView toSelectPerfKPIDef(HttpServletRequest request,
			HttpServletResponse response) {
		String flag = request.getParameter("flag");
		if (flag != null && !"".equals(flag)) {
			request.setAttribute("flag", flag);
		} else {
			request.setAttribute("flag", "doInit");
		}
		String classID = request.getParameter("classID");
		if (classID != null && !"".equals(classID)) {
			request.setAttribute("classID", classID);
		} 
		ModelAndView mv = new ModelAndView();
		mv.setViewName("monitor/perf/perfKPIDef_list");
		return mv;
	}

	/**
	 * 加载指标数据表格
	 */
	@RequestMapping("/selectPerfKPIDef")
	@ResponseBody
	public Map<String, Object> listPerfKPIDef(PerfKPIDefBean perfKPIDefBean) {
		return thresholdService.listPerfKPIDef(perfKPIDefBean);
	}

	@RequestMapping("/findPerfKPIDef")
	@ResponseBody
	public PerfKPIDefBean findPerfKPIDef(PerfKPIDefBean perfKPIDefBean) {
		PerfKPIDefBean perfKPIDef = perfKPIDefMapper
				.getPerfKPIDefById(perfKPIDefBean.getKpiID());
		return perfKPIDef;
	}

	/**
	 * 阈值规则定义唯一性的验证(新增)
	 */
	@RequestMapping("/checkBeforeAdd")
	@ResponseBody
	public boolean checkBeforeAdd(MOKPIThresholdBean mokpiThresholdBean) {

		logger.info("验证告警名称的唯一性......");
		return thresholdService.checkBeforeAdd(mokpiThresholdBean);
	}

	/**
	 * 新增阈值规则定义
	 */
	@RequestMapping("addThreshold")
	@ResponseBody
	public boolean addThreshold(MOKPIThresholdBean mokpiThresholdBean) {
		return thresholdService.addThreshold(mokpiThresholdBean);
	}

	/**
	 * 打开查看页面
	 */
	@RequestMapping("/toShowThresholdDetail")
	@ResponseBody
	public ModelAndView toShowThresholdDetail(HttpServletRequest request) {
		logger.info("打开查看页面");
		String ruleID = request.getParameter("ruleID");
		request.setAttribute("ruleID", ruleID);
		return new ModelAndView(
				"monitor/alarmMgr/mokpithreshold/moKPIThreshold_detail");
	}

	/**
	 * 查看页面详情
	 */
	@RequestMapping("/initThresholdDetail")
	@ResponseBody
	public MOKPIThresholdBean initThresholdDetail(
			MOKPIThresholdBean mokpiThresholdBean) {

		logger.info("初始化查看页面。。。。");
		return thresholdService.initThresholdDetail(mokpiThresholdBean);
	}

	/**
	 * 打开编辑页面
	 */
	@RequestMapping("/toShowThresholdModify")
	@ResponseBody
	public ModelAndView toShowThresholdModify(HttpServletRequest request) {
		logger.info("打开编辑页面");
		String ruleID = request.getParameter("ruleID");
		request.setAttribute("ruleID", ruleID);
		return new ModelAndView(
				"monitor/alarmMgr/mokpithreshold/moKPIThreshold_modify");
	}

	/**
	 * 阈值规则定义唯一性的验证(编辑)
	 */
	@RequestMapping("/checkBeforeUpdate")
	@ResponseBody
	public boolean checkBeforeUpdate(MOKPIThresholdBean mokpiThresholdBean) {

		logger.info("验证告警名称的唯一性......");
		return thresholdService.checkBeforeUpdate(mokpiThresholdBean);
	}

	/**
	 * 编辑阈值规则定义
	 */
	@RequestMapping("updateThreshold")
	@ResponseBody
	public boolean updateThreshold(MOKPIThresholdBean mokpiThresholdBean) {

		logger.info(" 更新阈值规则定义......");
		return thresholdService.updateThreshold(mokpiThresholdBean);
	}

	/**
	 * 初始化对象类型树
	 */
	@RequestMapping("/initTree")
	@ResponseBody
	public Map<String, Object> initPortalTree() throws Exception {
		List<MObjectDefBean> menuLst = mobjectInfoMapper
				.queryMObjectRelation2();
		List<MObjectDefBean> treeData = new ArrayList<MObjectDefBean>();
		for (int i = 0; i < menuLst.size(); i++) {
			MObjectDefBean bean = menuLst.get(i);
			if (bean.getParentClassId() == null) {
				bean.setParentClassId(0);
			}
			if(bean.getClassId() != -1 && bean.getClassId() != 75 && bean.getClassId() != 89 && bean.getClassId() != 91 && bean.getClassId() != 92 && bean.getClassId() != 93 && bean.getClassId() != 94){
				treeData.add(bean);
			}
		}
		String menuLstJson = JsonUtil.toString(treeData);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("menuLstJson", menuLstJson);
		return result;
	}

	/**
	 * 查询设备IP列表 以供选择
	 */
	@RequestMapping("/findDeviceInfo")
	@ResponseBody
	public MODeviceObj findDeviceInfo(MOKPIThresholdBean bean) throws Exception {
		MODeviceObj moDevice = moDeviceMapper.selectByPrimaryKey(bean.getMoID());
		return moDevice;
	}

	/**
	 * 设备接口列表
	 */
	@RequestMapping("/toNetworkIf")
	public ModelAndView toNetworkIf(HttpServletRequest request,
			HttpServletResponse response) {
		String flag = request.getParameter("flag");
		String deviceMOID = request.getParameter("deviceMOID");
		if (flag != null && !"".equals(flag)) {
			request.setAttribute("flag", flag);
		} else {
			request.setAttribute("flag", "doInit");
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("monitor/component/monetworkIfList");
		mv.addObject("deviceMOID", deviceMOID);
		return mv;
	}

	/**
	 * 加载接口列表
	 */
	@RequestMapping("/selectNetworkIf")
	@ResponseBody
	public Map<String, Object> selectNetworkIf(MONetworkIfBean moNetworkIfBean)
			throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MONetworkIfBean> page = new Page<MONetworkIfBean>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("deviceMOID", moNetworkIfBean.getDeviceMOID());
		paramMap.put("ifName", moNetworkIfBean.getIfName());
		page.setParams(paramMap);

		List<MONetworkIfBean> networkIfList = moNetworkIfMapper
				.selectMONetworkIf(page);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", page.getTotalRecord());
		result.put("rows", networkIfList);
		return result;
	}

	/**
	 * 查询接口信息
	 */
	@RequestMapping("/findNetworkIfInfo")
	@ResponseBody
	public MONetworkIfBean findNetworkIfInfo(MONetworkIfBean bean)
			throws Exception {
		MONetworkIfBean networkIf = moNetworkIfMapper.getMONetworkIfById(bean
				.getMoID());
		return networkIf;
	}

	/**
	 * 设备内存列表
	 */
	@RequestMapping("/toMemoriesList")
	public ModelAndView toMemoriesList(HttpServletRequest request,
			HttpServletResponse response) {
		String flag = request.getParameter("flag");
		String deviceMOID = request.getParameter("deviceMOID");
		if (flag != null && !"".equals(flag)) {
			request.setAttribute("flag", flag);
		} else {
			request.setAttribute("flag", "doInit");
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("monitor/component/momemoriesList");
		mv.addObject("deviceMOID", deviceMOID);
		return mv;
	}

	/**
	 * 加载内存列表
	 */
	@RequestMapping("/selectMemoriesList")
	@ResponseBody
	public Map<String, Object> selectMemoriesList(MOMemoriesBean moMemories)
			throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MOMemoriesBean> page = new Page<MOMemoriesBean>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("deviceMOID", moMemories.getDeviceMOID());
		paramMap.put("moName", moMemories.getMoName());
		page.setParams(paramMap);

		List<MOMemoriesBean> memoriesList = thresholdService
				.selectMOMemories(page);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", page.getTotalRecord());
		result.put("rows", memoriesList);
		return result;
	}

	/**
	 * 查询内存信息
	 */
	@RequestMapping("/findMemoriesInfo")
	@ResponseBody
	public MOMemoriesBean findMemoriesInfo(MOMemoriesBean bean)
			throws Exception {
		MOMemoriesBean memories = moMemoriesMapper.getMOMemoriesById(bean
				.getMoID());
		return memories;
	}

	/**
	 * 设备磁盘列表页面
	 */
	@RequestMapping("/toVolumeList")
	public ModelAndView toVolumeList(HttpServletRequest request,
			HttpServletResponse response) {
		String flag = request.getParameter("flag");
		String deviceMOID = request.getParameter("deviceMOID");
		if (flag != null && !"".equals(flag)) {
			request.setAttribute("flag", flag);
		} else {
			request.setAttribute("flag", "doInit");
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("monitor/component/movolumesList");
		mv.addObject("deviceMOID", deviceMOID);
		return mv;
	}

	/**
	 * 加载磁盘列表
	 */
	@RequestMapping("/selectVolumesList")
	@ResponseBody
	public Map<String, Object> selectDeviceList(MOVolumesBean moVolumes)
			throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MOVolumesBean> page = new Page<MOVolumesBean>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("deviceMOID", moVolumes.getDeviceMOID());
		paramMap.put("moName", moVolumes.getMoName());
		page.setParams(paramMap);

		List<MOVolumesBean> volumnList = thresholdService.selectMOVolumes(page);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", page.getTotalRecord());
		result.put("rows", volumnList);
		return result;
	}

	/**
	 * 查询磁盘信息
	 */
	@RequestMapping("/findVolumesInfo")
	@ResponseBody
	public MOVolumesBean findVolumesInfo(MOVolumesBean bean) throws Exception {
		MOVolumesBean volumes = moVolumesMapper.getVolumesById(bean.getMoID());
		return volumes;
	}

	/**
	 * 设备CPU列表页面
	 */
	@RequestMapping("/toCPUsList")
	public ModelAndView toCPUsList(HttpServletRequest request,
			HttpServletResponse response) {
		String flag = request.getParameter("flag");
		String deviceMOID = request.getParameter("deviceMOID");
		if (flag != null && !"".equals(flag)) {
			request.setAttribute("flag", flag);
		} else {
			request.setAttribute("flag", "doInit");
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("monitor/component/cpusList");
		mv.addObject("deviceMOID", deviceMOID);
		return mv;
	}

	/**
	 * 加载CPU列表
	 */
	@RequestMapping("/selectCPUsList")
	@ResponseBody
	public Map<String, Object> selectCPUsList(MOCPUsBean moCPUsBean)
			throws Exception {

		logger.info("加载CPU列表....");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MOCPUsBean> page = new Page<MOCPUsBean>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("deviceMOID", moCPUsBean.getDeviceMOID());
		paramMap.put("moName", moCPUsBean.getMoName());
		page.setParams(paramMap);

		List<MOCPUsBean> cpusList = mocpUsMapper.selectMOCPUs(page);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", page.getTotalRecord());
		result.put("rows", cpusList);
		return result;
	}

	/**
	 * 查询CPU信息
	 */
	@RequestMapping("/findCPUsInfo")
	@ResponseBody
	public MOCPUsBean findCPUsInfo(MOMemoriesBean bean) throws Exception {
		MOCPUsBean mocpus = mocpUsMapper.getMOCPUsId(bean.getMoID());
		return mocpus;
	}

	/**
	 * 验证接口的源对象与管理对象是否一致
	 */
	@RequestMapping("/checkNetworkIfMOID")
	@ResponseBody
	public boolean checkNetworkIfMOID(HttpServletRequest request) {
		String deviceMOID = request.getParameter("deviceMOID");
		String moID = request.getParameter("moID");
		MONetworkIfBean bean = new MONetworkIfBean();
		bean.setDeviceMOID(Integer.parseInt(deviceMOID));
		bean.setMoID(Integer.parseInt(moID));
		return thresholdService.checkNetworkIfMOID(bean);
	}

	/**
	 * 验证磁盘的源对象与管理对象是否一致
	 */
	@RequestMapping("/checkVolMOID")
	@ResponseBody
	public boolean checkVolMOID(HttpServletRequest request) {
		String deviceMOID = request.getParameter("deviceMOID");
		String moID = request.getParameter("moID");
		MOVolumesBean bean = new MOVolumesBean();
		bean.setDeviceMOID(Integer.parseInt(deviceMOID));
		bean.setMoID(Integer.parseInt(moID));
		return thresholdService.checkVolMOID(bean);
	}

	/**
	 * 验证CPU的源对象与管理对象是否一致
	 */
	@RequestMapping("/checkCPUtMOID")
	@ResponseBody
	public boolean checkCPUtMOID(HttpServletRequest request) {
		String deviceMOID = request.getParameter("deviceMOID");
		String moID = request.getParameter("moID");
		return thresholdService.checkCPUtMOID(deviceMOID, moID);
	}

	/**
	 * 验证内存的源对象与管理对象是否一致
	 */
	@RequestMapping("/checkMemMOID")
	@ResponseBody
	public boolean checkMemMOID(HttpServletRequest request) {
		String deviceMOID = request.getParameter("deviceMOID");
		String moID = request.getParameter("moID");
		return thresholdService.checkMemMOID(deviceMOID, moID);
	}

	/**
	 * 查询线程池信息
	 */
	@RequestMapping("/findThreadPoolInfo")
	@ResponseBody
	public MOMiddleWareThreadPoolBean findThreadPoolInfo(
			MOMiddleWareThreadPoolBean bean) throws Exception {
		MOMiddleWareThreadPoolBean threadPoolBean = tomcatService
				.getThreadPoolByID(bean.getMoId());
		return threadPoolBean;
	}

	/**
	 * 查询类加载信息
	 */
	@RequestMapping("/findClassLoadInfo")
	@ResponseBody
	public PerfTomcatClassLoadBean findClassLoadInfo(
			PerfTomcatClassLoadBean bean) throws Exception {
		PerfTomcatClassLoadBean classLoadBean = tomcatService
				.getClassLoadByID(bean.getMoId());
		return classLoadBean;
	}

	/**
	 * 查询JDBC数据源
	 */
	@RequestMapping("/findJdbcDSInfo")
	@ResponseBody
	public MOMiddleWareJdbcDSBean findJdbcDSInfo(MOMiddleWareJdbcDSBean bean)
			throws Exception {
		MOMiddleWareJdbcDSBean jdbcDSBean = tomcatService.getJdbcDSByID(bean
				.getMoId());
		return jdbcDSBean;
	}

	/**
	 * 查询JDBC连接池
	 */
	@RequestMapping("/findJdbcPoolInfo")
	@ResponseBody
	public MOMiddleWareJdbcPoolBean findJdbcPoolInfo(
			MOMiddleWareJdbcPoolBean bean) throws Exception {
		MOMiddleWareJdbcPoolBean jdbcPoolBean = tomcatService
				.getJdbcPoolByID(bean.getMoId());
		return jdbcPoolBean;
	}

	/**
	 * 查询中间件内存池
	 */
	@RequestMapping("/findMemPoolInfo")
	@ResponseBody
	public MOMiddleWareMemoryBean findMemPoolInfo(MOMiddleWareMemoryBean bean)
			throws Exception {
		MOMiddleWareMemoryBean memoryBean = tomcatService.getMemPoolByID(bean
				.getMoId());
		return memoryBean;
	}

	/**
	 * 查询中间件JTA
	 */
	@RequestMapping("/findMiddleWareJTAInfo")
	@ResponseBody
	public MOMiddleWareJTABean findMiddleWareJTAInfo(MOMiddleWareJTABean bean)
			throws Exception {
		MOMiddleWareJTABean jtaBean = tomcatService.getMiddleWareJTAByID(bean
				.getMoId());
		return jtaBean;
	}

	/**
	 * 查询Java虚拟机
	 */
	@RequestMapping("/findMiddlewareJvmInfo")
	@ResponseBody
	public MOMiddleWareJVMBean findMiddlewareJvmInfo(MOMiddleWareJVMBean bean)
			throws Exception {
		MOMiddleWareJVMBean jvmBean = tomcatService.getMiddlewareJvmByID(bean
				.getMoId());
		return jvmBean;
	}

	/**
	 * mysql(运行对象)页面跳转
	 */
	@RequestMapping("/toMysqlRunObj")
	public ModelAndView toMysqlRunObj(HttpServletRequest request, ModelMap map)
			throws Exception {
		map.put("flag", request.getParameter("flag"));
		map.put("index", request.getParameter("index"));
		map.put("sqlServerMOID", request.getParameter("sqlServerMOID"));
		return new ModelAndView("monitor/device/mysql_runobj");
	}

	/**
	 * 查询运行对象
	 */
	@RequestMapping("/findMysqlRunObjInfo")
	@ResponseBody
	public MOMySQLRunObjectsBean findMysqlRunObjInfo(MOMySQLRunObjectsBean bean)
			throws Exception {
		MOMySQLRunObjectsBean runBean = myService.getMySQLRunObjByID(bean
				.getMoId());
		return runBean;
	}

	/**
	 * 查询表空间信息
	 */
	@RequestMapping("/findOracleTBSInfo")
	@ResponseBody
	public MOOracleTBSBean findOracleTBSInfo(MOOracleTBSBean bean)
			throws Exception {
		MOOracleTBSBean tbsBean = orclService
				.getTbsDetailByMoId(bean.getMoid());
		return tbsBean;
	}

	/**
	 * 查询oracle回滚段信息
	 */
	@RequestMapping("/findOracleRollSegInfo")
	@ResponseBody
	public MOOracleRollSEGBean findOracleRollSegInfo(MOOracleRollSEGBean bean)
			throws Exception {
		MOOracleRollSEGBean rollSEGBean = orclService.getOrclRollSEGDetail(bean
				.getMoID());
		return rollSEGBean;
	}

	/**
	 * 查询数据文件信息
	 */
	@RequestMapping("/findOracleDataFileInfo")
	@ResponseBody
	public MOOracleDataFileBean findOracleDataFileInfo(MOOracleDataFileBean bean)
			throws Exception {
		MOOracleDataFileBean fileBean = orclService.getDataFileByMoId(bean
				.getMoId());
		return fileBean;
	}

	/**
	 * 查询SGA信息
	 */
	@RequestMapping("/findOracleSgaInfo")
	@ResponseBody
	public MOOracleSGABean findOracleSgaInfo(MOOracleSGABean bean)
			throws Exception {
		MOOracleSGABean sgaBean = orclService.getOrclSGAByMoID(bean.getMoID());
		return sgaBean;
	}

	/**
	 * 查询Oracle下面的数据库信息
	 */
	@RequestMapping("/findOracleDbInfo")
	@ResponseBody
	public OracleDbInfoBean findOracleDbInfo(OracleDbInfoBean bean)
			throws Exception {
		OracleDbInfoBean oracleDbInfoBean = orclService.getOracleDbByMoId(bean
				.getMoId());
		return oracleDbInfoBean;
	}

	/**
	 * 查询Oracle数据库实例信息
	 */
	@RequestMapping("/findOracleInsInfo")
	@ResponseBody
	public MODBMSServerBean findOracleInsInfo(MODBMSServerBean bean)
			throws Exception {
		MODBMSServerBean serverBean = orclService.getOrclInstanceByMoid(bean
				.getMoid());
		return serverBean;
	}

	/**
	 * 查询MysqlServer信息
	 */
	@RequestMapping("/findMysqlServerInfo")
	@ResponseBody
	public MOMySQLDBServerBean findMysqlServerInfo(MOMySQLDBServerBean bean)
			throws Exception {
		MOMySQLDBServerBean serverBean = myService.getMysqlServerByID(bean
				.getMoId());
		return serverBean;
	}

	/**
	 * 查询Mysql下数据库信息
	 */
	@RequestMapping("/findMysqlDBInfo")
	@ResponseBody
	public MOMySQLDBBean findMysqlDBInfo(MOMySQLDBBean bean) throws Exception {
		MOMySQLDBBean sqldbBean = myService.getMysqlDBByID(bean.getMoId());
		return sqldbBean;
	}

	/**
	 * 查询系统变量信息
	 */
	@RequestMapping("/findMysqlSysVarInfo")
	@ResponseBody
	public MOMySQLVarsBean findMysqlSysVarInfo(MOMySQLVarsBean bean)
			throws Exception {
		MOMySQLVarsBean sqlVarsBean = myService
				.getMysqlVarsByID(bean.getMoId());
		return sqlVarsBean;
	}

	/**
	 * 查询将j2ee应用信息
	 */
	@RequestMapping("/findJ2eeAppInfo")
	@ResponseBody
	public MoMiddleWareJ2eeAppBean findJ2eeAppInfo(MoMiddleWareJ2eeAppBean bean)
			throws Exception {
		MoMiddleWareJ2eeAppBean j2eeAppBean = tomcatService.getJ2eeAppByID(bean
				.getMoId());
		return j2eeAppBean;
	}

	/**
	 * 查询将JMS信息
	 */
	@RequestMapping("/findMiddleJMSInfo")
	@ResponseBody
	public MOMiddleWareJMSBean findMiddleJMSInfo(MOMiddleWareJMSBean bean)
			throws Exception {
		MOMiddleWareJMSBean jmsBean = tomcatService.getMiddleJMSByID(bean
				.getMoId());
		return jmsBean;
	}

	/**
	 * 查询将WebModule信息
	 */
	@RequestMapping("/findWebModuleInfo")
	@ResponseBody
	public MOMiddleWareWebModuleBean findWebModuleInfo(
			MOMiddleWareWebModuleBean bean) throws Exception {
		MOMiddleWareWebModuleBean webModuleBean = tomcatService
				.getWebModuleByID(bean.getMoId());
		return webModuleBean;
	}

	/**
	 * 查询将WebModule信息
	 */
	@RequestMapping("/findServletInfo")
	@ResponseBody
	public MOMiddleWareServletBean findServletInfo(MOMiddleWareServletBean bean)
			throws Exception {
		MOMiddleWareServletBean servletBean = tomcatService.getServletByID(bean
				.getMoId());
		return servletBean;
	}
	
	/**
	 * 初始化指标的对象类型
	 */
	@RequestMapping("/initObject")
	@ResponseBody
	public List<MObjectDefBean> initObject(HttpServletRequest request){
		int kpiID = -1;
		if(!"".equals(request.getParameter("kpiID"))){
			kpiID = Integer.parseInt(request.getParameter("kpiID"));
			return thresholdService.initObject(kpiID);
		}else{
			logger.error("指标为空！");
			return null;
		}
	}
}
