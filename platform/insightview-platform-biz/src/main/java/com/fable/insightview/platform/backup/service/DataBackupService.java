package com.fable.insightview.platform.backup.service;

import java.util.Map;

import com.fable.insightview.platform.GuzhangrizManager.entity.GuzhangrizManager;

public interface DataBackupService {

	public Map<String,Object> restoreDate(GuzhangrizManager data);
}
