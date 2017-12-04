package com.fable.insightview.monitor.environmentmonitor.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fable.insightview.monitor.alarmmgr.alarmactive.controller.AlarmActiveController;
import com.fable.insightview.monitor.alarmmgr.entity.MOKPIThresholdBean;
import com.fable.insightview.monitor.alarmmgr.mokpithreshold.service.IMOKPIThresholdService;
import com.fable.insightview.monitor.discover.mapper.MODeviceMapper;
import com.fable.insightview.monitor.discover.mapper.SynchronResResultMapper;
import com.fable.insightview.monitor.environmentmonitor.entity.PerfSnapshotRoom;
import com.fable.insightview.monitor.environmentmonitor.entity.UpAndDownThresholdBean;
import com.fable.insightview.monitor.environmentmonitor.service.IEnvMonitorService;
import com.fable.insightview.monitor.mocpus.mapper.MOCPUsMapper;
import com.fable.insightview.monitor.momemories.mapper.MOMemoriesMapper;
import com.fable.insightview.monitor.monetworkIif.mapper.MONetworkIfMapper;
import com.fable.insightview.monitor.movolumes.mapper.MOVolumesMapper;

@Controller
@RequestMapping("/rest/cmdb/monitor")
public class RoomRestInterfaceController {

	@Autowired
	MODeviceMapper moDeviceMapper;

	@Autowired
	SynchronResResultMapper synchronResResultMapper;

	@Autowired
	MONetworkIfMapper moNetworkIfMapper;

	@Autowired
	MOVolumesMapper moVolumesMapper;

	@Autowired
	MOCPUsMapper moCPUsMapper;

	@Autowired
	MOMemoriesMapper moMemoryMapper;
	
	@Autowired
	IEnvMonitorService envMonitorServer;
	
	@Autowired
	IMOKPIThresholdService thresholdService;

	private final static Logger logger = LoggerFactory.getLogger(AlarmActiveController.class);
	
	private static int Temperature = 46;
	
	private static int WaterHose = 47;
	
	private static int TemperatureHumidity = 48;
	
	private static int DoorMagnetic = 49;
	
	private static int DryContact = 50;
	
	private static int Pressure = 51;
	
	DecimalFormat df = new DecimalFormat("0.00");//格式化小数  
	
	/**
	 * 3d机房首次加载环境监控数据
	 * @param ciIds 设备的ID
	 * @param request 请求对象
	 * @return 返回监控数据
	 */
	@RequestMapping(value = "/envQueryBy3dRoom", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	List<Map<String, Object>> envQueryBy3dRoom(@RequestBody String ciIds,
			HttpServletRequest request) {
		try {
			logger.info("ciIds:"+ciIds+" 3d机房首次加载环境监控数据");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("resid", ciIds);

			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			List<PerfSnapshotRoom> perfDataList = envMonitorServer.loadEnvDataBy3DResID(params); 
			Map<String, Object> map = new HashMap<String, Object>(); 
			for (PerfSnapshotRoom perf : perfDataList) {
				if (perf.getClassID() == Temperature) {
					if (!perf.getKpiName().equals("TagTemperature")) {
						continue;
					}
				} else if (perf.getClassID() == WaterHose) {
					if (!perf.getKpiName().equals("FluidDetected")) {
						continue;
					}
				} else if (perf.getClassID() == TemperatureHumidity) {
					if (!perf.getKpiName().equals("TagHumidity")
							&& !perf.getKpiName().equals("TagTemperature")) {
						continue;
					}
				} else if (perf.getClassID() == DoorMagnetic) {
					if (!perf.getKpiName().equals("DoorOpen")) {
						continue;
					}
				} else if (perf.getClassID() == DryContact) {
					if (!perf.getKpiName().equals("DryContactOpen")) {
						continue;
					}
				} else if (perf.getClassID() == Pressure) {
					if (!perf.getKpiName().equals("DiffPressure")) {
						continue;
					}
				}
				
				boolean isExist = false;
				map = new HashMap<String, Object>();
				if (perf.getClassID() == TemperatureHumidity && perf.getKpiName().equals("TagHumidity")) {  
					for (Map<String, Object> tmpMap : list) {
						if (tmpMap.get("ciId") != null && tmpMap.get("ciId").equals(perf.getResID()) ) {
							tmpMap.put("value2", perf.getPerfValue());
							// 湿度							
							tmpMap.put("humhreshold", getHumhreshold(perf));
							isExist = true;
							break;
						}
					}
					if (!isExist) {
						map = new HashMap<String, Object>();
						map.put("ciId", perf.getResID());
						map.put("value2", perf.getPerfValue());
						// 湿度
						map.put("humhreshold", getHumhreshold(perf));
					}
				} else {
					for(Map<String, Object> tmpMap:list){  
						if (tmpMap.get("ciId") != null && tmpMap.get("ciId").equals(perf.getResID()) ) {
							tmpMap.put("value1", perf.getPerfValue()); 
							if (perf.getClassID() == Temperature){
								// 湿度
								map.put("temperThreshold", getHumhreshold(perf));
							}
							isExist = true;
							break;
						}
					}
					if(!isExist){
						map = new HashMap<String, Object>();
						map.put("ciId", perf.getResID());  
						map.put("value1", perf.getPerfValue());
						if (perf.getClassID() == Temperature){
							// 湿度
							map.put("temperThreshold", getHumhreshold(perf));
						}
					} 
				}
				
				if (map.size()>0) {
					list.add(map);
				}
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 3d机房加载告警详情
	 * @param alarmIDs 告警号
	 * @param request 请求对象
	 * @return 返回告警数据
	 */
	@RequestMapping(value = "/envDetailBy3dRoom", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	public @ResponseBody
	List<Map<String, Object>> envDetailBy3dRoom(@RequestBody String ciIds,HttpServletRequest request) {
		try {
			logger.info("ciIds:" + ciIds + " 3d机房加载环境监控详情数据");
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("resid", ciIds);

			// init返回对象
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Map<String, Object> map = new HashMap<String, Object>();
			// 查询详情
			List<PerfSnapshotRoom> perfDataList = envMonitorServer
					.loadEnvDataBy3DResID(params);
			// 遍历查询结果
			for (PerfSnapshotRoom perf : perfDataList) {
				map = new HashMap<String, Object>(); 
				map.put("name", perf.getKpiName());
				map.put("value", perf.getPerfValue());
				map.put("displayName", perf.getKpiChnName());
				map.put("categoryName", perf.getDescr()+"-"+perf.getTagID());
				map.put("accessType", "attr");  
				list.add(map);
			}
			// return
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	} 
	
	public String getHumhreshold(PerfSnapshotRoom perf){
		MOKPIThresholdBean mokpiVo = thresholdService.getInfoBySourceMOID(perf.getMoID()+"", perf.getDeviceMOID()+"", perf.getClassID()+"");
		if(mokpiVo==null){
			mokpiVo = thresholdService.getInfoBySourceMOID(null, perf.getDeviceMOID()+"", perf.getClassID()+"");
		}		
		return mokpiVo!=null?df.format(mokpiVo.getUpThreshold()):"0.00";
	}
	
	
	
	/**
	 * 电子标签性能值
	 * @param ciIds
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/electronicTagPefData", method = RequestMethod.POST, 
			headers = "Accept=application/json", produces = "application/json;charset=utf-8")
	public @ResponseBody List<Map<String, String>> 
	electronicTagPefData(@RequestBody String ciIds,HttpServletRequest request) {
		
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("resid", ciIds);
			// init返回对象
			Double up, down;
			List<Map<String, String>> list = new ArrayList<Map<String, String>>(); 
			Map<Integer, Map<String, String>> DataVlaueMap = new HashMap<Integer, Map<String, String>>(); 
			Map<String,Integer> conditionMap = new HashMap<String, Integer>();
			
			// 查询详情
			List<PerfSnapshotRoom> perfDataList = envMonitorServer.queryElectronicTag(params);
			// 遍历查询结果
			for (PerfSnapshotRoom perf : perfDataList) {
				conditionMap.put("moClassId",perf.getMOClassID());
				conditionMap.put("kpiID",perf.getKpiID());
				/**
				 * 构造Map<Integer, Map<String, String>> DataVlaueMap
				 * 将resId作为其key,其他属性放到Map<String, String>中,
				 * 如果DataVlaueMap中不存在key为perf.getResID()的Map,
				 * 则构建一个新的以resID为key,value为空的HashMap
				 */
				if (DataVlaueMap.containsKey(perf.getResID())) {
					
				} else {
					DataVlaueMap.put(perf.getResID(), new HashMap<String, String>());
					DataVlaueMap.get(perf.getResID()).put("resID", String.valueOf(perf.getResID()));
				}
				
				// 将查询出来的其他属性值放入Map<Integer, Map<String, String>> DataVlaueMap中的Map<String, String>里面
				DataVlaueMap.get(perf.getResID()).put(perf.getName(),perf.getPerfValue());
				if (perf.getKpiName().equals("TagTemperature")) {
					up = perf.getUpThreshold();
					down = perf.getDownThreshold();
					if (perf.getUpThreshold() == null || perf.getDownThreshold() == null) {
						UpAndDownThresholdBean upAndDown = envMonitorServer.queryUpAndDownValue(conditionMap);
						// 判断自己本身没有上限值，父类有上限值
						if (up == null && upAndDown != null) {
							up = upAndDown.getUpThreshold();
						}
						if (down == null && upAndDown != null) {
							down = upAndDown.getUpThreshold();
						}
					}
					DataVlaueMap.get(perf.getResID()).put("温度上限值",String.valueOf(up));
					DataVlaueMap.get(perf.getResID()).put("温度下限值",String.valueOf(down));
				}

				else if (perf.getKpiName().equals("TagHumidity")) {
					// 先将自身的上限值分别付给up、down
					up = perf.getUpThreshold();
					down = perf.getDownThreshold();
					
					if (perf.getUpThreshold() == null || perf.getDownThreshold() == null) {
						UpAndDownThresholdBean upAndDown = envMonitorServer.queryUpAndDownValue(conditionMap);
						// 判断自己本身没有上限值，父类有上限值,那么就将父类的上限值给UP
						if (up == null && upAndDown != null) {
							up = upAndDown.getUpThreshold();
						}
						if (down == null && upAndDown != null) {
							down = upAndDown.getUpThreshold();
						}
					}
					DataVlaueMap.get(perf.getResID()).put("湿度上限值",String.valueOf(up));
					DataVlaueMap.get(perf.getResID()).put("湿度下限值",String.valueOf(down));
				}
			}
			/**
			 * 遍历Map<Integer, Map<String, String>> DataVlaueMap中的value(Map<String, String>),
			 * 然后将其值放入到list中传到前台
			 */
			for (Integer dateKey : DataVlaueMap.keySet()) {
				list.add(DataVlaueMap.get(dateKey));
			}
			
			return list;
		} catch (Exception e) {
			logger.error("电子标签性能值出错",e);
		}
		return null;
	}
	
	
	 /**
		 * 电子标签
		 * @param ciIds
		 * @param request
		 * @return
		 * @create by 20150812
		 */
		@RequestMapping(value = "/thirdDRoomAlarmTagPefData", method = RequestMethod.POST, 
				headers = "Accept=application/json", produces = "application/json;charset=utf-8")
		public @ResponseBody  List<Map<String, Object>>
		thirdDRoomAlarmTagPefData(@RequestBody String ciIds,HttpServletRequest request) {
			
			try {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("resid", ciIds);
				// init返回对象
				Double up, down;
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(); 
				Map<String,Integer> conditionMap = new HashMap<String, Integer>();
				 Map<String, Object> DataVlaueMap = new HashMap<String, Object>(); 
				 Map<String, Object> tempMap = new HashMap<String, Object>(); 
				 Map<String, Object> oldMap = new HashMap<String, Object>();
				 Map<String, Object> tagMap = new HashMap<String, Object>();
				 Map<String, String> valueMap = new HashMap<String, String>();
				// 查询详情
				List<PerfSnapshotRoom> perfDataList = envMonitorServer.queryElectronicTag(params);
				int flag = 0;
				// 遍历查询结果
				for (PerfSnapshotRoom perf : perfDataList) {
					conditionMap.put("moClassId",perf.getMOClassID());
					conditionMap.put("kpiID",perf.getKpiID());
					conditionMap.put("parentMOID",perf.getParentMOID());
					DataVlaueMap = new HashMap<String, Object>(); 
					tempMap = new HashMap<String, Object>(); 
					oldMap= new HashMap<String, Object>(); 
					DataVlaueMap.put("name", perf.getKpiName());
					DataVlaueMap.put("value", perf.getPerfValue());
					DataVlaueMap.put("displayName", perf.getKpiChnName());
					DataVlaueMap.put("categoryName", perf.getDescr()+"-"+perf.getTagID());
					DataVlaueMap.put("accessType", "attr");  
					if(flag==0){
						tagMap.putAll(DataVlaueMap);
						tagMap.put("name","name");
						tagMap.put("value",perf.getTagID());
						tagMap.put("displayName",perf.getDescr()+"名称");
						list.add(tagMap);
					}
					if (perf.getKpiName().equals("TagTemperature")) {
					up = perf.getUpThreshold();
					down = perf.getDownThreshold();
				
					// 获取父类的路径例如1/44/45/52/46，46为本身在前面已查，1不需要查询，所以可以将首尾去掉
					UpAndDownThresholdBean RelationPath = envMonitorServer.queryRelationPath(conditionMap);
					String[] parentClassID = RelationPath.getRelationPath().split("/");
					List<Integer> classIdList = new ArrayList<Integer>();
					for (int i = 0; i < parentClassID.length; i++) {
						classIdList.add(Integer.valueOf(parentClassID[i]));
					}
					classIdList.remove(0);
					classIdList.remove(classIdList.size() - 1);
					if (up == null || down ==null) {
						for (int j = classIdList.size() - 1; j >= 0; j--) {
							String mapKey = perf.getParentMOID() + "_" + perf.getMoID();
							valueMap = new HashMap<String, String>();
							conditionMap.put("upAndDownClass", classIdList.get(j));
							// 查询所有的相关数据
							List<UpAndDownThresholdBean> upAndDown = envMonitorServer.queryAlarmUpAndDownVlaue(conditionMap);
							// 先去查询本身的上一级，如果没有数据，那么再往上一级查询，以此类推。
							for (UpAndDownThresholdBean upAndDownBean : upAndDown) {
								Double upValue = null;
								Double downValue = null;
									if(upAndDownBean.getUpThreshold() !=null){
										 upValue =upAndDownBean.getUpThreshold();
									}
									if(upAndDownBean.getDownThreshold() !=null){
										 downValue  = upAndDownBean.getDownThreshold();
									}
								valueMap.put(upAndDownBean.getSourceMOID() + "_" + upAndDownBean.getMOID(),
										String.valueOf(upValue)+"_"+String.valueOf(downValue));
							}
							if (valueMap.containsKey(mapKey)) {
								String[]  alarmValue = valueMap.get(mapKey).split("_");
								if(alarmValue[0] !=null &&!"null".equals(alarmValue[0]) && up ==null){
									up = Double.valueOf(alarmValue[0]);
								}
								if( alarmValue[1] !=null &&!"null".equals(alarmValue[1])&& down ==null){
									down = Double.valueOf(alarmValue[1]);
								}
							} else {
								// 如果获取当前ParentMOID和MoID不存在，那么去查询ParentMOID和-1的值
								mapKey = perf.getParentMOID() + "_" + "-1";
								if (valueMap.containsKey(mapKey)) {
									String[]  alarmValue = valueMap.get(mapKey).split("_");
									  if(alarmValue[0] !=null &&!"null".equals(alarmValue[0]) && up ==null){
											up = Double.valueOf(alarmValue[0]);
										}
										if(alarmValue[1] !=null &&!"null".equals(alarmValue[1]) && down ==null){
											down = Double.valueOf(alarmValue[1]);
										}
								} else {
									mapKey = "-1_-1";
									if(valueMap.containsKey(mapKey)){
										String[]  alarmValue = valueMap.get(mapKey).split("_");
										if(alarmValue[0] !=null &&!"null".equals(alarmValue[0]) && up ==null){
											up = Double.valueOf(alarmValue[0]);
										}
										if(alarmValue[1] !=null  &&!"null".equals(alarmValue[1]) && down ==null){
											down = Double.valueOf(alarmValue[1]);
										}
									}
								}
							}
							if (up != null || down!=null) {
								break;
							}
						}
					}
						tempMap.putAll(DataVlaueMap);
						oldMap.putAll(DataVlaueMap);
						if(up !=null){
							DataVlaueMap.put("name", perf.getKpiName()+"up");
							DataVlaueMap.put("value", up);
							DataVlaueMap.put("displayName","温度上限值");
						}else{
							DataVlaueMap.clear();
						}
						if(down !=null){
							tempMap.put("value", down);
							tempMap.put("name", perf.getKpiName()+"down");
							tempMap.put("displayName", "温度下限值");
							list.add(tempMap);
						}
						list.add(oldMap);
					}
					else if (perf.getKpiName().equals("TagHumidity")) {
						// 先将自身的上限值分别付给up、down
						up = perf.getUpThreshold();
						down = perf.getDownThreshold();
						conditionMap.put("kpiID",perf.getKpiID());
						// 获取父类的路径例如1/44/45/52/46，46为本身在前面已查，1不需要查询，所以可以将首尾去掉
						UpAndDownThresholdBean RelationPath = envMonitorServer.queryRelationPath(conditionMap);
						String[] parentClassID = RelationPath.getRelationPath().split("/");
						List<Integer> classIdList = new ArrayList<Integer>();
						for(int i=0;i<parentClassID.length;i++){
							classIdList.add(Integer.valueOf(parentClassID[i]));
						}
						classIdList.remove(0);
						classIdList.remove(classIdList.size()-1);
						String mapKeyHum=null;
						if(up==null || down ==null){
							for (int j = classIdList.size() -1; j >=0; j--) {
								 mapKeyHum = perf.getParentMOID() + "_" + perf.getMoID();
								valueMap = new HashMap<String, String>();
								conditionMap.put("upAndDownClass", classIdList.get(j));
								//查询所有的相关数据
								List<UpAndDownThresholdBean> upAndDown = envMonitorServer.queryAlarmUpAndDownVlaue(conditionMap);
								// 先去查询本身的上一级，如果没有数据，那么再往上一级查询，以此类推。
								for (UpAndDownThresholdBean upAndDownBean : upAndDown) {
									Double upValue = null;
									Double downValue = null;
									if(upAndDownBean.getUpThreshold() !=null){
										 upValue =upAndDownBean.getUpThreshold();
									}
									if(upAndDownBean.getDownThreshold() !=null){
										 downValue  = upAndDownBean.getDownThreshold();
									}
								valueMap.put(upAndDownBean.getSourceMOID() + "_" + upAndDownBean.getMOID(),
										String.valueOf(upValue)+"_"+String.valueOf(downValue));
								}
								if(valueMap.containsKey(mapKeyHum)){
									String[]  alarmValue = valueMap.get(mapKeyHum).split("_");
									  if(alarmValue[0] !=null &&!"null".equals(alarmValue[0]) && up==null){
											up = Double.valueOf(alarmValue[0]);
										}
										if(alarmValue[1] !=null &&!"null".equals(alarmValue[1]) && down ==null){
											down = Double.valueOf(alarmValue[1]);
										}
								}else{
									// 如果获取当前ParentMOID和MoID不存在，那么去查询ParentMOID和-1的值
									mapKeyHum = perf.getParentMOID()+"_"+"-1";
									if(valueMap.containsKey(mapKeyHum)){
										String[]  alarmValue = valueMap.get(mapKeyHum).split("_");
										  if(alarmValue[0] !=null &&!"null".equals(alarmValue[0]) && up ==null){
												up = Double.valueOf(alarmValue[0]);
											}
											if(alarmValue[1] !=null &&!"null".equals(alarmValue[1]) && down ==null){
												down = Double.valueOf(alarmValue[1]);
											}
									}else{
										mapKeyHum = "-1_-1";
										if(valueMap.containsKey(mapKeyHum)){
											String[]  alarmValue = valueMap.get(mapKeyHum).split("_");
											if(alarmValue[0] !=null &&!"null".equals(alarmValue[0]) && up ==null){
												up = Double.valueOf(alarmValue[0]);
											}
											if(alarmValue[1] !=null &&!"null".equals(alarmValue[1]) && down ==null){
												down = Double.valueOf(alarmValue[1]);
											}
										}
									}
								}
								if (up != null || down!=null) {
									break;
								}
							}
						}
						tempMap.putAll(DataVlaueMap);
						oldMap.putAll(DataVlaueMap);
						if(up!=null){
							DataVlaueMap.put("value", up);
							DataVlaueMap.put("name", perf.getKpiName()+"up");
							DataVlaueMap.put("displayName","湿度上限值");
						}else{
							DataVlaueMap.clear();
						}
						if(down !=null){
							tempMap.put("value", down);
							tempMap.put("name", perf.getKpiName()+"down");
							tempMap.put("displayName", "湿度下限值");
							list.add(tempMap);
						}
						list.add(oldMap);
					}
					if(DataVlaueMap.size()>0){
						list.add(DataVlaueMap);
					}
					flag++;
				}
				return list;
			} catch (Exception e) {
				logger.error("电子标签性能值出错",e);
			}
			return null;
		}
}