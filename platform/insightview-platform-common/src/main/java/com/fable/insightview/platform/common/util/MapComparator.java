package com.fable.insightview.platform.common.util;

import java.util.Comparator;
import java.util.Map;

/**
 * Map比较的工具类
 * 
 * @author Maowei
 *
 */
public class MapComparator implements Comparator<Map<String, String>>{

	/**
	 * 将属性按照seq进行排序
	 */
	@Override
	public int compare(Map<String, String> arg0, Map<String, String> arg1) {
		int seq1 = Integer.valueOf(arg0.get("seq"));
		int seq2 = Integer.valueOf(arg1.get("seq"));
		if (seq2 > seq1) {
			return -1;
		} else if (seq2 < seq1) {
			return 1;
		}
		return 0;
	}

}
