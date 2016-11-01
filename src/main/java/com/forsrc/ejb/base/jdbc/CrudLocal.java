package com.forsrc.ejb.base.jdbc;

import java.io.Serializable;

import javax.ejb.Local;

import com.forsrc.ejb.base.dao.BaseDao;

//@Local
public interface CrudLocal<E, PK extends Serializable> extends BaseDao<E, PK> {

}
