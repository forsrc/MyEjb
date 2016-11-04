package com.forsrc.ejb.user.dao.impl;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import com.forsrc.ejb.base.dao.impl.BaseDaoImpl;
import com.forsrc.ejb.user.dao.UserDao;
import com.forsrc.ejb.user.dao.UserDaoLocal;
import com.forsrc.ejb.user.dao.UserDaoRemote;
import com.forsrc.pojo.User;

@Local(UserDaoLocal.class)
@Remote(UserDaoRemote.class)
@Stateless()
public class UserDaoImpl extends BaseDaoImpl<User, Long> implements UserDao {

}
