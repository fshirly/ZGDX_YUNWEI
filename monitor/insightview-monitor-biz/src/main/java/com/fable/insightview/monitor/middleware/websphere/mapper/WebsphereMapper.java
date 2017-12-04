package com.fable.insightview.monitor.middleware.websphere.mapper;

import java.util.List;
import java.util.Map;

import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJMXBean;
import com.fable.insightview.monitor.middleware.websphere.entity.MoMidWareJdbcDsBean;
import com.fable.insightview.monitor.middleware.websphere.entity.MoMidWareJdbcPoolBean;
import com.fable.insightview.monitor.middleware.websphere.entity.MoMiddleWareJ2eeAppBean;
import com.fable.insightview.monitor.middleware.websphere.entity.PerfDeviceAvailabilityBean;
import com.fable.insightview.monitor.middleware.websphere.entity.PerfWASJDBCPoolBean;
import com.fable.insightview.monitor.middleware.websphere.entity.PerfWASJVMHeapBean;
import com.fable.insightview.monitor.middleware.websphere.entity.PerfWASThreadPoolBean;
import com.fable.insightview.monitor.middleware.websphere.entity.WebEjbMoudleInfoBean;
import com.fable.insightview.monitor.middleware.websphere.entity.WebMoudleInfosBean;
import com.fable.insightview.monitor.middleware.websphere.entity.WebSphereOperSituationBean;
public interface WebsphereMapper {
	/**
	 * 根据实例ID查询监测对象信息
	 * 
	 * @param MOID
	 * @return
	 */
	List<MOMiddleWareJMXBean> getWasInfo(Integer MOID);
	/**
	 * 获取内存堆使用率性能
	 * 
	 * @param map
	 * @return
	 */
	List<PerfWASJVMHeapBean> queryWasMemoryPerf(Map map);
	/**
	 * 获取响应趋势曲线
	 * 
	 * @param map
	 * @return
	 */
	List<PerfDeviceAvailabilityBean> queryWasResponsePerf(Map map);
	/**
	 * 获取线程曲线
	 * 
	 * @param map
	 * @return
	 */
	List<PerfWASThreadPoolBean> queryWasThreadPoolPerf(Map map);
	/**
	 * 查询线程信息列表
	 * 
	 * @param MOID
	 * @return
	 */
	List<PerfWASThreadPoolBean> getWasThreadPoolInfo(Map<String,Object> params);
	/**
	 * 获取JDBC池个数
	 */
	List<MoMidWareJdbcPoolBean> queryNumsByMoID(Map map);
	/**
	 * 获取JDBC池信息
	 */
	List<PerfWASJDBCPoolBean> getPerfJdbcPoolList(Map map);
	/**
	 * 查询当月websphere运行现状信息
	 * @param moID
	 * @return
	 */
	List<WebSphereOperSituationBean> getWebSphereOperSituationInfos(Map<String,Object> params);
	/**
	 * 查询当月可用性
	 * @param map
	 * @return
	 */
	List<WebSphereOperSituationBean> getDeviceStatus(Map map);
	/**
	 * 查询应用程序信息
	 */
	MoMiddleWareJ2eeAppBean getWebAppInfos(Integer moID);
	/**
	 * 查询WEB模块详情
	 */
	List<WebMoudleInfosBean> getWebMoudleInfos(Map<String,Object> params);
	
	/**
	 * 查询EJB模块详情
	 */
	List<WebEjbMoudleInfoBean> getWebEjbInfos(Integer moID);
	/**
	 * 获取WASservlet性能
	 * 
	 * @param map
	 * @return
	 */
	List<WebMoudleInfosBean> queryWebAppPerf(Map map);
	/**
	 * 查询JDBC连接池信息
	 */
	List<MoMidWareJdbcPoolBean> getJdbcPoolInfos(Integer moID);
	/**
	 * 查询JDBC数据源信息
	 */
	List<MoMidWareJdbcDsBean> getJdbcDsInfos(Integer moID);
	/**
	 * 查询某一条数据源详情
	 */
	MoMidWareJdbcDsBean getJdbcDsDetailInfos(Integer moID);
}