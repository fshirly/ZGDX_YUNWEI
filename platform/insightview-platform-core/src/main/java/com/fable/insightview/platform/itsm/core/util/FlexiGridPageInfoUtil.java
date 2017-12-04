package com.fable.insightview.platform.itsm.core.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @Title: FlexGridPageInfoUtil.java
 * @Description: 分页信息工具类
 * @author: 武林
 * @version: 1.0
 */
public class FlexiGridPageInfoUtil {

	/**
	 * 
	 * @Title: getFlexGridPageInfo
	 * @Description: 获取分页信息
	 * @param pageVar
	 *            当前页数变量名
	 * @param maxRowsVar
	 *            每页数据最大量变量名
	 * @param request
	 *            当前请求
	 * @return
	 * @throws:
	 * @author: 武林
	 */
	public static FlexiGridPageInfo getFlexiGridPageInfo(
			HttpServletRequest request) {
		FlexiGridPageInfo flexiGridPageInfo = new FlexiGridPageInfo();
		// 获得当前页数
		String pageIndex = request.getParameter("page");
		// 获得每页数据最大量
		String pageSize = request.getParameter("rows");
		flexiGridPageInfo.setPage(pageIndex == null ? 0 : Integer
				.parseInt(pageIndex));
		flexiGridPageInfo.setRp(pageSize == null ? 0 : Integer
				.parseInt(pageSize));

		return flexiGridPageInfo;
	}
}
