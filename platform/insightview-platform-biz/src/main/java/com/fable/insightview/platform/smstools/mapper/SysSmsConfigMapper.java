package com.fable.insightview.platform.smstools.mapper;

import java.util.List;

import com.fable.insightview.platform.smstools.entity.SysSmsConfigBean;

public interface SysSmsConfigMapper {
	/**
	 * 获得短信配置信息
	 */
	List<SysSmsConfigBean> getConfigInfo();
}
