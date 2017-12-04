package com.fable.insightview.monitor.dutydesk.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.monitor.dutydesk.service.IDutyDeskService;
import com.fable.insightview.monitor.topo.entity.TopoBean;

/**
 * 值班服务台监控模块rest接口
 * @author hanl
 *
 */
@Controller
@RequestMapping("/monitor/dutyDeskRoom")
public class DutyDeskRoom3DController {
	private static final Logger logger = LoggerFactory.getLogger(DutyDeskRoom3DController.class);
	@Autowired
	IDutyDeskService dutyDeskService;
	/**
	 * 跳转至拓扑
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/toDeskTopo")
	public ModelAndView toDeskTopo(ModelMap map) throws Exception {
		Map<String, Object> result = dutyDeskService.getTopos();
		List<TopoBean> topoList = null;
		if(result.get("topoList") != null){
			topoList = (List<TopoBean>) result.get("topoList");
		}
		if(result.get("defaultTopoId") != null){
			int defaultTopoId = Integer.parseInt(result.get("defaultTopoId").toString());
			map.put("defaultTopoId", defaultTopoId);
		}else{
			logger.info("没有配置默认展示的拓扑！");
		}
		map.put("topoList", topoList);

		return new ModelAndView("monitor/dutydesk/dutyDeskTopo");
	}

	/**
	 * 跳转至3d机房
	 */
	@RequestMapping("/toDeskRoom3D")
	public ModelAndView toDeskRoom3D(ModelMap map)
			throws Exception {
		Map<String, Object> result = dutyDeskService.get3dRooms();
		if(result.get("defaultRoomId") != null){
			int defaultRoomId = Integer.parseInt(result.get("defaultRoomId").toString());
			map.put("defaultRoomId", defaultRoomId);
		}else{
			logger.info("没有配置默认展示的3D的机房！");
		}
		map.put("roomList", result.get("roomList"));
		return new ModelAndView("monitor/dutydesk/dutyDeskRoom3D");
	}
}
