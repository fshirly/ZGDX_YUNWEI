package com.fable.insightview.monitor.alarmmgr.alarmhistory.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fable.enclosure.bussiness.entity.PageRequest;
import com.fable.enclosure.bussiness.entity.PageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.monitor.alarmmgr.alarmactive.mapper.AlarmActiveMapper;
import com.fable.insightview.monitor.alarmmgr.alarmhistory.mapper.AlarmHistoryMapper;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmHistoryInfo;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmLevelInfo;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmStatusInfo;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmTypeInfo;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.service.ISysUserService;

/**
 * @Description:   历史告警控制器
 * @author         zhengxh
 * @Date           2014-7-17
 */
@Controller
@RequestMapping("/monitor/alarmHistory")
public class AlarmHistoryController {
	
	@Autowired
	private AlarmActiveMapper alarmActiveMapper;
	
	@Autowired
	private AlarmHistoryMapper alarmHistoryMapper;
	
	@Autowired
	ISysUserService sysUserService;
	
	private final static Logger logger = LoggerFactory.getLogger(AlarmHistoryController.class);
	
	/**
	 * 菜单页面跳转
	 */
	@RequestMapping("/toAlarmHistoryList")
	public ModelAndView toAlarmHistoryList(ModelMap map,String navigationBar)throws Exception {
		//获取告警级别下拉框数据
		List<AlarmLevelInfo> levelList = alarmActiveMapper.queryLevelInfo();
		//获取告警类型下拉框数据
		List<AlarmTypeInfo> typeList = alarmActiveMapper.queryTypeInfo();
		//获取告警状态下拉框数据
		List<AlarmStatusInfo> statusList = new ArrayList<AlarmStatusInfo>();
		AlarmStatusInfo alarmStatusInfo = new AlarmStatusInfo();
		alarmStatusInfo.setStatusID(12);
		alarmStatusInfo.setStatusName("自动清除");
		statusList.add(alarmStatusInfo);
		AlarmStatusInfo alarmStatusInfo2 = new AlarmStatusInfo();
		alarmStatusInfo2.setStatusID(24);
		alarmStatusInfo2.setStatusName("人工清除");
		statusList.add(alarmStatusInfo2);
		
		map.put("levelList", levelList);
		map.put("typeList", typeList);
		map.put("statusList", statusList);
		map.put("navigationBar", navigationBar);
		return new ModelAndView("monitor/alarmMgr/alarmhistory/alarmHistory_list");
	}
	
	/**
	 * 获取页面显示数据
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listAlarmHistory")
	@ResponseBody
	public Map<String, Object> listAlarmHistory(AlarmHistoryInfo vo, HttpServletRequest request)throws Exception{
		logger.info("开始...获取页面显示数据");		
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil.getFlexiGridPageInfo(request);
		Page<AlarmHistoryInfo> page=new Page<AlarmHistoryInfo>(); 
		//设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		//设置查询参数
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("timeBegin", vo.getTimeBegin());
		paramMap.put("timeEnd", vo.getTimeEnd());
		paramMap.put("moName", vo.getMoName());
		paramMap.put("alarmLevel", vo.getAlarmLevel());
		paramMap.put("alarmTitle", vo.getAlarmTitle());
		paramMap.put("alarmStatus", vo.getAlarmStatus());
		paramMap.put("sourceMOIPAddress", vo.getSourceMOIPAddress());
		paramMap.put("alarmOperateStatus", vo.getAlarmOperateStatus());
		paramMap.put("alarmType", vo.getAlarmType());
		page.setParams(paramMap);
		//查询分页数据
		List<AlarmHistoryInfo> list = alarmHistoryMapper.queryList(page);
		//查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String,Object> result = new HashMap<String,Object>();		
		//设置至前台显示
		result.put("total",totalCount );
		result.put("rows",list);
		logger.info("结束...获取页面显示数据");
		return result;
	}

	@RequestMapping("/listAlarmHistoryNew")
	@ResponseBody
	public PageResponse<AlarmHistoryInfo> listAlarmHistoryNew(@RequestBody PageRequest<Map<String,Object>> param)throws Exception{
		logger.info("开始...获取页面显示数据");
		Page<AlarmHistoryInfo> page=new Page<AlarmHistoryInfo>();
		//设置分页参数
		page.setPageNo(param.getPageNo());
		page.setPageSize(param.getPageSize());
		page.setParams(param.getParam());
		//查询分页数据
		List<AlarmHistoryInfo> list = alarmHistoryMapper.queryList(page);
		PageResponse<AlarmHistoryInfo> response = new PageResponse<>();
		response.setRecordsFiltered(page.getTotalPage());
		response.setRecordsTotal(page.getTotalPage());
		response.setData(list);
		return response;
	}
	
	/**
	 * 告警详情页面跳转
	 * @return
	 */
	@RequestMapping("/toAlarmHistoryDetail")
	public ModelAndView toAlarmHistoryDetail(ModelMap map,AlarmHistoryInfo vo)throws Exception {		
		logger.info("告警详情id:"+vo.getAlarmID());
		vo = alarmHistoryMapper.getInfoById(vo.getAlarmID());
		map.put("alarmVo", vo);
		return new ModelAndView("monitor/alarmMgr/alarmhistory/alarmHistory_detail");
	}
	
	/**
	 * 根据用户ID查询用户名
	 * @param userId
	 * @return
	 */
	public String queryUserNameById(String userId){
		SysUserInfoBean bean=sysUserService.findSysUserById(Integer.parseInt(userId));
		if (null == bean) {
			return "";
		}
		return bean.getUserName();
	}
}
