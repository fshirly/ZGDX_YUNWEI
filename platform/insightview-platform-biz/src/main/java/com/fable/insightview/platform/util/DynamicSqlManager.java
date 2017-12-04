/**
 * 
 */
package com.fable.insightview.platform.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.parsing.GenericTokenParser;
import org.apache.ibatis.parsing.TokenHandler;

import com.fable.insightview.platform.common.util.Cast;
import com.fable.insightview.platform.common.util.DateTimeUtil;
import com.fable.insightview.platform.common.util.StringUtil;
import com.fable.insightview.platform.common.util.Tool;
import com.fable.insightview.platform.util.exception.BusinessException;
import com.fable.insightview.platform.util.macro.Macro;
import com.fable.insightview.platform.util.macro.MacroContainer;

/**
 * @author zhouwei
 * 
 */
public class DynamicSqlManager {

	/**
	 * 宏变量开始
	 */
	private static final String MACRO_OPEN_TOKEN = "${";
	
	/**
	 * 宏变量结束
	 */
	private static final String MACRO_CLOSE_TOKEN = "}";

	/**
	 * 变量开始
	 */
	private static final String VARIABLE_OPEN_TOKEN = "#{";
	
	/**
	 * 变量结束
	 */
	private static final String VARIABLE_CLOSE_TOKEN = "}";
	
	/**
	 * 检查filterSql
	 * 
	 * @param target
	 * @return
	 * @throws BusinessException
	 */

	public static String checkFilterSql(String target) throws BusinessException {
		if (StringUtil.isEmpty(target)) {
			return "";
		}

		// 检查左括号出现的次数
		List<Integer> lefts = findIndex(target, "(");
		// 检查右括号出现的次数
		List<Integer> rights = findIndex(target, ")");
		if (lefts.size() != rights.size()) {
			throw new BusinessException("00300011" , new String[]{target});
		}
		if (lefts.size() > 0) {
			int size = lefts.size();
			for (int i = size - 1; i >= 0; i--) {
				int rightIndex = findSmallest(lefts.get(i), rights);
				if (rightIndex == -1) {
					throw new BusinessException(
							"00300011" , new String[]{target});
				}
				String period = target.substring(lefts.get(i) + 1, rightIndex)
						.trim();

				if (period.startsWith("and")) {
					String newTarget = target.substring(0, lefts.get(i))
							+ " ("
							+ period.substring("and"
									.length()) + ") "
							+ target.substring(rightIndex + 1);
					return checkFilterSql(newTarget);
				}
				if (period.endsWith("and")) {
					String newTarget = target.substring(0, lefts.get(i))
							+ " ("
							+ period.substring(0, period.length()
									- "and".length())
							+ ") " + target.substring(rightIndex + 1);
					return checkFilterSql(newTarget);
				}
				if (period.startsWith("or")) {
					String newTarget = target.substring(0, lefts.get(i))
							+ " ("
							+ period.substring("or"
									.length()) + ") "
							+ target.substring(rightIndex + 1);
					return checkFilterSql(newTarget);
				}
				if (period.endsWith("or")) {
					String newTarget = target.substring(0, lefts.get(i))
							+ " ("
							+ period.substring(0, period.length()
									- "or".length())
							+ ") " + target.substring(rightIndex + 1);
					return checkFilterSql(newTarget);
				}
				if (StringUtil.isEmpty(period)) {
					String newTarget = target.substring(0, lefts.get(i)) + " "
							+ target.substring(rightIndex + 1);
					return checkFilterSql(newTarget);
				}
			}
		}

		if (StringUtil.equals(StringUtil.trimToEmpty(target),
				"and")
				|| StringUtil.equals(StringUtil.trimToEmpty(target),
						"or")) {
			return "";
		}

		if (StringUtil.endsWith(StringUtil.trimToEmpty(target),
				"and")) {
			target = target.trim().substring(
					0,
					target.trim().length()
							- "and".length());
		} else if (StringUtil.endsWith(StringUtil.trimToEmpty(target),
				"or")) {
			target = target.trim()
					.substring(
							0,
							target.trim().length()
									- "or".length());
		}

		return target;

	}

	/**
	 * 查找period出现的所有位置
	 * 
	 * @param target
	 * @param period
	 * @return
	 */
	public static List<Integer> findIndex(String target, String period) {

		List<Integer> result = new ArrayList<Integer>();

		if (StringUtil.isEmpty(target)) {
			return result;
		}

		int index = -1;

		do {
			index = target.indexOf(period, index + 1);
			if (index != -1) {
				result.add(index);
			}
		} while (index != -1);

		return result;
	}

	/**
	 * 查找相匹配的右括号
	 * 
	 * @param left
	 * @param rights
	 * @return
	 */
	public static int findSmallest(int left, List<Integer> rights) {
		for (int right : rights) {
			if (right > left) {
				rights.remove(new Integer(right));
				return right;
			}
		}
		return -1;
	}
	
	/**
	 * select * from (SQL) TEMP 
	 * 
	 * @param sql
	 * @return
	 */
	public static String transferToSelectStarSql(String sql) {
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT * FROM ( ").append(sql).append(" ) temp ");
		return sb.toString();
	}
	
	/**
	 * 
	 * 替换宏变量
	 * @param originalSql
	 * @param ignore
	 * @return
	 */
	public static String replaceMacros(final String originalSql , final boolean ignore) {
		if(StringUtil.isEmpty(originalSql)){
			return "";
		}
		return new GenericTokenParser(MACRO_OPEN_TOKEN, MACRO_CLOSE_TOKEN, new TokenHandler() {
			@Override
			public String handleToken(String key) {
				Macro macro = MacroContainer.getMacro(key);
				if(macro == null){
					if(ignore){
						return MACRO_OPEN_TOKEN + key + MACRO_CLOSE_TOKEN;
					}else{
						throw new BusinessException("00300011",new String[]{originalSql});
					}
				}
				return macro.getValue();
			}
		}).parse(originalSql);
	}
	
	/**
	 * 替换宏变量
	 * 
	 * @param userId
	 * @param strValue
	 * @return
	 */
	public static String replaceMacros_BAK(String userId, String sysId,
			String strValue) {
		
		if (StringUtil.isEmpty(strValue))
			return "";
		
		String strKey = strValue.toLowerCase();
		
		if (strKey.indexOf("@userid@") > -1) {
			strValue = strValue.replaceAll("@(?i)userid@", userId);
		}

		if (strKey.indexOf("@sysid@") > -1) {
			strValue = strValue.replaceAll("@(?i)sysid@", sysId);
		}

		if (strKey.equals("@username@") || strKey.equals("@loginname@")
				|| strKey.equals("@orgid@")) {

			if (strKey.equals("@username@")) {
				return userId;
			}
			if (strKey.equals("@loginname@")) {
				return userId;
			}
		}

		if (strKey.indexOf("@year@") > -1) {
			strValue = strValue.replaceAll("@(?i)year@",
					Cast.toString(Calendar.getInstance().get(Calendar.YEAR)));
		}
		if (strKey.indexOf("@month@") > -1) {
			strValue = strValue.replaceAll("@(?i)month@", Cast
					.toString(Calendar.getInstance().get(Calendar.MONTH) + 1));
		}
		if (strKey.indexOf("@day@") > -1) {
			strValue = strValue.replaceAll(
					"@(?i)day@",
					Cast.toString(Calendar.getInstance().get(
							Calendar.DAY_OF_MONTH)));
		}

		if (strKey.indexOf("@currentdate@") > -1) {
			strValue = strValue.replaceAll("@(?i)currentdate@",
					Tool.nowToString());
		}

		if (strKey.indexOf("@yearfirst@") > -1) {
			strValue = strValue.replaceAll("@(?i)yearfirst@",
					DateTimeUtil.dateToString(Tool.Now(), "yyyy") + "-01-01");
		}
		if (strKey.indexOf("@monthfirst@") > -1) {
			strValue = strValue.replaceAll("@(?i)monthfirst@",
					DateTimeUtil.dateToString(Tool.Now(), "yyyy-MM") + "-01");
		}

		if (strKey.indexOf("@yearlast@") > -1) {
			strValue = strValue.replaceAll("@(?i)yearlast@",
					DateTimeUtil.dateToString(Tool.Now(), "yyyy") + "-12-31");
		}
		if (strKey.indexOf("@monthlast@") > -1) {
			Calendar nn;
			nn = Calendar.getInstance();

			strValue = strValue
					.replaceAll(
							"@(?i)monthlast@",
							DateTimeUtil.dateToString(
									Tool.Now(),
									"yyyy-MM-"
											+ Cast.toString(nn
													.getActualMaximum(Calendar.DAY_OF_MONTH))));
		}

		return strValue;
	}
	
	/**
	 * 替换带有冒号的参数，并且将参数从前台条件中移除，配置的condition条件中将不会处理
	 * 
	 * @param params
	 * @param sql
	 * @return
	 */
	public static String replaceColonParams(HashMap<String, String> params, String sql , final boolean ignore) {
		if (StringUtil.isNotEmpty(sql)) {
			Pattern p = Pattern.compile("(:([a-zA-Z_0-9]*))");
			Matcher m = p.matcher(sql);

			List<String> removeParameters = new ArrayList<String>();

			while (m.find()) {
				String placeholder = StringUtil.trimToEmpty(m.group(1));
				String parameter = StringUtil.trimToEmpty(m.group(2));

				if (params.get(parameter) != null) {
					sql = sql.replaceAll(placeholder,
							"'" + StringUtil.trimToEmpty(params.get(parameter))
									+ "'");
					removeParameters.add(parameter);
				} else {
					// 不存在就不做任何处理
					// sql = "";
					// removeParameters.clear();
					// break;
					if(!ignore){
						throw new BusinessException("00300011",new String[]{sql});
					}
				}
			}

			if (removeParameters != null && !removeParameters.isEmpty()) {
				for (String removeParameter : removeParameters) {
					params.remove(removeParameter);
				}
			}
		}
		return sql;
	}
	
	/**
	 * 替换带有#{}的参数，并且将参数从前台条件中移除，配置的condition条件中将不会处理
	 * 
	 * @param params
	 * @param originalSql
	 * @return
	 */
	public static String replaceVariableParams(final HashMap<String, String> params, final String originalSql , final boolean ignore) {
		String sql = "";
		if (StringUtil.isNotEmpty(originalSql)) {
			final List<String> removeKeys = new ArrayList<String>();
			sql = new GenericTokenParser(VARIABLE_OPEN_TOKEN, VARIABLE_CLOSE_TOKEN, new TokenHandler() {
				@Override
				public String handleToken(String key) {
					String value = "''";
					if(StringUtil.isNotEmpty(key)){
						if(params==null || !params.containsKey(key)){
							if(!ignore){
								throw new BusinessException("00300011",new String[]{originalSql});
							}
						}else{
							value = "'" + StringUtil.trimToEmpty(params.get(key)) + "'";
							removeKeys.add(key);
						}
					}
					return value;
				}
			}).parse(originalSql);
			
			if (removeKeys != null && !removeKeys.isEmpty()) {
				for (String removeKey : removeKeys) {
					params.remove(removeKey);
				}
			}
		}
		return sql;
	}
	
	/**
	 * 分析是否有带冒号的可变参数，有就存放到list中
	 * 
	 * @param sql
	 * @return
	 */
	public static List<String> analysisSqlWithColonParameter(String sql) {
		List<String> result = new ArrayList<String>();
		if (StringUtil.isEmpty(sql)) {
			return result;
		}
		Pattern p = Pattern.compile("(:([a-zA-Z_0-9]*))");
		Matcher m = p.matcher(sql);
		while (m.find()) {
			// String placeholder = StringUtil.trimToEmpty(m.group(1));
			String parameter = StringUtil.trimToEmpty(m.group(2));
			if (!result.contains(parameter)) {
				result.add(parameter);
			}
		}
		return result;
	}
	
	/**
	 * 分析是否有带#{}的可变参数，有就存放到list中
	 * 
	 * @param sql
	 * @return
	 */
	public static List<String> analysisSqlWithVariableParameter(String sql) {
		final List<String> result = new ArrayList<String>();
		if (StringUtil.isEmpty(sql)) {
			return result;
		}
		new GenericTokenParser(VARIABLE_OPEN_TOKEN, VARIABLE_CLOSE_TOKEN, new TokenHandler() {
			@Override
			public String handleToken(String key) {
				if (!result.contains(key)) {
					result.add(key);
				}
				return key;
			}
		}).parse(sql);
		return result;
	}

}
