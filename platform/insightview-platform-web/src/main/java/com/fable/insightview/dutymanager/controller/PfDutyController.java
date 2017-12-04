package com.fable.insightview.dutymanager.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
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
import com.fable.insightview.platform.dutymanager.DutyData;
import com.fable.insightview.platform.dutymanager.DutyParams;
import com.fable.insightview.platform.dutymanager.duty.entity.DutyChangeRecord;
import com.fable.insightview.platform.dutymanager.duty.entity.PfDuty;
import com.fable.insightview.platform.dutymanager.duty.service.DutyChangeService;
import com.fable.insightview.platform.dutymanager.duty.service.IDutyService;
import com.fable.insightview.platform.dutymanager.dutyorder.entity.DutyOrder;
import com.fable.insightview.platform.dutymanager.dutyorder.service.DutyOrderService;
import com.fable.insightview.platform.page.Page;
import com.oreilly.servlet.ServletUtils;

@Controller
@RequestMapping("/platform/dutymanager/duty")
public class PfDutyController {

	@Autowired
	private IDutyService dutyService;
	@Autowired
	private DutyChangeService dutyChangeService;
	@Autowired
	private DutyOrderService dutyOrderService;

	private static final Logger LOGGER = LoggerFactory.getLogger(PfDutyController.class);

	/**
	 * 跳转到值班管理列表页面
	 * 
	 * @param navigationBar
	 * @return
	 */
	@RequestMapping("/toDutyList")
	public ModelAndView toList(String navigationBar) {
		return new ModelAndView("/dutymanager/duty_list").addObject("navigationBar", navigationBar);
	}

	/**
	 * 跳转到值班列表查询页面
	 * 
	 * @return
	 */
	@RequestMapping("/toSearch")
	public ModelAndView toSearch() {
		return new ModelAndView("/dutymanager/duty_search");
	}

	/**
	 * 跳转到值班日历页面
	 * 
	 * @return
	 */
	@RequestMapping("/toCalendar")
	public ModelAndView toCalendar() {
		return new ModelAndView("/dutymanager/duty_calendar");
	}

	/**
	 * 查询所有的值班人员信息
	 * 
	 * @return
	 */
	@RequestMapping("/queryDutyers")
	@ResponseBody
	public List<Map<String, String>> queryDutyers() {
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			SecurityUserInfoBean sysUser = (SecurityUserInfoBean) request.getSession().getAttribute("sysUserInfoBeanOfSession");
			params.put("userId", sysUser.getId());
			params.put("name", request.getParameter("userName"));
			params.put("deptId", request.getParameter("deptId"));
			params.put("userIds", request.getParameter("userIds"));
			return dutyService.queryDutyers(params);
		} catch (Exception e) {
			LOGGER.error("查询值班人员异常：{}", e);
		}
		return new ArrayList<Map<String, String>>();
	}

	/**
	 * 查询值班列表信息
	 * 
	 * @param dutyParams
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Map<String, Object> list(DutyParams dutyParams, Integer page, Integer rows, String order) {
		Page<Map<String, Object>> pages = new Page<Map<String, Object>>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		pages.setPageNo(page);
		pages.setPageSize(rows);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("leader", dutyParams.getLeader());
		params.put("watch", dutyParams.getWatch());
		params.put("order", order);
		if (null != dutyParams.getBegin()) {
			params.put("begin", sdf.format(dutyParams.getBegin()));
		}
		if (null != dutyParams.getEnd()) {
			params.put("end", sdf.format(dutyParams.getEnd()));
		}
		pages.setParams(params);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> datas = dutyService.list(pages);
			result.put("total", pages.getTotalRecord());
			result.put("rows", datas);
		} catch (Exception e) {
			result.put("total", 0);
			result.put("rows", new ArrayList<Map<String, Object>>());
			LOGGER.error("查询值班列表异常：{}", e);
		}
		return result;
	}

	/**
	 * 跳转到值班管理新增页面
	 * 
	 * @return
	 */
	@RequestMapping("/toAdd")
	public ModelAndView toAdd() {
		return new ModelAndView("/dutymanager/duty_add");
	}

	/**
	 * 跳转到选择值班人页面
	 * 
	 * @param type
	 * @return
	 */
	@RequestMapping("/toSelect")
	public ModelAndView toSelect(int type, String readys, String dutyers) {
		ModelAndView mv = new ModelAndView("/dutymanager/configerUser");
		mv.addObject("userType", type);
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			SecurityUserInfoBean sysUser = (SecurityUserInfoBean) request.getSession().getAttribute("sysUserInfoBeanOfSession");
			params.put("userId", sysUser.getId());
			List<Map<String, String>> readyers = null;/*页面中已选的值班人员*/
			int size = 0;
			if (type == 1) {/*选择值班备勤人员*/
				readyers = strToMap(readys);
				size = readyers.size();
				if (size > 0) {
					params.put("userIds", readys.substring(0, readys.indexOf(";")));
				}
			} else {/*选择值班人员*/
				readyers = strToMap(dutyers);
				size = readyers.size();
				if (size > 0) {
					params.put("userIds", dutyers.substring(0, dutyers.indexOf(";")));/*已选择的值班人员ID*/
					mv.addObject("Pincicla", readyers.get(size - 1));/*值班负责人*/
					readyers.remove(size - 1);
				}
			}
			mv.addObject("dutyers", readyers);
			List<Map<String, String>> users = dutyService.queryDutyers(params);
			if (!users.isEmpty()) {
				mv.addObject("users", users);
				mv.addObject("orgId", users.get(0).get("OrganizationID"));
			} else {
				mv.addObject("orgId", dutyService.findOrgIdByUserId(sysUser.getId().intValue()));
			}
		} catch (Exception e) {
			LOGGER.error("查询值班人员异常：{}", e);
		}
		return mv;
	}
	
	/**
	 * 把字符串改为map,字符';'分为key和value两部分
	 * @param str
	 * @return
	 */
	private List<Map<String, String>> strToMap(String str) {
		List<Map<String, String>> maps = new ArrayList<Map<String,String>>();
		if (StringUtils.isEmpty(str)) {
			return maps;
		}
		String[] keys = str.substring(0, str.indexOf(";")).split(",");
		String[] values = str.substring(str.indexOf(";") + 1).split(",");
		Map<String, String> map = null;
		for (int i = 0, size = keys.length; i < size; i++) {
			map = new HashMap<String, String>();
			map.put(keys[i], values[i]);
			maps.add(map);
		}
		return maps;
	}
	
	/**
	 * 查询所有的值班班次信息
	 * 
	 * @return
	 */
	@RequestMapping("/queryOrders")
	@ResponseBody
	public List<Map<String, Object>> queryOrders(String ids) {
		try {
			return dutyService.queryOrders(ids);
		} catch (Exception e) {
			LOGGER.error("查询值班中的班次信息异常：{}", e);
			return new ArrayList<Map<String, Object>>();
		}
	}

	/**
	 * 验证值班日期
	 * 
	 * @param dutyDate
	 * @return
	 */
	@RequestMapping("/validateDutyDate")
	@ResponseBody
	public boolean validateDutyDate(String dutyDate, Integer dutyId) {
		try {
			return dutyService.validateDutyDate(dutyDate, dutyId);
		} catch (Exception e) {
			LOGGER.error("验证值班日期异常：{}", e);
			return false;
		}
	}

	/**
	 *新增值班信息
	 * 
	 * @param duty
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public boolean add(PfDuty duty) {
		try {
			List<Map<String, Object>> orders = duty.getOrders();
			for(Map<String, Object> order : orders) {
				String id = order.get("ID").toString();
				DutyOrder dutyOrder = dutyOrderService.findDutyOrderById(Integer.valueOf(id));
				String exchangeStart = dutyOrder.getExchangeStart();
				String exchangeEnd = dutyOrder.getExchangeEnd();
				String forceTime = dutyOrder.getForceTime();
				Integer intervalDays = dutyOrder.getIntervalDays();
				Date dutyDate = duty.getDutyDate();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(dutyDate);
				calendar.add(Calendar.DATE, intervalDays);
				String newDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
				exchangeStart = newDate + " " + exchangeStart + ":00";
				exchangeEnd = newDate + " " + exchangeEnd + ":00";
				forceTime = newDate + " " + forceTime + ":00";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				order.put("exchangeStart", sdf.parse(exchangeStart));
				order.put("exchangeEnd", sdf.parse(exchangeEnd));
				order.put("forceTime", sdf.parse(forceTime));
			}
			dutyService.insert(duty);
		} catch (Exception e) {
			LOGGER.error("新增值班记录异常：{}", e);
		}
		return true;
	}

	/**
	 * 跳转到值班编辑页面
	 * 
	 * @param dutyId
	 * @return
	 */
	@RequestMapping("/toEdit")
	public ModelAndView toEdit(int dutyId) {
		ModelAndView mv = new ModelAndView("/dutymanager/duty_edit");
		Map<String, Object> duty = dutyService.query(dutyId);
		mv.addObject("duty", duty);
		return mv;
	}

	/**
	 * 查询值班中的班次信息列表
	 * 
	 * @param dutyId
	 * @return
	 */
	@RequestMapping("/ordersOfDuty")
	@ResponseBody
	public List<Map<String, Object>> ordersOfDuty(int dutyId) {
		return dutyService.queryOrders(dutyId);
	}

	/**
	 * 修改值班记录信息
	 * 
	 * @param duty
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public boolean update(PfDuty duty) {
		try {
			List<Map<String, Object>> orders = duty.getOrders();
			if(orders != null) {
				for(Map<String, Object> order : orders) {
					String id = order.get("ID").toString();
					DutyOrder dutyOrder = dutyOrderService.findDutyOrderById(Integer.valueOf(id));
					String exchangeStart = dutyOrder.getExchangeStart();
					String exchangeEnd = dutyOrder.getExchangeEnd();
					String forceTime = dutyOrder.getForceTime();
					Integer intervalDays = dutyOrder.getIntervalDays();
					Date dutyDate = duty.getDutyDate();
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(dutyDate);
					calendar.add(Calendar.DATE, intervalDays);
					String newDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
					exchangeStart = newDate + " " + exchangeStart + ":00";
					exchangeEnd = newDate + " " + exchangeEnd + ":00";
					forceTime = newDate + " " + forceTime + ":00";
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					order.put("exchangeStart", sdf.parse(exchangeStart));
					order.put("exchangeEnd", sdf.parse(exchangeEnd));
					order.put("forceTime", sdf.parse(forceTime));
				}
			}
			dutyService.update(duty);
		} catch (Exception e) {
			LOGGER.error("修改值班班次信息异常：{}", e);
			return false;
		}
		return true;
	}

	/**
	 * 删除值班记录中的班次信息
	 * 
	 * @param orderIdOfDuty
	 * @return
	 */
	@RequestMapping("/deleteOrder")
	@ResponseBody
	public boolean deleteOrder(int orderIdOfDuty) {
		try {
			dutyService.deleteSingleOrder(orderIdOfDuty);
		} catch (Exception e) {
			LOGGER.error("删除值班班次信息异常：{}", e);
			return false;
		}
		return true;
	}

	/**
	 * 查看值班记录信息
	 */
	@RequestMapping("/toView")
	public ModelAndView toView(int dutyId) {
		ModelAndView mv = new ModelAndView("/dutymanager/duty_detail");
		Map<String, Object> duty = dutyService.query(dutyId);
		if(duty.get("Level") != null) {
			int level = Integer.parseInt(duty.get("Level").toString());
			switch (level) {
			case 1:
				mv.addObject("Level", "一级");
				break;
			case 2:
				mv.addObject("Level", "二级");
				break;
			case 3:
				mv.addObject("Level", "三级");
				break;
			case 4:
				mv.addObject("Level", "四级");
				break;
			}
		}
		mv.addObject("duty", duty);
		return mv;
	}

	/**
	 * 删除值班记录信息
	 * 
	 * @param dutyId
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public boolean delete(int dutyId) {
		try {
			dutyService.delete(dutyId);
		} catch (Exception e) {
			LOGGER.error("删除值班记录信息：{}", e);
			return false;
		}
		return true;
	}

	/**
	 * 跳转值班调班页面
	 */
	@RequestMapping("/changeDutyPage")
	public ModelAndView changeDutyPage() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("dutymanager/duty_change");
		return mv;
	}

	/**
	 * 处理调班
	 */
	@RequestMapping("/changeDuty")
	@ResponseBody
	public String changeDuty(String happen, String fromUser, String toUser) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		SecurityUserInfoBean sysUser = (SecurityUserInfoBean) request.getSession().getAttribute("sysUserInfoBeanOfSession");
		//获取处理人的名字
		String handlerName = sysUser.getUserName();
		String fromUserName = dutyChangeService.findUsernameByUserId(Integer.parseInt(fromUser));
		String toUserName = dutyChangeService.findUsernameByUserId(Integer.parseInt(toUser));
		PfDuty duty = dutyChangeService.findDutyByDate(happen);
		//获取值班id
		Integer dutyId = duty.getId();
		DutyChangeRecord dcr = new DutyChangeRecord();
		dcr.setDutyId(dutyId);
		dcr.setFromUser(fromUserName);
		dcr.setToUser(toUserName);
		dcr.setHandler(handlerName);
		dcr.setHappen(new Date());
		dutyChangeService.addDutyChangeRecord(dcr);
		dutyChangeService.changeDuty(happen, fromUser, toUser);
		return "success";
	}

	/**
	 * 查询调班记录
	 */
	@RequestMapping("/loadDutyChangeList")
	@ResponseBody
	public Map<String, Object> loadDutyChangeList(String dutyDate) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<DutyChangeRecord> list = dutyChangeService.findDutyChangeRecordsByDutyDate(dutyDate);
		result.put("total", list.size());
		result.put("rows", list);
		return result;
	}

	/**
	 * 查询带班领导信息
	 */
	@RequestMapping("/loadLeaderName")
	public void loadLeaderName(String dutyDate, HttpServletResponse response) {
		String name = dutyChangeService.findLeaderNameByDutyDate(dutyDate);
		response.setContentType("text/html; charset=utf-8");
		try {
			response.getWriter().write(name);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询值班人员信息
	 */
	@RequestMapping("/loadFromUserList")
	@ResponseBody
	public List<Map<String, String>> loadFromUserList(String dutyDate) {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		//添加默认选项
		Map<String, String> initVal = new HashMap<String, String>();
		initVal.put("id", "-1");
		initVal.put("value", "请选择");
		result.add(initVal);
		if(dutyDate == null || "".equals(dutyDate)) {
			return result;
		}
		List<Integer> ids = dutyChangeService.findDutyersByDutyDate(dutyDate);
		if (ids.isEmpty()) {
			return result;
		}
		for (Integer id : ids) {
			Map<String, String> map = new HashMap<String, String>();
			String username = dutyChangeService.findUsernameByUserId(id);
			map.put("id", id.toString());
			map.put("value", username);
			result.add(map);
		}
		Integer leaderId = dutyChangeService.findDutyByDate(dutyDate).getLeaderId();
		if(leaderId != null) {
			String leaderName = dutyChangeService.findLeaderNameByDutyDate(dutyDate);
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", leaderId.toString());
			map.put("value", leaderName);
			// 判断值班领导人是否也是值班人
			if (!ids.contains(leaderId)) {
				result.add(map);
			}
		}
		return result;
	}

	/**
	 * 加载调班给列表
	 */
	@RequestMapping("/loadToUserList")
	@ResponseBody
	public List<Map<String, String>> loadToUserList(String id) {
		//获取当前登录的用户
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		SecurityUserInfoBean sysUser = (SecurityUserInfoBean) request.getSession().getAttribute("sysUserInfoBeanOfSession");
		//查询值班组的所有成员
		List<Map<String, Object>> list = dutyChangeService.findAllDutyers(sysUser.getId().intValue());
		//准备返回的数据
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		//添加默认选项
		Map<String, String> initVal = new HashMap<String, String>();
		initVal.put("id", "-1");
		initVal.put("value", "请选择");
		result.add(initVal);
		if(id == null || "".equals(id)) {
			for (Map<String, Object> temp : list) {
				String userId = temp.get("id").toString();
				String username = temp.get("name").toString();
				Map<String, String> map = new HashMap<String, String>();
				map.put("id", userId);
				map.put("value", username);
				result.add(map);
			}
			return result;
		}
		
		for (Map<String, Object> temp : list) {
			String userId = temp.get("id").toString();
			String username = temp.get("name").toString();
			if (userId.equals(id)) {
				continue;
			} else {
				Map<String, String> map = new HashMap<String, String>();
				map.put("id", userId);
				map.put("value", username);
				result.add(map);
			}
		}
		return result;
	}

	/**
	 * 查询日历下的events信息
	 * 
	 * @return
	 */
	@RequestMapping("/queryEvents")
	@ResponseBody
	public List<Map<String, Object>> queryEvents(String start, String end) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("begin", start);
			params.put("end", end);
			return dutyService.queryMulti(params);
		} catch (Exception e) {
			LOGGER.error("查询日历下的值班记录信息异常： {}", e);
			return new ArrayList<Map<String, Object>>();
		}
	}

	/**
	 * 下载值班记录模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/template")
	public void template(HttpServletRequest request, HttpServletResponse response) {
		try {
			String filePath = request.getSession().getServletContext().getRealPath("/js/templatefile/ZBMB.xls");
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("值班信息.xls", "UTF-8"));
			ServletUtils.returnFile(filePath, response.getOutputStream());
		} catch (Exception e) {
			LOGGER.error("下载值班记录模板异常：{}", e);
		}
	}

	/**
	 * 导入值班记录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/importData")
	@ResponseBody
	public Map<String, Object> importData(HttpServletRequest request) {
		try {
			SecurityUserInfoBean sysUser = (SecurityUserInfoBean) request.getSession().getAttribute("sysUserInfoBeanOfSession");
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			FileItem fi = (FileItem) upload.parseRequest(request).get(0);
			POIFSFileSystem fs = new POIFSFileSystem(fi.getInputStream());
			DutyData excelReader = new DutyData(new HSSFWorkbook(fs));
			String[] titles = excelReader.readExcelTitle();
			List<Map<Integer, Object>> data = excelReader.readExcelContent(titles.length);
			int recordCount = excelReader.getRecordCount(titles.length);
			return dutyService.importData(titles, data, sysUser.getId().intValue(), excelReader.getMinD(), excelReader.getMaxD(), recordCount);
		} catch (Exception e) {
			LOGGER.error("导入值班记录异常：{}", e);
			return new HashMap<String, Object>();
		}
	}

	/**
	 * 导出值班记录
	 */
	@RequestMapping("/exportExcel")
	public void exportExcel(HttpServletResponse response, DutyParams dutyParams) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("watch", dutyParams.getWatch());
		params.put("leader", dutyParams.getLeader());
		if(dutyParams.getBegin() != null) {
			params.put("begin", sdf.format(dutyParams.getBegin()));
		}
		if(dutyParams.getEnd() != null) {
			params.put("end", sdf.format(dutyParams.getEnd()));
		}
		
		List<Map<String, Object>> data = dutyService.queryMulti(params);
		
		// 设置response头信息
		response.setContentType("application/octet-stream");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode("值班信息", "utf-8")+".xls"); 
		
		OutputStream out = response.getOutputStream();
		dutyService.exportData(out, data, dutyParams.getOrder());
		out.close();
	}
}
