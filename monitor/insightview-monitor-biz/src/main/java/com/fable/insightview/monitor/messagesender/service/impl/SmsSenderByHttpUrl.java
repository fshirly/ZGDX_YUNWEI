package com.fable.insightview.monitor.messagesender.service.impl;

import java.rmi.RemoteException;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fable.insightview.monitor.messagesender.service.IMsInterfaceService;
import com.fable.insightview.monitor.messagesender.service.IMsInterfaceServiceService;
import com.fable.insightview.monitor.messagesender.service.IMsInterfaceServiceServiceLocator;
import com.fable.insightview.monitor.messagesender.service.MessageSender;

/**
 * 发送短信-四川
 * @author zhaods
 *
 */
@Component("smsSenderHttpUrl")
public class SmsSenderByHttpUrl implements MessageSender{

	private static final Logger logger = LoggerFactory.getLogger(SmsSenderByHttpUrl.class);
	
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

	@Override
	public boolean send(String recipient, String message, Map<String, String> configMap) {
		logger.error("..开始读取配置信息..");
		String systemId = configMap.get("systemId");
		String extensionNo = configMap.get("extensionNo");
		String toUserMobile = recipient;
		String content = message;
		String fromUserName = "四川公安移动警务平台管理";
		String url = configMap.get("url");
		
		IMsInterfaceServiceService service = new IMsInterfaceServiceServiceLocator();
		String in0 = String.format(module, systemId,extensionNo,toUserMobile,content,fromUserName);
		try {
			logger.error("..发送短信..");
			IMsInterfaceService iMsInterfaceServiceServiceStub = service.getIMsInterfaceServicePort();
			String r = iMsInterfaceServiceServiceStub.sendMessage(in0);
			logger.debug("发送结果" + r);
			return true;
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
//		
//		Service service = new ObjectServiceFactory().create(MessageInterface.class) ;
//		XFireProxyFactory factory = new XFireProxyFactory() ;
//		try {
//			logger.error("..发送短信..");
//			MessageInterface ser = (MessageInterface) factory.create(service,url);
//			String sendMessage = ser.sendMessage(in0);
//			logger.debug("发送结果" + sendMessage);
//			logger.error("****^^" + sendMessage);
//			return true;
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		}
//		
		
//		try {
//			MessageInterface ser = (MessageInterface) factory.create(service,url);
//			String sendMessage = ser.sendMessage(systemId, extensionNo, toUserMobile, content, fromUserName);
//			System.out.println("****^^" + sendMessage);
//			return true;
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		}
//		
		return false;
	}
	
	private String module="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
			"<xml>"+
			"<sminfo_input>"+
			"<systemid>%s</systemid>"+
			"<extension_no>%s</extension_no>"+
			"<tousermobile>%s</tousermobile>"+
			"<content>%s</content>"+
			"<fromusername>%s</fromusername>"+
			"</sminfo_input>"+
			"</xml>";


	/**
	 * 
	 * @param systemId 系统接入码
	 * @param extensionNo 系统扩展号
	 * @param toUserMobile 短信接收方手机号
	 * @param content 短信内容
	 * @param fromUserName 发信人署名
	 * @return
	 */
	public String sendMessage(String systemId, String extensionNo, String toUserMobile, String content, String fromUserName){
		
		return null;
	}
}
