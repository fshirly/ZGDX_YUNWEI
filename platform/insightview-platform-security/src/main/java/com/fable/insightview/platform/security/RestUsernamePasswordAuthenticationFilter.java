package com.fable.insightview.platform.security;

import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fable.insightview.platform.common.util.CryptoUtil;

public class RestUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private static final Logger logger = LogManager.getLogger();

	@Override
	protected boolean requiresAuthentication(HttpServletRequest request,
			HttpServletResponse response) {
		this.setContinueChainBeforeSuccessfulAuthentication(true);
		return true;
	}

	@Override
	protected String obtainPassword(HttpServletRequest request)
	  {
	    Object result = null;
	    try {
	      String userCredential = getCredential(request);
	      if (StringUtils.isEmpty(userCredential)) {
	    	  return null;
	      }
	      String password = userCredential.split(":")[1];
	      result = CryptoUtil.Encrypt(password);
	    } catch (Exception e) {
	      logger.error("密码转换类型错误");
	    }
	    return ((result == null) ? "" : result.toString());
	  }
	
	
	
	@Override
	protected String obtainUsername(HttpServletRequest request) {
		String userCredential = getCredential(request);
	      if (StringUtils.isEmpty(userCredential)) {
	    	  return null;
	      }
	      return userCredential.split(":")[0];
	}

	private String getCredential(HttpServletRequest request) {
		String auth = request.getHeader("Authorization");
		if (StringUtils.isEmpty(auth)) {
			return null;
		}
		String userCreds = auth.split(" ")[1];
		userCreds = new String(Base64.decodeBase64(userCreds.getBytes()),Charset.forName("UTF-8"));
		return userCreds;
	}
}
