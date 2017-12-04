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
import com.fable.insightview.platform.snmpcommunity.entity.SNMPCommunityBean;
import com.fable.insightview.platform.snmpcommunity.service.ISnmpCommunityService;

/**
 * 凭证管理
 * 
 * @author
 * 
 */
@Controller
@RequestMapping("/platform/snmpCommunity")
public class SnmpCommunityController {
	@Autowired
	ISnmpCommunityService snmpCommunityService;
	@SuppressWarnings("unused")
	@Autowired
	private HttpServletRequest request;
	private final Logger logger = LoggerFactory
			.getLogger(SnmpCommunityController.class);

	@RequestMapping("/toSnmpCommunity")
	public ModelAndView toSnmpCommunity(String navigationBar) {
		logger.info("开始...加载SNMP列表页面");
		ModelAndView mv =new ModelAndView("platform/snmpcommunity/snmpcommunity_list");
		mv.addObject("navigationBar", navigationBar);
		return mv;
	}

	/**
	 * 查询列表
	 * 
	 * @param snmpcommunityBean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/listSnmpCommunity")
	@ResponseBody
	public Map<String, Object> listSnmpCommunity(
			SNMPCommunityBean snmpcommunityBean) throws Exception {
		logger.info("初始化SNMP列表数据...");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		List<SNMPCommunityBean> s = snmpCommunityService
				.getSnmpCommunityByConditions(snmpcommunityBean,
						flexiGridPageInfo);

		// 获取总记录数
		int total = snmpCommunityService.getTotalCount(snmpcommunityBean);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", s);
		logger.info("数据获取结束...");
		return result;
	}

	/**
	 * 根据设备名称获取相关ID 以供模糊查询
	 * 
	 * @param snmpcommunityBean
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping("/getMoIdBymoName")
//	@ResponseBody
//	public SNMPCommunityBean getMoIdBymoName() throws Exception {
//		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
//				.getRequestAttributes()).getRequest();
//		logger.info("根据设备名称获取设备ID");
//		String moName = request.getParameter("moName");
//		logger.debug("param 设备名称[moName]={} ", moName);
//		SNMPCommunityBean s = snmpCommunityService.getMoIDBymoName(moName);
//
//		return s;
//	}

	/**
	 * 根据条件查询SNMPCommunity
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author 王淑平
	 */
	@RequestMapping("/findSnmpCommunityByID")
	@ResponseBody
	public SNMPCommunityBean findSnmpCommunityByID(int id) throws Exception {
		logger.info("获取更新数据");
		logger.info("被更新数据Id = " + id);
		List<SNMPCommunityBean> snmpLst = snmpCommunityService
				.findSnmpCommunityByID(id);
		if (!snmpLst.isEmpty() && snmpLst.size() > 0) {
			return snmpLst.get(0);
		}
		return null;

	}

	/**
	 * 修改时验证设备IP是否存在
	 * 
	 * @param snmpcommunityBean
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping("/checkSnmpCommunity")
//	@ResponseBody
//	public boolean checkSnmpCommunity(SNMPCommunityBean snmpcommunityBean)
//			throws Exception {
//		logger.info("验证设备IP是否存在");
//		logger.debug("param 设备OID[deviceIP]={} ", snmpcommunityBean
//				.getDeviceIP());
//		return snmpCommunityService
//				.getSnmpCommunityByConditions(snmpcommunityBean);
//
//	}

	/**
	 * 添加时验证设备IP是否存在
	 * 
	 * @param snmpcommunityBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/checkAddSnmp")
	@ResponseBody
	public boolean checkAddSnmp(SNMPCommunityBean snmpcommunityBean)
			throws Exception {
		logger.info("验证凭证是否存在");
		return snmpCommunityService	.getSnmpCommunityByConditions(snmpcommunityBean);
	}

	/**
	 * 新增SnmpCommunity
	 * 
	 * @param snmpcommunityBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addSnmpCommunity")
	@ResponseBody
	public boolean addSnmpCommunity(SNMPCommunityBean snmpcommunityBean) {
		logger.info("开始...新增SnmpCommunity");
		try {
			snmpCommunityService.addSnmpCommunity(snmpcommunityBean);
		} catch (Exception e) {
			logger.error("插入异常：" + e.getMessage(), snmpcommunityBean);
			return false;
		}
		logger.info("结束...新增SnmpCommunity");
		return true;
	}

	/**
	 * 修改SnmpCommunity
	 * 
	 * @param snmpcommunityBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateSnmpCommunity")
	@ResponseBody
	public boolean updateSnmpCommunity(SNMPCommunityBean snmpcommunityBean) {
		snmpcommunityBean.setDomainID(null);
		logger.info("开始...修改SnmpCommunity");
		try {
			snmpCommunityService.updateSnmpCommunity(snmpcommunityBean);
		} catch (Exception e) {
			logger.error("更新异常：" + e.getMessage(), snmpcommunityBean);
			return false;
		}
		logger.info("结束...修改SnmpCommunity");
		return true;
	}

	/**
	 * 删除SnmpCommunity
	 * 
	 * @param snmpcommunityBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delSnmpCommunity")
	@ResponseBody
	public boolean delSnmpCommunity(SNMPCommunityBean snmpcommunityBean) {
		snmpcommunityBean.setDomainID(null);
		logger.info("开始...删除SnmpCommunity");
		logger.debug("deleteBath by param {} is value "
				+ snmpcommunityBean.getId(), "id");
		try {
			snmpCommunityService.delSnmpCommunityById(snmpcommunityBean);
		} catch (Exception e) {
			logger.error("删除异常：" + e.getMessage(), snmpcommunityBean);
			return false;
		}
		logger.info("结束...删除SnmpCommunity");
		return true;
	}

	/**
	 * 批量删除SnmpCommunity
	 * 
	 * @param snmpcommunityBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/batchdelSnmpCommunity")
	@ResponseBody
	public boolean batchdelSnmpCommunity(SNMPCommunityBean snmpcommunityBean) {
		logger.info("开始...批量删除SnmpCommunity");
		boolean flag = false;
		String ids = request.getParameter("ids");
		logger.debug("deleteBath by param {} ids value " + ids, "ids");
		String[] splitIds = ids.split(",");
		try {
			for (String strId : splitIds) {
				Integer id = Integer.parseInt(strId);
				snmpcommunityBean.setId(id);
				flag = snmpCommunityService
						.delSnmpCommunityById(snmpcommunityBean);

			}
		} catch (Exception e) {
			logger.error("批量删除异常：" + e.getMessage(), snmpcommunityBean);
			return false;
		}

		logger.info("结束...批量删除SnmpCommunity");
		return flag;
	}
}
