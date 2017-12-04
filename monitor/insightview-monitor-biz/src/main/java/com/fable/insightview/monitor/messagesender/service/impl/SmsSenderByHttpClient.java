package com.fable.insightview.monitor.messagesender.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fable.insightview.monitor.messagesender.service.MessageSender;
@Component("smsSenderByHttpClient")
public class SmsSenderByHttpClient implements MessageSender {
	private static final Logger logger = LoggerFactory.getLogger(SmsSenderByHttpClient.class);
	@Override
	public boolean send(String recipient, String message, Map<String, String> configMap) {
		// 短信内容中的"?"、"&"都替换为"*"
		String sendInfo = message.replace("&", "*");
		sendInfo = message.replace("?", "*");
		sendInfo = sendInfo.trim();
		logger.info("短信发送的内容为：" + sendInfo + ",短信接收号码为：" + recipient);
		
		String url = "";
		try {
			url = configMap.get("URL");
			if (!"".equals(url) && url != null) {
				logger.info("URL地址为：" + url);
				String namespace = configMap.get("namespace");
				logger.info("命名空间为：" + namespace);
				//调用的参数
				Map<String, String> patameterMap = new LinkedHashMap<String, String>();
				//调用方法返回的结果
				Map<String, Object> invokeResult = new HashMap<String, Object>();
				String UserID = configMap.get("UserID");
				String UserPwd = configMap.get("UserPwd");
				
				patameterMap.put("UserID", UserID);
				patameterMap.put("UserPwd", UserPwd);
				patameterMap.put("Phone", recipient);
				patameterMap.put("Content", sendInfo);
				invokeResult = this.invoke(patameterMap, url, "SendMsg", namespace);
				int statusCode = Integer.parseInt(String.valueOf(invokeResult.get("statusCode")));
				if(statusCode == 200){
					logger.info("调用发送短信方法：SendMsg成功！");
					String soapResponseData = String.valueOf(invokeResult.get("soapResponseData"));
					logger.info("调用发送短信方法返回值：" + soapResponseData);
					int index = soapResponseData.indexOf("<Status>");
					String status = soapResponseData.substring(index + 8,soapResponseData.lastIndexOf("</Status>"));
					System.out.println("发送状态Status值为：" + status);
					if("200".equals(status)){
						return true;
					}else{
						return false;
					}
				}else{
					logger.error("调用返回码为："+ statusCode +",调用发送短信方法：SendMsg失败！");
					return false;
				}
			} else {
				logger.error("短信配置url信息异常!");
				return false;
			}
		} catch (Exception e) {
			logger.error("短信配置信息异常：" + e);
			return false;
		}
	}

	@Override
	public boolean send(String recipient, String message) {
		return false;
	}
	
	/**
	 * 调用方法
	 * @param patameterMap
	 * @param url 请求地址url
	 * @param methodName 调用的方法名
	 * @param namespace 命名空间
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> invoke(Map<String, String> patameterMap,String url,String methodName,String namespace){
		PostMethod postMethod = new PostMethod(url);
		String soapRequestData = buildRequestData(patameterMap, methodName,
				namespace);
		logger.info(soapRequestData);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			byte[] bytes = soapRequestData.getBytes("utf-8");

			InputStream inputStream = new ByteArrayInputStream(bytes, 0, bytes.length);
			RequestEntity requestEntity = new InputStreamRequestEntity( inputStream, bytes.length, "application/soap+xml; charset=utf-8");
			postMethod.setRequestEntity(requestEntity);

			HttpClient httpClient = new HttpClient();
			int statusCode = 0;
			try {
				statusCode = httpClient.executeMethod(postMethod);
			} catch (Exception e) {
				logger.error("调用方法异常：" + e);
			}
			String soapResponseData = postMethod.getResponseBodyAsString();
			result.put("statusCode", statusCode);
			result.put("soapResponseData", soapResponseData);
		} catch (UnsupportedEncodingException e1) {
			logger.error(e1.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}

		return result;
	}
	
	/**
	 * 构造请求参数
	 * @param patameterMap
	 * @param methodName 调用的方法名
	 * @param namespace	命名空间
	 * @return
	 */
	public String buildRequestData(Map<String, String> patameterMap,String methodName,String namespace) {
		StringBuffer soapRequestData = new StringBuffer();
		soapRequestData.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		soapRequestData.append("<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
						+ " xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\""
						+ " xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">");
		soapRequestData.append("<soap12:Body>");
		soapRequestData.append("<" + methodName + " xmlns=\"" + namespace + "\">");

		Set<String> nameSet = patameterMap.keySet();
		for (String name : nameSet) {
			soapRequestData.append("<" + name + ">" + patameterMap.get(name)
					+ "</" + name + ">");
		}
		soapRequestData.append("</" + methodName + ">");
		soapRequestData.append("</soap12:Body>");
		soapRequestData.append("</soap12:Envelope>");
		return soapRequestData.toString();
	}

	@Override
	public boolean send(String recipient, String message, String title) {
		// TODO Auto-generated method stub
		return false;
	}
}
