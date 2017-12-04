package com.fable.insightview.monitor.database.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fable.insightview.monitor.database.entity.PerfSybaseServerBean;
import com.fable.insightview.monitor.database.entity.SybaseServerKPINameDef;
import com.fable.insightview.monitor.database.service.ISyBaseService;

@Controller
@RequestMapping("/monitor/sybasePieManage")
public class SybaseChartPieController {
	@Autowired
    ISyBaseService sybaseService;
	
	private final Logger logger = LoggerFactory.getLogger(SyBaseServerController.class);
	/**
	 * server  事物数
	 */
	@RequestMapping("/findTransactionPie")
	@ResponseBody
	public Map<String, Object> findTransactionPie(HttpServletRequest request,ModelMap map){
		logger.info("事务数使用情况");
		int	moID = Integer.parseInt(request.getParameter("moID"));
		Map<String,Object> params = new HashMap<String,Object>();
		Map<String,Object> mapResult = new HashMap<String,Object>();
		Map<String,Object> map2 = new HashMap<String,Object>();
		Map<String, Object> mapName = new HashMap<String, Object>();
		ArrayList<Object> array1 = new ArrayList<Object>();
		ArrayList<Object> array2 = new ArrayList<Object>();
		StringBuffer pieName = new StringBuffer();
		StringBuffer pieJson = new StringBuffer();
		params.put("moID", moID);
		String[] textName = {"外部事务数","本地事务数"};
		String[] kpiName = {SybaseServerKPINameDef.EXTERNALTRANSACTIONNUM,SybaseServerKPINameDef.LOCALTRANSACTIONNUM};
		
		for(int i = 0; i < textName.length; i++) {
			pieName.append(",'"+textName[i]+"'");
			params.put("kpiName",kpiName[i]);
			PerfSybaseServerBean db = sybaseService.getPerfValue(params);
			if(db!=null){
			pieJson.append(",{value:"+db.getPerfValue()+",name:'"+textName[i]+"'}");//拼接json数据
			}else{
				pieJson.append(",{value:'',name:'"+textName[i]+"'}");//拼接json数据
			}
		}
		pieName.deleteCharAt(0);
		if(pieJson.length()!=0){
			pieJson.deleteCharAt(0);
		}
		//map1.put("textName","事务数使用情况统计");
		array1.add(pieJson);
		array2.add(pieName);
		map2.put("sybaseTransactionPie", array1);
		mapName.put("pieName", array2);
		mapResult.put("data", map2);
		mapResult.put("dataName", mapName);
		return mapResult;
	}
	/**
	 * server  进程数
	 */
	@RequestMapping("/findProcessesPie")
	@ResponseBody
	public Map<String, Object> findProcessesPie(HttpServletRequest request,ModelMap map){
		logger.info("进程数使用情况");
		int	moID = Integer.parseInt(request.getParameter("moID"));
		Map<String,Object> params = new HashMap<String,Object>();
		Map<String,Object> mapResult = new HashMap<String,Object>();
		Map<String,Object> map2 = new HashMap<String,Object>();
		Map<String, Object> mapName = new HashMap<String, Object>();
		ArrayList<Object> array1 = new ArrayList<Object>();
		ArrayList<Object> array2 = new ArrayList<Object>();
		StringBuffer pieName = new StringBuffer();
		StringBuffer pieJson = new StringBuffer();
		params.put("moID", moID);
		String[] textName = {"阻塞进程数","活跃进程数","系统进程数","空闲进程数"};
		String[] kpiName = {SybaseServerKPINameDef.BLOCKINGSYSPROCESSES,SybaseServerKPINameDef.ACTIVEPROCESSES,SybaseServerKPINameDef.SYSPROCESSES,SybaseServerKPINameDef.IDLESYSPROCESSES};
		
		for(int i = 0; i < textName.length; i++) {
			pieName.append(",'"+textName[i]+"'");
			params.put("kpiName",kpiName[i]);
			PerfSybaseServerBean db = sybaseService.getPerfValue(params);
			if(db!=null){
				pieJson.append(",{value:"+db.getPerfValue()+",name:'"+textName[i]+"'}");//拼接json数据
			}else{
				pieJson.append(",{value:'',name:'"+textName[i]+"'}");//拼接json数据
			}
		}
		pieName.deleteCharAt(0);
		if(pieJson.length()!=0){
			pieJson.deleteCharAt(0);
		}
		//map1.put("textName","进程数使用情况统计");
		array1.add(pieJson);
		array2.add(pieName);
		map2.put("sybaseProcessesPie", array1);
		mapName.put("pieName", array2);
		mapResult.put("data", map2);
		mapResult.put("dataName", mapName);
		return mapResult;
	}
}
