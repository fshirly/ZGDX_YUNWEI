package com.fable.insightview.monitor.snmpcommunitytemp.service;

import java.util.List;


import com.fable.insightview.monitor.snmpcommunitytemp.entity.SNMPCommunityTempBean;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.snmpcommunity.entity.SNMPCommunityBean;

public interface ISNMPCommunityTempService {
	//新增
	boolean insertSNMPCommunityTemp(SNMPCommunityTempBean bean);
	
	List<SNMPCommunityBean> listSnmpAndSnmpTem(Page<SNMPCommunityTempBean> page);
	
	/**
	 * 根据ip获得凭证信息
	 */
	SNMPCommunityTempBean getSnmpTempByIP(String deviceIp);
}
