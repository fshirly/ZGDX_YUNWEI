package com.fable.insightview.platform.core.push;

import org.atmosphere.cpr.Broadcaster;
/**
 * 抽象实现类
 * 主要控制同一主题对应的Broadcaster只需要配置一次即可
 * 
 * @author 郑自辉
 *
 */
public abstract class AbstractBroadcasterConfigure implements BroadcasterConfigure{

	/**
	 * 是否已经配置
	 * 同一个主题对应的Broadcaster只需要配置一次
	 */
	private boolean hasConfiged = false;
	
	public Broadcaster config(Broadcaster bc) {
		if (hasConfiged) {
			return bc;
		}
		bc = configBroadcaster(bc);
		hasConfiged = true;
		return bc;
	}
	
	public abstract Broadcaster configBroadcaster(Broadcaster bc);

}
