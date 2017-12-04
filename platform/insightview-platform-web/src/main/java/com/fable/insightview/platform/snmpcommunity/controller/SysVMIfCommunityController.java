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
import com.fable.insightview.platform.snmpcommunity.entity.SysVMIfCommunityBean;
import com.fable.insightview.platform.snmpcommunity.service.ISysVMIfCommunityService;

/**
 * VMWare管理
 * 
 * @author
 * 
 */
@Controller
@RequestMapping("/platform/sysVMIfCommunity")
public class SysVMIfCommunityController {
	@Autowired
	ISysVMIfCommunityService sysVMIfCommunityService;
	@SuppressWarnings("unused")
	@Autowired
	private HttpServletRequest request;
	private final Logger logger = LoggerFactory
			.getLogger(SysVMIfCommunityController.class);

	@RequestMapping("/toSysVmware")
	public ModelAndView toSysVmware(String navigationBar) {
		logger.info("开始加载VMWare管理页面");
		ModelAndView mv =new ModelAndView("platform/snmpcommunity/sysvmware_list");
		mv.addObject("navigationBar", navigationBar);
		return mv;
	}
	/**
	 * 管理域信息：菜单页面跳转
	 * @return
	 */
	@RequestMapping("/toSelectDomainList")
	public ModelAndView toSelectMfList() {
		return new ModelAndView("platform/snmpcommunity/selectDomainList");
	}

	/**
	 * 查询列表
	 * 
	 * @param authBean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/listSysVMIfCommunity")
	@ResponseBody
	public Map<String, Object> listSysAuthCommunity(
			SysVMIfCommunityBean authBean) {
		logger.info("初始化VMWare列表数据...");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();

		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		String type = request.getParameter("type");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<SysVMIfCommunityBean> s = sysVMIfCommunityService
					.getSysAuthCommunityByConditions(authBean,
							flexiGridPageInfo, type);
			// 获取总记录数
			int total = sysVMIfCommunityService.getTotalCount(authBean, type);

			result.put("total", total);
			result.put("rows", s);
		} catch (Exception e) {
			logger.error("查询异常：" + e.getMessage(), authBean);
		}
		logger.info("数据获取结束...");
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
		List<MODeviceBean> authLst = sysVMIfCommunityService.getDeviceById(bean
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
	public SysVMIfCommunityBean getMoIdBymoName() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		String moName = request.getParameter("moName");
		SysVMIfCommunityBean s = sysVMIfCommunityService
				.getMoIDBymoName(moName);
		return s;
	}

	/**
	 * 根据条件查询VMWare
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author 王淑平
	 */
	@RequestMapping("/findSysAuthCommunityByID")
	@ResponseBody
	public SysVMIfCommunityBean findSysAuthCommunityByID(int id)
			throws Exception {
		logger.info("获取更新数据");
		logger.info("被更新数据Id = " + id);
		List<SysVMIfCommunityBean> snmpLst = sysVMIfCommunityService
				.getSysAuthCommunityById(id);
		if (!snmpLst.isEmpty() && snmpLst.size() > 0) {
			return snmpLst.get(0);
		}
		logger.info("结束...获取更新数据");
		return null;
	}

	@RequestMapping("/checkDeviceIP")
	@ResponseBody
	public boolean checkDeviceIP(SysVMIfCommunityBean authBean)
			throws Exception {
		logger.info("验证设备IP是否存在");
		return sysVMIfCommunityService.checkDeviceIP(authBean);
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
	public boolean checkAddSyaAuthCommunity(SysVMIfCommunityBean authBean)
			throws Exception {
		List<SysVMIfCommunityBean> snmpLst = sysVMIfCommunityService
				.getSysAuthCommunityByConditions(authBean);
		if (null == snmpLst || snmpLst.size() <= 0) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * 新增 VMware
	 * 
	 * @param authBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addAuthCommunity")
	@ResponseBody
	public boolean addAuthCommunity(SysVMIfCommunityBean authBean) {
		logger.info("开始...新增 VMware");
		try {
			sysVMIfCommunityService.addSysAuthCommunity(authBean);
		} catch (Exception e) {
			logger.error("插入异常：" + e.getMessage(), authBean);
			return false;
		}
		logger.info("结束...新增 VMware");
		return true;
	}

	/**
	 * 修改 VMware
	 * 
	 * @param authBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAuthCommunity")
	@ResponseBody
	public boolean updateAuthCommunity(SysVMIfCommunityBean authBean) {
		authBean.setDomainID(null);
		logger.info("开始...修改 VMware");
		try {
			sysVMIfCommunityService.updateSysAuthCommunity(authBean);
		} catch (Exception e) {
			logger.error("修改异常：" + e.getMessage(), authBean);
			return false;
		}
		logger.info("结束...修改 VMware");
		return true;
	}

	/**
	 * 删除 VMware
	 * 
	 * @param authBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delAuthCommunity")
	@ResponseBody
	public boolean delAuthCommunity(SysVMIfCommunityBean authBean) {
		logger.info("开始...删除 VMware");
		try {
			sysVMIfCommunityService.delSysAuthCommunityById(authBean);
		} catch (Exception e) {
			logger.error("删除异常：" + e.getMessage(), authBean);
			return false;
		}
		logger.info("结束...删除 VMware");
		return true;
	}

	/**
	 * 批量删除VMware
	 * 
	 * @param authBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/batchdelAuthCommunity")
	@ResponseBody
	public boolean batchdelAuthCommunity(SysVMIfCommunityBean authBean) {
		logger.info("开始...批量删除VMware");
		boolean flag = false;
		String ids = request.getParameter("ids");
		String[] splitIds = ids.split(",");
		try {
			for (String strId : splitIds) {
				Integer id = Integer.parseInt(strId);
				authBean.setId(id);
				flag = sysVMIfCommunityService
						.delSysAuthCommunityById(authBean);
			}
		} catch (Exception e) {
			logger.error("批量删除异常：" + e.getMessage(), authBean);
			return false;
		}
		logger.info("结束...批量删除VMware");
		return flag;
	}

}
