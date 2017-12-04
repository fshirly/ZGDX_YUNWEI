package com.fable.insightview.monitor.sysroomcommunity.service;

import java.util.List;

import com.fable.insightview.platform.page.Page;
import com.fable.insightview.monitor.sysroomcommunity.entity.SysRoomCommunityBean;

public interface ISysRoomCommunityService {

	/* 新增3D机房认证 */
	boolean insertRoomCommunity(SysRoomCommunityBean sysRoomCommunityBean);

	/* 更新数据库验证 */
	boolean updateCommunityByID(SysRoomCommunityBean sysRoomCommunityBean);

	/* 更新时获取初始化信息 */
	SysRoomCommunityBean getCommunityByID(int id);

	/* 查询机房监控列表 */
	List<SysRoomCommunityBean> getRoomCommunityByConditions(
			Page<SysRoomCommunityBean> page);

	boolean checkCommunity(String flag,
			SysRoomCommunityBean sysRoomCommunityBean);

	boolean delRoomCommunity(List<Integer> ids);

	/* 根据IP获得凭证信息 */
	SysRoomCommunityBean getRoomCommunityByIP(SysRoomCommunityBean bean);

	boolean updateCommunityByIP(SysRoomCommunityBean bean);

	boolean isExistRoom(SysRoomCommunityBean bean);

	/* 根据采集任务获得凭证 */
	SysRoomCommunityBean getRoomByTask(int taskId);

	/* 根据IP获得凭证信息 */
	SysRoomCommunityBean getRoomCommunityByIPAndPort(SysRoomCommunityBean bean);
	
	List<SysRoomCommunityBean> getByComditions(SysRoomCommunityBean bean);
}