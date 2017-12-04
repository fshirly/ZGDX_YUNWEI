package com.fable.insightview.platform.dao;

import java.util.List;
import java.util.Map;

import com.fable.insightview.platform.entity.Dict;
import com.fable.insightview.platform.entity.SysConstantTypeBean;
import com.fable.insightview.platform.itsm.core.dao.GenericDao;

public interface DictDao extends GenericDao<Dict> {
	
	public List<Dict> getItemsById(Long id);
	public SysConstantTypeBean getTypeBeanById(String typeName);
	public Dict getItemsById(Long typeId,Long itemId);
	public List<SysConstantTypeBean> getTypeList();
	public void loadConstant(Map<String, Map<Integer,String>> constantMap);
}
