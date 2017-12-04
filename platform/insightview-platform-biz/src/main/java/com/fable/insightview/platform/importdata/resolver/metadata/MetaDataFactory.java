package com.fable.insightview.platform.importdata.resolver.metadata;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;

import com.fable.insightview.platform.importdata.resolver.Mapping;

public class MetaDataFactory {
	
	private static MetaDataFactory metaDataFactory;
	
	private Map<Class, ClassMetaData> classMetaDataMap = new HashMap<Class, ClassMetaData>();
	
	private MetaDataFactory(){}
	
	public static MetaDataFactory getInstance(){
		if(metaDataFactory == null){
			metaDataFactory = new MetaDataFactory();
		}
		return metaDataFactory;
	}
	
	public ClassMetaData getClassMetaDataByClazz(Class clazz){
		return classMetaDataMap.get(clazz);
	}
	
	public FieldMetaData getFieldMetaData(Class clazz, String fieldName){
		return getHierarchyFieldMetaDataByClazz(clazz).get(fieldName);
	}
	
	public Map<String, FieldMetaData> getHierarchyFieldMetaDataByClazz(Class clazz){
		ClassMetaData classMetaData = getClassMetaDataByClazz(clazz);
		return classMetaData.getHierarchyFieldMetaDataMap();
	}
	
	public Annotation getAnnotation(Class clazz, String fieldName, Class annotationClass){
		FieldMetaData fieldMetaData = getFieldMetaData(clazz, fieldName);
		try {
			Map map = BeanUtils.describe(fieldMetaData);
			for(Object o: map.values()){
				if(o.getClass() == annotationClass
						&& o instanceof Annotation){
					return (Annotation)o;
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void buildMetaData(Class mappingClass){
		ClassMetaData classMetaData = new ClassMetaData();
		buildClassMetaData(classMetaData, mappingClass);
		classMetaDataMap.put(mappingClass, classMetaData);
	}
	
	private void buildClassMetaData(final ClassMetaData classMetaData, Class mappingClass){
		
		classMetaData.setProcessor((Processor)mappingClass.getAnnotation(Processor.class));
		
		ReflectionUtils.doWithFields(mappingClass, new FieldCallback(){
			public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
				Mapping mapping = field.getAnnotation(Mapping.class);
				Processor processor = field.getAnnotation(Processor.class);
				if(mapping != null || processor != null){
					FieldMetaData fieldMetaData = new FieldMetaData();
					fieldMetaData.setFieldName(field.getName());
					fieldMetaData.setMapping(mapping);
					fieldMetaData.setProcessor(processor);
					classMetaData.getFieldMetaDataList().add(fieldMetaData);
				}else {
					Mapped mapped = field.getAnnotation(Mapped.class);
					if(mapped != null){
						ClassMetaData childClassMetaData = new ClassMetaData();
						classMetaData.getChildClassMetaDataMap().put(field.getName(), childClassMetaData);
						buildClassMetaData(childClassMetaData, field.getType());
					}
				}
			}});
	}
}
