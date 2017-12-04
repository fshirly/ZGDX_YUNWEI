package com.fable.insightview.monitor.database.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.monitor.database.entity.MODBMSServerBean;
import com.fable.insightview.monitor.database.entity.MOMsSQLDatabaseBean;
import com.fable.insightview.monitor.database.entity.MOMsSQLDeviceBean;
import com.fable.insightview.monitor.database.entity.MOMySQLDBServerBean;
import com.fable.insightview.monitor.database.entity.PerfMSSQLDatabaseBean;
import com.fable.insightview.monitor.database.entity.PerfMSSQLServerBean;
import com.fable.insightview.platform.page.Page;

public interface MsServerMapper {
	// 根据实例ID查询实例详情
	MODBMSServerBean getMsServerInfo(Map map);
	//查询ms服务信息列表
	List<MOMySQLDBServerBean> queryMOMsSQLServer(Page<MOMySQLDBServerBean> page);
	
	//查询ms设备信息列表
	List<MOMsSQLDeviceBean> queryMOMsSQLDevice(Page<MOMsSQLDeviceBean> page);
	//查询ms DB 信息列表
	List<MOMsSQLDatabaseBean> queryMOMsSQLDatabase(Page<MOMsSQLDatabaseBean> page);
	//查询DB列表
	List<MOMsSQLDatabaseBean> getDBListInfo(Map map);
	//查询设备列表
	List<MOMsSQLDeviceBean> getDeviceListInfo(Map map);
	//Server 曲线
	List<PerfMSSQLServerBean> queryMSSQLServerPerf(Map map);
	//数据库读写效率统计
	PerfMSSQLServerBean getDatabaseDetail(Map map);
	// 获取 数据库可用率 图表
	MODBMSServerBean getMsChartAvailability(Map map);
	//Pie 获取数据
	PerfMSSQLServerBean getPerfValue(Map map);
	// 数据库曲线	
	List<PerfMSSQLDatabaseBean> queryMSSQLDatabasePerf(Map map);
	//Pie 数据库获取数据
	PerfMSSQLDatabaseBean getLogPerfValue(Map map);
	// 数据库列表详情	
	MOMsSQLDatabaseBean getDBDetailInfo(Map map);
	//获取第一条server数据
	MOMySQLDBServerBean getFirstBean();
	
	MOMsSQLDatabaseBean getFirstMsDbBean();
	
	MOMySQLDBServerBean getMsServerById(Integer moId);
	
	MOMsSQLDatabaseBean getMsDbById(Integer moId);
	
	MOMySQLDBServerBean findMsSqlServerInfo(int moId);
	
	MOMsSQLDeviceBean findMsDeviceInfo(int moId);
	
	MOMsSQLDatabaseBean findMsSQLDatabaseInfo(int moId);
	
	List<Integer> getServerIDByMOID(int moId);
	
	List<MOMySQLDBServerBean> getMsSQLServer(Map map);
	
	int getDbmsIDByServer(@Param("moId")int moId);
}