package com.fable.insightview.platform.snmpdevice.service;

import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.snmpdevice.entity.SnmpDeviceSysOIDInfo;

/**
 * @Description:   设备OID维护Service
 * @author         郑小辉
 * @Date           2014-6-30 上午09:51:23 
 */
public interface ISnmpDeviceService {
	/**
	 * ID查询
	 * @param paramName
	 * @param paramVal
	 * @return
	 */
	SnmpDeviceSysOIDInfo getInfoById(String paramName,String paramVal);
	/**
	 * 更新数据
	 * @param vo
	 * @return
	 */
	void updateInfo(SnmpDeviceSysOIDInfo vo);
	/**
	 * 删除数据
	 * @param vo
	 * @return
	 */
	void deleteInfo(SnmpDeviceSysOIDInfo vo);
	/**
	 * 插入数据
	 * @param vo
	 * @return
	 */
	void addInfo(SnmpDeviceSysOIDInfo vo); 	
	/**
	 * 批量删除
	 * @param id
	 */
	void deleteBathInfo(String id);	
	/**
	 * 分页查询
	 * @param vo
	 * @param flexiGridPageInfo
	 * @return
	 */
	FlexiGridPageInfo queryPage(SnmpDeviceSysOIDInfo vo,FlexiGridPageInfo flexiGridPageInfo);
}
