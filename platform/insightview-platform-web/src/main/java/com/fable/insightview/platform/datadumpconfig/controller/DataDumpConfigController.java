package com.fable.insightview.platform.datadumpconfig.controller;

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

import com.fable.insightview.platform.datadumpconfig.entity.DataDumpConfigBean;
import com.fable.insightview.platform.datadumpconfig.entity.SysDumpBean;
import com.fable.insightview.platform.datadumpconfig.service.IDataDumpConfigService;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.page.Page;

/**
 * 数据转储配置
 *
 */
@Controller
@RequestMapping("/platform/dataDumpConfig")
public class DataDumpConfigController {
	private Logger logger = LoggerFactory.getLogger(DataDumpConfigController.class);
	@Autowired IDataDumpConfigService dataDumpConfigService;
	
	/**
	 * 跳转至数据转储配置界面
	 * 
	 */
	@RequestMapping("/toDataDumpConfig")
	public ModelAndView toDataDumpConfig(String navigationBar) {
		ModelAndView mv= new ModelAndView("platform/datadumpconfig/dataDumpConfig");
		mv.addObject("navigationBar", navigationBar);
		return mv;
	}
	
	/**
	 * 获得数据转储设置
	 */
	@RequestMapping("/initDataDumpConfig")
	@ResponseBody
	public DataDumpConfigBean initDataDumpConfig(){
		return dataDumpConfigService.initDataDumpConfig();
	}
	
	/**
	 * 配置数据转储设置
	 */
	@RequestMapping("/doSetDataDumpConfig")
	@ResponseBody
	public boolean doSetDataDumpConfig(DataDumpConfigBean bean){
		return dataDumpConfigService.doSetDataDumpConfig(bean);
	}
	
	/**
	 * 分页查询转储列表
	 */
	@RequestMapping("/listDump")
	@ResponseBody
	public Map<String, Object> listDump(){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<SysDumpBean> page = new Page<SysDumpBean>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		return dataDumpConfigService.listDump(page);
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delDump")
	@ResponseBody
	public boolean delDump(int id){
		logger.info("删除的转储的id为：" + id);
		return dataDumpConfigService.delDump(id);
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping("/delDumps")
	@ResponseBody
	public boolean delDumps(String ids){
		logger.info("批量删除的转储的id为：" + ids);
		return dataDumpConfigService.delDumps(ids);
	}
	
	/**
	 * 跳转至新增页面
	 */
	@RequestMapping("/toShowDataDumpAdd")
	public ModelAndView toShowDataDumpAdd() {
		ModelAndView mv= new ModelAndView("platform/datadumpconfig/dataDump_add");
		return mv;
	}
	
	/**
	 * 验证是否存在
	 * @return true:不存在 
	 * 		   false：已经存在
	 */
	@RequestMapping("/isExist")
	@ResponseBody
	public boolean isExist(SysDumpBean bean){
		return dataDumpConfigService.isExist(bean);
	}
	
	/**
	 * 新增转储表信息
	 */
	@RequestMapping("/addDataDump")
	@ResponseBody
	public boolean addDataDump(SysDumpBean bean){
		return dataDumpConfigService.addDataDump(bean);
	}
	
	/**
	 * 跳转至编辑转储表页面
	 */
	@RequestMapping("/toShowDataDumpModify")
	public ModelAndView toShowDataDumpModify(HttpServletRequest request) {
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		ModelAndView mv= new ModelAndView("platform/datadumpconfig/dataDump_modify");
		return mv;
	}
	
	/**
	 * 初始化转储表信息
	 */
	@RequestMapping("/initDumpDetail")
	@ResponseBody
	public SysDumpBean initDumpDetail(int id){
		return dataDumpConfigService.initDumpDetail(id);
	}
	
	/**
	 * 更新转储表信息
	 */
	@RequestMapping("/updateDataDump")
	@ResponseBody
	public boolean updateDataDump(SysDumpBean bean){
		return dataDumpConfigService.updateDataDump(bean);
	}
}
