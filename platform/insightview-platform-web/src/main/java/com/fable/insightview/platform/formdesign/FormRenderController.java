package com.fable.insightview.platform.formdesign;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.platform.common.util.MapComparator;
import com.fable.insightview.platform.core.util.BeanLoader;
import com.fable.insightview.platform.formdesign.entity.FdForm;
import com.fable.insightview.platform.formdesign.service.IBusinessFormsService;
import com.fable.insightview.platform.formdesign.service.IFormService;
import com.fable.insightview.platform.formdesign.service.IViewText;
import com.fable.insightview.platform.formdesign.vo.ProcessFormLstVO;

/**
 * 存放表单控件的渲染模板
 * 包括编辑界面和查看界面
 * 对于下拉、树等查看界面需要特殊处理的，要提供查看渲染模板
 * 其他不需要特殊处理的如单行文本、多行文本、日期等不需要提供查看模板
 * URL映射请遵守/form/combobox/view/render、/form/combobox/edit/render的形式
 * 其他控件类似
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/platform/form")
public class FormRenderController {
	
	@Autowired
	IFormService formService;
	
	@Autowired
	IBusinessFormsService businessFormService;
	
	/**
	 * 表单渲染模板
	 * @param businessNodeId 节点id，如流程节点
	 * * @param id 表单业务数据id
	 * @return
	 */
	/**
	 * @param businessNodeId
	 * @param id
	 * @return
	 */
	@RequestMapping("edit/render")
	public ModelAndView editRender(String businessNodeId,int id) {
		ModelAndView mv = new ModelAndView("platform/formdesign/form_edit");
		return initFormData(businessNodeId, id, mv);
	}
	
	/**
	 * 查询表单数据，查看、编辑页面公用
	 * @param businessNodeId
	 * @param id
	 * @param mv
	 * @return
	 */
	private ModelAndView initFormData(String businessNodeId,int id,ModelAndView mv){
		List<ProcessFormLstVO> forms = businessFormService.findProcessFormInfoLst(businessNodeId);
		//目前按每个节点只有一个表单处理
		if (null != forms && !forms.isEmpty()) {
			ProcessFormLstVO formVO = forms.get(0);
			if (null != formVO) {
				// Begin... Modified by maowei, 2014年12月11日 下午4:19:29
				// 将属性按照seq进行排序
				List<Map<String, String>> attributes = formService
						.getFormAttributes(formVO.getId());
				Collections.sort(attributes, new MapComparator());
				mv.addObject("attributes", attributes);
				// End... 
				
				FdForm form = formService.getById(formVO.getId());
				mv.addObject("form", form);
				//根据业务数据ID查询该表单对应的业务数据
				String formTable = form.getFormTable();
				Map<String, String> formData = formService.queryFormInstanceInfo(formTable, id);
				mv.addObject("formData", formData);
			}
		}
		return mv;
	}

	/**
	 * 返回单个编辑控件的渲染模板和渲染所需要的数据(比如id,标签名,列名,数据值)
	 * @param id 控件属性id
	 * @param editView 控件编辑界面
	 * @param formId 控件属性所属表单id
	 * @param value 控件属性对应的业务数据值
	 * @return
	 */
	@RequestMapping("/prop/edit/render")
	public ModelAndView propEditRender(int id,String editView,int formId,String value) {
		ModelAndView mv = new ModelAndView(editView);
		//调用接口获取该属性对应的数据JSON
		Map<String, String> attribute = formService.getFormAttrsToMap(id,formId);
		mv.addObject("attribute", attribute);
		mv.addObject("value", value);
		return mv;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/prop/value/init")
	@ResponseBody
	public List<Map<String, String>> valueInit(String initSQL) {
		if (StringUtils.isEmpty(initSQL)) {
			return Collections.EMPTY_LIST;
		}
		return formService.valueInit(initSQL);
	} 
	
	/**
	 * 表单渲染模板
	 * @param businessNodeId 节点id，如流程节点
	 * * @param id 表单业务数据id
	 * @return
	 */
	@RequestMapping("view/render")
	public ModelAndView viewRender(String businessNodeId,int id) {
		ModelAndView mv = new ModelAndView("platform/formdesign/form_view");
		return initFormData(businessNodeId, id, mv);
	}
	
	/**
	 * 返回单个查看控件的渲染模板和渲染所需要的数据(比如id,标签名,列名,数据值)
	 * @param id 控件属性id
	 * @param editView 控件查看界面
	 * @param formId 控件属性所属表单id
	 * @param value 控件属性对应的业务数据值
	 * @return
	 */
	@RequestMapping("/prop/view/render")
	public ModelAndView propViewRender(int id,String viewUrl,int formId,String value) {
		ModelAndView mv = new ModelAndView("/platform/formdesign/render/labelRender");
	
		//调用接口获取该属性对应的数据JSON
		Map<String, String> attribute = formService.getFormAttrsToMap(id,formId);
		IViewText viewText = (IViewText) BeanLoader.getBean("platform."+viewUrl);
		value = viewText.getText(value, attribute);
		mv.addObject("attribute", attribute);
		mv.addObject("value", value);
		return mv;
	}
}
