package com.fable.insightview.monitor.deleteMonitorObject;

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

import com.fable.insightview.monitor.alarmmgr.alarmactive.mapper.AlarmActiveMapper;
import com.fable.insightview.monitor.deleteMonitorObject.entity.RelationBean;
import com.fable.insightview.monitor.deleteMonitorObject.entity.ServiceBean;
import com.fable.insightview.monitor.deleteMonitorObject.mapper.DeleteDeviceMapper;
import com.fable.insightview.monitor.deleteMonitorObject.service.DeleteMonitorObject;
import com.fable.insightview.monitor.deleteMonitorObject.service.DeviceService;
import com.fable.insightview.monitor.deleteMonitorObject.service.Queue;
import com.fable.insightview.platform.core.util.BeanLoader;

@Controller
@RequestMapping("/monitor/deleteMonitorObject")
public class DeleteMonitorObjectController {
	private final Logger logger = LoggerFactory.getLogger(DeleteMonitorObjectController.class);
	@Autowired
	private AlarmActiveMapper alarmActiveMapper;
	@Autowired
	DeviceService deviceService;
	@Autowired
	DeleteDeviceMapper deviceMapper;
	/**
	 * 删除设备对象
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteMonitor")
	@ResponseBody
	public boolean deleteMonitor(HttpServletRequest request){
		String value = request.getParameter("MOID");
		String [] arry =value.split(",");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, List<String>> tempMap = new HashMap<String, List<String>>();
	 	for(int i =0;i<arry.length;i++){
	 		String[] moIdArray = arry[i].split("_");
	 		if (tempMap.containsKey(moIdArray[1])) {
	 			tempMap.get(moIdArray[1]).add(moIdArray[0]);
	 		}else{
	 			List<String> ids = new ArrayList<String>();
	 			ids.add(moIdArray[0]);
	 			tempMap.put(moIdArray[1],ids);
	 		}
	 	}
		DeleteMonitorObject delObject = null;
		boolean  obj=  false;
		Map<DeleteMonitorObject, Map<String, Object>> result = new HashMap<DeleteMonitorObject, Map<String, Object>>();
		for (String key : tempMap.keySet()) {
			String MOID = tempMap.get(key).toString().replace("[", "").replace("]", "");
			paramMap = new HashMap<String, Object>();
			paramMap.put("MOID", MOID);
			// 5-路由器,6 -交换机,59-二层交换机,60-三层交换机
			if("5".equals(key) || "6".equals(key) || "59".equals(key) || "60".equals(key)) {
				key = "netWork";
			}
			if("62".equals(key)||"117".equals(key)||"118".equals(key)){
				key = "safeService";
			}
			if("132".equals(key)||"133".equals(key) || "134".equals(key)|| "135".equals(key)|| "136".equals(key)|| "137".equals(key)|| "138".equals(key)) {
				key = "7";
			}
			// 根据相对应的监测对象去删除具体的监测对象
			delObject = (DeleteMonitorObject)BeanLoader.getBean("common_"+key);
			obj = delObject.deleteData(paramMap);
			/**
			 *  因为网络设备都在deleteNetWork中，那么需要将对应的数据都传入
			 * 	故用Map<DeleteMonitorObject,Map<String, Object>>将同一类型的设备，
			 * 	构造在一起将MOID都传入
			 */
			if(result.containsKey(delObject)){
				result.get(delObject).put("MOID", MOID+","+result.get(delObject).get("MOID"));
			}else{
				result.put(delObject, paramMap);
			}
			// 删除告警统计
			alarmActiveMapper.clearMOActiveAlarmStInfo();
			synchronized (this) {
				alarmActiveMapper.alarmDeviceStatisBySourceMOID();
				alarmActiveMapper.alarmZoomStatisByMOID();
			}
		}
		try {
			Queue.writeQueue(result);
		} catch (InterruptedException e) {
			logger.error("监测设备性能、告警数据写入队列失败", e.getMessage());
		}
		return  obj;
	}
	
	
	/**
	 * 查询设备上部署的服务（如数据库、中间件、虚拟机）
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryDeviceServie")
	@ResponseBody
	public List<ServiceBean> queryService(HttpServletRequest request){
		String deviceIPs = request.getParameter("deviceIP");
		String vcentIP =request.getParameter("vcentIP");
		String moid = request.getParameter("moid");
		Map<String, Object>  paramMap=  new HashMap<String, Object>();
		List<ServiceBean> deviceServicesInfo =new ArrayList<ServiceBean>();
		List<ServiceBean> vmServicesInfo =new ArrayList<ServiceBean>();
		List<ServiceBean> vhostServicesInfo =new ArrayList<ServiceBean>();
		List<ServiceBean> list = new ArrayList<ServiceBean>();
		List<String>  ipLists = new ArrayList<String>();
		
		if(vcentIP ==null || "".equals(vcentIP)){
			paramMap.put("moid", moid);
			 // 查询虚拟机的IP
			List<String> vmips = deviceService.queryVMIP(paramMap);
			paramMap.put("deviceIP", deviceIPs);
			String vmip="";
		
			for(int j=0;j<vmips.size();j++){
				vmip = "'"+vmips.get(j)+"'";
				ipLists.add(vmip);
			}
			String deviceipArray[] = deviceIPs.split(",");
			for(int i=0;i<deviceipArray.length;i++){
				ipLists.add(deviceipArray[i]);
			}
			// 查询虚拟机
			vmServicesInfo =  deviceService.queryVmServices(paramMap);
			list.addAll(vmServicesInfo);
		}else{
			// 查询vcent下的虚拟宿主机MOID以便查询下面的虚拟机的信息
			paramMap.put("vcentIP", vcentIP);
			List<Integer> vmMOID = deviceService.queryVMID(paramMap);
			String vmoid = vmMOID.toString().replace("[", "").replace("]", "");
			paramMap.put("vmoid", vmoid);
			// 查询vcent中宿主机下的虚拟机
			paramMap.put("moid", vmoid);
			vmServicesInfo =  deviceService.queryVmServices(paramMap);
			list.addAll(vmServicesInfo);
			String vcentChildVMips="";
		 
			for (ServiceBean deviceService : vmServicesInfo) {
				// 查询虚拟机的IP以便查询该设备上的数据库、中间件
				vcentChildVMips = "'"+deviceService.getIp()+"'";
				ipLists.add(vcentChildVMips);
			}
			 // 查询虚拟宿主机信息
			vhostServicesInfo =  deviceService.queryVhostServices(paramMap);
			list.addAll(vhostServicesInfo);
		}
		// 根据设备IP查询该设备上的数据库、中间件
		for(int j=0;j<ipLists.size();j++){
			paramMap.put("deviceIP", ipLists.get(j));
			// 查询数据库、中间件
			deviceServicesInfo =  deviceService.queryDBServices(paramMap);
			list.addAll(deviceServicesInfo);
		}
		
		return list;
	}
	
	/**
	 * 查询设备是否有正在采集的任务
	 * @param map
	 * @return
	 */
	@RequestMapping("/checkTask")
	@ResponseBody
	public List<RelationBean> queryTaskStatus(HttpServletRequest request) {
		String webStieMOID = request.getParameter("MOID");
		String [] arry =webStieMOID.split(",");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> AlarmMaintenancePolicyMap = new HashMap<String, Object>();
		Map<String, List<String>> tempMap = new HashMap<String, List<String>>();
	 	for(int i =0;i<arry.length;i++){
	 		String[] moIdArray = arry[i].split("_");
	 		if (tempMap.containsKey(moIdArray[1])) {
	 			tempMap.get(moIdArray[1]).add(moIdArray[0]);
	 		}else{
	 			List<String> ids = new ArrayList<String>();
	 			ids.add(moIdArray[0]);
	 			tempMap.put(moIdArray[1],ids);
	 		}
	 	}
	 	List<RelationBean> prefPollTask = new ArrayList<RelationBean>();
	 	List<RelationBean> MaintenancePolicyList = new ArrayList<RelationBean>();
	 	List<RelationBean> list = new ArrayList<RelationBean>();
	 	for(String prefKey : tempMap.keySet()){
	 		 //查询主机设备和网络设备的采集任务 (-1-未知,7-物理主机,8-虚拟宿主机,9-虚拟机 ,5-路由器,6 -交换机,59-二层交换机,60-三层交换机,75-Venter,96-空调，73-UPS)
	 		if(("8".equals(prefKey)) ||("9".equals(prefKey)) || ("7".equals(prefKey))||("-1".equals(prefKey)) 
	 				|| ("5".equals(prefKey)) || ("6".equals(prefKey))|| ("59".equals(prefKey)) || ("60".equals(prefKey)) 
	 				|| ("75".equals(prefKey)) || ("96".equals(prefKey)) || ("73".equals(prefKey)) || ("109".equals(prefKey))
	 				|| ("133".equals(prefKey))||("134".equals(prefKey))|| ("135".equals(prefKey))||("137".equals(prefKey))
	 				||("136".equals(prefKey))||("132".equals(prefKey)) || ("138".equals(prefKey)) ){
	 			paramMap.put("tableName", "MODevice");
	 			paramMap.put("result", "mo.DeviceIP");
	 			// 查询维护期策略
	 			AlarmMaintenancePolicyMap.put("type", "维护期策略");
	 		}
	 		// 查询数据库的采集任务(15-Oracle,16-Mysql,54-DB2,81-Sybase,86-MsSql)
	 		else if(("15".equals(prefKey))|| ("16".equals(prefKey)) || ("54".equals(prefKey)) 
	 				|| ("81".equals(prefKey)) || ("86".equals(prefKey))){
	 			paramMap.put("tableName", "MODBMSServer");
	 			paramMap.put("result", "mo.IP");
	 		}
	 		// 查询中间件的采集任务(19 -Websphere ,20-Tomcat,53-Weblogic)
	 		else if(("20".equals(prefKey)) || ("53".equals(prefKey)) || ("19".equals(prefKey))){
	 			paramMap.put("tableName", "MOMiddleWareJMX");
	 			paramMap.put("result", "mo.IP");
	 		}
	 		// 查询动环系统的采集任务
	 		else if("44".equals(prefKey)){
	 			paramMap.put("tableName", "MOZoneManager");
	 			paramMap.put("result", "mo.IPAddress");
	 		}
	 		 // 查询阅读器的采集任务
	 		else if("45".equals(prefKey)){
	 			paramMap.put("tableName", "MOReader");
	 			paramMap.put("result", "mo.IPAddress");
	 		}
	 		// 判断站点采集任务
	 		else if("1".equals(prefKey)){
	 			paramMap.put("tableName", "MOSiteFtp");
	 			paramMap.put("result", "mo.SiteName");
	 		}else if("2".equals(prefKey)){
	 			paramMap.put("tableName", "MOSiteDns");
	 			paramMap.put("result", "mo.SiteName");
	 		}else if("3".equals(prefKey)){
	 			paramMap.put("tableName", "MOSiteHttp");
	 			paramMap.put("result", "mo.SiteName");
	 		}else if("4".equals(prefKey)){
	 			paramMap.put("tableName", "MOSitePort");
	 			paramMap.put("result", "mo.SiteName");
	 		}//查询安全设备的采集任务 防火墙，光闸，VPN
	 		else if("106".equals(prefKey)||"62".equals(prefKey)|| ("117".equals(prefKey)) || ("118".equals(prefKey)) ){
	 			paramMap.put("tableName", "MOSecurityApplianceInfo");
	 			paramMap.put("result", "mo.DeviceID");
	 		}
	 		paramMap.put("type", "采集任务");
	 		paramMap.put("MOID", tempMap.get(prefKey).toString().replace("[", "").replace("]", ""));
	 		prefPollTask =  deviceMapper.queryWebSiteTask(paramMap);
	 		list.addAll(prefPollTask);
	 		
	 		if(AlarmMaintenancePolicyMap.containsKey("type")){
	 			AlarmMaintenancePolicyMap.put("MOID", tempMap.get(prefKey).toString().replace("[", "").replace("]", ""));
	 			MaintenancePolicyList = deviceMapper.queryAlarmMaintenancePolicy(AlarmMaintenancePolicyMap);
	 			list.addAll(MaintenancePolicyList);
	 		}
	 	}
		return list;
	}
	
 
	/***
	 * 查询动环系统的服务
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryZoneMagerServie")
	@ResponseBody
	public List<ServiceBean> queryZoneMagerServie(HttpServletRequest request){
		String MOID = request.getParameter("MOID");
		Map<String, Object>  paramMap=  new HashMap<String, Object>();
		paramMap.put("MOID", MOID);
		List<ServiceBean> deviceServicesInfo =  deviceService.queryZoneMagerServie(paramMap);
		return deviceServicesInfo;
	}
}
