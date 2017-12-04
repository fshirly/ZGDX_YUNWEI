package com.fable.insightview.platform.formdesign.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fable.insightview.platform.common.util.JsonUtil;

@Service("viewTextCustom")
public class ViewTextCustom extends AbstractViewText{

	@Override
	List<Map<String, String>> initValue( Map<String, String> attribute){
		// TODO Auto-generated method stub
		return JsonUtil.toListString(attribute.get("customInitValues"));
	}

	@Override
	String textKey(){
		// TODO Auto-generated method stub
		return "text";
	}

}
