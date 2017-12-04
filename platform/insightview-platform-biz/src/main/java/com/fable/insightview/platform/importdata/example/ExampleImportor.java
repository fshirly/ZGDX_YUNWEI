package com.fable.insightview.platform.importdata.example;

import java.util.List;

import com.fable.insightview.platform.importdata.AbstractDataImportor;
import com.fable.insightview.platform.importdata.DataType;
import com.fable.insightview.platform.importdata.ImportResult;
import com.fable.insightview.platform.importdata.ValidResult;
import com.fable.insightview.platform.importdata.resolver.DataResolver;
import com.fable.insightview.platform.importdata.resolver.DataResolverFactory;
import com.fable.insightview.platform.importdata.resolver.ExcelResolver;

public class ExampleImportor extends AbstractDataImportor<ExampleEntity> {

	public DataResolver<ExampleEntity> getResolver() {
		ExcelResolver<ExampleEntity> dataResolver = (ExcelResolver) DataResolverFactory.getInstance().getDataResolver(DataType.EXCEL,
						ExampleEntity.class);
		return dataResolver;
	}

	/*
	 * 校验失败处理
	 */
	@Override
	protected boolean validFaileHandle(List<ValidResult> vaildResultList) {
		return false;
	}

	@Override
	protected boolean logicHandle(List<ExampleEntity> data,
			ImportResult<ExampleEntity> result) {
//		System.out.println("这里编写你的逻辑代码！");
		return true;
	}
}
