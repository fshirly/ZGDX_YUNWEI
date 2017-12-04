package com.fable.insightview.monitor.machineRoom.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.machineRoom.entity.MachineRoomBean;
import com.fable.insightview.monitor.machineRoom.entity.MoiemBean;
import com.fable.insightview.monitor.machineRoom.mapper.MachineRoomMapper;
import com.fable.insightview.monitor.machineRoom.service.DBUtil;
import com.fable.insightview.monitor.machineRoom.service.MachineRoomService;
import com.fable.insightview.platform.core.dao.idgenerator.impl.IDGeneratorFactory;
@Service
public class MachineRoomServiceimpl implements MachineRoomService {
	private static final Logger logger = LoggerFactory .getLogger(MachineRoomServiceimpl.class);
	SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	MachineRoomMapper yzMapper;
	@Override
	public List<MachineRoomBean> queryInfo() {
		List<MachineRoomBean> macList  = new ArrayList<MachineRoomBean>();
		List<Map<String,String>> result = DBUtil.query("select de.OriDesc as deviceDesc,de.IpAddr,de.Type as type,"
				+ "a.valueType,a.indexOfType,a.curIntValue,a.curFloatValue,a.curValue,"
				+ "a.oriDesc,a.defDesc,a.linkCut  from rtnodevalue a" +
		"  left join rtdevice  de on de.Address = a.Address and de.HostName = a.HostName",null);
		for (Map<String, String> map : result) {
			if(map.containsKey("error") || map.containsKey("Switch")){
				return macList;
			}else{
				MachineRoomBean mac = new MachineRoomBean();
				mac.setDeviceDesc(map.get("deviceDesc"));
				mac.setIpAddr(map.get("IpAddr"));
				mac.setType(map.get("type"));
				mac.setValueType(Integer.parseInt(map.get("valueType")));
				mac.setIndexOfType(Integer.parseInt(map.get("indexOfType")));
				mac.setCurIntValue(Integer.parseInt(map.get("curIntValue")));
				mac.setCurFloatValue(Float.parseFloat(map.get("curFloatValue")));
				mac.setCurValue(map.get("curValue"));
				mac.setOriDesc(map.get("oriDesc"));
				mac.setDefDesc(map.get("defDesc"));
				mac.setLinkCut(Integer.parseInt(map.get("linkCut")));
				macList.add(mac);
			}
		}
		return macList;
	}
	@Override
	public boolean queryDeviceInfo() {
		List<MoiemBean>  deviceList = new ArrayList<MoiemBean>();
		List<MoiemBean>  MObject = yzMapper.queryDeviceInfo();
		List<Map<String,String>> resultList = DBUtil.query("select idRtDevice, type,address,hostName,serialNo,defDesc,oriDesc,ipAddr,startTime as createTime,"
				+"stopTime as lastUpdateTime  from rtdevice ", null);
		for (Map<String, String> map : resultList) {
			if(map.containsKey("error") || map.containsKey("Switch")){
				return false;
			} else{
				MoiemBean iem = new MoiemBean();
				for (MoiemBean bean : MObject) {
					if(map.get("oriDesc").contains(bean.getClassLable())){
						iem.setIdRtDevice(Integer.parseInt(map.get("idRtDevice")));
						iem.setType(Integer.parseInt(map.get("type")));
						iem.setAddress(Integer.parseInt(map.get("address")));
						iem.setHostName(map.get("hostName"));
						iem.setSerialNo(map.get("serialNo"));
						iem.setDefDesc(map.get("defDesc"));
						iem.setOriDesc(map.get("oriDesc"));
						iem.setIpAddr(map.get("ipAddr"));
						iem.setCreateTime(map.get("createTime"));
						iem.setLastUpdateTime(map.get("lastUpdateTime"));
						iem.setMoClassID(bean.getMoClassID());
					}
				}
				deviceList.add(iem);
			}
		}
		List<Map<String,String>> resultMoiemList =  DBUtil.query("select IdRtDevice from rtdevice", null);
		StringBuffer sb =new StringBuffer();
		for (Map<String, String> map : resultMoiemList) {
			sb.append(map.get("IdRtDevice")+",");
		}
		String idRtDevice = null ;
		List<MoiemBean> MOiemList=new ArrayList<MoiemBean>();
		if(null !=sb && sb.length()>0){
			idRtDevice= sb.toString().substring(0, sb.toString().length()-1);
			MOiemList = yzMapper.MOiemList(idRtDevice);
		}
		
		List<Integer> temp = new ArrayList<Integer>();
		List<MoiemBean> tempUpdateList=new ArrayList<MoiemBean>();
		List<MoiemBean> tempInsertList=new ArrayList<MoiemBean>();
		
		// 获取需要更新的idRtDevice
		for (MoiemBean moiemBean : MOiemList) {
			temp.add(moiemBean.getIdRtDevice());
		}
		
		for (MoiemBean moiemBean : deviceList) {
			if(temp.contains(moiemBean.getIdRtDevice())){
				// 需要更新的数据
				tempUpdateList.add(moiemBean);
			}else{
				// 需要新增的数据
				tempInsertList.add(moiemBean);
			}
		}
		
		if(tempUpdateList.size()>0){
			for (MoiemBean moiemBean : tempUpdateList) {
				moiemBean.setLastUpdateTime(simple.format(new Date()));
				try {
					yzMapper.updateMOiemInfo(moiemBean);
				} catch (Exception e) {
					logger.error("更新MOiem表异常",e);
				}
			}
		}
		 
		if(tempInsertList.size()>0){
			for (MoiemBean moiemBean : tempInsertList) {
				int id = Integer.parseInt(IDGeneratorFactory.getInstance().getGenerator(MoiemBean.class).generate().toString());
				moiemBean.setMOID(id);
				moiemBean.setCreateTime(simple.format(new Date()));
				moiemBean.setLastUpdateTime(simple.format(new Date()));
			}
			try {
				yzMapper.insertMOiemInfo(tempInsertList);
			} catch (Exception e) {
				logger.error("批量新增异常",e);
			}
		}
		 return true;
	}
	 
}
