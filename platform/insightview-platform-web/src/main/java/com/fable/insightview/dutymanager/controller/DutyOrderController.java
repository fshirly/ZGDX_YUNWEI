package com.fable.insightview.dutymanager.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.platform.dutymanager.dutyorder.entity.DutyOrder;
import com.fable.insightview.platform.dutymanager.dutyorder.service.DutyOrderService;

/**
 * 值班班次管理
 * @author chenly
 *
 */
@Controller
@RequestMapping("/dutyOrder")
public class DutyOrderController {
	
	@Autowired
	private DutyOrderService dutyOrderService;

	/**
	 * 值班班次列表页面
	 * @return
	 */
	@RequestMapping("/viewDutyOrderList")
	public ModelAndView viewDutyOrderList(String navigationBar) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("navigationBar", navigationBar);
		mv.setViewName("dutymanager/dutyOrder_list");
		return mv;
	}
	
	/**
	 * 值班班次列表
	 * @return
	 */
	@RequestMapping("/toDutyOrderList")
	@ResponseBody
	public Map<String, Object> toDutyOrderList() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<DutyOrder> dutyOrderList = dutyOrderService.findAllDutyOrders();
		result.put("total", dutyOrderList.size());
		result.put("rows", dutyOrderList);
		return result;
	}
	
	/**
	 * 新增值班班次页面
	 */
	@RequestMapping("/addDutyOrderPage")
	public ModelAndView addDutyOrderPage() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("dutymanager/dutyOrder_add");
		return mv;
	}
	
	/**
	 * 新增值班班次
	 */
	@RequestMapping("/addDutyOrder")
	@ResponseBody
	public String addDutuOrder(DutyOrder dutyOrder) {
		Integer intervalDays = dutyOrder.getIntervalDays();
		if(intervalDays > 0) {
			String endPoint = dutyOrder.getEndPoint()+"(第"+(intervalDays+1)+"天)";
			dutyOrder.setEndPoint(endPoint);
		}
		dutyOrderService.addDutyOrder(dutyOrder);
		return "success";
	}
	
	/**
	 * 修改值班班次页面
	 */
	@RequestMapping("/editDutyOrderPage")
	public ModelAndView editDutyOrderPage(Integer id) {
		ModelAndView mv = new ModelAndView();
		DutyOrder dutyOrder = dutyOrderService.findDutyOrderById(id);
		Integer intervalDays = dutyOrder.getIntervalDays();
		if(intervalDays > 0) {
			String endPoint = dutyOrder.getEndPoint().substring(0, 5);
			dutyOrder.setEndPoint(endPoint);
		}
		mv.addObject("dutyOrder", dutyOrder);
		mv.setViewName("dutymanager/dutyOrder_add");
		return mv;
	}
	
	/**
	 * 修改值班班次
	 */
	@RequestMapping("/editDutyOrder")
	@ResponseBody
	public String editDutyOrder(DutyOrder dutyOrder) {
		Integer intervalDays = dutyOrder.getIntervalDays();
		if(intervalDays > 0) {
			String endPoint = dutyOrder.getEndPoint()+"(第"+(intervalDays+1)+"天)";
			dutyOrder.setEndPoint(endPoint);
		}
		dutyOrderService.editDutyOrder(dutyOrder);
		return "success";
	}
	
	/**
	 * 查看值班班次详情
	 */
	@RequestMapping("/showDutyOrder")
	public ModelAndView showDutyOrder(Integer id) {
		ModelAndView mv = new ModelAndView();
		DutyOrder dutyOrder = dutyOrderService.findDutyOrderById(id);
		mv.addObject("dutyOrder", dutyOrder);
		mv.setViewName("dutymanager/dutyOrder_detail");
		return mv;
	}
	
	/**
	 * 删除值班班次
	 */
	@RequestMapping("deleteDutyOrder")
	@ResponseBody
	public String deleteDutyOrder(Integer id) {
		dutyOrderService.deleteDutyOrderById(id);
		return "success";
	}
}
