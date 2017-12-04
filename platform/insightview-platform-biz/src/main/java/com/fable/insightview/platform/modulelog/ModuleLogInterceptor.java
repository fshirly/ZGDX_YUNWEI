package com.fable.insightview.platform.modulelog;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.common.util.StringUtil;
import com.fable.insightview.platform.modulelog.entity.FbsSessionLogBean;
import com.fable.insightview.platform.modulelog.entity.SystemLogBean;
import com.fable.insightview.platform.modulelog.service.SessionLogService;
import com.fable.insightview.platform.modulelog.service.SystemLogService;

/**
 * @author fangang
 * 系统拦截器
 */
public class ModuleLogInterceptor implements HandlerInterceptor {
	
	private boolean isLog = true;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		boolean result = true;
		try {
			if(!isLog){
				return true;
			}
			result = preHandleProxyMethod(request, response, handler);
		}catch (BadSqlGrammarException bade){
			bade.printStackTrace();
			isLog = false;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 */
	private boolean preHandleProxyMethod(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String referer = request.getHeader("Referer");
		String path = request.getServletPath();
		System.out.println("URL[Interceptor]：" + path + "(" + referer + ")");
		
		//过滤资源文件
		if(path.startsWith("/js/") || path.endsWith(".js") || path.endsWith(".css") || 
				path.endsWith(".jpg") || path.endsWith(".gif") || path.endsWith(".png") || path.endsWith("/noright.html")) {
			return true;
		}
		
		//不使用缓存
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		
		//获取URL、方法名
		String[] ss = null;
		if(handler instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod)handler;
			ss = getReflectInfo(hm);
		}
		
		//用户已经登录
		if(request.getSession().getAttribute("sysUserInfoBeanOfSession") != null) {
			
			SecurityUserInfoBean sysUser = (SecurityUserInfoBean) request.getSession().getAttribute("sysUserInfoBeanOfSession");/* 当前登陆用户 */
			
			//记录系统URL日志
			if(systemLogService.isLoggerEnable("1")) {
				//记录登录日志
				if(referer != null && referer.endsWith("/commonLogin/toMain")){
					System.out.println(request.getSession().getId());
					if(sessionLogService.selectBySessionId(request.getSession().getId()) <= 0){
						FbsSessionLogBean bean = new FbsSessionLogBean();
						//String userId = requestUtil.getSession().getAttribute("USERID").toString();
						//String username = requestUtil.getSession().getAttribute("USERNAME").toString();
						bean.setUserId(sysUser.getId().toString());
						bean.setUsername(sysUser.getUserName());
						bean.setIp(getClientAddr(request));
						bean.setClientName(request.getRemoteHost());
						bean.setClientAgent(request.getHeader("User-Agent"));
						bean.setNote("");
						bean.setSessionId(request.getSession().getId());
						sessionLogService.insert(bean);
					}
				}
				
				if(ss != null) {
					String url = ss[0];
					String methodName = ss[1];
					String moduleName = systemLogService.getModuleIdByMethod(url, methodName);
					if(StringUtil.isNotEmpty(moduleName)){
						SystemLogBean bean = new SystemLogBean();
						//String userId = requestUtil.getSession().getAttribute("USERID").toString();
						//String username = requestUtil.getSession().getAttribute("USERNAME").toString();
						bean.setUserId(sysUser.getId().toString());
						bean.setUsername(sysUser.getUserName());
						bean.setIp(getClientAddr(request));
						bean.setClientName(request.getRemoteHost());
						bean.setClientAgent(request.getHeader("User-Agent"));
						bean.setPageName(moduleName);
						if(ss != null) {
							bean.setUrl(ss[0]);
							bean.setMethodName(ss[1]);
						}
						bean.setNote("");
						systemLogService.insertAccessLogService(bean);
					}
				}
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//
	}
	
	/**
	 * 获取客户端IP地址
	 * @param request
	 * @return
	 */
	private String getClientAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * 根据HandlerMethod对象获取反射信息
	 * @param handle HandlerMethod对象
	 * @return URL、完整的方法名
	 */
	private String[] getReflectInfo(HandlerMethod handle) {
		String[] ss = new String[2];
		
		try {
			String url = "";
			String urlCls = ""; //类地址
			String urlMtd = ""; //方法地址
			
			final Class<?> clazz = handle.getBeanType();
			Annotation cc = clazz.getAnnotation(Controller.class);
			if(cc != null) {
				//获取类的@RequestMapping
				Annotation rm = clazz.getAnnotation(RequestMapping.class);
				if(rm != null) {
					urlCls = ((RequestMapping)rm).value()[0];
				}
			}
			
			Method method = handle.getMethod();
			//获取方法的@RequestMapping注解
			RequestMapping rm11 = method.getAnnotation(RequestMapping.class);
			if(rm11 != null) {
				urlMtd = rm11.value()[0];
			}
			
			if(!urlCls.endsWith("/") && !urlMtd.startsWith("/")) {
				url = urlCls + "/" + urlMtd;
			} else {
				url = urlCls + urlMtd;
			}
			
			ss[0] = url;
			ss[1] = clazz.getName() + "." + method.getName();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return ss;
	}

	@Autowired
    private SystemLogService systemLogService;
	
	@Autowired
    private SessionLogService sessionLogService;
	
//	@Autowired
//	private RestSecurityService restSecurityService;
	
}
