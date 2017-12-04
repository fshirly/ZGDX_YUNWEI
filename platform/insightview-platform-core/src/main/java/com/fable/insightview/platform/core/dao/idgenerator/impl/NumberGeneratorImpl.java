package com.fable.insightview.platform.core.dao.idgenerator.impl;

import java.lang.annotation.Annotation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fable.insightview.platform.core.dao.idgenerator.IdentifierGenerator;
import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;
import com.fable.insightview.platform.core.exception.DaoException;
import com.fable.insightview.platform.core.util.BeanLoader;

public class NumberGeneratorImpl implements IdentifierGenerator {

	class IdConnectionCallback implements ConnectionCallback {
		private int count;

		public IdConnectionCallback() {
			count = conf.allocationSize();
		}

		public IdConnectionCallback(int count) {
			this.count = count;
		}

		public Object doInConnection(Connection conn) throws SQLException,
				DataAccessException {
			try {
				int rows;
				do {
					PreparedStatement qps = conn.prepareStatement(query);
					// System.out.println("fetchsize  === "
					// +qps.getFetchSize());
					PreparedStatement ips = null;
					ResultSet rs = null;
					try {
						qps.setString(1, conf.name());
						rs = qps.executeQuery();
						boolean isInitialized = rs.next();
						if (!isInitialized) {
							nextvalue = conf.begin();
							ips = conn.prepareStatement(insert);
							ips.setString(1, conf.name());
							ips.setInt(2, nextvalue);
//							ips.setTimestamp(3,
//									new Timestamp(System.currentTimeMillis()));
							ips.execute();
						} else {
							nextvalue = rs.getInt(1);
						}

					} catch (SQLException sqle) {
						throw new DaoException(
								"could not read or init a hi value", sqle);
					} finally {
						if(rs != null){
							rs.close();
						}
						if(ips != null){
							ips.close();
						}
						if(qps != null){
							qps.close();
						}
					}
					PreparedStatement ups = conn.prepareStatement(update);
					try {
						maxvalue = nextvalue + count;
						ups.setInt(1, maxvalue);
//						ups.setTimestamp(2,
//								new Timestamp(System.currentTimeMillis()));
						ups.setString(2, conf.name());
						ups.setInt(3, nextvalue);
						rows = ups.executeUpdate();
					} catch (SQLException sqle) {
						throw new DaoException("could not update hi value in: "
								+ table + ".", sqle);
					} finally {
						ups.close();
					}
				} while (rows == 0);
			} catch (SQLException e) {
				throw new DaoException("could not generate id", e);
			}
			return nextvalue;
		}
	}

	private String query;
	private String update;
	private String insert;
	private NumberGenerator conf;
	private volatile int nextvalue = 0;
	private volatile int maxvalue = 0;
	private JdbcTemplate jdbcTemplate;
	private PlatformTransactionManager txManager;
	private String table;
	private ConnectionCallback ccb;

	public NumberGeneratorImpl(Annotation anno, DataSource DataSource,
			PlatformTransactionManager txManager, String table) {
		conf = (NumberGenerator) anno;
		this.jdbcTemplate = new JdbcTemplate(DataSource);
		this.txManager = txManager;
		this.table = table;
		this.query = "select GenValue from " + table + " where GenName=?";// for
																	// update";
		this.update = "update " + table
				+ " set GenValue = ? where GenName = ? and GenValue = ?";
		this.insert = "insert into " + table
				+ " (GenName, GenValue) values (?, ?)";
		this.ccb = new IdConnectionCallback();
	}

	public Object generate() throws DaoException {
		return generate(1)[0];
	}

	public synchronized Object[] generate(int count) throws DaoException {
		int i = 0;
		Object[] r = new Object[count];
		for (i = 0; i < count; i++) {
			if (nextvalue >= maxvalue) {
				ConnectionCallback localccb = this.ccb;
				if (count - i > conf.allocationSize()) {
					localccb = new IdConnectionCallback(Math.max(count - i,
							conf.allocationSize()));
				}
				doInTransaction(localccb,
						TransactionDefinition.PROPAGATION_REQUIRES_NEW);
			}
			r[i] = nextvalue++;
		}
		return r;
	}

	private Object doInTransaction(ConnectionCallback callback, int type) {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition(
				type);
		TransactionStatus status = txManager.getTransaction(def);
		try {
			Object result = jdbcTemplate.execute(callback);
			txManager.commit(status);
			return result;
		} catch (DaoException ex) {
			txManager.rollback(status);
			throw ex;
		} catch (Throwable ex) {
			txManager.rollback(status);
			throw new DaoException(ex);
		}
	}

}
