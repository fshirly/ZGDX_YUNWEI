package com.fable.insightview.monitor.sysroomcommunity.mapper;

import java.util.List;

import com.fable.insightview.platform.page.Page;
import com.fable.insightview.monitor.sysroomcommunity.entity.SysRoomCommunityBean;

public interface SysRoomCommunityMapper {
	
	/* 新增数据库验证 */
	int insertRoomCommunity(SysRoomCommunityBean sysRoomCommunityBean);
	
	/* 更新数据库验证 */
	boolean updateCommunityByID(SysRoomCommunityBean sysRoomCommunityBean);
	
	int getCommunityByIPAndPort(SysRoomCommunityBean sysRoomCommunityBean);
	
	int getCommunityByIPAndID(SysRoomCommunityBean sysRoomCommunityBean);
	
	/*更新时获取初始化信息 */
	SysRoomCommunityBean getCommunityByID(int id);
	
	boolean delRoomCommunity(List<Integer> ids);

	List<SysRoomCommunityBean> getRoomCommunityByConditions(Page<SysRoomCommunityBean> page);
	
	/* 根据IP获得凭证信息 */
	SysRoomCommunityBean getRoomCommunityByIP(SysRoomCommunityBean bean);
	
	int updateCommunityByIP(SysRoomCommunityBean bean);
	
	/* 根据采集任务获得凭证*/
	SysRoomCommunityBean getRoomByTask(int taskId);
	
	SysRoomCommunityBean getRoomCommunityByIPAndPort(SysRoomCommunityBean bean);
	
	List<SysRoomCommunityBean> getByComditions(SysRoomCommunityBean bean);
	
}
