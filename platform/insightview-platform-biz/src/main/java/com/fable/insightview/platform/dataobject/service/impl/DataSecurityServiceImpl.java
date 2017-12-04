package com.fable.insightview.platform.dataobject.service.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.common.entity.DataBean;
import com.fable.insightview.platform.common.util.Cast;
import com.fable.insightview.platform.common.util.StringUtil;
import com.fable.insightview.platform.common.util.Tool;
import com.fable.insightview.platform.core.exception.BusinessException;
import com.fable.insightview.platform.dataobject.entity.DataObjectBean;
import com.fable.insightview.platform.dataobject.entity.DataObjectFieldLabelBean;
import com.fable.insightview.platform.dataobject.entity.DataSecurityBean;
import com.fable.insightview.platform.dataobject.entity.DataSecurityItemBean;
import com.fable.insightview.platform.dataobject.entity.RoleWithDataSecurityBean;
import com.fable.insightview.platform.dataobject.mapper.DataObjectBeanMapper;
import com.fable.insightview.platform.dataobject.mapper.DataObjectFieldLabelBeanMapper;
import com.fable.insightview.platform.dataobject.mapper.DataSecurityBeanMapper;
import com.fable.insightview.platform.dataobject.mapper.DataSecurityItemBeanMapper;
import com.fable.insightview.platform.dataobject.service.DataSecurityService;
import com.fable.insightview.platform.util.DynamicSqlManager;
import com.fable.insightview.platform.util.Constants.LEFT_BRACKET;
import com.fable.insightview.platform.util.Constants.LOGIC_SYMBOL;
import com.fable.insightview.platform.util.Constants.OPERATOR;
import com.fable.insightview.platform.util.Constants.RIGHT_BRACKET;
import com.fable.insightview.platform.util.Constants.SQL_TYPE;

@Service
public class DataSecurityServiceImpl implements DataSecurityService {
	@Autowired
	private DataObjectBeanMapper dataObjectBeanMapper;

	@Autowired
	private DataObjectFieldLabelBeanMapper dataObjectFieldLabelBeanMapper;

	@Autowired
	private DataSecurityBeanMapper dataSecurityBeanMapper;

	@Autowired
	private DataSecurityItemBeanMapper dataSecurityItemBeanMapper;

	@Autowired
	private SqlSessionFactoryBean sessionFactoryBean;

//	@Autowired
//	private RequestUtil requestUtil;

	@Override
	public void deleteDataObjects(String[] ids, String[] types) {
		int idsLen = ids == null ? 0 : ids.length, typesLen = types == null ? 0
				: types.length;
		if (idsLen == 0 || typesLen == 0 || idsLen != typesLen) {
			throw new BusinessException("00000014");
		}

		for (int i = 0; i < typesLen; i++) {
			if (!SQL_TYPE.SQL.equals(types[i])) {
				throw new BusinessException("00600001");
			}
		}
		dataObjectBeanMapper.deleteDataObjects(Tool.removeQuotes(ids));
	}

	@Override
	public String createDataObject(DataObjectBean dataObject) {
		if (dataObject == null) {
			throw new BusinessException("00000014");
		}
        // 校验对象名称是否唯一、不重复
        validateObjectNameUnique(dataObject);
		List<DataObjectFieldLabelBean> fieldLabels = dataObject
				.getFieldLabels();
		if (fieldLabels == null || fieldLabels.isEmpty()) {
			throw new BusinessException("00000014");
		}
		//requestUtil.boToUserbo(dataObject);
		String dataObjectId = Cast.guid2Str(Tool.newGuid());
		dataObject.setId(dataObjectId);
		for (DataObjectFieldLabelBean fieldLabel : fieldLabels) {
			// fieldLabel.setId(Cast.guid2Str(Tool.newGuid()));
			fieldLabel.setId(Cast.genUuidStr());
			fieldLabel.setDataobjectId(dataObjectId);
		}
		dataObjectBeanMapper.createDataObject(dataObject);
		return dataObjectId;
	}

    private void validateObjectNameUnique(DataObjectBean dataObject) {
        DataObjectBean dataObjectDB = dataObjectBeanMapper.selectByName(dataObject.getObjectName());
        if (dataObjectDB != null) {
            String id = dataObject.getId();
            if (StringUtil.isEmpty(id)) {// 新增
                // 对象名称已存在，请重新命名！
                throw new BusinessException("00600003");
            } else {// 修改
                if (!dataObjectDB.getId().equals(id)) {
                    // 对象名称已存在，请重新命名！
                    throw new BusinessException("00600003");
                }
            }
        }
    }

    @Override
	public void updateDataObject(DataObjectBean dataObject) {
		if (dataObject == null) {
			throw new BusinessException("00000014");
		}
        // 校验对象名称是否唯一、不重复
        validateObjectNameUnique(dataObject);
		List<DataObjectFieldLabelBean> fieldLabels = dataObject
				.getFieldLabels();
		if (fieldLabels == null || fieldLabels.isEmpty()) {
			throw new BusinessException("00000014");
		}
		//requestUtil.boToUserbo(dataObject);
		String dataObjectId = dataObject.getId();
		if (fieldLabels != null && !fieldLabels.isEmpty()) {
			for (DataObjectFieldLabelBean fieldLabel : fieldLabels) {
				fieldLabel.setId(Cast.guid2Str(Tool.newGuid()));
				fieldLabel.setDataobjectId(dataObjectId);
				//requestUtil.boToUserbo(fieldLabel);
			}
		}
		dataObjectBeanMapper.updateDataObject(dataObject);
	}

	public DataBean<DataObjectFieldLabelBean> queryFieldLabelsByDynamicSql(
			String sql) {
		if (StringUtils.isEmpty(sql)) {
			throw new BusinessException("00000014");
		}
		sql = DynamicSqlManager.replaceMacros(sql,true);//替换宏变量
		sql = DynamicSqlManager.replaceVariableParams(new HashMap<String, String>(), sql , true);//替换可变参数，忽略其中的错误
		sql = "SELECT * FROM (" + sql + ") t WHERE 0=1";
		DataBean<DataObjectFieldLabelBean> result = new DataBean<DataObjectFieldLabelBean>();
		List<DataObjectFieldLabelBean> list = new ArrayList<DataObjectFieldLabelBean>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			SqlSession session = sessionFactoryBean.getObject().openSession();
			ps = session.getConnection().prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			DataObjectFieldLabelBean fieldLabel = null;
			for (int i = 1, len = metaData.getColumnCount(); i <= len; i++) {
				fieldLabel = new DataObjectFieldLabelBean();
				fieldLabel.setPropertyName(metaData.getColumnName(i));
				list.add(fieldLabel);
			}
			result.setData(list);
		} catch (Exception e) {
			throw new BusinessException("00600002");
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
			}

			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
			}
		}

		return result;
	}

	@Override
	public DataBean<DataObjectFieldLabelBean> queryFieldLabelsByDataObjectId(
			String dataObjectId) {
		if (StringUtils.isEmpty(dataObjectId)) {
			throw new BusinessException("00000014");
		}
		DataBean<DataObjectFieldLabelBean> result = new DataBean<DataObjectFieldLabelBean>();
		List<DataObjectFieldLabelBean> list = dataObjectFieldLabelBeanMapper
				.selectListByDataObjectId(dataObjectId);
		result.setData(list);
		return result;
	}

	public List<RoleWithDataSecurityBean> queryRoleWithDataSecuritys(
			String dataObjectId) {
		if (StringUtil.isEmpty(dataObjectId)) {
			throw new BusinessException("00000014");
		}
		DataObjectBean dataObject = new DataObjectBean();
		// dataObject.setSysId(String.valueOf(sysId));
		dataObject.setId(dataObjectId);
		List<RoleWithDataSecurityBean> roles = dataSecurityBeanMapper
				.queryRoleTree(dataObject);
		if (roles != null && !roles.isEmpty()) {
			String securityId = null;
			List<String> securityIds = new ArrayList<String>();
			for (RoleWithDataSecurityBean role : roles) {
				securityId = role.getDataSecurityId();
				if (StringUtil.isNotEmpty(securityId)) {
					securityIds.add(securityId);
				}
			}
			if (securityIds != null && !securityIds.isEmpty()) {
				List<DataSecurityItemBean> dsItems = dataSecurityItemBeanMapper
						.queryDataSecurityItems(securityIds);
				if (dsItems != null && !dsItems.isEmpty()) {
					for (DataSecurityItemBean dsItem : dsItems) {
						securityId = dsItem.getSecurityId();
						for (RoleWithDataSecurityBean role : roles) {
							if (securityId.equals(role.getDataSecurityId())) {
								role.getDsItems().add(dsItem);
								break;
							}
						}
					}
				}
			}
		}
		return roles;
	}

	public void updateDataSecuritys(String dataObjectId,
			List<DataSecurityBean> dataSecuritys) {
		// dataSecuritys可为空，为空则删除该数据对象下的所有数据权限
		if (StringUtil.isEmpty(dataObjectId)) {
			throw new BusinessException("00000014");
		}
		String dsId = null, dsItemId = null;
		List<DataSecurityItemBean> dsItems = new ArrayList<DataSecurityItemBean>();
		for (DataSecurityBean ds : dataSecuritys) {
			dsId = Cast.guid2Str(Tool.newGuid());
			ds.setId(dsId);
			//requestUtil.boToUserbo(ds);
			for (DataSecurityItemBean item : ds.getDsItems()) {
				dsItemId = Cast.guid2Str(Tool.newGuid());
				item.setId(dsItemId);
				item.setSecurityId(dsId);
				//requestUtil.boToUserbo(item);
				dsItems.add(item);
			}
		}
		Map<String, Object> map = new HashMap<String, Object>(3);
		map.put("dataObjectId", dataObjectId);
		map.put("dataSecuritys", dataSecuritys);
		map.put("dsItems", dsItems);
		dataSecurityBeanMapper.updateDataSecuritys(map);
	}

	@Override
	public JSONObject queryComboboxValues() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("leftBrackets", initLeftBrackets());
		jsonObj.put("operators", initOperators());
		jsonObj.put("rightBrackets", initRightBrackets());
		jsonObj.put("logicSymbols", initLogicSymbols());
		return jsonObj;
	}

	// 初始化左括号下拉框值
	private JSONArray initLeftBrackets() {
		JSONArray jsonAry = new JSONArray();
		initJsonAry(LEFT_BRACKET.EMPTY, "无", jsonAry);
		initJsonAry(LEFT_BRACKET.LEFT_ONE, LEFT_BRACKET.LEFT_ONE, jsonAry);
		initJsonAry(LEFT_BRACKET.LEFT_TWO, LEFT_BRACKET.LEFT_TWO, jsonAry);
		initJsonAry(LEFT_BRACKET.LEFT_THREE, LEFT_BRACKET.LEFT_THREE, jsonAry);
		return jsonAry;
	}

	private void initJsonAry(String value, String text, JSONArray jsonAry) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("value", value);
		jsonObj.put("text", text);
		jsonAry.add(jsonObj);
	}

	// 初始化比较符下拉框值
	private JSONArray initOperators() {
		JSONArray jsonAry = new JSONArray();
		initJsonAry(OPERATOR.ALLLIKE, "匹配", jsonAry);
		initJsonAry(OPERATOR.LEFTLIKE, "左匹配", jsonAry);
		initJsonAry(OPERATOR.RIGHTLIKE, "右匹配", jsonAry);
		initJsonAry(OPERATOR.EQUAL, "等于", jsonAry);
		initJsonAry(OPERATOR.NOTEQUAL, "不等于", jsonAry);
		initJsonAry(OPERATOR.GREATER, "大于", jsonAry);
		initJsonAry(OPERATOR.LESS, "小于", jsonAry);
		initJsonAry(OPERATOR.GREATEREQUAL, "大于等于", jsonAry);
		initJsonAry(OPERATOR.LESSEQUAL, "小于等于", jsonAry);
		initJsonAry(OPERATOR.IN, "包含", jsonAry);
		initJsonAry(OPERATOR.NOTIN, "不包含", jsonAry);
		return jsonAry;
	}

	// 初始化右括号下拉框值
	private JSONArray initRightBrackets() {
		JSONArray jsonAry = new JSONArray();
		initJsonAry(RIGHT_BRACKET.EMPTY, "无", jsonAry);
		initJsonAry(RIGHT_BRACKET.RIGHT_ONE, RIGHT_BRACKET.RIGHT_ONE, jsonAry);
		initJsonAry(RIGHT_BRACKET.RIGHT_TWO, RIGHT_BRACKET.RIGHT_TWO, jsonAry);
		initJsonAry(RIGHT_BRACKET.RIGHT_THREE, RIGHT_BRACKET.RIGHT_THREE,
				jsonAry);
		return jsonAry;
	}

	// 初始化逻辑符下拉框值
	private JSONArray initLogicSymbols() {
		JSONArray jsonAry = new JSONArray();
		initJsonAry(LOGIC_SYMBOL.AND, "并且", jsonAry);
		initJsonAry(LOGIC_SYMBOL.OR, "或者", jsonAry);
		return jsonAry;
	}

    @Override
    public void updateDataObjectsWithProcdure() {
        dataObjectBeanMapper.updateDataObjectsWithProcdure();
    }
}
