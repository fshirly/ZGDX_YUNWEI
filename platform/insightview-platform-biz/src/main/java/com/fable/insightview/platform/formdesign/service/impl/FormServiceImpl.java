package com.fable.insightview.platform.formdesign.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.common.dynamicdb.api.IDynamicDB;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.formdesign.dao.IFormDao;
import com.fable.insightview.platform.formdesign.entity.FdForm;
import com.fable.insightview.platform.formdesign.service.IFormService;
import com.fable.insightview.platform.formdesign.vo.ProcessFormVO;
import com.fable.insightview.platform.itsm.core.service.GenericServiceImpl;

@Service("fromdesign.formService")
public class FormServiceImpl extends GenericServiceImpl<FdForm> implements
		IFormService {

	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private IFormDao formDao;

	@Resource(name = "platform.jdbcDB")
	private IDynamicDB dynamicDB;

	@Override
	public FdForm getByFormId(String formId) {
		return formDao.getByFormId(formId);
	}

	@Override
	public void addTable(String tableName, String id) {
		try {
			dynamicDB.createTable(tableName, id);
		} catch (Exception e) {
			logger.info("创建表Form_T_id失败：" + e.getMessage());
		}
	}

	@Override
	public void addColumn(String tableName, String columnName, String javaType,
			Integer length) {
		try {
			dynamicDB.addColumn(tableName, columnName, javaType, length);
		} catch (Exception e) {
			logger.info("表 " + tableName + " 添加字段 " + columnName + " 失败："
					+ e.getMessage());
		}
	}

	@Override
	public void removeColumn(String tableName, String columnName,
			boolean isLogic) {
		try {
			dynamicDB.removeColumn(tableName, columnName, isLogic);
		} catch (Exception e) {
			logger.info("删除表 " + tableName + " 的字段" + columnName + "失败："
					+ e.getMessage());
		}
	}

	/**
	 * 获取单个属性详情信息，参数为属性id
	 * 
	 * @param id
	 * @author Maowei
	 */
	@Override
	public Map<String, String> getFormAttrsToMap(int id, int formId) {
		List<Map<String, String>> allAttrsMap = this.getFormAttributes(formId);
		for (Map<String, String> map : allAttrsMap) {
			if (null == map) {
				continue;
			}
			if (String.valueOf(id).equals(map.get("id"))) {
				return map;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, String>> getFormAttributes(int formId) {
		FdForm form = formDao.getById(formId);
		String jsonAttrs = form.getFormAttributes();
		Map<String, Object> originalMap = JsonUtil.json2Map(jsonAttrs);
		List<Map<String, String>> allAttrsMap = (List<Map<String, String>>) originalMap
				.get("attributes");
		return allAttrsMap;
	}

	@Override
	public FdForm insertForm(FdForm form, ProcessFormVO processFormVO) {
		form.setFormName(processFormVO.getFormName());
		form.setFormLayout(processFormVO.getLayout());
		try {
			formDao.insert(form);
		} catch (Exception e) {
			logger.info("Fd_Form表新增记录失败：" + e.getMessage());
		}
		return form;
	}

	/**
	 * 保存表单实例信息
	 */
	@Override
	public int saveFormInstanceInfo(String id,String tableName,
			Map<String, String> attrsMap) {
		if (null != tableName) {
			return formDao.saveFormInstanceInfo(id,tableName, attrsMap);
		} else {
			logger.info("动态表参数值为空，保存失败！");
		}
		return 0;
	}

	@Override
	public Map<String, String> queryFormInstanceInfo(String tableName, int id) {
		return formDao.queryFormInstanceInfo(tableName, id);
	}

	@Override
	public void updateFormInstanceInfo(int id, String tableName,
			Map<String, String> formData) {
		formDao.updateFormInstanceInfo(id, tableName, formData);
	}

	@Override
	public List<Map<String, String>> valueInit(String initSQL) {
		return formDao.valueInit(initSQL);
	}

}
