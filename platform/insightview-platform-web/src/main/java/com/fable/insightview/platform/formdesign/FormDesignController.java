package com.fable.insightview.platform.formdesign;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.common.util.MapComparator;
import com.fable.insightview.platform.entity.FdSysValidateRule;
import com.fable.insightview.platform.formdesign.entity.FdForm;
import com.fable.insightview.platform.formdesign.entity.FdFormAttributes;
import com.fable.insightview.platform.formdesign.entity.FdWidgetType;
import com.fable.insightview.platform.formdesign.service.IBusinessAttributesService;
import com.fable.insightview.platform.formdesign.service.IBusinessFormsService;
import com.fable.insightview.platform.formdesign.service.IFormAttributesService;
import com.fable.insightview.platform.formdesign.service.IFormService;
import com.fable.insightview.platform.formdesign.service.IFormWidgetService;
import com.fable.insightview.platform.formdesign.vo.ProcessFormLstVO;
import com.fable.insightview.platform.formdesign.vo.ProcessFormVO;
import com.fable.insightview.platform.service.IFdSysValidateRuleService;

/**
 * 表单设计器的核心处理 表单的新增、修改、删除、查看
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/platform/form/design")
public class FormDesignController {

	@Autowired
	private IFormWidgetService formWidgetService;

	@Autowired
	private IFormService formService;

	@Autowired
	private IBusinessFormsService businessFormsService;

	@Autowired
	private IFormAttributesService formAttributesService;

	@Autowired
	private IBusinessAttributesService businessAttributesService;

	@Autowired
	private IFdSysValidateRuleService fdSysValidateRuleService;

	/**
	 * 获取所有表单控件类型
	 * 
	 * @return
	 * @author Maowei
	 */
	@RequestMapping("/findFormWidgetTypeLst")
	@ResponseBody
	public List<FdWidgetType> findFormWidgetTypeLst() {
		List<FdWidgetType> lst = formWidgetService.getAllFormWidgetType();
		return lst;
	}

	/**
	 * 根据Category，获取表单控件类型
	 * 
	 * @return
	 * @author Maowei
	 */
	@RequestMapping("/findWidgetTypeByCategory")
	@ResponseBody
	public List<FdWidgetType> findWidgetTypeByCategory(Integer category) {
		List<FdWidgetType> lst = formWidgetService.getByCategory(category);
		return lst;
	}

	/**
	 * 根据WidgetType，获取表单控件类型
	 * 
	 * @return
	 * @author Maowei
	 */
	@RequestMapping("/findByWidgetType")
	@ResponseBody
	public FdWidgetType findByWidgetType(String widgetType) {
		FdWidgetType formWidgetType = formWidgetService
				.getByWidgetType(widgetType);
		return formWidgetType;
	}

	/**
	 * 表单编辑界面
	 * 
	 * @param formId
	 *            表单ID
	 * @return
	 */
	@RequestMapping("/view/{formId}")
	public ModelAndView formView(@PathVariable int formId, String businessId) {
		ModelAndView mv = new ModelAndView("/platform/formdesign/formdesign");
		List<Map<String, String>> attributes = formService
				.getFormAttributes(formId);
		
		// 将属性按照seq进行排序
		Collections.sort(attributes, new MapComparator());
		
		mv.addObject("attributes", attributes);
		FdForm form = formService.getById(formId);
		mv.addObject("form", form);
		mv.addObject("businessId", businessId);
		return mv;
	}
	
	/**
	 * 新增表单界面
	 * 
	 * @return
	 */
	@RequestMapping("/view/add")
	public ModelAndView formAdd(String businessNodeId, String businessType,
			String businessId) {
		ModelAndView mv = new ModelAndView("/platform/formdesign/formdesign");
		mv.addObject("businessNodeId", businessNodeId);
		mv.addObject("businessType", businessType);
		mv.addObject("businessId", businessId);
		return mv;
	}

	/**
	 * 查询流程表单列表
	 * 
	 * @return
	 * @author Maowei
	 */
	@RequestMapping("/findProcessFormLst")
	@ResponseBody
	public List<ProcessFormLstVO> findProcessFormInfoLst(String businessNodeId) {
		List<ProcessFormLstVO> lst = businessFormsService
				.findProcessFormInfoLst(businessNodeId);
		return lst;
	}

	/**
	 * 提交表单
	 * 
	 * @return
	 * @author Maowei
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/submitForm")
	@ResponseBody
	public Integer submitForm(ProcessFormVO processFormVO) {
		FdForm form = new FdForm();
		if (null == processFormVO.getId()) {
			/**
			 * 如果表单的id不存在，认为是新增表单
			 */

			// 1.Fd_Form表新增记录
			form = formService.insertForm(form, processFormVO);

			// 2.Fd_Business_Forms新增记录
			businessFormsService.insertBForm(form, processFormVO);

			// 3.根据生成的Form的Id,创建表Form_T_id
			if (null != form) {
				formService.addTable("Form_T_" + form.getId(), "id");
			}

			//循环遍历所有新建的属性(因为表单是新建的,所以只有新建属性,没有更新和删除的属性)
			List<Map<String, String>> newAttrs = processFormVO.getNewAttrs();
			for (Map<String, String> map : newAttrs) {
				// 4.Fd_Form_Attributes新增记录
				FdFormAttributes formAttr = formAttributesService
						.insertFormAttributes(form, map);

				// 5.每个attr,生成属性Id后添加到attr的Map中
				map.put("id", formAttr.getId().toString());
				map.put("columnName", formAttr.getColumnName());

				// 6.根据validator获取校验规则的基本信息，放到Map中
				if (StringUtils.isNotEmpty(map.get("validator"))) {
					FdSysValidateRule validateRule = fdSysValidateRuleService
							.getById(Integer.valueOf(map.get("validator")));
					map.put("validatorValue", validateRule.getValidatorValue());
					map.put("validatorMsg", validateRule.getValidatorMsg());
					map.put("validType", validateRule.getValidType());
				}

				// 7.Form_T_id添加attr字段
				formService.addColumn("Form_T_" + form.getId()/*表名*/,
						formAttr.getColumnName()/*列名*/, 
						map.get("dataType")/*数据类型,参考mysql_type.xml和oracle_type.xml*/,
						Integer.valueOf(map.get("datalength")));

				// 8.Fd_Business_Attributes新增记录
				if (null != map.get("isTableDisplay")
						&& Integer.valueOf(map.get("isTableDisplay")) == 1) {
					businessAttributesService.insertBAttributes(form, formAttr,
							processFormVO);
				}
			}

			// 9.获取id后的newAttrs+buttons转成JSON格式
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("attributes", newAttrs);
			resultMap.put("buttons", processFormVO.getButtons());
			String attrsJson = JsonUtil.map2Json(resultMap);

			// 10.把attributes和formTable更新到Fd_Form表的相应记录
			form.setFormAttributes(attrsJson);//以json形式存储属性,处理时再把json转为map形式
			form.setFormTable("Form_T_" + form.getId());
			formService.update(form);

		} else {
			/**
			 * 如果表单的id存在，认为是编辑表单
			 */
			// 1.更新Fd_Form表的基本信息，表单名称、布局
			form = formService.getById(processFormVO.getId());
			form.setFormName(processFormVO.getFormName());
			form.setFormLayout(processFormVO.getLayout());

			//获取原来的attributes
			String oldAttrsJson = form.getFormAttributes();
			Map<String, Object> originalMap = JsonUtil.json2Map(oldAttrsJson);
			List<Map<String, String>> attrsMap = (List<Map<String, String>>) originalMap
					.get("attributes");

			/**
			 *  2.newAttrs:Fd_Form_Attributes新增记录
			 */
			List<Map<String, String>> newAttrs = processFormVO.getNewAttrs();
			if (null != newAttrs && newAttrs.size() > 0) {
				for (Map<String, String> map : newAttrs) {
					FdFormAttributes formAttr = formAttributesService
							.insertFormAttributes(form, map);

					// 3.每个attr,生成属性Id后添加到attr的Map中
					map.put("id", formAttr.getId().toString());
					map.put("columnName", formAttr.getColumnName());

					// 4.Form_T_id添加attr字段
					formService.addColumn("Form_T_" + form.getId(),
							formAttr.getColumnName(), map.get("dataType"),
							Integer.valueOf(map.get("datalength")));

					// 5.Fd_Business_Attributes新增记录
					if (null != map.get("isTableDisplay")
							&& Integer.valueOf(map.get("isTableDisplay")) == 1) {
						businessAttributesService.insertBAttributes(form,
								formAttr, processFormVO);
					}

					// 6.把组装后的newAttrs添加到原来的attributes中
					attrsMap.add(map);
				}
			}

			/**
			 *  对于updateAttrs
			 */
			List<Map<String, String>> updataAttrs = processFormVO
					.getUpdateAttrs();
			if (null != updataAttrs && updataAttrs.size() > 0) {
				for (Map<String, String> map : updataAttrs) {
					String attrId = map.get("id");

					// 7.更新Fd_Form_Attributes的相应记录
					formAttributesService.updateFormAttributes(map);

					// 8.把updateAttrs的记录替换掉原来的attribtes中的相应记录
					for (Map<String, String> attrMap : attrsMap) {
						if (attrId.equals(attrMap.get("id"))) {
							attrsMap.remove(attrMap);
							attrsMap.add(map);
							break;
						}
					}
				}
			}

			/**
			 *  对于deleteAttrs
			 */
			List<Map<String, String>> deleteAttrs = processFormVO
					.getDeleteAttrs();
			if (null != deleteAttrs && deleteAttrs.size() > 0) {
				for (Map<String, String> map : deleteAttrs) {
					// 9.删除Fd_Business_Attributes相应记录
					businessAttributesService.deleteByAttributeId(Integer
							.valueOf(map.get("id")));

					// 10.删除Fd_Form_Attributes相应记录
					formAttributesService
							.delete(formAttributesService.getById(Integer
							.valueOf(map.get("id"))));;

					// 11.删除表单Table的相应字段
					formService.removeColumn("Form_T_" + form.getId(),
							map.get("columnName"), false);

					// 12.把deleteAttrs的记录从原来的attributes中删除
					for (Map<String, String> attrMap : attrsMap) {
						if (map.get("id").equals(attrMap.get("id"))) {
							attrsMap.remove(attrMap);
							break;
						}
					}
				}
			}
			
			/**
			 * 编辑表单时，移动了已有的控件位置，对于控件顺序的序号更新
			 */
			Map<String, String> positions = processFormVO.getPositionAttrs(); // Map中key是控件id值，value是更新后的seq值
			if (null != positions && !positions.isEmpty()) {
				for (String key : positions.keySet()) {
					if (null != key) {
						String pValue = positions.get(key);
						for (Map<String, String> pMap : attrsMap) {
							if (key.equals(pMap.get("id"))) {
								pMap.put("seq", pValue); // 直接put进去新的序号值，会覆盖原来的序号值
								break;
							}
						}
					}
				}
			}
			
			// 13.把最终的attribtes更新到对应的表单记录中（Fd_Form）
			originalMap.put("attributes", attrsMap);
			form.setFormAttributes(JsonUtil.map2Json(originalMap));//以json形式存储属性,处理时再把json转为map形式
			formService.update(form);

		}

		return form.getId();
	}

}
