package com.fable.insightview.platform.core.dao.idgenerator.impl;

import java.lang.annotation.Annotation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fable.insightview.platform.core.dao.idgenerator.IdentifierGenerator;
import com.fable.insightview.platform.core.dao.idgenerator.StringGenerator;
import com.fable.insightview.platform.core.exception.DaoException;
import com.fable.insightview.platform.core.util.BeanLoader;

public class StringGeneratorImpl implements IdentifierGenerator {
	private String query;
	private String update;
	private String insert;
	private StringGenerator conf;
	private JdbcTemplate jdbcTemplate;
	private PlatformTransactionManager txManager;
	private String table;

	public StringGeneratorImpl(Annotation anno, DataSource DataSource,
			PlatformTransactionManager txManager, String table) {
		conf = (StringGenerator) anno;
		this.jdbcTemplate = new JdbcTemplate(DataSource);
		this.txManager = txManager;
		this.table = table;
		this.query = "select value,time from " + table + " where name=?";// for
																			// update";
		this.update = "update " + table
				+ " set value = ?, time = ? where name = ? and value = ?";
		this.insert = "insert into " + table
				+ " (name, value, time) values (?, ?, ?)";
	}

	public synchronized Object generate() throws DaoException {
		final Timestamp time = new Timestamp((new Date()).getTime());
		StringBuilder buffer = new StringBuilder(conf.format());
		for (int pos = buffer.indexOf("%"); pos != -1; pos = buffer.indexOf(
				"%", pos + 3)) { // 至少隔3隔字符${}
			int e = buffer.indexOf("}", pos);
			if (e == -1) {
				return buffer.toString();
			}
			final char cmd = buffer.charAt(pos + 1);
			String param = buffer.substring(pos + 3, e);
			String value = null;
			switch (cmd) {
			case 'x':
			case 'X':
				try {

					long id = (Long) doInTransaction(new ConnectionCallback() {
						public Object doInConnection(Connection conn)
								throws SQLException, DataAccessException {
							return doGenerate(conn, cmd == 'X', time);
						}
					}, TransactionDefinition.PROPAGATION_REQUIRES_NEW);

					int len = Integer.parseInt(param);
					value = String.valueOf(id);
					if (value.length() > len) {
						value = value.substring(value.length() - len);
					} else if (value.length() < len) {
						int delta = len - value.length();
						StringBuilder b = new StringBuilder(len);
						for (int i = 0; i < delta; i++)
							b.append("0");
						b.append(value);
						value = b.toString();
					}
					break;
				} catch (NumberFormatException ex) {
					throw new DaoException("无效的长度:" + param, ex);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			case 'd':
				try {
					SimpleDateFormat format = new SimpleDateFormat(param);
					value = format.format(time);
				} catch (IllegalArgumentException ex) {
					throw new DaoException("无效的日期模式:" + param, ex);
				}
				break;
			default:
				break;
			}
			if (value != null) {
				buffer.replace(pos, e + 1, value);
			}

		}
		return buffer.toString();
	}

	public long doGenerate(Connection conn, boolean isDay, Timestamp time)
			throws DaoException {
		try {
			int rows;
			do {
				long lastValue;
				long nextValue;
				Timestamp lastTime;

				PreparedStatement qps = conn.prepareStatement(query);
				PreparedStatement ips = null;
				ResultSet rs = null;
				try {
					qps.setString(1, conf.name());
					rs = qps.executeQuery();
					boolean isInitialized = rs.next();
					if (!isInitialized) {
						ips = conn.prepareStatement(insert);
						ips.setLong(2, 1);
						ips.setString(1, conf.name());
						ips.setTimestamp(3, time);
						ips.execute();
						lastValue = 1;
						lastTime = time;
					} else {
						lastValue = rs.getInt(1);
						lastTime = rs.getTimestamp(2);
					}
				} catch (SQLException sqle) {
					throw new DaoException("could not read or init a hi value",
							sqle);
				} finally {
					if (ips != null || rs != null) {
						rs.close();
						ips.close();
						qps.close();
					}
				}
				nextValue = lastValue;
				if (isDay) {
					Calendar c1 = java.util.Calendar.getInstance();
					c1.setTime(lastTime == null ? time : lastTime);
					int day1 = c1.get(Calendar.DAY_OF_YEAR);
					c1.setTime(time);
					int day2 = c1.get(Calendar.DAY_OF_YEAR);
					if (day1 != day2)
						nextValue = 1;
				}
				PreparedStatement ups = conn.prepareStatement(update);
				try {
					ups.setLong(1, nextValue + 1);
					ups.setTimestamp(2, time);
					ups.setString(3, conf.name());
					ups.setLong(4, lastValue);
					rows = ups.executeUpdate();
				} catch (SQLException sqle) {
					throw new DaoException("could not update hi value in: "
							+ table + ".", sqle);
				} finally {
					ups.close();
				}
				if (rows != 0) {
					return nextValue;
				}
			} while (true);

		} catch (SQLException e) {
			throw new DaoException("could not generate id", e);
		}
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
