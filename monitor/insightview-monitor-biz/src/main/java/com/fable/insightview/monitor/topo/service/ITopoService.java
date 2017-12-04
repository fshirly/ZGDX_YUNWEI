package com.fable.insightview.monitor.topo.service;

import java.util.List;
import java.util.Map;

import com.fable.insightview.platform.page.Page;
import com.fable.insightview.monitor.topo.entity.MOSubnetBean;
import com.fable.insightview.monitor.topo.entity.TopoBean;
import com.fable.insightview.monitor.topo.entity.TopoLink;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.manufacturer.entity.ManufacturerInfoBean;

public interface ITopoService {
	/**
	 * 分页查询拓扑图列表数据
	 */
	List<TopoBean> selectTopo(Page<TopoBean> page);

	/**
	 * 根据id获得信息
	 */
	TopoBean getTopoByID(int id);
	
	/**
	 * 加载topo列表
	 * @param param
	 * @return
	 */
	List<TopoBean> loadTopoView(Map<String, String> param);
	
	/**
	 * 获取ip地址对应的long
	 * @param ip
	 * @return
	 */
	long getIPByStr(String ip);
	
	/**
	 * 获取厂商
	 * @param manufacturerInfoBean
	 * @param flexiGridPageInfo
	 * @return
	 */
	List<ManufacturerInfoBean> loadManufacturer(
			ManufacturerInfoBean manufacturerInfoBean,
			FlexiGridPageInfo flexiGridPageInfo);
	
	/**
	 * @param topo
	 * @param operateType
	 * @return
	 */
	List<Map<String, Object>> operateTopo(TopoBean topo,String operateType);
	
	/**
	 * 根据设备获取连接点
	 */
	List<TopoLink> getTopoLink(Map<String, String> param);
	
	/**
	 * 选择子网列表
	 * @param param
	 * @return
	 */
	List<MOSubnetBean> querySubNetList(Map<String, String> param);
	
	/**
	 * 根据ip获得子网信息
	 * @param subnetIp
	 * @return
	 */
	List<MOSubnetBean> getSubnetByIP(String subnetIp);
	
	/**
	 * 验证topo名称是否重复
	 */
	boolean checkTopoName(Map paramMap);
	
	/**
	 * 获得数据库列表
	 */
	List<Map<String, Object>> getDbList(int classId,Map<String, Object> map);
	
	/**
	 * 根据db2实例获得所有的数据库
	 */
	String getDB2InfoMoIds(int instanceMoId);
}
