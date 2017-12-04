package com.fable.insightview.platform.manufacturer.controller;

import java.util.ArrayList;
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

import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.manufacturer.entity.ManufacturerInfoBean;
import com.fable.insightview.platform.manufacturer.service.IManufacturerService;


/**
 * 厂商控制器
 * @author zhurt
 * @version
 */

@Controller
@RequestMapping("/platform/manufacturer")
public class ManufacturerController {
	
	@Autowired
	IManufacturerService  manufacturerService;
	
	private final Logger logger = LoggerFactory.getLogger(ManufacturerController.class);
	
	@RequestMapping("/toManufacturerList")
	public ModelAndView toManufacturerList(String navigationBar) {
		logger.info("访问页面manufacturer_list.jsp");
		ModelAndView mv = new ModelAndView("platform/manufacturer/manufacturer_list");
		mv.addObject("navigationBar", navigationBar);
		return mv;
	}
	
	/**
	 * 查询所有可用的制造商厂家，用于下拉框
	 * @param manufacturerInfoBean
	 * @return
	 */
	@RequestMapping("/listManufacture")
	public List<ManufacturerInfoBean> listManufacture() {
		logger.info("正在执行listManufacture()方法");
		List<ManufacturerInfoBean> lst = manufacturerService.queryAllManufacturerInfo();
		return lst;
	}
	
	/**
	 * 判断厂商名是否唯一	
	 * @param manufacturerInfoBean
	 * @return
	 */
	@RequestMapping("/checkNameUnique")
	@ResponseBody
	public boolean checkNameUnique(ManufacturerInfoBean manufacturerInfoBean){
		logger.info("执行供应商名唯一性验证checkNameUnique()方法，输入的 'resManuFacturerName'值为: "+manufacturerInfoBean.getResManuFacturerName());
		try {
				manufacturerInfoBean = manufacturerService.getManufacturerInfoBeanByConditions("resManuFacturerName", manufacturerInfoBean.getResManuFacturerName()+""); 
		} catch (Exception e) {
			logger.error("验证异常："+e.getMessage(),manufacturerInfoBean);
		}
		return (null == manufacturerInfoBean) ? true:false;
	}
	
	/**
	 * 判断厂商名是否唯一	
	 * @param manufacturerInfoBean
	 * @return
	 */
	@RequestMapping("/checkNameUniqueBeforeUpdate")
	@ResponseBody
	public boolean checkNameUniqueBeforeUpdate(ManufacturerInfoBean manufacturerInfoBean){
				return   manufacturerService.checkName(manufacturerInfoBean);
	}
	
	/**
	 * 查询所有厂商信息 
	 * @param manufacturerInfoBean
	 * @return
	 */
	@RequestMapping("/findManufacturer")
	@ResponseBody
	public ManufacturerInfoBean findManufacturer(ManufacturerInfoBean manufacturerInfoBean) {
		logger.info("执行findManufacturer()方法，根据resManufacturerId获取供应商信息，resManufacturerId的值为："+manufacturerInfoBean.getResManuFacturerId(),"resManufacturerId");
		try{
			manufacturerInfoBean =manufacturerService.getManufacturerInfoBeanByConditions("resManufacturerId", manufacturerInfoBean.getResManuFacturerId()+"");
		}catch (Exception e) {
			logger.error("异常："+e.getMessage(),manufacturerInfoBean);
		}
			logger.info("findManufacturer()方法执行结束");
		return manufacturerInfoBean;
	}
	
	/**
	 * 增加厂商
	 * @param manufacturerInfoBean
	 * @return
	 */
	@RequestMapping("/addManufacturer")
	@ResponseBody
	public boolean addManufacturer(ManufacturerInfoBean manufacturerInfoBean) {
		logger.info("执行addManufacturer()方法，新增厂商信息");
		try {
			manufacturerService.addManufacturer(manufacturerInfoBean);
		} catch (Exception e) {
			logger.error("插入异常："+e.getMessage(),manufacturerInfoBean);
			return false;
		}
		logger.info("addManufacturer()方法执行结束");
		return true;
	}
	
	/**
	 * 删除厂商
	 * @param manufacturerInfoBean
	 * @return
	 */
	@RequestMapping("/delManufacturer")
	@ResponseBody
	public boolean delManufacturer(ManufacturerInfoBean manufacturerInfoBean) {
		ManufacturerInfoBean manufacturer = new ManufacturerInfoBean();
		manufacturer.setResManuFacturerId(manufacturerInfoBean.getResManuFacturerId());
		
		logger.debug("执行delManufacturer()方法，根据resManuFacturerId删除供应商,resManuFacturerId的值为"
						+manufacturerInfoBean.getResManuFacturerId(),"resManuFacturerId");
		try {
			manufacturerService.delManufacturerInfoById(manufacturerInfoBean);
		} catch (Exception e) {
			logger.error("删除异常："+e.getMessage(),manufacturerInfoBean.getResManuFacturerId());
			return false;
		}
		logger.info("delManufacturer()方法执行结束");
		return true;
	}
	
	/**
	 * 批量删除厂商
	 * @param manufacturerInfoBean
	 * @return
	 */
	@RequestMapping("/delBatchManufacturer")
	@ResponseBody
	public Map<String, Object> delBatchManufacturer(HttpServletRequest request) {
		logger.info("批量删除厂商........start");
		boolean flag = false;
		boolean checkRs = false;
		
		String resManuFacturerIds = request.getParameter("resManuFacturerIds");
		String[] splitIds = resManuFacturerIds.split(",");
		String manuName = "";
		/* 能够被删的ID数组 */
		List<Integer> delOrgId = new ArrayList<Integer>();
		/* 不能被删保留的ID数组 */
		List<Integer> reserveId = new ArrayList<Integer>();
		
		for (String strId : splitIds) {
			int manuFatureId  = Integer.parseInt(strId);
			logger.info("删除前验证厂商是否被使用");
			checkRs = manufacturerService.isUsedByRes(manuFatureId);
			if(checkRs == false){
				delOrgId.add(manuFatureId);
				logger.info("能够被删的Id：" + manuFatureId);
			} else {
				reserveId.add(manuFatureId);
				logger.info("不能够被删而保留的Id：" + manuFatureId);
			}
		}
		
		//批量删除能够删除的厂商
		for (int i = 0; i < delOrgId.size(); i++) {
			ManufacturerInfoBean bean = new ManufacturerInfoBean();
			bean.setResManuFacturerId(delOrgId.get(i));
			try {
				manufacturerService.delManufacturerInfoById(bean);
			} catch (Exception e) {
				logger.error("删除异常："+e);
			}
		}
		
		//获得不能删除的厂商的名称
		if(reserveId.size() > 0){
			flag = false;
			String name = "";
			for (int i = 0; i < reserveId.size(); i++) {
				ManufacturerInfoBean infoBean = manufacturerService.getManufacturerInfoByID(reserveId.get(i));
				name = infoBean.getResManuFacturerName();
				manuName = manuName + name + ",";
			}
			if(!"".equals(manuName) && manuName != null){
				manuName = manuName.substring(0 , manuName.lastIndexOf(","));
				logger.info("正在使用的厂商的名称为："+manuName);
			}
		}else{
			flag = true;
		}
		Map<String, Object> result = new HashMap<String, Object>();
		manuName = " 【 " + manuName + " 】 ";
		result.put("flag", flag);
		result.put("manuName", manuName);
		return result;
	}
	
	
	/**
	 * 修改
	 * @param manufacturerInfoBean
	 * @return
	 */
	@RequestMapping("/updateManufacturer")
	@ResponseBody
	public boolean updateManufacturer(ManufacturerInfoBean manufacturerInfoBean){
		logger.debug("执行updateManufacturer()方法，根据resManuFacturerId修改供应商,resManuFacturerId的值为:"
					+manufacturerInfoBean.getResManuFacturerId(),"resManuFacturerId");
		try {
			manufacturerService.updateManufacturer(manufacturerInfoBean);
		} catch (Exception e) {
			logger.error("修改异常："+e.getMessage(),manufacturerInfoBean.getResManuFacturerId());
			return false;
		}
		logger.info("updateManufacturer()方法执行结束");
		return  true;
	}	
	
	@RequestMapping("/listManufacturer")
	@ResponseBody
	public Map<String,Object> listManufacturer(ManufacturerInfoBean manufacturerInfoBean){
		logger.info("执行listManufacturer()方法，获取页面加载所需信息");
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		
		List<ManufacturerInfoBean> manufacturerList = 
				manufacturerService.getManufacturerInfoBeanByConditions(manufacturerInfoBean, flexiGridPageInfo);	
		// 获取总记录数
		int total = manufacturerService.getTotalCount(manufacturerInfoBean);
		logger.info("...获得数据库中记录总数："+total);
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("total", total);
		result.put("rows", manufacturerList);
		
		logger.info("...listManufacturer()方法执行结束");
		return result;
	}
	
	/**
	 * 初始化显示厂商combobox下拉框
	 */
	@RequestMapping("/readManufacturerInfo")
	@ResponseBody
	public List<ManufacturerInfoBean> readDepartment() {
		logger.info("开始...获取厂商显示数据");
		List<ManufacturerInfoBean> lst = manufacturerService
				.queryAllManufacturerInfo();
		return lst;
	}
	
	/**
	 * 是否被资源产品使用
	 */
	@RequestMapping("/isUsedByRes")
	@ResponseBody
	public boolean isUsedByRes(int resManuFacturerId) {
		return manufacturerService.isUsedByRes(resManuFacturerId);
	}
}

