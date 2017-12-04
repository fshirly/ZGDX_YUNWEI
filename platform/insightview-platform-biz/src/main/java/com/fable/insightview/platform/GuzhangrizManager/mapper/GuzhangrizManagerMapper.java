package com.fable.insightview.platform.GuzhangrizManager.mapper;

import java.util.List;

import com.fable.insightview.platform.GuzhangrizManager.entity.GuzhangrizManager;
import com.fable.insightview.platform.page.Page;

public interface GuzhangrizManagerMapper {
	
	public List<GuzhangrizManager> queryGuzhangrizManagerList(Page page);
	
	public List<GuzhangrizManager> queryBackupRecodes(GuzhangrizManager manager);
	
	public void updateBackupRecode(GuzhangrizManager manager);
}
