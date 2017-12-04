package com.fable.insightview.platform.formdesign.service;

import java.util.List;
import java.util.Map;

import com.fable.insightview.platform.formdesign.entity.FdForm;
import com.fable.insightview.platform.formdesign.vo.ProcessFormVO;
import com.fable.insightview.platform.itsm.core.service.GenericService;

/**
 * 表单设计业务逻辑API
 * 定义表单的核心接口，可供外部系统调用
 * 表单的增、删、改、查
 * @author Administrator
 *
 */
public interface IFormService extends GenericService<FdForm> {

	FdForm getByFormId(String formId);
	
	void addTable(String tableName, String id);
	
	void addColumn(String tableName, String columnName, String javaType, Integer length);
	
	void removeColumn(String tableName, String columnName, boolean isLogic);
	
	Map<String, String> getFormAttrsToMap(int id,int formId);
	
	List<Map<String, String>> getFormAttributes(int formId);
	
	FdForm insertForm(FdForm form, ProcessFormVO processFormVO);
	
	int saveFormInstanceInfo(String id,String tableName, Map<String, String> attrsMap);
	
	Map<String, String> queryFormInstanceInfo(String tableName, int id);

	void updateFormInstanceInfo(int id, String tableName, Map<String, String> formData);
	
	/**
	 * 获取属性的初始化值
	 * @param initSQL 初始化SQL语句
	 * @return
	 */
	List<Map<String, String>> valueInit(String initSQL);
}
