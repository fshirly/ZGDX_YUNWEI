package com.fable.insightview.platform.ipmanager.mapper;

import java.util.List;

import com.fable.insightview.platform.ipmanager.entity.IPManSubNetRDeptBean;
import com.fable.insightview.platform.page.Page;

public interface IPManSubNetRDeptMapper {
	/**
	 * 获得所有的子网部门关系信息
	 */
	List<IPManSubNetRDeptBean> getAllSubNetRDeptInfo();
	
	/**
	 * 点击子网展示的列表数据
	 */
	List<IPManSubNetRDeptBean> listSubnetConDept(Page<IPManSubNetRDeptBean> page);
	
	/**
	 * 获得点击子网的导出数据
	 */
	List<IPManSubNetRDeptBean> getAllSubNetRDeptInfo2(int subNetId);
	
	/**
	 * 根据部门id与子网id获得
	 */
	List<IPManSubNetRDeptBean> getBySubNetAndDept(IPManSubNetRDeptBean bean);
	
	/**
	 * 更新
	 */
	int updateSubNetRDept(IPManSubNetRDeptBean bean);
	
	/**
	 * 新增
	 */
	int insertSubnetRDept(IPManSubNetRDeptBean bean);
	
	/**
	 * 根据子网id获得
	 */
	List<IPManSubNetRDeptBean> getBySubNet(int subNetId);
	
	/**
	 * 删除子网部门关系
	 */
	boolean delSubNetDDeptById(int id);
	
	/**
	 * 根据id获得子网部门信息
	 */
	IPManSubNetRDeptBean getByID(int id);
	
	/**
	 * 根据子网删除子网部门关系
	 */
	boolean delSubNetDeptBySubNetID(int subNetId);
	
	/**
	 * 获得已分配的部门
	 */
	List<IPManSubNetRDeptBean> getDeptByCondition(Page<IPManSubNetRDeptBean> page);
	
	/**
	 * 获得部门所有的占用地址数 、预占地址数
	 * @param deptId
	 * @return
	 */
	IPManSubNetRDeptBean getUseNumAndPreemptNum(int deptId);
	
	List<IPManSubNetRDeptBean> getByDeptID(int deptId);
	
	/**
	 * 根据子网id获得占用的关系
	 */
	List<IPManSubNetRDeptBean> getUsedBySubNet(int subNetId);
}
