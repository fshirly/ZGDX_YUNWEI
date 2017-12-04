package com.fable.insightview.platform.core.push;

import org.atmosphere.cpr.Broadcaster;
/**
 * 配置对应的Broadcaster
 * 如配置BroadcasterFilter
 * 
 * @author 郑自辉
 *
 */
public interface BroadcasterConfigure {

	/**
	 * 对Broadcaster进行配置
	 * @param bc 需要配置的Broadcaster
	 * @return Broadcaster 配置好的Broadcaster
	 */
	Broadcaster config(Broadcaster bc);
}
