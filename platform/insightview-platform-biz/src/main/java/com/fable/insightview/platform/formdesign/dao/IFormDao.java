package com.fable.insightview.platform.formdesign.dao;

import java.util.List;
import java.util.Map;

import com.fable.insightview.platform.formdesign.entity.FdForm;
import com.fable.insightview.platform.itsm.core.dao.GenericDao;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;

public interface IFormDao extends GenericDao<FdForm> {

	FdForm getByFormId(String formId);

	int saveFormInstanceInfo(String id,String tableName, Map<String, String> attrsMap);

	Map<String, String> queryFormInstanceInfo(String tableName, int id);

	void updateFormInstanceInfo(int id, String tableName,
			Map<String, String> formData);
	
	/**
	 * 获取属性的初始化值
	 * @param initSQL 初始化SQL语句
	 * @return
	 */
	List<Map<String, String>> valueInit(String initSQL);

	Map<String, Object> findLinkFieldsForUser(String linkSql, String userId);

	List<Map<String, Object>> queryLinkList(String sql, Map<String, String> queryInfo, FlexiGridPageInfo flexiGridPageInfo);

	int queryCountForLink(String sql, Map<String, String> queryInfo);

}
