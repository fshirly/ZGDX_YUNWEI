package com.fable.insightview.monitor.discover.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map; 

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fable.insightview.monitor.discover.entity.SynchronResResult;
import com.fable.insightview.monitor.discover.mapper.MODeviceMapper;
import com.fable.insightview.monitor.discover.mapper.SynchronResResultMapper;
import com.fable.insightview.monitor.environmentmonitor.service.IEnvMonitorService;
import com.fable.insightview.monitor.mocpus.mapper.MOCPUsMapper;
import com.fable.insightview.monitor.momemories.mapper.MOMemoriesMapper;
import com.fable.insightview.monitor.monetworkIif.mapper.MONetworkIfMapper;
import com.fable.insightview.monitor.mostorage.mapper.MOStorageMapper;
import com.fable.insightview.monitor.movolumes.mapper.MOVolumesMapper;
import com.fable.insightview.monitor.website.mapper.WebSiteMapper;

@Controller
@RequestMapping("/rest/cmdb/monitor")
public class RestInterfaceController {

	private static final Logger log = LoggerFactory.getLogger(RestInterfaceController.class);

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
	MOStorageMapper moStorageMapper;
	@Autowired
	WebSiteMapper webSiteMapper;

	/**
	 * 接收资源ID与监测对象映射 
	 * @param ciIds 设备的ID
	 * @param request 请求对象
	 * @return 返回告警数据
	 */
	@RequestMapping(value = "/SyncResAndOMapping", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	String acceptResAndOMapping(@RequestBody List<Map<String, Object>> jsonObj) {
		try {
			log.info("----开始接收资源ID与监测对象映射----"); 
			log.info("jsonObj tostr=" + jsonObj.toString());
			// 资源ID,资源类型,MOID,MO类型,操作类型,处理结果,错误信息
			SynchronResResult result = null;
			Map<String, Object> param = null;
			for (Map<String, Object> obj : jsonObj) {
				result = new SynchronResResult();
				param = new HashMap<String, Object>();

				// 如果转换出错,直接返回
				try {
					if (obj.get("errorMessage") != null
							&& !obj.get("errorMessage").toString().equals("null")) {
						result.setErrorDescr(obj.get("errorMessage").toString());
					}

					if (obj.get("moId") != null
							&& !obj.get("moId").toString().equals("null")) {
						result.setMoID(Integer.parseInt(obj.get("moId").toString()));
					}

					if (obj.get("resTypeId") != null
							&& !obj.get("resTypeId").toString().equals("null")) {
						result.setResTypeID(Integer.parseInt(obj.get("resTypeId").toString()));
					}
					
					result.setExecFlag(Integer.parseInt(obj.get("success").toString()));
					result.setMoClassID(Integer.parseInt(obj.get("moTypeId").toString()));
					
					String resId = obj.get("resId").toString();
					//如果返回结束小于等于0,不更新resID
					if (Integer.parseInt(resId) > 0) {
						result.setResID(Integer.parseInt(resId));
					}
				} catch (Exception e) {
					// saveOPLog
					synchronResResultMapper.insert(result);
					continue;
				}
				
				// saveOPLog
				synchronResResultMapper.insert(result);
				
				// changeMapping
				param.put("resid", result.getResID());
				param.put("moid", result.getMoID());
				if (result.getMoClassID() == 5 
						|| result.getMoClassID() == 6
						|| result.getMoClassID() == 59
						|| result.getMoClassID() == 60
						|| result.getMoClassID() == 7
						|| result.getMoClassID() == 8
						|| result.getMoClassID() == 9) {
					// 更新设备表
					moDeviceMapper.updateResId(param);
				} else if (result.getMoClassID() == 10) {
					moNetworkIfMapper.updateResId(param);
				} else if (result.getMoClassID() == 11) {
					moVolumesMapper.updateResId(param);
				} else if (result.getMoClassID() == 12) {
					moCPUsMapper.updateResId(param);
				} else if (result.getMoClassID() == 13) {
					moMemoryMapper.updateResId(param);
				} else if (result.getMoClassID() >= 46
						&& result.getMoClassID() <= 51) {
					envMonitorServer.updateTagResId(param);
				} else if (result.getMoClassID() == 45) {
					envMonitorServer.updateReaderResId(param);
				} else if (result.getMoClassID() == 85) {
					moStorageMapper.updateResId(param);
				}else if(result.getMoClassID() == 93){
					webSiteMapper.updateWebSiteHttpId(param);
				}else if(result.getMoClassID() == 94){
					webSiteMapper.updateWebSiteTcpId(param);
				}
			}
			return "success";
		} catch (Exception e) {
			log.info("SyncResAndOMapping error", e);
			return "failed";
		}
	}
}