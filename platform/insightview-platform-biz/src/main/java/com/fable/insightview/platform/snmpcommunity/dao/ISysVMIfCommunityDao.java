package com.fable.insightview.platform.snmpcommunity.dao;

import java.util.List;

import com.fable.insightview.platform.itsm.core.dao.GenericDao;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.snmpcommunity.entity.MODeviceBean;
import com.fable.insightview.platform.snmpcommunity.entity.SysVMIfCommunityBean;

public interface ISysVMIfCommunityDao extends GenericDao<SysVMIfCommunityBean> {

	/**
	 * 查询SNMP 列表
	 * 
	 * @param authBean
	 * @param flexiGridPageInfo
	 * @return
	 */
	List<SysVMIfCommunityBean> getSysAuthCommunityByConditions(
			SysVMIfCommunityBean authBean, FlexiGridPageInfo flexiGridPageInfo,
			String type);

	/**
	 * 查询总个数
	 * 
	 * @param authBean
	 * @return
	 */
	int getTotalCount(SysVMIfCommunityBean authBean, String type);

	/**
	 * 根据ID查询SNMP信息
	 * 
	 * @param id
	 * @return
	 */
	List<SysVMIfCommunityBean> getSysAuthCommunityByConditions(
			SysVMIfCommunityBean authBean);

	/**
	 * 修改
	 * 
	 * @param authBean
	 * @return
	 */
	boolean updateSysAuthCommunity(SysVMIfCommunityBean authBean);

	/**
	 * 添加
	 * 
	 * @param authBean
	 * @return
	 */
	boolean addSysAuthCommunity(SysVMIfCommunityBean authBean);

	/**
	 * 删除
	 * 
	 * @param authBean
	 * @return
	 */
	boolean delSysAuthCommunityById(SysVMIfCommunityBean authBean);

	List<String> getMoIDBymoName(String moName);

	List<MODeviceBean> getDeviceById(int moId);// 根据设备ID查询设备信息

	List<SysVMIfCommunityBean> getSysAuthCommunityById(int id);// 根据ID查询信息

	public boolean checkDeviceIP(SysVMIfCommunityBean authBean);// 修改判断IP
	
	SysVMIfCommunityBean getObjFromDeviceIP(SysVMIfCommunityBean vo);
	
	/**
	 * 根据ip模糊查询
	 */
	List<SysVMIfCommunityBean> getByIp(String ip);
}
