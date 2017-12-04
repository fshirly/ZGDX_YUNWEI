package com.fable.insightview.platform.ipmanager.service;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.fable.insightview.platform.ipmanager.entity.IPManAddressListBean;
import com.fable.insightview.platform.ipmanager.entity.IPManSubNetInfoBean;
import com.fable.insightview.platform.ipmanager.entity.IPManSubNetRDeptBean;
import com.fable.insightview.platform.ipmanager.entity.SubnetDeptTreeBean;
import com.fable.insightview.platform.ipmanager.entity.SubnetExportBean;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.provider.entity.ProviderInfoBean;

/**
 * 子网视图
 * 
 */
public interface ISubnetViewService {
	// 获得子网本门树
	public Map<String, Object> getSubnetAndDeptVal();

	List<IPManSubNetInfoBean> selectSubNetInfo(Page<IPManSubNetInfoBean> page);

	/**
	 * 根据id获得子网信息
	 */
	IPManSubNetInfoBean getSubnetInfoByID(int subNetId);

	List<IPManSubNetInfoBean> getAllSubNetInfo();

	public SubnetExportBean refExportBean(SubnetExportBean exportBean);

	// 导出
	public void exportExcel(HttpServletResponse response,
			SubnetExportBean exportEntity);

	public void exportExcel(SubnetExportBean exportEntity, OutputStream out);

	/**
	 * 子网空闲数据
	 */
	List<IPManAddressListBean> selectFreeSubNetInfo(
			Page<IPManAddressListBean> page);

	List<IPManAddressListBean> getAllFreeSubNetInfo(int subNetId);

	// 点击子网展示的列表数据
	List<IPManSubNetRDeptBean> listSubnetConDept(Page<IPManSubNetRDeptBean> page);

	// 获得点击子网的导出数据
	List<IPManSubNetRDeptBean> getAllSubNetRDeptInfo2(int subNetId);

	/**
	 * 点击子网下部门的数据
	 */
	List<IPManAddressListBean> selectAllSubnetDeptInfo(
			Page<IPManAddressListBean> page);

	/**
	 * 获得子网下的部门的所有ip
	 */
	List<IPManAddressListBean> getAllSubNetDeptInfo(int subNetId, int deptId);

	/**
	 * 验证新增的子网是否重复
	 */
	Map<String, Object> checkAddSubnet(IPManSubNetInfoBean bean);

	/**
	 * 新增子网
	 */
	boolean doAddSubnet(IPManSubNetInfoBean bean);

	/**
	 * 空闲分配到子网
	 * 
	 */
	boolean doFreeAssignDept(IPManAddressListBean addressListBean);

	/**
	 * 验证子网是否被使用
	 * 
	 */
	boolean checkBeforeDel(int subNetId);

	/**
	 * 删除子网
	 */
	boolean delSubNetInfoById(int subNetId);

	/**
	 * 批量删除子网
	 */
	boolean delSubNetInfos(List<Integer> subNetIds);
	
	/**
	 * ip收回到子网
	 * 
	 */
	Map<String, Object> doWithdraw(IPManAddressListBean addressListBean, String ids);
	
	/**
	 * 根据子网id删除IP地址
	 */
	boolean delBySubNetId(int subNetId);
	
	/**
	 * 从子网收回部门
	 * 
	 */
	Map<String, Object> doWithdrawDept(String deptIds,int subNetId);
	
	/**
	 * 判断输入的ip是否属于该子网段
	 */
	public boolean isInSubNet(int subNetId, String checkIp);
	
	/**
	 * 判断ip地址段中的ip空闲使用情况
	 */
	public Map<String, Object> checkIsAllFree(IPManAddressListBean addressListBean);
	
	/**
	 * 子网拆分预览详情
	 */
	public Map<String, Object> doPreviewSplitSubnet(String ipAddress,String subNetMark,int splitNum);
	
	/**
	 * 子网拆分
	 */
	public boolean doSplit(int subNetId,String ipAddress,String subNetMark,int splitNum);
	
	SubnetDeptTreeBean searchTreeNodes(String treeName);
	
	/**
	 * 是否能够拆分
	 */
	public boolean isSplit(int subNetId,String ipAddress,String subNetMark,int splitNum);
	
	/**
	 * 
	 * 根据条件获得所有子网
	 */
	public int getSubnetCount(IPManSubNetInfoBean bean);
	
	public String getFreeIds(IPManAddressListBean bean);
	
	/**
	 * 更新子网
	 */
	boolean doUpdateSubnet(IPManSubNetInfoBean bean);
	
	/**
	 * 删除子网部门关系
	 */
	boolean delSubNetDeptBySubNetID(int subNetId);
	
	/**
	 * 查询部门下所有空闲的IP信息
	 * @param deptId
	 * @return
	 */
	List<IPManAddressListBean> getByDeptIdAndStatus(Page<IPManAddressListBean> page);
	
	/**
	 * 查询用户在用的IP信息
	 * @param deptId
	 * @return
	 */
	List<IPManAddressListBean> getByUserId(Page<IPManAddressListBean> page);
	
	IPManAddressListBean getByDeptIdAndUserId(Integer deptId, Integer userId);
	
	/**
	 * 根据IP获取对象
	 * @param ipAddress
	 * @return
	 */
	IPManAddressListBean getByIPAddress(String ipAddress);
	
	/**
	 * 更新IPManAddressListBean
	 * @param bean
	 * @return
	 */
	boolean updateAddressByID(IPManAddressListBean bean);

	public List<IPManAddressListBean> getByUserId(Integer userId, Integer netType);

	/**
	 * 获取外单位使用人的外单位
	 * @param userId
	 * @return
	 */
	public ProviderInfoBean getOutDeptByUserId(Integer userId);
	
}
