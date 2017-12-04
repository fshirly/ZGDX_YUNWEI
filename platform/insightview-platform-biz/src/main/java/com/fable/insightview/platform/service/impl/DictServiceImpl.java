package com.fable.insightview.platform.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.dao.DictDao;
import com.fable.insightview.platform.entity.Dict;
import com.fable.insightview.platform.entity.SysConstantTypeBean;
import com.fable.insightview.platform.service.DictService;

@Service("DictService")
public class DictServiceImpl implements DictService {
	
	private Map<String, Map<Integer,String>> constantMap = new ConcurrentHashMap<String, Map<Integer,String>>();
	
	@Autowired
	protected DictDao dictDao;

//    public void addItem(Long itemId,String itemName){
//        this.itemsMap.put(itemId, itemName);
//    }
//    
//    public String getItemValue(Long itemId){
//        return this.itemsMap.get(itemId);
//    }
//    public Map<Long,String> getItems(){
//        return this.itemsMap;
//    }
//    
//    public void clear(){
//    	this.itemsMap.clear();
//    }
    
	private void load() {
		loadConstant();
	}
	
	public void clear() {
		if(constantMap.size()==0){
			return ;
		} else {
			constantMap.clear();
		}
	}
	
	public void loadConstant() {
		dictDao.loadConstant(constantMap);
	}
	
	public List<Dict> getItemsById(Long id) {
		return dictDao.getItemsById(id);
	}
	
	public SysConstantTypeBean getTypeBeanById(String typeName) {
		return dictDao.getTypeBeanById(typeName);
	}

	@Override
	public Dict getItemValueById(Long typeId, Long itemId) {
		return dictDao.getItemsById(typeId, itemId);
	}
	
	@Override
	public List<SysConstantTypeBean> getTypeList(){
		return this.dictDao.getTypeList();
	}

	public synchronized Map<Integer, String> getConstantItems(String itemType) {
		if(constantMap.size()==0){
			this.load();
		}
		Map<Integer,String> items = this.constantMap.get(itemType);
		if (items != null) {
			return items;
		} else {
			return new HashMap<Integer,String>();
		}
	}
}
