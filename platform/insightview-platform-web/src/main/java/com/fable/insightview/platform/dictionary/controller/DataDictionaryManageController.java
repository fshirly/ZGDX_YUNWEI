package com.fable.insightview.platform.dictionary.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.fable.insightview.platform.dictionary.dao.IDataDictManageDao;
import com.fable.insightview.platform.dictionary.entity.ConstantItemDef;
import com.fable.insightview.platform.dictionary.entity.ConstantTypeDef;
import com.fable.insightview.platform.dictionary.service.IDataDictManageService;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.managedDomain.entity.ManagedDomain;

@Controller
@RequestMapping("/platform/dataDictManage")
public class DataDictionaryManageController {
	
	@Autowired
	IDataDictManageService dataDictManageService;
	@Autowired
	IDataDictManageDao dataDictManageDao;
	private final Logger logger = LoggerFactory.getLogger(DataDictionaryManageController.class);
	
	@RequestMapping(value = "/dataDictManageView")
	public ModelAndView dataDictManageView(String navigationBar) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("navigationBar", navigationBar);
		mv.setViewName("platform/dataDictionaryManage/DataDictionaryManageView");
		mv.addObject("navigationBar", navigationBar);
		return mv;
	}
	
	/**
	 * 加载数据字典管理列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/loadDataDictionaryList")
	@ResponseBody
	public Map<String, Object> loadDataDictionaryList(ConstantItemDef constantItemDef) {
		logger.info("加载数据字典管理列表");
		Map<String, Object> result = new HashMap<String, Object>();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil.getFlexiGridPageInfo(request);
		Map<String, Object> constantItemMap = this.dataDictManageService.getConstantItemList(constantItemDef, flexiGridPageInfo);
		List<ConstantItemDef> listConstantItem = (List<ConstantItemDef>) constantItemMap.get("constantItemList");
		int count = (Integer)constantItemMap.get("total");
		result.put("rows", listConstantItem);
		result.put("total", count);
		return result;
	}
	
	/**
	 * 根据数据字典类型获取数据字典项
	 * @param constantItemDef
	 * @return
	 */
	@RequestMapping("/findDictDataByDictTypeId")
	@ResponseBody
	public List<ConstantItemDef> findDictDataByDictTypeId(ConstantItemDef constantItemDef) {
		List<ConstantItemDef> listConstantItem = this.dataDictManageService.findDictDataByDictTypeId(constantItemDef.getConstantTypeId());
		return listConstantItem;
	}
	
	/**
	 * 初始化树
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findConstantTypeTreeVal")
	@ResponseBody
	public Map<String, Object> findConstantTypeTreeVal() throws Exception {
		logger.info("加载字典类型树");
		List<ConstantTypeDef> menuLst = dataDictManageService.getConstantTypeTreeVal();
		String menuLstJson = JsonUtil.toString(menuLst);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("menuLstJson", menuLstJson);
		return result;
	}
	
	/**
	 * 新增字典项
	 * @param constantItemDef
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addDataDictItem")
	@ResponseBody
	public boolean addDataDictItem(ConstantItemDef constantItemDef){
		logger.info("新增字典项数据");
		try {
			return dataDictManageService.addDataDictItem(constantItemDef);
		} catch (Exception e) {
			logger.error("新增字典项数据异常！",e.getMessage());
			return false;
		}
		
	}
	
	/**
	 * 根据类型ID获取类型信息
	 * @param constantTypeId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getConstantTypeName")
	@ResponseBody
	public ConstantTypeDef getConstantTypeName(int constantTypeId)throws Exception{
//		List<ConstantItemDef> list=dataDictManageService.getConstantTypeName(constantItemDef.getConstantTypeId());
		List<ConstantTypeDef> list=dataDictManageService.getConstantTypeName(constantTypeId);
		ConstantTypeDef bean=list.get(0);
		return bean;
	}
	
	
	/**
	 * 批量删除
	 * 
	 * @param event
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delConstantItems")
	@ResponseBody
	public boolean delConstantItems(HttpServletRequest request){
		logger.info("批量删除字典项");
		boolean flag=false;
		String ids = request.getParameter("ids");
		logger.info("被删字典项ID："+ids);
		int typeOrItem = Integer.parseInt(request.getParameter("typeOrItem"));
		String[] splitIds = ids.split(",");
		for (String strId : splitIds) {
			Integer id =  Integer.parseInt(strId);
			if(typeOrItem==1){
				if(dataDictManageService.isLeaf(id)==true) {
					flag=dataDictManageService.deleteConstantTypeById(id);
				} else {
					flag=false;
				}
			}else{
				flag=dataDictManageService.deleteConstantItemById(id);
			}
			
		}
		return flag;
	}
	
	@RequestMapping("/delConstantItem")
	@ResponseBody
	public boolean delConstantItem(int id,int typeOrItem){
		logger.info("删除字典项");
		boolean flag=false;
		try {
//			boolean isleaf=dataDictManageService.isLeafType(id);
			if(typeOrItem==1){
				if(dataDictManageService.isLeaf(id)) {
					flag=dataDictManageService.deleteConstantTypeById(id);
				} else {
					flag=false;
				}
			}else{
				flag=dataDictManageService.deleteConstantItemById(id);
			}
			return flag;
		} catch (Exception e) {
			logger.error("删除字典项异常", e.getMessage());
			return false;
		}

		
	}
	
	
	
	/**
	 * 修改字典项
	 * @param constantItemDef
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/editDataDictItem")
	@ResponseBody
	public boolean editDataDictItem(ConstantItemDef constantItemDef){
		logger.info("修改id = "+constantItemDef.getId()+"的字典项信息");
		boolean flag=false;
		try {
			ConstantTypeDef constantTypeDef=new ConstantTypeDef();
//			boolean isleaf=dataDictManageService.isLeafType(constantItemDef.getId());
			if(constantItemDef.getTypeOrItem()==1){
				constantTypeDef.setConstantTypeId(constantItemDef.getId());
				constantTypeDef.setConstantTypeName("");
				constantTypeDef.setConstantTypeCName(constantItemDef.getConstantItemName());
				constantTypeDef.setParentTypeID(Integer.parseInt(constantItemDef.getConstantTypeId()));
				constantTypeDef.setRemark(constantItemDef.getRemark());
				flag=dataDictManageService.updateDataDictType(constantTypeDef);
			}else{
				flag=dataDictManageService.updateDataDictItem(constantItemDef);
			}
			return flag;
		} catch (Exception e) {
			logger.error("修改字典项异常",e.getMessage());
			return false;
		}
		
	}
	
	/**
	 * 初始化编辑数据
	 * @param constantItemDef
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/initDataDictItem")
	@ResponseBody
	public ConstantItemDef initDataDictItem(ConstantItemDef constantItemDef)throws Exception{
		logger.info("获取id为"+constantItemDef.getId()+"的字典项信息");
		ConstantItemDef bean=new ConstantItemDef();
//		boolean isleaf=dataDictManageService.isLeafType(constantItemDef.getId());
		if(constantItemDef.getTypeOrItem()==1){
			ConstantTypeDef constantTypeDef=new ConstantTypeDef();
			constantTypeDef.setConstantTypeId(constantItemDef.getId());
			ConstantTypeDef typeBean=dataDictManageService.getConstantType(constantTypeDef.getConstantTypeId()).get(0);
			int parentId=typeBean.getParentTypeID();
			String parentName="无";
			if(parentId !=0 ){
				parentName=dataDictManageService.getConstantTypeName(parentId).get(0).getConstantTypeCName();
			}
			typeBean.setParentTypeName(parentName);
			bean.setId(typeBean.getConstantTypeId());
			bean.setConstantItemName(typeBean.getConstantTypeCName());
			bean.setConstantTypeId(typeBean.getParentTypeID()+"");
			bean.setParentTypeName(typeBean.getParentTypeName());
			bean.setRemark(typeBean.getRemark());
		}else{
			bean=dataDictManageService.getConstantItem(constantItemDef.getId()).get(0);
			int parentId=Integer.parseInt(bean.getConstantTypeId());
			String parentName="";
			if(parentId !=0 ){
				parentName=dataDictManageService.getConstantTypeName(parentId).get(0).getConstantTypeCName();
			}
			logger.info("字典项父类名为："+parentName);
			bean.setParentTypeName(parentName);
		}
		
		return bean;
	}
	
	
	/**
	 * 判断是否为叶子节点
	 * @param constantItemDef
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/isLeaf")
	@ResponseBody
	public boolean isLeaf(int id)throws Exception{
		return dataDictManageService.isLeaf(id);
	}
	
	
	/**
	 * 新增时默认父类型名
	 * @param constantItemDef
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getParentTypeName")
	@ResponseBody
	public ConstantItemDef getParentTypeName(ConstantItemDef constantItemDef)throws Exception{
		String parentName=null;
		if(constantItemDef.getConstantTypeId() !=null && !"0".equals(constantItemDef.getConstantTypeId())){
			List<ConstantTypeDef> condefs = dataDictManageService.getConstantTypeName(Integer.parseInt(constantItemDef.getConstantTypeId()));
			if(condefs != null && condefs.size() > 0 ){
				parentName = dataDictManageService.getConstantTypeName(Integer.parseInt(constantItemDef.getConstantTypeId())).get(0).getConstantTypeCName();
			}else{
				parentName="";
			}
		}else{
			parentName="";
		}
		ConstantItemDef bean=new ConstantItemDef();
		bean.setParentTypeName(parentName);
		return bean;
	}
	
	
	/**
	 * 查找节点ID
	 * @param constantTypeCName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/searchTreeNodes")
	@ResponseBody
	public ConstantTypeDef searchTreeNodes(String constantTypeCName)throws Exception{
		return dataDictManageService.getTypeIdByTypeName(constantTypeCName);
	}
	
	
	/**
	 * 检查constantItemId是否已经存在
	 * @param constantTypeCName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/checkItemId")
	@ResponseBody
	public boolean checkItemId(ConstantItemDef constantItemDef)throws Exception{
		return dataDictManageService.checkItemId(constantItemDef);
	}
	
	/**
	 * 检查constantItemName是否已经存在
	 * @param constantItemDef
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/checkDataDictItemName")
	@ResponseBody
	public boolean checkDataDictItemName(ConstantItemDef constantItemDef)throws Exception{
		return dataDictManageService.checkDataDictItemName(constantItemDef);
	}
	
	/**
	 * 检查constantItemAlias是否已经存在
	 * @param constantItemDef
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/checkDataDictItemAlias")
	@ResponseBody
	public boolean checkDataDictItemAlias(ConstantItemDef constantItemDef)throws Exception{
		return dataDictManageService.checkDataDictItemAlias(constantItemDef);
	}
}
