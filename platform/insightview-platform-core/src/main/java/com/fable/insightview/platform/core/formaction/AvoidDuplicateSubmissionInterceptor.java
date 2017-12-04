package com.fable.insightview.platform.core.formaction;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 防止表单重复提交
 * @author xue.antai
 *
 */
public class AvoidDuplicateSubmissionInterceptor implements HandlerInterceptor {
	public static String FORM_LOAD_TOKEN = "fable_form_token";
	public static String MUTIPART_FORM_LOAD_TOKEN = "fable_mutipart_form_load_token";

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp,
			Object handler) throws Exception {
		if(handler instanceof org.springframework.web.method.HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod)handler;
			Method method = handlerMethod.getMethod();
			AvoidDuplicateSubmission ads = handlerMethod.getMethodAnnotation(AvoidDuplicateSubmission.class);
			if(null != ads) {
				boolean formLoadToken = ads.formLoadToken();
				if(formLoadToken) {
					// 产生表单加载后唯一令牌
					req.getSession().setAttribute(FORM_LOAD_TOKEN, TokenProcessor.newInstance().generateToken());
				}
				boolean formSubmitToken = ads.formSubmitCompleteToken();
				if(formSubmitToken) {
//					resp.setContentType("application/json;charset=UTF-8");
					// 处理成功后移除过期的令牌
					if(assertDuplicateSubmit(req)) {
						System.out.println("通不过验证" + req.getSession(false).getAttribute(FORM_LOAD_TOKEN));
						return false;
					}
					req.getSession(false).removeAttribute(FORM_LOAD_TOKEN);
				}
				
				boolean multipartFormLoadToken = ads.multipartFormLoadToken();
				if(multipartFormLoadToken) {
					// 默认产生三个令牌
					int multipartTokenCount = ads.multipartTokenCount();
					if(multipartTokenCount!=0) {
						for(int i1=1;i1 <= multipartTokenCount;i1++) {
							req.getSession(false).setAttribute(MUTIPART_FORM_LOAD_TOKEN+ i1, TokenProcessor.newInstance().generateToken());
							System.out.println(req.getSession(false).getAttribute(MUTIPART_FORM_LOAD_TOKEN + i1));
						}
					}
				}
				boolean multipartSubmitCompleteToken = ads.multipartSubmitCompleteToken();
				if(multipartSubmitCompleteToken) {
					// 指定令牌索引移除指定令牌，否则默认移除第一个令牌
					int multipartTokenIndex = ads.multipartTokenIndex();
					if(0!=multipartTokenIndex) {
						// 处理成功后移除过期的令牌
						if(assertDuplicateSubmit(req)) {
							System.out.println("通不过验证" + req.getSession(false).getAttribute(MUTIPART_FORM_LOAD_TOKEN + multipartTokenIndex));
							return false;
						}
						req.getSession(false).removeAttribute(MUTIPART_FORM_LOAD_TOKEN + multipartTokenIndex);
					}
					else {
						if(assertDuplicateSubmit(req)) {
							System.out.println("通不过验证" + req.getSession(false).getAttribute(MUTIPART_FORM_LOAD_TOKEN + 1));
							return false;
						}
						req.getSession(false).removeAttribute(MUTIPART_FORM_LOAD_TOKEN + 1);
					}
				}
			}
		}
		System.out.println("通过验证：" + req.getSession(false).getAttribute(FORM_LOAD_TOKEN));
		return true;
	}

	private boolean assertDuplicateSubmit(HttpServletRequest req) {
		String serverToken = (String) req.getSession(false).getAttribute(FORM_LOAD_TOKEN);
		System.out.println("serverToken:" + serverToken);
        if (null == serverToken) {
            return true;
        }
        String clientToken = req.getParameter(FORM_LOAD_TOKEN);
        System.out.println("clientToken:" + clientToken);
        if (clientToken == null) {
            return true;
        }
        if (!serverToken.equals(clientToken)) {
            return true;
        }
        return false;
	}

	@Override
	public void afterCompletion(HttpServletRequest req,
			HttpServletResponse resp, Object handler, Exception exp)
			throws Exception {
		
		
	}

	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse resp,
			Object handler, ModelAndView mv) throws Exception {
		
	}

}
