package com.fable.insightview.monitor.deleteMonitorObject.service;

import java.util.List;
import java.util.Map;

import com.fable.insightview.monitor.deleteMonitorObject.entity.ServiceBean;

public interface DeviceService {

	 /**
	  * 查询设备上数据库服务
	  * @param map
	  * @return
	  */
	 List<ServiceBean> queryDBServices(Map<String, Object> map);
	 
	 /**
	  * 查询设备上部署的虚拟机
	  * @param map
	  * @return
	  */
	 List<ServiceBean> queryVmServices(Map<String, Object> map);
	 
	 
	 /**
	  * 查询设备上部署的虚拟宿主机
	  * @param map
	  * @return
	  */
	 List<ServiceBean> queryVhostServices(Map<String, Object> map);
	 
	 /**
	  * 查询虚拟机moid
	  * @param map
	  * @return
	  */
	 List<Integer> queryVMID(Map<String, Object> map);
	 
	 /**
	  * 查询动环系统的服务
	  * @param map
	  * @return
	  */
	 List<ServiceBean> queryZoneMagerServie(Map<String, Object> map);
	 
	 /**
	  * 查询设备ip
	  * @param map
	  * @return
	  */
	 List<ServiceBean> querydeviceIP(Map<String, Object> map);
	 
	 /**
	  * 查询设备虚拟机ip
	  * @param map
	  * @return
	  */
	 List<String> queryVMIP(Map<String, Object> map);
 }
