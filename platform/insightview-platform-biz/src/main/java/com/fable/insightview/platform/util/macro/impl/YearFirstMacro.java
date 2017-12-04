/**
 * 
 */
package com.fable.insightview.platform.util.macro.impl;

import org.springframework.stereotype.Component;

import com.fable.insightview.platform.common.util.DateTimeUtil;
import com.fable.insightview.platform.common.util.Tool;
import com.fable.insightview.platform.util.macro.Macro;

/**
 * yearfirst 宏变量
 * @author zhouwei
 *
 */
@Component
public class YearFirstMacro implements Macro {

	/* (non-Javadoc)
	 * @see com.fable.common.macro.Macro#getKey()
	 */
	@Override
	public String getKey() {
		return "yearfirst";
	}

	/* (non-Javadoc)
	 * @see com.fable.common.macro.Macro#getValue()
	 */
	@Override
	public String getValue() {
		return DateTimeUtil.dateToString(Tool.Now(), "yyyy") + "-01-01";
	}

}
