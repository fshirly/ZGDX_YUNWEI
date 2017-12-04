package com.fable.insightview.monitor.utils;

import java.text.DecimalFormat;

public class DecimalForTwo {

		DecimalFormat df = new DecimalFormat("#0.00");
	
		// 將字符串類型的數據转换成截取小数点后两位的数字类型
		public String DecimalFormatString(String s)
		{
			return df.format(Double.valueOf(s));
		}
		
		// 將字Double类型的數據转换成截取小数点后两位的数字类型
		public String DecimalFormatDouble(Double dou)
		{
			return df.format(dou);
		}
		
		// 將字Float类型的數據转换成截取小数点后两位的数字类型
		public String DecimalFormatFloat(Float fl)
		{
			return df.format(Double.valueOf(fl));
		}
	
}
