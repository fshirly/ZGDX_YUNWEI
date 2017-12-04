package com.fable.insightview.permission.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 权限管理
 * 
 * @author 武林
 * 
 */
@Controller
@RequestMapping("/permission")
public class PermissionController {

	@RequestMapping("/toPermissionMain")
	public ModelAndView toPermissionDepartmentList() {
		return new ModelAndView("permission/permission_main");
	}

}
