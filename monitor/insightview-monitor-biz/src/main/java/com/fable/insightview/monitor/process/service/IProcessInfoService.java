package com.fable.insightview.monitor.process.service;

import java.util.List;

import com.fable.insightview.monitor.process.entity.MoProcessBean;
import com.fable.insightview.platform.page.Page;

public interface IProcessInfoService {
	List<MoProcessBean> getMoProcessInfos(Page<MoProcessBean> page);
}
