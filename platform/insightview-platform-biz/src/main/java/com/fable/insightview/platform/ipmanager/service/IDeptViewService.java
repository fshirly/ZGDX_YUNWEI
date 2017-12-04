package com.fable.insightview.platform.ipmanager.service;

import java.util.List;
import java.util.Map;

import com.fable.insightview.platform.entity.OrgDeptProviderTreeBean;
import com.fable.insightview.platform.entity.SysEmploymentBean;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.ipmanager.entity.IPManAddressListBean;
import com.fable.insightview.platform.ipmanager.entity.IPManSubNetRDeptBean;
import com.fable.insightview.platform.page.Page;

public interface IDeptViewService {
	/**
	 * 点击单位，获得该单位下所有部门的子网分配情况
	 */
	List<IPManSubNetRDeptBean> listOrgUseSubNet(Page<IPManSubNetRDeptBean> page);
	
	/**
	 * 获得部门下所有子网
	 */
	List<IPManAddressListBean> getAllSubNet(int deptId);
	
	/**
	 * 点击部门，获得该该部门下子网情况
	 */
	List<IPManAddressListBean> listDeptUseSubNet(Page<IPManAddressListBean> page);
	
	/**
	 * 分配到设备
	 */
	boolean doGiveToDevice(IPManAddressListBean addressListBean);
	
	/**
	 * 收回到部门
	 */
	boolean doWithdrawToDept(IPManAddressListBean addressListBean);
	
	/**
	 * 批量分配到设备
	 */
	Map<String, Object> doBatchGiveToDevice(String ids);
	
	/**
	 * 批量收回到部门
	 */
	Map<String, Object> doBatchWithdrawToDept(String ids);
	
	OrgDeptProviderTreeBean searchTreeNodes(String treeName,int firstOrgId);
	
	/**
	 * 根据id获得ip地址信息
	 */
	IPManAddressListBean getAddressInfoById(int id);
	
	/**
	 * 更新ip地址
	 */
	boolean updateNoteByID(IPManAddressListBean bean);
	
	/**
	 * 获得部门下的所有用户
	 */
	List<SysEmploymentBean> getDeptUsers(int deptId);
}
