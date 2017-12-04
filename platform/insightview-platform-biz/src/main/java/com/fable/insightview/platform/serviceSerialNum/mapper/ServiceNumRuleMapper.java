package com.fable.insightview.platform.serviceSerialNum.mapper;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.platform.serviceSerialNum.entity.ServiceNumRule;

public interface ServiceNumRuleMapper {

	ServiceNumRule getNumRuleInfo(@Param("numRuleId")Integer numRuleId);

	void updateNumRuleInfo(ServiceNumRule rule);
}
