package com.fable.insightview.platform.dataobject.service;

import java.util.HashMap;
import java.util.List;

import com.fable.insightview.platform.core.exception.BusinessException;

/**
 * 
 * @author zhouwei
 * 
 */
public interface DataObjectQueryService {

	/**
	 * 
	 * 根据dataObjectId查询数据列表
	 * 
	 * @param dataObjectId
	 * @param isRight
	 *            是否开启权限
	 * @param t
	 * @return List<T>
	 * @throws BusinessException
	 */
	public <T> List<T> queryForListByDataObjectId(String dataObjectId,
			Class<T> t, HashMap<String, String> params, boolean isRight)
			throws BusinessException;

	/**
	 * 
	 * 根据dataObjectId查询数据列表(开启权限)
	 * 
	 * @param dataObjectId
	 * @param t
	 * @return List<T>
	 * @throws BusinessException
	 */
	public <T> List<T> queryForListByDataObjectId(String dataObjectId,
			Class<T> t, HashMap<String, String> params)
			throws BusinessException;

	/**
	 * 
	 * 根据dataObjectId查询数据列表
	 * 
	 * @param dataObjectName
	 * @param t
	 * @param isRight
	 *            是否开启权限
	 * @return List<T>
	 * @throws BusinessException
	 */
	public <T> List<T> queryForListByDataObjectName(String dataObjectName,
			Class<T> t, HashMap<String, String> params, boolean isRight)
			throws BusinessException;

	/**
	 * 
	 * 根据dataObjectId查询数据列表(开启权限)
	 * 
	 * @param dataObjectName
	 * @param t
	 * @return List<T>
	 * @throws BusinessException
	 */
	public <T> List<T> queryForListByDataObjectName(String dataObjectName,
			Class<T> t, HashMap<String, String> params)
			throws BusinessException;

	/**
	 * 
	 * 根据dataObjectId查询SQL
	 * 
	 * @param dataObjectName
	 * @param isRight
	 *            是否开启权限
	 * @return
	 * @throws BusinessException
	 */
	String querySqlByDataObjectId(String dataObjectId,
			HashMap<String, String> params, boolean isRight)
			throws BusinessException;

	/**
	 * 
	 * 根据dataObjectId查询SQL 默认开启权限
	 * 
	 * @param dataObjectName
	 * @return
	 * @throws BusinessException
	 */
	String querySqlByDataObjectId(String dataObjectId,
			HashMap<String, String> params) throws BusinessException;

	/**
	 * 
	 * 根据dataObjectName查询SQL
	 * 
	 * @param dataObjectName
	 * @param isRight
	 *            是否开启权限
	 * @return
	 * @throws BusinessException
	 */
	String querySqlByDataObjectName(String dataObjectName,
			HashMap<String, String> params, boolean isRight)
			throws BusinessException;

	/**
	 * 
	 * 根据dataObjectName查询SQL 默认开启权限
	 * 
	 * @param dataObjectName
	 * @return
	 * @throws BusinessException
	 */
	String querySqlByDataObjectName(String dataObjectName,
			HashMap<String, String> params) throws BusinessException;

//	/**
//	 * 
//	 * 根据sql查询list
//	 * 
//	 * @param sql
//	 * @param t
//	 * @return
//	 */
//	public <T> List<T> queryForListBySql(String sql, Class<T> t);

	/**
	 * 
	 * 根据dataObjectId查找权限SQL (放开此方法为public,供listview调用。)
	 * 
	 * @param dataObjectId
	 * @return
	 */
	public String getDataSecuritySqlFragment(String dataObjectId);

}
