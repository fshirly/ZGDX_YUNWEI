package com.fable.insightview.platform.listview.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.common.entity.ColumnBean;
import com.fable.insightview.platform.common.entity.DataBean;
import com.fable.insightview.platform.common.entity.TableDataBean;
import com.fable.insightview.platform.common.entity.TableQueryConditionBean;
import com.fable.insightview.platform.common.util.Cast;
import com.fable.insightview.platform.common.util.ReflectUtil;
import com.fable.insightview.platform.common.util.StringUtil;
import com.fable.insightview.platform.common.util.Tool;
import com.fable.insightview.platform.dataobject.entity.DataObjectBean;
import com.fable.insightview.platform.dataobject.entity.DataObjectFieldLabelBean;
import com.fable.insightview.platform.dataobject.mapper.DataObjectBeanMapper;
import com.fable.insightview.platform.dataobject.mapper.DataObjectFieldLabelBeanMapper;
import com.fable.insightview.platform.dataobject.service.DataSecurityService;
import com.fable.insightview.platform.listview.entity.ListviewBean;
import com.fable.insightview.platform.listview.entity.ListviewConditionBean;
import com.fable.insightview.platform.listview.entity.ListviewDataBean;
import com.fable.insightview.platform.listview.entity.ListviewFieldLabelBean;
import com.fable.insightview.platform.listview.mapper.ListviewBeanMapper;
import com.fable.insightview.platform.listview.mapper.ListviewConditionBeanMapper;
import com.fable.insightview.platform.listview.mapper.ListviewFieldLabelBeanMapper;
import com.fable.insightview.platform.listview.service.ListviewManagerService;
import com.fable.insightview.platform.util.UserSession;
import com.fable.insightview.platform.util.exception.BusinessException;

/**
 * 
 * @author zhouwei
 * 
 */
@Service
public class ListviewManagerServiceImpl implements ListviewManagerService {

	@Autowired
	ListviewBeanMapper listviewBeanMapper;

	@Autowired
	ListviewConditionBeanMapper listviewConditionBeanMapper;

	@Autowired
	ListviewFieldLabelBeanMapper listviewFieldLabelBeanMapper;

	@Autowired
	DataObjectBeanMapper dataObjectBeanMapper;

	@Autowired
	DataObjectFieldLabelBeanMapper dataObjectFieldLabelBeanMapper;

	@Autowired
	SqlSessionFactoryBean sessionFactoryBean;

	// @Autowired
	// RequestUtil requestUtil;

	@Autowired
	DataSecurityService dataSecurityService;

	@Override
	public void insertListviewDataBean(ListviewDataBean listviewDataBean)
			throws BusinessException {

		if (listviewDataBean == null) {
			// 传递json对象不能为空
			throw new BusinessException("00300001");
		}

		ListviewBean basic = listviewDataBean.getBasic();
		List<ListviewConditionBean> condition = listviewDataBean.getCondition();
		// List<ListviewDataSecurityBean> dataSecurity = listviewDataBean
		// .getDataSecurity();
		List<ListviewFieldLabelBean> cols = listviewDataBean.getCols();

		// 1
		if (basic == null) {
			// listview主数据不能为空
			throw new BusinessException("00300001");
		}
		String id = Cast.guid2Str(Tool.newGuid());
		basic.setId(id);
		boolean basicRet = insertListviewBasic(basic);
		if (!basicRet) {
			// 保存listview主数据异常
			throw new BusinessException("00300003");
		}

		// 2
		if (condition != null) {
			boolean conditionRet = insertListviewCondition(condition, id);
			if (!conditionRet) {
				// 保存listview条件数据异常
				throw new BusinessException("00300004");
			}
		}

		// 3
		// if (dataSecurity != null) {
		// boolean dataSecurityRet = insertListviewDataSecurity(dataSecurity,
		// id);
		// if (!dataSecurityRet) {
		// // 保存listview权限条件数据异常
		// throw new BusinessException("444444444444444");
		// }
		// }

		// 4
		if (cols == null) {
			// listview字段标签数据不能为空
			throw new BusinessException("00300005");
		}
		boolean colsRet = insertListviewFieldLabel(cols, id);
		if (!colsRet) {
			// 保存listview字段标签数据异常
			throw new BusinessException("00300006");
		}

	}

	/**
	 * 新增listview basic
	 * 
	 * @param listviewBean
	 * @return
	 */
	private boolean insertListviewBasic(ListviewBean listviewBean) {

		// 判断英文名字不能重复
		ListviewBean bean = listviewBeanMapper.selectByName(listviewBean
				.getName());
		if (bean != null) {
			// 已存在相同名字的listview
			throw new BusinessException("00300002",
					new String[] { listviewBean.getName() });
		}

		// requestUtil.boToUserbo(listviewBean);

		int count = listviewBeanMapper.insert(listviewBean);
		return count == 1;
	}

	/**
	 * 新增查询条件
	 * 
	 * @param conditions
	 * @param listviewId
	 * @return
	 */
	private boolean insertListviewCondition(
			List<ListviewConditionBean> conditions, String listviewId) {
		if (conditions != null && !conditions.isEmpty()) {
			// for (ListviewConditionBean condition : conditions) {
			// condition.setId(UUID.randomUUID().toString());
			// condition.setListviewId(listviewId);
			// int count = listviewConditionBeanMapper.insert(condition);
			// if (count != 1) {
			// return false;
			// }
			// }

			for (ListviewConditionBean condition : conditions) {
				condition.setId(Cast.guid2Str(Tool.newGuid()));
				// requestUtil.boToUserbo(condition);
				condition.setListviewId(listviewId);
			}

			listviewConditionBeanMapper.insertList(conditions);

		}
		return true;
	}

	// /**
	// * 新增权限查询条件
	// *
	// * @param dataSecurities
	// * @param listviewId
	// * @return
	// */
	// private boolean insertListviewDataSecurity(
	// List<ListviewDataSecurityBean> dataSecurities, String listviewId) {
	// if (CollectionUtils.isNotEmpty(dataSecurities)) {
	// for (ListviewDataSecurityBean dataSecurity : dataSecurities) {
	// dataSecurity.setId(UUID.randomUUID().toString());
	// dataSecurity.setListviewId(listviewId);
	// int count = listviewDataSecurityBeanMapper.insert(dataSecurity);
	// if (count != 1) {
	// return false;
	// }
	// }
	// }
	// return true;
	// }

	/**
	 * 增加fieldlabel标签
	 * 
	 * @param cols
	 * @param listviewId
	 * @return
	 */
	private boolean insertListviewFieldLabel(List<ListviewFieldLabelBean> cols,
			String listviewId) {
		if (cols != null && !cols.isEmpty()) {
			// for (ListviewFieldLabelBean col : cols) {
			// col.setId(UUID.randomUUID().toString());
			// col.setListviewId(listviewId);
			// int count = listviewFieldLabelBeanMapper.insert(col);
			// if (count != 1) {
			// return false;
			// }
			// }
			for (ListviewFieldLabelBean col : cols) {
				col.setId(Cast.guid2Str(Tool.newGuid()));
				// requestUtil.boToUserbo(col);
				col.setListviewId(listviewId);
			}

			listviewFieldLabelBeanMapper.insertList(cols);

		}
		return true;
	}

	@Override
	public void updateListviewDataBean(ListviewDataBean listviewDataBean)
			throws BusinessException {

		if (listviewDataBean == null) {
			// 传递json对象不能为空
			throw new BusinessException("00300001");
		}

		ListviewBean basic = listviewDataBean.getBasic();
		List<ListviewConditionBean> condition = listviewDataBean.getCondition();
		// List<ListviewDataSecurityBean> dataSecurity = listviewDataBean
		// .getDataSecurity();
		List<ListviewFieldLabelBean> cols = listviewDataBean.getCols();

		// 1
		if (basic == null) {
			// listview主数据不能为空
			throw new BusinessException("00300001");
		}
		String id = basic.getId();
		if (StringUtil.isEmpty(id)) {
			// listview主对象Id不能为空
			throw new BusinessException("00300001");
		}
		boolean basicRet = updateListviewBasic(basic);
		if (!basicRet) {
			// 更新listview主数据异常
			throw new BusinessException("00300007");
		}

		// 2
		boolean conditionRet = updateListviewCondition(condition, id);
		if (!conditionRet) {
			// 更新listview条件数据异常
			throw new BusinessException("00300008");
		}

		// 3
		// boolean dataSecurityRet = updateListviewDataSecurity(dataSecurity,
		// id);
		// if (!dataSecurityRet) {
		// // 更新listview权限条件数据异常
		// throw new BusinessException("444444444444444");
		// }

		// 4
		if (cols == null) {
			// listview字段标签数据不能为空
			throw new BusinessException("00300005");
		}
		boolean colsRet = updateListviewFieldLabel(cols, id);
		if (!colsRet) {
			// 保存listview字段标签数据异常
			throw new BusinessException("00300009");
		}

	}

	/**
	 * 更新listview basic
	 * 
	 * @param basic
	 * @return
	 */
	private boolean updateListviewBasic(ListviewBean basic) {
		// 根据配置，判断是否可以修改平台级的数据
		controlModifyPlatformListviewData(basic.getId());

		// 修改
		// requestUtil.boToUserbo(basic);
		int count = listviewBeanMapper.updateByPrimaryKey(basic);
		return count == 1;
	}

	// 根据配置，判断是否可以修改平台级的数据
	private void controlModifyPlatformListviewData(String listviewId) {
		// if(!Boolean.valueOf(SystemPropertyHandler.getProperty("fbs.platform.data.control"))){
		// ListviewBean listviewBean =
		// listviewBeanMapper.selectByPrimaryKey(listviewId);
		// if(listviewBean != null &&
		// StringUtil.equals(UserSession.getDefaultSysId(),
		// listviewBean.getSysId())){
		// throw new BusinessException("00300015" ,new
		// String[]{listviewBean.getName()} );
		// }
		// }
	}

	/**
	 * 更新条件
	 * 
	 * @param condition
	 * @param listviewId
	 * @return
	 */
	private boolean updateListviewCondition(
			List<ListviewConditionBean> condition, String listviewId) {
		// requestUtil.boListToUserboList(condition);
		listviewConditionBeanMapper.deleteAllByListviewId(listviewId);
		return insertListviewCondition(condition, listviewId);
	}

	// /**
	// * 更新权限条件
	// *
	// * @param dataSecurity
	// * @param id
	// * @return
	// */
	// private boolean updateListviewDataSecurity(
	// List<ListviewDataSecurityBean> dataSecurity, String listviewId) {
	// listviewDataSecurityBeanMapper.deleteAllByListviewId(listviewId);
	// return insertListviewDataSecurity(dataSecurity, listviewId);
	// }

	/**
	 * 更新字段标签
	 * 
	 * @param cols
	 * @param listviewId
	 * @return
	 */
	private boolean updateListviewFieldLabel(List<ListviewFieldLabelBean> cols,
			String listviewId) {
		// requestUtil.boListToUserboList(cols);
		listviewFieldLabelBeanMapper.deleteAllByListviewId(listviewId);

		return insertListviewFieldLabel(cols, listviewId);
	}

	@Override
	public void deleteListviewDataBeans(String[] ids) throws BusinessException {
		if (Arrays.asList(ids) != null && !Arrays.asList(ids).isEmpty()) {
			for (String id : ids) {
				deleteListviewDataBeanById(id);
			}
		}
	}

	/**
	 * 删除ListviewDataBean
	 * 
	 * @param id
	 */
	private void deleteListviewDataBeanById(String id) {

		if (StringUtil.isEmpty(id)) {
			return;
		}

		// 根据配置，判断是否可以修改平台级的数据
		controlModifyPlatformListviewData(id);

		// 1
		listviewBeanMapper.deleteByPrimaryKey(id);

		// 2
		listviewConditionBeanMapper.deleteAllByListviewId(id);

		// 3
		// listviewDataSecurityBeanMapper.deleteAllByListviewId(id);

		// 4
		listviewFieldLabelBeanMapper.deleteAllByListviewId(id);
	}

	@Override
	public ListviewDataBean selectListviewDataBean(String id)
			throws BusinessException {

		if (StringUtil.isEmpty(id)) {
			// id为空！
			throw new BusinessException("00300001");
		}

		ListviewDataBean data = new ListviewDataBean();

		// 1
		ListviewBean basic = listviewBeanMapper.selectByPrimaryKey(id);
		if (basic == null) {
			return null;
		}
		data.setBasic(basic);

		// 2
		List<ListviewConditionBean> condition = listviewConditionBeanMapper
				.selectConditionByListviewId(id);
		data.setCondition(condition);

		// 3
		// List<ListviewDataSecurityBean> dataSecurity =
		// listviewDataSecurityBeanMapper
		// .selectDataSecurityByListviewId(id);
		// data.setDataSecurity(dataSecurity);

		// 4
		List<ListviewFieldLabelBean> cols = listviewFieldLabelBeanMapper
				.selectFieldLabelByListviewId(id);
		data.setCols(cols);

		return data;
	}

	// @Override
	// public DataBean<ListviewFieldLabelBean> selectFieldLabelsByDynamicSql(
	// String sql) throws BusinessException {
	//
	// DataBean<ListviewFieldLabelBean> result = new
	// DataBean<ListviewFieldLabelBean>();
	// List<ListviewFieldLabelBean> list = new
	// ArrayList<ListviewFieldLabelBean>();
	//
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	//
	// try {
	// SqlSessionFactory sf = sessionFactoryBean.getObject();
	// SqlSession session = sf.openSession();
	// ps = session.getConnection().prepareStatement(sql);
	// rs = ps.executeQuery();
	// ResultSetMetaData rsmd = rs.getMetaData();
	// for (int i = 1; i <= rsmd.getColumnCount(); i++) {
	// ListviewFieldLabelBean fieldLabel = new ListviewFieldLabelBean();
	// fieldLabel.setPropertyName(rsmd.getColumnName(i));
	// fieldLabel.setDisplayTitle(rsmd.getColumnLabel(i));
	// list.add(fieldLabel);
	// }
	// result.setData(list);
	// } catch (SQLException e) {
	// // SQL异常
	// throw new BusinessException("00000001");
	// } catch (Exception e) {
	// // 获取SqlSessionFactory异常
	// throw new BusinessException("00000001");
	// } finally {
	// try {
	// if (rs != null) {
	// rs.close();
	// }
	// } catch (SQLException e) {
	// }
	//
	// try {
	// if (ps != null) {
	// ps.close();
	// }
	// } catch (SQLException e) {
	// }
	// }
	//
	// return result;
	// }

	@Override
	public DataBean<ListviewFieldLabelBean> selectFieldLabelsByDataObjectId(
			String dataObjectId, boolean camel) throws BusinessException {

		if (StringUtil.isEmpty(dataObjectId)) {
			throw new BusinessException("00300001");
		}

		List<DataObjectFieldLabelBean> list = dataObjectFieldLabelBeanMapper
				.selectListByDataObjectId(dataObjectId);

		DataBean<ListviewFieldLabelBean> result = new DataBean<ListviewFieldLabelBean>();
		List<ListviewFieldLabelBean> newList = new ArrayList<ListviewFieldLabelBean>();

		for (DataObjectFieldLabelBean bean : list) {
			ListviewFieldLabelBean fBean = new ListviewFieldLabelBean();
			try {
				BeanUtils.copyProperties(fBean, bean);
			} catch (IllegalAccessException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (camel) {
				fBean.setPropertyName(StringUtil.underscore2camel(fBean
						.getPropertyName()));
			}
			newList.add(fBean);
		}
		result.setData(newList);

		return result;
	}

	// @Override
	// public SelectDataBean selectTargets(String targetType)
	// throws BusinessException {
	//
	// SelectDataBean result = new SelectDataBean();
	// List<SelectBean> datas = new ArrayList<SelectBean>();
	//
	// if (Constants.TARGET_TYPE.USER.equals(targetType)) {
	// UserBean params = new UserBean();
	// params.setSysId(UserSession.getSysId());
	// List<UserBean> users = userBeanMapper.selectAllUsers(params);
	// if (CollectionUtils.isNotEmpty(users)) {
	// for (UserBean user : users) {
	// SelectBean sb = new SelectBean();
	// sb.setText(user.getUsername());
	// sb.setValue(user.getId());
	// datas.add(sb);
	// }
	// }
	// } else if (Constants.TARGET_TYPE.ROLE.equals(targetType)) {
	// RoleBean params = new RoleBean();
	// params.setSysId(UserSession.getSysId());
	// List<RoleBean> roles = roleBeanMapper.selectAllRoles(params);
	// if (CollectionUtils.isNotEmpty(roles)) {
	// for (RoleBean role : roles) {
	// SelectBean sb = new SelectBean();
	// sb.setText(role.getRoleName());
	// sb.setValue(role.getId());
	// datas.add(sb);
	// }
	// }
	// }
	//
	// result.setData(datas);
	// return result;
	// }

	@Override
	public void insertListviewDataBeans(DataBean<ListviewDataBean> dataBean)
			throws BusinessException {
		List<ListviewDataBean> datas = dataBean.getData();
		if (datas == null || datas.isEmpty()) {
			return;
		}
		for (ListviewDataBean listview : datas) {
			this.insertListviewDataBean(listview);
		}
	}

	@Override
	public DataBean<ListviewDataBean> selectListviewDataBeans(String[] ids) {
		DataBean<ListviewDataBean> dataBean = new DataBean<ListviewDataBean>();
		List<ListviewDataBean> data = new ArrayList<ListviewDataBean>();
		for (String id : ids) {
			// 查询listview
			ListviewDataBean listview = this.selectListviewDataBean(id);
			data.add(listview);
		}
		dataBean.setData(data);
		return dataBean;
	}

	/*
	 * @Override public void
	 * insertListviewDataBeans(DataBean<ListviewDataObjectBean> dataBean) throws
	 * BusinessException {
	 * 
	 * List<ListviewDataObjectBean> datas = dataBean.getData(); if
	 * (CollectionUtils.isEmpty(datas)) { return; } for (ListviewDataObjectBean
	 * data : datas) { ListviewDataBean listview = data.getListview();
	 * DataObjectBean dataObject = data.getDataObject();
	 * //根据dataObject名字判断是否存在，如果两个系统都存在相同但是不同意思的dataobject，则没办法了，故此最好不要相同否则有问题
	 * DataObjectBean dataObjectByName =
	 * dataObjectBeanMapper.selectByName(dataObject.getObjectName());
	 * if(dataObjectByName != null){ //存在则查出id，放入listview的basic中
	 * listview.getBasic().setDataobjectId(dataObjectByName.getId()); }else{
	 * //不存在则新增，然后将id放入listview的basic中 String dataObjectId =
	 * dataSecurityService.createDataObject(dataObject);
	 * listview.getBasic().setDataobjectId(dataObjectId); } //保存listview
	 * this.insertListviewDataBean(listview); }
	 * 
	 * }
	 * 
	 * @Override public DataBean<ListviewDataObjectBean>
	 * selectListviewDataObjectBeans(String[] ids) {
	 * DataBean<ListviewDataObjectBean> dataBean = new
	 * DataBean<ListviewDataObjectBean>(); List<ListviewDataObjectBean> datas =
	 * new ArrayList<ListviewDataObjectBean>(); for (String id : ids) {
	 * ListviewDataObjectBean listviewDataObject = new ListviewDataObjectBean();
	 * //查询listview ListviewDataBean listview = this.selectListviewDataBean(id);
	 * //查询dataObject String dataObjectId =
	 * listview.getBasic().getDataobjectId(); DataObjectBean dataObject =
	 * dataObjectBeanMapper.selectByPrimaryKey(dataObjectId);
	 * List<DataObjectFieldLabelBean> fieldLabels =
	 * dataObjectFieldLabelBeanMapper.selectListByDataObjectId(dataObjectId);
	 * dataObject.setFieldLabels(fieldLabels); //组装
	 * listviewDataObject.setListview(listview);
	 * listviewDataObject.setDataObject(dataObject);
	 * datas.add(listviewDataObject); } dataBean.setData(datas); return
	 * dataBean; }
	 */

	@Override
	public TableDataBean<ListviewBean> selectListviewBeansForPage(
			TableQueryConditionBean tableCondition) throws BusinessException {
		// PageHelper.startPage(tableCondition.getPageNum(),
		// tableCondition.getPageSize());
		ListviewBean params = new ListviewBean();
		HashMap<String, String> condition = tableCondition.getCondition();
		if (condition != null) {
			for (Map.Entry<String, String> entry : condition.entrySet()) {
				String key = StringUtil.trimToEmpty(entry.getKey());
				String value = StringUtil.trimToEmpty(entry.getValue());
				ReflectUtil
						.execMethod(params,
								"set" + StringUtil.toUpperCaseFirstOne(key),
								new Class<?>[] { String.class },
								new String[] { value });
			}
		}

		List<ListviewBean> datas = listviewBeanMapper
				.selectListviewBeans(params);
		// PageInfo<ListviewBean> page = new PageInfo<ListviewBean>(datas);
		TableDataBean<ListviewBean> result = new TableDataBean<ListviewBean>();
		result.setData(datas);
		List<ColumnBean> cols = new ArrayList<ColumnBean>();
		ColumnBean cb1 = new ColumnBean();
		cb1.setId("id");
		cb1.setName("ID");
		cols.add(cb1);
		ColumnBean cb2 = new ColumnBean();
		cb2.setId("name");
		cb2.setName("名称");
		cols.add(cb2);
		ColumnBean cb3 = new ColumnBean();
		cb3.setId("tital");
		cb3.setName("标题");
		cols.add(cb3);
		// ColumnBean cb4 = new ColumnBean();
		// cb4.setId("sql");
		// cb4.setName("SQL");
		// cols.add(cb4);
		ColumnBean cb5 = new ColumnBean();
		cb5.setId("note");
		cb5.setName("备注");
		cols.add(cb5);
		result.setCols(cols);
		result.setPageNum(tableCondition.getPageNum());
		result.setPageSize(tableCondition.getPageSize());
		result.setTotal(datas.size());
		result.setPageSizeList("10,20,50,100");
		return result;
	}

	@Override
	public DataBean<DataObjectBean> selectDataObjects() {

		DataBean<DataObjectBean> result = new DataBean<DataObjectBean>();

		List<DataObjectBean> data = dataObjectBeanMapper
				.selectDataObjects(UserSession.getSysId());

		result.setData(data);

		return result;
	}

}
