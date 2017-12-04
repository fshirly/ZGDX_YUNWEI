package com.fable.insightview.platform.smstools.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.smstools.entity.HostInfo;
import com.fable.insightview.platform.smstools.entity.SMSServerOutBean;
import com.fable.insightview.platform.smstools.mapper.SMSServerOutMapper;
import com.fable.insightview.platform.smstools.service.SmsSender;

/**
 * 利用短信猫发送短信
 *
 */
@Service("sendSmsBySmsCat")
public class SendSmsBySmsCat implements SmsSender {

	private static final Logger logger = org.slf4j.LoggerFactory
			.getLogger(SendSmsBySmsCat.class);

	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	SMSServerOutMapper smsServerOutMapper;

	@Override
	public boolean send(String recipient, String message) {
		logger.info("短信猫方式发送短信······");
		SMSServerOutBean smsServerOut = new SMSServerOutBean();
		smsServerOut.setRecipient(recipient);
		smsServerOut.setText(message);
		smsServerOut.setType("O");
		smsServerOut.setCreate_date(dateFormat.format(new Date()));
		smsServerOut.setOriginator("");
		smsServerOut.setEncoding("U");
		smsServerOut.setStatus_report(0);
		smsServerOut.setFlash_sms(0);
		smsServerOut.setSrc_port(-1);
		smsServerOut.setDst_port(-1);
		smsServerOut.setPriority(0);
		smsServerOut.setStatus("U");
		smsServerOut.setErrors(0);
		smsServerOut.setGateway_id("*");
		try {
			int insertRS = smsServerOutMapper.insertSMSServerOut(smsServerOut);
			logger.info("插入SMSServer_Out的结果为====" + insertRS);
			return true;
		} catch (Exception e) {
			logger.error("数据库操作异常：" + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean send(String message, HostInfo hostInfo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean send(String url,int returnType) {
		// TODO Auto-generated method stub
		return false;
	}

}
