package com.fable.insightview.platform.formdesign.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.fable.insightview.platform.formdesign.dao.IFormDao;

/**
 * SQL获取集合抽象类
 * @author : Zheng Zhen
 */
public abstract class ViewTextBySQL extends AbstractViewText{

	@Autowired
	private IFormDao formDao;
	
	@Override
	List<Map<String, String>> initValue(Map<String, String> attribute) {
		// TODO Auto-generated method stub
		String initSQL = attribute.get("initSQL");
		return formDao.valueInit(initSQL);
	}

}
