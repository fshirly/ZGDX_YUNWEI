package com.fable.insightview.monitor.messagesender.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.fable.insightview.monitor.messagesender.service.MessageSender;
import com.fable.insightview.monitor.utils.OgnlUtil;
import com.fable.insightview.platform.smstools.service.SmsSender;
import com.fable.insightview.platform.smstools.service.impl.SendSmsByUrl;

/**
 * 发送短信
 * 
 */
@Component("smsSenderByUrl")
public class SmsSenderByUrl implements MessageSender {
	private static final Logger logger = org.slf4j.LoggerFactory
			.getLogger(SmsSenderByUrl.class);
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public boolean send(String recipient, String message, Map<String, String> configMap) {
		logger.info("通知内容：" + message + ",短信接收号码：" + recipient);
		// 短信内容中的"?"、"&"都替换为"*"
		String sendInfo = message.replace("&", "*");
		sendInfo = message.replace("?", "*");
		sendInfo = message.replace("%", "%25");
		sendInfo = sendInfo.trim();
		
		String url = "";
		String logUrl = "";
		
		int returnType = 1;
		try {
			returnType = Integer.parseInt(configMap.get("ReturnType"));
			url = configMap.get("URL");
			logUrl = configMap.get("URL");
			if (!"".equals(url) && url != null) {
				String passwordName = configMap.get("passwordName");
				int paramCout = Integer.parseInt(configMap.get("paramCout"));
				for (int i = 1; i <= paramCout; i++) {
					if (i == 1) {
						url = url + "?" + configMap.get("paramName" + i) + "="
								+ configMap.get("paramValue" + i);

						if (!"".equals(passwordName)
								&& passwordName != null
								&& passwordName.equals(configMap
										.get("paramName" + i))) {

							logUrl = logUrl + "?"
									+ configMap.get("paramName" + i)
									+ "=******";
						} else {
							logUrl = logUrl + "?"
									+ configMap.get("paramName" + i) + "="
									+ configMap.get("paramValue" + i);
						}
					} else {
						url = url + "&" + configMap.get("paramName" + i) + "="
								+ configMap.get("paramValue" + i);
						if (!"".equals(passwordName)
								&& passwordName != null
								&& passwordName.equals(configMap
										.get("paramName" + i))) {
							logUrl = logUrl + "?"
									+ configMap.get("paramName" + i)
									+ "=******";
						} else {
							logUrl = logUrl + "&"
									+ configMap.get("paramName" + i) + "="
									+ configMap.get("paramValue" + i);
						}
					}
				}
			} else {
				logger.error("短信配置url信息异常!");
				return false;
			}
		} catch (NumberFormatException e) {
			logger.error("短信配置信息异常：" + e);
			return false;
		}
		
		//使用ognl表达式替换号码、短信内容
		Map<String, Object> root = new HashMap<String, Object>();
		Map<String, Object> smsSendInfo = new HashMap<String, Object>();
		smsSendInfo.put("recipient", recipient);
		smsSendInfo.put("message", sendInfo);
		root.put("smsSendInfo", smsSendInfo);
		url = OgnlUtil.parseText(url, null, root);
		logUrl = OgnlUtil.parseText(logUrl, null, root);
		logger.info("请求调用WEB接口的URL为：" + logUrl);
		SmsSender sendSmsByUrl = new SendSmsByUrl();
		boolean result = sendSmsByUrl.send(url,returnType);
//		boolean result = sendSmsByUrl.send("http://board2.finance.daum.net/gaia/do/xml/finance/read?bbsId=stock&articleId=1673473&pageIndex=1&viewObj=1:2:0");
		return result;

	}


	@Override
	public boolean send(String recipient, String message) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean send(String recipient, String message, String title) {
		// TODO Auto-generated method stub
		return false;
	}

}
