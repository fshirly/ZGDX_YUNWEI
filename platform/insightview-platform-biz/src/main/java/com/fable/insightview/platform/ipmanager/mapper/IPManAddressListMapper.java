package com.fable.insightview.platform.ipmanager.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.platform.ipmanager.entity.IPManAddressListBean;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.provider.entity.ProviderInfoBean;

public interface IPManAddressListMapper {
	
	/**
	 * 子网空闲数据
	 */
	List<IPManAddressListBean> selectFreeSubNetInfo(Page<IPManAddressListBean> page);
	
	/**
	 * 获得所有子网下的空闲ip
	 */
	List<IPManAddressListBean> getAllFreeSubNetInfo(int subNetId);
	
	/**
	 * 点击子网下部门的数据
	 */
	List<IPManAddressListBean> selectAllSubnetDeptInfo(Page<IPManAddressListBean> page);
	
	/**
	 * 获得子网下的部门的所有ip
	 */
	List<IPManAddressListBean> getAllSubNetDeptInfo(@Param("subNetId")int subNetId,@Param("deptId")int deptId);
	
	/**
	 * 新增
	 */
	int insertAddress(IPManAddressListBean bean);
	
	/**
	 * 根据id更新
	 */
	int updateAddressByID(IPManAddressListBean bean);
	
	/**
	 * 根据ids批量更新
	 */
	int updateAddressByIDs(IPManAddressListBean bean);
	
	/**
	 * 获得子网id获得所有IP地址
	 */
	List<IPManAddressListBean> getAllBySubNetIdAndDept(int subNetId);
	
	/**
	 * 根据id更新(部门设为空)
	 */
	int updateAddressByID2(IPManAddressListBean bean);
	
	/**
	 * 根据子网id删除IP地址
	 */
	boolean delBySubNetId(int subNetId);
	
	/**
	 * 根据子网和部门更新
	 */
	int updateBySubNetAndDept(IPManAddressListBean bean);
	
	/**
	 * 根据ip地址段及子网id
	 */
	List<IPManAddressListBean> getByIPScopeAndSubNetId(IPManAddressListBean bean);
	
	/**
	 * 根据IP地址获得详情
	 */
	IPManAddressListBean getByIPAddress(String ipAddress);
	
	/**
	 * 根据地址段获得IP地址
	 */
	List<IPManAddressListBean> getAddressByIPScope(IPManAddressListBean bean);
	
	/**
	 * 根据范围及状态获得数量
	 */
	int getCountByScopeAndStatus(IPManAddressListBean bean);
	
	/**
	 * 批量更新
	 */
	int batchUpdate(@Param("ids")String ids,@Param("subNetId")int subNetId);
	
	/**
	 * 更新为保留地址
	 */
	int updateAddressByID3(IPManAddressListBean bean);
	
	/**
	 * 根据范围、部门及状态获得数量
	 */
	int getCountByScopeAndStatusAndDept(IPManAddressListBean bean);
	
	/**
	 * 获得部门下的所有子网
	 */
	List<IPManAddressListBean> getAllSubNetByDept(int deptId);
	
	/**
	 * 点击部门，获得该该部门下子网情况
	 */
	List<IPManAddressListBean> listDeptUseSubNet(Page<IPManAddressListBean> page);
	
	/**
	 * 根据id更新状态
	 */
	int updateStatusById(IPManAddressListBean bean);
	
	/**
	 * 根据id获得ip地址
	 */
	IPManAddressListBean getById(int id);
	
	int getCountByIdsAndStatus(IPManAddressListBean bean);
	
	public int batchInsertAddress(List<IPManAddressListBean> item);
	
	List<IPManAddressListBean> getFreeAddress(IPManAddressListBean bean);
	
	IPManAddressListBean getAddressInfoById(int id);
	
	int updateNoteAndUserByID(IPManAddressListBean bean);
	
	/**
	 * 更新保留地址的状态
	 */
	int updateReservedAddressByID(IPManAddressListBean bean);
	
	List<IPManAddressListBean> getBySubNetIdAndDeptAndStatus(IPManAddressListBean bean);
	
	boolean delBySubNetId2(int subNetId);
	
	/**
	 *  查询部门下所有空闲的IP信息
	 * @param deptId
	 * @return
	 */
	List<IPManAddressListBean> getByDeptIdAndStatus(Page<IPManAddressListBean> page);

	IPManAddressListBean getByDeptIdAndUserId(@Param("deptId")Integer deptId, @Param("userId")Integer userId);

	List<IPManAddressListBean> getByUserId(Page<IPManAddressListBean> page);

	IPManAddressListBean getAddressInfoByOfficeID(Integer officeID);

	List<IPManAddressListBean> getByUserId2(@Param("userId")Integer userId, @Param("netType")Integer netType);
	
	ProviderInfoBean getOutDeptByUserId(@Param("userId")Integer userId);
}
