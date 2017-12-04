package com.fable.insightview.monitor.TerminalIllegalEvent.mapper;

import java.util.List;

import com.fable.insightview.monitor.TerminalIllegalEvent.entity.SyslogClientIllegalEventBean;
import com.fable.insightview.monitor.TerminalIllegalEvent.entity.SyslogPorxyAccessLogBean;
import com.fable.insightview.platform.page.Page;

/**
 * 终端违规事件mapper接口
 * @author zhaods
 *
 */
public interface SyslogClientIllegalEventMapper {

	//分页查询违规事件列表
	public List<SyslogClientIllegalEventBean> queryIllegalEventList(Page<SyslogClientIllegalEventBean> page);
	public List<SyslogPorxyAccessLogBean> queryProxyAccessList(Page<SyslogPorxyAccessLogBean> page);
	
}
