package com.fable.insightview.monitor.sysdbmscommunity.mapper;

import java.util.List;

import com.fable.insightview.platform.page.Page;
import com.fable.insightview.monitor.sysdbmscommunity.entity.SysDBMSCommunityBean;

public interface SysDBMSCommunityMapper {
	/* 新增数据库验证 */
	int insertDBMSCommunity(SysDBMSCommunityBean bean);

	int getByIPAndTypeAndPort(SysDBMSCommunityBean bean);

	List<SysDBMSCommunityBean> getByIP(SysDBMSCommunityBean bean);

	int getByIDAndIPAndTypeAndPort(SysDBMSCommunityBean bean);

	/* 更新 */
	int updateDBMSCommunity(SysDBMSCommunityBean bean);

	SysDBMSCommunityBean getDBMSByTaskId(int taskId);

	SysDBMSCommunityBean getDBMSByID(int moId);

	List<SysDBMSCommunityBean> getDBMSInfo(Page<SysDBMSCommunityBean> page);

	int deleteByPrimaryKey(int id);

	SysDBMSCommunityBean getInfoByID(int id);

	int deleteByIDs(String id);

	boolean delDBMS(List<Integer> ids);

	List<SysDBMSCommunityBean> getByIPAndPort(SysDBMSCommunityBean bean);

	/* 根据ID更新 */
	int updateDBMSCommunityByID(SysDBMSCommunityBean bean);
	
	List<SysDBMSCommunityBean> getByConditions(SysDBMSCommunityBean bean);
	
	List<SysDBMSCommunityBean> getByIPAndTypeAndPortAndName(SysDBMSCommunityBean bean);
	
	int getByIDAndIPAndTypeAndPortAndName(SysDBMSCommunityBean bean);
	
	SysDBMSCommunityBean getDBMSByTaskIdAndDBName(int taskId);
	
	int updateDBMSCommunity2(SysDBMSCommunityBean bean);
}
