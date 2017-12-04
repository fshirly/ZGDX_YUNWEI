package com.fable.insightview.platform.listview.service;

import java.util.HashMap;

import com.fable.insightview.platform.common.entity.DataBean;
import com.fable.insightview.platform.common.entity.ExcelExportConditionBean;
import com.fable.insightview.platform.common.entity.TableDataBean;
import com.fable.insightview.platform.common.entity.TableQueryConditionBean;
import com.fable.insightview.platform.listview.entity.ListviewConfigBean;
import com.fable.insightview.platform.listview.entity.ListviewDataBean;
import com.fable.insightview.platform.listview.entity.ListviewPreviewQueryConditionBean;
import com.fable.insightview.platform.listview.entity.ListviewRequestParamsBean;
import com.fable.insightview.platform.util.exception.BusinessException;

public interface ListviewQueryService {

	/**
	 * 根据名字查询并封装listviewConfig对象
	 * 
	 * @param ListviewRequestParamsBean
	 *            listviewRequestParamsBean
	 * @return
	 * @throws BusinessException
	 */
	ListviewConfigBean selectConfig(
			ListviewRequestParamsBean listviewRequestParamsBean)
			throws BusinessException;

	/**
	 * 根据listviewName、条件、分页信息等查询table数据
	 * 
	 * @param tableConditionBean
	 * @return
	 */
	TableDataBean<HashMap<String, Object>> selectList(
			TableQueryConditionBean tableConditionBean) throws BusinessException;

	/**
	 * 预览
	 * 
	 * @param listviewDataBean
	 * @return
	 */
	TableDataBean<HashMap<String, Object>> selectPreviewList(
			ListviewPreviewQueryConditionBean listviewPreviewQueryConditionBean) throws BusinessException;

	/**
	 * 生成excel临时文件
	 * 
	 * @param excelExportConditionBean
	 * @return
	 */
	String createTempExcel(ExcelExportConditionBean excelExportConditionBean);

	/**
	 * 根据页面填写的listview配置信息组装ListviewConfigBean
	 * @param listviewDataBean
	 * @return
	 */
	ListviewConfigBean selectPreviewConfig(ListviewDataBean listviewDataBean);

	/**
	 * 根据previewData分析可执行的SQL语句中是否带有可变参数
	 * @param listviewPreviewQueryConditionBean
	 * @return
	 */
	DataBean<String> selectVariableParameter(ListviewDataBean listviewDataBean);

}
