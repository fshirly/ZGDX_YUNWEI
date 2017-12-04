package com.fable.insightview.platform.listview.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.common.entity.ColumnBean;
import com.fable.insightview.platform.common.entity.DataBean;
import com.fable.insightview.platform.common.entity.ExcelExportConditionBean;
import com.fable.insightview.platform.common.entity.TableDataBean;
import com.fable.insightview.platform.common.entity.TableQueryConditionBean;
import com.fable.insightview.platform.common.entity.TreeDictionaryBean;
import com.fable.insightview.platform.common.entity.TreeDictionaryQueryBean;
import com.fable.insightview.platform.common.sqlparser.Page;
import com.fable.insightview.platform.common.sqlparser.Parser;
import com.fable.insightview.platform.common.sqlparser.ParserFactory;
import com.fable.insightview.platform.common.util.Cast;
import com.fable.insightview.platform.common.util.Constants;
import com.fable.insightview.platform.common.util.GsonUtil;
import com.fable.insightview.platform.common.util.StringUtil;
import com.fable.insightview.platform.dataobject.entity.DataObjectBean;
import com.fable.insightview.platform.dataobject.mapper.DataObjectBeanMapper;
import com.fable.insightview.platform.dataobject.service.DataObjectQueryService;
import com.fable.insightview.platform.listview.entity.ListviewBean;
import com.fable.insightview.platform.listview.entity.ListviewConditionBean;
import com.fable.insightview.platform.listview.entity.ListviewConditionDtoBean;
import com.fable.insightview.platform.listview.entity.ListviewConfigBean;
import com.fable.insightview.platform.listview.entity.ListviewDataBean;
import com.fable.insightview.platform.listview.entity.ListviewFieldLabelBean;
import com.fable.insightview.platform.listview.entity.ListviewPreviewQueryConditionBean;
import com.fable.insightview.platform.listview.entity.ListviewRequestParamsBean;
import com.fable.insightview.platform.listview.mapper.ListviewBeanMapper;
import com.fable.insightview.platform.listview.mapper.ListviewConditionBeanMapper;
import com.fable.insightview.platform.listview.mapper.ListviewFieldLabelBeanMapper;
import com.fable.insightview.platform.listview.service.DictionaryQueryService;
import com.fable.insightview.platform.listview.service.FileManagerService;
import com.fable.insightview.platform.listview.service.ListviewQueryService;
import com.fable.insightview.platform.util.DynamicSqlManager;
import com.fable.insightview.platform.util.ExcelUtil;
import com.fable.insightview.platform.util.ListviewResultSetHandler;
import com.fable.insightview.platform.util.OperatorEnum;
import com.fable.insightview.platform.util.SysOrgTreeEnum;
import com.fable.insightview.platform.util.exception.BusinessException;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @author zhouwei
 * 
 */
@Service
public class ListviewQueryServiceImpl implements ListviewQueryService {

	@Autowired
	ListviewBeanMapper listviewBeanMapper;

	@Autowired
	ListviewConditionBeanMapper listviewConditionBeanMapper;

	@Autowired
	ListviewFieldLabelBeanMapper listviewFieldLabelBeanMapper;

	@Autowired
	DataObjectQueryService dataObjectQueryService;

	@Autowired
	DataObjectBeanMapper dataObjectBeanMapper;

	@Autowired(required=false)
	DictionaryQueryService dictionaryQueryService;

	@Autowired(required=false)
	FileManagerService fileManagerService;

	@Autowired
	ParserFactory parserFactory;

	@Override
	public ListviewConfigBean selectConfig(
			ListviewRequestParamsBean listviewRequestParamsBean)
			throws BusinessException {

		// 参数
		if (listviewRequestParamsBean == null) {
			// 参数不能为空
			throw new BusinessException("00300001");
		}

		String listviewName = listviewRequestParamsBean.getListviewName();
		if (StringUtil.isEmpty(listviewName)) {
			// listviewName不能为空
			throw new BusinessException("00300001");
		}

		ListviewConfigBean config = new ListviewConfigBean();

		// 基本信息
		ListviewBean basic = listviewBeanMapper.selectByName(listviewName);

		if (basic == null) {
			// 未找到listviewBean
			throw new BusinessException("00300010",
					new String[] { listviewName });
		}

		try {
			BeanUtils.copyProperties(config, basic);
		} catch (Exception e) {
		}

		String listviewId = basic.getId();

		// cols
		config.setCols(getColsByListviewId(listviewId));

		// condition
		config.setCondition(getConditionByListviewId(listviewRequestParamsBean,
				listviewId, listviewName));

		return config;
	}

	/**
	 * @param listviewRequestParamsBean
	 * @param listviewId
	 * @return list
	 */
	private List<ListviewConditionDtoBean> getConditionByListviewId(
			ListviewRequestParamsBean listviewRequestParamsBean,
			String listviewId, String listviewName) {
		// condition
		List<ListviewConditionDtoBean> result = new ArrayList<ListviewConditionDtoBean>();

		// 页面传入条件
		Map<String, String> initParams = listviewRequestParamsBean
				.getInitParams();
		if (initParams == null) {
			initParams = new HashMap<String, String>();
		} else {
			for (Map.Entry<String, String> entry : initParams.entrySet()) {
				ListviewConditionDtoBean dto = new ListviewConditionDtoBean();
				dto.setControlType(Constants.CONTROL_TYPE.HIDDEN);
				dto.setPropertyName(StringUtil.trimToEmpty(entry.getKey()));
				dto.setValue(StringUtil.trimToEmpty(entry.getValue()));
				dto.setDefaultValue(StringUtil.trimToEmpty(entry.getValue()));
				result.add(dto);
			}
		}

		List<ListviewConditionBean> conditions = listviewConditionBeanMapper
				.selectConditionByListviewId(listviewId);

		List<ListviewConditionDtoBean> tempList = this
				.getConditionWithOutInitParams(conditions, listviewName,
						initParams);

		result.addAll(tempList);

		return result;
	}

	/**
	 * @param conditions
	 * @param listviewName
	 * @param initParams
	 */
	private List<ListviewConditionDtoBean> getConditionWithOutInitParams(
			List<ListviewConditionBean> conditions, String listviewName,
			Map<String, String> initParams) {

		List<ListviewConditionDtoBean> result = new ArrayList<ListviewConditionDtoBean>();

		// Gson gson = new Gson();

		// 放置重复的propertyName
		Map<String, Integer> usedProperty = new HashMap<String, Integer>();

		// 放置select 级联的父子关系 , key为childId
		Map<String, String> childIdParentToMap = new HashMap<String, String>();
		Map<String, String> parentTypeMap = new HashMap<String, String>();
		// 放置select 级联的数据字典type类别

		if (conditions != null && !conditions.isEmpty()) {
			for (ListviewConditionBean condition : conditions) {

				// 需要返回的dto对象
				ListviewConditionDtoBean dto = new ListviewConditionDtoBean();
				String propertyName = StringUtil.trimToEmpty(condition
						.getPropertyName());

				// 1.重复条件,如日期、时间 createTime>a && createTime<b
				if (usedProperty.containsKey(propertyName)) {
					int repeatTimes = usedProperty.get(propertyName);
					usedProperty.put(propertyName, repeatTimes + 1);
					condition.setPropertyName(propertyName + repeatTimes);
				} else {
					usedProperty.put(propertyName, 1);
				}

				// 2.判断是否与initParams重复存在
				// 重复存在的使用hidden类型
				if (initParams.containsKey(propertyName)) {
					continue;
				}

				// 组装dto
				try {
					BeanUtils.copyProperties(dto, condition);
				} catch (Exception e) {
				}

				// 计算values
				String controlType = condition.getControlType();
				String sourceType = condition.getSourceType() == null ? ""
						: condition.getSourceType().setScale(0).toString();
				String listSource = StringUtil.trimToEmpty(condition
						.getListSource());

				if (StringUtil.equals(controlType, Constants.CONTROL_TYPE.TEXT)
						|| StringUtil.equals(controlType,
								Constants.CONTROL_TYPE.DATETIME)
						|| StringUtil.equals(controlType,
								Constants.CONTROL_TYPE.DATE)) {

					String value = StringUtil.trimToEmpty(condition
							.getDefaultValue());
					dto.setValue(value);
				} else if (StringUtil.equals(controlType,
						Constants.CONTROL_TYPE.SELECT)
						|| StringUtil.equals(controlType,
								Constants.CONTROL_TYPE.POPUP)) {

					if (StringUtil.equals(controlType,
							Constants.CONTROL_TYPE.POPUP)) {
						dto.setMultiple("true");
					}

					if (StringUtil.equals(sourceType,
							Constants.SOURCE_TYPE.DICTIONARY)) {// 数据字典
						if (StringUtil.isNotEmpty(listSource)) {
							if (StringUtil.containsIgnoreCase(listSource,
									"childId")) {// 包含childId
								// Map<String, String> map = gson.fromJson(
								// listSource,
								// new TypeToken<Map<String, String>>() {
								// }.getType());
								Map<String, String> map = GsonUtil.fromJson(
										listSource,
										new TypeToken<Map<String, String>>() {
										}.getType());
								String childId = "";
								String type = "";
								String multiple = "";
								if (map != null) {
									for (Map.Entry<String, String> entry : map
											.entrySet()) {
										String key = entry.getKey();
										if (StringUtil.equalsIgnoreCase(key,
												"childId")) {
											childId = entry.getValue();
										} else if (StringUtil.equalsIgnoreCase(
												key, "type")) {
											type = entry.getValue();
										} else if (StringUtil.equalsIgnoreCase(
												key, "multiple")) {
											multiple = entry.getValue();
										}
									}

								}
								dto.setChildId(childId);
								// 如果type为空，type继承父节点
								childIdParentToMap.put(childId, propertyName);
								if (StringUtil.isEmpty(type)) {
									type = parentTypeMap.get(childIdParentToMap
											.get(propertyName));
								} else {
									TreeDictionaryQueryBean params = new TreeDictionaryQueryBean();
									params.setPid("-1");
									params.setType(type);
									params.setListviewName(listviewName);
									DataBean<TreeDictionaryBean> dataBean = dictionaryQueryService
											.selectDictionarys(params);
									dto.setValues(dataBean.getData());
								}
								parentTypeMap.put(propertyName, type);
								dto.setType(type);

								if (StringUtil.isNotEmpty(multiple)) {
									if (StringUtil.equalsIgnoreCase(multiple,
											"true")) {
										dto.setMultiple("true");
									} else if (StringUtil.equalsIgnoreCase(
											multiple, "false")) {
										dto.setMultiple("false");
									}
								}

							} else if (StringUtil.containsIgnoreCase(
									listSource, "type")) {

								// Map<String, Object> map = gson.fromJson(
								// listSource,
								// new TypeToken<Map<String, Object>>() {
								// }.getType());
								Map<String, Object> map = GsonUtil.fromJson(
										listSource,
										new TypeToken<Map<String, Object>>() {
										}.getType());

								String type = "";
								if (map != null) {
									Map<String, Object> opts = new HashMap<String, Object>();
									for (Map.Entry<String, Object> entry : map
											.entrySet()) {
										String key = entry.getKey();
										String value = Cast.toString(entry
												.getValue());
										if (StringUtil.equalsIgnoreCase(key,
												"type")) {
											type = value;
											dto.setType(value);
										} else if (StringUtil.equalsIgnoreCase(
												key, "multiple")) {
											dto.setMultiple(value);
										} else {
											opts.put(key, entry.getValue());
										}
									}
									dto.setOpts(opts);
								}

								// if (StringUtil.isNotEmpty(multiple)) {
								// if (StringUtil.equalsIgnoreCase(multiple,
								// "true")) {
								// dto.setMultiple("true");
								// } else if (StringUtil.equalsIgnoreCase(
								// multiple, "false")) {
								// dto.setMultiple("false");
								// }
								// }

								DataBean<TreeDictionaryBean> dataBean = dictionaryQueryService
										.selectDictionarysByType(type);
								dto.setValues(dataBean.getData());

							} else {// 不包含childId 直接查询出values 不是json对象，type
								dto.setType(listSource);
								DataBean<TreeDictionaryBean> dataBean = dictionaryQueryService
										.selectDictionarysByType(listSource);
								dto.setValues(dataBean.getData());

							}
						} else {// 数据源为空 ， 表联动
							// nothing to do
						}
					} else if (StringUtil.equals(sourceType,
							Constants.SOURCE_TYPE.JSON)) { // json格式:[{name:'--请选择--',code:''},{name:'未反馈',code:'1'}]
						// 计算values
						// List<TreeDictionaryBean> values = new
						// ArrayList<TreeDictionaryBean>();
						//
						// List<SelectBean> tempList = gson.fromJson(listSource,
						// new TypeToken<List<SelectBean>>() {
						// }.getType());
						//
						// int index = 1;
						// for (SelectBean selectBean : tempList) {
						// TreeDictionaryBean treeDictionaryBean = new
						// TreeDictionaryBean();
						// treeDictionaryBean.setName(selectBean.getText());
						// treeDictionaryBean.setCode(selectBean.getValue());
						// treeDictionaryBean.setSortOrder(BigDecimal
						// .valueOf(index));
						// values.add(treeDictionaryBean);
						// index++;
						// }

						// List<TreeDictionaryBean> values = gson.fromJson(
						// listSource,
						// new TypeToken<List<TreeDictionaryBean>>() {
						// }.getType());

						List<TreeDictionaryBean> values = GsonUtil.fromJson(
								listSource,
								new TypeToken<List<TreeDictionaryBean>>() {
								}.getType());

						dto.setValues(values);

					} else if (StringUtil.equals(sourceType,
							Constants.SOURCE_TYPE.URL)) {

						if (StringUtil.containsIgnoreCase(listSource, "url")) {// 包含url
							// Map<String, Object> map =
							// gson.fromJson(listSource,
							// new TypeToken<Map<String, Object>>() {
							// }.getType());
							Map<String, Object> map = GsonUtil.fromJson(
									listSource,
									new TypeToken<Map<String, Object>>() {
									}.getType());
							// String url = "";
							// String method = "";
							// Map<String,Object> params = new
							// HashMap<String,Object>();
							// String multiple = "";
							if (map != null) {
								Map<String, Object> opts = new HashMap<String, Object>();
								for (Map.Entry<String, Object> entry : map
										.entrySet()) {
									String key = entry.getKey();
									String value = Cast.toString(entry
											.getValue());
									if (StringUtil.equalsIgnoreCase(key, "url")) {
										// url =
										// Cast.toString(entry.getValue());
										dto.setUrl(value);
									} else if (StringUtil.equalsIgnoreCase(key,
											"method")) {
										// method =
										// Cast.toString(entry.getValue());
										dto.setMethod(value);
									} else if (StringUtil.equalsIgnoreCase(key,
											"multiple")) {
										// multiple =
										// Cast.toString(entry.getValue());
										dto.setMultiple(value);
									} else if (StringUtil.equalsIgnoreCase(key,
											"params")) {
										// String paramsStr =
										// Cast.toString(entry.getValue());
										// Map<String, Object> params = gson
										// .fromJson(
										// value,
										// new TypeToken<Map<String, Object>>()
										// {
										// }.getType());
										Map<String, Object> params = GsonUtil
												.fromJson(
														value,
														new TypeToken<Map<String, Object>>() {
														}.getType());
										dto.setParams(params);
									} else {// 其他动态参数
										// Map<String, Object> opts = new
										// HashMap<String,Object>();
										opts.put(key, entry.getValue());
									}
								}
								dto.setOpts(opts);
							}
						} else {
							dto.setUrl(StringUtil.trimToEmpty(listSource));
						}

					} else if (StringUtil.equals(sourceType,
							Constants.SOURCE_TYPE.SQL)) { // SQL 规范 :
															// 跟treeDictionaryBean属性一致，下拉框可以只含有name和code，tree需要id，pid

						// List<TreeDictionaryBean> values = new
						// ArrayList<TreeDictionaryBean>();
						//
						// List<SelectBean> tempList = listviewBeanMapper
						// .selectComboboxByDynamicSql(listSource);
						//
						// int index = 1;
						// for (SelectBean selectBean : tempList) {
						// TreeDictionaryBean treeDictionaryBean = new
						// TreeDictionaryBean();
						// treeDictionaryBean.setName(selectBean.getText());
						// treeDictionaryBean.setCode(selectBean.getValue());
						// treeDictionaryBean.setSortOrder(BigDecimal
						// .valueOf(index));
						// values.add(treeDictionaryBean);
						// index++;
						// }

						List<TreeDictionaryBean> values = listviewBeanMapper
								.selectTreeDictionaryByDynamicSql(listSource);

						dto.setValues(values);

					}

				} else if (StringUtil.equals(controlType,
						Constants.CONTROL_TYPE.TREE)) {

					Map<String, Object> opts = new HashMap<String, Object>();
					Map<String, Object> dialogOpts = new HashMap<String, Object>();
					Map<String, Object> treeOpts = new HashMap<String, Object>();

					String type = SysOrgTreeEnum.getValueByName(sourceType);

					if (StringUtil.isEmpty(type)) {
						throw new BusinessException("00300014",
								new String[] { propertyName });
					}

					// 自定义配置
					if (StringUtil.isNotEmpty(listSource)) {

						if (StringUtil.containsIgnoreCase(listSource,
								"dialogOpts")
								|| StringUtil.containsIgnoreCase(listSource,
										"treeOpts")) {
							// Map<String, Map<String, Object>> map = gson
							// .fromJson(
							// listSource,
							// new TypeToken<Map<String, Map<String,
							// Object>>>() {
							// }.getType());
							Map<String, Map<String, Object>> map = GsonUtil
									.fromJson(
											listSource,
											new TypeToken<Map<String, Map<String, Object>>>() {
											}.getType());
							for (Map.Entry<String, Map<String, Object>> entry : map
									.entrySet()) {
								String key = entry.getKey();
								Map<String, Object> value = entry.getValue();
								if (StringUtil.equalsIgnoreCase(key,
										"dialogOpts")) {
									dialogOpts.putAll(value);
								} else if (StringUtil.equalsIgnoreCase(key,
										"treeOpts")) {
									treeOpts.putAll(value);
								}
							}
						} else {
							// Map<String, Object> map = gson.fromJson(
							// listSource,
							// new TypeToken<Map<String, Object>>() {
							// }.getType());
							Map<String, Object> map = GsonUtil.fromJson(
									listSource,
									new TypeToken<Map<String, Object>>() {
									}.getType());
							treeOpts.putAll(map);
						}
					}

					// 一些默认的配置
					treeOpts.put("type", type);

					opts.put("dialogOpts", dialogOpts);
					opts.put("treeOpts", treeOpts);
					dto.setOpts(opts);
				}

				result.add(dto);
			}
		}
		return result;
	}

	/**
	 * 根据listviewId获取cols
	 * 
	 * @param listviewId
	 * @return
	 */
	private List<ColumnBean> getColsByListviewId(String listviewId) {

		List<ListviewFieldLabelBean> fieldLabels = listviewFieldLabelBeanMapper
				.selectFieldLabelByListviewId(listviewId);

		return getCols(fieldLabels);
	}

	@Override
	public TableDataBean<HashMap<String, Object>> selectList(
			TableQueryConditionBean condition) throws BusinessException {

		// 校验
		if (condition == null) {
			// 页面条件不能为空
			throw new BusinessException("00300001");
		}

		// listviewName
		String listviewName = condition.getListviewName();
		if (StringUtil.isEmpty(listviewName)) {
			// listviewName不能为空
			throw new BusinessException("00300001");
		}

		// 获得basic配置
		ListviewBean basic = listviewBeanMapper.selectByName(listviewName);
		if (basic == null) {
			// 该listview配置不存在
			throw new BusinessException("00300010",
					new String[] { listviewName });
		}

		// 获取可执行的SQL
		String execSql = getExecSql(condition.getCondition(), basic);

		// 查询
		Page<HashMap<String, Object>> page = queryPageData(execSql,
				condition.getPageNum(), condition.getPageSize());

		// 结果
		TableDataBean<HashMap<String, Object>> result = new TableDataBean<HashMap<String, Object>>();
		result.setCols(getColsByListviewId(basic.getId()));
		result.setData(page.getData());
		result.setPageNum(page.getPageNum());
		result.setPageSize(page.getPageSize());
		result.setTotal((int) page.getTotal());
		result.setPageSizeList(basic.getPageSizeList());

		return result;
	}

	/**
	 * 
	 * 正常查询和预览使用共通
	 * 
	 * @param originalSql
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	private Page<HashMap<String, Object>> queryPageData(String originalSql,
			int pageNum, int pageSize) {
		// 开始分页
		// PageHelper.startPage(pageNum, pageSize);
		// 得到数据总数
		String countSql = parserFactory.openParser().getCountSql(originalSql);
		long total = listviewBeanMapper.selectCountByDynamicSql(countSql);

		// 分页对象
		Page<HashMap<String, Object>> page = new Page<HashMap<String, Object>>(
				pageNum, pageSize, total);

		// 查询最终数据，可以是分页，也可以是不分页
		List<HashMap<String, Object>> underscoreDataList = null;
		if (pageSize <= 0) {// 不分页
			underscoreDataList = listviewBeanMapper
					.selectByDynamicSql(originalSql);
		} else {
			// 获取分页SQL
			String pageSql = parserFactory.openParser().getPageSql(originalSql,
					page);
			// 分页查询
			underscoreDataList = listviewBeanMapper.selectByDynamicSql(pageSql);
		}

		// 格式化结果集
		List<HashMap<String, Object>> camelDataList = ListviewResultSetHandler
				.formatResultData(underscoreDataList);

		page.setData(camelDataList);

		// 返回结果 //换成page
		// TableDataBean<HashMap<String, Object>> result = new
		// TableDataBean<HashMap<String, Object>>();

		// 数据保存
		// result.setData(camelDataList); //换成page

		// 字段保存
		// result.setCols(cols); //移到上层方法

		// 分页信息保存 pageInfo过期
		// PageInfo<HashMap<String, Object>> pageInfo = new
		// PageInfo<HashMap<String, Object>>(
		// underscoreDataList);
		// result.setPageNum(pageInfo.getPageNum());
		// result.setPageSize(pageInfo.getPageSize());
		// result.setTotal((int) pageInfo.getTotal());

		// result.setPageNum(page.getPageNum()); //换成page
		// result.setPageSize(page.getPageSize()); //换成page
		// result.setTotal((int) page.getTotal()); //换成page
		// result.setPageSizeList(basic.getPageSizeList()); //移到上层方法

		return page;
	}

	/**
	 * @param params
	 * @param basic
	 * @return
	 */
	private String getExecSql(HashMap<String, String> params, ListviewBean basic) {

		//获取basicSql包括filter order and security
		String basicAndFilterAndOrderAndSecuritySql = queryBasicAndFilterAndOrderAndSecuritySql(basic);

		// 替换带有冒号的参数， 然后从前台条件中删除。
		if (params != null) {
			// basicSql =
			// DynamicSqlManager.replaceVariableParams(params,basicSql, false);
			// filterSql =
			// DynamicSqlManager.replaceVariableParams(params,filterSql, false);
			basicAndFilterAndOrderAndSecuritySql = DynamicSqlManager
					.replaceVariableParams(params,
							basicAndFilterAndOrderAndSecuritySql, false);
		}

		// 替换宏变量
		// filterSql = DynamicSqlManager.replaceMacros(filterSql, false);

		String listviewId = basic.getId();

		// 获取condition配置
		List<ListviewConditionBean> conditions = listviewConditionBeanMapper
				.selectConditionByListviewId(listviewId);

		// 组装condition部分的sql
		String conditionSql = buildConditionSql(conditions, params);

		// 检查条件sql
		conditionSql = DynamicSqlManager.checkFilterSql(conditionSql);

		// 组装成最终的可执行的SQL
		String execSql = buildExecSql(basicAndFilterAndOrderAndSecuritySql,
				conditionSql);

		// 替换宏变量
		execSql = DynamicSqlManager.replaceMacros(execSql, false);

		return execSql;
	}

	/**
	 * 获取
	 * @param basic
	 * @return
	 */
	private String queryBasicAndFilterAndOrderAndSecuritySql(ListviewBean basic) {
		// 将basicSql与dataSecuritySql区分开，先拼接filterSql，最后拼接dataSecuritySql
		String dataObjectId = basic.getDataobjectId();

		if (StringUtil.isEmpty(dataObjectId)) {
			throw new BusinessException("00300001");
		}

		DataObjectBean dob = dataObjectBeanMapper
				.selectByPrimaryKey(dataObjectId);

		if (dob == null) {
			throw new BusinessException("00300012",
					new String[] { basic.getDataobjectId() });
		}

		// basicSql
		String basicSql = dob.getFbsSql();

		// dataSecuritySql
		String dataSecuritySql = "";
		if (StringUtil.equals(Constants.YES_OR_NO.YES, basic.getIsRight()) && false) {//TODO 关闭权限控制
			dataSecuritySql = dataObjectQueryService
					.getDataSecuritySqlFragment(dataObjectId);
		}

		// basicSql 不先拼接dataSecuritySql
		// String basicSql = dataObjectQueryService.querySqlByDataObjectId(
		// basic.getDataobjectId(),
		// StringUtil.equals(Constants.YES_OR_NO.YES, basic.getIsRight()));

		// 过滤条件SQL
		String filterSql = basic.getFilter();

		// 排序SQL
		String orderSql = basic.getSortOrder();

		// 第一次组装
		String basicAndFilterAndOrderAndSecuritySql = buildBasicAndFilterAndOrderAndSecuritySql(
				basicSql, filterSql, orderSql, dataSecuritySql);
		
		return basicAndFilterAndOrderAndSecuritySql;
	}

	/**
	 * 组装basicSql
	 * 
	 * @param basicSql
	 * @param filterSql
	 * @param dataSecuritySql
	 * @return
	 */
	private String buildBasicAndFilterAndOrderAndSecuritySql(String basicSql,
			String filterSql, String orderSql, String dataSecuritySql) {

		if (StringUtil.isEmpty(basicSql)) {
			return "";
		}

		// 组装sql
		StringBuffer resultSb = new StringBuffer(basicSql);

		// basic
		if (StringUtil.indexOfIgnoreCase(basicSql, "where") == -1) {// TODO
																	// 判断有问题
																	// ,
																	// 比如sql中关联部分有where条件
																	// ,
																	// 暂无解决方法
			resultSb.append(" where 1=1 ");
		}

		// filter
		if (StringUtil.isNotEmpty(filterSql)) {
			resultSb.append(" and ").append(filterSql);
		}

		// order
		if (StringUtil.isNotEmpty(orderSql)) {
			resultSb.append(" ").append(orderSql);
		}

		// basic、filter、order 括起来
		resultSb = new StringBuffer(
				DynamicSqlManager.transferToSelectStarSql(resultSb.toString())
						+ " where 1=1 ");

		// dataSecurity
		if (StringUtil.isNotEmpty(dataSecuritySql)) {
			resultSb.append(" and ").append(dataSecuritySql);
		}

		return resultSb.toString();
	}

	/**
	 * 
	 * 组装成最后可执行的SQL
	 * 
	 * @param basicSql
	 * @param filterSql
	 * @param conditionSql
	 * @param orderSql
	 * @return
	 */
	private String buildExecSql(String basicSql, String conditionSql) {

		if (StringUtil.isEmpty(basicSql)) {
			return "";
		}

		// 组装sql
		StringBuffer execSb = new StringBuffer(basicSql);

		// // basic
		// if (StringUtil
		// .indexOfIgnoreCase(basicSql, Constants.SQL_FRAGMENT.WHERE) == -1) {
		// execSb.append(Constants.SQL_FRAGMENT.SPACE)
		// .append(Constants.SQL_FRAGMENT.WHERE)
		// .append(Constants.SQL_FRAGMENT.SPACE)
		// .append(Constants.SQL_FRAGMENT.TRUE_SQL);
		// }
		//
		// // filter
		// if (StringUtil.isNotEmpty(filterSql)) {
		// execSb.append(Constants.SQL_FRAGMENT.SPACE)
		// .append(Constants.SQL_FRAGMENT.AND)
		// .append(Constants.SQL_FRAGMENT.SPACE).append(filterSql);
		// }
		//
		// // basic、filter 括起来
		// execSb = new StringBuffer(
		// DynamicSqlManager.transferToSelectStarSql(execSb
		// .append(Constants.SQL_FRAGMENT.WHERE)
		// .append(Constants.SQL_FRAGMENT.SPACE)
		// .append(Constants.SQL_FRAGMENT.TRUE_SQL).toString()));
		//
		// // dataSecurity
		// if (StringUtil.isNotEmpty(dataSecuritySql)) {
		// execSb.append(Constants.SQL_FRAGMENT.SPACE)
		// .append(Constants.SQL_FRAGMENT.AND)
		// .append(Constants.SQL_FRAGMENT.SPACE)
		// .append(dataSecuritySql);
		// }

		// condition
		if (StringUtil.isNotEmpty(conditionSql)) {
			execSb.append(" and ").append(conditionSql);
		}

		return execSb.toString();
	}

	/**
	 * 组装conditionSql
	 * 
	 * @param conditions
	 * @param params
	 * @return
	 */
	private String buildConditionSql(List<ListviewConditionBean> conditions,
			HashMap<String, String> params) {
		// 放置重复的propertyName
		Map<String, Integer> usedProperty = new HashMap<String, Integer>();

		// 组装contidion部分SQL
		StringBuffer conditionSb = new StringBuffer();

		if (conditions != null && !conditions.isEmpty()) {
			for (ListviewConditionBean condition : conditions) {

				String propertyName = StringUtil.trimToEmpty(condition
						.getPropertyName());
				// String controlType = condition.getControlType();
				String propertyValue = "";
				// 1.重复条件,如日期、时间 createTime>a && createTime<b
				if (usedProperty.containsKey(propertyName)) {
					int repeatTimes = usedProperty.get(propertyName);
					usedProperty.put(propertyName, repeatTimes + 1);
					// 条件value
					propertyValue = StringUtil.trimToEmpty(params
							.get(propertyName + repeatTimes));
				} else {
					usedProperty.put(propertyName, 1);
					// 条件value
					propertyValue = StringUtil.trimToEmpty(params
							.get(propertyName));
				}

				// 格式化propertyValue
				propertyValue = formatValueByOperator(propertyValue, condition);

				if (StringUtil.isNotEmpty(propertyValue)) {
					// 临时SQL
					String tmpSql = StringUtil.trimToEmpty(condition
							.getLeftBracket())
							+ " "
							+ propertyName
							+ " "
							+ OperatorEnum.getValueByName(condition
									.getOperator())
							+ " "
							+ propertyValue
							+ " "
							+ StringUtil.trimToEmpty(condition
									.getRightBracket())
							+ " "
							+ condition.getLogicSymbol()
							+ " ";

					conditionSb.append(" ").append(
							tmpSql);
				} else {
					conditionSb
							.append(" ")
							.append(StringUtil.trimToEmpty(condition
									.getLeftBracket()))
							.append(" ")
							// .append("1=1")
							.append(" ")
							.append(StringUtil.trimToEmpty(condition
									.getRightBracket()));
				}
			}
		}
		return conditionSb.toString();
	}

	/**
	 * 根据操作格式化数据
	 * 
	 * @param propertyValue
	 * @param condition
	 * @return
	 */
	private String formatValueByOperator(String propertyValue,
			ListviewConditionBean condition) {

		if (StringUtil.isEmpty(propertyValue)) {
			return "";
		}

		String operator = condition.getOperator();

		String controlType = condition.getControlType();

		if (StringUtil.equalsIgnoreCase(propertyValue, "null")
				&& Constants.OPERATOR.EQUAL.equals(operator)) {

			condition.setOperator("is");
		} else if (StringUtil.equalsIgnoreCase(propertyValue, "null")
				&& Constants.OPERATOR.NOTEQUAL.equals(operator)) {

			condition.setOperator("is not");
		} else if (Constants.OPERATOR.RIGHTLIKE.equals(operator)) {

			propertyValue = "'%" + propertyValue + "'";
		} else if (Constants.OPERATOR.LEFTLIKE.equals(operator)) {

			propertyValue = "'" + propertyValue + "%'";
		} else if (Constants.OPERATOR.ALLLIKE.equals(operator)) {

			propertyValue = "'%" + propertyValue + "%'";
		} else if (Constants.OPERATOR.IN.equals(operator)
				|| Constants.OPERATOR.NOTIN.equals(operator)) {

			String[] candidate = propertyValue.split(",");
			StringBuffer sb = new StringBuffer();

			if (candidate.length > 1000) {
				// oracle限制IN的元素个数不超过1000
				for (int i = 0; i < candidate.length - 1000; i++) {
					candidate[1000 + i] = null;
				}
			}
			for (String str : candidate) {
				if (!StringUtil.isEmpty(str))
					sb.append("'" + str + "',");
			}
			String rs = sb.toString();
			if (rs.endsWith(",")) {
				rs = rs.substring(0, rs.length() - 1);
			}
			propertyValue = "(" + rs + ")";

		} else {
			propertyValue = "'" + propertyValue + "'";
		}

		Parser parser = parserFactory.openParser();

		if (controlType.equals("date")) {
			if (propertyValue.length() == 12) {
				// propertyValue = "to_date(" + propertyValue +
				// ", 'YYYY-MM-DD')";
				propertyValue = parser.toDate(propertyValue, "date");
			} else {
				// 在日期<=条件下时分秒需要加上 23:59:59才能有效地匹配当天的数据
				if (Constants.OPERATOR.LESSEQUAL
						.equals(condition.getOperator())) {
					// propertyValue = "to_date(" + propertyValue.substring(0,
					// 11)
					// + " 23:59:59', 'YYYY-MM-DD HH24:MI:SS')";
					propertyValue = parser.toDate(
							propertyValue.substring(0, 11) + " 23:59:59'",
							"datetime");
				} else {
					// propertyValue = "to_date(" + propertyValue
					// + ", 'YYYY-MM-DD HH24:MI:SS')";
					propertyValue = parser.toDate(propertyValue, "datetime");
				}
			}
		} else if (controlType.equals("datetime")) {
			// propertyValue = "to_date(" + propertyValue
			// + ", 'YYYY-MM-DD HH24:MI:SS')";
			propertyValue = parser.toDate(propertyValue, "datetime");
		}
		return propertyValue;
	}

	@Override
	public ListviewConfigBean selectPreviewConfig(
			ListviewDataBean listviewDataBean) throws BusinessException {

		ListviewConfigBean config = new ListviewConfigBean();

		// 基本信息
		ListviewBean basic = listviewDataBean.getBasic();

		try {
			BeanUtils.copyProperties(config, basic);
		} catch (Exception e) {
		}

		// cols
		config.setCols(getCols(listviewDataBean.getCols()));

		// condition
		// 为了构造conditionsDto在预览和非预览情况下能够成为共通,故传入空的listviewName和initParams
		config.setCondition(getConditionWithOutInitParams(
				listviewDataBean.getCondition(), "",
				new HashMap<String, String>()));

		return config;
	}

	@Override
	public DataBean<String> selectVariableParameter(
			ListviewDataBean listviewDataBean) {

		if (listviewDataBean == null) {
			throw new BusinessException("00300001");
		}

		ListviewBean basic = listviewDataBean.getBasic();

		if (basic == null) {
			throw new BusinessException("00300001");
		}

		String dataObjectId = basic.getDataobjectId();

		if (StringUtil.isEmpty(dataObjectId)) {
			throw new BusinessException("00300001");
		}

		// basicSql
//		String basicSql = dataObjectQueryService.querySqlByDataObjectId(//TODO 不调这个方法，此方法已经替换过变量了
//				basic.getDataobjectId(),
//				StringUtil.equals(Constants.YES_OR_NO.YES, basic.getIsRight()));

//		// 过滤条件SQL
//		String filterSql = basic.getFilter();
//
//		// 非执行SQL
//		String noExecsql = basicSql + " " + filterSql;
		
		//获取basicSql包括filter order and security
		String noExecsql = queryBasicAndFilterAndOrderAndSecuritySql(basic);

		// 分析SQL
		List<String> data = DynamicSqlManager
				.analysisSqlWithVariableParameter(noExecsql);

		DataBean<String> result = new DataBean<String>();

		result.setData(data);

		return result;
	}

	@Override
	public TableDataBean<HashMap<String, Object>> selectPreviewList(
			ListviewPreviewQueryConditionBean previewQuery) {

		ListviewDataBean listviewDataBean = previewQuery.getPreviewData();

		if (listviewDataBean == null) {
			throw new BusinessException("00300001");
		}

		ListviewBean basic = listviewDataBean.getBasic();

		if (basic == null) {
			throw new BusinessException("00300001");
		}

		String dataObjectId = basic.getDataobjectId();

		if (StringUtil.isEmpty(dataObjectId)) {
			throw new BusinessException("00300001");
		}

		// 获取可执行的SQL
		String execSql = getExecSql(previewQuery.getCondition(), basic);

		// 查询
		Page<HashMap<String, Object>> page = queryPageData(execSql,
				previewQuery.getPageNum(), previewQuery.getPageSize());

		// 结果
		TableDataBean<HashMap<String, Object>> result = new TableDataBean<HashMap<String, Object>>();
		result.setCols(getCols(listviewDataBean.getCols()));
		result.setData(page.getData());
		result.setPageNum(page.getPageNum());
		result.setPageSize(page.getPageSize());
		result.setTotal((int) page.getTotal());
		result.setPageSizeList(basic.getPageSizeList());

		return result;
	}

	/**
	 * 
	 * 获得字段标签并转成columnBean对象
	 * 
	 * @param fieldLabels
	 * @return
	 */
	private List<ColumnBean> getCols(List<ListviewFieldLabelBean> fieldLabels) {

		List<ColumnBean> cols = new ArrayList<ColumnBean>();

		if (fieldLabels != null && !fieldLabels.isEmpty()) {
			for (ListviewFieldLabelBean fieldLabel : fieldLabels) {
				ColumnBean col = new ColumnBean();
				col.setId(StringUtil.trimToEmpty(fieldLabel.getPropertyName()));
				col.setName(StringUtil.isEmpty(fieldLabel.getDisplayTitle()) ? StringUtil
						.trimToEmpty(fieldLabel.getPropertyName()) : StringUtil
						.trimToEmpty(fieldLabel.getDisplayTitle()));
				cols.add(col);
			}
		}

		return cols;
	}

	@Override
	public String createTempExcel(
			ExcelExportConditionBean excelExportConditionBean) {

		// 校验
		if (excelExportConditionBean == null) {
			// 页面条件不能为空
			throw new BusinessException("00300001");
		}

		// listviewName
		String listviewName = excelExportConditionBean.getListviewName();
		if (StringUtil.isEmpty(listviewName)) {
			// listviewName不能为空
			throw new BusinessException("00300001");
		}

		// 获得basic配置
		ListviewBean basic = listviewBeanMapper.selectByName(listviewName);
		if (basic == null) {
			// 该listview配置不存在
			throw new BusinessException("00300010",
					new String[] { listviewName });
		}

		// 获取可执行的SQL
		String execSql = getExecSql(excelExportConditionBean.getCondition(),
				basic);

		// 查询全部数据
		List<HashMap<String, Object>> underscoreDataList = listviewBeanMapper
				.selectByDynamicSql(execSql);

		// 格式化结果集
		List<HashMap<String, Object>> dataList = ListviewResultSetHandler
				.formatResultData(underscoreDataList);

		// // 生成excel文件
		// Workbook wb = ExcelUtil.dataToExcel(
		// excelExportConditionBean.getCols(), dataList, "");
		//
		// // 写入临时文件
		// File file = ExcelUtil.writeToFile(wb);

		// 生成excel文件
		File file = ExcelUtil.dataToExcel(excelExportConditionBean.getCols(),
				dataList, "", "tmpExcel", ".xls");

		// 上传临时文件
		return fileManagerService.uploadTempFile(file);
	}
}
