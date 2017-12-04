package com.fable.insightview.monitor.database.service;

import java.util.List;
import java.util.Map;

import com.fable.insightview.monitor.database.entity.MODBMSServerBean;
import com.fable.insightview.monitor.database.entity.MOMsSQLDatabaseBean;
import com.fable.insightview.monitor.database.entity.MOMsSQLDeviceBean;
import com.fable.insightview.monitor.database.entity.MOMySQLDBServerBean;
import com.fable.insightview.monitor.database.entity.MOSybaseDatabaseBean;
import com.fable.insightview.monitor.database.entity.MOSybaseDeviceBean;
import com.fable.insightview.monitor.database.entity.PerfMSSQLServerBean;
import com.fable.insightview.monitor.database.entity.PerfSybaseDatabaseBean;
import com.fable.insightview.monitor.database.entity.PerfSybaseServerBean;
import com.fable.insightview.platform.page.Page;

public interface ISyBaseService {
	/**
	 * 根据实例ID查询实例详情
	 * @param MOID
	 * @return
	 */
	MODBMSServerBean getOrclInstanceDetail(Map map);
	/**
	 * 获取 数据库可用率 图表
	 */
	MODBMSServerBean getSybaseChartAvailability(Map map);
	/**
	 *查询SyBase服务信息列表
	 * @param page
	 * @return
	 */
	List<MOMySQLDBServerBean> queryMOSybaseServer(Page<MOMySQLDBServerBean> page);
	/**
	 * 查询SyBase设备信息列表
	 * @param page
	 * @return
	 */
	List<MOSybaseDeviceBean> queryMOSybaseDevice(Page<MOSybaseDeviceBean> page);
	/**
	 * 查询SyBase DB 信息列表
	 * @param page
	 * @return
	 */
	List<MOSybaseDatabaseBean> queryMOSybaseDatabase(Page<MOSybaseDatabaseBean> page);
	/**
	 * Server 曲线
	 * @param map
	 * @return
	 */
	List<PerfSybaseServerBean> querySybaseServerPerf(Map map);
	/**
	 * Pie 获取数据
	 * @param map
	 * @return
	 */
	PerfSybaseServerBean getPerfValue(Map map);
	/**
	 * Pie 获取数据库数据
	 * @param map
	 * @return
	 */
	PerfSybaseDatabaseBean getDatabasefPerfValue(Map map);
	/**
	 * 数据库其他参数统计
	 * @param map
	 * @return
	 */
	PerfSybaseServerBean getDatabaseDetail(Map map);
	
	MOMySQLDBServerBean getFirstBean();
	
	MOSybaseDatabaseBean getFirstSybaseDbBean();
	
	MOMySQLDBServerBean getSybaseServerById(Integer moId);
	
	MOSybaseDatabaseBean getSybaseDbById(Integer moId);
	
	MOMySQLDBServerBean findSyBaseServerInfo(int moId);
	
	MOSybaseDeviceBean findSybaseDeviceInfo(int moId);
	
	MOSybaseDatabaseBean findSybaseDatabase(int moId);
}
