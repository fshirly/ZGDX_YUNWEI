package com.fable.insightview.monitor.databasecommunity.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.platform.page.Page;
import com.fable.insightview.monitor.sysdbmscommunity.entity.SysDBMSCommunityBean;
import com.fable.insightview.monitor.sysdbmscommunity.mapper.SysDBMSCommunityMapper;
import com.fable.insightview.monitor.sysdbmscommunity.service.ISysDBMSCommunityService;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;

/**
 * 数据库认证
 * 
 * @Description:DatabaseCommunityController
 * @author zhurt
 */
@Controller
@RequestMapping("/monitor/databaseCommunity")
public class DatabaseCommunityController {
	@Autowired
	ISysDBMSCommunityService sysDBMSCommunityService;

	@Autowired
	SysDBMSCommunityMapper sysDBMSCommunityMapper;

	private static final Logger logger = LoggerFactory
			.getLogger(DatabaseCommunityController.class);

	@RequestMapping("/todatabaseCommunity")
	public ModelAndView todatabaseCommunity(String navigationBar) {
		logger.info("开始加载数据库认证页面");
		ModelAndView mv =new ModelAndView("monitor/databaseCommunity/databaseCommunity_list");
		mv.addObject("navigationBar", navigationBar);
		return mv;
	}

	@RequestMapping("/listDatabaseCommunity")
	@ResponseBody
	public Map<String, Object> listDatabaseCommunity(SysDBMSCommunityBean vo)
			throws Exception {
		logger.info("查询列表数据。。。");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);

		Page<SysDBMSCommunityBean> page = new Page<SysDBMSCommunityBean>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());

		Map<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("ip", vo.getIp());
		paramMap.put("dbName", vo.getDbName());
		paramMap.put("dbmsType", vo.getDbmsType());
		paramMap.put("userName", vo.getUserName());
		paramMap.put("sort", request.getParameter("sort"));
		paramMap.put("order", request.getParameter("order"));
		page.setParams(paramMap);

		List<SysDBMSCommunityBean> categoryList = sysDBMSCommunityService
				.getDatabaseCommunityByConditions(page);

		int total = page.getTotalRecord();
		logger.info("*******total:" + total);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", categoryList);
		return result;
	}

	@RequestMapping("/toAddDatabaseCommunity")
	public ModelAndView toAddDatabaseCommunity(HttpServletRequest request,
			ModelMap map) {
		logger.info("跳转到新增页面");
		return new ModelAndView(
				"monitor/databaseCommunity/databaseCommunity_add");
	}

	/**
	 * 对象类型为数据库时，验证对象IP是已经发现的
	 * 
	 * @return
	 */
	@RequestMapping("/isDiscovered")
	@ResponseBody
	public boolean isDiscovered(String deviceip) {
		return sysDBMSCommunityService.isDiscovered(deviceip);
	}

	/**
	 * 验证数据库认证中改设备IP是否存在
	 * 
	 * @return
	 */
	@RequestMapping("/checkAddDBMSCommunity")
	@ResponseBody
	public boolean checkAddDBMSCommunity(SysDBMSCommunityBean vo) {
		return sysDBMSCommunityService.checkbeforeAdd(vo);
	}

	@RequestMapping("/checkUpdateDBMSCommunity")
	@ResponseBody
	public boolean checkUpdateDBMSCommunity(SysDBMSCommunityBean vo) {
		return sysDBMSCommunityService.checkbeforeUpdate(vo);
	}

	/**
	 * 新增数据库认证
	 * 
	 * @return
	 */
	@RequestMapping("/addDatabaseCommunity")
	@ResponseBody
	public boolean addDatabaseCommunity(SysDBMSCommunityBean vo) {
		return sysDBMSCommunityService.addDBMSCommunity(vo);
	}

	/**
	 * 跳转到修改编辑页面
	 * 
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("toDatabaseCommunityModify")
	@ResponseBody
	public ModelAndView toDatabaseCommunityModify(SysDBMSCommunityBean vo,
			ModelMap map, HttpServletRequest request) {
		logger.info("跳转到修改编辑页面。。。");
		vo = sysDBMSCommunityService.getInfoByID(vo.getId());
		map.put("dbmsCommunity", vo);
		return new ModelAndView(
				"monitor/databaseCommunity/databaseCommunity_modify");
	}

	/**
	 * 修改数据库认证信息
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping("/updateDatabaseCommunity")
	@ResponseBody
	public boolean updateDatabaseCommunity(SysDBMSCommunityBean vo) {
		return sysDBMSCommunityService.updateDBMSCommunityByID(vo);
	}

	/**
	 * 删除
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping("/delDatabaseCommunity")
	@ResponseBody
	public boolean delDatabaseCommunity(SysDBMSCommunityBean vo) {
		return sysDBMSCommunityService.delDBMSCommunity(vo);
	}

	/**
	 * 数据库认证详情
	 * 
	 * @Description:
	 *@param map
	 *@param vo
	 */
	@RequestMapping("/toShowDatabaseCommunity")
	@ResponseBody
	public ModelAndView toShowDatabaseCommunity(ModelMap map,
			SysDBMSCommunityBean vo) {
		logger.info("跳转到新增页面");
		vo = sysDBMSCommunityService.getInfoByID(vo.getId());
		map.put("dbmsCommunity", vo);
		return new ModelAndView(
				"monitor/databaseCommunity/databaseCommunity_detail");
	}

	/**
	 * 批量删除
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping("/doBatchDel")
	@ResponseBody
	public Map<String, Object> doBatchDel(HttpServletRequest request) {
		logger.info("批量删除。。。");
		String delIDs = request.getParameter("delIDs");
		// 获取单个ID
		String[] splitIds = delIDs.split(",");
		int flag = 0;
		/* 能够被删的ID数组 */
		List<Integer> delDBMS = new ArrayList<Integer>();

		for (String strID : splitIds) {
			int id = Integer.parseInt(strID);
			delDBMS.add(id);
		}
		for (int i = 0; i < delDBMS.size(); i++) {
			int id = delDBMS.get(i);
			try {
				sysDBMSCommunityMapper.deleteByPrimaryKey(id);
				flag = 1;
			} catch (Exception e) {
				logger.error("删除异常：" + e.getMessage());
			}
		}
		Map<String, Object> result = new HashMap<String, Object>();

		result.put("flag", flag);
		return result;
	}
	
	@RequestMapping("/getAllDbNames")
	@ResponseBody
	public List<SysDBMSCommunityBean> getAllDbNames(HttpServletRequest request){
		String deviceip = request.getParameter("deviceip");
		int port = Integer.parseInt(request.getParameter("port"));
		String dbmstype = request.getParameter("dbmstype");
		SysDBMSCommunityBean bean = new SysDBMSCommunityBean();
		bean.setIp(deviceip);
		bean.setPort(port);
		bean.setDbmsType(dbmstype);
		List<SysDBMSCommunityBean> dbCommunityLst = sysDBMSCommunityMapper.getByIPAndPort(bean);
		return dbCommunityLst;
	}
}
