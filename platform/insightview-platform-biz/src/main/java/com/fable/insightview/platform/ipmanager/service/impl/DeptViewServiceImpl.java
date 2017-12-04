package com.fable.insightview.platform.ipmanager.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.dao.IDepartmentDao;
import com.fable.insightview.platform.dao.IOrganizationDao;
import com.fable.insightview.platform.dao.ISysUserDao;
import com.fable.insightview.platform.dict.util.DictionaryLoader;
import com.fable.insightview.platform.entity.DepartmentBean;
import com.fable.insightview.platform.entity.OrgDeptProviderTreeBean;
import com.fable.insightview.platform.entity.OrganizationBean;
import com.fable.insightview.platform.entity.SysEmploymentBean;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.ipmanager.entity.IPManAddressListBean;
import com.fable.insightview.platform.ipmanager.entity.IPManSubNetInfoBean;
import com.fable.insightview.platform.ipmanager.entity.IPManSubNetRDeptBean;
import com.fable.insightview.platform.ipmanager.mapper.IPManAddressListMapper;
import com.fable.insightview.platform.ipmanager.mapper.IPManSubNetInfoMapper;
import com.fable.insightview.platform.ipmanager.mapper.IPManSubNetRDeptMapper;
import com.fable.insightview.platform.ipmanager.service.IDeptViewService;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.service.IDepartmentService;
@Service
public class DeptViewServiceImpl implements IDeptViewService {
	private final Logger logger = LoggerFactory.getLogger(DeptViewServiceImpl.class);
	//预占状态
	private static final int PREEMPT_STATUS = 2;
	//使用状态
	private static final int USED_STATUS = 3;
	@Autowired
	IPManSubNetRDeptMapper subNetRDeptMapper;
	@Autowired
	IPManAddressListMapper addressListMapper;
	@Autowired
	IOrganizationDao organizationDao;
	@Autowired
	IDepartmentDao departmentDao;
	@Autowired
	IPManSubNetInfoMapper subNetInfoMapper;
	@Autowired
	protected ISysUserDao sysUserDao;
	
	@Override
	public List<IPManSubNetRDeptBean> listOrgUseSubNet(
			Page<IPManSubNetRDeptBean> page) {
		List<IPManSubNetRDeptBean> subNetRDeptLst = subNetRDeptMapper.getDeptByCondition(page);
		for (int i = 0; i < subNetRDeptLst.size(); i++) {
			IPManSubNetRDeptBean bean = subNetRDeptMapper.getUseNumAndPreemptNum(subNetRDeptLst.get(i).getDeptId());
			subNetRDeptLst.get(i).setUsedNum(bean.getUsedNum());
			subNetRDeptLst.get(i).setPreemptNum(bean.getPreemptNum());
		}
		return subNetRDeptLst;
	}

	@Override
	public List<IPManAddressListBean> getAllSubNet(int deptId) {
		return addressListMapper.getAllSubNetByDept(deptId);
	}

	@Override
	public List<IPManAddressListBean> listDeptUseSubNet(
			Page<IPManAddressListBean> page) {
		return addressListMapper.listDeptUseSubNet(page);
	}

	@Override
	public boolean doGiveToDevice(IPManAddressListBean addressListBean) {
		logger.info("分配到设备的IP地址的id："+addressListBean.getId()+",所属子网为："+addressListBean.getSubNetId());
		boolean updateAddress = false;
		boolean updateSubnetRDept = false;
		boolean updateSubnet = false;
		
		// 更新ip地址状态为占用
		addressListBean.setStatus(USED_STATUS);
		try {
			if(addressListBean.getUserId() == -1){
				addressListBean.setUserId(null);
			}
			addressListMapper.updateAddressByID3(addressListBean);
			updateAddress = true;
		} catch (Exception e) {
			logger.error("更新IP地址状态异常："+e);
		}
		//更新子网部门关系预占数(-1)与占用地址数(+1)
		IPManSubNetRDeptBean subNetRDept = new IPManSubNetRDeptBean();
		subNetRDept.setDeptId(addressListBean.getDeptId());
		subNetRDept.setSubNetId(addressListBean.getSubNetId());
		IPManSubNetRDeptBean subNetRDeptBean = subNetRDeptMapper.getBySubNetAndDept(subNetRDept).get(0);
		subNetRDeptBean.setUsedNum(subNetRDeptBean.getUsedNum() + 1);
		subNetRDeptBean.setPreemptNum(subNetRDeptBean.getPreemptNum() - 1);
		try {
			subNetRDeptMapper.updateSubNetRDept(subNetRDeptBean);
			updateSubnetRDept = true;
		} catch (Exception e) {
			logger.error("更新子网部门关系异常："+e);
		}
		
		//更新子网信息表关系预占数(-1)与占用地址数(+1)
		IPManSubNetInfoBean subNetInfoBean = subNetInfoMapper.getSubnetInfoByID(addressListBean.getSubNetId());
		subNetInfoBean.setPreemptNum(subNetInfoBean.getPreemptNum() - 1);
		subNetInfoBean.setUsedNum(subNetInfoBean.getUsedNum() + 1);
		try {
			subNetInfoMapper.updateSubnetInfoByID(subNetInfoBean);
			updateSubnet = true;
		} catch (Exception e) {
			logger.error("更新子网信息异常："+e);
		}
		
		if(updateAddress == true && updateSubnetRDept == true && updateSubnet == true){
			return true;
		}
		return false;
	}

	@Override
	public boolean doWithdrawToDept(IPManAddressListBean addressListBean) {
		logger.info("收回到部门的IP地址的id："+addressListBean.getId()+",所属子网为："+addressListBean.getSubNetId());
		boolean updateAddress = false;
		boolean updateSubnetRDept = false;
		boolean updateSubnet = false;
		
		// 更新ip地址状态为预占
		addressListBean.setStatus(PREEMPT_STATUS);
		try {
			addressListMapper.updateAddressByID3(addressListBean);
			updateAddress = true;
		} catch (Exception e) {
			logger.error("更新IP地址状态异常："+e);
		}
		//更新子网部门关系预占数(+1)与占用地址数(-1)
		IPManSubNetRDeptBean subNetRDept = new IPManSubNetRDeptBean();
		subNetRDept.setDeptId(addressListBean.getDeptId());
		subNetRDept.setSubNetId(addressListBean.getSubNetId());
		IPManSubNetRDeptBean subNetRDeptBean = subNetRDeptMapper.getBySubNetAndDept(subNetRDept).get(0);
		subNetRDeptBean.setUsedNum(subNetRDeptBean.getUsedNum() - 1);
		subNetRDeptBean.setPreemptNum(subNetRDeptBean.getPreemptNum() + 1);
		try {
			subNetRDeptMapper.updateSubNetRDept(subNetRDeptBean);
			updateSubnetRDept = true;
		} catch (Exception e) {
			logger.error("更新子网部门关系异常："+e);
		}
		
		//更新子网信息表关系预占数(+1)与占用地址数(-1)
		IPManSubNetInfoBean subNetInfoBean = subNetInfoMapper.getSubnetInfoByID(addressListBean.getSubNetId());
		subNetInfoBean.setPreemptNum(subNetInfoBean.getPreemptNum() + 1);
		subNetInfoBean.setUsedNum(subNetInfoBean.getUsedNum() - 1);
		try {
			subNetInfoMapper.updateSubnetInfoByID(subNetInfoBean);
			updateSubnet = true;
		} catch (Exception e) {
			logger.error("更新子网信息异常："+e);
		}
		
		if(updateAddress == true && updateSubnetRDept == true && updateSubnet == true){
			return true;
		}
		return false;
	}

	@Override
	public Map<String, Object> doBatchGiveToDevice(String ids) {
		boolean updateFlag = false;
		boolean isReserve = false;
		String ipAddress ="";
		//能够分配的IP地址(status=2 预占)
		List<IPManAddressListBean> assignAddressLst = new ArrayList<IPManAddressListBean>();
		//不能分配的IP地址
		List<IPManAddressListBean> reserveAddressLst = new ArrayList<IPManAddressListBean>();
		String[] splits = ids.split(",");
		for (int i = 0; i < splits.length; i++) {
			IPManAddressListBean addressListBean = addressListMapper.getById(Integer.parseInt(splits[i]));
			if(addressListBean.getStatus() == 2){
				assignAddressLst.add(addressListBean);
			}else{
				reserveAddressLst.add(addressListBean);
			}
		}
		
		//分配能够分配的IP地址
		for (int i = 0; i < assignAddressLst.size(); i++) {
			updateFlag = this.doGiveToDevice(assignAddressLst.get(i));
		}
		
		//获得不能分配的IP地址
		for (int i = 0; i < reserveAddressLst.size(); i++) {
			String ip = reserveAddressLst.get(i).getIpAddress();
			if((i + 1) % 2 == 0){
				ipAddress = ipAddress + ip + "," + "<br/>";
			}else{
				ipAddress = ipAddress + ip + ",";
			}
		}
		if(ipAddress.length() > 0){
			updateFlag = false;
			isReserve = true;
			ipAddress = ipAddress.substring(0,ipAddress.lastIndexOf(","));
		}
		ipAddress = "<div style='float:left'>【"+ipAddress+"】</div>";
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("updateFlag", updateFlag);
		result.put("isReserve", isReserve);
		result.put("ipAddress", ipAddress);
		return result;
	}

	@Override
	public Map<String, Object> doBatchWithdrawToDept(String ids) {
		boolean updateFlag = false;
		boolean isReserve = false;
		String ipAddress ="";
		//能够收回的IP地址(status=3占用)
		List<IPManAddressListBean> withdrawAddressLst = new ArrayList<IPManAddressListBean>();
		//不能收回的IP地址
		List<IPManAddressListBean> reserveAddressLst = new ArrayList<IPManAddressListBean>();
		String[] splits = ids.split(",");
		for (int i = 0; i < splits.length; i++) {
			IPManAddressListBean addressListBean = addressListMapper.getById(Integer.parseInt(splits[i]));
			if(addressListBean.getStatus() == USED_STATUS){
				withdrawAddressLst.add(addressListBean);
			}else{
				reserveAddressLst.add(addressListBean);
			}
		}
		
		//收回能够收回的IP地址
		for (int i = 0; i < withdrawAddressLst.size(); i++) {
			updateFlag = this.doWithdrawToDept(withdrawAddressLst.get(i));
		}
		
		//获得不能收回的IP地址
		for (int i = 0; i < reserveAddressLst.size(); i++) {
			String ip = reserveAddressLst.get(i).getIpAddress();
			if((i + 1) % 2 == 0){
				ipAddress = ipAddress + ip + "," + "<br/>";
			}else{
				ipAddress = ipAddress + ip + ",";
			}
		}
		if(ipAddress.length() > 0){
			updateFlag = false;
			isReserve = true;
			ipAddress = ipAddress.substring(0,ipAddress.lastIndexOf(","));
		}
		ipAddress = "<div style='float:left'>【"+ipAddress+"】</div>";
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("updateFlag", updateFlag);
		result.put("isReserve", isReserve);
		result.put("ipAddress", ipAddress);
		return result;
	}

	@Override
	public OrgDeptProviderTreeBean searchTreeNodes(String treeName,int firstOrgId) {
		OrgDeptProviderTreeBean treeBean = new OrgDeptProviderTreeBean();
		OrganizationBean org = organizationDao.getOrganizationBeanByOrgName(treeName);
		String[] orgIds = org.getNodeID().split(",");
		if(orgIds.length > 1){
			treeBean.setId("O" + orgIds[1]);
		}else{
			DepartmentBean dept = new DepartmentBean();
			dept.setOrganizationID(firstOrgId);
			dept.setDeptName(treeName);
			List<DepartmentBean> deptLst = departmentDao.getByOrgAndDeptName(dept);
			if (deptLst.size() > 0) {
				treeBean.setId("D" + deptLst.get(0).getDeptId());
			}
		}
		return treeBean;
	}

	@Override
	public IPManAddressListBean getAddressInfoById(int id) {
		IPManAddressListBean bean = addressListMapper.getAddressInfoById(id);
		Map<Integer, String> networkTypeMap = DictionaryLoader.getConstantItems("networkType");
		String subNetTypeName = networkTypeMap.get(bean.getSubNetType());
		bean.setSubNetTypeName(subNetTypeName);
		return bean;
	}

	@Override
	public boolean updateNoteByID(IPManAddressListBean bean) {
		logger.info("更新的ip地址id为：" + bean.getId() + ",备注信息为：" + bean.getNote() + ",使用人为：" + bean.getUserId());
		try {
			addressListMapper.updateNoteAndUserByID(bean);
			return true;
		} catch (Exception e) {
			logger.error("更新ip地址异常："+e);
		}
		return false;
	}

	@Override
	public List<SysEmploymentBean> getDeptUsers(int deptId) {
		DepartmentBean departmentBean = departmentDao.getById(deptId);
		if(departmentBean.getOrganizationID() == null){
			departmentBean.setOrganizationID(0);
		}
		List<SysEmploymentBean> empList = departmentDao.getEmploymentListByOrgID(departmentBean);
		for (int i = 0; i < empList.size(); i++) {
			String userName = sysUserDao.getById(empList.get(i).getUserId()).getUserName();
			empList.get(i).setUserName(userName);
		}
		return empList;
	}
}
