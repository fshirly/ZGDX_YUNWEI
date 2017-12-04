package com.fable.insightview.monitor.sysroomcommunity.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.page.Page;
import com.fable.insightview.monitor.sysroomcommunity.entity.SysRoomCommunityBean;
import com.fable.insightview.monitor.sysroomcommunity.mapper.SysRoomCommunityMapper;
import com.fable.insightview.monitor.sysroomcommunity.service.ISysRoomCommunityService;

@Service
public class SysRoomCommunityServiceImpl implements ISysRoomCommunityService {

	@Autowired
	SysRoomCommunityMapper sysRoomCommunityMapper;

	private static final Logger logger = LoggerFactory
			.getLogger(SysRoomCommunityServiceImpl.class);

	@Override
	public boolean insertRoomCommunity(SysRoomCommunityBean sysRoomCommunityBean) {
		try {
			sysRoomCommunityMapper.insertRoomCommunity(sysRoomCommunityBean);
			return true;
		} catch (Exception e) {
			System.out.println("新增认证失败:" + e);
			return false;
		}
	}

	@Override
	public List<SysRoomCommunityBean> getRoomCommunityByConditions(
			Page<SysRoomCommunityBean> page) {
		return sysRoomCommunityMapper.getRoomCommunityByConditions(page);
	}

	@Override
	public boolean checkCommunity(String flag,
			SysRoomCommunityBean roomCommunityBean) {
		if ("add".equals(flag)) {
			System.out.println("port:****" + roomCommunityBean.getPort());
			int count = sysRoomCommunityMapper
					.getCommunityByIPAndPort(roomCommunityBean);
			if (count > 0) {
				return false;
			} else {
				return true;
			}
		} else {
			System.out.println("port:****" + roomCommunityBean.getPort());
			int count = sysRoomCommunityMapper
					.getCommunityByIPAndID(roomCommunityBean);
			if (count > 0) {
				return false;
			} else {
				return true;
			}
		}
	}

	@Override
	public SysRoomCommunityBean getCommunityByID(int id) {
		return sysRoomCommunityMapper.getCommunityByID(id);
	}

	/**
	 *更新
	 */
	@Override
	public boolean updateCommunityByID(SysRoomCommunityBean sysRoomCommunityBean) {
		return sysRoomCommunityMapper.updateCommunityByID(sysRoomCommunityBean);
	}

	/**
	 * 删除
	 */
	@Override
	public boolean delRoomCommunity(List<Integer> ids) {
		return sysRoomCommunityMapper.delRoomCommunity(ids);
	}

	@Override
	public SysRoomCommunityBean getRoomCommunityByIP(SysRoomCommunityBean bean) {
		return sysRoomCommunityMapper.getRoomCommunityByIP(bean);
	}

	/**
	 * 根据IP更新
	 */
	@Override
	public boolean updateCommunityByIP(SysRoomCommunityBean bean) {
		try {
			sysRoomCommunityMapper.updateCommunityByIP(bean);
			return true;
		} catch (Exception e) {
			logger.error("更新机房环境监控凭证异常：" + e);
			return false;
		}
	}

	@Override
	public boolean isExistRoom(SysRoomCommunityBean bean) {
		SysRoomCommunityBean roomCommunityBean = sysRoomCommunityMapper
				.getRoomCommunityByIPAndPort(bean);
		if (roomCommunityBean == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public SysRoomCommunityBean getRoomByTask(int taskId) {
		return sysRoomCommunityMapper.getRoomByTask(taskId);
	}

	@Override
	public SysRoomCommunityBean getRoomCommunityByIPAndPort(
			SysRoomCommunityBean bean) {
		return sysRoomCommunityMapper.getRoomCommunityByIPAndPort(bean);
	}

	@Override
	public List<SysRoomCommunityBean> getByComditions(SysRoomCommunityBean bean) {
		return sysRoomCommunityMapper.getByComditions(bean);
	}
}
