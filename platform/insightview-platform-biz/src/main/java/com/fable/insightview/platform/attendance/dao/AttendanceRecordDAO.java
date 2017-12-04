package com.fable.insightview.platform.attendance.dao;

import java.util.List;

import com.fable.insightview.platform.attendance.entity.AttRecInfoStatisVO;
import com.fable.insightview.platform.page.Page;

public interface AttendanceRecordDAO {
	

	/**
	 * 签到统计列表
	 * @param page
	 * @param isPage 是否分页
	 * @return
	 */
	public List<AttRecInfoStatisVO> qryAttRecInfoStatisVOs(
			Page<AttRecInfoStatisVO> page, Boolean isPage);
	

}
