package com.fable.insightview.platform.exclexport.controller;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 导出用户信息到Excel
 * @author 
 *
 */
@Controller
@RequestMapping("/platform/userExport")
public class UserExport {
	
	
	@RequestMapping("/toUserExport")
	public ModelAndView toUserExport() {
		return new ModelAndView("platform/exclexport/userExport");
	}
	
}
