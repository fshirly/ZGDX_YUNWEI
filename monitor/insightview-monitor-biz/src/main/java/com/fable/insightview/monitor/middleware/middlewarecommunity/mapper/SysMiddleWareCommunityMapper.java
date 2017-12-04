package com.fable.insightview.monitor.middleware.middlewarecommunity.mapper;

import java.util.List;

import com.fable.insightview.monitor.middleware.middlewarecommunity.entity.SysMiddleWareCommunityBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJVMBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareMemoryBean;
import com.fable.insightview.platform.page.Page;

public interface SysMiddleWareCommunityMapper {
	/**
	 * 根据IP和中间件类型获得中间件信息
	 */
	List<SysMiddleWareCommunityBean> getByIP(SysMiddleWareCommunityBean bean);

	/**
	 * 新增
	 */
	int insertMiddleWareCommunity(SysMiddleWareCommunityBean bean);

	/**
	 * 更新
	 */
	int updateMiddleWareCommunity(SysMiddleWareCommunityBean bean);

	SysMiddleWareCommunityBean getMiddleWareTask(int taskId);

	/**
	 * 中间件凭证列表
	 * 
	 * @param page
	 * @return
	 */
	List<SysMiddleWareCommunityBean> getMiddleWareCommunityList(
			Page<SysMiddleWareCommunityBean> page);

	/**
	 * 删除中间件凭证
	 * 
	 * @param ids
	 * @return
	 */
	boolean delMiddleWareCommunity(List<Integer> ids);

	int getCommunityByNameAndIP(SysMiddleWareCommunityBean bean);

	int getCommunityByNameAndIPAndID(SysMiddleWareCommunityBean bean);

	/**
	 * 获取中间件详情
	 * 
	 * @param id
	 * @return
	 */
	SysMiddleWareCommunityBean getCommunityByID(int id);

	int updateCommunityByID(SysMiddleWareCommunityBean bean);
	
	SysMiddleWareCommunityBean getCommunityByIP(SysMiddleWareCommunityBean bean);
	
	/**
	 * 分页查询中间件Java虚拟机
	 * @author zhengxh
	 * @param page
	 * @return
	 */
	List<MOMiddleWareJVMBean> queryListJVM(Page<MOMiddleWareJVMBean> page);
	
	/**
	 * 分页查询中间件内存池
	 * @author zhengxh
	 * @param page
	 * @return
	 */
	List<MOMiddleWareMemoryBean> queryListMemPool(Page<MOMiddleWareMemoryBean> page);
	
	/**
	 * 快照表查询内存空闲和内存百分比
	 * @author zhengxh
	 * @param page
	 * @return
	 */
	List<MOMiddleWareMemoryBean> querySnapshot (MOMiddleWareMemoryBean vo);
	
	SysMiddleWareCommunityBean getCommunityByIPAndPort(SysMiddleWareCommunityBean bean);
	
	List<SysMiddleWareCommunityBean> getByConditions(SysMiddleWareCommunityBean bean);
}
