package com.fable.insightview.platform.serviceSerialNum.entity;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * 业务编号中的流水号自增规则
 * 
 * @author maow
 */
@Alias("serviceSerialNumber")
public class ServiceSerialNumber {
	@NumberGenerator(name = "serviceSerialNumberPK")
	private Integer id;
	private String serialPattern; // 流水号自增规则，支持按天-day、按月-month、按年-year 流水号的自增方式
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSerialPattern() {
		return serialPattern;
	}
	public void setSerialPattern(String serialPattern) {
		this.serialPattern = serialPattern;
	}
	
}
