package com.fable.insightview.platform.importdata.resolver;

import java.util.List;

import com.fable.insightview.platform.importdata.resolver.metadata.MetaDataFactory;
import com.fable.insightview.platform.importdata.resolver.metadata.Reloadable;

public abstract class AbstractDataResolver<T> implements DataResolver<T> {

	protected Class<T> mappingClass;

	protected void initMetaData() {
		MetaDataFactory.getInstance().buildMetaData(mappingClass);
	}

	public abstract List<T> resolve();

	public Class<T> getMappingClass() {
		return mappingClass;
	}

	public void setMappingClass(Class<T> mappingClass) {
		this.mappingClass = mappingClass;
		this.initMetaData();
	}

	public boolean isReloadable() {
		Reloadable reloadable = mappingClass.getAnnotation(Reloadable.class);
		if (reloadable == null) {
			return false;
		} else if (reloadable.value()) {
			this.initMetaData();
		}
		return reloadable.value();
	}
}
