/**
 * 
 */
package com.fable.insightview.platform.util.macro.impl;

import java.util.Calendar;

import org.springframework.stereotype.Component;

import com.fable.insightview.platform.common.util.Cast;
import com.fable.insightview.platform.util.macro.Macro;

/**
 * month 宏变量
 * @author zhouwei
 *
 */
@Component
public class MonthMacro implements Macro {

	/* (non-Javadoc)
	 * @see com.fable.common.macro.Macro#getKey()
	 */
	@Override
	public String getKey() {
		return "month";
	}

	/* (non-Javadoc)
	 * @see com.fable.common.macro.Macro#getValue()
	 */
	@Override
	public String getValue() {
		return Cast.toString(Calendar.getInstance().get(Calendar.MONTH) + 1);
	}

}
