/**
 * 
 */
package com.fable.insightview.platform.util;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;

/**
 * @author zhouwei
 * 
 */
public interface Constants {

	/**
	 * targetType
	 */
	interface TARGET_TYPE {
		/**
		 * 用户
		 */
		String USER = "1";

		/**
		 * 角色
		 */
		String ROLE = "2";
	}

	/**
	 * 控件类型
	 */
	interface CONTROL_TYPE {

		/**
		 * 隐藏域
		 */
		String HIDDEN = "hidden";

		/**
		 * 文本框
		 */
		String TEXT = "text";

		/**
		 * 选择框
		 */
		String SELECT = "select";

		/**
		 * 弹出框
		 */
		String POPUP = "popup";

		/**
		 * 下拉树
		 */
		String TREE = "tree";

		/**
		 * 日期
		 */
		String DATE = "date";

		/**
		 * 日期时间
		 */
		String DATETIME = "datetime";
	}

	/**
	 * 左括号
	 */
	interface LEFT_BRACKET {
		/**
		 * 无
		 */
		String EMPTY = "";

		/**
		 * 左1括号
		 */
		String LEFT_ONE = "(";

		/**
		 * 左2括号
		 */
		String LEFT_TWO = "((";

		/**
		 * 左3括号
		 */
		String LEFT_THREE = "(((";
	}

	/**
	 * 比较符
	 */
	interface OPERATOR {
		/**
		 * 等于
		 */
		String EQUAL = "equal";

		/**
		 * 不等于
		 */
		String NOTEQUAL = "notEqual";

		/**
		 * 大于
		 */
		String GREATER = "greater";

		/**
		 * 小于
		 */
		String LESS = "less";

		/**
		 * 大于等于
		 */
		String GREATEREQUAL = "greaterEqual";

		/**
		 * 小于等于
		 */
		String LESSEQUAL = "lessEqual";

		/**
		 * 左匹配
		 */
		String LEFTLIKE = "leftLike";

		/**
		 * 右匹配
		 */
		String RIGHTLIKE = "rightLike";

		/**
		 * 匹配
		 */
		String ALLLIKE = "allLike";

		/**
		 * 包含
		 */
		String IN = "in";

		/**
		 * 不包含
		 */
		String NOTIN = "notIn";
	}

	/**
	 * 右括号
	 */
	interface RIGHT_BRACKET {
		/**
		 * 无
		 */
		String EMPTY = "";

		/**
		 * 右1括号
		 */
		String RIGHT_ONE = ")";

		/**
		 * 右2括号
		 */
		String RIGHT_TWO = "))";

		/**
		 * 右3括号
		 */
		String RIGHT_THREE = ")))";
	}

	/**
	 * 逻辑运算符
	 */
	interface LOGIC_SYMBOL {
		/**
		 * 并且
		 */
		String AND = "and";

		/**
		 * 或者
		 */
		String OR = "or";
	}

	/**
	 * 数据源类型
	 */
	interface SOURCE_TYPE {
		/**
		 * 无
		 */
		String EMPTY = "0";

		/**
		 * 数据字典
		 */
		String DICTIONARY = "1";

		/**
		 * JSON
		 */
		String JSON = "2";

		/**
		 * URL
		 */
		String URL = "3";

		/**
		 * SQL
		 */
		String SQL = "4";

		/**
		 * 单位机构
		 */
		String ORG = "5";
		
		/**
		 * 部门
		 */
		String DEPARTMENT = "6";
		
		/**
		 * 岗位
		 */
		String POST = "7";
		
		/**
		 * 人员带岗位
		 */
		String USER = "8";
		
		/**
		 * 人员不带岗位
		 */
		String USER_NO_POST = "9";
		
		/**
		 * 角色
		 */
		String ROLE = "10";

		/**
		 * 角色人员
		 */
		String ROLE_USER = "11";
	}
	
	/**
	 * 数据源类型
	 */
	interface SYS_ORG_TYPE {
		/**
		 * 单位机构
		 */
		String ORG = "org";
		
		/**
		 * 部门
		 */
		String DEPARTMENT = "department";
		
		/**
		 * 岗位
		 */
		String POST = "post";
		
		/**
		 * 人员带岗位
		 */
		String USER = "user";
		
		/**
		 * 人员不带岗位
		 */
		String USER_NO_POST = "userNoPost";
		
		/**
		 * 角色
		 */
		String ROLE = "role";

		/**
		 * 角色人员
		 */
		String ROLE_USER = "roleUser";
	}

	/**
	 * 是否
	 */
	interface YES_OR_NO {
		/**
		 * 是
		 */
		String YES = "1";

		/**
		 * 否
		 */
		String NO = "0";
	}

	/**
	 * 真假
	 */
	interface TRUE_OR_FALSE {
		/**
		 * 真
		 */
		String TRUE = "true";

		/**
		 * 假
		 */
		String FALSE = "false";
	}

	/**
	 * SQL 片段
	 */
	interface SQL_FRAGMENT {
		/**
		 * 空白
		 */
		String SPACE = " ";
		
		/**
		 * *
		 */
		String STAR = "*";

		/**
		 * where
		 */
		String WHERE = "where";

		/**
		 * select
		 */
		String SELECT = "select";
		
		/**
		 * from
		 */
		String FROM = "from";

		/**
		 * 永真条件
		 */
		String TRUE_SQL = "1=1";

		/**
		 * 永假条件
		 */
		String FALSE_SQL = "1=0";

		/**
		 * and
		 */
		String AND = "and";

		/**
		 * or
		 */
		String OR = "or";

		/**
		 * 左括号
		 */
		String LEFT = "(";

		/**
		 * 右括号
		 */
		String RIGHT = ")";
	}

	/**
	 * SQL类型
	 */
	interface SQL_TYPE {
		/**
		 * 表
		 */
		String TABLE = "1";

		/**
		 * 视图
		 */
		String VIEW = "2";

		/**
		 * 自定义SQL
		 */
		String SQL = "3";
	}

	/**
	 * 操作类型
	 * 
	 * @author nimj
	 */
	public enum OperationType {
		LOGIN("0", "登录"), QUERY("1", "查询"), ADD("2", "新增"), MODIFY("3", "修改"), DELETE(
				"4", "删除");

		private String code;
		private String text;

		private OperationType(String code, String text) {
			this.code = code;
			this.text = text;
		}

		public static String getTextByCode(String code) {
			if (StringUtils.isEmpty(code)) {
				return "";
			}
			OperationType[] types = OperationType.values();
			for (OperationType type : types) {
				if (code.equals(type.code)) {
					return type.text;
				}
			}
			return "";
		}

		public static JSONObject getOperationTypes() {
			JSONObject jsonObj = new JSONObject();
			OperationType[] types = OperationType.values();
			for (OperationType type : types) {
				jsonObj.put(type.code, type.text);
			}
			return jsonObj;
		}
	}

	/**
	 * 操作结果
	 * 
	 * @author nimj
	 */
	public enum OperationResult {
		FAILURE("0", "失败"), SUCCESS("1", "成功");

		private String code;
		private String text;

		private OperationResult(String code, String text) {
			this.code = code;
			this.text = text;
		}

		public static String getTextByCode(String code) {
			if (StringUtils.isEmpty(code)) {
				return "";
			}
			OperationResult[] results = OperationResult.values();
			for (OperationResult result : results) {
				if (code.equals(result.code)) {
					return result.text;
				}
			}
			return "";
		}

		public static JSONObject getOperationResults() {
			JSONObject jsonObj = new JSONObject();
			OperationResult[] results = OperationResult.values();
			for (OperationResult result : results) {
				jsonObj.put(result.code, result.text);
			}
			return jsonObj;
		}
	}

	/**
	 * 任务状态
	 * 
	 * @author nimj
	 */
	public enum TaskStatus {
		STOP("0", "停止"), START("1", "启动");

		public String value;
		public String text;

		private TaskStatus(String value, String text) {
			this.value = value;
			this.text = text;
		}
	}

	/**
	 * 日志类型
	 * 
	 * @author nimj
	 */
	public enum SystemLogType {
		AccessLog("1", "系统访问日志"), OperationLog("2", "系统操作日志"), ExceptionLog(
				"3", "系统异常日志");

		public String code;
		public String text;

		private SystemLogType(String code, String text) {
			this.code = code;
			this.text = text;
		}
	}

	/**
	 * 用户类型
	 * 
	 * @author nimj
	 */
	public enum UserType {
		BusinessUser("1", "业务用户"), SystemUser("2", "系统用户");

		public String code;
		public String text;

		private UserType(String code, String text) {
			this.code = code;
			this.text = text;
		}
	}
}
