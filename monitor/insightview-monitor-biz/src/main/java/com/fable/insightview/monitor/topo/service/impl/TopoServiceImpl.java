package com.fable.insightview.monitor.topo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.page.Page;
import com.fable.insightview.monitor.database.entity.Db2InfoBean;
import com.fable.insightview.monitor.database.entity.MODBMSServerBean;
import com.fable.insightview.monitor.database.entity.MOMySQLDBServerBean;
import com.fable.insightview.monitor.database.mapper.Db2Mapper;
import com.fable.insightview.monitor.database.mapper.MsServerMapper;
import com.fable.insightview.monitor.database.mapper.MySQLMapper;
import com.fable.insightview.monitor.database.mapper.OracleMapper;
import com.fable.insightview.monitor.database.mapper.SyBaseMapper;
import com.fable.insightview.monitor.host.entity.HostComm;
import com.fable.insightview.monitor.mobject.entity.MObjectDefBean;
import com.fable.insightview.monitor.topo.entity.MOSubnetBean;
import com.fable.insightview.monitor.topo.entity.TopoBean;
import com.fable.insightview.monitor.topo.entity.TopoLink;
import com.fable.insightview.monitor.topo.mapper.TopoMapper;
import com.fable.insightview.monitor.topo.service.ITopoService;
import com.fable.insightview.platform.dict.util.DictionaryLoader;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.manufacturer.dao.IManufacturerDao;
import com.fable.insightview.platform.manufacturer.entity.ManufacturerInfoBean;

@Service
public class TopoServiceImpl implements ITopoService {
	private static final Logger log = LoggerFactory.getLogger(TopoServiceImpl.class);
	private static final int MysqlServer = 28;
	private static final int OracleInstanse = 26;
	private static final int Db2Instance = 55;
	private static final int Db2Db = 56;
	private static final int SybaseServer = 82;
	private static final int MsSqlServer = 87;
	
	@Autowired
	TopoMapper topoMapper;
	@Autowired
	IManufacturerDao manufacturerDao;
	@Autowired
	MySQLMapper mySQLMapper;
	@Autowired
	OracleMapper oracleMapper;
	@Autowired
	Db2Mapper db2Mapper;
	@Autowired
	SyBaseMapper sybaseMapper;
	@Autowired
	MsServerMapper msServerMapper;

	/**
	 * 拓扑图列表页面显示数据
	 */
	@Override
	public List<TopoBean> selectTopo(Page<TopoBean> page) {
		List<TopoBean> topolList = topoMapper.selectTopo(page);

		// 获得数据字典中视图级别名称
		Map<Integer, String> toMap = DictionaryLoader
				.getConstantItems("TopoLevel");
		for (TopoBean to : topolList) {
			to.setTopoLevelName(toMap.get(to.getTopoLevel()));
		}
		return topolList;
	}

	/**
	 * 根据id获得信息
	 */
	@Override
	public TopoBean getTopoByID(int id) {
		TopoBean topo = topoMapper.getTopoByID(id);
		if (topo != null) {
			Map<Integer, String> toMap = DictionaryLoader
					.getConstantItems("TopoLevel");
			topo.setTopoLevelName(toMap.get(topo.getTopoLevel()));
			if(topo.getAlarmLevel() == null){
				topo.setAlarmLevelName("正常");
			}
		}
		return topo;
	}
		
	@Override
	public List<TopoBean> loadTopoView(Map<String,String> param) {
		return topoMapper.getTopoViewList(param);
	}

	@Override
	public long getIPByStr(String strIp) {
		if (strIp.indexOf(".") <= 0) {
			return 0;
		}
		
        long[] ip = new long[4];
        int position1 = strIp.indexOf(".");
        int position2 = strIp.indexOf(".", position1 + 1);
        int position3 = strIp.indexOf(".", position2 + 1);
        
        //.换成整型
        ip[0] = Long.parseLong(strIp.substring(0, position1));
        ip[1] = Long.parseLong(strIp.substring(position1+1, position2));
        ip[2] = Long.parseLong(strIp.substring(position2+1, position3));
        ip[3] = Long.parseLong(strIp.substring(position3+1));
        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] <<  8 ) + ip[3]; 
	}

	@Override
	public List<ManufacturerInfoBean> loadManufacturer(ManufacturerInfoBean manufacturerInfoBean,	FlexiGridPageInfo flexiGridPageInfo) {
		return manufacturerDao.getManufacturerInfoBeanByConditions(
				manufacturerInfoBean, flexiGridPageInfo);
	}

	@Override
	public List<Map<String, Object>> operateTopo(TopoBean topo,String operateType) {
		int operateResult = 1;
		String operateResultDescr = "success";
		//删除
		if("3".equals(operateType)){
			try {
				topoMapper.delTopoByID(topo.getIds());
				operateResult = 1;  //删除成功
				operateResultDescr = "success";
			} catch (Exception e) {
				operateResult = 2; //删除失败
				operateResultDescr = "";
				log.error("删除topo表失败："+e);
			}
		}
		//新增
		else if("1".equals(operateType)){
			try {
				topoMapper.insertTopo(topo);
				operateResult = 1;  //新增成功
				operateResultDescr = "success";
				topo.setIds(topo.getId()+"");
			} catch (Exception e) {
				operateResult = 2; //新增失败
				operateResultDescr = "";
				log.error("新增topo表失败："+e);
			}
		}
		
		//更新
		else if("2".equals(operateType)){
			try {
				topoMapper.updateTopo(topo);
				operateResult = 1;  //更新成功
				operateResultDescr = "success";
			} catch (Exception e) {
				operateResult = 2; //更新失败
				operateResultDescr = "";
				log.error("更新topo表失败："+e);
			}
		}
		
		// 初始化返回json 
		List<Map<String, Object>> topoDetail = new ArrayList<Map<String, Object>>();
		Map<String, Object> detail = new HashMap<String, Object>();
		detail.put("viewID", topo.getIds());
		detail.put("operateResult", operateResult);
		detail.put("operateResultDescr", operateResultDescr); 
		topoDetail.add(detail);
		return topoDetail;
	}

	@Override
	public List<TopoLink> getTopoLink(Map<String, String> param) {
		return topoMapper.getTopoLink(param);
	}

	@Override
	public List<MOSubnetBean> querySubNetList(Map<String, String> param) {
		return topoMapper.querySubNetList(param);
	}

	@Override
	public List<MOSubnetBean> getSubnetByIP(String subnetIp) {
		return topoMapper.getSubnetByIP(subnetIp);
	}

	@Override
	public boolean checkTopoName(Map paramMap) {
		List<TopoBean> topoLst = topoMapper.getByNameAndID(paramMap);
		if(topoLst.size() > 0){
			return false;
		}
		return true;
	}

	@Override
	public List<Map<String, Object>> getDbList(int classId,
			Map<String, Object> paramMap) {
		// 初始化返回json
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if(classId == MysqlServer){
			List<MOMySQLDBServerBean> mysqlServerLst = mySQLMapper.getMySQLDBServer(paramMap);
			if (mysqlServerLst != null) {
				for (int i = 0; i < mysqlServerLst.size(); i++) {
					MOMySQLDBServerBean vo = mysqlServerLst.get(i);
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("moID", vo.getMoId());
					map.put("serverName", vo.getServerName());
					map.put("ip", vo.getIp());
					map.put("port", vo.getPort());
					map.put("moName", vo.getMoName());
					map.put("startTime", vo.getStartTime());
					map.put("serverCharset", vo.getServerCharset());
					map.put("dbNum", vo.getDbNum());
					map.put("dbmsMoId", vo.getDbmsMoid());
					map.put("alias", vo.getMoAlias());
					map.put("classId", classId);
					list.add(map);
				}
			}
		}
		else if(classId == OracleInstanse){
			List<MODBMSServerBean> orclInsList = oracleMapper.getOrclInsList(paramMap);
			if(orclInsList != null){
				for (MODBMSServerBean  mo: orclInsList) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("moID", mo.getMoid());
					map.put("serverName", mo.getInstancename());
					map.put("ip", mo.getIp());
					map.put("port", mo.getPort());
					map.put("deviceip", mo.getDeviceip());
					map.put("formatTime", mo.getFormatTime());
					map.put("freesize", HostComm.getBytesToSize(Double.parseDouble(mo.getFreesize())));
					map.put("totalsize", HostComm.getBytesToSize(Double.parseDouble(mo.getTotalsize())));
					map.put("installpath", mo.getInstallpath());
					map.put("dbmsMoId", mo.getDbmsMoId());
					map.put("alias", mo.getMoalias());
					map.put("classId", classId);
					list.add(map);
				}
			}
		}
		else if(classId == Db2Instance){
			List<MODBMSServerBean> db2InsList = db2Mapper.getDb2InsList(paramMap);
			if(db2InsList != null){
				for (MODBMSServerBean  mo: db2InsList) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("moID", mo.getMoid());
					map.put("serverName", mo.getInstancename());
					map.put("ip", mo.getIp());
					map.put("port", mo.getPort());
					map.put("deviceip", mo.getDeviceip());
					map.put("formatTime", mo.getFormatTime());
					map.put("nodeNum", mo.getNodeNum());
					map.put("dbmsMoId", mo.getDbmsMoId());
					map.put("alias", mo.getMoalias());
					map.put("classId", classId);
					list.add(map);
				}
			}
		}
		else if(classId == Db2Db){
			List<Db2InfoBean> db2DbInsList = db2Mapper.getDb2DBInfoList(paramMap);
			if(db2DbInsList != null){
				for (Db2InfoBean  mo: db2DbInsList) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("moID", mo.getMoId());
					map.put("databaseName", mo.getDatabaseName());
					map.put("instanceMoId", mo.getInstanceMOID());
					map.put("serverName", mo.getInstanceName());
					map.put("ip", mo.getIp());
					map.put("port", mo.getPort());
					map.put("databasePath", mo.getDatabasePath());
					map.put("formatTime", mo.getFormatTime());
					map.put("databaseStatus", mo.getDatabaseStatus());
					map.put("alias", mo.getMoalias());
					map.put("classId", classId);
					list.add(map);
				}
			}
		}
		else if(classId == SybaseServer || classId == MsSqlServer){
			List<MOMySQLDBServerBean> serverList = new ArrayList<MOMySQLDBServerBean>();
			if(classId == SybaseServer){
				serverList = sybaseMapper.getSybaseServerList(paramMap);
			}else if(classId == MsSqlServer){
				serverList = msServerMapper.getMsSQLServer(paramMap);
			}
			if(serverList != null){
				for (MOMySQLDBServerBean  mo: serverList) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("moID", mo.getMoId());
					map.put("serverName", mo.getServerName());
					map.put("ip", mo.getIp());
					map.put("port", mo.getPort());
					map.put("moName", mo.getMoName());
					map.put("startTime", mo.getStartTime());
					map.put("dbmsMoId", mo.getDbmsMoid());
					map.put("alias", mo.getMoAlias());
					map.put("classId", classId);
					list.add(map);
				}
			}
		}
		return list;
	}

	@Override
	public String getDB2InfoMoIds(int instanceMoId) {
		List<Db2InfoBean> db2Lst = db2Mapper.getDb2InfoByInstanceMoId(instanceMoId);
		String ids = "";
		for (int i = 0; i < db2Lst.size(); i++) {
			ids += db2Lst.get(i).getMoId() + ",";
		}
		return ids;
	}
}