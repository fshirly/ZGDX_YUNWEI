package com.fable.insightview.platform.smstools.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fable.insightview.platform.smstools.entity.HostInfo;
import com.fable.insightview.platform.smstools.service.SmsSender;

/**
 * 通过短信网关发送短信
 * 
 */
public class SendSmsByGateway implements SmsSender {

	private static final Logger logger = LoggerFactory
			.getLogger(SendSmsByGateway.class);

	@Override
	public boolean send(String message ,HostInfo hostInfo) {
		 try {
			 	logger.info("短信网关方式发送短信······");
			 	logger.info("socket IP地址="+hostInfo.getServerIpAddress()+",端口号="+hostInfo.getServerPort());
			 	// 建立socket连接
			 	Socket socket = new Socket(hostInfo.getServerIpAddress(), hostInfo.getServerPort());
				//得到socket读写流  
				OutputStream os=socket.getOutputStream();  
				PrintWriter pw=new PrintWriter(os);  
				
				//输入流  
				InputStream is=socket.getInputStream();  
				BufferedReader br=new BufferedReader(new InputStreamReader(is));  
				//对socket进行读写操作  
				
				pw.write(message);  
				pw.flush();  
				socket.shutdownOutput();
				
				//关闭资源  
				br.close();  
				is.close();  
				pw.close();  
				os.close();  
				socket.close();
				return true;
			} catch (IOException e) {
				logger.error("socket发送信息异常："+e);
			}  
			return false;
	}


	@Override
	public boolean send(String recipient, String message) {
		return false;
	}


	@Override
	public boolean send(String url,int returnType) {
		// TODO Auto-generated method stub
		return false;
	}

}
