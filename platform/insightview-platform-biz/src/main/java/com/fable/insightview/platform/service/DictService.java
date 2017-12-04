package com.fable.insightview.platform.service;

import java.util.List;
import java.util.Map;

import com.fable.insightview.platform.entity.Dict;
import com.fable.insightview.platform.entity.SysConstantTypeBean;

public interface DictService{
	public void loadConstant();
	public void clear();
	public List<Dict> getItemsById(Long id);
	public SysConstantTypeBean getTypeBeanById(String typeName);
	public Dict getItemValueById(Long typeId,Long itemId);
	public List<SysConstantTypeBean> getTypeList();
	public Map<Integer,String> getConstantItems(String itemType);
}
