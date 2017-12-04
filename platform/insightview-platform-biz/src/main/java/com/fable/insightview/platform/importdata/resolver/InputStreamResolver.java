package com.fable.insightview.platform.importdata.resolver;

import java.io.InputStream;

public abstract class InputStreamResolver<T> extends AbstractDataResolver<T>{

	private InputStream inputStream;
	
	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
}
