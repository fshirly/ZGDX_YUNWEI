package com.fable.insightview.monitor.database.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.monitor.database.entity.MODBMSServerBean;
import com.fable.insightview.monitor.database.entity.MOOracleDataFileBean;
import com.fable.insightview.monitor.database.entity.MOOracleRollSEGBean;
import com.fable.insightview.monitor.database.entity.MOOracleSGABean;
import com.fable.insightview.monitor.database.entity.MOOracleTBSBean;
import com.fable.insightview.monitor.database.entity.OracleDbInfoBean;
import com.fable.insightview.monitor.database.service.IOracleService;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;

@Controller
@RequestMapping("/monitor/orclDbManage")
public class OracleDBController {
	@Autowired
	IOracleService orclService;

	private final Logger logger = LoggerFactory.getLogger(OracleDBController.class);
	
	/**
	 * 加载数据文件列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/toOrclDbInfoList")
	public ModelAndView toOrclDbInfoList(HttpServletRequest request) {
		String flag = request.getParameter("flag");
		request.setAttribute("flag", flag);
		request.setAttribute("navigationBar", request.getParameter("navigationBar"));
		return new ModelAndView("monitor/device/oracleDb_list");
	}

	/**
	 * 加载数据文件列表页面数据
	 * 
	 * @param taskBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listOrclDbInfos")
	@ResponseBody
	public Map<String, Object> listOrclDbInfos() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
		.getFlexiGridPageInfo(request);
		Page<OracleDbInfoBean> page = new Page<OracleDbInfoBean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dbName", request.getParameter("dbName"));
		paramMap.put("ip", request.getParameter("ip"));
		page.setParams(paramMap);
		List<OracleDbInfoBean> dataFileLst = orclService.getOracleDB(page);
		// 查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", dataFileLst);
		return result;
	}

	/**
	 * 加载数据文件列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/toOrclDataFileList")
	public ModelAndView toOrclDataFileList(HttpServletRequest request) {
		String flag = request.getParameter("flag");
		request.setAttribute("flag", flag);
		String instanceMOID = request.getParameter("instanceMOID");
		request.setAttribute("instanceMOID", instanceMOID);
		request.setAttribute("navigationBar", request.getParameter("navigationBar"));
		return new ModelAndView("monitor/device/oracleDataFile_list");
	}

	/**
	 * 加载数据文件列表页面数据
	 * 
	 * @param taskBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listOrclDataFile")
	@ResponseBody
	public Map<String, Object> listOrclDataFile() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
		.getFlexiGridPageInfo(request);
		Page<MOOracleDataFileBean> page = new Page<MOOracleDataFileBean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dbName", request.getParameter("dbName"));
		paramMap.put("ip", request.getParameter("ip"));
		paramMap.put("dataFileName", request.getParameter("dataFileName"));
		paramMap.put("instanceMOID", request.getParameter("instanceMOID"));
		page.setParams(paramMap);
		List<MOOracleDataFileBean> dataFileLst = orclService
				.getAllOracleDataFiles(page);
		// 查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", dataFileLst);
		return result;
	}

	
	/**
	 * 加载表空间列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/toShowOrclTbsInfo")
	public ModelAndView toShowOrclTbsInfo(HttpServletRequest request) {
		String flag = request.getParameter("flag");
		request.setAttribute("flag", flag);
		String instanceMOID = request.getParameter("instanceMOID");
		request.setAttribute("instanceMOID", instanceMOID);
		request.setAttribute("navigationBar", request.getParameter("navigationBar"));
		return new ModelAndView("monitor/device/orclTbsInfo_list");
	}

	/**
	 * 加载表空间列表页面数据
	 * 
	 * @param taskBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getOrclTbsInfo")
	@ResponseBody
	public Map<String, Object> getOrclTbsInfo() throws Exception {
		logger.info("开始...加载表空间信息列表");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
		.getFlexiGridPageInfo(request);
		Page<MOOracleTBSBean> page = new Page<MOOracleTBSBean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dbName", request.getParameter("dbName"));
		paramMap.put("ip", request.getParameter("ip"));
		paramMap.put("tbsName", request.getParameter("tbsName"));
		paramMap.put("instanceMOID", request.getParameter("instanceMOID"));
		page.setParams(paramMap);
		List<MOOracleTBSBean> moLsinfo = orclService.getAllOrclTbsInfo(page);
		// 查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", moLsinfo);
		logger.info("结束...加载表空间信息列表 " + moLsinfo);
		return result;
	}
	
	
	/**
	 * 加载回滚段监测对象列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/toOrclRollSEGList")
	public ModelAndView toOrclRollSEGList(HttpServletRequest request,
			ModelMap map) {
		String flag = request.getParameter("flag");
		request.setAttribute("flag", flag);
		String instanceMOID = request.getParameter("instanceMOID");
		request.setAttribute("instanceMOID", instanceMOID);
		request.setAttribute("navigationBar", request.getParameter("navigationBar"));
		return new ModelAndView("monitor/device/orclRollSEG_list");
	}

	/**
	 * 加载回滚段监测对象列表页面数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listOrclRollSEG")
	@ResponseBody
	public Map<String, Object> listOrclRollSEG() throws Exception {
		logger.info("开始...加载回滚段监测对象列表");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
		.getFlexiGridPageInfo(request);
		Page<MOOracleRollSEGBean> page = new Page<MOOracleRollSEGBean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dbName", request.getParameter("dbName"));
		paramMap.put("ip", request.getParameter("ip"));
		paramMap.put("segName", request.getParameter("segName"));
		paramMap.put("instanceMOID", request.getParameter("instanceMOID"));
		page.setParams(paramMap);
		Map params = new HashMap();
		List<MOOracleRollSEGBean> rollSEGLst = orclService.getAllOrclRollSEGList(page);

		// 查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", rollSEGLst);
		logger.info("结束...加载回滚段监测对象列表 " + rollSEGLst);
		return result;
	}
	
	/**
	 * 加载数据库实例列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/toOrclInstanceList")
	public ModelAndView toOrclInstanceList(HttpServletRequest request,
			ModelMap map) {
		String flag = request.getParameter("flag");
		String dbmsMoId = request.getParameter("dbmsMoId");
		request.setAttribute("flag", flag);
		request.setAttribute("dbmsMoId", dbmsMoId);
		request.setAttribute("navigationBar", request.getParameter("navigationBar"));
		return new ModelAndView("monitor/device/orclInstance_list");
	}

	/**
	 * 加载数据库实例列表页面数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listOrclInstance")
	@ResponseBody
	public Map<String, Object> listOrclInstance() throws Exception {
		logger.info("开始...加载回滚段监测对象列表");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
		.getFlexiGridPageInfo(request);
		Page<MODBMSServerBean> page = new Page<MODBMSServerBean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(!"".equals(request.getParameter("dbmsMoId")) && request.getParameter("dbmsMoId") != null){
			paramMap.put("dbmsMoId", request.getParameter("dbmsMoId"));
		}
		paramMap.put("ip", request.getParameter("ip"));
		paramMap.put("instanceName", request.getParameter("instanceName"));
		page.setParams(paramMap);
		Map params = new HashMap();
		List<MODBMSServerBean> orclInsList = orclService.getOrclInstanceList(page);

		// 查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", orclInsList);
		logger.info("结束...加载回滚段监测对象列表 " + orclInsList);
		return result;
	}
	
	/**
	 * 加载SGA列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/toOrclSgaList")
	public ModelAndView toOrclSgaList(HttpServletRequest request,
			ModelMap map) {
		String flag = request.getParameter("flag");
		request.setAttribute("flag", flag);
		String instanceMOID = request.getParameter("instanceMOID");
		request.setAttribute("instanceMOID", instanceMOID);
		request.setAttribute("navigationBar", request.getParameter("navigationBar"));
		return new ModelAndView("monitor/device/orclSga_list");
	}

	/**
	 * 加载SGA列表页面数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listOrclSga")
	@ResponseBody
	public Map<String, Object> listOrclSga() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
		.getFlexiGridPageInfo(request);
		Page<MOOracleSGABean> page = new Page<MOOracleSGABean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ip", request.getParameter("ip"));
		paramMap.put("instanceName", request.getParameter("instanceName"));
		paramMap.put("instanceMOID", request.getParameter("instanceMOID"));
		page.setParams(paramMap);
		Map params = new HashMap();
		List<MOOracleSGABean> orclInsList = orclService.getOrclSGAList(page);

		// 查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", orclInsList);
		return result;
	}
}
