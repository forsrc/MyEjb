package com.forsrc.ejb.base.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.forsrc.exception.DaoException;

public interface BaseDao<E, PK extends Serializable> {

	public static final Logger LOGGER = Logger.getLogger(BaseDao.class.getName());

	public List<E> list(String sql, Map<String, Object> param, int start,
			int size) throws DaoException;

	public E get(String sql, Map<String, Object> param) throws DaoException;

	public E find(PK pk) throws DaoException;

	public E find(Class<E> cls, PK pk) throws DaoException;

	public int save(String sql, Map<String, Object> param) throws DaoException;

	public void save(E e) throws DaoException;

	public int update(String sql, Map<String, Object> param) throws DaoException;

	public void update(E e) throws DaoException;

	public int delete(String sql, Map<String, Object> param) throws DaoException;

	public void delete(E e) throws DaoException;

	public Class<E> getEntityClass();

	public BaseDao getTransaction() throws DaoException;

	public BaseDao begin() throws DaoException;

	public BaseDao commit() throws DaoException;

	public BaseDao rollback() throws DaoException;
}
