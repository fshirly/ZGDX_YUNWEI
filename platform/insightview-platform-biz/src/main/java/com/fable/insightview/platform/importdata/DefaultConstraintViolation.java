package com.fable.insightview.platform.importdata;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.metadata.ConstraintDescriptor;


public class DefaultConstraintViolation implements ConstraintViolation{

	private String message;//错误提示
	
	private Object entity;//对于的实体对象
	
	private Object invalidValue;//实体的哪个属性值导致的问题
	
	public DefaultConstraintViolation(Object entity, String message){
		this.entity = entity;
		this.message = message;
	}
	
	public DefaultConstraintViolation(Object entity, String message, Object invalidValue){
		this.entity = entity;
		this.message = message;
		this.invalidValue = invalidValue;
	}
	
	public ConstraintDescriptor getConstraintDescriptor() {
		return null;
	}

	public Object getInvalidValue() {
		return invalidValue;
	}

	public Object getLeafBean() {
		return entity;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageTemplate() {
		return null;
	}

	public Path getPropertyPath() {
		return null;
	}

	public Object getRootBean() {
		return entity;
	}

	public Class getRootBeanClass() {
		return entity.getClass();
	}

	public void setInvalidValue(Object invalidValue) {
		this.invalidValue = invalidValue;
	}
}
