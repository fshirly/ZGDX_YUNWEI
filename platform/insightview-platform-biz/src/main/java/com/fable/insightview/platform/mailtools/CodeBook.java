package com.fable.insightview.platform.mailtools;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CodeBook {

	/**
	 * 分割符
	 */
	public static final String FW_LINE = "_";
	
	/**
	 * IsNew = 1 新增
	 */
	public static final String ISNEW_ADD = "1";

	/**
	 * IsNew = 2 删除
	 */
	public static final String ISNEW_DEL = "2";

	/**
	 * IsNew = 3 修改
	 */
	public static final String ISNEW_EDIT = "3";

	/**
	 * 表的主键名称
	 */
	public static final String TABLE_KEY = "resObjID";

	/**
	 * 表的主键名称
	 */
	public static final String TABLE_KEY_NODEID = "nodeId";

	/**
	 * 表的键名称
	 */
	public static final String TABLE_OWNER = "owner";

	/**
	 * 修改的javascript的方法
	 */
	public static final String JS_DOEDIT = "doEdit";

	/**
	 * 分割符
	 */
	public static final String SPILT = ",";

	/**
	 * 分割符
	 */
	public static final String SPILT_FENGE = "|";

	/**
	 * 列表显示，属性不显示
	 */
	public static final String ONE_ZERO = "10";

	/**
	 * 列表显示，属性显示
	 */
	public static final String ONE_ONE = "11";

	/**
	 * 列表不显示，属性显示
	 */
	public static final String ZERO_ONE = "01";

	/**
	 * InputStyle
	 */
	public static final String IPAddress = "IPAddress";

	/**
	 * 操作名称 List
	 */
	public static final String OPERATION_LIST = "list";

	/**
	 * 操作名称 New
	 */
	public static final String OPERATION_NEW = "add";

	/**
	 * 操作名称 Edit
	 */
	public static final String OPERATION_EDIT = "edit";

	/**
	 * 不做任何操作
	 */
	public static final String DO_NONE = "0";

	/**
	 * 关闭画面
	 */
	public static final String DO_CLOSE = "1";

	/**
	 * 对话框
	 */
	public static final String DO_ALERT = "2";

	/**
	 * 非数值型
	 */
	public static final String NOT_NUMBER_TYPE = "0";

	/**
	 * 数值型
	 */
	public static final String NUMBER_TYPE = "1";

	public static final String DATE_FORMAT = "yyyy-MM-dd";
	
	public static final String FILTER_VALUE = "999999999";
    
	// 小分割符号
    public static final String FW_SPILT = "^^~";
    // 大分割符号
    public static final String FRAMEWORK_SPILT = "&^~";

    public static final String FW_SPILT_GET = "\\^\\^\\~";

    public static final String FRAMEWORK_SPILT_GET = "\\&\\^\\~";
    public static final String DB_DATE_FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
    private static SimpleDateFormat sdfDateTime =
        new SimpleDateFormat(DB_DATE_FORMAT_DATETIME);
	/**
	 * 值类型 0: 字符
	 */
	public static final BigInteger CHAR_TYPE = new BigInteger("0");

	/**
	 * 值类型 1: 整形
	 */
	public static final BigInteger INTEGER_TYPE = new BigInteger("1");

	/**
	 * 值类型 2: 浮点
	 */
	public static final BigInteger FLOAT_TYPE = new BigInteger("2");

	/**
	 * 输入格式 图标
	 */
	public static final String INPUTSTYLE_IMAGE = "IMAGE";

	/**
	 * 1：发现一个节点的标志
	 */
	public static final String FINDNODE = "1";

	/**
	 * 2：发现一个子网的标志
	 */
	public static final String FINDSUBNET = "2";

	/**
	 * 3：以一个节点为种子开始发现的标志
	 */
	public static final String FINDFROMSEED = "3";


	
    /**
     * 采用FRAMEWORK_SPILT_GET和FW_SPILT_GET分隔连成的长字符串解析为List
     * @param str FRAMEWORK_SPILT_GET和FW_SPILT_GET分隔连成的长字符串
     * @param lis1 分割结果
     * @param lis2 分割结果
     */
	public static void StringToList(String str, List lis1, List lis2) {
        if (isNullString(str)) {
            return;
        }
        String[] oneinfo = str.split(FRAMEWORK_SPILT_GET);
        for (int i = 0; i < oneinfo.length; i++) {
            String[] detOneinfo = oneinfo[i].split(FW_SPILT_GET);
            lis1.add(detOneinfo[0]);
            lis2.add(detOneinfo[1]);
        }
	}
	
	public static boolean isNullString(String str) {
		return (str == null || "".equals(str.trim()));
	}

    public static String nullStringToBlank(String str) {
        if (isNullString(str)){
            return "";
        }else{
            return str;
        }
    }
    
	public static boolean isEmpty(String sIn) {
		if (sIn == null) {
			return true;
		} else {
			return sIn.length() == 0;
		}
	}

	public static boolean isInteger(String str) {
		boolean result = true;
		if (isEmpty(str)) {
			return result;
		}
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException e) {
			result = false;
		}
		return result;
	}

	public static boolean isDouble(String str) {
		boolean result = true;
		if (isEmpty(str)) {
            
			return result;
		}
		try {
			Double.parseDouble(str);
		} catch (NumberFormatException e) {
			result = false;
		}
		return result;
	}

	public static String toString(Object num) {
		String str = null;
		if (null == num) {
			return str;
		}
		str = String.valueOf(num);
		return str;
	}

	public static String toBlankString(String num) {
		String str = null;
		if (isEmpty(num)) {
			return str;
		}
		str = String.valueOf(num);
		return str;
	}
	
	public static BigDecimal stringToBigDecimal(String str) {
		BigDecimal num = null;
		if (isEmpty(str)) {
			return num;
		}

		num = new BigDecimal(str);
		return num;
	}
    public static BigDecimal stringToBigDecimal2(String str) {
        BigDecimal num = null;
        if (isEmpty(str)) {
            return num;
        }

        num = new BigDecimal(str);
        num = num.setScale(2, BigDecimal.ROUND_HALF_UP);
        return num;
    }
	public static Double stringToDouble(String str) {
		Double num = null;
		if (isEmpty(str)) {
			return num;
		}

		num = new Double(str);
		return num;
	}
	
    public static Float stringToFloat(String str) {
        Float num = null;
        if (isEmpty(str)) {
            return num;
        }

        num = new Float(str);
        return num;
    }
    
	public static int stringToInt(String str) {
		int num = 0;
		if (isEmpty(str)) {
			return num;
		}
		num = Integer.parseInt(str);
		return num;
	}

	public static BigInteger stringToBigInteger(String str) {
		BigInteger num = null;
		if (isEmpty(str)) {
			return num;
		}
		num = new BigInteger(str);
		return num;
	}

	public static Long stringToLong(String str) {
		Long num = null;
		if (isEmpty(str)) {
			return num;
		}
		num = new Long(str);
		return num;
	}
	
	public static Integer stringToInteger(String str) {
		Integer num = null;
		if (isEmpty(str)) {
			return num;
		}
		num = new Integer(str);
		return num;
	}
	
	public static Date toDate(String sDate, String sFmt) {
		return toDate(sDate, new SimpleDateFormat(sFmt));
	}
    public static String toString(
            Timestamp dt,
            SimpleDateFormat formatter) {
            return formatter.format(dt).toString();
    }
    
    public static String toString(Timestamp dt) {
        return toString(dt, sdfDateTime);
    }
	public static Date toDate(String sDate, SimpleDateFormat formatter) {

		if (null == sDate) {
			return null;
		}

		Date dt = null;
		try {
			java.util.Date tmp = formatter.parse(sDate);
			dt = new java.util.Date(tmp.getTime());
		} catch (ParseException e) {
			dt = null;
		}
		return dt;
	}
	   
    public static String filter(String value) {
        if (null == value) {
            return "&nbsp;";
        }

        StringBuffer result = new StringBuffer();

        for (int i = 0; i < value.length(); i++) {
            final char ch = value.charAt(i);

            if ('<' == ch) {
                result.append("&lt;");
            } else if ('>' == ch) {
                result.append("&gt;");
            } else if ('&' == ch) {
                result.append("&amp;");
            } else if ('"' == ch) {
                result.append("&quot;");
            } else if ('\'' == ch) {
                result.append("&apos;");
            } else if ('\r' == ch) {
                result.append("<BR>");
            } else if ('\n' == ch) {
                if (0 < i && '\r' == value.charAt(i - 1)) {
                    doNothing();
                } else {
                    result.append("<BR>");
                }
            } else if ('\t' == ch) {
                result.append("&nbsp;&nbsp;&nbsp;&nbsp");
            } else {
                result.append(ch);
            }
        }
        return (result.toString());
    }
    
    private static void doNothing() {
    }

    public static double round(double v,BigDecimal one,int scale){
        BigDecimal b = new BigDecimal(Double.toString(v));
        return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
