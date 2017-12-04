package com.fable.insightview.monitor.snmpcommunitytemp.mapper;

import java.util.List;


import com.fable.insightview.monitor.snmpcommunitytemp.entity.SNMPCommunityTempBean;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.snmpcommunity.entity.SNMPCommunityBean;

public interface SNMPCommunityTempMapper {
	
	//新增
	int insertSNMPCommunityTemp(SNMPCommunityTempBean bean);
	
	List<SNMPCommunityBean> listSnmpAndSnmpTem(Page<SNMPCommunityTempBean> page);
	
	/**
	 * 根据ip获得凭证信息
	 */
	SNMPCommunityTempBean getSnmpTempByIP(String deviceIp);
}
