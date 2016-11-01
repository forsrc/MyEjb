package com.forsrc.ejb.base.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;

import com.forsrc.ejb.base.dao.BaseDao;
import com.forsrc.exception.DaoException;

public class BaseDaoImpl<E, PK extends Serializable> implements BaseDao<E, PK> {
	@PersistenceContext(unitName = "pu-myejb")
	protected EntityManager entityManager;
	@Resource(mappedName = "java:jboss/datasources/ExampleDS")
	protected DataSource dataSource;

	private static final ThreadLocal<EntityTransaction> THREADLOCAL_TRANSACTION = new ThreadLocal<EntityTransaction>();

	private Class<E> entityClass;

	public BaseDaoImpl() {
		try {
			getEntityClass();
		} catch (Exception e) {
			LOGGER.warn("\n××××××××××\ngetEntityClass() fail!\n××××××××××\n");
			// e.printStackTrace();
			LOGGER.debug(e.getMessage(), e);
		}
	}

	@Override
	public List<E> list(String sql, Map<String, Object> param, int start,
			int size) throws DaoException {
		Query query = this.entityManager.createQuery(sql);
		if (param != null) {
			for (Map.Entry<String, Object> entry : param.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		query.setFirstResult(start);
		query.setMaxResults(size);
		try {
			return query.getResultList();
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	@Override
	public E get(String sql, Map<String, Object> param) throws DaoException {
		Query query = this.entityManager.createQuery(sql);
		if (param != null) {
			for (Map.Entry<String, Object> entry : param.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		query.setFirstResult(0);
		query.setMaxResults(1);
		try {
			return (E) query.getSingleResult();
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	@Override
	public E find(PK pk) throws DaoException {
		try {
			return (E) this.entityManager.find(getEntityClass(), pk);
		} catch (Exception e) {
			throw new DaoException(e);
		}

	}

	@Override
	public E find(Class<E> cls, PK pk) throws DaoException {
		try {
			return (E) this.entityManager.find(cls, pk);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	@Override
	public int save(String sql, Map<String, Object> param) throws DaoException {
		return executeUpdate(sql, param);
	}

	@Override
	public void save(E e) {
		this.entityManager.persist(e);
		this.entityManager.flush();
	}

	@Override
	public int update(String sql, Map<String, Object> param) throws DaoException {
		return executeUpdate(sql, param);

	}

	@Override
	public int delete(String sql, Map<String, Object> param) throws DaoException {
		return executeUpdate(sql, param);
	}

	private int executeUpdate(String sql, Map<String, Object> param) throws DaoException {
		Query query = this.entityManager.createQuery(sql);
		if (param != null) {
			for (Map.Entry<String, Object> entry : param.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		try {
			return query.executeUpdate();
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	@Override
	public void delete(E e) {
		this.entityManager.remove(e);
		this.entityManager.flush();
	}

	@Override
	public void update(E e) {
		this.entityManager.merge(e);
		this.entityManager.flush();
	}

	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public Class<E> getEntityClass() {
		if (this.entityClass == null) {
			synchronized (BaseDaoImpl.class) {
				if (this.entityClass == null) {
					this.entityClass = (Class<E>) ((ParameterizedType) getClass()
							.getGenericSuperclass()).getActualTypeArguments()[0];
				}
			}
		}
		return this.entityClass;
	}

	public void setEntityClass(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	public BaseDao getTransaction() {
		EntityTransaction entityTransaction = THREADLOCAL_TRANSACTION.get();
		if (entityTransaction == null || !entityTransaction.isActive()) {
			synchronized (BaseDaoImpl.class) {
				if (entityTransaction == null || !entityTransaction.isActive()) {
					entityTransaction = this.entityManager.getTransaction();
					THREADLOCAL_TRANSACTION.set(entityTransaction);
				}
			}
		}
		return this;
	}

	@Override
	public BaseDao begin() {
		EntityTransaction entityTransaction = THREADLOCAL_TRANSACTION.get();
		if (entityTransaction != null && entityTransaction.isActive()) {
			synchronized (BaseDaoImpl.class) {
				if (entityTransaction != null && entityTransaction.isActive()) {
					entityTransaction.begin();
				}
			}
		}
		return this;
	}

	@Override
	public BaseDao commit() {
		EntityTransaction entityTransaction = THREADLOCAL_TRANSACTION.get();
		if (entityTransaction != null && entityTransaction.isActive()) {
			synchronized (BaseDaoImpl.class) {
				if (entityTransaction != null && entityTransaction.isActive()) {
					entityTransaction.commit();
				}
			}
		}
		return this;
	}

	@Override
	public BaseDao rollback() {

		EntityTransaction entityTransaction = THREADLOCAL_TRANSACTION.get();
		if (entityTransaction != null && entityTransaction.isActive()) {
			synchronized (BaseDaoImpl.class) {
				if (entityTransaction != null && entityTransaction.isActive()) {
					entityTransaction.rollback();
				}
			}
		}
		return this;
	}
}
