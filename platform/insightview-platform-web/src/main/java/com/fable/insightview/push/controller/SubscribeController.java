package com.fable.insightview.push.controller;



import javax.servlet.http.HttpSession;

import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.Serializer;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.common.helper.ApplicationContextHelper;
import com.fable.insightview.platform.common.util.SystemFinalValue;
import com.fable.insightview.platform.core.push.BroadcasterConfigure;
import com.fable.insightview.push.support.AtmosphereContext;
import com.fable.insightview.push.support.AtmosphereUtils;
import com.fable.insightview.push.support.DefaultBroadcasterConfigure;

/**
 * 订阅相关主题进行推送
 * 客户端需要请求该地址进行主题订阅
 * 要求主题在应用中保持唯一
 * 
 * @author 郑自辉
 *
 */
@Controller
@RequestMapping("/atmosphere/subscribe")
public class SubscribeController {
	
	/**
	 * 上下文，主要获取Broadcaster
	 */
	@Autowired
	private AtmosphereContext context;
	
	@Autowired
	private Serializer serializer;

	/**
	 * 订阅主题的统一入口
	 * @param resource 客户单连接
	 * @param topic 指定主题
	 */
	@RequestMapping(value="/{topic}",method=RequestMethod.GET)
	@ResponseBody
	public void subscribe(AtmosphereResource resource,@PathVariable String topic,HttpSession session) {
		/**
		 * 获取主题对应的Broadcaster
		 */
		Broadcaster bc = context.getBroadCaster(resource,topic);
		
		/**
		 * 设置Broadcaster
		 */
		BroadcasterConfigure bcConfigure = null;
		try {
			bcConfigure = ApplicationContextHelper.getBean(topic, BroadcasterConfigure.class);
		}
		catch(NoSuchBeanDefinitionException ex) {
			bcConfigure = new DefaultBroadcasterConfigure();
		}
		bc = bcConfigure.config(bc);
		
		/**
		 * 订阅主题
		 */
		resource.setBroadcaster(bc);
		
		/**,
		 * 本次连接与用户的绑定
		 * 用于在消息过滤器中进行推送控制
		 */
		SecurityUserInfoBean currentUser = (SecurityUserInfoBean) resource.getRequest().getSession().getAttribute(SystemFinalValue.SESSION_DATA);
		Integer currentUserId = currentUser.getId().intValue();
		String currentUserAccount = currentUser.getUserAccount();
		resource.getRequest().setAttribute("current_user", currentUserId);//count指当前登录用户ID
		resource.getRequest().setAttribute("current_userAccount", currentUserAccount);
		resource.setSerializer(serializer);
		
		/**
		 * 推迟响应
		 */
		AtmosphereUtils.suspend(resource);
	}
}
