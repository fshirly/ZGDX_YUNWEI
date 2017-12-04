package com.fable.insightview.platform.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.dao.ISysUserDao;
import com.fable.insightview.platform.dao.ISysUserInGroupsDao;
import com.fable.insightview.platform.entity.SysUserInGroupsBean;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.service.ISysUserInGroupsService;

/**
 * 用户与组的关联表的信息处理接口
 * 
 * @author Administrator sanyou
 * 
 */
@Service("sysUserInGroupsService")
public class SysUserInGroupsServiceImpl implements ISysUserInGroupsService {

	@Autowired
	private ISysUserInGroupsDao sysUserInGroupsDao;

	@Autowired
	private ISysUserDao sysUserDao;

	/**
	 * 根据组id查询其下的员工信息
	 */
	@Override
	public boolean addSysUserInGroupsBean(
			SysUserInGroupsBean sysUserInGroupsBean) {
		String[] userIds = sysUserInGroupsBean.getUserIds().split(",");
		for (String userId : userIds) {
			sysUserInGroupsDao.insert(new SysUserInGroupsBean(Integer
					.parseInt(userId), sysUserInGroupsBean.getGroupId()));
		}

		return true;
	}
	
	/**
	 * 移除用户
	 */
	public boolean removeSysUserInGroupsBean(
			SysUserInGroupsBean sysUserInGroupsBean) {
		String[] userIds = sysUserInGroupsBean.getUserIds().split(",");
		for (String userId : userIds) {
			sysUserInGroupsDao.delete(Integer.parseInt(userId), sysUserInGroupsBean.getGroupId());
		}

		return true;
	}

	/**
	 * 根据组id查询其下的员工信息
	 */
	@Override
	public List<SysUserInfoBean> findSysUserInfoByGroupId(int groupId) {
		List<SysUserInfoBean> userInfo = new ArrayList<SysUserInfoBean>();
		List<SysUserInGroupsBean> sysUserIngroups = sysUserInGroupsDao
				.findUserInGroupsByGroupId(groupId);
		for (int i = 0; i < sysUserIngroups.size(); i++) {
			if(sysUserDao.getById(sysUserIngroups.get(i).getUserId()).getState() == 0) {
				userInfo.add(sysUserDao.getById(sysUserIngroups.get(i).getUserId()));
			}
		}
		return userInfo;
	}

	/*
	 * 根据用户Id查询SysUserInGroupsBean
	 */
	@Override
	public List<SysUserInGroupsBean> findGroupsByUserId(int userId) {
		return sysUserInGroupsDao.findUserInGroupsByuserId(userId);
	}

	@Override
	public List<SysUserInGroupsBean> findGroupsByUserId(String userId) {
		return sysUserInGroupsDao.findUserInGroupsByuserId(userId);
	}

}
