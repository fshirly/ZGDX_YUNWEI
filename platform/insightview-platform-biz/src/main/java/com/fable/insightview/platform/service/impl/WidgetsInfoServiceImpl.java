package com.fable.insightview.platform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.dao.IWidgetsInfoDao;
import com.fable.insightview.platform.entity.WidgetsInfoBean;
import com.fable.insightview.platform.service.IWidgetsInfoService;

/**
 * 单位组织Service
 * 
 * @author 武林
 * 
 */
@Service("widgetsInfoService")
public class WidgetsInfoServiceImpl implements IWidgetsInfoService {

	@Autowired
	protected IWidgetsInfoDao widgetsInfoDao;

	@Override
	public List<WidgetsInfoBean> getWidgetsInfoLst(
			WidgetsInfoBean widgetsInfoBean, int userid) {
		return widgetsInfoDao.getWidgetsInfoLst(widgetsInfoBean, userid);

	}
}
