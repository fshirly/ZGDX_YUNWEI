package com.fable.insightview.dict.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fable.insightview.platform.common.util.KeyValPair;
import com.fable.insightview.platform.dict.util.DictionaryLoader;
import com.fable.insightview.platform.entity.Dict;
import com.fable.insightview.platform.entity.ItsmEventTypeDict;
import com.fable.insightview.platform.entity.SysConstantTypeBean;
import com.fable.insightview.platform.service.DictService;
import com.fable.insightview.platform.service.ItsmEventTypeDictService;

/**
 * 
 * 用于读取字典表
 * @author Jiuwei Li
 *
 */
@Controller
@RequestMapping("/dict")
public class DictController {

	@Autowired
	private DictService service;
	
	@Autowired
	private ItsmEventTypeDictService itsmEventTypeDictService;
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/readItems")
	@ResponseBody
	public List<KeyValPair<String,String>> readItems(Long id) {
		List<KeyValPair<String,String>> pairs = new ArrayList<KeyValPair<String,String>>();
		//改为由缓存中获取
//		List<Dict> items = service.getItemsById(id);
		Map<Integer,String> map = DictionaryLoader.getConstantItems(String.valueOf(id));
		for(Integer key : map.keySet()) {
			pairs.add(new KeyValPair(key,map.get(key)));
		}
		return pairs;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/readConstantItemAlias")
	@ResponseBody
	public List<KeyValPair<String,String>> readConstantItemAlias(Long id) {
		List<KeyValPair<String,String>> pairs = new ArrayList<KeyValPair<String,String>>();
		List<Dict> items = service.getItemsById(id);
		for(int i = 0; i < items.size(); i++) {
			pairs.add(new KeyValPair(items.get(i).getConstantItemName(),items.get(i).getConstantItemAlias()));
		}
		return pairs;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/readItemsByName")
	@ResponseBody
	public List<KeyValPair<String,String>> readItems(String typeName) {
		Map<Integer,String> map=DictionaryLoader.getConstantItems(typeName);
		List<KeyValPair<String,String>> pairs = new ArrayList<KeyValPair<String,String>>();
//		SysConstantTypeBean typeBean=service.getTypeBeanById(typeName);
//		List<Dict> items = service.getItemsById(typeBean.getConstantTypeId());
//		for(int i = 0; i< items.size(); i++) {
//			pairs.add(new KeyValPair(items.get(i).getConstantItemId(),items.get(i).getConstantItemName()));
//		}
		for (Map.Entry<Integer, String> entry : map.entrySet()) { 
			pairs.add(new KeyValPair(entry.getKey(),entry.getValue()));
			}
		return pairs;
	}
	
	
	@SuppressWarnings("unused")
	@RequestMapping("/readValueByNameAndId")
	@ResponseBody
	public String readItems(String typeName,Long itemId) {
		List<KeyValPair<String,String>> pairs = new ArrayList<KeyValPair<String,String>>();
		SysConstantTypeBean typeBean=service.getTypeBeanById(typeName);
		Dict dictBean = service.getItemValueById(typeBean.getConstantTypeId(), itemId);
		return "guanliyuan";
	}
	
	
	/**
	 * 对于ItsmEventType字典表的加载
	 * @param parentTypeId
	 * @author maow
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/readEventTypeItems")
	@ResponseBody
	public List<KeyValPair<String,String>> readEventTypeItems(Integer parentTypeId) {
		List<KeyValPair<String,String>> pairs = new ArrayList<KeyValPair<String,String>>();
		
		List<ItsmEventTypeDict> items = itsmEventTypeDictService.getEventTypeItems(parentTypeId);
		for(int i = 0; i< items.size(); i++) {
			pairs.add(new KeyValPair(items.get(i).getId(),items.get(i).getTitle()));
		}
		return pairs;
	}
	
	@RequestMapping("/getSysConstantList")
	@ResponseBody
	public List<SysConstantTypeBean> getTypeList() {
		List<SysConstantTypeBean> list=service.getTypeList();
		return list;
	}
}
