package com.fable.insightview.platform.common.excel.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wul
 * 
 */

public class DateUtils {
	private static String defaultPattern = "yyyy-MM-dd";

	/**
	 * ���pattern�ж��ַ��Ƿ�Ϊ�Ϸ�����
	 * 
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static boolean isValidDate(String dateStr, String pattern) {
		boolean isValid = false;
		String patterns = "yyyy-MM-dd,MM/dd/yyyy";

		if (pattern == null || pattern.length() < 1) {
			pattern = "yyyy-MM-dd";
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			// sdf.setLenient(false);
			String date = sdf.format(sdf.parse(dateStr));
			if (date.equalsIgnoreCase(dateStr)) {
				isValid = true;
			}
		} catch (Exception e) {
			isValid = false;
		}
		// ���Ŀ���ʽ����ȷ���ж��Ƿ��������ʽ������
		if (!isValid) {
			isValid = isValidDatePatterns(dateStr, "");
		}
		return isValid;
	}

	public static boolean isValidDatePatterns(String dateStr, String patterns) {
		if (patterns == null || patterns.length() < 1) {
			patterns = "yyyy-MM-dd;dd/MM/yyyy;yyyy/MM/dd;yyyy/M/d h:mm";
		}
		boolean isValid = false;
		String[] patternArr = patterns.split(";");
		for (int i = 0; i < patternArr.length; i++) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(patternArr[i]);
				// sdf.setLenient(false);
				String date = sdf.format(sdf.parse(dateStr));
				if (date.equalsIgnoreCase(dateStr)) {
					isValid = true;
					DateUtils.defaultPattern = patternArr[i];
					break;
				}
			} catch (Exception e) {
				isValid = false;
			}
		}
		return isValid;
	}

	public static String getFormatDate(String dateStr, String pattern) {
		if (pattern == null || pattern.length() < 1) {
			pattern = "yyyy-MM-dd";
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(
					DateUtils.defaultPattern);
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			String date = format.format(sdf.parse(dateStr));
			return date;
		} catch (Exception e) {
			//System.out.println("���ڸ�ת��ʧ�ܣ�");
		}
		return null;
	}

	public static String getFormatDate(Date date, String pattern) {
		if (pattern == null || pattern.length() < 1) {
			pattern = "yyyy-MM-dd";
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			String strDate = sdf.format(date);
			return strDate;
		} catch (Exception e) {
			//System.out.println("���ڸ�ת��ʧ�ܣ�");
		}
		return null;
	}

	public static void main(String[] args) {

		// boolean isd = DateUtils.isValidDate("08/09/2007", "yyyy-MM-dd");
		// if(isd){
		// String date = DateUtils.getFormatDate("08/09/2007", "yyyy-MM-dd");
		// //System.out.println(date);
		// }
		// //System.out.println(date);

		DateFormat df = DateFormat.getDateInstance();
		try {
			Date myDate = df.parse("2007-7-9");
			//System.out.println(myDate.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
