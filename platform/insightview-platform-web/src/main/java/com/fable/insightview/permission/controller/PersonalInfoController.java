package com.fable.insightview.permission.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.common.util.CryptoUtil;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.mybatis.entity.PersonalInfo;
import com.fable.insightview.platform.mybatis.entity.STUserCertificate;
import com.fable.insightview.platform.service.ISysUserService;
import com.fable.insightview.platform.service.PersonalInfoService;

/**
 * @author Li jiuwei
 * @date   2015年4月8日 下午2:25:02
 */
@Controller
@RequestMapping("/personalInfo")
public class PersonalInfoController {
	
	@Autowired
	PersonalInfoService personalInfoService;
	
	@Autowired
	ISysUserService sysUserService;
	
	/**
	 * String转化为需要的Date形式
	 * 
	 * @param request
	 * @param binder
	 * @throws Exception
	 * @author sanyou
	 */
	@InitBinder
	protected void init(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
	
	/**
	 * 通过session获取用户信息
	 * 
	 * @author chengdawei
	 * @return
	 */
	@RequestMapping("/getCurrentUserInfo")
	@ResponseBody
	public SecurityUserInfoBean getUserIdBySession(HttpServletRequest request) {
		SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
				.getSession().getAttribute("sysUserInfoBeanOfSession");

		return sysUserInfoBeanTemp;
	}
	
	@RequestMapping("/toPersonalInfo")
	public ModelAndView toPersonalInfo(HttpServletRequest request) {
		request.setAttribute("userId", getUserIdBySession(request).getId());
		return new ModelAndView("permission/personal_info");
	}
	
	@RequestMapping("/savePersonalInfo")
	@ResponseBody
	public void savePersonalInfo(PersonalInfo personalInfo) {
		personalInfoService.updatePersonalInfo(personalInfo);
	}
	
	@RequestMapping("/toUserCertificateList")
	public ModelAndView toUserCertificateList(HttpServletRequest request) {
		request.setAttribute("userId", getUserIdBySession(request).getId());
		return new ModelAndView("permission/certificateList");
	}
	
	@RequestMapping("/toAddUserCertificate")
	public ModelAndView toAddUserCertificate() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("permission/certificate");
		return mv;
	}
	
	@RequestMapping("/toEditUserCertificate")
	public ModelAndView toEditUserCertificate(Integer certificateId) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("permission/certificate");
		
		if(certificateId != null) {
			STUserCertificate obj = personalInfoService.getUserCertificate(certificateId);
			mv.addObject("userCertificate", obj);
		}
		
		return mv;
	}
	
	@RequestMapping("/toViewUserCertificate")
	public ModelAndView toViewUserCertificate(Integer certificateId) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("permission/certificate_view");
		
		if(certificateId != null) {
			STUserCertificate obj = personalInfoService.getUserCertificate(certificateId);
			mv.addObject("userCertificate", obj);
		}
		
		return mv;
	}
	
	@RequestMapping("/queryUserCertificate")
	@ResponseBody
	public Map<String, Object> queryCertificate(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		int userId = getUserIdBySession(request).getId().intValue();
		List<STUserCertificate> list = personalInfoService.queryUserCertificate(userId);
		result.put("rows", list);
		result.put("total", list.size());
		
		return result;
	}
	
	@RequestMapping("/saveUserCertificate")
	@ResponseBody
	public void saveCertificate(HttpServletRequest request, STUserCertificate userCertificate) {
		userCertificate.setUserId(getUserIdBySession(request).getId().intValue());
		if(userCertificate.getCertificateId() == null) {
			personalInfoService.insertUserCertificate(userCertificate);
		} else {
			personalInfoService.updateUserCertificate(userCertificate);
		}
	}
	
	@RequestMapping("/deleteUserCertificate")
	@ResponseBody
	public void deleteCertificate(Integer certificateId) {
		personalInfoService.deleteUserCertificate(certificateId);
	}
	
	@RequestMapping("/toChangePwd")
	public ModelAndView toChangePwd(HttpServletRequest request) {
		request.setAttribute("userId", getUserIdBySession(request).getId());
		return new ModelAndView("permission/changepwd");
	}

	@RequestMapping("/changePwd")
	@ResponseBody
	public String changePwd(HttpServletRequest request,String oldPwd, String newPwd) {
		SysUserInfoBean bean = sysUserService.findSysUserById(getUserIdBySession(request).getId().intValue());
		String pwd = bean.getUserPassword();
		
		try {
			if(!CryptoUtil.Encrypt(oldPwd).equals(pwd)) {
				return "1";
			} else {
				bean.setUserPassword(newPwd);
				sysUserService.modifyPwd(bean);
				return "ok";
			}
		} catch(Exception e) {
			return "1";
		}
		
	}
}
