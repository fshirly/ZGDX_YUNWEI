package com.fable.insightview.monitor.deleteMonitorObject.mapper;

import java.util.List;
import java.util.Map;

import com.fable.insightview.monitor.deleteMonitorObject.entity.ServiceBean;
import com.fable.insightview.monitor.perf.entity.PerfPollTaskBean;

public interface DeleteDBserviceMapper {

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
	 int deleteMODevice(Map<String, Object> map);
	 /**
	  * 删除设备性能数据
	  * @param map
	  * @return
	  */
	 int deletePrefData(Map<String, Object> map);
	 
	 /**
	  * 删除性能快照
	  * @param map
	  * @return
	  */
	 int deletePrefSnapshot(Map<String, Object> map);
	 
	 /***
	  * 删除子对象
	  * @param map
	  * @return
	  */
	 int deleteChildDevice(Map<String, Object> map);
	 /**
	  * 删除设备告警数据
	  * @param map
	  * @return
	  */
	 int deleteAlarmDetail(Map<String, Object> map);
	 /**
	  * 删除该设备信息、性能任务表
	  * @param map
	  * @return
	  */
	  int deleteTaskAndvars(Map<String, Object> map);
	  /**
	   * 删除凭证
	   * @param map
	   * @return
	   */
	  int deleteSNMP (Map<String, Object> map);
	  
	 /**
	  * 查询该设备是否有采集任务
	  * @param map
	  * @return
	  */
	 List<PerfPollTaskBean> queryTaskStatus(Map<String, Object> map);
	 
	 /**
	  * 查询站点的采集任务
	  * @param map
	  * @return
	  */
	 List<PerfPollTaskBean> queryWebSiteTask(Map<String, Object> map);
	 /**
	  * 删除告警通知策略、告警视图定义、自动派单设置
	  * @param map
	  * @return
	  */
	 int deleteAlarmFilter(Map<String, Object> map);
	 
	 	/**
		 * 删除DBMS该设备
		 * @param MOID
		 * @return
		 */
		 int deleteDBMSservice(Map<String, Object> map);
		 
		 
		 /**
			 * 删除监测器模板
			 * @param map
			 * @return
			 */
			int deleteTemplate(Map<String,Object> map); 
		 
		 
		 
		 List<Integer> queryOracleChlidMOID (Map<String, Object> map);
		 
		 /**
		  * 查询oracleDB的MOID
		  * @param map
		  * @return
		  */
		 List<Integer> queryDBMOID(Map<String, Object> map);
		 
		 /**
		  * 查询MOOracleInstance的InstanceID
		  * @param map
		  * @return
		  */
		 List<Integer> queryInstanceID(Map<String, Object> map);
 		 
		 
		 /**
			 * 查询oracleSGA的moid
			 * @param map
			 * @return
			 */
			List<Integer> querySGAMOID(Map<String, Object> map);
		 
			/**
			 * 删除oracle的子对象或者DB2的子对象
			 * @param map
			 * @return
			 */
			 int deleteOralceOrDB2Child(Map<String, Object> map);
			 /**
			  * 删除oracle子对象SGA或者MODB2Database
			  * @param map
			  * @return
			  */
			 int deleteSGAOrDB2(Map<String, Object> map);
			 /***
			  * SGA性能数据
			  * @param map
			  * @return
			  */
			 int deleteSGApref (Map<String, Object> map);
			 /**
			  * 删除oracleDB表
			  * @param map
			  * @return
			  */
			 int deleteOracleDB(Map<String, Object> map);
			 
			
				List<Integer> queryDB2DataBaseMOID(Map<String, Object> map);
				/**
				 * 为删除SysNetworkDiscoverTask查询数据库信息
				 * @param map
				 * @return
				 */
			List<ServiceBean> queryDBInfo(Map<String, Object> map);
			
			int  deleteDiscoverDBInfo(Map<String, Object> map);
}
