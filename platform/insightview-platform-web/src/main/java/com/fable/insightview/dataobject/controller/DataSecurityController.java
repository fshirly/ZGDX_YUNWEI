package com.fable.insightview.dataobject.controller;

import java.text.MessageFormat;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fable.insightview.platform.common.entity.DataBean;
import com.fable.insightview.platform.common.entity.MessageBean;
import com.fable.insightview.platform.common.util.Tool;
import com.fable.insightview.platform.dataobject.entity.DataObjectBean;
import com.fable.insightview.platform.dataobject.entity.DataObjectFieldLabelBean;
import com.fable.insightview.platform.dataobject.entity.DataSecurityBean;
import com.fable.insightview.platform.dataobject.entity.RoleWithDataSecurityBean;
import com.fable.insightview.platform.dataobject.service.DataSecurityService;

/**
 * 数据权限控制器
 * 
 * @author nimj
 * 
 */
@Controller
@RequestMapping("sys/dataSecurity/")
public class DataSecurityController {
	private static final String PAGE = "platform/system/dataSecurity/{0}";

	@Autowired
	private DataSecurityService dataSecurityService;

//	@Autowired
//	private DictionaryService dictionaryService;

	@RequestMapping(value = "dataObjects/list/html", method = RequestMethod.GET)
	//@ApiOperation(value = "数据对象管理页面", notes = "前往数据对象管理页面")
	public String toDataObjectListPage() {
		return MessageFormat.format(PAGE, "dataObjectList");
	}

	@RequestMapping(value = "dataObject/create/html", method = RequestMethod.GET)
	//@ApiOperation(value = "数据对象新增页面", notes = "前往数据对象新增页面")
	public String toDataObjectCreatePage() {
		return MessageFormat.format(PAGE, "dataObject");
	}

	@RequestMapping(value = "dataObject/update/html", method = RequestMethod.GET)
	//@ApiOperation(value = "数据对象更新页面", notes = "前往数据对象更新页面")
	public String toDataObjectUpdatePage() {
		return MessageFormat.format(PAGE, "dataObject");
	}

//	@RequestMapping(value = "dataSecurityItem/comparationValues", method = RequestMethod.GET)
//	@ApiOperation(value = "查询权限条件比较值", notes = "查询权限条件比较值")
//	public @ResponseBody
//	DataBean<TreeDictionaryBean> queryComparationValues() throws Exception {
//		DataBean<TreeDictionaryBean> data = new DataBean<TreeDictionaryBean>();
//		List<TreeDictionaryBean> dictionarys = dictionaryService
//				.getDictionaryDataByType("权限条件比较值");
//		data.setData(dictionarys);
//		return data;
//	}

	@RequestMapping(value = "dataObjects/[{ids}]", method = RequestMethod.DELETE)
	//@ApiOperation(value = "批量删除数据对象", notes = "批量删除数据对象")
	public @ResponseBody
	MessageBean deleteDataObjects(
			//@ApiParam(required = true, value = "数据对象ID数组") 
			@PathVariable("ids") String[] ids,
			//@ApiParam(required = true, value = "对应于数据对象ID的类型数组") 
			@RequestBody String[] types) {
		dataSecurityService.deleteDataObjects(ids, types);
		return Tool.getSuccMsgBean("删除成功");
	}

	@RequestMapping(value = "fieldLabels", method = RequestMethod.GET)
	//@ApiOperation(value = "根据SQL获取字段列表", notes = "根据SQL获取字段列表")
	public @ResponseBody
	DataBean<DataObjectFieldLabelBean> queryFieldLabelsByDynamicSql(
			//@ApiParam(required = true, value = "sql语句") 
			String sql) {
		return dataSecurityService.queryFieldLabelsByDynamicSql(sql);
	}

	@RequestMapping(value = "dataObject/{id}/fieldLabels", method = RequestMethod.GET)
	//@ApiOperation(value = "根据数据对象ID获取字段列表", notes = "根据数据对象ID获取字段列表")
	public @ResponseBody
	DataBean<DataObjectFieldLabelBean> queryFieldLabelsByDataObjectId(
			//@ApiParam(required = true, value = "数据对象ID") 
			@PathVariable("id") String dataObjectId) {
		return dataSecurityService.queryFieldLabelsByDataObjectId(dataObjectId);
	}

	@RequestMapping(value = "dataSecurityItem/comboboxValues", method = RequestMethod.GET)
	//@ApiOperation(value = "获取权限条件各下拉框选项值", notes = "获取权限条件各下拉框选项值")
	public @ResponseBody
	JSONObject queryComboboxValues() {
		return dataSecurityService.queryComboboxValues();
	}

	@RequestMapping(value = "dataObject", method = RequestMethod.POST)
	//@ApiOperation(value = "新增数据对象", notes = "新增数据对象")
	public @ResponseBody
	MessageBean createDataObject(
			//@ApiParam(required = true, value = "数据对象信息") 
			@RequestBody DataObjectBean dataObject) {
		dataSecurityService.createDataObject(dataObject);
		return Tool.getSuccMsgBean("新增成功");
	}

	@RequestMapping(value = "dataObject", method = RequestMethod.PUT)
	//@ApiOperation(value = "更新数据对象", notes = "更新数据对象")
	public @ResponseBody
	MessageBean updateDataObject(
			//@ApiParam(required = true, value = "数据对象信息") 
			@RequestBody DataObjectBean dataObject) {
		dataSecurityService.updateDataObject(dataObject);
		return Tool.getSuccMsgBean("保存成功");
	}

    @RequestMapping(value = "dataObjects/update", method = RequestMethod.POST)
    //@ApiOperation(value = "使用存储过程更新所有数据对象", notes = "使用存储过程更新所有数据对象")
    public @ResponseBody
    MessageBean updateDataObjectsWithProcdure() {
        dataSecurityService.updateDataObjectsWithProcdure();
        return Tool.getSuccMsgBean("所有数据对象已更新成功！");
    }

	@RequestMapping(value = "dataSecuritys/list/html", method = RequestMethod.GET)
	//@ApiOperation(value = "前往数据权限设置页面", notes = "前往数据权限设置页面")
	public String toDataSecurityPage() {
		return MessageFormat.format(PAGE, "dataSecurityList");
	}

	@RequestMapping(value = "dataObject/{id}/roleWithDataSecuritys", method = RequestMethod.GET)
	//@ApiOperation(value = "获取数据权限", notes = "获取数据权限")
	public @ResponseBody
	DataBean<RoleWithDataSecurityBean> queryRoleWithDataSecuritys(
			//@ApiParam(required = true, value = "数据对象ID") 
			@PathVariable("id") String dataObjectId) {
		DataBean<RoleWithDataSecurityBean> data = new DataBean<RoleWithDataSecurityBean>();
		data.setData(dataSecurityService
				.queryRoleWithDataSecuritys(dataObjectId));
		return data;
	}

	@RequestMapping(value = "dataObject/{id}/dataSecuritys", method = RequestMethod.PUT)
	//@ApiOperation(value = "更新数据对象的权限", notes = "更新数据对象的权限")
	public @ResponseBody
	MessageBean updateDataSecuritys(
			//@ApiParam(required = true, value = "数据对象ID") 
			@PathVariable("id") String dataObjectId,
			//@ApiParam(required = false, value = "数据权限") 
			@RequestBody List<DataSecurityBean> dataSecuritys) {
		dataSecurityService.updateDataSecuritys(dataObjectId, dataSecuritys);
		return Tool.getSuccMsgBean("保存成功");
	}
}
