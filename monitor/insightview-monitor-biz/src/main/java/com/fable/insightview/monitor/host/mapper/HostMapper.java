package com.fable.insightview.monitor.host.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.monitor.entity.AlarmNode;
import com.fable.insightview.monitor.host.entity.AlarmActiveDetail;
import com.fable.insightview.monitor.host.entity.MODevice;
import com.fable.insightview.monitor.host.entity.PerfNetworkCPU;
import com.fable.insightview.monitor.host.entity.PerfNetworkMemory;
import com.fable.insightview.platform.page.Page;

public interface HostMapper {

	/**
	 * 获取 主机 TOP10CPU 使用率
	 * 
	 * @return
	 */
	List<MODevice> getPerfCPU(Map map);
	/**
	 * 获取  图表内存 使用率
	 * 
	 * @return
	 */
	MODevice getChartMemory(Map map);
	/**
	 * 获取  图表CPU 使用率
	 * 
	 * @return
	 */
	MODevice getChartCPU(Map map);
	/**
	 * 获取  图表可用性
	 * 
	 * @return
	 */
	MODevice getChartAvailability(Map map);
	/**
	 * 获取  接口图表可用性
	 * 
	 * @return
	 */
	MODevice getHSWChartAvailability(Map map);
	/**
	 * 获取  图表丢包率
	 * 
	 * @return
	 */
	MODevice getDeviceLoss(Map map);
	/**
	 * 获取  图表硬盘
	 * 
	 * @return
	 */
	MODevice getChartDisk(Map map);
	/**
	 * 获取  图表总带宽
	 * 
	 * @return
	 */
	MODevice getChartSumIfUsage(Map map);
	/**
	 * 获取  图表接口带宽
	 * 
	 * @return
	 */
	MODevice getChartIfUsage(Map map);

	/**
	 * 获取 主机 TOP10内存 使用率
	 * 
	 * @return
	 */
	List<MODevice> getPerfMemory(Map map);

	/**
	 * 获取 TOP10主机硬盘 使用率
	 * 
	 * @return
	 */
	List<MODevice> getPerfDisk(Map map);

	/**
	 * 获取 TOP10接口流量
	 * 
	 * @return
	 */
	List<MODevice> getPerfFlows(Map map);

	/**
	 * 获取 TOP10接口带宽使用率
	 * 
	 * @return
	 */
	List<MODevice> getPerfIfUsage(Map map);

	/**
	 * 获取最新告警
	 * 
	 * @return
	 */
	List<AlarmActiveDetail> getAlarmActive(Integer moClassID);

	/**
	 * 主机根据设备IP查找详情
	 * 
	 * @param deviceIP
	 * @return
	 */
	MODevice getHostDetail(Integer MOID);

	/**
	 * 虚拟机根据设备IP查找详情
	 * 
	 * @param deviceIP
	 * @return
	 */
	MODevice getVMDetail(Integer MOID);

	/**
	 * 接口详情
	 * 
	 * @param deviceIP
	 * @return
	 */
	MODevice getIfDetail(Map map);

	/**
	 * 设备根据设备IP查找详情
	 * 
	 * @param deviceIP
	 * @return
	 */
	MODevice getDeviceDetail(Map map);

	/**
	 * 宿主机根据设备IP查找详情
	 * 
	 * @param deviceIP
	 * @return
	 */
	List<MODevice> getMoClassIDCount(@Param("classids") String moClassID); 
	
	/**
	 * 宿主机根据设备IP查找详情
	 * 
	 * @param deviceIP
	 * @return
	 */
	List<MODevice> getMoClassIDAllCount(@Param("classids") String moClassID); 
	
	MODevice getPhysicsDetail(Integer MOID);

	/**
	 * 设备快照
	 * 
	 * @return
	 */
	List<MODevice> getSnapshotInfo(Map map);
	
	List<MODevice> querySnapshotInfo(Map map);
	
	List<MODevice> querySeriousDeviceCount(Map map);
	
	List<MODevice> getVmSnapshotInfo(Map map);
	
	List<MODevice> queryMObjectDef(Map map);
	
	/**
	 * 查询设备总数，以及有问题（告警等级1-3）的设备数
	 * @param map
	 * @return
	 */
	List<MODevice> queryAllDeviceInfo(Map map);
	
	/**
	 * 查询告警总数
	 * @param map
	 * @return
	 */
	List<MODevice> queryAlarmInfoCounts(Map map);
	 
	 
	/**
	 * 主机视图列表 for 设备个数
	 * 
	 * @param deviceIP
	 * @return
	 */
	List<MODevice> getHostViewCount(); 
	/**
	 * 获取告警状态
	 * @param MOID
	 * @return
	 */
    Integer getViewLevel(int MOID);
	/**
	 * 主机视图列表
	 * 
	 * @return
	 */
	List<MODevice> getHostViewInfo(Map map);
	
	/**
	 * 主机视图列表之告警状态正常
	 * 
	 * @return
	 */
	List<MODevice> getHostViewLevInfo(Map map);
	/**
	 * 宿主机视图列表 for 设备个数
	 * 
	 * @param deviceIP
	 * @return
	 */
	List<MODevice> getVHostViewCount(); 
	/**
	 * 宿主机视图列表
	 * 
	 * @return
	 */
	List<MODevice> getVHostViewInfo(Map map);
	/**
	 * 宿主机视图列表之告警状态正常
	 * 
	 * @return
	 */
	List<MODevice> getVHostViewLevInfo(Map map);
	/**
	 * 获取 数据存储 柱状图
	 * 
	 * @return
	 */
	List<MODevice> getBarChartDatastore(Map map);
	/**
	 * 获取 硬盘 柱状图
	 * 
	 * @return
	 */
	List<MODevice> getBarChartDisk(Map map);
	/**
	 * 获取 带宽 柱状图
	 * 
	 * @return
	 */
	List<MODevice> getBarChartIfUsage(Map map);
	/*** ******************************************************主机详情列表******************************************************************** */
	/**
	 * 获取主机详情信息列表 FOR 接口
	 * 
	 * @return
	 */
	List<MODevice> getDetailFlowsInfo(Map map);
	/**
	 * 获取主机详情信息列表 FOR 最近告警
	 * 
	 * @return
	 */
	List<AlarmActiveDetail> getDetailAlarmInfo(Map map);
	/*** ******************************************************宿主机详情列表******************************************************************** */
	/**
	 * 获取宿主机详情信息列表 FOR 虚拟机列表
	 * 
	 * @return
	 */
	List<MODevice> getVHostForVMInfo(Map map);
	/*** ******************************************************VM详情列表******************************************************************** */
	
	List<MODevice> getKPICount(Map map);
	
	List<MODevice> getKPIMemoryValue(Map map);
	
	List<MODevice> getKPICPUValue(Map map);
	
	List<MODevice> getKPIDiskValue(Map map);
	
	List<MODevice> getKPIDatastoreValue(Map map);
	
	List<MODevice> getKPIFlowsValue(Map map);
	
	List<MODevice> getKPIIFCount(Map map);
	
	List<MODevice> getKPIIFValue(Map map);
	//----------------------------------------------
	/**
	 * 获取设备部件的个数
	 */
	List<MODevice> queryNumsByDevice(Map map);
	
	List<MODevice> queryNumsByAvg(Map map);
	
	List<MODevice> queryVoNameByDevice(Map map);
	/**
	 * 
	 * 查询宿主机当前内存使用率所有的数据
	 * @return
	 */
//	List<MODevice> queryAllMemory(Map map);
	List<MODevice> queryMainMemoryData(Map map);
	
	/**
	 * 
	 * 查询主机当前内存使用率所有的数据
	 * @return
	 */
	List<MODevice> queryAllMainMemory(Map map);
	
	/**
	 * 
	 * 查询性能表所有的数据
	 * @return
	 */
	List<MODevice> queryAll(Map map);
	
	/**
	 * 
	 * 查询主机cpu使用率所有的数据
	 * @return
	 */
	List<MODevice> queryAllMainCpu(Map map);
	/**
	 * 获取设备内存部件的个数
	 */
	List<MODevice> queryMemByDevice(Map map);
	
	List<MODevice> queryInfaceByDevice(Map map);
	/**
	 * 获取主机cpu历史信息	
	 */
	List<MODevice> mainCPUList(Map map);
	/**
	 * 获取路由器  交换机cpu历史信息	
	 */
//	List<MODevice> netCPUList(Map map);
	List<MODevice> queryNetCPUList(Map map);
	/**
	 * 获取主机硬盘历史信息	
	 */
//	List<MODevice> mainHardList(Map map);
	List<MODevice> queryMainHardList(Map map);
	/**
	 * 获取主机接口历史信息	
	 */
	List<MODevice> mainInterfaceList(Map map);// 之前的主机接口历史信息
	List<MODevice> queryMainInterfaceList(Map map); //后期修改的主机接口历史信息
	List<MODevice> queryInterfaceDataList(Map map); //后期修改的主机接口流入流出信息
	/**
	 * 获取主机内存历史信息	
	 */
	List<MODevice> mainMemoryList(Map map);	
	/**
	 * 获取路由器 交换机 内存历史信息	
	 */
//	List<MODevice> netMemoryList(Map map);
	List<MODevice> queryNetMemoryList(Map map);	
	/**
	 * 获取虚拟机cpu历史信息	
	 */
//	List<MODevice> virtualCPUList(Map map);
	List<MODevice> queryVirtualCPUList(Map map);
	/**
	 * 获取虚拟机硬盘历史信息	
	 */
//	List<MODevice> virtualHardList(Map map);
	List<MODevice> queryVirtualHardList(Map map);
	/**
	 * 获取虚拟机接口历史信息
	 */
	List<MODevice> virtualInterfaceList(Map map);//之前的虚拟机接口历史信息
	List<MODevice> queryVirtualInterfaceList(Map map);//后期修改的虚拟机接口历史信息
	/**
	 * 获取宿主机接口历史信息
	 */
//	List<MODevice> vhostInterfaceList(Map map);
	List<MODevice> queryVhostInterfaceList(Map map);
	/**
	 * 获取虚拟机内存历史信息	
	 */
//	List<MODevice> virtualMemoryList(Map map);
	List<MODevice> queryVirtualMemoryList(Map map);
	/**
	 * 获取虚拟机数据存储历史信息	
	 */
//	List<MODevice> virtualStoreList(Map map);
	List<MODevice> queryVirtualStoreList(Map map);
	/**
	 * 获取宿主机cpu历史信息	
	 */
	List<MODevice> hostCPUList(Map map);
	/**
	 * 获取宿主机硬盘历史信息	
	 */
//	List<MODevice> hostHardList(Map map);	
	List<MODevice> queryHostHardDataList(Map map);	
	/**
	 * 获取宿主机内存历史信息	
	 */
	List<MODevice> hostMemoryList(Map map);
	/**
	 * 获取宿主机数据存储历史信息	
	 */
//	List<MODevice> hostStoreList(Map map);
	List<MODevice> queryHostStoreDataList(Map map);
	//---------------------------------hl接口----------------------
	List<MODevice> getNetworkIf(Map map);

	String getPerfValue(@Param("kpiName") String kpiName,@Param("moID") int moID, @Param("deviceMOID") int deviceMOID);
	//****************************************路由器   交换机***********
	/**
	 * 获取路由器   交换机 最新告警
	 * 
	 * @return
	 */
	List<AlarmActiveDetail> getRSAlarmActive(Integer moClassID);
	
	/**
	 *获取路由器   交换机 设备快照
	 * 
	 * @return
	 */
	List<MODevice> getRsSnapshotInfo(Map map);
	
	List<MODevice> getRsSnapshotInfomation(Map map);
	
	List<MODevice> getAlarmdeviceCount(Map map);
	
	List<MODevice> getNeManufacturerIDCount(String moClassID); 
	
	List<MODevice> getfactoryInfomation(Map param);
	
	
	/**
	 * 查出相关厂商的设备数及严重(告警级别1-3)告警的设备数
	 */
	
	List<MODevice> queryfactoryInfs(Map param);
	/**
	 * 新修改的设备快照告警
	 * 20150721
	 * @param map
	 * @return
	 */
	List<MODevice> getAlarmCountInfomation(Map map);
	/**
	 * 获取名称个数
	 */
	List<MODevice> queryNamesByMoID(Map map);
	
	/**
	 * 获取内存名称个数
	 */
	List<MODevice> queryMemMoidByMoID(Map map);
	/**
	 * 路由器 交换机  内存使用率 曲线图
	 * @param map
	 * @return
	 */
//	List<PerfNetworkMemory> queryRSMemoryPerf(Map map);
	List<PerfNetworkMemory> queryRSMemoryPerfCurve(Map map);
	/**
	 * 路由器 交换机  CPU使用率 曲线图
	 * @param map
	 * @return
	 */
//	List<PerfNetworkCPU> queryRSCPUPerf(Map map);
	List<PerfNetworkCPU> queryRSCPUPerfCurve(Map map);
	
	/**
	 * 根据设备id获取设备类型
	 * @param moID
	 * @return
	 */
	int getMoClassID (Integer moID);
	List<PerfNetworkMemory> queryRSMemoryTime(Map map);
	
	List<MODevice> getKPIFlowsValue2(Map map);
	//首页告警
	List<AlarmActiveDetail> getIndexAlarmActive(Integer num);
	
	int getNetworkIfCount(Integer deviceMOID);
	
	MODevice getMoDeviceInfo(Integer moID);//根据设备ID获取设备信息
	
	int getMoAlarmCount(Map map);//获取某设备告警数量
	
	MODevice getAvgCpu(Map map);//获取cpu平均使用率
	
	MODevice getAvgMemory(Map map);//获取内存平均使用率
	
	List<MODevice> getTZMoClassIDCount(); //泰州定制 快照
	/**
	 * 查询设备总数，以及有问题（告警等级1-3）的设备数
	 * @param map
	 * @return
	 */
	List<MODevice> queryTZAllDeviceInfo(Map map);
	/**
	 * 查询告警总数
	 * @param map
	 * @return
	 */
	List<MODevice> queryTZAlarmInfoCounts(Map map);
	
	/**
	 * 获取值班服务台TOP10CPU
	 * 
	 */
	List<MODevice> getTopCPUByDutyDesk(Map map);
	
	/**
	 * 获取值班服务台 TOP10内存
	 * 
	 * @return
	 */
	List<MODevice> getTopMemoryByDutyDesk(Map map);

	/**
	 * 获取值班服务台 TOP10主机硬盘
	 * 
	 * @return
	 */
	List<MODevice> getTopDiskByDutyDesk(Map map);
	
	/**
	 * 分页查询值班服务台cpu
	 * 
	 */
	List<MODevice> getCpuListByDutyDesk(Page<MODevice> page);
	
	/**
	 * 分页查询值班服务台内存
	 * 
	 */
	List<MODevice> getMemListByDutyDesk(Page<MODevice> page);
	
	/**
	 * 分页查询值班服务台磁盘
	 * 
	 */
	List<MODevice> getVolListByDutyDesk(Page<MODevice> page);
	List<Integer> getMobjectRelationClassIDS(@Param("MOClassID") Integer MOClassID);
}