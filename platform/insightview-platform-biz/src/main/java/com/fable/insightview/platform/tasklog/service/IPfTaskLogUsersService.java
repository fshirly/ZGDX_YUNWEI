package com.fable.insightview.platform.tasklog.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.tasklog.PfOrgTree;

public interface IPfTaskLogUsersService {

	/**
	 * 查询任务日志配置人员IDs
	 * 
	 * @return
	 */
	List<Integer> queryUserIds();

	/**
	 * 查询任务日志配置人员IDs
	 * 
	 * @return
	 */
	String queryUserIdsToStr();

	/**
	 * 添加配置人员
	 * 
	 * @param userId
	 */
	void add(@Param("userId") int userId);

	/**
	 * 添加多个用户信息
	 * 
	 * @param userIds
	 */
	void addMulti(String userIds);

	/**
	 * 删除配置人员
	 * 
	 * @param userId
	 */
	void delete(@Param("userId") int userId);

	/**
	 * 查询人员列表
	 * 
	 * @param page
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map<String, String>> queryUsers(Page page);

	/**
	 * 查询配置用户所处单位及部门信息
	 * 
	 * @return
	 */
	List<PfOrgTree> queryTrees();
}
