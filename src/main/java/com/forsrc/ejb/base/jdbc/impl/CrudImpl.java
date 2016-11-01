package com.forsrc.ejb.base.jdbc.impl;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import com.forsrc.ejb.base.dao.BaseDao;
import com.forsrc.ejb.base.dao.impl.BaseDaoImpl;
import com.forsrc.ejb.base.jdbc.CrudLocal;
import com.forsrc.ejb.base.jdbc.CrudRemote;


public class CrudImpl<E, PK extends Serializable> extends BaseDaoImpl<E, PK> implements CrudLocal<E, PK>, CrudRemote<E, PK>,
		BaseDao<E, PK> {
	@PersistenceContext(unitName = "pu-myejb")
	private EntityManager entityManager;
	@Resource(mappedName = "java:jboss/datasources/ExampleDS")
	private DataSource dataSource;
}
