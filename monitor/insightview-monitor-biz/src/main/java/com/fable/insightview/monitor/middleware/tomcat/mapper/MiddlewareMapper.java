package com.fable.insightview.monitor.middleware.tomcat.mapper;

import java.util.List;
import java.util.Map;

import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJMXBean;
import com.fable.insightview.platform.page.Page;

public interface MiddlewareMapper {
	/**
	 * 分页中间件列表
	 * 
	 * @param page
	 * @return
	 */
	List<MOMiddleWareJMXBean> queryList(Page<MOMiddleWareJMXBean> page);

	/**
	 * 根据moId查询中间件信息
	 * 
	 * @param moId
	 * @return
	 */
	MOMiddleWareJMXBean selectMoMidByPrimaryKey(Integer moId);

	/**
	 * 获得中间件信息
	 * 
	 * @param moId
	 * @return
	 */
	MOMiddleWareJMXBean getMiddleWareInfo(int moId);

	int getJMXByIPAndType(MOMiddleWareJMXBean bean);

	MOMiddleWareJMXBean getJMXInfoByIPAndType(MOMiddleWareJMXBean bean);
	MOMiddleWareJMXBean getJMXInfoByIPAndTypeAlias(MOMiddleWareJMXBean bean);
	
	MOMiddleWareJMXBean getFirstMiddle(String jmxType);
	
	int updateMiddleWareJMX(MOMiddleWareJMXBean bean);
	List<MOMiddleWareJMXBean> queryAll();
	
	/**
	 * 根据条件查询中间件列表
	 */
	List<MOMiddleWareJMXBean> queryListByCondition(Map map);
}
