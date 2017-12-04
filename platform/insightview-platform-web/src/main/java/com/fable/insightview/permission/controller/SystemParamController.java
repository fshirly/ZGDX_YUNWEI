package com.fable.insightview.permission.controller;

import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.platform.entity.SystemParamBean;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.service.ISystemParamService;
import com.fable.insightview.platform.sysinit.SystemParamInit;

/**
 * 系统参数设置
 * 
 * @author wsp
 * 
 */
@Controller
@RequestMapping("/permissionSystemParam")
public class SystemParamController {

	// 产品登录页面主图标路径
	public static final String LOGINICON_PATH = "/style/images/logo.png";

	// 产品登入后左上角部分的副图标路径
	public static final String PRODUCTSMALLICON_PATH = "/style/images/logos.png";
	
	// 产品logo路径
	public static final String LOGOICON_PATH = "/style/images/favicon.ico";

	// 产品登录页面主图标名称
	public static final String LOGINICON_NAME = "LoginIcon";
	
	// 产品登入后左上角部分的副图标名称
	public static final String PRODUCTSMALLICON_NAME = "ProductSmallIcon";
	
	// 产品logo名称
	public static final String LOGOICON_NAME = "LogoIcon";
	
	@Autowired
	ISystemParamService systemParamService;
	@Autowired
	private HttpServletRequest request;

	@RequestMapping("/listSystemParam")
	@ResponseBody
	public ModelAndView listSystemParam() throws Exception {
		SystemParamBean systemParamBeanTemp = new SystemParamBean("CopyRight");
		SystemParamBean systemParamBeanTempSession = systemParamService
				.querySystemParam(systemParamBeanTemp);
		request.getSession().setAttribute("CopyRight",
				systemParamBeanTempSession.getParamValue());

		systemParamBeanTemp = new SystemParamBean("Title");
		systemParamBeanTempSession = systemParamService
				.querySystemParam(systemParamBeanTemp);
		request.getSession().setAttribute("Title",
				systemParamBeanTempSession.getParamValue());

		systemParamBeanTemp = new SystemParamBean("LoginIcon");
		systemParamBeanTempSession = systemParamService
				.querySystemParam(systemParamBeanTemp);
		request.getSession().setAttribute("LoginIcon",
				systemParamBeanTempSession.getParamValue());
		
		systemParamBeanTemp = new SystemParamBean("LogoIcon");
		systemParamBeanTempSession = systemParamService
				.querySystemParam(systemParamBeanTemp);
		request.getSession().setAttribute("LogoIcon",
				systemParamBeanTempSession.getParamValue());
		
		systemParamBeanTemp = new SystemParamBean("Version");
		systemParamBeanTempSession = systemParamService
				.querySystemParam(systemParamBeanTemp);
		request.getSession().setAttribute("Version",
				systemParamBeanTempSession.getParamValue());
		
		request.getSession().setAttribute("filePath",SystemParamInit.getValueByKey("fileServerURL"));
		return new ModelAndView("login");
	}

	@RequestMapping("/tosysParmaList")
	@ResponseBody
	public ModelAndView tosysParmaList(String navigationBar) throws Exception {
		ModelAndView mv = new ModelAndView("permission/sysparam_list");
		mv.addObject("navigationBar", navigationBar);
		return mv ;
	}

	/**
	 * 查询列表
	 * 
	 * @param systemParamBean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/listSysParam")
	@ResponseBody
	public Map<String, Object> listSysParam(SystemParamBean systemParamBean)
			throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();

		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);

		List<SystemParamBean> s = systemParamService.sysParamInfo(
				systemParamBean, flexiGridPageInfo);
		// 获取总记录数
		int total = systemParamService.getTotalCount(systemParamBean);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", s);
		return result;
	}

	/**
	 * 根据paramId查询参数信息
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 * @author wsp
	 */
	@RequestMapping("/findDepartmentByParamId")
	@ResponseBody
	public SystemParamBean findDepartmentByParamId(SystemParamBean sysparamBean)
			throws Exception {

		String paramId = request.getParameter("paramId");
		List<SystemParamBean> paramLst = systemParamService
				.getSysParamBeanByConditions("paramId", paramId);
		return paramLst.get(0);
	}

	/**
	 * 修改
	 * 
	 * @param systemParamBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateSysParam")
	@ResponseBody
	public boolean updateSysParam(SystemParamBean systemParamBean)
			throws Exception {
		boolean flag = false;
		int paramID = Integer.parseInt(request.getParameter("paramID"));
		String paramValue = request.getParameter("paramValue");
		String paramName = request.getParameter("paramName");
		int result = systemParamService.updateDepartmentBean(paramValue,
				paramID);
		if (result > 0) {
			request.getSession().setAttribute(paramName, paramValue);
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}

	/**
	 * 查看系统参数
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toShowInfo")
	@ResponseBody
	public ModelAndView toShowInfo(ModelMap map, HttpServletRequest request)
			throws Exception {
		setRes(map, request);
		return new ModelAndView("permission/sysparam_detail");
	}

	/**
	 * 修改系统参数
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toUpdate")
	@ResponseBody
	public ModelAndView toUpdate(ModelMap map, HttpServletRequest request)
			throws Exception {
		setRes(map, request);
		return new ModelAndView("permission/sysparam_modify");
	}

	/**
	 * @param map
	 * @param request
	 */
	private void setRes(ModelMap map, HttpServletRequest request) {
		String imgPath = "";
		String fileServerURL = SystemParamInit.getValueByKey("fileServerURL");
		if (request.getParameter("paramName").contains("Icon")) {
			if (LOGINICON_PATH.equals(request.getParameter("paramValue"))) {
				imgPath = request.getContextPath() + LOGINICON_PATH;
			} else if (PRODUCTSMALLICON_PATH.equals(request.getParameter("paramValue"))) {
				imgPath = request.getContextPath() + PRODUCTSMALLICON_PATH;
			} else if (LOGOICON_PATH.equals(request.getParameter("paramValue"))) {
				imgPath = request.getContextPath() + LOGOICON_PATH;
			} else {
				// 考虑文件server是否打开
				String fileServerPath = SystemParamInit.getValueByKey("fileServerPath");
				if (fileServerStarted(fileServerPath)) {
					imgPath = fileServerURL + request.getParameter("paramValue");
				}else if(LOGINICON_NAME.equals(request.getParameter("paramName"))){
					imgPath = request.getContextPath() + LOGINICON_PATH;
				}else if(PRODUCTSMALLICON_NAME.equals(request.getParameter("paramName"))){
					imgPath = request.getContextPath() + PRODUCTSMALLICON_PATH;
				}else if(LOGOICON_NAME.equals(request.getParameter("paramName"))){
					imgPath = request.getContextPath() + LOGOICON_PATH;
				}
			}
		}
		map.put("paramId", request.getParameter("paramId"));
		map.put("imgPath", imgPath);
	}

	
	/**
	 * 测试http连接，用以判断fileserver是否开启。
	 * @param url
	 * @return 测试结果
	 */
	public boolean fileServerStarted(String url) {
		try {
			URL urlPath = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) urlPath
					.openConnection();
			connection.setConnectTimeout(5000);
			connection.setRequestMethod("GET");
			connection.connect();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				if (connection != null) {
					connection.disconnect();
				}
				return true;
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}catch (ConnectException e) {
			return false;
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return false;
	}
}
