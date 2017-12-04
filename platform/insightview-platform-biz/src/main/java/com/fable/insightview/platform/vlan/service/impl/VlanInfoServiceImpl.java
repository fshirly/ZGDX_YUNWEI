package com.fable.insightview.platform.vlan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.common.util.KeyValPair;
import com.fable.insightview.platform.vlan.entity.VlanInfoBean;
import com.fable.insightview.platform.vlan.mapper.VlanInfoMapper;
import com.fable.insightview.platform.vlan.service.IVlanInfoService;

@Service
public class VlanInfoServiceImpl implements IVlanInfoService{

	@Autowired
	private VlanInfoMapper vlanInfoMapper;

	@Override
	public int addVlanInfo(VlanInfoBean vlanInfo) {
		return this.vlanInfoMapper.insertVlanInfo(vlanInfo);
	}

	@Override
	public List<VlanInfoBean> getVlanInfo() {
		return this.vlanInfoMapper.getVlanInfo();
	}

	@Override
	public VlanInfoBean getVlanByVlanNo(String vlanNo) {
		return this.vlanInfoMapper.getVlanByVlanNo(vlanNo);
	}

	@Override
	public List<KeyValPair<Integer, String>> queryVlan() {
		return this.vlanInfoMapper.queryVlan();
	}

}
