package com.fable.insightview.monitor.alarmmgr.alarmnotifycfg.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

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

import com.fable.insightview.monitor.alarmmgr.alarmnotify.entity.AlarmNotifyBean;
import com.fable.insightview.monitor.alarmmgr.alarmnotify.mapper.AlarmNotifyCfgMapper;
import com.fable.insightview.monitor.alarmmgr.alarmnotifyfilter.mapper.AlarmNotifyFilterMapper;
import com.fable.insightview.monitor.alarmmgr.alarmnotifytousers.mapper.AlarmNotifyToUsersMapper;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmEventDefineBean;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmLevelInfo;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmNotifyCfgBean;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmNotifyFilterBean;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmNotifyToUsersBean;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmTypeInfo;
import com.fable.insightview.monitor.alarmmgr.entity.MOSourceBean;
import com.fable.insightview.platform.message.entity.PhoneVoiceBean;
import com.fable.insightview.platform.message.mapper.PhoneVoiceMapper;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.service.ISysUserService;

@Controller
@RequestMapping("/monitor/alarmNotifyCfg")
public class AlarmNotifyCfgController {
	private static final int ZERO = 0;
	private static final int VOICE = 2;
	private Logger logger = LoggerFactory
			.getLogger(AlarmNotifyCfgController.class);

	@Autowired
	AlarmNotifyCfgMapper alarmNotifyCfgMapper;
	@Autowired
	AlarmNotifyFilterMapper alarmNotifyFilterMapper;
	@Autowired
	AlarmNotifyToUsersMapper notifyToUsersMapper;
	@Autowired
	ISysUserService sysUserService;
	@Autowired
	Cache alarmNotifyConfigCache;
	@Autowired
	PhoneVoiceMapper phoneVoiceMapper;
	/**
	 * 告警通知策略页面弹出
	 * 
	 * @return
	 */
	@RequestMapping("/toAlarmNotifyCfg")
	@ResponseBody
	public ModelAndView toAlarmEventDefineList(String navigationBar) {
		logger.info("进入告通知策略界面");
		ModelAndView mv =new ModelAndView("monitor/alarmMgr/alarmnotifycfg/alarmNotifyCfg_list");
		mv.addObject("navigationBar", navigationBar);
		return mv;
	}

	/**
	 * 加载表格数据
	 */
	@RequestMapping("/listAlarmNotifyCfg")
	@ResponseBody
	public Map<String, Object> listAlarmNotifyCfg(
			AlarmNotifyCfgBean alarmNotifyCfgBean) {
		logger.info("开始加载数据。。。。。。。。");
		logger.info("查询的内容：" + alarmNotifyCfgBean.getPolicyName());
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<AlarmNotifyCfgBean> page = new Page<AlarmNotifyCfgBean>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("policyName", alarmNotifyCfgBean.getPolicyName());
		page.setParams(paramMap);
		List<AlarmNotifyCfgBean> alarmNotifyCfgList = alarmNotifyCfgMapper
				.selectAlarmNotifyCfg(page);
		int total = page.getTotalRecord();
		logger.info("total=====" + total);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", alarmNotifyCfgList);
		logger.info("加载数据结束。。。");
		return result;
	}

	/**
	 * 删除前的验证
	 * 
	 * @param policyID
	 * @return
	 */
	public boolean checkBeforeDel(int policyID) {
		logger.info("删除前的验证。。。。" + policyID);
		List<AlarmNotifyFilterBean> notifyFilterList = alarmNotifyFilterMapper
				.getAlarmNotifyFilterByID(policyID);
		List<AlarmNotifyToUsersBean> notifyToUsersList = notifyToUsersMapper
				.getNotifyToUsersByID(policyID);
		boolean flag = (null == notifyFilterList || notifyFilterList.size() <= 0)
				&& (null == notifyToUsersList || notifyToUsersList.size() <= 0);
		return flag;
	}

	/**
	 * 删除
	 * 
	 * @param alarmNotifyCfgBean
	 * @return
	 */
	@RequestMapping("/delNotifyCfg")
	@ResponseBody
	public boolean delNotifyCfg(AlarmNotifyCfgBean alarmNotifyCfgBean) {
		logger.info("删除。。。。start");
		List<Integer> policyIDs = new ArrayList<Integer>();
		policyIDs.add(alarmNotifyCfgBean.getPolicyID());
		try {
			notifyToUsersMapper.delToUsersByPolicyID(policyIDs);
			alarmNotifyFilterMapper.delFilterByPolicyID(policyIDs);
			alarmNotifyCfgMapper.delAlarmNotifyCfg(policyIDs);

			// 删除后更新缓存
			List<Integer> matchPolicyIDs = alarmNotifyConfigCache.getKeys();
			if (matchPolicyIDs.contains(alarmNotifyCfgBean.getPolicyID())) {
				alarmNotifyConfigCache.remove(alarmNotifyCfgBean.getPolicyID());
			}
			List<Integer> matchPolicyIDs2 = alarmNotifyConfigCache.getKeys();
			return true;
		} catch (Exception e) {
			logger.error("告警通知删除异常：" + e.getMessage());
			return false;
		}

	}

	/**
	 * 批量删除
	 * 
	 * @param alarmEventDefineBean
	 * @return
	 */
	@RequestMapping("/delNotifyCfgs")
	@ResponseBody
	public boolean delAlarmDefines(HttpServletRequest request) {
		String policyIDs = request.getParameter("policyIDs");
		logger.info("批量删除的ID=====" + policyIDs);
		String[] splitIds = policyIDs.split(",");
		/* 能够被删的ID数组 */
		List<Integer> delNotify = new ArrayList<Integer>();
		for (String strId : splitIds) {
			int policyID = Integer.parseInt(strId);
			delNotify.add(policyID);
		}
		logger.info("删除告警通知策略.....start");
		try {
			notifyToUsersMapper.delToUsersByPolicyID(delNotify);
			alarmNotifyFilterMapper.delFilterByPolicyID(delNotify);
			alarmNotifyCfgMapper.delAlarmNotifyCfg(delNotify);
			// 删除后更新缓存
			List<Integer> matchPolicyIDs = alarmNotifyConfigCache.getKeys();
			for (int i = 0; i < delNotify.size(); i++) {
				if (matchPolicyIDs.contains(delNotify.get(i))) {
					alarmNotifyConfigCache.remove(delNotify.get(i));
				}
			}
			return true;
		} catch (Exception e) {
			logger.error("删除异常：" + e.getMessage());
			return false;
		}
	}

	/**
	 * 打开详情页面前的判断
	 * 
	 * @param alarmEventDefineBean
	 * @return
	 */
	@RequestMapping("/checkForOpenDetail")
	@ResponseBody
	public Map<String, Object> checkForOpenDetail(
			AlarmNotifyCfgBean alarmNotifyCfgBean) {
		logger.info("打开详情页面前的判断...........start");
		AlarmNotifyCfgBean notify = alarmNotifyCfgMapper
				.getAlarmNotifyCfgByID(alarmNotifyCfgBean.getPolicyID());
		int smsFlag = notify.getIsSms();
		int emailFlag = notify.getIsEmail();
		logger.info("smsFlag=======" + smsFlag + ",emailFlag========="
				+ emailFlag);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("smsFlag", smsFlag);
		result.put("emailFlag", emailFlag);
		result.put("policyID", alarmNotifyCfgBean.getPolicyID());
		return result;

	}

	/**
	 * 打开查看页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/toShowAlarmNotifyCfgDetail")
	@ResponseBody
	public ModelAndView toShowAlarmEventDefineDetail(HttpServletRequest request) {
		logger.info("打开查看页面");
		String policyID = request.getParameter("policyID");
		String smsFlag = request.getParameter("smsFlag");
		String emailFlag = request.getParameter("emailFlag");
		request.setAttribute("policyID", policyID);
		request.setAttribute("smsFlag", smsFlag);
		request.setAttribute("emailFlag", emailFlag);
		return new ModelAndView(
				"monitor/alarmMgr/alarmnotifycfg/alarmNotifyCfg_detail");
	}

	/**
	 * 查看详情
	 */
	@RequestMapping("/viewAlarmNotifyCfg")
	@ResponseBody
	public AlarmNotifyCfgBean getAlarmNotifyCfgInfo(
			AlarmNotifyCfgBean alarmNotifyCfgBean) {
		logger.info("通知策略详情。。。。");
		AlarmNotifyCfgBean alarmNotify = alarmNotifyCfgMapper
				.getAlarmNotifyCfgByID(alarmNotifyCfgBean.getPolicyID());
		return alarmNotify;
	}

	/**
	 * 打开新增页面
	 */
	@RequestMapping("/toShowAlarmNotify")
	@ResponseBody
	public ModelAndView toShowAlarmAdd(HttpServletRequest request) {
		logger.info("加载新增页面");
		String policyID = request.getParameter("policyID");
		request.setAttribute("policyID", policyID);
		//获得所有的录音
		List<PhoneVoiceBean> voiceList = phoneVoiceMapper.queryAllPhoneVoice();
		return new ModelAndView("monitor/alarmMgr/alarmnotifycfg/alarmNotifyCfg_add").addObject("voiceList", voiceList);
	}

	/**
	 * 验证规则名称的唯一性
	 */
	@RequestMapping("/checkNamebeforeUpdate")
	@ResponseBody
	public boolean checkNamebeforeUpdate(String policyName, int policyID) {
		logger.info("更新验证规则的唯一性......" + policyName + "=====" + policyID);
		AlarmNotifyCfgBean alarmNotifyCfgBean = new AlarmNotifyCfgBean();
		alarmNotifyCfgBean.setPolicyID(policyID);
		alarmNotifyCfgBean.setPolicyName(policyName);
		int checkRs = alarmNotifyCfgMapper
				.getAlarmNotifyCfgByName(alarmNotifyCfgBean);
		if (checkRs > 0) {
			return false;
		} else {
			return true;
		}
	}

	@RequestMapping("/checkNamebeforeAdd")
	@ResponseBody
	public boolean checkNamebeforeAdd(String policyName) {
		logger.info("新增时验证规则的唯一性......" + policyName);
		AlarmNotifyCfgBean alarmNotifyCfgBean = new AlarmNotifyCfgBean();
		alarmNotifyCfgBean.setPolicyName(policyName);
		int checkRs = alarmNotifyCfgMapper.getByName(alarmNotifyCfgBean);
		if (checkRs > 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 更新
	 */
	@RequestMapping("/addNotify")
	@ResponseBody
	public boolean addNotify(AlarmNotifyBean alarmNotifyBean) {
		int notifyCfgInsert = 0;
		AlarmNotifyCfgBean notifyCfg = alarmNotifyBean.getNotifyCfg();
		try {
			if (null == notifyCfg.getPolicyID()
					|| "".equals(notifyCfg.getPolicyID())) {
				logger.info("新增通知策略。。。。。");
				addAlarmNotifyCfg(notifyCfg);
			} else {
				setParamValue(notifyCfg);
				notifyCfgInsert = alarmNotifyCfgMapper
						.updateAlarmNotifyCfg(notifyCfg);
			}
			logger.info("更新通知策略的结果为：" + notifyCfgInsert);

			logger.info("======新增或更新后更新缓存=====");
			Element element = new Element(notifyCfg.getPolicyID(), notifyCfg);
			alarmNotifyConfigCache.put(element);
			return true;
		} catch (Exception e) {
			logger.error("新增失败：" + e.getMessage());
			return false;
		}
	}

	/**
	 * 新增
	 */
	@RequestMapping("/addAlarmNotifyCfg")
	@ResponseBody
	public Map<String, Integer> addAlarmNotifyCfg(AlarmNotifyCfgBean bean) {
		logger.info("同步新增通知策略。。。。。");
		Map<String, Integer> result = new HashMap<String, Integer>();
		try {
			setParamValue(bean);
			int notifyCfgInsert = alarmNotifyCfgMapper.insertAlarmNotifyCfg(bean);
			Element element = new Element(bean.getPolicyID(), bean);
			alarmNotifyConfigCache.put(element);
			int policyID = bean.getPolicyID();
			logger.info("同步新增通知策略的结果为：" + notifyCfgInsert + ",id为==="
					+ policyID);
			result.put("policyID", policyID);
		} catch (Exception e) {
			logger.error("同步新增失败：" ,e);
		}
		return result;
	}

	@RequestMapping("/getPolicy")
	@ResponseBody
	public Map<String, Object> getPolicy() {
		logger.info("新增前创建空通知策略。。。");
		boolean flag = false;
		AlarmNotifyCfgBean notifyCfgBean = new AlarmNotifyCfgBean();
		notifyCfgBean.setPolicyName("");
		notifyCfgBean.setIsSms(0);
		notifyCfgBean.setmFlag(1);
		int insertCount = alarmNotifyCfgMapper
				.insertAlarmNotifyCfg(notifyCfgBean);
		int policyID = notifyCfgBean.getPolicyID();
		logger.info("通知策略的id为=====" + policyID);
		if (insertCount > 0) {
			flag = true;
		} else {
			flag = false;
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("flag", flag);
		result.put("policyID", policyID);
		return result;
	}

	/**
	 * 打开编辑页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/toShowAlarmNotifyCfgModify")
	@ResponseBody
	public ModelAndView toShowAlarmNotifyCfgModify(HttpServletRequest request) {
		logger.info("打开查看页面");
		String policyID = request.getParameter("policyID");
		String smsFlag = request.getParameter("smsFlag");
		String emailFlag = request.getParameter("emailFlag");
		request.setAttribute("policyID", policyID);
		request.setAttribute("smsFlag", smsFlag);
		request.setAttribute("emailFlag", emailFlag);
		//获得所有的录音
		List<PhoneVoiceBean> voiceList = phoneVoiceMapper.queryAllPhoneVoice();
		return new ModelAndView("monitor/alarmMgr/alarmnotifycfg/alarmNotifyCfg_modify").addObject("voiceList", voiceList);
	}

	@RequestMapping("/checkBeforeCancle")
	@ResponseBody
	public Map<String, Object> checkBeforeCancle() {
		boolean flag = false;
		String policyIDs = "";
		List<AlarmNotifyCfgBean> NotifyList = alarmNotifyCfgMapper
				.getNotifyByName();
		Map<String, Object> result = new HashMap<String, Object>();
		if (NotifyList == null || NotifyList.size() == 0) {
			flag = true;
		} else {
			flag = false;
			for (int i = 0; i < NotifyList.size(); i++) {
				policyIDs += NotifyList.get(i).getPolicyID() + ",";
			}
			policyIDs = policyIDs.substring(0, policyIDs.lastIndexOf(","));
		}
		result.put("policyIDs", policyIDs);
		result.put("flag", flag);
		return result;
	}

	@RequestMapping("/toCancelAdd")
	@ResponseBody
	public boolean toCancelAdd(HttpServletRequest request) {
		String policyIDs = request.getParameter("policyIDs");
		String[] splitIds = policyIDs.split(",");
		List<Integer> idList = new ArrayList<Integer>();
		for (String strId : splitIds) {
			idList.add(Integer.parseInt(strId));
		}
		try {
			alarmNotifyFilterMapper.delFilterByPolicyID(idList);
			notifyToUsersMapper.delToUsersByPolicyID(idList);
			alarmNotifyCfgMapper.delAlarmNotifyCfg(idList);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@RequestMapping("/listSysUser")
	@ResponseBody
	public Map<String, Object> listSysUser(SysUserInfoBean sysUserBean)
			throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();

		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);

		List<SysUserInfoBean> userLst = sysUserService
				.getSysUserByNotifyFilter(sysUserBean, flexiGridPageInfo);
		// 获取总记录数
		int total = sysUserService.getTotalCountByNotifyFilter(sysUserBean);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", userLst);

		return result;
	}

	public Cache getAlarmNotifyConfigCache() {
		return alarmNotifyConfigCache;
	}

	public void setAlarmNotifyConfigCache(Cache alarmNotifyConfigCache) {
		this.alarmNotifyConfigCache = alarmNotifyConfigCache;
	}

	/**
	 * 告警级别页面弹出
	 * 
	 * @return
	 */
	@RequestMapping("/toSelectAlarmLevel")
	public ModelAndView toSelectAlarmLevel(HttpServletRequest request,
			ModelMap map) throws Exception {
		String proUrl = "/monitor/alarmNotifyCfg/filterAlarmLevel?policyID="
				+ request.getParameter("policyID");
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
		String proUrl = "/monitor/alarmNotifyCfg/filterAlarmType?policyID="
				+ request.getParameter("policyID");
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
		String proUrl = "/monitor/alarmNotifyCfg/filterMOSource?policyID="
				+ request.getParameter("policyID");
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
		String proUrl = "/monitor/alarmNotifyCfg/filterAlarmEvent?policyID="
				+ request.getParameter("policyID");
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
		paramMap.put("policyID", request.getParameter("policyID"));
		paramMap.put("alarmLevelName", request.getParameter("alarmLevelName"));
		page.setParams(paramMap);
		// 查询分页数据
		List<AlarmLevelInfo> list = alarmNotifyFilterMapper
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
		paramMap.put("policyID", request.getParameter("policyID"));
		paramMap.put("alarmTypeName", request.getParameter("alarmTypeName"));
		page.setParams(paramMap);
		// 查询分页数据
		List<AlarmTypeInfo> list = alarmNotifyFilterMapper
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
		paramMap.put("policyID", request.getParameter("policyID"));
		paramMap.put("moName", request.getParameter("moName"));
		paramMap.put("deviceIP", request.getParameter("deviceIP"));
		page.setParams(paramMap);
		// 查询分页数据
		List<MOSourceBean> list = alarmNotifyFilterMapper
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
		paramMap.put("policyID", request.getParameter("policyID"));
		paramMap.put("alarmName", request.getParameter("alarmName"));
		page.setParams(paramMap);
		// 查询分页数据
		List<AlarmEventDefineBean> list = alarmNotifyFilterMapper
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
	
	private void setParamValue(AlarmNotifyCfgBean bean){
		bean.setmFlag(1);
//		int isSms = bean.getIsSms();
//		int isEmail = bean.getIsEmail();
//		int isPhone = bean.getIsPhone();
//		int voiceMessageType = bean.getVoiceMessageType();
//		//不需要短信通知
//		if(isSms == ZERO){
//			bean.setSmsAlarmContent(null);
//			bean.setSmsClearAlarmContent(null);
//			bean.setSmsDeleteAlarmContent(null);
//		}
//		//不需要邮件通知
//		if(isEmail == ZERO){
//			bean.setEmailAlarmContent(null);
//			bean.setEmailClearAlarmContent(null);
//			bean.setEmailDeleteAlarmContent(null);
//		}
//		
//		//不需要电话语音处理通知
//		if(isPhone == ZERO){
//			bean.setPhoneAlarmContent(null);
//			bean.setPhoneClearAlarmContent(null);
//			bean.setPhoneDeleteAlarmContent(null);
//			bean.setAlarmVoiceID(null);
//			bean.setClearAlarmVoiceID(null);
//			bean.setDeleteAlarmVoiceID(null);
//		}else if(voiceMessageType == VOICE){
//			bean.setPhoneAlarmContent(null);
//			bean.setPhoneClearAlarmContent(null);
//			bean.setPhoneDeleteAlarmContent(null);
//			if(bean.getAlarmVoiceID() == -1){
//				bean.setAlarmVoiceID(null);
//			}
//			if(bean.getClearAlarmVoiceID() == -1){
//				bean.setClearAlarmVoiceID(null);
//			}
//			if(bean.getDeleteAlarmVoiceID() == -1){
//				bean.setDeleteAlarmVoiceID(null);
//			}
//		}else{
//			bean.setAlarmVoiceID(null);
//			bean.setClearAlarmVoiceID(null);
//			bean.setDeleteAlarmVoiceID(null);
//		}
	}
}
