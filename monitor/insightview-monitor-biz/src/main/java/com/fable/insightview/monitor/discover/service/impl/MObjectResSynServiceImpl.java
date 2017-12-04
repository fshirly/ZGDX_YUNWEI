package com.fable.insightview.monitor.discover.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.fable.insightview.monitor.discover.entity.MODeviceObj;
import com.fable.insightview.monitor.discover.entity.SynchronConstant;
import com.fable.insightview.monitor.discover.entity.SynchronObject;
import com.fable.insightview.monitor.discover.mapper.MODeviceMapper; 
import com.fable.insightview.monitor.discover.service.IMObjectResSynService;
import com.fable.insightview.monitor.environmentmonitor.entity.MOReader;
import com.fable.insightview.monitor.environmentmonitor.entity.MOTag;
import com.fable.insightview.monitor.mostorage.entity.MOStorageBean;

import com.fable.insightview.monitor.environmentmonitor.mapper.EnvMonitorMapper;
import com.fable.insightview.monitor.mostorage.mapper.MOStorageMapper;

import com.fable.insightview.monitor.host.entity.HostComm;
import com.fable.insightview.monitor.mocpus.entity.MOCPUsBean;
import com.fable.insightview.monitor.mocpus.mapper.MOCPUsMapper;
import com.fable.insightview.monitor.momemories.entity.MOMemoriesBean;
import com.fable.insightview.monitor.momemories.mapper.MOMemoriesMapper;
import com.fable.insightview.monitor.monetworkIif.entity.MONetworkIfBean;
import com.fable.insightview.monitor.monetworkIif.mapper.MONetworkIfMapper;
import com.fable.insightview.monitor.movolumes.entity.MOVolumesBean;
import com.fable.insightview.monitor.movolumes.mapper.MOVolumesMapper;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.common.helper.RestHepler; 
import com.fable.insightview.platform.sysinit.SystemParamInit;

@Service("mObjectResSynService")
public class MObjectResSynServiceImpl implements IMObjectResSynService {
	
	private static final long serialVersionUID = -8697049781798812644L;

	private static final Logger logger = LoggerFactory.getLogger(MObjectResSynServiceImpl.class);
	private static final int COMPONENT = 1;

	@Autowired
	MODeviceMapper moDeviceMapper;
	
	@Autowired
	MOMemoriesMapper moMemoryMapper;
	
	@Autowired
	MOCPUsMapper moCPUsMapper;
	
	@Autowired
	MOVolumesMapper moVolumesMapper;
	
	@Autowired
	MONetworkIfMapper moNetworkIfMapper;
	
	@Autowired
	EnvMonitorMapper envMonitorMapper;
	
	@Autowired
	MOStorageMapper moStorageMapper;
	
	// 默认每批次10条
	private static int pagesize = 100000;
	
	private static int sendsize = 10;
	// 默认发送者
	private static String transferor = "monitor";
	// 主机
	// private static int modevice = 3;
	// 接口
	private static int mointerface = 10;
	// 数据存储
	private static int mostorage = 85;
	// 磁盘
	private static int movolume = 11;
	// cpu
	private static int mocputype = 12;
	// 内存
	private static int momomery = 13;
	
	private static int motag = 45;
	
	private static Map<Integer,Integer> typeRelation = new HashMap<Integer,Integer>();
	// 电子标签关系暂时保存在缓存
	static {
		//温度
		typeRelation.put(46, 27);
		//水带
		typeRelation.put(47, 29);
		//温湿度
		typeRelation.put(48, 28);
		//门磁
		typeRelation.put(49, 30);
		//干节点
		typeRelation.put(50, 31);
		//气压
		typeRelation.put(51, 32);
	}
  
	/***
	 * 执行数据同步方法
	 * 
	 */
	public boolean synchronRes(int mobectType,int resType,String moIDs,String resIDs,String assetType) {
		logger.info("....start execute synchron mobject to CMDB....moIDs="+moIDs);
		if (moIDs == null || moIDs.equals("")) {
			logger.info("缺少必要参数, moid不能为空");
			return false;
		}
		
		try { 
			
			int assetTypeId  = 0;
			if (assetType != null && !assetType.equals("")) {
				assetTypeId = Integer.parseInt(assetType);
			}
			
			//如果是阅读器类型时,同步下面的电子标签
			if (mobectType == motag) {
				executeReader(mobectType,resType,moIDs,assetTypeId);
				executeTag(mobectType,resType,moIDs);
				return true;
			}
			
			if (resIDs == null || resIDs.equals("null") || resIDs.equals("")
					|| resIDs.indexOf(",") <= 0) {
				logger.info("没有任何子对象!");
				return true;
			}
			
			Map<Integer, Integer> relation = new HashMap<Integer, Integer>();
			String[] arr = resIDs.split(",");
			 
			for (int i = 0; i < arr.length; i++) {
				relation.put(Integer.parseInt(arr[i]), Integer
						.parseInt(arr[i + 1]));
				i++;
			}
			
			// 同步设备
			executeDevice(mobectType,resType,moIDs,assetTypeId,relation); 
			
			for (Map.Entry<Integer, Integer> entry : relation.entrySet()) {
				int moTypeID = entry.getKey();
				int resTypeID = entry.getValue();
				// 接口
				if (moTypeID > 0 && moTypeID == 10) {
					executeNetworkIf(moTypeID, resTypeID, moIDs);
				}
				// 磁盘
				else if (moTypeID > 0 && moTypeID == 11) {
					executeVolumes(moTypeID, resTypeID, moIDs);
				}
				// cpu
				else if (moTypeID > 0 && moTypeID == 12) {
					executeCpu(moTypeID, resTypeID, moIDs);
				}
				// 内存
				else if (moTypeID > 0 && moTypeID == 13) {
					executeMemory(moTypeID, resTypeID, moIDs);
				}
				// 数据存储
				else if(moTypeID >0 && moTypeID==85){
					executeStorage(moTypeID, resTypeID, moIDs);
				}
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 同步阅读器 
	 */
	private void executeReader(int mobectType,int resType,String moIDs,int assetTypeId) {
		logger.info(".....开始同步阅读  start sync reader ......");
		Page<MOReader> page = new Page<MOReader>();
		page.setPageNo(1);
		page.setPageSize(pagesize);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("moIDs", moIDs);
		paramMap.put("mobectType", mobectType);
		page.setParams(paramMap);
		
		List<MOReader> syncMOReader = envMonitorMapper.synchronMOReaderToRes(page);
		if (syncMOReader == null || syncMOReader.size() == 0) {
			logger.info("reader没有可同步的信息!");
			return;
		}
		
		int pagenum = syncMOReader.size() / sendsize; 
		if (syncMOReader.size() % sendsize > 0 && syncMOReader.size() > sendsize) {
			pagenum = pagenum + 1;
		}

		
		int batch = 0;
		List<MOReader> tmpList = new ArrayList<MOReader>();
		for (int p = 0; p < syncMOReader.size(); p++) {
			tmpList.add(syncMOReader.get(p));
			if (p > 0 && p % sendsize == 0) {
				batch++;
				invokeParty(writeMapJSON(1000, batch + "/" + pagenum, tmpList,
						resType,assetTypeId));
				tmpList = new ArrayList<MOReader>();
			}
		}
		if (tmpList.size() > 0) { 
			batch++;
			logger.info("reader last invoke:");
			invokeParty(writeMapJSON(1000, batch + "/" + pagenum, tmpList,resType,assetTypeId)); 
		}
	}

	/**
	 * 同步设备
	 * 
	 */
	private void executeDevice(int mobectType,int resType,String moIDs,int assetTypeId,Map<Integer, Integer> relation) {
		logger.info("...... start sync device ......");
		Page<MODeviceObj> page = new Page<MODeviceObj>();
		List<MODeviceObj> syncMODevice = new ArrayList<MODeviceObj>();
		page.setPageNo(1);
		page.setPageSize(pagesize);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("moIDs", moIDs);
		paramMap.put("mobectType", mobectType);
		page.setParams(paramMap);
		
		//同步站点-http
		if(mobectType == 93){
			syncMODevice = moDeviceMapper.synchronMoHttpToRes(page);
		}else if(mobectType == 94){//同步站点-tcp
			syncMODevice = moDeviceMapper.synchronMoTcpToRes(page);
		}else {
			syncMODevice = moDeviceMapper.synchronMoDeviceToRes(page);
		}
		if (syncMODevice == null || syncMODevice.size() == 0) {
			logger.info("device没有可同步的信息!");
			return;
		}
		
		int pagenum = syncMODevice.size() / sendsize; 
		if (syncMODevice.size() % sendsize > 0 && syncMODevice.size() > sendsize) {
			pagenum = pagenum + 1;
		}
		
		int batch = 0;
		List<MODeviceObj> tmpList = new ArrayList<MODeviceObj>();
		for (int p = 0; p < syncMODevice.size(); p++) {
			int moId = syncMODevice.get(p).getMoid();
			//List<String> ipList = moDeviceMapper.getMoinfoByMOID(moId);
			//StringBuffer ipsBuf = new StringBuffer();
			//for (int i = 0; i < ipList.size(); i++) {
				//ipsBuf.append(ipList.get(i) + ",");
			//}
			//String deviceips = ipsBuf.toString().substring(0, ipsBuf.toString().lastIndexOf(","));
			//syncMODevice.get(p).setDeviceip(deviceips);
			List<Map<String, Object>> allComponent = new ArrayList<>();
			Map<String, Object> component = null;
			for (Map.Entry<Integer, Integer> entry : relation.entrySet()) {
				component = new HashMap<>();
				int moTypeID = entry.getKey();
				int resTypeID = entry.getValue();
				String componentIds = "";
				List<Integer> moIDLst = new ArrayList<>();
				// 接口
				if (moTypeID > 0 && moTypeID == 10) {
					moIDLst = moNetworkIfMapper.getResIDByDeviceId(moId);
				}
				// 磁盘
				else if (moTypeID > 0 && moTypeID == 11) {
					moIDLst = moVolumesMapper.getResIDByDeviceId(moId);
				}
				// cpu
				else if (moTypeID > 0 && moTypeID == 12) {
					moIDLst = moCPUsMapper.getResIDByDeviceId(moId);
				}
				// 内存
				else if (moTypeID > 0 && moTypeID == 13) {
					moIDLst = moMemoryMapper.getResIDByDeviceId(moId);
				}
				// 数据存储
				else if(moTypeID >0 && moTypeID==85){
					moIDLst = moStorageMapper.getResIDByDeviceId(moId);
				}
				if(moIDLst != null){
					for (int i = 0; i < moIDLst.size(); i++) {
						Integer resId = moIDLst.get(i);
						if(resId != null){
							componentIds += resId +",";
						}
					}
					if(!"".equals(componentIds) && !componentIds.isEmpty()){
						componentIds = componentIds.substring(0, componentIds.lastIndexOf(","));
					}else{
						componentIds = null;
					}
				}
				//component.put("componentTypeid", resTypeID);
				//component.put("componentId", componentIds);
				//allComponent.add(component);
			}
			//syncMODevice.get(p).setAllComponent(allComponent);
			tmpList.add(syncMODevice.get(p));
			if (p > 0 && p % sendsize == 0) {
				batch++;
				invokeParty(writeMapJSON(1000, batch + "/" + pagenum, tmpList,
						resType,assetTypeId));
				tmpList = new ArrayList<MODeviceObj>();
			}
		}
		if (tmpList.size() > 0) { 
			batch++;
			logger.info("device last invoke:");
			invokeParty(writeMapJSON(1000, batch + "/" + pagenum, tmpList,resType,assetTypeId)); 
		}
	}

	/**
	 * 同步内存 
	 */
	private void executeMemory(int mobectType,int resType,String moIDs) {
		Page<MOMemoriesBean> page = new Page<MOMemoriesBean>();
		page.setPageNo(1);
		page.setPageSize(pagesize);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("moIDs", moIDs);
		page.setParams(paramMap);
		
		List<MOMemoriesBean> moMemory = moMemoryMapper.synchronMOMemoryToRes(page);
		if (moMemory == null || moMemory.size() == 0) {
			logger.info("momory没有可同步的信息!");
			return;
		}
		
		int pagenum = moMemory.size() / sendsize;
		if (moMemory.size() % sendsize > 0 &&  moMemory.size() > sendsize) {
			pagenum = pagenum + 1;
		}
		
		int batch = 0;
		List<MOMemoriesBean> tmpList = new ArrayList<MOMemoriesBean>();
		for (int p = 0; p < moMemory.size(); p++) { 
			MOMemoriesBean memories = moMemory.get(p);
			memories.setMemorySize(HostComm.getBytesToSize(Long.parseLong(memories.getMemorySize())));
			tmpList.add(memories);
			if (p > 0 && p % sendsize == 0) { 
				batch++; 
				invokeParty(writeMapJSON(1000, batch + "/" + pagenum, tmpList,resType,0));
				tmpList = new ArrayList<MOMemoriesBean>();
			}
		}
		if (tmpList.size() > 0) {
			batch++;
			logger.info("last memory:");
			invokeParty(writeMapJSON(1000, batch + "/" + pagenum, tmpList,resType,0)); 
		}
	}

	/**
	 * 同步数据存储
	 */
	private void executeStorage(int mobectType,int resType,String moIDs) {
		Page<MOStorageBean> page = new Page<MOStorageBean>();
		page.setPageNo(1);
		page.setPageSize(pagesize);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("moIDs", moIDs);
		page.setParams(paramMap);
		
		List<MOStorageBean> moStorage = moStorageMapper.synchronMOStorage(page);
		if (moStorage == null || moStorage.size() == 0) {
			logger.info("storage没有可同步的信息!");
			return;
		}
		
		int pagenum = moStorage.size() / sendsize;
		if (moStorage.size() % sendsize > 0 &&  moStorage.size() > sendsize) {
			pagenum = pagenum + 1;
		}
		
		int batch = 0;
		List<MOStorageBean> tmpList = new ArrayList<MOStorageBean>();
		for (int p = 0; p < moStorage.size(); p++) { 
			MOStorageBean storage = moStorage.get(p);
			storage.setCapacity(HostComm.getBytesToSize(Long.parseLong(storage.getCapacity())));
			tmpList.add(storage);
			if (p > 0 && p % sendsize == 0) { 
				batch++; 
				invokeParty(writeMapJSON(1000, batch + "/" + pagenum, tmpList,resType,0));
				tmpList = new ArrayList<MOStorageBean>();
			}
		}
		if (tmpList.size() > 0) {
			batch++;
			logger.info("last storage:");
			invokeParty(writeMapJSON(1000, batch + "/" + pagenum, tmpList,resType,0)); 
		}
	}

	/**
	 * 同步CPU
	 * 
	 */
	private void executeCpu(int mobectType,int resType,String moIDs) {
		logger.info("...... start sync cpu ......");
		Page<MOCPUsBean> page = new Page<MOCPUsBean>();
		page.setPageNo(1);
		page.setPageSize(pagesize);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("moIDs", moIDs);
		page.setParams(paramMap);
		List<MOCPUsBean> moCPUs = moCPUsMapper.synchronMOCPUsToRes(page);
		if (moCPUs == null || moCPUs.size() == 0) {
			logger.info("cpu没有可同步的信息!");
			return;
		}
		
		int pagenum = moCPUs.size() / sendsize;
		if (moCPUs.size() % sendsize > 0 && moCPUs.size() > sendsize) {
			pagenum = pagenum + 1;
		}

		int batch = 0;
		List<MOCPUsBean> tmpList = new ArrayList<MOCPUsBean>();
		for (int p = 0; p < moCPUs.size(); p++) { 
			tmpList.add(moCPUs.get(p));
			if (p > 0 && p % sendsize == 0) { 
				batch++; 
				invokeParty(writeMapJSON(1000, batch + "/" + pagenum, tmpList, resType,0));
				tmpList = new ArrayList<MOCPUsBean>();
			}
		}
		if (tmpList.size() > 0) { 
			batch++;
			logger.info("last cpu:");
			invokeParty(writeMapJSON(1000, batch + "/" + pagenum, tmpList, resType,0)); 
		}
	}

	/**
	 * 同步磁盘
	 */
	private void executeVolumes(int mobectType,int resType,String moIDs) {
		logger.info("...... start sync volumes ......");
		Page<MOVolumesBean> page = new Page<MOVolumesBean>();
		page.setPageNo(1);
		page.setPageSize(pagesize);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("moIDs", moIDs);
		page.setParams(paramMap);
		List<MOVolumesBean> moVolumes = moVolumesMapper.synchronMOVolumes(page);
		if (moVolumes == null || moVolumes.size() == 0) {
			logger.info("volumes没有可同步的信息!");
			return;
		}
		
		int pagenum = moVolumes.size() / sendsize;
		if (moVolumes.size() % sendsize > 0 && moVolumes.size() > sendsize) {
			pagenum = pagenum + 1;
		}
		
		int batch = 0;
		List<MOVolumesBean> tmpList = new ArrayList<MOVolumesBean>();
		for (int p = 0; p < moVolumes.size(); p++) {
			tmpList.add(moVolumes.get(p));
			MOVolumesBean moVolumesBean = moVolumes.get(p);
			moVolumesBean.setDiskSize(HostComm.getBytesToSize(Long.parseLong(moVolumesBean.getDiskSize()))) ;
			if (p > 0 && p % sendsize == 0) {
				batch++;
				invokeParty(writeMapJSON(1000, batch + "/" + pagenum, tmpList, resType,0));
				tmpList = new ArrayList<MOVolumesBean>();
			}
		}
		
		if (tmpList.size() > 0) { 
			batch++;
			logger.info("last Volumes:");
			invokeParty(writeMapJSON(1000, batch + "/" + pagenum, tmpList, resType,0)); 
		}
	}

	/**
	 * 同步接口
	 */ 
	private void executeNetworkIf(int moTypeID,int resTypeID,String moIDs) {
		logger.info("...... start sync networkif ......");
		Page<MONetworkIfBean> page = new Page<MONetworkIfBean>();
		page.setPageNo(1);
		page.setPageSize(pagesize);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("moIDs", moIDs);
		page.setParams(paramMap);
		List<MONetworkIfBean> moNetworkIf = moNetworkIfMapper.synchronMONetworkIfToRes(page);
		if (moNetworkIf == null || moNetworkIf.size() == 0) {
			logger.info("networkIf没有可同步的信息!");
			return;
		}
		
		int pagenum = moNetworkIf.size() / sendsize;
		if (moNetworkIf.size() % sendsize > 0 && moNetworkIf.size() > sendsize) {
			pagenum = pagenum + 1;
		}
		
		int batch = 0;
		List<MONetworkIfBean> tmpList = new ArrayList<MONetworkIfBean>();
		for (int p = 0; p < moNetworkIf.size(); p++) {
			tmpList.add(moNetworkIf.get(p));
			
			MONetworkIfBean networkIfBean = moNetworkIf.get(p);
			networkIfBean.setIfSpeed(HostComm.getBytesToSpeed(Long.parseLong(networkIfBean.getIfSpeed())));
//			Byte
//			networkIfBean.setIfMtu(HostComm.getBytesToSize(Long.parseLong(networkIfBean.getIfSpeed())));
			if (p > 0 && p % sendsize == 0) {
				batch++;
				invokeParty(writeMapJSON(1000, batch + "/" + pagenum, tmpList, resTypeID,0));
				tmpList = new ArrayList<MONetworkIfBean>();
			}
		}
		if (tmpList.size() > 0) {
			batch++;
			logger.info("last networkif:");
			invokeParty(writeMapJSON(1000, batch + "/" + pagenum, tmpList, resTypeID,0)); 
		}
	}
	
	
	/**
	 * 同步电子标签
	 */
	private void executeTag(int moTypeID,int resTypeID,String moIDs) {
		logger.info("...... start sync mo tag ......"); 
		Page<MOTag> page = null;  
		int batch = 0;
		List<MOTag> tmpList = new ArrayList<MOTag>();
		List<MOTag> motag = null;
		//按电子标签类型遍历
		
		for (Map.Entry<Integer, Integer> entry : typeRelation.entrySet()) { 	 
			page = new Page<MOTag>();
			page.setPageNo(1);
			page.setPageSize(pagesize);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("moIDs", moIDs);
			paramMap.put("motype", entry.getKey());
			page.setParams(paramMap);

			motag = envMonitorMapper.synchronMOTagToRes(page);
			logger.info("motype="+entry.getKey()+" motag.size()="+motag.size());
			if (motag == null || motag.size() == 0) {
				logger.info("此标签类型motag没有可同步的信息!");
				continue;
			}
			
			int pagenum = motag.size() / sendsize;
			if (motag.size() % sendsize > 0 && motag.size() > sendsize) {
				pagenum = pagenum + 1;
			}
			
			batch = 0;
			tmpList = new ArrayList<MOTag>();
			for (int p = 0; p < motag.size(); p++) {
				tmpList.add(motag.get(p));
				if (p > 0 && p % sendsize == 0) {
					batch++;
					invokeParty(writeMapJSON(1000, batch + "/" + pagenum, tmpList, entry.getValue(),0));
					tmpList = new ArrayList<MOTag>();
				}
			}
			
			if (tmpList.size() > 0) {
				batch++; 
				invokeParty(writeMapJSON(1000, batch + "/" + pagenum, tmpList, entry.getValue(),0)); 
			} 
		} 
	}

	/**
	 * 组织json
	 * @param batchid
	 * @param process
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static JSONObject writeMapJSON(int batchid, String process, List obj,int resType,int assetTypeId) {
		try {
			SynchronObject joinsObj = new SynchronObject();
			joinsObj.setTransferor(transferor);
			joinsObj.setTransfertime(new Date()); 
			Random random = new Random();      
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");    
			Date curDate = new Date(System.currentTimeMillis());//获取当前时间      
			String str = formatter.format(curDate) +String.valueOf(random.nextInt(1000)); 
			long batchid2 =  Long.parseLong(str);  
			joinsObj.setBatchid(batchid2);
			joinsObj.setProcess(process); 
			int size = obj.size();
			List data = new ArrayList();
			for (int i = 0; i < obj.size(); i++) {
				SynchronConstant constant = new SynchronConstant();
				if (obj.get(i) instanceof MODeviceObj) {
					MODeviceObj mo = (MODeviceObj) obj.get(i);
					if (mo.getMoClassId() != null && mo.getMoClassId() > 0) {
						//constant.setResTypeId(resType);
					} else {
						size--;
						continue;
					}
					if (mo.getResid() != null) {
						constant.setResId(mo.getResid());
					}
					constant.setMoTypeId(mo.getMoClassId());
					constant.setMoId(mo.getMoid());
					//constant.setAllComponent(mo.getAllComponent());
					//mo.setAllComponent(null);
				} else if (obj.get(i) instanceof MOMemoriesBean) {
					MOMemoriesBean mo = (MOMemoriesBean) obj.get(i); 
					if (mo.getResid() != null) {
						constant.setResId(mo.getResid());
					}
					constant.setMoTypeId(momomery);
					constant.setMoId(mo.getMoID());
					constant.setIsComponent(COMPONENT);
				} else if (obj.get(i) instanceof MOCPUsBean) {
					MOCPUsBean mo = (MOCPUsBean) obj.get(i); 
					if (mo.getResid() != null) {
						constant.setResId(mo.getResid());
					}
					constant.setMoTypeId(mocputype);
					constant.setMoId(mo.getMoID());
					constant.setIsComponent(COMPONENT);
				} else if (obj.get(i) instanceof MOVolumesBean) {
					MOVolumesBean mo = (MOVolumesBean) obj.get(i); 
					if (mo.getResid() != null) {
						constant.setResId(mo.getResid());
					}
					constant.setMoTypeId(movolume);
					constant.setMoId(mo.getMoID());
					constant.setIsComponent(COMPONENT);
				} else if (obj.get(i) instanceof MONetworkIfBean) {
					MONetworkIfBean mo = (MONetworkIfBean) obj.get(i); 
					if (mo.getResid() != null) {
						constant.setResId(mo.getResid());
					}
					constant.setMoTypeId(mointerface);
					constant.setMoId(mo.getMoID());
					constant.setIsComponent(COMPONENT);
				} else if (obj.get(i) instanceof MOStorageBean) {
					MOStorageBean mo = (MOStorageBean) obj.get(i); 
					if (mo.getResid() != null) {
						constant.setResId(mo.getResid());
					}
					constant.setMoTypeId(mostorage);
					constant.setMoId(mo.getMoID());
					constant.setIsComponent(COMPONENT);
				}else if(obj.get(i) instanceof MOTag){
					MOTag mo = (MOTag) obj.get(i); 
					if (mo.getResID() != null && mo.getResID() > 0) {
						constant.setResId(mo.getResID());
					}
					constant.setMoTypeId(mo.getMoClassId());
					constant.setMoId(mo.getMoID());
				} else if(obj.get(i) instanceof MOReader){
					MOReader mo = (MOReader) obj.get(i); 
					if (mo.getResID() != null && mo.getResID() > 0) {
						constant.setResId(mo.getResID());
					}
					constant.setMoTypeId(mo.getMoClassId());
					constant.setMoId(mo.getMoID());
				}
				constant.setAssetTypeId(assetTypeId);
				constant.setResTypeId(resType);
				constant.setContent(obj.get(i));
				data.add(constant);
			}
			
			joinsObj.setSize(size);
			joinsObj.setData(data);
			// 返回json
			String json = ObjectToJsonString(joinsObj);
			logger.info(json);
			JSONObject objJson = JSONObject.parseObject(json);
			return objJson;
		} catch (Exception e) {
			logger.error("同步设备信息调用Rest接口出错",e);
		}
		return null;
	}

	private void invokeParty(JSONObject jsondata) {
		try {
			String path = "/rest/cmdb/monitor/sync/bulk";
			String username = SystemParamInit.getValueByKey("rest.username");
			String password = SystemParamInit.getValueByKey("rest.password");
			// 拼接获取单板接口URL
			StringBuffer basePath = new StringBuffer();
			String url = SystemParamInit.getValueByKey("rest.resSychron.url");
			//basePath.append("http://192.168.1.25:8080/insightview-bpmconsole-war/");
			basePath.append(url);
			basePath.append(path);
			// 设置请求头信息
			HttpHeaders requestHeaders = RestHepler.createHeaders(username,
					password);
			HttpEntity<Object> requestEntity = new HttpEntity<Object>(jsondata,
					requestHeaders);
			RestTemplate rest = new RestTemplate();
			// 参数1：请求地址 ，参数2：请求方式，参数3：请求头信息，参数4：相应类
			logger.info("准备发送参数:"+basePath.toString());
			ResponseEntity<String> rssResponse = rest.exchange(basePath
					.toString(), HttpMethod.POST, requestEntity, String.class);
			if (null != rssResponse) {
				logger.info("rest请求返回："+rssResponse.getBody());
			}
		} catch (Exception e) {
//			logger.error("3  调用接口同步,有错误!");
//			e.printStackTrace();
		}
	}

//	private void invokeAll(String jsondata) {
//		RestTemplate restTemplate = new RestTemplate();
//		restTemplate.postForObject(
//				"http://localhost:8080/rest/cmdb/monitor/sync/all", null,
//				String.class, jsondata);
//	}
	
//	/**
//	 * 获取指定的时间
//	 * @param interval
//	 * @return
//	 */
//	private String getConcreteTime(int interval) {
//		Calendar c = Calendar.getInstance();
//		c.add(Calendar.DAY_OF_MONTH, -interval);
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		return formatter.format(c.getTime());
//	}
	
	public static String ObjectToJsonString(Object o){
		if(o != null){
			ObjectMapper mapper = new ObjectMapper(); 
			try { 
//			    /* We want dates to be treated as ISO8601 not timestamps. */ 
//				mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false); 
  				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 				extracted(mapper, formatter);
				return mapper.writeValueAsString(o);
			} catch (Exception e) {
				logger.error("对象转json失败！", e);
			}
		}
		return null;
	}
	
 	@SuppressWarnings("deprecation")
 	private static void extracted(ObjectMapper mapper,SimpleDateFormat formatter) {
 		mapper.setDateFormat(formatter);
 	}
}