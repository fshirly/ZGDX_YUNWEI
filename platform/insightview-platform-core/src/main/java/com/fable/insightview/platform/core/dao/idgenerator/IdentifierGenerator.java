package com.fable.insightview.platform.core.dao.idgenerator;

import com.fable.insightview.platform.core.exception.DaoException;

public interface IdentifierGenerator {
	public abstract Object generate() throws DaoException;
}
