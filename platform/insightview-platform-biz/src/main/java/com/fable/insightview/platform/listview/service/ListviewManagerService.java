package com.fable.insightview.platform.listview.service;

import com.fable.insightview.platform.common.entity.DataBean;
import com.fable.insightview.platform.common.entity.TableDataBean;
import com.fable.insightview.platform.common.entity.TableQueryConditionBean;
import com.fable.insightview.platform.dataobject.entity.DataObjectBean;
import com.fable.insightview.platform.listview.entity.ListviewBean;
import com.fable.insightview.platform.listview.entity.ListviewDataBean;
import com.fable.insightview.platform.listview.entity.ListviewFieldLabelBean;
import com.fable.insightview.platform.util.exception.BusinessException;

public interface ListviewManagerService {

	/**
	 * 增加ListviewDataBean
	 * 
	 * @param listviewDataBean
	 * @return
	 * @throws BusinessException
	 */
	void insertListviewDataBean(ListviewDataBean listviewDataBean)
			throws BusinessException;

	/**
	 * 修改ListviewDataBean
	 * 
	 * @param listviewDataBean
	 * @throws BusinessException
	 */
	void updateListviewDataBean(ListviewDataBean listviewDataBean)
			throws BusinessException;

	/**
	 * 删除ListviewDataBean
	 * 
	 * @param listviewDataBean
	 * @throws BusinessException
	 */
	void deleteListviewDataBeans(String[] ids) throws BusinessException;

	/**
	 * 查询ListviewDataBean
	 * 
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	ListviewDataBean selectListviewDataBean(String id) throws BusinessException;

	// /**
	// * 根据sql查询字段标签
	// *
	// * @param sql
	// * @return
	// * @throws BusinessException
	// */
	// DataBean<ListviewFieldLabelBean> selectFieldLabelsByDynamicSql(String
	// sql)
	// throws BusinessException;

	// /**
	// * 根据targetType获取用户或者角色信息
	// *
	// * @param targetType
	// * 1:用户 / 2：角色
	// * @return
	// * @throws BusinessException
	// */
	// SelectDataBean selectTargets(String targetType) throws BusinessException;
	
	/**
	 * 批量新增ListviewDataBean
	 * 
	 * @param dataBean
	 * @throws BusinessException
	 */
	void insertListviewDataBeans(DataBean<ListviewDataBean> dataBean)
			throws BusinessException;

	/**
	 * 批量查询ListviewDataBean
	 * 
	 * @param ids
	 * @return
	 */
	DataBean<ListviewDataBean> selectListviewDataBeans(String[] ids);

//	/**
//	 * 批量新增ListviewDataBean
//	 * 
//	 * @param dataBean
//	 * @throws BusinessException
//	 */
//	void insertListviewDataBeans(DataBean<ListviewDataObjectBean> dataBean)
//			throws BusinessException;
//
//	/**
//	 * 批量查询ListviewDataBean
//	 * 
//	 * @param ids
//	 * @return
//	 */
//	DataBean<ListviewDataObjectBean> selectListviewDataObjectBeans(String[] ids);

	/**
	 * 分页查询ListviewBean
	 * 
	 * @param tableCondition
	 * @return
	 */
	TableDataBean<ListviewBean> selectListviewBeansForPage(
			TableQueryConditionBean tableCondition) throws BusinessException;

	/**
	 * 
	 * 通过数据对象Id获取字段列表
	 * 
	 * @param dataObjectId
	 * @return
	 */
	DataBean<ListviewFieldLabelBean> selectFieldLabelsByDataObjectId(
			String dataObjectId, boolean camel) throws BusinessException;

	/**
	 * 查询所有数据对象
	 * 
	 * @return
	 */
	DataBean<DataObjectBean> selectDataObjects();

}
