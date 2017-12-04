package com.fable.insightview.platform.dao;

import java.util.List;

import com.fable.insightview.platform.entity.FdSysValidateRule;
import com.fable.insightview.platform.itsm.core.dao.GenericDao;

/**
 * 
 * @author chengdawei
 *
 */
public interface IFdSysValidateRuleDao extends GenericDao<FdSysValidateRule> {

	/**
	 * 查询校验规则
	 * @return
	 */
	public List<FdSysValidateRule> queryAllList();
}
