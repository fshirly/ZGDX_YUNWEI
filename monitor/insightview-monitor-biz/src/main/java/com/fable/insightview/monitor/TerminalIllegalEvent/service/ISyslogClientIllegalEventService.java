package com.fable.insightview.monitor.TerminalIllegalEvent.service;

import java.util.List;

import com.fable.insightview.monitor.TerminalIllegalEvent.entity.SyslogClientIllegalEventBean;
import com.fable.insightview.monitor.TerminalIllegalEvent.entity.SyslogPorxyAccessLogBean;
import com.fable.insightview.platform.page.Page;

/**
 * 终端违规事件实现类接口
 * @author zhaods
 *
 */
public interface ISyslogClientIllegalEventService {

	//分页查询违规事件列表
	List<SyslogClientIllegalEventBean> queryIllegalEventList(Page<SyslogClientIllegalEventBean> page);
    List<SyslogPorxyAccessLogBean> queryProxyAccessList(Page<SyslogPorxyAccessLogBean> page);
}
