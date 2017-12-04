package com.fable.insightview.listview.controller;

import java.io.File;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fable.insightview.platform.common.entity.DataBean;
import com.fable.insightview.platform.common.entity.MessageBean;
import com.fable.insightview.platform.common.entity.TableDataBean;
import com.fable.insightview.platform.common.entity.TableQueryConditionBean;
import com.fable.insightview.platform.common.util.GsonUtil;
import com.fable.insightview.platform.dataobject.entity.DataObjectBean;
import com.fable.insightview.platform.dataobject.entity.DataObjectFieldLabelBean;
import com.fable.insightview.platform.listview.entity.ListviewBean;
import com.fable.insightview.platform.listview.entity.ListviewDataBean;
import com.fable.insightview.platform.listview.entity.ListviewExportFileBean;
import com.fable.insightview.platform.listview.entity.ListviewFieldLabelBean;
import com.fable.insightview.platform.listview.mapper.ListviewBeanMapper;
import com.fable.insightview.platform.listview.service.FileManagerService;
import com.fable.insightview.platform.listview.service.ListviewManagerService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * @author zhouwei listviewManager控制器处理类
 */
@Controller
@RequestMapping("tag/listviewManager")
public class ListviewManagerController {

	/**
	 * 导出listview文件前缀
	 */
	public final static String EXPORT_FILE_PREFIX = "listview";

	/**
	 * 导出listview文件后缀
	 */
	public final static String EXPORT_FILE_SUFFIX = ".txt";

	/**
	 * 导出listview文件名
	 */
	public final static String EXPORT_FILE_NAME = EXPORT_FILE_PREFIX
			+ EXPORT_FILE_SUFFIX;

	@Autowired
	ListviewManagerService listviewManagerService;

	@Autowired
	ListviewBeanMapper listviewBeanMapper;

	@Autowired(required=false)
	FileManagerService fileManagerService;
	
	
	@RequestMapping(value = "/listview/list/html", method = RequestMethod.GET)
	//@ApiOperation(value = "Listview管理查询页面(使用listview配置出来)", httpMethod = "GET", response = String.class, notes = "Listview管理查询页面(使用listview配置出来)")
	public String testEasyUi() throws Exception {
		return "platform/tags/listviewList";
	}
	

	@RequestMapping(value = "/listview/listCustom/html", method = RequestMethod.GET)
	//@ApiOperation(value = "Listview管理查询页面(非使用listview配置出来)", httpMethod = "GET", response = String.class, notes = "Listview管理查询页面(非使用listview配置出来)")
	public String forListListViewData() throws Exception {
		return "platform/tags/listviewCustomList";
	}

	@RequestMapping(value = "/listviews", method = RequestMethod.POST)
	//@ApiOperation(value = "分页获取查询配置", httpMethod = "POST", response = TableDataBean.class, notes = "分页获取查询配置")
	public @ResponseBody
	TableDataBean<ListviewBean> selectListviewDatas(
			//@ApiParam(required = true, name = "tableCondition", value = "查询条件") 
			@RequestBody TableQueryConditionBean tableCondition)
			throws Exception {

		TableDataBean<ListviewBean> tableData = listviewManagerService
				.selectListviewBeansForPage(tableCondition);

		return tableData;
	}

	@RequestMapping(value = "/listview/create/html", method = RequestMethod.GET)
	//@ApiOperation(value = "跳转新增查询配置页面", httpMethod = "GET", response = String.class, notes = "跳转新增查询配置页面")
	public String forCreateListViewData() throws Exception {
		return "platform/tags/listviewManager";
	}

	@RequestMapping(value = "/listview", method = RequestMethod.POST)
	//@ApiOperation(value = "新增查询配置", httpMethod = "POST", response = MessageBean.class, notes = "新增查询配置")
	public @ResponseBody
	MessageBean createListViewData(
			//@ApiParam(required = true, name = "listviewDataBean", value = "查询配置") 
			@RequestBody ListviewDataBean listviewDataBean)
			throws Exception {
		
//		ListviewDataBean listviewDataBean = new ListviewDataBean();
//		listviewDataBean.setBasic(listviewDataVoBean.getBasic());
//		listviewDataBean.setCondition(Arrays.asList(listviewDataVoBean.getCondition()));
//		listviewDataBean.setCols(Arrays.asList(listviewDataVoBean.getCols()));
		
		listviewManagerService.insertListviewDataBean(listviewDataBean);

		MessageBean msg = new MessageBean();
		msg.setMessage("保存成功！");
		return msg;
	}

	// @RequestMapping(value = "/listview/targets/{targetType}", method =
	// RequestMethod.GET)
	// //@ApiOperation(value = "获取用户/角色下拉框数据", httpMethod = "GET", response =
	// SelectDataBean.class, notes = "获取用户/角色下拉框数据")
	// public @ResponseBody
	// SelectDataBean selectTargets(
	// @ApiParam(required = true, name = "targetType", value =
	// "用户/角色类型,1:用户 2：角色") @PathVariable("targetType") String targetType)
	// throws Exception {
	//
	// SelectDataBean result = listviewManagerService
	// .selectTargets(targetType);
	// return result;
	// }

	// @RequestMapping(value = "/listview/cols", method = RequestMethod.POST)
	// //@ApiOperation(value = "获取字段列表", httpMethod = "POST", response =
	// DataBean.class, notes = "获取字段列表")
	// public @ResponseBody
	// DataBean<ListviewFieldLabelBean> selectListviewFieldLabels(
	// @ApiParam(required = true, name = "sql", value = "SQL语句") @RequestBody
	// String sql)
	// throws Exception {
	//
	// DataBean<ListviewFieldLabelBean> dataBean = listviewManagerService
	// .selectFieldLabelsByDynamicSql(sql);
	//
	// return dataBean;
	// }

	@RequestMapping(value = "/dataObjects", method = RequestMethod.GET)
	//@ApiOperation(value = "获取数据对象列表", httpMethod = "GET", response = DataBean.class, notes = "获取数据对象列表")
	public @ResponseBody
	DataBean<DataObjectBean> selectDataObjects() throws Exception {

		DataBean<DataObjectBean> dataBean = listviewManagerService
				.selectDataObjects();

		return dataBean;
	}

	@RequestMapping(value = "/listview/camelCols/{dataObjectId}", method = RequestMethod.GET)
	//@ApiOperation(value = "通过dataObjectId获取字段列表", httpMethod = "GET", response = DataBean.class, notes = "通过dataObjectId获取字段列表")
	public @ResponseBody
	DataBean<ListviewFieldLabelBean> selectDataObjectFieldLabelsWithCamelByDataObjectId(
			//@ApiParam(required = true, name = "dataObjectId", value = "数据对象Id") 
			@PathVariable("dataObjectId") String dataObjectId)
			throws Exception {

		DataBean<ListviewFieldLabelBean> dataBean = listviewManagerService
				.selectFieldLabelsByDataObjectId(dataObjectId , true);

		return dataBean;
	}
	
	@RequestMapping(value = "/listview/cols/{dataObjectId}", method = RequestMethod.GET)
	//@ApiOperation(value = "通过dataObjectId获取字段列表", httpMethod = "GET", response = DataBean.class, notes = "通过dataObjectId获取字段列表")
	public @ResponseBody
	DataBean<ListviewFieldLabelBean> selectDataObjectFieldLabelsByDataObjectId(
			//@ApiParam(required = true, name = "dataObjectId", value = "数据对象Id") 
			@PathVariable("dataObjectId") String dataObjectId)
			throws Exception {

		DataBean<ListviewFieldLabelBean> dataBean = listviewManagerService
				.selectFieldLabelsByDataObjectId(dataObjectId , false);

		return dataBean;
	}

	@RequestMapping(value = "/listview/{id}", method = RequestMethod.GET)
	//@ApiOperation(value = "获取查询配置", httpMethod = "GET", response = ListviewDataBean.class, notes = "获取查询配置")
	public @ResponseBody
	ListviewDataBean selectListviewData(
			//@ApiParam(required = true, name = "id", value = "主键") 
			@PathVariable("id") String id)
			throws Exception {

		ListviewDataBean data = listviewManagerService
				.selectListviewDataBean(id);

		return data;
	}

	@RequestMapping(value = "/listview/update/html", method = RequestMethod.GET)
	//@ApiOperation(value = "跳转修改查询配置页面", httpMethod = "GET", response = String.class, notes = "跳转修改查询配置页面")
	public String forEditListViewData() throws Exception {
		return "platform/tags/listviewManager";
	}

	@RequestMapping(value = "/listview", method = RequestMethod.PUT)
	//@ApiOperation(value = "修改查询配置", httpMethod = "PUT", response = MessageBean.class, notes = "修改查询配置")
	public @ResponseBody
	MessageBean editListViewData(
			//@ApiParam(required = true, name = "listviewDataBean", value = "查询配置") 
			@RequestBody ListviewDataBean listviewDataBean)
			throws Exception {

		listviewManagerService.updateListviewDataBean(listviewDataBean);

		MessageBean msg = new MessageBean();
		msg.setMessage("保存成功！");
		return msg;
	}

	@RequestMapping(value = "/listview", method = RequestMethod.DELETE)
	//@ApiOperation(value = "删除查询配置", httpMethod = "DELETE", response = MessageBean.class, notes = "删除查询配置")
	public @ResponseBody
	MessageBean deleteListViewData(
			//@ApiParam(required = true, name = "ids", value = "主键数组") 
			@RequestBody String[] ids)
			throws Exception {

		listviewManagerService.deleteListviewDataBeans(ids);

		MessageBean msg = new MessageBean();
		msg.setMessage("删除成功！");
		return msg;
	}

	@RequestMapping(value = "/listview/import/html", method = RequestMethod.GET)
	//@ApiOperation(value = "跳转导入查询配置页面", httpMethod = "GET", response = String.class, notes = "跳转导入查询配置页面")
	public String forImportListViewData() throws Exception {
		return "platform/tags/listviewImport";
	}

	@RequestMapping(value = "/listviews/import", method = RequestMethod.POST)
	//@ApiOperation(value = "导入查询配置", httpMethod = "POST", response = MessageBean.class, notes = "导入查询配置")
	public @ResponseBody
	MessageBean importListViewData(@RequestParam("file") MultipartFile file)
			throws Exception {
		String json = new String(file.getBytes());
//		DataBean<ListviewDataObjectBean> dataBean = GsonUtil.fromJson(json,
//				new TypeToken<DataBean<ListviewDataObjectBean>>() {
//				}.getType());
		DataBean<ListviewDataBean> dataBean = GsonUtil.fromJson(json,
				new TypeToken<DataBean<ListviewDataBean>>() {
				}.getType());

		listviewManagerService.insertListviewDataBeans(dataBean);

		MessageBean msg = new MessageBean();
		msg.setMessage("导入成功！");
		return msg;
	}

	@RequestMapping(value = "/listviews/export", method = RequestMethod.POST)
	//@ApiOperation(value = "导出查询配置", httpMethod = "POST", response = MessageBean.class, notes = "导出查询配置")
	public @ResponseBody
	ListviewExportFileBean exportListViewDataForPath(
			//@ApiParam(required = true, name = "ids", value = "主键数组") 
			@RequestBody String[] ids)
			throws Exception {

//		DataBean<ListviewDataObjectBean> dataBean = listviewManagerService
//				.selectListviewDataObjectBeans(ids);
		
		DataBean<ListviewDataBean> dataBean = listviewManagerService
				.selectListviewDataBeans(ids);

		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.serializeNulls().setPrettyPrinting().create();
		String json = GsonUtil.toJson(dataBean,gson);

		File file = new File(EXPORT_FILE_PREFIX);
		FileUtils.writeByteArrayToFile(file, json.getBytes());

		String path = fileManagerService.uploadTempFile(file);

		ListviewExportFileBean ExportFile = new ListviewExportFileBean();

		ExportFile.setFilePath(path);

		return ExportFile;
	}

	@RequestMapping(value = "/listviews/exportByPath/{filePath}", method = RequestMethod.GET)
	//@ApiOperation(value = "根据路径导出文件,并删除", httpMethod = "GET", response = ResponseEntity.class, notes = "根据路径导出文件,并删除，不涉及数据库操作")
	public ResponseEntity<byte[]> exportFileThenDelete(
			//@ApiParam(required = true, name = "filePath", value = "文件路径") 
			@PathVariable("filePath") String filePath)
			throws Exception {

		HttpHeaders headers = new HttpHeaders();
		File file = null;
		try {
			filePath = URLDecoder.decode(filePath, "utf-8");
			file = fileManagerService.getFileByRelPath(filePath);

			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment",
					URLEncoder.encode(EXPORT_FILE_NAME, "UTF-8"));
			return new ResponseEntity<byte[]>(
					FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<byte[]>(null, headers,
					HttpStatus.NOT_FOUND);
		} finally {
			if (file != null) {
				FileUtils.deleteQuietly(file);
			}
		}

	}

}
