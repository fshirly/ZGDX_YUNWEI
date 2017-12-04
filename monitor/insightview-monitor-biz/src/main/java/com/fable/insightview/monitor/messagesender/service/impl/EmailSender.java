package com.fable.insightview.monitor.messagesender.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fable.insightview.monitor.messagesender.service.MessageSender;
import com.fable.insightview.platform.entity.SysMailServerConfigBean;
import com.fable.insightview.platform.mailtools.MailUtil;
import com.fable.insightview.platform.service.ISysMailServerService;

/**
 * 发送邮件
 * 
 */
@Component("emailSender")
public class EmailSender implements MessageSender {
	@Autowired
	ISysMailServerService sysMailServerService;
	private static final Logger logger = org.slf4j.LoggerFactory
			.getLogger(EmailSender.class);

	@Override
	public boolean send(String recipient, String message, String title) {
		logger.info("sysMailServerService：" + sysMailServerService);
		SysMailServerConfigBean mailServer = sysMailServerService
				.getMailServerConfigInfo().get(0);
		String from = mailServer.getSenderAccount();
		logger.info("发件者邮箱：" + from);
		String to = recipient;
		String content = message;
		MailUtil rm = new MailUtil(from, to, title, content);
		rm.host = mailServer.getMailServer();
		rm.PORT = mailServer.getPort() + "";
		rm.username = mailServer.getUserName();
		rm.password = mailServer.getPassword();
		int isAuth = (int) mailServer.getIsAuth();
		boolean sendMail = rm.sendReport();
		return sendMail;
	}

	public ISysMailServerService getSysMailServerService() {
		return sysMailServerService;
	}

	public void setSysMailServerService(
			ISysMailServerService sysMailServerService) {
		this.sysMailServerService = sysMailServerService;
	}

	@Override
	public boolean send(String recipient, String message,
			Map<String, String> configMap) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean send(String recipient, String message) {
		// TODO Auto-generated method stub
		return false;
	}

}
