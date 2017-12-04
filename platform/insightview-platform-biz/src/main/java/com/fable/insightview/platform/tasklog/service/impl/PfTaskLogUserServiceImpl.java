package com.fable.insightview.platform.tasklog.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.tasklog.PfOrgTree;
import com.fable.insightview.platform.tasklog.mapper.PfTaskLogUsersMapper;
import com.fable.insightview.platform.tasklog.service.IPfTaskLogUsersService;

@Service
public class PfTaskLogUserServiceImpl implements IPfTaskLogUsersService {

	@Autowired
	private PfTaskLogUsersMapper logusersMapper;

	@Override
	public List<Integer> queryUserIds() {
		return logusersMapper.queryUserIds();
	}

	@Override
	public void add(int userId) {
		logusersMapper.add(userId);
	}

	@Override
	public void delete(int userId) {
		logusersMapper.delete(userId);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Map<String, String>> queryUsers(Page page) {
		return logusersMapper.queryUsers(page);
	}

	@Override
	public List<PfOrgTree> queryTrees() {
		return logusersMapper.queryTrees();
	}

	@Override
	public void addMulti(String userIds) {
		if (StringUtils.isEmpty(userIds)) {
			return;
		}
		logusersMapper.addMulti(userIds.split(","));
	}

	@Override
	public String queryUserIdsToStr() {
		List<Integer> userIds = this.queryUserIds();
		if (null == userIds || userIds.isEmpty()) {
			return "";
		}
		String ids = userIds.toString();
		return ids.substring(1, ids.length() - 1);
	}

}
