package com.fable.insightview.platform.vlan.mapper;

import java.util.List;

import com.fable.insightview.platform.common.util.KeyValPair;
import com.fable.insightview.platform.vlan.entity.VlanInfoBean;

public interface VlanInfoMapper {

	int insertVlanInfo(VlanInfoBean vlanInfo);
	
	List<VlanInfoBean> getVlanInfo();
	
	VlanInfoBean getVlanByVlanNo(String vlanNo);
	
	//查询所有的vlan号
	List<KeyValPair<Integer, String>> queryVlan();
}
