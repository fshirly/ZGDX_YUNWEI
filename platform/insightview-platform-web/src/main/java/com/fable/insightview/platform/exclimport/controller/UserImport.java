package com.fable.insightview.platform.exclimport.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 从Excel导入用户信息
 * @author 
 *
 */
@Controller
@RequestMapping("/platform/userImport")
public class UserImport {
	
	/**
	 * 跳转到用户导入页面
	 * @return
	 */
	@RequestMapping("/toUserImport")
	public ModelAndView toUserImport() {
		return new ModelAndView("platform/exclimport/userImport");
	}
	
	
	
}
