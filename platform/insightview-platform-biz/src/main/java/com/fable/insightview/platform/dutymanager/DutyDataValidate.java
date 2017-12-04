package com.fable.insightview.platform.dutymanager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.fable.insightview.platform.dict.util.DictionaryLoader;

public class DutyDataValidate {

	public static Map<String, Object> validateData(List<Map<Integer, Object>> inx, List<String> users, List<String> dates, String[] titles, List<String> orderNames) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<Integer, Object>> effect = new ArrayList<Map<Integer, Object>>();
		if (null == titles || titles.length < 5) {/*最少需要包含一个值班班次信息*/
			result.put("error", "导入文件中至少包含一个值班班次！");
			result.put("effect", effect);
			return result;
		}
		StringBuilder dataInfo = new StringBuilder();
		List<String> existDate = new ArrayList<String>(dates);
		String dutyer = null;
		Map<Integer, Object> data = null;
		StringBuilder sb = new StringBuilder();
		int columns = titles.length;
		String dutyerName = null, dutyReserve = null, leaderName = null, level = null;
		for (int i = 0, size = inx.size(); i < size; i++) {
			data = inx.get(i);
			if(data.isEmpty()) {
				continue;
			}
			if (!validateDateRequired(data, columns)) {/* 验证值班日期必填 */
				sb.append("值班日期不能为空;");
			} else {
				if (!validateDateFormate(data, columns)) {
					sb.append("["+data.get(columns - 3)+"]为无效日期");
				}
				if (!validateDateRepeat(data, columns, existDate)) {
					sb.append(data.get(columns - 3) + "值班日期已排班;");
				}
			}
			/*if (!validateOrderRequired(data, columns)) { 验证值班负责人为必填 
				sb.append("没有设置值班班次中的值班负责人;");
			}*/
			leaderName = validateLeader((String[])data.get(0), users);
			if (null != leaderName) {/* 验证带班领导是否在值班组存在 */
				sb.append(leaderName);
			}
			
			dutyer = validateUserArr(concatArr(data, columns), users);
			if (null != dutyer) {/* 验证值班人是否在值班组存在 */
				sb.append(dutyer);
			}

			for (int j = 1; j < columns - 3; j++) {
				dutyerName = validateUserRq((String[]) data.get(j), users, true);
				if (null != dutyerName) {
					sb.append("[" + getRealTitle(titles[j], orderNames) + "]" + dutyerName);
				}
			}
			dutyReserve = validateUserRq((String[]) data.get(columns - 2), users, false);
			if (null != dutyReserve) {
				sb.append(titles[columns - 2] + ":" + dutyReserve);
			}
			level = validateLevel(data.get(columns - 1).toString());
			if(level != null) {
				sb.append(titles[columns - 1] + ":" + level);
			}
			if (sb.toString().length() == 0) {
				existDate.add(String.valueOf(data.get(columns - 3)));
				effect.add(data);
			} else {
				dataInfo.append("第" + (i + 2) + "行记录：" + sb.toString() + "<br/>");
				sb.delete(0, sb.toString().length());
			}
		}
		result.put("effect", effect);
		if (dataInfo.length() > 0) {
			result.put("error", dataInfo.toString());
		}
		return result;
	}

	public static String validateTitle(List<String> orders, String[] titles) {
		StringBuilder result = new StringBuilder();
		List<String> noExit = new ArrayList<String>();/* 存储不存在的值班班次信息 */
		List<String> exit = new ArrayList<String>();/* 存在的值班班次信息 */
		List<String> repeat = new ArrayList<String>();/* 存储重复出现的班次信息 */
		String orderName = null;
		boolean blank = false;
		for (int i = 1, size = titles.length - 3; i < size; i++) {
			orderName = getRealTitle(titles[i], orders);
			if (orders.contains(orderName)) {/* 验证值班班次存在 */
				if (exit.contains(orderName)) {
					repeat.add(orderName);
				}
				exit.add(orderName);
				continue;
			} else {
				if (StringUtils.isEmpty(orderName)) {
					blank = true;
				} else {
					noExit.add(orderName);
				}
			}
		}
		if (blank) {
			result.append("在导入文件中，存在值班班次为空;");
		}
		if (!noExit.isEmpty()) {
			result.append("[" + mutiToString(noExit) + "]值班班次不存在,请输入正确值班班次信息;");
		}
		if (!repeat.isEmpty()) {
			result.append("[" + mutiToString(repeat) + "]值班班次在文件中重复,请检查！");
		}
		return result.toString();
	}
	
	/**
	 * 获取班次名称
	 * @param orderName
	 * @return
	 */
	public static String getRealTitle (String orderName, List<String> orderNames) {
		//以“值班人”结尾的title且该title有且只有一个“值班人”
		if(orderName.endsWith("值班人") && orderName.indexOf("值班人")==orderName.lastIndexOf("值班人")) {
			if(!orderNames.contains(orderName)) {
				orderName = orderName.replace("值班人", "");
			}
		}
		//以“值班人”结尾的title且该title中不止一个“值班人”
		if(orderName.endsWith("值班人") && orderName.indexOf("值班人")!=orderName.lastIndexOf("值班人")) {
			if(!orderNames.contains(orderName)) {
				orderName = orderName.substring(0, orderName.length()-3);
			}
		}
		return orderName;
	}

	public static boolean validateUser(String user, List<String> users) {
		if (users.contains(user)) {
			return true;
		}
		return false;
	}

	/**
	 * 验证值班人员必填
	 * 
	 * @param data
	 * @return
	 */
	public static boolean validateOrderRequired(Map<Integer, Object> data, int columns) {
		for (int i = 1; i < columns - 2; i++) {
			if (data.containsKey(i)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 验证日期必填
	 * 
	 * @param data
	 * @return
	 */
	public static boolean validateDateRequired(Map<Integer, Object> data, int columns) {
		if (data.containsKey(columns - 3)) {
			return true;
		}
		return false;
	}

	/**
	 * 验证日期格式
	 * 
	 * @param data
	 * @param columns
	 * @return
	 */
	public static boolean validateDateFormate(Map<Integer, Object> data, int columns) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date = String.valueOf(data.get(columns - 3));
		if (date.length() > 10) {
			return false;
		}
		try {
			Date newDate = df.parse(date);
			String newDateStr = df.format(newDate);
			if(!date.equals(newDateStr)) {
				return false;
			}
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	/**
	 * 验证带班领导
	 * 
	 * @param data
	 * @param users
	 * @return
	 */
	public static boolean validateLeader(Map<Integer, Object> data, List<String> users) {
		String leader = (String) data.get(0);
		if (users.contains(leader)) {
			return true;
		}
		return false;
	}

	/**
	 * 验证日期是否已存在
	 * 
	 * @param data
	 * @param columns
	 * @param existDate
	 * @return
	 */
	public static boolean validateDateRepeat(Map<Integer, Object> data, int columns, List<String> existDate) {
		if (existDate.contains(String.valueOf(data.get(columns - 3)))) {
			return false;
		}
		return true;
	}

	/**
	 * 验证值班人
	 * 
	 * @param dutyer
	 * @param users
	 * @return
	 */
	public static String validateUserArr(String[] dutyer, List<String> users) {
		List<String> noExit = new ArrayList<String>();/* 不存在的值班人 */
		for (int i = 0, size = dutyer.length; i < size; i++) {
			if (users.contains(dutyer[i])) {/* 验证值班人存在 */
				continue;
			} else if (!noExit.contains(dutyer[i])){
				noExit.add(dutyer[i]);
			}
		}
		if (!noExit.isEmpty()) {
			return "值班人：[" + mutiToString(noExit) + "]值班组中不存在,请添加相应人员到值班组;";
		}
		return null;
	}
	
	/**
	 * 验证带班领导是否存在
	 * @param dutyer
	 * @param users
	 * @return
	 */
	public static String validateLeader (String[] dutyer, List<String> users) {
		if (null == dutyer || dutyer.length == 0) {
			return null;
		}
		String result = validateUserArr(dutyer, users);
		if (!StringUtils.isEmpty(result)) {
			result = "带班领导：[" + mutiToString(Arrays.asList(dutyer)) + "]值班组中不存在,请添加相应人员到值班组;";
		}
		return result;
	}

	/**
	 * 验证值班人是否重复
	 * 
	 * @param dutyer
	 * @param users
	 * @return
	 */
	public static String validateUserRq(String[] dutyer, List<String> users, boolean required) {
		if (null == dutyer && required) {
			return "班次没有设置值班人;";
		} else if (null == dutyer) {
			return null;
		}
		List<String> exit = new ArrayList<String>();/* 不存在的值班人 */
		List<String> repeat = new ArrayList<String>();/* 不存在的值班人 */
		for (int i = 0, size = dutyer.length; i < size; i++) {
			if (users.contains(dutyer[i])) {/* 验证值班人存在 */
				if (exit.contains(dutyer[i]) && !repeat.contains(dutyer[i])) {
					repeat.add(dutyer[i]);
				}
				exit.add(dutyer[i]);
				continue;
			}
		}
		if (!repeat.isEmpty()) {
			return "值班人：[" + mutiToString(repeat) + "]重复存在;";
		}
		return null;
	}
	
	/**
	 * 验证备勤等级
	 * @param level
	 * @return
	 */
	private static String validateLevel(String level) {
		if(level == null || level.length() == 0) {
			return "班次没有设置备勤等级;";
		}
		Map<Integer,String> map = DictionaryLoader.getConstantItems("3687");
		List<Integer> list = new ArrayList<Integer>(map.keySet());
		try {
			if(list.contains(Integer.parseInt(level))) {
				return null;
			} else {
				return "班次没有设置正确的备勤等级,可选的为"+map.keySet()+";";
			}
		} catch(Exception e) {
			return "班次没有设置正确的备勤等级,可选的为"+map.keySet()+";";
		}
	}

	/**
	 * 数值转换字符串
	 * 
	 * @param arr
	 * @return
	 */
	public static String mutiToString(List<String> list) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0, size = list.size(); i < size; i++) {
			sb.append(list.get(i));
			if (i != size - 1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	public static String[] concatArr(Map<Integer, Object> data, int columns) {
		String[] start = new String[0];
		for (int i = 1; i < columns - 3; i++) {
			start = concatAll(start, (String[]) data.get(i));
		}
		return concatAll(start, (String[]) data.get(columns - 2));
	}

	public static String[] concatAll(String[] first, String[]... after) {
		int totalLength = first.length;
		for (String[] array : after) {
			if (array == null) {
				continue;
			}
			totalLength += array.length;
		}
		String[] result = Arrays.copyOf(first, totalLength);
		int offset = first.length;
		for (String[] array : after) {
			if (array == null) {
				continue;
			}
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}
		return result;
	}
}
