package com.fable.insightview.monitor.TerminalIllegalEvent.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.TerminalIllegalEvent.entity.SyslogClientIllegalEventBean;
import com.fable.insightview.monitor.TerminalIllegalEvent.entity.SyslogPorxyAccessLogBean;
import com.fable.insightview.monitor.TerminalIllegalEvent.mapper.SyslogClientIllegalEventMapper;
import com.fable.insightview.monitor.TerminalIllegalEvent.service.ISyslogClientIllegalEventService;
import com.fable.insightview.platform.page.Page;

/**
 * 终端违规事件实现类
 * @author zhaods
 *
 */
@Service
public class SyslogClientIllegalEventServiceImpl implements ISyslogClientIllegalEventService{

	@Autowired
	private SyslogClientIllegalEventMapper syslogClientIllegalEventMapper;

	@Override
	public List<SyslogClientIllegalEventBean> queryIllegalEventList(Page<SyslogClientIllegalEventBean> page) {
		List<SyslogClientIllegalEventBean> queryIllegalEventList = this.syslogClientIllegalEventMapper.queryIllegalEventList(page);
		return queryIllegalEventList;
	}

	@Override
	public List<SyslogPorxyAccessLogBean> queryProxyAccessList(Page<SyslogPorxyAccessLogBean> page) {
		// TODO Auto-generated method stub
		List<SyslogPorxyAccessLogBean> list= this.syslogClientIllegalEventMapper.queryProxyAccessList(page);
		return list;
	}
}
