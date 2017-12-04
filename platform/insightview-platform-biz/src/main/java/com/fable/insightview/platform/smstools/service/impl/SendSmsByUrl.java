package com.fable.insightview.platform.smstools.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fable.insightview.platform.smstools.entity.HostInfo;
import com.fable.insightview.platform.smstools.service.SmsSender;

/**
 * 通过访问url调用第三方接口发送短信
 * 
 */
public class SendSmsByUrl implements SmsSender {

	private static final Logger logger = LoggerFactory
			.getLogger(SendSmsByUrl.class);
	private static final int zero = 0;
	private static final int shengtingType = 1;
	private static final int anhuiType = 2;
	private static final String zeroStr = "0";

	@Override
	public boolean send(String message, HostInfo hostInfo) {
		return false;
	}

	@Override
	public boolean send(String recipient, String message) {
		return false;
	}

	@Override
	public boolean send(String urlStr, int returnType) {
		boolean sendFlag = false;
		System.out.println("访问的url:" + urlStr);
		urlStr=urlStr.replaceAll(" ", "%20");
		logger.info("returnType===" + returnType);
		/** 网络的url地址 */
		URL url = null;
		try {
			url = new URL(urlStr);
		} catch (MalformedURLException e1) {
			logger.error("url异常：" + e1, e1);
		}

		try {
			// 江苏省厅
			if (returnType == shengtingType) {
				/** 输入流 */
				BufferedReader in = null;
				StringBuffer sb = new StringBuffer();
				try {
					// url = new
					// URL("http://board2.finance.daum.net/gaia/do/xml/finance/read?bbsId=stock&articleId=1673473&pageIndex=1&viewObj=1:2:0");
					in = new BufferedReader(new InputStreamReader(url
							.openStream(), "UTF-8"));
					String str = null;
					while ((str = in.readLine()) != null) {
						sb.append(str);
					}
				} catch (Exception ex) {
					logger.error("读文件异常：" + ex, ex);
					return false;
				} finally {
					try {
						if (in != null) {
							in.close();
						}
					} catch (IOException ex) {
						logger.error("关闭输入流异常：" + ex, ex);
						return false;
					}
				}
				String result = sb.toString();
				logger.info("返回的文件内容为：" + result);

				Document doc = DocumentHelper.parseText(result);

				Element root = doc.getRootElement();
				Element packageHeader = root.element("package-header");
				Element responsecode = packageHeader.element("responsecode");
				logger.info("返回代码responsecode：" + responsecode.getData());
				Element comment = packageHeader.element("comment");
				logger.info("返回说明comment：" + comment.getData());
				int returnCode = Integer.parseInt(responsecode.getData()
						.toString());
				if (returnCode == zero) {
					sendFlag = true;
				}
			}
			// 安徽
			else if (returnType == anhuiType) {
				String flag = "1";
				InputStream localInputStream = null;

				HttpURLConnection localHttpURLConnection = null;
				try {
					localHttpURLConnection = (HttpURLConnection) url
							.openConnection();
				} catch (IOException e) {
					logger.error("http连接异常：" + e, e);
					sendFlag = false;
				}
				if (localHttpURLConnection == null) {
					System.out.println("============连接失败,url=" + urlStr);
					flag = "1";
				} else {
					try {
						localInputStream = localHttpURLConnection
								.getInputStream();
						byte[] arrayOfByte = new byte[10240];
						int j = localInputStream.read(arrayOfByte);
						flag = new String(arrayOfByte, 0, j).trim();
					} catch (IOException e) {
						logger.error("短信内容发送失败：" + e, e);
					}finally{
						try {
							if (localInputStream != null) {
								localInputStream.close();
							}
						} catch (IOException ex) {
							logger.error("关闭输入流localInputStream异常：" + ex, ex);
							return false;
						}
						localHttpURLConnection.disconnect();
					}
				}
				if (zeroStr.equals(flag)) {
					sendFlag = true;
				}
			} else {
				logger.error("短信配置信息异常!");
			}
		} catch (DocumentException e) {
			logger.error("dom4j解析异常：" + e);
			return false;
		}
		logger.info("短信发送结果：" + sendFlag);
		return sendFlag;
	}

}
