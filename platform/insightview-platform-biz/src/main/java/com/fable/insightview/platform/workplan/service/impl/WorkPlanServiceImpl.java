package com.fable.insightview.platform.workplan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.workplan.entity.WorkPlan;
import com.fable.insightview.platform.workplan.mapper.WorkPlanMapper;
import com.fable.insightview.platform.workplan.service.WorkPlanService;

@Service
public class WorkPlanServiceImpl implements WorkPlanService {

	@Autowired
	private WorkPlanMapper workPlanMapper;
	
	@Override
	public boolean deleteWorkPlanById(Integer id) {
		int count = workPlanMapper.deleteWorkPlanById(id);
		if(count > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean editWorkPlan(WorkPlan workPlan) {
		int count = workPlanMapper.updateWorkPlan(workPlan);
		if(count > 0) {
			return true;
		}
		return false;
	}

	@Override
	public void addWorkPlan(WorkPlan workPlan) {
		workPlanMapper.insertWorkPlan(workPlan);
	}

	@Override
	public WorkPlan findWorkPlanById(Integer id) {
		return workPlanMapper.selectWorkPlanById(id);
	}

	@Override
	public List<WorkPlan> findWorkPlans(Page page) {
		return workPlanMapper.selectWorkPlans(page);
	}

	@Override
	public List<WorkPlan> findWorkPlansByUserId(Integer userId) {
		return workPlanMapper.selectWorkPlansByUserId(userId);
	}

}
