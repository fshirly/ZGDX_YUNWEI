/**
 * 
 */
package com.fable.insightview.platform.util.macro.impl;

import org.springframework.stereotype.Component;

import com.fable.insightview.platform.util.UserSession;
import com.fable.insightview.platform.util.macro.Macro;

/**
 * sysId 宏变量
 * @author zhouwei
 *
 */
@Component
public class SysIdMacro implements Macro {

	/* (non-Javadoc)
	 * @see com.fable.common.macro.Macro#getKey()
	 */
	@Override
	public String getKey() {
		return "sysId";
	}

	/* (non-Javadoc)
	 * @see com.fable.common.macro.Macro#getValue()
	 */
	@Override
	public String getValue() {
		return UserSession.getSysId();
	}

}
