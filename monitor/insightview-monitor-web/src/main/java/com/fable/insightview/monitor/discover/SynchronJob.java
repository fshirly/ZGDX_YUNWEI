package com.fable.insightview.monitor.discover;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map; 
import java.util.Random;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;
 
import com.fable.insightview.monitor.alarmdispatcher.utils.AlarmObjToJSON;
import com.fable.insightview.monitor.discover.entity.MODeviceObj;
import com.fable.insightview.monitor.discover.entity.SynchronConstant;
import com.fable.insightview.monitor.discover.entity.SynchronObject;
import com.fable.insightview.monitor.discover.mapper.MODeviceMapper;
import com.fable.insightview.monitor.mocpus.entity.MOCPUsBean;
import com.fable.insightview.monitor.mocpus.mapper.MOCPUsMapper;
import com.fable.insightview.monitor.monetworkIif.entity.MONetworkIfBean;
import com.fable.insightview.monitor.monetworkIif.mapper.MONetworkIfMapper;
import com.fable.insightview.monitor.movolumes.entity.MOVolumesBean;
import com.fable.insightview.monitor.movolumes.mapper.MOVolumesMapper;
import com.fable.insightview.monitor.momemories.entity.MOMemoriesBean;
import com.fable.insightview.monitor.momemories.mapper.MOMemoriesMapper;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.common.helper.RestHepler;
//import com.fable.insightview.platform.core.util.BeanLoader;
import com.fable.insightview.platform.sysinit.SystemParamInit;

@Controller
public class SynchronJob {
	 
	private static final long serialVersionUID = -8697049781798812644L;

	private static final Logger logger = LoggerFactory.getLogger(SynchronJob.class);

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
	
	// 默认每批次10条
	private static int pagesize = 100000;
	
	private static int sendsize = 10;
	// 默认发送者
	private static String transferor = "monitor";
	// 主机
	private static int modevice = 3;
	// 接口
	private static int mointerface = 10;
	// 磁盘
	private static int movolume = 11;
	// cpu
	private static int mocputype = 12;
	// 内存
	private static int momomery = 13;

	// 对应资源类型
	// 主机
	private static int restype_device = 3;
	// 接口
	private static int restype_interface = 10053;
	// 磁盘
	private static int restype_volume = 10054;
	// cpu
	private static int restype_cpu = 10056;
	// 内存
	private static int restype_momery = 10055;
	
	private static Map<Integer,Integer> mapperMap = new HashMap<Integer,Integer>();
	
	static{
		// 监测类型对应资源类型
		mapperMap.put(5, 10050);
		mapperMap.put(6, 10057);
		mapperMap.put(7, 10048);
		mapperMap.put(8, 10049);
		mapperMap.put(9, 10047);
		
		mapperMap.put(10, 10053);
		mapperMap.put(11, 10054);
		mapperMap.put(12, 10056);
		mapperMap.put(13, 10055);
	}

	/***
	 * 执行数据同步方法
	 * 
	 */
	public void execute() {
		logger.info("....start execute synchron mobject to CMDB....");
		try {
			// 同步设备
			logger.info("....开始同步MODeviceTemp.....");
			executeDevice();
			logger.info("....MODeviceTemp同步结束.....");

			// 同步内存
			logger.info("....开始同步内存 .....");
			executeMemory();
			logger.info("....内存 同步结束.....");

			// 同步CPU
			logger.info("....开始同步CPU .....");
			executeCpu();
			logger.info("....CPU同步结束.....");

			// 同步磁盘
			logger.info("....开始同步磁盘 .....");
			executeVolumes();
			logger.info("....磁盘同步结束.....");

			// 同步接口
			logger.info("....开始同步接口 .....");
			executeNetworkIf();
			logger.info("....接口同步结束.....");
 
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 同步设备
	 * 
	 */
	private void executeDevice() {
		logger.info("...... start sync device ......");
		Page<MODeviceObj> page = new Page<MODeviceObj>();
		page.setPageNo(1);
		page.setPageSize(pagesize);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("updateTime", getConcreteTime(1));
		page.setParams(paramMap);
		List<MODeviceObj> syncMODeviceTemp = moDeviceMapper.synchronMoDeviceToRes(page);
		if(syncMODeviceTemp==null || syncMODeviceTemp.size()==0){
			logger.info("device没的可同步的信息!");
			return;
		}
		
		int pagenum = syncMODeviceTemp.size() / sendsize;
		if (syncMODeviceTemp.size() % sendsize > 0 && syncMODeviceTemp.size()>11) {
			pagenum = pagenum + 1;
		}
		int batch = 0;
		List<MODeviceObj> tmpList = new ArrayList<MODeviceObj>();
		for (int p = 0; p < syncMODeviceTemp.size(); p++) {
			tmpList.add(syncMODeviceTemp.get(p));
			if (p > 0 && p % sendsize == 0) { 
				batch++; 
				invokeParty(writeMapJSON(1000, batch + "/" + pagenum, tmpList));
				tmpList = new ArrayList<MODeviceObj>();
			}
		}
		if (tmpList.size() > 0) { 
			batch++;
			logger.info("device last invoke:");
			invokeParty(writeMapJSON(1000, batch + "/" + pagenum, tmpList)); 
		}
	}

	/**
	 * 同步内存
	 * 
	 */
	private void executeMemory() {
		Page<MOMemoriesBean> page = new Page<MOMemoriesBean>();
		page.setPageNo(1);
		page.setPageSize(pagesize);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("updateTime", getConcreteTime(1));
		page.setParams(paramMap);
		List<MOMemoriesBean> moMemory = moMemoryMapper.synchronMOMemoryToRes(page);
		if (moMemory == null || moMemory.size() == 0) {
			logger.info("momory没的可同步的信息!");
			return;
		}
		
		int pagenum = moMemory.size() / sendsize;
		if (moMemory.size() % sendsize > 0 && moMemory.size() > 11) {
			pagenum = pagenum + 1;
		}
		int batch = 0;
		List<MOMemoriesBean> tmpList = new ArrayList<MOMemoriesBean>();
		for (int p = 0; p < moMemory.size(); p++) { 
			tmpList.add(moMemory.get(p));
			if (p > 0 && p % sendsize == 0) { 
				batch++; 
				invokeParty(writeMapJSON(1000, batch + "/" + pagenum, tmpList));
				tmpList = new ArrayList<MOMemoriesBean>();
			}
		}
		if (tmpList.size() > 0) {
			batch++;
			logger.info("last memory:");
			invokeParty(writeMapJSON(1000, batch + "/" + pagenum, tmpList)); 
		}
	}

	/**
	 * 同步CPU
	 * 
	 */
	private void executeCpu() {
		logger.info("...... start sync cpu ......");
		Page<MOCPUsBean> page = new Page<MOCPUsBean>();
		page.setPageNo(1);
		page.setPageSize(pagesize);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("updateTime", getConcreteTime(1));
		page.setParams(paramMap);
		List<MOCPUsBean> moCPUs = moCPUsMapper.synchronMOCPUsToRes(page);
		if (moCPUs == null || moCPUs.size() == 0) {
			logger.info("cpu没的可同步的信息!");
			return;
		}
		
		int pagenum = moCPUs.size() / sendsize;
		if (moCPUs.size() % sendsize > 0 && moCPUs.size()>11) {
			pagenum = pagenum + 1;
		}
		int batch = 0;
		List<MOCPUsBean> tmpList = new ArrayList<MOCPUsBean>();
		for (int p = 0; p < moCPUs.size(); p++) { 
			tmpList.add(moCPUs.get(p));
			if (p > 0 && p % sendsize == 0) { 
				batch++; 
				invokeParty(writeMapJSON(1000, batch + "/" + pagenum, tmpList));
				tmpList = new ArrayList<MOCPUsBean>();
			}
		}
		if (tmpList.size() > 0) { 
			batch++;
			logger.info("last cpu:");
			invokeParty(writeMapJSON(1000, batch + "/" + pagenum, tmpList)); 
		}
	}

	/**
	 * 同步磁盘
	 */
	private void executeVolumes() {
		logger.info("...... start sync volumes ......");
		Page<MOVolumesBean> page = new Page<MOVolumesBean>();
		page.setPageNo(1);
		page.setPageSize(pagesize);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("updateTime", getConcreteTime(1));
		page.setParams(paramMap);
		List<MOVolumesBean> moVolumes = moVolumesMapper.synchronMOVolumes(page);
		if (moVolumes == null || moVolumes.size() == 0) {
			logger.info("volumes没的可同步的信息!");
			return;
		}
		
		int pagenum = moVolumes.size() / sendsize;
		if (moVolumes.size() % sendsize > 0 && moVolumes.size() > 11) {
			pagenum = pagenum + 1;
		}
		
		int batch = 0;
		List<MOVolumesBean> tmpList = new ArrayList<MOVolumesBean>();
		for (int p = 0; p < moVolumes.size(); p++) {
			tmpList.add(moVolumes.get(p));
			if (p > 0 && p % sendsize == 0) {
				batch++;
				invokeParty(writeMapJSON(1000, batch + "/" + pagenum, tmpList));
				tmpList = new ArrayList<MOVolumesBean>();
			}
		}
		if (tmpList.size() > 0) { 
			batch++;
			logger.info("last Volumes:");
			invokeParty(writeMapJSON(1000, batch + "/" + pagenum, tmpList)); 
		}
	}

	/**
	 * 同步接口
	 */
	private void executeNetworkIf() {
		logger.info("...... start sync networkif ......");
		Page<MONetworkIfBean> page = new Page<MONetworkIfBean>();
		page.setPageNo(1);
		page.setPageSize(pagesize);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("updateTime", getConcreteTime(1));
		page.setParams(paramMap);
		List<MONetworkIfBean> moNetworkIf = moNetworkIfMapper.synchronMONetworkIfToRes(page);
		if (moNetworkIf == null || moNetworkIf.size() == 0) {
			logger.info("networkIf没的可同步的信息!");
			return;
		}
		
		int pagenum = moNetworkIf.size() / sendsize;
		if (moNetworkIf.size() % sendsize > 0 && moNetworkIf.size()>11) {
			pagenum = pagenum + 1;
		}
		
		int batch = 0;
		List<MONetworkIfBean> tmpList = new ArrayList<MONetworkIfBean>();
		for (int p = 0; p < moNetworkIf.size(); p++) {
			tmpList.add(moNetworkIf.get(p));
			if (p > 0 && p % sendsize == 0) {
				batch++;
				invokeParty(writeMapJSON(1000, batch + "/" + pagenum, tmpList));
				tmpList = new ArrayList<MONetworkIfBean>();
			}
		}
		if (tmpList.size() > 0) {
			batch++;
			logger.info("last networkif:");
			invokeParty(writeMapJSON(1000, batch + "/" + pagenum, tmpList)); 
		}
	}

	/**
	 * 组织json
	 * 
	 * @param batchid
	 * @param process
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static JSONObject writeMapJSON(int batchid, String process, List obj) {
		try {
			// json str
			// {"transferor":"monitor"," transfertime":"2014-08-19 12:12:12","batchid":"1000","process":"1/10","data":
			// [{"resTypeId":1,
			// "moId":123321,moTypeId:1,resId:0,"content":{"moname":"dev1"}}]}
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
					if(mo.getMoClassId()!=null && mo.getMoClassId()>0 && mapperMap.get(mo.getMoClassId())!=null){
						constant.setResTypeId(mapperMap.get(mo.getMoClassId()));
					} else {
						size--;
						continue;
					}
					if (mo.getResid() != null) {
						constant.setResId(mo.getResid());
					}
					constant.setMoTypeId(mo.getMoClassId());
					constant.setMoId(mo.getMoid());
				} else if (obj.get(i) instanceof MOMemoriesBean) {
					MOMemoriesBean mo = (MOMemoriesBean) obj.get(i);
					constant.setResTypeId(restype_momery);
					if (mo.getResid() != null) {
						constant.setResId(mo.getResid());
					}
					constant.setMoTypeId(momomery);
					constant.setMoId(mo.getMoID());
				} else if (obj.get(i) instanceof MOCPUsBean) {
					MOCPUsBean mo = (MOCPUsBean) obj.get(i);
					constant.setResTypeId(restype_cpu);
					if (mo.getResid() != null) {
						constant.setResId(mo.getResid());
					}
					constant.setMoTypeId(mocputype);
					constant.setMoId(mo.getMoID());
				} else if (obj.get(i) instanceof MOVolumesBean) {
					MOVolumesBean mo = (MOVolumesBean) obj.get(i);
					constant.setResTypeId(restype_volume);
					if (mo.getResid() != null) {
						constant.setResId(mo.getResid());
					}
					constant.setMoTypeId(movolume);
					constant.setMoId(mo.getMoID());
				} else if (obj.get(i) instanceof MONetworkIfBean) {
					MONetworkIfBean mo = (MONetworkIfBean) obj.get(i);
					constant.setResTypeId(restype_interface);
					if (mo.getResid() != null) {
						constant.setResId(mo.getResid());
					}
					constant.setMoTypeId(mointerface);
					constant.setMoId(mo.getMoID());
				}
				constant.setContent(obj.get(i));
				data.add(constant);
			}
			
			joinsObj.setSize(size);
			joinsObj.setData(data);
			// 返回json
//			String json = AlarmObjToJSON.ObjectToJsonString(joinsObj);
			JSONObject newObj = JSONObject.fromObject(joinsObj);
//			logger.info(joinsObj);
			return newObj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void invokeParty(JSONObject jsondata) { 
		try{
			String url = SystemParamInit.getValueByKey("rest.resSychron.url");
			String path = "/rest/cmdb/monitor/sync/bulk";
			String username = SystemParamInit.getValueByKey("rest.username");
			String password = SystemParamInit.getValueByKey("rest.password");
			// 拼接获取单板接口URL
			StringBuffer basePath = new StringBuffer();
//			basePath.append("http://192.168.1.18:8080/insightview-bpmconsole-war/");
			basePath.append(url);
			basePath.append(path);
			// http://192.168.1.25:7070/fable-itsm-bpmconsole-war/rest/cmdb/monitor/sync/bulk
			// basePath.append("http://192.168.1.18:8080/insightview-bpmconsole-war/rest/cmdb/monitor/sync/bulk"); 
			// 设置请求头信息
			HttpHeaders requestHeaders = RestHepler.createHeaders(username,password);
			// System.out.println("\n jsondata:"+jsondata);
			HttpEntity<Object> requestEntity = new HttpEntity<Object>(jsondata,requestHeaders);
			RestTemplate rest = new RestTemplate();
			// 参数1：请求地址 ，参数2：请求方式，参数3：请求头信息，参数4：相应类
			ResponseEntity<String> rssResponse = rest.exchange(basePath.toString(),
					HttpMethod.POST, requestEntity, String.class);
	
			if (null != rssResponse) {
				logger.info(rssResponse.getBody());
			}
		}catch(Exception e){
			logger.error("调用接口同步,有错误!");
		}
	}

	private void invokeAll(String jsondata) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.postForObject(
				"http://localhost:8080/rest/cmdb/monitor/sync/all", null,
				String.class, jsondata);
	}
	
	/**
	 * 获取指定的时间
	 * @param interval
	 * @return
	 */
	private String getConcreteTime(int interval) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -interval);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(c.getTime());
	}

	public static void main(String[] args) {
		List deviceList = new ArrayList();
		MODeviceObj mo = new MODeviceObj();
		mo.setMoname("设备1");
		mo.setMoalias("用户测试");
		deviceList.add(mo);
		writeMapJSON(1000, "1/10", deviceList);
	}
}