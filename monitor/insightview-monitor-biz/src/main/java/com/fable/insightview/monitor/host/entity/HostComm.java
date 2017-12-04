package com.fable.insightview.monitor.host.entity;

import java.text.DecimalFormat;
import java.util.Date;

public class HostComm {
	/*      经需求确定 无效数据一律用  "" 表示
	 * 将字节数转换为可读的显示格式：bytes_human_readable () 1、 若小于1K，用B显示。 2、
	 * 若大于等于1K、小于1M，用KB显示。 3、 若大于等于1M、小于1G，用MB显示。 4、 若大于等于1G、小于1T，用GB显示。 5、
	 * 若大于等于1T，用TB显示。 其中1k=1024，1M= 1048576，1G= 1073741824，1T= 1099511627776
	 */

		public static String getBytesToSize(double num) {
		DecimalFormat df = new DecimalFormat("#.##");
		String valueByNum = "";
		try {
			if (num >=0 &&num < 1000) {
				valueByNum = df.format(num) + "B";
			} else if (num >= 1000 && num < 1024000) {
				valueByNum = df.format(num / 1024) + "KB";
			} else if (num >= 1024000 && num < 1048576000) {
				valueByNum = df.format(num /1024/1024) + "MB";
			} else if (num >= 1048576000 && num < 1073741824000l) {
				valueByNum = df.format(num /1024/1024/1024) + "GB";
			} else if (num >= 1073741824000l && num < 1099511627776000l) {
				valueByNum = df.format(num /1024/1024/1024/1024) + "TB";
			} else if (num >= 1099511627776000l) {
				valueByNum = df.format(num /1024/1024/1024/1024/1024) + "PB";
			}else{
				valueByNum="";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return valueByNum;
	}

		
		public static String getKBytesToSize(double num) {
			DecimalFormat df = new DecimalFormat("#.##");
			String valueByNum = "";
			try {
				valueByNum = df.format(num / 1024);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return valueByNum;
		}
	/*
	 * 将字节数转换为可读的显示格式：bytes_human_readable () 1、 若小于1000，用Kbps显示。 2、
	 * 若大于等于1000，用KBps显示。 其中1KBps=1000  1MBps=1000000
	 */
	public static String getBytesToSpeed(double num) {
		DecimalFormat df = new DecimalFormat("#");
		String valueByNum = "";
		try {
			if (num>=0 && num < 1000) {
				valueByNum = df.format(num) + "bps";
			} else if (num >= 1000 && num<1000000) {
				valueByNum = df.format(num / 1000) + "Kbps";
			} else if (num>=1000000 && num<1000000000){
				valueByNum = df.format(num / 1000000) + "Mbps";
			} else if (num>=1000000000){
				valueByNum = df.format(num / 1000000000) + "Gbps";
			}else{
				valueByNum="";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return valueByNum;
	}


	/*
	 * 将字节数转换为可读的显示格式：bytes_human_readable () 1、 若小于1000，用bps显示。 2、
	 * 若大于等于1000，用Kbps显示。 其中1Kbps=1000  1Mbps=1000000
	 */
	public static String getBytesToFlows(double num) {
		DecimalFormat df = new DecimalFormat("#");
		String valueByNum = "";
		num=num/8;
		try {
			if (num >=0 && num < 1000) {
				valueByNum = df.format(num) + "bps";
			} else if (num >= 1000 && num<1000000) {
				valueByNum = df.format(num / 1000) + "Kbps";
			} else if (num>=1000000 && num<1000000000){
				valueByNum = df.format(num / 1000000) + "Mbps";
			} else if (num>=1000000000){
				valueByNum = df.format(num / 1000000000) + "Gbps";
			}else{
				valueByNum="";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return valueByNum;
	}
	/*
	 * 入库为 ms
	 * 1、 若小于1s，用毫秒显示。 2、 若大于等于1s、小于1min，用秒显示。 3、 若大于等于1min、小于1h，用分钟显示。 4、
	 * 若大于等于1h、小于1d，用小时显示。 5、 若大于等于1d，用天显示。 1s=1000 1min=60000 1h=3600000
	 * 1d=86400000
	 */
	public static String getMsToTime(long num) {
		DecimalFormat df = new DecimalFormat("#.##");
		String valueByNum = "";
		try {
			if (num >=0 && num < 1000) {
				valueByNum = df.format(num) + "毫秒";
			} else if (num >= 1000 && num < 60000) {
				valueByNum = df.format(num / 1000) + "秒";
			} else if (num >= 60000 && num < 3600000) {
				valueByNum = df.format(num / 60000) + "分钟";
			} else if (num >= 3600000 && num < 86400000) {
				valueByNum = df.format(num / 3600000) + "小时";
			} else if (num >= 86400000) {
				valueByNum = df.format(num / 86400000) + "天";
			}else{
				valueByNum="";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return valueByNum;
	}
	
	public static String getDurationToTime(double num) {
		DecimalFormat df = new DecimalFormat("#.#");
		DecimalFormat df2 = new DecimalFormat("#");
		String valueByNum = "";
		try {
			if (num >=0 && num < 1000) {
				valueByNum = "1秒";
			} else if (num >= 1000 && num < 60000) {
				valueByNum = df2.format(num / 1000) + "秒";
			} else if (num >= 60000 && num < 3600000) {
				valueByNum = df2.format(num / 60000) + "分钟";
			} else if (num >= 3600000) {
				valueByNum = df.format(num / 3600000) + "小时";
			}else{
				valueByNum="";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return valueByNum;
	}
	/*
	 * 将字节数转换为可读的显示格式：bytes_human_readable () 1、 若小于1000，用Hz显示。 2、
	 * 若大于等于1000，用KHz显示。 其中1KHz=1000  1MHz=1000000
	 */
	public static String getBytesToHz(double num) {
		DecimalFormat df = new DecimalFormat("#.##");
		String valueByNum = "";
		try {
			if (num >=0 && num < 1000) {
				valueByNum = df.format(num) + "Hz";
			} else if (num >= 1000 && num<1000000) {
				valueByNum = df.format(num / 1000) + "KHz";
			} else if (num>=1000000 && num<1000000000){
				valueByNum = df.format(num / 1000000) + "MHz";
			} else if (num>=1000000000){
				valueByNum = df.format(num / 1000000000) + "GHz";
			}else{
				valueByNum="";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return valueByNum;
	}
			public static void main(String[] args) {
				System.out.println(getMsToTime(1440287));
				System.out.println(getBytesToSize(5078315000l));
			}
}
