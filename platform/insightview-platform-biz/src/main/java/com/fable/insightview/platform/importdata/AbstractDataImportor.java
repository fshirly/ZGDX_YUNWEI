package com.fable.insightview.platform.importdata;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.beanutils.PropertyUtils;

import com.fable.insightview.platform.importdata.resolver.DataResolver;
import com.fable.insightview.platform.importdata.resolver.ProcessorImpl;
import com.fable.insightview.platform.importdata.resolver.metadata.ClassMetaData;
import com.fable.insightview.platform.importdata.resolver.metadata.FieldMetaData;
import com.fable.insightview.platform.importdata.resolver.metadata.MetaDataFactory;

public abstract class AbstractDataImportor<T> implements DataImportor<T>{
	
	private ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory(); 
	
	private Validator validator = validatorFactory.getValidator();
	
	private List<T> data;
	
	private DataResolver<T> dataResolver;
	
	public ImportResult<T> importHandle(){
		
		ImportResult<T> result = new ImportResult<T>();
		
		// 解析出对象
		data =  getResolver().resolve();
		if(data != null && data.size() > 0){
			
			//导入总数
			result.setTotalNum(data.size());
			
			// 数据校验
			List<ValidResult> vaildResultList = this.validateData();
			result.setVaildResultList(vaildResultList);
			if(vaildResultList.size() > 0){
				if(!validFaileHandle(vaildResultList)){
					result.setFailureNum(data.size());
					return result;
				}
			}
			
			// 处理器
			process(data);
			
			// 子类逻辑处理
			boolean isSuccess = logicHandle(data, result);
			
			result.setSuccess(isSuccess);
		}
		return result;
	}
	
	/**
	 * 处理器处理
	 * 
	 * @param results
	 * @return
	 */
	private List<T> process(List<T> results){
		for (T element : results) {
			ClassMetaData classMetaData = MetaDataFactory.getInstance().getClassMetaDataByClazz(getResolver().getMappingClass());
			innerProcess(element, classMetaData);
		}
		return results;
	}
	
	private void innerProcess(Object element, ClassMetaData classMetaData){
		if(classMetaData.getProcessor() != null){
			new ProcessorImpl(null, classMetaData.getProcessor(), element).invoke();
		}
		
		for (FieldMetaData fieldMetaData : classMetaData.getFieldMetaDataList()) {
			if(fieldMetaData.getProcessor() != null){
				new ProcessorImpl(fieldMetaData.getFieldName(), fieldMetaData.getProcessor(), element).invoke();
			}
		}
		
		Iterator<String> iterator = classMetaData.getChildClassMetaDataMap().keySet().iterator();
		while(iterator.hasNext()){
			String fieldName = iterator.next();
			ClassMetaData childClassMetaData = classMetaData.getChildClassMetaDataMap().get(fieldName);
			Object o;
			try {
				o = PropertyUtils.getProperty(element, fieldName);
				innerProcess(o, childClassMetaData);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 校验失败处理
	 * 
	 * @param result
	 * @return 是否继续做逻辑处理
	 */
	protected boolean validFaileHandle(List<ValidResult> vaildResultList) {
		return false;
	}
 
	private List<ValidResult> validateData(){
		 
		 List<ValidResult> vaildResultList = new ArrayList<ValidResult>();
		 if(getResolver().isReloadable()){
			 validatorFactory = Validation.buildDefaultValidatorFactory(); 
			 validator = validatorFactory.getValidator();
		 }
		 
		 Map<Integer, Set<ConstraintViolation<T>>> map = new TreeMap<Integer, Set<ConstraintViolation<T>>>();
		 if(data != null){
			 int i = 1;
			 for(T target : data){
				 Set<ConstraintViolation<T>> constraintSet = validator.validate(target);
				 if(constraintSet.size()>0){
					 ValidResult validResult = new ValidResult();
					 validResult.setLineNum(i);
					 validResult.setConstraintSet(constraintSet);
					 validResult.setTarget(target);
					 vaildResultList.add(validResult);
				 }
				 i++;
			 }
		 }
		 return vaildResultList;
	}
	
	protected abstract boolean logicHandle(List<T> data, ImportResult<T> result);
}
