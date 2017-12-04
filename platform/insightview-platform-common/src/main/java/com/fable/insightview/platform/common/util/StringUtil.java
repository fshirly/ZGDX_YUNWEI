/**
 * 
 */
package com.fable.insightview.platform.common.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

/**
 * 字符串操作相关工具类
 * @author wuanguo
 * 
 */
public final class StringUtil extends org.apache.commons.lang.StringUtils {
	
	public static final String STRING_BLANK = " ";

    /**
     * 基本功能：替换标记正常显示
     *
     * @param input
     * @return String
     */
    public static String replaceTag(String input) {
        if (!hasSpecialChars(input)) {
            return input;
        }
        StringBuilder filtered = new StringBuilder(input.length());
        char c;
        for (int i = 0; i <= input.length() - 1; i++) {
            c = input.charAt(i);
            switch (c) {
                case '<':
                    filtered.append("&lt;");
                    break;
                case '>':
                    filtered.append("&gt;");
                    break;
                case '"':
                    filtered.append("&quot;");
                    break;
                case '\'':
                    filtered.append("&#39;");
                    break;
                case '|':
                    filtered.append("&#124;");
                    break;
                case '(':
                    filtered.append("&#40;");
                    break;
                case ')':
                    filtered.append("&#41;");
                    break;
                case '\\':
                    filtered.append("&#92;");
                    break;
                /*case '&':
                    filtered.append("&amp;");
                    break;*/
//                case '/':
//                    filtered.append("&frasl;");
//                    break;
                default:
                    filtered.append(c);
            }

        }
        return (filtered.toString());
    }

    /**
     * 过滤html标记返回文本内容
     *
     * @param htmlStr
     * @return
     */
    public static String getClearTagString(String htmlStr) {
        String regxpForHtml = "<([^>]*)>"; // 过滤所有以<开头以>结尾的标签
        Pattern pattern = Pattern.compile(regxpForHtml);

        Matcher matcher = pattern.matcher(htmlStr);
        StringBuffer sb = new StringBuffer();
        boolean result1 = matcher.find();
        while (result1) {
            matcher.appendReplacement(sb, "");
            result1 = matcher.find();
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 判断标记是否存在
     *
     * @param input
     * @return boolean
     */
    public static boolean hasSpecialChars(String input) {
        boolean flag = false;
        if (isNotBlank(input)) {
            char c;
            for (int i = 0; i <= input.length() - 1; i++) {
                c = input.charAt(i);
                switch (c) {
                    case '>':
                        flag = true;
                        break;
                    case '<':
                        flag = true;
                        break;
                    case '"':
                        flag = true;
                        break;
                    case '&':
                        flag = true;
                        break;
                    case '\'':
                        flag = true;
                        break;
                    case '|':
                        flag = true;
                        break;
                    case '(':
                        flag = true;
                        break;
                    case ')':
                        flag = true;
                        break;
                    case '\\':
                        flag = true;
                        break;
                }
                if (flag) {
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;

    }

    /**
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * @param strings
     * @param str
     * @return
     */
    public static boolean contains(String[] strings, String str) {
        if (strings == null || strings.length == 0) {
            return false;
        }

        if (str == null) {
            return false;
        }

        for (int i = 0; i < strings.length; i++) {
            if (strings[i].equals(str)) {
                return true;
            }
        }

        return false;
    }

    //字节转KB
    public static String convertByteToKB(long size, String formatPattern, boolean addPostfix) {
        if (size <= 0) {
            return "0KB";
        }

        if (size < 1000) {
            return "1KB";
        }

        if (StringUtil.isEmpty(formatPattern)) {
            formatPattern = "####.000000";
        }

        DecimalFormat df1 = new DecimalFormat(formatPattern);
        String r = df1.format(size * 0.1 / (1024 * 0.1));

        return StringUtil.substring(r, 0, StringUtil.indexOf(r, '.') + 3) + (addPostfix ? " KB" : "");
    }

    //字节转MB
    public static String convertByteToMB(long size, String formatPattern, boolean addPostfix) {
        if (StringUtil.isEmpty(formatPattern)) {
            formatPattern = "####.000000";
        }

        DecimalFormat df1 = new DecimalFormat(formatPattern);

        // return df1.format(size * 0.1 / (1024 * 1024 * 0.1)) + (addPostfix ? " MB" : "");
        String r = df1.format(size * 0.1 / (1024 * 1024 * 0.1));

        if (".".equals(StringUtil.substring(r, 0, 1))) {
            r = "0" + r;
        }
        return StringUtil.substring(r, 0, StringUtil.indexOf(r, '.') + 3) + (addPostfix ? " MB" : "");
    }

    /**
     * @param size
     * @param formatPattern
     * @param addPostfix
     * @return
     */
    public static String convertByteToGB(long size, String formatPattern, boolean addPostfix) {
        if (StringUtil.isEmpty(formatPattern)) {
            formatPattern = "####.000000";
        }

        DecimalFormat df1 = new DecimalFormat(formatPattern);

        /// return df1.format(size * 0.1 / (1024 * 1024 * 1024 * 0.1)) + (addPostfix ? " GB" : "");

        String r = df1.format(size * 0.1 / (1024 * 1024 * 1024 * 0.1));

        return StringUtil.substring(r, 0, StringUtil.indexOf(r, '.') + 3) + (addPostfix ? " GB" : "");
    }

    /**
     * @param size
     * @param formatPattern
     * @param addPostfix
     * @return
     */
    public static String convertByteToSuitableSize(long size, String formatPattern, boolean addPostfix) {

        if (size > FileUtils.ONE_GB) {
            return convertByteToGB(size, formatPattern, addPostfix);
        } else if (size > FileUtils.ONE_MB) {
            return convertByteToMB(size, formatPattern, addPostfix);
        } else {
            return convertByteToKB(size, formatPattern, addPostfix);
        }
    }

    /**
     * @param size
     * @param formatPattern
     * @param addPostfix
     * @param unit
     * @return
     */
    public static String convertByteToSuitableSize(long size, String formatPattern, boolean addPostfix, String unit) {

        if (size > FileUtils.ONE_GB) {
            return convertByteToGB(size, formatPattern, addPostfix);
        } else if (size > FileUtils.ONE_MB) {
            if ("gb".equals(unit)) {
                return convertByteToGB(size, formatPattern, addPostfix);
            } else {
                return convertByteToMB(size, formatPattern, addPostfix);
            }
        } else {
            if ("gb".equals(unit)) {
                return convertByteToGB(size, formatPattern, addPostfix);
            } else if ("mb".equals(unit)) {
                return convertByteToMB(size, formatPattern, addPostfix);
            } else {
                return convertByteToKB(size, formatPattern, addPostfix);
            }

        }
    }

    /**
     * @param e
     * @return
     */
    public static String encodeBase64(String e) {
        String result = null;

        if (isNotBlank(e)) {
            result = new String(Base64.encodeBase64(e.getBytes()));
        }

        return result;
    }

    /**
     * @param e
     * @return
     */
    public static String decodeBase64(String e) {
        String result = null;

        if (isNotBlank(e)) {
            result = new String(Base64.decodeBase64(e.getBytes()));
        }

        return result;
    }

    /**
     * 将list中元素转化成字符串类型。list中值为null的元素将被删除
     *
     * @param list 要被转化的集合
     * @return 包含字符串的集合
     */
    public static List toStringList(List list) {
        List result = new ArrayList(list.size());
        Object obj;
        for (int i = 0, n = list.size(); i < n; i++) {
            obj = list.get(i);
            if (obj != null) {
                result.add(obj.toString());
            }
        }
        return result;
    }

    /**
     * 将分号等分割符统一成“|”分隔符。如：“;;aa1;;aa;;qq”处理成“aa1|aa|qq|”
     *
     * @param raw              原始字符串
     * @param separators       分割字符
     * @param uniformSeparator 使用统一的分割符
     * @return 处理后的字符串
     */
    public static String uniformSeparator(String raw, String separators, String uniformSeparator) {
        String agsToCheck = raw;
        StringBuffer sb = new StringBuffer(raw.length());
        sb.append(uniformSeparator);
        int repeat = 0;
        char testWord;
        for (int i = 0; i < agsToCheck.length(); i++) {
            testWord = agsToCheck.charAt(i);
            if (separators.indexOf(testWord) != -1) {
                if (i == 0) {
                    agsToCheck = agsToCheck.replaceFirst("\\" + testWord, StringUtil.EMPTY);
                    i--;
                } else {
                    if (repeat == 0) {
                        sb.append(uniformSeparator);
                    }
                    repeat += 1;
                }
            } else {
                if (repeat > 0) {
                    repeat = 0;
                }
                sb.append(testWord);
            }
        }

        if (sb.lastIndexOf(uniformSeparator) != sb.length() - 1) {
            sb.append(uniformSeparator);
        }
        return sb.toString();
    }

    /**
     * 将一个Long类型转换成一个指定长度的字符串，不够前补0
     *
     * @param tempLong
     * @param length   最小宽度 转换之后最少几位
     * @return
     */
    private static String autoComplete(Long tempLong, int length) {
        /**
         * 处理空指针问题
         */
        if (tempLong == null) {
            tempLong = Long.valueOf(0);
        }

        /**
         * 代表前面补充0
         * 代表长度为4
         * d代表参数为十进制
         */
        final String regString = "%0" + String.valueOf(length) + "d";

        return String.format(regString, tempLong);
    }

    /**
     * 将带有中文的url链接加密
     *
     * @param url     链接地址
     * @param charset 字符集
     * @return 加密过的url
     */
    public static String encodeUrlWithCharacters(String url, String charset) {
        try {
            Matcher matcher = Pattern.compile("[^\\x00-\\xff]").matcher(url);
            while (matcher.find()) {
                String tmp = matcher.group();
                url = url.replaceAll(tmp, java.net.URLEncoder.encode(tmp, charset));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * 提取出content中img内容.
     *
     * @return 提取的图片
     */
    public static List<String> getFileUrls(String content) {
        if (isNotBlank(content)) {
            List<String> resultList = new ArrayList<String>();
            String regxp = "(src|href)=\"([^\"]+)\"";

            Pattern p = Pattern.compile(regxp);
            Matcher m = p.matcher(content);

            while (m.find()) {
                if (m.group(2).indexOf("fckeditor") == -1)
                    resultList.add(m.group(2));//获取被匹配的部分,并且去除fck的表情图片.
            }
            return resultList;
        }
        return Collections.emptyList();
    }


    /**
     * 过滤指定标签.
     *
     * @param str 待过滤的字符串
     * @param tag 过滤的标签
     * @return 过滤后的字符串
     */
    private String filterHtmlTag(String str, String tag) {
        if (isNotBlank(str)) {
            String regxp = "<\\s*" + tag + "\\s+([^>]*)\\s*>";
            Pattern pattern = Pattern.compile(regxp);
            Matcher matcher = pattern.matcher(str);
            StringBuffer sb = new StringBuffer();
            boolean result1 = matcher.find();

            while (result1) {
                matcher.appendReplacement(sb, "");
                result1 = matcher.find();
            }
            matcher.appendTail(sb);
            return sb.toString();
        }
        return str;
    }

    /**
     * @param tempStr
     * @return
     */
    public static String capitalizeStr(String tempStr) {
        if (StringUtil.isBlank(tempStr)) {
            return StringUtil.EMPTY;
        }

        StringBuffer stringBuffer = new StringBuffer(400);

        String[] arrStr = StringUtil.split(tempStr, "_");
        for (int i = 0; i < arrStr.length; i++) {
            stringBuffer.append(StringUtil.capitalize(arrStr[i]));
        }

        return stringBuffer.toString();
    }

    /**
     * @param shortPinyin
     * @return
     */
    public static String replaceSpecialCharacters(String shortPinyin) {

        String regEx = "[-`~!@#$%^&*()+=|{}':;',\\[\\]\".<>《》/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\s*]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(shortPinyin);
        return m.replaceAll("fh").trim();
    }

    /**
     * @param str
     * @return
     */
    public static String base64Encode(String str) {
        if (StringUtil.isEmpty(str)) return StringUtil.EMPTY;
        return new sun.misc.BASE64Encoder().encode(str.getBytes());
    }

    /**
     * @param str
     * @return
     */
    public static String base64Decode(String str) {
        if (StringUtil.isEmpty(str)) return StringUtil.EMPTY;
        byte[] bt;
        try {
            sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
            bt = decoder.decodeBuffer(str);
            str = new String(bt, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 字符串截取 比如：/2014/11/10/12/
     * 截取到第几个/为止
     *
     * @param str   要截取的字符串
     * @param split 分隔符
     * @param index 第几个
     * @return
     */
    public static String substring(String str, String split, int index) {
        if (StringUtil.isEmpty(str) ||
                StringUtil.isEmpty(split) || index < 0) return StringUtil.EMPTY;
        String tmp[] = str.split(split);
        StringBuilder sb = new StringBuilder();
        if (index > tmp.length) return StringUtil.EMPTY;

        for (int i = 0; i < tmp.length; i++) {
            if (i < index) sb.append(tmp[i]).append(split);
        }
        tmp = null;
        return sb.toString();
    }

	/**
	 * 首字母转大写
	 * @param s 待转换的字符窜
	 * @return String 转化后的
	 */
	public static String toUpperCaseFirstOne(String s) {
		if (Character.isUpperCase(s.charAt(0))) {
			return s;
		} else {
			StringBuilder sb = new StringBuilder(s);
			sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
			return sb.toString();
		}
	}
	
	/**
	 * 下划线转驼峰风格
	 * 
	 * @param underscoreName
	 * @return
	 */
	public static String underscore2camel(String underscoreName){
		
		if(isEmpty(underscoreName)){
			return EMPTY;
		}
		
		String[] sections = underscoreName.toLowerCase().split("_");
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<sections.length;i++){
			String s = sections[i];
			if(i==0){
				sb.append(s);
			}else{
				sb.append(capitalize(s));
			}
		}
		return sb.toString();
	}
}
