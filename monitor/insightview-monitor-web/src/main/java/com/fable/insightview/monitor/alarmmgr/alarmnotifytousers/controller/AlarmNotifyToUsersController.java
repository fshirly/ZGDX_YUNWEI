package com.fable.insightview.monitor.alarmmgr.alarmnotifytousers.controller;

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

import com.fable.insightview.monitor.alarmmgr.alarmnotifytousers.mapper.AlarmNotifyToUsersMapper;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmNotifyToUsersBean;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.service.ISysUserService;

@Controller
@RequestMapping("/monitor/alarmNotifyToUsers")
public class AlarmNotifyToUsersController {
	private Logger logger = LoggerFactory
			.getLogger(AlarmNotifyToUsersController.class);

	@Autowired
	AlarmNotifyToUsersMapper notifyToUsersMapper;
	@Autowired
	ISysUserService sysUserService;

	/**
	 * 查看详情
	 */
	@RequestMapping("/viewNotifyToUsers")
	@ResponseBody
	public Map<String, Object> getAlarmNotifyCfgInfo(
			AlarmNotifyToUsersBean alarmNotifyToUsersBean) {
		logger.info("初始化通知过滤列表。。。。");
		logger.info("通知规则的ID：" + alarmNotifyToUsersBean.getPolicyID());
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<AlarmNotifyToUsersBean> page = new Page<AlarmNotifyToUsersBean>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("policyID", alarmNotifyToUsersBean.getPolicyID());
		page.setParams(paramMap);
		List<AlarmNotifyToUsersBean> notifyToUsersList = notifyToUsersMapper
				.selectAlarmNotifyToUsers(page);
		int total = page.getTotalRecord();
		logger.info("total=====" + total);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", notifyToUsersList);
		logger.info("加载数据结束。。。");
		return result;
	}

	/**
	 * 从用户列表中添加用户
	 */
	@RequestMapping("/addUserFromList")
	@ResponseBody
	public Map<String, Object> addUserFromList(HttpServletRequest request) {
		boolean flag = false;
		try {
			String userIds = request.getParameter("userIds");
			String policyID = request.getParameter("policyID");
			logger.info("从用户列表中添加用户ID = " + userIds);
			String[] splitIds = userIds.split(",");
			for (String strId : splitIds) {
				AlarmNotifyToUsersBean notifyToUsers = new AlarmNotifyToUsersBean();
				notifyToUsers.setUserID(Integer.parseInt(strId));
				List<SysUserInfoBean> userLst = sysUserService
						.getSysUserByConditions("userID", strId);
				SysUserInfoBean sysUserBeanTemp = userLst.get(0);
				notifyToUsers.setPolicyID(Integer.parseInt(policyID));
				notifyToUsers.setEmail(sysUserBeanTemp.getEmail());
				notifyToUsers.setMobileCode(sysUserBeanTemp.getMobilePhone());
				int insertCount = notifyToUsersMapper
						.insertAlarmNotifyToUsers(notifyToUsers);
				logger.info("添加用户的结果：" + insertCount);
			}
			flag = true;
		} catch (Exception e) {
			logger.error("添加用户失败失败！" + e.getMessage());
			logger.info("错误信息打印结束。。。。");
			flag = false;
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("flag", flag);
		return result;
	}

	/**
	 * 临时添加用户
	 * 
	 * @param notifyToUsers
	 * @return
	 */
	@RequestMapping("addNow")
	@ResponseBody
	public boolean addNow(AlarmNotifyToUsersBean notifyToUsers) {
		try {
			int insertCount = notifyToUsersMapper
					.insertAlarmNotifyToUsers(notifyToUsers);
			logger.info("添加用户的结果：" + insertCount);
			return true;
		} catch (Exception e) {
			logger.info("临时添加用户失败" + e.getMessage());
			return false;
		}

	}

	/**
	 * 删除通知人
	 * 
	 * @param alarmNotifyToUsersBean
	 * @return
	 */
	@RequestMapping("/delNotifyToUsesrs")
	@ResponseBody
	public boolean delNotifyToUsesrs(
			AlarmNotifyToUsersBean alarmNotifyToUsersBean) {
		logger.info("删除。。。。start");
		return notifyToUsersMapper.delAlarmNotifyToUsers(alarmNotifyToUsersBean
				.getFilterID());
	}

	@RequestMapping("/checkForAddNow")
	@ResponseBody
	public boolean checkForAddNow(AlarmNotifyToUsersBean alarmNotifyToUsersBean) {
		int checkRS = notifyToUsersMapper
				.selectByConditions(alarmNotifyToUsersBean);
		if (checkRS > 0) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 验证是否已经添加值班负责人
	 * @param alarmNotifyToUsersBean
	 * @return
	 */
	@RequestMapping("/checkForAddDutier")
	@ResponseBody
	public boolean checkForAddDutier(AlarmNotifyToUsersBean alarmNotifyToUsersBean){
		int count = notifyToUsersMapper.selectByIdAndUserID(alarmNotifyToUsersBean);
		if(count > 0){
			return false
			;
		}
		return true;
	}

}
