package com.fable.insightview.monitor.alarmdispatcher.daily;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fable.insightview.monitor.alarmdispatcher.daily.SmsTool;

public class OperateTool {
	/**
	 * compile
	 * @param str
	 * @return
	 */
	public boolean isNumeric(String str) {
		if (str == null || str.equals("")) {
			return false;
		}

		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str.trim());
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	
	/**
	 * sms interface
	 * @param type
	 * @param phoneNumber
	 * @param msg
	 */
	public void sendSms(int type, String phoneNumber, String msg) {
		SmsTool sms = new SmsTool();
		if (phoneNumber.indexOf(",") > -1) {
			String[] numberArray = phoneNumber.split(",");
			for (int i = 0; i < numberArray.length; i++) {
				sms.sendSms(type, numberArray[i], msg);
			}
		} else {
			sms.sendSms(type, phoneNumber, msg);
		}
	}

}
