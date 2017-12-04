package com.fable.insightview.platform.event;

public interface Listener {
	/**
	 * 接受到一个消息
	 * @param event 
	 * @return true表示同意此事件，否则反对此事件，会引起终止事件传播，并立即返回通知者失败
	 * @exception 可以通过异常表示失败，让整个事务回滚
	 */
   boolean onEvent(BaseEvent event);
}
