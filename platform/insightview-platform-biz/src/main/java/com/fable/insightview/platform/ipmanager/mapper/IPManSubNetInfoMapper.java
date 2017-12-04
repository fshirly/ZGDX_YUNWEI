package com.fable.insightview.platform.ipmanager.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.platform.ipmanager.entity.IPManSubNetInfoBean;
import com.fable.insightview.platform.page.Page;

public interface IPManSubNetInfoMapper {
	//获得所有的子网信息
	List<IPManSubNetInfoBean> getAllSubNetInfo();
	
	/**
	 * 根据条件查询
	 */
	List<IPManSubNetInfoBean> selectSubNetInfo(Page<IPManSubNetInfoBean> page);
	
	/**
	 * 根据id获得子网信息
	 */
	IPManSubNetInfoBean getSubnetInfoByID(int subNetId);
	
	/**
	 * 根据ip和子网掩码获得子网信息
	 */
	List<IPManSubNetInfoBean> getByIPAndSubNetMark(IPManSubNetInfoBean bean);
	
	/**
	 * 新增子网
	 */
	int insertSubnetInfo(IPManSubNetInfoBean bean);
	
	/**
	 * 根据id更新
	 */
	int updateSubnetInfoByID(IPManSubNetInfoBean bean);
	
	/**
	 * 删除
	 */
	boolean delSubNetInfoById(int subNetId);
	
	/**
	 * 批量删除
	 */
	boolean delSubNetInfos(List<Integer> subNetIds);
	
	List<IPManSubNetInfoBean> getByIpAddress(@Param("ipAddress")String ipaddress);
	
	List<IPManSubNetInfoBean> getBySubnetAndDept(@Param("subNetId")int subNetId,@Param("deptName")String deptName);
	
	/**
	 * 
	 * 根据条件获得所有子网
	 */
	public List<IPManSubNetInfoBean> getAllSubnetByCondition(IPManSubNetInfoBean bean);
}
