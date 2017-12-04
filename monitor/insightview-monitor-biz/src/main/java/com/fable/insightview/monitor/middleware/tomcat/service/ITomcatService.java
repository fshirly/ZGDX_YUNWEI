package com.fable.insightview.monitor.middleware.tomcat.service;

import java.util.List;
import java.util.Map;

import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJMSBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJMXBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJTABean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJVMBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJdbcDSBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJdbcPoolBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareMemoryBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareServletBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareThreadPoolBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareWebModuleBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.PerfTomcatClassLoadBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.PerfTomcatJVMHeapBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.PerfTomcatMemoryPoolBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.PerfTomcatThreadPoolBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.TomcatOperSituationBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.TomcatWebModuleBean;
import com.fable.insightview.monitor.middleware.websphere.entity.MoMiddleWareJ2eeAppBean;
import com.fable.insightview.platform.page.Page;

public interface ITomcatService {
	/**
	 * 根据实例ID查询监测对象信息
	 * 
	 * @param MOID
	 * @return
	 */
	MOMiddleWareJMXBean getTmInfo(Map map);

	/**
	 * 获取 Tomcat可用率 图表
	 * 
	 * @return
	 */
	MOMiddleWareJMXBean getTmChartAvailability(Map map);

	/**
	 * 获取内存池个数
	 */
	List<MOMiddleWareMemoryBean> queryMemNameByMoID(Map map);

	/**
	 * 内存池 性能
	 * 
	 * @param map
	 * @return
	 */
	List<PerfTomcatMemoryPoolBean> queryTmMemoryPoolPerf(Map map);

	/**
	 * 获取内存占用连接性能
	 * 
	 * @param map
	 * @return
	 */
	List<PerfTomcatJVMHeapBean> queryTmMemoryUsedPerf(Map map);

	/**
	 * 查询所有应用实例
	 * 
	 * @param moID
	 * @return
	 */
	List<TomcatWebModuleBean> getTmInstanceList(Integer moID);

	/**
	 * 获取JDBC 列表个数
	 * 
	 * @param map
	 * @return
	 */
	List<MOMiddleWareJdbcDSBean> getJdbcDSCount(Map map);

	/**
	 * 获取JDBC 列表信息
	 * 
	 * @param map
	 * @return
	 */
	List<MOMiddleWareJdbcDSBean> getJdbcDSValue(Map map);

	/**
	 * 获取线程池 列表个数
	 * 
	 * @param map
	 * @return
	 */
	List<MOMiddleWareThreadPoolBean> getThreadPoolCount(Map map);

	/**
	 * 获取线程池 列表信息
	 * 
	 * @param map
	 * @return
	 */
	List<MOMiddleWareThreadPoolBean> getThreadPoolValue(Map map);

	/**
	 * 内存池 列表信息
	 * 
	 * @param map
	 * @return
	 */
	List<MOMiddleWareMemoryBean> getMemoryPoolValue(Map map);

	/**
	 * 获取 内存池 柱状图
	 * 
	 * @return
	 */
	List<MOMiddleWareMemoryBean> getBarChartMemory(Map map);

	/**
	 * 查询当天tomcat运行现状信息
	 * 
	 * @param moID
	 * @return
	 */
	List<TomcatOperSituationBean> getTomcatOperSituationInfos(
			Map<String, Object> params);

	/**
	 * 查询tomcatClassLoader信息
	 * 
	 * @param moID
	 * @return
	 */
	List<PerfTomcatClassLoadBean> getTomcatClassLoaderInfos(Map<String,Object> params);

	/**
	 * 查询tomcatClassLoader信息(分页查询)
	 * 
	 * @param moID
	 * @return
	 */
	List<PerfTomcatClassLoadBean> getListInfo(Page<PerfTomcatClassLoadBean> page);

	/**
	 * 查询JDBC连接池信息(分页查询)
	 * 
	 * @param moID
	 * @return getListInfo
	 */
	List<MOMiddleWareJdbcPoolBean> getJdbcPoolInfo(
			Page<MOMiddleWareJdbcPoolBean> page);

	/**
	 * 查询JMS信息(分页查询)
	 * 
	 * @param moID
	 * @return getListInfo
	 */
	List<MOMiddleWareJMSBean> getJMSInfo(Page<MOMiddleWareJMSBean> page);

	/**
	 * 查询JTA信息(分页查询)
	 * 
	 * @param moID
	 * @return getListInfo
	 */
	List<MOMiddleWareJTABean> getJTAInfo(Page<MOMiddleWareJTABean> page);

	/**
	 * 查询当天可用性和响应时间
	 * 
	 * @param map
	 * @return
	 */
	List<TomcatOperSituationBean> getDeviceStatusAndResponseTime(Map map);

	/**
	 * 查询JVM内存信息
	 * 
	 * @param moID
	 * @return
	 */
	List<PerfTomcatJVMHeapBean> getJvmHeapInfos(Map<String, Object> params);

	/**
	 * 获取线程个数
	 */
	List<MOMiddleWareThreadPoolBean> queryNumsByMoID(Map map);

	/**
	 * 获取线程信息
	 */
	List<PerfTomcatThreadPoolBean> getPerfThreadPoolList(Map map);

	/**
	 * byte和kb、mb转换
	 * 
	 * @param bytes
	 * @return
	 */
	public String formatValue(long bytes);

	List<MOMiddleWareThreadPoolBean> getThreadPoolList(
			Page<MOMiddleWareThreadPoolBean> page);

	/**
	 * jdbc数据源列表信息
	 * 
	 * @param page
	 * @return
	 */
	List<MOMiddleWareJdbcDSBean> getJdbcDSList(Page<MOMiddleWareJdbcDSBean> page);

	/**
	 * j2ee应用列表页面
	 * 
	 * @param page
	 * @return
	 */
	List<MoMiddleWareJ2eeAppBean> getJ2eeAppList(
			Page<MoMiddleWareJ2eeAppBean> page);

	/**
	 * 根据Id获得线程池的信息
	 * 
	 * @param moId
	 * @return
	 */
	MOMiddleWareThreadPoolBean getThreadPoolByID(int moId);

	/**
	 * 根据Id获得类加载的信息
	 * 
	 * @param moId
	 * @return
	 */
	PerfTomcatClassLoadBean getClassLoadByID(int moId);

	/**
	 * 根据Id获得JDBC数据源的信息
	 * 
	 * @param moId
	 * @return
	 */
	MOMiddleWareJdbcDSBean getJdbcDSByID(int moId);

	/**
	 * 根据Id获得JDBC连接池的信息
	 * 
	 * @param moId
	 * @return
	 */
	MOMiddleWareJdbcPoolBean getJdbcPoolByID(int moId);

	/**
	 * 根据Id获得中间件内存池的信息
	 * 
	 * @param moId
	 * @return
	 */
	MOMiddleWareMemoryBean getMemPoolByID(int moId);

	/**
	 * 根据Id获得中间件JTA的信息
	 * 
	 * @param moId
	 * @return
	 */
	MOMiddleWareJTABean getMiddleWareJTAByID(int moId);

	/**
	 * 根据Id获得Java虚拟机信息的信息
	 * 
	 * @param moId
	 * @return
	 */
	MOMiddleWareJVMBean getMiddlewareJvmByID(int moId);

	/**
	 * 根据Id获得j2ee应用的信息
	 * 
	 * @param moId
	 * @return
	 */
	MoMiddleWareJ2eeAppBean getJ2eeAppByID(int moId);

	/**
	 * 根据Id获得JMS的信息
	 * 
	 * @param moId
	 * @return
	 */
	MOMiddleWareJMSBean getMiddleJMSByID(int moId);

	/**
	 * WebModule列表信息
	 * 
	 * @param page
	 * @return
	 */
	List<MOMiddleWareWebModuleBean> getWebModuleList(
			Page<MOMiddleWareWebModuleBean> page);

	/**
	 * servlet列表信息
	 * 
	 * @param page
	 * @return
	 */
	List<MOMiddleWareServletBean> getServletList(
			Page<MOMiddleWareServletBean> page);

	/**
	 * 根据Id获得WebModule的信息
	 * 
	 * @param moId
	 * @return
	 */
	MOMiddleWareWebModuleBean getWebModuleByID(int moId);

	/**
	 * 根据Id获得Servlet的信息
	 * 
	 * @param moId
	 * @return
	 */
	MOMiddleWareServletBean getServletByID(int moId);
	
	List<MOMiddleWareThreadPoolBean> getThreadPoolPerf(int moId);
	
	MOMiddleWareJdbcPoolBean getFirstJdbcPool(Map map);
}
