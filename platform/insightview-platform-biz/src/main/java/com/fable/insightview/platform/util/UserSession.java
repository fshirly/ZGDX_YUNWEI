/**
 * 
 */
package com.fable.insightview.platform.util;

import org.springframework.stereotype.Component;

/**
 * @author zhouwei
 * 
 */
@Component
public class UserSession {

	//private static RequestUtil requestUtil;
	
//	@Autowired
//	public UserSession(RequestUtil requestUtil) {
//		UserSession.requestUtil = requestUtil;
//    }

	public static String getSysId() {
		//return requestUtil.getSysId();
		return "";
	}

	public static String getUserId() {
		//return requestUtil.getUserId();
		return "";
	}

	public static boolean isAdmin() {
//		UserBean user = (UserBean) requestUtil.getSession().getAttribute(
//				"CURRENT_USER");
//		if (user != null && "1".equals(user.getIsSuper())) {
//			return true;
//		}
		return true;
	}
	
	public static String getDefaultSysId() {
		return "";//Nothing.nullGuidStr;
	}

}
