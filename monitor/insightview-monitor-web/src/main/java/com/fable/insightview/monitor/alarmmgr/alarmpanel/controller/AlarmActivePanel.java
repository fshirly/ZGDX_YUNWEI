package com.fable.insightview.monitor.alarmmgr.alarmpanel.controller;

import java.io.IOException;
import java.text.SimpleDateFormat; 
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fable.enclosure.bussiness.entity.PageRequest;
import com.fable.enclosure.bussiness.entity.PageResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.monitor.alarmdispatcher.utils.AlarmObjToJSON;
import com.fable.insightview.monitor.alarmmgr.alarmactive.controller.AlarmActiveController;
import com.fable.insightview.monitor.alarmmgr.alarmactive.mapper.AlarmActiveMapper;
import com.fable.insightview.monitor.alarmmgr.alarmview.entity.ParamterVo;
import com.fable.insightview.monitor.alarmmgr.alarmview.mapper.AlarmViewMapper;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmLevelInfo;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmViewColCfgInfo;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmViewFilterInfo;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmViewInfo;
import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.common.util.DateUtil;
import com.fable.insightview.platform.common.util.SystemFinalValue;
import com.fable.insightview.platform.sysconf.service.SysConfigService;
import com.fable.insightview.monitor.entity.AlarmNode;

@Controller
@RequestMapping("/monitor/alarmMgr/alarmPanel")
public class AlarmActivePanel {
	
	@Autowired
	AlarmViewMapper alarmViewMapper;
	
	@Autowired
	AlarmActiveMapper alarmActiveMapper;
	
	@Autowired
	private SysConfigService sysConfigServiceImpl;
	
	private final static Logger logger = LoggerFactory.getLogger(AlarmActiveController.class);
	
	@RequestMapping("/loadActive")
	public ModelAndView loadActive(HttpServletRequest request,
			HttpServletResponse response) {
		// 获取登陆用户编号
		SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
							.getSession().getAttribute("sysUserInfoBeanOfSession"); 
		ModelAndView mv = new ModelAndView();
		// 是否启用新告警派单
		String workOrderVersion = this.sysConfigServiceImpl.getConfParamValue(SystemFinalValue.SYS_CONFIG_TYPE_PROCESS_WOA ,SystemFinalValue.SCT_PROC_WOA_CITY_WORKORDER_FLAG);
		if(StringUtils.isNotEmpty(workOrderVersion)) {
			mv.addObject("alarmOrderVersion", workOrderVersion);
		}
		String transferTimeStr = DateUtil.date2String(new Date());
		mv.addObject("transferTimeStr", transferTimeStr);
		mv.setViewName("monitor/alarmMgr/alarmPanel/newVersionAlarm");
		String viewCfgID = request.getParameter("viewCfgID");
		if (viewCfgID == null || viewCfgID.equals("")) {
			viewCfgID = "0";
		}
		mv.addObject("viewCfgID", viewCfgID);
		mv.addObject("userID", sysUserInfoBeanTemp.getId().intValue());
		mv.addObject("username", sysUserInfoBeanTemp.getUserName());
		return mv; 
	}
	
	@RequestMapping("/getViewColumn")
	public void getViewColumn(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String,String> params = new HashMap<String,String>();
			params.put("userID", " and UserID="+request.getParameter("userID"));
			
			String viewCfgID = request.getParameter("viewCfgID");
			if (viewCfgID != null && viewCfgID.equals("0")) {
				params.put("viewFilter", " and UserDefault=1");
			} else {
				params.put("viewFilter", " and ViewCfgID=" + viewCfgID);
			}
			
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("userID", " and UserID="+request.getParameter("userID"));
			param.put("viewFilter", " and UserDefault=1");
			
			List<AlarmViewColCfgInfo> viewColumn = alarmViewMapper.queryColCfgListByUserID(params);
			// 根据用户id判断是否有默认的视图
			List<AlarmViewInfo> viewInfo = alarmViewMapper.queryViewListByUserID(param);
			// 默认进来时视图ID（viewCfgID==0）判断是否有默认的视图
			String flag =null;
			if (viewInfo.size() == 0) {
				params = new HashMap<String,String>();
				params.put("userID", " and UserID="+ request.getParameter("userID"));
				viewInfo = alarmViewMapper.queryNewViewByUserID(params);
				// 没有默认的视图
				flag ="0";
			}
			if("0".equals(flag))
			{
				if (viewColumn.size() == 0) {
					params = new HashMap<String,String>();
					params.put("userID", " and UserID="+ request.getParameter("userID"));
					viewColumn = alarmViewMapper.queryColCfgListByUserID(params);
				}
			}
			
			if(viewInfo.size() ==0 && viewCfgID.equals("0"))
			{
				if (viewColumn.size() == 0) {
					params = new HashMap<String,String>();
					params.put("userID", " and UserID="+ request.getParameter("userID"));
					viewColumn = alarmViewMapper.queryColCfgListByUserID(params);
				}
				if (viewColumn.size() == 0) {
					params = new HashMap<String,String>();
					params.put("viewFilter", " and IsSystem=1 and UserDefault=1");
					viewColumn = alarmViewMapper.queryColCfgListByUserID(params);
				}
			}
			
			String colName = "";
			String evalStr = "";
			
			for (AlarmViewColCfgInfo column : viewColumn) {
				colName = column.getColName().substring(0, 2).toLowerCase()
						+ column.getColName().substring(2,column.getColName().length());
				if (colName.equals("alarmOperateStatus")) {
					colName = "operateStatusName";
				}
				evalStr += "columnModel.getDataByTag('" + colName + "').setVisible(true);";
			}
			
			logger.debug("显示列:"+evalStr);
			response.setContentType("application/json");
			response.getWriter().write("{\"evalStr\":\"" +evalStr+"\"}"); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}  
	
	/**
	 * 下拉视图选项
	 */
	@RequestMapping("/getViewNameOption")
	public void getViewNameOption(HttpServletRequest request, HttpServletResponse response) {
		try { 
			ParamterVo param = new ParamterVo();
			param.setKey("userID");
			param.setVal(request.getParameter("userID"));
			String viewCfgID = request.getParameter("viewCfgID"); 
			String evalStr = "";
			List<AlarmViewInfo> userView = alarmViewMapper.queryAllViewByUserID(param);
			int maxViewID = 0;
			Map<String,Object> parMap = new HashMap<String, Object>();
			//将默认视图展示开始
			if(userView.size()==0)
			{
				parMap.put("viewFilter", " and IsSystem=1 and UserDefault=1");
				List<AlarmViewInfo> viewInfo = alarmViewMapper.queryViewListByUserID(parMap);
				for (AlarmViewInfo alarmViewInfo : viewInfo) {
						evalStr += "<option value=" + alarmViewInfo.getViewCfgID() + " selected>" + alarmViewInfo.getCfgName()+ "</option>";
				}
			}//将默认视图展示结束
			
			for (AlarmViewInfo view : userView) {
				if (viewCfgID != null && !viewCfgID.equals("0") && viewCfgID.compareTo("") > 0) {
					if (viewCfgID != null
							&& view.getViewCfgID() == Integer.parseInt(viewCfgID)) {
						evalStr += "<option value=" + view.getViewCfgID()
								+ " selected>" + view.getCfgName()
								+ "</option>";
					} else {
						evalStr += "<option value=" + view.getViewCfgID() + ">"
								+ view.getCfgName() + "</option>";
					}
				} else {
					if (view.getUserDefault() == 1) {
						evalStr += "<option value=" + view.getViewCfgID()
								+ " selected>" + view.getCfgName()
								+ "</option>";
					} else {
						if (maxViewID < view.getViewCfgID()) {
							maxViewID = view.getViewCfgID();
						}
						evalStr += "<option value=" + view.getViewCfgID() + ">"
								+ view.getCfgName() + "</option>";
					}
				}
			}

			if (evalStr.toString().indexOf("selected") <= -1) {
				evalStr = evalStr.toString().replace(String.valueOf(maxViewID),
						String.valueOf(maxViewID) + " selected");
			}
			// return
			response.setContentType("application/json");
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write("{\"evalStr\":\"" +evalStr+"\"}"); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}  
	
	/**
	 * 实时告警列表页面查询统计条数
	 */
	@RequestMapping("/getAlarmCount")
	public void getAlarmCount(HttpServletRequest request, HttpServletResponse response) {
		try {
			int defaultInterval = -1;
			int defaultCount = 500;
			String evalStr = "";
			 
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("userID", request.getParameter("userID"));
			
			String viewCfgID = request.getParameter("viewCfgID");
			if (viewCfgID != null && !viewCfgID.equals("0")) {
				params.put("viewFilter", " and tb.viewCfgID = " + viewCfgID);
			} else {
				params.put("viewFilter", " and tb.UserDefault=1");
			}
			
			//查询默认加载数
			AlarmViewInfo userView = alarmViewMapper.queryViewInfoByCfgID(params);
			if (userView != null) {
				defaultCount = userView.getDefaultRows();
				if(defaultCount < 0) {
					defaultCount = 500;
				}
				defaultInterval = userView.getDefaultInterval();
				if (defaultInterval > 0) {
					params.put("limitedTime", getPreHour(defaultInterval));
				}
			}
			params.put("alarmcount", defaultCount); 
			logger.debug("\n 实时告警列表 开始时间:" + getPreHour(defaultInterval)
					+ "  defaultCount:" + defaultCount);
			
			//查询有哪些类型的加载条件
			List<AlarmViewFilterInfo> filterInfo = alarmViewMapper
					.queryDefaultFilterByUser(params);
			if (filterInfo.size() == 0) {
				params.put("userID", 0); 
				params.put("viewFilter", " and tb.UserDefault=1");
				filterInfo = alarmViewMapper.queryDefaultFilterByUser(params);
			}
			
			for (AlarmViewFilterInfo viewFilter : filterInfo) {
				if (viewFilter.getFilterKey().equals("AlarmLevel")) {
					params.put("alarmlevel", "true");
				} else if (viewFilter.getFilterKey().equals("AlarmType")) {
					params.put("alarmtype", "true");
				} else if (viewFilter.getFilterKey().equals("AlarmSourceMOID")) {
					params.put("alarmsource", "true");
				} else if (viewFilter.getFilterKey().equals("AlarmDefineID")) {
					params.put("alarmdefine", "true");
				}
			}

			
			List<AlarmLevelInfo> alarmCount= alarmActiveMapper.loadActiveAlarmCount(params);
			for (AlarmLevelInfo levelInfo : alarmCount) {
				if (levelInfo.getAlarmLevelID() == 1) {
					evalStr += "document.getElementById('emergency').innerHTML=bbb<img src='../../../style/images/levelIcon/1.png'/></b>&nbsp;"
						+levelInfo.getTotalNum()+"bbb;";
				} else if (levelInfo.getAlarmLevelID() == 2) {
					evalStr += "document.getElementById('severity').innerHTML=bbb<img src='../../../style/images/levelIcon/2.png'/>&nbsp;"
						+levelInfo.getTotalNum()+"bbb;";
				} else if (levelInfo.getAlarmLevelID() == 3) {
					evalStr += "document.getElementById('ordinary').innerHTML=bbb<img src='../../../style/images/levelIcon/3.png'/>&nbsp;"
						+levelInfo.getTotalNum()+"bbb;";
				} else if (levelInfo.getAlarmLevelID() == 4) {
					evalStr += "document.getElementById('prompt').innerHTML=bbb<img src='../../../style/images/levelIcon/4.png'/>&nbsp;"
						+levelInfo.getTotalNum()+"bbb;";
				} else if (levelInfo.getAlarmLevelID() == 5) {
					evalStr += "document.getElementById('uncertain').innerHTML=bbb<img src='../../../style/images/levelIcon/5.png'/>&nbsp;"
						+levelInfo.getTotalNum()+"bbb;";
				}				
			}
			//logger.info("evalStr="+evalStr);
			response.setContentType("application/json");
			response.getWriter().write("{\"evalStr\":\"" +evalStr+"\"}"); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/loadActiveAlarmList")
	public void loadActiveAlarmList(HttpServletRequest request, HttpServletResponse response) {
		try {
			logger.info("实时告警面板,加载部分活动告警");
			int defaultCount = 500;
			int defaultInterval = -1;
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("userID", request.getParameter("userID"));
			
			String viewCfgID = request.getParameter("viewCfgID");
			if (viewCfgID != null && !viewCfgID.equals("0")) {
				params.put("viewFilter", " and tb.viewCfgID = " + viewCfgID);
			} else {
				params.put("viewFilter", " and tb.UserDefault=1");
			}
			
			//查询默认加载数
			AlarmViewInfo userView = alarmViewMapper.queryViewInfoByCfgID(params);
			if (userView != null) {
				defaultCount = userView.getDefaultRows();
				if(defaultCount < 0) {
					defaultCount = 500;
				}
				defaultInterval = userView.getDefaultInterval();
				if (defaultInterval > 0) {
					params.put("limitedTime", getPreHour(defaultInterval));
				}
			}
			
			params.put("alarmcount", defaultCount); 
			logger.debug("\n 实时告警列表 开始时间:" + getPreHour(defaultInterval)
					+ "  defaultCount:" + defaultCount);
			
			//查询有哪些类型的加载条件
			List<AlarmViewFilterInfo> filterInfo = alarmViewMapper
					.queryDefaultFilterByUser(params);
			if (filterInfo.size() == 0) {
				params.put("userID", 0); 
				params.put("viewFilter", " and tb.UserDefault=1");
				filterInfo = alarmViewMapper.queryDefaultFilterByUser(params);
			}
			for (AlarmViewFilterInfo viewFilter : filterInfo) {
				if (viewFilter.getFilterKey().equals("AlarmLevel")) {
					params.put("alarmlevel", "true");
				} else if (viewFilter.getFilterKey().equals("AlarmType")) {
					params.put("alarmtype", "true");
				} else if (viewFilter.getFilterKey().equals("AlarmSourceMOID")) {
					params.put("alarmsource", "true");
				} else if (viewFilter.getFilterKey().equals("AlarmDefineID")) {
					params.put("alarmdefine", "true");
				}
			}
			List<AlarmNode> alarmList= alarmActiveMapper.loadActiveAlarm(params);
			StringBuffer returnTmp = new StringBuffer();
			int count = 0;
			for (AlarmNode alarm : alarmList) {
				count++;
				if (count > 1) {
					returnTmp.append(",");
				}
				returnTmp.append(AlarmObjToJSON.writeMapJSON(alarm).toString());
			}
			
			response.setContentType("application/json");
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write("["+returnTmp.toString()+"]");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/loadActiveAlarmListNew")
	@ResponseBody
	public PageResponse<AlarmNode> loadActiveAlarmListNew(@RequestBody PageRequest<Map<String,Object>> param) {
		try {
			int defaultCount = 500;
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("userID", param.getParam().get("userId"));

			String viewCfgID = param.getParam().get("viewCfgID").toString();
			if (viewCfgID != null && !viewCfgID.equals("0")) {
				params.put("viewFilter", " and tb.viewCfgID = " + viewCfgID);
			} else {
				params.put("viewFilter", " and tb.UserDefault=1");
			}

			params.put("alarmcount", defaultCount);

			//查询有哪些类型的加载条件
			List<AlarmViewFilterInfo> filterInfo = alarmViewMapper
					.queryDefaultFilterByUser(params);
			if (filterInfo.size() == 0) {
				params.put("userID", 0);
				params.put("viewFilter", " and tb.UserDefault=1");
				filterInfo = alarmViewMapper.queryDefaultFilterByUser(params);
			}
			for (AlarmViewFilterInfo viewFilter : filterInfo) {
				if (viewFilter.getFilterKey().equals("AlarmLevel")) {
					params.put("alarmlevel", "true");
				} else if (viewFilter.getFilterKey().equals("AlarmType")) {
					params.put("alarmtype", "true");
				} else if (viewFilter.getFilterKey().equals("AlarmSourceMOID")) {
					params.put("alarmsource", "true");
				} else if (viewFilter.getFilterKey().equals("AlarmDefineID")) {
					params.put("alarmdefine", "true");
				}
			}
			List<AlarmNode> alarmList= alarmActiveMapper.loadActiveAlarm(params);
			return PageResponse.wrap(alarmList, param.getPageNo(), param.getPageSize());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return PageResponse.wrap(new ArrayList<AlarmNode>(),param.getPageNo(), param.getPageSize());
	}
 
	public static String getPreHour(int hour) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - hour);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(calendar.getTime());
	}
}