package com.forsrc.ejb.base.jdbc;

import java.io.Serializable;

import javax.ejb.Remote;

import com.forsrc.ejb.base.dao.BaseDao;

//@Remote
public interface CrudRemote<E, PK extends Serializable> extends BaseDao<E, PK> {

}
