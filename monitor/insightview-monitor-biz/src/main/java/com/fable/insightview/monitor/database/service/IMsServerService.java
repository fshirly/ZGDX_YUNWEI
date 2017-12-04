package com.fable.insightview.monitor.database.service;

import java.util.List;
import java.util.Map;

import com.fable.insightview.monitor.database.entity.MODBMSServerBean;
import com.fable.insightview.monitor.database.entity.MOMsSQLDatabaseBean;
import com.fable.insightview.monitor.database.entity.MOMsSQLDeviceBean;
import com.fable.insightview.monitor.database.entity.MOMySQLDBServerBean;
import com.fable.insightview.monitor.database.entity.PerfMSSQLDatabaseBean;
import com.fable.insightview.monitor.database.entity.PerfMSSQLServerBean;
import com.fable.insightview.platform.page.Page;

public interface IMsServerService {
	/**
	 * 根据服务ID查询服务详情
	 * @param MOID
	 * @return
	 */
	MODBMSServerBean getMsServerInfo(Map map);
	/**
	 *查询ms服务信息列表
	 * @param page
	 * @return
	 */
	List<MOMySQLDBServerBean> queryMOMsSQLServer(Page<MOMySQLDBServerBean> page);
	/**
	 * 查询ms设备信息列表
	 * @param page
	 * @return
	 */
	List<MOMsSQLDeviceBean> queryMOMsSQLDevice(Page<MOMsSQLDeviceBean> page);
	/**
	 * 查询ms DB 信息列表
	 * @param page
	 * @return
	 */
	List<MOMsSQLDatabaseBean> queryMOMsSQLDatabase(Page<MOMsSQLDatabaseBean> page);
	
	/**
	 * 查询数据库列表
	 * @param serverMOID
	 * @return
	 */
	List<MOMsSQLDatabaseBean> getDBListInfo(Map map);
	/**
	 * 数据库列表详情
	 * @param map
	 * @return
	 */
	MOMsSQLDatabaseBean getDBDetailInfo(Map map);
	/**
	 * 查询设备列表
	 * @param serverMOID
	 * @return
	 */
	List<MOMsSQLDeviceBean> getDeviceListInfo(Map map);
	/**
	 * Server 曲线
	 * @param map
	 * @return
	 */
	List<PerfMSSQLServerBean> queryMSSQLServerPerf(Map map);
	/**
	 * 数据库读写效率统计
	 * @param map
	 * @return
	 */
	PerfMSSQLServerBean getDatabaseDetail(Map map);
	/**
	 * 获取 数据库可用率 图表
	 * @return
	 */
	MODBMSServerBean getMsChartAvailability(Map map);
	/**
	 * Pie 获取数据
	 * @param map
	 * @return
	 */
	PerfMSSQLServerBean getPerfValue(Map map);
	/**
	 * 数据库曲线
	 * @param map
	 * @return
	 */
	List<PerfMSSQLDatabaseBean> queryMSSQLDatabasePerf(Map map);
	/**
	 * MsServerMapper
	 * @param map
	 * @return
	 */
	PerfMSSQLDatabaseBean getLogPerfValue(Map map);
	
	MOMySQLDBServerBean getFirstBean();
	
	MOMsSQLDatabaseBean getFirstMsDbBean();
	
	MOMySQLDBServerBean getMsServerById(Integer moId);
	
	MOMsSQLDatabaseBean getMsDbById(Integer moId);
	
	/**
	 * 获得MsSQLServer信息
	 */
	MOMySQLDBServerBean findMsSqlServerInfo(int moId);
	
	/**
	 * msSql数据库设备信息
	 */
	MOMsSQLDeviceBean findMsDeviceInfo(int moId);
	
	/**
	 * msSql数据库信息
	 */
	MOMsSQLDatabaseBean findMsSQLDatabaseInfo(int moId);
}
