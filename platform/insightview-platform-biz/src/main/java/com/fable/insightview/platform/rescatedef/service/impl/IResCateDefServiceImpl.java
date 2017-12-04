package com.fable.insightview.platform.rescatedef.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.rescatedef.dao.IResCateDefDao;
import com.fable.insightview.platform.rescatedef.entity.ResCateDefInfo;
import com.fable.insightview.platform.rescatedef.service.IResCateDefService;
@Service("ResCateDefService")
public class IResCateDefServiceImpl implements IResCateDefService {

	@Autowired
	protected IResCateDefDao resCateDefDao;
	
	@Override
	public void addInfo(ResCateDefInfo vo) {
		 resCateDefDao.addInfo(vo);
	}

	@Override
	public void deleteInfo(ResCateDefInfo vo) {
		 resCateDefDao.deleteInfo(vo);
	}

	@Override
	public ResCateDefInfo getInfoById(int id) {
		return resCateDefDao.getInfoById(id);
	}

	@Override
	public void updateInfo(ResCateDefInfo vo) {
		 resCateDefDao.updateInfo(vo);
	}

	@Override
	public void deleteBathInfo(String id) {
		resCateDefDao.deleteBathInfo(id);
	}

	@Override
	public FlexiGridPageInfo queryPage(ResCateDefInfo vo,
			FlexiGridPageInfo flexiGridPageInfo) {
		// TODO Auto-generated method stub
		return resCateDefDao.queryPage(vo, flexiGridPageInfo);
	}

	@Override
	public String getCanDelId(String id) {
		// TODO Auto-generated method stub
		return resCateDefDao.getCanDelId(id);
	}


}
