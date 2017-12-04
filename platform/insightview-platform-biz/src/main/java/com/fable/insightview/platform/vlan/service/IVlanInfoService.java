package com.fable.insightview.platform.vlan.service;

import java.util.List;

import com.fable.insightview.platform.common.util.KeyValPair;
import com.fable.insightview.platform.vlan.entity.VlanInfoBean;

public interface IVlanInfoService {

	//查询所有的vlan信息
	List<VlanInfoBean> getVlanInfo();
	
	//增加vlan信息
	int addVlanInfo(VlanInfoBean vlanInfo);
	
	//根据vlan号获取vlan
	VlanInfoBean getVlanByVlanNo(String vlanNo);
	
	//查询所有的vlan号
	List<KeyValPair<Integer, String>> queryVlan();
}
