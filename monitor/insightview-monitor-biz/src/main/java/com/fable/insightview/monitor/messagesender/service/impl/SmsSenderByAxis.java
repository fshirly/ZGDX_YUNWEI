package com.fable.insightview.monitor.messagesender.service.impl;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fable.insightview.monitor.messagesender.service.MessageSender;

@Component("smsSenderByAxis")
public class SmsSenderByAxis implements MessageSender{
	private static final Logger logger = LoggerFactory.getLogger(SmsSenderByAxis.class);
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public boolean send(String recipient, String message) {
		return false;
	}

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
				String username = configMap.get("username");
				String password = configMap.get("password");
				String channel = configMap.get("channel");
				logger.info("username:" + username);
				logger.info("password:" + password);
				logger.info("channel:" + channel);
				String param = this.buildParamString(username, password, channel, recipient, sendInfo);
				//调用发送短信的方法
				String sendResult = this.invoke(username, password, url, "setSubmit",param);
				if(sendResult == null || "".equals(sendResult)){
					logger.error("调用发送短信方法：SendMsg失败，返回值为空！");
					return false;
				}else{
					logger.info("调用发送短信状态方法SendMsg返回值为：" + sendResult);
					String param2 = sendResult.replace("<list>", "<map><ids>");
					param2 = param2.replace("</list>", "</ids></map>");
					logger.info("查看发送状态参数为：" + param2);
					//调用查看发送状态的方法
					String statusResult = this.invoke(username, password, url, "getStatus",param);
					logger.error("调用查看发送状态方法：getStatus，返回值为：",statusResult);
					if(statusResult == null || "".equals(statusResult)){
						return true;
					}else{
						if(statusResult.contains("<state>")){
							int index = statusResult.indexOf("<state>");
							String status = statusResult.substring(index + 7,statusResult.lastIndexOf("</state>"));
							logger.info("发送状态status值为：" + status);
							if(status.startsWith("DE")){
								return true;
							}else{
								return false;
							}
						}else{
							return true;
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
	}
	
	/**
	 * 构造调用短信发送的参数
	 */
	String buildParamString(String username, String password, String channel,String recipient, String sendInfo){
		String currentTime = dateFormat.format(new Date());
		logger.info("customtime 为：" + currentTime);
		String returnInfo = "<list>";
		returnInfo += "<map>";
		returnInfo += "<content>" + sendInfo + "</content>";
		returnInfo += "<channel>" + channel + "</channel>";
		returnInfo += "<customtime>" + currentTime + "</customtime>";
		returnInfo += "<mobiles>";
		returnInfo += "<mobile>" + recipient + "</mobile>";
		returnInfo += "</mobiles>";
		returnInfo += "</map>";
		returnInfo += "</list>";
		logger.info("构造的请求参数为： " + returnInfo);
		return returnInfo;
	}

	/**
	 * 方法调用
	 * @param wsdlUrl wsdl地址
	 * @param userName 用户名
	 * @param passWord 密码
	 * @param invokeMethod 调用的方法名
	 * @param param 拼接的参数
	 * @return
	 */
	String invoke(String userName,String passWord,String wsdlUrl,String invokeMethod,String param){
		try {
			Service service = new Service();
			Call call = (Call) service.createCall();
			call.setTimeout(30 * 60 * 1000);
			call.addParameter("in0",org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN);
			call.addParameter("in1",org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN);
			call.addParameter("in2",org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN);
			call.setTargetEndpointAddress(wsdlUrl);
			call.setOperationName(invokeMethod);
			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
			call.setUseSOAPAction(true);
			call.setSOAPActionURI(wsdlUrl);
			try {
				return (String) call.invoke(new Object[] { userName,passWord, param });
			} catch (RemoteException e) { 
				logger.error("RemoteException:",e);
			} 
		} catch (Exception e) {
			logger.error("调用接口异常：",e);
		}
		return "";
	}

	@Override
	public boolean send(String recipient, String message, String title) {
		// TODO Auto-generated method stub
		return false;
	}
}
