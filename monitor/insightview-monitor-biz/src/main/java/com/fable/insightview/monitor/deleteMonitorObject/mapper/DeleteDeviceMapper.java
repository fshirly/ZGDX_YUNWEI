package com.fable.insightview.monitor.deleteMonitorObject.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.monitor.deleteMonitorObject.entity.RelationBean;
import com.fable.insightview.monitor.deleteMonitorObject.entity.ServiceBean;

public interface DeleteDeviceMapper {

	/**
	 * 查询子对象的MOID
	 * @param map
	 * @return
	 */
	List<Integer> queryChildMOID(Map<String, Object> map);
	/**
	 * 删除该设备
	 * @param MOID
	 * @return
	 */
	 int deleteMOVolumes(Map<String, Object> map);
	/**
	 * 删除该设备
	 * @param MOID
	 * @return
	 */
	 int deleteMODevice(Map<String, Object> map);
	 /**
	  * 删除设备性能数据
	  * @param map
	  * @return
	  */
	 int deletePrefData(Map<String, Object> map);
	 
	 /**
	  * 删除性能快照、子对象
	  * @param map
	  * @return
	  */
	 int deletePrefSnapshotAndChild(Map<String, Object> map);
	 /**
	  * 删除设备告警数据
	  * @param map
	  * @return
	  */
	 int deleteAlarmDetail(Map<String, Object> map);
	 /**
	  * 删除该设备IP信息、性能任务表
	  * @param map
	  * @return
	  */
	  int deleteTaskAndIP(Map<String, Object> map);
	  /**
	   * 删除凭证
	   * @param map
	   * @return
	   */
	  int deleteSNMP (Map<String, Object> map);
	  
	  /***
	   * 删除虚拟宿主机、虚拟机的凭证
	   * @param map
	   * @return
	   */
	  int deleteVhostAndVMSNMP (Map<String, Object> map);
	  
	  
	 /**
	  * 查询该设备是否有采集任务
	  * @param map
	  * @return
	  */
	 List<RelationBean> queryTaskStatus(Map<String, Object> map);
	 
	 /**
	  * 查询站点的采集任务
	  * @param map
	  * @return
	  */
	 List<RelationBean> queryWebSiteTask(Map<String, Object> map);
	 /**
	  * 删除告警通知策略、告警视图定义、自动派单设置
	  * @param map
	  * @return
	  */
	 int deleteAlarmFilter(Map<String, Object> map);
	 
	 /**
	  * 查询维护期策略
	  * @param map
	  * @return
	  */
	 List<RelationBean> queryAlarmMaintenancePolicy(Map<String, Object> map);
	 
	 /**
	  * 为删除SysNetworkDiscoverTask查询设备的信息
	  * @param map
	  * @return
	  */
	 List<ServiceBean> queryDeviceInfo(Map<String, Object> map);
	 
	int deleteDiscoverTask(Map<String, Object> map);
	
	/**
	 * 删除监测器模板
	 * @param map
	 * @return
	 */
	int deleteTemplate(Map<String,Object> map); 
	
	/**
	 * 查询斯特纽的子对象的MOID
	 * @param map
	 * @return
	 */
	List<Integer> queryStoneuChildMOID(Map<String, Object> map);
	/**
	 * 删除斯特纽子对象
	 * @return
	 */
	int deleteStoneuModevice(@Param("MOID")Integer moid);
	/**
	 * 删除斯特纽子对象的性能数据 
	 * @param moid 
	 * @return
	 */
	int deletePerfStoneuModevice(@Param("MOID")Integer moid);
}
