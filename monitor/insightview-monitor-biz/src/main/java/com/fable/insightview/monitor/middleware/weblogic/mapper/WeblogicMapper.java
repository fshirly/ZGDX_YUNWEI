package com.fable.insightview.monitor.middleware.weblogic.mapper;

import java.util.List;
import java.util.Map;

import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJMXBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.TomcatOperSituationBean;
import com.fable.insightview.monitor.middleware.weblogic.entity.PerfWebLogicJDBCPoolBean;
import com.fable.insightview.monitor.middleware.weblogic.entity.PerfWebLogicJMSBean;
import com.fable.insightview.monitor.middleware.weblogic.entity.PerfWebLogicJTABean;
import com.fable.insightview.monitor.middleware.weblogic.entity.PerfWebLogicJVMHeapBean;
import com.fable.insightview.monitor.middleware.weblogic.entity.PerfWebLogicMemoryPoolBean;
import com.fable.insightview.monitor.middleware.weblogic.entity.PerfWebLogicThreadPoolBean;
public interface WeblogicMapper {
	/**
	 * 查询当天WebLogic运行现状信息
	 * 
	 * @param moID
	 * @return
	 */
	List<TomcatOperSituationBean> getWebLogicOperSituationInfos(
			Map<String, Object> params);
	/**
	 * 根据实例ID查询监测对象信息
	 * @param MOID
	 * @return
	 */
	List<MOMiddleWareJMXBean> getWeblogicInfo(Integer MOID);
	/**
	 * 获取内存堆使用率性能
	 * @param map
	 * @return
	 */
	List<PerfWebLogicJVMHeapBean> queryWebLogicMemoryPerf(Map map);
	/**
	 * 获取连接数统计性能数据
	 * @param map
	 * @return
	 */
	List<PerfWebLogicJMSBean> queryServerPerf(Map map);
	/**
	 * 根据实例ID查询JTA象信息
	 * @param MOID
	 * @return
	 */
	PerfWebLogicJTABean getJTAInfo(Map map);
	
	PerfWebLogicJTABean getPieInfo(Integer MOID);
	/**
	 * 获取JDBC池信息
	 */
	List<PerfWebLogicJDBCPoolBean> getPerfJdbcPoolList(Map map);
	/**
	 * 获取当前内存使用率
	 * @param map
	 * @return
	 */
	PerfWebLogicJVMHeapBean getCurrMemUsage(Map map);
	/**
	 * 查询某一条数据源详情
	 */
	PerfWebLogicJDBCPoolBean getJdbcPoolDetailInfos(Map map);
	/**
	 * 查询线程池曲线
	 * @param map
	 * @return
	 */
    List<PerfWebLogicThreadPoolBean> queryWebLogicThreadPoolPerf(Map map);
    /**
   	 * 内存池 性能
   	 * @param map
   	 * @return
   	 */
   	List<PerfWebLogicMemoryPoolBean> queryWebLogicMemoryPoolPerf(Map map);
   	/**
	 * 获取连接数统计性能数据
	 * @param map
	 * @return
	 */
	List<PerfWebLogicJDBCPoolBean> queryJdbcPerf(Map map);
	
}