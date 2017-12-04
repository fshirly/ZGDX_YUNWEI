package com.fable.insightview.monitor.sysdbmscommunity.service;

import java.util.List;
import java.util.Map;

import com.fable.insightview.platform.page.Page;
import com.fable.insightview.monitor.sysdbmscommunity.entity.SysDBMSCommunityBean;

public interface ISysDBMSCommunityService {

	/**
	 * 新增数据库凭证
	 */
	boolean addDBMSCommunity(SysDBMSCommunityBean bean);

	/**
	 * 新增前验证唯一性
	 */
	boolean checkbeforeAdd(SysDBMSCommunityBean bean);

	/**
	 * 更新前验证唯一性
	 */
	boolean checkbeforeUpdate(SysDBMSCommunityBean bean);

	/**
	 * 新增发现任务
	 */
	public Map<String, Object> addDiscoverTask2(String deviceIP, int creator,
			String moClassNames, int port,String dbName);

	public boolean addDiscoverTask(String deviceIP, int creator,
			String moClassNames, int port, String dbName);

	/**
	 * 更新数据库凭证
	 */
	boolean updateDBMSCommunity(SysDBMSCommunityBean bean);

	/**
	 * 根据任务ID获取数据库凭证信息
	 */
	SysDBMSCommunityBean getDBMSByTaskId(int taskId);

	/**
	 * 根据ID获取数据库凭证信息
	 */
	SysDBMSCommunityBean getDBMSByID(int id);

	/**
	 * 验证设备对象是否已发现
	 */
	boolean isDiscovered(String deviceIP);

	/**
	 * 查询数据库凭证列表
	 */
	List<SysDBMSCommunityBean> getDatabaseCommunityByConditions(
			Page<SysDBMSCommunityBean> page);

	boolean delDBMSCommunity(SysDBMSCommunityBean bean);

	SysDBMSCommunityBean getInfoByID(int id);

	/**
	 * 删除数据库凭证
	 */
	boolean delDBMS(List<Integer> ids);

	/**
	 * 根据IP获取数据库凭证信息
	 */
	List<SysDBMSCommunityBean> getByIP(SysDBMSCommunityBean bean);

	/* 更新数据库验证 */
	boolean updateDBMSCommunityByID(SysDBMSCommunityBean bean);

	List<SysDBMSCommunityBean> getByIPAndPort(SysDBMSCommunityBean bean);
	
	List<SysDBMSCommunityBean> getByConditions(SysDBMSCommunityBean bean);
	
	List<SysDBMSCommunityBean> getByIPAndTypeAndPortAndName(SysDBMSCommunityBean bean);
	
	boolean updateDBMSCommunity2(SysDBMSCommunityBean bean);
}
