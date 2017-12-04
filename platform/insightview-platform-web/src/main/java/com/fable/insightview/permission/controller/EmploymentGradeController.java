package com.fable.insightview.permission.controller;

import java.util.HashMap;
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

import com.fable.insightview.permission.form.EmploymentGradeForm;
import com.fable.insightview.platform.entity.EmploymentGradeBean;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.service.IEmploymentGradeService;

/**
 * 兴趣爱好
 * 
 * @author 汪朝
 * 
 */
@Controller
@RequestMapping("/permissionEmploymentGrade")
public class EmploymentGradeController {

	@Autowired
	IEmploymentGradeService employmentGradeService;
	@Autowired
	private HttpServletRequest request;

	@RequestMapping("/toEmploymentGradeList")
	public ModelAndView toEmploymentGradeList() {
		return new ModelAndView("permission/employmentgrade_list");
	}

	@RequestMapping("/addEmploymentGrade")
	@ResponseBody
	public boolean addEmploymentGrade(EmploymentGradeBean employmentGradeBean) throws Exception {
		return employmentGradeService.addEmploymentGrade(employmentGradeBean);
	}

	@RequestMapping("/delEmploymentGrade")
	@ResponseBody
	public boolean delEmploymentGrade(EmploymentGradeForm employmentGradeForm) throws Exception {
		EmploymentGradeBean employmentGradeBean = new EmploymentGradeBean();
		//EmploymentGradeBean.setUserID(EmploymentGradeForm.getUserId());

		return employmentGradeService.delEmploymentGradeById(employmentGradeBean);
	}

	@RequestMapping("/listEmploymentGrade")
	@ResponseBody
	public Map<String, Object> listEmploymentGrade(EmploymentGradeForm employmentGradeForm)
			throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();

		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		// 获得当前页数
		String pageIndex = request.getParameter("page");
		// 获得每页数据最大量
		String pageSize = request.getParameter("rows");

		EmploymentGradeBean EmploymentGradeBean = new EmploymentGradeBean();
		//EmploymentGradeBean.setUserName("");

		List<EmploymentGradeBean> s = employmentGradeService.getEmploymentGradeByConditions(
				EmploymentGradeBean, flexiGridPageInfo);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", 100);
		result.put("rows", s);
		return result;
	}
}
