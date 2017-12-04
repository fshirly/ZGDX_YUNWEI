package com.fable.insightview.platform.mailserver.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.platform.entity.SysMailServerConfigBean;
import com.fable.insightview.platform.service.ISysMailServerService;

/**
 * 邮件
 * 
 * @author 
 * 
 */
@Controller
@RequestMapping("/platform/mailServer")
public class MailServerController {

	@Autowired
	ISysMailServerService sysMailServerService;
	@Autowired
	private HttpServletRequest request;
	private final Logger logger = LoggerFactory.getLogger(MailServerController.class);

	@RequestMapping("/toMailServerConfig")
	public ModelAndView toMailServerConfig(String navigationBar) {
		logger.info("start.../platform/mailServer/toMailServerConfig");
		ModelAndView mv = new ModelAndView("platform/mail/mailServer_config");
		mv.addObject("navigationBar", navigationBar);
		return mv ;
	}
	/**
	 * 服务器设置列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/listMailServerConfig")
	@ResponseBody
	public SysMailServerConfigBean listMailServerConfig(SysMailServerConfigBean mailBean) throws Exception{ 
		logger.info("start.../platform/mailServer/listMailServerConfig");
		List<SysMailServerConfigBean> mailServerLst=sysMailServerService.getMailServerConfigInfo();
		
		if(mailServerLst.size()!=0)
		{
			mailBean=mailServerLst.get(0);
		}else
		{
			mailBean=null;
		}
		logger.info("end.../platform/mailServer/listMailServerConfig");
		return mailBean;
	}
	/**
	 * 新增
	 * @param mailBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addMailServer")
	@ResponseBody
	public boolean addMailServer(SysMailServerConfigBean mailBean) throws Exception {
		logger.info("start.../platform/mailServer/addMailServer");
		String mailServer=request.getParameter("mailServer");
		Integer port=Integer.parseInt(request.getParameter("port"));
		int timeout=Integer.parseInt(request.getParameter("timeout"));
		String senderAccount=request.getParameter("senderAccount");
		double isAuth=Double.parseDouble((request.getParameter("isAuth")));
		String userName=request.getParameter("userName");
		String password=request.getParameter("password");
		mailBean.setMailServer(mailServer);
		mailBean.setPort(port);
		mailBean.setTimeout(timeout);
		mailBean.setSenderAccount(senderAccount);
		mailBean.setIsAuth(isAuth);
		mailBean.setUserName(userName);
		mailBean.setPassword(password);
		logger.info("end.../platform/mailServer/addMailServer");
		return sysMailServerService.addSysMailConfig(mailBean);
	}
	/**
	 * 修改邮件配置信息
	 * @param mailBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateMailServer")
	@ResponseBody
	public boolean updateMailServer(SysMailServerConfigBean mailBean) throws Exception {
		logger.info("start.../platform/mailServer/updateMailServer");
		int Id=Integer.parseInt(request.getParameter("id"));
		String mailServer=request.getParameter("mailServer");
		Integer port=Integer.parseInt(request.getParameter("port"));
		int timeout=Integer.parseInt(request.getParameter("timeout"));
		String senderAccount=request.getParameter("senderAccount");
		double isAuth=Double.parseDouble((request.getParameter("isAuth")));
		String userName=request.getParameter("userName");
		String password=request.getParameter("password");
		//System.out.println("服务器:"+mailServer+"端口:"+port+"超时:"+timeout+"邮箱:"+senderAccount+"是否认证:"+isAuth+"用户名:"+userName+"密码:"+password+"用户ID:"+Id);
		mailBean.setMailServer(mailServer);
		mailBean.setPort(port);
		mailBean.setTimeout(timeout);
		mailBean.setSenderAccount(senderAccount);
		mailBean.setIsAuth(isAuth);
		mailBean.setUserName(userName);
		mailBean.setPassword(password);
		mailBean.setId(Id);
		logger.info("end.../platform/mailServer/updateMailServer");
		return sysMailServerService.updateSysMailConfig(mailBean);
	}

}
