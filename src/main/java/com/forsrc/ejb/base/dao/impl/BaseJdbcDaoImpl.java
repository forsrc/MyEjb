package com.forsrc.ejb.base.dao.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityTransaction;
import javax.sql.DataSource;

import com.forsrc.ejb.base.dao.BaseJdbcDao;
import com.forsrc.exception.DaoException;

public class BaseJdbcDaoImpl<E, PK extends Serializable> implements BaseJdbcDao<E, PK> {

	@Resource(mappedName = "java:jboss/datasources/ExampleDS")
	protected DataSource dataSource;

	private static final ThreadLocal<Connection> THREADLOCAL_CONNECTION = new ThreadLocal<Connection>();

	private static final ThreadLocal<EntityTransaction> THREADLOCAL_TRANSACTION = new ThreadLocal<EntityTransaction>();

	public BaseJdbcDaoImpl() {
	}

	private Connection getConnection() throws DaoException {
		Connection connection = THREADLOCAL_CONNECTION.get();
		try {
			if (connection == null || connection.isClosed()) {
				synchronized (BaseJdbcDaoImpl.class) {
					if (connection == null || connection.isClosed()) {
						connection = dataSource.getConnection();
						THREADLOCAL_CONNECTION.set(connection);
					}
				}
			}
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
			throw new DaoException(e);
		}
		return connection;
	}

	@Override
	public List<Map<String, Object>> list(String sql, Object[] param,
			int start, int size) throws DaoException {

		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement(sql);
			if (param != null) {
				for (int i = 0; i < param.length; i++) {
					preparedStatement.setObject(i, param[i]);
				}
			}
			preparedStatement.setFetchDirection(start);
			preparedStatement.setMaxRows(size);
			ResultSet resultSet = preparedStatement.executeQuery();
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			int columnCount = resultSetMetaData.getColumnCount();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			try {
				while (resultSet.next()) {
					Map<String, Object> map = new HashMap<String, Object>(
							columnCount);
					for (int i = 1; i <= columnCount; i++) {
						map.put(resultSetMetaData.getColumnName(i),
								resultSet.getObject(i));
					}
					list.add(map);
				}
			} finally {
				close(resultSet);
				close(preparedStatement);
			}
			return list;
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
			throw new DaoException(e);
		}
	}

	@Override
	public Map<String, Object> get(String sql, Object[] param)
			throws DaoException {
		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement(sql);
			if (param != null) {
				for (int i = 0; i < param.length; i++) {
					preparedStatement.setObject(i, param[i]);
				}
			}
			preparedStatement.setFetchDirection(0);
			preparedStatement.setMaxRows(1);
			ResultSet resultSet = preparedStatement.executeQuery();
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			int columnCount = resultSetMetaData.getColumnCount();
			Map<String, Object> map = new HashMap<String, Object>(1);
			try {
				if (resultSet.next()) {
					for (int i = 1; i <= columnCount; i++) {
						map.put(resultSetMetaData.getColumnName(i),
								resultSet.getObject(i));
					}
				}
			} finally {
				close(resultSet);
				close(preparedStatement);
			}
			return map;
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
			throw new DaoException(e);
		}
	}

	@Override
	public int save(String sql, List<Object[]> params) throws DaoException {
		return executeBatch(sql, params);
	}

	@Override
	public int update(String sql, List<Object[]> params) throws DaoException {
		return executeBatch(sql, params);
	}

	@Override
	public int delete(String sql, List<Object[]> params) throws DaoException {
		return executeBatch(sql, params);
	}

	public BaseJdbcDao getTransaction() {
		return this;
	}

	@Override
	public BaseJdbcDao begin() throws DaoException {
		Connection connection = THREADLOCAL_CONNECTION.get();
		try {
			if (connection != null && !connection.isClosed()) {
				synchronized (BaseJdbcDaoImpl.class) {
					if (connection != null && !connection.isClosed()) {
						connection.setAutoCommit(false);
					}
				}
			}
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
			throw new DaoException(e);
		}
		return this;
	}

	@Override
	public BaseJdbcDao commit() throws DaoException {
		Connection connection = THREADLOCAL_CONNECTION.get();
		try {
			if (connection != null && !connection.isClosed()) {
				synchronized (BaseJdbcDaoImpl.class) {
					if (connection != null && !connection.isClosed()) {
						connection.commit();
					}
				}
			}
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
			throw new DaoException(e);
		}
		return this;
	}

	@Override
	public BaseJdbcDao rollback() throws DaoException {
		Connection connection = THREADLOCAL_CONNECTION.get();
		try {
			if (connection != null && !connection.isClosed()) {
				synchronized (BaseJdbcDaoImpl.class) {
					if (connection != null && !connection.isClosed()) {
						connection.rollback();
					}
				}
			}
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
			throw new DaoException(e);
		}
		return this;
	}

	public static void close(AutoCloseable closeable) {
		if (closeable == null) {
			return;
		}
		try {
			closeable.close();
		} catch (Exception e) {
		}
	}

	@Override
	public int save(String sql, Object[] param) throws DaoException {
		return executeUpdate(sql, param);
	}

	@Override
	public int update(String sql, Object[] param) throws DaoException {
		return executeUpdate(sql, param);
	}

	@Override
	public int delete(String sql, Object[] param) throws DaoException {
		return executeUpdate(sql, param);
	}

	// @Override
	public int executeUpdate(String sql, Object[] param) throws DaoException {
		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement(sql);
			if (param != null) {
				for (int i = 0; i < param.length; i++) {
					preparedStatement.setObject(i, param[i]);
				}
			}
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
			throw new DaoException(e);
		}
	}

	// @Override
	public int executeBatch(String sql, List<Object[]> params)
			throws DaoException {
		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement(sql);
			if (params == null) {
				return 0;
			}
			try {
				int size = params.size();
				int count = 0;
				for (int i = 0; i < size; i++) {
					Object[] param = params.get(i);
					if (param == null) {
						continue;
					}
					for (int j = 0; j < param.length; j++) {
						preparedStatement.setObject(j, param[j]);
					}
					preparedStatement.addBatch();
					count++;
					if ((i + 1) % 100 == 0) {
						preparedStatement.executeBatch();
					}
				}
				preparedStatement.executeBatch();
				return count;
			} finally {
				close(preparedStatement);
			}
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
			throw new DaoException(e);
		}
	}

}
