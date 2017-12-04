package com.fable.insightview.monitor.database.controller;

import java.util.ArrayList;
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
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.monitor.database.entity.MODBMSServerBean;
import com.fable.insightview.monitor.database.entity.MOMySQLDBBean;
import com.fable.insightview.monitor.database.entity.MOMySQLDBServerBean;
import com.fable.insightview.monitor.database.entity.MOMySQLRunObjectsBean;
import com.fable.insightview.monitor.database.entity.MOMySQLVarsBean;
import com.fable.insightview.monitor.database.service.IMyService;
import com.fable.insightview.monitor.database.service.IOracleService;
import com.fable.insightview.monitor.host.entity.HostComm;
import com.fable.insightview.monitor.host.entity.KPINameDef;
import com.fable.insightview.platform.dict.util.DictionaryLoader;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.page.Page;

/**
 * @Description: 监测对象数据库
 * @Date 2014-11-5
 */
@Controller
@RequestMapping("/monitor/dbObjMgr")
public class DBObjController {
	@Autowired
	IOracleService orclService;
	@Autowired
	IMyService myService;

	private final static Logger logger = LoggerFactory.getLogger(DBObjController.class);
	
	/**
	 * 数据库页面跳转
	 */
	@RequestMapping("/toDataBaseList")
	public ModelAndView toDataBaseList(HttpServletRequest request,
			MODBMSServerBean vo, ModelMap map) throws Exception {
		map.put("dbmstype", vo.getDbmstype());
		map.put("flag", request.getParameter("flag"));// 隐藏字段标志
		map.put("id", request.getParameter("id"));
		map.put("navigationBar",request.getParameter("navigationBar"));
		return new ModelAndView("monitor/device/database_list");
	}

	/**
	 * 获取数据库页面显示数据
	 * @param vo
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/listDataBase")
	@ResponseBody
	public Map<String, Object> listDataBase(MODBMSServerBean vo,
			HttpServletRequest request) {
		logger.info("开始...获取页面显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MODBMSServerBean> page = new Page<MODBMSServerBean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String dbmstype = request.getParameter("dbmstype");
		String dbName = request.getParameter("dbName");
		// 约定为全部小写，如sybase/oracle/db2/mysql/mssql/sybase
		if (dbmstype.equals("14")) {
			dbmstype = null;
		} else if (dbmstype.equals("15")) {
			dbmstype = "oracle";
		} else if (dbmstype.equals("16")) {
			dbmstype = "mysql";
		} else if (dbmstype.equals("54")) {
			dbmstype = "db2";
		}else if (dbmstype.equals("86")) {
			dbmstype = "mssql";
		}else if (dbmstype.equals("81")) {
			dbmstype = "sybase";
		}
		paramMap.put("dbmstype", dbmstype);
		paramMap.put("ip", vo.getIp());
		paramMap.put("dbName", dbName);
		
		paramMap.put("jobOracleAvailable", KPINameDef.JOBORACLEAVAILABLE);
		paramMap.put("jobMysqlAvailable", KPINameDef.JOBMYSQLAVAILABLE);
		paramMap.put("jobDB2Available", KPINameDef.JOBDB2AVAILABLE);
		paramMap.put("jobMssqlAvailable", KPINameDef.JOBMSSQLSERVERAVAILABLE);
		paramMap.put("jobSybaseAvailable", KPINameDef.JOBSYBASESERVERAVAILABLE);
		page.setParams(paramMap);
		// 查询分页数据
		List<MODBMSServerBean> list = orclService.queryList(page);
		// 查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", list);
		logger.info("结束...获取页面显示数据");
		return result;
	}
	
	/**
	 * mysql(系统变量)页面跳转
	 */
	@RequestMapping("/toMysqlSysVar")
	public ModelAndView toMysqlSysVar(HttpServletRequest request, ModelMap map)
			throws Exception {
		map.put("flag", request.getParameter("flag"));// 隐藏字段标志
		map.put("sqlServerMOID", request.getParameter("sqlServerMOID"));
		map.put("navigationBar",request.getParameter("navigationBar"));
		return new ModelAndView("monitor/device/mysql_sysvar");
	}

	/**
	 * mysql(系统变量)页面显示数据
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/listMysqlSysVar")
	@ResponseBody
	public Map<String, Object> listMysqlSysVar(HttpServletRequest request) {
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MOMySQLVarsBean> page = new Page<MOMySQLVarsBean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String varName = request.getParameter("varName");
		String txtValue = request.getParameter("txtValue");
		paramMap.put(varName, txtValue);
		paramMap.put("sqlServerMOID", request.getParameter("sqlServerMOID"));
		page.setParams(paramMap);
		List<MOMySQLVarsBean> list = new ArrayList<MOMySQLVarsBean>();
		
		String opera = request.getParameter("opera");
		if ("%".equals(opera) || "".equals(opera) || opera == null) {
			list = myService.queryMOMySQLVarsByLike(page);
		} else if ("=".equals(opera)) {
			list = myService.queryMOMySQLVarsByEq(page);
		}
		
		int total = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", list);
		return result;
	}

	/**
	 * mysql(数据库)页面跳转
	 */
	@RequestMapping("/toMysqlDB")
	public ModelAndView toMysqlDB(HttpServletRequest request, ModelMap map)
			throws Exception {
		map.put("flag", request.getParameter("flag"));// 隐藏字段标志
		map.put("sqlServerMOID", request.getParameter("sqlServerMOID"));
		map.put("navigationBar",request.getParameter("navigationBar"));
		return new ModelAndView("monitor/device/mysql_db");
	}

	/**
	 * mysql(数据库)页面显示数据
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/listMysqlDB")
	@ResponseBody
	public Map<String, Object> listMysqlDB(MOMySQLDBBean vo,
			HttpServletRequest request) {
		logger.info("开始...获取页面显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MOMySQLDBBean> page = new Page<MOMySQLDBBean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("serverName", vo.getServerName());
		paramMap.put("dbName", vo.getDbName());
		paramMap.put("sqlServerMOID",request.getParameter("sqlServerMOID"));
		page.setParams(paramMap);
		// 查询分页数据
		List<MOMySQLDBBean> list = myService.queryMOMySQLDB(page);
		// 查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", list);
		logger.info("结束...获取页面显示数据");
		return result;
	}

	/**
	 * mysql(Server)页面跳转
	 */
	@RequestMapping("/toMysqlServer")
	public ModelAndView toMysqlServer(HttpServletRequest request, ModelMap map)
			throws Exception {
		map.put("flag", request.getParameter("flag"));// 隐藏字段标志
		map.put("dbmsMoId", request.getParameter("dbmsMoId"));
		map.put("navigationBar",request.getParameter("navigationBar"));
		return new ModelAndView("monitor/device/mysql_server");
	}

	/**
	 * mysql(数据库)页面显示数据
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/listMysqlServer")
	@ResponseBody
	public Map<String, Object> listMysqlServer(MOMySQLDBServerBean vo,
			HttpServletRequest request) {
		logger.info("开始...获取页面显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MOMySQLDBServerBean> page = new Page<MOMySQLDBServerBean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(!"".equals(request.getParameter("dbmsMoId")) && request.getParameter("dbmsMoId") != null){
			paramMap.put("dbmsMoId", request.getParameter("dbmsMoId"));
		}
		paramMap.put("serverName", vo.getServerName());
		paramMap.put("IP", vo.getIp());
		page.setParams(paramMap);
		// 查询分页数据
		List<MOMySQLDBServerBean> list = myService.queryMOMySQLDBServer(page);
		// 查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", list);
		logger.info("结束...获取页面显示数据");
		return result;
	}

	/**
	 * mysql(运行对象)页面跳转
	 */
	@RequestMapping("/toMysqlRunObj")
	public ModelAndView toMysqlRunObj(HttpServletRequest request, ModelMap map)
			throws Exception {
		map.put("flag", request.getParameter("flag"));// 隐藏字段标志
		map.put("navigationBar",request.getParameter("navigationBar"));
		return new ModelAndView("monitor/device/mysql_runobj");
	}

	/**
	 * mysql(运行对象)页面显示数据
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/listMysqlRunObj")
	@ResponseBody
	public Map<String, Object> listMysqlRunObj(MOMySQLRunObjectsBean vo,
			HttpServletRequest request) {
		logger.info("开始...获取页面显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MOMySQLRunObjectsBean> page = new Page<MOMySQLRunObjectsBean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("serverName", vo.getServerName());
		paramMap.put("moName", vo.getMoName());
		paramMap.put("sqlServerMOID", request.getParameter("sqlServerMOID"));
		page.setParams(paramMap);
		// 查询分页数据
		List<MOMySQLRunObjectsBean> list = myService.queryMOMySQLRunObj(page);
		
		Map<Integer, String> roMap = DictionaryLoader.getConstantItems("RunObjType");// 类别
		for (MOMySQLRunObjectsBean mo : list) {
			mo.setTypeName(roMap.get(mo.getType()));
		}		
		// 查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", list);
		logger.info("结束...获取页面显示数据");
		return result;
	}

	@RequestMapping("/findOracleInfo")
	@ResponseBody
	public MODBMSServerBean findOracleInfo(MODBMSServerBean mo) {
		MODBMSServerBean moDbms = orclService.selectMoDbmsByPrimaryKey(mo
				.getMoid());
		return moDbms;
	}

}
