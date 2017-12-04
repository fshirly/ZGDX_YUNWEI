package com.fable.insightview.platform.listview.service;

import com.fable.insightview.platform.common.entity.DataBean;
import com.fable.insightview.platform.common.entity.TreeDictionaryBean;
import com.fable.insightview.platform.common.entity.TreeDictionaryQueryBean;

/**
 * 数据字典查询服务层
 * 
 * @author zhouwei
 * 
 */
public interface DictionaryQueryService {

	/**
	 * 根据类型查询树形字典表
	 * @param type
	 * @return
	 */
	DataBean<TreeDictionaryBean> selectDictionarysByType(String type);

	/**
	 * 根据多种条件查询树形字典表
	 * @param treeDictionaryQueryBean
	 * @return
	 */
	DataBean<TreeDictionaryBean> selectDictionarys(
			TreeDictionaryQueryBean treeDictionaryQueryBean);
}
