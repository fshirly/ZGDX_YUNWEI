package com.fable.insightview.monitor.database.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.database.entity.MODBMSServerBean;
import com.fable.insightview.monitor.database.entity.MOOracleDataFileBean;
import com.fable.insightview.monitor.database.entity.MOOracleRollSEGBean;
import com.fable.insightview.monitor.database.entity.MOOracleSGABean;
import com.fable.insightview.monitor.database.entity.MOOracleTBSBean;
import com.fable.insightview.monitor.database.entity.OracleDbInfoBean;
import com.fable.insightview.monitor.database.entity.PerfOrclDataFileBean;
import com.fable.insightview.monitor.database.entity.PerfOrclRollbackBean;
import com.fable.insightview.monitor.database.entity.PerfOrclSGABean;
import com.fable.insightview.monitor.database.entity.PerfOrclTBsBean;
import com.fable.insightview.monitor.database.mapper.MOOracleRollSEGMapper;
import com.fable.insightview.monitor.database.mapper.MOOracleSGAMapper;
import com.fable.insightview.monitor.database.mapper.OracleMapper;
import com.fable.insightview.monitor.database.service.IOracleService;
import com.fable.insightview.monitor.host.entity.AlarmActiveDetail;
import com.fable.insightview.monitor.host.entity.HostComm;
import com.fable.insightview.monitor.host.entity.KPINameDef;
import com.fable.insightview.monitor.website.entity.SitePort;
import com.fable.insightview.monitor.website.mapper.WebSiteMapper;
import com.fable.insightview.platform.page.Page;

@Service
public class OracleServiceImpl implements IOracleService {

	@Autowired
	OracleMapper orclMapper;
	@Autowired
	MOOracleSGAMapper oracleSGAMapper;
	@Autowired
	MOOracleRollSEGMapper rollSEGMapper;
	@Autowired 
	WebSiteMapper webSiteMapper;


	private final Logger logger = LoggerFactory
			.getLogger(OracleServiceImpl.class);

	@Override
	public MODBMSServerBean getOrclInstanceDetail(Map map) {
		MODBMSServerBean modbserv = orclMapper.getOrclInstanceDetail(map);
		int period=1;
		long curr=0;
		long currTime=getCurrentDate().getTime();
		try {
			if(modbserv!=null){
					Date updateAlarmTime = modbserv.getUpdateAlarmTime();
					Date collectTime=modbserv.getCollectTime();
					if(modbserv.getDoIntervals()==null || "".equals(modbserv.getDoIntervals())){
						period=modbserv.getDefDoIntervals()*getMultiple()*60000;
					}else{
						period=modbserv.getDoIntervals()*getMultiple()*1000;
					}
					if(collectTime!=null){
						curr=currTime-modbserv.getCollectTime().getTime();
						if(curr<=period){
							if (KPINameDef.up == modbserv.getPerfValueAvai()) {
								modbserv.setOperstatus("up.png");
								modbserv.setOperaTip("UP");
							} else if (KPINameDef.down == modbserv.getPerfValueAvai()) {
								modbserv.setOperstatus("down.png");
								modbserv.setOperaTip("DOWN");
							} else {
								modbserv.setOperstatus("unknown.png");
								modbserv.setOperaTip("未知");
							}
							if (0 == modbserv.getAlarmlevel()
									|| "".equals(modbserv.getAlarmlevel())
									|| modbserv.getAlarmlevel() == null) {
								modbserv.setLevelicon("right.png");
								modbserv.setAlarmLevelName("正常");
							}
							if (updateAlarmTime != null) {
								String durationTime = HostComm.getDurationToTime(currTime-updateAlarmTime.getTime());
								modbserv.setDurationTime(durationTime);
							}else{
								modbserv.setDurationTime("");
							}
						}else{
							modbserv = new MODBMSServerBean();
							modbserv.setOperstatus("unknown.png");
							modbserv.setOperaTip("未知");
							modbserv.setLevelicon("5.png");
							modbserv.setAlarmLevelName("未知");
							String durationTime = HostComm.getDurationToTime(currTime-collectTime.getTime());
							modbserv.setDurationTime(durationTime);
						}
					}else{
						modbserv = new MODBMSServerBean();
						modbserv.setOperstatus("unknown.png");
						modbserv.setOperaTip("未知");
						modbserv.setLevelicon("5.png");
						modbserv.setAlarmLevelName("未知");
						modbserv.setDurationTime("");
					}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return modbserv;
	}

	@Override
	public List<MODBMSServerBean> getOrclTbsInfo(Map map) {
		return orclMapper.getOrclTbsInfo(map);
	}

	@Override
	public List<MODBMSServerBean> getTbsCount(Map map) {
		return orclMapper.getTbsCount(map);
	}

	@Override
	public MOOracleSGABean getOrclSGADetail(Integer MOID) {
		MOOracleSGABean orclSGA = oracleSGAMapper.getOrclSGADetail(MOID);
		if (orclSGA != null) {
			// 转换单位
			String totalSize = HostComm.getBytesToSize(Long.parseLong(orclSGA
					.getTotalSize()));
			orclSGA.setTotalSize(totalSize);

			String fixedSize = HostComm.getBytesToSize(Long.parseLong(orclSGA
					.getFixedSize()));
			orclSGA.setFixedSize(fixedSize);

			String bufferSize = HostComm.getBytesToSize(Long.parseLong(orclSGA
					.getBufferSize()));
			orclSGA.setBufferSize(bufferSize);

			String redologBuf = HostComm.getBytesToSize(Long.parseLong(orclSGA
					.getRedologBuf()));
			orclSGA.setRedologBuf(redologBuf);

			String poolSize = HostComm.getBytesToSize(Long.parseLong(orclSGA
					.getPoolSize()));
			orclSGA.setPoolSize(poolSize);

			String sharedPool = HostComm.getBytesToSize(Long.parseLong(orclSGA
					.getSharedPool()));
			orclSGA.setSharedPool(sharedPool);

			String largePool = HostComm.getBytesToSize(Long.parseLong(orclSGA
					.getLargePool()));
			orclSGA.setLargePool(largePool);

			String javaPool = HostComm.getBytesToSize(Long.parseLong(orclSGA
					.getJavaPool()));
			orclSGA.setJavaPool(javaPool);

			String streamPool = HostComm.getBytesToSize(Long.parseLong(orclSGA
					.getStreamPool()));
			orclSGA.setStreamPool(streamPool);

			String libraryCache = HostComm.getBytesToSize(Long
					.parseLong(orclSGA.getLibraryCache()));
			orclSGA.setLibraryCache(libraryCache);

			String dicaCache = HostComm.getBytesToSize(Long.parseLong(orclSGA
					.getDicaCache()));
			orclSGA.setDicaCache(dicaCache);
		}
		return orclSGA;
	}

	@Override
	public List<MOOracleDataFileBean> getAllDataFiles(Integer moId) {
		List<MOOracleDataFileBean> mofileLst = orclMapper.getAllDataFiles(moId);
		if (mofileLst != null) {
			for (int i = 0; i < mofileLst.size(); i++) {
				String dataFileBytes = HostComm.getBytesToSize(Long
						.parseLong(mofileLst.get(i).getDataFileBytes()));
				mofileLst.get(i).setDataFileBytes(dataFileBytes);
			}
		}
		return mofileLst;
	}

	@Override
	public List<AlarmActiveDetail> getTbsAlarmInfo(Map map) {
		return orclMapper.getTbsAlarmInfo(map);
	}

	@Override
	public MOOracleDataFileBean getDataDetailByMoId(Integer moId) {
		MOOracleDataFileBean moDatafile=orclMapper.getDataDetailByMoId(moId);
		if(moDatafile!=null){
			if(null!=moDatafile.getDataFileBytes() && !"".equals(moDatafile.getDataFileBytes())){
				moDatafile.setDataFileBytes(HostComm.getBytesToSize(Long.parseLong(moDatafile.getDataFileBytes())));
			}else{
				moDatafile.setDataFileBytes("0B");
			}
			if(null!=moDatafile.getMaxSize() && !"".equals(moDatafile.getMaxSize())){
				moDatafile.setMaxSize(HostComm.getBytesToSize(Long.parseLong(moDatafile.getMaxSize())));
			}else{
				moDatafile.setMaxSize("0B");
			}
			if(null!=moDatafile.getUserBytes() && !"".equals(moDatafile.getUserBytes())){
				moDatafile.setUserBytes(HostComm.getBytesToSize(Long.parseLong(moDatafile.getUserBytes())));
			}else{
				moDatafile.setUserBytes("0B");
			}
		}
		return moDatafile;
	}
	
	public int getMultiple(){
		return webSiteMapper.getConfParamValue();
	}
	
	@Override
	public List<MODBMSServerBean> queryList(Page<MODBMSServerBean> page) {
		int period=1;
		long curr=0;
		long currTime=getCurrentDate().getTime();
		List<MODBMSServerBean> orclLst = orclMapper.queryList(page);
		try {
			if(orclLst!=null){
				for (int i = 0; i < orclLst.size(); i++) {
					MODBMSServerBean mo = orclLst.get(i);
					Date updateAlarmTime = mo.getUpdateAlarmTime();
					Date collectTime=mo.getCollectTime();
					if(mo.getDoIntervals()==null || "".equals(mo.getDoIntervals())){
						period=mo.getDefDoIntervals()*getMultiple()*60000;
					}else{
						period=mo.getDoIntervals()*getMultiple()*1000;
					}
					if(collectTime!=null){
						curr=currTime-mo.getCollectTime().getTime();
						if(curr<=period){
							if ("1".equals(mo.getOperstatus())) {
								mo.setOperaTip("UP");
								mo.setOperstatus("up.png");
							} else if ("2".equals(mo.getOperstatus())) {
								mo.setOperaTip("DOWN");
								mo.setOperstatus("down.png");
							} 
							if (updateAlarmTime != null) {
								String durationTime = HostComm.getDurationToTime(currTime-updateAlarmTime.getTime());
								mo.setDurationTime(durationTime);
							}else{
								mo.setDurationTime("");
							}
						}else{
							mo.setOperaTip("未知");
							mo.setOperstatus("unknown.png");
							String durationTime = HostComm.getDurationToTime(currTime-collectTime.getTime());
							mo.setDurationTime(durationTime);
							}
					}else{
						mo.setOperaTip("未知");
						mo.setOperstatus("unknown.png");
						mo.setDurationTime("");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orclLst;
	}

	@Override
	public List<MOOracleRollSEGBean> queryAllOrclRollback(String moId) {
		// TODO Auto-generated method stub
		return rollSEGMapper.queryAllOrclRollback(moId);
	}

	@Override
	public List<PerfOrclRollbackBean> queryOrclRollBackPerf(Map map) {
		// TODO Auto-generated method stub
		return rollSEGMapper.queryOrclRollBackPerf(map);
	}

	@Override
	public MOOracleTBSBean getTbsDetailByMoId(Integer moId) {
		MOOracleTBSBean tbsBean=orclMapper.getTbsDetailByMoId(moId);
		if(tbsBean!=null){
			tbsBean.setInitialextent(HostComm.getBytesToSize(Long.parseLong(tbsBean.getInitialextent())));
			tbsBean.setMinextlen(HostComm.getBytesToSize(Long.parseLong(tbsBean.getMinextlen())));
		}
		return tbsBean;
	}

	@Override
	public MODBMSServerBean getOrclChartAvailability(Map map) {
		return orclMapper.getOrclChartAvailability(map);
	}

	@Override
	public List<MOOracleRollSEGBean> getOrclRollSEGList(Map map) {
		List<MOOracleRollSEGBean> rollSEGList = rollSEGMapper
				.getOrclRollSEGList(map);
		for (int i = 0; i < rollSEGList.size(); i++) {
			// 转换段大小、Extent初始大小两个字段单位
			String segSize = HostComm.getBytesToSize(Long.parseLong(rollSEGList
					.get(i).getSegSize()));
			rollSEGList.get(i).setSegSize(segSize);

			String initialExtent = HostComm.getBytesToSize(Long
					.parseLong(rollSEGList.get(i).getInitialExtent()));
			rollSEGList.get(i).setInitialExtent(initialExtent);
		}
		return rollSEGList;
	}

	@Override
	public MOOracleRollSEGBean getOrclRollSEGDetail(Integer moID) {
		MOOracleRollSEGBean oracleRollSEGBean = rollSEGMapper
				.getRollSEGByMoID(moID);
		if (oracleRollSEGBean != null) {
			String segSize = HostComm.getBytesToSize(Long
					.parseLong(oracleRollSEGBean.getSegSize()));
			oracleRollSEGBean.setSegSize(segSize);

			String initialExtent = HostComm.getBytesToSize(Long
					.parseLong(oracleRollSEGBean.getInitialExtent()));
			oracleRollSEGBean.setInitialExtent(initialExtent);
		}
		return oracleRollSEGBean;
	}

	@Override
	public List<PerfOrclDataFileBean> queryOrclDataFilePerf(Map map) {
		return orclMapper.queryOrclDataFilePerf(map);
	}

	@Override
	public List<MOOracleTBSBean> queryAllOrclTbs(String MOID) {
		return orclMapper.queryAllOrclTbs(MOID);
	}

	@Override
	public List<PerfOrclTBsBean> queryOrclTbsPerf(Map map) {
		return orclMapper.queryOrclTbsPerf(map);
	}

	@Override
	public List<PerfOrclSGABean> queryOrclSGAPerf(Map map) {
		List<PerfOrclSGABean> perfList = oracleSGAMapper.queryOrclSGAPerf(map);
		// 转化单位
		for (int i = 0; i < perfList.size(); i++) {
			double freeMemory = perfList.get(i).getFreeMemory();
			freeMemory = freeMemory / 1024 / 1024;
			BigDecimal bg2 = new BigDecimal(freeMemory);
			freeMemory = bg2.setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
			perfList.get(i).setFreeMemory(freeMemory);
		}
		return perfList;
	}

	@Override
	public MODBMSServerBean selectMoDbmsByPrimaryKey(Integer moId) {
		return orclMapper.selectMoDbmsByPrimaryKey(moId);
	}

	@Override
	public List<MODBMSServerBean> getDBMSServerList(Page<MODBMSServerBean> page) {
		return orclMapper.getDBMSServerList(page);
	}

	@Override
	public MODBMSServerBean getDBMSServerInfo(Integer moId) {
		return orclMapper.getDBMSServerInfo(moId);
	}

	@Override
	public int getDBMSServerByIp(MODBMSServerBean bean) {
		return orclMapper.getDBMSServerByIp(bean);
	}

	@Override
	public MODBMSServerBean getDBMSServerByIpAndType(MODBMSServerBean bean) {
		return orclMapper.getDBMSServerByIpAndType(bean);
	}

	@Override
	public List<OracleDbInfoBean> getOracleDB(Page<OracleDbInfoBean> page) {
		return orclMapper.getOracleDB(page);
	}

	@Override
	public List<MOOracleDataFileBean> getAllOracleDataFiles(
			Page<MOOracleDataFileBean> page) {
		List<MOOracleDataFileBean> mofileLst = orclMapper
				.getAllOracleDataFiles(page);
		if (mofileLst != null) {
			for (int i = 0; i < mofileLst.size(); i++) {
				String dataFileBytes = HostComm.getBytesToSize(Long
						.parseLong(mofileLst.get(i).getDataFileBytes()));
				mofileLst.get(i).setDataFileBytes(dataFileBytes);
			}
		}
		return mofileLst;
	}

	@Override
	public List<MODBMSServerBean> getOracleTbsCount() {
		return orclMapper.getOracleTbsCount();
	}

	@Override
	public List<MOOracleTBSBean> getAllOrclTbsInfo(Page<MOOracleTBSBean> page) {
			List<MOOracleTBSBean> tbsLst = orclMapper.getAllOrclTbsInfo(page);
			try {
			if (tbsLst != null) {
				for (int i = 0; i < tbsLst.size(); i++) {
					if (tbsLst.get(i).getInitialextent() != null
							&&  !"".equals(tbsLst.get(i).getInitialextent())) {
						tbsLst.get(i).setInitialextent(
								HostComm.getBytesToSize(Long.parseLong(tbsLst
										.get(i).getInitialextent())));
					} else {
						tbsLst.get(i).setInitialextent("");
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return tbsLst;
	}

	@Override
	public List<MOOracleRollSEGBean> getAllOrclRollSEGList(
			Page<MOOracleRollSEGBean> page) {
		List<MOOracleRollSEGBean> rollSEGList = rollSEGMapper
				.getAllOrclRollSEGList(page);
		for (int i = 0; i < rollSEGList.size(); i++) {
			// 转换段大小、Extent初始大小两个字段单位
			String segSize = HostComm.getBytesToSize(Long.parseLong(rollSEGList
					.get(i).getSegSize()));
			rollSEGList.get(i).setSegSize(segSize);

			String initialExtent = HostComm.getBytesToSize(Long
					.parseLong(rollSEGList.get(i).getInitialExtent()));
			rollSEGList.get(i).setInitialExtent(initialExtent);
		}
		return rollSEGList;
	}

	@Override
	public List<MODBMSServerBean> getOrclInstanceList(
			Page<MODBMSServerBean> page) {
		List<MODBMSServerBean> orclInsList = orclMapper.getOrclInstanceList(page);
		if (orclInsList.size() > 0) {
			for (int i = 0; i < orclInsList.size(); i++) {
				orclInsList.get(i).setTotalsize(HostComm.getBytesToSize(Double.parseDouble(orclInsList.get(i).getTotalsize())));
				orclInsList.get(i).setFreesize(HostComm.getBytesToSize(Double.parseDouble(orclInsList.get(i).getFreesize())));
			}
		}
		
		return orclInsList;
	}

	@Override
	public List<MOOracleSGABean> getOrclSGAList(Page<MOOracleSGABean> page) {
		List<MOOracleSGABean> list = oracleSGAMapper.getOrclSGAList(page);
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {

				// 转换单位
				String totalSize = HostComm.getBytesToSize(Long.parseLong(list
						.get(i).getTotalSize()));
				list.get(i).setTotalSize(totalSize);

				String fixedSize = HostComm.getBytesToSize(Long.parseLong(list
						.get(i).getFixedSize()));
				list.get(i).setFixedSize(fixedSize);

				String bufferSize = HostComm.getBytesToSize(Long.parseLong(list
						.get(i).getBufferSize()));
				list.get(i).setBufferSize(bufferSize);

				String redologBuf = HostComm.getBytesToSize(Long.parseLong(list
						.get(i).getRedologBuf()));
				list.get(i).setRedologBuf(redologBuf);

				String poolSize = HostComm.getBytesToSize(Long.parseLong(list
						.get(i).getPoolSize()));
				list.get(i).setPoolSize(poolSize);

				String sharedPool = HostComm.getBytesToSize(Long.parseLong(list
						.get(i).getSharedPool()));
				list.get(i).setSharedPool(sharedPool);

				String largePool = HostComm.getBytesToSize(Long.parseLong(list
						.get(i).getLargePool()));
				list.get(i).setLargePool(largePool);

				String javaPool = HostComm.getBytesToSize(Long.parseLong(list
						.get(i).getJavaPool()));
				list.get(i).setJavaPool(javaPool);

				String streamPool = HostComm.getBytesToSize(Long.parseLong(list
						.get(i).getStreamPool()));
				list.get(i).setStreamPool(streamPool);

				String libraryCache = HostComm.getBytesToSize(Long
						.parseLong(list.get(i).getLibraryCache()));
				list.get(i).setLibraryCache(libraryCache);

				String dicaCache = HostComm.getBytesToSize(Long.parseLong(list
						.get(i).getDicaCache()));
				list.get(i).setDicaCache(dicaCache);

			}
		}

		return list;
	}

	@Override
	public MOOracleDataFileBean getDataFileByMoId(Integer moId) {
		return orclMapper.getDataFileByMoId(moId);
	}

	@Override
	public MOOracleSGABean getOrclSGAByMoID(int moID) {
		return oracleSGAMapper.getOrclSGAByMoID(moID);
	}

	@Override
	public OracleDbInfoBean getOracleDbByMoId(int moId) {
		return orclMapper.getOracleDbByMoId(moId);
	}

	@Override
	public MODBMSServerBean getOrclInstanceByMoid(Integer moId) {
		return orclMapper.getOrclInstanceByMoid(moId);
	}

	@Override
	public int updateDBMSServerMOClassID(MODBMSServerBean bean) {
		return orclMapper.updateDBMSServerMOClassID(bean);
	}

	@Override
	public Integer getInsIdBymoId(int moId) {
		return orclMapper.getInsIdBymoId(moId);
	}

	@Override
	public List<MODBMSServerBean> queryAll() {
		return orclMapper.queryAll();
	}

	@Override
	public Date getCurrentDate() {
		return orclMapper.getCurrentDate();
	}

	@Override
	public MODBMSServerBean getDBMSServerByIpAndTypeAlias(MODBMSServerBean bean) {
		return orclMapper.getDBMSServerByIpAndTypeAlias(bean);
	}
}
