package com.fable.insightview.dashboard.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.platform.common.BaseControllerModified;
import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.entity.WidgetsInfoBean;
import com.fable.insightview.platform.portal.entity.PortalInfoBean;
import com.fable.insightview.platform.portal.service.IPortalInfoService;
import com.fable.insightview.platform.service.ISysUserService;
import com.fable.insightview.platform.service.IWidgetsInfoService;

@Controller
@RequestMapping("/dashboard")
public class DashboardController extends BaseControllerModified {

	@Autowired
	IWidgetsInfoService widgetsInfoService;
	@Autowired
	IPortalInfoService portalInfoService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	ISysUserService sysUserService;

	@RequestMapping("/toDashboardList")
	public ModelAndView toDashboardList() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
		.getSession().getAttribute("sysUserInfoBeanOfSession");
		PortalInfoBean portalBean = new PortalInfoBean();
		portalBean.setPortalType(2);
		portalBean.setCreator(sysUserInfoBeanTemp.getId().intValue());
		List<PortalInfoBean> portalList = portalInfoService.queryPortalInfosByType(portalBean);
		if (portalList.size() == 0) {
			Integer adminId = sysUserService.getSysUserByConditions("UserAccount", "admin").get(0).getUserID();
			portalBean.setCreator(adminId);
			portalList = portalInfoService.queryPortalInfosByType(portalBean);
		}
		Map<String,String> linkedMap = new LinkedHashMap<String,String>();
		for (int i = 0; i < portalList.size(); i++) {
			linkedMap.put(portalList.get(i).getPortalName(), portalList.get(i).getPortalDesc());
		}
		String tabsIndex = request.getParameter("tabsIndex");
		request.setAttribute("tabsIndex", tabsIndex);
		request.setAttribute("map", linkedMap);
		return new ModelAndView("dashboard/dashboard_list");
	}

	@RequestMapping("/doGetDashboardList")
	@ResponseBody
	public Map<String, Object> doGetDashboardList(
			WidgetsInfoBean widgetsInfoBean) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		
		List<WidgetsInfoBean> widgetsInfoLst = widgetsInfoService
				.getWidgetsInfoLst(widgetsInfoBean, getUserIdBySession(request).getId().intValue());
		
		String widgetsLstJson = JsonUtil.toString(widgetsInfoLst);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("widgetsLstJson", widgetsLstJson);
		return result;
	}

}
