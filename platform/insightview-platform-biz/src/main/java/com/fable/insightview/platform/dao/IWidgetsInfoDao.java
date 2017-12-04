package com.fable.insightview.platform.dao;

import java.util.List;

import com.fable.insightview.platform.entity.WidgetsInfoBean;
import com.fable.insightview.platform.itsm.core.dao.GenericDao;

public interface IWidgetsInfoDao extends GenericDao<WidgetsInfoBean> {
	List<WidgetsInfoBean> getWidgetsInfoLst(WidgetsInfoBean widgetsInfoBean, int userid);
}
