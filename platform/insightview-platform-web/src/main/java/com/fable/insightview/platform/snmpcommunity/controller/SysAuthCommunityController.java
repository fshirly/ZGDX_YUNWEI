package com.fable.insightview.platform.snmpcommunity.controller;

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
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.snmpcommunity.entity.MODeviceBean;
import com.fable.insightview.platform.snmpcommunity.entity.SysAuthCommunityBean;
import com.fable.insightview.platform.snmpcommunity.service.ISysAuthCommunityService;

/**
 * SSH/TELNET管理
 * 
 * @author 王淑平
 * 
 */
@Controller
@RequestMapping("/platform/sysAuthCommunity")
public class SysAuthCommunityController {
	@Autowired
	ISysAuthCommunityService sysAuthCommunityService;
	@SuppressWarnings("unused")
	@Autowired
	private HttpServletRequest request;
	private final Logger logger = LoggerFactory
			.getLogger(SysAuthCommunityController.class);

	@RequestMapping("/toSysAuthCommunity")
	public ModelAndView toSysAuthCommunity(String navigationBar) {
		logger.info("开始加载SSH/TELNET管理页面");
		ModelAndView mv = new ModelAndView("platform/snmpcommunity/sysauthcommunity_list");
		mv.addObject("navigationBar", navigationBar);
		return mv;
	}

	/**
	 * 查询列表
	 * 
	 * @param authBean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/listSysAuthCommunity")
	@ResponseBody
	public Map<String, Object> listSysAuthCommunity(
			SysAuthCommunityBean authBean) throws Exception {
		logger.info("初始化SSH/TELNET列表数据...");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();

		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		String type = request.getParameter("type");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<SysAuthCommunityBean> s = sysAuthCommunityService
					.getSysAuthCommunityByConditions(authBean,
							flexiGridPageInfo, type);
			// 获取总记录数
			int total = sysAuthCommunityService.getTotalCount(authBean, type);

			result.put("total", total);
			result.put("rows", s);
		} catch (Exception e) {
			logger.error("查询异常：" + e.getMessage(), authBean);
		}
		logger.info("结束...SSH/TELNET列表数据加载");
		return result;
	}

	/**
	 * 查询设备IP列表 以供选择
	 * 
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findDeviceInfo")
	@ResponseBody
	public MODeviceBean findDeviceInfo(MODeviceBean bean) throws Exception {
		logger.info("加载设备列表");
		List<MODeviceBean> authLst = sysAuthCommunityService.getDeviceById(bean
				.getMoID());
		logger.info("结束...设备列表加载");
		return authLst.get(0);
	}

	/**
	 * 根据设备名称获取相关ID 以供模糊查询
	 * 
	 * @param snmpcommunityBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getMoIdBymoName")
	@ResponseBody
	public SysAuthCommunityBean getMoIdBymoName() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		String moName = request.getParameter("moName");
		SysAuthCommunityBean s = sysAuthCommunityService
				.getMoIDBymoName(moName);
		return s;
	}

	/**
	 * 根据条件查询SNMPCommunity
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author 王淑平
	 */
	@RequestMapping("/findSysAuthCommunityByID")
	@ResponseBody
	public SysAuthCommunityBean findSysAuthCommunityByID(int id)
			throws Exception {
		logger.info("获取更新数据");
		logger.info("被更新数据Id = " + id);
		List<SysAuthCommunityBean> snmpLst = sysAuthCommunityService
				.getSysAuthCommunityById(id);
		if (!snmpLst.isEmpty() && snmpLst.size() > 0) {
			return snmpLst.get(0);
		}
		logger.info("结束...获取更新数据");
		return null;
	}

	@RequestMapping("/checkDeviceIP")
	@ResponseBody
	public boolean checkDeviceIP(SysAuthCommunityBean authBean)
			throws Exception {
		logger.info("验证设备IP是否存在");
		return sysAuthCommunityService.checkDeviceIP(authBean);
	}

	/**
	 * 验证设备IP是否存在
	 * 
	 * @param authBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/checkAddSyaAuthCommunity")
	@ResponseBody
	public boolean checkAddSyaAuthCommunity(SysAuthCommunityBean authBean)
			throws Exception {
		List<SysAuthCommunityBean> snmpLst = sysAuthCommunityService
				.getSysAuthCommunityByConditions(authBean);
		if (null == snmpLst || snmpLst.size() <= 0) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * 新增Telnet/SSH
	 * 
	 * @param authBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addAuthCommunity")
	@ResponseBody
	public boolean addAuthCommunity(SysAuthCommunityBean authBean) {
		logger.info("开始...新增Telnet/SSH");
		try {
			sysAuthCommunityService.addSysAuthCommunity(authBean);
		} catch (Exception e) {
			logger.error("插入异常：" + e.getMessage(), authBean);
			return false;
		}
		logger.info("结束...新增Telnet/SSH");
		return true;
	}

	/**
	 * 修改Telnet/SSH
	 * 
	 * @param authBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAuthCommunity")
	@ResponseBody
	public boolean updateAuthCommunity(SysAuthCommunityBean authBean) {
		authBean.setDomainID(null);

		logger.info("开始...修改Telnet/SSH ");
		try {
			sysAuthCommunityService.updateSysAuthCommunity(authBean);
		} catch (Exception e) {
			logger.error("修改异常：" + e.getMessage(), authBean);
			return false;
		}
		logger.info("结束...修改Telnet/SSH ");
		return true;
	}

	/**
	 * 删除Telnet/SSH
	 * 
	 * @param authBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delAuthCommunity")
	@ResponseBody
	public boolean delAuthCommunity(SysAuthCommunityBean authBean) {
		logger.info("开始...删除Telnet/SSH ");
		try {
			sysAuthCommunityService.delSysAuthCommunityById(authBean);
		} catch (Exception e) {
			logger.error("删除异常：" + e.getMessage(), authBean);
			return false;
		}
		logger.info("结束...删除Telnet/SSH ");
		return true;
	}

	/**
	 * 批量删除Telnet/SSH
	 * 
	 * @param authBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/batchdelAuthCommunity")
	@ResponseBody
	public boolean batchdelAuthCommunity(SysAuthCommunityBean authBean) {
		logger.info("开始...批量删除Telnet/SSH");
		boolean flag = false;
		String ids = request.getParameter("ids");
		String[] splitIds = ids.split(",");
		try {
			for (String strId : splitIds) {
				Integer id = Integer.parseInt(strId);
				authBean.setId(id);
				flag = sysAuthCommunityService
						.delSysAuthCommunityById(authBean);
			}
		} catch (Exception e) {
			logger.error("批量删除异常：" + e.getMessage(), authBean);
			return false;
		}

		logger.info("结束...批量删除Telnet/SSH");
		return flag;
	}
}
