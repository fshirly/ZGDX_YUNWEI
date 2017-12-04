package com.fable.insightview.push.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.atmosphere.cpr.Broadcaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.core.push.Publish;
import com.fable.insightview.platform.core.push.PushMessage;

@Service
public class PublishImpl implements Publish{

	@Autowired
	private AtmosphereContext context;
	
	public void publish(Object message, String topic) {
		Broadcaster bc = context.getBroadCaster(topic);
		bc.broadcast(message);
	}

	@Override
	public void publish(List<Integer> userIdList, String title, String content,Integer id, String url) {
		Map<String,Object> msgMap = new HashMap<String, Object>();
		msgMap.put("title", title);
		msgMap.put("content", content);
		msgMap.put("id", id);
		msgMap.put("url", url);
		
		PushMessage pm = new PushMessage();
		pm.setNotifyUserIds(userIdList);
		pm.setMessage(msgMap);
		this.publish(pm, "ann");
	}

	@Override
	public void publish(List<Integer> userIdList, String title, String content,Integer id, String url, String flag) {
		Map<String,Object> msgMap = new HashMap<String, Object>();
		msgMap.put("title", title);
		msgMap.put("content", content);
		msgMap.put("id", id);
		msgMap.put("url", url);
		msgMap.put("flag", flag);
		
		PushMessage pm = new PushMessage();
		pm.setNotifyUserIds(userIdList);
		pm.setMessage(msgMap);
		this.publish(pm, "ann");
	}
}
