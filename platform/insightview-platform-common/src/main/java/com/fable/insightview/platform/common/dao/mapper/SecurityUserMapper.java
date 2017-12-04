package com.fable.insightview.platform.common.dao.mapper;

import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;

public interface SecurityUserMapper {
	public SecurityUserInfoBean getBaseInfoByUserId(Integer id);
}
