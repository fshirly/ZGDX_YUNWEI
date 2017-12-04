package com.fable.insightview.platform.formdesign.service.impl;

import java.util.List;
import java.util.Map;

import com.fable.insightview.platform.formdesign.service.IViewText;

/**
 * 获取查看文本抽象类
 * @author : Zheng Zhen
 */
public abstract class AbstractViewText implements IViewText{
	
	/**
	 * 初始化value，由子类实现，根据不同情况得到list集合
	 * @param attribute
	 * @return
	 */
	abstract List<Map<String, String>> initValue( Map<String, String> attribute);
	
	/**
	 * 由子类实现，根据不同情况设置key值
	 * @return
	 */
	abstract String textKey();
	
	/**
	 * 子类公共逻辑：获取list集合后，遍历匹配value，通过key值获取value的实际值
	 */
	@Override
	public String getText(String value, Map<String, String> attribute) {
		// TODO Auto-generated method stub
		String viewText = "";
		List<Map<String, String>> list = initValue(attribute);
		for (int i = 0; i < list.size(); i++) {
			Map<String, String> map = list.get(i);
			if(value.equals(map.get("id"))){
				viewText = (String) map.get(textKey());
				return viewText;
			}
		}
		return viewText;
	}
}
