package com.fable.insightview.platform.security;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.TextEscapeUtils;

import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.common.service.ISecurityUserService;
import com.fable.insightview.platform.common.util.CryptoUtil;
import com.fable.insightview.platform.common.util.SystemFinalValue;
import com.fable.insightview.platform.service.ISysUserService;
import com.fable.insightview.platform.sysinit.SystemParamInit;

/**
 * 处理用户登录时的认证
 * 
 * @author 汪朝 2013-9-30
 * 
 */
public class FableUsernamePasswordAuthenticationFilter extends
		UsernamePasswordAuthenticationFilter {

	private static final Logger logger = LogManager.getLogger();

	/** session中的最后的错误信息KEY */
	private static final String SPRING_SECURITY_LAST_EX_MSG_KEY = "SPRING_SECURITY_LAST_EX_MSG";

	/** session中的最后的用户名KEY */
	private static final String SPRING_SECURITY_LAST_USERNAME_KEY = "SPRING_SECURITY_LAST_USERNAME";
	/**
	 * 对应页面中的验证码
	 */
	public static final String VALIDATE_CODE = "validateCode";

	public static final int maxCount = 3;
	/**
	 * 对应页面中的用户名
	 */
	public static final String USERNAME = "username";
	/**
	 * 对应页面中的密码
	 */
	public static final String PASSWORD = "password";

	@Autowired
	private ISecurityUserService securityUserService;
	
	@Autowired
	private ISysUserService sysUserService;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		this.securityUserService.updateResourceMap();
		// 判断登录页面的提交方式是否为POST
 		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException(
					"Authentication method not supported: "
							+ request.getMethod());
		}
		// 根据后期看看页面中是否有验证码相关的验证,保留
		// checkValidateCode(request);
		SecurityUserInfoBean securityUserInfoBean = null;
		// 取得用户名和密码
		int msgInfoLocked = 0;
		String msg = null;
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		SecurityUserInfoBean sysUserBean = new SecurityUserInfoBean();
		sysUserBean.setUserAccount(username);
		sysUserBean.setUserPassword(password);
		// 对用户名和密码后台进行非空验证
		if (StringUtils.isNotEmpty(username)
				&& StringUtils.isNotEmpty(password)) {
			// 通过用户名查询用户信息
			List<SecurityUserInfoBean> list1 = this.securityUserService
					.chkUserInfo(sysUserBean);
			
			/****用户名区分大小写***/
			List<SecurityUserInfoBean> list = new ArrayList<SecurityUserInfoBean>();
			if ((list1 != null || list1.size() > 0)) {
				for (SecurityUserInfoBean userBean : list1) {
					if (username.equals(userBean.getUserAccount())) {
						list.add(userBean);
					}
				}
			}
			
			//admin管理员用户不作为锁定目标
			if ((list == null || list.size() <= 0)) {
				if(!"admin".equals(username)){
					// 记录用户输入错误次数
					if (null == SystemParamInit.mapCount.get(username)
							|| 0 == SystemParamInit.mapCount.get(username)) {
						SystemParamInit.mapCount.put(username, 1);
					} else {
						SystemParamInit.mapCount.put(username, (SystemParamInit.mapCount.get(username) + 1));
						if (SystemParamInit.mapCount.get(username.trim()) == maxCount) {
							//
							sysUserService.updateStuts(username.trim());
						}
					}
				}
				msgInfoLocked = maxCount- (SystemParamInit.mapCount.get(username.trim()) == null ? maxCount : SystemParamInit.mapCount.get(username.trim()));
				msg = msgInfoLocked <= 0?"用户名被锁定":"您还有" + (msgInfoLocked < 0?0 : msgInfoLocked) + "次机会";
				//如果是admin用户则不提示锁定信息
				msg = !"admin".equals(username)?msg:"";
				request.getSession().setAttribute(SPRING_SECURITY_LAST_EX_MSG_KEY, "用户名或密码不正确!"+msg);
				throw new AuthenticationServiceException("用户名或密码不正确！");
			} else {
				for(SecurityUserInfoBean su : list){
					if(su.getState() == 0){
						securityUserInfoBean = su;
					}
				}
				if (securityUserInfoBean == null) {
					request.getSession().setAttribute(
							SPRING_SECURITY_LAST_EX_MSG_KEY, "用户已删除不能登录!");
					throw new AuthenticationServiceException("用户已删除不能登录!");
				}
				if (securityUserInfoBean.getStatus() == 0) {
					request.getSession().setAttribute(
							SPRING_SECURITY_LAST_EX_MSG_KEY, "用户被锁定不能登录!");
					throw new AuthenticationServiceException("用户被锁定不能登录!");
				}
			}
			// 构造未认证的UsernamePasswordAuthenticationToken
			// UsernamePasswordAuthenticationToken实现 Authentication
			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
					username, password);

			// 设置USERNAME方便页面${sessionScope['SPRING_SECURITY_LAST_USERNAME']}
			HttpSession session = request.getSession(false);
			// 如果session不为空，添加username到session中
			if (session != null || getAllowSessionCreation()) {
				request.getSession().setAttribute(
						SPRING_SECURITY_LAST_USERNAME_KEY,
						TextEscapeUtils.escapeEntities(username));
				// 在SESSION中添加用户信息
				sysUserBean.setId(securityUserInfoBean.getId());
				sysUserBean.setUserName(securityUserInfoBean.getUserName());
				request.getSession().setAttribute(
						SystemFinalValue.SESSION_DATA, sysUserBean);
			}

			// Allow subclasses to set the "details" property 允许子类设置详细属性
			// 设置details，这里就是设置org.springframework.security.web.authentication.WebAuthenticationDetails实例到details中
			setDetails(request, authRequest);

			// 由认证管理器完成认证工作(通过AuthenticationManager:ProviderManager完成认证任务):运行UserDetailsService的loadUserByUsername
			// 再次封装Authentication
			return this.getAuthenticationManager().authenticate(authRequest);
		} else {
			request.getSession().setAttribute(SPRING_SECURITY_LAST_EX_MSG_KEY,
					"用户名或密码不能为空!");
			throw new AuthenticationServiceException("用户名或密码不能为空！");
		}

	}

	/**
	 * 验证码的校验
	 * 
	 * @param request
	 */
	protected void checkValidateCode(HttpServletRequest request) {
		HttpSession session = request.getSession();

		String sessionValidateCode = obtainSessionValidateCode(session);
		// 让上一次的验证码失效
		session.setAttribute(VALIDATE_CODE, null);
		String validateCodeParameter = obtainValidateCodeParameter(request);
		if (StringUtils.isEmpty(validateCodeParameter)
				|| !sessionValidateCode.equalsIgnoreCase(validateCodeParameter)) {
			throw new AuthenticationServiceException("validateCode.notEquals");
		}
	}

	/**
	 * 获取表单提交的验证码
	 * 
	 * @param request
	 * @return
	 */
	private String obtainValidateCodeParameter(HttpServletRequest request) {
		Object obj = request.getParameter(VALIDATE_CODE);
		return null == obj ? "" : obj.toString();
	}

	/**
	 * 获取Session中保存的验证码
	 * 
	 * @param session
	 * @return
	 */
	protected String obtainSessionValidateCode(HttpSession session) {
		Object obj = session.getAttribute(VALIDATE_CODE);
		return null == obj ? "" : obj.toString();
	}

	/**
	 * 获取表单提交的用户名
	 */
	@Override
	protected String obtainUsername(HttpServletRequest request) {
		Object obj = request.getParameter(USERNAME);
		if("用户名：".equals(obj)){
			obj = "";
		}
		return null == obj ? "" : StringUtils.trim(obj.toString());
	}

	/**
	 * 获取表单提交的密码
	 */
	@Override
	protected String obtainPassword(HttpServletRequest request) {
//		System.out.println("用户名："+request.getParameter(USERNAME));
//		System.out.println("密码："+request.getParameter(PASSWORD));
		Object obj = null;
		if(!"".equals(request.getParameter(PASSWORD))){
			try {
				obj = CryptoUtil.Encrypt(request.getParameter(PASSWORD));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("密码转换类型错误", request.getParameter(PASSWORD));
			}

		}
		return null == obj ? "" : obj.toString();
	}

}
