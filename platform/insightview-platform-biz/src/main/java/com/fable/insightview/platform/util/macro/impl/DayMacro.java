/**
 * 
 */
package com.fable.insightview.platform.util.macro.impl;

import java.util.Calendar;

import org.springframework.stereotype.Component;

import com.fable.insightview.platform.common.util.Cast;
import com.fable.insightview.platform.util.macro.Macro;

/**
 * day 宏变量
 * @author zhouwei
 *
 */
@Component
public class DayMacro implements Macro {

	/* (non-Javadoc)
	 * @see com.fable.common.macro.Macro#getKey()
	 */
	@Override
	public String getKey() {
		return "day";
	}

	/* (non-Javadoc)
	 * @see com.fable.common.macro.Macro#getValue()
	 */
	@Override
	public String getValue() {
		return Cast.toString(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
	}

}
