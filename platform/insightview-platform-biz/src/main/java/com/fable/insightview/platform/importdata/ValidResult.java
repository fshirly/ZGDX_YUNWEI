package com.fable.insightview.platform.importdata;

import java.util.Set;

import javax.validation.ConstraintViolation;

public class ValidResult<T> {

	private int lineNum; // 校验失败的行号
	
	private Object target;//校验的对象
	
	private Set<ConstraintViolation<T>> constraintSet;// 校验异常

	public final Set<ConstraintViolation<T>> getConstraintSet() {
		return constraintSet;
	}

	public final void setConstraintSet(Set<ConstraintViolation<T>> constraintSet) {
		this.constraintSet = constraintSet;
	}

	public final int getLineNum() {
		return lineNum;
	}

	public final void setLineNum(int lineNum) {
		this.lineNum = lineNum;
	}

	public final Object getTarget() {
		return target;
	}

	public final void setTarget(Object target) {
		this.target = target;
	}
}
