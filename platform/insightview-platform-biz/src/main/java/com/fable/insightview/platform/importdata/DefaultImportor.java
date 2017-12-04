package com.fable.insightview.platform.importdata;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fable.insightview.platform.importdata.resolver.DataResolver;
import com.fable.insightview.platform.importdata.resolver.ExcelResolver;
import com.fable.insightview.platform.importdata.resolver.metadata.ExcelDataResolver;

public class DefaultImportor extends AbstractDataImportor{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Class mappingClass;

	@Override
	protected boolean logicHandle(List data, ImportResult result) {
		for(Object o : data){
			sessionFactory.getCurrentSession().save(o);
		}
		return true;
	}

	public DataResolver getResolver() {
		ExcelDataResolver dataResolverMetaData = (ExcelDataResolver) mappingClass.getAnnotation(ExcelDataResolver.class);
		if(dataResolverMetaData != null){
			 ExcelResolver excelResolver = new ExcelResolver();
			 excelResolver.setMappingClass(mappingClass);
		}
		return null;
	}

	public final Class getMappingClass() {
		return mappingClass;
	}

	public final void setMappingClass(Class mappingClass) {
		this.mappingClass = mappingClass;
	}
}
