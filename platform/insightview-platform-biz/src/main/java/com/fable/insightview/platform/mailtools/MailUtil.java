package com.fable.insightview.platform.mailtools;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fable.insightview.platform.service.ISysMailServerService;
import com.fable.insightview.platform.service.impl.SysMailServerServiceImpl;

/**
 * class MailUtil 定义一份电子邮件 在构造器里接受smtp服务器地址、发件人地址、收件人地址（可多个）、
 * 主题、内容、附件、用户名、密码等参数。 使用method sendReport发送邮件。
 */

public class MailUtil {

	private final Logger logger = LoggerFactory.getLogger(MailUtil.class);
	ISysMailServerService sysMailServerService = new SysMailServerServiceImpl();

	// ============================== Field
	// =======================================

	/** this class name */
	private static final String thisClassName = MailUtil.class.getName();

	public String host; // 服务器地址
	public String PORT;
	public String from; // 发件人
	public String to; // 收件人
	public String title; // 主题
	public String content; // 内容
	public String attachment; // 附件
	public String username; // 用户名
	public String password; // 密码

	// ============================= Constructor
	// ==================================

	/**
	 * constructor
	 * 
	 * @param host
	 *            ,from,to,title,content,attachment,username,password
	 */
	public MailUtil(String host, String from, String to, String title,
			String content, String attachment, String username, String password) {
		this.host = host;
		this.from = from;
		this.to = to;
		this.title = title;
		this.content = content;
		this.attachment = attachment;
		this.username = username;
		this.password = password;
	}

	/**
	 * constructor
	 * 
	 * @param from
	 *            ,to,title,content,attachment without host,username,password
	 */
	public MailUtil(String from, String to, String title, String content,
			String attachment) {
		this(from, to, title, content);
		this.attachment = attachment;
	}

	/**
	 * constructor
	 * 
	 * @param from
	 *            ,to,title,content without host,username,password,attachment
	 */
	public MailUtil(String from, String to, String title, String content) {
		this.from = from;
		this.to = to;
		this.title = title;
		this.content = content;
	}

	// ============================= Method
	// =======================================

	/**
	 * public boolean sendReport() 功能：发送电子邮件
	 * 
	 * @return true:发送成功 false:发送失败
	 */
	public boolean sendReport() {

		// Create session
		Session session;

		// Get system properties
		Properties props = System.getProperties();
		if (CodeBook.isNullString(host)) {
//			System.out.println("提示：请先配置邮件服务器！");
			return false;
		}
		// Setup mail server
		props.put("mail.smtp.host", host);
		logger.info("发送SMTP_SERVER：" + host);
		if (!CodeBook.isNullString(PORT)) {
			props.put("mail.smtp.port", PORT);
			logger.info("发送port：" + PORT);
		}
		// 如果某个地址出错,继续发送
		props.put("mail.smtp.sendpartial", "true");

		// Get session
		if (username.equals("")) {
			// 不需验证
			session = Session.getDefaultInstance(props, null);
			// props.put("mail.host",Globals.MAIL_SERVER);
			// props.put("mail.smtp.auth","true");
			// session= Session.getInstance(props, new Authenticator(){
			// protected PasswordAuthentication getPasswordAuthentication() {
			// return new PasswordAuthentication(Globals.MAIL_USERNAME,
			// Globals.MAIL_PASSWORD);
			// }});
		} else {
			// 需要验证
			props.put("mail.smtp.auth", "false");
			logger.info(">>>>>>>>>username= " + username);
			logger.info(">>>>>>>>>password= " + password);
			MyAuthenticator auth = new MyAuthenticator(username, password);
			session = Session.getDefaultInstance(props, auth);
			// session = Session.getInstance(props, auth);
		}// ~if~else

		// 设置邮件
		try {
			// 创建邮件
			MimeMessage message = new MimeMessage(session);

			// 设置发件人地址
			message.setFrom(new InternetAddress(this.from));

			// 设置收件人地址（多个邮件地址）
			InternetAddress[] toAddr = InternetAddress.parse(this.to);
			message.addRecipients(Message.RecipientType.TO, toAddr);

			// 设置邮件主题
			// sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
			// message.setSubject("=?GB2312?B?" + enc.encode(title.getBytes())
			// + "?=");
			message.setSubject(title);
			// 设置发送时间
			message.setSentDate(new Date());

			// 设置附件
			if (attachment != null) {

				// Create multipart
				Multipart multipart = new MimeMultipart();

				// Create the content part
				MimeBodyPart contentPart = new MimeBodyPart();

				// Create the attachment part

				// Fill the message
				contentPart.setText(content);
				String[] files = attachment.split(",");
				for (int i = 0; i < files.length; i++) {
					logger.info("files=" + files[i]);
					File file = new File(files[i]);
					MimeBodyPart attachmentPart = new MimeBodyPart();
					// Paste the attachment
					FileDataSource source = new FileDataSource(file);
					attachmentPart.setDataHandler(new DataHandler(source));

					// Set file name of attachment
					String attachmentName = new String(file.getName()
							.getBytes(), "ISO8859-1");
					attachmentPart.setFileName(attachmentName);

					// Add body part to multipart
					multipart.addBodyPart(contentPart);
					multipart.addBodyPart(attachmentPart);
				}
				// Put parts in message
				message.setContent(multipart);
			} else {
				// without attachment
				// message.setText("=?GB2312?B?" +
				// enc.encode(content.getBytes())
				// + "?=");
				message.setText(content);
			}// ~if~else

			// send email
			if (username.equals("")) {
				// 不需验证
				Transport.send(message);
			} else {
				// 需要验证
				Transport transport = session.getTransport("smtp");
				transport.connect();
				transport.sendMessage(message, message.getAllRecipients());
				transport.close();
			}// ~if~else

			// 发送成功，返回true
			logger.info("发送成功!");
			return true;
		} catch (MessagingException mex) {
			logger.info(thisClassName + ".sendReport():");
			mex.printStackTrace();
			logger.error("发送失败!", mex);
			// 发送失败，返回false
			return false;
		} catch (UnsupportedEncodingException ex) {
			logger.info(thisClassName + ".sendReport():");
			ex.printStackTrace();
			logger.error("发送失败!", ex);
			// 发送失败，返回false
			return false;
		}// ~try~catch
	}// ~method

}// ~class

/**
 * class MyAuthenticator用于邮件服务器认证 构造器需要用户名、密码作参数
 */
class MyAuthenticator extends Authenticator {

	private String username = null;
	private String password = null;

	public MyAuthenticator(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(username, password);
	}
}// ~class
