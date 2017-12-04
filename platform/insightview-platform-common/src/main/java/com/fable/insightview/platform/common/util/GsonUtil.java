package com.fable.insightview.platform.common.util;

import java.lang.reflect.Type;

import com.fable.insightview.platform.core.exception.BusinessException;
import com.google.gson.Gson;

/**
 * 
 * @author zhouwei
 * 
 */
public class GsonUtil {

	/**
	 * formJson
	 * 
	 * @param json
	 * @param typeOfT
	 * @return
	 */
	public static <T> T fromJson(String json, Type typeOfT) {
		T t = null;
		try {
			t = new Gson().fromJson(json, typeOfT);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
		return t;
	}

	/**
	 * to json
	 * 
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
		return new Gson().toJson(obj);
	}

	/**
	 * to json
	 * 
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj, Gson gson) {
		return gson.toJson(obj);
	}

}
