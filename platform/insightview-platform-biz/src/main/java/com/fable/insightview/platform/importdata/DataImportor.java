package com.fable.insightview.platform.importdata;

import com.fable.insightview.platform.importdata.resolver.DataResolver;

public interface DataImportor<T> {
	
	public DataResolver<T> getResolver();
	
	public ImportResult<T> importHandle();
	
}
