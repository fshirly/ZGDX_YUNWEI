package com.fable.insightview.platform.snmpcommunity.dao;

import java.util.List;

import com.fable.insightview.platform.entity.SysRoleBean;
import com.fable.insightview.platform.itsm.core.dao.GenericDao;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.snmpcommunity.entity.MODeviceBean;
import com.fable.insightview.platform.snmpcommunity.entity.SNMPCommunityBean;
import com.fable.insightview.platform.snmpcommunity.entity.SysAuthCommunityBean;

public interface ISysAuthCommunityDao extends GenericDao<SysAuthCommunityBean> {

	/**
	 * 查询SNMP 列表
	 * 
	 * @param authBean
	 * @param flexiGridPageInfo
	 * @return
	 */
	List<SysAuthCommunityBean> getSysAuthCommunityByConditions(
			SysAuthCommunityBean authBean, FlexiGridPageInfo flexiGridPageInfo,
			String type);

	/**
	 * 查询总个数
	 * 
	 * @param authBean
	 * @return
	 */
	int getTotalCount(SysAuthCommunityBean authBean, String type);

	/**
	 * 根据ID查询SNMP信息
	 * 
	 * @param id
	 * @return
	 */
	List<SysAuthCommunityBean> getSysAuthCommunityByConditions(
			SysAuthCommunityBean authBean);

	/**
	 * 修改
	 * 
	 * @param authBean
	 * @return
	 */
	boolean updateSysAuthCommunity(SysAuthCommunityBean authBean);

	/**
	 * 添加
	 * 
	 * @param authBean
	 * @return
	 */
	boolean addSysAuthCommunity(SysAuthCommunityBean authBean);

	/**
	 * 删除
	 * 
	 * @param authBean
	 * @return
	 */
	boolean delSysAuthCommunityById(SysAuthCommunityBean authBean);

	List<String> getMoIDBymoName(String moName);

	List<MODeviceBean> getDeviceById(int moId);// 根据设备ID查询设备信息

	List<SysAuthCommunityBean> getSysAuthCommunityById(int id);// 根据ID查询信息

	public boolean checkDeviceIP(SysAuthCommunityBean authBean);// 修改判断IP

	public SysAuthCommunityBean getObjFromDeviceIP(SysAuthCommunityBean authBean);
}
