package com.fable.insightview.platform.serviceReport.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.platform.common.util.DateUtil;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.serviceReport.entity.ServiceReport;
import com.fable.insightview.platform.serviceReport.service.IServiceReportService;



@Controller
@RequestMapping("/serviceReport")
public class ServiceReportController {
	
	@Autowired
	private IServiceReportService serviceReportService;
	
	/**
	 * 服务报告列表页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toServiceReportList")
	public ModelAndView toServiceReportList(String navigationBar){
		return new ModelAndView("platform/serviceReport/serviceReport_list").addObject("navigationBar", navigationBar);
	}
	
	/**
	 * 耗材领用列表查询
	 * @param request
	 * @param materialApply
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listServiceReport")
	@ResponseBody
	public Map<String, Object> listServiceReport(HttpServletRequest request, ServiceReport serviceReport)
			throws Exception {
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		
		Page<ServiceReport> page=new Page<ServiceReport>(); 
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("name", serviceReport.getName());
		paramMap.put("reporter", serviceReport.getReporter());
		paramMap.put("startTime", serviceReport.getStartTime()); 
		paramMap.put("endTime", serviceReport.getEndTime());
		page.setParams(paramMap);
		List<ServiceReport> serviceReports=serviceReportService.getServiceReportByConditions(page);
		int total=serviceReportService.getTotalCount(serviceReport);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", serviceReports);
		return result;
	}
	
	/**
	 * 服务报告新增页面
	 * @return
	 */
	@RequestMapping(value = "/toServiceReportAdd")
	public ModelAndView toServiceReportAdd(){
		return new ModelAndView("platform/serviceReport/serviceReport_add");
	}
	
	/**
	 * 服务报告新增
	 * @param request
	 * @param serviceReport
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/submitServiceReportInfo")
	@ResponseBody
	public boolean submitServiceReportInfo(HttpServletRequest request, ServiceReport serviceReport) throws Exception {
		serviceReportService.newServiceReport(serviceReport);
		return true;
	}
	
	/**
	 * 服务报告修改页面
	 * @return
	 */
	@RequestMapping(value = "/toServiceReportEdit")
	public ModelAndView toServiceReportEdit(HttpServletRequest request){
		String id = request.getParameter("id");
		ModelAndView mv = new ModelAndView();
		ServiceReport serviceReport = serviceReportService.getServiceReportById(Integer.parseInt(id));
		serviceReport.setStrReportTime(DateUtil.date2String(serviceReport.getReportTime()));
		mv.addObject("serviceReport", serviceReport);
		mv.setViewName("platform/serviceReport/serviceReport_add");
		return mv;
	}
	
	/**
	 * 服务报告查看页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toServiceReportView")
	public ModelAndView toServiceReportView(HttpServletRequest request){
		String id = request.getParameter("id");
		ModelAndView mv = new ModelAndView();
		ServiceReport serviceReport = serviceReportService.getServiceReportById(Integer.parseInt(id));
		mv.addObject("serviceReport", serviceReport);
		mv.setViewName("platform/serviceReport/serviceReport_view");
		return mv;
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delServiceReport")
	@ResponseBody
	public boolean delServiceReport(Integer id) throws Exception {
		return serviceReportService.delServiceReport(id);
	}
	
	/**
	 * 批量删除
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/batchDelServiceReport")
	@ResponseBody
	public void batchDelServiceReport(HttpServletRequest request) throws Exception {
		String ids = request.getParameter("ids");
		String[] splitIds = ids.split(",");
		serviceReportService.delByServiceReportIds(splitIds);
	}
	
}
