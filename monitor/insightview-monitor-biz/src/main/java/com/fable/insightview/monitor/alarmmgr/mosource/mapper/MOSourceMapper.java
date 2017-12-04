package com.fable.insightview.monitor.alarmmgr.mosource.mapper;

import java.util.List;

import com.fable.insightview.monitor.alarmmgr.entity.MOSourceBean;
import com.fable.insightview.platform.page.Page;

public interface MOSourceMapper {
	/* 查询所有 */
	List<MOSourceBean> selectMOScource(Page<MOSourceBean> page);

	MOSourceBean getMOSourceById(int moID);

	List<MOSourceBean> getAllMOSource();

}
