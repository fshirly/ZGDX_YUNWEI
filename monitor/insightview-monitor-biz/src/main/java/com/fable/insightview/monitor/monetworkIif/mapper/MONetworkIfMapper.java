package com.fable.insightview.monitor.monetworkIif.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.monitor.monetworkIif.entity.MONetworkIfBean;
import com.fable.insightview.monitor.monetworkIif.entity.TopoLinkBean;
import com.fable.insightview.platform.page.Page;

public interface MONetworkIfMapper {
	List<MONetworkIfBean> selectMONetworkIf(Page<MONetworkIfBean> page);
	
	List<MONetworkIfBean> synchronMONetworkIfToRes(Page<MONetworkIfBean> page);
	
	int updateResId(Map map);
	
	MONetworkIfBean getMONetworkIfById(int moID);

	int getByDeviceMOIDAndMOID(MONetworkIfBean bean);	
	
	List<MONetworkIfBean> queryList(Page<MONetworkIfBean> page);
	
	List<TopoLinkBean> queryTopoList();
	
	MONetworkIfBean getFirstInterface(Integer parentMoClassId);
	
	int updateNetWorkIf(@Param("moID")int moID,@Param("isCollect") int isCollect);
	 void  deleteSnapshotNetDevice(@Param("moID")int moID);
	int toUpdateNetWorkIf(@Param("DeviceMOID")int DeviceMOID,@Param("isCollect") int isCollect, @Param("SourcePort")int SourcePort);
	
	List<Integer> getResIDByDeviceId(@Param("deviceMoId")int deviceMoId);
}
