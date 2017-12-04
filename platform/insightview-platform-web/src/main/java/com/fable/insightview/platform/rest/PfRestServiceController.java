package com.fable.insightview.platform.rest;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.dutymanager.duty.service.IDutyService;
import com.fable.insightview.platform.dutymanager.dutylog.service.IDutyLogService;
import com.fable.insightview.platform.entity.DepartmentBean;
import com.fable.insightview.platform.service.IDepartmentService;
import com.fable.insightview.platform.sysinit.SystemParamInit;
import com.fable.insightview.platform.userAuthenticate.entity.UserInfoBean;
import com.fable.insightview.platform.userAuthenticate.service.IUserInfoService;

@Controller
@RequestMapping("/rest/platform/service")
public class PfRestServiceController {

	private static final Logger LOG = LoggerFactory.getLogger(PfRestServiceController.class);
	
	@Autowired
	private IUserInfoService userService;
	
	@Autowired
	private IDepartmentService departmentService;
	
	@Autowired
	private IDutyService dutyService;
	
	@Autowired
	private IDutyLogService logService;
	
	/**
	 * 依据用户账户获取用户信息
	 * @param userAcount
	 * @return
	 */
	@RequestMapping("/user/{userAccount}")
	@ResponseBody
	public Map<String, Object> queryUser(@PathVariable String userAccount){
		Map<String, Object> user = new HashMap<String, Object>();
		if (StringUtils.isEmpty(userAccount)) {
			return user;
		}
		try {
			return userService.getUserByAccount(userAccount);
		} catch (Exception e) {
			LOG.error("平台Rest接口【依据用户账户查询用户信息】异常：{}" + e + ";用户账户参数：{" + userAccount + "}");
			return user;
		}
	}
	
	/**
	 * 依据用户ID获取用户信息
	 * @param userId
	 * @return
	 */
	@RequestMapping("/userById/{userId}")
	@ResponseBody
	public Map<String, Object> queryUserById(@PathVariable String userId){
		Map<String, Object> user = new HashMap<String, Object>();
		if (StringUtils.isEmpty(userId)) {
			return user;
		}
		try {
			UserInfoBean userInfo = userService.getUserInfoById(Integer.parseInt(userId));
			if (null != userInfo) {
				user.put("userName", userInfo.getUserName());
				user.put("userAccount", userInfo.getUserAccount());
				user.put("userIcon", userInfo.getUserIcon());
			}
		} catch (Exception e) {
			LOG.error("平台Rest接口【依据用户账户查询用户信息】异常：{}" + e + ";用户Id参数：{" + userId + "}");
		}
		return user;
	}
	
	/**
	 * 依据用户账号查询相应单位下的部门信息
	 * @param userAccount
	 * @return
	 */
	@RequestMapping("/departmentByAccount/{userAccount}")
	@ResponseBody
	public List<DepartmentBean> queryDepartmentByAccout(@PathVariable String userAccount) {
		List<DepartmentBean> departments = new ArrayList<DepartmentBean>();
		if (StringUtils.isEmpty(userAccount)) {
			return departments;
		}
		try {
			Map<String, Object> user = userService.getUserByAccount(userAccount);
			if (null == user) {
				return departments;
			}
			DepartmentBean dept = new DepartmentBean();
			dept.setOrganizationID(Integer.parseInt(user.get("OrganizationID") + ""));
			return departmentService.getDepartmentByConditions(dept);
		} catch (Exception e) {
			LOG.error("平台Rest接口【依据用户账号信息查询部门列表】异常：{}" + e + ";用户账号：{" + userAccount + "}");
			return departments;
		}
	}
	
	/**
	 * 依据组织ID查询相应的部门信息
	 * @param orgId
	 * @return
	 */
	@RequestMapping("/departments/{orgId}")
	@ResponseBody
	public List<DepartmentBean> queryDepartmentByOrgId(@PathVariable String orgId) {
		List<DepartmentBean> departments = new ArrayList<DepartmentBean>();
		if (StringUtils.isEmpty(orgId)) {
			return departments;
		}
		try {
			DepartmentBean dept = new DepartmentBean();
			dept.setOrganizationID(Integer.parseInt(orgId));
			return departmentService.getDepartmentByConditions(dept);
		} catch (Exception e) {
			LOG.error("平台Rest接口【依据组织单位Id查询部门列表】异常：{}" + e + ";组织单位Id：{" + orgId + "}");
			return departments;
		}
	}
	
	/**
	 * 依据部门Id查询相应人员信息
	 * @param deptId
	 * @return
	 */
	@RequestMapping("/queryUsers/{deptId}")
	@ResponseBody
	public List<Map<String, Object>> queryUsers(@PathVariable String deptId){
		if (StringUtils.isEmpty(deptId)) {
			return new ArrayList<Map<String,Object>>();
		}
		try {
			return userService.queryUsers(deptId);
		} catch (Exception e) {
			LOG.error("平台Rest接口【依据部门Id查询人员信息】异常：{}" + e + ";单位部门Id：{" + deptId + "}");
			return new ArrayList<Map<String,Object>>();
		}
	}
	
	/**
	 * 验证当前用户是否有操作权限
	 * @param userAccount
	 * @return
	 */
	@RequestMapping("/isOperator/{userAccount}")
	@ResponseBody
	public String isOperator(@PathVariable String userAccount) {
		try {
			return dutyService.isOperator(userAccount);
		} catch (Exception e) {
			LOG.error("平台Rest接口【查询当前登陆用户是否为值班人操作权限】异常：{}" + e + ";用户账户信息：{" + userAccount + "}");
			return "false";
		}
	}

	/**
	 * 验证当前是否可以进行交班操作
	 * @return
	 */
	@RequestMapping("/isExchange")
	@ResponseBody
	public boolean isExchange(){
		try {
			return dutyService.isExchange();
		} catch (Exception e) {
			LOG.error("平台Rest接口【验证是否可以交班】异常：{}", e);
			return false;
		}
	}
	
	/**
	 * 加载模块一中的所有相关数据
	 * @return
	 */
	@RequestMapping("/loadModuleOneData")
	@ResponseBody
	public Map<String, Object> loadModuleOneData() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> orders = dutyService.findOrdersOfDutyBeforeCurrent();
		if(orders != null && !orders.isEmpty()) {
			String fileUrl = SystemParamInit.getValueByKey("fileServerURL");
			Map<String, Object> currentOrder = orders.get(0);//当前值班班次信息
			Integer currentDutyId = Integer.valueOf(currentOrder.get("DutyId").toString());//当前值班ID
			String currentLeaderNotice = dutyService.findLeaderNoticesByDutyId(currentDutyId);//当前领导交班记录
			if(orders.size() >= 2) {
				Map<String, Object> nextOrder = orders.get(1);//下次值班班次信息
				result.put("next", nextOrder);
			}
			result.put("fileUrl", fileUrl);//文件服务器路径
			result.put("leaderNotice", currentLeaderNotice);
			result.put("current", currentOrder);
		}
		List<Map<String, Object>> finishedOrders = dutyService.findLatestFinishedOrdersOfDuty();//最新的已交班的班次信息
		if(finishedOrders!=null && !finishedOrders.isEmpty()) {
			Map<String, Object> finishedOrder = finishedOrders.get(0);
			String noticeInfo = finishedOrder.get("NoticeInfo") == null ? "":finishedOrder.get("NoticeInfo")+"";//最新的已交班的值班记录
			result.put("noticeInfo", noticeInfo);
		}
		return result;
	}
	
	/**
	 * 加载模块二中的所有相关数据
	 * @return
	 */
	@RequestMapping("/loadModuleTwoData")
	@ResponseBody
	public Map<String, Object> loadModuleTwoData() {
		List<Map<String, Object>> finishedOrders = dutyService.findLatestFinishedOrdersOfDuty();//最新的已交班的班次信息
		if(finishedOrders ==null || finishedOrders.isEmpty()) {
			return new HashMap<String, Object>();
		} else {
			return finishedOrders.get(0);
		}
	}
	
	/**
	 * 更新带班领导交办事项
	 * @param logInfo
	 * @return
	 */
	@RequestMapping("/updateLeadLog")
	@ResponseBody
	public String updateLeadLog(@RequestBody String leadInfo) {
		try {
			Map<String, Object> info = JsonUtil.json2Map(URLDecoder.decode(leadInfo, "UTF-8"));
			if (null == info || info.isEmpty()) {
				return "fail";
			}
			logService.updateLeadNotice(info.get("dutyId") + "", info.get("notices") + "");
			return "success";
		} catch (Exception e) {
			LOG.error("platform平台Rest接口【修改值班带班领导的交办事项信息】异常,{}" + e + ";参数：｛" + leadInfo + "}");
			return "fail";
		}
	}
	
	/**
	 * 更新值班人值班日志信息
	 * @param leadInfo
	 * @return
	 */
	@RequestMapping("/updateDutyLog")
	@ResponseBody
	public String updateDutyLog(@RequestBody String logInfo) {
		try {
			Map<String, Object> info = JsonUtil.json2Map(URLDecoder.decode(logInfo, "UTF-8"));
			if (null == info || info.isEmpty()) {
				return "fail";
			}
			logService.updateDutyLogs(info);
			return "success";
		} catch (Exception e) {
			LOG.error("platform平台Rest接口【修改值班人值班日志信息】异常,{}" + e + ";参数：｛" + logInfo + "}");
			return "error";
		}
	}
	
	/**
	 * 依据值班班次ID查询班次信息
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/queryOrder/{orderId}")
	@ResponseBody
	public Map<String, Object> queryOrder (@PathVariable String orderId) {
		try {
			return dutyService.queryOrder(orderId);
		} catch (Exception e) {
			LOG.error("platform平台REST接口【查询值班班次信息】异常, {}" + e + ";参数{" + orderId + "}");
			return new HashMap<String, Object>();
		}
	}
	
	/**
	 * 查询班次相关值班人数
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/queryUserCount/{orderId}")
	public int queryUserCount(@PathVariable String orderId) {
		try {
			return dutyService.queryUsers(orderId);
		} catch (Exception e) {
			LOG.error("platform平台REST接口【查询值班班次的值班人数】异常, {}" + e + ";参数{" + orderId + "}");
		}
		return 0;//异常返回人数为0
	}
	
	/**
	 * 查询交班信息
	 */
	@RequestMapping("/exchanges")
	@ResponseBody
	public Map<String, Object> queryExchanges() {
		try {
			return dutyService.queryExchanges();
		} catch (Exception e) {
			LOG.error("platform平台REST接口【查询交班信息】异常");
			return new HashMap<String, Object>();
		}
	}
}
