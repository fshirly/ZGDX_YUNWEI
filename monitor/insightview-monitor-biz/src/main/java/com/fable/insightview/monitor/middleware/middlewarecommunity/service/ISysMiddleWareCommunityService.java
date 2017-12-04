package com.fable.insightview.monitor.middleware.middlewarecommunity.service;

import java.util.List;

import com.fable.insightview.monitor.middleware.middlewarecommunity.entity.SysMiddleWareCommunityBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJVMBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareMemoryBean;
import com.fable.insightview.platform.page.Page;

public interface ISysMiddleWareCommunityService {
	/**
	 * 是否已存在
	 * 
	 * @param bean
	 * @return
	 */
	boolean isExistMiddleWare(SysMiddleWareCommunityBean bean);

	/**
	 * 新增
	 */
	boolean insertMiddleWareCommunity(SysMiddleWareCommunityBean bean);

	/**
	 * 更新
	 */
	boolean updateMiddleWareCommunity(SysMiddleWareCommunityBean bean);

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

	/**
	 * 中间件凭证唯一性的验证
	 */
	boolean checkCommunity(String flag, SysMiddleWareCommunityBean communityBean);

	/**
	 * 获取中间件详情
	 * 
	 * @param id
	 * @return
	 */
	SysMiddleWareCommunityBean getCommunityByID(int id);

	/**
	 * 更新
	 * 
	 * @param bean
	 * @return
	 */
	boolean updateCommunityByID(SysMiddleWareCommunityBean bean);

	SysMiddleWareCommunityBean getCommunityByIP(SysMiddleWareCommunityBean bean);

	/**
	 * 分页查询中间件Java虚拟机
	 * 
	 * @author zhengxh
	 * @param page
	 * @return
	 */
	List<MOMiddleWareJVMBean> queryListJVM(Page<MOMiddleWareJVMBean> page);

	/**
	 * 分页查询中间件内存池
	 * 
	 * @author zhengxh
	 * @param page
	 * @return
	 */
	List<MOMiddleWareMemoryBean> queryListMemPool(
			Page<MOMiddleWareMemoryBean> page);

	/**
	 * 快照表查询内存空闲和内存百分比
	 * 
	 * @author zhengxh
	 * @param page
	 * @return
	 */
	List<MOMiddleWareMemoryBean> querySnapshot(MOMiddleWareMemoryBean vo);

	SysMiddleWareCommunityBean getCommunityByIPAndPort(
			SysMiddleWareCommunityBean bean);
	
	List<SysMiddleWareCommunityBean> getByConditions(SysMiddleWareCommunityBean bean);
}
