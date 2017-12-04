package com.fable.insightview.listview.controller;

import java.io.File;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionFactoryBean;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.fable.insightview.platform.common.entity.DataBean;
import com.fable.insightview.platform.common.entity.ExcelExportConditionBean;
import com.fable.insightview.platform.common.entity.MessageBean;
import com.fable.insightview.platform.common.entity.TableDataBean;
import com.fable.insightview.platform.common.entity.TableQueryConditionBean;
import com.fable.insightview.platform.listview.entity.ListviewBean;
import com.fable.insightview.platform.listview.entity.ListviewConfigBean;
import com.fable.insightview.platform.listview.entity.ListviewDataBean;
import com.fable.insightview.platform.listview.entity.ListviewExportFileBean;
import com.fable.insightview.platform.listview.entity.ListviewPreviewQueryConditionBean;
import com.fable.insightview.platform.listview.entity.ListviewRequestParamsBean;
import com.fable.insightview.platform.listview.mapper.ListviewBeanMapper;
import com.fable.insightview.platform.listview.service.FileManagerService;
import com.fable.insightview.platform.listview.service.ListviewQueryService;

/**
 * @author zhouwei listviewQuery控制器处理类
 */
@Controller
@RequestMapping("tag/listviewQuery")
public class ListviewQueryController {

	@Autowired
	ListviewQueryService listviewQueryService;

	@Autowired
	ListviewBeanMapper listviewBeanMapper;

	@Autowired(required=false)
	FileManagerService fileManagerService;
	
	@Autowired
	SqlSessionFactoryBean sqlSessionFactoryBean;
	
	@Autowired
	DataSource dataSource;
	
	@RequestMapping(value = "/listview/previewEasyUi/html", method = RequestMethod.GET)
	//@ApiOperation(value = "跳转查询配置预览页面", httpMethod = "GET", response = String.class, notes = "跳转查询配置预览页面")
	public String forPreviewEasyUi() throws Exception {
		return "platform/tags/listviewPreviewEasyUi";
	}

	@RequestMapping(value = "/listview/previewOniUi/html", method = RequestMethod.GET)
	//@ApiOperation(value = "跳转查询配置预览页面", httpMethod = "GET", response = String.class, notes = "跳转查询配置预览页面")
	public String forPreviewOniUi() throws Exception {
		return "platform/tags/listviewPreviewOniUi";
	}

	@RequestMapping(value = "/previewConfig", method = RequestMethod.POST)
	//@ApiOperation(value = "查询预览配置", httpMethod = "POST", response = ListviewConfigBean.class, notes = "查询预览配置")
	public @ResponseBody
	ListviewConfigBean selectPreviewConfigByBean(
			//@ApiParam(required = true, name = "listviewDataBean", value = "listview数据")
			@RequestBody ListviewDataBean listviewDataBean)
			throws Exception {

		ListviewConfigBean config = listviewQueryService
				.selectPreviewConfig(listviewDataBean);

		return config;
	}

	@RequestMapping(value = "/previewAnalysisSql", method = RequestMethod.POST)
	//@ApiOperation(value = "预览前，分析SQL语句是否存在可变参数", httpMethod = "POST", response = DataBean.class, notes = "预览前，分析SQL语句是否存在可变参数")
	public @ResponseBody
	DataBean<String> selectColonParameter(
			//@ApiParam(required = true, name = "listviewDataBean", value = "listview数据") 
			@RequestBody ListviewDataBean listviewDataBean)
			throws Exception {

		DataBean<String> result = listviewQueryService
				.selectVariableParameter(listviewDataBean);

		return result;
	}

	@RequestMapping(value = "/previewForPage", method = RequestMethod.POST)
	//@ApiOperation(value = "预览", httpMethod = "POST", response = TableDataBean.class, notes = "预览")
	public @ResponseBody
	TableDataBean<HashMap<String, Object>> selectPreviewDatasForPage(
			//@ApiParam(required = true, name = "previewQuery", value = "listview预览查询参数") 
			@RequestBody ListviewPreviewQueryConditionBean previewQuery)
			throws Exception {

		TableDataBean<HashMap<String, Object>> tableData = listviewQueryService
				.selectPreviewList(previewQuery);

		return tableData;
	}

	@RequestMapping(value = "/listviewConfig", method = RequestMethod.POST)
	//@ApiOperation(value = "根据ListviewName等获取基本信息条件等", httpMethod = "POST", response = ListviewConfigBean.class, notes = "根据ListviewName等获取基本信息条件")
	public @ResponseBody
	ListviewConfigBean selectListviewConfigByName(
			//@ApiParam(required = true, name = "listviewRequestParamsBean", value = "listview请求参数") 
			@RequestBody ListviewRequestParamsBean listviewRequestParamsBean)
			throws Exception {

		ListviewConfigBean config = listviewQueryService
				.selectConfig(listviewRequestParamsBean);

		return config;
	}

	@RequestMapping(value = "/datasForPage", method = RequestMethod.POST)
	//@ApiOperation(value = "根据listviewId分页查询数据", httpMethod = "POST", response = TableDataBean.class, notes = "根据listviewId分页查询数据")
	public @ResponseBody
	TableDataBean<HashMap<String, Object>> selectDatasForPageByListview(
			//@ApiParam(required = true, name = "tableConditionBean", value = "查询条件") 
			@RequestBody TableQueryConditionBean tableQueryConditionBean)
			throws Exception {

		TableDataBean<HashMap<String, Object>> data = listviewQueryService.selectList(tableQueryConditionBean);

		return data;
	}

	@RequestMapping(value = "/excel", method = RequestMethod.POST)
	//@ApiOperation(value = "导出listview查询数据不分页", httpMethod = "POST", response = ListviewExportFileBean.class, notes = "导出listview查询数据不分页")
	public @ResponseBody
	ListviewExportFileBean excelData(
			//@ApiParam(required = true, name = "excelExportConditionBean", value = "excel导出条件") 
			@RequestBody ExcelExportConditionBean excelExportConditionBean)
			throws Exception {

		String path = listviewQueryService
				.createTempExcel(excelExportConditionBean);

		ListviewExportFileBean ExportFile = new ListviewExportFileBean();
		ExportFile.setFilePath(path);
		return ExportFile;
	}

	@RequestMapping(value = "/exportExcel/{filePath}", method = RequestMethod.GET)
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
					URLEncoder.encode("导出"+ ".xls", "UTF-8"));
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

	@RequestMapping(value = "/test", method = RequestMethod.POST)
	//@ApiOperation(value = "test", httpMethod = "POST", response = MessageBean.class, notes = "test")
	public @ResponseBody
	MessageBean test() throws Exception {

		MessageBean msg = new MessageBean();
		msg.setMessage("保存成功！");
		return msg;
	}

	@RequestMapping(value = "/test/html", method = RequestMethod.GET)
	//@ApiOperation(value = "跳转测试页面", httpMethod = "GET", response = String.class, notes = "跳转测试页面")
	public String testHtml() throws Exception {
		return "platform/tags/test";
	}

	@RequestMapping(value = "/testEasyUi/list/html", method = RequestMethod.GET)
	//@ApiOperation(value = "跳转测试页面", httpMethod = "GET", response = String.class, notes = "跳转测试页面")
	public String testEasyUi() throws Exception {
		return "platform/tags/testEasyUiListview";
	}

	@RequestMapping(value = "/testOniUi/list/html", method = RequestMethod.GET)
	//@ApiOperation(value = "跳转测试页面", httpMethod = "GET", response = String.class, notes = "跳转测试页面")
	public String testOniUi() throws Exception {
		return "platform/tags/testOniUiListview";
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	//@ApiOperation(value = "test", httpMethod = "GET", response = MessageBean.class, notes = "test")
	public @ResponseBody
	MessageBean testGet() throws Exception {
//		String sql = " select * from fbs_listview ";
//		Page<ListviewBean> page = pageQuery.queryListForPage(sql , 1, 10, ListviewBean.class, null);
//		List<ListviewBean> list = page.getData();
//		for(ListviewBean listview : list){
//			System.out.println(listview.getName());
//		}
		MessageBean msg = new MessageBean();
		msg.setMessage("保存成功！");
		return msg;
	}
	
	@RequestMapping(value = "/testDynamicSqlSource", method = RequestMethod.GET)
	//@ApiOperation(value = "testDynamicSqlSource", httpMethod = "GET", response = MessageBean.class, notes = "testDynamicSqlSource")
	public @ResponseBody
	MessageBean testDynamicSqlSource() throws Exception {
		
		final SqlSession sqlSession = sqlSessionFactoryBean.getObject().openSession();

		String originalSql = " select * from fbs_listview where name like '%'||#{name}||'%' and title  like '%'||#{title}||'%'  " ;
		
		SqlSource sqlSource = new XMLLanguageDriver().createSqlSource(sqlSession.getConfiguration(), originalSql, ListviewBean.class);
		
		ListviewBean bean = new ListviewBean();
		bean.setName("list");
		bean.setTitle("listview");
		
		if(!sqlSession.getConfiguration().hasStatement( SqlCommandType.SELECT.toString() + "."+ originalSql.hashCode())){
			MappedStatement ms = new MappedStatement.Builder(sqlSession.getConfiguration(), 
					SqlCommandType.SELECT.toString() + "."+ originalSql.hashCode(), sqlSource, SqlCommandType.SELECT).resultMaps(new ArrayList<ResultMap>(){
						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						{
							add(new ResultMap.Builder(sqlSession.getConfiguration(), "mapId", 
									ListviewBean.class, new ArrayList<ResultMapping>()).build());
						}
					}).build();
			
			sqlSession.getConfiguration().addMappedStatement(ms);
		}

		List<ListviewBean> list = sqlSession.selectList(SqlCommandType.SELECT.toString() + "."+ originalSql.hashCode(), bean);
		
		for(ListviewBean bean2 : list){
			System.out.println(bean2.getName() + "===" +  bean.getTitle());
		}
		
		MessageBean msg = new MessageBean();
		msg.setMessage("保存成功！");
		return msg;
	}

}
