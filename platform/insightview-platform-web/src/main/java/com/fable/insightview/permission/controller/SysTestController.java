package com.fable.insightview.permission.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.platform.common.util.CryptoUtil;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.service.ISysUserService;

/**
 * 系统测试模块
 * 
 * @author 武林
 * 
 */
@Controller
@RequestMapping("/permissionSysTest")
public class SysTestController {

	@Autowired
	ISysUserService sysUserService;

	@RequestMapping("/toSysUserList")
	public ModelAndView toSysUserList() {
		return new ModelAndView("permission/test_list");
	}

	@RequestMapping("/findSysUser")
	@ResponseBody
	public SysUserInfoBean findSysUser(SysUserInfoBean sysUserBean)
			throws Exception {
		List<SysUserInfoBean> userLst = sysUserService.getSysUserByConditions(
				"userID", sysUserBean.getUserID() + "");
		SysUserInfoBean sysUserBeanTemp = userLst.get(0);
		sysUserBeanTemp.setUserPassword(CryptoUtil.Decrypt(sysUserBeanTemp
				.getUserPassword()));
		return sysUserBeanTemp;
	}

}
