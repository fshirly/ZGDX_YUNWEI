package com.fable.insightview.monitor.AcUPS.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.monitor.AcUPS.entity.AirConditionBean;
import com.fable.insightview.monitor.AcUPS.entity.DomainBean;
import com.fable.insightview.monitor.AcUPS.entity.PerfRoomConditionsBean;
import com.fable.insightview.monitor.AcUPS.entity.SNMPbean;
import com.fable.insightview.monitor.AcUPS.entity.perfEastUpsBean;
import com.fable.insightview.monitor.AcUPS.entity.perfInvtUpsBean;
import com.fable.insightview.platform.page.Page;

public interface AirConditionMapper {

	boolean addData(AirConditionBean condition);
	
	boolean insertSNMP(SNMPbean snmPbean);
	
	boolean insertMOIPInfo(AirConditionBean condition);
	
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
	 AirConditionBean   queryAcditionByMOID(Map<String, Object> paramMap);
	 
	 	/**
		 * 新增前验证唯一性
		 */
		int checkbeforeAdd(AirConditionBean bean);
		
	/**
	 * 更新SNMPCommunityCache表
	 * @param SNMPCommunityCache
	 * @return
	 */
	int updateSNMPInfo(SNMPbean snmPbean);
	/***
	 * 获取snmp的自增长ID
	 * @param snmPbean
	 * @return
	 */
	int getSNMPID();
	/***
	 * 更新modevice表
	 * @param condition
	 * @return
	 */
	int updateModevice(AirConditionBean condition);
	
	/***
	 * 更新moipinfo
	 * @param condition
	 * @return
	 */
	int updateMOIpinfo(AirConditionBean condition);
	/***
	 * 查询moipinfo的MOID
	 * @param condition
	 * @return
	 */
	AirConditionBean selectIPinfo(AirConditionBean condition);
	
	 PerfRoomConditionsBean  queryperfRoomInfo(@Param("moId")int moId);
	 
	 /***
		 * 获取UPS的性能数据
		 * @param moid
		 * @return
		 */
		 perfEastUpsBean getUpsInfo(@Param("moId")int moId);
	
	int getConfParamValue();
	/**
	 * 获取厂商ID
	 * @param OID
	 * @return
	 */
	int getResManufacturerID(@Param("OID")String OID);
	
	 /***
	  * 查询英威腾UPS的信息
	  * @param moid
	  * @return
	  */
	 perfInvtUpsBean getInvtUpsInfo(@Param("moId")int moid);
}
