package com.fable.insightview.monitor.database.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.fable.insightview.monitor.database.entity.Db2InfoBean;
import com.fable.insightview.monitor.database.entity.MODBMSServerBean;
import com.fable.insightview.monitor.database.entity.MoDb2BufferPoolBean;
import com.fable.insightview.monitor.database.entity.MoDbTabsBean;
import com.fable.insightview.monitor.database.entity.PerfDB2BufferPoolBean;
import com.fable.insightview.monitor.database.entity.PerfDB2TbsBean;
import com.fable.insightview.monitor.database.service.IDb2Service;
import com.fable.insightview.monitor.host.entity.AlarmActiveDetail;
import com.fable.insightview.monitor.host.entity.HostComm;
import com.fable.insightview.monitor.host.entity.KPINameDef;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJdbcPoolBean;
import com.fable.insightview.monitor.middleware.tomcat.service.ITomcatService;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;

@Controller
@RequestMapping("/monitor/db2Manage")
public class Db2Controller {

	@Autowired
	IDb2Service db2Service;
	@Autowired
	ITomcatService tomService;
	
	private final Logger logger = LoggerFactory.getLogger(Db2Controller.class);

	/**
	 * 加载数据库实例列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/toDb2InstanceList")
	public ModelAndView toDb2InstanceList(HttpServletRequest request,
			ModelMap map,String navigationBar) {
		String flag = request.getParameter("flag");
		request.setAttribute("flag", flag);
		String dbmsMoId = request.getParameter("flag");
		request.setAttribute("dbmsMoId", dbmsMoId);
		request.setAttribute("navigationBar",navigationBar);
		return new ModelAndView("monitor/device/db2Instance_list");
	}

	/**
	 * 加载数据库实例列表页面数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listDb2Instance")
	@ResponseBody
	public Map<String, Object> listDb2Instance() throws Exception {
		logger.info("开始...加载DB2实例对象列表");
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
		paramMap.put("ip", request.getParameter("ip"));
		paramMap.put("instanceName", request.getParameter("instanceName"));
		if(!"".equals(request.getParameter("dbmsMoId")) && request.getParameter("dbmsMoId") != null){
			paramMap.put("dbmsMoId", request.getParameter("dbmsMoId"));
		}
		page.setParams(paramMap);
		Map params = new HashMap();
		List<MODBMSServerBean> orclInsList = db2Service
				.getDb2InstanceList(page);

		// 查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", orclInsList);
		logger.info("结束...加载DB2实例对象列表" + orclInsList);
		return result;
	}

	/**
	 * 加载数据文件列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/toDb2InfoList")
	public ModelAndView toDb2InfoList(HttpServletRequest request) {
		String flag = request.getParameter("flag");
		request.setAttribute("flag", flag);
		String instanceMOID = request.getParameter("instanceMOID");
		request.setAttribute("instanceMOID", instanceMOID);
		String isMoid = request.getParameter("isMoid");
		request.setAttribute("isMoid", isMoid);
		String dbmsMoId = request.getParameter("dbmsMoId");
		request.setAttribute("dbmsMoId", dbmsMoId);
		request.setAttribute("navigationBar",request.getParameter("navigationBar"));
		return new ModelAndView("monitor/device/db2Info_list");
	}

	/**
	 * 加载数据文件列表页面数据
	 * 
	 * @param taskBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listDb2Infos")
	@ResponseBody
	public Map<String, Object> listDb2Infos() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<Db2InfoBean> page = new Page<Db2InfoBean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("databaseName", request.getParameter("databaseName"));
		paramMap.put("ip", request.getParameter("ip"));
		paramMap.put("instanceMOID", request.getParameter("instanceMOID"));
		paramMap.put("instanceName", request.getParameter("instanceName"));
		if(!"".equals(request.getParameter("dbmsMoId")) && request.getParameter("dbmsMoId") != null){
			paramMap.put("dbmsMoId", request.getParameter("dbmsMoId"));
		}
		page.setParams(paramMap);
		List<Db2InfoBean> dataFileLst = db2Service.getDb2InfoList(page);
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
	@RequestMapping("/toShowDb2TbsInfo")
	public ModelAndView toShowDb2TbsInfo(HttpServletRequest request) {
		String flag = request.getParameter("flag");
		request.setAttribute("flag", flag);
		String instanceMOID = request.getParameter("instanceMOID");
		request.setAttribute("instanceMOID", instanceMOID);
		String dbMoId = request.getParameter("dbMoId");
		request.setAttribute("dbMoId", dbMoId);
		request.setAttribute("navigationBar", request.getParameter("navigationBar"));
		return new ModelAndView("monitor/device/db2TbsInfo_list");
	}

	/**
	 * 加载表空间列表页面数据
	 * 
	 * @param taskBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getDb2TbsInfo")
	@ResponseBody
	public Map<String, Object> getDb2TbsInfo() throws Exception {
		logger.info("开始...加载表空间信息列表");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MoDbTabsBean> page = new Page<MoDbTabsBean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("databaseName", request.getParameter("databaseName"));
		paramMap.put("ip", request.getParameter("ip"));
		paramMap.put("tbsName", request.getParameter("tbsName"));
		paramMap.put("dbMoId", request.getParameter("dbMoId"));
		paramMap.put("instanceName", request.getParameter("instanceName"));
		page.setParams(paramMap);
		List<MoDbTabsBean> moLsinfo = db2Service.getDb2TabsList(page);
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
	 * 加载缓冲池列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/toShowDb2BufferPoolInfo")
	public ModelAndView toShowDb2BufferPoolInfo(HttpServletRequest request) {
		String flag = request.getParameter("flag");
		request.setAttribute("flag", flag);
		String dbMoId = request.getParameter("dbMoId");
		request.setAttribute("dbMoId", dbMoId);
		request.setAttribute("navigationBar",request.getParameter("navigationBar"));
		return new ModelAndView("monitor/device/db2BufferPool_list");
	}

	/**
	 * 加载缓冲池列表页面数据
	 * 
	 * @param taskBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getDb2BufferPoolInfo")
	@ResponseBody
	public Map<String, Object> getDb2BufferPoolInfo() throws Exception {
		logger.info("开始...加载缓冲池信息列表");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MoDb2BufferPoolBean> page = new Page<MoDb2BufferPoolBean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("databaseName", request.getParameter("databaseName"));
		paramMap.put("ip", request.getParameter("ip"));
		paramMap.put("bufferPoolName", request.getParameter("bufferPoolName"));
		paramMap.put("dbMoId", request.getParameter("dbMoId"));
		paramMap.put("instanceName", request.getParameter("instanceName"));
		page.setParams(paramMap);
		List<MoDb2BufferPoolBean> moLsinfo = db2Service
				.getDb2BufferPoolList(page);
		// 查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", moLsinfo);
		logger.info("结束...加载缓冲池信息列表 " + moLsinfo);
		return result;
	}
	
	/**
	 * 加载数据库实例列表部件
	 * 
	 * @return
	 */
	@RequestMapping("/toInstanceList")
	public ModelAndView toInstanceList(HttpServletRequest request,
			ModelMap map) {
		return new ModelAndView("monitor/database/db2/db2Instance_list");
	}

	/**
	 * 加载数据库实例列表页面数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listInstance")
	@ResponseBody
	public Map<String, Object> listInstance() throws Exception {
		logger.info("开始...加载DB2实例对象列表");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
		.getFlexiGridPageInfo(request);
		Page<MODBMSServerBean> page = new Page<MODBMSServerBean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map params = new HashMap();
		List<MODBMSServerBean> orclInsList = db2Service.getDb2InstanceList(page);

		// 查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", orclInsList);
		logger.info("结束...加载DB2实例对象列表" + orclInsList);
		return result;
	}
	
	/**
	 * 加载数据库列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/toDb2DatabaseList")
	public ModelAndView toDb2DatabaseList(HttpServletRequest request) {
		String liInfo = request.getParameter("liInfo");
		request.setAttribute("liInfo", liInfo);
		request.setAttribute("moID", request.getParameter("moID"));
		return new ModelAndView("monitor/database/db2/db2Info_list");
	}

	/**
	 * 加载数据库列表页面数据
	 * 
	 * @param taskBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listDb2Database")
	@ResponseBody
	public Map<String, Object> listDb2Database(HttpServletRequest request) throws Exception {
		List<Db2InfoBean> dataFileLst = db2Service.getDb2InfoByInstanceMoId(Integer.parseInt(request.getParameter("moID")));
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("rows", dataFileLst);
		return result;
	}

	/**
	 * 加载缓冲池列表部件
	 * 
	 * @return
	 */
	@RequestMapping("/toShowDb2BufferPoolList")
	public ModelAndView toShowDb2BufferPoolList(HttpServletRequest request) {
		String liInfo = request.getParameter("liInfo");
		request.setAttribute("liInfo", liInfo);
		request.setAttribute("moID", request.getParameter("moID"));
		return new ModelAndView("monitor/database/db2/db2BufferPool_list");
	}

	/**
	 * 加载缓冲池列表部件数据
	 * 
	 * @param taskBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getDb2BufferPoolList")
	@ResponseBody
	public Map<String, Object> getDb2BufferPoolList() throws Exception {
		logger.info("开始...加载缓冲池信息列表");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		List<PerfDB2BufferPoolBean> moLsinfo = db2Service.getBufferPoolByDbMoId(Integer.parseInt(request.getParameter("moID")));
		// 查询总记录数
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("rows", moLsinfo);
		logger.info("结束...加载缓冲池信息列表 " + moLsinfo);
		return result;
	}
	
	/**
	 * 加载表空间列表部件
	 * 
	 * @return
	 */
	@RequestMapping("/toShowDb2TbsList")
	public ModelAndView toShowDb2TbsList(HttpServletRequest request) {
		String liInfo = request.getParameter("liInfo");
		request.setAttribute("liInfo", liInfo);
		request.setAttribute("moID", request.getParameter("moID"));
		return new ModelAndView("monitor/database/db2/db2TbsInfo_list");
	}

	/**
	 * 加载表空间列表部件数据
	 * 
	 * @param taskBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getDb2TbsList")
	@ResponseBody
	public Map<String, Object> getDb2TbsList() throws Exception {
		logger.info("开始...加载表空间信息列表");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		List<PerfDB2TbsBean> moLsinfo = db2Service.getDb2TabsByDbMoId(Integer.parseInt(request.getParameter("moID")));
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("rows", moLsinfo);
		logger.info("结束...加载表空间信息列表 " + moLsinfo);
		return result;
	}

	
	
	/**
	 * 获得DB2数据库实例信息
	 */
	@RequestMapping("/findDB2InsInfo")
	@ResponseBody
	public MODBMSServerBean findDB2InsInfo(MODBMSServerBean bean) {
		return db2Service.getDb2InstanceByMoId(bean.getMoid());
	}

	/**
	 * 获得DB2数据库信息
	 */
	@RequestMapping("/findDB2DbInfo")
	@ResponseBody
	public Db2InfoBean findDB2DbInfo(Db2InfoBean db2InfoBean) {
		return db2Service.getDb2DatabseByMoId(db2InfoBean.getMoId());
	}

	/**
	 * 获得DB2数据库缓冲池信息
	 */
	@RequestMapping("/findDb2BufferPoolInfo")
	@ResponseBody
	public MoDb2BufferPoolBean findDb2BufferPoolInfo(MoDb2BufferPoolBean bean) {
		return db2Service.getDb2BufferPoolByMoId(bean.getMoId());
	}

	/**
	 * 获得DB2数据库表空间
	 */
	@RequestMapping("/findDB2TBSInfo")
	@ResponseBody
	public MoDbTabsBean findDB2TBSInfo(MoDbTabsBean bean) {
		return db2Service.getDb2TabsByMoId(bean.getMoId());
	}
	
	/**
	 * 提供默认数据库实例
	 */
	@RequestMapping("/initDb2InstanceName")
	@ResponseBody
	public MODBMSServerBean initDb2InstanceName(HttpServletRequest request) {
		MODBMSServerBean dbBean = db2Service.getFirstDb2InsInfo();
		return dbBean;
	}
	
	/**
	 * 提供默认DB2数据库
	 */
	@RequestMapping("/initDb2dbName")
	@ResponseBody
	public Db2InfoBean initDb2dbName(HttpServletRequest request) {
		Db2InfoBean dbBean = db2Service.getFirstDb2dbInfo();
		return dbBean;
	}
	
	/**
	 * 提供默认连接池
	 */
	@RequestMapping("/initJdbcPoolName")
	@ResponseBody
	public MOMiddleWareJdbcPoolBean initJdbcPoolName(HttpServletRequest request) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("jmxType", request.getParameter("jmxType"));
		MOMiddleWareJdbcPoolBean dbBean = tomService.getFirstJdbcPool(paramMap);
		return dbBean;
	}
	
	/**
	 * 获取数据库实例可用率
	 * 
	 * @param deviceIP
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findDb2ChartAvailability")
	@ResponseBody
	public Map<String, Object> findDb2ChartAvailability(
			HttpServletRequest request) throws Exception {
		logger.info("根据数据库实例ID获取图表可用性使用率");
		Map params = new HashMap();
		params = queryBetweenTime(request, params);
		params.put("MOID", Integer.parseInt(request.getParameter("moID")));
		MODBMSServerBean mo = db2Service.getDb2ChartAvailability(params);
		Map map = new HashMap();
		Map map2 = new HashMap();
		ArrayList<Object> array1 = new ArrayList<Object>();
		Map map1 = new HashMap();
		if (mo != null) {
			map1.put("value", mo.getDeviceavailability());
			map1.put("name", "可用性");
			array1.add(map1);
			map2.put("db2Availability", array1);
			map.put("data", map2);
			map.put("url", "");
		} else {
			map1.put("value", "");
			map1.put("name", "可用性");
			array1.add(map1);
			map2.put("db2Availability", array1);
			map.put("data", map2);
			map.put("url", "");
		}
		return map;
	}
	
	
	/**
	 * 跳转至告警列表页
	 * 
	 * @return
	 */
	@RequestMapping("/toShowTbsAlarmInfo")
	public ModelAndView toShowTbsAlarmInfo(HttpServletRequest request) {
		String moId = request.getParameter("moID");
		String liInfo = request.getParameter("liInfo");
		request.setAttribute("liInfo", liInfo);
		request.setAttribute("moId", moId);
		return new ModelAndView("monitor/database/db2/db2AlarmInfo");
	}
	
	/**
	 * 最近告警
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getTbsAlarmInfo")
	@ResponseBody
	public Map<String, Object> getTbsAlarmInfo() throws Exception {
		logger.info("开始...加载表空间信息列表 for 最近告警");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Map<String, Object> result = new HashMap<String, Object>();
		Map params = new HashMap();
		params.put("MOID", Integer.parseInt(request.getParameter("moID")));
		int alarmNum = Integer.parseInt(request.getParameter("alarmNum"));
		try {
			List<AlarmActiveDetail> moLsinfo = db2Service
					.getTbsAlarmInfo(params);
			if (moLsinfo.size() >= alarmNum) {
				moLsinfo = moLsinfo.subList(0, alarmNum);
			}
			result.put("rows", moLsinfo);
		} catch (Exception e) {
			logger.error("查询异常：" + e.getMessage());
		}

		logger.info("结束...加载表空间信息列表 for 最近告警");
		return result;
	}
	/**
	 * 设置查询时间(公共调用方法)
	 * 
	 * @param request
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map queryBetweenTime(HttpServletRequest request, Map params) {
		String time = request.getParameter("time");
		String seltDate = request.getParameter("seltDate");
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar d = Calendar.getInstance();
		String timeEnd = f.format(d.getTime());
		String timeBegin = "";
		if(seltDate != null){
			if (seltDate.endsWith("0")) {// 最近1小时
				d.add(Calendar.HOUR, -1);
			} else if (seltDate.endsWith("1")) {// 最近一天
				d.add(Calendar.DATE, -1);
			} else if (seltDate.endsWith("2")) {// 最近一周
				d.add(Calendar.DATE, -7);
			} else if (seltDate.endsWith("3")) {// 最近一月
				d.add(Calendar.MONTH, -1);
			} else if (seltDate.endsWith("4")) {// 最近三个月
				d.add(Calendar.MONTH, -3);
			} else if (seltDate.endsWith("5")) {// 最近半年
				d.add(Calendar.MONTH, -6);
			} else if (seltDate.endsWith("6")) {// 最近一年
				d.add(Calendar.YEAR, -1);
			}
			timeBegin = f.format(d.getTime());
		}
		
		if(time != null){
			if ("24H".equals(time)) {// 最近一天
				d.add(Calendar.DATE, -1);
				timeBegin = f.format(d.getTime());
			} else if ("7D".equals(time)) {// 最近一周
				d.add(Calendar.DATE, -7);
				timeBegin = f.format(d.getTime());
			} else if ("Today".equals(time)) {// 今天
				d.add(Calendar.DATE, 0);
				d.set(Calendar.HOUR_OF_DAY, 0);
				d.set(Calendar.MINUTE, 0);
				d.set(Calendar.SECOND, 0);
				timeBegin = f.format(d.getTime());
			} else if ("Yes".equals(time)) {// 昨天
				d.add(Calendar.DAY_OF_MONTH, -1);
				d.set(Calendar.HOUR_OF_DAY, 0);
				d.set(Calendar.MINUTE, 0);
				d.set(Calendar.SECOND, 0);
				timeBegin = f.format(d.getTime());
				d.set(Calendar.HOUR_OF_DAY, 23);
				d.set(Calendar.MINUTE, 59);
				d.set(Calendar.SECOND, 59);
				timeEnd = f.format(d.getTime());
			}else if ("Week".equals(time)) {// 本周
				if(d.get(Calendar.DAY_OF_WEEK)==1){
					d.add(Calendar.DAY_OF_WEEK, -(d.get(Calendar.DAY_OF_WEEK)+5));
				}else{
					d.add(Calendar.DAY_OF_WEEK, -(d.get(Calendar.DAY_OF_WEEK)-2));
				}
				
				d.set(Calendar.HOUR_OF_DAY, 0);
				d.set(Calendar.MINUTE, 0);
				d.set(Calendar.SECOND, 0);
				timeBegin = f.format(d.getTime());
			}else if ("Month".equals(time)) {// 本月
				d.add(Calendar.MONTH, 0);
				//设置为1号,当前日期既为本月第一天 
				d.set(Calendar.DAY_OF_MONTH,1);
				d.set(Calendar.HOUR_OF_DAY, 0);
				d.set(Calendar.MINUTE, 0);
				d.set(Calendar.SECOND, 0);
				timeBegin = f.format(d.getTime());
			}else if ("LastMonth".equals(time)) {// 上月
				 d.add(Calendar.MONTH, -1);
			     d.set(Calendar.DAY_OF_MONTH,1);//上月第一天
			     d.set(Calendar.HOUR_OF_DAY, 0);
				 d.set(Calendar.MINUTE, 0);
				 d.set(Calendar.SECOND, 0);
			     timeBegin = f.format(d.getTime());
			     Calendar cale = Calendar.getInstance();
			     //设置为1号,当前日期既为本月第一天 
			     cale.set(Calendar.DAY_OF_MONTH,0);
			     cale.set(Calendar.HOUR_OF_DAY, 23);
			     cale.set(Calendar.MINUTE, 59);
			     cale.set(Calendar.SECOND, 59);
			     timeEnd = f.format(cale.getTime());

			}
		}
		params.put("timeBegin", timeBegin);
		params.put("timeEnd", timeEnd);
		return params;
	}
}
