package com.fable.insightview.platform.GuzhangrizManager.service;

import java.util.List;

import com.fable.insightview.platform.GuzhangrizManager.entity.GuzhangFile;
import com.fable.insightview.platform.GuzhangrizManager.entity.GuzhangrizManager;
import com.fable.insightview.platform.page.Page;

public interface GuzhangrizManagerService {
	public List<GuzhangrizManager> queryguzhangrizManagerList(Page page);
	
	public List<GuzhangFile> getTomcatLogs_Info();
	
	/**
	 * 数据备份日志信息查询列表
	 * @param manager
	 * @return
	 */
	public List<GuzhangrizManager> queryBackupRecodes(GuzhangrizManager manager);
	/**
	 * id查询数据备份日志信
	 * @param id
	 * @return
	 */
	public GuzhangrizManager queryBackupRecodeById(int id);
	
	/**
	 * 数据备份日志更新
	 * @param id
	 * @return
	 */
	public void updateBackupRecode(GuzhangrizManager manager);
	
}
