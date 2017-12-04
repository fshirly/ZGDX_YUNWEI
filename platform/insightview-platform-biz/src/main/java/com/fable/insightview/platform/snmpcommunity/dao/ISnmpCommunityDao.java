package com.fable.insightview.platform.snmpcommunity.dao;

import java.util.List;

import com.fable.insightview.platform.itsm.core.dao.GenericDao;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.snmpcommunity.entity.MODeviceBean;
import com.fable.insightview.platform.snmpcommunity.entity.SNMPCommunityBean;

public interface ISnmpCommunityDao extends GenericDao<SNMPCommunityBean> {

	/**
	 * 查询SNMP 列表
	 * 
	 * @param snmpBean
	 * @param flexiGridPageInfo
	 * @return
	 */
	List<SNMPCommunityBean> getSnmpCommunityByConditions(
			SNMPCommunityBean snmpBean, FlexiGridPageInfo flexiGridPageInfo);

	/**
	 * 查询总个数
	 * 
	 * @param snmpBean
	 * @return
	 */
	int getTotalCount(SNMPCommunityBean snmpBean);

	/**
	 * 根据ID查询SNMP信息
	 * 
	 * @param id
	 * @return
	 */
	boolean getSnmpCommunityByConditions(SNMPCommunityBean snmpBean);

//	public List<SNMPCommunityBean> checkDeviceIP(SNMPCommunityBean snmpBean);

	List<SNMPCommunityBean> findSnmpCommunityByID(int id);

	/**
	 * 修改
	 * 
	 * @param snmpBean
	 * @return
	 */
	boolean updateSnmpCommunity(SNMPCommunityBean snmpBean);

	/**
	 * 添加
	 * 
	 * @param snmpBean
	 * @return
	 */
	boolean addSnmpCommunity(SNMPCommunityBean snmpBean);

	/**
	 * 删除
	 * 
	 * @param snmpBean
	 * @return
	 */
	boolean delSnmpCommunityById(SNMPCommunityBean snmpBean);

	List<MODeviceBean> getMoIDBymoName(String moName);
	
//	SNMPCommunityBean getObjFromDeviceIP(SNMPCommunityBean snmpBean);

}
