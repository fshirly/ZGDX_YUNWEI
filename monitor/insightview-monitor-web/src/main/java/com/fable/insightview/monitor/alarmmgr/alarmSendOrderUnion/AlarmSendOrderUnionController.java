package com.fable.insightview.monitor.alarmmgr.alarmSendOrderUnion;

import java.util.ArrayList;
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
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.monitor.alarmmgr.alarmsendorderunion.service.AlarmSendOrderUnionService;
import com.fable.insightview.monitor.entity.AlarmNode;
import com.fable.insightview.platform.common.BaseControllerModified;
import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.common.util.DateUtil;
import com.fable.insightview.platform.common.util.SystemFinalValue;
import com.fable.insightview.platform.page.Page;

/**
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/monitor/alarmSendOrderUnion")
public class AlarmSendOrderUnionController extends BaseControllerModified {

	@Autowired
	private AlarmSendOrderUnionService alarmSendOrderUnionService;

	// 统一告警派单列表展示条数
	private static int DEFAULT_ALARM_LIST = 10;

	private final static Logger LOG = LoggerFactory.getLogger(AlarmSendOrderUnionController.class);

	// 告警派单新窗口
	@RequestMapping("/toNewAlarmSendOrderUnion")
	@ResponseBody
	public ModelAndView newAlarmSendOrderUnionView(String alarmIds, HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("alarmIds", alarmIds);
		String transferTimeStr = DateUtil.date2String(new Date());
		modelAndView.addObject("transferTimeStr", transferTimeStr);
		SecurityUserInfoBean user = (SecurityUserInfoBean) request.getSession()
				.getAttribute("sysUserInfoBeanOfSession");
		if (null != user) {
			modelAndView.addObject("userId", user.getId());
			modelAndView.addObject("username", user.getUserName());
		}
		modelAndView.setViewName("monitor/alarmMgr/alarmSendOrderUnion/newAlarmSendOrderUnion");
		return modelAndView;

	}

	@RequestMapping("/alarmSendOrderUnionConfig")
	@ResponseBody
	public Map<String, Object> newAlarmSendOrderUnionConfig(String alarmIds) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		Page<AlarmNode> page = new Page<AlarmNode>();
		page.setPageSize(DEFAULT_ALARM_LIST);
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("alarmIds", alarmIds);
		page.setParams(params);
		try {
			List<AlarmNode> alarmActiveList = alarmSendOrderUnionService.getAlarmListByAlarmId(page);
			if (!alarmActiveList.isEmpty()) {
				result.put("rows", alarmActiveList);
				result.put("total", page.getTotalRecord());
			}

		} catch (Exception e) {
			LOG.error("Query alarm active failed!");

		}
		return result;
	}

	// 统一告警派单老窗口
	@RequestMapping("/toOldAlarmSendOrderUnion")
	@ResponseBody
	public ModelAndView oldAlarmSendOrderUnionView(String alarmIds, HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("alarmIds", alarmIds);
		String transferTimeStr = DateUtil.date2String(new Date());
		modelAndView.addObject("transferTimeStr", transferTimeStr);
		SecurityUserInfoBean user = (SecurityUserInfoBean) request.getSession()
				.getAttribute("sysUserInfoBeanOfSession");
		if (null != user) {
			modelAndView.addObject("userId", user.getId());
			modelAndView.addObject("username", user.getUserName());
		}
		modelAndView.setViewName("monitor/alarmMgr/alarmSendOrderUnion/oldAlarmSendOrderUnion");
		return modelAndView;

	}

	@RequestMapping("toThirdVersionSendOrderUnion")
	@ResponseBody
	public ModelAndView thirdVersionAlarmSendOrderUnionView(String alarmIds, HttpServletRequest request)
			throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("alarmIds", alarmIds);
		AlarmNode alarmNode = new AlarmNode();
		try {
			alarmNode = alarmSendOrderUnionService.getInfoById(Integer.parseInt(alarmIds));
		} catch (Exception e) {
			LOG.error("Failed to query alarm detail");
		}
		if (null != alarmNode) {
			modelAndView.addObject("alarmDetail", "[" + alarmNode.getSourceMOName() + "-"
					+ alarmNode.getSourceMOIPAddress() + "]" + alarmNode.getAlarmTitle());
			modelAndView.addObject("alarmNode", alarmNode);
		}
		String transferTimeStr = DateUtil.date2String(new Date());
		modelAndView.addObject("transferTimeStr", transferTimeStr);
		SecurityUserInfoBean user = (SecurityUserInfoBean) request.getSession()
				.getAttribute("sysUserInfoBeanOfSession");
		if (null != user) {
			modelAndView.addObject("userId", user.getId());
			modelAndView.addObject("username", user.getUserName());
		}
		modelAndView.setViewName("monitor/alarmMgr/alarmSendOrderUnion/thirdVersionAlarmSendOrderUnion");
		return modelAndView;

	}

	/**
	 * 根据告警ID查询出告警状态，是否存在已派发的告警
	 * 
	 * @param alarmIds
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryAlarmStatusById")
	@ResponseBody
	public Boolean queryAlarmStatusById(String alarmIds) throws Exception {
		Boolean result = true;
		ArrayList<Integer> alarmStatus = new ArrayList<Integer>();
		ArrayList<String> alarmIdsList = new ArrayList<String>();
		if (null != alarmIds && "" != alarmIds) {
			for (String status : alarmIds.toString().split(",")) {
				alarmIdsList.add(status);
			}
		}
		try {
			alarmStatus = (ArrayList<Integer>) alarmSendOrderUnionService.getAlarmStatusById(alarmIdsList);
			if (null != alarmStatus && !alarmStatus.isEmpty()) {
				for (Integer status : alarmStatus) {
					if (SystemFinalValue.ALREADY_SIGNED_ALARM == status) {
						result = false;
					}
				}

			}
		} catch (Exception e) {

			LOG.error(e.getMessage(), e);
		}

		return result;
	}

}
