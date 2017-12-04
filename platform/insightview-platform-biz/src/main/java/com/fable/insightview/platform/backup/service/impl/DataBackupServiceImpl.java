package com.fable.insightview.platform.backup.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fable.insightview.platform.GuzhangrizManager.entity.GuzhangrizManager;
import com.fable.insightview.platform.GuzhangrizManager.service.GuzhangrizManagerService;
import com.fable.insightview.platform.backup.db.DbInfo;
import com.fable.insightview.platform.backup.db.DbInter;
import com.fable.insightview.platform.backup.db.JdbcUtils;
import com.fable.insightview.platform.backup.service.DataBackupService;
import com.fable.insightview.platform.sysconf.entity.SysConfig;
import com.fable.insightview.platform.sysconf.mapper.SysConfigMapper;
@Service
public class DataBackupServiceImpl implements DataBackupService {

	@Autowired
	private SysConfigMapper sysConfigMapper;
	
	@Autowired
	private GuzhangrizManagerService  guzhangrizManagerService;
	
	@Override
	public Map<String, Object> restoreDate(GuzhangrizManager data) {
		Map<String, Object> res;
		DbInfo hostDB = getDbInfo(DbInter.KEY.host);
		DbInfo bakDB = getDbInfo(DbInter.KEY.bak);
		String sql = getQuerySql(data.getTableName());
		data.setDataRestoreStartTime(new Date());
		ArrayList<HashMap<String, String>> bakDatas = JdbcUtils.getTableData(sql,bakDB); //备份表中的数据
		res = JdbcUtils.insertDataToSql(bakDatas, data.getTableName(),hostDB);
		data.setDataRestoreEndTime(new Date());
		data.setDataRestoreTotal(res.get("successTol") != null ? (int)res.get("successTol") : 0);
		data.setDataRestoreResult((boolean) res.get("success") ? "1" : "2");
		guzhangrizManagerService.updateBackupRecode(data);
		return res;
	}
	
	public String getQuerySql(String tableName) {
		return "SELECT * FROM "+tableName;
	}
	/**
	 * 封装db信息
	 * @param typeID
	 * @return
	 */
	public DbInfo getDbInfo(int typeID) {
		DbInfo dbInfo = null;
		List<SysConfig> sysConfigs = sysConfigMapper.getConfByTypeID(typeID);
		if (!CollectionUtils.isEmpty(sysConfigs)) {
			dbInfo = new DbInfo();
			for (SysConfig sysConfig : sysConfigs) {
				if ("url".equals(sysConfig.getParaKey())) {
					dbInfo.setUrl(sysConfig.getParaValue());
				}
				if ("userName".equals(sysConfig.getParaKey())) {
					dbInfo.setUserName(sysConfig.getParaValue());
				}
				if ("password".equals(sysConfig.getParaKey())) {
					dbInfo.setPassword(sysConfig.getParaValue());
				}
			}
		}
		return dbInfo;
	}
	
}
