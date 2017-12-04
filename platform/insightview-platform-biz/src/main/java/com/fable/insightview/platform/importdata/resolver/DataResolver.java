package com.fable.insightview.platform.importdata.resolver;

import java.util.List;

public interface DataResolver<T> {
	
	public Class<T> getMappingClass();
	
	public void setMappingClass(Class<T> mappingClass);
	
	public List<T> resolve();
	
	public boolean isReloadable();
	
}
