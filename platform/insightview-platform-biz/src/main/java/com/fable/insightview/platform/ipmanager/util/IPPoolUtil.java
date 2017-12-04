package com.fable.insightview.platform.ipmanager.util;

import java.net.InetAddress;
public class IPPoolUtil {
	
    private final static int INADDRSZ = 4;

    /**
     * 把IP地址转化为字节数组
     * @param ipAddr
     * @return byte[]
     */
    public static byte[] ipToBytesByInet(String ipAddr) {
        try {
            return InetAddress.getByName(ipAddr).getAddress();
        } catch (Exception e) {
            throw new IllegalArgumentException(ipAddr + " is invalid IP");
        }
    } 

    /**
     * 把IP地址转化为int
     * @param ipAddr
     * @return int
     */
    public static byte[] ipToBytesByReg(String ipAddr) {
        byte[] ret = new byte[4];
        try {
            String[] ipArr = ipAddr.split("\\.");
            ret[0] = (byte) (Integer.parseInt(ipArr[0]) & 0xFF);
            ret[1] = (byte) (Integer.parseInt(ipArr[1]) & 0xFF);
            ret[2] = (byte) (Integer.parseInt(ipArr[2]) & 0xFF);
            ret[3] = (byte) (Integer.parseInt(ipArr[3]) & 0xFF);
            return ret;
        } catch (Exception e) {
            throw new IllegalArgumentException(ipAddr + " is invalid IP");
        }
    }

    /**
     * 字节数组转化为IP
     * @param bytes
     * @return int
     */
    public static String bytesToIp(byte[] bytes) {
        return new StringBuffer().append(bytes[0] & 0xFF).append('.').append(
                bytes[1] & 0xFF).append('.').append(bytes[2] & 0xFF)
                .append('.').append(bytes[3] & 0xFF).toString();
    }

    /**
     * 根据位运算把 byte[] -> int
     * @param bytes
     * @return int
     */
    public static int bytesToInt(byte[] bytes) {
        int addr = bytes[3] & 0xFF;
        addr |= ((bytes[2] << 8) & 0xFF00);
        addr |= ((bytes[1] << 16) & 0xFF0000);
        addr |= ((bytes[0] << 24) & 0xFF000000);
        return addr;
    }

    /**
     * 把IP地址转化为int
     * @param ipAddr
     * @return int
     */
    public static int ipToInt(String ipAddr) {
        try {
            return bytesToInt(ipToBytesByInet(ipAddr));
        } catch (Exception e) {
            throw new IllegalArgumentException(ipAddr + " is invalid IP");
        }
    }

    /**
     * ipInt -> byte[]
     * @param ipInt
     * @return byte[]
     */
    public static byte[] intToBytes(int ipInt) {
        byte[] ipAddr = new byte[INADDRSZ];
        ipAddr[0] = (byte) ((ipInt >>> 24) & 0xFF);
        ipAddr[1] = (byte) ((ipInt >>> 16) & 0xFF);
        ipAddr[2] = (byte) ((ipInt >>> 8) & 0xFF);
        ipAddr[3] = (byte) (ipInt & 0xFF);
        return ipAddr;
    }

    /**
     * 把int->ip地址
     * @param ipInt
     * @return String
     */
    public static String intToIp(int ipInt) {
        return new StringBuilder().append(((ipInt >> 24) & 0xff)).append('.')
                .append((ipInt >> 16) & 0xff).append('.').append(
                        (ipInt >> 8) & 0xff).append('.').append((ipInt & 0xff))
                .toString();
    }

    /**
     * 把192.168.1.1/24 转化为int数组范围
     * @param ipAndMask
     * @return int[]
     */
    public static int[] getIPIntScope(String ipAndMask) {
        String[] ipArr = ipAndMask.split("/");
        if (ipArr.length != 2) {
            throw new IllegalArgumentException("invalid ipAndMask with: " + ipAndMask);
        }
        int netMask = Integer.valueOf(ipArr[1].trim());
        if (netMask < 0 || netMask > 31) {
            throw new IllegalArgumentException("invalid ipAndMask with: " + ipAndMask);
        }
        int ipInt = IPPoolUtil.ipToInt(ipArr[0]);
        int netIP = ipInt & (0xFFFFFFFF << (32 - netMask));
        int hostScope = (0xFFFFFFFF >>> netMask);
        return new int[] { netIP, netIP + hostScope };

    }

    /**
     * 把192.168.1.1/24 转化为IP数组范围
     * @param ipAndMask
     * @return String[]
     */
    public static String[] getIPAddrScope(String ipAndMask) {
        int[] ipIntArr = IPPoolUtil.getIPIntScope(ipAndMask);
        return new String[] { IPPoolUtil.intToIp(ipIntArr[0]),
                IPPoolUtil.intToIp(ipIntArr[0]) };
    }

    /**
     * 根据IP 子网掩码（192.168.1.1 255.255.255.0）转化为IP段
     * @param ipAddr ipAddr
     * @param mask mask
     * @return int[]
     */
    public static int[] getIPIntScope(String ipAddr, String mask) { 
        int ipInt;
        int netMaskInt = 0, ipcount = 0;
        try {
            ipInt = IPPoolUtil.ipToInt(ipAddr);
            System.out.println("\n ipInt="+ipInt);
            if (null == mask || "".equals(mask)) {
                return new int[] { ipInt, ipInt };
            }
            netMaskInt = IPPoolUtil.ipToInt(mask); 
            ipcount = IPPoolUtil.ipToInt("255.255.255.255") - netMaskInt;
            int netIP = ipInt & netMaskInt;
            int hostScope = netIP + ipcount;
            return new int[] { netIP, hostScope };
        } catch (Exception e) {
            throw new IllegalArgumentException("invalid ip scope express  ip:"
                    + ipAddr + "  mask:" + mask);
        }
    }
    
    /**
     * 根据 子网掩码（ 255.255.255.0）获取掩码位数 
     * @param mask mask
     * @return int[]
     */
	public static int getSubNetMark(String mask) {
		if (mask == null || mask.equals("")) {
			return 24;
		}
		StringBuffer sbf;
		String str;
		int inetmask = 0, count = 0;
		String[] ipList = mask.split("\\.");
		for (int n = 0; n < ipList.length; n++) {
			sbf = toBin(Integer.parseInt(ipList[n]));
			str = sbf.reverse().toString();
			count = 0;
			for (int i = 0; i < str.length(); i++) {
				i = str.indexOf('1', i);
				if (i == -1) {
					break;
				}
				count++;
			}
			inetmask += count;
		}
		return inetmask;
	}

	static StringBuffer toBin(int x) {
		StringBuffer result = new StringBuffer();
		result.append(x % 2);
		x /= 2;
		while (x > 0) {
			result.append(x % 2);
			x /= 2;
		}
		return result;
	}
    /**
     * 根据IP 子网掩码（192.168.1.1 255.255.255.0）转化为IP段
     * @param ipAddr ipAddr
     * @param mask mask
     * @return String[]
     */
	public static String[] getIPStrScope(String ipAddr, String mask) {
		int[] ipIntArr = IPPoolUtil.getIPIntScope(ipAddr, mask);
		return new String[] { IPPoolUtil.intToIp(ipIntArr[0]),IPPoolUtil.intToIp(ipIntArr[0]) };
	}
	
	/**
	 * 子网划分
	 * @param subNetIp subNetIp
	 * @param subNetMask subNetMask
	 * @param subNum subNum
	 * @return
	 */
	public static Nets getSubnetting(String subNetIp,String subNetMask,int subNum){
		// 返回对象
		Nets net = new Nets();
		int mark = getSubNetMark(subNetMask) + (int)(Math.log(subNum)/Math.log(2));
		net.setSubNetMask(getSubNetMark(mark)); 
        int[] ipscope1 = IPPoolUtil.getIPIntScope(subNetIp, subNetMask);  
        int sumIPCount = ipscope1[1]-ipscope1[0]+1; 
        // 求每段数 
        int cc = (sumIPCount/subNum)-1;
        net.setIpCount(cc);
        StringBuffer subnet = new StringBuffer();
		for (int i = 1; i < subNum + 1; i++) {
			if (i > 1) {
				subnet.append(intToIp(ipscope1[0] + (i - 1) * cc + i - 1)
								+ "---"
								+ intToIp(ipscope1[0] + (i) * cc + i - 1) + ",");
			} else {
				subnet.append(intToIp(ipscope1[0] + (i - 1) * cc) + "---"
						+ intToIp(ipscope1[0] + (i) * cc) + ",");
			}
		}
		if (subnet.indexOf(",") > -1) {
			net.setSubNetColl(subnet.toString().split(","));
		}
		return net;
	}
	
	/**
	 * 根据位数获取对应掩码
	 * @param mark
	 * @return
	 */
	static String getSubNetMark(int mark) {
		String s = "";
		switch (mark) {
		case 8:
			s = "255.0.0.0";
			break;
		case 9:
			s = "255.128.0.0";
			break;
		case 10:
			s = "255.192.0.0";
			break;
		case 11:
			s = "255.224.0.0";
			break;
		case 12:
			s = "255.240.0.0";
			break;
		case 13:
			s = "255.248.0.0";
			break;
		case 14:
			s = "255.252.0.0";
			break;
		case 15:
			s = "255.254.0.0";
			break;
		case 16:
			s = "255.255.0.0";
			break;
		case 17:
			s = "255.255.128.0";
			break;
		case 18:
			s = "255.255.192.0";
			break;
		case 19:
			s = "255.255.224.0";
			break;
		case 20:
			s = "255.255.240.0";
			break;
		case 21:
			s = "255.255.248.0";
			break;
		case 22:
			s = "255.255.252.0";
			break;
		case 23:
			s = "255.255.254.0";
			break;
		case 24:
			s = "255.255.255.0";
			break;
		case 25:
			s = "255.255.255.128";
			break;
		case 26:
			s = "255.255.255.192";
			break;
		case 27:
			s = "255.255.255.224";
			break;
		case 28:
			s = "255.255.255.240";
			break;
		case 29:
			s = "255.255.255.248";
			break;
		case 30:
			s = "255.255.255.252";
			break;
		}
		return s;
	}
	
	// 1.计算IP地址段(包括网络地址和广播地址)getIPIntScope(String ipAddr, String mask)
	// 2.int 地址转换成 str IPUtil.intToIp(int ip);
	public static void main(String[] args){
//		String ipAddr1 = "222.216.206.0", ipMask1 = "255.255.254.0";
		String ipAddr1 = "192.168.1.0", ipMask1 = "255.255.255.254"; 
        int[] ipscope1 = IPPoolUtil.getIPIntScope(ipAddr1, ipMask1);
        System.out.println(ipAddr1 + " , " + ipMask1 
        		+ "  --> int地址段 ：[ " + ipscope1[0] + "," + ipscope1[1] + " ]");
        
        System.out.println("共有可用="+(ipscope1[1]-ipscope1[0]+1));
        
        System.out.println(ipAddr1 + " , " + ipMask1 
        		+ "  --> str地址段 ：[ " + IPPoolUtil.intToIp(ipscope1[0]) + "," + IPPoolUtil.intToIp(ipscope1[1]) + " ]");
        
        Nets net = getSubnetting(ipAddr1,ipMask1,16);
        for(int i = 0;i<net.getSubNetColl().length;i++){
        	System.out.println(net.getSubNetColl()[i]+"   /"+net.getSubNetMask());
        } 
	}
}