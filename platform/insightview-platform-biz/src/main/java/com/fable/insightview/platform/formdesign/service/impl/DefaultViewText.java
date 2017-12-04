package com.fable.insightview.platform.formdesign.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.fable.insightview.platform.formdesign.service.IViewText;

@Service("defaultViewText")
public class DefaultViewText implements IViewText{

	@Override
	public String getText(String value, Map<String, String> attribute) {
		// TODO Auto-generated method stub
		return value;
	}

}
