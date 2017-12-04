package com.fable.insightview.monitor.machineRoom.contorller;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.monitor.machineRoom.entity.MachineRoomBean;
import com.fable.insightview.monitor.machineRoom.service.MachineRoomService;

@Controller
@RequestMapping("/monitor/machineRoom")
public class MachineRoomContorller {
	private static final Logger logger = LoggerFactory .getLogger(MachineRoomContorller.class);
	@Autowired
	MachineRoomService yzService;

	@RequestMapping("/toQueryInfo")
	public ModelAndView toQueryInfo(ModelMap map,HttpServletRequest request){
		map.put("navigationBar",request.getParameter("navigationBar"));
		List<MachineRoomBean> list = yzService.queryInfo();
		TreeMap<String,List<MachineRoomBean>> resultMap = new TreeMap<String, List<MachineRoomBean>>();
		String status="";
		for (MachineRoomBean bean : list) {
			if(bean.getLinkCut()==0){
				status="连接正常";
			}else{
				status="连接中断";
			}
			if(resultMap.containsKey(bean.getDeviceDesc()+":"+status)){
				resultMap.get(bean.getDeviceDesc()+":"+status).add(bean);
			}else{
				List<MachineRoomBean>  beanList = new ArrayList<MachineRoomBean>();
				beanList.add(bean);
				resultMap.put(bean.getDeviceDesc()+":"+status,beanList);
			}
		}
		request.setAttribute("resultMap", resultMap);
		return new ModelAndView("monitor/machineRoom/machineRoom_list");
	}
	
}
