package com.fable.insightview.platform.formdesign.service.impl;

import org.springframework.stereotype.Service;

@Service("viewTextByTitleKey")
public class ViewTextByTitleKey extends ViewTextBySQL{

	@Override
	String textKey() {
		// TODO Auto-generated method stub
		return "title";
	}

}
