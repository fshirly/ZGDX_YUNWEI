package com.fable.insightview.monitor.process.mapper;

import java.util.List;

import com.fable.insightview.monitor.process.entity.MoProcessBean;
import com.fable.insightview.platform.page.Page;


public interface ProcessInfoMapper {
	List<MoProcessBean> getMoProcessInfos(Page<MoProcessBean> page);
}
