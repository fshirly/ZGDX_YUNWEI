package com.fable.insightview.platform.common.excel.config;

import com.fable.insightview.platform.common.excel.config.impl.ExcelConfigManagerImpl;

public class ExcelConfigFactory {
	private static ExcelConfigManager instance = new ExcelConfigManagerImpl();
	public static ExcelConfigManager createExcelConfigManger(){
		return instance;
	}
}
