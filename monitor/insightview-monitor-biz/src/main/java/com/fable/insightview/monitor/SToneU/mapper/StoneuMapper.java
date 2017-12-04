package com.fable.insightview.monitor.SToneU.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.monitor.AcUPS.entity.SNMPbean;
import com.fable.insightview.monitor.SToneU.entity.MODeviceSt;
import com.fable.insightview.monitor.SToneU.entity.PerfStoneuBean;
import com.fable.insightview.platform.page.Page;

public interface StoneuMapper {

	List<MODeviceSt>  getStoneuList(Page<MODeviceSt> page);
	
	int addModevice(MODeviceSt modevice);
	
	int addBatch(List<MODeviceSt> stoneu);
	
	int getResManufacturerID(@Param("OID")String OID);
	
	int insertSNMP(SNMPbean snmp);
	
	int updateInfo(MODeviceSt stoneu);
	
	int updateSnmp(SNMPbean snmp);
	
	MODeviceSt queryByMOID(Map<String, Object> paramMap);
	
	List<MODeviceSt> queryChildInfo();
	
	List<PerfStoneuBean> perfList(@Param("deviceIP") String deviceIP);
	
	int checkMOdevice(Map<String, Object> paramMap);
	
	Date getDateNow();
}
