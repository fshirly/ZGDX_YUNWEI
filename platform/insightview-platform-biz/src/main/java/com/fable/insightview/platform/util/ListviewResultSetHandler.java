/**
 * 
 */
package com.fable.insightview.platform.util;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.sql.CLOB;

import com.fable.insightview.platform.common.util.StringUtil;
import com.fable.insightview.platform.util.exception.BusinessException;


/**
 * @author zhouwei
 * 
 */
public class ListviewResultSetHandler {
	/**
	 * 
	 * 格式化结果集
	 * 
	 * @param resultDataList
	 * @return
	 */
	public static List<HashMap<String, Object>> formatResultData(
			List<HashMap<String, Object>> resultDataList) {

		List<HashMap<String, Object>> formatDataList = new ArrayList<HashMap<String, Object>>();
		
		SimpleDateFormat df = null;

		if (resultDataList != null && !resultDataList.isEmpty()) {
			for (HashMap<String, Object> map : resultDataList) {

				if (map != null) {
					HashMap<String, Object> camelMap = new HashMap<String, Object>();
					for (Map.Entry<String, Object> entry : map.entrySet()) {
						String key = StringUtil
								.underscore2camel(entry.getKey());
						Object value = entry.getValue() == null ? "" : entry
								.getValue();
						//System.out.println(value.getClass());
						if (value instanceof java.sql.Timestamp) {
							Timestamp tt = (java.sql.Timestamp) value;
							if(df == null){
								df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							}
							String temp = df.format(tt);
							camelMap.put(key, temp);
						}else if (value instanceof oracle.sql.TIMESTAMP) {
							try {
								Timestamp tt = ((oracle.sql.TIMESTAMP) value)
										.timestampValue();
								if(df == null){
									df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								}
								String temp = df.format(tt);
								camelMap.put(key, temp);
							} catch (SQLException e) {
								throw new BusinessException("00300011", e,
										new String[] { "" });
							}

						} else if (value instanceof java.sql.Clob) {//clob速度慢，一览页面最好不要查询这个字段
							CLOB clob = (CLOB) value;
							try {
								String temp = clob.getSubString((long) 1,
										(int) clob.length());
								camelMap.put(key, temp);
							} catch (SQLException e) {
								throw new BusinessException("00300011", e,
										new String[] { "" });
							}
						} else {
							camelMap.put(key, value);
						}

					}
					formatDataList.add(camelMap);
				}
			}
		}
		return formatDataList;
	}
	
}
