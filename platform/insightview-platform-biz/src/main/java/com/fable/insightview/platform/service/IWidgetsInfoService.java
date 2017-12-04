package com.fable.insightview.platform.service;

import java.util.List;

import com.fable.insightview.platform.entity.WidgetsInfoBean;

public interface IWidgetsInfoService {
	List<WidgetsInfoBean> getWidgetsInfoLst(WidgetsInfoBean widgetsInfoBean, int userid);
}
