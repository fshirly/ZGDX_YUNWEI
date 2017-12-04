package com.fable.insightview.monitor.messagesender.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fable.insightview.monitor.messagesender.service.MessageSender;
import com.fable.insightview.platform.smstools.entity.HostInfo;
import com.fable.insightview.platform.smstools.service.SmsSender;
import com.fable.insightview.platform.smstools.service.impl.SendSmsByGateway;

/**
 * 通过短信网关发送短信
 *
 */
@Component("smsSenderByGateway")
public class SmsSenderByGateway implements MessageSender {
//	@Resource(name = "sendSmsByGateway")
//	SmsSender sendSmsByGateway;
	private static final Logger logger = LoggerFactory.getLogger(SmsSenderByGateway.class);
	private	String serverIpAddress;
	
	private String serverPort;
	
	/**
	 * 发送
	 * @param remoteAddress 网关ip地址
	 * @param remotePort 网关端口
	 * @param recipient 短信接收这号码
	 * @param message 短信内容
	 * @return
	 */
	@Override
	public boolean send(String recipient, String message, Map<String, String> configMap){
		logger.info("通知内容："+message+",短信接收号码："+recipient);
		//短信内容中的"#"都替换为"*"
		String sendInfo = message.replace("#", "*");
		sendInfo = sendInfo.trim();

		// 单个手机发送： ‘内容’+‘#’+‘手机号码’ ;多个手机发送：‘内容’+‘#’+‘手机号码1；手机号码2；……’
		sendInfo = sendInfo + "#" + recipient;

		logger.info("处理后发送的message："+sendInfo);
		// 初始化网关信息
		HostInfo hostInfo = new HostInfo();
		String serverIpAddress = configMap.get("ServerIpAddress");
		int serverPort = Integer.parseInt(configMap.get("ServerPort"));
		hostInfo.setServerIpAddress(serverIpAddress);
		hostInfo.setServerPort(serverPort);
		SmsSender sendSmsByGateway = new SendSmsByGateway();
		return sendSmsByGateway.send(sendInfo, hostInfo);
	}

	public String getServerIpAddress() {
		return serverIpAddress;
	}

	public void setServerIpAddress(String serverIpAddress) {
		this.serverIpAddress = serverIpAddress;
	}

	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	@Override
	public boolean send(String recipient, String message) {
		return false;
	}

	@Override
	public boolean send(String recipient, String message, String title) {
		// TODO Auto-generated method stub
		return false;
	}

}
