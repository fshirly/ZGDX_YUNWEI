package com.fable.insightview.monitor.discover.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fable.insightview.monitor.host.entity.MODevice;
import com.fable.insightview.monitor.host.mapper.HostMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.monitor.database.entity.Db2InfoBean;
import com.fable.insightview.monitor.database.mapper.Db2Mapper;
import com.fable.insightview.monitor.database.service.IOracleService;
import com.fable.insightview.monitor.discover.entity.MODeviceObj;
import com.fable.insightview.monitor.discover.mapper.MODeviceMapper;
import com.fable.insightview.monitor.discover.service.IMObjectResSynService;
import com.fable.insightview.monitor.host.entity.HostComm;
import com.fable.insightview.monitor.host.entity.KPINameDef;
import com.fable.insightview.monitor.middleware.middlewarecommunity.entity.SysMiddleWareCommunityBean;
import com.fable.insightview.monitor.middleware.middlewarecommunity.service.ISysMiddleWareCommunityService;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJMXBean;
import com.fable.insightview.monitor.middleware.tomcat.service.IMiddlewareService;
import com.fable.insightview.monitor.mobject.entity.MObjectDefBean;
import com.fable.insightview.monitor.mobject.mapper.MobjectInfoMapper;
import com.fable.insightview.monitor.mocpus.entity.MOCPUsBean;
import com.fable.insightview.monitor.mocpus.mapper.MOCPUsMapper;
import com.fable.insightview.monitor.momemories.entity.MOMemoriesBean;
import com.fable.insightview.monitor.momemories.mapper.MOMemoriesMapper;
import com.fable.insightview.monitor.monetworkIif.entity.MONetworkIfBean;
import com.fable.insightview.monitor.monetworkIif.entity.TopoLinkBean;
import com.fable.insightview.monitor.monetworkIif.mapper.MONetworkIfMapper;
import com.fable.insightview.monitor.mostorage.entity.MOStorageBean;
import com.fable.insightview.monitor.mostorage.mapper.MOStorageMapper;
import com.fable.insightview.monitor.movolumes.entity.MOVolumesBean;
import com.fable.insightview.monitor.movolumes.mapper.MOVolumesMapper;
import com.fable.insightview.monitor.perf.entity.PerfKPIDefBean;
import com.fable.insightview.monitor.perf.mapper.PerfKPIDefMapper;
import com.fable.insightview.monitor.perf.mapper.PerfPollTaskMapper;
import com.fable.insightview.monitor.perf.mapper.PerfTaskInfoMapper;
import com.fable.insightview.monitor.sysdbmscommunity.service.ISysDBMSCommunityService;
import com.fable.insightview.monitor.website.mapper.WebSiteMapper;
import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.common.helper.RestHepler;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.dict.util.DictionaryLoader;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.snmpcommunity.entity.SysVMIfCommunityBean;
import com.fable.insightview.platform.snmpcommunity.service.ISnmpCommunityService;
import com.fable.insightview.platform.snmpcommunity.service.ISysVMIfCommunityService;
import com.fable.insightview.platform.sysinit.SystemParamInit;

/**
 * @Description: 设备列表
 * @author zhengxh
 * @Date 2014-8-26
 */
@Controller
@RequestMapping("/monitor/deviceManager")
public class DeviceManagerController {
	@Autowired
	MobjectInfoMapper mobjectInfoMapper;
	@Autowired
	MODeviceMapper moDeviceMapper;
	@Autowired
	MONetworkIfMapper moNetworkIfMapper;
	@Autowired
	MOVolumesMapper moVolumesMapper;
	@Autowired
	MOStorageMapper moStorageMapper;
	@Autowired
	MOCPUsMapper mocpUsMapper;
	@Autowired
	MOMemoriesMapper moMemoriesMapper;
	@Autowired
	IMiddlewareService imiddlewareService;
	@Autowired
	PerfKPIDefMapper perfKPIDefMapper;
	@Autowired
	PerfPollTaskMapper perfPollTaskMapper;
	@Autowired
	PerfTaskInfoMapper perfTaskInfoMapper;
	@Autowired
	ISysDBMSCommunityService sysDBMSCommunityService;
	@Autowired
	ISysMiddleWareCommunityService middleWareCommunityService;
	@Autowired
	IMObjectResSynService mobjectResSyn;
	@Autowired
	ISysVMIfCommunityService vmIfCommunityService;
	@Autowired
	ISnmpCommunityService snmpCommunityService;
	@Autowired
	Db2Mapper db2Mapper;
	@Autowired
	IOracleService orclService;
	@Autowired 
	WebSiteMapper webSiteMapper;

	@Autowired
	HostMapper modMaper;

	DecimalFormat format = new DecimalFormat("0.00");
	private final static Logger logger = LoggerFactory
			.getLogger(DeviceManagerController.class);

	/**
	 * 设备列表页面跳转
	 */
	@RequestMapping("/toDeviceManagerList")
	public ModelAndView toDeviceManagerList(String navigationBar) throws Exception {
		ModelAndView mv = new ModelAndView("monitor/device/deviceManager_list");
		mv.addObject("navigationBar", navigationBar);
		return mv ;
	}

	/**
	 * 初始化树
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/initDeviceTree")
	@ResponseBody
	public Map<String, Object> initDeviceTree() throws Exception {
		logger.info("初始化树");
		List<MObjectDefBean> menuLst = mobjectInfoMapper
				.queryMObjectRelation2();
//		List<MObjectDefBean> dataLst = new ArrayList<MObjectDefBean>();
//		for (int i = 0; i < menuLst.size(); i++) {
//			if(menuLst.get(i).getClassId() != 75){
//				dataLst.add(menuLst.get(i));
//			}
//		}
		String menuLstJson = JsonUtil.toString(menuLst);
		logger.info("menuLstJson:" + menuLstJson);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("menuLstJson", menuLstJson);
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
		map.put("navigationBar",request.getParameter("navigationBar"));
		return new ModelAndView("monitor/device/deviceClassify_list");
	}
	
	@RequestMapping("/toPhysicsList")
	public ModelAndView toPhysicsList(HttpServletRequest request, ModelMap map)
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
		map.put("navigationBar",request.getParameter("navigationBar"));
		return new ModelAndView("monitor/device/devicePhysics_list");
	}
	/**
	 * 接口页面跳转
	 */
	@RequestMapping("/toInterfaceList")
	public ModelAndView toInterfaceList(HttpServletRequest request, ModelMap map)
			throws Exception {
		map.put("mOClassID", request.getParameter("mOClassID"));

		Map<Integer, String> osMap = DictionaryLoader
				.getConstantItems("OperStatus");// 可用状态
		map.put("osMap", osMap);
		map.put("flag", request.getParameter("flag"));// 隐藏字段标志
		map.put("navigationBar",request.getParameter("navigationBar"));
		return new ModelAndView("monitor/device/interface_list");
	}
	/**
	 * 未知类型设备页面跳转
	 */
	@RequestMapping("/toUnknownDeviceList")
	public ModelAndView toUnknownDeviceList(HttpServletRequest request, ModelMap map)
			throws Exception {
		map.put("mOClassID", request.getParameter("mOClassID"));
		map.put("flag", request.getParameter("flag"));// 隐藏字段标志
		map.put("navigationBar",request.getParameter("navigationBar"));
		return new ModelAndView("monitor/device/unkonwnDevice_list");
	}
	@RequestMapping("/updateMoAlias")
	@ResponseBody
	public boolean updateMoAlias(String moID,String moAlias){
		boolean flag=false;
		if(moID!=null && moID.trim().length()>0){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("moID", moID);
			map.put("moAlias", moAlias);
			try {
				moDeviceMapper.updateMoAlias(map);
				flag= true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				flag=false;
				e.printStackTrace();
			}
			return flag;
		}else{
			return flag;
		}
	}
	/**
	 * 查询未知类型设备列表
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listUnknownDevice")
	@ResponseBody
	public Map<String, Object> listUnknownDevice(MODeviceObj vo,
			HttpServletRequest request) throws Exception {
		logger.info("开始...获取位置类型设备页面显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MODeviceObj> page = new Page<MODeviceObj>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("deviceip", vo.getDeviceip());
		paramMap.put("domainName", vo.getDomainName());
		paramMap.put("MID", KPINameDef.JOBPINGPOLL);
		page.setParams(paramMap);
		// 查询分页数据
		List<MODeviceObj> list = moDeviceMapper.queryUnknownDeviceList(page);
		// TOPO 新增可用/持续时间
		getListInfo(list,vo);
		
		// 查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", list);
		logger.info("结束...获取未知类型设备页面显示数据");
		return result;
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
	public Map<String, Object> listDevice(MODeviceObj vo,
			HttpServletRequest request) throws Exception {
		logger.info("开始...获取页面显示数据");
		System.out.println("vo.getDeviceip():"+vo.getDeviceip()+"vo.getMoname():"+vo.getMoname()+"*");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MODeviceObj> page = new Page<MODeviceObj>();
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
		// 查询分页数据
		List<MODeviceObj> moLst = moDeviceMapper.queryMoDeviceList(page);

		getListInfo(moLst,vo);
		
		// 由于上一步对moLst中元素的Operstatus状态可能有更改，所以这一步进行最后的过滤
		List<MODeviceObj> resultList  = new ArrayList<MODeviceObj>();
		for (MODeviceObj moDeviceObj : moLst) {
			if (vo.getOperstatus()==null || moDeviceObj.getOperstatus() == vo.getOperstatus() || vo.getOperstatus()==-1) {
				resultList.add(moDeviceObj);
			}
		}
		// 查询总记录数
		int totalCount = resultList.size()==0?0:page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", resultList);
		logger.info("结束...获取页面显示数据");
		return result;
	}
	/**
	 * 宿主机列表
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listPhysics")
	@ResponseBody
	public Map<String, Object> listPhysics(MODeviceObj vo,
			HttpServletRequest request) throws Exception {
		logger.info("开始...获取页面显示数据");
		System.out.println("vo.getDeviceip():"+vo.getDeviceip()+"vo.getMoname():"+vo.getMoname()+"*");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MODeviceObj> page = new Page<MODeviceObj>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String classID = request.getParameter("classID");
			paramMap.put("classID", classID);
		
		paramMap.put("deviceip", vo.getDeviceip());
		paramMap.put("moname", vo.getMoname());
		paramMap.put("domainName", vo.getDomainName());
		paramMap.put("operStatus", vo.getOperstatus());
		paramMap.put("ismanage", vo.getIsmanage());
		paramMap.put("nemanufacturername", vo.getNemanufacturername());
		paramMap.put("MID", KPINameDef.JOBPINGPOLL);
		page.setParams(paramMap);
		// 查询分页数据
		List<MODeviceObj> moLst = moDeviceMapper.queryPhysicsDeviceList(page);

		// 查询数据字典关联数据
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
	
	public int getMultiple(){
		return webSiteMapper.getConfParamValue();
	}
	
	public  List<MODeviceObj>  getListInfo(List<MODeviceObj> parLst,MODeviceObj bean){
		int period=1;
		long curr=0;
		long currTime=orclService.getCurrentDate().getTime();
		Date updateAlarmTime;
		Date collectTime;
		Map<Integer,String> imMap = DictionaryLoader.getConstantItems("IsManage");//是否管理
		if(parLst!=null){
			for (int i = 0; i < parLst.size(); i++) {
				 bean = parLst.get(i);
				 updateAlarmTime = bean.getUpdateAlarmTime();
				 collectTime=bean.getCollectTime();
				if(bean.getDoIntervals()==null || "".equals(bean.getDoIntervals())){
					period=bean.getDefDoIntervals()*getMultiple()*60000;
				}else{
					period=bean.getDoIntervals()*getMultiple()*1000;
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
								String durationTime = HostComm.getDurationToTime(currTime-updateAlarmTime.getTime());
								bean.setDurationTime(durationTime);
							}else{
								bean.setDurationTime("");
							}
							logger.debug("设备状态_状态对应图：{},IP:{}",bean.getOperstatus()+"_"+bean.getOperstatusdetail(),bean.getDeviceip());
					}else{
						bean.setOperaTip("未知");
						bean.setOperstatusdetail("unknown.png");
						bean.setOperstatus(3);//被认定为是未知后，设置Operstatus状态为3
						String durationTime = HostComm.getDurationToTime(currTime-collectTime.getTime());
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
			}
		}
	return  parLst;
	}
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

	/**
	 * 磁盘页面跳转
	 */
	@RequestMapping("/toDiscList")
	public ModelAndView toDiscList(HttpServletRequest request, ModelMap map)
			throws Exception {
		map.put("mOClassID", request.getParameter("mOClassID"));
		Map<Integer, String> osMap = DictionaryLoader
				.getConstantItems("OperStatus");// 可用状态
		map.put("osMap", osMap);
		map.put("flag", request.getParameter("flag"));// 隐藏字段标志
		map.put("navigationBar",request.getParameter("navigationBar"));
		return new ModelAndView("monitor/device/disc_list");
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
		paramMap.put("deviceIP", vo.getDeviceIP());
		paramMap.put("rawDescr", vo.getRawDescr());
		page.setParams(paramMap);
		// 查询分页数据
		List<MOVolumesBean> list = moVolumesMapper.queryList(page);
		// 查询数据字典关联数据
		Map<Integer, String> osMap = DictionaryLoader
				.getConstantItems("OperStatus");// 可用状态
		Map<Integer, String> asMap = DictionaryLoader
				.getConstantItems("AdminStatus");// 操作状态

        Map<String,Object> params = new HashMap<>();
		for (MOVolumesBean mo : list) {
			mo.setOperStatusName(osMap.get(mo.getOperStatus()));
			mo.setAdminStatusName(asMap.get(mo.getAdminStatus()));
            params.put("MOID", mo.getDeviceMOID());
            params.put("KPIName", KPINameDef.DISKRATE);
            params.put("tableName", "PerfSnapshotHost" );
            MODevice moDevice = modMaper.getChartDisk(params);
            if (moDevice != null) {
                mo.setUsage(moDevice.getPerfvalue()/1000);
            } else {
                mo.setUsage(null);
            }
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
	 * cpu页面跳转
	 */
	@RequestMapping("/toCpuList")
	public ModelAndView toCpuList(HttpServletRequest request, ModelMap map)
			throws Exception {
		map.put("mOClassID", request.getParameter("mOClassID"));

		Map<Integer, String> osMap = DictionaryLoader
				.getConstantItems("OperStatus");// 可用状态
		map.put("osMap", osMap);
		map.put("flag", request.getParameter("flag"));// 隐藏字段标志
		map.put("navigationBar",request.getParameter("navigationBar"));
		return new ModelAndView("monitor/device/cpu_list");
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
		page.setParams(paramMap);
		// 查询分页数据
		List<MOCPUsBean> list = mocpUsMapper.queryList(page);
		// 查询数据字典关联数据
		Map<Integer, String> osMap = DictionaryLoader
				.getConstantItems("OperStatus");// 可用状态
		Map<Integer, String> asMap = DictionaryLoader
				.getConstantItems("AdminStatus");// 操作状态

		Map<String,Object> params = new HashMap<>();
		for (MOCPUsBean mo : list) {
			mo.setOperStatusName(osMap.get(mo.getOperStatus()));
			mo.setAdminStatusName(asMap.get(mo.getAdminStatus()));
			params.put("MOID", mo.getDeviceMOID());
			params.put("KPIName", KPINameDef.PERUSAGE);
			params.put("tableName", "PerfSnapshotHost" );
			MODevice moDevice = modMaper.getChartCPU(params);
			int period;
			Date collectTime;
			long curr;
			// 当前时间
			long currTime=System.currentTimeMillis();
			if (moDevice != null) {
				collectTime=moDevice.getCollecttime();
				if(moDevice.getDoIntervals()==null || "".equals(moDevice.getDoIntervals())){
					//采集周期的倍数
					period=moDevice.getDefDoIntervals()*getMultiple()*60000;
				}else{
					//采集周期的倍数
					period=moDevice.getDoIntervals()*getMultiple()*1000;
				}
				if (collectTime != null) {
					curr=currTime-moDevice.getCollecttime().getTime();
					if(curr<=period){
						if(moDevice.getPerValue() != null){ //存在值为null的情况，此处抛出空指针异常
							mo.setUsage(format.format(Double.parseDouble(moDevice.getPerValue())));
							if(moDevice.getPerValue().equals("-1")){
								mo.setUsage("");
							}
						}
						else{
							mo.setUsage("");
						}
						if(moDevice.getDevicestatus().equals("2")) {//down
							mo.setUsage("");
						}
					}else{
						mo.setUsage("");
					}
				}else{
					mo.setUsage("");
				}
			} else {
				mo.setUsage("");
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
	 * 内存页面跳转
	 */
	@RequestMapping("/toMemoryList")
	public ModelAndView toMemoryList(HttpServletRequest request, ModelMap map)
			throws Exception {
		map.put("mOClassID", request.getParameter("mOClassID"));

		Map<Integer, String> osMap = DictionaryLoader
				.getConstantItems("OperStatus");// 可用状态
		map.put("osMap", osMap);
		map.put("flag", request.getParameter("flag"));// 隐藏字段标志
		map.put("navigationBar",request.getParameter("navigationBar"));
		return new ModelAndView("monitor/device/memory_list");
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
		page.setParams(paramMap);
		// 查询分页数据
		List<MOMemoriesBean> list = moMemoriesMapper.queryList(page);
		// 查询数据字典关联数据
		Map<Integer, String> osMap = DictionaryLoader
				.getConstantItems("OperStatus");// 可用状态
		Map<Integer, String> asMap = DictionaryLoader
				.getConstantItems("AdminStatus");// 操作状态

		Map<String,Object> params = new HashMap<>();
		for (MOMemoriesBean mo : list) {
			mo.setOperStatusName(osMap.get(mo.getOperStatus()));
			mo.setAdminStatusName(asMap.get(mo.getAdminStatus()));
			params.put("MOID", mo.getDeviceMOID());
			params.put("KPIName", KPINameDef.PHYMEMUSAGE);
			params.put("tableName", "PerfSnapshotHost" );
			MODevice moDevice = modMaper.getChartMemory(params);
			int period;
			Date collectTime;
			long curr;
			// 当前时间
			long currTime=System.currentTimeMillis();
			if (moDevice != null) {
				// 采集时间
				collectTime=moDevice.getCollecttime();
				logger.debug("设备{}内存采集时间为{}",moDevice.getDeviceip(),collectTime);
				if(moDevice.getDoIntervals()==null || "".equals(moDevice.getDoIntervals())){
					//采集周期的倍数
					period=moDevice.getDefDoIntervals()*getMultiple()*60000;
				}else{
					//采集周期的倍数
					period=moDevice.getDoIntervals()*getMultiple()*1000;
				}
				logger.debug("设备{}内存采集周期倍数{}",moDevice.getDeviceip(),period);
				if (collectTime != null) {
					// 当前时间与采集时间差
					curr=currTime-moDevice.getCollecttime().getTime();
					logger.debug("设备{}内存当前时间与采集时间差{}",moDevice.getDeviceip(),curr);
					//当前时间与采集时间差超过采集周期的倍数（可配置）或没有采集时间（即没有采集）
					if(curr<=period){
						if(moDevice.getPerValue() != null){ //存在值为null的情况，此处抛出空指针异常
							mo.setUsage(format.format(Double.parseDouble(moDevice.getPerValue())));
						}
						else{
							mo.setUsage("");
						}
						if(moDevice.getDevicestatus()!=null&&moDevice.getDevicestatus().equals("2")) {//down
							mo.setUsage("");
						}
					}else{
						mo.setUsage("");
					}
				}else{
					mo.setUsage("");
				}
			} else {
				mo.setUsage("");
			}
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
	 * 数据存储页面跳转
	 */
	@RequestMapping("/toStorageList")
	public ModelAndView toStorageList(HttpServletRequest request, ModelMap map)
			throws Exception {
		map.put("mOClassID", request.getParameter("mOClassID"));
		Map<Integer, String> osMap = DictionaryLoader
				.getConstantItems("OperStatus");// 可用状态
		map.put("osMap", osMap);
		map.put("flag", request.getParameter("flag"));// 隐藏字段标志
		map.put("navigationBar",request.getParameter("navigationBar"));
		return new ModelAndView("monitor/device/storage_list");
	}

	/**
	 * 获取数据存储页面显示数据
	 * @param vo
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/listStorage")
	@ResponseBody
	public Map<String, Object> listStorage(MOStorageBean vo,
			HttpServletRequest request) throws Exception {
		logger.info("开始...获取页面显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MOStorageBean> page = new Page<MOStorageBean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("mOClassID", request.getParameter("mOClassID"));
		paramMap.put("deviceMOName", vo.getDeviceMOName());
		page.setParams(paramMap);
		// 查询分页数据
		List<MOStorageBean> list = moStorageMapper.queryList(page);
		// 查询数据字典关联数据
		Map<Integer, String> osMap = DictionaryLoader
				.getConstantItems("OperStatus");// 可用状态
		Map<Integer, String> asMap = DictionaryLoader
				.getConstantItems("AdminStatus");// 操作状态

		for (MOStorageBean mo : list) {
			mo.setOperStatusName(osMap.get(mo.getOperStatus()));
			mo.setAdminStatusName(asMap.get(mo.getAdminStatus()));
			if (mo.getCapacity() != null && !"".equals(mo.getCapacity())) {
				mo.setCapacity(HostComm.getBytesToSize(Long.parseLong(mo
						.getCapacity())));
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
	 * 查找节点ID
	 *
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/searchTreeNodes")
	@ResponseBody
	public MObjectDefBean searchTreeNodes(
			@RequestParam("classLable") String classLable) {
		Map<String, String> map = new HashMap();
		map.put("classLable", classLable);
		List<MObjectDefBean> objList = mobjectInfoMapper
				.getMessageByTreeName2(map);
		if (objList != null && objList.size() > 0) {
			return objList.get(0);
		} else {
			return null;
		}
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
		request.setAttribute("classID", classID);
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
		logger.info("开始加载管理对象定义数据。。。。。。。。");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<PerfKPIDefBean> page = new Page<PerfKPIDefBean>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", perfKPIDefBean.getName());
		paramMap.put("classID", perfKPIDefBean.getClassID());
		page.setParams(paramMap);
		List<PerfKPIDefBean> perfKPIDefList = perfKPIDefMapper
				.selectPerfKPIDefs(page);
		int total = page.getTotalRecord();
		logger.info("total=====" + total);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", perfKPIDefList);
		logger.info("加载数据结束。。。");
		return result;
	}

	@RequestMapping("/findPerfKPIDef")
	@ResponseBody
	public PerfKPIDefBean findPerfKPIDef(PerfKPIDefBean perfKPIDefBean) {
		PerfKPIDefBean perfKPIDef = perfKPIDefMapper
				.getPerfKPIDefById(perfKPIDefBean.getKpiID());
		return perfKPIDef;
	}

	/**
	 * 查询中间件信息
	 *
	 */
	@RequestMapping("/findMiddleInfo")
	@ResponseBody
	public MOMiddleWareJMXBean findMiddleInfo(MOMiddleWareJMXBean vo) {
		MOMiddleWareJMXBean moMid = imiddlewareService
				.selectMoMidByPrimaryKey(vo.getMoId());
		return moMid;
	}

	/**
	 * 打开配置页面
	 */
	@RequestMapping("/toSetMiddleware")
	public ModelAndView toSetMiddleware(HttpServletRequest request,
			ModelMap map, MODeviceObj vo) {
		logger.info("加载配置页面");
		System.out.println("加载配置页面");
		int moid = Integer.parseInt(request.getParameter("moid"));
		System.out.println("moId:" + moid);
		String ip = request.getParameter("ip");
		System.out.println("ip:" + ip);
		String jmxType = request.getParameter("jmxType");
		System.out.println("jmxType:" + jmxType);
		map.put("moid", moid);
		map.put("ip", ip);
		map.put("jmxType", jmxType);

		SysMiddleWareCommunityBean middleCommunity = new SysMiddleWareCommunityBean();
		middleCommunity.setIpAddress(ip);
		middleCommunity.setMiddleWareType(jmxType);
		middleCommunity = middleWareCommunityService
				.getCommunityByIP(middleCommunity);
		map.put("middleCommunity", middleCommunity);
		return new ModelAndView("monitor/device/setMiddleware");
	}

	/**
	 * 重新发现
	 *
	 */
	@RequestMapping("/doRediscover")
	@ResponseBody
	public Map<String, Object> doRediscover(HttpServletRequest request) {
		String deviceIP = request.getParameter("deviceip");
		int moClassId = Integer.parseInt(request.getParameter("moClassId"));
		int port = -1;
		if (moClassId < 10 || moClassId == 75) {
			// 查询获得虚拟宿主机的端口
			if (moClassId == 9 || moClassId == 8  || moClassId == 75) {
				SysVMIfCommunityBean vo = new SysVMIfCommunityBean();
				vo.setDeviceIP(deviceIP);
				SysVMIfCommunityBean bean = new SysVMIfCommunityBean();
				bean = vmIfCommunityService.getObjFromDeviceIP(vo);
				if (bean == null) {
					String[] split = deviceIP.split("\\.");
					String ip = split[0]+"."+split[1]+"."+split[2]+".*";
					vo.setDeviceIP(ip);
					bean = vmIfCommunityService.getObjFromDeviceIP(vo);
					if (bean == null) {
						vo.setDeviceIP("*");
						bean = vmIfCommunityService.getObjFromDeviceIP(vo);
					}
				}
				
				if (bean != null) {
					if (bean.getPort() != null) {
						port = bean.getPort();
					}
				}
			} else {
				port = 161;
			}
		}
		if (request.getParameter("port") != null) {
			port = Integer.parseInt(request.getParameter("port"));
		}
		String moClassName = mobjectInfoMapper.getMobjectById(moClassId)
				.getClassName();
		SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
				.getSession().getAttribute("sysUserInfoBeanOfSession");
		int creator = sysUserInfoBeanTemp.getId().intValue();
		String dbName = "";
		if(moClassId == 54){
			dbName = request.getParameter("dbName");
		}
		return sysDBMSCommunityService.addDiscoverTask2(deviceIP, creator,
				moClassName, port,dbName);
	}

	@RequestMapping("/showDeviceTask")
	public ModelAndView showDeviceTask(HttpServletRequest request,
			HttpServletResponse response) {
		String taskid = request.getParameter("taskid");

		String deviceip = request.getParameter("deviceip");
		String moid = request.getParameter("moid");
		String moClassId = request.getParameter("moClassId");
		String nemanufacturername = request.getParameter("nemanufacturername");
		String port = request.getParameter("port");
		String dbName = request.getParameter("dbName");
		request.setAttribute("taskid", taskid);
		request.setAttribute("deviceip", deviceip);
		request.setAttribute("moid", moid);
		request.setAttribute("moClassId", moClassId);
		request.setAttribute("nemanufacturername", nemanufacturername);
		request.setAttribute("port", port);
		request.setAttribute("dbName", dbName);
		return new ModelAndView("monitor/discover/rediscover_list");
	}
    /**
     * 监测同步到资源
     * moClassId:监测对象类型
     * relationPath：父节点的节点路径
     * @param request
     * @param map
     * @return
     */
	@RequestMapping("/showCmdb")
	public ModelAndView showCmdb(HttpServletRequest request, ModelMap map) {
		Integer moClassID = Integer.parseInt(request.getParameter("moClassId"));
		Integer rsMoClassID = Integer.parseInt(request.getParameter("moClassId"));
		if(moClassID==59 || moClassID==60){
			moClassID=6;	
		}
		String relationPath = request.getParameter("relationPath");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("moClassID", moClassID);
		params.put("relationPath", relationPath);
		//获取监测对象对应的资源类型及其名称--下拉框
		String TypeName = this.requestHanlder(moClassID, "type");
		//对应的监测对象主键
		String moids = request.getParameter("moids");
		if(moClassID==6){
			params.put("moClassID", rsMoClassID);
		}
		//获取子对象及其名称--下拉框
		List<MObjectDefBean> NameLst = mobjectInfoMapper
				.getresNameByParentID(params);
		map.put("nameLst", NameLst);
		// [{"id":4,"name":"附属资源-机柜U位"}]
		if (!TypeName.equals("[]")) {
			JSONArray jsonArray = JSONArray.fromObject(TypeName);
			List<Map<String, Object>> mapListJson = (List) jsonArray;
			map.put("typeLst", mapListJson);

			Integer id = (Integer) mapListJson.get(0).get("id");
			//获取资源附属组件的信息
			String assetName = this.requestHanlder(id, "asset");

			JSONObject jsonObj = JSONObject.fromObject(assetName);
			map.put("assetLst", jsonObj.get("assetTypeList").toString());
			map.put("isAsset", jsonObj.get("isAsset").toString());
		}
		//没有组件，传给前台一个标志
		if(NameLst.size() == 0){
			map.put("isShowTip", 1);
		}
		map.put("moClassID", moClassID);
		map.put("rsMoClassID", rsMoClassID);
		map.put("moids", moids);

		return new ModelAndView("monitor/device/cmdb");
	}

	/**
	 * 获取设备页面显示数据
	 * 
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/listResName")
	@ResponseBody
	public Map<String, Object> listResName(HttpServletRequest request) {
		logger.info("开始...获取页面显示数据");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Integer id = Integer.parseInt(request.getParameter("id"));
			String resName = this.requestHanlder(id, "res");
			// [{"id":5,"name":"处理器"},{"id":6,"name":"内存"},{"id":7,"name":"磁盘"},{"id":8,"name":"网卡"},{"id":20,"name":"接口/扩展插槽信息"}]
			result.put("resNameLstJson", resName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 获取设备页面显示数据
	 * 
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/listAssetName")
	@ResponseBody
	public Map<String, Object> listAssetName(HttpServletRequest request) {
		logger.info("开始...获取页面显示数据");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Integer id = Integer.parseInt(request.getParameter("typeId"));
			String assetName = this.requestHanlder(id, "asset");
			JSONObject jsonArray = JSONObject.fromObject(assetName);
			// {"assetTypeList":[{"id":11023,"name":"计算机"}],"isAsset":1}
			result.put("assetNameLstJson", jsonArray.get("assetTypeList")
					.toString());
			result.put("isAsset", jsonArray.get("isAsset").toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	private String requestHanlder(Integer moClassID, String type) {
		String path = "";
		if ("type".equals(type)) {
			path = "/rest/cmdb/restypedef/queryResTypeDefineByMoTypeId";
		} else if ("res".equals(type)) {
			path = "/rest/cmdb/restypedef/queryResCiComponentByResTypeId";
		} else if ("asset".equals(type)) {
			path = "/rest/cmdb/restypedef/queryAssetTypeIdByTypeDefineId";
		}

		try {
			String url = SystemParamInit.getValueByKey("rest.resSychron.url");
			logger.info("Request parameter url = " + url);
			String username = SystemParamInit.getValueByKey("rest.username");
			String password = SystemParamInit.getValueByKey("rest.password");

			// 拼接获取单板接口URL
			StringBuffer basePath = new StringBuffer();
			basePath.append(url);
			basePath.append(path);
			// 设置请求头信息
			HttpHeaders requestHeaders = RestHepler.createHeaders(username,
					password);
			HttpEntity<Object> requestEntity = new HttpEntity<Object>(
					moClassID, requestHeaders);
			RestTemplate rest = new RestTemplate();
			// 参数1：请求地址 ，参数2：请求方式，参数3：请求头信息，参数4：相应类
			ResponseEntity<String> rssResponse = rest.exchange(basePath
					.toString(), HttpMethod.POST, requestEntity, String.class);
			if (null != rssResponse) {
				return rssResponse.getBody();
			}
		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * * 加载CMDB配置信息
	 * @param request
	 * @throws Exception
	 * moClassID：监测对象类型ID
	 * resTypeID:资源对象类型ID
	 * moids:监测对象moid
	 * resids:监测组件和资源组件的映射关系
	 * assetTypeId：
	 */
	@RequestMapping("/doCmdbInfo")
	@ResponseBody
	public boolean doCmdbInfo(HttpServletRequest request) throws Exception {
		logger.info("开始...加载CMBD");
		boolean flag = true;
		Integer moClassID = Integer.parseInt(request.getParameter("moClassID"));
		Integer resTypeID = Integer.parseInt(request.getParameter("typeID"));
		String moids = request.getParameter("moids");
		String resids = request.getParameter("resids");
		String assetTypeId = request.getParameter("assetTypeId");
		logger.info("moClassID=" + moClassID + " typeID=" + resTypeID
				+ " moids=" + moids + " resids=" + resids + " assetTypeId="
				+ assetTypeId);
		try {
			// flag = mobjectResSyn.synchronRes(moClassID, resTypeID, moids,
			// resids);
			new Thread(new synchronRes(moClassID, resTypeID, moids, resids,
					assetTypeId)).start();

		} catch (Exception e) {
			logger.error("CMDB配置异常：" + e.getMessage());
			return false;
		}
		return flag;
	}

	class synchronRes implements Runnable {
		int moClassID;
		int resTypeID;
		String assetTypeId;
		String moids;
		String resids;
		  /**
         * moclassID:监测对象类型，MObjectDef表中的ClassID
         * resTypeID:资源类型，ResTypeDefiend表中的resTypeID
         * moids:要同步的监测对象对应的主键moid
         * resids:
         * assetTypeID:对应资产的监测类型的ID
         */
		public synchronRes(int moClassID, int resTypeID, String moids,
				String resids, String assetTypeId) {
			this.moClassID = moClassID;
			this.resTypeID = resTypeID;
			this.moids = moids;
			this.resids = resids;
			this.assetTypeId = assetTypeId;
		}
        /**
         * moclassID:监测对象类型，MObjectDef表中的ClassID
         * resTypeID:资源类型，ResTypeDefiend表中的resTypeID
         * moids:要同步的监测对象对应的主键moid
         * resids:
         * assetTypeID:对应资产的监测类型的ID
         */
		public void run() {
			mobjectResSyn.synchronRes(moClassID, resTypeID, moids, resids,
					assetTypeId);
		}
	}
	
	/**
	 * 选择数据库名
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toGetDbNames")
	public ModelAndView toGetDbNames(HttpServletRequest request,
			HttpServletResponse response) {
		String deviceip = request.getParameter("deviceip");
		String port = request.getParameter("port");
		String dbmstype = request.getParameter("dbmstype");
		String moClassId = request.getParameter("moClassId");
		String moid = request.getParameter("moid");
		String isForPerfSet = request.getParameter("isForPerfSet");
		String moAlias = request.getParameter("moAlias");
		if("null".equals(moAlias)|| moAlias==null){
			moAlias="";
		}
		request.setAttribute("deviceip", deviceip);
		request.setAttribute("port", port);
		request.setAttribute("dbmstype", dbmstype);
		request.setAttribute("moClassId", moClassId);
		request.setAttribute("moid", moid);
		request.setAttribute("isForPerfSet", isForPerfSet);
		request.setAttribute("moAlias", moAlias);
		return new ModelAndView("monitor/discover/dbNames_list");
	}
	
	
	@RequestMapping("/getAllDbNames")
	@ResponseBody
	public List<Db2InfoBean> getAllDbNames(HttpServletRequest request){
		int moId = Integer.parseInt(request.getParameter("moid"));
		return db2Mapper.getDataBaseByserver(moId);
	}
	
	/***
	 * 更新networkInfo表的是否采集信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/doUpdateIfInfo")
	@ResponseBody
	public boolean  doUpdateIfInfo(HttpServletRequest request){
		int moID = Integer.parseInt(request.getParameter("moID"));
		int isCollect = Integer.parseInt(request.getParameter("isCollect"));
		try {
			int i = moNetworkIfMapper.updateNetWorkIf(moID, isCollect);
			if(isCollect==0){
				moNetworkIfMapper.deleteSnapshotNetDevice(moID);
			}
			return i>0?true:false;
		} catch (Exception e) {
			logger.error("更新networkInfo表失败======",e);
			return false;
		}
	}
	
	/***
	 * 默认采集
	 * @param request
	 * @return
	 */
	@RequestMapping("/UpdateIfInfo")
	@ResponseBody
	public boolean  UpdateIfInfo(HttpServletRequest request){
		List<TopoLinkBean> topoList = moNetworkIfMapper.queryTopoList();
		int isCollect = Integer.parseInt(request.getParameter("isCollect"));
		for (TopoLinkBean topoLinkBean : topoList) {
			int i =moNetworkIfMapper.toUpdateNetWorkIf(topoLinkBean.getSourceMOID(), isCollect,topoLinkBean.getSourcePort());
			if(i<0){
				return false;
			}
		}
		return true;
	}
	@RequestMapping("/doUpdateDiskInfo")
	@ResponseBody
	public boolean doUpdateDiskInfo(HttpServletRequest request){
		int moID = Integer.parseInt(request.getParameter("moID"));
		int isCollect = Integer.parseInt(request.getParameter("isCollect"));
		try {
			int i = moVolumesMapper.updateDiskIfCollect(moID, isCollect);
			if(isCollect==0){
				moVolumesMapper.deleteSnapshotDeviceDevice(moID);
			}
			return i>0?true:false;
		} catch (Exception e) {
			logger.error("更新networkInfo表失败======",e);
			return false;
		}
		
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
					period=vo.getDoIntervals()*getMultiple()*60*1000;
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
							} else{
								vo.setOperaTip("未知");
								vo.setOperstatusdetail("unknown.png");
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
	
}
