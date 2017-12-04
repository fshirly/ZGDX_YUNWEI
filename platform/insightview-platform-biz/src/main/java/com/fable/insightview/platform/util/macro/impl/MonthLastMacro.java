/**
 * 
 */
package com.fable.insightview.platform.util.macro.impl;

import java.util.Calendar;

import org.springframework.stereotype.Component;

import com.fable.insightview.platform.common.util.Cast;
import com.fable.insightview.platform.common.util.DateTimeUtil;
import com.fable.insightview.platform.common.util.Tool;
import com.fable.insightview.platform.util.macro.Macro;

/**
 * monthlast 宏变量
 * 
 * @author zhouwei
 * 
 */
@Component
public class MonthLastMacro implements Macro {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fable.common.macro.Macro#getKey()
	 */
	@Override
	public String getKey() {
		return "monthlast";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fable.common.macro.Macro#getValue()
	 */
	@Override
	public String getValue() {
		return DateTimeUtil.dateToString(
				Tool.Now(),
				"yyyy-MM-"
						+ Cast.toString(Calendar.getInstance()
								.getActualMaximum(Calendar.DAY_OF_MONTH)));
	}

}
