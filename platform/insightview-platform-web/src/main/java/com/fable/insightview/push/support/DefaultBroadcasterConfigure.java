package com.fable.insightview.push.support;

import java.util.Collection;

import org.atmosphere.cpr.BroadcastFilter;
import org.atmosphere.cpr.Broadcaster;

import com.fable.insightview.platform.core.push.AbstractBroadcasterConfigure;

public class DefaultBroadcasterConfigure extends AbstractBroadcasterConfigure{

	/**
	 * 配置过滤器
	 * @param bc
	 * @return
	 */
	private Broadcaster configFilter(Broadcaster bc) {
		Collection<BroadcastFilter> filters = bc.getBroadcasterConfig().filters();
		if (filters.isEmpty()) {
			//设置过滤器
			bc.getBroadcasterConfig().addFilter(new DefaultFilter());
		}
		return bc;
	}

	@Override
	public Broadcaster configBroadcaster(Broadcaster bc) {
		return configFilter(bc);
	}
}
