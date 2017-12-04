package com.fable.insightview.monitor.discover.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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

import com.fable.insightview.monitor.database.service.IOracleService;
import com.fable.insightview.monitor.discover.entity.MODeviceObj;
import com.fable.insightview.monitor.discover.entity.SafeDeviceObj;
import com.fable.insightview.monitor.discover.mapper.SafeDeviceMapper;
import com.fable.insightview.monitor.host.entity.HostComm;
import com.fable.insightview.monitor.host.entity.KPINameDef;
import com.fable.insightview.monitor.host.entity.MODevice;
import com.fable.insightview.monitor.host.entity.MOSafeDevice;
import com.fable.insightview.monitor.host.mapper.HostMapper;
import com.fable.insightview.monitor.mocpus.entity.MOCPUsBean;
import com.fable.insightview.monitor.mocpus.mapper.MOCPUsMapper;
import com.fable.insightview.monitor.momemories.entity.MOMemoriesBean;
import com.fable.insightview.monitor.momemories.mapper.MOMemoriesMapper;
import com.fable.insightview.monitor.monetworkIif.entity.MONetworkIfBean;
import com.fable.insightview.monitor.monetworkIif.mapper.MONetworkIfMapper;
import com.fable.insightview.monitor.movolumes.entity.MOVolumesBean;
import com.fable.insightview.monitor.movolumes.mapper.MOVolumesMapper;
import com.fable.insightview.monitor.process.entity.MoProcessBean;
import com.fable.insightview.monitor.process.mapper.ProcessInfoMapper;
import com.fable.insightview.monitor.website.mapper.WebSiteMapper;
import com.fable.insightview.platform.dict.util.DictionaryLoader;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.portal.mapper.PortalInfoMapper;

/**
 * @Description: 安全设备
 * @author liuw
 * @Date 2016-9-20
 */
@Controller
@RequestMapping("/monitor/safeDeviceManager")
public class DiscoverSafeDeviceController {
	private final static Logger logger = LoggerFactory
			.getLogger(DiscoverSafeDeviceController.class);
	@Autowired
	SafeDeviceMapper safeDeviceMapper;
	@Autowired
	IOracleService orclService;
	@Autowired 
	WebSiteMapper webSiteMapper;
	@Autowired
	PortalInfoMapper portalInfoMapper;
	@Autowired
	HostMapper modMaper;
	
	@Autowired
	MOCPUsMapper mocpUsMapper;
	@Autowired
	MOMemoriesMapper moMemoriesMapper;
	@Autowired
	MONetworkIfMapper moNetworkIfMapper;
	@Autowired
	MOVolumesMapper moVolumesMapper;
	@Autowired
	ProcessInfoMapper processInfoMapper;
	String portalContent = null;
	
	String moClass;// 设备类型ID
	String tableName;// 表名
	Integer MOID;
	Integer moClassID;
	String cpuUrl;// CPU 曲线跳转地址
	String memUrl;// 内存 曲线跳转地址
	String diskUrl;// 硬盘 曲线跳转地址
	String storeUrl;// 数据存储 曲线跳转地址
	String ifTableName;// 接口表名
	DecimalFormat   format = new DecimalFormat("0.00");
	
	
	/**
	 * 获取接口页面显示数据
	 * 
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listInterface")
	@ResponseBody
	public Map<String, Object> listInterface(MONetworkIfBean vo,
			HttpServletRequest request) throws Exception {
		logger.info("开始...获取页面显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MONetworkIfBean> page = new Page<MONetworkIfBean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("mOClassID", request.getParameter("mOClassID"));
		paramMap.put("deviceIP", vo.getDeviceIP());
		paramMap.put("deviceMOName", vo.getDeviceMOName());
		paramMap.put("operStatus", vo.getOperStatus());
	/*	paramMap.put("timeBegin", KPINameDef.queryBetweenTime().get("timeBegin"));
		paramMap.put("timeEnd", KPINameDef.queryBetweenTime().get("timeEnd"));*/
		paramMap.put("MID", KPINameDef.JOBPINGPOLL);
		page.setParams(paramMap);
		// 查询分页数据
		List<MONetworkIfBean> list = moNetworkIfMapper.queryList(page);
		// 查询数据字典关联数据
		Map<Integer, String> osMap = DictionaryLoader.getConstantItems("OperStatus");// 可用状态
		Map<Integer, String> interfaceTypeMap = DictionaryLoader.getConstantItems("InterfaceType");// 设备类型名称
		for (MONetworkIfBean mo : list) {
			 //获得数据字典中的设备类型名称
			mo.setIfTypeName(interfaceTypeMap.get(Integer.parseInt(mo.getIfType())));
			/*if (1 == mo.getOperStatus() || 2 == mo.getOperStatus()) {
				mo.setOperStatusName(osMap.get(mo.getOperStatus()));
			} else {
				mo.setOperStatusName(osMap.get(3));
			}*/
			if (mo.getIfSpeed() != null && !"".equals(mo.getIfSpeed())) {
				mo.setIfSpeed(HostComm.getBytesToSpeed(Long.parseLong(mo.getIfSpeed())));
			}

		}
		getListInterfaceInfo(list,vo);
		// 查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", list);
		logger.info("结束...获取页面显示数据");
		return result;
	}
	
	/***
	 * 获取接口采集信息
	 * @param list
	 * @param vo
	 * @return
	 */
	public  List<MONetworkIfBean>  getListInterfaceInfo(List<MONetworkIfBean> list,MONetworkIfBean vo){
		int period=1;
		long curr=0;
		long currTime=orclService.getCurrentDate().getTime();
		Date collectTime;
		if(list!=null){
			for (int i = 0; i < list.size(); i++) {
				 vo = list.get(i);
				 collectTime=vo.getCollectTime();
				if(vo.getDoIntervals()==0){
					period=vo.getDefDoIntervals()*getMultiple()*60000;
				}else{
					period=vo.getDoIntervals()*getMultiple()*1000;
				}
				if (collectTime != null) {
					curr=currTime-vo.getCollectTime().getTime();
					if(curr<=period){					
							if (1 == vo.getOperStatus()) {
								vo.setOperaTip("UP");
								vo.setOperstatusdetail("up.png");
							} else if (2 == vo.getOperStatus()) {
								vo.setOperaTip("DOWN");
								vo.setOperstatusdetail("down.png");
							} 
					}else{
						vo.setOperaTip("未知");
						vo.setOperstatusdetail("unknown.png");
					}
				}else{
					vo.setOperaTip("未知");
					vo.setOperstatusdetail("unknown.png");
				}
			}
		}
	return  list;
	}
	
	/**
	 * 获取cpu页面显示数据
	 * 
	 * @param vo
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/listCpu")
	@ResponseBody
	public Map<String, Object> listCpu(MOCPUsBean vo, HttpServletRequest request)
			throws Exception {
		logger.info("开始...获取页面显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MOCPUsBean> page = new Page<MOCPUsBean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("mOClassID", request.getParameter("mOClassID"));
		paramMap.put("deviceMOName", vo.getDeviceMOName());
		//设备ip查询参数
		paramMap.put("deviceIP", vo.getDeviceIP());
		page.setParams(paramMap);
		// 查询分页数据
		List<MOCPUsBean> list = mocpUsMapper.queryList(page);
		// 查询数据字典关联数据
		Map<Integer, String> osMap = DictionaryLoader
				.getConstantItems("OperStatus");// 可用状态
		Map<Integer, String> asMap = DictionaryLoader
				.getConstantItems("AdminStatus");// 操作状态

		for (MOCPUsBean mo : list) {
			mo.setOperStatusName(osMap.get(mo.getOperStatus()));
			mo.setAdminStatusName(asMap.get(mo.getAdminStatus()));
		}
		// 查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", list);
		logger.info("结束...获取页面显示数据");
		return result;
	}
	
	
	/**
	 * 获取磁盘页面显示数据
	 * 
	 * @param vo
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/listDisc")
	@ResponseBody
	public Map<String, Object> listDisc(MOVolumesBean vo,
			HttpServletRequest request) throws Exception {
		logger.info("开始...获取页面显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MOVolumesBean> page = new Page<MOVolumesBean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("mOClassID", request.getParameter("mOClassID"));
		paramMap.put("deviceMOName", vo.getDeviceMOName());
		page.setParams(paramMap);
		// 查询分页数据
		List<MOVolumesBean> list = moVolumesMapper.queryList(page);
		// 查询数据字典关联数据
		Map<Integer, String> osMap = DictionaryLoader
				.getConstantItems("OperStatus");// 可用状态
		Map<Integer, String> asMap = DictionaryLoader
				.getConstantItems("AdminStatus");// 操作状态

		for (MOVolumesBean mo : list) {
			mo.setOperStatusName(osMap.get(mo.getOperStatus()));
			mo.setAdminStatusName(asMap.get(mo.getAdminStatus()));
			if (mo.getDiskSize() != null && !"".equals(mo.getDiskSize())) {
				mo.setDiskSize(HostComm.getBytesToSize(Long.parseLong(mo
						.getDiskSize())));
			}
		}
		// 查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", list);
		logger.info("结束...获取页面显示数据");
		return result;
	}
	
	
	/**
	 * 获取内存页面显示数据
	 * 
	 * @param vo
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/listMemory")
	@ResponseBody
	public Map<String, Object> listMemory(MOMemoriesBean vo,
			HttpServletRequest request) throws Exception {
		logger.info("开始...获取页面显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MOMemoriesBean> page = new Page<MOMemoriesBean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("mOClassID", request.getParameter("mOClassID"));
		paramMap.put("deviceMOName", vo.getDeviceMOName());
		//增加设备ip查询条件
		paramMap.put("deviceIP", vo.getDeviceIP());
		page.setParams(paramMap);
		// 查询分页数据
		List<MOMemoriesBean> list = moMemoriesMapper.queryList(page);
		// 查询数据字典关联数据
		Map<Integer, String> osMap = DictionaryLoader
				.getConstantItems("OperStatus");// 可用状态
		Map<Integer, String> asMap = DictionaryLoader
				.getConstantItems("AdminStatus");// 操作状态

		for (MOMemoriesBean mo : list) {
			mo.setOperStatusName(osMap.get(mo.getOperStatus()));
			mo.setAdminStatusName(asMap.get(mo.getAdminStatus()));
			if (mo.getMemorySize() != null && !"".equals(mo.getMemorySize())) {
				mo.setMemorySize(HostComm.getBytesToSize(Long.parseLong(mo
						.getMemorySize())));
			}
		}
		// 查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", list);
		logger.info("结束...获取页面显示数据");
		return result;
	}
	/**
	 * 进程页面跳转
	 */
	@RequestMapping("/toProcessList")
	public ModelAndView toProcessList(HttpServletRequest request, ModelMap map)
			throws Exception {
		map.put("mOClassID", request.getParameter("mOClassID"));

		Map<Integer, String> osMap = DictionaryLoader
				.getConstantItems("OperStatus");// 可用状态
		map.put("osMap", osMap);
		map.put("flag", request.getParameter("flag"));// 隐藏字段标志
		map.put("navigationBar",request.getParameter("navigationBar"));
		return new ModelAndView("monitor/device/safe_toProcess_List");
	}
	
	/**
	 * 获取内存页面显示数据
	 * 
	 * @param vo
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/listProcess")
	@ResponseBody
	public Map<String, Object> listProcess(MOMemoriesBean vo,
			HttpServletRequest request) throws Exception {
		logger.info("开始...获取页面显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MoProcessBean> page = new Page<MoProcessBean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("mOClassID", request.getParameter("mOClassID"));
		paramMap.put("processName", vo.getDeviceMOName());
		//增加设备ip查询条件
		paramMap.put("deviceIP", vo.getDeviceIP());
		page.setParams(paramMap);
		// 查询分页数据
		//List<MOMemoriesBean> list = moMemoriesMapper.queryList(page);
		List<MoProcessBean> list = processInfoMapper.getMoProcessInfos(page);
		// 查询数据字典关联数据
		Map<Integer, String> osMap = DictionaryLoader
				.getConstantItems("OperStatus");// 可用状态
		Map<Integer, String> asMap = DictionaryLoader
				.getConstantItems("AdminStatus");// 操作状态

		/*for (MOMemoriesBean mo : list) {
			mo.setOperStatusName(osMap.get(mo.getOperStatus()));
			mo.setAdminStatusName(asMap.get(mo.getAdminStatus()));
			if (mo.getMemorySize() != null && !"".equals(mo.getMemorySize())) {
				mo.setMemorySize(HostComm.getBytesToSize(Long.parseLong(mo
						.getMemorySize())));
			}
		}*/
		// 查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", list);
		logger.info("结束...获取页面显示数据");
		return result;
	}
	/**
	 * 设备页面跳转
	 */
	@RequestMapping("/toDeviceList")
	public ModelAndView toDeviceList(HttpServletRequest request, ModelMap map)
			throws Exception {
		map.put("mOClassID", request.getParameter("mOClassID"));

		Map<Integer, String> osMap = DictionaryLoader
				.getConstantItems("OperStatus");// 可用状态
		Map<Integer, String> imMap = DictionaryLoader
				.getConstantItems("IsManage");// 是否管理
		map.put("osMap", osMap);
		map.put("imMap", imMap);
		map.put("flag", request.getParameter("flag"));// 隐藏字段标志
		map.put("relationPath", request.getParameter("relationPath"));
		map.put("id", request.getParameter("id"));
		map.put("navigationBar", request.getParameter("navigationBar"));
		return new ModelAndView("monitor/device/safeDeviceClassify_list");
	}

	/**
	 * 获取设备页面显示数据
	 * 
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listDevice")
	@ResponseBody
	public Map<String, Object> listDevice(SafeDeviceObj vo,
			HttpServletRequest request) throws Exception {
		logger.info("开始...获取页面显示数据");
		System.out.println("vo.getDeviceip():" + vo.getDeviceip()
				+ " vo.getMoname():" + vo.getMoname() + "*");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<SafeDeviceObj> page = new Page<SafeDeviceObj>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String classID = request.getParameter("classID");
		if (!(classID.equals("-1"))) {
			paramMap.put("classID", "(" + classID + ")");
		}
		paramMap.put("deviceip", vo.getDeviceip());
		paramMap.put("moname", vo.getMoname());
		paramMap.put("domainName", vo.getDomainName());
		paramMap.put("operStatus", vo.getOperstatus());
		paramMap.put("ismanage", vo.getIsmanage());
		paramMap.put("nemanufacturername", vo.getNemanufacturername());
		paramMap.put("MID", KPINameDef.JOBPINGPOLL);
		page.setParams(paramMap);
		System.out.println(paramMap);
		// 查询分页数据 
		List<SafeDeviceObj> moLst = safeDeviceMapper.querySafeDeviceList(page);

		getListInfo(moLst,vo);
		// 查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", moLst);
		logger.info("结束...获取页面显示数据");
		return result;
	}

	/**
	 * 防火墙，光闸，VPN等视图-详情
	 * 
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toShowFirewallDetail")
	@ResponseBody
	public ModelAndView toShowFirewallDetail(ModelMap map,
			HttpServletRequest request) throws Exception {
		
		if (request.getParameter("moID") != null) {
			MOID = Integer.parseInt(request.getParameter("moID"));
		}
		
		MOSafeDevice mo = safeDeviceMapper.getSafeDeviceDetail(MOID);
		
		try {
			if (mo != null) {
				
				if(mo.getDiskSize()!=null&&!"".equals(mo.getDiskSize())){
					mo.setDiskSize(HostComm.getBytesToSize(Long.parseLong(mo.getDiskSize())));
				}else{
					mo.setDiskSize("");
				}
				if(mo.getMemorySize()!=null&&!"".equals(mo.getMemorySize())){
					mo.setMemorySize(HostComm.getBytesToSize(Long.parseLong(mo.getMemorySize())));
				}else{
					mo.setMemorySize("");
				}
				if(mo.getvMemorySize()!=null&&!"".equals(mo.getvMemorySize())){
					mo.setvMemorySize(HostComm.getBytesToSize(Long.parseLong(mo.getvMemorySize())));
				}else{
					mo.setvMemorySize("");
				}
			}
			System.out.println(mo.toString());
			map.put("mo", mo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("monitor/safeDevice/firewall");
	}
	
	/**
	 * 光闸视图-详情
	 * 
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toShowShutterDetail")
	@ResponseBody
	public ModelAndView toShowShutterDetail(ModelMap map,
			HttpServletRequest request) throws Exception {
		
		if (request.getParameter("moID") != null) {
			MOID = Integer.parseInt(request.getParameter("moID"));
		}
		
		MOSafeDevice mo = safeDeviceMapper.getSafeDeviceDetail(MOID);
		
		try {
			if (mo != null) {
				
				if(mo.getDiskSize()!=null&&!"".equals(mo.getDiskSize())){
					mo.setDiskSize(HostComm.getBytesToSize(Long.parseLong(mo.getDiskSize())));
				}else{
					mo.setDiskSize("");
				}
				if(mo.getMemorySize()!=null&&!"".equals(mo.getMemorySize())){
					mo.setMemorySize(HostComm.getBytesToSize(Long.parseLong(mo.getMemorySize())));
				}else{
					mo.setMemorySize("");
				}
				if(mo.getvMemorySize()!=null&&!"".equals(mo.getvMemorySize())){
					mo.setvMemorySize(HostComm.getBytesToSize(Long.parseLong(mo.getvMemorySize())));
				}else{
					mo.setvMemorySize("");
				}
			}
			System.out.println(mo.toString());
			map.put("mo", mo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("monitor/safeDevice/shutter");
	}
	
	/**
	 * VPN视图-详情
	 * 
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toShowVPNDetail")
	@ResponseBody
	public ModelAndView toShowVPNDetail(ModelMap map,
			HttpServletRequest request) throws Exception {
		
		if (request.getParameter("moID") != null) {
			MOID = Integer.parseInt(request.getParameter("moID"));
		}
		
		MOSafeDevice mo = safeDeviceMapper.getSafeDeviceDetail(MOID);
		
		try {
			if (mo != null) {
				
				if(mo.getDiskSize()!=null&&!"".equals(mo.getDiskSize())){
					mo.setDiskSize(HostComm.getBytesToSize(Long.parseLong(mo.getDiskSize())));
				}else{
					mo.setDiskSize("");
				}
				if(mo.getMemorySize()!=null&&!"".equals(mo.getMemorySize())){
					mo.setMemorySize(HostComm.getBytesToSize(Long.parseLong(mo.getMemorySize())));
				}else{
					mo.setMemorySize("");
				}
				if(mo.getvMemorySize()!=null&&!"".equals(mo.getvMemorySize())){
					mo.setvMemorySize(HostComm.getBytesToSize(Long.parseLong(mo.getvMemorySize())));
				}else{
					mo.setvMemorySize("");
				}
			}
			System.out.println(mo.toString());
			map.put("mo", mo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("monitor/safeDevice/Vpn");
	}
	/**
	 * 防火墙，光闸，VPN等视图-丢包率
	 * 
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findChartLoss")
	@ResponseBody
	public ModelAndView findChartLoss(HttpServletRequest request, ModelMap map) throws Exception {
		logger.info("根据设备IP获取图表丢包率");
		moClass = request.getParameter("moClass");
		Map params = new HashMap();
		params.put("MOID", request.getParameter("moID"));
		getmoClassID(moClass);
		params.put("KPIName", KPINameDef.DEVICELOSS);
		params.put("moClassID", moClassID);
		MODevice mo = modMaper.getDeviceLoss(params);
		long value = 0;
		if (mo == null || mo.getPerfvalue() == -1) {
			mo = new MODevice();
			mo.setOne("");
			mo.setTwo("");
			mo.setThree("-2");
		} else {
			int period=0;
			Date collectTime;
			long curr=0;
			// 当前时间
			long currTime=orclService.getCurrentDate().getTime();
			// 采集时间
			 collectTime=mo.getCollecttime();
			if(mo.getDoIntervals()==null || "".equals(mo.getDoIntervals())){
				//采集周期的倍数
				period=mo.getDefDoIntervals()*getMultiple()*60000;
			}else{
				//采集周期的倍数
				period=mo.getDoIntervals()*getMultiple()*1000;
			}
			if (collectTime != null) {
				// 当前时间与采集时间差
				curr=currTime-mo.getCollecttime().getTime();
				//当前时间与采集时间差超过采集周期的倍数（可配置）或没有采集时间（即没有采集）
				if(curr<=period){
					value = mo.getPerfvalue();
					long numValue = value;
					long[] ary = new long[(value + "").length()];
					for (int i = ary.length - 1; i >= 0; i--) {
						ary[i] = value % 10;
						value /= 10;
					}
					if (numValue < 10) {
						mo.setOne(String.valueOf(0));
						mo.setTwo(String.valueOf(0));
						mo.setThree(String.valueOf(ary[0]));
					} else if (numValue < 100 && numValue >= 10) {
						mo.setOne(String.valueOf(0));
						mo.setTwo(String.valueOf(ary[0]));
						mo.setThree(String.valueOf(ary[1]));
					} else {
						mo.setOne(String.valueOf(ary[0]));
						mo.setTwo(String.valueOf(ary[1]));
						mo.setThree(String.valueOf(ary[2]));
					}
				}else{
					// 当前时间与采集时间差超过采集周期的倍数
					mo.setOne("");
					mo.setTwo("");
					mo.setThree("-2");
				}
			}else{
				// 没有采集时间（即没有采集）
				mo.setOne("");
				mo.setTwo("");
				mo.setThree("-2");
			}
		}
		map.put("mo", mo);
		return new ModelAndView("monitor/host/chartLoss");
	}	
	/**
	 * 防火墙，光闸，VPN等视图-延时
	 * 
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findChartResponse")
	@ResponseBody
	public ModelAndView findChartResponse(HttpServletRequest request,ModelMap map){
		logger.info("根据设备IP获取图表延时");
		moClass = request.getParameter("moClass");
		Map params = new HashMap();
		params.put("MOID", request.getParameter("moID"));
		getmoClassID(moClass);
		params.put("KPIName", KPINameDef.DEVICERESPONSE);
		params.put("moClassID", moClassID);
		ModelAndView model = new ModelAndView("monitor/host/chartResponse");
		Double numPer = 0.00;
		try {
			MODevice mo = modMaper.getDeviceLoss(params);
			long value = 0;
			if (mo == null ||  mo.getPerfvalue() == -1) {
				mo = new MODevice();
				mo.setOne("");
				mo.setTwo("");
				mo.setThree("-2");
			} else {
				int period=0;
				Date collectTime;
				long curr=0;
				// 当前时间
				long currTime=orclService.getCurrentDate().getTime();
				// 采集时间
				 collectTime=mo.getCollecttime();
				if(mo.getDoIntervals()==null || "".equals(mo.getDoIntervals())){
					//采集周期的倍数
					period=mo.getDefDoIntervals()*getMultiple()*60000;
				}else{
					//采集周期的倍数
					period=mo.getDoIntervals()*getMultiple()*1000;
				}
				if (collectTime != null) {
					// 当前时间与采集时间差
					curr=currTime-mo.getCollecttime().getTime();
					//当前时间与采集时间差超过采集周期的倍数（可配置）或没有采集时间（即没有采集）
					if(curr<=period){
						value =  mo.getPerfvalue();
						Double valueD = Double.parseDouble(mo.getPerValue());
						long numValue = value;
						long[] ary = new long[(value + "").length()];
						for (int i = ary.length - 1; i >= 0; i--) {
							ary[i] = value % 10;
							value /= 10;
						}
						if(valueD > 0 && valueD < 1){
							String val = mo.getPerValue();
							int index = val.indexOf(".");
							mo.setOne(String.valueOf(0));
							mo.setTwo(val.substring(index+1,index+2));
							if(val.length() >= 4){
								mo.setThree(val.substring(index+2,index+3));
							}else{
								mo.setThree(String.valueOf(0));
							}
							numPer = valueD;
						}else if (numValue >= 1 && numValue < 10) {
							mo.setOne(String.valueOf(0));
							mo.setTwo(String.valueOf(0));
							mo.setThree(String.valueOf(ary[0]));
						} else if (numValue < 100 && numValue >= 10) {
							mo.setOne(String.valueOf(0));
							mo.setTwo(String.valueOf(ary[0]));
							mo.setThree(String.valueOf(ary[1]));
						} else if(numValue == 0){
							mo.setOne(String.valueOf(0));
							mo.setTwo(String.valueOf(0));
							mo.setThree(String.valueOf(0));
						}else {
							mo.setOne(String.valueOf(ary[0]));
							mo.setTwo(String.valueOf(ary[1]));
							mo.setThree(String.valueOf(ary[2]));
						}
					}else{
						// 当前时间与采集时间差超过采集周期的倍数
						mo.setOne("");
						mo.setTwo("");
						mo.setThree("-2");
					}
				}else{
					// 没有采集时间（即没有采集）
					mo.setOne("");
					mo.setTwo("");
					mo.setThree("-2");
				}
			}
			map.put("mo", mo);
			model.addObject("numPer", numPer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	
	/**
	 * 防火墙，光闸，VPN等视图-可用性
	 * 
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findChartAvailability")
	@ResponseBody
	public Map findChartAvailability() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		logger.info("根据设备IP获取图表可用性使用率");
		moClass = request.getParameter("moClass");
		String seltDate = request.getParameter("time");
		String seltDateValue = "";
		if ("24H".equals(seltDate)) {// 最近一天
			seltDateValue = "24小时";
		} else if ("7D".equals(seltDate)) {// 最近一周
			seltDateValue = "最近一周";
		} else if ("Today".equals(seltDate)) {// 今天
			seltDateValue = "今天";
		} else if ("Yes".equals(seltDate)) {// 昨天
			seltDateValue = "昨天";
		}else if ("Week".equals(seltDate)) {// 本周
			seltDateValue = "本周";
		}else if ("Month".equals(seltDate)) {// 本月
			seltDateValue = "本月";
		}else if ("LastMonth".equals(seltDate)) {// 上月
			seltDateValue = "上月";
		}
		String nameValue = seltDateValue + "可用性";
		
		Map params = new HashMap();
		params = queryBetweenTime(request, params);
		params.put("MOID", request.getParameter("moID"));
		getmoClassID(moClass);
		params.put("moClassID", moClassID);
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar d = Calendar.getInstance();
		MODevice mo = modMaper.getChartAvailability(params);
		Map map = new HashMap();
		Map map2 = new HashMap();
		ArrayList<Object> array1 = new ArrayList<Object>();
		Map map1 = new HashMap();
		if (mo != null) {
			map1.put("value", mo.getDeviceavailability());
			map1.put("name", nameValue);
			array1.add(map1);
			map2.put("chartAvailability", array1);
			map.put("data", map2);
			map.put("url", "");
		} else {
			map1.put("value", "");
			map1.put("name", nameValue);
			array1.add(map1);
			map2.put("chartAvailability", array1);
			map.put("data", map2);
			map.put("url", "");
		}
		return map;
	}
	
	/**
	 * 防火墙，光闸，VPN等视图-cpu使用率
	 * 
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findChartCPU")
	@ResponseBody
	public Map findChartCPU() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		//PerfServCPU
		logger.info("根据设备IP获取图表CPU使用率");
		moClass = request.getParameter("moClass");
		Map params = new HashMap();
		params.put("MOID", request.getParameter("moID"));
		//赋伪值造数据
		//params.put("MOID", 10387);
		getParam(moClass);
		logger.debug("param 表名称[tableName]={} ", tableName);
		params.put("KPIName", KPINameDef.PERUSAGE);
		params.put("tableName", tableName);
		Map map = new HashMap();
		Map map1 = new HashMap();
		Map map2 = new HashMap();
		ArrayList<Object> array1 = new ArrayList<Object>();
		try {
			MODevice mo = modMaper.getChartCPU(params);
			getCPUUrl();
			String StrUrl = "/monitor/interfaceChart/toHistoryChartsDetail?proUrl="
					+ cpuUrl
					+ "&perfKind=1&MOID="
					+ request.getParameter("moID");
			int period=0;
			Date collectTime;
			long curr=0;
			// 当前时间
			long currTime=orclService.getCurrentDate().getTime();
			if (mo != null) {
				// 采集时间
				 collectTime=mo.getCollecttime();
				 logger.debug("主机设备{}cpu采集时间为{}",mo.getDeviceip(),collectTime);
				if(mo.getDoIntervals()==null || "".equals(mo.getDoIntervals())){
					//采集周期的倍数
					period=mo.getDefDoIntervals()*getMultiple()*60000;
				}else{
					//采集周期的倍数
					period=mo.getDoIntervals()*getMultiple()*1000;
				}
				logger.debug("主机设备{}cpu采集周期的倍数{}",mo.getDeviceip(),period);
				if (collectTime != null) {
					// 当前时间与采集时间差
					curr=currTime-mo.getCollecttime().getTime();
					logger.debug("主机设备{}cpu当前时间与采集时间差{}",mo.getDeviceip(),curr);
					//当前时间与采集时间差超过采集周期的倍数（可配置）或没有采集时间（即没有采集）
					System.out.println("展示时间:"+(curr-period));
					if(curr<=period){
						//map1.put("value", format.format(Double.parseDouble(mo.getPerValue())));
						if(mo.getPerValue() != null){ //存在值为null的情况，此处抛出空指针异常
							map1.put("value",  format.format(Double.parseDouble(mo.getPerValue())));
							// 倘若单核使用率没有时，cpu的平均值为-1后台采集时入库的数据
							if(mo.getPerValue().equals("-1")){
								map1.put("value", "");
							}
						}
						else{
							map1.put("value",  "");
						}
						/*if(mo.getDevicestatus().equals("2")) {//down
							map1.put("value", "");
						}*/
						map1.put("name", "当前CPU使用率");
						array1.add(map1);
						map2.put("chartCPU", array1);
						map.put("data", map2);
						map.put("url", StrUrl);
					}else{
						//当前时间与采集时间差超过采集周期的倍数（可配置）
						//map1.put("value", "");
						//伪值
						map1.put("value", mo.getPerValue());
						map1.put("name", "当前CPU使用率");
						array1.add(map1);
						map2.put("chartCPU", array1);
						map.put("data", map2);
						map.put("url", StrUrl);
					}
				}else{
					// 没有采集时间（即没有采集）
					//map1.put("value", "");
					//伪值
					map1.put("value", mo.getPerValue());
					map1.put("name", "当前CPU使用率");
					array1.add(map1);
					map2.put("chartCPU", array1);
					map.put("data", map2);
					map.put("url", StrUrl);
				}
			} else {
				//map1.put("value", "");
				map1.put("value", mo.getPerValue());
				map1.put("name", "当前CPU使用率");
				array1.add(map1);
				map2.put("chartCPU", array1);
				map.put("data", map2);
				map.put("url", StrUrl);
			}
		} catch (Exception e) {
			map1.put("value", "");
			map1.put("name", "当前CPU使用率");
			array1.add(map1);
			map2.put("chartCPU", array1);
			map.put("data", map2);
			map.put("url", "");
		}
		return map;
	}	
	
	/**
	 * 防火墙，光闸，VPN等视图-内存使用率
	 * 
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findChartMemory")
	@ResponseBody
	public Map findChartMemory(){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		
		logger.info("根据设备IP获取图表内存使用率");
		moClass = request.getParameter("moClass");
		Map params = new HashMap();
		params.put("MOID", request.getParameter("moID"));
		getParam(moClass);
		logger.debug("param 表名称[tableName]={} ", tableName);
		if("router".equals(moClass)||"switcher".equals(moClass) ||"switcherl2".equals(moClass)||"switcherl3".equals(moClass)){
			params.put("KPIName", KPINameDef.NETMEMUSAGE);
		}else {
		params.put("KPIName", KPINameDef.PHYMEMUSAGE);
		}
		params.put("tableName", tableName);
		Map map = new HashMap();
		Map map1 = new HashMap();
		ArrayList<Object> array1 = new ArrayList<Object>();
		getMemUrl();
		String StrUrl = "/monitor/interfaceChart/toHistoryChartsDetail?proUrl="
				+ memUrl + "&perfKind=1&MOID=" + request.getParameter("moID");
		Map map2 = new HashMap();
		try {
			MODevice mo = modMaper.getChartMemory(params);
			int period=0;
			Date collectTime;
			long curr=0;
			// 当前时间
			long currTime=orclService.getCurrentDate().getTime();
			if (mo != null) {
				// 采集时间
				 collectTime=mo.getCollecttime();
				 logger.debug("设备{}内存采集时间为{}",mo.getDeviceip(),collectTime);
				if(mo.getDoIntervals()==null || "".equals(mo.getDoIntervals())){
					//采集周期的倍数
					period=mo.getDefDoIntervals()*getMultiple()*60000;
				}else{
					//采集周期的倍数
					period=mo.getDoIntervals()*getMultiple()*1000;
				}
				logger.debug("设备{}内存采集周期倍数{}",mo.getDeviceip(),period);
				if (collectTime != null) {
					// 当前时间与采集时间差
					curr=currTime-mo.getCollecttime().getTime();
					logger.debug("设备{}内存当前时间与采集时间差{}",mo.getDeviceip(),curr);
					//当前时间与采集时间差超过采集周期的倍数（可配置）或没有采集时间（即没有采集）
					if(curr<=period){
						if(mo.getPerValue() != null){ //存在值为null的情况，此处抛出空指针异常
							map1.put("value",  format.format(Double.parseDouble(mo.getPerValue())));
						}
						else{
							map1.put("value",  "");
						}
						if(mo.getDevicestatus()==null){
							if("vpn".equals(moClass)){
								map1.put("value", "up");
							}
						}
						
						map1.put("name", "当前内存使用率");
						array1.add(map1);
						map2.put("chartMemory", array1);
						map.put("data", map2);
						map.put("url", StrUrl);
					}else{
						map1.put("value", "");
						map1.put("name", "当前内存使用率");
						array1.add(map1);
						map2.put("chartMemory", array1);
						map.put("data", map2);
						map.put("url", StrUrl);
					}
				}else{
					map1.put("value", "");
					map1.put("name", "当前内存使用率");
					array1.add(map1);
					map2.put("chartMemory", array1);
					map.put("data", map2);
					map.put("url", StrUrl);
				}
				
			} else {
				map1.put("value", "");
				map1.put("name", "当前内存使用率");
				array1.add(map1);
				map2.put("chartMemory", array1);
				map.put("data", map2);
				map.put("url", StrUrl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}
	/**
	 * 获取Bar 源连接数详情
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findBarConnsrc")
	@ResponseBody
	public Map findBarConnsrc() {
		logger.info("开始...源连接数详情页面数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		
		return getConnMap(request,"1","fwConnSrc");
	}
	
	/**
	 * 获取Bar 目的连接数详情
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findBarConnDes")
	@ResponseBody
	public Map findBarConnDes() {
		logger.info("开始...目的连接数详情页面数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		
		return getConnMap(request,"2","fwConnDes");
	}
	

	/**
	 * 安全设备跳转至详情列表页
	 */
	@RequestMapping("/toHostDetailListInfo")
	public ModelAndView toHostDetailListInfo(HttpServletRequest request) {
		MOID = Integer.parseInt(request.getParameter("moID"));
		moClass = request.getParameter("moClass");
		//moClassID = Integer.parseInt(request.getParameter("mOClassID"));
		String liInfo = request.getParameter("liInfo");
		System.out.println("toHostDetailListInfo:"+liInfo);
		request.setAttribute("liInfo", liInfo);
		if(moClass.equals("Shutter")){
			return new ModelAndView("monitor/safeDevice/shutterDeviceDetailList");
		}else if(moClass.equals("VPN")){
			return new ModelAndView("monitor/safeDevice/vpnDeviceDetailList");
		}
		return new ModelAndView("monitor/safeDevice/safeDeviceDetailList");
	}
	
	
	/**************************************************** TAB页公共 ******************/
	/**
	 * 详情信息列表 for CPU公共
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findCPUDetailInfo")
	@ResponseBody
	public Map<String, Object> findCPUDetailInfo() throws Exception {
		logger.info("开始...加载公共详情信息列表");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<Integer, MODevice> cpuMap = new HashMap<Integer, MODevice>();
		Map params = new HashMap();
		params.put("MOID", MOID);
		params.put("tabName", "MOCPUs");
		//params.put("perfTabName", "PerfServCPU");
		String moClass = request.getParameter("moClass");
		String snapTable = "";
		if ("Firewall".equals(moClass)||"Shutter".equals(moClass)||"VPN".equals(moClass)) {//防火墙与主机共用cpu表
			snapTable = "PerfSnapshotHost";
		} 
		params.put("snapTable", snapTable);
		// List<MODevice> moLsCount = modMaper.getKPICount(params);
		System.out.println("findCPUDetailInfo:"+params);
		List<MODevice> moLsinfo = new ArrayList<MODevice>();
		List<MODevice> moLsinfo1 = modMaper.getKPICPUValue(params);
		MODevice mo = null;
		if (moLsinfo1.size() > 0) {
			for (MODevice cpu : moLsinfo1) {
				mo = new MODevice();
				if (cpuMap.get(cpu.getMoid()) != null) {
					getCPUObj(cpuMap.get(cpu.getMoid()), cpu, moClass);
				} else {
					cpuMap.put(cpu.getMoid(), getCPUObj(mo, cpu, moClass));
				}
			}
			for (Map.Entry<Integer, MODevice> entry : cpuMap.entrySet()) {
				moLsinfo.add(entry.getValue());
			}

		}
		result.put("rows", moLsinfo);
		logger.info("结束...加载公共详情信息列表 " + moLsinfo);
		return result;
	}
	/**
	 * 详情信息列表 for 内存公共
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findMemoryDetailInfo")
	@ResponseBody
	public Map<String, Object> findMemoryDetailInfo() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		logger.info("开始...加载公共详情信息列表");
		Map<String, Object> result = new HashMap<String, Object>();
		Map params = new HashMap();
		params.put("MOID", MOID);
		params.put("tabName", "MOMemories");
		String moClass = request.getParameter("moClass");

		String snapTable = "";
		if ("Firewall".equals(moClass)||"Shutter".equals(moClass)||"VPN".equals(moClass)) {//防火墙与主机共用cpu表
			snapTable = "PerfSnapshotHost";
		} 
		params.put("snapTable", snapTable);

		List<MODevice> memoryList = modMaper.getKPIMemoryValue(params);

		// return data
		Map<Integer, MODevice> memoryMap = new HashMap<Integer, MODevice>();
		MODevice memory = null;
		if (memoryList.size() > 0) {
			for (MODevice moMemory : memoryList) {
				memory = new MODevice();
				if (memoryMap.get(moMemory.getMoid()) != null) {
					getMemoryObj(memoryMap.get(moMemory.getMoid()), moMemory, moClass);
				} else {
					memoryMap.put(moMemory.getMoid(), getMemoryObj(memory, moMemory, moClass));
				}
			}
		}

		memoryList.clear();
		for (Map.Entry<Integer, MODevice> entry : memoryMap.entrySet()) {
			memoryList.add(entry.getValue());
		}

		if ("Firewall".equals(moClass)||"Shutter".equals(moClass)||"VPN".equals(moClass)) {
			memory = getMemValue(memoryList);
			memoryList.clear();
			memoryList.add(memory);
		}

		result.put("rows", memoryList);

		logger.info("结束...加载公共详情信息列表 " + memoryList.size());
		return result;
	}

	/**
	 * 详情信息列表 for 硬盘公共
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findDiskDetailInfo")
	@ResponseBody
	public Map<String, Object> findDiskDetailInfo() throws Exception {
		logger.info("开始...加载公共详情信息列表");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<Integer, MODevice> diskMap = new HashMap<Integer, MODevice>();
		Map params = new HashMap();
		params.put("MOID", MOID);
		params.put("tabName", "MOVolumes");
		String moClass = request.getParameter("moClass");
		String snapTable = "";
		String volType = "";
//		if ("vhost".equals(moClass)) {
//			snapTable = "PerfSnapshotVHost";
//			volType = "Volume";
//		} else if ("vm".equals(moClass)) {
//			snapTable = "PerfSnapshotVM";
//			volType = "Volume";
//		} else if ("host".equals(moClass)) {
//			snapTable = "PerfSnapshotHost";
//			volType = "Volume";
//		}
		
		if ("Firewall".equals(moClass)||"Shutter".equals(moClass)||"VPN".equals(moClass)) {//防火墙与主机共用cpu表
			snapTable = "PerfSnapshotHost";
			volType = "Volume";
		} 
		params.put("volType", volType);
		params.put("snapTable", snapTable);
		List<MODevice> moLsinfo = new ArrayList<MODevice>();
		List<MODevice> moLsinfo1 = modMaper.getKPIDiskValue(params);
		MODevice mo = null;
		if (moLsinfo1.size() > 0) {
			for (MODevice disk : moLsinfo1) {
				mo = new MODevice();
				if (diskMap.get(disk.getMoid()) != null) {
					getDiskObj(diskMap.get(disk.getMoid()), disk, moClass);
				} else {
					diskMap.put(disk.getMoid(), getDiskObj(mo, disk, moClass));
				}
			}
			for (Map.Entry<Integer, MODevice> entry : diskMap.entrySet()) {
				moLsinfo.add(entry.getValue());
			}
		}
		result.put("rows", moLsinfo);
		logger.info("结束...加载公共详情信息列表 " + moLsinfo);
		return result;
	}
	
	
	/**
	 * 详情信息列表 for 接口
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findFlowsDetailInfo")
	@ResponseBody
	public Map<String, Object> findFlowsDetailInfo() throws Exception {
		logger.info("开始...加载公共详情信息列表");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		Map<String, Object> result = new HashMap<String, Object>();
		Map params = new HashMap();
		params.put("MOID", MOID);
		getmoClassID(moClass);
		params.put("moClassID", moClassID);
		params.put("tabName", "MONetworkIf");
		String moClass = request.getParameter("moClass");
		getIfTableName(moClass);
		params.put("snapTable", "PerfSnapshotNetDevice");

		List<MODevice> moLsCount = modMaper.getKPIFlowsValue2(params);
		DecimalFormat df = new DecimalFormat("#.##");
		System.out.println("接口:"+moLsCount.size()+","+moClass);
		System.out.println(params);
		Map<Integer, MODevice> resultMap = new HashMap<Integer, MODevice>();
		for (MODevice moDevice : moLsCount) {
			int ifMOID = moDevice.getMoid();
			if (resultMap.get(ifMOID) != null) {
				MODevice keyMoDevice = resultMap.get(ifMOID);
//				if ("vhost".equals(moClass)) {
//					if (KPINameDef.INFLOWS.equals(moDevice.getKpiname())) {
//						String inflow = HostComm.getBytesToFlows(moDevice.getViewValue());
//						keyMoDevice.setInflows(inflow);
//					} else if (KPINameDef.OUTFLOWS.equals(moDevice.getKpiname())) {
//						keyMoDevice.setOutflows(HostComm.getBytesToFlows(moDevice.getViewValue()));
//					} else if (KPINameDef.IFUSAGE.equals(moDevice.getKpiname())) {
//						keyMoDevice.setIfUsage(df.format(moDevice.getViewValue()) + "%");
//					} else if (KPINameDef.INUSAGE.equals(moDevice.getKpiname())) {
//						keyMoDevice.setInusage(df.format(moDevice.getViewValue()) + "%");
//					} else if (KPINameDef.OUTUSAGE.equals(moDevice.getKpiname())) {
//						keyMoDevice.setOutusage(df.format(moDevice.getViewValue()) + "%");
//					} else if (KPINameDef.IFSPEED.equals(moDevice.getKpiname())) {
//						keyMoDevice.setIfspeed(HostComm.getBytesToFlows(moDevice.getViewValue()));
//					}
//				} else if ("vm".equals(moClass)) {
//					if (KPINameDef.INFLOWS.equals(moDevice.getKpiname())) {
//						String inflow = HostComm.getBytesToFlows(moDevice.getViewValue());
//						keyMoDevice.setInflows(inflow);
//					} else if (KPINameDef.OUTFLOWS.equals(moDevice.getKpiname())) {
//						keyMoDevice.setOutflows(HostComm.getBytesToFlows(moDevice.getViewValue()));
//					}
//				} else 
				if ("Firewall".equals(moClass)||"Shutter".equals(moClass)||"VPN".equals(moClass)) {

					if (KPINameDef.INFLOWS.equals(moDevice.getKpiname())) {
						keyMoDevice.setInflows(HostComm.getBytesToFlows(moDevice.getViewValue()));
					} else if (KPINameDef.OUTFLOWS.equals(moDevice.getKpiname())) {
						keyMoDevice.setOutflows(HostComm.getBytesToFlows(moDevice.getViewValue()));
					} else if (KPINameDef.INDISCARDS.equals(moDevice.getKpiname())) {
						keyMoDevice.setIndiscards(moDevice.getViewValue());
					} else if (KPINameDef.OUTDISCARDS.equals(moDevice.getKpiname())) {
						keyMoDevice.setOutdiscards(moDevice.getViewValue());
					} else if (KPINameDef.INERRORS.equals(moDevice.getKpiname())) {
						keyMoDevice.setInerrors(moDevice.getViewValue());
					} else if (KPINameDef.OUTERRORS.equals(moDevice.getKpiname())) {
						keyMoDevice.setOuterrors(moDevice.getViewValue());
					} else if (KPINameDef.IFOPERSTATUS.equals(moDevice.getKpiname())) {
						if (KPINameDef.upd == moDevice.getViewValue()) {
							keyMoDevice.setOperstatus("UP");
						} else {
							keyMoDevice.setOperstatus("DOWN");
						}
					}

				}
			} else {
//				if ("vhost".equals(moClass)) {
//					if (KPINameDef.INFLOWS.equals(moDevice.getKpiname())) {
//						String inflow = HostComm.getBytesToFlows(moDevice.getViewValue());
//						moDevice.setInflows(inflow);
//					} else if (KPINameDef.OUTFLOWS.equals(moDevice.getKpiname())) {
//						moDevice.setOutflows(HostComm.getBytesToFlows(moDevice.getViewValue()));
//					} else if (KPINameDef.IFUSAGE.equals(moDevice.getKpiname())) {
//						moDevice.setIfUsage(df.format(moDevice.getViewValue()) + "%");
//					} else if (KPINameDef.INUSAGE.equals(moDevice.getKpiname())) {
//						moDevice.setInusage(df.format(moDevice.getViewValue()) + "%");
//					} else if (KPINameDef.OUTUSAGE.equals(moDevice.getKpiname())) {
//						moDevice.setOutusage(df.format(moDevice.getViewValue()) + "%");
//					} else if (KPINameDef.IFSPEED.equals(moDevice.getKpiname())) {
//						moDevice.setIfspeed(HostComm.getBytesToFlows(moDevice.getViewValue()));
//					}
//				} else if ("vm".equals(moClass)) {
//					if (KPINameDef.INFLOWS.equals(moDevice.getKpiname())) {
//						String inflow = HostComm.getBytesToFlows(moDevice.getViewValue());
//						moDevice.setInflows(inflow);
//					} else if (KPINameDef.OUTFLOWS.equals(moDevice.getKpiname())) {
//						moDevice.setOutflows(HostComm.getBytesToFlows(moDevice.getViewValue()));
//					}
//				} else 
				if ("Firewall".equals(moClass)||"Shutter".equals(moClass)||"VPN".equals(moClass)) {

					if (KPINameDef.INFLOWS.equals(moDevice.getKpiname())) {
						moDevice.setInflows(HostComm.getBytesToFlows(moDevice.getViewValue()));
					} else if (KPINameDef.OUTFLOWS.equals(moDevice.getKpiname())) {
						moDevice.setOutflows(HostComm.getBytesToFlows(moDevice.getViewValue()));
					} else if (KPINameDef.INDISCARDS.equals(moDevice.getKpiname())) {
						moDevice.setIndiscards(moDevice.getViewValue());
					} else if (KPINameDef.OUTDISCARDS.equals(moDevice.getKpiname())) {
						moDevice.setOutdiscards(moDevice.getViewValue());
					} else if (KPINameDef.INERRORS.equals(moDevice.getKpiname())) {
						moDevice.setInerrors(moDevice.getViewValue());
					} else if (KPINameDef.OUTERRORS.equals(moDevice.getKpiname())) {
						moDevice.setOuterrors(moDevice.getViewValue());
					} else if (KPINameDef.IFOPERSTATUS.equals(moDevice.getKpiname())) {
						if (KPINameDef.upd == moDevice.getViewValue()) {
							moDevice.setOperstatus("UP");
						} else {
							moDevice.setOperstatus("DOWN");
						}
					}

				}
				resultMap.put(ifMOID, moDevice);
			}
		}
System.out.println("接口数量:"+resultMap.size());
		// 遍历map，将接口列表数据放入数组中返回
		List<MODevice> resultList = new ArrayList<MODevice>();
		for (Iterator it = resultMap.keySet().iterator(); it.hasNext();) {
			resultList.add(resultMap.get(it.next()));
		}
		result.put("rows", resultList);
		logger.info("结束...加载公共详情信息列表 " + resultList);
		return result;
	}
	
	
//	/**
//	 * 未使用，使用与主机一样的方法。
//	 */
//	@RequestMapping("/toHostDetailChartLine")
//	public ModelAndView toHostDetailChartLine(HttpServletRequest request, ModelMap map) {
//		String MOID=request.getParameter("moID");
//		map.put("MOID", MOID);
//		return new ModelAndView("monitor/safeDevice/mainDetailChart");
//	}
	
	
//	/**
//	 * 跳转至告警列表页,未使用，使用与主机一样的方法
//	 * 
//	 * @return
//	 */
//	@RequestMapping("/toHostAlarmDetailInfo")
//	public ModelAndView toHostAlarmDetailInfo(HttpServletRequest request) {
//		MOID = Integer.parseInt(request.getParameter("moID"));
//		String liInfo = request.getParameter("liInfo");
//		request.setAttribute("liInfo", liInfo);
//		return new ModelAndView("monitor/host/hostAlarmDetailInfo");
//	}
	
	/**
	 * 根据设备类型 查询接口表名
	 * 
	 * @param moClass
	 * @return
	 */
	public String getIfTableName(String moClass) {
		if ("Firewall".equals(moClass) || "null".equals(moClass) || moClass == null||"Shutter".equals(moClass)||"VPN".equals(moClass) ) {
			ifTableName = "PerfSnapshotHost";
		} 
//		else if ("vm".equals(moClass)) {
//			ifTableName = "PerfSnapshotVM";
//		} else if ("vhost".equals(moClass)) {
//			ifTableName = "PerfSnapshotVHost";
//		}
		return ifTableName;
	}
	
	public MODevice getDiskObj(MODevice mo, MODevice disk, String snapTable) {
		String name;
		long value;
		name = disk.getKpiname();
		value = disk.getPerfvalue();
		mo.setRawdescr(disk.getRawdescr());
		double valueD = Double.parseDouble(disk.getPerValue());
		String valueStr = valueD == 0?"0":format.format(valueD);
//		if ("vhost".equals(snapTable)) {
//			if (name.equals(KPINameDef.DISKRATE)) {
//				mo.setDiskrate(HostComm.getBytesToSpeed(value));
//			} else if (name.equals(KPINameDef.DISKRSPEED)) {
//				mo.setDiskrspeed(HostComm.getBytesToSpeed(value));
//			} else if (name.equals(KPINameDef.DISKWSPEED)) {
//				mo.setDiskwspeed(HostComm.getBytesToSpeed(value));
//			} else if (name.equals(KPINameDef.DISKRREQUEST)) {
//				mo.setDiskrrequest(value);
//			} else if (name.equals(KPINameDef.DISKWREQUEST)) {
//				mo.setDiskwrequest(value);
//			}
//		} else if ("vm".equals(snapTable)) {
//			if (name.equals(KPINameDef.DISKRSPEED)) {
//				mo.setDiskrspeed(HostComm.getBytesToSpeed(value));
//			} else if (name.equals(KPINameDef.DISKWSPEED)) {
//				mo.setDiskwspeed(HostComm.getBytesToSpeed(value));
//			} else if (name.equals(KPINameDef.DISKRREQUEST)) {
//				mo.setDiskrrequest(value);
//			} else if (name.equals(KPINameDef.DISKWREQUEST)) {
//				mo.setDiskwrequest(value);
//			} else if (name.equals(KPINameDef.DISKSIZE)) {
//				mo.setDisksize(HostComm.getBytesToSize(value));
//			} else if (name.equals(KPINameDef.DISKUSAGE)) {
//				mo.setDiskusage(valueStr + "%");
//			} else if (name.equals(KPINameDef.DISKFREE)) {
//				mo.setDiskfree(HostComm.getBytesToSize(value));
//			} else if (name.equals(KPINameDef.DISKUSED)) {
//				mo.setDiskused(HostComm.getBytesToSize(value));
//			}
//		} else 
		if ("Firewall".equals(snapTable)||"Shutter".equals(snapTable)) {

			if (name.equals(KPINameDef.DISKSIZE)) {
				mo.setDisksize(HostComm.getBytesToSize(value*1024));
			} else if (name.equals(KPINameDef.DISKFREE)) {
				mo.setDiskfree(HostComm.getBytesToSize(value*1024));
			} else if (name.equals(KPINameDef.DISKUSAGE)) {
				mo.setDiskusage(valueStr + "%");
			}
		}
		return mo;
	}
	
	/**
	 * 内存
	 * 
	 * @param list
	 * @return
	 */
	public MODevice getMemoryObj(MODevice mo, MODevice old_obj, String snapTable) {
		String name;
		long value;
		name = old_obj.getKpiname();
		value =  old_obj.getPerfvalue();
		double valueD = Double.parseDouble(old_obj.getPerValue());
		String valueStr = valueD == 0?"0":format.format(valueD);
		if ("Firewall".equals(snapTable)||"Shutter".equals(snapTable)) {
			if (name.equals(KPINameDef.PHYMEMSIZE)) {
				mo.setPhymemsize(HostComm.getBytesToSize(value));
			} else if (name.equals(KPINameDef.PHYMEMFREE)) {
				mo.setPhymemfree(HostComm.getBytesToSize(value));
			} else if (name.equals(KPINameDef.PHYMEMUSAGE)) {
				mo.setPhymemusage(valueStr + "%");
			} else if (name.equals(KPINameDef.VIRMEMFREE)) {
				mo.setVirmemfree(HostComm.getBytesToSize(value));
			} else if (name.equals(KPINameDef.VIRMEMSIZE)) {
				mo.setVirmemsize(HostComm.getBytesToSize(value));
			} else if (name.equals(KPINameDef.VIRMEMUSAGE)) {
				mo.setVirmemusage(valueStr + "%");
			}
		}else if("VPN".equals(snapTable)){
			if (name.equals(KPINameDef.PHYMEMSIZE)) {
				mo.setPhymemsize(HostComm.getBytesToSize(value));
			} else if (name.equals(KPINameDef.PHYMEMFREE)) {
				mo.setPhymemfree(HostComm.getBytesToSize(value));
			} else if (name.equals(KPINameDef.PHYMEMUSAGE)) {
				mo.setPhymemusage(valueStr + "%");
			} else if (name.equals(KPINameDef.VIRMEMFREE)) {
				mo.setVirmemfree(HostComm.getBytesToSize(value));
			} else if (name.equals(KPINameDef.VIRMEMSIZE)) {
				mo.setVirmemsize(HostComm.getBytesToSize(value));
			} else if (name.equals(KPINameDef.VIRMEMUSAGE)) {
				mo.setVirmemusage(valueStr + "%");
			}
		}
		return mo;
	}
	
	public MODevice getMemValue(List<MODevice> list) {
		if (list.size() == 1) {
			return list.get(0);
		} else if (list.size() == 2 && list.get(0).getVirmemsize() == null) {
			MODevice mo = list.get(0);
			mo.setVirmemsize(list.get(1).getVirmemsize());
			mo.setVirmemfree(list.get(1).getVirmemfree());
			mo.setVirmemusage(list.get(1).getVirmemusage());
			return mo;
		} else if (list.size() == 2 && list.get(0).getVirmemsize() != null) {
			MODevice mo = list.get(0);
			mo.setPhymemsize(list.get(1).getPhymemsize());
			mo.setPhymemfree(list.get(1).getPhymemfree());
			mo.setPhymemusage(list.get(1).getPhymemusage());
			return mo;
		}
		return null;
	}
	
	public MODevice getCPUObj(MODevice mo, MODevice cpu, String snapTable) {
		String name;
		long value;
		name = cpu.getKpiname();
		value =  cpu.getPerfvalue();
		double valueD = Double.parseDouble(cpu.getPerValue());
		String valueStr = valueD == 0?"0":format.format(valueD);
		mo.setInstance(cpu.getInstance());
		if ("vhost".equals(snapTable)) {
			if (name.equals(KPINameDef.PERUSAGE)) {
				mo.setPerusage(valueStr + "%");
			} else if (name.equals(KPINameDef.CPUSED)) {
				mo.setCpused(HostComm.getMsToTime(value));
			} else if (name.equals(KPINameDef.CPUIDLE)) {
				mo.setCpuidle(HostComm.getMsToTime(value));
			}
		} else if ("vm".equals(snapTable)) {
			if (name.equals(KPINameDef.PERUTIL)) {
				mo.setPerUtil(HostComm.getBytesToHz(value));
			} else if (name.equals(KPINameDef.CPUSED)) {
				mo.setCpused(HostComm.getMsToTime(value));
			} else if (name.equals(KPINameDef.CPUREADY)) {
				mo.setCPUReady(HostComm.getMsToTime(value));
			} else if (name.equals(KPINameDef.CPUWAIT)) {
				mo.setCPUWait(HostComm.getMsToTime(value));
			}
		} else if ("Firewall".equals(snapTable)||"Shutter".equals(snapTable)||"VPN".equals(snapTable)||"host".equals(snapTable)) {
			if (name.equals(KPINameDef.PERUSAGE)) {
				mo.setPerusage(valueStr + "%");
			}
		}else{
			if (name.equals(KPINameDef.PERUSAGE)) {
				mo.setPerusage(valueStr + "%");
			}
		}
		return mo;
	}
	/**
	 * type为１是源连接数，２是目的连接数
	 * */
	public Map getConnMap(HttpServletRequest request,String type,String lable){
		Map params = new HashMap();
		int mid = request.getParameter("moID")==null||request.getParameter("moID").length()<1?0:Integer.parseInt(request.getParameter("moID"));
		params.put("MOID", mid==0?MOID:mid);
		params.put("Type", type);
		Map map = new HashMap();
		Map map1 = new HashMap();
		ArrayList<Object> array = new ArrayList<Object>();//x坐标名
		ArrayList<Object> array1 = new ArrayList<Object>();//值
		try {
			List<MOSafeDevice> moLsinfo =safeDeviceMapper.getSafeDeviceConnNum(params);
			if (moLsinfo.size() > 0) {
				for (int i = 0; i < moLsinfo.size(); i++) {
					MOSafeDevice mo = moLsinfo.get(i);
					array.add(mo.getConnTopIP());
					array1.add(mo.getConnTopNum());
				}
				map.put("category", array);
				map1.put(lable, array1);
				map.put("url", "");
				map.put("data", map1);
			}else {
				array.add("");
				map.put("category", array);
				map1.put(lable, array1);
				map.put("url", "");
				map.put("data", map1);
			}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * 曲线内存 链接
	 * @return
	 */
	public String getMemUrl() {
		if ("Firewall".equals(moClass) || "null".equals(moClass) || moClass == null||"Shutter".equals(moClass)||"VPN".equals(moClass)) {//防火墙cpu和内存数据结构与主机一致，存放在一起
			memUrl = "/monitor/historyManage/initMemoryChartData";
		} 
		return memUrl;
	}
	/**
	 * 曲线CPU 链接
	 * @return
	 */
	public String getCPUUrl() {
		if ("Firewall".equals(moClass) || "null".equals(moClass) || moClass == null||"Shutter".equals(moClass)||"VPN".equals(moClass) ) {//防火墙cpu和内存数据结构与主机一致，存放在一起
			cpuUrl = "/monitor/historyManage/initCpuChartData";
		} 
		return cpuUrl;
	}
	/**
	 * 根据设备类型 查询 表名
	 * @param moClass
	 * @return
	 */
	public String getParam(String moClass) {
		System.out.println("moClass:"+moClass);
		if ("Firewall".equals(moClass) || "null".equals(moClass)|| moClass == null||"Shutter".equals(moClass)||"VPN".equals(moClass)) {//防火墙cpu和内存数据结构与主机一致，存放在一起
			tableName = "PerfSnapshotHost";
		}else{//目前安全设备只有防火墙，光闸，VPN等
			tableName = "PerfSnapshotHost";
		} 
		return tableName;
	}
	/**
	 * 根据类型获取moClassID
	 * @param moClass
	 */
	public Integer getmoClassID(String moClass) {
		if("Firewall".equals(moClass)){//目前只有防火墙，后面加其他安全设备时此处要加if
			moClassID = 62;
		}else if("VPN".equals(moClass)){
			moClassID = 118;
		}else if("Shutter".equals(moClass)){
			moClassID = 117;
		}
		return moClassID;
	}
	/**
	 * 处理可用/持续时间
	 * @param parLst
	 * @param bean
	 */
	private List<SafeDeviceObj> getListInfo(List<SafeDeviceObj> parLst, SafeDeviceObj bean) {
		int period=1;
		long currTime = orclService.getCurrentDate().getTime();
		long curr=0;
		Date updateAlarmTime;
		Date collectTime;
		Map<Integer, String> imMap = DictionaryLoader
				.getConstantItems("IsManage");// 是否管理
		if (parLst != null) {
			for (int i = 0; i < parLst.size(); i++) {
				bean = parLst.get(i);
				updateAlarmTime = bean.getUpdateAlarmTime();
				collectTime = bean.getCollectTime();
				if (bean.getDoIntervals() == null
						|| "".equals(bean.getDoIntervals())) {
					period = bean.getDefDoIntervals() * getMultiple() * 60000;
				} else {
					period = bean.getDoIntervals() * getMultiple() *60 * 1000;
				}
				if (collectTime != null) {
					curr=currTime-bean.getCollectTime().getTime();
					logger.debug("当前时间与采集时间的差==={}",curr);
					logger.debug("采集周期的倍数==={}",period);
					if(curr<=period){	
						
							if (1 == bean.getOperstatus()) {
								bean.setOperaTip("UP");
								bean.setOperstatusdetail("up.png");
							} else if (2 == bean.getOperstatus()) {
								bean.setOperaTip("DOWN");
								bean.setOperstatusdetail("down.png");
							}else{
								bean.setOperaTip("未知");
								bean.setOperstatusdetail("unknown.png");
							}
							if (updateAlarmTime != null) {
								String durationTime = HostComm.getDurationToTime(currTime-bean.getCreatetime().getTime());
								bean.setDurationTime(durationTime);
							}else{
								bean.setDurationTime("");
							}
							logger.debug("设备状态_状态对应图：{},IP:{}",bean.getOperstatus()+"_"+bean.getOperstatusdetail(),bean.getDeviceip());
					}else{
						bean.setOperaTip("未知");
						bean.setOperstatusdetail("unknown.png");
						bean.setOperstatus(3);//被认定为是未知后，设置Operstatus状态为3
						String durationTime = HostComm.getDurationToTime(currTime-bean.getCreatetime().getTime());
						bean.setDurationTime(durationTime);
						logger.debug("未知设备状态_状态对应图：{},IP:{}",bean.getOperstatus()+"_"+bean.getOperstatusdetail(),bean.getDeviceip());

					}
				}else{
					bean.setOperaTip("未知");
					bean.setOperstatusdetail("unknown.png");
					bean.setOperstatus(3);//被认定为是未知后，设置Operstatus状态为3
					bean.setDurationTime("");
				}
				bean.setId(bean.getMoid());
				bean.setIsmanageinfo(imMap.get(bean.getIsmanage()));
				long mills = currTime - bean.getCreatetime().getTime();
				String days = mills<=0?"0":getDays(mills);
				bean.setCreatetimeLong(days);
			}
		}
		return parLst;
	}
	/**
	 * 
	 * @param mills
	 * @return
	 */
	private String getDays(long mills){
		String re = "";
		
		long day = mills/(1000*60*60*24);
		
		long hour = (mills-(day*1000*60*60*24))/(1000*60*60);
		
//		System.out.println(day+"天,"+hour+"小时");
		re = day+"天"+hour+"小时";
		
		return re;
	}
	public int getMultiple(){
		return webSiteMapper.getConfParamValue();
	}
	/**
	 * 设置查询时间(公共调用方法)
	 * @param request
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map queryBetweenTime(HttpServletRequest request, Map params) {
		String seltDate = request.getParameter("time");
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar d = Calendar.getInstance();
		String timeEnd = f.format(d.getTime());
		String timeBegin = "";

		if ("24H".equals(seltDate)) {// 最近一天
			d.add(Calendar.DATE, -1);
			timeBegin = f.format(d.getTime());
		} else if ("7D".equals(seltDate)) {// 最近一周
			d.add(Calendar.DATE, -7);
			timeBegin = f.format(d.getTime());
		} else if ("Today".equals(seltDate)) {// 今天
			d.add(Calendar.DATE, 0);
			d.set(Calendar.HOUR_OF_DAY, 0);
			d.set(Calendar.MINUTE, 0);
			d.set(Calendar.SECOND, 0);
			timeBegin = f.format(d.getTime());
		} else if ("Yes".equals(seltDate)) {// 昨天
			d.add(Calendar.DAY_OF_MONTH, -1);
			d.set(Calendar.HOUR_OF_DAY, 0);
			d.set(Calendar.MINUTE, 0);
			d.set(Calendar.SECOND, 0);
			timeBegin = f.format(d.getTime());
			d.set(Calendar.HOUR_OF_DAY, 23);
			d.set(Calendar.MINUTE, 59);
			d.set(Calendar.SECOND, 59);
			timeEnd = f.format(d.getTime());
		}else if ("Week".equals(seltDate)) {// 本周
			if(d.get(Calendar.DAY_OF_WEEK)==1){
				d.add(Calendar.DAY_OF_WEEK, -(d.get(Calendar.DAY_OF_WEEK)+5));
			}else{
				d.add(Calendar.DAY_OF_WEEK, -(d.get(Calendar.DAY_OF_WEEK)-2));
			}
			
			d.set(Calendar.HOUR_OF_DAY, 0);
			d.set(Calendar.MINUTE, 0);
			d.set(Calendar.SECOND, 0);
			timeBegin = f.format(d.getTime());
		}else if ("Month".equals(seltDate)) {// 本月
			d.add(Calendar.MONTH, 0);
			//设置为1号,当前日期既为本月第一天 
			d.set(Calendar.DAY_OF_MONTH,1);
			d.set(Calendar.HOUR_OF_DAY, 0);
			d.set(Calendar.MINUTE, 0);
			d.set(Calendar.SECOND, 0);
			timeBegin = f.format(d.getTime());
		}else if ("LastMonth".equals(seltDate)) {// 上月
			 d.add(Calendar.MONTH, -1);
		     d.set(Calendar.DAY_OF_MONTH,1);//上月第一天
		     d.set(Calendar.HOUR_OF_DAY, 0);
			 d.set(Calendar.MINUTE, 0);
			 d.set(Calendar.SECOND, 0);
		     timeBegin = f.format(d.getTime());
		     Calendar cale = Calendar.getInstance();
		     //设置为1号,当前日期既为本月第一天 
		     cale.set(Calendar.DAY_OF_MONTH,0);
		     cale.set(Calendar.HOUR_OF_DAY, 23);
		     cale.set(Calendar.MINUTE, 59);
		     cale.set(Calendar.SECOND, 59);
		     timeEnd = f.format(cale.getTime());

		}else{
			d.add(Calendar.DATE, -1);
		}/* else if ("1Y".equals(seltDate)) {// 
			d.add(Calendar.MONTH, -3);
		} else if ("".equals(seltDate)) {// 最近半年
			d.add(Calendar.MONTH, -6);
		} */
		
		params.put("timeBegin", timeBegin);
		params.put("timeEnd", timeEnd);
		return params;
	}
//	public static void main(String[] args) throws Exception{
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date d1 = sdf.parse("2016-09-21 18:09:00");
//		Date d2 = sdf.parse("2016-09-10 17:00:00");
//		
////		getDays(d1.getTime()-d2.getTime());
//	}
}
