package com.fable.insightview.platform.dao;

import java.util.List;

import com.fable.insightview.platform.entity.SysUserInGroupsBean;
import com.fable.insightview.platform.itsm.core.dao.GenericDao;
/**
 * 用户与组关联表处理
 * @author Administrator sanyou
 *
 */
public interface ISysUserInGroupsDao extends GenericDao<SysUserInGroupsBean> {
    /*
     * 根据组Id查询SysUserInGroupsBean     
     */
	List<SysUserInGroupsBean>  findUserInGroupsByGroupId(int groupId);
	
	 /*
     * 根据用户Id查询SysUserInGroupsBean     
     */
	List<SysUserInGroupsBean>  findUserInGroupsByuserId(int userId);
	
	/*
     * 根据用户Id查询SysUserInGroupsBean     
     */
	List<SysUserInGroupsBean>  findUserInGroupsByuserId(String userId);
	
	
	boolean delete(int userId, int groupId);
	
	boolean deleteByGroupID(int groupId);
}
