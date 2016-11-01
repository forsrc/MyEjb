package com.forsrc.ejb.base.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.forsrc.exception.DaoException;


public interface BaseJdbcDao<E, PK extends Serializable> {

	public static final Logger LOGGER = Logger.getLogger(BaseJdbcDao.class
			.getName());

	public List<Map<String, Object>> list(String sql, Object[] param,
			int start, int size) throws DaoException;

	public Map<String, Object> get(String sql, Object[] param) throws DaoException;

	public int save(String sql, List<Object[]> params) throws DaoException;

	public int update(String sql, List<Object[]> params) throws DaoException;

	public int delete(String sql, List<Object[]> params) throws DaoException;
	
	public int save(String sql, Object[] param) throws DaoException;

	public int update(String sql, Object[] param) throws DaoException;
	
	public int delete(String sql, Object[] param) throws DaoException;
	
	//public int executeUpdate(String sql, Object[] param) throws DaoException;

	public BaseJdbcDao getTransaction() throws DaoException;

	public BaseJdbcDao begin() throws DaoException;

	public BaseJdbcDao commit() throws DaoException;

	public BaseJdbcDao rollback() throws DaoException;
}
