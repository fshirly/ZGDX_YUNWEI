package com.fable.insightview.monitor.AcUPS.service.impl;

import java.util.StringTokenizer;

public class IPTool {
//	public static final int MAX_NUMBER = 255;
	public static final int OFFSET = 8;
	public static final String DOT = ".";
	
	public static long stringToLong(String ipAddress) {
		long value = 0L;
		StringTokenizer st = new StringTokenizer(ipAddress, DOT);
		while (st.hasMoreTokens()) {
			int v = Integer.parseInt(st.nextToken());
			value <<= OFFSET;
			value |= v;
		}
		return value;
	}
	
	public static String longToString(long ipAddress) {
		StringBuffer sb = new StringBuffer();
		for (int i = 4; i > 1; i--) {
			sb.insert(0, ipAddress & 0xFF);
			sb.insert(0, DOT);
			ipAddress >>= OFFSET;
		}
		sb.insert(0, ipAddress);
		return sb.toString();
	}
	
	public static int stringToInt(String ipAddress) {
		int value = 0;
		StringTokenizer st = new StringTokenizer(ipAddress, DOT);
		while (st.hasMoreTokens()) {
			int v = Integer.parseInt(st.nextToken());
			value <<= OFFSET;
			value += v;
		}
		return value;
	}
	
 
	/**
	 * 判断Ip是否属于Start和End
	 * @param Start
	 * @param End
	 * @param Ip
	 * @return
	 */
	public static Boolean ContainsIP(String Start, String End, String Ip) {
		long ip = stringToLong(Ip);
		long start = stringToLong(Start);
		long end = stringToLong(End);
		
		if(start <= ip && ip <= end)
			return true;
		return false;
	}
	
	public static String getBaseIp(String IP, String MASK) {
		long ip = stringToLong(IP);
		long mask = stringToLong(MASK);
		long baseIp = (ip & mask);
		
		return longToString(baseIp);
	}
	
	/**
	 * 判断MatchIP是否属于子网<IP,MASK>内
	 * @param IP
	 * @param MASK
	 * @param MatchIP
	 * @return
	 */
	public static Boolean subNetContainsIP(String IP, String MASK, String MatchIP) {
		long ip = stringToLong(IP);
		long mask = stringToLong(MASK);
		long matchIP = stringToLong(MatchIP);
		
		long allmask = stringToLong("255.255.255.255");
		long hostNum = allmask - mask +1;
		long baseIp = (ip & mask);
		
		
		if(hostNum > 2) {
			hostNum -= 2;
			baseIp += 1;
		}
		
		if(baseIp <= matchIP && matchIP <= (baseIp+hostNum))
			return true;
		return false;
	}
}