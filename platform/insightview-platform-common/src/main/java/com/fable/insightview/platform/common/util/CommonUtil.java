package com.fable.insightview.platform.common.util;

import java.util.Map;
import java.util.regex.Pattern;

import org.codehaus.jackson.map.ObjectMapper;

import com.fable.insightview.platform.common.helper.ApplicationContextHelper;
import com.fable.insightview.platform.common.service.AdapterService;

public class CommonUtil {
	private static ObjectMapper mapper;


	private static final String reg = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
			+ "(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute|as|desc|asc)\\b)";

	private static final Pattern sqlPattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);

	/**
	 * 
	 * @param createNew
	 *            是否创建一个新的Mapper
	 * @return
	 */
	public static synchronized ObjectMapper getMapperInstance(boolean createNew) {
		if (createNew) {
			return new ObjectMapper();
		} else if (mapper == null) {
			mapper = new ObjectMapper();
		}
		return mapper;
	}

	public static int castLongToInt(Long id) {
		if (id == null) {
			return 0;
		} else {
			return Integer.parseInt(String.valueOf(id));
		}
	}
	
	public static <T>Map<String,T> getOtherSysyemServiceImpl(Class<T> t){
		return (Map<String, T>) ApplicationContextHelper.getApplicationContext().getBeansOfType(t);
	}
	
	/***************************************************************************
	 * 校验输入的字符串是否含有非法sql注入字符
	 * 
	 * @param str
	 */
	public static boolean isValid(String str) {

		if (sqlPattern.matcher(str).find()) {

			return false;
		}
		return true;
	}
}