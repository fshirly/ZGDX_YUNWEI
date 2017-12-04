package com.fable.insightview.platform.snmpdevice.controller;

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
import com.fable.insightview.platform.rescatedef.entity.ResCateDefInfo;
import com.fable.insightview.platform.rescatedef.service.IResCateDefService;
import com.fable.insightview.platform.snmpdevice.entity.SnmpDeviceSysOIDInfo;
import com.fable.insightview.platform.snmpdevice.service.ISnmpDeviceService;

/**
 * @Description:   设备OID维护 控制器
 * @author         郑小辉
 * @Date           2014-6-27 上午11:24:41 
 */
@Controller
@RequestMapping("/platform/snmpDevice")
public class SnmpDeviceController {
	
	@Autowired
	private ISnmpDeviceService snmpDeviceService;
	
	@Autowired
	private IResCateDefService resCateDefService;
	
	private final Logger logger = LoggerFactory.getLogger(SnmpDeviceController.class);
	/**
	 * 菜单页面跳转
	 * @return
	 */
	@RequestMapping("/toSnmpDeviceList")
	public ModelAndView toSnmpDeviceList(String navigationBar) {
		ModelAndView mv = new ModelAndView("platform/snmpdevice/snmpDevice_list");
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
	@RequestMapping("/listSnmpDevice")
	@ResponseBody
	public Map<String, Object> listSnmpDevice(SnmpDeviceSysOIDInfo vo, HttpServletRequest request)throws Exception{
		logger.info("开始...获取页面显示数据");
		logger.debug("param 设备OID[deviceOID]={} ",vo.getDeviceOID());
		//获取分页对象
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil.getFlexiGridPageInfo(request);
		//获取排序参数
		flexiGridPageInfo.setSort(request.getParameter("sort"));
		flexiGridPageInfo.setOrder(request.getParameter("order"));
		//执行分页方法
		flexiGridPageInfo = snmpDeviceService.queryPage(vo, flexiGridPageInfo);
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
	@RequestMapping("/delSnmpDevice")
	@ResponseBody
	public boolean delSnmpDevice(SnmpDeviceSysOIDInfo vo){
		logger.info("开始...删除SnmpDeviceSysOIDInfo");
		logger.debug("delete by param id={}",vo.getId());
		try {
			snmpDeviceService.deleteInfo(vo);
		} catch (Exception e) {
			logger.error("删除异常："+e.getMessage(),vo);
			return false;
		}
		logger.info("结束...删除SnmpDeviceSysOIDInfo");
		return true ;
	}
	
	/**
	 * 增加信息
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addSnmpDevice")
	@ResponseBody
	public boolean addSnmpDevice(SnmpDeviceSysOIDInfo vo){
		logger.info("开始...增加SnmpDeviceSysOIDInfo");
		try {
			snmpDeviceService.addInfo(vo);
		} catch (Exception e) {
			logger.error("插入异常："+e.getMessage(),vo);
			return false;
		}
		logger.info("结束...增加SnmpDeviceSysOIDInfo");
		return true ;
	}
	
	/**
	 * 查找对象信息
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findSnmpDevice")
	@ResponseBody
	public SnmpDeviceSysOIDInfo findSnmpDevice(SnmpDeviceSysOIDInfo vo) throws Exception{
		logger.info("开始...查询SnmpDeviceSysOIDInfo");
		logger.debug("find by param id={}",vo.getId());
		vo = snmpDeviceService.getInfoById("id",vo.getId()+"");
		if(null !=vo.getResCategoryID()){
			ResCateDefInfo resVo = resCateDefService.getInfoById(vo.getResCategoryID());
			if(null !=resVo){
				vo.setResCategoryName(resVo.getResCategoryName());
			}
		}
		logger.info("结束...查询SnmpDeviceSysOIDInfo");
		return vo;
	}
	
	/**
	 * 更新信息
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateSnmpDevice")
	@ResponseBody
	public boolean updateSnmpDevice(SnmpDeviceSysOIDInfo vo){
		logger.info("开始...更新SnmpDeviceSysOIDInfo");
		logger.debug("update by param id={}",vo.getId());
		try {
			snmpDeviceService.updateInfo(vo);
		} catch (Exception e) {
			logger.error("修改异常："+e.getMessage(),vo);
			return false;
		}
		logger.info("结束...更新SnmpDeviceSysOIDInfo");
		return true ;
	}
	
	/**
	 * PEN维护信息：菜单页面弹出
	 * @return
	 */
	@RequestMapping("/toSelectSnmpPenList")
	public ModelAndView toSelectSnmpPenList() {	
		return new ModelAndView("platform/snmpdevice/selectSnmpPenList");
	}
	
	/**
	 * 产品类型：菜单页面弹出
	 * @return
	 */
	@RequestMapping("/toSelectResCateList")
	public ModelAndView toSelectResCateList() {	
		return new ModelAndView("platform/snmpdevice/selectResCateList");
	}
	
	/**
	 * 批量删除信息
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delBathSnmpDevice")
	@ResponseBody
	public boolean delBathSnmpPen(String id){
		logger.info("开始...批量删除SnmpDeviceSysOIDInfo");
		logger.debug("deleteBath by param id={}",id);
		try {
			snmpDeviceService.deleteBathInfo(id);
		} catch (Exception e) {
			logger.error("删除异常："+e.getMessage(),id);
			return false;
		}
		logger.info("结束...批量删除SnmpDeviceSysOIDInfo");
		return true ;
	}
}
