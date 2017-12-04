package com.fable.insightview.platform.formdesign.service.impl;

import org.springframework.stereotype.Service;

@Service("viewTextByNameKey")
public class ViewTextByNameKey extends ViewTextBySQL{

	@Override
	String textKey() {
		// TODO Auto-generated method stub
		return "name";
	}

}
