package com.fable.insightview.push.support;

import java.util.List;

import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.BroadcastFilter.BroadcastAction.ACTION;
import org.atmosphere.cpr.PerRequestBroadcastFilter;

import com.fable.insightview.platform.core.push.PushMessage;


/**
 * 推送过滤器
 * 通过该过滤器实现消息的推送逻辑
 */
public class DefaultFilter implements PerRequestBroadcastFilter{

	@Override
	public BroadcastAction filter(Object originalMessage, Object message) {
		return new BroadcastAction(message);
	}


	@Override
	public BroadcastAction filter(AtmosphereResource r, Object originalMessage,
			Object message) {
		Object realMessage = ((PushMessage)message).getMessage();
		List<Integer> notifyUsers = ((PushMessage)message).getNotifyUserIds();
		List<String> notifyUserAccounts = ((PushMessage)message).getNotifyUserAccounts();
		Integer currentUser = (Integer) r.getRequest().getAttribute("current_user");
		String currentUserAccount = (String)r.getRequest().getAttribute("current_userAccount");
		if (null != notifyUsers && !notifyUsers.isEmpty() 
				&& !notifyUsers.contains(currentUser)) {
			return new BroadcastAction(ACTION.ABORT, realMessage);
		}
		if (null != notifyUserAccounts && !notifyUserAccounts.isEmpty()
				&& !notifyUserAccounts.contains(currentUserAccount)) {
			return new BroadcastAction(ACTION.ABORT, realMessage);
		}
		return new BroadcastAction(realMessage);
	}

}
