package com.forsrc.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

public class JdbcUtils {

	private static final ThreadLocal<DataSource> THREADLOCAL_DATASOURCE = new ThreadLocal<DataSource>();
	private static final ThreadLocal<Connection> THREADLOCAL_CONNECTION = new ThreadLocal<Connection>();

	public final Connection getConnection() throws SQLException {
		Connection connection = THREADLOCAL_CONNECTION.get();
		if (connection == null || connection.isClosed()) {
			synchronized (JdbcUtils.class) {
				if (connection == null || connection.isClosed()) {
					connection = THREADLOCAL_DATASOURCE.get().getConnection();
					THREADLOCAL_CONNECTION.set(connection);
				}
			}
		}
		return connection;
	}

	public final Connection getConnection(String url, String user,
			String password) throws SQLException {

		Connection connection = THREADLOCAL_CONNECTION.get();
		if (connection == null || connection.isClosed()) {
			synchronized (JdbcUtils.class) {
				if (connection == null || connection.isClosed()) {
					connection = DriverManager.getConnection(url, user,
							password);
					THREADLOCAL_CONNECTION.set(connection);
				}
			}
		}
		return connection;
	}

	public JdbcUtils setDataSource(DataSource dataSource) {
		THREADLOCAL_DATASOURCE.set(dataSource);
		return this;
	}

	public void close() throws SQLException {
		Connection connection = THREADLOCAL_CONNECTION.get();
		if (connection != null && !connection.isClosed()) {
			synchronized (JdbcUtils.class) {
				if (connection != null && !connection.isClosed()) {
					connection.close();
					THREADLOCAL_CONNECTION.remove();
				}
			}
		}
	}

	public JdbcUtils call(HandlerConnection handler) throws SQLException {
		Connection connection = getConnection();
		handler.handle(connection);
		return this;
	}

	public List<Map<String, Object>> list(final String sql, Object[] parameters, int start, int size)
			throws SQLException {
		Connection connection = getConnection();

		return list(connection, sql, parameters, start, size);
	}

	public static List<Map<String, Object>> list(final Connection connection,
			final String sql, final Object[] parameters, int start, int size) throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		if (parameters != null) {
			for (int i = 0; i < parameters.length; i++) {
				preparedStatement.setObject(i, parameters[i]);
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
				Map<String, Object> map = new HashMap<String, Object>(columnCount);
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
	}

	public JdbcUtils call(final String sql, final Object[] parameters,
			final HandlerResultSet handlerResultSet) throws SQLException {
		call(getConnection(), sql, parameters, handlerResultSet);
		return this;
	}

	public static void call(final Connection connection, final String sql,
			final Object[] parameters, final HandlerResultSet handlerResultSet)
			throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		if (parameters != null) {
			for (int i = 0; i < parameters.length; i++) {
				preparedStatement.setObject(i + 1, parameters[i]);
			}
		}
		ResultSet resultSet = preparedStatement.executeQuery();
		ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
		int columnCount = resultSetMetaData.getColumnCount();
		handlerResultSet.handle(resultSetMetaData);
		try {
			while (resultSet.next()) {
				for (int i = 1; i <= columnCount; i++) {
					if (!handlerResultSet.handle(resultSet.getRow(),
							resultSetMetaData.getColumnName(i),
							resultSet.getObject(i))) {
						break;
					}
				}
			}
		} finally {
			close(resultSet);
			close(preparedStatement);
		}
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

	public static interface HandlerResultSet {
		public void handle(ResultSetMetaData resultSetMetaData)
				throws SQLException;

		public boolean handle(int row, String columnName, Object value)
				throws SQLException;
	}

	public static interface HandlerConnection {
		public void handle(Connection connection) throws SQLException;
	}

}
