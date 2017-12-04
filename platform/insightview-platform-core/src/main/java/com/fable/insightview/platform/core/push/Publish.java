package com.fable.insightview.platform.core.push;

import java.util.List;


public interface Publish {
	
	public void publish(Object message, String topic);
	
	/**
	 * 消息推送接口
	 * @param userIdList 推送对象的id
	 * @param title	推送消息的标题
	 * @param content	推送消息的内容
	 * @param id	区分不同的推送框 ，只要能区分就行，保证不同
	 * @param url	查看详情或者办理的链接
	 */
	public void publish(List<Integer> userIdList,String title,String content,Integer id,String url);

	/**
	 * 消息推送接口
	 * @param userIdList 推送对象的id
	 * @param title	推送消息的标题
	 * @param content	推送消息的内容
	 * @param id	区分不同的推送框 ，只要能区分就行，保证不同
	 * @param url	查看详情或者办理的链接
	 * @param flag	配置管理需要弹出popWin2,默认为popWin
	 */
	public void publish(List<Integer> userIdList,String title,String content,Integer id,String url, String flag);
	
}
