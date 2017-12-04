package com.fable.insightview.platform.datadumpconfig.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.datadumpconfig.entity.DataDumpConfigBean;
import com.fable.insightview.platform.datadumpconfig.entity.SysDumpBean;
import com.fable.insightview.platform.datadumpconfig.mapper.SysDumpMapper;
import com.fable.insightview.platform.datadumpconfig.service.IDataDumpConfigService;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.sysconf.entity.SysConfig;
import com.fable.insightview.platform.sysconf.mapper.SysConfigMapper;

@Service
public class DataDumpConfigServiceImpl implements IDataDumpConfigService {
	private static final int DATADUMPTYPE = 201;
	private static final String ORIGINALDATARETENTIONTIME = "originalDataRetentionTime";
	private static final String DUMPDATARETENTIONTIME = "dumpDataRetentionTime";
	private static final String EXECUTETIME = "executeTime";
	private Logger logger = LoggerFactory
			.getLogger(DataDumpConfigServiceImpl.class);
	@Autowired
	SysConfigMapper sysConfigMapper;
	@Autowired
	SysDumpMapper sysDumpMapper;

	@Override
	public boolean addConfig(SysConfig sysConfig) {
		try {
			sysConfigMapper.insertConfig(sysConfig);
			return true;
		} catch (Exception e) {
			logger.error("新增系统配置异常：", e);
		}
		return false;
	}

	@Override
	public DataDumpConfigBean initDataDumpConfig() {
		List<SysConfig> sysConfigLst = sysConfigMapper
				.getConfByTypeID(DATADUMPTYPE);
		Map<String, String> result = new HashMap<String, String>();
		for (SysConfig sys : sysConfigLst) {
			result.put(sys.getParaKey(), sys.getParaValue());
		}
		DataDumpConfigBean bean = new DataDumpConfigBean();
		if (sysConfigLst.size() > 0) {
			bean.setOriginalDataRetentionTime(Integer.parseInt(result.get(
					ORIGINALDATARETENTIONTIME).toString()));
			bean.setDumpDataRetentionTime(Integer.parseInt(result.get(
					DUMPDATARETENTIONTIME).toString()));
			bean.setExecuteTime(result.get(EXECUTETIME));
		}
		return bean;
	}

	@Override
	public boolean doSetDataDumpConfig(DataDumpConfigBean bean) {
		try {
		//删除之前的配置
		sysConfigMapper.delByType(DATADUMPTYPE);
		//新增最新的配置
		SysConfig config = new SysConfig();
		config.setType(DATADUMPTYPE);
		config.setParaKey(ORIGINALDATARETENTIONTIME);
		config.setParaValue(bean.getOriginalDataRetentionTime().toString());
		int i = sysConfigMapper.insertConfig(config);
		
			if (i > 0) {
				config.setParaKey(DUMPDATARETENTIONTIME);
				config.setParaValue(bean.getDumpDataRetentionTime().toString());
				int j = sysConfigMapper.insertConfig(config);
				if (j > 0) {
					config.setParaKey(EXECUTETIME);
					config.setParaValue(bean.getExecuteTime().toString());
					sysConfigMapper.insertConfig(config);
				}
			}
			return true;
		} catch (Exception e) {
			logger.error("数据转储配置异常：", e);
		}
		return false;
	}

	@Override
	public Map<String, Object> listDump(Page<SysDumpBean> page) {
		List<SysDumpBean> dumpLst = sysDumpMapper.selectDump(page);
		int total = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", dumpLst);
		return result;
	}

	@Override
	public boolean delDump(int id) {
		try {
			sysDumpMapper.delById(id);
			return true;
		} catch (Exception e) {
			logger.error("删除转储异常：",e);
		}
		return false;
	}

	@Override
	public boolean delDumps(String ids) {
		try {
			sysDumpMapper.delByIds(ids);
			return true;
		} catch (Exception e) {
			logger.error("批量删除转储异常：",e);
		}
		return false;
	}

	@Override
	public boolean isExist(SysDumpBean bean) {
		List<SysDumpBean> dunmpLst = sysDumpMapper.getByCondition(bean);
		if(dunmpLst.size() > 0){
			return false;
		}
		return true;
	}

	@Override
	public boolean addDataDump(SysDumpBean bean) {
		try {
			sysDumpMapper.insertDump(bean);
			return true;
		} catch (Exception e) {
			logger.error("新增转储表信息异常：" , e);
		}
		return false;
	}

	@Override
	public SysDumpBean initDumpDetail(int id) {
		return sysDumpMapper.getByID(id);
	}

	@Override
	public boolean updateDataDump(SysDumpBean bean) {
		try {
			sysDumpMapper.updateDumpByID(bean);
			return true;
		} catch (Exception e) {
			logger.error("更新转储表信息异常：" , e);
		}
		return false;
	}

}
