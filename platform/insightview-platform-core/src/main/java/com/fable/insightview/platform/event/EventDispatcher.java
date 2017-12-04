package com.fable.insightview.platform.event;

public interface EventDispatcher {
	/**
	 * 添加侦听器
	 * @param type
	 * @param listener
	 * @param priority 越高，越优先收到事件
	 * @return true 表示成功，否则表示重复忽略
	 */
	boolean  addEventListener(String eventName, Listener listener, int priority);
	/**
	 * 发布一个事件，所以侦听器都在同一事务中完成
	 * @param event
	 * @return 如果返回true，表示所有侦听器同意此事件，否则表示反对此事件
	 */
	boolean dispatchEvent(BaseEvent event);
	/**
	 * 判断是否有某类型的侦听器
	 * @param type
	 * @return 
	 */
	boolean hasEventListener(String eventName);
	/**
	 * 删除一个侦听器
	 * @param type
	 * @param listener
	 * @return 表示找到并去除，否则表示未找到
	 */
	boolean removeEventListener(String eventName, Listener listener);
}
