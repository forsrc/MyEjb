package com.forsrc.ejb.base.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import com.forsrc.exception.DaoException;

@TransactionManagement(TransactionManagementType.CONTAINER) 
public interface BaseService<E, PK extends Serializable> {

	public static final Logger LOGGER = Logger.getLogger(BaseService.class.getName());

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED) 
	public List<E> list(String sql, Map<String, Object> param, int start,
			int size) throws DaoException;

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED) 
	public E get(String sql, Map<String, Object> param) throws DaoException;

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED) 
	public E find(PK pk) throws DaoException;
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED) 
	public E find(Class<E> cls, PK pk) throws DaoException;

	@TransactionAttribute(TransactionAttributeType.REQUIRED) 
	public int save(String sql, Map<String, Object> param) throws DaoException;

	@TransactionAttribute(TransactionAttributeType.REQUIRED) 
	public void save(E e) throws DaoException;

	@TransactionAttribute(TransactionAttributeType.REQUIRED) 
	public int update(String sql, Map<String, Object> param) throws DaoException;

	@TransactionAttribute(TransactionAttributeType.REQUIRED) 
	public void update(E e) throws DaoException;

	@TransactionAttribute(TransactionAttributeType.REQUIRED) 
	public int delete(String sql, Map<String, Object> param) throws DaoException;

	@TransactionAttribute(TransactionAttributeType.REQUIRED) 
	public void delete(E e) throws DaoException;

	public Class<E> getEntityClass();


}
