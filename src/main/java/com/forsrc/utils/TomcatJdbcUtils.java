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

import org.apache.tomcat.jdbc.pool.PoolProperties;

public class TomcatJdbcUtils extends JdbcUtils{

	public static DataSource getDataSource(String driverName, String url, String user,
			String password) {
		PoolProperties p = new PoolProperties();
		p.setUrl(url);
		p.setDriverClassName(driverName);
		p.setUsername(user);
		p.setPassword(password);
		p.setJmxEnabled(true);
		p.setTestWhileIdle(false);
		p.setTestOnBorrow(true);
		p.setValidationQuery("SELECT 1");
		p.setTestOnReturn(false);
		p.setValidationInterval(30000);
		p.setTimeBetweenEvictionRunsMillis(30000);
		p.setMaxActive(100);
		p.setInitialSize(10);
		p.setMaxWait(10000);
		p.setRemoveAbandonedTimeout(60);
		p.setMinEvictableIdleTimeMillis(30000);
		p.setMinIdle(10);
		p.setLogAbandoned(true);
		p.setRemoveAbandoned(true);
		p.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
		org.apache.tomcat.jdbc.pool.DataSource datasource = new org.apache.tomcat.jdbc.pool.DataSource();
		datasource.setPoolProperties(p);
		return datasource;
	}

	public static DataSource getDataSource(PoolProperties poolProperties) {
		org.apache.tomcat.jdbc.pool.DataSource datasource = new org.apache.tomcat.jdbc.pool.DataSource();
		datasource.setPoolProperties(poolProperties);
		return datasource;
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
