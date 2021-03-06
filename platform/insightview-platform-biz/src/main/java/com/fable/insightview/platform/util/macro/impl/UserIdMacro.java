/**
 * 
 */
package com.fable.insightview.platform.util.macro.impl;

import org.springframework.stereotype.Component;

import com.fable.insightview.platform.util.UserSession;
import com.fable.insightview.platform.util.macro.Macro;

/**
 * userId 宏变量
 * @author zhouwei
 *
 */
@Component
public class UserIdMacro implements Macro {

	/* (non-Javadoc)
	 * @see com.fable.common.macro.Macro#getKey()
	 */
	@Override
	public String getKey() {
		return "userId";
	}

	/* (non-Javadoc)
	 * @see com.fable.common.macro.Macro#getValue()
	 */
	@Override
	public String getValue() {
		return UserSession.getUserId();
	}

}
