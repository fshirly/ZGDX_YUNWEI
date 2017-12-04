package com.fable.insightview.monitor.middelware.controller;

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

import com.fable.insightview.monitor.database.service.IOracleService;
import com.fable.insightview.monitor.host.entity.HostComm;
import com.fable.insightview.monitor.host.entity.KPINameDef;
import com.fable.insightview.monitor.middleware.middlewarecommunity.service.ISysMiddleWareCommunityService;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJMSBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJMXBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJTABean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJVMBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJdbcDSBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJdbcPoolBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareMemoryBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareServletBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareThreadPoolBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareWebModuleBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MiddleWareKPINameDef;
import com.fable.insightview.monitor.middleware.tomcat.entity.PerfTomcatClassLoadBean;
import com.fable.insightview.monitor.middleware.tomcat.service.IMiddlewareService;
import com.fable.insightview.monitor.middleware.tomcat.service.ITomcatService;
import com.fable.insightview.monitor.middleware.websphere.entity.MoMiddleWareJ2eeAppBean;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.dict.util.DictionaryLoader;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;

@Controller
@RequestMapping("/monitor/DeviceTomcatManage")
public class DeviceTomcatController {
	@Autowired
	ITomcatService tomService;
	@Autowired
	IMiddlewareService imiddlewareService;
	@Autowired
	ISysMiddleWareCommunityService middleWareCommunityService;
	@Autowired
	IOracleService orclService;
	private final Logger logger = LoggerFactory
			.getLogger(DeviceTomcatController.class);

	/**
	 * JDBC连接池信息页面跳转
	 */
	@RequestMapping("/toMiddleWareJdbcPoolList")
	public ModelAndView toMiddleWareJdbcPoolList(HttpServletRequest request,
			MOMiddleWareJMXBean vo, ModelMap map) throws Exception {
		logger.info("跳转到JDBC连接池信息。。。");
		map.put("jmxType", vo.getJmxType());
		map.put("flag", request.getParameter("flag"));// 隐藏字段标志
		map.put("parentMoId", request.getParameter("parentMoId"));
		map.put("navigationBar",request.getParameter("navigationBar"));
		return new ModelAndView(
				"monitor/middleware/tomcat/middleWareJdbcPool_list");
	}

	/**
	 * 获取JDBC连接池页面显示数据
	 * 
	 * @param vo
	 * @param request
	 * @return
	 */
	@RequestMapping("/listMiddleWareJdbcPool")
	@ResponseBody
	public Map<String, Object> listMiddleWareJdbcPool(
			MOMiddleWareJdbcPoolBean vo, HttpServletRequest request) {
		logger.info("开始...获取页面显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MOMiddleWareJdbcPoolBean> page = new Page<MOMiddleWareJdbcPoolBean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String jmxType = request.getParameter("jmxType");
		// 约定为全部小写，如weblogic, websphere, tomcat
		if (!"".equals(jmxType) && jmxType != null && !"null".equals(jmxType)) {
			if (jmxType.equals("19")) {
				jmxType = MiddleWareKPINameDef.WEBSPHERE;
			} else if (jmxType.equals("20")) {
				jmxType = MiddleWareKPINameDef.TOMCAT;
			}else if (jmxType.equals("53")) {
				jmxType = MiddleWareKPINameDef.WEBLOGIC;
			}
		}
		if (!"null".equals(request.getParameter("parentMoId"))
				&& !"".equals(request.getParameter("parentMoId"))
				&& request.getParameter("parentMoId") != null) {
			int parentMoId = Integer.parseInt(request
					.getParameter("parentMoId"));
			paramMap.put("parentMoId", parentMoId);
		}
		paramMap.put("jmxType", jmxType);
		paramMap.put("serverName", vo.getServerName());
		paramMap.put("dsName", vo.getDsName());
		paramMap.put("ip", vo.getIp());
		page.setParams(paramMap);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 查询分页数据
			List<MOMiddleWareJdbcPoolBean> list = tomService
					.getJdbcPoolInfo(page);
			// 查询总记录数
			int totalCount = page.getTotalRecord();
			// 设置至前台显示
			result.put("total", totalCount);
			result.put("rows", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("结束...获取页面显示数据");
		return result;
	}

	/**
	 * JMS信息页面跳转
	 * 
	 * @return
	 */
	@RequestMapping("/toMiddleWareJMSList")
	public ModelAndView toMiddleWareJMSList(HttpServletRequest request,
			MOMiddleWareJMXBean vo, ModelMap map) throws Exception {
		logger.info("跳转到JMS信息。。。");
		map.put("jmxType", vo.getJmxType());
		map.put("flag", request.getParameter("flag"));// 隐藏字段标志
		map.put("parentMoId", request.getParameter("parentMoId"));
		map.put("navigationBar",request.getParameter("navigationBar"));
		return new ModelAndView("monitor/middleware/tomcat/middleWareJMS_list");
	}

	/**
	 * 获取JMS页面显示数据
	 * 
	 * @param vo
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/listMiddleWareJMS")
	@ResponseBody
	public Map<String, Object> listMiddleWareJMS(MOMiddleWareJMSBean vo,
			HttpServletRequest request) {
		logger.info("开始...获取页面显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MOMiddleWareJMSBean> page = new Page<MOMiddleWareJMSBean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String jmxType = request.getParameter("jmxType");
		// 约定为全部小写，如weblogic, websphere, tomcat
		if (!"".equals(jmxType) && jmxType != null && !"null".equals(jmxType)) {
			if (jmxType.equals("19")) {
				jmxType = MiddleWareKPINameDef.WEBSPHERE;
			} else if (jmxType.equals("20")) {
				jmxType = MiddleWareKPINameDef.TOMCAT;
			}else if (jmxType.equals("53")) {
				jmxType = MiddleWareKPINameDef.WEBLOGIC;
			}
		}
		if (!"null".equals(request.getParameter("parentMoId"))
				&& !"".equals(request.getParameter("parentMoId"))
				&& request.getParameter("parentMoId") != null) {
			int parentMoId = Integer.parseInt(request
					.getParameter("parentMoId"));
			paramMap.put("parentMoId", parentMoId);
		}
		paramMap.put("jmxType", jmxType);
		paramMap.put("serverName", vo.getServerName());
		paramMap.put("name", vo.getName());
		paramMap.put("ip", vo.getIp());
		page.setParams(paramMap);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 查询分页数据
			List<MOMiddleWareJMSBean> list = tomService.getJMSInfo(page);
			// 查询总记录数
			int totalCount = page.getTotalRecord();
			// 设置至前台显示
			result.put("total", totalCount);
			result.put("rows", list);
			logger.info("结束...获取页面显示数据");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * JTA信息页面跳转
	 * 
	 * @return
	 */
	@RequestMapping("/toMiddleWareJTAList")
	public ModelAndView toMiddleWareJTAList(HttpServletRequest request,
			MOMiddleWareJMXBean vo, ModelMap map) throws Exception {
		logger.info("跳转到JTA信息。。。");
		map.put("jmxType", vo.getJmxType());
		map.put("flag", request.getParameter("flag"));// 隐藏字段标志
		map.put("parentMoId", request.getParameter("parentMoId"));
		map.put("navigationBar",request.getParameter("navigationBar"));
		return new ModelAndView("monitor/middleware/tomcat/middleWareJTA_list");
	}

	/**
	 * 获取JTA页面显示数据
	 * 
	 * @param vo
	 * @param request
	 */
	@RequestMapping("/listMiddleWareJTA")
	@ResponseBody
	public Map<String, Object> listMiddleWareJTA(MOMiddleWareJTABean vo,
			HttpServletRequest request) {

		logger.info("开始...获取页面显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MOMiddleWareJTABean> page = new Page<MOMiddleWareJTABean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String jmxType = request.getParameter("jmxType");
		// 约定为全部小写，如weblogic, websphere, tomcat
		if (!"".equals(jmxType) && jmxType != null && !"null".equals(jmxType)) {
			if (jmxType.equals("19")) {
				jmxType = MiddleWareKPINameDef.WEBSPHERE;
			} else if (jmxType.equals("20")) {
				jmxType = MiddleWareKPINameDef.TOMCAT;
			}else if (jmxType.equals("53")) {
				jmxType = MiddleWareKPINameDef.WEBLOGIC;
			}
		}
		if (!"null".equals(request.getParameter("parentMoId"))
				&& !"".equals(request.getParameter("parentMoId"))
				&& request.getParameter("parentMoId") != null) {
			int parentMoId = Integer.parseInt(request
					.getParameter("parentMoId"));
			paramMap.put("parentMoId", parentMoId);
		}
		paramMap.put("jmxType", jmxType);
		paramMap.put("serverName", vo.getServerName());
		paramMap.put("name", vo.getName());
		paramMap.put("ip", vo.getIp());
		page.setParams(paramMap);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 查询分页数据
			List<MOMiddleWareJTABean> list = tomService.getJTAInfo(page);
			// 查询总记录数
			int totalCount = page.getTotalRecord();

			// 设置至前台显示
			result.put("total", totalCount);
			result.put("rows", list);
			logger.info("结束...获取页面显示数据");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 获取线程池页面显示数据
	 */
	@RequestMapping("/getThreadPoolList")
	@ResponseBody
	public Map<String, Object> getThreadPoolList(
			MOMiddleWareThreadPoolBean threadPoolBean,
			HttpServletRequest request) {

		logger.info("开始...获取页面显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MOMiddleWareThreadPoolBean> page = new Page<MOMiddleWareThreadPoolBean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String jmxType = request.getParameter("jmxType");
		if (!"".equals(jmxType) && jmxType != null && !"null".equals(jmxType)) {
			int middleID = Integer.parseInt(jmxType);
			if (middleID == 19) {
				paramMap.put("jmxType", MiddleWareKPINameDef.WEBSPHERE);
			} else if (middleID == 20) {
				paramMap.put("jmxType", MiddleWareKPINameDef.TOMCAT);
			}else if (middleID == 53) {
				paramMap.put("jmxType", MiddleWareKPINameDef.WEBLOGIC);
			}
		}
		if (!"null".equals(request.getParameter("parentMoId"))
				&& !"".equals(request.getParameter("parentMoId"))
				&& request.getParameter("parentMoId") != null) {
			int parentMoId = Integer.parseInt(request
					.getParameter("parentMoId"));
			paramMap.put("parentMoId", parentMoId);
		}
		paramMap.put("threadName", threadPoolBean.getThreadName());
		paramMap.put("ip", threadPoolBean.getIp());
		page.setParams(paramMap);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 查询分页数据
			List<MOMiddleWareThreadPoolBean> list = tomService.getThreadPoolList(page);
			for (int i = 0; i < list.size(); i++) {
				//获得KPI名称，性能值
				List<MOMiddleWareThreadPoolBean> pool = tomService.getThreadPoolPerf(list.get(i).getMoId());
				for (int j = 0; j < pool.size(); j++) {
					String name = pool.get(j).getKpiName();
					long value = pool.get(j).getPerfValue();
					if (name != null && !"".equals(name)) {
						if (name.equals(MiddleWareKPINameDef.CURRTHREADS)) {
							list.get(i).setCurrThreads(value);
						} else if (name.equals(MiddleWareKPINameDef.BUSYTHREADS)) {
							list.get(i).setBusyThreads(value);
						}
					}
				}
			}
			// 查询总记录数
			int totalCount = page.getTotalRecord();
			// 设置至前台显示
			result.put("total", totalCount);
			result.put("rows", list);
			logger.info("结束...获取页面显示数据");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 获取数据源页面显示数据
	 */
	@RequestMapping("/getJdbcDSList")
	@ResponseBody
	public Map<String, Object> getJdbcDSList(MOMiddleWareJdbcDSBean jdbcDSBean,
			HttpServletRequest request) {

		logger.info("开始...获取页面显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MOMiddleWareJdbcDSBean> page = new Page<MOMiddleWareJdbcDSBean>();

		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());

		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String jmxType = request.getParameter("jmxType");
		if (!"".equals(jmxType) && jmxType != null && !"null".equals(jmxType)) {
			int middleID = Integer.parseInt(jmxType);
			if (middleID == 19) {
				paramMap.put("jmxType", MiddleWareKPINameDef.WEBSPHERE);
			} else if (middleID == 20) {
				paramMap.put("jmxType", MiddleWareKPINameDef.TOMCAT);
			}
		}
		if (!"null".equals(request.getParameter("parentMoId"))
				&& !"".equals(request.getParameter("parentMoId"))
				&& request.getParameter("parentMoId") != null) {
			int parentMoId = Integer.parseInt(request
					.getParameter("parentMoId"));
			paramMap.put("parentMoId", parentMoId);
		}
		paramMap.put("dSName", jdbcDSBean.getdSName());
		paramMap.put("ip", jdbcDSBean.getIp());
		page.setParams(paramMap);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 查询分页数据
			List<MOMiddleWareJdbcDSBean> list = tomService.getJdbcDSList(page);
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getMaxWait() != null
							&& !"".equals(list.get(i).getMaxWait())) {
						list.get(i).setMaxWait(
								HostComm.getMsToTime(Long.parseLong(list.get(i)
										.getMaxWait())));
					} else {
						list.get(i).setMaxWait("0毫秒");
					}
				}
			}
			// 查询总记录数
			int totalCount = page.getTotalRecord();
			result.put("total", totalCount);
			result.put("rows", list);

			logger.info("结束...获取页面显示数据");
		} catch (Exception e) {
			logger.error("查询jdbc数据源异常：" + e);
		}

		return result;
	}

	/**
	 * 获取j2ee应用页面显示数据
	 * 
	 */
	@RequestMapping("/getdoJ2eeAppList")
	@ResponseBody
	public Map<String, Object> getdoJ2eeAppList(
			MoMiddleWareJ2eeAppBean j2eeAppBean, HttpServletRequest request) {

		logger.info("开始...获取页面显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MoMiddleWareJ2eeAppBean> page = new Page<MoMiddleWareJ2eeAppBean>();

		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());

		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String jmxType = request.getParameter("jmxType");
		if (!"".equals(jmxType) && jmxType != null && !"null".equals(jmxType)) {
			int middleID = Integer.parseInt(jmxType);
			if (middleID == 19) {
				paramMap.put("jmxType", MiddleWareKPINameDef.WEBSPHERE);
			} else if (middleID == 20) {
				paramMap.put("jmxType", MiddleWareKPINameDef.TOMCAT);
			}
		}
		if (!"null".equals(request.getParameter("parentMoId"))
				&& !"".equals(request.getParameter("parentMoId"))
				&& request.getParameter("parentMoId") != null) {
			int parentMoId = Integer.parseInt(request
					.getParameter("parentMoId"));
			paramMap.put("parentMoId", parentMoId);
		}
		paramMap.put("parentMoName", j2eeAppBean.getParentMoName());
		paramMap.put("appName", j2eeAppBean.getAppName());
		page.setParams(paramMap);

		// 查询分页数据
		List<MoMiddleWareJ2eeAppBean> list = tomService.getJ2eeAppList(page);

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
	 * WebModule
	 */
	@RequestMapping("/toWebModuleList")
	public ModelAndView toWebModuleList(HttpServletRequest request) {

		logger.info("跳转到WebModule信息。。。");
		request.setAttribute("flag", request.getParameter("flag"));
		request.setAttribute("jmxType", request.getParameter("jmxType"));
		request.setAttribute("parentMoId", request.getParameter("parentMoId"));
		request.setAttribute("navigationBar",request.getParameter("navigationBar"));
		return new ModelAndView("monitor/device/webModuleList");
	}

	/**
	 * 获取WebModule数据
	 */
	@RequestMapping("/getWebModuleList")
	@ResponseBody
	public Map<String, Object> getWebModuleList(
			MOMiddleWareWebModuleBean webModuleBean, HttpServletRequest request) {

		logger.info("开始...获取页面显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MOMiddleWareWebModuleBean> page = new Page<MOMiddleWareWebModuleBean>();

		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());

		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String jmxType = request.getParameter("jmxType");
		if (!"".equals(jmxType) && jmxType != null && !"null".equals(jmxType)) {
			int middleID = Integer.parseInt(jmxType);
			if (middleID == 19) {
				paramMap.put("jmxType", MiddleWareKPINameDef.WEBSPHERE);
			} else if (middleID == 20) {
				paramMap.put("jmxType", MiddleWareKPINameDef.TOMCAT);
			}
		}
		if (!"null".equals(request.getParameter("parentMoId"))
				&& !"".equals(request.getParameter("parentMoId"))
				&& request.getParameter("parentMoId") != null) {
			int parentMoId = Integer.parseInt(request
					.getParameter("parentMoId"));
			paramMap.put("parentMoId", parentMoId);
		}
		paramMap.put("parentMoName", webModuleBean.getParentMoName());
		paramMap.put("warName", webModuleBean.getWarName());
		page.setParams(paramMap);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 查询分页数据
			List<MOMiddleWareWebModuleBean> list = tomService
					.getWebModuleList(page);

			// 查询总记录数
			int totalCount = page.getTotalRecord();
			result.put("total", totalCount);
			result.put("rows", list);
			logger.info("结束...获取页面显示数据");
		} catch (Exception e) {
			logger.error("查询WebModule异常：" + e);
		}

		return result;
	}

	/**
	 * 跳转至ServletList界面
	 */
	@RequestMapping("/toServletList")
	public ModelAndView toServletList(HttpServletRequest request)
			throws Exception {

		logger.info("跳转到Servlet信息。。。");
		request.setAttribute("flag", request.getParameter("flag"));
		request.setAttribute("jmxType", request.getParameter("jmxType"));
		request.setAttribute("parentMoId", request.getParameter("parentMoId"));
		request.setAttribute("navigationBar",request.getParameter("navigationBar"));
		return new ModelAndView("monitor/device/servletList");
	}

	/**
	 * 获取Servlet数据
	 */
	@RequestMapping("/getServletList")
	@ResponseBody
	public Map<String, Object> getServletList(
			MOMiddleWareServletBean servletBean, HttpServletRequest request) {

		logger.info("开始...获取页面显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MOMiddleWareServletBean> page = new Page<MOMiddleWareServletBean>();

		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());

		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String jmxType = request.getParameter("jmxType");
		if (!"".equals(jmxType) && jmxType != null && !"null".equals(jmxType)) {
			int middleID = Integer.parseInt(jmxType);
			if (middleID == 19) {
				paramMap.put("jmxType", MiddleWareKPINameDef.WEBSPHERE);
			} else if (middleID == 20) {
				paramMap.put("jmxType", MiddleWareKPINameDef.TOMCAT);
			}
		}
		if (!"null".equals(request.getParameter("parentMoId"))
				&& !"".equals(request.getParameter("parentMoId"))
				&& request.getParameter("parentMoId") != null) {
			int parentMoId = Integer.parseInt(request
					.getParameter("parentMoId"));
			paramMap.put("parentMoId", parentMoId);
		}
		paramMap.put("parentMoName", servletBean.getParentMoName());
		paramMap.put("warName", servletBean.getWarName());
		paramMap.put("ip", servletBean.getIp());
		page.setParams(paramMap);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 查询分页数据
			List<MOMiddleWareServletBean> list = tomService
					.getServletList(page);

			// 查询总记录数
			int totalCount = page.getTotalRecord();
			result.put("total", totalCount);
			result.put("rows", list);
			logger.info("结束...获取页面显示数据");
		} catch (Exception e) {
			logger.error("查询Servlet异常：" + e);
		}
		return result;
	}

	/**
	 * j2ee应用页面
	 */
	@RequestMapping("/toJ2eeAppList")
	public ModelAndView toJ2eeAppList(HttpServletRequest request) {
		request.setAttribute("flag", request.getParameter("flag"));
		request.setAttribute("jmxType", request.getParameter("jmxType"));
		request.setAttribute("parentMoId", request.getParameter("parentMoId"));
		request.setAttribute("navigationBar",request.getParameter("navigationBar"));
		return new ModelAndView("monitor/device/j2eeAppList");
	}

	/**
	 * 线程池页面
	 */
	@RequestMapping("/toThreadPoolList")
	public ModelAndView toThreadPoolList(HttpServletRequest request) {
		request.setAttribute("flag", request.getParameter("flag"));
		request.setAttribute("jmxType", request.getParameter("jmxType"));
		request.setAttribute("parentMoId", request.getParameter("parentMoId"));
		request.setAttribute("navigationBar",request.getParameter("navigationBar"));
		return new ModelAndView("monitor/device/threadPoolList");
	}

	/**
	 * jsbc数据源页面
	 */
	@RequestMapping("/toJDBCDSList")
	public ModelAndView toJDBCDSList(HttpServletRequest request) {
		request.setAttribute("flag", request.getParameter("flag"));
		request.setAttribute("jmxType", request.getParameter("jmxType"));
		request.setAttribute("parentMoId", request.getParameter("parentMoId"));
		request.setAttribute("navigationBar",request.getParameter("navigationBar"));
		return new ModelAndView("monitor/device/jdbcDSList");
	}

	/**
	 * 中间件页面跳转
	 */
	@RequestMapping("/toMiddlewareList")
	public ModelAndView toMiddlewareList(HttpServletRequest request,
			MOMiddleWareJMXBean vo, ModelMap map) throws Exception {
		map.put("jmxType", vo.getJmxType());
		map.put("flag", request.getParameter("flag"));// 隐藏字段标志
		Map<Integer, String> osMap = DictionaryLoader
				.getConstantItems("OperStatus");// 可用状态
		map.put("osMap", osMap);
		map.put("id", request.getParameter("id"));
		map.put("navigationBar",request.getParameter("navigationBar"));
		return new ModelAndView("monitor/device/middleware_list");
	}

	/**
	 * 获取中间件页面显示数据
	 * 
	 * @param vo
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/listMiddleware")
	@ResponseBody
	public Map<String, Object> listMiddleware(MOMiddleWareJMXBean vo,
			HttpServletRequest request) throws Exception {
		logger.info("开始...获取页面显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MOMiddleWareJMXBean> page = new Page<MOMiddleWareJMXBean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String jmxType = request.getParameter("jmxType");
		// 约定为全部小写，如weblogic, websphere, tomcat
		if (!"".equals(jmxType) && jmxType != null && !"null".equals(jmxType)) {
			if (jmxType.equals("17") || jmxType.equals("18")) {
				jmxType = null;
			} else if (jmxType.equals("19")) {
				jmxType = MiddleWareKPINameDef.WEBSPHERE;
			} else if (jmxType.equals("20")) {
				jmxType = MiddleWareKPINameDef.TOMCAT;
			}else if (jmxType.equals("53")) {
				jmxType = MiddleWareKPINameDef.WEBLOGIC;
			}
		}
		paramMap.put("jmxType", jmxType);
		paramMap.put("operStatus", vo.getOperStatus());
		paramMap.put("ip", vo.getIp());
		paramMap.put("jobMiddleAvailable", KPINameDef.JOBURLPOLL);
		page.setParams(paramMap);
		// 查询分页数据
		List<MOMiddleWareJMXBean> list = imiddlewareService.queryList(page);
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
	 * 中间件(Java虚拟机)页面跳转
	 */
	@RequestMapping("/toMiddlewareJvm")
	public ModelAndView toMiddlewareJvm(HttpServletRequest request,
			MOMiddleWareJMXBean vo, ModelMap map) throws Exception {
		map.put("jmxType", vo.getJmxType());
		map.put("flag", request.getParameter("flag"));// 隐藏字段标志
		map.put("parentMoId", request.getParameter("parentMoId"));
		map.put("navigationBar",request.getParameter("navigationBar"));
		return new ModelAndView("monitor/device/middleware_jvm");
	}

	/**
	 * 获取中间件(Java虚拟机)页面显示数据
	 * 
	 * @param vo
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/listMiddlewareJvm")
	@ResponseBody
	public Map<String, Object> listMiddlewareJvm(MOMiddleWareJVMBean vo,
			HttpServletRequest request) throws Exception {
		logger.info("开始...获取页面显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MOMiddleWareJVMBean> page = new Page<MOMiddleWareJVMBean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String jmxType = request.getParameter("jmxType");
		// 约定为全部小写，如weblogic, websphere, tomcat
		if (!"".equals(jmxType) && jmxType != null && !"null".equals(jmxType)) {
			if (jmxType.equals("19")) {
				jmxType = MiddleWareKPINameDef.WEBSPHERE;
			} else if (jmxType.equals("20")) {
				jmxType = MiddleWareKPINameDef.TOMCAT;
			} else if (jmxType.equals("53")) {
				jmxType = MiddleWareKPINameDef.WEBLOGIC;
			}
		}
		if (!"null".equals(request.getParameter("parentMoId"))
				&& !"".equals(request.getParameter("parentMoId"))
				&& request.getParameter("parentMoId") != null) {
			int parentMoId = Integer.parseInt(request
					.getParameter("parentMoId"));
			paramMap.put("parentMoId", parentMoId);
		}
		paramMap.put("jmxType", jmxType);
		paramMap.put("jvmName", vo.getJvmName());
		paramMap.put("ip", vo.getIp());
		page.setParams(paramMap);
		// 查询分页数据
		List<MOMiddleWareJVMBean> list = middleWareCommunityService
				.queryListJVM(page);
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getUpTime() != null
						&& !"".equals(list.get(i).getUpTime())) {
					list.get(i).setUpTime(
							HostComm.getMsToTime(Long.parseLong(list.get(i)
									.getUpTime())));
				} else {
					list.get(i).setUpTime("");
				}
				if (list.get(i).getHeapSizeMax() != null
						&& !"".equals(list.get(i).getHeapSizeMax())) {
					list.get(i).setHeapSizeMax(
							HostComm.getBytesToSize(Long.parseLong(list.get(i)
									.getHeapSizeMax())));
				} else {
					list.get(i).setHeapSizeMax("");
				}

			}
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

	/**
	 * 中间件(内存池)页面跳转
	 */
	@RequestMapping("/toMiddlewareMemPool")
	public ModelAndView toMiddlewareMemPool(HttpServletRequest request,
			MOMiddleWareJMXBean vo, ModelMap map) throws Exception {
		map.put("jmxType", vo.getJmxType());
		map.put("flag", request.getParameter("flag"));// 隐藏字段标志
		map.put("parentMoId", request.getParameter("parentMoId"));
		map.put("navigationBar",request.getParameter("navigationBar"));
		return new ModelAndView("monitor/device/middleware_mempool");
	}

	/**
	 * 获取中间件(内存池)页面显示数据
	 * 
	 * @param vo
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/listMiddlewareMemPool")
	@ResponseBody
	public Map<String, Object> listMiddlewareMemPool(MOMiddleWareMemoryBean vo,
			HttpServletRequest request) throws Exception {
		logger.info("开始...获取页面显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MOMiddleWareMemoryBean> page = new Page<MOMiddleWareMemoryBean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String jmxType = request.getParameter("jmxType");
		// 约定为全部小写，如weblogic, websphere, tomcat
		if (!"".equals(jmxType) && jmxType != null && !"null".equals(jmxType)) {
			if (jmxType.equals("19")) {
				jmxType = MiddleWareKPINameDef.WEBSPHERE;
			} else if (jmxType.equals("20")) {
				jmxType = MiddleWareKPINameDef.TOMCAT;
			} else if (jmxType.equals("53")) {
				jmxType = MiddleWareKPINameDef.WEBLOGIC;
			}
		}
		if (!"null".equals(request.getParameter("parentMoId"))
				&& !"".equals(request.getParameter("parentMoId"))
				&& request.getParameter("parentMoId") != null) {
			int parentMoId = Integer.parseInt(request
					.getParameter("parentMoId"));
			paramMap.put("parentMoId", parentMoId);
		}
		paramMap.put("jmxType", jmxType);
		paramMap.put("memName", vo.getMemName());
		paramMap.put("ip", vo.getIp());
		page.setParams(paramMap);
		// 查询分页数据
		List<MOMiddleWareMemoryBean> list = middleWareCommunityService
				.queryListMemPool(page);
		for (MOMiddleWareMemoryBean mo : list) {
			List<MOMiddleWareMemoryBean> moList = middleWareCommunityService
					.querySnapshot(mo);
			if (moList != null && moList.size() == 2) {
				if (moList.get(0).getKpiName().equals(
						MiddleWareKPINameDef.MEMORYFREE)) {
					mo.setMemoryFree(HostComm.getBytesToSize(moList.get(0)
							.getPerfValue()));
				} else {
					mo.setMemoryUsage(moList.get(0).getPerfValue());
				}
				if (moList.get(1).getKpiName().equals(
						MiddleWareKPINameDef.MEMORYUSAGE)) {
					mo.setMemoryUsage(moList.get(1).getPerfValue());
				} else {
					mo.setMemoryFree(HostComm.getBytesToSize(moList.get(1)
							.getPerfValue()));
				}
			}

			if (mo.getInitSize() != null && !"".equals(mo.getInitSize())) {
				mo.setInitSize(HostComm.getBytesToSize(Long.parseLong(mo
						.getInitSize())));
			} else {
				mo.setInitSize("");
			}
			if (mo.getMaxSize() != null && !"".equals(mo.getMaxSize())) {
				mo.setMaxSize(HostComm.getBytesToSize(Long.parseLong(mo
						.getMaxSize())));
			} else {
				mo.setMaxSize("");
			}
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

	/**
	 * 类加载信息页面跳转
	 */
	@RequestMapping("/toMiddleClassLoadList")
	public ModelAndView toMiddleClassLoadList(HttpServletRequest request,
			MOMiddleWareJMXBean vo, ModelMap map) throws Exception {
		logger.info("跳转到类加载信息。。。");
		map.put("jmxType", vo.getJmxType());
		map.put("flag", request.getParameter("flag"));// 隐藏字段标志
		map.put("parentMoId", request.getParameter("parentMoId"));
		request.setAttribute("parentMoId", request.getParameter("parentMoId"));
		request.setAttribute("navigationBar",request.getParameter("navigationBar"));
		return new ModelAndView("monitor/device/middleClassLoad_list");
	}

	/**
	 * 获取类加载页面显示数据
	 * 
	 * @param vo
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/listMiddleClassLoad")
	@ResponseBody
	public Map<String, Object> listMiddleClassLoad(PerfTomcatClassLoadBean vo,
			HttpServletRequest request) throws Exception {
		logger.info("开始...获取页面显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<PerfTomcatClassLoadBean> page = new Page<PerfTomcatClassLoadBean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String jmxType = request.getParameter("jmxType");
		// 约定为全部小写，如weblogic, websphere, tomcat
		if (!"".equals(jmxType) && jmxType != null && !"null".equals(jmxType)) {
			if (jmxType.equals("19")) {
				jmxType = MiddleWareKPINameDef.WEBSPHERE;
			} else if (jmxType.equals("20")) {
				jmxType = MiddleWareKPINameDef.TOMCAT;
			}
		}
		if (!"null".equals(request.getParameter("parentMoId"))
				&& !"".equals(request.getParameter("parentMoId"))
				&& request.getParameter("parentMoId") != null) {
			int parentMoId = Integer.parseInt(request
					.getParameter("parentMoId"));
			paramMap.put("parentMoId", parentMoId);
		}
		paramMap.put("jmxType", jmxType);
		paramMap.put("serverName", vo.getServerName());
		paramMap.put("ip", vo.getIp());
		paramMap.put("timeBegin", KPINameDef.queryBetweenTime().get("timeBegin"));
		paramMap.put("timeEnd", KPINameDef.queryBetweenTime().get("timeEnd"));
		page.setParams(paramMap);
		// 查询分页数据
		List<PerfTomcatClassLoadBean> list = tomService.getListInfo(page);

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
	 * 查询中间件信息
	 * 
	 * @param mo
	 */
	@RequestMapping("/findMiddleInfo")
	@ResponseBody
	public MOMiddleWareJMXBean findMiddleInfo(MOMiddleWareJMXBean vo) {
		MOMiddleWareJMXBean moMid = imiddlewareService
				.selectMoMidByPrimaryKey(vo.getMoId());
		return moMid;
	}
}
