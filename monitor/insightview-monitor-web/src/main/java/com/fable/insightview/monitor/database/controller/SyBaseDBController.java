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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.monitor.database.entity.MOMySQLDBServerBean;
import com.fable.insightview.monitor.database.entity.MOSybaseDatabaseBean;
import com.fable.insightview.monitor.database.entity.MOSybaseDeviceBean;
import com.fable.insightview.monitor.database.entity.PerfSybaseDatabaseBean;
import com.fable.insightview.monitor.database.entity.SybaseServerKPINameDef;
import com.fable.insightview.monitor.database.service.ISyBaseService;
import com.fable.insightview.monitor.host.entity.HostComm;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.page.Page;

@Controller
@RequestMapping("/monitor/sybaseDbManage")
public class SyBaseDBController {
	@Autowired
	ISyBaseService sybaseService;

	private final Logger logger = LoggerFactory
			.getLogger(SyBaseDBController.class);

	/**
	 * 加载msServer列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/toSybaseServerList")
	@ResponseBody
	public ModelAndView toSybaseServerList(HttpServletRequest request,String navigationBar) {
		String flag = request.getParameter("flag");
		String dbmsMoId = request.getParameter("dbmsMoId");
		request.setAttribute("flag", flag);
		request.setAttribute("dbmsMoId", dbmsMoId);
		request.setAttribute("navigationBar", navigationBar);
		return new ModelAndView("monitor/device/sybaseServer_list");
	}

	/**
	 * 加载msServer列表页面
	 * 
	 * @param taskBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listSybaseServerInfos")
	@ResponseBody
	public Map<String, Object> listSybaseServerInfos() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
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
		paramMap.put("serverName", request.getParameter("serverName"));
		paramMap.put("IP", request.getParameter("ip"));
		page.setParams(paramMap);
		List<MOMySQLDBServerBean> serverLst = sybaseService
				.queryMOSybaseServer(page);
		// 查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", serverLst);
		return result;
	}

	/**
	 * 加载ms Device列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/toSybaseDeviceList")
	@ResponseBody
	public ModelAndView toSybaseDeviceList(HttpServletRequest request,String navigationBar) {
		String flag = request.getParameter("flag");
		request.setAttribute("flag", flag);
		String serverMoId = request.getParameter("serverMoId");
		request.setAttribute("serverMoId", serverMoId);
		request.setAttribute("navigationBar", navigationBar);
		return new ModelAndView("monitor/device/sybaseDevice_list");
	}

	/**
	 * 加载ms设备列表页面
	 * 
	 * @param taskBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listSybaseDeviceInfos")
	@ResponseBody
	public Map<String, Object> listSybaseDeviceInfos() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MOSybaseDeviceBean> page = new Page<MOSybaseDeviceBean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String serverMoId = request.getParameter("serverMoId");
		if(!"".equals(serverMoId) && serverMoId != null && !"null".equals(serverMoId)){
			paramMap.put("serverMoId", Integer.parseInt(serverMoId));
		}
		paramMap.put("deviceName", request.getParameter("deviceName"));
		paramMap.put("IP", request.getParameter("ip"));
		page.setParams(paramMap);
		List<MOSybaseDeviceBean> deviceLst = sybaseService
				.queryMOSybaseDevice(page);
		// 查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", deviceLst);
		return result;
	}

	/**
	 * 加载msDB列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/toSybaseDBList")
	@ResponseBody
	public ModelAndView toSybaseDBList(HttpServletRequest request,String navigationBar) {
		String flag = request.getParameter("flag");
		request.setAttribute("flag", flag);
		String serverMoId = request.getParameter("serverMoId");
		request.setAttribute("serverMoId", serverMoId);
		request.setAttribute("navigationBar", navigationBar);
		return new ModelAndView("monitor/device/sybaseDB_list");
	}

	/**
	 * 加载ms DB列表页面
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/listSybaseDBInfos")
	@ResponseBody
	public Map<String, Object> listSybaseDBInfos() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MOSybaseDatabaseBean> page = new Page<MOSybaseDatabaseBean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String serverMoId = request.getParameter("serverMoId");
		if(!"".equals(serverMoId) && serverMoId != null){
			paramMap.put("serverMoId", Integer.parseInt(serverMoId));
		}
		paramMap.put("databaseName", request.getParameter("databaseName"));
		paramMap.put("IP", request.getParameter("ip"));
		page.setParams(paramMap);
		List<MOSybaseDatabaseBean> dbLst = sybaseService
				.queryMOSybaseDatabase(page);
		// 查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", dbLst);
		return result;
	}

	/**
	 * 数据库空间使用情况 饼图
	 */
	@RequestMapping("/findDBFileSizePie")
	@ResponseBody
	public Map<String, Object> findDBFileSizePie(HttpServletRequest request,
			ModelMap map) {
		logger.info(" 数据库空间使用情况");
		int moID = Integer.parseInt(request.getParameter("moID"));
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> mapResult = new HashMap<String, Object>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		Map<String, Object> mapName = new HashMap<String, Object>();
		ArrayList<Object> array1 = new ArrayList<Object>();
		ArrayList<Object> array2 = new ArrayList<Object>();
		StringBuffer pieName = new StringBuffer();
		StringBuffer pieJson = new StringBuffer();
		params.put("moID", moID);
		String[] snapName = { "已用空间", "空闲空间" };
		String[] kpiName = { SybaseServerKPINameDef.USEDSIZE,
				SybaseServerKPINameDef.FREESIZE };

		for (int i = 0; i < snapName.length; i++) {
			pieName.append(",'" + snapName[i] + "'");
			params.put("kpiName", kpiName[i]);
			PerfSybaseDatabaseBean db = sybaseService
					.getDatabasefPerfValue(params);
			if (db != null) {
				pieJson.append(",{value:" + db.getPerfValue() + ",name:'"
						+ snapName[i] + "'}");// 拼接json数据
			} else {
				pieJson.append(",{value:'',name:'" + snapName[i] + "'}");// 拼接json数据
			}

		}
		pieName.deleteCharAt(0);
		if (pieJson.length() != 0) {
			pieJson.deleteCharAt(0);
		}

		array1.add(pieJson);
		array2.add(pieName);
		map2.put("sybaseDBSizePie", array1);
		mapName.put("pieName", array2);
		mapResult.put("data", map2);
		mapResult.put("dataName", mapName);
		return mapResult;
	}

	/**
	 * 数据库空间分配情况饼图
	 */
	@RequestMapping("/findDBFileSpacePie")
	@ResponseBody
	public Map<String, Object> findDBFileSpacePie(HttpServletRequest request,
			ModelMap map) {
		logger.info("数据库空间分配情况");
		int moID = Integer.parseInt(request.getParameter("moID"));
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> mapResult = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		Map<String,Object> mapName = new HashMap<String,Object>();
		ArrayList<Object> array1 = new ArrayList<Object>();
		ArrayList<Object> array2 = new ArrayList<Object>();
		params.put("moID", moID);
		String[] snapName = { "数据空间", "日志空间" };
		String[] kpiName = { SybaseServerKPINameDef.DATAFILESPACE,
				SybaseServerKPINameDef.LOGSPACE };

		for (int i = 0; i < snapName.length; i++) {
			params.put("kpiName", kpiName[i]);
			PerfSybaseDatabaseBean db = sybaseService
					.getDatabasefPerfValue(params);
			if (db != null) {
				String perfValue = HostComm.getKBytesToSize(db.getPerfValue());
				array1.add("{value:" + Double.parseDouble(perfValue) + ",name:'"
						+ snapName[i] + "KB'}");
				array2.add(snapName[i] + "KB");
			} else {
				array1.add("{value:'',name:'" + snapName[i] + "KB'}");
				array2.add(snapName[i] + "KB");
			}

		}
		map2.put("sybaseDBSpacePie", array1);
		mapName.put("pieName", array2);
		mapResult.put("data", map2);
		mapResult.put("dataName", mapName);
		return mapResult;

	}

	/**
	 * server DBFile 数据库页使用饼图
	 */
	@RequestMapping("/findPageSizePie")
	@ResponseBody
	public Map<String, Object> findPageSizePie(HttpServletRequest request,
			ModelMap map) {
		logger.info("DBFile 数据库页使用情况");
		int moID = Integer.parseInt(request.getParameter("moID"));
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> mapResult = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		Map<String,Object> mapName = new HashMap<String,Object>();
		ArrayList<Object> array1 = new ArrayList<Object>();
		ArrayList<Object> array2 = new ArrayList<Object>();
		params.put("moID", moID);
		String[] snapName = { "已用页数", "空闲页数" };
		String[] kpiName = { SybaseServerKPINameDef.USEDPAGE,
				SybaseServerKPINameDef.FREEPAGE };

		for (int i = 0; i < snapName.length; i++) {
			params.put("kpiName", kpiName[i]);
			PerfSybaseDatabaseBean db = sybaseService
					.getDatabasefPerfValue(params);
			if (db != null) {
				String perfValue = HostComm.getKBytesToSize(db.getPerfValue());
				array1.add("{value:" + Double.parseDouble(perfValue) + ",name:'"
						+ snapName[i] + "KB'}");
				array2.add(snapName[i] + "KB");
			} else {
				array1.add("{value:'',name:'" + snapName[i] + "KB'}");
				array2.add(snapName[i] + "KB");
			}

		}
		map2.put("sybasePageSizePie", array1);
		mapName.put("pieName", array2);
		mapResult.put("data", map2);
		mapResult.put("dataName", mapName);
		return mapResult;
	}

	/**
	 * server DBFile 数据库页分配情况饼图
	 */
	@RequestMapping("/findPageSpacePie")
	@ResponseBody
	public Map<String, Object> findPageSpacePie(HttpServletRequest request,
			ModelMap map) {
		logger.info("DBFile 数据库页分配情况");
		int moID = Integer.parseInt(request.getParameter("moID"));
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> mapResult = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		Map<String,Object> mapName = new HashMap<String,Object>();
		ArrayList<Object> array1 = new ArrayList<Object>();
		ArrayList<Object> array2 = new ArrayList<Object>();
		params.put("moID", moID);
		String[] snapName = { "数据页数", "日志页数" };
		String[] kpiName = { SybaseServerKPINameDef.DATAFILEPAGES,
				SybaseServerKPINameDef.LOGPAGES };

		for (int i = 0; i < snapName.length; i++) {
			params.put("kpiName", kpiName[i]);
			PerfSybaseDatabaseBean db = sybaseService
					.getDatabasefPerfValue(params);
			if (db != null) {
				String perfValue = HostComm.getKBytesToSize(db.getPerfValue());
				array1.add("{value:" + Double.parseDouble(perfValue) + ",name:'"
						+ snapName[i] + "KB'}");
				array2.add(snapName[i] + "KB");
			} else {
				array1.add("{value:'',name:'" + snapName[i] + "KB'}");
				array2.add(snapName[i] + "KB");
			}

		}
		map2.put("sybasePageSpacePie", array1);
		mapName.put("pieName", array2);
		mapResult.put("data", map2);
		mapResult.put("dataName", mapName);
		return mapResult;
	}

	/**
	 * server DBFile 日志使用空间饼图
	 */
	@RequestMapping("/findLogSpacePie")
	@ResponseBody
	public Map<String, Object> findLogSpacePie(HttpServletRequest request,
			ModelMap map) {
		logger.info("日志 空间使用情况");
		int moID = Integer.parseInt(request.getParameter("moID"));
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> mapResult = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		Map<String,Object> mapName = new HashMap<String,Object>();
		ArrayList<Object> array1 = new ArrayList<Object>();
		ArrayList<Object> array2 = new ArrayList<Object>();
		params.put("moID", moID);
		String[] snapName = { "日志已用空间", "日志空闲空间" };
		String[] kpiName = { SybaseServerKPINameDef.LOGUSEDSPACE,
				SybaseServerKPINameDef.LOGFREESPACE };

		for (int i = 0; i < snapName.length; i++) {
			params.put("kpiName", kpiName[i]);
			PerfSybaseDatabaseBean db = sybaseService
					.getDatabasefPerfValue(params);
			if (db != null) {
				String perfValue = HostComm.getKBytesToSize(db.getPerfValue());
				array1.add("{value:" + Double.parseDouble(perfValue) + ",name:'"
						+ snapName[i] + "KB'}");
				array2.add(snapName[i] + "KB");
			} else {
				array1.add("{value:'',name:'" + snapName[i] + "KB'}");
				array2.add(snapName[i] + "KB");
			}

		}
		map2.put("sybaseDBLogPie", array1);
		mapName.put("pieName", array2);
		mapResult.put("data", map2);
		mapResult.put("dataName", mapName);
		return mapResult;
		
	}

	/**
	 * server DBFile 日志使用空间饼图
	 */
	@RequestMapping("/findDFSpacePie")
	@ResponseBody
	public Map<String, Object> findDFSpacePie(HttpServletRequest request,
			ModelMap map) {
		logger.info("数据 空间使用情况");
		int moID = Integer.parseInt(request.getParameter("moID"));
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> mapResult = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		Map<String,Object> mapName = new HashMap<String,Object>();
		ArrayList<Object> array1 = new ArrayList<Object>();
		ArrayList<Object> array2 = new ArrayList<Object>();
		params.put("moID", moID);
		String[] snapName = { "数据已用空间", "数据空闲空间" };
		String[] kpiName = { SybaseServerKPINameDef.DATAFILEUSEDSPACE,
				SybaseServerKPINameDef.DATAFILEFREESPACE };

		for (int i = 0; i < snapName.length; i++) {
			params.put("kpiName", kpiName[i]);
			PerfSybaseDatabaseBean db = sybaseService
					.getDatabasefPerfValue(params);
			if (db != null) {
				String perfValue = HostComm.getKBytesToSize(db.getPerfValue());
				array1.add("{value:" + Double.parseDouble(perfValue) + ",name:'"
						+ snapName[i] + "KB'}");
				array2.add(snapName[i] + "KB");
			} else {
				array1.add("{value:'',name:'" + snapName[i] + "KB'}");
				array2.add(snapName[i] + "KB");
			}

		}
		map2.put("sybaseDFSpacePie", array1);
		mapName.put("pieName", array2);
		mapResult.put("data", map2);
		mapResult.put("dataName", mapName);
		return mapResult;
	}

	/**
	 * server DBFile 日志页数饼图
	 */
	@RequestMapping("/findLogPagesPie")
	@ResponseBody
	public Map<String, Object> findLogPagesPie(HttpServletRequest request,
			ModelMap map) {
		logger.info("日志 页数使用情况");
		int moID = Integer.parseInt(request.getParameter("moID"));
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> mapResult = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		Map<String,Object> mapName = new HashMap<String,Object>();
		ArrayList<Object> array1 = new ArrayList<Object>();
		ArrayList<Object> array2 = new ArrayList<Object>();
		params.put("moID", moID);
		String[] snapName = { "日志已用页", "日志空闲页" };
		String[] kpiName = { SybaseServerKPINameDef.LOGUSEDPAGES,
				SybaseServerKPINameDef.LOGFREEPAGES };
		for (int i = 0; i < snapName.length; i++) {
			params.put("kpiName", kpiName[i]);
			PerfSybaseDatabaseBean db = sybaseService
					.getDatabasefPerfValue(params);
			if (db != null) {
				String perfValue = HostComm.getKBytesToSize(db.getPerfValue());
				array1.add("{value:" + Double.parseDouble(perfValue) + ",name:'"
						+ snapName[i] + "KB'}");
				array2.add(snapName[i] + "KB");
			} else {
				array1.add("{value:'',name:'" + snapName[i] + "KB'}");
				array2.add(snapName[i] + "KB");
			}

		}
		map2.put("sybasePageSizePie", array1);
		mapName.put("pieName", array2);
		mapResult.put("data", map2);
		mapResult.put("dataName", mapName);
		return mapResult;
	}

	/**
	 * server DBFile 数据页饼图
	 */
	@RequestMapping("/findDFPagesPie")
	@ResponseBody
	public Map<String, Object> findDFPagesPie(HttpServletRequest request,
			ModelMap map) {
		logger.info("数据 页使用情况");
		int moID = Integer.parseInt(request.getParameter("moID"));
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> mapResult = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		Map<String,Object> mapName = new HashMap<String,Object>();
		ArrayList<Object> array1 = new ArrayList<Object>();
		ArrayList<Object> array2 = new ArrayList<Object>();
		params.put("moID", moID);
		String[] snapName = { "数据已用页", "数据空闲页" };
		String[] kpiName = { SybaseServerKPINameDef.DATAFILEUSEDPAGES,
				SybaseServerKPINameDef.DATAFILEFREEPAGES };

		for (int i = 0; i < snapName.length; i++) {
			params.put("kpiName", kpiName[i]);
			PerfSybaseDatabaseBean db = sybaseService
					.getDatabasefPerfValue(params);
			if (db != null) {
				String perfValue = HostComm.getKBytesToSize(db.getPerfValue());
				array1.add("{value:" + Double.parseDouble(perfValue) + ",name:'"
						+ snapName[i] + "KB'}");
				array2.add(snapName[i] + "KB");
			} else {
				array1.add("{value:'',name:'" + snapName[i] + "KB'}");
				array2.add(snapName[i] + "KB");
			}

		}
		map2.put("sybasePagesPie", array1);
		mapName.put("pieName", array2);
		mapResult.put("data", map2);
		mapResult.put("dataName", mapName);
		return mapResult;
	}

	/**
	 * 获取 DB 空间使用率
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findSyChartSpaceUsage")
	@ResponseBody
	public Map<String, Object> findSyChartSpaceUsage(HttpServletRequest request) {
		logger.info("根据数据库实例ID获取图表可用性使用率");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("moID", Integer.parseInt(request.getParameter("moID")));
		params.put("kpiName", SybaseServerKPINameDef.SPACEUSAGE);
		PerfSybaseDatabaseBean cache = sybaseService
				.getDatabasefPerfValue(params);
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		ArrayList<Object> array1 = new ArrayList<Object>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		if (cache != null) {
			map1.put("value", cache.getPerfValue());
			map1.put("name", "空间使用率");
			array1.add(map1);
			map2.put("sybaseSpaceUsage", array1);
			map.put("data", map2);
		} else {
			map1.put("value", "");
			map1.put("name", "空间使用率");
			array1.add(map1);
			map2.put("sybaseSpaceUsage", array1);
			map.put("data", map2);
		}
		return map;
	}

	/**
	 * 获取 DB 页使用率
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findSyChartPageUsage")
	@ResponseBody
	public Map<String, Object> findSyChartPageUsage(HttpServletRequest request) {
		logger.info("数据页 仪表盘");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("moID", Integer.parseInt(request.getParameter("moID")));
		params.put("kpiName", SybaseServerKPINameDef.USAGEPAGE);
		PerfSybaseDatabaseBean cache = sybaseService
				.getDatabasefPerfValue(params);
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		ArrayList<Object> array1 = new ArrayList<Object>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		if (cache != null) {
			map1.put("value", cache.getPerfValue());
			map1.put("name", "页使用率");
			array1.add(map1);
			map2.put("sybasePageUsage", array1);
			map.put("data", map2);
		} else {
			map1.put("value", "");
			map1.put("name", "页使用率");
			array1.add(map1);
			map2.put("sybasePageUsage", array1);
			map.put("data", map2);
		}
		return map;
	}

	/**
	 * 获取日志空间使用率
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findSyChartLogSpaceUsage")
	@ResponseBody
	public Map<String, Object> findSyChartLogSpaceUsage(
			HttpServletRequest request) {
		logger.info("根据数据库实例ID获取图表可用性使用率");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("moID", Integer.parseInt(request.getParameter("moID")));
		params.put("kpiName", SybaseServerKPINameDef.LOGSPACEUSAGE);
		PerfSybaseDatabaseBean cache = sybaseService
				.getDatabasefPerfValue(params);
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		ArrayList<Object> array1 = new ArrayList<Object>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		if (cache != null) {
			map1.put("value", cache.getPerfValue());
			map1.put("name", "日志空间使用率");
			array1.add(map1);
			map2.put("sybaseDBLogUsage", array1);
			map.put("data", map2);
		} else {
			map1.put("value", "");
			map1.put("name", "日志空间使用率");
			array1.add(map1);
			map2.put("sybaseDBLogUsage", array1);
			map.put("data", map2);
		}
		return map;
	}

	/**
	 * 获取数据空间使用率
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findSyChartDFSpaceUsage")
	@ResponseBody
	public Map<String, Object> findSyChartDFSpaceUsage(
			HttpServletRequest request) {
		logger.info("数据空间使用情况");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("moID", Integer.parseInt(request.getParameter("moID")));
		params.put("kpiName", SybaseServerKPINameDef.DATAFILESPACEUSAGE);
		PerfSybaseDatabaseBean cache = sybaseService
				.getDatabasefPerfValue(params);
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		ArrayList<Object> array1 = new ArrayList<Object>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		if (cache != null) {
			map1.put("value", cache.getPerfValue());
			map1.put("name", "数据空间使用率");
			array1.add(map1);
			map2.put("sybaseDFSpaceUsage", array1);
			map.put("data", map2);
		} else {
			map1.put("value", "");
			map1.put("name", "数据空间使用率");
			array1.add(map1);
			map2.put("sybaseDFSpaceUsage", array1);
			map.put("data", map2);
		}
		return map;
	}

	/**
	 * 获取日志页使用率
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findSyChartLogPageUsage")
	@ResponseBody
	public Map<String, Object> findSyChartLogPageUsage(
			HttpServletRequest request) {
		logger.info("根据数据库实例ID获取图表可用性使用率");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("moID", Integer.parseInt(request.getParameter("moID")));
		params.put("kpiName", SybaseServerKPINameDef.LOGPAGEUSAGE);
		PerfSybaseDatabaseBean cache = sybaseService
				.getDatabasefPerfValue(params);
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		ArrayList<Object> array1 = new ArrayList<Object>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		if (cache != null) {
			map1.put("value", cache.getPerfValue());
			map1.put("name", "日志页使用率");
			array1.add(map1);
			map2.put("sybaseLogPageUsage", array1);
			map.put("data", map2);
		} else {
			map1.put("value", "");
			map1.put("name", "日志页使用率");
			array1.add(map1);
			map2.put("sybaseLogPageUsage", array1);
			map.put("data", map2);
		}
		return map;
	}

	/**
	 * 获取数据页使用率
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findSyChartDFUsagePages")
	@ResponseBody
	public Map<String, Object> findSyChartDFUsagePages(
			HttpServletRequest request) {
		logger.info("数据页使用率");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("moID", Integer.parseInt(request.getParameter("moID")));
		params.put("kpiName", SybaseServerKPINameDef.DATAFILEUSAGEPAGES);
		PerfSybaseDatabaseBean cache = sybaseService
				.getDatabasefPerfValue(params);
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		ArrayList<Object> array1 = new ArrayList<Object>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		if (cache != null) {
			map1.put("value", cache.getPerfValue());
			map1.put("name", "数据页使用率");
			array1.add(map1);
			map2.put("sybaseDFUsagePages", array1);
			map.put("data", map2);
		} else {
			map1.put("value", "");
			map1.put("name", "数据页使用率");
			array1.add(map1);
			map2.put("sybaseDFUsagePages", array1);
			map.put("data", map2);
		}
		return map;
	}

	/**
	 * 提供默认SybaseServer
	 */
	@RequestMapping("/initSybaseDbName")
	@ResponseBody
	public MOSybaseDatabaseBean initSybaseDbName(HttpServletRequest request) {
		MOSybaseDatabaseBean sybaseDbBean = sybaseService
				.getFirstSybaseDbBean();
		return sybaseDbBean;
	}

	/**
	 * 提供默认SybaseServer
	 */
	@RequestMapping("/findSybaseDbInfo")
	@ResponseBody
	public MOSybaseDatabaseBean findSybaseDbInfo(Integer moId) {
		MOSybaseDatabaseBean sybaseDbBean = sybaseService.getSybaseDbById(moId);
		return sybaseDbBean;
	}
	
	/**
	 * 查找sybase服务信息
	 */
	@RequestMapping("/findSyBaseServerInfo")
	@ResponseBody
	public MOMySQLDBServerBean findSyBaseServerInfo(int moId){
		return sybaseService.findSyBaseServerInfo(moId);
	}
	
	/**
	 * 查找Sybase数据库设备信息
	 */
	@RequestMapping("/findSybaseDeviceInfo")
	@ResponseBody
	public MOSybaseDeviceBean findSybaseDeviceInfo(int moId){
		return sybaseService.findSybaseDeviceInfo(moId);
	}
	
	/**
	 * 查找Sybase数据库信息
	 */
	@RequestMapping("/findSybaseDatabase")
	@ResponseBody
	public MOSybaseDatabaseBean findSybaseDatabase(int moId){
		return sybaseService.findSybaseDatabase(moId);
	}
}
