package com.fable.insightview.platform.restypedef.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.restypedef.dao.IResTypeDefDao;
import com.fable.insightview.platform.restypedef.entity.ResTypeDefineInfo;
import com.fable.insightview.platform.restypedef.service.IResTypeDefService;

@Service("ResTypeDefService")
public class ResTypeDefServiceImpl implements IResTypeDefService {
	
	@Autowired
	protected IResTypeDefDao resTypeDefDao;
	
	@Override
	public void addInfo(ResTypeDefineInfo vo) {
		 resTypeDefDao.addInfo(vo);
	}

	@Override
	public void deleteInfo(ResTypeDefineInfo vo) {
		 resTypeDefDao.deleteInfo(vo);
	}

	@Override
	public ResTypeDefineInfo getInfoById(int id) {
		return resTypeDefDao.getInfoById(id);
	}

	@Override
	public void updateInfo(ResTypeDefineInfo vo) {
		 resTypeDefDao.updateInfo(vo);
	}

	@Override
	public List<ResTypeDefineInfo> getResTypeDefineTree(String paramName,String paramValue) {
		List<ResTypeDefineInfo> list = resTypeDefDao.getResTypeDefineTree(paramName, paramValue);
		return list;
	}

	@Override
	public void deleteBathInfo(String id) {
		resTypeDefDao.deleteBathInfo(id);
	}

	@Override
	public FlexiGridPageInfo queryPage(ResTypeDefineInfo vo,
			FlexiGridPageInfo flexiGridPageInfo) {
		return resTypeDefDao.queryPage(vo, flexiGridPageInfo);
	}

}
