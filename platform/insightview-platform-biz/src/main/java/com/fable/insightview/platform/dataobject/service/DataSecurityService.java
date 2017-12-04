package com.fable.insightview.platform.dataobject.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.fable.insightview.platform.common.entity.DataBean;
import com.fable.insightview.platform.dataobject.entity.DataObjectBean;
import com.fable.insightview.platform.dataobject.entity.DataObjectFieldLabelBean;
import com.fable.insightview.platform.dataobject.entity.DataSecurityBean;
import com.fable.insightview.platform.dataobject.entity.RoleWithDataSecurityBean;

/**
 * 数据权限服务类
 * 
 * @author nimj
 * 
 */
public interface DataSecurityService {

	/**
	 * 批量DataObjectBean对象（除DataObjectBean外，还需要删除关联的DataObjectFieldLabelBean、
	 * DataSecurityBean和DataSecurityItemBean）
	 */
	void deleteDataObjects(String[] ids, String[] types);

	/**
	 * 新增数据对象
	 */
	String createDataObject(DataObjectBean dataObject);

	/**
	 * 更新数据对象
	 */
	void updateDataObject(DataObjectBean dataObject);

	/**
	 * 获取字段列表
	 */
	DataBean<DataObjectFieldLabelBean> queryFieldLabelsByDynamicSql(String sql);

	/**
	 * 根据数据对象ID获取DataObjectFieldLabelBean对象集
	 */
	DataBean<DataObjectFieldLabelBean> queryFieldLabelsByDataObjectId(
			String dataObjectId);

	/**
	 * 查询指定数据对象下的带数据权限信息的角色信息
	 */
	List<RoleWithDataSecurityBean> queryRoleWithDataSecuritys(
			String dataObjectId);

	/**
	 * 更新数据对象的权限
	 */
	void updateDataSecuritys(String dataObjectId,
			List<DataSecurityBean> dataSecuritys);

	/**
	 * 获取权限条件各下拉框选项值
	 */
	JSONObject queryComboboxValues();

    /**
     * 使用存储过程更新所有数据对象
     */
    void updateDataObjectsWithProcdure();

}
