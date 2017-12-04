package com.fable.insightview.platform.util;

import com.fable.insightview.platform.common.util.StringUtil;

/**
 * @author zhouwei sql的比较符号
 */
public enum OperatorEnum {
	/**
	 * 等于
	 */
	EQUAL(Constants.OPERATOR.EQUAL, "="),

	// 不等于
	NOTEQUAL(Constants.OPERATOR.NOTEQUAL, "<>"),

	// 大于
	GREATER(Constants.OPERATOR.GREATER, ">"),

	// 小于
	LESS(Constants.OPERATOR.LESS, "<"),

	// 左like
	LEFTLIKE(Constants.OPERATOR.LEFTLIKE, "like"),

	// 右like
	RIGHTLIKE(Constants.OPERATOR.RIGHTLIKE, "like"),

	// 全like
	ALLLIKE(Constants.OPERATOR.ALLLIKE, "like"),

	// 存在于
	IN(Constants.OPERATOR.IN, "in"),

	// 不存在于
	NOTIN(Constants.OPERATOR.NOTIN, "not in"),

	// 大于等于
	GREATEREQUAL(Constants.OPERATOR.GREATEREQUAL, ">="),

	// 小于等于
	LESSEQUAL(Constants.OPERATOR.LESSEQUAL, "<=");

	private String name;

	private String value;

	OperatorEnum(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public static String getValueByName(String name) {
		for (OperatorEnum operator : OperatorEnum.class.getEnumConstants()) {
			if (StringUtil.equals(name, operator.getName())) {
				return operator.getValue();
			}
		}
		return name;
	}
}
