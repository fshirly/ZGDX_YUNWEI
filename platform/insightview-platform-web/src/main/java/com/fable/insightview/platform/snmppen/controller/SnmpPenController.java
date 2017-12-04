package com.fable.insightview.platform.snmppen.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.manufacturer.entity.ManufacturerInfoBean;
import com.fable.insightview.platform.manufacturer.service.IManufacturerService;
import com.fable.insightview.platform.snmppen.entity.SnmpPenInfo;
import com.fable.insightview.platform.snmppen.service.ISnmpPenService;

/**
 * @Description:   PEN维护 控制器
 * @author         郑小辉
 * @Date           2014-6-26 下午07:37:21 
 */
@Controller
@RequestMapping("/platform/snmpPen")
public class SnmpPenController {
	@Autowired
	private ISnmpPenService snmpPenService;
	
	@Autowired
	private IManufacturerService  manufacturerService;	
	
	private final Logger logger = LoggerFactory.getLogger(SnmpPenController.class);
	
	/**
	 * 菜单页面跳转
	 * @return
	 */
	@RequestMapping("/toSnmpPenList")
	public ModelAndView toSnmpPenList(String navigationBar) {
		ModelAndView mv = new ModelAndView("platform/snmppen/snmpPen_list");
		mv.addObject("navigationBar", navigationBar);
		return mv ;
	}
	
	/**
	 * 获取页面显示数据
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listSnmpPen")
	@ResponseBody
	public Map<String, Object> listSnmpPen(SnmpPenInfo vo, HttpServletRequest request){
		logger.info("开始...获取页面显示数据");
		logger.debug("param 企业名称[organization]={}",vo.getOrganization());
		//获取分页对象
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil.getFlexiGridPageInfo(request);
		//获取排序参数
		flexiGridPageInfo.setSort(request.getParameter("sort"));
		flexiGridPageInfo.setOrder(request.getParameter("order"));
		//执行分页方法
		flexiGridPageInfo = snmpPenService.queryPage(vo, flexiGridPageInfo);
		Map<String,Object> result = new HashMap<String,Object>();
		//设置
		result.put("total", flexiGridPageInfo.getTotal() );
		result.put("rows", flexiGridPageInfo.getRows());
		logger.info("结束...获取页面显示数据");
		return result;
	}
	
	/**
	 * 删除信息
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delSnmpPen")
	@ResponseBody
	public boolean delSnmpPen(SnmpPenInfo vo){
		logger.info("开始...删除SnmpPenInfo");
		logger.debug("delete by param id={}",vo.getId());
		try {
			snmpPenService.deleteInfo(vo);
		} catch (Exception e) {
			logger.error("删除异常："+e.getMessage(),vo);
			return false;
		}
		logger.info("结束...删除SnmpPenInfo");
		return true ;
	}
	
	/**
	 * 增加信息
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addSnmpPen")
	@ResponseBody
	public boolean addSnmpPen(SnmpPenInfo vo){
		logger.info("开始...增加SnmpPenInfo");
		try {
			snmpPenService.addInfo(vo);
		} catch (Exception e) {
			logger.error("插入异常："+e.getMessage(),vo);
			return false;
		}
		logger.info("结束...增加SnmpPenInfo");
		return true ;
	}
	
	/**
	 * 查找对象信息
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findSnmpPen")
	@ResponseBody
	public SnmpPenInfo findSnmpPen(SnmpPenInfo vo)throws Exception{
		logger.info("开始...查询SnmpPenInfo");
		logger.debug("find by param id={}",vo.getId());
		vo = snmpPenService.getInfoById("id",vo.getId()+"");
		if(null !=vo.getResManufacturerID()){
			ManufacturerInfoBean mfInfo = manufacturerService.getManufacturerInfoBeanByConditions("resManufacturerId",vo.getResManufacturerID()+"");
			if(null !=mfInfo){
				vo.setResManufacturerName(mfInfo.getResManuFacturerName());
			}
		}
		logger.info("结束...查询SnmpPenInfo");
		return vo;
	}
	
	/**
	 * 更新信息
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateSnmpPen")
	@ResponseBody
	public boolean updateSnmpPen(SnmpPenInfo vo){
		logger.info("开始...更新SnmpPenInfo");
		logger.debug("update by param id= {}",vo.getId());
		try {
			snmpPenService.updateInfo(vo);
		} catch (Exception e) {
			logger.error("修改异常："+e.getMessage(),vo);
			return false;
		}
		logger.info("结束...更新SnmpPenInfo");
		return true ;
	}	
	
	/**
	 * 判断PEN是否唯一
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/colValIsUnique")
	@ResponseBody
	public boolean colValIsUnique(SnmpPenInfo vo)throws Exception{
		logger.info("开始...判断SnmpPenInfo是否唯一");
		logger.debug("check by param pen={}",vo.getPen());
		//判断PEN是否唯一,不唯一返回false
		vo = snmpPenService.getInfoById("pen",vo.getPen()+"");
		return (null==vo)?true:false;
	}
	
	/**
	 * 批量删除信息
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delBathSnmpPen")
	@ResponseBody
	public boolean delBathSnmpPen(String id){
		logger.info("开始...批量删除SnmpPenInfo");
		logger.debug("deleteBath by param id={}",id);
		try {
			snmpPenService.deleteBathInfo(id);
		} catch (Exception e) {
			logger.error("删除异常："+e.getMessage(),id);
			return false;
		}
		logger.info("结束...批量删除SnmpPenInfo");
		return true ;
	}
}
