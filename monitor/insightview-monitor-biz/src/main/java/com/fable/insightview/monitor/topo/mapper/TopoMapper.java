package com.fable.insightview.monitor.topo.mapper;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.monitor.topo.entity.MOSubnetBean;
import com.fable.insightview.monitor.topo.entity.TopoBean;
import com.fable.insightview.monitor.topo.entity.TopoLink;
import com.fable.insightview.platform.page.Page;

public interface TopoMapper {

	/**
	 * 分页查询拓扑图列表数据
	 */
	List<TopoBean> selectTopo(Page<TopoBean> page);

	/**
	 * 根据id获得信息
	 */
	TopoBean getTopoByID(int id);

	/**
	 * 查询topo view list
	 */
	LinkedList<TopoBean> getTopoViewList(Map map);

	/**
	 * 删除
	 */
	boolean delTopoByID(@Param("ids") String ids);

	/**
	 * 新增
	 */
	int insertTopo(TopoBean bean);

	/**
	 * 更新
	 */
	int updateTopo(TopoBean bean);

	/**
	 * topo 设备连接
	 */
	LinkedList<TopoLink> getTopoLink(Map<String, String> param);
	
	/**
	 * 选择子网列表
	 * 
	 * @param param
	 * @return
	 */
	List<MOSubnetBean> querySubNetList(Map map);

	List<TopoBean> getAllTopo();
	
	List<MOSubnetBean> getSubnetByIP(String subnetIp);
	
	List<TopoBean> getByNameAndID(Map parmetermap);
}
