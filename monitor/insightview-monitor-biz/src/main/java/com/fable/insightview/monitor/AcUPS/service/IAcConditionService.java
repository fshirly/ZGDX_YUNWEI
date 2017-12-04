package com.fable.insightview.monitor.AcUPS.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.fable.insightview.monitor.AcUPS.entity.AirConditionBean;
import com.fable.insightview.monitor.AcUPS.entity.DomainBean;
import com.fable.insightview.monitor.AcUPS.entity.PerfRoomConditionsBean;
import com.fable.insightview.monitor.AcUPS.entity.perfEastUpsBean;
import com.fable.insightview.monitor.AcUPS.entity.perfInvtUpsBean;
import com.fable.insightview.monitor.util.ConfigParameterCommon;
import com.fable.insightview.platform.page.Page;


public interface IAcConditionService {

	/**
	 * 测试该监测是否能够连接成功
	 * @param snmp
	 * @param mOClassID 
	 * @return
	 */
	JSONObject getTestResult(ConfigParameterCommon snmp, String mOClassID);
	
	boolean insertData(AirConditionBean condition);
	
	boolean updateData(AirConditionBean condition, int templateID, String moTypeLstJson);
	/***
	 * 查询列表展示
	 * @param page
	 * @return
	 */
	List<AirConditionBean>  queryAcConditionList(Page<AirConditionBean> page);
	
	/***
	 * 查询Domain的信息
	 * @return
	 */
	ArrayList<DomainBean> DomainList();
	/***
	 * 根据MOID查询空调信息
	 * @param paramMap
	 * @return
	 */
	 AirConditionBean  queryAcditionByMOID(Map<String, Object> paramMap);
	 
		/**
		 * 新增前验证唯一性
		 */
		boolean checkbeforeAdd(AirConditionBean bean);
		
		/***
		 * 查询空调性能数据
		 * @param moid
		 * @return
		 */
		 PerfRoomConditionsBean  queryperfRoomInfo(int moid);
		/***
		 * 获取UPS的性能数据
		 * @param moid
		 * @return
		 */
		 perfEastUpsBean getUpsInfo(int moid);
		 /***
		  * 查询英威腾UPS的信息
		  * @param moid
		  * @return
		  */
		 perfInvtUpsBean getInvtUpsInfo(int moid);
		 
		int getConfParamValue();
		
		boolean addSitePerfTask(AirConditionBean condition,int templateID,String moTypeLstJson);
		
		int getDomainID(String startIP);
}
