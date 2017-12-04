package com.fable.insightview.platform.workplan.service;

import java.util.List;

import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.workplan.entity.WorkPlan;

public interface WorkPlanService {
	
	List<WorkPlan> findWorkPlans(Page page);
	
	boolean deleteWorkPlanById(Integer id);
	
	boolean editWorkPlan(WorkPlan workPlan);
	
	void addWorkPlan(WorkPlan workPlan);
	
	WorkPlan findWorkPlanById(Integer id);
	
	List<WorkPlan> findWorkPlansByUserId(Integer userId);
}
