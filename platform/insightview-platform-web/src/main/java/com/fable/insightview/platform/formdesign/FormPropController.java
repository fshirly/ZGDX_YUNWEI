package com.fable.insightview.platform.formdesign;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.platform.common.util.KeyValPair;
import com.fable.insightview.platform.formdesign.entity.FdFormDefault;
import com.fable.insightview.platform.formdesign.entity.FdWidgetType;
import com.fable.insightview.platform.formdesign.service.IFormDefaultService;
import com.fable.insightview.platform.formdesign.service.IFormService;
import com.fable.insightview.platform.formdesign.service.IFormWidgetService;

/**
 * 主要存放表单控件的属性模板
 * URL映射请遵守/form/input/prop的形式，其他控件类似
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/platform/form")
public class FormPropController {
	
	@Autowired
	IFormWidgetService widgetService;
	
	@Autowired
	IFormService formService;
	
	@Autowired
	IFormDefaultService formDefaultService;

	/**
	 * 控件属性弹出窗口模板
	 * @param attrId 属性ID
	 * @param widgetType 控件类型
	 * @return
	 */
	@RequestMapping("/prop/window")
	public ModelAndView propWindow(String attrId,String widgetType,String formId,ModelAndView model) {
		FdWidgetType formWidget = widgetService.getByWidgetType(widgetType);
		ModelAndView mv = new ModelAndView("/platform/formdesign/propWindow");
		mv.addObject("attrId", attrId);
		mv.addObject("propView", formWidget.getPropUrl());
		mv.addObject("formId", formId);
		return mv;
	}
	
	/**
	 * 弹出层控件弹出模版
	 * @param attrId 属性ID
	 * @param sql 初始化sql
	 * @param formId  
	 * @return 
	 */
	@RequestMapping("/prop/inputPopUp")
	public ModelAndView inputPopUp(String id,String sql,String formId,ModelAndView model) {
		ModelAndView mv = new ModelAndView("/platform/formdesign/inputPopUpPage");
		mv.addObject("id", id);
		mv.addObject("sql", sql);
		mv.addObject("formId", formId);
		return mv;
	}
	/**
	 * Input属性模板
	 * @param attrId 属性ID
	 * @return
	 */
	@RequestMapping("/prop")
	public ModelAndView propInput(int attrId,String propView,int formId) {
		ModelAndView mv = new ModelAndView(propView);
		if (attrId != 0) {
			//查询已有属性的数据，这个查询从Form的JSON中获取
			Map<String, String> attribute = formService.getFormAttrsToMap(attrId,formId);
			mv.addObject("attribute", attribute);
		}
		return mv;
	}
	
	@RequestMapping("/readItems")
	@ResponseBody
	public List<FdFormDefault> readItems(String widgetType) {
		List<FdFormDefault> items = formDefaultService.getItems(widgetType);
		return items;
	}
}
