package com.fable.insightview.platform.importdata.resolver;

import com.fable.insightview.platform.importdata.DataType;

public class DataResolverFactory<T> {

	private static DataResolverFactory factory;
	
	public  Class<T> pubMappingClass = null;
	
	private ThreadLocal<DataResolver> threadLocal = new ThreadLocal<DataResolver>();

	private DataResolverFactory() {
	}

	public static DataResolverFactory getInstance() {
		if (factory == null) {
			factory = new DataResolverFactory();
		}
		return factory;
	}
	
	public DataResolver<T> getDataResolver(DataType dataType, Class<T> mappingClass) {
		if(pubMappingClass == null){
			threadLocal.set(null);
			pubMappingClass = mappingClass;
		}else{
			if(pubMappingClass != mappingClass){
				threadLocal.set(null);
				pubMappingClass = mappingClass;
			}
		}
		
		if(threadLocal.get() == null){
			DataResolver dataResolver = getDataResolver(dataType);
			dataResolver.setMappingClass(mappingClass);
			threadLocal.set(dataResolver);
		}
		return threadLocal.get();
	}

	private DataResolver<T> getDataResolver(DataType dataType) {
		switch (dataType) {
		case EXCEL:
			return new ExcelResolver<T>();
		case JSON:
			break;
		case TEXT:
			return new TextResolver<T>();
		case XML:
			break;
		}
		return null;
	}
}
