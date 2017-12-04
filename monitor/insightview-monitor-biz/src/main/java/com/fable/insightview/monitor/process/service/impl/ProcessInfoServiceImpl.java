package com.fable.insightview.monitor.process.service.impl;

import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.host.entity.HostComm;
import com.fable.insightview.monitor.process.entity.MoProcessBean;
import com.fable.insightview.monitor.process.mapper.ProcessInfoMapper;
import com.fable.insightview.monitor.process.service.IProcessInfoService;
import com.fable.insightview.platform.page.Page;

@Service
public class ProcessInfoServiceImpl implements IProcessInfoService {
	
	@Autowired ProcessInfoMapper processInfoMapper;
	
	public List<MoProcessBean> getMoProcessInfos(Page<MoProcessBean> page) {
		List<MoProcessBean> beanLst = processInfoMapper.getMoProcessInfos(page);
		for (int i = 0; i < beanLst.size(); i++) {
			MoProcessBean bean =beanLst.get(i);
			bean.setCpuValue(HostComm.getMsToTime(bean.getProcessCpu()*10));
			bean.setMemValue(HostComm.getBytesToSize(bean.getProcessMem()));
		}
		return beanLst;
	}
}
