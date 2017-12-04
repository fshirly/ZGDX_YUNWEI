package com.fable.insightview.monitor.alarmmgr.alarmDispatchFilter.controller;

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
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.monitor.alarmdispatcher.sendsingle.AlarmDispatchFilterUtils;
import com.fable.insightview.monitor.alarmmgr.alarmDispatchFilter.entity.AlarmDispatchFilter;
import com.fable.insightview.monitor.alarmmgr.alarmDispatchFilter.entity.AlarmDispatchFilterDef;
import com.fable.insightview.monitor.alarmmgr.alarmDispatchFilter.entity.AlarmDispatchFilterDefExample;
import com.fable.insightview.monitor.alarmmgr.alarmDispatchFilter.entity.AlarmDispatchFilterExample;
import com.fable.insightview.monitor.alarmmgr.alarmDispatchFilter.mapper.AlarmDispatchFilterDefMapper;
import com.fable.insightview.monitor.alarmmgr.alarmDispatchFilter.mapper.AlarmDispatchFilterMapper;
import com.fable.insightview.monitor.alarmmgr.alarmactive.mapper.AlarmActiveMapper;
import com.fable.insightview.monitor.alarmmgr.alarmeventdefine.mapper.AlarmEventDefineMapper;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmEventDefineBean;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmLevelInfo;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmTypeInfo;
import com.fable.insightview.monitor.alarmmgr.entity.MOSourceBean;
import com.fable.insightview.monitor.alarmmgr.mosource.mapper.MOSourceMapper;
import com.fable.insightview.monitor.util.JsonUtils;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.service.ISysUserService;

/**
 * @Description:
 * @author
 * @Date 2015-05-20
 */
@Controller
@RequestMapping("/monitor/AlarmDispatchFilter")
@SuppressWarnings("all")
public class AlarmDispatchFilterController {

	@Autowired
	private AlarmActiveMapper alarmActiveMapper;
	@Autowired
	private AlarmEventDefineMapper alarmEventDefineMapper;
	@Autowired
	private MOSourceMapper moSourceMapper;
	@Autowired
	private AlarmDispatchFilterMapper alarmDispatchFilterMapper;
	@Autowired
	private AlarmDispatchFilterDefMapper alarmDispatchFilterDefMapper;

	@Autowired
	ISysUserService sysUserService;

	private final static Logger logger = LoggerFactory.getLogger(AlarmDispatchFilterController.class);
	
	/**
	 * 菜单页面跳转
	 */
	
	@RequestMapping("/toAlarmDispatchFilter")
	public ModelAndView toAlarmDispatchFilter(ModelMap map, HttpServletRequest request,String navigationBar) throws Exception {
		ModelAndView mv =  new ModelAndView("monitor/alarmMgr/alarmDispatchFilter/list");
		mv.addObject("navigationBar", navigationBar);
		return mv;
	}
	@ResponseBody
	@RequestMapping("/testAlarmDispatchFilter")
	public String testAlarmDispatchFilter(ModelMap map, HttpServletRequest request) throws Exception {
		AlarmDispatchFilterUtils.getInstance().test();
		return "更新派发规则测试...";
	}
	
	@RequestMapping("/toAlarmDispatchFilterAdd")
	public ModelAndView toAlarmDispatchFilterAdd(ModelMap map, HttpServletRequest request) throws Exception {
		return new ModelAndView("monitor/alarmMgr/alarmDispatchFilter/add");
	}
	
	@RequestMapping("/toAlarmDispatchFilterUpdate")
	public ModelAndView toAlarmDispatchFilterUpdate(ModelMap map, HttpServletRequest request) throws Exception {
		String id = request.getParameter("id");
		AlarmDispatchFilterDef temp = alarmDispatchFilterDefMapper.selectByPrimaryKey(Integer.parseInt(id));
		
		map.put("id", temp.getId());
		map.put("isDefault", temp.getIsDefault());
		map.put("name", temp.getFilterName());
		map.put("descr", temp.getDescr());
		
		return new ModelAndView("monitor/alarmMgr/alarmDispatchFilter/edit");
	}
	
	@RequestMapping("/toAlarmDispatchFilterView")
	public ModelAndView toAlarmDispatchFilterView(ModelMap map, HttpServletRequest request) throws Exception {
		String id = request.getParameter("id");
		AlarmDispatchFilterDef temp = alarmDispatchFilterDefMapper.selectByPrimaryKey(Integer.parseInt(id));
		
		map.put("id", temp.getId());
		map.put("isDefault", temp.getIsDefault());
		map.put("name", temp.getFilterName());
		map.put("descr", temp.getDescr());
		
		return new ModelAndView("monitor/alarmMgr/alarmDispatchFilter/view");
	}
	
	@ResponseBody
	@RequestMapping("/ajaxRemoveAlarmDispatchFilterDef")
	public String ajaxRemoveAlarmDispatchFilterDef(HttpServletRequest request) {
		String ids = request.getParameter("ids");
		if("".equals(ids) || null == ids){
			return "false";
		}
		String[] is = ids.split(",");
		for (String id : is) {
			// 删除子项
			AlarmDispatchFilterExample example = new AlarmDispatchFilterExample();
			example.createCriteria().andFilterIdEqualTo(Integer.parseInt(id));
			alarmDispatchFilterMapper.deleteByExample(example);
			// 删除总的
			alarmDispatchFilterDefMapper.deleteByPrimaryKey(Integer.parseInt(id));
		}
		return "true";
	}
	
	@ResponseBody
	@RequestMapping("/ajaxUpdateAlarmDispatchFilterDef")
	public String ajaxUpdateAlarmDispatchFilterDef(HttpServletRequest request) {
		String id = request.getParameter("id");
		String is = request.getParameter("isDefault");
		if("".equals(id) || null == id){
			return "false";
		}
		AlarmDispatchFilterDef record = new AlarmDispatchFilterDef();
		record.setId(Integer.parseInt(id));
		record.setFilterName(request.getParameter("name"));
		record.setIsDefault(Short.parseShort(is));
		if(1 == record.getIsDefault()){
			AlarmDispatchFilterDef temp = new AlarmDispatchFilterDef();
			temp.setIsDefault((short) 0);
			AlarmDispatchFilterDefExample ex2 = new AlarmDispatchFilterDefExample();
			alarmDispatchFilterDefMapper.updateByExampleSelective(temp , ex2 );
		}
		record.setDescr(request.getParameter("descr"));
		alarmDispatchFilterDefMapper.updateByPrimaryKey(record );
		// update cache
		logger.info("[INFO]: update alarm dispatch filter cache..");
		updateAlarmDispatchFilterCache();
		return "true";
	}
	
	@ResponseBody
	@RequestMapping("/ajaxAlarmDispatchFilterDefDataGrid")
	public String ajaxAlarmDispatchFilterDefDataGrid(HttpServletRequest request) {
		String rows = request.getParameter("rows");
		String page = request.getParameter("page");
		Integer r = Integer.parseInt(rows);
		Integer s = (Integer.parseInt(page) - 1) * r;
		AlarmDispatchFilterDefExample e = new AlarmDispatchFilterDefExample();
		String name = request.getParameter("name");
		if("" != name && null != name){
			e.createCriteria().andFilterNameLike("%" + name + "%");
		}
		e.setPage(s);
		e.setRows(r);
		int total = alarmDispatchFilterDefMapper.countByExample(e);
		List<AlarmDispatchFilterDef> t = alarmDispatchFilterDefMapper.selectByExampleLimit(e);
		Map map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("rows", t);
		return JsonUtils.ObjectToJsonString(map);
	}

	@ResponseBody
	@RequestMapping("/ajaxAlarmDispatchFilterDataGrid")
	public String ajaxAlarmDispatchFilterDataGrid(HttpServletRequest request) {
		String defid = request.getParameter("defid");
		
		// 获取告警级别下拉框数据
		List<AlarmLevelInfo> levelList = alarmActiveMapper.queryLevelInfo();
		// 获取告警类型下拉框数据
		List<AlarmTypeInfo> typeList = alarmActiveMapper.queryTypeInfo();
		// 获取告警事件
		List<AlarmEventDefineBean> statusList = alarmEventDefineMapper.getAllAlarmEvent();

		Map<Integer, String> map1 = new HashMap<Integer, String>();
		Map<Integer, String> map2 = new HashMap<Integer, String>();
		Map<Integer, String> map3 = new HashMap<Integer, String>();

		for (AlarmLevelInfo i : levelList) {
			map1.put(i.getAlarmLevelValue(), i.getAlarmLevelName());
		}
		for (AlarmTypeInfo i : typeList) {
			map2.put(i.getAlarmTypeID(), i.getAlarmTypeName());
		}
		for (AlarmEventDefineBean i : statusList) {
			map3.put(i.getAlarmDefineID(), i.getAlarmName());
		}
		Map<String, Map> map5 = new HashMap<String, Map>();
		map5.put("AlarmLevel", map1);
		map5.put("AlarmType", map2);
		map5.put("AlarmDefineID", map3);

		String rows = request.getParameter("rows");
		String page = request.getParameter("page");
		Integer r = Integer.parseInt(rows);
		Integer s = (Integer.parseInt(page) - 1) * r;

		AlarmDispatchFilterExample e = new AlarmDispatchFilterExample();
		e.createCriteria().andFilterIdEqualTo(Integer.parseInt(defid));
		e.setPage(s);
		e.setRows(r);
		int total = alarmDispatchFilterMapper.countByExample(e);
		List<AlarmDispatchFilter> t = alarmDispatchFilterMapper.selectByExampleLimit(e);
		for (AlarmDispatchFilter adf : t) {
			if ("AlarmSourceMOID".equals(adf.getFilterKey())) {
				MOSourceBean mo = moSourceMapper.getMOSourceById(adf.getFilterKeyValue());
				adf.setFilterKeyValueName(mo.getMoName());
				continue;
			}
			Map<Integer, String> type = (Map<Integer, String>) map5.get(adf.getFilterKey());
			if (null == type || 0 == type.size()) {
				continue;
			}
			adf.setFilterKeyValueName(type.get(adf.getFilterKeyValue()));
		}
		Map map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("rows", t);
		return JsonUtils.ObjectToJsonString(map);
	}

	@ResponseBody
	@RequestMapping("/ajaxAddAlarmDispatchFilterDef")
	public String ajaxAddAlarmDispatchFilterDef(HttpServletRequest request) {
		String n = request.getParameter("name");
		String df = request.getParameter("isDefault");
		String d = request.getParameter("descr");
		try {
			AlarmDispatchFilterDef record = new AlarmDispatchFilterDef();
			record.setFilterName(n);
			record.setDescr(d);
			record.setIsDefault(Short.parseShort(df));
			// 新增了启用规则
			if(1 == record.getIsDefault()){
				AlarmDispatchFilterDef temp = new AlarmDispatchFilterDef();
				temp.setIsDefault((short) 0);
				AlarmDispatchFilterDefExample ex2 = new AlarmDispatchFilterDefExample();
				// 将其他所有启用的关闭
				alarmDispatchFilterDefMapper.updateByExampleSelective(temp , ex2 );
			}	
			alarmDispatchFilterDefMapper.insert(record);
			if(1 == record.getIsDefault()){
				// 更新缓存
				updateAlarmDispatchFilterCache();
			}
			return record.getId() + "";
		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		}
	}
	
	@ResponseBody
	@RequestMapping("/ajaxAddAlarmDispatchFilter")
	public String ajaxAddAlarmDispatchFilter(HttpServletRequest request) {
		// 主表id
		String id = request.getParameter("id");
		// 过滤条件关键字
		String key = request.getParameter("key");
		// 过滤条件值
		String value = request.getParameter("val");
		String[] vs = value.split(",");
		// 
		if (0 == vs.length || null == id || "".equals(id)) {
			return "false";
		}
		try {
			for (String s : vs) {
				AlarmDispatchFilter record = new AlarmDispatchFilter();
				record.setFilterId(Integer.parseInt(id));
				record.setFilterKey(key);
				record.setFilterKeyValue(Integer.parseInt(s));
				alarmDispatchFilterMapper.insert(record);
			}
			AlarmDispatchFilterDef temp = alarmDispatchFilterDefMapper.selectByPrimaryKey(Integer.parseInt(id));
			// 更新启用项则更新缓存
			if(temp != null && 1 == temp.getIsDefault()){
				updateAlarmDispatchFilterCache();
			}
			return "true";
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}
	}

	@ResponseBody
	@RequestMapping("/ajaxRemoveAlarmDispatchFilter")
	public String ajaxRemoveAlarmDispatchFilter(HttpServletRequest request) {
		String id = request.getParameter("id");
		String defid = request.getParameter("defid");
		try {
			AlarmDispatchFilterDef temp = alarmDispatchFilterDefMapper.selectByPrimaryKey(Integer.parseInt(defid));
			// 更新启用项则更新缓存
			if(null != temp && 1 == temp.getIsDefault()){
				updateAlarmDispatchFilterCache();
			}
			alarmDispatchFilterMapper.deleteByPrimaryKey(Integer.parseInt(id));
			return "true";
		} catch (Exception e) {
			return "false";
		}
	}
	
	private void updateAlarmDispatchFilterCache(){
		logger.info("[INFO] 刷新自定义派单规则缓存...");
		try {
			AlarmDispatchFilterUtils.getInstance().buildAlarmDispatchFilter();
		} catch (Exception e) {
			logger.info("[ERROR] 刷新自定义派单规则缓存异常: ", e.getMessage());
		}
	}
	
	/**
	 * 告警级别页面弹出
	 * 
	 * @return
	 */
	@RequestMapping("/toSelectAlarmLevel")
	public ModelAndView toSelectAlarmLevel(HttpServletRequest request,
			ModelMap map) throws Exception {
		String proUrl = "/monitor/AlarmDispatchFilter/filterAlarmLevel?id="
				+ request.getParameter("id");
		map.put("proUrl", proUrl);
		return new ModelAndView("monitor/component/mcAlarmLevelList");
	}

	/**
	 * 告警类型页面弹出
	 * 
	 * @return
	 */
	@RequestMapping("/toSelectAlarmType")
	public ModelAndView toSelectAlarmType(HttpServletRequest request,
			ModelMap map) throws Exception {
		String proUrl = "/monitor/AlarmDispatchFilter/filterAlarmType?id="
				+ request.getParameter("id");
		map.put("proUrl", proUrl);
		return new ModelAndView("monitor/component/mcAlarmTypeList");
	}

	/**
	 * 告警源对象页面弹出
	 * 
	 * @return
	 */
	@RequestMapping("/toSelectMOSource")
	public ModelAndView toSelectMOSource(HttpServletRequest request,
			ModelMap map) throws Exception {
		String proUrl = "/monitor/AlarmDispatchFilter/filterMOSource?id="
				+ request.getParameter("id");
		map.put("proUrl", proUrl);
		return new ModelAndView("monitor/component/mcMOSourceList");
	}

	/**
	 * 告警事件页面弹出
	 * 
	 * @return
	 */
	@RequestMapping("/toSelectAlarmEvent")
	public ModelAndView toSelectAlarmEvent(HttpServletRequest request,
			ModelMap map) throws Exception {
		String proUrl = "/monitor/AlarmDispatchFilter/filterAlarmEvent?id="
				+ request.getParameter("id");
		map.put("proUrl", proUrl);
		return new ModelAndView("monitor/component/mcAlarmEventList");
	}
	
	/**
	 * 过滤条件：告警级别
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/filterAlarmLevel")
	@ResponseBody
	public Map<String, Object> filterAlarmLevel(HttpServletRequest request)
			throws Exception {
		logger.info("开始...获取页面告警级别显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<AlarmLevelInfo> page = new Page<AlarmLevelInfo>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", request.getParameter("id"));
		paramMap.put("alarmLevelName", request.getParameter("alarmLevelName"));
		page.setParams(paramMap);
		// 查询分页数据
		List<AlarmLevelInfo> list = alarmDispatchFilterDefMapper
				.queryAlarmLevelList(page);
		// 查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", list);
		logger.info("结束...获取页面告警级别显示数据");
		return result;
	}

	/**
	 * 过滤条件：告警类型
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/filterAlarmType")
	@ResponseBody
	public Map<String, Object> filterAlarmType(HttpServletRequest request)
			throws Exception {
		logger.info("开始...获取页面告警类型显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<AlarmTypeInfo> page = new Page<AlarmTypeInfo>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", request.getParameter("id"));
		paramMap.put("alarmTypeName", request.getParameter("alarmTypeName"));
		page.setParams(paramMap);
		// 查询分页数据
		List<AlarmTypeInfo> list = alarmDispatchFilterDefMapper
				.queryAlarmTypeList(page);
		// 查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", list);
		logger.info("结束...获取页面告警类型显示数据");
		return result;
	}

	/**
	 * 过滤条件：告警源对象
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/filterMOSource")
	@ResponseBody
	public Map<String, Object> filterMOSource(HttpServletRequest request)
			throws Exception {
		logger.info("开始...获取页面告警源对象显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MOSourceBean> page = new Page<MOSourceBean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", request.getParameter("id"));
		paramMap.put("moName", request.getParameter("moName"));
		paramMap.put("deviceIP", request.getParameter("deviceIP"));
		page.setParams(paramMap);
		// 查询分页数据
		List<MOSourceBean> list = alarmDispatchFilterDefMapper
				.queryMOSourceList(page);
		// 查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", list);
		logger.info("结束...获取页面告警源对象显示数据");
		return result;
	}

	/**
	 * 过滤条件：告警事件
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/filterAlarmEvent")
	@ResponseBody
	public Map<String, Object> filterAlarmEvent(HttpServletRequest request)
			throws Exception {
		logger.info("开始...获取页面告警事件显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<AlarmEventDefineBean> page = new Page<AlarmEventDefineBean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", request.getParameter("id"));
		paramMap.put("alarmName", request.getParameter("alarmName"));
		page.setParams(paramMap);
		// 查询分页数据
		List<AlarmEventDefineBean> list = alarmDispatchFilterDefMapper
				.queryEventList(page);
		// 查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", list);
		logger.info("结束...获取页面告警事件显示数据");
		return result;
	}
}