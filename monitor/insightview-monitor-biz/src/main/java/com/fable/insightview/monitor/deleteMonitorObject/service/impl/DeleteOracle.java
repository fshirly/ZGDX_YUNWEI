package com.fable.insightview.monitor.deleteMonitorObject.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.deleteMonitorObject.entity.ServiceBean;
import com.fable.insightview.monitor.deleteMonitorObject.mapper.DeleteDBserviceMapper;
import com.fable.insightview.monitor.deleteMonitorObject.service.IDelCommon;

@Service("common_15")
public class DeleteOracle extends DeleteObjectServiceImpl {

	@Autowired
	DeleteDBserviceMapper dbserviceMapper;
	@Autowired
	IDelCommon delCommon;
	
	private static Map<String, Object> temp = new HashMap<String, Object>();

	@Override
	public boolean delete(Map<String, Object> map) {

		Map<String, Object> parm;
		Object MOID = null;
		for (String moid : map.keySet()) {
			MOID = map.get(moid);
		}
		// 删除SysNetworkDiscoverTask表
		parm = new HashMap<String, Object>();
		parm.put("MOID", MOID);
		List<ServiceBean> DbList = dbserviceMapper.queryDBInfo(parm);
		for (ServiceBean device : DbList) {
			parm.put("MOName", device.getMOName());
			parm.put("IP", device.getIp());
			dbserviceMapper.deleteDiscoverDBInfo(parm);
		}
		
		//删除告警重定义
		delCommon.delAlarmEventRedefine(MOID.toString());

		// 获取oracleDB的MOID,以便去删除性能数据
		String dbmoids = "";
		List<Integer> dbMoid = dbserviceMapper.queryDBMOID(map);
		if (dbMoid.size() > 0) {
			dbmoids = dbMoid.toString().replace("[", "").replace("]", "");
		}

		if (dbmoids != null && !"".equals(dbmoids)) {
			// 查询子对象的MOID
			String moids = "";
			Map<String, Object> childMOIDMap = new HashMap<String, Object>();
			childMOIDMap.put("MOOracleTBS", dbmoids);
			childMOIDMap.put("MOOracleDataFile", dbmoids);
			childMOIDMap.put("MOOracleRollSEG", dbmoids);
			// Map<String, Object> temp = new HashMap<String, Object>();
			for (String parmKey : childMOIDMap.keySet()) {
				parm = new HashMap<String, Object>();
				parm.put("tableName", parmKey);
				parm.put("MOID", childMOIDMap.get(parmKey));
				List<Integer> childMoid = dbserviceMapper
						.queryOracleChlidMOID(parm);
				if (childMoid.size() > 0) {
					moids = childMoid.toString().replace("[", "")
							.replace("]", "");
					temp.put(parmKey, moids);
				}
			}
		}
		/*
		 * // 删除性能数据 Map<String, Object> oraclePrefData = new HashMap<String,
		 * Object>(); oraclePrefData.put("PerfOrclTableSpace", "MOOracleTBS");
		 * oraclePrefData.put("PerfOrclDataFile", "MOOracleDataFile");
		 * oraclePrefData.put("PerfOrclRollback", "MOOracleRollSEG");
		 * 
		 * oraclePrefData.put("PerfOrclSession", "MOOracleInstance");
		 * oraclePrefData.put("PerfOrclPGA", "MOOracleInstance");
		 * oraclePrefData.put("PerfOrclPGAbyProcess", "MOOracleInstance");
		 * oraclePrefData.put("PerfOrclJob", "MOOracleInstance"); for(String
		 * oracleKey:oraclePrefData.keySet()){ parm = new HashMap<String,
		 * Object>(); parm.put("prefTableName", oracleKey); for(String key :
		 * temp.keySet()){ if(key.equals(oraclePrefData.get(oracleKey))){
		 * parm.put("MOID",temp.get(key)); } } if(parm.containsKey("MOID")){ int
		 * j; do{ j=dbserviceMapper.deletePrefData(parm); }while(j>0); } }
		 */

		String inMOID = "";// 用于告警删除
		Map<String, Object> childInstanceMap = new HashMap<String, Object>();
		childInstanceMap.put("tableName", "MOOracleInstance");
		childInstanceMap.put("MOID", MOID);
		List<Integer> instanceMoid = dbserviceMapper
				.queryInstanceID(childInstanceMap);
		if (instanceMoid.size() > 0) {
			inMOID = instanceMoid.toString().replace("[", "").replace("]", "");
		}
		if (inMOID != null && !"".equals(inMOID)) {

			// 查询sga子对象
			String sgamoids = "";
			Map<String, Object> childSGAMap = new HashMap<String, Object>();
			childSGAMap.put("MOOracleSGA", inMOID);
			Map<String, Object> sgatemp = new HashMap<String, Object>();
			for (String parmKey : childSGAMap.keySet()) {
				parm = new HashMap<String, Object>();
				parm.put("tableName", parmKey);
				parm.put("MOID", childSGAMap.get(parmKey));
				List<Integer> childMoid = dbserviceMapper.querySGAMOID(parm);
				if (childMoid.size() > 0) {
					sgamoids = childMoid.toString().replace("[", "")
							.replace("]", "");
					sgatemp.put(parmKey, sgamoids);
				}
			}
			// 删除OracleSGA性能数据
			Map<String, Object> oracleSGAPrefData = new HashMap<String, Object>();
			oracleSGAPrefData.put("PerfOrclSGA", "MOOracleSGA");
			for (String sgaKey : oracleSGAPrefData.keySet()) {
				parm = new HashMap<String, Object>();
				parm.put("prefTableName", sgaKey);
				for (String key : sgatemp.keySet()) {
					if (key.equals(oracleSGAPrefData.get(sgaKey))) {
						parm.put("MOID", sgatemp.get(key));
					}
				}
				if (parm.containsKey("MOID")) {
					int j;
					do {
						j = dbserviceMapper.deleteSGApref(parm);
					} while (j > 0);
				}
			}

			// 删除告警数据
			Map<String, Object> alarmMap = new HashMap<String, Object>();
			alarmMap.put("AlarmActiveDetail", inMOID);
			alarmMap.put("AlarmHistoryDetail", inMOID);
			alarmMap.put("AlarmOriginalEventDetail", inMOID);
			alarmMap.put("MOKPIThreshold", inMOID);
			for (String alarmKey : alarmMap.keySet()) {
				parm = new HashMap<String, Object>();
				parm.put("alarmTableName", alarmKey);
				if (dbmoids != null && !"".equals(dbmoids)) {
					parm.put("MOID", inMOID + "," + dbmoids);
				} else {
					parm.put("MOID", inMOID);
				}
				int k;
				do {
					k = dbserviceMapper.deleteAlarmDetail(parm);
				} while (k > 0);

			}
			// 删除该设备的性能快照数据
			parm = new HashMap<String, Object>();
			parm.put("prefTableName", "PerfSnapshotDatabase");
			if (dbmoids != null && !"".equals(dbmoids)) {
				parm.put("MOID", inMOID + "," + dbmoids);
			} else {
				parm.put("MOID", inMOID);
			}
			int m;
			do {
				m = dbserviceMapper.deletePrefSnapshot(parm);
			} while (m > 0);

			// 删除任务性能表
			parm = new HashMap<String, Object>();
			parm.put("tableName", "SysPerfPollTask");
			parm.put("MOID", MOID);
			dbserviceMapper.deleteTaskAndvars(parm);
			if (dbmoids != null && !"".equals(dbmoids)) {
				// 删除该设备的子对象
				Map<String, Object> PrefMap = new HashMap<String, Object>();
				PrefMap.put("MOOracleRollSEG", dbmoids);
				PrefMap.put("MOOracleDataFile", dbmoids);
				PrefMap.put("MOOracleTBS", dbmoids);
				for (String prefKey : PrefMap.keySet()) {
					parm = new HashMap<String, Object>();
					parm.put("tableName", prefKey);
					parm.put("MOID", PrefMap.get(prefKey));
					int w;
					do {
						w = dbserviceMapper.deleteOralceOrDB2Child(parm);
					} while (w > 0);
				}
			}

			// 删除子对象OracleSGA
			parm = new HashMap<String, Object>();
			parm.put("tableName", "MOOracleSGA");
			parm.put("MOID", inMOID);
			dbserviceMapper.deleteSGAOrDB2(parm);

			// 删除凭证
			parm = new HashMap<String, Object>();
			parm.put("snmpTableName", "SysDBMSCommunity");
			parm.put("tableName", "MOOracleInstance");
			parm.put("DBtype", "Oracle");
			parm.put("MOID", MOID);
			dbserviceMapper.deleteSNMP(parm);

			// 删除告警通知策略、告警视图定义、自动派单设置
			Map<String, Object> alarmFiterMap = new HashMap<String, Object>();
			alarmFiterMap.put("AlarmNotifyFilter", inMOID);
			alarmFiterMap.put("AlarmViewFilter", inMOID);
			alarmFiterMap.put("AlarmDispatchFilter", inMOID);
			for (String fiterKey : alarmFiterMap.keySet()) {
				parm = new HashMap<String, Object>();
				parm.put("tableName", fiterKey);
				parm.put("MOID", inMOID);
				int n;
				do {
					n = dbserviceMapper.deleteAlarmFilter(parm);
				} while (n > 0);
			}
		}
		// 删除该对象
		parm = new HashMap<String, Object>();
		parm.put("tableName", "MOOracleInstance");
		parm.put("MOID", MOID);
		dbserviceMapper.deleteMODevice(parm);

		// 删除oracleDB
		dbserviceMapper.deleteOracleDB(map);

		// 删除监测器模板
		dbserviceMapper.deleteTemplate(map);

		// 删除DBMS对象
		int i = dbserviceMapper.deleteDBMSservice(map);
		return (i > 0) ? true : false;

	}

	@Override
	public void deletePerfAndAlarm(Map<String, Object> map) {
		Map<String, Object> parm;

		// 删除性能数据
		Map<String, Object> oraclePrefData = new HashMap<String, Object>();
		oraclePrefData.put("PerfOrclTableSpace", "MOOracleTBS");
		oraclePrefData.put("PerfOrclDataFile", "MOOracleDataFile");
		oraclePrefData.put("PerfOrclRollback", "MOOracleRollSEG");

		oraclePrefData.put("PerfOrclSession", "MOOracleInstance");
		oraclePrefData.put("PerfOrclPGA", "MOOracleInstance");
		oraclePrefData.put("PerfOrclPGAbyProcess", "MOOracleInstance");
		oraclePrefData.put("PerfOrclJob", "MOOracleInstance");
		for (String oracleKey : oraclePrefData.keySet()) {
			parm = new HashMap<String, Object>();
			parm.put("prefTableName", oracleKey);
			for (String key : temp.keySet()) {
				if (key.equals(oraclePrefData.get(oracleKey))) {
					parm.put("MOID", temp.get(key));
				}
			}
			if (parm.containsKey("MOID")) {
				int j;
				do {
					j = dbserviceMapper.deletePrefData(parm);
				} while (j > 0);
			}
		}

	}

}
