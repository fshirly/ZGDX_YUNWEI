package com.fable.insightview.monitor.middleware.middlewarecommunity.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.middleware.middlewarecommunity.entity.SysMiddleWareCommunityBean;
import com.fable.insightview.monitor.middleware.middlewarecommunity.mapper.SysMiddleWareCommunityMapper;
import com.fable.insightview.monitor.middleware.middlewarecommunity.service.ISysMiddleWareCommunityService;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJVMBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareMemoryBean;
import com.fable.insightview.platform.page.Page;

@Service
public class SysMiddleWareCommunityServiceImpl implements
		ISysMiddleWareCommunityService {
	private final Logger logger = LoggerFactory
			.getLogger(SysMiddleWareCommunityServiceImpl.class);

	@Autowired
	SysMiddleWareCommunityMapper middleWareCommunityMapper;

	@Override
	public boolean isExistMiddleWare(SysMiddleWareCommunityBean bean) {
		SysMiddleWareCommunityBean community = middleWareCommunityMapper
				.getCommunityByIPAndPort(bean);
		if (community != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean insertMiddleWareCommunity(SysMiddleWareCommunityBean bean) {
		try {
			middleWareCommunityMapper.insertMiddleWareCommunity(bean);
			return true;
		} catch (Exception e) {
			logger.error("新增中间件认证失败:" + e);
			return false;
		}

	}

	@Override
	public boolean updateMiddleWareCommunity(SysMiddleWareCommunityBean bean) {
		try {
			middleWareCommunityMapper.updateMiddleWareCommunity(bean);
			return true;
		} catch (Exception e) {
			logger.error("更新中间件认证失败:" + e);
			return false;
		}
	}

	@Override
	public SysMiddleWareCommunityBean getMiddleWareTask(int taskId) {
		return middleWareCommunityMapper.getMiddleWareTask(taskId);
	}

	@Override
	public List<SysMiddleWareCommunityBean> getMiddleWareCommunityList(
			Page<SysMiddleWareCommunityBean> page) {
		return middleWareCommunityMapper.getMiddleWareCommunityList(page);
	}

	@Override
	public boolean delMiddleWareCommunity(List<Integer> ids) {
		return middleWareCommunityMapper.delMiddleWareCommunity(ids);
	}

	@Override
	public boolean checkCommunity(String flag,
			SysMiddleWareCommunityBean communityBean) {
		if ("add".equals(flag)) {
			int count = middleWareCommunityMapper
					.getCommunityByNameAndIP(communityBean);
			if (count > 0) {
				return false;
			} else {
				return true;
			}
		} else {
			int count = middleWareCommunityMapper
					.getCommunityByNameAndIPAndID(communityBean);
			if (count > 0) {
				return false;
			} else {
				return true;
			}
		}
	}

	@Override
	public SysMiddleWareCommunityBean getCommunityByID(int id) {
		return middleWareCommunityMapper.getCommunityByID(id);
	}

	@Override
	public boolean updateCommunityByID(SysMiddleWareCommunityBean bean) {
		try {
			middleWareCommunityMapper.updateCommunityByID(bean);
			return true;
		} catch (Exception e) {
			logger.error("更新中间件信息异常：" + e);
			return false;
		}
	}

	@Override
	public SysMiddleWareCommunityBean getCommunityByIP(
			SysMiddleWareCommunityBean bean) {
		return middleWareCommunityMapper.getCommunityByIP(bean);
	}

	@Override
	public List<MOMiddleWareJVMBean> queryListJVM(Page<MOMiddleWareJVMBean> page) {
		return middleWareCommunityMapper.queryListJVM(page);
	}

	@Override
	public List<MOMiddleWareMemoryBean> queryListMemPool(
			Page<MOMiddleWareMemoryBean> page) {
		return middleWareCommunityMapper.queryListMemPool(page);
	}

	@Override
	public List<MOMiddleWareMemoryBean> querySnapshot(MOMiddleWareMemoryBean vo) {
		// TODO Auto-generated method stub
		return middleWareCommunityMapper.querySnapshot(vo);
	}

	@Override
	public SysMiddleWareCommunityBean getCommunityByIPAndPort(
			SysMiddleWareCommunityBean bean) {
		return middleWareCommunityMapper.getCommunityByIPAndPort(bean);
	}

	@Override
	public List<SysMiddleWareCommunityBean> getByConditions(
			SysMiddleWareCommunityBean bean) {
		// TODO Auto-generated method stub
		return middleWareCommunityMapper.getByConditions(bean);
	}

}
