package com.fable.insightview.platform.dataobject.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.common.util.Constants;
import com.fable.insightview.platform.common.util.StringUtil;
import com.fable.insightview.platform.dataobject.entity.DataObjectBean;
import com.fable.insightview.platform.dataobject.entity.DataSecurityItemBean;
import com.fable.insightview.platform.dataobject.mapper.DataObjectBeanMapper;
import com.fable.insightview.platform.dataobject.mapper.DataSecurityItemBeanMapper;
import com.fable.insightview.platform.dataobject.service.DataObjectQueryService;
import com.fable.insightview.platform.util.DynamicSqlManager;
import com.fable.insightview.platform.util.OperatorEnum;
import com.fable.insightview.platform.util.UserSession;
import com.fable.insightview.platform.util.exception.BusinessException;

/**
 * 
 * @author zhouwei
 * 
 */
@Service
public class DataObjectQueryServiceImpl implements DataObjectQueryService {

	@Autowired
	DataObjectBeanMapper dataObjectBeanMapper;

	@Autowired
	DataSecurityItemBeanMapper dataSecurityItemBeanMapper;

	@Autowired
	SqlSessionFactory sqlSessionFactory;

	@Override
	public <T> List<T> queryForListByDataObjectId(String dataObjectId,
			Class<T> t, HashMap<String, String> params, boolean isRight)
			throws BusinessException {

		if (StringUtil.isEmpty(dataObjectId)) {
			throw new BusinessException("00300001");
		}

		DataObjectBean dob = dataObjectBeanMapper
				.selectByPrimaryKey(dataObjectId);

		if (dob == null) {
			throw new BusinessException("00300012",
					new String[] { dataObjectId });
		}

		return queryForList(dob, t, params, isRight);

	}

	@Override
	public <T> List<T> queryForListByDataObjectId(String dataObjectId,
			Class<T> t, HashMap<String, String> params)
			throws BusinessException {
		return queryForListByDataObjectId(dataObjectId, t, params, true);
	}

	@Override
	public <T> List<T> queryForListByDataObjectName(String dataObjectName,
			Class<T> t, HashMap<String, String> params, boolean isRight)
			throws BusinessException {

		if (StringUtil.isEmpty(dataObjectName)) {
			throw new BusinessException("00300001");
		}

		DataObjectBean dob = dataObjectBeanMapper.selectByName(dataObjectName);

		if (dob == null) {
			throw new BusinessException("00300012",
					new String[] { dataObjectName });
		}

		return queryForList(dob, t, params, isRight);
	}

	@Override
	public <T> List<T> queryForListByDataObjectName(String dataObjectName,
			Class<T> t, HashMap<String, String> params)
			throws BusinessException {
		return queryForListByDataObjectName(dataObjectName, t, params, true);
	}

	private <T> List<T> queryForList(DataObjectBean dataObjectBean, Class<T> t,
			HashMap<String, String> params, boolean isRight)
			throws BusinessException {

		String sql = querySqlByDataObjectBean(dataObjectBean, params, isRight);

		List<T> result = queryForListBySql(sql, t);

		return result;

	}

	/**
	 * @param sql
	 * @param t
	 * @return
	 */
	private <T> List<T> queryForListBySql(String sql, Class<T> t) {

		SqlSession sqlSession = sqlSessionFactory.openSession();

		String msId = createMsId(sql, t, sqlSession);

		List<T> result = sqlSession.selectList(msId);

		return result;
	}

	@Override
	public String querySqlByDataObjectId(String dataObjectId,
			HashMap<String, String> params, boolean isRight)
			throws BusinessException {

		if (StringUtil.isEmpty(dataObjectId)) {
			throw new BusinessException("00300001");
		}

		DataObjectBean dob = dataObjectBeanMapper
				.selectByPrimaryKey(dataObjectId);

		if (dob == null) {
			throw new BusinessException("00300012",
					new String[] { dataObjectId });
		}

		return querySqlByDataObjectBean(dob, params, isRight);
	}

	@Override
	public String querySqlByDataObjectId(String dataObjectId,
			HashMap<String, String> params) throws BusinessException {
		return querySqlByDataObjectId(dataObjectId, params, true);
	}

	@Override
	public String querySqlByDataObjectName(String dataObjectName,
			HashMap<String, String> params, boolean isRight)
			throws BusinessException {

		if (StringUtil.isEmpty(dataObjectName)) {
			throw new BusinessException("00300001");
		}

		DataObjectBean dob = dataObjectBeanMapper.selectByName(dataObjectName);

		if (dob == null) {
			throw new BusinessException("00300012",
					new String[] { dataObjectName });
		}

		return querySqlByDataObjectBean(dob, params, isRight);
	}

	@Override
	public String querySqlByDataObjectName(String dataObjectName,
			HashMap<String, String> params) throws BusinessException {
		return querySqlByDataObjectName(dataObjectName, params, true);
	}

	/**
	 * 
	 * @param dataObjectBean
	 * @return
	 * @throws BusinessException
	 */
	private String querySqlByDataObjectBean(DataObjectBean dataObjectBean,
			HashMap<String, String> params, boolean isRight)
			throws BusinessException {

		if (dataObjectBean == null) {
			throw new BusinessException("00300001");
		}

		// basicSql
		String basicSql = dataObjectBean.getFbsSql();

		if (StringUtil.isEmpty(basicSql)) {
			throw new BusinessException("00300001");
		}

		// 权限数据
		String dataSecuritySql = "";
		if (isRight) {
			dataSecuritySql = getDataSecuritySqlFragment(dataObjectBean.getId());
		}

		// 组装sql
		StringBuffer execSql = new StringBuffer(
				DynamicSqlManager.transferToSelectStarSql(basicSql));

		if (StringUtil.isNotEmpty(dataSecuritySql)) {
			if (execSql.toString().toLowerCase().indexOf("where") == -1) {
				execSql.append(" where ").append(dataSecuritySql);
			} else {
				execSql.append(" and ").append(dataSecuritySql);
			}
		}

		// 替换#{var}变量 先替换可变参数，再替换宏变量为妙
		String tmpSql = DynamicSqlManager.replaceVariableParams(params,
				execSql.toString(), false);
		// 替换宏变量
		String resultSql = DynamicSqlManager.replaceMacros(tmpSql, false);

		return resultSql;
	}

	/**
	 * 
	 * 根据dataObjectId查找权限SQL (放开此方法为public,供listview调用。)
	 * 
	 * @param dataObjectId
	 * @return
	 */
	public String getDataSecuritySqlFragment(String dataObjectId) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("dataObjectId", dataObjectId);
		params.put("userId", UserSession.getUserId());

		List<DataSecurityItemBean> dataSecurityItems = dataSecurityItemBeanMapper
				.selectItemsByDataObjectId(params);

		// 为空时admin具有所有权限，非admin用户将无权限
		if (dataSecurityItems == null || dataSecurityItems.isEmpty()) {
			if (UserSession.isAdmin()) {// admin
				return Constants.SQL_FRAGMENT.TRUE_SQL;
			} else {
				return Constants.SQL_FRAGMENT.FALSE_SQL;
			}
		}

		StringBuffer result = new StringBuffer(" ( ");

		String securityTmpId = "";

		int index = 0;
		for (DataSecurityItemBean item : dataSecurityItems) {

			// 权限Id
			String securityId = item.getSecurityId();

			// 第一次进入 增加 "("
			if (StringUtil.isEmpty(securityTmpId)) {
				result.append(" ( ");
				securityTmpId = securityId;
			} else {
				// 两组权限之间以OR分隔
				if (!StringUtil.equals(securityTmpId, securityId)) {
					result.append(" or ").append(" ( ");
					securityTmpId = securityId;
				}
			}

			// 比较值 替换宏变量 替换#{var}变量 此处不进行变量的替换，放到
			// querySqlByDataObjectBean方法中一起替换
			// String value =
			// DynamicSqlManager.replaceMacros(item.getValue(),false);
			String value = item.getValue();

			// 根据操作格式化比较值
			String formatValue = formatValueByOperator(value,
					item.getOperator());

			// 临时SQL
			StringBuffer tmpSb = new StringBuffer();

			tmpSb.append(StringUtil.trimToEmpty(item.getLeftBracket()))
					.append(" ").append(item.getPropertyName()).append(" ")
					.append(OperatorEnum.getValueByName(item.getOperator()))
					.append(" ").append(formatValue).append(" ")
					.append(StringUtil.trimToEmpty(item.getRightBracket()));

			index++;

			// 如果list未结束，并且，下个权限还是同一组，则增加logic符，否则增加右括号
			if (index < dataSecurityItems.size()
					&& StringUtil.equals(securityId,
							dataSecurityItems.get(index).getSecurityId())) {
				tmpSb.append(" ").append(item.getLogicSymbol()).append(" ");
			} else {
				tmpSb.append(" ) ");
			}

			result.append(tmpSb.toString());

		}
		result.append(" ) ");

		return result.toString();
	}

	/**
	 * 根据操作格式化数据
	 * 
	 * @param propertyValue
	 * @param operator
	 * @return
	 */
	private String formatValueByOperator(String propertyValue, String operator) {

		if (propertyValue.toLowerCase().indexOf(Constants.SQL_FRAGMENT.SELECT) > -1) {

			return propertyValue;
		}
		if (StringUtil.equals(operator, Constants.OPERATOR.LEFTLIKE)) {

			return "'" + propertyValue + "%'";

		} else if (StringUtil.equals(operator, Constants.OPERATOR.RIGHTLIKE)) {

			return "'%" + propertyValue + "'";

		} else if (StringUtil.equals(operator, Constants.OPERATOR.ALLLIKE)) {

			return "'%" + propertyValue + "%'";

		} else {

			return "'" + propertyValue + "'";
		}

	}

	/**
	 * 查询
	 * 
	 * @param sql
	 * @param resultType
	 * @param sqlSession
	 * @return
	 */
	private String createMsId(String sql, Class<?> resultType,
			SqlSession sqlSession) {
		String msId = newMsId(resultType + sql, SqlCommandType.SELECT);
		if (hasMappedStatement(msId, sqlSession.getConfiguration())) {
			return msId;
		}
		StaticSqlSource sqlSource = new StaticSqlSource(
				sqlSession.getConfiguration(), sql);
		newSelectMappedStatement(msId, sqlSource, resultType,
				sqlSession.getConfiguration());
		return msId;
	}

	/**
	 * 创建MSID
	 * 
	 * @param sql
	 *            执行的sql
	 * @param sql
	 *            执行的sqlCommandType
	 * @return
	 */
	private String newMsId(String sql, SqlCommandType sqlCommandType) {
		StringBuilder msIdBuilder = new StringBuilder(sqlCommandType.toString());
		msIdBuilder.append(".").append(sql.hashCode());
		return msIdBuilder.toString();
	}

	/**
	 * 是否已经存在该ID
	 * 
	 * @param msId
	 * @return
	 */
	private boolean hasMappedStatement(String msId, Configuration configuration) {
		return configuration.hasStatement(msId, false);
	}

	/**
	 * 创建一个查询的MS
	 * 
	 * @param msId
	 * @param sqlSource
	 *            执行的sqlSource
	 * @param configuration
	 * @param resultType
	 *            返回的结果类型
	 */
	private void newSelectMappedStatement(String msId, SqlSource sqlSource,
			final Class<?> resultType, final Configuration configuration) {
		MappedStatement ms = new MappedStatement.Builder(configuration, msId,
				sqlSource, SqlCommandType.SELECT).resultMaps(
				new ArrayList<ResultMap>() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 7597297507959059639L;

					{
						add(new ResultMap.Builder(configuration,
								"defaultResultMap", resultType,
								new ArrayList<ResultMapping>(0)).build());
					}
				}).build();
		// 缓存
		configuration.addMappedStatement(ms);
	}

}
