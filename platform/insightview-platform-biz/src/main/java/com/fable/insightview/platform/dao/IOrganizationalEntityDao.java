package com.fable.insightview.platform.dao;

import java.util.List;

import com.fable.insightview.platform.entity.OrganizationalEntityBean;
import com.fable.insightview.platform.itsm.core.dao.GenericDao;

public interface IOrganizationalEntityDao extends
		GenericDao<OrganizationalEntityBean> {
	boolean addOrganization(OrganizationalEntityBean organizationalEntityBean);
	List<OrganizationalEntityBean> getAllOrganizationalEntity();
}
