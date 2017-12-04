package com.fable.insightview.monitor.topo.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fable.insightview.monitor.alarmdispatcher.utils.AlarmObjToJSON;
import com.fable.insightview.monitor.alarmmgr.alarmactive.mapper.AlarmActiveMapper;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmLevelInfo;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmStatusInfo;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmTypeInfo;
import com.fable.insightview.monitor.database.entity.MOMySQLDBServerBean;
import com.fable.insightview.monitor.discover.entity.MODeviceObj;
import com.fable.insightview.monitor.discover.entity.SysNetworkDiscoverTask;
import com.fable.insightview.monitor.discover.mapper.MODeviceMapper;
import com.fable.insightview.monitor.discover.mapper.SysNetworkDiscoverTaskMapper;
import com.fable.insightview.monitor.dispatcher.Dispatcher;
import com.fable.insightview.monitor.dispatcher.constants.TaskType;
import com.fable.insightview.monitor.dispatcher.facade.ManageFacade;
import com.fable.insightview.monitor.dispatcher.notify.TaskDispatchNotification;
import com.fable.insightview.monitor.dispatcher.server.TaskDispatcherServer;
import com.fable.insightview.monitor.dispatcher.utils.DispatcherUtils;
import com.fable.insightview.monitor.entity.AlarmNode;
import com.fable.insightview.monitor.host.entity.HostComm;
import com.fable.insightview.monitor.host.entity.KPINameDef;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJMXBean;
import com.fable.insightview.monitor.middleware.tomcat.service.IMiddlewareService;
import com.fable.insightview.monitor.mobject.entity.MObjectDefBean;
import com.fable.insightview.monitor.mobject.entity.MObjectRelationBean;
import com.fable.insightview.monitor.mobject.mapper.MobjectInfoMapper;
import com.fable.insightview.monitor.monetworkIif.entity.MONetworkIfBean;
import com.fable.insightview.monitor.monetworkIif.mapper.MONetworkIfMapper;
import com.fable.insightview.monitor.sysdbmscommunity.service.ISysDBMSCommunityService;
import com.fable.insightview.monitor.topo.entity.MOSubnetBean;
import com.fable.insightview.monitor.topo.entity.TopoBean;
import com.fable.insightview.monitor.topo.entity.TopoCurrentPerBean;
import com.fable.insightview.monitor.topo.entity.TopoDeviceListObj;
import com.fable.insightview.monitor.topo.entity.TopoLink;
import com.fable.insightview.monitor.topo.service.ITopoService;
import com.fable.insightview.monitor.website.entity.WebSite;
import com.fable.insightview.monitor.website.entity.WebSiteKPINameDef;
import com.fable.insightview.monitor.website.service.IWebSiteService;
import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.dict.util.DictionaryLoader;
import com.fable.insightview.platform.entity.Dict;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.managedDomain.entity.ManagedDomain;
import com.fable.insightview.platform.managedDomain.service.IManagedDomainService;
import com.fable.insightview.platform.manufacturer.entity.ManufacturerInfoBean;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.service.DictService;
import com.fable.insightview.platform.snmpcommunity.service.ISnmpCommunityService;

@Controller
@RequestMapping("/rest/monitor/topo")
public class TopoInvokeRestController {

	private static final Logger log = LoggerFactory.getLogger(TopoInvokeRestController.class);

	@Autowired
	MODeviceMapper moDeviceMapper;

	@Autowired
	MONetworkIfMapper moNetworkIfMapper;
	
	@Autowired
	private AlarmActiveMapper alarmActiveMapper;

	@Autowired
	ITopoService topoService;

	@Autowired
	MobjectInfoMapper mobjectInfoMapper;

	@Autowired
	IManagedDomainService managedDomainService;
	
	@Autowired
	ISnmpCommunityService snmpCommunityService;
	
	@Autowired
	ISysDBMSCommunityService sysDBMSCommunityService;
	
	@Autowired
	SysNetworkDiscoverTaskMapper sysNetworkDiscover;

	@Autowired
	DictService dictService;
	
	@Autowired
	IWebSiteService webSiteService;
	
	@Autowired
	IMiddlewareService middlewareService;
	/**
	 * 获取检测对象子类型
	 * @param parentClassId
	 * @return
	 */
	@RequestMapping(value = "/queryParentClassID", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody List<Integer> queryParentClassID(@RequestBody String parentClassId){
		
		List<Integer> classid= new ArrayList<Integer>();
		List<MObjectRelationBean> mobjectRelation = mobjectInfoMapper.queryParentClassID(parentClassId);
		 for (MObjectRelationBean mObjectRelation : mobjectRelation) {
			 classid.add(mObjectRelation.getClassID());
		}
		return classid;
	}
	
	
	/**
	 * TOPO链路告警
	 * 
	 * @return 返回设备JSON
	 *         参数map中,含有deviceMOID,ifMOID,alarmStatus,alarmLevel
	 */
	@RequestMapping(value = "/getLinkAlarm", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	public @ResponseBody
	TopoDeviceListObj getLinkAlarm(@RequestBody Map<String, String> param) {
		try {
			int pageNo = 1;
			int pageSize = 10000;
			Map<String, Object> pageParam = new HashMap<String, Object>();
			Page<AlarmNode> page = new Page<AlarmNode>();
			page.setPageNo(pageNo);
			page.setPageSize(pageSize);
			pageParam.put("deviceMOID", param.get("deviceMOID"));
			pageParam.put("ifMOID", param.get("ifMOID"));
			pageParam.put("alarmStatus", param.get("alarmStatus"));
			pageParam.put("alarmLevel", param.get("alarmLevel"));
			page.setParams(pageParam);
			List<AlarmNode> moList = alarmActiveMapper.queryLinkAlarm(page);
			TopoDeviceListObj topoJson = new TopoDeviceListObj();
			topoJson.setTotal(moList.size());
			// 获取告警状态名称
			List<AlarmStatusInfo> alarmStatus = alarmActiveMapper.queryAlarmStatusName();
			Map<Integer,String> AlarmStatusMap = new HashMap<Integer, String>();
			for (AlarmStatusInfo alarmStatusInfo : alarmStatus) {
				AlarmStatusMap.put(alarmStatusInfo.getStatusID(), alarmStatusInfo.getStatusName());
			}
			// 获取告警类型名称
			List<AlarmTypeInfo> alarmType = alarmActiveMapper.queryAlarmTypeName();
			Map<Integer,String> AlarmTypeMap = new HashMap<Integer, String>();
			for (AlarmTypeInfo alarmTypesInfo : alarmType) {
				AlarmTypeMap.put(alarmTypesInfo.getAlarmTypeID(), alarmTypesInfo.getAlarmTypeName());
			}
			// 获取告警等级名称
			List<AlarmLevelInfo> alarmLevel = alarmActiveMapper.queryAlarmLevelName();
			Map<Integer,String> AlarmLevelMap = new HashMap<Integer, String>();
			for (AlarmLevelInfo alarmLevelInfo : alarmLevel) {
				AlarmLevelMap.put(alarmLevelInfo.getAlarmLevelValue(), alarmLevelInfo.getAlarmLevelName());
			}
			// 初始化返回json
			List<Map<String, Object>> objDetail = new ArrayList<Map<String, Object>>();
			Map<String, Object> detail = new HashMap<String, Object>();
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
			for (AlarmNode mo : moList) {
				detail = new HashMap<String, Object>();
				detail.put("alarmID", mo.getAlarmID());
				detail.put("alarmOperateStatus",mo.getAlarmOperateStatus());
				detail.put("alarmOperateStatusName",AlarmStatusMap.get(mo.getAlarmOperateStatus()));
				detail.put("alarmLevelName", AlarmLevelMap.get(mo.getAlarmLevel()));
				detail.put("alarmLevel", mo.getAlarmLevel());
				detail.put("alarmTitle", mo.getAlarmTitle());
				detail.put("moid", mo.getMoid());
				detail.put("sourceMOName", mo.getSourceMOName());
				detail.put("MOName", mo.getMoName());
				detail.put("alarmType", mo.getAlarmType());
				detail.put("alarmTypeName", AlarmTypeMap.get(mo.getAlarmType()));
				detail.put("lastTime", simple.format(mo.getLastTime()));
				detail.put("repeatCount", mo.getRepeatCount());
				objDetail.add(detail);
			}
			log.info("objDetail.size=" + objDetail.size());
			topoJson.setRow(objDetail);
			return topoJson;
		} catch (Exception e) {
			log.info(" topo链路告警错误!");
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * TOPO链路当前性能值
	 * 
	 * @return 返回设备JSON
	 *         参数map中,含有deviceMOID,ifMOID 
	 */
	@RequestMapping(value = "/getCurrentPerformanceValue", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	public @ResponseBody
	Map getCurrentPerformanceValue(@RequestBody Map<String, String> param) {
		try {
			Map<String, Object> pageParam = new HashMap<String, Object>();
			Map<String,String> mapValue = new HashMap<String, String>();
			String deviceMOID = param.get("deviceMOID");
			String ifMOID = param.get("ifMOID");
			 
			if (deviceMOID != null && !deviceMOID.equals("")) {
				mapValue.put("deviceMOID", deviceMOID);
				pageParam.put("deviceMOID", deviceMOID);
			}else
			{
			  return  new HashMap<String, Object>();
			}
			if (ifMOID != null && !ifMOID.equals("")) {
				mapValue.put("ifMOID",  ifMOID);
				pageParam.put("ifMOID", ifMOID);
			}else
			{
				  return  new HashMap<String, Object>();
			}
			
			List<TopoCurrentPerBean> moList = alarmActiveMapper.queryCurrentPerformance(pageParam);
			
			for (TopoCurrentPerBean topoCurrentPerBean : moList) {
				if(topoCurrentPerBean.getKpiName().equals("IfUsage"))
				{
					mapValue.put("ifUsage", topoCurrentPerBean.getPerfValue());
				} else if(topoCurrentPerBean.getKpiName().equals("Flows"))
				{
					mapValue.put("flows", topoCurrentPerBean.getPerfValue());
				}else if(topoCurrentPerBean.getKpiName().equals("InDiscards"))
				{
					if(mapValue.containsKey("discards")){
						mapValue.put("discards",String.valueOf(Integer.valueOf(mapValue.get("discards"))+Integer.valueOf(topoCurrentPerBean.getPerfValue())));
					}else {
						mapValue.put("discards", topoCurrentPerBean.getPerfValue());
					}
				} else if(topoCurrentPerBean.getKpiName().equals("OutDiscards"))
				{ 
					if(mapValue.containsKey("discards")){
						mapValue.put("discards",String.valueOf(Integer.valueOf(mapValue.get("discards"))+Integer.valueOf(topoCurrentPerBean.getPerfValue())));
					}else {
						mapValue.put("discards", topoCurrentPerBean.getPerfValue());
					}
				}else if(topoCurrentPerBean.getKpiName().equals("InErrors"))
				{
					if(mapValue.containsKey("errors")){
						mapValue.put("errors",String.valueOf(Integer.valueOf(mapValue.get("errors"))+Integer.valueOf(topoCurrentPerBean.getPerfValue())));
					}else {
						mapValue.put("errors", topoCurrentPerBean.getPerfValue());
					}
				} else if(topoCurrentPerBean.getKpiName().equals("OutErrors"))
				{
					if(mapValue.containsKey("errors")){
						mapValue.put("errors",String.valueOf(Integer.valueOf(mapValue.get("errors"))+Integer.valueOf(topoCurrentPerBean.getPerfValue())));
					}else {
						mapValue.put("errors",topoCurrentPerBean.getPerfValue());
					}
				}
			}
			 return mapValue;
		} catch (Exception e) {
			log.info(" topo链路告警错误!");
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	/**
	 * TOPO加载设备列表
	 * 
	 * @param ip地址段
	 * @return 返回设备JSON
	 *         参数map中,含有ipBegin,ipEnd,deviceFactory,status,isManager,deviceType
	 */
	@RequestMapping(value = "/getMODeviceList", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	public @ResponseBody
	TopoDeviceListObj getMODeviceList(@RequestBody Map<String, String> param) {
		try {
			int pageNo = 1;
			int pageSize = 10000;
			long startIP = 0;
			long endIP = 0;
			long tmpIP = 0;

			Map<String, Object> pageParam = new HashMap<String, Object>();
			Page<MODeviceObj> page = new Page<MODeviceObj>();
			page.setPageNo(pageNo);
			page.setPageSize(pageSize);

			String ipBegin = param.get("ipBegin");
			String ipEnd = param.get("ipEnd");
			String deviceFactory = param.get("deviceFactory");
			String deviceType = param.get("deviceType");

			if (ipBegin != null && !ipBegin.equals("")) {
				startIP = topoService.getIPByStr(ipBegin);
			}
			if (ipEnd != null && !ipEnd.equals("")) {
				endIP = topoService.getIPByStr(ipEnd);
			}

			if (deviceFactory != null && !deviceFactory.equals("")) {
				pageParam.put("deviceFactory", deviceFactory);
			}
			if (deviceType != null && !deviceType.equals("")) {
				pageParam.put("deviceType1", "(" + deviceType + ")");
			}

			page.setParams(pageParam);
			LinkedList<MODeviceObj> moList = moDeviceMapper.MoDeviceProInfoList2(page);
			TopoDeviceListObj topoJson = new TopoDeviceListObj();
			topoJson.setTotal(moList.size());

			// 初始化返回json
			List<Map<String, Object>> objDetail = new ArrayList<Map<String, Object>>();
			Map<String, Object> detail = new HashMap<String, Object>();

			for (MODeviceObj mo : moList) {
				tmpIP = 0;
				detail = new HashMap<String, Object>();
				detail.put("moID", mo.getMoid());
				detail.put("deviceName", mo.getMoname());
				detail.put("moAlias",mo.getMoalias());
				detail.put("domainId", mo.getDomainid());
				detail.put("domainName", mo.getDomainName());
				detail.put("manuFacturer", mo.getNemanufacturername());
				detail.put("deviceTypeID", mo.getDevicetype());
				detail.put("deviceTypeName", mo.getDevicetypeDescr());
				detail.put("deviceModelName", mo.getDevicemodelname());
				tmpIP = topoService.getIPByStr(mo.getDeviceip());
				if (startIP > 0 && tmpIP < startIP) {
					continue;
				}
				if (endIP > 0 && tmpIP > endIP) {
					continue;
				}
				detail.put("deviceIP", mo.getDeviceip());
				objDetail.add(detail);
			}
			log.info("objDetail.size=" + objDetail.size());
			topoJson.setRow(objDetail);
			return topoJson;
		} catch (Exception e) {
			log.info(" topo加载设备列表错误!");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * TOPO加载设备列表
	 * 
	 * @param ip地址段
	 * @return 返回设备JSON
	 *         参数map中,含有ipBegin,ipEnd,deviceFactory,status,isManager,deviceType
	 */
	@RequestMapping(value = "/getMONetworkIfList", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	public @ResponseBody
	TopoDeviceListObj getMONetworkIfList(@RequestBody Map<String, String> param) {
		try {
			int pageNo = 1;
			int pageSize = 10000;

			Map<String, Object> pageParam = new HashMap<String, Object>();
			Page<MONetworkIfBean> page = new Page<MONetworkIfBean>();
			page.setPageNo(pageNo);
			page.setPageSize(pageSize);

			String moID = param.get("moID");
			log.info("设备编号:" + moID);
			if (moID != null && !moID.equals("")) {
				pageParam.put("moID", moID);
			}
			pageParam.put("timeBegin", KPINameDef.queryBetweenTime().get("timeBegin"));
			pageParam.put("timeEnd", KPINameDef.queryBetweenTime().get("timeEnd"));
			page.setParams(pageParam);
			List<MONetworkIfBean> moList = moNetworkIfMapper.queryList(page);
			TopoDeviceListObj topoJson = new TopoDeviceListObj();
			topoJson.setTotal(moList.size());

			// 初始化返回json
			List<Map<String, Object>> objDetail = new ArrayList<Map<String, Object>>();
			Map<String, Object> detail = new HashMap<String, Object>();
			// 获得数据字典中视图级别名称
			Map<Integer, String> toMap = DictionaryLoader
					.getConstantItems("InterfaceType");
			for (MONetworkIfBean mo : moList) {
				detail = new HashMap<String, Object>();
				detail.put("operStatus", mo.getOperStatus());
				detail.put("moID", mo.getMoID());
				detail.put("ifName", mo.getIfName());
				detail.put("ifAlias", mo.getMoAlias());
				detail.put("ifDescr", mo.getIfDescr());
				detail.put("deviceIP", mo.getDeviceIP());
				detail.put("deviceMOName", mo.getDeviceMOName());
				if (mo.getIfSpeed() != null && !"".equals(mo.getIfSpeed())) {
					detail.put("ifSpeed",HostComm.getBytesToSpeed(Long.parseLong(mo.getIfSpeed())));
				}
				detail.put("ifDescr", mo.getIfDescr());
				detail.put("ifTypeName", toMap.get(Integer.parseInt(mo.getIfType())));
				detail.put("instance", mo.getInstance());
				objDetail.add(detail);
				 
			}
			log.info("objDetail.size=" + objDetail.size());
			topoJson.setRow(objDetail);
			return topoJson;
		} catch (Exception e) {
			log.info(" topo加载接口列表错误!");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * TOPO加载视图列表
	 * 
	 * @param viewID
	 *            :视图编号
	 * @param viewName
	 *            :视图名称
	 * @return 返回设备JSON
	 */
	@RequestMapping(value = "/getTopoViewList", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	public @ResponseBody
	List<Map<String, Object>> getTopoViewList(@RequestBody Map<String, String> paramMap) {
		try {
			List<TopoBean> topoViewList = topoService.loadTopoView(paramMap);
			List<Map<String, Object>> jsonList = new ArrayList<Map<String, Object>>();
			Map<String, Object> obj = null;

			for (TopoBean topoView : topoViewList) {
				obj = new HashMap<String, Object>();
				obj.put("viewID", topoView.getId());
				obj.put("viewName", topoView.getTopoName());
				obj.put("topoNodeNum", topoView.getTotalNodeNum());
				obj.put("topoLevel", topoView.getTopoLevel());
				obj.put("topoType", topoView.getTopoType());
				obj.put("description", topoView.getDescription());
				jsonList.add(obj);
			}
			return jsonList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Topo加载厂商列表
	 * 
	 * @param manufacturerID
	 *            ：厂商编号
	 * @param manufacturerName
	 *            :厂商名称
	 * @return 返回厂商JSON
	 */
	@RequestMapping(value = "/getManufacturerList", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	public @ResponseBody
	String getManufacturerList(@RequestBody Map<String, String> paramMap) {
		try {
			FlexiGridPageInfo flexiGridPageInfo = new FlexiGridPageInfo();
			flexiGridPageInfo.setRp(10000);

			String manufacturerIDStr = paramMap.get("manufacturerID");
			int manufacturerID = -1;
			if (!"".equals(manufacturerIDStr) && manufacturerIDStr != null) {
				manufacturerID = Integer.parseInt(manufacturerIDStr);
			}

			String manufacturerName = paramMap.get("manufacturerName");
			ManufacturerInfoBean manufacturerInfo = new ManufacturerInfoBean();
			manufacturerInfo.setResManuFacturerId(manufacturerID);
			manufacturerInfo.setResManuFacturerName(manufacturerName);

			List<ManufacturerInfoBean> manufacturerList = topoService.loadManufacturer(manufacturerInfo, flexiGridPageInfo);

			// 初始化返回json
			List<Map<String, Object>> manufactureDetail = new ArrayList<Map<String, Object>>();
			Map<String, Object> detail = new HashMap<String, Object>();

			for (ManufacturerInfoBean manu : manufacturerList) {
				detail = new HashMap<String, Object>();
				detail.put("manufacturerID", manu.getResManuFacturerId());
				detail.put("manufacturerName", manu.getResManuFacturerName());
				manufactureDetail.add(detail);
			}

			// 转换成json
			// log.info("返回的厂商json:"+AlarmObjToJSON.ObjectToJsonString(manufactureDetail));
			return AlarmObjToJSON.ObjectToJsonString(manufactureDetail);
		} catch (Exception e) {
			log.error(" topo加载厂商列表错误!");
		}
		return "";
	}

	/**
	 * Topo加载设备类型列表
	 * 
	 * @param classType
	 *            ：类型
	 * @return 返回设备类型JSON
	 */
	@RequestMapping(value = "/getMObjectDefList", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	public @ResponseBody
	String getMObjectDefList(@RequestBody String classType) {
		try {
			int pageNo = 1;
			int pageSize = 10000;
			Map<String, Object> pageParam = new HashMap<String, Object>();
			Page<MObjectDefBean> page = new Page<MObjectDefBean>();
			page.setPageNo(pageNo);
			page.setPageSize(pageSize);
			// 未添加预留类型过滤条件
			page.setParams(pageParam);
			List<MObjectDefBean> objectDefList = mobjectInfoMapper.selectMobject(page);
			// 初始化
			List<Map<String, Object>> objectDetail = new ArrayList<Map<String, Object>>();
			Map<String, Object> detail = new HashMap<String, Object>();

			for (MObjectDefBean def : objectDefList) {
				detail = new HashMap<String, Object>();
				detail.put("classID", def.getClassId());
				detail.put("className", def.getClassName());
				detail.put("classDisplayName", def.getClassLable());
				objectDetail.add(detail);
				
				// TODO 临时处理二层三层交换类型
				if(6 == def.getClassId()){
					detail = new HashMap<String, Object>();
					detail.put("classID", "59");
					detail.put("className", "SwitcherL2");
					detail.put("classDisplayName", "二层交换机");
					objectDetail.add(detail);	
					detail = new HashMap<String, Object>();
					detail.put("classID", "60");
					detail.put("className", "SwitcherL3");
					detail.put("classDisplayName", "三层交换机");
					objectDetail.add(detail);
				}
			}
			// 转换成json
			// log.info("返回的设备类型json:"+AlarmObjToJSON.ObjectToJsonString(objectDetail));
			return AlarmObjToJSON.ObjectToJsonString(objectDetail);
		} catch (Exception e) {
			log.error(" topo加载设备类型列表错误!");
		}
		return null;
	}

	/**
	 * 编辑topo列表
	 * 
	 * @param viewID
	 *            :视图编号
	 * @param topoName
	 *            :视图名称
	 * @param topoLevel
	 *            :视图级别
	 * @param description
	 *            :视图描述
	 * @param nodeNum
	 *            :节点数
	 * @param linkNum
	 *            :连接数
	 * @param operateType
	 *            :操作类型 1.保存 2.更新 3.删除
	 * @return 返回topo表JSON
	 */
	@RequestMapping(value = "/operateTopo", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	public @ResponseBody
	String operateTopo(@RequestBody Map<String, String> paramMap) {
		String viewIDStr = paramMap.get("viewID");
		String topoName = paramMap.get("topoName");
		String alarmLevelStr = paramMap.get("alarmLevel");
		String topoLevelStr = paramMap.get("topoLevel");
		String topoTypeStr = paramMap.get("topoType");
		String description = paramMap.get("description");
		String nodeNumStr = paramMap.get("nodeNum");
		String operateType = paramMap.get("operateType");

		TopoBean topo = new TopoBean();
		if (!"".equals(viewIDStr) && viewIDStr != null && !"3".equals(operateType)) {
			topo.setId(Integer.parseInt(viewIDStr));
		}
		topo.setIds(viewIDStr);
		topo.setTopoName(topoName);
		if (!"".equals(alarmLevelStr) && alarmLevelStr != null) {
			topo.setAlarmLevel(Integer.parseInt(alarmLevelStr));
		}
		if (!"".equals(topoLevelStr) && topoLevelStr != null) {
			topo.setTopoLevel(Integer.parseInt(topoLevelStr));
		}
		if (!"".equals(topoTypeStr) && topoTypeStr != null) {
			topo.setTopoType(Integer.parseInt(topoTypeStr));
		}
		topo.setDescription(description);
		if (!"".equals(nodeNumStr) && nodeNumStr != null) {
			topo.setTotalNodeNum(Integer.parseInt(nodeNumStr));
		}
		List<Map<String, Object>> topoDetail = topoService.operateTopo(topo, operateType);

		// 转换成json
		// log.info("返回的Json："+AlarmObjToJSON.ObjectToJsonString(topoDetail));
		return AlarmObjToJSON.ObjectToJsonString(topoDetail);
	}

	/**
	 * Topo加载设备连接
	 * 
	 * @param viewID
	 *            :视图编号
	 * @param topoName
	 *            :视图名称
	 * @param topoLevel
	 *            :视图级别
	 * @param description
	 *            :视图描述
	 * @param nodeNum
	 *            :节点数
	 * @param linkNum
	 *            :连接数
	 * @param operateType
	 *            :操作类型 1.保存 2.更新 3.删除
	 * @return 返回topo表JSON
	 */
	@RequestMapping(value = "/loadDeviceLink", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	public @ResponseBody
	List<Map<String, Object>> loadDeviceLink(@RequestBody Map<String, String> paramMap) {
		List<Map<String, Object>> linkList = new ArrayList<Map<String, Object>>();
		Map<String, Object> tmpMap = new HashMap<String, Object>();
		List<TopoLink> topoLink = topoService.getTopoLink(paramMap);
		for (TopoLink link : topoLink) {
			tmpMap = new HashMap<String, Object>();
			tmpMap.put("id", link.getId());
			tmpMap.put("sourceMOID", link.getSourceMOID());
			tmpMap.put("sourceMOClassID", link.getSourceMOClassID());
			tmpMap.put("sourcePort", link.getSourcePort());
			tmpMap.put("desMOID", link.getDesMOID());
			tmpMap.put("desMOClassID", link.getDesMOClassID());
			tmpMap.put("desPort", link.getDesPort());
			tmpMap.put("linkType", link.getLinkType());
			tmpMap.put("linkStatus", link.getLinkStatus());
			tmpMap.put("alarmLevel", link.getAlarmLevel());
			linkList.add(tmpMap);
		}
		return linkList;
	}

	/**
	 * 设备类型接口
	 * 
	 * @author zhengxh
	 * @return 返回集合数据
	 */
	@RequestMapping(value = "/modeviceTypes", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json; charset=utf-8")
	public @ResponseBody
	List<Map<String, Object>> modeviceTypes() {
		log.info("设备类型树形数据");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String ClassId = "2,3,4,5,6,7,8,9,138,127,136,135,137,134,133,132,118,117,106,62,59,60";// 仅展示的MOClassID
		List<MObjectDefBean> typeLst = mobjectInfoMapper.queryMObjectBySecondLevel(ClassId);
		if (typeLst != null) {
			for (int i = 0; i < typeLst.size(); i++) {
				MObjectDefBean vo = typeLst.get(i);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", vo.getRelationID());
				map.put("title", vo.getClassLable());
				map.put("parentId", vo.getNewParentID());
				map.put("classId", vo.getClassId());
				list.add(map);
			}
			return list;
		}
		return null;
	}

	/**
	 * 选择子网接口
	 * 
	 * @return 返回设备JSON
	 * @param param
	 *            含有ipAddress mask domain
	 */
	@RequestMapping(value = "/querySubNetList", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json; charset=utf-8")
	public @ResponseBody
	TopoDeviceListObj querySubNetList(@RequestBody Map<String, String> param) {
		try {
			// 查询列表数据
			log.info("子网列表数据");
			List<MOSubnetBean> list = topoService.querySubNetList(param);
			TopoDeviceListObj topoJson = new TopoDeviceListObj();
			topoJson.setTotal(list.size());

			List<Map<String, Object>> objDetail = new ArrayList<Map<String, Object>>();
			Map<String, Object> detail = null;

			for (MOSubnetBean mo : list) {
				detail = new HashMap<String, Object>();
				detail.put("id", mo.getMoId());
				detail.put("ipAddress", mo.getSubnetIp());
				detail.put("mask", mo.getSubnetMask());
				detail.put("domain", mo.getDomainId());
				detail.put("domainName", mo.getDomainDesc());
				detail.put("deviceNums", mo.getSubnetNum());
				objDetail.add(detail);
			}
			topoJson.setRow(objDetail);
			return topoJson;
		} catch (Exception e) {
			log.info("查询子网列表错误!");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 管理域接口
	 * 
	 * @author zhengxh
	 * @return 返回集合数据
	 */
	@RequestMapping(value = "/managedDomain", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json; charset=utf-8")
	public @ResponseBody
	List<Map<String, Object>> managedDomain() {
		log.info("管理域数据");
		List<ManagedDomain> list = this.managedDomainService.getManagedDomainTreeVal();

		List<Map<String, Object>> objDetail = new ArrayList<Map<String, Object>>();
		Map<String, Object> detail = null;

		if (list != null) {
			for (ManagedDomain mo : list) {
				detail = new HashMap<String, Object>();
				detail.put("id", mo.getDomainId());
				detail.put("name", mo.getDomainName());
				detail.put("parent", mo.getParentId());
				objDetail.add(detail);
			}
			return objDetail;
		}
		return null;
	}

	/**
	 * Topo选择设备类型过滤
	 * 
	 * @return 返回topo表JSON
	 */
	@RequestMapping(value = "/getDeviceType", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	public @ResponseBody
	List<Map<String, Object>> getDeviceType(@RequestBody Map<String, String> paramMap) {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(5, "路由器");
		map.put(6, "交换机");
		map.put(7, "物理主机");
		map.put(8, "虚拟宿主机");
		map.put(9, "虚拟机");
		List<Map<String, Object>> linkList = new ArrayList<Map<String, Object>>();
		Iterator iter = map.entrySet().iterator();
		Map<String, Object> tempMap = null;
		while (iter.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iter.next();
			tempMap = new LinkedHashMap<String, Object>();
			tempMap.put("id", entry.getKey());
			tempMap.put("value", entry.getValue());
			linkList.add(tempMap);
		}
		return linkList;
	}

	/**
	 * 子网下节点设备
	 * 
	 * @return 返回设备JSON
	 * @param param
	 *            含有moClassID subnetId
	 */
	@RequestMapping(value = "/querySubNetDeviceList", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json; charset=utf-8")
	public @ResponseBody
	TopoDeviceListObj querySubNetDeviceList(@RequestBody Map<String, String> param) {
		try {
			// 查询子网下节点设备
			log.info("子网下节点设备 数据");
			List<MODeviceObj> list = moDeviceMapper.querySubNetDeviceList(param);
			TopoDeviceListObj topoJson = new TopoDeviceListObj();
			topoJson.setTotal(list.size());

			List<Map<String, Object>> objDetail = new ArrayList<Map<String, Object>>();
			Map<String, Object> detail = null;

			for (MODeviceObj mo : list) {
				detail = new HashMap<String, Object>();
				detail.put("moID", mo.getMoid());
				detail.put("deviceIP", mo.getDeviceip());
				detail.put("deviceName", mo.getMoname());
				detail.put("domainId", mo.getDomainid());
				detail.put("domainName", mo.getDomainName());
				detail.put("manuFacturer", mo.getNemanufacturername());
				detail.put("deviceType", mo.getDevicetype());
				detail.put("className", mo.getDevicetypeDescr());
				detail.put("description", mo.getMoalias());
				detail.put("model", mo.getDevicemodelname());
				objDetail.add(detail);
			}
			topoJson.setRow(objDetail);
			return topoJson;
		} catch (Exception e) {
			log.info("查询子网下节点设备错误!");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据设备id查找设备 
	 * 
	 * @param userId
	 * @return 返回设备JSON
	 * 
	 */
	@RequestMapping(value = "/queryMODeviceByIds", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	@ResponseBody
	@SuppressWarnings("all")
	public List<MODeviceObj> queryMODeviceByIds(@RequestBody Map<String, String> param) {
		try {
			return moDeviceMapper.queryMODeviceByIds(param);
		} catch (Exception e) {
			log.info("拓扑根据设备id加载设备报错!");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据用户加载管理域下的设备列表
	 * 
	 * @param userId
	 *            用户ID moClassID 设备classId，逗号分割
	 * @return 返回设备JSON
	 * 
	 */
	@RequestMapping(value = "/queryMODeviceInDomainByUserId", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	@ResponseBody
	@SuppressWarnings("all")
	public List<Map<String, Object>> queryMODeviceInDomainByUserId(@RequestBody Map<String, String> param) {
		try {
			int userId = Integer.parseInt(param.get("userId"));
			// 所有的管理域
			List<ManagedDomain> dos = managedDomainService.getManagedDomainTreeVal();
			// 转换成树形结构
			Map<String, Tree> dms = new HashMap<String, Tree>();
			for (ManagedDomain md : dos) {
				Tree tree = new Tree();
				tree.setId(md.getDomainId().toString());
				tree.setObj(md);
				dms.put(tree.getId(), tree);
			}
			for (ManagedDomain md : dos) {
				if(dms.containsKey(md.getParentId())){
					dms.get(md.getParentId()).getChildren().add(dms.get(md.getDomainId().toString()));
					continue;
				}
			}
			// 转换成树形结构结束
			
			// 查找用户拥有的管理域
			List<String> owns = moDeviceMapper.queryDomainsByUserId(param);
			StringBuffer sb = new StringBuffer();
			for (String ss : owns) {
				sb.append(Tree.findChildren(dms.get(ss)));
			}
			if(sb.toString().endsWith(",")){
				sb.deleteCharAt(sb.length() - 1);
			}
			if(0 == sb.length()){
				return null;
			}
			param.put("domainID",  sb.toString());
			// 根据管理域，设备类型，查询设备
			List<MODeviceObj> mos = moDeviceMapper.queryDeviceByDomainsAndTypes(param);
			//
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (MODeviceObj mo : mos) {
				Map map = new HashMap<String, Object>();
				map.put("moID", mo.getMoid());
				map.put("deviceIP", mo.getDeviceip());
				map.put("deviceName", mo.getMoname());
				map.put("domainId", mo.getDomainid());
				map.put("domainName", mo.getDomainName());
				map.put("manuFacturer", mo.getNemanufacturername());
				map.put("deviceType", mo.getDevicetype());
				map.put("className", mo.getDevicetypeDescr());
				list.add(map);
			}
			return list;
		} catch (Exception e) {
			log.info(" topo根据用户加载管理域下的设备列表错误!");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 添加设备获得moid
	 * @param deviceIp 设备IP
	 */
	@RequestMapping(value = "/queryMODeviceMoId", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	@ResponseBody
	@SuppressWarnings("all")
	public int queryMODeviceMoId(@RequestBody Map<String, String> param) {
		String deviceIp = param.get("deviceIp");
		List<MODeviceObj> deviceLst = moDeviceMapper.getDeviceListByIP(deviceIp);
		
		//如果有对应的设备则返回该设备的moid
		if(deviceLst.size() > 0){
			return deviceLst.get(0).getMoid();
		}
		//如果没有，则返回-1，并进行发现
		else{
			int port = 161;
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest();
			SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
					.getSession().getAttribute("sysUserInfoBeanOfSession");
			int creator = sysUserInfoBeanTemp.getId().intValue();
			int classId = Integer.parseInt(param.get("classId"));
			MObjectDefBean mobjectDefBean = mobjectInfoMapper.getMobjectById(classId);
			String className = mobjectDefBean.getClassName();
			sysDBMSCommunityService.addDiscoverTask(deviceIp, creator, className, port,"");
			
			//返回-1
			return -1;
		}
	}
	
	/**
	 * 添加子网获得moid
	 * @param subnetIp 子网IP
	 */
	@RequestMapping(value = "/queryMosubnetMoId", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	@ResponseBody
	@SuppressWarnings("all")
	public int queryMosubnetMoId(@RequestBody Map<String, String> param) {
		String subnetIp = param.get("subnetIp");
		List<MOSubnetBean> subnetLst = topoService.getSubnetByIP(subnetIp);
		
		//如果有对应的子网则返回该子网的moid
		if(subnetLst.size() > 0){
			return subnetLst.get(0).getMoId();
		}
		//如果没有，则返回-1
		else{
			//返回-1
			return -1;
		}
	}
	
	/**
	 * 拓扑图列表页面显示数据
	 */
	@RequestMapping(value = "/listTopos", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	public @ResponseBody
	Map<String, Object> listTopos(@RequestBody Map<String, String> paramMap, HttpServletRequest request) {
		log.info("开始...获取页面显示数据");
		Page<TopoBean> page = new Page<TopoBean>();
		// 设置分页参数
		page.setPageNo(1);
		page.setPageSize(10);

		// 设置查询参数
		String topoName = paramMap.get("topoName");
		Map<String, Object> paramMap2 = new HashMap<String, Object>();
		if(!"".equals(topoName) && topoName != null){
			paramMap2.put("topoName", topoName);
		}else{
			paramMap2.put("topoName", "");
		}
		page.setParams(paramMap2);

		// 查询分页数据
		List<TopoBean> topoList = topoService.selectTopo(page);

		// 查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", topoList);

		log.info("结束...获取页面显示数据");
		return result;
	}
	
	/**
	 * 查看页面详情信息
	 */
	@RequestMapping(value = "/initTopoDetail", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	public @ResponseBody
	TopoBean initTopoDetail(@RequestBody Map<String, String> paramMap) {
		int id = Integer.parseInt(paramMap.get("id"));
		TopoBean topo = new TopoBean();
		topo = topoService.getTopoByID(id);
		try {
			if(!"".equals(topo.getAlarmLevelName()) && topo.getAlarmLevelName() != null){
				topo.setAlarmLevelName(java.net.URLEncoder.encode(topo.getAlarmLevelName(), "UTF-8"));
			}
			if(!"".equals(topo.getTopoName()) && topo.getTopoName() != null){
				topo.setTopoName(java.net.URLEncoder.encode(topo.getTopoName(), "UTF-8"));
			}
			if(!"".equals(topo.getLevelIcon()) && topo.getLevelIcon() != null){
				topo.setLevelIcon(java.net.URLEncoder.encode(topo.getLevelIcon(), "UTF-8"));
			}
			if(!"".equals(topo.getJsonFilePath()) && topo.getJsonFilePath() != null){
				topo.setJsonFilePath(java.net.URLEncoder.encode(topo.getJsonFilePath(), "UTF-8"));
			}
			if(!"".equals(topo.getTopoLevelName()) && topo.getTopoLevelName() != null){
				topo.setTopoLevelName(java.net.URLEncoder.encode(topo.getTopoLevelName(), "UTF-8"));
			}
			if(!"".equals(topo.getDescription()) && topo.getDescription() != null){
				topo.setDescription(java.net.URLEncoder.encode(topo.getDescription(), "UTF-8"));
			}
		} catch (UnsupportedEncodingException e) {
			log.error("数据加密异常："+e);
		}
		return topo;
	}
	
	/**
	 * 创建发现任务
	 */
	@RequestMapping(value = "/saveDiscoverParam", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	public @ResponseBody
	Map<String, Object> saveDiscoverParam(@RequestBody Map<String, String> paramMap,HttpServletRequest request,HttpServletResponse response) {
		SysNetworkDiscoverTask discover = new SysNetworkDiscoverTask();
		int way = Integer.parseInt(paramMap.get("way"));
		if (way == 1) {
			discover.setIpaddress1(paramMap.get("startIP1"));
			discover.setIpaddress2(paramMap.get("endIP1"));
		} else if (way == 2) {
			discover.setIpaddress1(paramMap.get("startIP2"));
			discover.setNetmask(paramMap.get("netMask2"));
		} else if (way == 3) {
			discover.setIpaddress1(paramMap.get("startIP3"));
			discover.setHops(Integer.parseInt(paramMap.get("step3")));
		}

		// 1:地址段 2:子网 3:路由表
		discover.setTasktype(way);
		discover.setWebipaddress(DispatcherUtils.getLocalAddress());
		discover.setCreator(Integer.parseInt(paramMap.get("creator")));
		discover.setCreatetime(new Date());
		discover.setProgressstatus(1);
		discover.setOperatestatus(1);
		Map<String, Object> result = new HashMap<String, Object>();
		// 保存
		try {
			sysNetworkDiscover.insert2(discover);
			log.info("保存任务结束 taskID=" + discover.getTaskid());
			result.put("taskID", discover.getTaskid());
			result.put("errorInfo", "");
			
			response.setContentType("application/json");
			// 返回任务编号
		} catch (Exception e) {
			response.setContentType("application/json");
			String errorInfo = "保存入库错误!";
			result.put("errorInfo", errorInfo);
		}

		log.info("发送消息通知发现程序!");
		// 发送消息给
		sendMeg();
		return result;
	}
	
	/**
	 * 发送消息给zookeeper,通知任务分发
	 */
	public static void sendMeg() {
		try {
			log.info("开始发送消息给zookeeper!");
			Dispatcher dispatcher = Dispatcher.getInstance();
			ManageFacade facade = dispatcher.getManageFacade();
			List<TaskDispatcherServer> servers = facade
					.listActiveServersOf(TaskDispatcherServer.class);
			if (servers.size() > 0) {
				String topic = "TaskDispatch";
				TaskDispatchNotification message = new TaskDispatchNotification();
				message.setDispatchTaskType(TaskType.TASK_DISCOVERY);
				facade.sendNotification(servers.get(0).getIp(), topic, message);
			}
		} catch (Exception e) {
			log.error("发送消息给zookeeper出错", e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 发现结果设备列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/listDiscoverDevice", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	public @ResponseBody
	Map<String, Object> listDiscoverDevice(@RequestBody Map<String, String> paramMap,HttpServletRequest request){
		String taskID = paramMap.get("taskID");
		int taskId = -1;
		String resourceTypes = paramMap.get("resourceTypes");
		String ClassNames ="";
		if(!"".equals(resourceTypes) && resourceTypes != null){
			String[] split = resourceTypes.split(",");
			for (int i = 0; i < split.length; i++) {
				ClassNames = ClassNames + "'" + split[i] +"',";
			}
			ClassNames = ClassNames.substring(0,ClassNames.lastIndexOf(","));
		}
		if(!"".equals(taskID) && taskID != null){
			taskId =Integer.parseInt(taskID);
		}
		
		List<MODeviceObj> listDevice = moDeviceMapper.listDeviceByConditions(taskId,ClassNames);
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("rows", listDevice);
		System.out.print("结束...获取页面显示数据");
		return result;
	}
	
	/**
	 * 校验topo名称是否重复 
	 * 
	 * @param topoName 拓扑名称；  id 拓扑id
	 * 
	 */
	@RequestMapping(value = "/checkTopoName", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	@ResponseBody
	@SuppressWarnings("all")
	public boolean checkTopoName(@RequestBody Map<String, String> param) {
		return topoService.checkTopoName(param);
	}
	
	
	public static void main(String[] args) {
		TopoInvokeRestController bean = new TopoInvokeRestController();
		Map<String, String> param = new HashMap<String, String>();
		param.put("deviceIp", "192.168.20.2");
		int result ;
		result = bean.queryMODeviceMoId(param);
		
	}
	
	/**
	 * 网络设备应用类型
	 * 
	 * @author caoj
	 * @return 返回集合数据
	 */
	@RequestMapping(value = "/applicationType", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json; charset=utf-8")
	public @ResponseBody
	String applicationType() {
		log.info("应用类型数据");
		List<Dict> list = dictService.getItemsById(dictService.getTypeBeanById("NetDeviceApplicationType").getConstantTypeId());

		List<Map<String, Object>> objDetail = new ArrayList<Map<String, Object>>();
		Map<String, Object> detail = null;

		if (list != null) {
			for (Dict dict : list) {
				detail = new HashMap<String, Object>();
				detail.put("constantTypeId", dict.getConstantTypeId());
				detail.put("constantItemId", dict.getConstantItemId());
				detail.put("constantItemName", dict.getConstantItemName());
				objDetail.add(detail);
			}
			return AlarmObjToJSON.ObjectToJsonString(objDetail);
		}
		return null;
	}
	
	/**
	 * TOPO加载设备列表
	 * 
	 * @param ip地址段
	 * @return 返回设备JSON
	 *         参数map中,含有ipBegin,ipEnd,deviceFactory,status,isManager,deviceType
	 */
	@RequestMapping(value = "/getMODeviceListByAppType", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	public @ResponseBody
	TopoDeviceListObj getMODeviceListByAppType(@RequestBody Map<String, String> param) {
		try {
			int pageNo = 1;
			int pageSize = 10000;
			long startIP = 0;
			long endIP = 0;
			long tmpIP = 0;

			Map<String, Object> pageParam = new HashMap<String, Object>();
			Page<MODeviceObj> page = new Page<MODeviceObj>();
			page.setPageNo(pageNo);
			page.setPageSize(pageSize);

			String ipBegin = param.get("ipBegin");
			String ipEnd = param.get("ipEnd");
			String deviceFactory = param.get("deviceFactory");
			String deviceType = param.get("deviceType");
			String appType = param.get("appType");

			if (ipBegin != null && !ipBegin.equals("")) {
				startIP = topoService.getIPByStr(ipBegin);
			}
			if (ipEnd != null && !ipEnd.equals("")) {
				endIP = topoService.getIPByStr(ipEnd);
			}

			if (deviceFactory != null && !deviceFactory.equals("")) {
				pageParam.put("deviceFactory", deviceFactory);
			}
			if (deviceType != null && !deviceType.equals("")) {
				pageParam.put("deviceType1", "(" + deviceType + ")");
			}
			if (appType != null && !appType.equals("")) {
				pageParam.put("appType", appType);
			}
			page.setParams(pageParam);
			LinkedList<MODeviceObj> moList = moDeviceMapper.getMoDeviceListByAppType(page);
			TopoDeviceListObj topoJson = new TopoDeviceListObj();
			topoJson.setTotal(moList.size());

			// 初始化返回json
			List<Map<String, Object>> objDetail = new ArrayList<Map<String, Object>>();
			Map<String, Object> detail = new HashMap<String, Object>();

			for (MODeviceObj mo : moList) {
				if(null == mo){
					continue;
				}
				tmpIP = 0;
				detail = new HashMap<String, Object>();
				detail.put("moID", mo.getMoid());
				detail.put("deviceName", mo.getMoname());
				detail.put("domainId", mo.getDomainid());
				detail.put("domainName", mo.getDomainName());
				detail.put("manuFacturer", mo.getNemanufacturername());
				detail.put("deviceTypeID", mo.getDevicetype());
				detail.put("deviceTypeName", mo.getDevicetypeDescr());
				detail.put("deviceModelName", mo.getDevicemodelname());
				tmpIP = topoService.getIPByStr(mo.getDeviceip());
				if (startIP > 0 && tmpIP < startIP) {
					continue;
				}
				if (endIP > 0 && tmpIP > endIP) {
					continue;
				}
				detail.put("deviceIP", mo.getDeviceip());
				objDetail.add(detail);
			}
			log.info("objDetail.size=" + objDetail.size());
			topoJson.setRow(objDetail);
			return topoJson;
		} catch (Exception e) {
			log.info(" topo加载设备列表错误!");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 选择应用站点的列表数据
	 */
	@RequestMapping(value = "/getSiteList", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	public @ResponseBody
	List<Map<String, Object>> getSiteList(@RequestBody Map<String, String> param) {
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("siteName", param.get("siteName"));
			paramMap.put("siteType", param.get("siteType"));
			paramMap.put("responseTimeKPI", WebSiteKPINameDef.RESPONSETIME);
			paramMap.put("dnsStateKPI", WebSiteKPINameDef.DNSSTATE);
			paramMap.put("connsStateKPI", WebSiteKPINameDef.CONNSSTATE);
			paramMap.put("loginStateKPI", WebSiteKPINameDef.LOGINSTATE);
			paramMap.put("portStateKPI", WebSiteKPINameDef.PORTSTATE);
			paramMap.put("jobSiteFTP", KPINameDef.JOBSITEFTP);
			paramMap.put("jobSiteHTTP", KPINameDef.JOBSITEHTTP);
			paramMap.put("jobSiteDNS", KPINameDef.JOBSITEDNS);
			paramMap.put("jobSiteTCP", KPINameDef.JOBSITETCP);
			List<WebSite> siteList = webSiteService.getAllSites(paramMap);
			Map<String, Object> obj = null;
			List<Map<String, Object>> jsonList = new ArrayList<Map<String, Object>>();
			for (WebSite site : siteList) {
				obj = new HashMap<String, Object>();
				obj.put("moID", site.getMoID());
				obj.put("siteType", site.getSiteType());
				obj.put("siteName", site.getSiteName());
				obj.put("siteAddr", site.getSiteAddr());
				jsonList.add(obj);
			}
			return jsonList;
		} catch (Exception e) {
			log.error("选择站点列表错误!",e);
		}
		return null;
	}
	
	/**
	 * 选择中间件的列表数据
	 */
	@RequestMapping(value = "/getMiddleWareList", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	public @ResponseBody
	List<Map<String, Object>> getMiddleWareList(@RequestBody Map<String, String> param) {
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("moClassId", param.get("moClassId"));
			paramMap.put("ip", param.get("ip"));
			Map<String, Object> obj = null;
			List<MOMiddleWareJMXBean> middleWareLst = middlewareService.getListByCondition(paramMap);
			List<Map<String, Object>> jsonList = new ArrayList<Map<String, Object>>();
			for (MOMiddleWareJMXBean jmx : middleWareLst) {
				obj = new HashMap<String, Object>();
				obj.put("moID", jmx.getMoId());
				obj.put("ip", jmx.getIp());
				obj.put("port", jmx.getPort());
				obj.put("alias", jmx.getMoalias());
				obj.put("className", jmx.getJmxType());
				jsonList.add(obj);
			}
			return jsonList;
		} catch (Exception e) {
			log.error("选择中间件列表错误!",e);
		}
		return null;
	}
	
	/**
	 * 数据库类型接口
	 * 
	 * @return 返回集合数据
	 */
	@RequestMapping(value = "/databaseTypes", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json; charset=utf-8")
	public @ResponseBody
	List<Map<String, Object>> databaseTypes() {
		log.info("数据库类型树形数据");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String ClassId = "15,26,16,28,54,55,56,81,82,86,87";// 仅展示的MOClassID
		List<MObjectDefBean> typeLst = mobjectInfoMapper.queryMObjectBySecondLevel(ClassId);
		if (typeLst != null) {
			for (int i = 0; i < typeLst.size(); i++) {
				MObjectDefBean vo = typeLst.get(i);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", vo.getRelationID());
				map.put("title", vo.getClassLable());
				map.put("parentId", vo.getNewParentID());
				map.put("classId", vo.getClassId());
				list.add(map);
			}
			return list;
		}
		return null;
	}
	
	/**
	 * TOPO加载数据库列表
	 * 
	 * @param ip,classId
	 * @return 返回设备JSON
	 */
	@RequestMapping(value = "/getDataBase", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	public @ResponseBody
	List<Map<String, Object>> getDataBase(@RequestBody Map<String, String> param) {
		try {
			String ip = param.get("ip");
			String deviceType = param.get("classId");
			int classId = -1;
			Map<String, Object> pageParam = new HashMap<String, Object>();
			if (ip != null && !"".equals(ip)) {
				pageParam.put("ip", ip);
			}
			if (deviceType != null && !deviceType.equals("")) {
				classId = Integer.parseInt(deviceType);
			}
			
			List<Map<String, Object>> objDetail = topoService.getDbList(classId, pageParam);
			log.info("objDetail.size=" + objDetail.size());
			return objDetail;
		} catch (Exception e) {
			log.info(" topo加载数据库列表错误!");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据db2实例获得所有的数据库
	 */
	@RequestMapping(value = "/getDB2InfoMoIds", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	public @ResponseBody
	String getDB2InfoMoIds(@RequestBody Map<String, String> param) {
		try {
			String ids = "";
			String instanceMoIdStr = param.get("instanceMoId");
			int instanceMoId = -1;
			if (instanceMoIdStr != null && !"".equals(instanceMoIdStr)) {
				instanceMoId = Integer.parseInt(instanceMoIdStr);
				ids = topoService.getDB2InfoMoIds(instanceMoId);
			}
			
			return ids;
		} catch (Exception e) {
			log.error(" 根据db2实例获得下面所有的数据库错误!",e);
		}
		return "";
	}
}



class Tree {
	private String id;
	private Object obj;
	private List<Tree> children;

	public static String findChildren(Tree node){
		if(node.children.size() == 0){
			return node.getId() + ",";
		}
		String temp = node.getId() + ",";
		for (Tree n : node.getChildren()) {
			temp += findChildren(n);
		}
		return temp;
	}
	
	public Tree() {
		super();
		children = new ArrayList<Tree>();
	}

	public final String getId() {
		return id;
	}

	public final void setId(String id) {
		this.id = id;
	}

	public final Object getObj() {
		return obj;
	}

	public final void setObj(Object obj) {
		this.obj = obj;
	}

	public final List<Tree> getChildren() {
		return children;
	}
}
