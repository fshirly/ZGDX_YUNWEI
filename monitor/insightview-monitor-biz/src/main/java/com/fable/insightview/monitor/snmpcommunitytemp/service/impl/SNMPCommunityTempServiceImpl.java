package com.fable.insightview.monitor.snmpcommunitytemp.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.snmpcommunitytemp.entity.SNMPCommunityTempBean;
import com.fable.insightview.monitor.snmpcommunitytemp.mapper.SNMPCommunityTempMapper;
import com.fable.insightview.monitor.snmpcommunitytemp.service.ISNMPCommunityTempService;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.snmpcommunity.entity.SNMPCommunityBean;

@Service
public class SNMPCommunityTempServiceImpl implements ISNMPCommunityTempService{
	private static final Logger logger = LoggerFactory.getLogger(SNMPCommunityTempServiceImpl.class);
	
	@Autowired
	SNMPCommunityTempMapper snmpCommunityTempMapper;

	@Override
	public boolean insertSNMPCommunityTemp(SNMPCommunityTempBean bean) {
		try {
			snmpCommunityTempMapper.insertSNMPCommunityTemp(bean);
			return true;
		} catch (Exception e) {
			logger.error("新增snmp临时凭证异常："+e);
		}
		return false;
	}

	@Override
	public List<SNMPCommunityBean> listSnmpAndSnmpTem(Page<SNMPCommunityTempBean> page) {
		return snmpCommunityTempMapper.listSnmpAndSnmpTem(page);
	}

	@Override
	public SNMPCommunityTempBean getSnmpTempByIP(String deviceIp) {
		return snmpCommunityTempMapper.getSnmpTempByIP(deviceIp);
	}

}
