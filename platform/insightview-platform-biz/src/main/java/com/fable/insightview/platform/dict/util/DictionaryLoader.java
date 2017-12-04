package com.fable.insightview.platform.dict.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.fable.insightview.platform.core.util.BeanLoader;
import com.fable.insightview.platform.service.DictService;

public class DictionaryLoader {
	public static void main(String[] args) throws Exception {
		String s = "1422633600000";
		long l = 1422633600000L;
		long ls = Long.valueOf(s).longValue();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		System.out.println(sdf.format(new Date(ls)));
	}

	public static Map<Integer, String> getConstantItems(String typeNameOrId) {
		DictService service = (DictService) BeanLoader.getBean("DictService");
		Map<Integer, String> map = service.getConstantItems(typeNameOrId);
		return map;
	}

}
