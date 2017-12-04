package com.fable.insightview.platform.dictionary.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.dictionary.entity.ConstantItemDef;
import com.fable.insightview.platform.dictionary.entity.ConstantTypeDef;
import com.fable.insightview.platform.dictionary.service.IDataDictManageService;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;

@Controller
@RequestMapping("/platform/dataDictTypeManage")
public class DataDictTypeManageController {
	
	@Autowired
	IDataDictManageService dataDictManageService;
	private final Logger logger = LoggerFactory.getLogger(DataDictTypeManageController.class);
	
	/**
	 * 加载字典项页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toConstantTypeView")
	public ModelAndView toConstantTypeView(HttpServletRequest request,
			HttpServletResponse response) {
		String flag=request.getParameter("flag");
		String type=request.getParameter("type");
		if(flag !=null && !"".equals(flag)){
			request.setAttribute("flag", flag);
		}else{
			request.setAttribute("flag", "doInit");
		}
		if(type !=null && !"".equals(type)){
			request.setAttribute("type", type);
		}else{
			request.setAttribute("type", "");
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("platform/dataDictionaryManage/ConstantTypeListView");
		return mv;
	}
	
	/**
	 * 加载数据字典管理列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/loadConstantTypeList")
	@ResponseBody
	public Map<String, Object> loadConstantTypeList(ConstantTypeDef constantTypeDef) {
		logger.info("加载字典类型列表数据");
		Map<String, Object> result = new HashMap<String, Object>();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil.getFlexiGridPageInfo(request);
		String type=request.getParameter("type");
		Map<String, Object> constantTypeMap=null;
		if(!"".equals(type)){
			constantTypeMap = this.dataDictManageService.getConstantTypeList(constantTypeDef, flexiGridPageInfo,type);
		}else{
			constantTypeMap = this.dataDictManageService.getConstantTypeList(constantTypeDef, flexiGridPageInfo);
		}
		
		List<ConstantTypeDef> listConstantType = (List<ConstantTypeDef>) constantTypeMap.get("constantTypeList");
		int count = (Integer)constantTypeMap.get("total");
		result.put("rows", listConstantType);
		result.put("total", count);
		logger.info("加载字典类型列表数据over");
		return result;
	}
	
	/**
	 * 批量删除
	 * 
	 * @param event
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delConstantTypes")
	@ResponseBody
	public boolean delConstantTypes(HttpServletRequest request) throws Exception {
		logger.info("批量删除字典类型");
		String constantTypeIds = request.getParameter("constantTypeIds");
		String[] splitIds = constantTypeIds.split(",");
		for (String strId : splitIds) {
			Integer id =  Integer.parseInt(strId);
			if(dataDictManageService.isLeaf(id)) {
				dataDictManageService.deleteConstantTypeById(id);
			} else {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 删除字典类型
	 * @param constantTypeId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delConstantType")
	@ResponseBody
	public boolean delConstantType(int constantTypeId) throws Exception {
		boolean delTypeflag=false;
		boolean delItemflag=false;
		delItemflag=dataDictManageService.deleteItemByConstantTypeId(constantTypeId);
		if (delItemflag == true) {
			delTypeflag=dataDictManageService.deleteConstantTypeById(constantTypeId);
		}
		if(delTypeflag == true){
			return true;
		}
		return false;
	}
	
	
	/**
	 * 新增字典类型
	 * @param constantItemDef
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addDataDictType")
	@ResponseBody
	public boolean addDataDictType(ConstantTypeDef constantTypeDef)throws Exception{
		logger.info("新增字典类型信息");
		constantTypeDef.setUpdateTime(new Timestamp(new Date().getTime()));
		return dataDictManageService.addDataDictType(constantTypeDef);
	}
	
	/**
	 * 根据条件获取字典类型
	 * @param parentTypeID
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getParentTypeName")
	@ResponseBody
	public ConstantTypeDef getParentTypeName(ConstantTypeDef constantTypeDef)throws Exception{
		List<ConstantTypeDef> list=dataDictManageService.getParentTypeName(constantTypeDef.getParentTypeID());
		ConstantTypeDef bean=list.get(0);
		return bean;
	}
	
	/**
	 * 修改字典类型
	 * @param constantItemDef
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/editDataDictType")
	@ResponseBody
	public boolean editDataDictType(ConstantTypeDef constantTypeDef){
		logger.info("修改字典类型信息");
		try {
			return dataDictManageService.updateDataDictType(constantTypeDef);
		} catch (Exception e) {
			logger.error("修改字典类型异常", e.getMessage());
			return false;
		}
		
	}
	
	/**
	 * 初始化编辑数据
	 * @param constantItemDef
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/initDataDictType")
	@ResponseBody
	public ConstantTypeDef initDataDictType(ConstantTypeDef constantTypeDef)throws Exception{
		logger.info("初始化ID = "+constantTypeDef.getConstantTypeId()+" 的字典类型信息");
		List<ConstantTypeDef> condefs = dataDictManageService.getConstantType(constantTypeDef.getConstantTypeId());
		ConstantTypeDef bean = null;
		if(condefs != null && condefs.size() > 0 ){
			bean = dataDictManageService.getConstantType(constantTypeDef.getConstantTypeId()).get(0);
		}else{
			bean = new ConstantTypeDef();
		}
		String parentName="无";
		if(bean.getParentTypeID() != null && bean.getParentTypeID() != 0){
			int parentId = bean.getParentTypeID();
			parentName=dataDictManageService.getConstantTypeName(parentId).get(0).getConstantTypeCName();
		}
		bean.setParentTypeName(parentName);
		return bean;
	}
	
	/**
	 * 校验字典类别名的唯一性
	 * @param constantTypeDef
	 * @return true:没有存在的  false:已存在该名称
	 */
	@RequestMapping("/checkTypeName")
	@ResponseBody
	public boolean checkTypeName(ConstantTypeDef constantTypeDef){
		return dataDictManageService.checkTypeName(constantTypeDef);
	}
}
